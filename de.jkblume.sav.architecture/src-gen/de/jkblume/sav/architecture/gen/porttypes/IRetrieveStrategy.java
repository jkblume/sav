
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

@PortTypeA(name = "IRetrieveStrategy", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IRetrieveStrategy {

	public static Class remotePortClass = IRetrieveStrategyRemote.class;
	public static Class proxyComponentClass = IRetrieveStrategyRemoteProxy.class;

	public Object retrieveValue();

	public AbstractProcess getSmlConfiguration();
	public void setSmlConfiguration(AbstractProcess smlConfiguration);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IRetrieveStrategyRemote extends AbstractRemotePort<IRetrieveStrategy>implements IRetrieveStrategy {

		public IRetrieveStrategyRemote(String name) {
			super(name, IRetrieveStrategy.class);
		}

		public AbstractProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
		}

		public Object retrieveValue() {
			Object result = base.retrieveValue();
			return result;
		}

		@Override
		public <T> T as(Class<T> c) {
			return base.as(c);
		}
	}

	/*--------- REMOTE PROXY ---------*/
	@Component(name = "IRetrieveStrategy", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IRetrieveStrategyRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IRetrieveStrategyRemoteProxy extends RemoteProxyComponent<IRetrieveStrategy>
			implements
				IRetrieveStrategy {
		public IRetrieveStrategyRemoteProxy(String name) {
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

		public Object retrieveValue() {
			RetrieveValueRemoteMessage in = new RetrieveValueRemoteMessage();

			return ((RetrieveValueRemoteMessage) client.send(in, RetrieveValueRemoteMessage.class)).getResponseResult();
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

	public class RetrieveValueRemoteMessage extends RemoteMessageBase<Object> {

		public RetrieveValueRemoteMessage() {
			super("retrieveValue");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

}
