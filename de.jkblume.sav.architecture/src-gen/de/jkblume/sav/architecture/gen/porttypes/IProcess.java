
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

@PortTypeA(name = "IProcess", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IProcess {

	public static Class remotePortClass = IProcessRemote.class;
	public static Class proxyComponentClass = IProcessRemoteProxy.class;

	public IOPropertyList execute(IOPropertyList value);

	public AbstractProcess getSmlConfiguration();
	public void setSmlConfiguration(AbstractProcess smlConfiguration);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IProcessRemote extends AbstractRemotePort<IProcess>implements IProcess {

		public IProcessRemote(String name) {
			super(name, IProcess.class);
		}

		public AbstractProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
		}

		public IOPropertyList execute(IOPropertyList value) {
			IOPropertyList result = base.execute(value);
			return result;
		}

		@Override
		public <T> T as(Class<T> c) {
			return base.as(c);
		}
	}

	/*--------- REMOTE PROXY ---------*/
	@Component(name = "IProcess", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IProcessRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IProcessRemoteProxy extends RemoteProxyComponent<IProcess>implements IProcess {
		public IProcessRemoteProxy(String name) {
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

		public AbstractProcess getSmlConfiguration() {
			GetSmlConfigurationRemoteMessage in = new GetSmlConfigurationRemoteMessage();
			return client.send(in, GetSmlConfigurationRemoteMessage.class).getResponseResult();
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			SetSmlConfigurationRemoteMessage in = new SetSmlConfigurationRemoteMessage();
			in.setSmlConfiguration(smlConfiguration);
			client.send(in, SetSmlConfigurationRemoteMessage.class);
		}

		public IOPropertyList execute(IOPropertyList value) {
			ExecuteRemoteMessage in = new ExecuteRemoteMessage();
			in.setValue(value);

			return ((ExecuteRemoteMessage) client.send(in, ExecuteRemoteMessage.class)).getResponseResult();
		}

	}

	public class GetSmlConfigurationRemoteMessage extends RemoteMessageBase<AbstractProcess> {

		public GetSmlConfigurationRemoteMessage() {
			super("getSmlConfiguration");
		}

	}

	public class SetSmlConfigurationRemoteMessage extends RemoteMessageBase<Object> {

		private AbstractProcess smlConfiguration;

		public SetSmlConfigurationRemoteMessage() {
			super("setSmlConfiguration", AbstractProcess.class.getName());
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			this.smlConfiguration = smlConfiguration;
		}

		public AbstractProcess getSmlConfiguration() {
			return smlConfiguration;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();
			result.add(getSmlConfiguration());
			return result;
		}
	}

	public class ExecuteRemoteMessage extends RemoteMessageBase<IOPropertyList> {

		private IOPropertyList value;

		public ExecuteRemoteMessage() {
			super("execute", IOPropertyList.class.getName());
		}

		public void setValue(IOPropertyList value) {
			this.value = value;
		}

		public IOPropertyList getValue() {
			return value;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getValue());

			return result;
		}
	}

}
