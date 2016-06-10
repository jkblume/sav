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

import de.jkblume.sav.components.components.JAggregateProcess;
import de.jkblume.sav.components.components.JLogicalRetrieveStrategy;
import de.jkblume.sav.components.components.JNumericScaleProcess;
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

	private static AggregateProcess gloveAggregatorConfig;

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

		InputStream is = new FileInputStream("res/glove_process.xml");
		gloveAggregatorConfig = (AggregateProcess) utils.readProcess(is);

		is = new FileInputStream("res/glove_retriever.xml");
		AbstractProcess gloveRetrieverConfig = utils.readProcess(is);

		is = new FileInputStream("res/glove_sensor.xml");
		AbstractProcess gloveSensorConfig = utils.readProcess(is);

		is = new FileInputStream("res/scale_sensor.xml");
		AbstractProcess scaleSensorConfig = utils.readProcess(is);

		is = new FileInputStream("res/scale_retriever.xml");
		AbstractProcess scaleRetrieverConfig = utils.readProcess(is);

		is = new FileInputStream("res/scale_process.xml");
		AbstractProcess scaleProcessConfig = utils.readProcess(is);

		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreateComponentInstanceOperation("diagramVizComp", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("diagramVizPort", DiagramVisualisationStrategy.class));
		ops.add(new BindPortOperation("diagramVizComp", "diagramVizPort", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("diagramVizComp"));
		ops.add(new SetupPortOperation("diagramVizPort"));

		ops.add(new CreateComponentInstanceOperation("cubeVizComp", JVisualizer.class));
		ops.add(new CreatePortInstanceOperation("cubeVizPort", Cube3DVisualisationStrategy.class));
		ops.add(new BindPortOperation("cubeVizComp", "cubeVizPort", "IVisualisationStrategy"));
		ops.add(new SetupComponentOperation("cubeVizComp"));
		ops.add(new SetupPortOperation("cubeVizPort"));

		ops.add(new CreateComponentInstanceOperation("gloveSensor", JSensor.class));
		ops.add(new CreateComponentInstanceOperation("serialRetriever", JSerialPortRetrieveStrategy.class));
		ops.add(new SetComponentParameterOperation("serialRetriever", "smlConfiguration", gloveRetrieverConfig));
		ops.add(new SetComponentParameterOperation("gloveSensor", "smlConfiguration", gloveSensorConfig));
		ops.add(new SetupComponentOperation("gloveSensor"));
		ops.add(new SetupComponentOperation("serialRetriever"));
		ops.add(new ConnectOperation("serialRetriever", "gloveSensor", "IRetrieveStrategy"));
		
		ops.add(new CreateComponentInstanceOperation("scaleSensor", JSensor.class));
		ops.add(new CreateComponentInstanceOperation("scaleRetriever", JLogicalRetrieveStrategy.class));
		ops.add(new CreateComponentInstanceOperation("scaleProcess", JNumericScaleProcess.class));
		ops.add(new SetComponentParameterOperation("scaleRetriever", "smlConfiguration", scaleRetrieverConfig));
		ops.add(new SetComponentParameterOperation("scaleSensor", "smlConfiguration", scaleSensorConfig));
		ops.add(new SetComponentParameterOperation("scaleProcess", "smlConfiguration", scaleProcessConfig));
		ops.add(new SetupComponentOperation("scaleSensor"));
		ops.add(new SetupComponentOperation("scaleRetriever"));
		ops.add(new SetupComponentOperation("scaleProcess"));
		ops.add(new ConnectOperation("scaleRetriever", "scaleSensor", "IRetrieveStrategy"));
		ops.add(new ConnectOperation("gloveSensor", "scaleRetriever", "ISensor"));
		ops.add(new ConnectOperation("scaleProcess", "scaleSensor", "IProcess"));
		
		ops.add(new CreateComponentInstanceOperation("ap", JAggregateProcess.class));
		ops.add(new SetComponentParameterOperation("ap", "smlConfiguration", gloveAggregatorConfig));

		ops.add(new CreateComponentInstanceOperation("regexX", JRegexProcess.class));
		ops.add(new SetComponentParameterOperation("regexX", "smlConfiguration",
				gloveAggregatorConfig.getComponent("gyroscope_regex_x")));
		ops.add(new SetupComponentOperation("regexX"));
		ops.add(new ConnectOperation("regexX", "ap", "IProcess"));

		ops.add(new CreateComponentInstanceOperation("regexY", JRegexProcess.class));
		ops.add(new SetComponentParameterOperation("regexY", "smlConfiguration",
				gloveAggregatorConfig.getComponent("gyroscope_regex_y")));
		ops.add(new SetupComponentOperation("regexY"));
		ops.add(new ConnectOperation("regexY", "ap", "IProcess"));

		ops.add(new CreateComponentInstanceOperation("regexZ", JRegexProcess.class));
		ops.add(new SetComponentParameterOperation("regexZ", "smlConfiguration",
				gloveAggregatorConfig.getComponent("gyroscope_regex_z")));
		ops.add(new SetupComponentOperation("regexZ"));
		ops.add(new ConnectOperation("regexZ", "ap", "IProcess"));

		ops.add(new CreateComponentInstanceOperation("regexF", JRegexProcess.class));
		ops.add(new SetComponentParameterOperation("regexF", "smlConfiguration",
				gloveAggregatorConfig.getComponent("regex_f")));
		ops.add(new SetupComponentOperation("regexF"));
		ops.add(new ConnectOperation("regexF", "ap", "IProcess"));

		ops.add(new SetupComponentOperation("ap"));

		ops.add(new ConnectOperation("ap", "gloveSensor", "IProcess"));

		ops.add(new ConnectOperation("scaleSensor", "diagramVizComp", "ISensor"));
		ops.add(new ConnectOperation("gloveSensor", "cubeVizComp", "ISensor"));

		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));

		JSensor gloveSensor = (JSensor) re.getRuntimeModel().getComponentByName("gloveSensor");
		gloveSensor.start();
		JSensor scaleSensor = (JSensor) re.getRuntimeModel().getComponentByName("scaleSensor");
		scaleSensor.start();
	}

}