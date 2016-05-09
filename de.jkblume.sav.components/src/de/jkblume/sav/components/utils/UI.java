package de.jkblume.sav.components.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;

public class UI extends Application {

	private static Map<String, LineChart<?, ?>> charts = new HashMap<>();
	private static Map<String, Stage> stages = new HashMap<>();
	private static Stage primaryStage;

	private static VBox chartBox;

	private static double firstTimestamp = 0;
	
	public static void initialize() {
		if  (primaryStage == null) {
			Application.launch(UI.class, new String[] {});
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
	}

	public static void addChart(String vsName, String sensorId) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Stage targetStage = stages.get(vsName);
				if (targetStage == null) {
					targetStage = new Stage();
					chartBox = new VBox(8);
					Scene scene = new Scene(chartBox, 800, 600);
					targetStage.setScene(scene);
					targetStage.show();
				}
				
				NumberAxis xAxis = new NumberAxis();
				NumberAxis yAxis = new NumberAxis();
				xAxis.setLabel("Number of Month");
				// creating the chart
				LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
				lineChart.setTitle(sensorId);
				XYChart.Series series = new XYChart.Series();
				lineChart.getData().add(series);

				chartBox.getChildren().add(lineChart);
				charts.put(sensorId, lineChart);
			}
		});
	}

	public static void addPoint(String sensorId, Event event) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (firstTimestamp == 0.0) {
					firstTimestamp = getTimestamp(event);
				}
				
				LineChart chart = charts.get(sensorId);

				double value = getValue(event);
				double timestamp = getTimestamp(event) - firstTimestamp;
				System.out.println("Retrieved " +value);
				XYChart.Data point = new XYChart.Data(timestamp, value);

				Object object = chart.getData().get(0);
				((XYChart.Series) object).getData().add(point);
			}
		});
	}

	private static double getValue(Event event) {
		DataComponent dataComponent = event.getPropertyList().get(0);
		return ((Quantity) dataComponent).getValue();
	}

	private static double getTimestamp(Event event) {
		TimeInstant timeInstant = (TimeInstant) event.getTime();
		return timeInstant.getTimePosition().getDecimalValue();
	}

}
