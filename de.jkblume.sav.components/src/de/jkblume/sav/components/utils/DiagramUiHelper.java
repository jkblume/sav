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

	private Map<String, LineChart<?, ?>> charts = new HashMap<>();
	private Stage stage;

	private VBox chartBox;

	private NumberAxis xAxis;
	

	public void destroy(String vsName) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				stage.close();
			}
		});
	}
	
	public void addChart(String sensorId) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (stage == null) {
					stage = new Stage();
					chartBox = new VBox(8);
					Scene scene = new Scene(chartBox, 800, 600);
					stage.setScene(scene);
					stage.show();
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

	public void addPoint(String sensorId, Event event) {
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

	private double getValue(Event event) {
		DataComponent dataComponent = event.getPropertyList().get("flex");
		return ((Quantity) dataComponent).getValue();
	}

	private double getTimestamp(Event event) {
		TimeInstant timeInstant = (TimeInstant) event.getTime();
		return timeInstant.getTimePosition().getDecimalValue();
	}

}
