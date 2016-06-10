
package de.jkblume.sav.components.components;

import de.jkblume.sav.architecture.gen.components.AbstractSimpleProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.Text;

public class JNumericScaleProcess extends AbstractSimpleProcess {

	private static final String SOURCE_MIN_VALUE_PARAMETER_NAME = "sourceMinValue";
	private static final String SOURCE_MAX_VALUE_PARAMETER_NAME = "sourceMaxValue";
	private static final String TARGET_MIN_VALUE_PARAMETER_NAME = "targetMinValue";
	private static final String TARGET_MAX_VALUE_PARAMETER_NAME = "targetMaxValue";
	private static final String COMPONENT_NAME_PARAMETER_NAME = "componentName";

	private double sourceMinValue;
	private double sourceMaxValue;
	private double targetMinValue;
	private double targetMaxValue;
	private String componentName;

	public JNumericScaleProcess(String name) {
		super(name);
	}

	public void setup() {
		Quantity sourceMinValueParameter = (Quantity) getParameter(SOURCE_MIN_VALUE_PARAMETER_NAME);
		sourceMinValue = sourceMinValueParameter.getValue();
		Quantity sourceMaxValueParameter = (Quantity) getParameter(SOURCE_MAX_VALUE_PARAMETER_NAME);
		sourceMaxValue = sourceMaxValueParameter.getValue();
		Quantity targetMinValueParameter = (Quantity) getParameter(TARGET_MIN_VALUE_PARAMETER_NAME);
		targetMinValue = targetMinValueParameter.getValue();
		Quantity targetMaxValueParameter = (Quantity) getParameter(TARGET_MAX_VALUE_PARAMETER_NAME);
		targetMaxValue = targetMaxValueParameter.getValue();

		Text componentNameParameter = (Text) getParameter(COMPONENT_NAME_PARAMETER_NAME);
		componentName = componentNameParameter.getValue();
	}

	public void destroy() {
		// TODO:Implement
	}

	public Object executeImpl(Object input) {
		Quantity toScale = (Quantity) ((IOPropertyList) input).get(componentName);

		if (toScale == null) {
			return null;
		}
				
		double result = (toScale.getValue() - sourceMinValue) / (sourceMaxValue - sourceMinValue)
				* (targetMaxValue - targetMinValue) + targetMinValue;

		Quantity scaled = toScale.copy();
		scaled.setValue(result);

		IOPropertyList list = new IOPropertyList();
		list.add(componentName, scaled);

		return list;
	}

	private AbstractSWEIdentifiable getParameter(String parameterName) {
		IOPropertyList parameterList = getSmlConfiguration().getParameterList();
		return parameterList.get(parameterName);
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("smlConfiguration")) {
			// TODO:Implement
		}

	}

}
