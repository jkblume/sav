
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

	public void buildClassifier(List<ISensor> sensors);
	public DataComponent getQualityOfService();
	public Category classifyCurrentState(IOPropertyList input);

	public Boolean getInjectorProvided();
	public void setInjectorProvided(Boolean injectorProvided);

	public Boolean getExtractorProvided();
	public void setExtractorProvided(Boolean extractorProvided);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IReasoningProcessRemote extends AbstractRemotePort<IReasoningProcess>implements IReasoningProcess {

		public IReasoningProcessRemote(String name) {
			super(name, IReasoningProcess.class);
		}

		public Boolean getInjectorProvided() {
			return base.getInjectorProvided();
		}

		public void setInjectorProvided(Boolean injectorProvided) {
			base.setInjectorProvided(injectorProvided);
		}

		public Boolean getExtractorProvided() {
			return base.getExtractorProvided();
		}

		public void setExtractorProvided(Boolean extractorProvided) {
			base.setExtractorProvided(extractorProvided);
		}

		public AbstractProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
		}

		public void buildClassifier(List<ISensor> sensors) {
			base.buildClassifier(sensors);
		}
		public DataComponent getQualityOfService() {
			DataComponent result = base.getQualityOfService();
			return result;
		}
		public Category classifyCurrentState(IOPropertyList input) {
			Category result = base.classifyCurrentState(input);
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

		public Boolean getInjectorProvided() {
			GetInjectorProvidedRemoteMessage in = new GetInjectorProvidedRemoteMessage();
			return client.send(in, GetInjectorProvidedRemoteMessage.class).getResponseResult();
		}

		public void setInjectorProvided(Boolean injectorProvided) {
			SetInjectorProvidedRemoteMessage in = new SetInjectorProvidedRemoteMessage();
			in.setInjectorProvided(injectorProvided);
			client.send(in, SetInjectorProvidedRemoteMessage.class);
		}

		public Boolean getExtractorProvided() {
			GetExtractorProvidedRemoteMessage in = new GetExtractorProvidedRemoteMessage();
			return client.send(in, GetExtractorProvidedRemoteMessage.class).getResponseResult();
		}

		public void setExtractorProvided(Boolean extractorProvided) {
			SetExtractorProvidedRemoteMessage in = new SetExtractorProvidedRemoteMessage();
			in.setExtractorProvided(extractorProvided);
			client.send(in, SetExtractorProvidedRemoteMessage.class);
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

		public void buildClassifier(List<ISensor> sensors) {
			BuildClassifierRemoteMessage in = new BuildClassifierRemoteMessage();
			in.setSensors(sensors);

			client.send(in, BuildClassifierRemoteMessage.class);
		}

		public DataComponent getQualityOfService() {
			GetQualityOfServiceRemoteMessage in = new GetQualityOfServiceRemoteMessage();

			return ((GetQualityOfServiceRemoteMessage) client.send(in, GetQualityOfServiceRemoteMessage.class))
					.getResponseResult();
		}

		public Category classifyCurrentState(IOPropertyList input) {
			ClassifyCurrentStateRemoteMessage in = new ClassifyCurrentStateRemoteMessage();
			in.setInput(input);

			return ((ClassifyCurrentStateRemoteMessage) client.send(in, ClassifyCurrentStateRemoteMessage.class))
					.getResponseResult();
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

	public class GetInjectorProvidedRemoteMessage extends RemoteMessageBase<Boolean> {

		public GetInjectorProvidedRemoteMessage() {
			super("getInjectorProvided");
		}

	}

	public class SetInjectorProvidedRemoteMessage extends RemoteMessageBase<Object> {

		private Boolean injectorProvided;

		public SetInjectorProvidedRemoteMessage() {
			super("setInjectorProvided", Boolean.class.getName());
		}

		public void setInjectorProvided(Boolean injectorProvided) {
			this.injectorProvided = injectorProvided;
		}

		public Boolean getInjectorProvided() {
			return injectorProvided;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();
			result.add(getInjectorProvided());
			return result;
		}
	}

	public class GetExtractorProvidedRemoteMessage extends RemoteMessageBase<Boolean> {

		public GetExtractorProvidedRemoteMessage() {
			super("getExtractorProvided");
		}

	}

	public class SetExtractorProvidedRemoteMessage extends RemoteMessageBase<Object> {

		private Boolean extractorProvided;

		public SetExtractorProvidedRemoteMessage() {
			super("setExtractorProvided", Boolean.class.getName());
		}

		public void setExtractorProvided(Boolean extractorProvided) {
			this.extractorProvided = extractorProvided;
		}

		public Boolean getExtractorProvided() {
			return extractorProvided;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();
			result.add(getExtractorProvided());
			return result;
		}
	}

	public class BuildClassifierRemoteMessage extends RemoteMessageBase<Object> {

		private List<ISensor> sensors;

		public BuildClassifierRemoteMessage() {
			super("buildClassifier", List.class.getName());
		}

		public void setSensors(List<ISensor> sensors) {
			this.sensors = sensors;
		}

		public List<ISensor> getSensors() {
			return sensors;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getSensors());

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

	public class ClassifyCurrentStateRemoteMessage extends RemoteMessageBase<Category> {

		private IOPropertyList input;

		public ClassifyCurrentStateRemoteMessage() {
			super("classifyCurrentState", IOPropertyList.class.getName());
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

}
