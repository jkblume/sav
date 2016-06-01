
package de.jkblume.sav.components.components;

import java.util.List;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.components.AbstractSpecificationReasonerProcess;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;

public class SimpleRuleReasoner extends AbstractSpecificationReasonerProcess {

	public SimpleRuleReasoner(String name) {
		super(name);
	}

	public void setup() {
		//TODO:Implement
	}

	public void destroy() {
		//TODO:Implement
	}

	public void buildClassifierImpl(List<ISensor> sensors) {
		if (sensors == null) {
			return;
		}
		
		for (ISensor iSensor : sensors) {
			System.out.println(iSensor.getId());
		}
	}

	public Category classifyImpl(IOPropertyList input) {
		Quantity component = (Quantity) input.get("flex");
		
		Category category = new CategoryImpl();
		if (component == null) {
			return null;
		}
		
		if (component.getValue() < 780) {
			category.setValue("not buckled");
		} else {
			category.setValue("buckled");
		}
		
		return category;
	}

	public DataComponent getQualityOfServiceImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public Boolean initializeImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public Boolean validateSmlConfigurationImpl() {
		//TODO: IMPLEMENT
		return null;
	}

	public Object executeImpl(Object value) {
		Category classify = classify((IOPropertyList) value);
		
		IOPropertyList list = new IOPropertyList();
		list.add(classify);
		
		return list;
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

		if (propertyName.equals("injectorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("extractorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

	}

}
