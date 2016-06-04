package de.jkblume.sav.components.utils;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.porttypes.ILearningReasoner;
import de.jkblume.sav.architecture.gen.porttypes.IReasoner;
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

	private static VBox box;
	private static Label state;
	
	public static IReasoner reasoner;
	
	private static Stage stage;
	
	public static void destroy() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				stage.close();
			}
		});
	}
	
	public static void initialize() {
		
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
				
				Button startClassification = new Button();
				startClassification.setText("Start classification...");
				startClassification.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						reasoner.start();
					}
				});
				
				box.getChildren().add(startClassification);
				
				if (reasoner instanceof ILearningReasoner) {
					
					Button yes = new Button();
					yes.setText("This is A gesture!");
					yes.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							Category category = new CategoryImpl();
							category.setValue("yes");
							((ILearningReasoner) reasoner).teachCurrentState(category);
						}
					});
					
					Button no= new Button();
					no.setText("This is NO gesture!");
					no.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							Category category = new CategoryImpl();
							category.setValue("no");
							((ILearningReasoner) reasoner).teachCurrentState(category);
						}
					});
					
					box.getChildren().add(yes);
					box.getChildren().add(no);
				}
				
			}
		});
	}
	
	public static void updateLabel(String value) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (state != null) {
					state.setText(value);
				}
			}
		});
	}
}
