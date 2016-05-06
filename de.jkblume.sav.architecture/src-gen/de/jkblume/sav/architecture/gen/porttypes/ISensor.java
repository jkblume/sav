
package de.jkblume.sav.architecture.gen.porttypes;

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
import org.smags.componentmodel.annotations.PortTypeA;
import org.smags.componentmodel.IPort;
import org.smags.remoting.RemoteProxyComponent;
import org.smags.remoting.AbstractRemotePort;
import org.smags.remoting.RemoteMessageBase;
import org.smags.componentmodel.annotations.Component;

@PortTypeA(name = "ISensor", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface ISensor {

	public static Class remotePortClass = ISensorRemote.class;
	public static Class proxyComponentClass = ISensorRemoteProxy.class;

	public void start();
	public void stop();
	public Boolean isRunning();

	public AbstractPhysicalProcess getSmlConfiguration();
	public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration);

	public Event getCurrentEvent();
	public void setCurrentEvent(Event currentEvent);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class ISensorRemote extends AbstractRemotePort<ISensor>implements ISensor {

		public ISensorRemote(String name) {
			super(name, ISensor.class);
		}

		public AbstractPhysicalProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
		}

		public Event getCurrentEvent() {
			return base.getCurrentEvent();
		}

		public void setCurrentEvent(Event currentEvent) {
			base.setCurrentEvent(currentEvent);
		}

		public void start() {
			base.start();
		}
		public void stop() {
			base.stop();
		}
		public Boolean isRunning() {
			Boolean result = base.isRunning();
			return result;
		}

		@Override
		public <T> T as(Class<T> c) {
			return base.as(c);
		}
	}

	/*--------- REMOTE PROXY ---------*/
	@Component(name = "ISensor", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "ISensorRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class ISensorRemoteProxy extends RemoteProxyComponent<ISensor>implements ISensor {
		public ISensorRemoteProxy(String name) {
			super(name);
		}

		@Override
		protected boolean innerBindPort(IPort port) {
			return false;
		}

		@Override
		protected boolean innerUnbindPort(IPort port) {
			return false;
		}

		@Override
		protected <T> T innerGetPort(Class<T> type) {
			return null;
		}

		public AbstractPhysicalProcess getSmlConfiguration() {
			GetSmlConfigurationRemoteMessage in = new GetSmlConfigurationRemoteMessage();
			return client.send(in, GetSmlConfigurationRemoteMessage.class).getResponseResult();
		}

		public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
			SetSmlConfigurationRemoteMessage in = new SetSmlConfigurationRemoteMessage();
			in.setSmlConfiguration(smlConfiguration);
			client.send(in, SetSmlConfigurationRemoteMessage.class);
		}

		public Event getCurrentEvent() {
			GetCurrentEventRemoteMessage in = new GetCurrentEventRemoteMessage();
			return client.send(in, GetCurrentEventRemoteMessage.class).getResponseResult();
		}

		public void setCurrentEvent(Event currentEvent) {
			SetCurrentEventRemoteMessage in = new SetCurrentEventRemoteMessage();
			in.setCurrentEvent(currentEvent);
			client.send(in, SetCurrentEventRemoteMessage.class);
		}

		public void start() {
			StartRemoteMessage in = new StartRemoteMessage();

			client.send(in, StartRemoteMessage.class);
		}

		public void stop() {
			StopRemoteMessage in = new StopRemoteMessage();

			client.send(in, StopRemoteMessage.class);
		}

		public Boolean isRunning() {
			IsRunningRemoteMessage in = new IsRunningRemoteMessage();

			return ((IsRunningRemoteMessage) client.send(in, IsRunningRemoteMessage.class)).getResponseResult();
		}

	}

	public class GetSmlConfigurationRemoteMessage extends RemoteMessageBase<AbstractPhysicalProcess> {

		public GetSmlConfigurationRemoteMessage() {
			super("getSmlConfiguration");
		}

	}

	public class SetSmlConfigurationRemoteMessage extends RemoteMessageBase<Object> {

		private AbstractPhysicalProcess smlConfiguration;

		public SetSmlConfigurationRemoteMessage() {
			super("setSmlConfiguration", AbstractPhysicalProcess.class.getName());
		}

		public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
			this.smlConfiguration = smlConfiguration;
		}

		public AbstractPhysicalProcess getSmlConfiguration() {
			return smlConfiguration;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();
			result.add(getSmlConfiguration());
			return result;
		}
	}

	public class GetCurrentEventRemoteMessage extends RemoteMessageBase<Event> {

		public GetCurrentEventRemoteMessage() {
			super("getCurrentEvent");
		}

	}

	public class SetCurrentEventRemoteMessage extends RemoteMessageBase<Object> {

		private Event currentEvent;

		public SetCurrentEventRemoteMessage() {
			super("setCurrentEvent", Event.class.getName());
		}

		public void setCurrentEvent(Event currentEvent) {
			this.currentEvent = currentEvent;
		}

		public Event getCurrentEvent() {
			return currentEvent;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();
			result.add(getCurrentEvent());
			return result;
		}
	}

	public class StartRemoteMessage extends RemoteMessageBase<Object> {

		public StartRemoteMessage() {
			super("start");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

	public class StopRemoteMessage extends RemoteMessageBase<Object> {

		public StopRemoteMessage() {
			super("stop");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

	public class IsRunningRemoteMessage extends RemoteMessageBase<Boolean> {

		public IsRunningRemoteMessage() {
			super("isRunning");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

}
