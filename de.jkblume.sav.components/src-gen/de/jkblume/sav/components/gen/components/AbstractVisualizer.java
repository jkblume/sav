
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

@Component(name = "Visualizer", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "Visualizer", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractVisualizer extends AbstractComponent
		implements
			INotifyPropertyChanged,
			IVisualisationStrategy,
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

	private final List<IVisualisationStrategy> iVisualisationStrategyRoles = new ArrayList<IVisualisationStrategy>();

	private final List<IMyPushSensorObserver> iMyPushSensorObserverRoles = new ArrayList<IMyPushSensorObserver>();

	public AbstractVisualizer(String name) {
		super(name);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == IVisualisationStrategy.class)
			return iVisualisationStrategyRoles.size() > 0 ? (T) iVisualisationStrategyRoles.get(0) : (T) this;

		if (type == IMyPushSensorObserver.class) {
			return iMyPushSensorObserverRoles.size() > 0 ? (T) iMyPushSensorObserverRoles.get(0) : (T) this;
		}

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof IVisualisationStrategy) {
			iVisualisationStrategyRoles.add(0, (IVisualisationStrategy) port);
			return true;
		}

		if (port instanceof IMyPushSensorObserver) {
			iMyPushSensorObserverRoles.add(0, (IMyPushSensorObserver) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof IVisualisationStrategy && iVisualisationStrategyRoles.contains(port)) {
			iVisualisationStrategyRoles.remove(port);
			return true;
		}

		if (port instanceof IMyPushSensorObserver && iMyPushSensorObserverRoles.contains(port)) {
			iMyPushSensorObserverRoles.remove((IMyPushSensorObserver) port);
			return true;
		}

		return false;
	}

	public void visualizeEvent(ISensor source, Event event) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("visualizeEvent", this);

		if (countInCallStack > 1 || iVisualisationStrategyRoles.size() == 0)
			visualizeEventImpl(source, event);
		else
			iVisualisationStrategyRoles.get(0).visualizeEvent(source, event);

	}

	public void addSensor(ISensor sensor) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("addSensor", this);

		if (countInCallStack > 1 || iVisualisationStrategyRoles.size() == 0)
			addSensorImpl(sensor);
		else
			iVisualisationStrategyRoles.get(0).addSensor(sensor);

	}

	public void removeSensor(ISensor sensor) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("removeSensor", this);

		if (countInCallStack > 1 || iVisualisationStrategyRoles.size() == 0)
			removeSensorImpl(sensor);
		else
			iVisualisationStrategyRoles.get(0).removeSensor(sensor);

	}

	public abstract void visualizeEventImpl(ISensor source, Event event);
	public abstract void addSensorImpl(ISensor sensor);
	public abstract void removeSensorImpl(ISensor sensor);

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
