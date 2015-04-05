package comparer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import comparer.UI;

/**
 * An Action that catches any exception thrown in the doAction method.
 */
abstract class CatchAction extends AbstractAction {
	/**
	 * 
	 */
	private UI ui;
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public CatchAction(UI ui, String name) {
		super(name);
		this.ui = ui;
	}
	/**
	 * When this or any subclass is performed, try to do the action, if it fails catch and display the error.
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			doAction();
		} catch (Exception ex) {
			this.ui.showExceptionDialog(ex);
			ex.printStackTrace();
		}
	}

	/** Subclasses implement this for their behavior */
	protected abstract void doAction();
}