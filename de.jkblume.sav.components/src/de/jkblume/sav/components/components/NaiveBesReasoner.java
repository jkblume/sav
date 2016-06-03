
package de.jkblume.sav.components.components;

import java.util.ArrayList;
import java.util.List;

import org.vast.data.CategoryImpl;

import de.jkblume.sav.architecture.components.PullThread;
import de.jkblume.sav.architecture.gen.components.AbstractLearningReasonerProcess;
import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.architecture.gen.porttypes.ISensor;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.Event;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.Count;
import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataRecord;
import net.opengis.swe.v20.Quantity;
import net.opengis.swe.v20.ScalarComponent;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class NaiveBesReasoner extends AbstractLearningReasonerProcess {
	private static final String SAMPLING_RATE_PARAMETER_NAME = "samplingRate";

	private Thread pullThread;
	private boolean running;
	
	public NaiveBesReasoner(String name) {
		super(name);
	}

	public void setup() {
		if (!validateSmlConfiguration()) {
			throw new IllegalStateException("Invalid SML Configuration of sensoe " + getId());
		}
		
		pullThread = new PullThread(this);
	}

	public void destroy() {
		stop();
	}

	public void startImpl() {
		pullThread.start();
		running = true;
	}

	public void stopImpl() {
		pullThread.interrupt();
		running = false;
	}

	public Boolean isRunningImpl() {
		return running;
	}


	public void startGestureImpl(Category category) {
		//TODO: IMPLEMENT

	}

	public void stopGestureImpl(Category category) {
		//TODO: IMPLEMENT

	}

	public void teachCurrentStateImpl(Category category) {
		IOPropertyList list = new IOPropertyList();
		for (ISensor sensor : getISensors()) {
			list.addAll(sensor.getLastEvent().getPropertyList());
		}
		list.add(category);
		
		
		
		Instance labledInstance = inject(list);
		
		try {
			classifier.updateClassifier(labledInstance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateClassifierImpl(Object trainingDate) {
		
	}
	
	private Instances structure;
	private ArrayList<Attribute> features;
	private NaiveBayesUpdateable classifier;
	
	public void buildClassifierImpl() {
		features = new ArrayList<>();
		for (ISensor iSensor : getISensors()) {
			IOPropertyList sensorOutput = iSensor.retrieveOutputStructure();
			for (AbstractSWEIdentifiable identifiable : sensorOutput) {
				addFeature((DataComponent) identifiable);
			}
		}
		addFeature((Category) getSmlConfiguration().getOutputList().get(0));
		structure = new Instances(getSmlConfiguration().getId(), features, 0);
		structure.setClassIndex(structure.numAttributes()-1);
		
		classifier = new NaiveBayesUpdateable();
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
			System.out.println(component.getName());
			System.out.println(component.getClass().getSimpleName());
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

	public Category classifyImpl(IOPropertyList input) {
		Instance unlabeled = inject(input);
//		unlabeled.setClassMissing();
		
		double cls = 0;
		try {
			cls = classifier.classifyInstance(unlabeled);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		unlabeled.setClassValue(cls);
		
		Category output = new CategoryImpl();
		output.setValue(unlabeled.classAttribute().value((int) cls));
		
		return output;
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

	public DataComponent getQualityOfServiceImpl() {
		//TODO: IMPLEMENT
		return null;
	}
	
	public Boolean validateSmlConfigurationImpl() {
		boolean result = true;
		
		result &= getSmlConfiguration().getId() != null;
	
		
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		result &= parameter != null && parameter instanceof Count;
		
		return result;
	}

	public Object executeImpl(Object value) {
		Category classify = classify((IOPropertyList) value);
		
		IOPropertyList list = new IOPropertyList();
		list.add(classify);
		
		return list;
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {

		if (propertyName.equals("injectorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("extractorProvided")) {
			//TODO:Implement
		}

		if (propertyName.equals("smlConfiguration")) {
			//TODO:Implement
		}

	}

	@Override
	public IOPropertyList retrieveValuesImpl() {
		IOPropertyList values = new IOPropertyList();
		
		// retrieve values from pull sensors
		for (ISensor pullSensor : getISensors()) {
			Event lastEvent = pullSensor.getLastEvent();
			if (lastEvent == null) {
				continue;
			}
			OgcPropertyList<DataComponent> partialResult = lastEvent.getPropertyList();
			values.addAll(partialResult);
		}
		
		return values;
	}

	@Override
	public String getIdImpl() {
		return getSmlConfiguration().getId();
	}
	
	@Override
	public Integer getSamplingRateImpl() {
		AbstractSWEIdentifiable parameter = getSmlConfiguration().getParameter(SAMPLING_RATE_PARAMETER_NAME);
		return ((Count) parameter).getValue();
	}


	@Override
	public IOPropertyList retrieveOutputStructureImpl() {
		if (getIProcess() != null) {
			return getIProcess().getSmlConfiguration().getOutputList();
		}
		return getSmlConfiguration().getOutputList();
	}

	@Override
	public Boolean initializeImpl() {
		return true;
	}


	@Override
	public void handleISensorAdded(ISensor item) {
		buildClassifier();
	}

	@Override
	public void handleISensorRemoved(ISensor item) {
		buildClassifier();
	}

	@Override
	public void handleIProcessConnected(IProcess item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleIProcessDisconnected(IProcess item) {
		// TODO Auto-generated method stub
		
	}

}
