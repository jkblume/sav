package de.jkblume.sav.testsuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.vast.data.TextImpl;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.components.components.JRegexProcess;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.Text;

public class JRegexProcessTest {

	
	@Test
	public void testCsvQueryExecute() throws FileNotFoundException, IOException {
		JRegexProcess processor = createProcessUnderTest();
		processor.setup();
		
		Text input = new TextImpl();
		input.setValue("X:-123123.1231	Y:12312399128	Z:8987.0912");
		
		IOPropertyList list = new IOPropertyList();
		list.add(input);
		
		IOPropertyList output = (IOPropertyList) processor.execute(list);
		
		DataComponent component = output.getComponent(0);
		assertTrue(component instanceof Quantity);
		assertEquals(-123123.1231, ((Quantity) component).getValue(), 0.0f);
	}
	
	private JRegexProcess createProcessUnderTest() throws FileNotFoundException, IOException {
        AbstractProcess description = parseDescription("res-test/regex_process.xml");
        
        JRegexProcess process = new JRegexProcess("name");
        process.setSmlConfiguration(description);
        return process;
	}
	
	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        SMLUtils utils = new SMLUtils("");
        AbstractProcess process = utils.readProcess(is);
        return process;
	}
	
}
