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

import de.jkblume.sav.architecture.components.JTechnicalPullSensor;
import de.jkblume.sav.architecture.components.JVisualizer;
import de.jkblume.sav.components.ports.JavaFXVisualisationStrategy;
import de.jkblume.sav.components.ports.SimulatingTechnicalSensor;
import net.opengis.sensorml.v20.AbstractProcess;

public class Starter {
	
	public static void main(String[] args) throws FileNotFoundException, XMLReaderException, InterruptedException {
		RuntimeEnvironment re = RuntimeEnvironment.instance();

		//Initializing the MAPE-K Feedback loop 
		re.initializeAdaptationArchitecture();

		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		SMLUtils utils = new SMLUtils(SMLUtils.V2_0);
		
        InputStream is = new FileInputStream("res/ssp1.xml");
        AbstractProcess sspDesc1 = utils.readProcess(is);
        
        is = new FileInputStream("res/ssp2.xml");
        AbstractProcess sspDesc2 = utils.readProcess(is);
        

		ops.add(new CreateComponentInstanceOperation("v", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("vs", JavaFXVisualisationStrategy.class));
		ops.add(new BindPortOperation("v", "vs", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("v"));
		ops.add(new SetupPortOperation("vs"));
		
		ops.add(new CreateComponentInstanceOperation("jtps1", JTechnicalPullSensor.class));
		ops.add(new CreatePortInstanceOperation("ssp1", SimulatingTechnicalSensor.class));
		ops.add(new BindPortOperation("jtps1", "ssp1", "ISensor"));
		ops.add(new SetComponentParameterOperation("jtps1", "smlConfiguration", sspDesc1));
		ops.add(new SetupComponentOperation("jtps1"));
		ops.add(new SetupPortOperation("ssp1"));
		
		ops.add(new CreateComponentInstanceOperation("jtps2", JTechnicalPullSensor.class));
		ops.add(new CreatePortInstanceOperation("ssp2", SimulatingTechnicalSensor.class));
		ops.add(new BindPortOperation("jtps2", "ssp2", "ISensor"));
		ops.add(new SetComponentParameterOperation("jtps2", "smlConfiguration", sspDesc2));
		ops.add(new SetupComponentOperation("jtps2"));
		ops.add(new SetupPortOperation("ssp2"));
		

		
		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));

		JTechnicalPullSensor simulatingSensor = (JTechnicalPullSensor) re.getRuntimeModel().getComponentByName("jtps1");
		simulatingSensor.start();
		
		Thread.sleep(1000);
		
		ops = new ArrayList<ReconfigurtionOperation>();
		ops.add(new ConnectOperation("jtps1", "v", "ISensor"));
		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));

		simulatingSensor = (JTechnicalPullSensor) re.getRuntimeModel().getComponentByName("jtps2");
		simulatingSensor.start();
		
		Thread.sleep(10000);
		
		ops.add(new CreateComponentInstanceOperation("v2", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("vs2", JavaFXVisualisationStrategy.class));
		ops.add(new BindPortOperation("v2", "vs2", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("v2"));
		ops.add(new SetupPortOperation("vs2"));
		
		
		ops = new ArrayList<ReconfigurtionOperation>();
		ops.add(new ConnectOperation("jtps2", "v", "ISensor"));
		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));

		
		
	}
}