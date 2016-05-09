
package de.jkblume.sav.components.gen.porttypes;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.components.*;

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

@PortTypeA(name = "IVisualisationStrategy", architectureName = "SavMetaArchitecture", architectureNamespace = "de.jkblume.sav.architecture")
public interface IVisualisationStrategy {

	public static Class remotePortClass = IVisualisationStrategyRemote.class;
	public static Class proxyComponentClass = IVisualisationStrategyRemoteProxy.class;

	public void visualizeEvent(ISensor source, Event event);
	public void addSensor(ISensor sensor);
	public void removeSensor(ISensor sensor);

	public <T> T as(Class<T> c);

	/*--------- REMOTE ---------*/

	public class IVisualisationStrategyRemote extends AbstractRemotePort<IVisualisationStrategy>
			implements
				IVisualisationStrategy {

		public IVisualisationStrategyRemote(String name) {
			super(name, IVisualisationStrategy.class);
		}

		public void visualizeEvent(ISensor source, Event event) {
			base.visualizeEvent(source, event);
		}
		public void addSensor(ISensor sensor) {
			base.addSensor(sensor);
		}
		public void removeSensor(ISensor sensor) {
			base.removeSensor(sensor);
		}

		@Override
		public <T> T as(Class<T> c) {
			return base.as(c);
		}
	}

	/*--------- REMOTE PROXY ---------*/
	@Component(name = "IVisualisationStrategy", appName = "SavMetaArchitecture", appPackageName = "de.jkblume.sav.architecture", componentTypeName = "IVisualisationStrategyRemoteProxy", typeArchitectureName = "SavMetaArchitecture", typeArchitectureNamespace = "de.jkblume.sav.architecture")
	public class IVisualisationStrategyRemoteProxy extends RemoteProxyComponent<IVisualisationStrategy>
			implements
				IVisualisationStrategy {
		public IVisualisationStrategyRemoteProxy(String name) {
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

		public void visualizeEvent(ISensor source, Event event) {
			VisualizeEventRemoteMessage in = new VisualizeEventRemoteMessage();
			in.setSource(source);
			in.setEvent(event);

			client.send(in, VisualizeEventRemoteMessage.class);
		}

		public void addSensor(ISensor sensor) {
			AddSensorRemoteMessage in = new AddSensorRemoteMessage();
			in.setSensor(sensor);

			client.send(in, AddSensorRemoteMessage.class);
		}

		public void removeSensor(ISensor sensor) {
			RemoveSensorRemoteMessage in = new RemoveSensorRemoteMessage();
			in.setSensor(sensor);

			client.send(in, RemoveSensorRemoteMessage.class);
		}

	}

	public class VisualizeEventRemoteMessage extends RemoteMessageBase<Object> {

		private ISensor source;

		private Event event;

		public VisualizeEventRemoteMessage() {
			super("visualizeEvent", ISensor.class.getName(), Event.class.getName());
		}

		public void setSource(ISensor source) {
			this.source = source;
		}

		public ISensor getSource() {
			return source;
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

			result.add(getSource());

			result.add(getEvent());

			return result;
		}
	}

	public class AddSensorRemoteMessage extends RemoteMessageBase<Object> {

		private ISensor sensor;

		public AddSensorRemoteMessage() {
			super("addSensor", ISensor.class.getName());
		}

		public void setSensor(ISensor sensor) {
			this.sensor = sensor;
		}

		public ISensor getSensor() {
			return sensor;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getSensor());

			return result;
		}
	}

	public class RemoveSensorRemoteMessage extends RemoteMessageBase<Object> {

		private ISensor sensor;

		public RemoveSensorRemoteMessage() {
			super("removeSensor", ISensor.class.getName());
		}

		public void setSensor(ISensor sensor) {
			this.sensor = sensor;
		}

		public ISensor getSensor() {
			return sensor;
		}

		@Override
		public List<Object> getArguments() {
			List<Object> result = new ArrayList<Object>();

			result.add(getSensor());

			return result;
		}
	}

}
