
package de.jkblume.sav.architecture.gen.components;

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
import org.smags.componentmodel.AbstractComponent;
import org.smags.componentmodel.IPort;
import org.smags.componentmodel.annotations.RequirementA;
import org.smags.componentmodel.annotations.EventListenersA;
import org.smags.componentmodel.annotations.ParameterA;
import org.smags.reflection.ReflectionHelper;
import org.smags.componentmodel.parameter.INotifyPropertyChanged;
import org.smags.componentmodel.annotations.Component;

@Component(name = "Sensor", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "Sensor", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractSensor extends AbstractComponent implements INotifyPropertyChanged, ISensor {

	@RequirementA
	private IRetrieveStrategy iRetrieveStrategy;

	public IRetrieveStrategy getIRetrieveStrategy() {
		return this.iRetrieveStrategy;

	}

	public void setIRetrieveStrategy(IRetrieveStrategy iRetrieveStrategy) {
		this.iRetrieveStrategy = iRetrieveStrategy;
		if (iRetrieveStrategy != null)
			handleIRetrieveStrategyConnected(iRetrieveStrategy);
		else
			handleIRetrieveStrategyDisconnected(iRetrieveStrategy);
	}

	public abstract void handleIRetrieveStrategyConnected(IRetrieveStrategy item);
	public abstract void handleIRetrieveStrategyDisconnected(IRetrieveStrategy item);

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

	private final List<ISensor> iSensorRoles = new ArrayList<ISensor>();

	public AbstractSensor(String name) {
		super(name);
	}

	public Boolean getRunning() {
		return (Boolean) getSharedMemory().getValue(AbstractSensor.class, "running");
	}

	public void setRunning(Boolean running) {
		Boolean oldValue = getRunning();
		getSharedMemory().setValue(AbstractSensor.class, "running", running);
		notifyPropertyChanged(this, "running", oldValue, running);
	}

	public Event getLastEvent() {
		return (Event) getSharedMemory().getValue(AbstractSensor.class, "lastEvent");
	}

	public void setLastEvent(Event lastEvent) {
		Event oldValue = getLastEvent();
		getSharedMemory().setValue(AbstractSensor.class, "lastEvent", lastEvent);
		notifyPropertyChanged(this, "lastEvent", oldValue, lastEvent);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == ISensor.class)
			return iSensorRoles.size() > 0 ? (T) iSensorRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof ISensor) {
			iSensorRoles.add(0, (ISensor) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof ISensor && iSensorRoles.contains(port)) {
			iSensorRoles.remove(port);
			return true;
		}

		return false;
	}

	public void start() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("start", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			startImpl();
		else
			iSensorRoles.get(0).start();

	}

	public void stop() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("stop", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			stopImpl();
		else
			iSensorRoles.get(0).stop();

	}

	public String getId() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("getId", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return getIdImpl();
		else
			return iSensorRoles.get(0).getId();

	}

	public Integer getSamplingRate() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("getSamplingRate", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return getSamplingRateImpl();
		else
			return iSensorRoles.get(0).getSamplingRate();

	}

	public IOPropertyList retrieveOutputStructure() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("retrieveOutputStructure", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return retrieveOutputStructureImpl();
		else
			return iSensorRoles.get(0).retrieveOutputStructure();

	}

	public abstract void startImpl();
	public abstract void stopImpl();
	public abstract String getIdImpl();
	public abstract Integer getSamplingRateImpl();
	public abstract IOPropertyList retrieveOutputStructureImpl();

}
