
package de.jkblume.sav.prototype.gen.initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.smags.runtime.RuntimeEnvironment;
import org.smags.runtime.reconfigurtion.ReconfigurationScript;
import org.smags.runtime.reconfigurtion.ReconfigurtionOperation;

public class Initializer {

	public static void initialize(RuntimeEnvironment re) {
		List<ReconfigurtionOperation> ops = new ArrayList<ReconfigurtionOperation>();

		re.getReconfigurationEngine().executeScript(new ReconfigurationScript(ops));
	}

}
