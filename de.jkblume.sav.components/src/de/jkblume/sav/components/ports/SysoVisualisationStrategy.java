
package de.jkblume.sav.components.ports;

import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.gen.ports.AbstractSysoVisualisationStrategy;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.DataComponent;

public class SysoVisualisationStrategy extends AbstractSysoVisualisationStrategy {

	public SysoVisualisationStrategy(String name) {
		super(name);
	}

	public void setup() {
		//TODO: IMPLEMENT
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

	@Override
	public void visualizeEvent(ISensor source, Event event) {
		String sensorId = source.getSmlConfiguration().getId();
		System.out.println("Sensor: " + sensorId);
		for (DataComponent component : event.getPropertyList()) {
			System.out.println("\t- Value: " + component.toString());
		}
	}

	@Override
	public void addSensor(ISensor sensor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSensor(ISensor sensor) {
		// TODO Auto-generated method stub
		
	}

}
