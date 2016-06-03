package de.jkblume.sav.components.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.opengis.gml.v32.TimeInstant;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;

public class DiagramUiHelper {

	private static Map<String, LineChart<?, ?>> charts = new HashMap<>();
	private static Map<String, Stage> stages = new HashMap<>();

	private static VBox chartBox;

	private static NumberAxis xAxis;

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
				
				xAxis = new NumberAxis();
				xAxis.setAutoRanging(false);
				
				NumberAxis yAxis = new NumberAxis();
				yAxis.setAutoRanging(false);
				yAxis.setLowerBound(560);
				yAxis.setUpperBound(1000);
				
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
				
				LineChart chart = charts.get(sensorId);

				double value = getValue(event);
				double timestamp = getTimestamp(event);
				
				if (xAxis != null) {
					xAxis.setLowerBound(timestamp - 10000);
					xAxis.setUpperBound(timestamp + 10000);
					xAxis.setTickUnit(100 / 20000);
				}
				
				XYChart.Data point = new XYChart.Data(timestamp, value);

				Object object = chart.getData().get(0);
				((XYChart.Series) object).getData().add(point);
			}
		});
	}

	private static double getValue(Event event) {
		DataComponent dataComponent = event.getPropertyList().get("flex");
		return ((Quantity) dataComponent).getValue();
	}

	private static double getTimestamp(Event event) {
		TimeInstant timeInstant = (TimeInstant) event.getTime();
		return timeInstant.getTimePosition().getDecimalValue();
	}

}
