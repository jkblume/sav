
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

@Component(name = "LogicalSensor", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "LogicalSensor", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractLogicalSensor extends AbstractComponent
		implements
			INotifyPropertyChanged,
			ILogicalSensor {

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

	@RequirementA
	private IPullThread iPullThread;

	public IPullThread getIPullThread() {
		return this.iPullThread;

	}

	public void setIPullThread(IPullThread iPullThread) {
		this.iPullThread = iPullThread;
		if (iPullThread != null)
			handleIPullThreadConnected(iPullThread);
		else
			handleIPullThreadDisconnected(iPullThread);
	}

	public abstract void handleIPullThreadConnected(IPullThread item);
	public abstract void handleIPullThreadDisconnected(IPullThread item);

	private final List<ILogicalSensor> iLogicalSensorRoles = new ArrayList<ILogicalSensor>();

	public AbstractLogicalSensor(String name) {
		super(name);
	}

	public Event getLastEvent() {
		return (Event) getSharedMemory().getValue(AbstractLogicalSensor.class, "lastEvent");
	}

	public void setLastEvent(Event lastEvent) {
		Event oldValue = getLastEvent();
		getSharedMemory().setValue(AbstractLogicalSensor.class, "lastEvent", lastEvent);
		notifyPropertyChanged(this, "lastEvent", oldValue, lastEvent);
	}

	public AbstractProcess getSmlConfiguration() {
		return (AbstractProcess) getSharedMemory().getValue(AbstractLogicalSensor.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		AbstractProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractLogicalSensor.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == ILogicalSensor.class)
			return iLogicalSensorRoles.size() > 0 ? (T) iLogicalSensorRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof ILogicalSensor) {
			iLogicalSensorRoles.add(0, (ILogicalSensor) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof ILogicalSensor && iLogicalSensorRoles.contains(port)) {
			iLogicalSensorRoles.remove(port);
			return true;
		}

		return false;
	}

	public Object retrieveValues() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("retrieveValues", this);

		if (countInCallStack > 1 || iLogicalSensorRoles.size() == 0)
			return retrieveValuesImpl();
		else
			return iLogicalSensorRoles.get(0).retrieveValues();

	}

	public String getId() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("getId", this);

		if (countInCallStack > 1 || iLogicalSensorRoles.size() == 0)
			return getIdImpl();
		else
			return iLogicalSensorRoles.get(0).getId();

	}

	public Integer getSamplingRate() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("getSamplingRate", this);

		if (countInCallStack > 1 || iLogicalSensorRoles.size() == 0)
			return getSamplingRateImpl();
		else
			return iLogicalSensorRoles.get(0).getSamplingRate();

	}

	public IOPropertyList retrieveOutputStructure() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("retrieveOutputStructure", this);

		if (countInCallStack > 1 || iLogicalSensorRoles.size() == 0)
			return retrieveOutputStructureImpl();
		else
			return iLogicalSensorRoles.get(0).retrieveOutputStructure();

	}

	public Boolean initialize() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("initialize", this);

		if (countInCallStack > 1 || iLogicalSensorRoles.size() == 0)
			return initializeImpl();
		else
			return iLogicalSensorRoles.get(0).initialize();

	}

	public Boolean validateSmlConfiguration() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("validateSmlConfiguration", this);

		if (countInCallStack > 1 || iLogicalSensorRoles.size() == 0)
			return validateSmlConfigurationImpl();
		else
			return iLogicalSensorRoles.get(0).validateSmlConfiguration();

	}

	public Object execute(Object value) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("execute", this);

		if (countInCallStack > 1 || iLogicalSensorRoles.size() == 0)
			return executeImpl(value);
		else
			return iLogicalSensorRoles.get(0).execute(value);

	}

	public abstract Object retrieveValuesImpl();
	public abstract String getIdImpl();
	public abstract Integer getSamplingRateImpl();
	public abstract IOPropertyList retrieveOutputStructureImpl();
	public abstract Boolean initializeImpl();
	public abstract Boolean validateSmlConfigurationImpl();
	public abstract Object executeImpl(Object value);

}
