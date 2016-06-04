
package de.jkblume.sav.components.ports;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.architecture.gen.porttypes.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.lang.Class;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataComponent;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.IOPropertyList;

import de.jkblume.sav.architecture.gen.porttypes.*;

import de.jkblume.sav.components.gen.ports.*;
import de.jkblume.sav.components.utils.ClassifyUiHelper;

import org.smags.componentmodel.IPort;
import java.util.*;

public class ClassificationVisualisationStrategy extends AbstractClassificationVisualisationStrategy {
	
	private ClassifyUiHelper uiHelper;
	
	public ClassificationVisualisationStrategy(String name) {
		super(name);
	}

	public void setup() {
		setUiHelper(new ClassifyUiHelper());
		getUiHelper().initialize();
	}

	public void destroy() {
		getUiHelper().destroy();
	}

	public void visualizeEvent(ISensor source, Event event) {
		Category value = (Category) event.getPropertyList().get(0);
		if (value != null) {
			getUiHelper().updateLabel(value.getValue());		
		}
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

	public ClassifyUiHelper getUiHelper() {
		return uiHelper;
	}

	public void setUiHelper(ClassifyUiHelper uiHelper) {
		this.uiHelper = uiHelper;
	}

}
