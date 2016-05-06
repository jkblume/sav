
package de.jkblume.sav.components.components;

import java.util.Random;

import org.vast.data.QuantityImpl;

import de.jkblume.sav.architecture.gen.components.AbstractTechnicalSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import net.opengis.gml.v32.impl.TimeInstantImpl;
import net.opengis.gml.v32.impl.TimePositionImpl;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.impl.EventImpl;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;

public class SimulatingTechnicalSensor extends AbstractTechnicalSensor {

	private Thread simulatingThread;
	private Random random = new Random();
	private double start = 1;
	private boolean running;
	
	public SimulatingTechnicalSensor(String name) {
		super(name);
	}

	public void setup() {
		simulatingThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (isRunning()) {
					Quantity quantity = new QuantityImpl();
					int nextInt = random.nextInt(10);
					double i = (nextInt+1)/10.0;
					System.out.println(i);
					double d = start + i;
					quantity.setValue(d);
					start = quantity.getValue();
					
					System.out.println("simulated " + start);
					
					setCurrentEvent(createEvent(quantity));
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			private Event createEvent(DataComponent value) {
				Event event = new EventImpl();
				event.addProperty(value);
				// FIXME: here should be more work done with time zones etc...
				TimeInstantImpl timeInstant = new TimeInstantImpl();
				TimePositionImpl timePosition = new TimePositionImpl();
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

	public void startImpl() {
		running = true;
		simulatingThread.start();
	}

	public void stopImpl() {
		running = false;
		simulatingThread.interrupt();
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

		if (propertyName.equals("description")) {
			//TODO:Implement
		}

		if (propertyName.equals("currentEvent")) {
			//TODO:Implement
		}

	}

}
