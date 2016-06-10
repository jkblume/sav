package de.jkblume.sav.prototype.ui;

import java.io.FileInputStream;
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
import org.smags.runtime.reconfigurtion.operations.DisconnectOperation;
import org.smags.runtime.reconfigurtion.operations.SetComponentParameterOperation;
import org.smags.runtime.reconfigurtion.operations.SetupComponentOperation;
import org.smags.runtime.reconfigurtion.operations.SetupPortOperation;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.components.JLogicalRetrieveStrategy;
import de.jkblume.sav.components.components.JSensor;
import de.jkblume.sav.components.components.JSimpleRuleReasoner;
import de.jkblume.sav.components.components.JVisualizer;
import de.jkblume.sav.components.components.JWekaLearningReasoner;
import de.jkblume.sav.components.ports.ClassificationVisualisationStrategy;
import de.jkblume.sav.components.ports.JSvmReasoner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.opengis.sensorml.v20.AbstractProcess;

public class UI extends Application {

	private Stage stage;

	private SMLUtils utils = new SMLUtils(SMLUtils.V2_0);

	@Override
	public void start(Stage stage) throws Exception {
		InputStream is = new FileInputStream("res/reasoner_sensor.xml");
		AbstractProcess process = utils.readProcess(is);

		InputStream is2 = new FileInputStream("res/reasoner_retriever.xml");
		AbstractProcess process2 = utils.readProcess(is2);

		InputStream is3 = new FileInputStream("res/reasoner_process.xml");
		AbstractProcess process3 = utils.readProcess(is3);

		VBox box = new VBox();
		Button btn1 = new Button();
		btn1.setText("Enable Specification Reasoning");
		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					RuntimeEnvironment re = RuntimeEnvironment.instance();
				

					List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();
					ops.add(new CreateComponentInstanceOperation("reasoningSensor1", JSensor.class));
					ops.add(new CreateComponentInstanceOperation("simpleReasoner", JSimpleRuleReasoner.class));
					ops.add(new CreateComponentInstanceOperation("logicalRetriever1", JLogicalRetrieveStrategy.class));
					
					ops.add(new SetComponentParameterOperation("reasoningSensor1", "smlConfiguration", process));
					ops.add(new SetComponentParameterOperation("logicalRetriever1", "smlConfiguration", process2));
					ops.add(new SetComponentParameterOperation("simpleReasoner", "smlConfiguration", process3));
	
					ops.add(new ConnectOperation("scaleSensor", "logicalRetriever1", "ISensor"));
					ops.add(new ConnectOperation("simpleReasoner", "reasoningSensor1", "IProcess"));
					ops.add(new ConnectOperation("logicalRetriever1", "reasoningSensor1", "IRetrieveStrategy"));
					ops.add(new SetupComponentOperation("reasoningSensor1"));
					ops.add(new SetupComponentOperation("simpleReasoner"));
					ops.add(new SetupComponentOperation("logicalRetriever1"));
					
					ops.add(new CreateComponentInstanceOperation("vSimple", JVisualizer.class));
					ops.add(new CreatePortInstanceOperation("vsSimple", ClassificationVisualisationStrategy.class));
					ops.add(new BindPortOperation("vSimple", "vsSimple", "IVisualisationStrategy"));
					ops.add(new SetupComponentOperation("vSimple"));
					ops.add(new SetupPortOperation("vsSimple"));
					ops.add(new ConnectOperation("reasoningSensor1", "vSimple", "ISensor"));
	
					ReconfigurationScript rs = new ReconfigurationScript(ops);
	
					re.getReconfigurationEngine().executeScript(rs);
	
					ISensor sensor = (ISensor) re.getRuntimeModel().getComponentByName("reasoningSensor1");
					ClassificationVisualisationStrategy vs = (ClassificationVisualisationStrategy) re.getRuntimeModel()
							.getPortByName("vsSimple");
					vs.getUiHelper().setSensor(sensor);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button btn2 = new Button();
		btn2.setText("Enable Learning Reasoning");
		btn2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					RuntimeEnvironment re = RuntimeEnvironment.instance();
				

					List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

					ops.add(new CreateComponentInstanceOperation("reasoningSensor2", JSensor.class));
					ops.add(new CreateComponentInstanceOperation("wekaReasoner", JWekaLearningReasoner.class));
					ops.add(new CreateComponentInstanceOperation("logicalRetriever2", JLogicalRetrieveStrategy.class));
					
					ops.add(new SetComponentParameterOperation("reasoningSensor2", "smlConfiguration", process));
					ops.add(new SetComponentParameterOperation("logicalRetriever2", "smlConfiguration", process2));
					ops.add(new SetComponentParameterOperation("wekaReasoner", "smlConfiguration", process3));
	
					ops.add(new ConnectOperation("gloveSensor", "logicalRetriever2", "ISensor"));
					ops.add(new ConnectOperation("gloveSensor", "wekaReasoner", "ISensor"));
					ops.add(new ConnectOperation("wekaReasoner", "reasoningSensor2", "IProcess"));
					ops.add(new ConnectOperation("logicalRetriever2", "reasoningSensor2", "IRetrieveStrategy"));
					ops.add(new SetupComponentOperation("reasoningSensor2"));
					ops.add(new SetupComponentOperation("wekaReasoner"));
					
					ops.add(new CreateComponentInstanceOperation("vWeka", JVisualizer.class));
					ops.add(new CreatePortInstanceOperation("vsWeka", ClassificationVisualisationStrategy.class));
					ops.add(new BindPortOperation("vWeka", "vsWeka", "IVisualisationStrategy"));
					ops.add(new SetupComponentOperation("vWeka"));
					ops.add(new SetupPortOperation("vsWeka"));
					ops.add(new ConnectOperation("reasoningSensor2", "vWeka", "ISensor"));
	
					ReconfigurationScript rs = new ReconfigurationScript(ops);
	
					re.getReconfigurationEngine().executeScript(rs);
	
					ISensor sensor = (ISensor) re.getRuntimeModel().getComponentByName("reasoningSensor2");
					ClassificationVisualisationStrategy vs = (ClassificationVisualisationStrategy) re.getRuntimeModel()
							.getPortByName("vsWeka");
					vs.getUiHelper().setSensor(sensor);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


		Button btn3 = new Button();
		btn3.setText("Switch to SVM Reasoning");
		btn3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					RuntimeEnvironment re = RuntimeEnvironment.instance();
				

					List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();
					
					ops.add(new CreatePortInstanceOperation("svmReasoner", JSvmReasoner.class));
					ops.add(new BindPortOperation("wekaReasoner", "svmReasoner", "ILearningProcess"));
					
					ReconfigurationScript rs = new ReconfigurationScript(ops);
	
					re.getReconfigurationEngine().executeScript(rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		
		box.getChildren().add(btn1);
		box.getChildren().add(btn2);
		box.getChildren().add(btn3);
		
		Scene scene = new Scene(box, 200, 200);
		this.stage = stage;
		this.stage.setTitle("SVA - ManagementConsole");
		this.stage.setScene(scene);
		this.stage.show();
	}

}