
package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.components.AbstractLogicalPullSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.architecture.utils.MySMLUtils;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;

public class JLogicalPullSensor extends AbstractLogicalPullSensor {
	private Thread pollingThread;
	private boolean running;
	
	private String id;
	private int samplingRate;
	
	public JLogicalPullSensor(String name) {
		super(name);
	}

	public void setup() {
		Boolean initializationSuccess = initialize();
		if (!initializationSuccess) {
			throw new IllegalStateException("Failure during initialization of sensor " + id);
		}
		
		pollingThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				IOPropertyList values = retrieveValues();				
				
				// process values
				if (getIProcess() != null) {
					values = getIProcess().execute(values);
				} 
				
				Event currentEvent = MySMLUtils.createEvent(values);
				setLastEvent(currentEvent);
				
				try {
					Thread.sleep(samplingRate);
				} catch (InterruptedException e) {
					System.out.println("Polling thread of sensor " + id + " was interupted");
				}
			}
		});
	}

	public void destroy() {
		stop();
	}

	public void startImpl() {
		pollingThread.start();
		running = true;
	}

	public void stopImpl() {
		pollingThread.interrupt();
		running = false;
	}

	public Boolean isRunningImpl() {
		return running;
	}

	@Override
	public void handleIProcessConnected(IProcess connectedItem) {
		//TODO Handle
	}

	@Override
	public void handleIProcessDisconnected(IProcess disconnectedItem) {
		//TODO Handle
	}
	
	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

		if (propertyName.equals("lastEvent")) {
			//TODO:Implement
		}

	}

	@Override
	public IOPropertyList retrieveValuesImpl() {
		IOPropertyList values = new IOPropertyList();
		
		// retrieve values from pull sensors
		for (ISensor pullSensor : getISensors()) {
			OgcPropertyList<DataComponent> partialResult = pullSensor.getLastEvent().getPropertyList();
			values.addAll(partialResult);
		}
		
		return values;
	}

	@Override
	public String getIdImpl() {
		return id;
	}

	@Override
	public void handleISensorAdded(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleISensorRemoved(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	public Boolean validateSmlConfigurationImpl() {
		boolean result = true;
		
		id = getSmlConfiguration().getId();
		result &= id != null;
	
		
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter("samplingRate");
		result &= parameter != null && parameter instanceof Count;
		if (result) {
			samplingRate = ((Count) parameter).getValue();
		}
		
		return result;
	}

	@Override
	public Boolean initializeImpl() {
		return validateSmlConfigurationImpl();
	}
}
