package comparer.actions;

import comparer.Node;
import comparer.UI;

public class LoadAction extends CatchAction {
	/**
	 * 
	 */
	private UI ui;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3838191395143096966L;
	private String toLoad = null;
	private Node toLoadNode = null;
	private boolean excludeEmptyValues;
	private boolean excludeHigherCounts;
	private int runs;
	private String type;

	public LoadAction(UI ui, String name, boolean eev, boolean ehc, int runs, String string) {
		super(ui, "Display " + name);
		this.ui = ui;
		toLoad = name;
		excludeEmptyValues = eev;
		excludeHigherCounts = ehc;
		this.runs = runs;
		type = string;
	}

	public LoadAction(UI ui, Node child, boolean eev,
			boolean ehc, int runs, String string) {
		super(ui, "Display " + child.getKey());
		this.ui = ui;
		toLoadNode = child;
		excludeEmptyValues = eev;
		excludeHigherCounts = ehc;
		this.runs = runs;
		type = string;
	}

	@Override
	public void doAction() {
		if(toLoad != null)
		this.ui.getOutputArea().display(this.ui.getModel().getResult(type, toLoad), excludeEmptyValues, excludeHigherCounts, runs);
		else this.ui.getOutputArea().display(toLoadNode, excludeEmptyValues, excludeHigherCounts, runs);
			
	}
	public String toString(){
		if(toLoad != null) return toLoad;
		else return toLoadNode.getKey();
	}
}