
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

	public String getId();
	public Boolean initialize();
	public IOPropertyList retrieveValues();
	public void start();
	public void stop();
	public Boolean isRunning();

	public AbstractPhysicalProcess getSmlConfiguration();
	public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration);

	public Event getLastEvent();
	public void setLastEvent(Event lastEvent);

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

		public Event getLastEvent() {
			return base.getLastEvent();
		}

		public void setLastEvent(Event lastEvent) {
			base.setLastEvent(lastEvent);
		}

		public String getId() {
			String result = base.getId();
			return result;
		}
		public Boolean initialize() {
			Boolean result = base.initialize();
			return result;
		}
		public IOPropertyList retrieveValues() {
			IOPropertyList result = base.retrieveValues();
			return result;
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

		public Event getLastEvent() {
			GetLastEventRemoteMessage in = new GetLastEventRemoteMessage();
			return client.send(in, GetLastEventRemoteMessage.class).getResponseResult();
		}

		public void setLastEvent(Event lastEvent) {
			SetLastEventRemoteMessage in = new SetLastEventRemoteMessage();
			in.setLastEvent(lastEvent);
			client.send(in, SetLastEventRemoteMessage.class);
		}

		public String getId() {
			GetIdRemoteMessage in = new GetIdRemoteMessage();

			return ((GetIdRemoteMessage) client.send(in, GetIdRemoteMessage.class)).getResponseResult();
		}

		public Boolean initialize() {
			InitializeRemoteMessage in = new InitializeRemoteMessage();

			return ((InitializeRemoteMessage) client.send(in, InitializeRemoteMessage.class)).getResponseResult();
		}

		public IOPropertyList retrieveValues() {
			RetrieveValuesRemoteMessage in = new RetrieveValuesRemoteMessage();

			return ((RetrieveValuesRemoteMessage) client.send(in, RetrieveValuesRemoteMessage.class))
					.getResponseResult();
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

	public class GetLastEventRemoteMessage extends RemoteMessageBase<Event> {

		public GetLastEventRemoteMessage() {
			super("getLastEvent");
		}

	}

	public class SetLastEventRemoteMessage extends RemoteMessageBase<Object> {

		private Event lastEvent;

		public SetLastEventRemoteMessage() {
			super("setLastEvent", Event.class.getName());
		}

		public void setLastEvent(Event lastEvent) {
			this.lastEvent = lastEvent;
		}

		public Event getLastEvent() {
			return lastEvent;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();
			result.add(getLastEvent());
			return result;
		}
	}

	public class GetIdRemoteMessage extends RemoteMessageBase<String> {

		public GetIdRemoteMessage() {
			super("getId");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

	public class InitializeRemoteMessage extends RemoteMessageBase<Boolean> {

		public InitializeRemoteMessage() {
			super("initialize");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

	public class RetrieveValuesRemoteMessage extends RemoteMessageBase<IOPropertyList> {

		public RetrieveValuesRemoteMessage() {
			super("retrieveValues");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

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
