package de.jkblume.sav.components.ports.test;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.vast.data.TextImpl;
import org.vast.sensorML.SMLUtils;

import de.jkblume.sav.architecture.gen.porttypes.IOrchestratorProcess;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.components.ports.CSVQueryProcessor;
import de.jkblume.sav.components.ports.JAggregateProcessOperator;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.Text;

public class JAggregateProcessOperatorTest {

	@Test
	public void testExecuteMethod() throws FileNotFoundException, IOException {
		JAggregateProcessOperator operator = createAggregateProcess("res-test/aggregate_process.xml");
		
		AggregateProcess aggregateProcess = (AggregateProcess) operator.getSmlConfiguration();
		for (AbstractProcess description : aggregateProcess.getComponentList()) {
			CSVQueryProcessor processor = createCsvProcess(description);
			processor.setup();
			operator.addIProcess(processor);
		}
		
		operator.setup();
		
		Text input = new TextImpl();
		input.setValue("1;2;3/4;5;6");
		
		IOPropertyList list = new IOPropertyList();
		list.add(input);
		
		IOPropertyList output = operator.execute(list);
		assertEquals("The size of the output list is wrong", 2, output.size());
		
		DataComponent partialOutput = output.getComponent(0);
				
		DataComponent x = partialOutput.getComponent("x");
		assertEquals("The x value is not processed correctly. ",1.0, ((Quantity) x).getValue(), 0);
		DataComponent y = partialOutput.getComponent("y");
		assertEquals("The y value is not processed correctly. ",2.0, ((Quantity) y).getValue(), 0);
		DataComponent z = partialOutput.getComponent("z");
		assertEquals("The z value is not processed correctly. ",3.0, ((Quantity) z).getValue(), 0);
		

		partialOutput = output.getComponent(1);
		
		x = partialOutput.getComponent("x");
		assertEquals("The x value is not processed correctly. ",4.0, ((Quantity) x).getValue(), 0);
		y = partialOutput.getComponent("y");
		assertEquals("The y value is not processed correctly. ",5.0, ((Quantity) y).getValue(), 0);
		z = partialOutput.getComponent("z");
		assertEquals("The z value is not processed correctly. ",6.0, ((Quantity) z).getValue(), 0);
	}
	
	private JAggregateProcessOperator createAggregateProcess(String fileName) throws FileNotFoundException, IOException {
        AbstractProcess process = parseDescription(fileName);
        System.out.println(((AggregateProcess) process).getConnectionList().size());
        JAggregateProcessOperator operator = new JAggregateProcessOperator(fileName);
        IOrchestratorProcess base = new TestOrchestrationBase();
        operator.setBase(base);
        operator.setSmlConfiguration(process);
        return operator;
	}
	
	private CSVQueryProcessor createCsvProcess(AbstractProcess description) throws FileNotFoundException, IOException {
		CSVQueryProcessor processor = new CSVQueryProcessor("name");
        IProcess base = new TestCsvProcessorBase();
        processor.setBase(base);
        processor.setSmlConfiguration(description);
        return processor;
	}
	
	private AbstractProcess parseDescription(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);
        SMLUtils utils = new SMLUtils("");
        AbstractProcess process = utils.readProcess(is);
        return process;
	}
	
	private class TestOrchestrationBase implements IOrchestratorProcess {
		
		private AbstractProcess process;
		
		@Override
		public IOPropertyList execute(IOPropertyList value) {
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
	
	private class TestCsvProcessorBase implements IProcess {
		
		private AbstractProcess process;
		
		@Override
		public IOPropertyList execute(IOPropertyList value) {
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
