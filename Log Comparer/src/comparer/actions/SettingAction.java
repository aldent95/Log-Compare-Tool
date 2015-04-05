package comparer.actions;

import java.io.IOException;

import comparer.Node;
import comparer.SettingsMenu;
import comparer.UI;

public class SettingAction extends CatchAction {

	/**
	 * 
	 */
	private UI ui;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2547220370578297250L;

	public SettingAction(UI ui) {
		super(ui, "Settings Log Load");
		this.ui = ui;
	}

	@Override
	protected void doAction() {
		SettingsMenu settings = new SettingsMenu(this.ui);
		settings.setVisible(true);
		Node root = null;
		try {
			root = this.ui.getModel().loadSettings(settings.getRuns());
		} catch (IOException e) {
			ui.showExceptionDialog(e);
			e.printStackTrace();
		}
		if(root != null){
		this.ui.setupSettingsLogMenu(root, settings.getExcludeEmptyValues(), settings.getExcludeHigherCounts(), settings.getRuns());
		}
	}
}