package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.components.AbstractLogicalSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.gml.v32.TimePosition;
import net.opengis.gml.v32.impl.TimeInstantImpl;
import net.opengis.gml.v32.impl.TimePositionImpl;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.sensorml.v20.impl.EventImpl;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;

public class JLogicalSensor extends AbstractLogicalSensor {

	private Thread pollThread;
	private boolean running;
	
	public JLogicalSensor(String name) {
		super(name);
	}

	public void setup() {
		pollThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (isRunning()) {
					IOPropertyList list = new IOPropertyList();
					for (ISensor sensor : getISensors()) {
						Event currentEvent = sensor.getCurrentEvent();
						list.addAll(currentEvent.getPropertyList());
					}
					IOPropertyList output = getIProcess().execute(list);
					setCurrentEvent(createEvent(output));
				}
			}
			
			private Event createEvent(IOPropertyList values) {
				Event event = new EventImpl();
				for (AbstractSWEIdentifiable value : values) {
					if (value instanceof DataComponent)
						event.addProperty((DataComponent) value);
				}	
				
				// FIXME: here should be more work done with time zones etc...
				TimeInstant timeInstant = new TimeInstantImpl();
				TimePosition timePosition = new TimePositionImpl();
				timePosition.setDecimalValue(System.currentTimeMillis());
				timeInstant.setTimePosition(timePosition);
				event.setTimeAsTimeInstant(timeInstant);
				return event;
			}
		});
	}

	public void destroy() {
		//TODO:Implement
	}

	public Event getCurrentEventImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public void startImpl() {
		running = true;
		pollThread.start();
	}

	public void stopImpl() {
		running = false;
		pollThread.interrupt();
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
	public void handleISensorAdded(ISensor connectedItem) {
		//TODO Handle
	}

	@Override
	public void handleISensorRemoved(ISensor disconnectedItem) {
		//TODO Handle
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

	}

}
