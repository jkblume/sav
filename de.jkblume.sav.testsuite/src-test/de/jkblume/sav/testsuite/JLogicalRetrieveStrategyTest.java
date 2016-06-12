package de.jkblume.sav.testsuite;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;
import org.vast.data.DataRecordImpl;
import org.vast.data.QuantityImpl;

import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.components.components.JLogicalRetrieveStrategy;
import de.jkblume.sav.components.utils.MySMLUtils;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;

public class JLogicalRetrieveStrategyTest {

	private ISensor sensor1, sensor2;
	
	@Test
	public void testRetrieveValue() {
		setupMocks();
	    
		JLogicalRetrieveStrategy strategy = new JLogicalRetrieveStrategy("strategy");
		strategy.addISensor(sensor1);
		strategy.addISensor(sensor2);
		
		Object result = strategy.retrieveValue();
		
		assertTrue(result instanceof IOPropertyList);
		
		IOPropertyList castedResult = (IOPropertyList) result;
		assertEquals(2, castedResult.size());
		
		assertTrue(castedResult.get("acceleration") instanceof DataRecord);
		assertEquals(4.0, ((Quantity) castedResult.get("f")).getValue(), 0.0);
	}

	private void setupMocks() {
		IOPropertyList list1 = new IOPropertyList();
		DataRecord record1 = new DataRecordImpl();
		Quantity x = new QuantityImpl();
		x.setValue(1.0);
		Quantity y = new QuantityImpl();
		x.setValue(2.0);
		Quantity z = new QuantityImpl();
		x.setValue(3.0);
		record1.addComponent("x", x);
		record1.addComponent("y", y);
		record1.addComponent("z", z);
		list1.add("acceleration", record1);
		
		IOPropertyList list2 = new IOPropertyList();
		Quantity f = new QuantityImpl();
		f.setValue(4.0);
		list2.add("f", f);
		
	    sensor1 = Mockito.mock(ISensor.class);
	    when(sensor1.getLastEvent()).thenReturn(MySMLUtils.createEvent(list1));
	    sensor2 = Mockito.mock(ISensor.class);
	    when(sensor2.getLastEvent()).thenReturn(MySMLUtils.createEvent(list2));
	}
	
}
