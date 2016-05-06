
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

@Component(name = "TechnicalSensor", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "TechnicalSensor", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractTechnicalSensor extends AbstractComponent implements INotifyPropertyChanged, ISensor {

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

	public AbstractTechnicalSensor(String name) {
		super(name);
	}

	public AbstractPhysicalProcess getSmlConfiguration() {
		return (AbstractPhysicalProcess) getSharedMemory().getValue(AbstractTechnicalSensor.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
		AbstractPhysicalProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractTechnicalSensor.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	public Event getCurrentEvent() {
		return (Event) getSharedMemory().getValue(AbstractTechnicalSensor.class, "currentEvent");
	}

	public void setCurrentEvent(Event currentEvent) {
		Event oldValue = getCurrentEvent();
		getSharedMemory().setValue(AbstractTechnicalSensor.class, "currentEvent", currentEvent);
		notifyPropertyChanged(this, "currentEvent", oldValue, currentEvent);
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

	public Boolean isRunning() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("isRunning", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return isRunningImpl();
		else
			return iSensorRoles.get(0).isRunning();

	}

	public abstract void startImpl();
	public abstract void stopImpl();
	public abstract Boolean isRunningImpl();

}
