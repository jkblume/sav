
package de.jkblume.sav.components.ports;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vast.data.CountImpl;
import org.vast.data.QuantityImpl;
import org.vast.data.TextImpl;

import de.jkblume.sav.components.gen.ports.AbstractReqexProcessor;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataType;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.ScalarComponent;
import net.opengis.swe.v20.Text;

public class RegexProcessor extends AbstractReqexProcessor {

	private static final String REGEX_PARAMETER_NAME = "regex";
	private static final String GROUP_PARAMETER_NAME = "group";
	
	private String regex;
	private Pattern pattern;
	private int group;
	private DataType dataType;
	
	public RegexProcessor(String name) {
		super(name);
	}

	public void setup() {
		Text separatorParameter = (Text) getParameter(REGEX_PARAMETER_NAME);
		regex = separatorParameter.getValue();
		pattern = Pattern.compile(regex);
		
		Count columnParameter = (Count) getParameter(GROUP_PARAMETER_NAME);
		group = columnParameter.getValue();
		
		ScalarComponent outputProperty = (ScalarComponent) getSmlConfiguration().getOutputList().getComponent("output1");
		dataType = outputProperty.getDataType();
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public Object execute(Object input) {
		Text castedInput = getCastedInput(input);
		String inputString = castedInput.getValue();
		
		Matcher matcher = pattern.matcher(inputString);
		
		String outputString = null;
		if (matcher.find()) {
			outputString = matcher.group(group);
		} else {
			return null;
		}
		
		
		DataComponent result = null;
		if (dataType.isIntegralType()) {
			Integer value = Integer.parseInt(outputString);
			Count specificResult = new CountImpl();
			specificResult.setValue(value);
			result = specificResult;
		} else if (dataType.isNumberType()) {
			Float value = Float.parseFloat(outputString);
			Quantity specificResult = new QuantityImpl();
			specificResult.setValue(value);
			result = specificResult;
		} else if (dataType.isTextType()) {
			Text specificResult = new TextImpl();
			specificResult.setValue(outputString);
			result = specificResult;
		}
		
		IOPropertyList list = new IOPropertyList();
		list.add(result);
		return list;
	}
	
	private Text getCastedInput(Object input) {
		if (input instanceof IOPropertyList) {
			IOPropertyList list = (IOPropertyList) input;
			if (list.get(0) instanceof Text) {
				return (Text) list.get(0);
			}
		}
		return null;
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}
	
	private AbstractSWEIdentifiable getParameter(String parameterName) {
		IOPropertyList parameterList = getSmlConfiguration().getParameterList();
		return parameterList.get(parameterName);
	}

	@Override
	public Boolean validateSmlConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
