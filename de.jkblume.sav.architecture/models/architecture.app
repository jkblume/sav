import "platform:/plugin/org.qualitune.smags.architecture.dsl/resources/StandardTypes.madl";
import "platform:/plugin/org.qualitune.smags.platform.dsl/resources/Java7Platform.pdl";
import "architecture.madl";
import "../../de.jkblume.sav.sensorml/models/sensorml.madl";


namespace "de.jkblume.sav.architecture";

app SavArchitecture {
	platform: Java7 with mappings {
		Event maps to "Event" ["net.opengis.sensorml.v20"]
		Category maps to "Category" ["net.opengis.swe.v20"]
		DataComponent maps to "DataComponent" ["net.opengis.swe.v20"]
		AbstractProcess maps to "AbstractProcess" ["net.opengis.sensorml.v20"]
		AbstractPhysicalProcess maps to "AbstractPhysicalProcess" ["net.opengis.sensorml.v20"]
		AbstractSWEIdentifiable maps to "AbstractSWEIdentifiable" ["net.opengis.swe.v20"]
		IOPropertyList maps to "IOPropertyList" ["net.opengis.sensorml.v20"]
	}

	ports:
	

	components:
	component JLogicalPullSensor implements LogicalPullSensor{}
	component JLogicalPushSensor implements LogicalPushSensor{}
	component JTechnicalPullSensor implements TechnicalPullSensor{}
	component JTechnicalPushSensor implements TechnicalPushSensor{}
	
	component JVisualizer implements Visualizer{}
	
	sensors:

	initialization{
	}
}