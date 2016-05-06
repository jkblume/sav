
package de.jkblume.sav.components.ports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vast.process.SMLException;
import org.vast.sensorML.SMLHelper;

import de.jkblume.sav.architecture.gen.porttypes.IProcess;
import de.jkblume.sav.components.gen.ports.AbstractJAggregateProcessOperator;
import net.opengis.sensorml.v20.AggregateProcess;
import net.opengis.sensorml.v20.IOPropertyList;
import net.opengis.sensorml.v20.Link;
import net.opengis.swe.v20.DataComponent;

public class JAggregateProcessOperator extends AbstractJAggregateProcessOperator {
	
	private AggregateProcess aggregateProcess;

	private List<ProcessTree> trees = new ArrayList<>();
	private Map<String, IProcess> id2process = new HashMap<>();
	private Map<String, ProcessTreeNode> name2node = new HashMap<>();
	
	public JAggregateProcessOperator(String name) {
		super(name);
	}
	
	@Override
	public void setup() {
		if (getSmlConfiguration() instanceof AggregateProcess) {
			aggregateProcess = (AggregateProcess) getSmlConfiguration();
		} else {
			throw new IllegalArgumentException("SensorML description is not an AggregateProcess");
		}
		
		for (Link link : aggregateProcess.getConnectionList()) {
			String[] destinationPath = link.getDestination().split("/");
			String[] sourcePath = link.getSource().split("/");
						
			if (sourcePath[0].equals("inputs")) {
				ProcessTree tree = new ProcessTree();
				ProcessTreeNode root = new ProcessTreeNode();
				String processId = aggregateProcess.getComponent(destinationPath[1]).getId();
				root.process = id2process.get(processId);
				root.sourcePath = sourcePath;
				root.destinationPath = destinationPath;				
				tree.root = root;
				trees.add(tree);
				name2node.put(destinationPath[1], root);
			}
			
			if (sourcePath[0].equals("components") && destinationPath[0].equals("components")) {
				ProcessTreeNode node = new ProcessTreeNode();
				node.sourcePath = sourcePath;
				node.destinationPath = destinationPath;
				String processId = aggregateProcess.getComponent(destinationPath[1]).getId();
				node.process = id2process.get(processId);
				ProcessTreeNode parent = name2node.get(sourcePath[1]);
				parent.children.add(node);
				name2node.put(destinationPath[1], node);
			}
			
			if (destinationPath[0].equals("outputs")) {
				ProcessTreeNode node = new ProcessTreeNode();
				node.sourcePath = sourcePath;
				node.destinationPath = destinationPath;
				if (sourcePath[0].equals("components")) {
					ProcessTreeNode parent = name2node.get(sourcePath[1]);
					parent.children.add(node);
				}
			}
		}
	}
	

	@Override
	public void destroy() {
		// TODO: IMPLEMENT
	}
	
	private IOPropertyList result = new IOPropertyList();
	
	@Override
	public IOPropertyList execute(IOPropertyList value) {
		for (ProcessTree tree : trees) {
			try {
				executeForProcess(tree.root, value);
			} catch (SMLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void executeForProcess(ProcessTreeNode node, IOPropertyList input) throws SMLException {
		if (node.isLeaf()) {
			fillResultWithOutput(node, input);
			return;
		}
		IOPropertyList output = node.process.execute(input);
		for (ProcessTreeNode child : node.children) {
			executeForProcess(child, output);
		};
	}

	private void fillResultWithOutput(ProcessTreeNode node, IOPropertyList output) throws SMLException {
		if (node.isOutputPartial()) {
			DataComponent component = result.getComponent(node.destinationPath[1]);
			if (component == null) {
				String pathToOutputComponent = node.destinationPath[0] + "/" + node.destinationPath[1];
				component = SMLHelper.findComponentByPath(aggregateProcess, pathToOutputComponent);
				result.add(node.destinationPath[1], component);
			}
			component.getComponent(node.destinationPath[2]).setData(output.getComponent(0).getData());
		} else {
			result.addAll(output);
		}
	}

	@Override
	public void notifyPropertyChanged(Object sender, String propertyName, Object oldValue, Object newValue) {
		if (sender == this) {

		}
	}

	@Override
	public void handleIProcessAdded(IProcess item) {
		id2process.put(item.getSmlConfiguration().getId(), item);
	}
	
	@Override
	public void handleIProcessRemoved(IProcess item) {
		id2process.remove(item.getSmlConfiguration().getId());
	}
	
	private class ProcessTree {
		public ProcessTreeNode root;
		
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Tree with rootNode: " + root + "\n");
			for (ProcessTreeNode child : root.children) {
				sb.append(child);
			}
			return sb.toString();
		}
	}
	
	private class ProcessTreeNode {
		public String[] sourcePath;
		public String[] destinationPath;
		public IProcess process;
		public List<ProcessTreeNode> children = new ArrayList<>();
				
		public boolean isLeaf() {
			return this.children.isEmpty();
		}
		
		public boolean isOutputPartial() {
			return this.destinationPath.length > 2;
		}
		
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Node Process: " + process.getSmlConfiguration().getId());
			sb.append("\n\tChildren: \n");
			for (ProcessTreeNode child : children) {
				sb.append("\t\t- ");
				if (child.isLeaf()) {
					sb.append(child.destinationPath[1]);
					sb.append("/");
					sb.append(child.destinationPath[2]);
				} else {
					sb.append(child.process.getSmlConfiguration().getId());
				}
				sb.append("\n");
			}
			return sb.toString();
		}
	}

}