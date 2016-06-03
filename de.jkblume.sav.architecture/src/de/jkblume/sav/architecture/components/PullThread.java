package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.architecture.utils.MySMLUtils;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;

public class PullThread extends Thread implements Runnable{

	private ISensor sensor;
	
	public PullThread(ISensor sensor) {
		this.sensor = sensor;
	}
	
	@Override
	public void run() {
		while(sensor.isRunning()) {
			
			IOPropertyList values = sensor.retrieveValues();
			if (values == null) {
				continue;
			}
			
			values = (IOPropertyList) sensor.execute(values);
			if (values == null) {
				continue;
			}
			
			Event currentEvent = MySMLUtils.createEvent(values);
			sensor.setLastEvent(currentEvent);
			
			try {
				Thread.sleep(sensor.getSamplingRate());
			} catch (InterruptedException e) {
				System.out.println("Polling thread of sensor " + sensor.getId() + " was interupted");
			}
		}
	}

}
