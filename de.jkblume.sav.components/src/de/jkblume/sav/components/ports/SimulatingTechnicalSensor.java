
package de.jkblume.sav.components.ports;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.architecture.gen.porttypes.*;
import de.jkblume.sav.architecture.utils.MySMLUtils;

import java.net.URI;
import java.lang.Class;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.IOPropertyList;

import de.jkblume.sav.architecture.gen.porttypes.*;

import de.jkblume.sav.components.gen.ports.*;
import org.smags.componentmodel.IPort;
import org.vast.data.QuantityImpl;

import java.util.*;

public class SimulatingTechnicalSensor extends AbstractSimulatingTechnicalSensor {

	private double simulatedValue = 1;
	private Thread simulatingThread;
	private Random random = new Random();
	
	public SimulatingTechnicalSensor(String name) {
		super(name);
	}

	public void setup() {

		simulatingThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (isRunning()) {
					int nextInt = random.nextInt(10);
					double i = (nextInt+1)/10.0;
					simulatedValue += i;
					
					try {
						Thread.sleep(1900);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public String getId() {
		String result = base.getId();
		return result;
	}
	
	public IOPropertyList retrieveValues() {
		IOPropertyList values = new IOPropertyList();
		Quantity value = new QuantityImpl();
		value.setValue(simulatedValue);
		values.add(value);
		return values;
	}
	
	public void start() {
		simulatingThread.start();
		base.start();
	}
	public void stop() {
		simulatingThread.interrupt();
		simulatedValue = 1.0;
		base.stop();
	}
	public Boolean isRunning() {
		Boolean result = base.isRunning();
		return result;
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

	@Override
	public void handleIProcessConnected(IProcess item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIProcessDisconnected(IProcess item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object execute(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSmlConfiguration(AbstractProcess smlConfiguration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getSamplingRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean validateSmlConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IOPropertyList retrieveOutputStructure() {
		return base.retrieveOutputStructure();
	}

	@Override
	public Boolean initialize() {
		// TODO Auto-generated method stub
		return null;
	}


}
