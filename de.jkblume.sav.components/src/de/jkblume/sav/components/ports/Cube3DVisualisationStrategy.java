
package de.jkblume.sav.components.ports;

import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.gen.ports.AbstractCube3DVisualisationStrategy;
import de.jkblume.sav.components.utils.CubeAnimator;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;

public class Cube3DVisualisationStrategy extends AbstractCube3DVisualisationStrategy {

	private CubeAnimator animator;
	
	public Cube3DVisualisationStrategy(String name) {
		super(name);
	}

	public void setup() {
		animator = new CubeAnimator();
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public void visualizeEvent(ISensor source, Event event) {
		if (event == null) {
			return;
		}
		
		DataComponent dataComponent = event.getPropertyList().get("gyroscope");
		if (dataComponent == null) {
			return;
		}
		
		DataRecord record = (DataRecord) dataComponent;
		double x = ((Quantity) record.getField("x")).getValue();
		double y = ((Quantity) record.getField("y")).getValue();
		double z = ((Quantity) record.getField("z")).getValue();
		
		
		animator.setRotation((float) x,(float) y,(float) z);
	}
	
	public void addSensor(ISensor sensor) {
		base.addSensor(sensor);
	}
	
	public void removeSensor(ISensor sensor) {
		base.removeSensor(sensor);
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

}
