import "platform:/plugin/org.qualitune.smags.architecture.dsl/resources/StandardTypes.madl";
import "platform:/plugin/org.qualitune.smags.platform.dsl/resources/Java7Platform.pdl";
import "../../de.jkblume.sav.architecture/models/architecture.madl";
import "../../de.jkblume.sav.architecture/models/architecture.app";
import "../../de.jkblume.sav.sensorml/models/sensorml.app";
import "../../de.jkblume.sav.sensorml/models/sensorml.madl";


namespace "de.jkblume.sav.components";

app SavComponents {
		platform: Java7 with mappings {
		Category maps to "Category" ["net.opengis.swe.v20"]
		AbstractSWEIdentifiable maps to "AbstractSWEIdentifiable" ["net.opengis.swe.v20"]
		AbstractProcess maps to "AbstractProcess" ["net.opengis.sensorml.v20"]
		AbstractPhysicalProcess maps to "AbstractPhysicalProcess" ["net.opengis.sensorml.v20"]
	}

	ports:
	implementation port JAggregateProcessOperator implements IOrchestratorProcess{}
	implementation port CSVQueryProcessor implements IProcess{}
	implementation port DifferenceProcessor implements IProcess{}
	implementation port SysoVisualisationStrategy implements IVisualisationStrategy{}
	implementation port JavaFXVisualisationStrategy implements IVisualisationStrategy{}
	
	components:
	component SerialTechnicalSensor implements TechnicalSensor{}
	component SimulatingTechnicalSensor implements TechnicalSensor{}
	
	sensors:

	initialization{
		port vs = new JavaFXVisualisationStrategy;
		component v = new JVisualizer {
			bind port vs to offer IVisualisationStrategy;	
		};
		
	}
}