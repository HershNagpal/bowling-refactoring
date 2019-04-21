/* ControlDeskView.java
 *
 *  Version:
 *			$Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

/**
 * Class for representation of the control desk
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class ControlDeskView implements ActionListener, ControlDeskObserver {

	/**
	 * addParty: The button the user presses to create a new party and add them to the game
	 * finished: The button the user presses to finish party creation
	 * assign: The button that assigns the parties to lanes
	 * win: The window that displays the control desk interface
	 * partyList: The list that shows all of the parties and members
	 * maxMembers: The maximum number of members that can be assigned to a party
	 * controlDesk: The control desk implementation to be used for functionality.
	 */
	private JButton addParty, finished, assign;
	private JFrame win;
	private JList partyList;
	private int maxMembers;
	private ControlDesk controlDesk;

	/**
	 * Displays a GUI representation of the ControlDesk
	 *
	 */
	public ControlDeskView(ControlDesk controlDesk, int maxMembers) {

		this.controlDesk = controlDesk;
		this.maxMembers = maxMembers;
		int numLanes = controlDesk.getNumLanes();

		win = new JFrame("Control Desk");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());

		// Controls Panel
		JPanel controlsPanel = createControlsPanel();
		JPanel addPartyPanel = createAddPartyPanel(controlsPanel);
		JPanel assignPanel = createAssignLanesPanel();
		JPanel finishedPanel = createFinishedPanel(controlsPanel);

		// Lane Status Panel
		JPanel laneStatusPanel = createLaneStatusPanel(numLanes);

		HashSet lanes=controlDesk.getLanes();
		Iterator it = lanes.iterator();
		int laneCount=0;
		while (it.hasNext()) {
			Lane curLane = (Lane) it.next();
			LaneStatusView laneStat = new LaneStatusView(curLane,(laneCount+1));
			curLane.subscribe(laneStat);
			((Pinsetter)curLane.getPinsetter()).subscribe(laneStat);
			JPanel lanePanel = laneStat.showLane();
			lanePanel.setBorder(new TitledBorder("Lane" + ++laneCount ));
			laneStatusPanel.add(lanePanel);
		}

		// Party Queue Panel
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Party Queue"));

		Vector empty = new Vector();
		empty.add("(Empty)");

		JScrollPane partyPane = createPartyPane(partyPanel, empty);

		// Clean up main panel
		colPanel.add(controlsPanel, "East");
		colPanel.add(laneStatusPanel, "Center");
		colPanel.add(partyPanel, "West");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		/* Close program when this window closes */
		win.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

	}

	/**
	 * Creates the control panel with lane and party controls.
	 * @return the lane control panel
	 */
	private JPanel createControlsPanel() {
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new GridLayout(3, 1));
		controlsPanel.setBorder(new TitledBorder("Controls"));
		return controlsPanel;
	}

	/**
	 * Creates the panel that displays the lanes and their current status.
	 * @return the lane status panel
	 */
	private JPanel createLaneStatusPanel(int numLanes) {
		JPanel laneStatusPanel = new JPanel();
		laneStatusPanel.setLayout(new GridLayout(numLanes, 1));
		laneStatusPanel.setBorder(new TitledBorder("Lane Status"));
		return laneStatusPanel;
	}

	/**
	 * Creates the button that finishes the creation of parties.
	 * @return the button that finishes party creation
	 */
	private JPanel createFinishedPanel(JPanel controlsPanel) {
		this.finished = new JButton("Finished");
		JPanel finishedPanel = new JPanel();
		finishedPanel.setLayout(new FlowLayout());
		finished.addActionListener(this);
		finishedPanel.add(finished);
		controlsPanel.add(finishedPanel);
		return finishedPanel;
	}

	/**
	 * Creates the pane where party information is displayed.
	 * @return the party pane.
	 */
	private JScrollPane createPartyPane(JPanel partyPanel, Vector empty) {
		this.partyList = new JList(empty);
		partyList.setFixedCellWidth(120);
		partyList.setVisibleRowCount(10);
		JScrollPane partyPane = new JScrollPane(partyList);
		partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		partyPanel.add(partyPane);
		return partyPane;
	}

	/**
	 * Creates the button that adds a party to the game.
	 * @return the add party button
	 */
	private JPanel createAddPartyPanel (JPanel controlsPanel) {
		this.addParty = new JButton("Add Party");
		JPanel addPartyPanel = new JPanel();
		addPartyPanel.setLayout(new FlowLayout());
		addParty.addActionListener(this);
		addPartyPanel.add(addParty);
		controlsPanel.add(addPartyPanel);
		return addPartyPanel;
	}

	/**
	 * Creates the button that allows the user to assign lanes to the parties.
	 * @return the button used to assign lanes
	 */
	private JPanel createAssignLanesPanel() {
		this.assign = new JButton("Assign Lanes");
		JPanel assignPanel = new JPanel();
		assignPanel.setLayout(new FlowLayout());
		assign.addActionListener(this);
		assignPanel.add(assign);
		return assignPanel;
	}
	

	/**
	 * Handler for actionEvents
	 * @param e	the ActionEvent that triggered the handler
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addParty)) {
			AddPartyView addPartyWin = new AddPartyView(this, maxMembers);
		}
		if (e.getSource().equals(assign)) {
			controlDesk.assignLane();
		}
		if (e.getSource().equals(finished)) {
			win.hide();
			System.exit(0);
		}
	}

	/**
	 * Receive a new party from andPartyView.
	 * @param addPartyView	the AddPartyView that is providing a new party
	 */
	public void updateAddParty(AddPartyView addPartyView) {
		controlDesk.addPartyQueue(addPartyView.getParty());
	}

	/**
	 * Receive a broadcast from a ControlDesk
	 * @param ce	the ControlDeskEvent that triggered the handler
	 */
	public void receiveControlDeskEvent(ControlDeskEvent ce) {
		partyList.setListData(((Vector) ce.getPartyQueue()));
	}
}
