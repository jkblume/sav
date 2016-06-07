
package de.jkblume.sav.components.components;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.components.AbstractSimpleProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.Quantity;

public class JSimpleRuleReasoner extends AbstractSimpleProcess {

	public JSimpleRuleReasoner(String name) {
		super(name);
	}

	public void setup() {
		// nothing to setup here
	}

	public void destroy() {
		// noting to destroy here
	}

	@Override
	public Object executeImpl(Object input) {
		Quantity component = (Quantity) ((IOPropertyList) input).get("flex");
		
		Category category = new CategoryImpl();
		if (component == null) {
			return null;
		}
		
		if (component.getValue() < 780) {
			category.setValue("not buckled");
		} else {
			category.setValue("buckled");
		}
		
		
		IOPropertyList list = new IOPropertyList();
		list.add(category);
		
		return list;
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

	}

}
