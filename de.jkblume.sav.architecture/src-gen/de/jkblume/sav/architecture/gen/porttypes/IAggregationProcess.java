
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

@PortTypeA(name = "IAggregationProcess", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IAggregationProcess extends IProcess {

	public static Class remotePortClass = IAggregationProcessRemote.class;
	public static Class proxyComponentClass = IAggregationProcessRemoteProxy.class;

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IAggregationProcessRemote extends AbstractRemotePort<IAggregationProcess>
			implements
				IAggregationProcess {

		public IAggregationProcessRemote(String name) {
			super(name, IAggregationProcess.class);
		}

		public AbstractProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
		}

		public Object execute(Object value) {
			Object result = base.execute(value);
			return result;
		}

		@Override
		public <T> T as(Class<T> c) {
			return base.as(c);
		}
	}

	/*--------- REMOTE PROXY ---------*/
	@Component(name = "IAggregationProcess", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IAggregationProcessRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IAggregationProcessRemoteProxy extends RemoteProxyComponent<IAggregationProcess>
			implements
				IAggregationProcess {
		public IAggregationProcessRemoteProxy(String name) {
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

		public Object execute(Object value) {
			ExecuteRemoteMessage in = new ExecuteRemoteMessage();
			in.setValue(value);

			return ((ExecuteRemoteMessage) client.send(in, ExecuteRemoteMessage.class)).getResponseResult();
		}

	}

}
