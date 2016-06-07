package de.jkblume.sav.testsuite;


import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.vast.data.TextImpl;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.components.components.JAggregateProcess;
import de.jkblume.sav.components.components.JRegexProcess;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.Text;

public class JAggregateProcessTest {

	@Test
	public void testExecuteMethod() throws FileNotFoundException, IOException {
		JAggregateProcess process = createAggregateProcess("res-test/aggregate_process.xml");
		
		AggregateProcess aggregateProcess = (AggregateProcess) process.getSmlConfiguration();
		for (AbstractProcess description : aggregateProcess.getComponentList()) {
			JRegexProcess processor = createRegexProcess(description);
			processor.setup();
			process.addIProcess(processor);
		}
		
		process.setup();
		
		Text input = new TextImpl();
		input.setValue("A: X:1.1Y:2.2Z:3.3 ! B: X:4.4Y:5.5Z:6.6 !");
		
		IOPropertyList list = new IOPropertyList();
		list.add(input);
		
		IOPropertyList output = (IOPropertyList) process.execute(list);
		assertEquals("The size of the output list is wrong", 2, output.size());
		
		DataComponent partialOutput = output.getComponent(0);
		
		DataComponent x = partialOutput.getComponent("x");		
		assertEquals("The x value is not processed correctly. ",1.1, ((Quantity) x).getValue(), 0);
		DataComponent y = partialOutput.getComponent("y");
		assertEquals("The y value is not processed correctly. ",2.2, ((Quantity) y).getValue(), 0);
		DataComponent z = partialOutput.getComponent("z");
		assertEquals("The z value is not processed correctly. ",3.3, ((Quantity) z).getValue(), 0);
		

		partialOutput = output.getComponent(1);
		
		x = partialOutput.getComponent("x");
		assertEquals("The x value is not processed correctly. ",4.4, ((Quantity) x).getValue(), 0);
		y = partialOutput.getComponent("y");
		assertEquals("The y value is not processed correctly. ",5.5, ((Quantity) y).getValue(), 0);
		z = partialOutput.getComponent("z");
		assertEquals("The z value is not processed correctly. ",6.6, ((Quantity) z).getValue(), 0);
	}
	
	private JAggregateProcess createAggregateProcess(String fileName) throws FileNotFoundException, IOException {
        AbstractProcess description = parseDescription(fileName);
        JAggregateProcess process = new JAggregateProcess(fileName);
        process.setSmlConfiguration(description);
        return process;
	}
	
	private JRegexProcess createRegexProcess(AbstractProcess description) throws FileNotFoundException, IOException {
		JRegexProcess process = new JRegexProcess("name");
        process.setSmlConfiguration(description);
        return process;
	}
	
	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        SMLUtils utils = new SMLUtils(SMLUtils.V2_0);
        AbstractProcess process = utils.readProcess(is);
        return process;
	}
		
}
