package de.jkblume.sav.prototype.ui;
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
import org.smags.runtime.reconfigurtion.operations.DisconnectOperation;
import org.smags.runtime.reconfigurtion.operations.RemoveComponentOperation;
import org.smags.runtime.reconfigurtion.operations.RemovePortOperation;
import org.smags.runtime.reconfigurtion.operations.SetPortParameterOperation;
import org.smags.runtime.reconfigurtion.operations.SetupComponentOperation;
import org.smags.runtime.reconfigurtion.operations.SetupPortOperation;
import org.vast.sensorML.SMLUtils;
import org.vast.xml.XMLReaderException;

import de.jkblume.sav.architecture.components.JLearningReasoner;
import de.jkblume.sav.architecture.components.JSpecificationReasoner;
import de.jkblume.sav.architecture.components.JVisualizer;
import de.jkblume.sav.architecture.gen.porttypes.IReasoner;
import de.jkblume.sav.components.ports.ClassificationVisualisationStrategy;
import de.jkblume.sav.components.ports.NaiveBayesReasoner;
import de.jkblume.sav.components.ports.SimpleRuleReasoner;
import de.jkblume.sav.components.utils.ClassifyUiHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.opengis.sensorml.v20.AbstractProcess;

public class UI extends Application{
	
		private Stage stage;
		

		private SMLUtils utils = new SMLUtils(SMLUtils.V2_0);
		
		@Override
		public void start(Stage stage) throws Exception {
	        VBox box = new VBox();
	        Button btn1 = new Button();
	        btn1.setText("Enable Specification Reasoning");
	        btn1.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					RuntimeEnvironment re = RuntimeEnvironment.instance();
					
					InputStream is = null;
					try {
						is = new FileInputStream("res/glove_reasoner.xml");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					AbstractProcess gloveReasonerDesription = null;
					try {
						gloveReasonerDesription = utils.readProcess(is);
					} catch (XMLReaderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

					ops.add(new CreateComponentInstanceOperation("r1", JSpecificationReasoner.class));
					ops.add(new CreatePortInstanceOperation("rs1", SimpleRuleReasoner.class));
					ops.add(new BindPortOperation("r1", "rs1", "ISpecificationReasoner"));
					ops.add(new SetPortParameterOperation("rs1", "smlConfiguration", gloveReasonerDesription));
					ops.add(new ConnectOperation("jtps1", "r1", "ISensor"));
					ops.add(new ConnectOperation("jtps1", "rs1", "ISensor"));
					ops.add(new SetupComponentOperation("r1"));
					
					ops.add(new CreateComponentInstanceOperation("v4", JVisualizer.class));
					ops.add(new CreatePortInstanceOperation("vs4", ClassificationVisualisationStrategy.class));
					ops.add(new BindPortOperation("v4", "vs4", "IVisualisationStrategy"));
					ops.add(new SetupComponentOperation("v4"));
					ops.add(new SetupPortOperation("vs4"));
					ops.add(new ConnectOperation("r1", "v4", "ISensor"));
					
					ReconfigurationScript rs = new ReconfigurationScript(ops);
					
					re.getReconfigurationEngine().executeScript(rs);
					
					IReasoner reasoner = (IReasoner) re.getRuntimeModel().getComponentByName("r1");
					ClassifyUiHelper.reasoner = reasoner;
				}
			});
	        
	        Button btn2 = new Button();
	        btn2.setText("Enable Learning Reasoning");
	        btn2.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					RuntimeEnvironment re = RuntimeEnvironment.instance();
					
					InputStream is = null;
					try {
						is = new FileInputStream("res/glove_reasoner.xml");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					AbstractProcess gloveReasonerDesription = null;
					try {
						gloveReasonerDesription = utils.readProcess(is);
					} catch (XMLReaderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();
					
					ops.add(new DisconnectOperation("jtps1", "r1", "ISensor"));
					ops.add(new DisconnectOperation("jtps1", "rs1", "ISensor"));
					ops.add(new RemoveComponentOperation("r1"));
					ops.add(new RemoveComponentOperation("v4"));
					ops.add(new RemovePortOperation("vs4"));
					
					ops.add(new CreateComponentInstanceOperation("r2", JLearningReasoner.class));
					ops.add(new CreatePortInstanceOperation("rs2", NaiveBayesReasoner.class));
					ops.add(new BindPortOperation("r2", "rs2", "ILearningReasoner"));
					ops.add(new SetPortParameterOperation("rs2", "smlConfiguration", gloveReasonerDesription));
					ops.add(new ConnectOperation("jtps1", "r2", "ISensor"));
					ops.add(new ConnectOperation("jtps1", "rs2", "ISensor"));
					ops.add(new SetupComponentOperation("r2"));
					
					ops.add(new CreateComponentInstanceOperation("v5", JVisualizer.class));
					ops.add(new CreatePortInstanceOperation("vs5", ClassificationVisualisationStrategy.class));
					ops.add(new BindPortOperation("v5", "vs5", "IVisualisationStrategy"));
					ops.add(new SetupComponentOperation("v5"));
					ops.add(new SetupPortOperation("vs5"));
					ops.add(new ConnectOperation("r2", "v5", "ISensor"));
					
					ReconfigurationScript rs = new ReconfigurationScript(ops);
					
					re.getReconfigurationEngine().executeScript(rs);
					
					IReasoner reasoner = (IReasoner) re.getRuntimeModel().getComponentByName("r2");
					ClassifyUiHelper.reasoner = reasoner;
					
				}
			});
			
	        box.getChildren().add(btn1);
	        box.getChildren().add(btn2); 	
	        
			Scene scene = new Scene(box, 200, 200);
	        this.stage = stage;
	        this.stage.setTitle("SVA - ManagementConsole");
	        this.stage.setScene(scene);
	        this.stage.show();
		}



		
	}