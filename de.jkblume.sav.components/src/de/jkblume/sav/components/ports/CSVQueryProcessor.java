
package de.jkblume.sav.components.ports;

import org.vast.data.CountImpl;
import org.vast.data.QuantityImpl;
import org.vast.data.TextImpl;

import de.jkblume.sav.components.gen.ports.AbstractCSVQueryProcessor;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataType;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.ScalarComponent;
import net.opengis.swe.v20.Text;

public class CSVQueryProcessor extends AbstractCSVQueryProcessor {

	private static final String COLUMN_PARAMETER_NAME = "column";
	private static final String SEPARATOR_PARAMETER_NAME = "separator";

	private int column;
	private String separator;
	private DataType dataType;
	
	public CSVQueryProcessor(String name) {
		super(name);
	}

	public void setup() {
		if (!validateInputs()) {
			System.err.println("There is a problem with the input description of sensor " + getSmlConfiguration().getId());
			return;
		}
		if (!validateOutputs()) {
			System.err.println("There is a problem with the output description of sensor " + getSmlConfiguration().getId());
			return;
		}
		if (!validateParameters()) {
			System.err.println("There is a problem with the parameter description of sensor " + getSmlConfiguration().getId());
			return;
		}
		
		Text separatorParameter = (Text) getParameter(SEPARATOR_PARAMETER_NAME);
		separator = separatorParameter.getValue();
		
		Count columnParameter = (Count) getParameter(COLUMN_PARAMETER_NAME);
		column = columnParameter.getValue();
		
		ScalarComponent outputProperty = (ScalarComponent) getSmlConfiguration().getOutputList().getComponent("output1");
		dataType = outputProperty.getDataType();
	}

	public void destroy() {
		// nothing to do here
	}

	public IOPropertyList execute(IOPropertyList input) {
		DataComponent extractedInputFromList = input.getComponent(0);
		String string = ((Text) extractedInputFromList).getValue();			
		String[] splitted = string.split(separator);
		String columValue = splitted[column-1];
		
		DataComponent result = null;
		if (dataType.isIntegralType()) {
			Integer value = Integer.parseInt(columValue);
			Count specificResult = new CountImpl();
			specificResult.setValue(value);
			result = specificResult;
		} else if (dataType.isNumberType()) {
			Float value = Float.parseFloat(columValue);
			Quantity specificResult = new QuantityImpl();
			specificResult.setValue(value);
			result = specificResult;
		} else if (dataType.isTextType()) {
			Text specificResult = new TextImpl();
			specificResult.setValue(columValue);
			result = specificResult;
		}
		
		IOPropertyList list = new IOPropertyList();
		list.add(result);
		return list;
	}

	public boolean validateInputs() {
		boolean result = true;
		AbstractProcess description = getSmlConfiguration();
		
		// validate inputs
		IOPropertyList inputList = description.getInputList();

		if (inputList == null) {
			return false;
		}
		
		DataComponent component = inputList.getComponent("input1");
		result &= component != null;
		
		return result;
	}
	
	public boolean validateOutputs() {
		boolean result = true;
		AbstractProcess description = getSmlConfiguration();
		
		// validate inputs
		IOPropertyList outputList = description.getOutputList();

		if (outputList == null) {
			return false;
		}
		

		DataComponent component = outputList.getComponent("output1");
		result &= component != null;
		
		return result;
	}
	
	public boolean validateParameters() {
		boolean result = true;
		AbstractProcess description = getSmlConfiguration();
				
		// validate parameters
		IOPropertyList parameterList = description.getParameterList();
		
		if (parameterList == null) {
			return false;
		}
		
		AbstractSWEIdentifiable separatorParameter = parameterList.get(SEPARATOR_PARAMETER_NAME);
		result &= separatorParameter != null;
		String separatorParameterValue = ((Text) separatorParameter).getValue();
		result &= separatorParameterValue != null && separatorParameterValue != "";
		
		AbstractSWEIdentifiable columnParameter = parameterList.get(COLUMN_PARAMETER_NAME);
		result &= columnParameter != null;
		result &= ((Count) columnParameter).getValue() != 0;
		
		return result;
	}
	
	private AbstractSWEIdentifiable getParameter(String parameterName) {
		IOPropertyList parameterList = getSmlConfiguration().getParameterList();
		return parameterList.get(parameterName);
	}
	
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}


}
