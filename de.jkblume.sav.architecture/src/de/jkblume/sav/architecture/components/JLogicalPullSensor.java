
package de.jkblume.sav.architecture.components;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.porttypes.*;
import de.jkblume.sav.architecture.utils.MySMLUtils;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.lang.Class;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataComponent;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.IOPropertyList;

import java.util.*;
import org.smags.componentmodel.annotations.ParameterA;

public class JLogicalPullSensor extends AbstractLogicalPullSensor {
	private Map<String, Event> lastEventOfPushSensors;
	private Thread pollingThread;
	private boolean running;
	
	private String id;
	private int samplingRate;
	
	public JLogicalPullSensor(String name) {
		super(name);
	}

	public void setup() {
		Boolean initializationSuccess = initialize();
		if (!initializationSuccess) {
			throw new IllegalStateException("Failure during initialization of sensor " + id);
		}
		
		pollingThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				IOPropertyList values = retrieveValues();				
				
				// process values
				if (getIProcess() != null) {
					values = getIProcess().execute(values);
				} 
				
				Event currentEvent = MySMLUtils.createEvent(values);
				setLastEvent(currentEvent);
				
				try {
					Thread.sleep(samplingRate);
				} catch (InterruptedException e) {
					System.out.println("Polling thread of sensor " + id + " was interupted");
				}
			}
		});
	}

	public void destroy() {
		stop();
	}

	public void startImpl() {
		pollingThread.start();
		running = true;
	}

	public void stopImpl() {
		pollingThread.interrupt();
		running = false;
	}

	public Boolean isRunningImpl() {
		return running;
	}

	@Override
	public void notifyEventChangedImpl(Object sender, Event argument) {
		if (sender instanceof IMyPushSensorSubject) {
			lastEventOfPushSensors.put(((IMyPushSensorSubject) sender).getId(), argument);
		}
		retrieveValues();
	}

	@Override
	public void handleIProcessConnected(IProcess connectedItem) {
		//TODO Handle
	}

	@Override
	public void handleIProcessDisconnected(IProcess disconnectedItem) {
		//TODO Handle
	}
	
	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

		if (propertyName.equals("lastEvent")) {
			//TODO:Implement
		}

	}

	@Override
	public void handleIMyPushSensorAdded(IMyPushSensorSubject item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIMyPushSensorRemoved(IMyPushSensorSubject item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IOPropertyList retrieveValuesImpl() {
		IOPropertyList values = new IOPropertyList();
		
		// retrieve values from pull sensors
		for (ISensor pullSensor : getISensors()) {
			OgcPropertyList<DataComponent> partialResult = pullSensor.getLastEvent().getPropertyList();
			values.addAll(partialResult);
		}
		
		// join with last values from push sensors
		for (Event lastPushedEvent : lastEventOfPushSensors.values()) {
			values.addAll(lastPushedEvent.getPropertyList());
		}
		
		return values;
	}

	@Override
	public String getIdImpl() {
		return id;
	}

	@Override
	public void handleISensorAdded(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleISensorRemoved(ISensor item) {
		// TODO Auto-generated method stub
		
	}

	public Boolean validateSmlConfigurationImpl() {
		boolean result = true;
		
		id = getSmlConfiguration().getId();
		result &= id != null;
	
		
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter("samplingRate");
		result &= parameter != null && parameter instanceof Count;
		if (result) {
			samplingRate = ((Count) parameter).getValue();
		}
		
		return result;
	}

	@Override
	public Boolean initializeImpl() {
		return validateSmlConfigurationImpl();
	}
}
