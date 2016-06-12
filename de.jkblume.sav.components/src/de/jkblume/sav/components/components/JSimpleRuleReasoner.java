
package de.jkblume.sav.components.components;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.components.AbstractReasoningProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.Text;

public class JSimpleRuleReasoner extends AbstractReasoningProcess {

	public JSimpleRuleReasoner(String name) {
		super(name);
	}

	private static final String COMPONENT_NAME_PARAMETER_NAME = "componentName";
	private static final String THRESHOLD_PARAMETER_NAME = "threshold";
	private static final String OPERATION_PARAMETER_NAME = "operation";
	
	
	private String componentName;
	private double threshold;
	private String operation;
	
	public void setup() {
		Text componentNameParameter = (Text) getParameter(COMPONENT_NAME_PARAMETER_NAME);
		componentName = componentNameParameter.getValue();
		Quantity thresholdParameter = (Quantity) getParameter(THRESHOLD_PARAMETER_NAME);
		threshold = thresholdParameter.getValue();
		Text operationParameter = (Text) getParameter(OPERATION_PARAMETER_NAME);
		operation = operationParameter.getValue();
	}

	public void destroy() {
		// noting to destroy here
	}

	@Override
	public Object executeImpl(Object input) {
		Quantity component = (Quantity) ((IOPropertyList) input).get(componentName);
		
		Category category = new CategoryImpl();
		if (component == null) {
			return null;
		}
		
		String value = null;
		if (operation.equals("gt")) {
			if (component.getValue() > threshold) {
				value = "yes";
			} else {
				value = "no";
			}
		} else if (operation.equals("lt")) {
			if (component.getValue() < threshold) {
				value = "yes";
			} else {
				value = "no";
			}
		} else {
			return null;
		}
		

		category.setValue(value);
		IOPropertyList list = new IOPropertyList();
		list.add(category);
		
		return list;
	}


	private AbstractSWEIdentifiable getParameter(String parameterName) {
		IOPropertyList parameterList = getSmlConfiguration().getParameterList();
		return parameterList.get(parameterName);
	}
	
	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

	}

	@Override
	public Object injectImpl(IOPropertyList input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOPropertyList extractImpl(Object input) {
		// TODO Auto-generated method stub
		return null;
	}

}
