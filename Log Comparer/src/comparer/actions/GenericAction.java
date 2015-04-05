package comparer.actions;

import comparer.UI;
/**
 * Action that calls the generic loading and tells the UI to add the generic button to the tree
 * @author Alex Dent
 *
 */
public class GenericAction extends CatchAction {


	private UI ui;

	private static final long serialVersionUID = 8882217782544946912L;

	public GenericAction(UI ui) {
		super(ui, "Generic file Load");
		this.ui = ui;
	}

	@Override
	protected void doAction() {
		//Display the settings menu
		//SettingsMenu settings = new SettingsMenu(this.ui);
		//settings.setVisible(true);
		//Call the loader to load up the files ready to display
		//ArrayList<String> buttons = this.ui.getModel().loadGeneric(settings.getRuns());
		//Call the UI to display the generic menu
		//this.ui.setupGenericMenu(buttons, settings.getExcludeEmptyValues(), settings.getExcludeHigherCounts(), settings.getRuns());

	}
}