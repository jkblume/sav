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

import de.jkblume.sav.components.components.JAggregateProcess;
import de.jkblume.sav.components.components.JRegexProcess;
import de.jkblume.sav.components.components.JSensor;
import de.jkblume.sav.components.components.JSerialPortRetrieveStrategy;
import de.jkblume.sav.components.components.JVisualizer;
import de.jkblume.sav.components.ports.Cube3DVisualisationStrategy;
import de.jkblume.sav.components.ports.DiagramVisualisationStrategy;
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

		rs = connectVisualizersToSensorScript();
		re.getReconfigurationEngine().executeScript(rs);

		JSensor sensor = (JSensor) re.getRuntimeModel().getComponentByName("gloveSensor");
		sensor.start();

	}

	//
	private static ReconfigurationScript connectVisualizersToSensorScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new ConnectOperation("gloveSensor", "diagramVizComp", "ISensor"));
		ops.add(new ConnectOperation("gloveSensor", "cubeVizComp", "ISensor"));

		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript connectAggregateProcessScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new ConnectOperation("ap", "gloveSensor", "IProcess"));

		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript setupAggregateProcessScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new SetupComponentOperation("ap"));

		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createChildProcessesScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreateComponentInstanceOperation("regexX", JRegexProcess.class));
		ops.add(new SetComponentParameterOperation("regexX", "smlConfiguration",
				aggregatorProcess.getComponent("gyroscope_regex_x")));
		ops.add(new SetupComponentOperation("regexX"));
		ops.add(new ConnectOperation("regexX", "ap", "IProcess"));

		ops.add(new CreateComponentInstanceOperation("regexY", JRegexProcess.class));
		ops.add(new SetComponentParameterOperation("regexY", "smlConfiguration",
				aggregatorProcess.getComponent("gyroscope_regex_y")));
		ops.add(new SetupComponentOperation("regexY"));
		ops.add(new ConnectOperation("regexY", "ap", "IProcess"));

		ops.add(new CreateComponentInstanceOperation("regexZ", JRegexProcess.class));
		ops.add(new SetComponentParameterOperation("regexZ", "smlConfiguration",
				aggregatorProcess.getComponent("gyroscope_regex_z")));
		ops.add(new SetupComponentOperation("regexZ"));
		ops.add(new ConnectOperation("regexZ", "ap", "IProcess"));

		ops.add(new CreateComponentInstanceOperation("regexF", JRegexProcess.class));
		ops.add(new SetComponentParameterOperation("regexF", "smlConfiguration",
				aggregatorProcess.getComponent("regex_f")));
		ops.add(new SetupComponentOperation("regexF"));
		ops.add(new ConnectOperation("regexF", "ap", "IProcess"));

		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createAggregateProcessScript()
			throws FileNotFoundException, XMLReaderException {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		InputStream is = new FileInputStream("res/glove_aggregator_process.xml");
		aggregatorProcess = (AggregateProcess) utils.readProcess(is);

		ops.add(new CreateComponentInstanceOperation("ap", JAggregateProcess.class));
		ops.add(new SetComponentParameterOperation("ap", "smlConfiguration", aggregatorProcess));

		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createSerialSensorScript() throws FileNotFoundException, XMLReaderException {
		InputStream is = new FileInputStream("res/glove_serial_port_retriever.xml");
		AbstractProcess glove_serial_port_retriever = utils.readProcess(is);

		InputStream is2 = new FileInputStream("res/glove_sensor.xml");
		AbstractProcess glove_sensor = utils.readProcess(is2);

		
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();
		ops.add(new CreateComponentInstanceOperation("gloveSensor", JSensor.class));
		ops.add(new CreateComponentInstanceOperation("serialRetriever", JSerialPortRetrieveStrategy.class));
		ops.add(new SetComponentParameterOperation("serialRetriever", "smlConfiguration",
				glove_serial_port_retriever));
		ops.add(new SetComponentParameterOperation("gloveSensor", "smlConfiguration",
				glove_sensor));
		ops.add(new SetupComponentOperation("gloveSensor"));
		ops.add(new SetupComponentOperation("serialRetriever"));
		ops.add(new ConnectOperation("serialRetriever", "gloveSensor", "IRetrieveStrategy"));
		
		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createDiagramVisualizationScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreateComponentInstanceOperation("diagramVizComp", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("diagramVizPort", DiagramVisualisationStrategy.class));
		ops.add(new BindPortOperation("diagramVizComp", "diagramVizPort", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("diagramVizComp"));
		ops.add(new SetupPortOperation("diagramVizPort"));

		return new ReconfigurationScript(ops);
	}

	private static ReconfigurationScript createCubeVisualizationScript() {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreateComponentInstanceOperation("cubeVizComp", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("cubeVizPort", Cube3DVisualisationStrategy.class));
		ops.add(new BindPortOperation("cubeVizComp", "cubeVizPort", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("cubeVizComp"));
		ops.add(new SetupPortOperation("cubeVizPort"));

		return new ReconfigurationScript(ops);
	}
}