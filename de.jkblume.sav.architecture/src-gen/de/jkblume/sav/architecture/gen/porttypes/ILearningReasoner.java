
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

@PortTypeA(name = "ILearningReasoner", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface ILearningReasoner extends IReasoner {

	public static Class remotePortClass = ILearningReasonerRemote.class;
	public static Class proxyComponentClass = ILearningReasonerRemoteProxy.class;

	public Object translateEvent(Event event);
	public void train();
	public void teachCurrentState(Category category);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class ILearningReasonerRemote extends AbstractRemotePort<ILearningReasoner>implements ILearningReasoner {

		public ILearningReasonerRemote(String name) {
			super(name, ILearningReasoner.class);
		}

		public AbstractPhysicalProcess getSmlConfiguration() {
			return base.getSmlConfiguration();
		}

		public void setSmlConfiguration(AbstractPhysicalProcess smlConfiguration) {
			base.setSmlConfiguration(smlConfiguration);
		}

		public Event getCurrentEvent() {
			return base.getCurrentEvent();
		}

		public void setCurrentEvent(Event currentEvent) {
			base.setCurrentEvent(currentEvent);
		}

		public Object translateEvent(Event event) {
			Object result = base.translateEvent(event);
			return result;
		}
		public void train() {
			base.train();
		}
		public void teachCurrentState(Category category) {
			base.teachCurrentState(category);
		}

		public void classify(Long intervalStart, Long intervalEnd) {
			base.classify(intervalStart, intervalEnd);
		}
		public void classifySnapshot(Long timestamp) {
			base.classifySnapshot(timestamp);
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
	@Component(name = "ILearningReasoner", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "ILearningReasonerRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class ILearningReasonerRemoteProxy extends RemoteProxyComponent<ILearningReasoner>
			implements
				ILearningReasoner {
		public ILearningReasonerRemoteProxy(String name) {
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

		public Event getCurrentEvent() {
			GetCurrentEventRemoteMessage in = new GetCurrentEventRemoteMessage();
			return client.send(in, GetCurrentEventRemoteMessage.class).getResponseResult();
		}

		public void setCurrentEvent(Event currentEvent) {
			SetCurrentEventRemoteMessage in = new SetCurrentEventRemoteMessage();
			in.setCurrentEvent(currentEvent);
			client.send(in, SetCurrentEventRemoteMessage.class);
		}

		public Object translateEvent(Event event) {
			TranslateEventRemoteMessage in = new TranslateEventRemoteMessage();
			in.setEvent(event);

			return ((TranslateEventRemoteMessage) client.send(in, TranslateEventRemoteMessage.class))
					.getResponseResult();
		}

		public void train() {
			TrainRemoteMessage in = new TrainRemoteMessage();

			client.send(in, TrainRemoteMessage.class);
		}

		public void teachCurrentState(Category category) {
			TeachCurrentStateRemoteMessage in = new TeachCurrentStateRemoteMessage();
			in.setCategory(category);

			client.send(in, TeachCurrentStateRemoteMessage.class);
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

	public class TranslateEventRemoteMessage extends RemoteMessageBase<Object> {

		private Event event;

		public TranslateEventRemoteMessage() {
			super("translateEvent", Event.class.getName());
		}

		public void setEvent(Event event) {
			this.event = event;
		}

		public Event getEvent() {
			return event;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getEvent());

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

}
