
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

@PortTypeA(name = "IPullReasoner", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IPullReasoner extends ISensor {

	public static Class remotePortClass = IPullReasonerRemote.class;
	public static Class proxyComponentClass = IPullReasonerRemoteProxy.class;

	public void classify(Long intervalStart, Long intervalEnd);
	public void classifySnapshot(Long timestamp);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IPullReasonerRemote extends AbstractRemotePort<IPullReasoner>implements IPullReasoner {

		public IPullReasonerRemote(String name) {
			super(name, IPullReasoner.class);
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

		public void classify(Long intervalStart, Long intervalEnd) {
			base.classify(intervalStart, intervalEnd);
		}
		public void classifySnapshot(Long timestamp) {
			base.classifySnapshot(timestamp);
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
	@Component(name = "IPullReasoner", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IPullReasonerRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IPullReasonerRemoteProxy extends RemoteProxyComponent<IPullReasoner>implements IPullReasoner {
		public IPullReasonerRemoteProxy(String name) {
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

		public void classify(Long intervalStart, Long intervalEnd) {
			ClassifyRemoteMessage in = new ClassifyRemoteMessage();
			in.setIntervalStart(intervalStart);
			in.setIntervalEnd(intervalEnd);

			client.send(in, ClassifyRemoteMessage.class);
		}

		public void classifySnapshot(Long timestamp) {
			ClassifySnapshotRemoteMessage in = new ClassifySnapshotRemoteMessage();
			in.setTimestamp(timestamp);

			client.send(in, ClassifySnapshotRemoteMessage.class);
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

	public class ClassifyRemoteMessage extends RemoteMessageBase<Object> {

		private Long intervalStart;

		private Long intervalEnd;

		public ClassifyRemoteMessage() {
			super("classify", Long.class.getName(), Long.class.getName());
		}

		public void setIntervalStart(Long intervalStart) {
			this.intervalStart = intervalStart;
		}

		public Long getIntervalStart() {
			return intervalStart;
		}

		public void setIntervalEnd(Long intervalEnd) {
			this.intervalEnd = intervalEnd;
		}

		public Long getIntervalEnd() {
			return intervalEnd;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getIntervalStart());

			result.add(getIntervalEnd());

			return result;
		}
	}

	public class ClassifySnapshotRemoteMessage extends RemoteMessageBase<Object> {

		private Long timestamp;

		public ClassifySnapshotRemoteMessage() {
			super("classifySnapshot", Long.class.getName());
		}

		public void setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
		}

		public Long getTimestamp() {
			return timestamp;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getTimestamp());

			return result;
		}
	}

}
