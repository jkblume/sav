package de.jkblume.sav.components.utils;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.porttypes.ILearningReasonerProcess;
import de.jkblume.sav.architecture.gen.porttypes.IReasonerProcess;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.opengis.swe.v20.Category;

public class ClassifyUiHelper {

	private static VBox box;
	private static Label state;
	
	private static IReasonerProcess reasoner;
	
	
	public static void initialize() {
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (reasoner instanceof ILearningReasonerProcess) {
					
					Button yes = new Button();
					yes.setText("This is A gesture!");
					yes.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							Category category = new CategoryImpl();
							category.setValue("yes");
							((ILearningReasonerProcess) reasoner).teachCurrentState(category);
						}
					});
					
					Button no= new Button();
					no.setText("This is NO gesture!");
					no.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							Category category = new CategoryImpl();
							category.setValue("no");
							((ILearningReasonerProcess) reasoner).teachCurrentState(category);
						}
					});
					
					box.getChildren().add(yes);
					box.getChildren().add(no);
				}
				
				Stage targetStage = new Stage();
				box = new VBox(8);
				Scene scene = new Scene(box, 800, 600);
				targetStage.setScene(scene);
				targetStage.show();
				
				state = new Label();
				state.setText("Not yet classified");
				
				box.getChildren().add(state);
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
