
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
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.IOPropertyList;

import java.util.*;
import org.smags.componentmodel.annotations.ParameterA;

public class JTechnicalSensor extends AbstractTechnicalSensor {

	private static final String SAMPLING_RATE_PARAMETER_NAME = "samplingRate";
	private Thread pollingThread;
	private boolean running;
	
	private String id;
	private int samplingRate;
	
	public JTechnicalSensor(String name) {
		super(name);
	}

	public void setup() {
		pollingThread = new PollThread(this);
	}

	public void destroy() {
		//TODO:Implement
	}

	@Override
	public Boolean initializeImpl() {
		return validateSmlConfiguration();
	}

	@Override
	public Boolean validateSmlConfigurationImpl() {
		boolean result = true;
		
		result &= getSmlConfiguration().getId() != null;
		
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		result &= parameter != null && parameter instanceof Count;

		return result;
	}

	@Override
	public String getIdImpl() {
		return getSmlConfiguration().getId();
	}
	
	@Override
	public Integer getSamplingRateImpl() {
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		return ((Count) parameter).getValue();
	}

	public IOPropertyList retrieveValuesImpl() {
		throw new IllegalStateException("This method need to be implemented by an implementation port!");
	}

	public void startImpl() {
		running = true;
		pollingThread.start();
	}

	public void stopImpl() {
		running = false;
		pollingThread.interrupt();
	}

	public Boolean isRunningImpl() {
		return running;
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
	public Object executeImpl(Object value) {
		if (getIProcess() != null) {
			value = (IOPropertyList) getIProcess().execute(value);
		}
		
		return value;
	}

}
