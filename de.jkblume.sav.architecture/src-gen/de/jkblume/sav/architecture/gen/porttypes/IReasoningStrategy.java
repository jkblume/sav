
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

@PortTypeA(name = "IReasoningStrategy", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IReasoningStrategy {

	public static Class remotePortClass = IReasoningStrategyRemote.class;
	public static Class proxyComponentClass = IReasoningStrategyRemoteProxy.class;

	public void buildClassifier(ISensor reasoner, List<ISensor> sourceSensors);
	public void updateClassifier(Object trainingDate);
	public DataComponent getQualityOfService();

	public Boolean getInjectorProvided();
	public void setInjectorProvided(Boolean injectorProvided);

	public Boolean getExtractorProvided();
	public void setExtractorProvided(Boolean extractorProvided);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IReasoningStrategyRemote extends AbstractRemotePort<IReasoningStrategy>implements IReasoningStrategy {

		public IReasoningStrategyRemote(String name) {
			super(name, IReasoningStrategy.class);
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

		public void buildClassifier(ISensor reasoner, List<ISensor> sourceSensors) {
			base.buildClassifier(reasoner, sourceSensors);
		}
		public void updateClassifier(Object trainingDate) {
			base.updateClassifier(trainingDate);
		}
		public DataComponent getQualityOfService() {
			DataComponent result = base.getQualityOfService();
			return result;
		}

		@Override
		public <T> T as(Class<T> c) {
			return base.as(c);
		}
	}

	/*--------- REMOTE PROXY ---------*/
	@Component(name = "IReasoningStrategy", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IReasoningStrategyRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IReasoningStrategyRemoteProxy extends RemoteProxyComponent<IReasoningStrategy>
			implements
				IReasoningStrategy {
		public IReasoningStrategyRemoteProxy(String name) {
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

		public void buildClassifier(ISensor reasoner, List<ISensor> sourceSensors) {
			BuildClassifierRemoteMessage in = new BuildClassifierRemoteMessage();
			in.setReasoner(reasoner);
			in.setSourceSensors(sourceSensors);

			client.send(in, BuildClassifierRemoteMessage.class);
		}

		public void updateClassifier(Object trainingDate) {
			UpdateClassifierRemoteMessage in = new UpdateClassifierRemoteMessage();
			in.setTrainingDate(trainingDate);

			client.send(in, UpdateClassifierRemoteMessage.class);
		}

		public DataComponent getQualityOfService() {
			GetQualityOfServiceRemoteMessage in = new GetQualityOfServiceRemoteMessage();

			return ((GetQualityOfServiceRemoteMessage) client.send(in, GetQualityOfServiceRemoteMessage.class))
					.getResponseResult();
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

		private ISensor reasoner;

		private List<ISensor> sourceSensors;

		public BuildClassifierRemoteMessage() {
			super("buildClassifier", ISensor.class.getName(), List.class.getName());
		}

		public void setReasoner(ISensor reasoner) {
			this.reasoner = reasoner;
		}

		public ISensor getReasoner() {
			return reasoner;
		}

		public void setSourceSensors(List<ISensor> sourceSensors) {
			this.sourceSensors = sourceSensors;
		}

		public List<ISensor> getSourceSensors() {
			return sourceSensors;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getReasoner());

			result.add(getSourceSensors());

			return result;
		}
	}

	public class UpdateClassifierRemoteMessage extends RemoteMessageBase<Object> {

		private Object trainingDate;

		public UpdateClassifierRemoteMessage() {
			super("updateClassifier", Object.class.getName());
		}

		public void setTrainingDate(Object trainingDate) {
			this.trainingDate = trainingDate;
		}

		public Object getTrainingDate() {
			return trainingDate;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getTrainingDate());

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
