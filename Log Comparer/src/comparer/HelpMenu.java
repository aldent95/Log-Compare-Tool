package comparer;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class HelpMenu extends JDialog{
	public HelpMenu(JFrame parent){
		super(parent, "Help Menu", true);
		
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		Box labels = new Box(BoxLayout.PAGE_AXIS);
		labels.add(new JLabel("Log file Comapre tool V1.0"));
		labels.add(Box.createVerticalStrut(10));
		labels.add(new JLabel("Help:"));
		labels.add(new JLabel("dxDiag is used to compare direct x diagnostic log files"));
		labels.add(new JLabel("To use place dxDiag files in the dxDiags folder"));
		labels.add(new JLabel("The first one should be called dxDiag.txt, the rest should be dxDiag (x).txt where x is its number starting from 2"));
		labels.add(Box.createVerticalStrut(10));
		labels.add(new JLabel("Settings is used to compare EVE settings log files"));
		labels.add(new JLabel("To use place settings log files in the settingsLogs folder"));
		labels.add(new JLabel("The first one should be called settings.yaml, the rest should be settings (x).yaml where x is its number starting from 2"));
		labels.add(Box.createVerticalStrut(10));
		labels.add(new JLabel("Generic is used to do a line by line compare of any text or otherwise un-encrypted/readable file that is not supported"));
		labels.add(new JLabel("PLEASE NOTE GENERIC DOES NOT CURRENTLY WORK"));
		labels.add(Box.createVerticalStrut(10));
		getContentPane().add(labels, BorderLayout.WEST);
		pack();
		setLocationRelativeTo(parent);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6772463678261765104L;

}
