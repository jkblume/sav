
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

@Component(name = "LearningReasoner", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "LearningReasoner", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractLearningReasoner extends AbstractComponent
		implements
			INotifyPropertyChanged,
			ILearningReasoner {

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

	private final List<ILearningReasoner> iLearningReasonerRoles = new ArrayList<ILearningReasoner>();

	public AbstractLearningReasoner(String name) {
		super(name);
	}

	public AbstractPhysicalProcess getSmlConfiguration() {
		return (AbstractPhysicalProcess) getSharedMemory().getValue(AbstractLearningReasoner.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
		AbstractPhysicalProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractLearningReasoner.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	public Event getCurrentEvent() {
		return (Event) getSharedMemory().getValue(AbstractLearningReasoner.class, "currentEvent");
	}

	public void setCurrentEvent(Event currentEvent) {
		Event oldValue = getCurrentEvent();
		getSharedMemory().setValue(AbstractLearningReasoner.class, "currentEvent", currentEvent);
		notifyPropertyChanged(this, "currentEvent", oldValue, currentEvent);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == ILearningReasoner.class)
			return iLearningReasonerRoles.size() > 0 ? (T) iLearningReasonerRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof ILearningReasoner) {
			iLearningReasonerRoles.add(0, (ILearningReasoner) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof ILearningReasoner && iLearningReasonerRoles.contains(port)) {
			iLearningReasonerRoles.remove(port);
			return true;
		}

		return false;
	}

	public Object translateEvent(Event event) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("translateEvent", this);

		if (countInCallStack > 1 || iLearningReasonerRoles.size() == 0)
			return translateEventImpl(event);
		else
			return iLearningReasonerRoles.get(0).translateEvent(event);

	}

	public void train() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("train", this);

		if (countInCallStack > 1 || iLearningReasonerRoles.size() == 0)
			trainImpl();
		else
			iLearningReasonerRoles.get(0).train();

	}

	public void teachCurrentState(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("teachCurrentState", this);

		if (countInCallStack > 1 || iLearningReasonerRoles.size() == 0)
			teachCurrentStateImpl(category);
		else
			iLearningReasonerRoles.get(0).teachCurrentState(category);

	}

	public void classify(Long intervalStart, Long intervalEnd) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("classify", this);

		if (countInCallStack > 1 || iLearningReasonerRoles.size() == 0)
			classifyImpl(intervalStart, intervalEnd);
		else
			iLearningReasonerRoles.get(0).classify(intervalStart, intervalEnd);

	}

	public void classifySnapshot(Long timestamp) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("classifySnapshot", this);

		if (countInCallStack > 1 || iLearningReasonerRoles.size() == 0)
			classifySnapshotImpl(timestamp);
		else
			iLearningReasonerRoles.get(0).classifySnapshot(timestamp);

	}

	public void start() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("start", this);

		if (countInCallStack > 1 || iLearningReasonerRoles.size() == 0)
			startImpl();
		else
			iLearningReasonerRoles.get(0).start();

	}

	public void stop() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("stop", this);

		if (countInCallStack > 1 || iLearningReasonerRoles.size() == 0)
			stopImpl();
		else
			iLearningReasonerRoles.get(0).stop();

	}

	public Boolean isRunning() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("isRunning", this);

		if (countInCallStack > 1 || iLearningReasonerRoles.size() == 0)
			return isRunningImpl();
		else
			return iLearningReasonerRoles.get(0).isRunning();

	}

	public abstract Object translateEventImpl(Event event);
	public abstract void trainImpl();
	public abstract void teachCurrentStateImpl(Category category);
	public abstract void classifyImpl(Long intervalStart, Long intervalEnd);
	public abstract void classifySnapshotImpl(Long timestamp);
	public abstract void startImpl();
	public abstract void stopImpl();
	public abstract Boolean isRunningImpl();

}
