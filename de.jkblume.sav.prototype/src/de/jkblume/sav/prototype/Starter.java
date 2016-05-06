package de.jkblume.sav.prototype;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.smags.runtime.RuntimeEnvironment;
import org.smags.runtime.reconfigurtion.ReconfigurationScript;
import org.smags.runtime.reconfigurtion.ReconfigurtionOperation;
import org.smags.runtime.reconfigurtion.operations.BindPortOperation;
import org.smags.runtime.reconfigurtion.operations.ConnectOperation;
import org.smags.runtime.reconfigurtion.operations.CreateComponentInstanceOperation;
import org.smags.runtime.reconfigurtion.operations.CreatePortInstanceOperation;
import org.smags.runtime.reconfigurtion.operations.SetComponentParameterOperation;
import org.smags.runtime.reconfigurtion.operations.SetupComponentOperation;
import org.smags.runtime.reconfigurtion.operations.SetupPortOperation;
import org.vast.sensorML.SMLUtils;
import org.vast.xml.XMLReaderException;

import de.jkblume.sav.architecture.components.JVisualizer;
import de.jkblume.sav.components.components.SimulatingTechnicalSensor;
import de.jkblume.sav.components.ports.JavaFXVisualisationStrategy;
import net.opengis.sensorml.v20.AbstractProcess;

public class Starter {
	
	public static void main(String[] args) throws FileNotFoundException, XMLReaderException, InterruptedException {
		RuntimeEnvironment re = RuntimeEnvironment.instance();

		//Initializing the MAPE-K Feedback loop 
		re.initializeAdaptationArchitecture();

		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

        InputStream is = new FileInputStream("res/simsen.xml");
        SMLUtils utils = new SMLUtils(SMLUtils.V2_0);
        AbstractProcess ssDesc = utils.readProcess(is);
        
        ops.add(new CreatePortInstanceOperation("vs", JavaFXVisualisationStrategy.class));
		
		ops.add(new CreateComponentInstanceOperation("simsen", SimulatingTechnicalSensor.class));
		ops.add(new CreateComponentInstanceOperation("v", JVisualizer.class));
		
		ops.add(new BindPortOperation("v", "vs", "IVisualisationStrategy"));
		ops.add(new SetComponentParameterOperation("simsen", "smlConfiguration", ssDesc));
		
		ops.add(new SetupComponentOperation("simsen"));
		ops.add(new SetupComponentOperation("v"));
		ops.add(new SetupPortOperation("vs"));
		
		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));

		SimulatingTechnicalSensor simulatingSensor = (SimulatingTechnicalSensor) re.getRuntimeModel().getComponentByName("simsen");
		simulatingSensor.start();
		
		Thread.sleep(1000);
		
		ops = new ArrayList<ReconfigurtionOperation>();
		ops.add(new ConnectOperation("simsen", "v", "ISensor"));
		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));

		
	}
}