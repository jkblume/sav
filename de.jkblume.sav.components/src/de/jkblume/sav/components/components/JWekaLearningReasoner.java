
package de.jkblume.sav.components.components;

import java.util.ArrayList;
import java.util.List;

import org.smags.componentmodel.annotations.ParameterA;
import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.gen.components.AbstractLearningProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.ScalarComponent;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class JWekaLearningReasoner extends AbstractLearningProcess {

	@ParameterA(name = "instances")
	private Instances instances;

	@ParameterA(name = "classifier")
	private Classifier classifier;

	public JWekaLearningReasoner(String name) {
		super(name);
	}

	public Instances getInstances() {
		return this.instances;
	}

	public void setInstances(Instances instances) {
		Instances oldValue = this.instances;
		this.instances = instances;
		notifyPropertyChanged(this, "instances", oldValue, instances);
	}
	public Classifier getClassifier() {
		return this.classifier;
	}

	public void setClassifier(Classifier classifier) {
		Classifier oldValue = this.classifier;
		this.classifier = classifier;
		notifyPropertyChanged(this, "classifier", oldValue, classifier);
	}

	public void setup() {
		features = new ArrayList<>();
		for (ISensor iSensor : getISensors()) {
			IOPropertyList sensorOutput = iSensor.retrieveOutputStructure();
			for (AbstractSWEIdentifiable identifiable : sensorOutput) {
				addFeature((DataComponent) identifiable);
			}
		}
		addFeature((Category) getSmlConfiguration().getOutputList().get(0));
		setInstances(new Instances(getSmlConfiguration().getId(), features, 0));
		getInstances().setClassIndex(getInstances().numAttributes()-1);
	}

	public void destroy() {
		//TODO:Implement
	}

	@Override
	public void startGestureImpl(Category category) {
		throw new UnsupportedOperationException("The reasoner does not support gesture reasoning");
	}

	@Override
	public void stopGestureImpl(Category category) {
		throw new UnsupportedOperationException("The reasoner does not support gesture reasoning");
	}

	@Override
	public void teachCurrentStateImpl(Category category) {
		IOPropertyList list = new IOPropertyList();
		for (ISensor sensor : getISensors()) {
			list.addAll(sensor.getLastEvent().getPropertyList());
		}
		list.add(category);
		
		
		
		Instance labledInstance = inject(list);
		
		getInstances().add(labledInstance);
	}
	
	private ArrayList<Attribute> features;
	
	@Override
	public Boolean buildClassifierImpl() {
		setClassifier(new NaiveBayes());
		try {
			getClassifier().buildClassifier(getInstances());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
		result.setDataset(getInstances());
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
	public Object executeImpl(Object input) {
		Instance unlabeled = inject((IOPropertyList) input);
		
		double cls = 0;
		try {
			cls = getClassifier().classifyInstance(unlabeled);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Category classCategory = new CategoryImpl();
		classCategory.setValue(unlabeled.classAttribute().value((int) cls));

		IOPropertyList output = new IOPropertyList();
		output.add(classCategory);
		
		return output;
	}
	@Override
	public void handleISensorAdded(ISensor connectedItem) {
		setup();
	}

	@Override
	public void handleISensorRemoved(ISensor disconnectedItem) {
		setup();
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("instances")) {
			//TODO:Implement
		}

		if (propertyName.equals("classifier")) {
			//TODO:Implement
		}

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

	}

}
