
package de.jkblume.sav.components.gen.ports;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.components.*;

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

public abstract class AbstractSerialTechnicalSensor implements IPort<ISensor>, INotifyPropertyChanged, ISensor {

	private String name;
	private boolean isActive = true;
	protected ISensor base;

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

	public AbstractSerialTechnicalSensor(String name) {
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
	public ISensor getBase() {
		return base;
	}

	@Override
	public void setBase(ISensor base) {
		this.base = base;
	}

	public <T> T as(Class<T> c) {
		return base.as(c);
	}

	public AbstractPhysicalProcess getSmlConfiguration() {
		return base.getSmlConfiguration();
	}

	public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
		base.setSmlConfiguration(smlConfiguration);
	}

	public Event getLastEvent() {
		return base.getLastEvent();
	}

	public void setLastEvent(Event lastEvent) {
		base.setLastEvent(lastEvent);
	}

}
