
package de.jkblume.sav.prototype.gen.initialization;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.architecture.components.*;

import de.jkblume.sav.architecture.gen.porttypes.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.lang.Class;
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
