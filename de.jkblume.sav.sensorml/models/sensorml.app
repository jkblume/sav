import "platform:/plugin/org.qualitune.smags.architecture.dsl/resources/StandardTypes.madl";
import "platform:/plugin/org.qualitune.smags.platform.dsl/resources/Java7Platform.pdl";
import "sensorml.madl";

namespace "de.jkblume.sav.sensorml";

app SavSensorML {
	platform: Java7 with mappings { 
		AbstractSWEIdentifiable maps to "AbstractSWEIdentifiable" ["net.opengis.swe.v20"]
		DataComponent maps to "DataComponent" ["net.opengis.swe.v20"]
		Category maps to "Category" ["net.opengis.swe.v20"]
		AbstractProcess maps to "AbstractProcess" ["net.opengis.sensorml.v20"]
		AbstractPhysicalProcess maps to "AbstractPhysicalProcess" ["net.opengis.sensorml.v20"]
		SimpleProcess maps to "SimpleProcess" ["net.opengis.sensorml.v20"]
		PhysicalComponent maps to "PhysicalComponent" ["net.opengis.sensorml.v20"]
		Event maps to "Event" ["net.opengis.sensorml.v20"]
		IOPropertyList maps to "IOPropertyList" ["net.opengis.sensorml.v20"]
	
	}
	

	ports:

	components:

	sensors:

	initialization{
	}
}