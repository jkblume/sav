
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

@Component(name = "Visualizer", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "Visualizer", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractVisualizer extends AbstractComponent
		implements
			INotifyPropertyChanged,
			IVisualisationStrategy {

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

	private final List<IVisualisationStrategy> iVisualisationStrategyRoles = new ArrayList<IVisualisationStrategy>();

	public AbstractVisualizer(String name) {
		super(name);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == IVisualisationStrategy.class)
			return iVisualisationStrategyRoles.size() > 0 ? (T) iVisualisationStrategyRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof IVisualisationStrategy) {
			iVisualisationStrategyRoles.add(0, (IVisualisationStrategy) port);
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

}
