
package de.jkblume.sav.components.ports;

import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.components.gen.ports.AbstractWekaBayesianReasoner;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataComponent;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;

public class WekaBayesianReasoner extends AbstractWekaBayesianReasoner {
	
	private NaiveBayesUpdateable classifier;
	private Instances trainingSet;
	
	public WekaBayesianReasoner(String name) {
		super(name);
	}

	public void setup() {
		// TODO: IMPLEMENT
	}

	public void destroy() {
		// TODO: IMPLEMENT
	}

	@Override
	public void startGesture(Category category) {
		base.startGesture(category);
	}

	@Override
	public void stopGesture(Category category) {
		base.stopGesture(category);
	}

	@Override
	public void teachCurrentState(Category category) {
		Instance trainingDate = null;
		
		
		
		try {
			classifier.updateClassifier(trainingDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void train() {
		base.train();
	}
	
	@Override
	public void buildClassifier() {
		try {
			classifier.buildClassifier(trainingSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public DataComponent getQualityOfService() {
		DataComponent result = base.getQualityOfService();
		return result;
	}

	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

	@Override
	public void handleIProcessAdded(IProcess item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleIProcessRemoved(IProcess item) {
		// TODO Auto-generated method stub

	}

}
