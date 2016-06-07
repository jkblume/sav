
package de.jkblume.sav.components.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

import de.jkblume.sav.architecture.gen.components.AbstractTechnicalRetrieveStrategy;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Text;

public class JSerialPortRetrieveStrategy extends AbstractTechnicalRetrieveStrategy {

	private static final String BAUDRATE_PARAMETER_NAME = "baudrate";
	private static final String TIMEOUT_PARAMETER_NAME = "timeout";
	private static final String SERIAL_PORT_PARAMETER_NAME = "serialPort";
	
	private SerialPort serialPort;
	private BufferedReader inputReader;

	private IOPropertyList lastValue;
	
	public JSerialPortRetrieveStrategy(String name) {
		super(name);
	}

	public void setup() {

		Text serialPortParameter = (Text) getSmlConfiguration().getParameter(SERIAL_PORT_PARAMETER_NAME);
		CommPortIdentifier portId = null;
		try {
			portId = CommPortIdentifier.getPortIdentifier(serialPortParameter.getValue());
		} catch (NoSuchPortException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			e.printStackTrace();
			return;
		}
		
		Count timeoutParameter = (Count) getSmlConfiguration().getParameter(TIMEOUT_PARAMETER_NAME);
		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), timeoutParameter.getValue());
		} catch (PortInUseException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return;
		}
		
		Count baudrateParameter = (Count) getSmlConfiguration().getParameter(BAUDRATE_PARAMETER_NAME);
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
		serialPort.notifyOnDataAvailable(true);
			
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
						
						DataComponent outputComponent = getSmlConfiguration().getOutputComponent("output");
						Text input =  (Text) outputComponent.copy();
						input.setValue(inputString);						
						
						lastValue = new IOPropertyList();
						lastValue.add(input);
					}
				}
			});
		} catch (TooManyListenersException e) {
			System.err.println("There is a problem connection to sensor " + getSmlConfiguration().getId());
			System.err.println(e.getMessage());
			return;
		}
		return;

	}

	public void destroy() {
		if (serialPort != null) {
			serialPort.close();
		}
	}

	public Object retrieveValueImpl() {
		return lastValue;
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

	}

}
