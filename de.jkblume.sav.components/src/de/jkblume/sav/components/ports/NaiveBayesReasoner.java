
package de.jkblume.sav.components.ports;

import java.util.ArrayList;
import java.util.List;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.porttypes.ILogicalSensor;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import de.jkblume.sav.architecture.ports.JLogicalSensorPort;
import de.jkblume.sav.components.gen.ports.AbstractNaiveBayesReasoner;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.ScalarComponent;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class NaiveBayesReasoner extends AbstractNaiveBayesReasoner {

	public NaiveBayesReasoner(String name) {
		super(name);
	}

	public void setup() {
		//TODO: IMPLEMENT
	}

	public void destroy() {
		//TODO: IMPLEMENT
	}

	@Override
	public void startGesture(Category category) {
		throw new UnsupportedOperationException("The reasoner does not support dynamic gesture reasoning");
	}

	@Override
	public void stopGesture(Category category) {
		throw new UnsupportedOperationException("The reasoner does not support dynamic gesture reasoning");
	}

	@Override
	public void teachCurrentState(Category category) {
		IOPropertyList list = new IOPropertyList();
		JLogicalSensorPort logicalSensor = (JLogicalSensorPort) getILogicalSensor();
		for (ISensor sensor : logicalSensor.getISensors()) {
			list.addAll(sensor.getLastEvent().getPropertyList());
		}
		list.add(category);
		
		
		
		Instance labledInstance = inject(list);
		
		structure.add(labledInstance);
	}
	
	private Instances structure;
	private ArrayList<Attribute> features;
	private Classifier classifier;
	
	@Override
	public void buildClassifier() {
		classifier = new SMO();
		try {
			classifier.buildClassifier(structure);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addFeature(DataComponent component) {
		if (component instanceof DataRecord) {
			for (int i = 0; i < component.getComponentCount(); i++) {
				addFeature(component.getComponent(i));
			}
		} else {
			Attribute targetAttribute = null;
			if (component instanceof Quantity) {
				// FIXME: what if component.getName() == null
				targetAttribute = new Attribute(component.getName());
			}
			if (component instanceof Category) {
				List<String> valueList =  new ArrayList<String>();//((Category) component).getConstraint().getValueList();
				valueList.add("yes");
				valueList.add("no");
				//FIXME: Remove class and get right name
				targetAttribute = new Attribute("class", valueList);
			}
			features.add(targetAttribute);
		}
	}

	private Instance result;
	private List<ScalarComponent> values;
	
	private Instance inject(IOPropertyList input) {
		values = new ArrayList<>();
		for (AbstractSWEIdentifiable identifiable : input) {
			DataComponent component = (DataComponent) identifiable;
			addFeature1(component);
		}
		
		result = new DenseInstance(values.size());
		result.setDataset(structure);
		for (int i = 0; i < values.size(); i++) {
			ScalarComponent sComponent = values.get(i);
			if (sComponent.getDataType().isIntegralType() || sComponent.getDataType().isNumberType()) {
				result.setValue(i, sComponent.getData().getDoubleValue());
			}
			if (sComponent.getDataType().isTextType()) {
				result.setValue(i, sComponent.getData().getStringValue());
			}
		}
		return result;
	}
	
	private void addFeature1(DataComponent component) {
		DataBlock block = component.getData();
		if (block == null) {
			for (int i = 0; i < component.getComponentCount(); i++) {
				addFeature1(component.getComponent(i));
			}
		} else {
			values.add((ScalarComponent) component);
		}
	}

	@Override
	public Boolean initialize() {
		features = new ArrayList<>();
		JLogicalSensorPort logicalSensor = (JLogicalSensorPort) getILogicalSensor();
		for (ISensor sensor : logicalSensor.getISensors()) {
			IOPropertyList sensorOutput = sensor.retrieveOutputStructure();
			for (AbstractSWEIdentifiable identifiable : sensorOutput) {
				addFeature((DataComponent) identifiable);
			}
		}
		addFeature((Category) getSmlConfiguration().getOutputList().get(0));
		structure = new Instances(getSmlConfiguration().getId(), features, 0);
		structure.setClassIndex(structure.numAttributes()-1);
		return true;
	}
	
	public Boolean validateSmlConfiguration() {
		Boolean result = base.validateSmlConfiguration();
		return result;
	}


	@Override
	public Object execute(Object input) {
		Instance unlabeled = inject((IOPropertyList) input);
		
		double cls = 0;
		try {
			cls = classifier.classifyInstance(unlabeled);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Category classCategory = new CategoryImpl();
		classCategory.setValue(unlabeled.classAttribute().value((int) cls));

		IOPropertyList output = new IOPropertyList();
		output.add(classCategory);
		
		return output;
	}


	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

	@Override
	public void handleILogicalSensorConnected(ILogicalSensor item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleILogicalSensorDisconnected(ILogicalSensor item) {
		// TODO Auto-generated method stub
		
	}
}
