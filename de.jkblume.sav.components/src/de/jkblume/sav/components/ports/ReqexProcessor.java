
package de.jkblume.sav.components.ports;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.architecture.gen.porttypes.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.lang.Class;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataComponent;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.IOPropertyList;

import de.jkblume.sav.architecture.gen.porttypes.*;

import de.jkblume.sav.components.gen.ports.*;
import org.smags.componentmodel.IPort;
import java.util.*;

public class ReqexProcessor extends AbstractReqexProcessor {

	public ReqexProcessor(String name) {
		super(name);
	}

	public void setup() {
		//TODO: IMPLEMENT
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	public Object execute(Object value) {
		Object result = base.execute(value);
		return result;
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

}
