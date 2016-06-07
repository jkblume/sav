
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

public abstract class AbstractJPullThread implements IPort<IPullThread>, INotifyPropertyChanged, IPullThread {

	private String name;
	private boolean isActive = true;
	protected IPullThread base;

	@RequirementA
	private ISensor iSensor;

	public ISensor getISensor() {
		return this.iSensor;

	}

	public void setISensor(ISensor iSensor) {
		this.iSensor = iSensor;
		if (iSensor != null)
			handleISensorConnected(iSensor);
		else
			handleISensorDisconnected(iSensor);
	}

	public abstract void handleISensorConnected(ISensor item);
	public abstract void handleISensorDisconnected(ISensor item);

	public AbstractJPullThread(String name) {
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
	public IPullThread getBase() {
		return base;
	}

	@Override
	public void setBase(IPullThread base) {
		this.base = base;
	}

	public <T> T as(Class<T> c) {
		return base.as(c);
	}

}
