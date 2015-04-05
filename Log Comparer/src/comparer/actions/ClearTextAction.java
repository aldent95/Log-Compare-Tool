package comparer.actions;

import static java.awt.event.KeyEvent.VK_T;
import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import comparer.UI;
/**
 * Action for clearing the text
 * 
 *
 */
public class ClearTextAction extends AbstractAction {
	/**
	 * 
	 */
	private UI ui;
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public ClearTextAction(UI ui) {
		super("Clear Text");
		this.ui = ui;
		//Setup shortcut key to be Ctrl+T
		putValue(MNEMONIC_KEY, VK_T);
		putValue(ACCELERATOR_KEY, getKeyStroke("ctrl T"));
	}
	/**
	 * Clear the text for the program when the action is called to perform
	 */
	public void actionPerformed(ActionEvent evt) {
		Document document = this.ui.getOutputArea().getDocument();
		try {
			document.remove(0, document.getLength());
		} catch (BadLocationException ble) {
		}
	}
}