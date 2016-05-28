
package de.jkblume.sav.components.ports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.utils.MySMLUtils;
import de.jkblume.sav.components.gen.ports.AbstractSerialTechnicalSensor;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Text;

public class SerialTechnicalSensor extends AbstractSerialTechnicalSensor {
	private static final String BAUDRATE_PARAMETER_NAME = "baudrate";
	private static final String TIMEOUT_PARAMETER_NAME = "timeout";
	private static final String SERIAL_PORT_PARAMETER_NAME = "serialPort";

	private SerialPort serialPort;
	private BufferedReader inputReader;
	
	private boolean running;
	
	private IOPropertyList lastValue;
	
	public SerialTechnicalSensor(String name) {
		super(name);
	}

	public void setup() {
		if (!validateSmlConfiguration()) {
			throw new IllegalStateException("There is an error during validation of sensor " + base.getId() + "'s configuration");
		}
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public void notifyEventChanged(Object sender, Event argument) {
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		return base.getId();
	}

	@Override
	public Boolean initialize() {
		Text serialPortParameter = (Text) getSmlConfiguration().getParameter(SERIAL_PORT_PARAMETER_NAME);
		CommPortIdentifier portId;
		try {
			portId = CommPortIdentifier.getPortIdentifier(serialPortParameter.getValue());
		} catch (NoSuchPortException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return false;
		}
		
		Count timeoutParameter = (Count) getSmlConfiguration().getParameter(TIMEOUT_PARAMETER_NAME);
		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), timeoutParameter.getValue());
		} catch (PortInUseException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return false;
		}
		
		Count baudrateParameter = (Count) getSmlConfiguration().getParameter(BAUDRATE_PARAMETER_NAME);
		try {
			serialPort.setSerialPortParams(baudrateParameter.getValue(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return false;
		}
		
		try {
			inputReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		} catch (IOException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return false;
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
						
						IOPropertyList values = new IOPropertyList();
						values.add(input);
						
						if (getIProcess() != null) {
							values = (IOPropertyList) getIProcess().execute(values);
						}
						
						lastValue = values;
					}
				}
			});
		} catch (TooManyListenersException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	private Boolean validateSmlConfiguration() {
		boolean result = true;
		
		AbstractPhysicalProcess description = (AbstractPhysicalProcess) getSmlConfiguration();
		IOPropertyList parameterList = description.getParameterList();
		
		if (parameterList == null) {
			return false;
		}
				
		result &= parameterList.contains(SERIAL_PORT_PARAMETER_NAME);
		result &= parameterList.contains(TIMEOUT_PARAMETER_NAME);
		result &= parameterList.contains(BAUDRATE_PARAMETER_NAME);
	
		return result;
	}

	@Override
	public IOPropertyList retrieveValues() {
		return lastValue;
	}

	@Override
	public void start() {
		running = true;
		serialPort.notifyOnDataAvailable(true);
	}

	@Override
	public void stop() {
		running = false;
		serialPort.notifyOnDataAvailable(false);
	}

	@Override
	public Boolean isRunning() {
		return running;
	}

	@Override
	public Event getLastEvent() {
		return base.getLastEvent();
	}

	@Override
	public void setLastEvent(Event lastEvent) {
		base.setLastEvent(lastEvent);
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
	public Object execute(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		// TODO Auto-generated method stub
		
	}}
