package de.jkblume.sav.testsuite;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.vast.data.QuantityImpl;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.components.components.JNumericMapIntervalProcess;
import de.jkblume.sav.components.components.JRegexProcess;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Quantity;

public class JNumericMapIntervalProcessTest {
	
	@Test
	public void testExecute() throws FileNotFoundException, IOException {
		JNumericMapIntervalProcess process = new JNumericMapIntervalProcess("test");
		process.setSmlConfiguration(parseDescription("res-test/map_process.xml"));
		process.setup();
		
		IOPropertyList input = new IOPropertyList();
		Quantity partialInput = new QuantityImpl();
		partialInput.setValue(15.0);
		input.add("flex", partialInput);
		
		Object execute = process.execute(input);
		
		assertTrue(execute instanceof IOPropertyList);
		
		AbstractSWEIdentifiable component = ((IOPropertyList)execute).get("flex");
		assertTrue(component instanceof Quantity);
		
		assertEquals(115.0, ((Quantity) component).getValue(), 0);
	}
	
	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        SMLUtils utils = new SMLUtils("");
        AbstractProcess process = utils.readProcess(is);
        return process;
	}
	
}
