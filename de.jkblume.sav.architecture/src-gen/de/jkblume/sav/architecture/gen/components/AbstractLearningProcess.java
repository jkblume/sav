
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

@Component(name = "LearningProcess", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "LearningProcess", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractLearningProcess extends AbstractComponent
		implements
			INotifyPropertyChanged,
			ILearningProcess {

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

	private final List<ILearningProcess> iLearningProcessRoles = new ArrayList<ILearningProcess>();

	public AbstractLearningProcess(String name) {
		super(name);
	}

	public AbstractProcess getSmlConfiguration() {
		return (AbstractProcess) getSharedMemory().getValue(AbstractLearningProcess.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		AbstractProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractLearningProcess.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == ILearningProcess.class)
			return iLearningProcessRoles.size() > 0 ? (T) iLearningProcessRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof ILearningProcess) {
			iLearningProcessRoles.add(0, (ILearningProcess) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof ILearningProcess && iLearningProcessRoles.contains(port)) {
			iLearningProcessRoles.remove(port);
			return true;
		}

		return false;
	}

	public Boolean buildClassifier() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("buildClassifier", this);

		if (countInCallStack > 1 || iLearningProcessRoles.size() == 0)
			return buildClassifierImpl();
		else
			return iLearningProcessRoles.get(0).buildClassifier();

	}

	public void teachCurrentState(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("teachCurrentState", this);

		if (countInCallStack > 1 || iLearningProcessRoles.size() == 0)
			teachCurrentStateImpl(category);
		else
			iLearningProcessRoles.get(0).teachCurrentState(category);

	}

	public void startGesture(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("startGesture", this);

		if (countInCallStack > 1 || iLearningProcessRoles.size() == 0)
			startGestureImpl(category);
		else
			iLearningProcessRoles.get(0).startGesture(category);

	}

	public void stopGesture(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("stopGesture", this);

		if (countInCallStack > 1 || iLearningProcessRoles.size() == 0)
			stopGestureImpl(category);
		else
			iLearningProcessRoles.get(0).stopGesture(category);

	}

	public Object execute(Object input) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("execute", this);

		if (countInCallStack > 1 || iLearningProcessRoles.size() == 0)
			return executeImpl(input);
		else
			return iLearningProcessRoles.get(0).execute(input);

	}

	public abstract Boolean buildClassifierImpl();
	public abstract void teachCurrentStateImpl(Category category);
	public abstract void startGestureImpl(Category category);
	public abstract void stopGestureImpl(Category category);
	public abstract Object executeImpl(Object input);

}
