package comparer.actions;

import static java.awt.event.KeyEvent.VK_E;
import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import comparer.UI;
/**
 * Action used to exit the program
 * 
 *
 */
public class ExitAction extends AbstractAction {

	private UI ui;

	private static final long serialVersionUID = 1L;

	public ExitAction(UI ui) {
		super("Exit");
		this.ui = ui;
		//Sets up the shortcut key
		putValue(MNEMONIC_KEY, VK_E);
		putValue(ACCELERATOR_KEY, getKeyStroke("ctrl Q"));
	}

	public void actionPerformed(ActionEvent evt) {
		this.ui.doExit();
	}
}