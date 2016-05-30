package de.jkblume.sav.components.ports.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.vast.data.TextImpl;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.components.ports.CsvProcessor;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Text;

public class CsvProcessorTest {

	@Test
	public void testCsvQueryDescriptionValidation() throws FileNotFoundException, IOException {
		CsvProcessor processor = createProcessUnderTest();
		
		assertTrue("Problem with inputs", processor.validateInputs());
		assertTrue("Problem with outputs", processor.validateOutputs());
		assertTrue("Problem with parameters", processor.validateParameters());
	}
	
	@Test
	public void testCsvQueryExecute() throws FileNotFoundException, IOException {
		CsvProcessor processor = createProcessUnderTest();
		processor.setup();
		
		Text input = new TextImpl();
		input.setValue("sensor;value");
		
		IOPropertyList list = new IOPropertyList();
		list.add(input);
		
		IOPropertyList output = (IOPropertyList) processor.execute(list);
		
		DataComponent component = output.getComponent(0);
		assertTrue(component instanceof Text);
		assertEquals("sensor", ((Text) component).getValue());
	}
	
	private CsvProcessor createProcessUnderTest() throws FileNotFoundException, IOException {
        AbstractProcess process = parseDescription("res-test/csv_process.xml");
        
        CsvProcessor operator = new CsvProcessor("name");
        IProcess base = new TestSimplePortBase();
        operator.setBase(base);
        operator.setSmlConfiguration(process);
        return operator;
	}
	
	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        SMLUtils utils = new SMLUtils("");
        AbstractProcess process = utils.readProcess(is);
        return process;
	}
	
	private class TestSimplePortBase implements IProcess {
		
		private AbstractProcess process;
		
		@Override
		public Object execute(Object value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AbstractProcess getSmlConfiguration() {
			return process;
		}

		@Override
		public void setSmlConfiguration(AbstractProcess processDescription) {
			this.process = processDescription;
		}

		@Override
		public <T> T as(Class<T> c) {
			// TODO Auto-generated method stub
			return null;
		}

		
	}
	
}
