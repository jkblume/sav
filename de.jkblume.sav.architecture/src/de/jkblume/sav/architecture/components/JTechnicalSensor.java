
package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.components.AbstractTechnicalSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.IPullThread;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;

public class JTechnicalSensor extends AbstractTechnicalSensor {

	private static final String SAMPLING_RATE_PARAMETER_NAME = "samplingRate";
	
	public JTechnicalSensor(String name) {
		super(name);
	}

	public void setup() {
		if (!validateSmlConfiguration()) {
			throw new IllegalStateException("Invalid SML Configuration of sensoe " + getId());
		}
		getIPullThread().start();
	}

	public void destroy() {
		
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
		throw new UnsupportedOperationException("This method need to be implemented by an implementation port!");
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
	
	@Override
	public IOPropertyList retrieveOutputStructureImpl() {
		if (getIProcess() != null) {
			return getIProcess().getSmlConfiguration().getOutputList();
		}
		return getSmlConfiguration().getOutputList();
	}

	@Override
	public Boolean initializeImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleIPullThreadConnected(IPullThread item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIPullThreadDisconnected(IPullThread item) {
		// TODO Auto-generated method stub
		
	}


}
