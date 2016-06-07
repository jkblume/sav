
package de.jkblume.sav.components.components;

import de.jkblume.sav.architecture.gen.components.AbstractLogicalRetrieveStrategy;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataComponent;

public class JLogicalRetrieveStrategy extends AbstractLogicalRetrieveStrategy {

	public JLogicalRetrieveStrategy(String name) {
		super(name);
	}

	public void setup() {
		// nothing to setup here
	}

	public void destroy() {
		// nothing to destroy here
	}

	public Object retrieveValueImpl() {
		IOPropertyList values = new IOPropertyList();
		
		// retrieve values from pull sensors
		for (ISensor pullSensor : getISensors()) {
			Event lastEvent = pullSensor.getLastEvent();
			if (lastEvent == null) {
				continue;
			}
			OgcPropertyList<DataComponent> partialResult = lastEvent.getPropertyList();
			values.addAll(partialResult);
		}
		
		return values;
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
