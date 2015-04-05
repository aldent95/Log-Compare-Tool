package comparer;

import static javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.BorderLayout;

import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import comparer.actions.HideAction;

public class SettingsMenu extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5638307243343524212L;
	private JTextField runs;
	private JCheckBox excludeEmptyValues;
	private JCheckBox excludeHigherCounts;
	public SettingsMenu(JFrame parent){
		super(parent, "Settings", true);
		runs = new JTextField(5);
		runs.setText("1");
		JPanel runsBox = new JPanel();
		runsBox.setLayout(new BorderLayout());
		JLabel temp2 = new JLabel("How many files to compare?");
		runsBox.add(temp2, BorderLayout.CENTER);
		runsBox.add(runs, BorderLayout.WEST);
		excludeEmptyValues =new JCheckBox("Exclude resutls with empty values?");
		excludeHigherCounts = new JCheckBox("Exclude results with counts higher than number of fires?");
		excludeEmptyValues.setSelected(false);
		excludeHigherCounts.setSelected(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		Box settings = new Box(BoxLayout.PAGE_AXIS);
		JLabel label = new JLabel("Settings for loading results. Check help menu for more detailed explinations");
		label.setAlignmentX(RIGHT_ALIGNMENT);
		settings.add(label);
		settings.add(runsBox);
		JPanel temp = new JPanel(new BorderLayout());
		temp.add(excludeEmptyValues, BorderLayout.NORTH);
		temp.add(excludeHigherCounts, BorderLayout.CENTER);
		settings.add(temp);
		getContentPane().add(settings, BorderLayout.WEST);
		pack();
		setLocationRelativeTo(parent);
		
		// And that the escape key exits
		InputMap inputMap =
		    getRootPane().getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap actionMap = getRootPane().getActionMap();
		inputMap.put(getKeyStroke("ENTER"), "hideAction");
		actionMap.put("hideAction", new HideAction(this));
	}
	/**
	 * @return the runs
	 */
	public int getRuns() {
		if(runs.getText().equals("")) return 0;
		return Integer.valueOf(runs.getText());
	}
	/**
	 * @return the excludeEmptyValues
	 */
	public boolean getExcludeEmptyValues() {
		return excludeEmptyValues.isSelected();
	}
	/**
	 * @return the excludeHigherCounts
	 */
	public boolean getExcludeHigherCounts() {
		return excludeHigherCounts.isSelected();
	}

}
