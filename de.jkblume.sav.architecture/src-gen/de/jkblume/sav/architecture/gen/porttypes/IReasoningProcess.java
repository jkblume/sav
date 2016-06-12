
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

@PortTypeA(name = "IReasoningProcess", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IReasoningProcess extends IProcess {

	public static Class remotePortClass = IReasoningProcessRemote.class;
	public static Class proxyComponentClass = IReasoningProcessRemoteProxy.class;

	public Object inject(IOPropertyList input);
	public IOPropertyList extract(Object input);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IReasoningProcessRemote extends AbstractRemotePort<IReasoningProcess>implements IReasoningProcess {

		public IReasoningProcessRemote(String name) {
			super(name, IReasoningProcess.class);
		}

		public AbstractProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
		}

		public Object inject(IOPropertyList input) {
			Object result = base.inject(input);
			return result;
		}
		public IOPropertyList extract(Object input) {
			IOPropertyList result = base.extract(input);
			return result;
		}

		public Object execute(Object input) {
			Object result = base.execute(input);
			return result;
		}

		@Override
		public <T> T as(Class<T> c) {
			return base.as(c);
		}
	}

	/*--------- REMOTE PROXY ---------*/
	@Component(name = "IReasoningProcess", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IReasoningProcessRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IReasoningProcessRemoteProxy extends RemoteProxyComponent<IReasoningProcess>
			implements
				IReasoningProcess {
		public IReasoningProcessRemoteProxy(String name) {
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

		public Object inject(IOPropertyList input) {
			InjectRemoteMessage in = new InjectRemoteMessage();
			in.setInput(input);

			return ((InjectRemoteMessage) client.send(in, InjectRemoteMessage.class)).getResponseResult();
		}

		public IOPropertyList extract(Object input) {
			ExtractRemoteMessage in = new ExtractRemoteMessage();
			in.setInput(input);

			return ((ExtractRemoteMessage) client.send(in, ExtractRemoteMessage.class)).getResponseResult();
		}

		public Object execute(Object input) {
			ExecuteRemoteMessage in = new ExecuteRemoteMessage();
			in.setInput(input);

			return ((ExecuteRemoteMessage) client.send(in, ExecuteRemoteMessage.class)).getResponseResult();
		}

	}

	public class InjectRemoteMessage extends RemoteMessageBase<Object> {

		private IOPropertyList input;

		public InjectRemoteMessage() {
			super("inject", IOPropertyList.class.getName());
		}

		public void setInput(IOPropertyList input) {
			this.input = input;
		}

		public IOPropertyList getInput() {
			return input;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getInput());

			return result;
		}
	}

	public class ExtractRemoteMessage extends RemoteMessageBase<IOPropertyList> {

		private Object input;

		public ExtractRemoteMessage() {
			super("extract", Object.class.getName());
		}

		public void setInput(Object input) {
			this.input = input;
		}

		public Object getInput() {
			return input;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getInput());

			return result;
		}
	}

}
