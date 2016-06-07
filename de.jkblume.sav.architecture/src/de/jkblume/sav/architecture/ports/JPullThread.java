
package de.jkblume.sav.architecture.ports;

import de.jkblume.sav.architecture.gen.ports.AbstractJPullThread;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.architecture.utils.MySMLUtils;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;

public class JPullThread extends AbstractJPullThread {
	
	private Thread pullThread;
	private boolean running; 
	
	public JPullThread(String name) {
		super(name);
	}

	public void setup() {
		pullThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isRunning()) {
					
					Object values = getISensor().retrieveValues();
					if (values == null) {
						continue;
					}
					
					IOPropertyList castedValues = (IOPropertyList) getISensor().execute(values);
					if (castedValues == null) {
						continue;
					}
					
					Event currentEvent = MySMLUtils.createEvent(castedValues);
					getISensor().setLastEvent(currentEvent);
					
					try {
						Thread.sleep(getISensor().getSamplingRate());
					} catch (InterruptedException e) {
						System.out.println("Polling thread of sensor " + getISensor().getId() + " was interupted");
					}
				}
			}
		});
	}

	public void destroy() {
		stop();
	}

	public void start() {
		running = true;
		pullThread.start();
	}
	
	public void stop() {
		running = false;
		pullThread.interrupt();
	}
	
	public Boolean isRunning() {
		return running;
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}
	
	@Override
	public void handleISensorConnected(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleISensorDisconnected(ISensor item) {
		// TODO Auto-generated method stub
		
	}

}
