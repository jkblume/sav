
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

@Component(name = "LearningReasonerProcess", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "LearningReasonerProcess", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractLearningReasonerProcess extends AbstractComponent
		implements
			INotifyPropertyChanged,
			ILearningReasonerProcess {

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

	private final List<ILearningReasonerProcess> iLearningReasonerProcessRoles = new ArrayList<ILearningReasonerProcess>();

	public AbstractLearningReasonerProcess(String name) {
		super(name);
	}

	public Boolean getInjectorProvided() {
		return (Boolean) getSharedMemory().getValue(AbstractLearningReasonerProcess.class, "injectorProvided");
	}

	public void setInjectorProvided(Boolean injectorProvided) {
		Boolean oldValue = getInjectorProvided();
		getSharedMemory().setValue(AbstractLearningReasonerProcess.class, "injectorProvided", injectorProvided);
		notifyPropertyChanged(this, "injectorProvided", oldValue, injectorProvided);
	}

	public Boolean getExtractorProvided() {
		return (Boolean) getSharedMemory().getValue(AbstractLearningReasonerProcess.class, "extractorProvided");
	}

	public void setExtractorProvided(Boolean extractorProvided) {
		Boolean oldValue = getExtractorProvided();
		getSharedMemory().setValue(AbstractLearningReasonerProcess.class, "extractorProvided", extractorProvided);
		notifyPropertyChanged(this, "extractorProvided", oldValue, extractorProvided);
	}

	public Event getLastEvent() {
		return (Event) getSharedMemory().getValue(AbstractLearningReasonerProcess.class, "lastEvent");
	}

	public void setLastEvent(Event lastEvent) {
		Event oldValue = getLastEvent();
		getSharedMemory().setValue(AbstractLearningReasonerProcess.class, "lastEvent", lastEvent);
		notifyPropertyChanged(this, "lastEvent", oldValue, lastEvent);
	}

	public AbstractProcess getSmlConfiguration() {
		return (AbstractProcess) getSharedMemory().getValue(AbstractLearningReasonerProcess.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		AbstractProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractLearningReasonerProcess.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == ILearningReasonerProcess.class)
			return iLearningReasonerProcessRoles.size() > 0 ? (T) iLearningReasonerProcessRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof ILearningReasonerProcess) {
			iLearningReasonerProcessRoles.add(0, (ILearningReasonerProcess) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof ILearningReasonerProcess && iLearningReasonerProcessRoles.contains(port)) {
			iLearningReasonerProcessRoles.remove(port);
			return true;
		}

		return false;
	}

	public void startGesture(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("startGesture", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			startGestureImpl(category);
		else
			iLearningReasonerProcessRoles.get(0).startGesture(category);

	}

	public void stopGesture(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("stopGesture", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			stopGestureImpl(category);
		else
			iLearningReasonerProcessRoles.get(0).stopGesture(category);

	}

	public void teachCurrentState(Category category) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("teachCurrentState", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			teachCurrentStateImpl(category);
		else
			iLearningReasonerProcessRoles.get(0).teachCurrentState(category);

	}

	public void updateClassifier(Object trainingDate) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("updateClassifier", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			updateClassifierImpl(trainingDate);
		else
			iLearningReasonerProcessRoles.get(0).updateClassifier(trainingDate);

	}

	public void buildClassifier() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("buildClassifier", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			buildClassifierImpl();
		else
			iLearningReasonerProcessRoles.get(0).buildClassifier();

	}

	public Category classify(IOPropertyList input) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("classify", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return classifyImpl(input);
		else
			return iLearningReasonerProcessRoles.get(0).classify(input);

	}

	public DataComponent getQualityOfService() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("getQualityOfService", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return getQualityOfServiceImpl();
		else
			return iLearningReasonerProcessRoles.get(0).getQualityOfService();

	}

	public void start() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("start", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			startImpl();
		else
			iLearningReasonerProcessRoles.get(0).start();

	}

	public void stop() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("stop", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			stopImpl();
		else
			iLearningReasonerProcessRoles.get(0).stop();

	}

	public Boolean isRunning() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("isRunning", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return isRunningImpl();
		else
			return iLearningReasonerProcessRoles.get(0).isRunning();

	}

	public IOPropertyList retrieveValues() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("retrieveValues", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return retrieveValuesImpl();
		else
			return iLearningReasonerProcessRoles.get(0).retrieveValues();

	}

	public String getId() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("getId", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return getIdImpl();
		else
			return iLearningReasonerProcessRoles.get(0).getId();

	}

	public Integer getSamplingRate() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("getSamplingRate", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return getSamplingRateImpl();
		else
			return iLearningReasonerProcessRoles.get(0).getSamplingRate();

	}

	public IOPropertyList retrieveOutputStructure() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("retrieveOutputStructure", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return retrieveOutputStructureImpl();
		else
			return iLearningReasonerProcessRoles.get(0).retrieveOutputStructure();

	}

	public Boolean initialize() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("initialize", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return initializeImpl();
		else
			return iLearningReasonerProcessRoles.get(0).initialize();

	}

	public Boolean validateSmlConfiguration() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("validateSmlConfiguration", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return validateSmlConfigurationImpl();
		else
			return iLearningReasonerProcessRoles.get(0).validateSmlConfiguration();

	}

	public Object execute(Object value) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("execute", this);

		if (countInCallStack > 1 || iLearningReasonerProcessRoles.size() == 0)
			return executeImpl(value);
		else
			return iLearningReasonerProcessRoles.get(0).execute(value);

	}

	public abstract void startGestureImpl(Category category);
	public abstract void stopGestureImpl(Category category);
	public abstract void teachCurrentStateImpl(Category category);
	public abstract void updateClassifierImpl(Object trainingDate);
	public abstract void buildClassifierImpl();
	public abstract Category classifyImpl(IOPropertyList input);
	public abstract DataComponent getQualityOfServiceImpl();
	public abstract void startImpl();
	public abstract void stopImpl();
	public abstract Boolean isRunningImpl();
	public abstract IOPropertyList retrieveValuesImpl();
	public abstract String getIdImpl();
	public abstract Integer getSamplingRateImpl();
	public abstract IOPropertyList retrieveOutputStructureImpl();
	public abstract Boolean initializeImpl();
	public abstract Boolean validateSmlConfigurationImpl();
	public abstract Object executeImpl(Object value);

}
