package de.jkblume.sav.components.utils;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.components.AbstractSensor;
import de.jkblume.sav.architecture.gen.porttypes.ILearningProcess;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.opengis.swe.v20.Category;

public class ClassifyUiHelper {

	private VBox box;
	private Label state;
	
	private ISensor sensor;
	private IProcess reasonerProcess;
	
	private Stage stage;
	
	public void destroy() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				stage.close();
			}
		});
	}
	
	public void initialize() {
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				stage = new Stage();
				box = new VBox(8);
				Scene scene = new Scene(box, 400, 200);
				stage.setScene(scene);
				stage.show();

				
				state = new Label();
				state.setFont(new Font("Arial", 30));
				state.setText("Not yet classified");
				
				box.getChildren().add(state);
				
				Button startReasoner = new Button();
				startReasoner.setText("Start reasoner...");
				startReasoner.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						sensor.start();
					}
				});
				
				box.getChildren().add(startReasoner);
							
				if (reasonerProcess instanceof ILearningProcess) {
					ILearningProcess learningProcess = (ILearningProcess) reasonerProcess;

					Button yes = new Button();
					yes.setText("This is A gesture!");
					yes.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							Category category = new CategoryImpl();
							category.setValue("yes");
							learningProcess.teachCurrentState(category);
						}
					});
					
					Button buildClassifier = new Button();
					buildClassifier.setText("Build classifier...");
					buildClassifier.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							learningProcess.buildClassifier();
						}
					});
					
					box.getChildren().add(buildClassifier);
					
					
					Button no= new Button();
					no.setText("This is NO gesture!");
					no.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							Category category = new CategoryImpl();
							category.setValue("no");
							learningProcess.teachCurrentState(category);
						}
					});
					
					box.getChildren().add(yes);
					box.getChildren().add(no);
				}
				
			}
		});
	}
	
	public void updateLabel(String value) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (state != null) {
					state.setText(value);
				}
			}
		});
	}

	public void setSensor(ISensor sensor) {
		this.sensor = sensor;
		if (sensor instanceof AbstractSensor) {
			this.reasonerProcess = ((AbstractSensor) sensor).getIProcess();
		}
	}
}
