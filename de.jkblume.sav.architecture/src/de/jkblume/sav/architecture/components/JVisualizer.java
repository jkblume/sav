
package de.jkblume.sav.architecture.components;

import java.util.HashMap;
import java.util.Map;

import de.jkblume.sav.architecture.gen.components.AbstractVisualizer;
import de.jkblume.sav.architecture.gen.porttypes.IMyPushSensorSubject;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;

public class JVisualizer extends AbstractVisualizer {

	private Thread pollThread;

	private Map<String, Double> lastVisualizedEvent = new HashMap<>();
	
	public JVisualizer(String name) {
		super(name);
	}

	public void setup() {
		pollThread = new Thread(new Runnable() {

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
		pollThread.start();
	}

	public void destroy() {
		//TODO:Implement
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSensorImpl(ISensor sensor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSensorImpl(ISensor sensor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean initialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOPropertyList retrieveValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isRunning() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractPhysicalProcess getSmlConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Event getLastEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastEvent(Event lastEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIMyPushSensorAdded(IMyPushSensorSubject item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIMyPushSensorRemoved(IMyPushSensorSubject item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyEventChangedImpl(Object sender, Event argument) {
		// TODO Auto-generated method stub
		
	}

}
