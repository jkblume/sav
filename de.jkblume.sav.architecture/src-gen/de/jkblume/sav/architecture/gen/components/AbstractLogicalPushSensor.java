
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

@Component(name = "LogicalPushSensor", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "LogicalPushSensor", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractLogicalPushSensor extends AbstractComponent
		implements
			INotifyPropertyChanged,
			IMyPushSensorSubject,
			IMyPushSensorObserver {

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
	private List<IMyPushSensorSubject> iMyPushSensorSubjects = new ArrayList<IMyPushSensorSubject>();

	public List<IMyPushSensorSubject> getIMyPushSensors() {
		return this.iMyPushSensorSubjects;
	}

	public void addIMyPushSensorSubject(IMyPushSensorSubject item) {
		this.iMyPushSensorSubjects.add(item);
		handleIMyPushSensorAdded(item);
	}

	public abstract void handleIMyPushSensorAdded(IMyPushSensorSubject item);
	public abstract void handleIMyPushSensorRemoved(IMyPushSensorSubject item);

	public void removeIMyPushSensorSubject(IMyPushSensorSubject item) {
		this.iMyPushSensorSubjects.remove(item);
		handleIMyPushSensorRemoved(item);
	}

	@EventListenersA
	private final List<IMyPushSensorObserver> iMyPushSensorObserver = new ArrayList<IMyPushSensorObserver>();

	@Override
	public void addIMyPushSensorObserver(IMyPushSensorObserver observer) {
		if (!iMyPushSensorObserver.contains(observer))
			iMyPushSensorObserver.add(observer);
	}

	@Override
	public void removeIMyPushSensorObserver(IMyPushSensorObserver observer) {
		iMyPushSensorObserver.remove(observer);
	}

	protected void fireIMyPushSensor(Event arg) {
		for (IMyPushSensorObserver observer : iMyPushSensorObserver)
			observer.notifyEventChanged(this, arg);
	}

	private final List<IMyPushSensorObserver> iMyPushSensorObserverRoles = new ArrayList<IMyPushSensorObserver>();

	public AbstractLogicalPushSensor(String name) {
		super(name);
	}

	public AbstractPhysicalProcess getSmlConfiguration() {
		return (AbstractPhysicalProcess) getSharedMemory().getValue(AbstractLogicalPushSensor.class,
				"smlConfiguration");
	}

	public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
		AbstractPhysicalProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractLogicalPushSensor.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	public Event getLastEvent() {
		return (Event) getSharedMemory().getValue(AbstractLogicalPushSensor.class, "lastEvent");
	}

	public void setLastEvent(Event lastEvent) {
		Event oldValue = getLastEvent();
		getSharedMemory().setValue(AbstractLogicalPushSensor.class, "lastEvent", lastEvent);
		notifyPropertyChanged(this, "lastEvent", oldValue, lastEvent);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == IMyPushSensorObserver.class) {
			return iMyPushSensorObserverRoles.size() > 0 ? (T) iMyPushSensorObserverRoles.get(0) : (T) this;
		}

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof IMyPushSensorObserver) {
			iMyPushSensorObserverRoles.add(0, (IMyPushSensorObserver) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof IMyPushSensorObserver && iMyPushSensorObserverRoles.contains(port)) {
			iMyPushSensorObserverRoles.remove((IMyPushSensorObserver) port);
			return true;
		}

		return false;
	}

	public void fireUnprocessedValues(IOPropertyList values) {

		fireUnprocessedValuesImpl(values);

	}

	public String getId() {

		return getIdImpl();

	}

	public Boolean initialize() {

		return initializeImpl();

	}

	public IOPropertyList retrieveValues() {

		return retrieveValuesImpl();

	}

	public void start() {

		startImpl();

	}

	public void stop() {

		stopImpl();

	}

	public Boolean isRunning() {

		return isRunningImpl();

	}

	public abstract void fireUnprocessedValuesImpl(IOPropertyList values);
	public abstract String getIdImpl();
	public abstract Boolean initializeImpl();
	public abstract IOPropertyList retrieveValuesImpl();
	public abstract void startImpl();
	public abstract void stopImpl();
	public abstract Boolean isRunningImpl();

	public abstract void notifyEventChangedImpl(Object sender, Event argument);

	@Override
	public final void notifyEventChanged(Object sender, Event argument) {
		int countInCallStack = ReflectionHelper.countContainedInCallStack("notifyEventChanged", this);

		if (countInCallStack > 1 || iMyPushSensorObserverRoles.size() == 0)
			notifyEventChangedImpl(sender, argument);
		else
			iMyPushSensorObserverRoles.get(0).notifyEventChanged(sender, argument);
	}

}
