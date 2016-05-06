
package de.jkblume.sav.components.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

import de.jkblume.sav.architecture.gen.components.AbstractTechnicalSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import net.opengis.gml.v32.impl.TimeInstantImpl;
import net.opengis.gml.v32.impl.TimePositionImpl;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.sensorml.v20.impl.EventImpl;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Text;

public class SerialTechnicalSensor extends AbstractTechnicalSensor {

	private static final String BAUDRATE_PARAMETER_NAME = "baudrate";
	private static final String TIMEOUT_PARAMETER_NAME = "timeout";
	private static final String SERIAL_PORT_PARAMETER_NAME = "serialPort";

	private SerialPort serialPort;
	private BufferedReader inputReader;
	
	private boolean running;

	public SerialTechnicalSensor(String name) {
		super(name);
	}

	public void setup() {
		if (!validateDescription()) {
			System.err.println("There is a problem with the configuration of sensor " + getSmlConfiguration().getId());
			return;
		}
		
		Text serialPortParameter = (Text) getParameter(SERIAL_PORT_PARAMETER_NAME);
		CommPortIdentifier portId;
		try {
			portId = CommPortIdentifier.getPortIdentifier(serialPortParameter.getValue());
		} catch (NoSuchPortException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return;
		}
		
		Count timeoutParameter = (Count) getParameter(TIMEOUT_PARAMETER_NAME);
		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), timeoutParameter.getValue());
		} catch (PortInUseException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return;
		}
		
		Count baudrateParameter = (Count) getParameter(BAUDRATE_PARAMETER_NAME);
		try {
			serialPort.setSerialPortParams(baudrateParameter.getValue(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return;
		}
		
		try {
			inputReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		} catch (IOException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return;
		}

		// initially deactive/stop data notifications, so the sensor is not running
		serialPort.notifyOnDataAvailable(false);
			
		// add event port
		try {
			serialPort.addEventListener(new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent serialPortEvent) {
					if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
						String inputString = null;
						try {
							inputString = inputReader.readLine();
						} catch (IOException e) {
							System.err.println("There is a problem while retrieving sensor value from sensor " + getSmlConfiguration().getId());
							System.err.println(e.getMessage());
							return;
						}
						
						DataComponent outputComponent = getSmlConfiguration().getOutputComponent("output1");
						Text input =  (Text) outputComponent.copy();
						input.setValue(inputString);						
						
						IOPropertyList list = new IOPropertyList();
						list.add(input);
						
						IOPropertyList eventValue = null;
						if (getIProcess() != null) {
							eventValue = getIProcess().execute(list);
						}

						Event event = createEvent(eventValue);
						
						setCurrentEvent(event);
					}
				}

				private Event createEvent(IOPropertyList values) {
					Event event = new EventImpl();
					for (AbstractSWEIdentifiable value : values) {
						if (value instanceof DataComponent)
							event.addProperty((DataComponent) value);
					}	
					
					// FIXME: here should be more work done with time zones etc...
					TimeInstantImpl timeInstant = new TimeInstantImpl();
					TimePositionImpl timePosition = new TimePositionImpl();
					timePosition.setDecimalValue(System.currentTimeMillis());
					timeInstant.setTimePosition(timePosition);
					event.setTimeAsTimeInstant(timeInstant);
					return event;
				}
			});
		} catch (TooManyListenersException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return;
		}

	}
	
	private boolean validateDescription() {
		boolean result = true;
		
		AbstractPhysicalProcess description = getSmlConfiguration();
		IOPropertyList parameterList = description.getParameterList();
		
		if (parameterList == null) {
			return false;
		}
				
		result &= parameterList.contains(SERIAL_PORT_PARAMETER_NAME);
		result &= parameterList.contains(TIMEOUT_PARAMETER_NAME);
		result &= parameterList.contains(BAUDRATE_PARAMETER_NAME);
	
		return result;
	}
	
	private AbstractSWEIdentifiable getParameter(String parameterName) {
		IOPropertyList parameterList = getSmlConfiguration().getParameterList();
		return parameterList.get(parameterName);
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		if (serialPort != null) {
			serialPort.close();
		}
	}

	@Override
	public void handleIProcessConnected(IProcess item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIProcessDisconnected(IProcess item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startImpl() {
		serialPort.notifyOnDataAvailable(true);
	}

	@Override
	public void stopImpl() {
		serialPort.notifyOnDataAvailable(false);
	}

	@Override
	public Boolean isRunningImpl() {
		return false;
	}


}
