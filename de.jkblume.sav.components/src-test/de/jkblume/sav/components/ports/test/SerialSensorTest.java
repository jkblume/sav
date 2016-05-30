package de.jkblume.sav.components.ports.test;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.smags.componentmodel.IComponent;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.architecture.components.JTechnicalSensor;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.ports.SerialTechnicalSensor;
import net.opengis.sensorml.v20.AbstractProcess;

public class SerialSensorTest {

	@Test
	public void testSerialSensorConnect() throws FileNotFoundException, IOException, InterruptedException {
		SerialTechnicalSensor technicalSensor = createSensorUnderTest();
				
		assertTrue(technicalSensor.initialize());
	}
	
	private SerialTechnicalSensor createSensorUnderTest() throws FileNotFoundException, IOException {
        AbstractProcess process = parseDescription("res-test/serial_sensor.xml");
        
        SerialTechnicalSensor sensor = new SerialTechnicalSensor("name");
        ISensor base = new JTechnicalSensor("name");
        sensor.setBase(base);
        sensor.setSmlConfiguration(process);
        return sensor;
	}
	
	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        SMLUtils utils = new SMLUtils(SMLUtils.V2_0);
        AbstractProcess process = utils.readProcess(is);
        return process;
	}
		
}
