
package de.jkblume.sav.components.ports;

import org.vast.data.QuantityImpl;

import de.jkblume.sav.components.gen.ports.AbstractDifferenceProcessor;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;

public class DifferenceProcessor extends AbstractDifferenceProcessor {

	public DifferenceProcessor(String name) {
		super(name);
	}

	public void setup() {
		// nothing to do here
	}

	public void destroy() {
		// nothing to do here
	}
	
	public IOPropertyList execute(IOPropertyList input) {		
		DataRecord minuend = (DataRecord) input.getComponent(1);
		DataRecord subtrahend = (DataRecord) input.getComponent(2);
		
		Quantity xQuantityMinuend = (Quantity) minuend.getComponent("x");
		Quantity yQuantityMinuend = (Quantity) minuend.getComponent("y");
		Quantity zQuantityMinuend = (Quantity) minuend.getComponent("z");
		Quantity xQuantitySubtrahend = (Quantity) subtrahend.getComponent("x");
		Quantity yQuantitySubtrahend = (Quantity) subtrahend.getComponent("y");
		Quantity zQuantitySubtrahend = (Quantity) subtrahend.getComponent("z");
		
		Double xDiff = xQuantityMinuend.getValue() - xQuantitySubtrahend.getValue();
		Double yDiff = yQuantityMinuend.getValue() - yQuantitySubtrahend.getValue();
		Double zDiff = zQuantityMinuend.getValue() - zQuantitySubtrahend.getValue();
		Double value = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) + Math.pow(zDiff, 2));

		Quantity result = new QuantityImpl();
		result.setValue(value);
		
		IOPropertyList output = new IOPropertyList();
		output.add(result);
		return output;
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}


}
