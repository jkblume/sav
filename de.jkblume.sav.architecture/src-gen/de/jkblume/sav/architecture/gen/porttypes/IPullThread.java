
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

@PortTypeA(name = "IPullThread", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IPullThread {

	public static Class remotePortClass = IPullThreadRemote.class;
	public static Class proxyComponentClass = IPullThreadRemoteProxy.class;

	public void start();
	public void stop();
	public Boolean isRunning();

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IPullThreadRemote extends AbstractRemotePort<IPullThread>implements IPullThread {

		public IPullThreadRemote(String name) {
			super(name, IPullThread.class);
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
	@Component(name = "IPullThread", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IPullThreadRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IPullThreadRemoteProxy extends RemoteProxyComponent<IPullThread>implements IPullThread {
		public IPullThreadRemoteProxy(String name) {
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
