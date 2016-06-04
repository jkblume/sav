
package de.jkblume.sav.components.ports;

import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.gen.ports.AbstractDiagramVisualisationStrategy;
import de.jkblume.sav.components.utils.DiagramUiHelper;
import net.opengis.sensorml.v20.Event;

public class DiagramVisualisationStrategy extends AbstractDiagramVisualisationStrategy {
	
	private DiagramUiHelper uiHelper;
	
	public DiagramVisualisationStrategy(String name) {
		super(name);
	}

	public void setup() {
		uiHelper = new DiagramUiHelper();
	}

	public void destroy() {
		uiHelper.destroy(getName());
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

	@Override
	public void visualizeEvent(ISensor source, Event event) {
		String sensorId = source.getSmlConfiguration().getId();
		uiHelper.addPoint(sensorId, event);
	}

	@Override
	public void addSensor(ISensor sensor) {
		String sensorId = sensor.getSmlConfiguration().getId();
		uiHelper.addChart(sensorId);
	}

	@Override
	public void removeSensor(ISensor sensor) {
		// TODO Auto-generated method stub
		
	}

}
