package de.jkblume.sav.components.utils;

import java.util.Collection;

import net.opengis.gml.v32.impl.TimeInstantImpl;
import net.opengis.gml.v32.impl.TimePositionImpl;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.sensorml.v20.impl.EventImpl;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.DataComponent;

public class MySMLUtils {

	public static Event createEvent(IOPropertyList values) {
		Event event = new EventImpl();
		
		event.getPropertyList().addAll((Collection<? extends DataComponent>) values);
		
		TimeInstantImpl timeInstant = new TimeInstantImpl();
		TimePositionImpl timePosition = new TimePositionImpl();
		timePosition.setDecimalValue(System.currentTimeMillis());
		timeInstant.setTimePosition(timePosition);
		event.setTimeAsTimeInstant(timeInstant);
		return event;
	}
	
}
