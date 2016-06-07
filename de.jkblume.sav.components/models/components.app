import "platform:/plugin/org.qualitune.smags.architecture.dsl/resources/StandardTypes.madl";
import "platform:/plugin/org.qualitune.smags.platform.dsl/resources/Java7Platform.pdl";
import "components.madl";
import "../../de.jkblume.sav.architecture/models/architecture.madl";
import "../../de.jkblume.sav.sensorml/models/sensorml.app";
import "../../de.jkblume.sav.sensorml/models/sensorml.madl";


namespace "de.jkblume.sav.components";

app SavComponents {
	platform: Java7 with mappings {
		// sensorml specific
		Event maps to "Event" ["net.opengis.sensorml.v20"]
		Category maps to "Category" ["net.opengis.swe.v20"]
		DataComponent maps to "DataComponent" ["net.opengis.swe.v20"]
		AbstractProcess maps to "AbstractProcess" ["net.opengis.sensorml.v20"]
		AbstractPhysicalProcess maps to "AbstractPhysicalProcess" ["net.opengis.sensorml.v20"]
		AbstractSWEIdentifiable maps to "AbstractSWEIdentifiable" ["net.opengis.swe.v20"]
		IOPropertyList maps to "IOPropertyList" ["net.opengis.sensorml.v20"]
		
		// weka specific
		Instances maps to "Instances" ["weka.core"]
		Classifier maps to "Classifier" ["weka.classifiers"]
	}

	ports:
	implementation port DiagramVisualisationStrategy implements IVisualisationStrategy{}
	implementation port ClassificationVisualisationStrategy implements IVisualisationStrategy{}
	implementation port Cube3DVisualisationStrategy implements IVisualisationStrategy{}
	implementation port JSvmReasoner implements ILearningProcess{}
	
	components:
	component JSensor implements Sensor{}
	component JAggregateProcess implements AggregateProcess{}
	component JRegexProcess implements SimpleProcess{}
	component JSerialPortRetrieveStrategy implements TechnicalRetrieveStrategy{}
	component JSimpleRuleReasoner implements SimpleProcess{}
	component JWekaLearningReasoner implements LearningProcess{
		parameter instances : Instances;
		parameter classifier : Classifier;
	}	
	component JVisualizer implements Visualizer{}
	
	sensors:

	initialization{
	}
}