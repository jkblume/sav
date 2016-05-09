
package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.components.AbstractTechnicalPullSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.utils.MySMLUtils;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;

public class JTechnicalPullSensor extends AbstractTechnicalPullSensor {

	private Thread pollingThread;
	private boolean running;
	
	private String id;
	private int samplingRate;
	
	public JTechnicalPullSensor(String name) {
		super(name);
	}

	public void setup() {
		if (!validateSmlConfiguration()) {
			throw new IllegalStateException("Failure during validation of sensor " + id + "'s configuration");
		}
		
		pollingThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (isRunning()) {
					IOPropertyList values = retrieveValues();
					
					if (getIProcess() != null) {
						values = getIProcess().execute(values);
					}
					
					Event currentEvent = MySMLUtils.createEvent(values);
					setLastEvent(currentEvent);
					
					try {
						Thread.sleep(samplingRate);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						Thread.sleep(samplingRate);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
			}
		});
	}

	public void destroy() {
		//TODO:Implement
	}

	@Override
	public Boolean initializeImpl() {
		return validateSmlConfiguration();
	}

	private Boolean validateSmlConfiguration() {
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

	public String getIdImpl() {
		return id;
	}

	public IOPropertyList retrieveValuesImpl() {
		throw new IllegalStateException("This class need to be implemented by a implementation port!");
	}

	public void startImpl() {
		running = true;
		pollingThread.start();
	}

	public void stopImpl() {
		running = false;
		pollingThread.interrupt();
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


}
