
package de.jkblume.sav.components.components;

import java.util.List;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.components.PullThread;
import de.jkblume.sav.architecture.gen.components.AbstractSpecificationReasonerProcess;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;

public class SimpleRuleReasoner extends AbstractSpecificationReasonerProcess {
	private static final String SAMPLING_RATE_PARAMETER_NAME = "samplingRate";

	private Thread pullThread;
	private Boolean running;
	
	public SimpleRuleReasoner(String name) {
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


	public void buildClassifierImpl() {
		for (ISensor iSensor : getISensors()) {
			System.out.println(iSensor.getId());
		}
	}

	public Category classifyImpl(IOPropertyList input) {
		Quantity component = (Quantity) input.get("flex");
		
		Category category = new CategoryImpl();
		if (component == null) {
			return null;
		}
		
		if (component.getValue() < 780) {
			category.setValue("not buckled");
		} else {
			category.setValue("buckled");
		}
		
		return category;
	}

	public DataComponent getQualityOfServiceImpl() {
		//TODO: IMPLEMENT
		return null;
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
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("injectorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("extractorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("smlConfiguration")) {
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
	public Object executeImpl(Object value) {
		Category classify = classify((IOPropertyList) value);
		
		IOPropertyList list = new IOPropertyList();
		list.add(classify);
		
		return list;
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
