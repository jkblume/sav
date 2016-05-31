
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
public abstract class AbstractLearningReasoner extends AbstractLogicalSensor
		implements
			INotifyPropertyChanged,
			ILearningReasoningStrategy {

	@RequirementA
	private List<IProcess> iProcesss = new ArrayList<IProcess>();

	public List<IProcess> getIProcesss() {
		return this.iProcesss;
	}

	public void addIProcess(IProcess item) {
		this.iProcesss.add(item);
		handleIProcessAdded(item);
	}

	public void removeIProcess(IProcess item) {
		this.iProcesss.remove(item);
		handleIProcessRemoved(item);
	}

	public abstract void handleIProcessAdded(IProcess item);
	public abstract void handleIProcessRemoved(IProcess item);

	private final List<ILearningReasoningStrategy> iLearningReasoningStrategyRoles = new ArrayList<ILearningReasoningStrategy>();

	private final List<ISensor> iSensorRoles = new ArrayList<ISensor>();

	public AbstractLearningReasoner(String name) {
		super(name);
	}

	public Boolean getInjectorProvided() {
		return (Boolean) getSharedMemory().getValue(AbstractLearningReasoner.class, "injectorProvided");
	}

	public void setInjectorProvided(Boolean injectorProvided) {
		Boolean oldValue = getInjectorProvided();
		getSharedMemory().setValue(AbstractLearningReasoner.class, "injectorProvided", injectorProvided);
		notifyPropertyChanged(this, "injectorProvided", oldValue, injectorProvided);
	}

	public Boolean getExtractorProvided() {
		return (Boolean) getSharedMemory().getValue(AbstractLearningReasoner.class, "extractorProvided");
	}

	public void setExtractorProvided(Boolean extractorProvided) {
		Boolean oldValue = getExtractorProvided();
		getSharedMemory().setValue(AbstractLearningReasoner.class, "extractorProvided", extractorProvided);
		notifyPropertyChanged(this, "extractorProvided", oldValue, extractorProvided);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == ILearningReasoningStrategy.class)
			return iLearningReasoningStrategyRoles.size() > 0 ? (T) iLearningReasoningStrategyRoles.get(0) : (T) this;

		if (type == ISensor.class)
			return iSensorRoles.size() > 0 ? (T) iSensorRoles.get(0) : (T) this;

		return super.innerGetPort(type);
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof ILearningReasoningStrategy) {
			iLearningReasoningStrategyRoles.add(0, (ILearningReasoningStrategy) port);
			return true;
		}

		if (port instanceof ISensor) {
			iSensorRoles.add(0, (ISensor) port);
			return true;
		}

		return super.innerBindPort(port);
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof ILearningReasoningStrategy && iLearningReasoningStrategyRoles.contains(port)) {
			iLearningReasoningStrategyRoles.remove(port);
			return true;
		}

		if (port instanceof ISensor && iSensorRoles.contains(port)) {
			iSensorRoles.remove(port);
			return true;
		}

		return super.innerUnbindPort(port);
	}

	public void startGesture(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("startGesture", this);

		if (countInCallStack > 1 || iLearningReasoningStrategyRoles.size() == 0)
			startGestureImpl(category);
		else
			iLearningReasoningStrategyRoles.get(0).startGesture(category);

	}

	public void stopGesture(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("stopGesture", this);

		if (countInCallStack > 1 || iLearningReasoningStrategyRoles.size() == 0)
			stopGestureImpl(category);
		else
			iLearningReasoningStrategyRoles.get(0).stopGesture(category);

	}

	public void teachCurrentState(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("teachCurrentState", this);

		if (countInCallStack > 1 || iLearningReasoningStrategyRoles.size() == 0)
			teachCurrentStateImpl(category);
		else
			iLearningReasoningStrategyRoles.get(0).teachCurrentState(category);

	}

	public void train() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("train", this);

		if (countInCallStack > 1 || iLearningReasoningStrategyRoles.size() == 0)
			trainImpl();
		else
			iLearningReasoningStrategyRoles.get(0).train();

	}

	public void buildClassifier() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("buildClassifier", this);

		if (countInCallStack > 1 || iLearningReasoningStrategyRoles.size() == 0)
			buildClassifierImpl();
		else
			iLearningReasoningStrategyRoles.get(0).buildClassifier();

	}

	public DataComponent getQualityOfService() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("getQualityOfService", this);

		if (countInCallStack > 1 || iLearningReasoningStrategyRoles.size() == 0)
			return getQualityOfServiceImpl();
		else
			return iLearningReasoningStrategyRoles.get(0).getQualityOfService();

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

	public Boolean initialize() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("initialize", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return initializeImpl();
		else
			return iSensorRoles.get(0).initialize();

	}

	public Boolean isRunning() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("isRunning", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return isRunningImpl();
		else
			return iSensorRoles.get(0).isRunning();

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

	public IOPropertyList retrieveValues() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("retrieveValues", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return retrieveValuesImpl();
		else
			return iSensorRoles.get(0).retrieveValues();

	}

	public Boolean validateSmlConfiguration() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("validateSmlConfiguration", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return validateSmlConfigurationImpl();
		else
			return iSensorRoles.get(0).validateSmlConfiguration();

	}

	public Object execute(Object value) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("execute", this);

		if (countInCallStack > 1 || iSensorRoles.size() == 0)
			return executeImpl(value);
		else
			return iSensorRoles.get(0).execute(value);

	}

	public abstract void startGestureImpl(Category category);
	public abstract void stopGestureImpl(Category category);
	public abstract void teachCurrentStateImpl(Category category);
	public abstract void trainImpl();
	public abstract void buildClassifierImpl();
	public abstract DataComponent getQualityOfServiceImpl();
	public abstract void startImpl();
	public abstract void stopImpl();
	public abstract Boolean initializeImpl();
	public abstract Boolean isRunningImpl();
	public abstract String getIdImpl();
	public abstract Integer getSamplingRateImpl();
	public abstract IOPropertyList retrieveValuesImpl();
	public abstract Boolean validateSmlConfigurationImpl();
	public abstract Object executeImpl(Object value);

}
