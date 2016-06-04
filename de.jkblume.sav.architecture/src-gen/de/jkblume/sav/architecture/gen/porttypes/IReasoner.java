
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

@PortTypeA(name = "IReasoner", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IReasoner extends ILogicalSensor {

	public static Class remotePortClass = IReasonerRemote.class;
	public static Class proxyComponentClass = IReasonerRemoteProxy.class;

	public void buildClassifier();
	public DataComponent getQualityOfService();

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IReasonerRemote extends AbstractRemotePort<IReasoner>implements IReasoner {

		public IReasonerRemote(String name) {
			super(name, IReasoner.class);
		}

		public Event getLastEvent() {
			return base.getLastEvent();
		}

		public void setLastEvent(Event lastEvent) {
			base.setLastEvent(lastEvent);
		}

		public AbstractProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
		}

		public void buildClassifier() {
			base.buildClassifier();
		}
		public DataComponent getQualityOfService() {
			DataComponent result = base.getQualityOfService();
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
		public IOPropertyList retrieveValues() {
			IOPropertyList result = base.retrieveValues();
			return result;
		}
		public String getId() {
			String result = base.getId();
			return result;
		}
		public Integer getSamplingRate() {
			Integer result = base.getSamplingRate();
			return result;
		}
		public IOPropertyList retrieveOutputStructure() {
			IOPropertyList result = base.retrieveOutputStructure();
			return result;
		}

		public Boolean initialize() {
			Boolean result = base.initialize();
			return result;
		}
		public Boolean validateSmlConfiguration() {
			Boolean result = base.validateSmlConfiguration();
			return result;
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
	@Component(name = "IReasoner", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IReasonerRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IReasonerRemoteProxy extends RemoteProxyComponent<IReasoner>implements IReasoner {
		public IReasonerRemoteProxy(String name) {
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

		public Event getLastEvent() {
			GetLastEventRemoteMessage in = new GetLastEventRemoteMessage();
			return client.send(in, GetLastEventRemoteMessage.class).getResponseResult();
		}

		public void setLastEvent(Event lastEvent) {
			SetLastEventRemoteMessage in = new SetLastEventRemoteMessage();
			in.setLastEvent(lastEvent);
			client.send(in, SetLastEventRemoteMessage.class);
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

		public void buildClassifier() {
			BuildClassifierRemoteMessage in = new BuildClassifierRemoteMessage();

			client.send(in, BuildClassifierRemoteMessage.class);
		}

		public DataComponent getQualityOfService() {
			GetQualityOfServiceRemoteMessage in = new GetQualityOfServiceRemoteMessage();

			return ((GetQualityOfServiceRemoteMessage) client.send(in, GetQualityOfServiceRemoteMessage.class))
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

		public IOPropertyList retrieveValues() {
			RetrieveValuesRemoteMessage in = new RetrieveValuesRemoteMessage();

			return ((RetrieveValuesRemoteMessage) client.send(in, RetrieveValuesRemoteMessage.class))
					.getResponseResult();
		}

		public String getId() {
			GetIdRemoteMessage in = new GetIdRemoteMessage();

			return ((GetIdRemoteMessage) client.send(in, GetIdRemoteMessage.class)).getResponseResult();
		}

		public Integer getSamplingRate() {
			GetSamplingRateRemoteMessage in = new GetSamplingRateRemoteMessage();

			return ((GetSamplingRateRemoteMessage) client.send(in, GetSamplingRateRemoteMessage.class))
					.getResponseResult();
		}

		public IOPropertyList retrieveOutputStructure() {
			RetrieveOutputStructureRemoteMessage in = new RetrieveOutputStructureRemoteMessage();

			return ((RetrieveOutputStructureRemoteMessage) client.send(in, RetrieveOutputStructureRemoteMessage.class))
					.getResponseResult();
		}

		public Boolean initialize() {
			InitializeRemoteMessage in = new InitializeRemoteMessage();

			return ((InitializeRemoteMessage) client.send(in, InitializeRemoteMessage.class)).getResponseResult();
		}

		public Boolean validateSmlConfiguration() {
			ValidateSmlConfigurationRemoteMessage in = new ValidateSmlConfigurationRemoteMessage();

			return ((ValidateSmlConfigurationRemoteMessage) client.send(in,
					ValidateSmlConfigurationRemoteMessage.class)).getResponseResult();
		}

		public Object execute(Object value) {
			ExecuteRemoteMessage in = new ExecuteRemoteMessage();
			in.setValue(value);

			return ((ExecuteRemoteMessage) client.send(in, ExecuteRemoteMessage.class)).getResponseResult();
		}

	}

	public class BuildClassifierRemoteMessage extends RemoteMessageBase<Object> {

		public BuildClassifierRemoteMessage() {
			super("buildClassifier");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

	public class GetQualityOfServiceRemoteMessage extends RemoteMessageBase<DataComponent> {

		public GetQualityOfServiceRemoteMessage() {
			super("getQualityOfService");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

}
