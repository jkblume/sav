
package de.jkblume.sav.components.components;

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

import java.util.*;
import org.smags.componentmodel.annotations.ParameterA;

public class SimpleLogicRuleReasoner extends AbstractSpecificationReasoningProcess {

	public SimpleLogicRuleReasoner(String name) {
		super(name);
	}

	public void setup() {
		//TODO:Implement
	}

	public void destroy() {
		//TODO:Implement
	}

	public Boolean validateSmlConfigurationImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public Object executeImpl(Object value) {
		//TODO: IMPLEMENT
		return null;
	}

	public void buildClassifierImpl(List<ISensor> sensors) {
		//TODO: IMPLEMENT

	}

	public Category classifyCurrentStateImpl(IOPropertyList input) {
		//TODO: IMPLEMENT
		return null;
	}

	public DataComponent getQualityOfServiceImpl() {
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
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

		if (propertyName.equals("injectorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("extractorProvided")) {
			//TODO:Implement
		}

	}

}
