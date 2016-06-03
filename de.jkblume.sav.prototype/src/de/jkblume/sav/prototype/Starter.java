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
import org.smags.runtime.reconfigurtion.operations.SetPortParameterOperation;
import org.smags.runtime.reconfigurtion.operations.SetupComponentOperation;
import org.smags.runtime.reconfigurtion.operations.SetupPortOperation;
import org.vast.sensorML.SMLUtils;
import org.vast.xml.XMLReaderException;

import de.jkblume.sav.architecture.components.JAggregateProcess;
import de.jkblume.sav.architecture.components.JSimpleProcess;
import de.jkblume.sav.architecture.components.JTechnicalSensor;
import de.jkblume.sav.architecture.components.JVisualizer;
import de.jkblume.sav.architecture.gen.porttypes.IReasonerProcess;
import de.jkblume.sav.components.components.NaiveBayesReasoner;
import de.jkblume.sav.components.ports.ClassificationVisualisationStrategy;
import de.jkblume.sav.components.ports.Cube3DVisualisationStrategy;
import de.jkblume.sav.components.ports.DiagramVisualisationStrategy;
import de.jkblume.sav.components.ports.RegexProcessor;
import de.jkblume.sav.components.ports.SerialTechnicalSensor;
import de.jkblume.sav.components.utils.ClassifyUiHelper;
import de.jkblume.sav.prototype.ui.UI;
import javafx.application.Application;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;

public class Starter {
	
	private static SMLUtils utils;
	
	private static AggregateProcess aggregatorProcess;
	
	public static void main(String[] args) throws FileNotFoundException, XMLReaderException, InterruptedException {
		RuntimeEnvironment re = RuntimeEnvironment.instance();

		// Initializing the MAPE-K Feedback loop
		re.initializeAdaptationArchitecture();

		utils = new SMLUtils(SMLUtils.V2_0);
		
		Thread uiThread = new Thread(new Runnable() {
	
				@Override
				public void run() {
					Application.launch(UI.class, new String[] {});
				}
		});
		uiThread.start();
		
		// create visualization strategy
		ReconfigurationScript rs = createDiagramVisualizationScript();
		re.getReconfigurationEngine().executeScript(rs);

		rs = createCubeVisualizationScript();
		re.getReconfigurationEngine().executeScript(rs);
		
		// create serial technical sensor for glove
		rs = createSerialSensorScript();
		re.getReconfigurationEngine().executeScript(rs);

		// create processes for the glove sensor
		rs = createAggregateProcessScript();
		re.getReconfigurationEngine().executeScript(rs);
		
		rs = createChildProcessesScript();
		re.getReconfigurationEngine().executeScript(rs);
		
		rs = setupAggregateProcessScript();
		re.getReconfigurationEngine().executeScript(rs);
		

		rs = connectAggregateProcessScript();
		re.getReconfigurationEngine().executeScript(rs);

//		Thread.sleep(1000);

		rs = connectSensorScript();
		re.getReconfigurationEngine().executeScript(rs);
		
		rs = createReasoningEngineScript();
		re.getReconfigurationEngine().executeScript(rs);
		
		JTechnicalSensor simulatingSensor = (JTechnicalSensor) re.getRuntimeModel().getComponentByName("jtps1");
		simulatingSensor.start();
		
		IReasonerProcess reasoner = (IReasonerProcess) re.getRuntimeModel().getComponentByName("ls");
		ClassifyUiHelper.reasoner = reasoner;
	}

	private static ReconfigurationScript createReasoningEngineScript() throws FileNotFoundException, XMLReaderException {
		InputStream is = new FileInputStream("res/glove_reasoner.xml");
		AbstractProcess gloveReasonerDesription = utils.readProcess(is);
		
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreateComponentInstanceOperation("ls", NaiveBayesReasoner.class));
		ops.add(new SetComponentParameterOperation("ls", "smlConfiguration", gloveReasonerDesription));
		ops.add(new ConnectOperation("jtps1", "ls", "ISensor"));
		ops.add(new SetupComponentOperation("ls"));
		
		ops.add(new CreateComponentInstanceOperation("v3", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("vs3", ClassificationVisualisationStrategy.class));
		ops.add(new BindPortOperation("v3", "vs3", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("v3"));
		ops.add(new SetupPortOperation("vs3"));
		ops.add(new ConnectOperation("ls", "v3", "ISensor"));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createCubeVisualizationScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreateComponentInstanceOperation("v2", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("vs2", Cube3DVisualisationStrategy.class));
		ops.add(new BindPortOperation("v2", "vs2", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("v2"));
		ops.add(new SetupPortOperation("vs2"));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript connectSensorScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new ConnectOperation("jtps1", "v", "ISensor"));
		ops.add(new ConnectOperation("jtps1", "v2", "ISensor"));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript connectAggregateProcessScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();
		
		ops.add(new ConnectOperation("ap", "jtps1", "IProcess"));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript setupAggregateProcessScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();
		
		ops.add(new SetupComponentOperation("ap"));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createChildProcessesScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreateComponentInstanceOperation("spX", JSimpleProcess.class));
		ops.add(new CreatePortInstanceOperation("regexX", RegexProcessor.class));
		ops.add(new BindPortOperation("spX", "regexX", "IProcess"));
		ops.add(new SetPortParameterOperation("regexX", "smlConfiguration", aggregatorProcess.getComponent("gyroscope_regex_x")));
		ops.add(new SetupPortOperation("regexX"));
		ops.add(new ConnectOperation("spX", "ap", "IProcess"));
		
		ops.add(new CreateComponentInstanceOperation("spY", JSimpleProcess.class));
		ops.add(new CreatePortInstanceOperation("regexY", RegexProcessor.class));
		ops.add(new BindPortOperation("spY", "regexY", "IProcess"));
		ops.add(new SetPortParameterOperation("regexY", "smlConfiguration", aggregatorProcess.getComponent("gyroscope_regex_y")));
		ops.add(new SetupPortOperation("regexY"));
		ops.add(new ConnectOperation("spY", "ap", "IProcess"));
		
		ops.add(new CreateComponentInstanceOperation("spZ", JSimpleProcess.class));
		ops.add(new CreatePortInstanceOperation("regexZ", RegexProcessor.class));
		ops.add(new BindPortOperation("spZ", "regexZ", "IProcess"));
		ops.add(new SetPortParameterOperation("regexZ", "smlConfiguration", aggregatorProcess.getComponent("gyroscope_regex_z")));
		ops.add(new SetupPortOperation("regexZ"));
		ops.add(new ConnectOperation("spZ", "ap", "IProcess"));
		
		ops.add(new CreateComponentInstanceOperation("spF", JSimpleProcess.class));
		ops.add(new CreatePortInstanceOperation("regexF", RegexProcessor.class));
		ops.add(new BindPortOperation("spF", "regexF", "IProcess"));
		ops.add(new SetPortParameterOperation("regexF", "smlConfiguration", aggregatorProcess.getComponent("regex_f")));
		ops.add(new SetupPortOperation("regexF"));
		ops.add(new ConnectOperation("spF", "ap", "IProcess"));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createAggregateProcessScript() throws FileNotFoundException, XMLReaderException {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();
		
		InputStream is = new FileInputStream("res/glove_aggregator_process.xml");
		aggregatorProcess = (AggregateProcess) utils.readProcess(is);

		ops.add(new CreateComponentInstanceOperation("ap", JAggregateProcess.class));
		ops.add(new SetComponentParameterOperation("ap", "smlConfiguration", aggregatorProcess));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createSerialSensorScript() throws FileNotFoundException, XMLReaderException {
		InputStream is = new FileInputStream("res/serial_sensor_glove.xml");
		AbstractProcess serialSensorGloveDescription = utils.readProcess(is);
		
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();
		ops.add(new CreateComponentInstanceOperation("jtps1", JTechnicalSensor.class));
		ops.add(new CreatePortInstanceOperation("ssp1", SerialTechnicalSensor.class));
		ops.add(new BindPortOperation("jtps1", "ssp1", "ISensor"));
		ops.add(new SetComponentParameterOperation("jtps1", "smlConfiguration", serialSensorGloveDescription));
		ops.add(new SetupComponentOperation("jtps1"));
		ops.add(new SetupPortOperation("ssp1"));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createDiagramVisualizationScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreateComponentInstanceOperation("v", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("vs", DiagramVisualisationStrategy.class));
		ops.add(new BindPortOperation("v", "vs", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("v"));
		ops.add(new SetupPortOperation("vs"));
		
		return new ReconfigurationScript(ops);
	}
}