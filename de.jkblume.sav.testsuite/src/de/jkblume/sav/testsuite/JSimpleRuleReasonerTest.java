package de.jkblume.sav.testsuite;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.vast.data.QuantityImpl;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.components.components.JSimpleRuleReasoner;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.Quantity;

public class JSimpleRuleReasonerTest {

	@Test
	public void testExecute() throws FileNotFoundException, IOException {
		JSimpleRuleReasoner reasoner = new JSimpleRuleReasoner("test");
		reasoner.setSmlConfiguration(parseDescription("res-test/jsrr_process.xml"));
		reasoner.setup();
		
		IOPropertyList input1 = new IOPropertyList();
		Quantity partialInput1 = new QuantityImpl();
		partialInput1.setValue(109.0);
		input1.add("flex", partialInput1);
		
		IOPropertyList output1 = (IOPropertyList) reasoner.execute(input1);
		
		assertEquals("no", ((Category) output1.getComponent(0)).getValue());
	
		IOPropertyList input2 = new IOPropertyList();
		Quantity partialInput2 = new QuantityImpl();
		partialInput2.setValue(111.0);
		input2.add("flex", partialInput2);
		
		IOPropertyList output2 = (IOPropertyList) reasoner.execute(input2);
		
		assertEquals("yes", ((Category) output2.getComponent(0)).getValue());
	
	}
	
	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        SMLUtils utils = new SMLUtils("");
        AbstractProcess process = utils.readProcess(is);
        return process;
	}
	
	
}
