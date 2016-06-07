
package de.jkblume.sav.components.components;

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

import java.util.*;
import org.smags.componentmodel.annotations.ParameterA;

public class JWekaLearningReasoner extends AbstractLearningProcess {

	public JWekaLearningReasoner(String name) {
		super(name);
	}

	public void setup() {
		//TODO:Implement
	}

	public void destroy() {
		//TODO:Implement
	}

	public Boolean buildClassifierImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public void teachCurrentStateImpl(Category category) {
		//TODO: IMPLEMENT

	}

	public void startGestureImpl(Category category) {
		//TODO: IMPLEMENT

	}

	public void stopGestureImpl(Category category) {
		//TODO: IMPLEMENT

	}

	public Object executeImpl(Object input) {
		//TODO: IMPLEMENT
		return null;
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

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

	}

}
