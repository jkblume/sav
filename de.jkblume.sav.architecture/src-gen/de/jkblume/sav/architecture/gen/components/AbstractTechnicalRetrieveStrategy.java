
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

@Component(name = "TechnicalRetrieveStrategy", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "TechnicalRetrieveStrategy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractTechnicalRetrieveStrategy extends AbstractComponent
		implements
			INotifyPropertyChanged,
			IRetrieveStrategy {

	private final List<IRetrieveStrategy> iRetrieveStrategyRoles = new ArrayList<IRetrieveStrategy>();

	public AbstractTechnicalRetrieveStrategy(String name) {
		super(name);
	}

	public AbstractProcess getSmlConfiguration() {
		return (AbstractProcess) getSharedMemory().getValue(AbstractTechnicalRetrieveStrategy.class,
				"smlConfiguration");
	}

	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		AbstractProcess oldValue = getSmlConfiguration();
		getSharedMemory().setValue(AbstractTechnicalRetrieveStrategy.class, "smlConfiguration", smlConfiguration);
		notifyPropertyChanged(this, "smlConfiguration", oldValue, smlConfiguration);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == IRetrieveStrategy.class)
			return iRetrieveStrategyRoles.size() > 0 ? (T) iRetrieveStrategyRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof IRetrieveStrategy) {
			iRetrieveStrategyRoles.add(0, (IRetrieveStrategy) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof IRetrieveStrategy && iRetrieveStrategyRoles.contains(port)) {
			iRetrieveStrategyRoles.remove(port);
			return true;
		}

		return false;
	}

	public Object retrieveValue() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("retrieveValue", this);

		if (countInCallStack > 1 || iRetrieveStrategyRoles.size() == 0)
			return retrieveValueImpl();
		else
			return iRetrieveStrategyRoles.get(0).retrieveValue();

	}

	public abstract Object retrieveValueImpl();

}
