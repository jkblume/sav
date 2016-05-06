package de.jkblume.sav.prototype.ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI extends Application{
	
		private Stage stage;
		
		@Override
		public void start(Stage stage) throws Exception {
	        VBox box = new VBox();
	        Button btn = new Button();
	        btn.setText("Say 'Hello World'");
			box.getChildren().add(btn); 	
	        
			Scene scene = new Scene(box, 600, 512);
	        this.stage = stage;
	        this.stage.setTitle("SVA - ManagementConsole");
	        this.stage.setScene(scene);
	        this.stage.show();
		}



		
	}