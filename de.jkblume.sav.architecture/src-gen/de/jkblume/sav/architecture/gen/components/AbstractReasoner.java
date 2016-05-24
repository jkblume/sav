
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

@Component(name = "Reasoner", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "Reasoner", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractReasoner extends AbstractComponent implements INotifyPropertyChanged, IReasoner {

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

	private final List<IReasoner> iReasonerRoles = new ArrayList<IReasoner>();

	public AbstractReasoner(String name) {
		super(name);
	}

	public AbstractProcess getSmlConfiguration() {
		return (AbstractProcess) getSharedMemory().getValue(AbstractReasoner.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		AbstractProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractReasoner.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == IReasoner.class)
			return iReasonerRoles.size() > 0 ? (T) iReasonerRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof IReasoner) {
			iReasonerRoles.add(0, (IReasoner) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof IReasoner && iReasonerRoles.contains(port)) {
			iReasonerRoles.remove(port);
			return true;
		}

		return false;
	}

	public void buildClassifier() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("buildClassifier", this);

		if (countInCallStack > 1 || iReasonerRoles.size() == 0)
			buildClassifierImpl();
		else
			iReasonerRoles.get(0).buildClassifier();

	}

	public IOPropertyList execute(IOPropertyList value) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("execute", this);

		if (countInCallStack > 1 || iReasonerRoles.size() == 0)
			return executeImpl(value);
		else
			return iReasonerRoles.get(0).execute(value);

	}

	public abstract void buildClassifierImpl();
	public abstract IOPropertyList executeImpl(IOPropertyList value);

}
