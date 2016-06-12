package de.jkblume.sav.testsuite;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.mockito.Mockito;
import org.vast.data.CategoryImpl;
import org.vast.data.DataRecordImpl;
import org.vast.data.QuantityImpl;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.components.JSensor;
import de.jkblume.sav.components.components.JWekaLearningReasoner;
import de.jkblume.sav.components.utils.MySMLUtils;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;

public class JWekaReasonerTest {

	@Test
	public void test() throws FileNotFoundException, IOException {

		JWekaLearningReasoner reasoner = new JWekaLearningReasoner("reasoner");
		reasoner.setSmlConfiguration(parseDescription("res-test/j_reasoner.xml"));

	    JSensor sensor1 = new JSensor("sensor1");
	    IProcess process1 = Mockito.mock(IProcess.class);
	    AbstractProcess description1 = parseDescription("res-test/process1.xml");
	    when(process1.getSmlConfiguration()).thenReturn(description1);
	    sensor1.setIProcess(process1);
	    
	    reasoner.addISensor(sensor1);
	    
	    reasoner.setup();
	    
		for (int i = 0; i <= 10; i++) {
			sensor1.setLastEvent(MySMLUtils.createEvent(createSensorValue(i / 10.0)));
			Category clazz = new CategoryImpl();
			clazz.setValue("no");
			reasoner.teachCurrentState(clazz);
		}

		for (int i = 100; i <= 110; i++) {
			sensor1.setLastEvent(MySMLUtils.createEvent(createSensorValue(i / 10.0)));
			Category clazz = new CategoryImpl();
			clazz.setValue("yes");
			reasoner.teachCurrentState(clazz);
		}

	    reasoner.buildClassifier();
	    
		IOPropertyList input = createSensorValue(0);
		IOPropertyList execute1 = (IOPropertyList) reasoner.execute(input);
		assertEquals("no", ((Category) execute1.get(0)).getValue());
		input = createSensorValue(10);
		IOPropertyList execute2 = (IOPropertyList) reasoner.execute(input);
		assertEquals("yes", ((Category) execute2.get(0)).getValue());
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
