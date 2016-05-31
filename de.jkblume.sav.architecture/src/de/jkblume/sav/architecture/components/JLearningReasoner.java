
package de.jkblume.sav.architecture.components;

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

import java.util.*;
import org.smags.componentmodel.annotations.ParameterA;

public class JLearningReasoner extends AbstractLearningReasoner {

	public JLearningReasoner(String name) {
		super(name);
	}

	public void setup() {
		//TODO:Implement
	}

	public void destroy() {
		//TODO:Implement
	}

	public void startGestureImpl(Category category) {
		//TODO: IMPLEMENT

	}

	public void stopGestureImpl(Category category) {
		//TODO: IMPLEMENT

	}

	public void teachCurrentStateImpl(Category category) {
		//TODO: IMPLEMENT

	}

	public void trainImpl() {
		//TODO: IMPLEMENT

	}

	public void buildClassifierImpl() {
		//TODO: IMPLEMENT

	}

	public DataComponent getQualityOfServiceImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public void startImpl() {
		//TODO: IMPLEMENT

	}

	public void stopImpl() {
		//TODO: IMPLEMENT

	}

	public Boolean initializeImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public Boolean isRunningImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public String getIdImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public Integer getSamplingRateImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public IOPropertyList retrieveValuesImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public Boolean validateSmlConfigurationImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public Object executeImpl(Object value) {
		//TODO: IMPLEMENT
		return null;
	}

	@Override
	public void handleIProcessAdded(IProcess connectedItem) {
		//TODO Handle
	}

	@Override
	public void handleIProcessRemoved(IProcess disconnectedItem) {
		//TODO Handle
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

		if (propertyName.equals("injectorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("extractorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("lastEvent")) {
			//TODO:Implement
		}

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

	}

}
