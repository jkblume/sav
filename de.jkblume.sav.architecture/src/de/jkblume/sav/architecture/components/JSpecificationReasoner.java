
package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.components.AbstractSpecificationReasoner;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;

public class JSpecificationReasoner extends AbstractSpecificationReasoner {
	private static final String SAMPLING_RATE_PARAMETER_NAME = "samplingRate";
	private Thread pullThread;
	private boolean running;
	
	public JSpecificationReasoner(String name) {
		super(name);
	}

	public void setup() {
		if (!validateSmlConfiguration()) {
			throw new IllegalStateException("Invalid SML Configuration of sensoe " + getId());
		}

		pullThread = new PullThread(this);
	}

	public void destroy() {
		stop();
	}

	public void startImpl() {
		pullThread.start();
		running = true;
	}

	public void stopImpl() {
		pullThread.interrupt();
		running = false;
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
	public IOPropertyList retrieveValuesImpl() {
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
	public String getIdImpl() {
		return getSmlConfiguration().getId();
	}
	
	@Override
	public Integer getSamplingRateImpl() {
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		return ((Count) parameter).getValue();
	}

	@Override
	public void handleISensorAdded(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleISensorRemoved(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	public Boolean validateSmlConfigurationImpl() {
		boolean result = true;
		
		result &= getSmlConfiguration().getId() != null;
	
		
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		result &= parameter != null && parameter instanceof Count;
		
		return result;
	}

	@Override
	public Object executeImpl(Object value) {
		if (getIProcess() != null) {
			value = (IOPropertyList) getIProcess().execute(value);
		}
		
		return value;
	}
	
	@Override
	public IOPropertyList retrieveOutputStructureImpl() {
		if (getIProcess() != null) {
			return getIProcess().getSmlConfiguration().getOutputList();
		}
		return getSmlConfiguration().getOutputList();
	}

	@Override
	public Boolean initializeImpl() {
		return true;
	}

	@Override
	public void buildClassifierImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataComponent getQualityOfServiceImpl() {
		// TODO Auto-generated method stub
		return null;
	}

}
