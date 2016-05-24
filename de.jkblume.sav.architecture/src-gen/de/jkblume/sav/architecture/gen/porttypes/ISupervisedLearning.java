
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

@PortTypeA(name = "ISupervisedLearning", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface ISupervisedLearning extends IReasoner {

	public static Class remotePortClass = ISupervisedLearningRemote.class;
	public static Class proxyComponentClass = ISupervisedLearningRemoteProxy.class;

	public void startGesture(Category category);
	public void stopGesture(Category category);
	public void teachCurrentState(Category category);
	public void train();

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class ISupervisedLearningRemote extends AbstractRemotePort<ISupervisedLearning>
			implements
				ISupervisedLearning {

		public ISupervisedLearningRemote(String name) {
			super(name, ISupervisedLearning.class);
		}

		public AbstractProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
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
		public void train() {
			base.train();
		}

		public void buildClassifier() {
			base.buildClassifier();
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
	@Component(name = "ISupervisedLearning", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "ISupervisedLearningRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class ISupervisedLearningRemoteProxy extends RemoteProxyComponent<ISupervisedLearning>
			implements
				ISupervisedLearning {
		public ISupervisedLearningRemoteProxy(String name) {
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

		public void train() {
			TrainRemoteMessage in = new TrainRemoteMessage();

			client.send(in, TrainRemoteMessage.class);
		}

		public void buildClassifier() {
			BuildClassifierRemoteMessage in = new BuildClassifierRemoteMessage();

			client.send(in, BuildClassifierRemoteMessage.class);
		}

		public IOPropertyList execute(IOPropertyList value) {
			ExecuteRemoteMessage in = new ExecuteRemoteMessage();
			in.setValue(value);

			return ((ExecuteRemoteMessage) client.send(in, ExecuteRemoteMessage.class)).getResponseResult();
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

	public class TrainRemoteMessage extends RemoteMessageBase<Object> {

		public TrainRemoteMessage() {
			super("train");
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			return result;
		}
	}

}
