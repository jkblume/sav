
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

@Component(name = "ReasoningProcess", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "ReasoningProcess", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractReasoningProcess extends AbstractComponent
		implements
			INotifyPropertyChanged,
			IReasoningProcess {

	private final List<IReasoningProcess> iReasoningProcessRoles = new ArrayList<IReasoningProcess>();

	public AbstractReasoningProcess(String name) {
		super(name);
	}

	public AbstractProcess getSmlConfiguration() {
		return (AbstractProcess) getSharedMemory().getValue(AbstractReasoningProcess.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		AbstractProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractReasoningProcess.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == IReasoningProcess.class)
			return iReasoningProcessRoles.size() > 0 ? (T) iReasoningProcessRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof IReasoningProcess) {
			iReasoningProcessRoles.add(0, (IReasoningProcess) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof IReasoningProcess && iReasoningProcessRoles.contains(port)) {
			iReasoningProcessRoles.remove(port);
			return true;
		}

		return false;
	}

	public Object inject(IOPropertyList input) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("inject", this);

		if (countInCallStack > 1 || iReasoningProcessRoles.size() == 0)
			return injectImpl(input);
		else
			return iReasoningProcessRoles.get(0).inject(input);

	}

	public IOPropertyList extract(Object input) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("extract", this);

		if (countInCallStack > 1 || iReasoningProcessRoles.size() == 0)
			return extractImpl(input);
		else
			return iReasoningProcessRoles.get(0).extract(input);

	}

	public Object execute(Object input) {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("execute", this);

		if (countInCallStack > 1 || iReasoningProcessRoles.size() == 0)
			return executeImpl(input);
		else
			return iReasoningProcessRoles.get(0).execute(input);

	}

	public abstract Object injectImpl(IOPropertyList input);
	public abstract IOPropertyList extractImpl(Object input);
	public abstract Object executeImpl(Object input);

}
