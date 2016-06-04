
package de.jkblume.sav.components.ports;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.gen.ports.AbstractSimpleRuleReasoner;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;

public class SimpleRuleReasoner extends AbstractSimpleRuleReasoner {

	public SimpleRuleReasoner(String name) {
		super(name);
	}

	public void setup() {
		//TODO: IMPLEMENT
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public void buildClassifier() {
		base.buildClassifier();
	}
	public DataComponent getQualityOfService() {
		DataComponent result = base.getQualityOfService();
		return result;
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

	public Boolean initialize() {
		Boolean result = base.initialize();
		return result;
	}
	public Boolean validateSmlConfiguration() {
		Boolean result = base.validateSmlConfiguration();
		return result;
	}

	@Override
	public Object execute(Object input) {

		Quantity component = (Quantity) ((IOPropertyList) input).get("flex");
		
		Category category = new CategoryImpl();
		if (component == null) {
			return null;
		}
		
		if (component.getValue() < 780) {
			category.setValue("not buckled");
		} else {
			category.setValue("buckled");
		}
		
		
		IOPropertyList list = new IOPropertyList();
		list.add(category);
		
		return list;
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
