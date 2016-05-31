
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

@Component(name = "SimpleProcess", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "SimpleProcess", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractSimpleProcess extends AbstractComponent implements INotifyPropertyChanged, IProcess {

	private final List<IProcess> iProcessRoles = new ArrayList<IProcess>();

	public AbstractSimpleProcess(String name) {
		super(name);
	}

	public AbstractProcess getSmlConfiguration() {
		return (AbstractProcess) getSharedMemory().getValue(AbstractSimpleProcess.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		AbstractProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractSimpleProcess.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == IProcess.class)
			return iProcessRoles.size() > 0 ? (T) iProcessRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof IProcess) {
			iProcessRoles.add(0, (IProcess) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof IProcess && iProcessRoles.contains(port)) {
			iProcessRoles.remove(port);
			return true;
		}

		return false;
	}

	public Boolean validateSmlConfiguration() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("validateSmlConfiguration", this);

		if (countInCallStack > 1 || iProcessRoles.size() == 0)
			return validateSmlConfigurationImpl();
		else
			return iProcessRoles.get(0).validateSmlConfiguration();

	}

	public Object execute(Object value) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("execute", this);

		if (countInCallStack > 1 || iProcessRoles.size() == 0)
			return executeImpl(value);
		else
			return iProcessRoles.get(0).execute(value);

	}

	public abstract Boolean validateSmlConfigurationImpl();
	public abstract Object executeImpl(Object value);

}
