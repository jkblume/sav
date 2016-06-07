
package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.components.AbstractLogicalSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.IPullThread;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;

public class JLogicalSensor extends AbstractLogicalSensor {
	
	public JLogicalSensor(String name) {
		super(name);
	}

	public void setup() {
		if (!validateSmlConfiguration()) {
			throw new IllegalStateException("Invalid SML Configuration of sensoe " + getId());
		}

		getIPullThread().start();
	}

	public void destroy() {
		//TODO: implement
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
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
	public void handleISensorAdded(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleISensorRemoved(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIPullThreadConnected(IPullThread item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIPullThreadDisconnected(IPullThread item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object retrieveValuesImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getSamplingRateImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOPropertyList retrieveOutputStructureImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean initializeImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean validateSmlConfigurationImpl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object executeImpl(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
