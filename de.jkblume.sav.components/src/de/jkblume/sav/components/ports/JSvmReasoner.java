
package de.jkblume.sav.components.ports;

import de.jkblume.sav.components.types.*;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

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
import weka.core.Instances;
import weka.classifiers.Classifier;

import de.jkblume.sav.architecture.gen.porttypes.*;

import de.jkblume.sav.components.gen.ports.*;
import org.smags.componentmodel.IPort;
import java.util.*;

public class JSvmReasoner extends AbstractJSvmReasoner {

	public JSvmReasoner(String name) {
		super(name);
	}

	public void setup() {
		//TODO: IMPLEMENT
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public Boolean buildClassifier() {
		Boolean result = base.buildClassifier();
		return result;
	}
	public void teachCurrentState(Category category) {
		base.teachCurrentState(category);
	}
	public void startGesture(Category category) {
		base.startGesture(category);
	}
	public void stopGesture(Category category) {
		base.stopGesture(category);
	}

	public Object execute(Object input) {
		Object result = base.execute(input);
		return result;
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

	@Override
	public void handleISensorAdded(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleISensorRemoved(ISensor item) {
		// TODO Auto-generated method stub
		
	}
}
