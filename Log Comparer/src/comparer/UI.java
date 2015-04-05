package comparer;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import comparer.actions.ClearTextAction;
import comparer.actions.DxAction;
import comparer.actions.ExitAction;
import comparer.actions.GenericAction;
import comparer.actions.HelpAction;
import comparer.actions.LoadAction;
import comparer.actions.SettingAction;

/**
 * Main UI Controller class
 * 
 * @author Alex Dent
 *
 */
public class UI extends JFrame implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	// Actions
	private Action exitAction;
	private Action clearTextAction;
	private Action helpAction;
	private Action dxAction;
	private Action settingAction;
	private Action genericAction;

	// The main output area
	private UITextField outputArea;

	// Help windows
	private HelpMenu help = new HelpMenu(this);

	// Button Panel
	private JPanel buttons;
	// Tree fields
	private JScrollPane treeView;
	private JTree tree;
	DefaultTreeModel treeModel;
	private LogTreeListener treeListener;
	// The data model
	private Comparer model;

	/**
	 * Creates a new UI object and then sets up all the UI elements and creates
	 * a Comparor object
	 */
	private UI() {
		super("Log Compare tool");
		int width = 1200;
		int height = 700;
		this.getContentPane().setPreferredSize(new Dimension(width, height));

		// Initialise everything
		initActions();
		initUI();
		setSize(width, height);

		// Create Comparer
		setModel(new Comparer());

		// Center window on screen
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Point center = ge.getCenterPoint();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height
				/ 2);
		pack();
		// Show ourselves
		setVisible(true);
	}

	/**
	 * Sets up all the actions to be used by the buttons
	 */
	private void initActions() {
		// Setup all the actions
		exitAction = new ExitAction(this);
		clearTextAction = new ClearTextAction(this);
		helpAction = new HelpAction(this);
		dxAction = new DxAction(this);
		settingAction = new SettingAction(this);
		genericAction = new GenericAction(this);
		// Set the window to run the exit method on close
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				doExit();
			}
		});
	}

	/**
	 * Sets up all the UI contained in the main UI JFrame
	 */
	private void initUI() {
		// Create tabbed pane with commands in it
		setTabbedPane(new JPanel());
		getContentPane().add(getTabbedPane(), BorderLayout.NORTH);
		// Add all the buttons to the tabbedPane
		buttons.add(new JButton(dxAction));
		buttons.add(new JButton(settingAction));
		buttons.add(new JButton(genericAction));
		// Create output area with scrollpane
		setOutputArea(new UITextField());
		getOutputArea().setEditable(false);
		getOutputArea().setFocusable(false);
		getOutputArea().setTabSize(2);
		JScrollPane sp = new JScrollPane(getOutputArea());
		sp.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

		getContentPane().add(sp, BorderLayout.CENTER);

		// Create menus
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		// Setup menu items
		JMenuItem clearTextMenuItem = new JMenuItem(clearTextAction);
		JMenuItem exitMenuItem = new JMenuItem(exitAction);
		JMenuItem helpMenuItem = new JMenuItem(helpAction);
		// Add menu items to menu
		fileMenu.add(clearTextMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(helpMenuItem);
		// Setup the menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		// Tree testing
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		treeModel = new DefaultTreeModel(root);
		treeListener = new LogTreeListener();
		treeModel.addTreeModelListener(treeListener);
		tree = new JTree(treeModel);
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		tree.setShowsRootHandles(true);
		treeView = new JScrollPane(tree);
		Dimension size = treeView.getSize();
		size.width = 300;
		treeView.setPreferredSize(size);
		this.getContentPane().add(treeView, BorderLayout.WEST);

		// Pack it all
		pack();
	}

	/** Exit the Application */
	public void doExit() {
		try {
			getModel().ensureClose();
		} catch (IOException e) {
			showExceptionDialog(e);
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		// Build the UI on the Swing thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new UI();
			}
		});
	}

	/**
	 * Generate and popup an error dialog
	 * 
	 * @param e
	 *            - The exception to show in the popup
	 */
	public void showExceptionDialog(Exception e) {
		showMessageDialog(this, e.toString(), "Error performing action",
				ERROR_MESSAGE);
	}

	/**
	 * @return the tabbedPane
	 */
	public JPanel getTabbedPane() {
		return buttons;
	}

	/**
	 * @param tabbedPane
	 *            the tabbedPane to set
	 */
	private void setTabbedPane(JPanel tabbedPane) {
		this.buttons = tabbedPane;
	}

	/**
	 * @return the model
	 */
	public Comparer getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	private void setModel(Comparer model) {
		this.model = model;
	}

	/**
	 * @return the outputArea
	 */
	public UITextField getOutputArea() {
		return outputArea;
	}

	/**
	 * @param outputArea
	 *            the outputArea to set
	 */
	private void setOutputArea(UITextField outputArea) {
		this.outputArea = outputArea;
	}

	/**
	 * @return the help
	 */
	public HelpMenu getHelp() {
		return help;
	}

	public void setupDxMenu(Set<String> buttons, boolean eev, boolean ehc,
			int runs) {
		try {
			tree.setSelectionPath(tree.getNextMatch("dxDiag", 0,
					Position.Bias.Forward));
			treeModel.removeNodeFromParent((DefaultMutableTreeNode) tree
					.getSelectionPath().getLastPathComponent());
		} catch (NullPointerException e) {
		}
		DefaultMutableTreeNode dxMenu = new DefaultMutableTreeNode("dxDiag");
		treeListener.addObject((DefaultMutableTreeNode) treeModel.getRoot(),
				dxMenu);
		ArrayList<String> buttonsSorted = new ArrayList<String>();
		for (String s : buttons) {
			buttonsSorted.add(s);
		}
		Collections.sort(buttonsSorted);
		for (String name : buttonsSorted) {
			LoadAction temp = new LoadAction(this, name, eev, ehc, runs,
					"dxDiag");
			treeListener.addObject(dxMenu, temp);
		}
		for (int i = 1; i < tree.getRowCount(); i++) {
			tree.collapseRow(i);
		}
		this.repaint();
	}

	public void setupGenericMenu(ArrayList<String> buttons,
			boolean excludeEmptyValues, boolean excludeHigherCounts, int runs) {
		this.getJMenuBar().remove(3);
		JMenu genericMenu = new JMenu("Generic File");
		genericMenu.setMnemonic('G');
		ArrayList<String> buttonsSorted = new ArrayList<String>();
		for (String s : buttons) {
			buttonsSorted.add(s);
		}
		Collections.sort(buttonsSorted);
		for (String name : buttonsSorted) {
			JMenuItem temp = new JMenuItem(new LoadAction(this, name,
					excludeEmptyValues, excludeHigherCounts, runs, "Generic"));
			genericMenu.add(temp);
		}
		JMenuBar temp = this.getJMenuBar();
		temp.add(genericMenu, 3);
		this.setJMenuBar(temp);
		this.repaint();

	}

	public void setupSettingsLogMenu(Node root, boolean excludeEmptyValues,
			boolean excludeHigherCounts, int runs) {
		DefaultMutableTreeNode settingsLogMenu = new DefaultMutableTreeNode(
				"Settings Logs");
		treeListener.addObject((DefaultMutableTreeNode) treeModel.getRoot(),
				settingsLogMenu);
		for (Node child : root.getChildren()) {
			if (child.getChildren().size() == 0) {
				LoadAction temp = new LoadAction(this, child,
						excludeEmptyValues, excludeHigherCounts, runs,
						"Settings");
				treeListener.addObject(settingsLogMenu, temp);
			} else {

				setupSettingsRecur(child, excludeEmptyValues,
						excludeHigherCounts, runs, treeListener.addObject(
								settingsLogMenu, new LoadAction(this, child,
										excludeEmptyValues,
										excludeHigherCounts, runs, "Settings")));
			}
		}
		for (int i = 1; i < tree.getRowCount(); i++) {
			tree.collapseRow(i);
		}
		System.gc();
		this.repaint();

	}

	public void setupSettingsRecur(Node current, boolean eev, boolean ehc,
			int runs, DefaultMutableTreeNode parent) {
		for (Node child : current.getChildren()) {
			if (child.getChildren().size() == 0) {
				LoadAction temp = new LoadAction(this, child, eev, ehc, runs,
						"Settings");
				treeListener.addObject(parent, temp);
			} else {
				setupSettingsRecur(child, eev, ehc, runs,
						treeListener.addObject(parent, child.getKey()));
			}
		}
	}

	private class LogTreeListener implements TreeModelListener {

		public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
				Object child) {
			tree.setSelectionPath(tree.getNextMatch(parent.getUserObject()
					.toString(), 0, Position.Bias.Forward));
			DefaultMutableTreeNode parent2 = (DefaultMutableTreeNode) tree
					.getSelectionPath().getLastPathComponent();

			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

			treeModel
					.insertNodeInto(childNode, parent2, parent.getChildCount());

			// Make sure the user can see the lovely new node.
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
			return childNode;
		}

		@Override
		public void treeNodesChanged(TreeModelEvent e) {
		}

		@Override
		public void treeNodesInserted(TreeModelEvent e) {
		}

		@Override
		public void treeNodesRemoved(TreeModelEvent e) {
		}

		@Override
		public void treeStructureChanged(TreeModelEvent e) {
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		if (node == null)
			return;

		if (node.isLeaf()) {
			Object temp = node.getUserObject();
			if (temp instanceof LoadAction) {
				((LoadAction) temp).doAction();
			}
		} else {
		}

	}
}
