
package de.jkblume.sav.architecture.gen.ports;

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
import java.util.*;
import org.smags.componentmodel.IPort;
import java.util.*;
import org.smags.componentmodel.annotations.ParameterA;
import org.smags.componentmodel.parameter.INotifyPropertyChanged;
import org.smags.componentmodel.annotations.RequirementA;

public abstract class AbstractJLogicalSensorPort
		implements
			IPort<ILogicalSensor>,
			INotifyPropertyChanged,
			ILogicalSensor {

	private String name;
	private boolean isActive = true;
	protected ILogicalSensor base;

	@RequirementA
	private List<ISensor> iSensors = new ArrayList<ISensor>();

	public List<ISensor> getISensors() {
		return this.iSensors;
	}

	public void addISensor(ISensor item) {
		this.iSensors.add(item);
		handleISensorAdded(item);
	}

	public void removeISensor(ISensor item) {
		this.iSensors.remove(item);
		handleISensorRemoved(item);
	}

	public abstract void handleISensorAdded(ISensor item);
	public abstract void handleISensorRemoved(ISensor item);

	@RequirementA
	private IProcess iProcess;

	public IProcess getIProcess() {
		return this.iProcess;

	}

	public void setIProcess(IProcess iProcess) {
		this.iProcess = iProcess;
		if (iProcess != null)
			handleIProcessConnected(iProcess);
		else
			handleIProcessDisconnected(iProcess);
	}

	public abstract void handleIProcessConnected(IProcess item);
	public abstract void handleIProcessDisconnected(IProcess item);

	public AbstractJLogicalSensorPort(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void active() {
		isActive = true;
	}

	@Override
	public void deactivate() {
		isActive = false;
	}

	@Override
	public ILogicalSensor getBase() {
		return base;
	}

	@Override
	public void setBase(ILogicalSensor base) {
		this.base = base;
	}

	public <T> T as(Class<T> c) {
		return base.as(c);
	}

	public Event getLastEvent() {
		return base.getLastEvent();
	}

	public void setLastEvent(Event lastEvent) {
		base.setLastEvent(lastEvent);
	}

	public AbstractProcess getSmlConfiguration() {
		return base.getSmlConfiguration();
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		base.setSmlConfiguration(smlConfiguration);
	}

}
