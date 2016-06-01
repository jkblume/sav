
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
import net.opengis.swe.v20.DataComponent;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.IOPropertyList;

import de.jkblume.sav.architecture.gen.porttypes.*;

import de.jkblume.sav.architecture.gen.ports.*;
import org.smags.componentmodel.IPort;
import java.util.*;

public class JReasoner extends AbstractJReasoner {

	public JReasoner(String name) {
		super(name);
	}

	public void setup() {
		//TODO: IMPLEMENT
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public void start() {
		base.start();
	}
	public void stop() {
		base.stop();
	}
	public Boolean isRunning() {
		Boolean result = base.isRunning();
		return result;
	}
	public IOPropertyList retrieveValues() {
		IOPropertyList result = base.retrieveValues();
		return result;
	}
	public String getId() {
		String result = base.getId();
		return result;
	}
	public Integer getSamplingRate() {
		Integer result = base.getSamplingRate();
		return result;
	}
	public IOPropertyList retrieveOutputStructure() {
		IOPropertyList result = base.retrieveOutputStructure();
		return result;
	}

	public Boolean validateSmlConfiguration() {
		Boolean result = base.validateSmlConfiguration();
		return result;
	}
	public Object execute(Object value) {
		Object result = base.execute(value);
		return result;
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

	@Override
	public Boolean initialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
