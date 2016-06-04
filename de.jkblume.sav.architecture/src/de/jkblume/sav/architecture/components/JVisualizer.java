
package de.jkblume.sav.architecture.components;

import java.util.HashMap;
import java.util.Map;

import de.jkblume.sav.architecture.gen.components.AbstractVisualizer;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataComponent;

public class JVisualizer extends AbstractVisualizer {

	private Thread pullThread;

	private Map<String, Double> lastVisualizedEvent = new HashMap<>();
	
	public JVisualizer(String name) {
		super(name);
	}

	public void setup() {
		pullThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					for (ISensor sensor : getISensors()) {
						Event currentEvent = sensor.getLastEvent();
						if (currentEvent != null) {
							if (!alreadVisualized(sensor, currentEvent)) {
								visualizeEvent(sensor, currentEvent);
								TimeInstant timeInstant = (TimeInstant) currentEvent.getTime();
								double currentTimestamp = timeInstant.getTimePosition().getDecimalValue(); 
								lastVisualizedEvent.put(sensor.getSmlConfiguration().getId(), currentTimestamp);
							}
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						System.out.println("Visualizer thread interuppted");
					}
				}
			}

			private boolean alreadVisualized(ISensor source, Event event) {
				Double lastTimestamp = lastVisualizedEvent.get(source.getSmlConfiguration().getId());
				if (lastTimestamp == null) {
					return false;
				}
								
				TimeInstant timeInstant = (TimeInstant) event.getTime();
				double currentTimestamp = timeInstant.getTimePosition().getDecimalValue();
								
				return lastTimestamp.equals(currentTimestamp);
			}
		});
		pullThread.start();
	}

	public void destroy() {
		pullThread.interrupt();
	}


	@Override
	public void handleISensorAdded(ISensor connectedItem) {
		addSensor(connectedItem);
	}

	@Override
	public void handleISensorRemoved(ISensor disconnectedItem) {
		removeSensor(disconnectedItem);
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

	}

	@Override
	public void visualizeEventImpl(ISensor source, Event event) {
		String sensorId = source.getSmlConfiguration().getId();
		System.out.println("### Sensor: " + sensorId);
		for (DataComponent component : event.getPropertyList()) {
			if (component == null) {
				continue;
			}
			System.out.println("Value: " + component.toString());
		}
	}	

	@Override
	public void addSensorImpl(ISensor sensor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSensorImpl(ISensor sensor) {
		// TODO Auto-generated method stub
		
	}

	
}
