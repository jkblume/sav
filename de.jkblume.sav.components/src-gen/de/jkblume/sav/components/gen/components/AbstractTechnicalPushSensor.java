
package de.jkblume.sav.components.gen.components;

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
import org.smags.componentmodel.AbstractComponent;
import org.smags.componentmodel.IPort;
import org.smags.componentmodel.annotations.RequirementA;
import org.smags.componentmodel.annotations.EventListenersA;
import org.smags.componentmodel.annotations.ParameterA;
import org.smags.reflection.ReflectionHelper;
import org.smags.componentmodel.parameter.INotifyPropertyChanged;
import org.smags.componentmodel.annotations.Component;

@Component(name = "TechnicalPushSensor", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "TechnicalPushSensor", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractTechnicalPushSensor extends AbstractComponent
		implements
			INotifyPropertyChanged,
			IMyPushSensorSubject {

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

	public AbstractTechnicalPushSensor(String name) {
		super(name);
	}

	public AbstractPhysicalProcess getSmlConfiguration() {
		return (AbstractPhysicalProcess) getSharedMemory().getValue(AbstractTechnicalPushSensor.class,
				"smlConfiguration");
	}

	public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
		AbstractPhysicalProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractTechnicalPushSensor.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	public Event getLastEvent() {
		return (Event) getSharedMemory().getValue(AbstractTechnicalPushSensor.class, "lastEvent");
	}

	public void setLastEvent(Event lastEvent) {
		Event oldValue = getLastEvent();
		getSharedMemory().setValue(AbstractTechnicalPushSensor.class, "lastEvent", lastEvent);
		notifyPropertyChanged(this, "lastEvent", oldValue, lastEvent);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		return false;
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

	public abstract String getIdImpl();
	public abstract Boolean initializeImpl();
	public abstract IOPropertyList retrieveValuesImpl();
	public abstract void startImpl();
	public abstract void stopImpl();
	public abstract Boolean isRunningImpl();

}
