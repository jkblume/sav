
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

@Component(name = "LogicalRetrieveStrategy", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "LogicalRetrieveStrategy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractLogicalRetrieveStrategy extends AbstractComponent
		implements
			INotifyPropertyChanged,
			ILogicalRetrieveStrategy {

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

	private final List<ILogicalRetrieveStrategy> iLogicalRetrieveStrategyRoles = new ArrayList<ILogicalRetrieveStrategy>();

	public AbstractLogicalRetrieveStrategy(String name) {
		super(name);
	}

	public AbstractProcess getSmlConfiguration() {
		return (AbstractProcess) getSharedMemory().getValue(AbstractLogicalRetrieveStrategy.class, "smlConfiguration");
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		AbstractProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractLogicalRetrieveStrategy.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == ILogicalRetrieveStrategy.class)
			return iLogicalRetrieveStrategyRoles.size() > 0 ? (T) iLogicalRetrieveStrategyRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof ILogicalRetrieveStrategy) {
			iLogicalRetrieveStrategyRoles.add(0, (ILogicalRetrieveStrategy) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof ILogicalRetrieveStrategy && iLogicalRetrieveStrategyRoles.contains(port)) {
			iLogicalRetrieveStrategyRoles.remove(port);
			return true;
		}

		return false;
	}

	public Object retrieveValue() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("retrieveValue", this);

		if (countInCallStack > 1 || iLogicalRetrieveStrategyRoles.size() == 0)
			return retrieveValueImpl();
		else
			return iLogicalRetrieveStrategyRoles.get(0).retrieveValue();

	}

	public abstract Object retrieveValueImpl();

}
