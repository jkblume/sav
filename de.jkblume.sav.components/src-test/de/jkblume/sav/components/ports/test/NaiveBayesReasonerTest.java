package de.jkblume.sav.components.ports.test;


import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.vast.data.CategoryImpl;
import org.vast.data.DataRecordImpl;
import org.vast.data.QuantityImpl;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.architecture.components.JTechnicalSensor;
import de.jkblume.sav.architecture.utils.MySMLUtils;
import de.jkblume.sav.components.components.NaiveBayesReasoner;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;

public class NaiveBayesReasonerTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		
		NaiveBayesReasoner reasoner = new NaiveBayesReasoner("testReasoner");
		reasoner.setSmlConfiguration(parseDescription("res-test/j_reasoner.xml"));
		
		JTechnicalSensor sensor = new JTechnicalSensor("testSensor");
		sensor.setSmlConfiguration(parseDescription("res-test/glove_aggregator_process.xml"));

		reasoner.addISensor(sensor);		
		reasoner.buildClassifier();

		for (int i = 0; i <= 10; i++) {
			sensor.setLastEvent(MySMLUtils.createEvent(createSensorValue(i/10.0)));
			Category clazz = new CategoryImpl();
			clazz.setValue("no");
			reasoner.teachCurrentState(clazz);
		}
		
		for (int i = 100; i <= 110; i++) {
			sensor.setLastEvent(MySMLUtils.createEvent(createSensorValue(i/10.0)));
			Category clazz = new CategoryImpl();
			clazz.setValue("yes");
			reasoner.teachCurrentState(clazz);
		}
		
		IOPropertyList input = createSensorValue(0);
		assertEquals("no", reasoner.classify(input).getValue());
		input = createSensorValue(10);
		assertEquals("yes", reasoner.classify(input).getValue());
	}
	
	private IOPropertyList createSensorValue(double add) {
		IOPropertyList input = new IOPropertyList();
		DataRecord record = new DataRecordImpl();
		
		QuantityImpl xQuantity = new QuantityImpl();
		xQuantity.setValue(2.12 + add);
		record.addField("x", xQuantity);

		QuantityImpl yQuantity = new QuantityImpl();
		yQuantity.setValue(3.12 + add);
		record.addField("y", yQuantity);
		
		QuantityImpl zQuantity = new QuantityImpl();
		zQuantity.setValue(4.12 + add);
		record.addField("z", zQuantity);
		
		Quantity flexQuantity = new QuantityImpl();
		flexQuantity.setValue(10.001 + add);

		
		input.add("gyroscope", record);
		
		input.add("flex", flexQuantity);
		
		
		return input;
	}
	
	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        SMLUtils utils = new SMLUtils("");
        AbstractProcess process = utils.readProcess(is);
        return process;
	}
	
}
