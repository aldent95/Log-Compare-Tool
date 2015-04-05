package comparer.actions;

import static java.awt.event.KeyEvent.VK_T;
import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import comparer.UI;

public class HelpAction extends AbstractAction {


	private UI ui;

	private static final long serialVersionUID = 5439102722660386066L;

	public HelpAction(UI ui) {
		super("Help Menu");
		this.ui = ui;
		putValue(MNEMONIC_KEY, VK_T);
		putValue(ACCELERATOR_KEY, getKeyStroke("ctrl H"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.ui.getHelp().setVisible(true);
	}
}