
package de.jkblume.sav.components.ports;

import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.gen.ports.AbstractJavaFXVisualisationStrategy;
import de.jkblume.sav.components.utils.UI;
import javafx.application.Application;
import net.opengis.sensorml.v20.Event;

public class JavaFXVisualisationStrategy extends AbstractJavaFXVisualisationStrategy {


	private Thread uiThread;
	private double firstTimestamp = 0.0;
	
	public JavaFXVisualisationStrategy(String name) {
		super(name);
	}

	public void setup() {
		uiThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Application.launch(UI.class, new String[] {});
			}
		});
		uiThread.start();
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
		UI.addPoint(sensorId, event);
	}

	@Override
	public void addSensor(ISensor sensor) {
		String sensorId = sensor.getSmlConfiguration().getId();
		UI.addChart(sensorId);
	}

	@Override
	public void removeSensor(ISensor sensor) {
		// TODO Auto-generated method stub
		
	}

}
