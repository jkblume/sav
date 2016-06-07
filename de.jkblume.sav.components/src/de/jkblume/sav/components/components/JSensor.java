
package de.jkblume.sav.components.components;

import de.jkblume.sav.architecture.gen.components.AbstractSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.IRetrieveStrategy;
import de.jkblume.sav.components.utils.MySMLUtils;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;

public class JSensor extends AbstractSensor {

	private static final String SAMPLING_RATE_PARAMETER_NAME = "samplingRate";
	private Thread pullThread;
	
	public JSensor(String name) {
		super(name);
	}

	public void setup() {
		pullThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(getRunning()) {
					
					Object value = getIRetrieveStrategy().retrieveValue();
					if (value == null) {
						continue;
					}
					
					value = (IOPropertyList) getIProcess().execute(value);
					if (value == null) {
						continue;
					}
					
					Event currentEvent = MySMLUtils.createEvent(value);
					setLastEvent(currentEvent);
					
					try {
						Thread.sleep(getSamplingRate());
					} catch (InterruptedException e) {
						System.out.println("Polling thread of sensor " + getId() + " was interupted");
					}
				}
			}
		});
		
		

	}

	public void destroy() {
		stop();
	}

	public void startImpl() {
		setRunning(true);
		pullThread.start();
	}

	public void stopImpl() {
		setRunning(false);
		pullThread.interrupt();
	}

	@Override
	public String getIdImpl() {
		return getIProcess().getSmlConfiguration().getId();
	}
	
	@Override
	public Integer getSamplingRateImpl() {
		AbstractSWEIdentifiable parameter = getIProcess().getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		return ((Count) parameter).getValue();
	}

	@Override
	public IOPropertyList retrieveOutputStructureImpl() {
		return getIProcess().getSmlConfiguration().getOutputList();
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
	public void handleIRetrieveStrategyConnected(IRetrieveStrategy connectedItem) {
		//TODO Handle
	}

	@Override
	public void handleIRetrieveStrategyDisconnected(IRetrieveStrategy disconnectedItem) {
		//TODO Handle
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("running")) {
			//TODO:Implement
		}

		if (propertyName.equals("lastEvent")) {
			//TODO:Implement
		}

	}

}
