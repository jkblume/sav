
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

@Component(name = "PullThread", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "PullThread", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
public abstract class AbstractPullThread extends AbstractComponent implements INotifyPropertyChanged, IPullThread {

	@RequirementA
	private ISensor iSensor;

	public ISensor getISensor() {
		return this.iSensor;

	}

	public void setISensor(ISensor iSensor) {
		this.iSensor = iSensor;
		if (iSensor != null)
			handleISensorConnected(iSensor);
		else
			handleISensorDisconnected(iSensor);
	}

	public abstract void handleISensorConnected(ISensor item);
	public abstract void handleISensorDisconnected(ISensor item);

	private final List<IPullThread> iPullThreadRoles = new ArrayList<IPullThread>();

	public AbstractPullThread(String name) {
		super(name);
	}

	@Override
	protected <T> T innerGetPort(Class<T> type) {

		if (type == IPullThread.class)
			return iPullThreadRoles.size() > 0 ? (T) iPullThreadRoles.get(0) : (T) this;

		return null;
	}

	@Override
	public boolean innerBindPort(IPort port) {

		if (port instanceof IPullThread) {
			iPullThreadRoles.add(0, (IPullThread) port);
			return true;
		}

		return false;
	}

	@Override
	public boolean innerUnbindPort(IPort port) {

		if (port instanceof IPullThread && iPullThreadRoles.contains(port)) {
			iPullThreadRoles.remove(port);
			return true;
		}

		return false;
	}

	public void start() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("start", this);

		if (countInCallStack > 1 || iPullThreadRoles.size() == 0)
			startImpl();
		else
			iPullThreadRoles.get(0).start();

	}

	public void stop() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("stop", this);

		if (countInCallStack > 1 || iPullThreadRoles.size() == 0)
			stopImpl();
		else
			iPullThreadRoles.get(0).stop();

	}

	public Boolean isRunning() {

		int countInCallStack = ReflectionHelper.countContainedInCallStack("isRunning", this);

		if (countInCallStack > 1 || iPullThreadRoles.size() == 0)
			return isRunningImpl();
		else
			return iPullThreadRoles.get(0).isRunning();

	}

	public abstract void startImpl();
	public abstract void stopImpl();
	public abstract Boolean isRunningImpl();

}
