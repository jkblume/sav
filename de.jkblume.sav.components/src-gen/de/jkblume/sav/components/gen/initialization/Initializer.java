
package de.jkblume.sav.components.gen.initialization;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.architecture.components.*;

import de.jkblume.sav.components.components.*;

import de.jkblume.sav.components.ports.*;

import de.jkblume.sav.architecture.gen.porttypes.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.lang.Class;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import org.smags.runtime.RuntimeEnvironment;
import org.smags.runtime.reconfigurtion.operations.*;
import org.smags.runtime.reconfigurtion.ReconfigurationScript;
import org.smags.runtime.reconfigurtion.ReconfigurtionOperation;
import java.util.ArrayList;
import java.util.List;

public class Initializer {

	public static final String V = "v";

	public static void initialize(RuntimeEnvironment re) {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		ops.add(new CreatePortInstanceOperation("vs", JavaFXVisualisationStrategy.class));

		ops.add(new CreateComponentInstanceOperation("v", JVisualizer.class));

		ops.add(new BindPortOperation("v", "vs", "IVisualisationStrategy"));

		ops.add(new SetupPortOperation("vs"));

		ops.add(new SetupComponentOperation("v"));

		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));
	}

	public static JVisualizer getV() {
		return RuntimeEnvironment.instance().getRuntimeModel().getComponentByName(Initializer.V).as(JVisualizer.class);
	}

}
