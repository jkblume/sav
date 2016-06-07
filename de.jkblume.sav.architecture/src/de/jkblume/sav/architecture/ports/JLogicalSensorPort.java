
package de.jkblume.sav.architecture.ports;

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
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.IOPropertyList;

import de.jkblume.sav.architecture.gen.porttypes.*;

import de.jkblume.sav.architecture.gen.ports.*;
import org.smags.componentmodel.IPort;
import java.util.*;

public class JLogicalSensorPort extends AbstractJLogicalSensorPort {

	private static final String SAMPLING_RATE_PARAMETER_NAME = "samplingRate";

	public JLogicalSensorPort(String name) {
		super(name);
	}

	public void setup() {
		//TODO: IMPLEMENT
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	@Override
	public IOPropertyList retrieveValues() {
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
	public String getId() {
		return getSmlConfiguration().getId();
	}
	
	@Override
	public Integer getSamplingRate() {
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		return ((Count) parameter).getValue();
	}

	@Override
	public Boolean validateSmlConfiguration() {
		boolean result = true;
		
		result &= getSmlConfiguration().getId() != null;
	
		
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		result &= parameter != null && parameter instanceof Count;
		
		return result;
	}

	@Override
	public Object execute(Object value) {
		if (getIProcess() != null) {
			value = (IOPropertyList) getIProcess().execute(value);
		}
		
		return value;
	}
	
	@Override
	public IOPropertyList retrieveOutputStructure() {
		if (getIProcess() != null) {
			return getIProcess().getSmlConfiguration().getOutputList();
		}
		return getSmlConfiguration().getOutputList();
	}

	@Override
	public Boolean initialize() {
		return true;
	}


	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

	@Override
	public void handleISensorAdded(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleISensorRemoved(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIProcessConnected(IProcess item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIProcessDisconnected(IProcess item) {
		// TODO Auto-generated method stub
		
	}

}
