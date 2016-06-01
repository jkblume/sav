package de.jkblume.sav.components.utils;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainUI extends Application {
	
	public static Stage rootStage;

	public static void initialize() {
		Application.launch(MainUI.class, new String[] {});
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		rootStage = stage;
	}


}
