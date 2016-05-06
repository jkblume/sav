
package de.jkblume.sav.architecture.gen.initialization;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.components.*;

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
import org.smags.runtime.RuntimeEnvironment;
import org.smags.runtime.reconfigurtion.operations.*;
import org.smags.runtime.reconfigurtion.ReconfigurationScript;
import org.smags.runtime.reconfigurtion.ReconfigurtionOperation;
import java.util.ArrayList;
import java.util.List;

public class Initializer {

	public static void initialize(RuntimeEnvironment re) {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));
	}

}
