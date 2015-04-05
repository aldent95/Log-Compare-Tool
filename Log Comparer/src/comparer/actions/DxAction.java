package comparer.actions;

import java.io.IOException;
import java.util.Set;

import comparer.SettingsMenu;
import comparer.UI;
/**
 * Action class used to call for loading the dxDiag logs and to call for loading the tree for displaying them
 * @author Alex Dent
 *
 */
public class DxAction extends CatchAction {

	private UI ui;
	private static final long serialVersionUID = 5755520289520419044L;

	public DxAction(UI ui) {
		super(ui, "dxDiag Load");
		this.ui = ui;
	}

	@Override
	protected void doAction() {
		//Display the settings menu to get load settings
		SettingsMenu settings = new SettingsMenu(this.ui);
		settings.setVisible(true);
		Set<String> buttons = null;
		//Try to load the logs, catch and display any errors
		try {
			//Load the logs, store returned set for passing to the ui to display
			buttons = this.ui.getModel().loadDx(settings.getRuns());
			//Call the UI to setup the tree menu for the dxDiags
			this.ui.setupDxMenu(buttons, settings.getExcludeEmptyValues(), settings.getExcludeHigherCounts(), settings.getRuns());
		} catch (IOException e) {
			ui.showExceptionDialog(e);
		}


	}
}