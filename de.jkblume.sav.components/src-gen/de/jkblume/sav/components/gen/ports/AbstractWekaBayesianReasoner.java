
package de.jkblume.sav.components.gen.ports;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.sensorml.types.*;

import de.jkblume.sav.architecture.gen.components.*;

import de.jkblume.sav.architecture.gen.porttypes.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.lang.Class;
import net.opengis.sensorml.v20.Event;
import net.opengis.swe.v20.Category;
import net.opengis.swe.v20.DataComponent;
import net.opengis.sensorml.v20.AbstractProcess;
import net.opengis.sensorml.v20.AbstractPhysicalProcess;
import net.opengis.swe.v20.AbstractSWEIdentifiable;
import net.opengis.sensorml.v20.IOPropertyList;
import java.util.*;
import org.smags.componentmodel.IPort;
import java.util.*;
import org.smags.componentmodel.annotations.ParameterA;
import org.smags.componentmodel.parameter.INotifyPropertyChanged;
import org.smags.componentmodel.annotations.RequirementA;

public abstract class AbstractWekaBayesianReasoner
		implements
			IPort<ILearningReasoningStrategy>,
			INotifyPropertyChanged,
			ILearningReasoningStrategy {

	private String name;
	private boolean isActive = true;
	protected ILearningReasoningStrategy base;

	@RequirementA
	private List<IProcess> iProcesss = new ArrayList<IProcess>();

	public List<IProcess> getIProcesss() {
		return this.iProcesss;
	}

	public void addIProcess(IProcess item) {
		this.iProcesss.add(item);
		handleIProcessAdded(item);
	}

	public void removeIProcess(IProcess item) {
		this.iProcesss.remove(item);
		handleIProcessRemoved(item);
	}

	public abstract void handleIProcessAdded(IProcess item);
	public abstract void handleIProcessRemoved(IProcess item);

	public AbstractWekaBayesianReasoner(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void active() {
		isActive = true;
	}

	@Override
	public void deactivate() {
		isActive = false;
	}

	@Override
	public ILearningReasoningStrategy getBase() {
		return base;
	}

	@Override
	public void setBase(ILearningReasoningStrategy base) {
		this.base = base;
	}

	public <T> T as(Class<T> c) {
		return base.as(c);
	}

	public Boolean getInjectorProvided() {
		return base.getInjectorProvided();
	}

	public void setInjectorProvided(Boolean injectorProvided) {
		base.setInjectorProvided(injectorProvided);
	}

	public Boolean getExtractorProvided() {
		return base.getExtractorProvided();
	}

	public void setExtractorProvided(Boolean extractorProvided) {
		base.setExtractorProvided(extractorProvided);
	}

}
