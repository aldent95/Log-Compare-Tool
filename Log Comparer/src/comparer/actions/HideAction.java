package comparer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import comparer.SettingsMenu;

public class HideAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SettingsMenu settings;
	public HideAction(SettingsMenu settings) {
		super("Hide");
		this.settings = settings;
	}

	public void actionPerformed(ActionEvent evt) {
		this.settings.setVisible(false);
	}
}
