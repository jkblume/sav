
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

@PortTypeA(name = "ILearningReasoningStrategy", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface ILearningReasoningStrategy extends IReasoningStrategy {

	public static Class remotePortClass = ILearningReasoningStrategyRemote.class;
	public static Class proxyComponentClass = ILearningReasoningStrategyRemoteProxy.class;

	public void startGesture(Category category);
	public void stopGesture(Category category);
	public void teachCurrentState(Category category);
	public void updateClassifier(Object trainingDate);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class ILearningReasoningStrategyRemote extends AbstractRemotePort<ILearningReasoningStrategy>
			implements
				ILearningReasoningStrategy {

		public ILearningReasoningStrategyRemote(String name) {
			super(name, ILearningReasoningStrategy.class);
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

		public void startGesture(Category category) {
			base.startGesture(category);
		}
		public void stopGesture(Category category) {
			base.stopGesture(category);
		}
		public void teachCurrentState(Category category) {
			base.teachCurrentState(category);
		}
		public void updateClassifier(Object trainingDate) {
			base.updateClassifier(trainingDate);
		}

		public void buildClassifier(List<ISensor> sensors) {
			base.buildClassifier(sensors);
		}
		public Category classifyCurrentState(IOPropertyList input) {
			Category result = base.classifyCurrentState(input);
			return result;
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
	@Component(name = "ILearningReasoningStrategy", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "ILearningReasoningStrategyRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class ILearningReasoningStrategyRemoteProxy extends RemoteProxyComponent<ILearningReasoningStrategy>
			implements
				ILearningReasoningStrategy {
		public ILearningReasoningStrategyRemoteProxy(String name) {
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

		public void startGesture(Category category) {
			StartGestureRemoteMessage in = new StartGestureRemoteMessage();
			in.setCategory(category);

			client.send(in, StartGestureRemoteMessage.class);
		}

		public void stopGesture(Category category) {
			StopGestureRemoteMessage in = new StopGestureRemoteMessage();
			in.setCategory(category);

			client.send(in, StopGestureRemoteMessage.class);
		}

		public void teachCurrentState(Category category) {
			TeachCurrentStateRemoteMessage in = new TeachCurrentStateRemoteMessage();
			in.setCategory(category);

			client.send(in, TeachCurrentStateRemoteMessage.class);
		}

		public void updateClassifier(Object trainingDate) {
			UpdateClassifierRemoteMessage in = new UpdateClassifierRemoteMessage();
			in.setTrainingDate(trainingDate);

			client.send(in, UpdateClassifierRemoteMessage.class);
		}

		public void buildClassifier(List<ISensor> sensors) {
			BuildClassifierRemoteMessage in = new BuildClassifierRemoteMessage();
			in.setSensors(sensors);

			client.send(in, BuildClassifierRemoteMessage.class);
		}

		public Category classifyCurrentState(IOPropertyList input) {
			ClassifyCurrentStateRemoteMessage in = new ClassifyCurrentStateRemoteMessage();
			in.setInput(input);

			return ((ClassifyCurrentStateRemoteMessage) client.send(in, ClassifyCurrentStateRemoteMessage.class))
					.getResponseResult();
		}

		public DataComponent getQualityOfService() {
			GetQualityOfServiceRemoteMessage in = new GetQualityOfServiceRemoteMessage();

			return ((GetQualityOfServiceRemoteMessage) client.send(in, GetQualityOfServiceRemoteMessage.class))
					.getResponseResult();
		}

	}

	public class StartGestureRemoteMessage extends RemoteMessageBase<Object> {

		private Category category;

		public StartGestureRemoteMessage() {
			super("startGesture", Category.class.getName());
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		public Category getCategory() {
			return category;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getCategory());

			return result;
		}
	}

	public class StopGestureRemoteMessage extends RemoteMessageBase<Object> {

		private Category category;

		public StopGestureRemoteMessage() {
			super("stopGesture", Category.class.getName());
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		public Category getCategory() {
			return category;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getCategory());

			return result;
		}
	}

	public class TeachCurrentStateRemoteMessage extends RemoteMessageBase<Object> {

		private Category category;

		public TeachCurrentStateRemoteMessage() {
			super("teachCurrentState", Category.class.getName());
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		public Category getCategory() {
			return category;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getCategory());

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

}
