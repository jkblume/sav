
package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.porttypes.*;
import de.jkblume.sav.architecture.utils.MySMLUtils;

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

import java.util.*;
import org.smags.componentmodel.annotations.ParameterA;

public class JTechnicalPushSensor extends AbstractTechnicalPushSensor {
	
	private String id;
	private int samplingRate;
	
	public JTechnicalPushSensor(String name) {
		super(name);
	}

	public void setup() {
		//TODO:Implement
		
	}

	public void destroy() {
		//TODO:Implement
	}

	@Override
	public Boolean initializeImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdImpl() {
		return id;
	}

	public Boolean validateSmlConfigurationImpl() {
		throw new IllegalStateException("This class need to be implemented by a implementation port!");
	}

	public IOPropertyList retrieveValuesImpl() {
		throw new IllegalStateException("This class need to be implemented by a implementation port!");
	}

	public void startImpl() {
		throw new IllegalStateException("This class need to be implemented by a implementation port!");
	}

	public void stopImpl() {
		throw new IllegalStateException("This class need to be implemented by a implementation port!");
	}

	public Boolean isRunningImpl() {
		throw new IllegalStateException("This class need to be implemented by a implementation port!");
	}

	@Override
	public void handleIProcessConnected(IProcess connectedItem) {
		//TODO Handle
	}

	@Override
	public void handleIProcessDisconnected(IProcess disconnectedItem) {
		//TODO Handle
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

		if (propertyName.equals("lastEvent")) {
			//TODO:Implement
		}

	}

	@Override
	public void fireUnprocessedValuesImpl(IOPropertyList values) {
		// TODO Auto-generated method stub
		
	}

	
}
