/* AddPartyView.java
 *
 *  Version:
 * 		 $Id$
 * 
 *  Revisions:
 * 		$Log: AddPartyView.java,v $
 * 		Revision 1.7  2003/02/20 02:05:53  ???
 * 		Fixed addPatron so that duplicates won't be created.
 * 		
 * 		Revision 1.6  2003/02/09 20:52:46  ???
 * 		Added comments.
 * 		
 * 		Revision 1.5  2003/02/02 17:42:09  ???
 * 		Made updates to migrate to observer model.
 * 		
 * 		Revision 1.4  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 
 */

/**
 * Class for GUI components need to add a party
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

/**
 * Constructor for GUI used to Add Parties to the waiting party queue.
 */
public class AddPartyView implements ActionListener, ListSelectionListener {

	/**
	 * maxSize: The maximum size of a party
	 * win: the window displayed on screen
	 * addPatron: The button displayed on screen to add created patrons to a party
	 * newPatron: The button displayed on screen to create a new patron
	 * remPatron: The button to remove patrons from a party
	 * finished: The button to finish managing the party
	 * party: a list of players in the party
	 * bowlerdb: the database with bowlerfile classes
	 * partyList: the list of bowlers in the party
	 * allBowlers: the list of all bowlers 
	 */

	private int maxSize;

	private JFrame win;
	private JButton addPatron, newPatron, remPatron, finished;
	private JList partyList, allBowlers;
	private Vector party, bowlerdb;

	private ControlDeskView controlDesk;

	private String selectedNick, selectedMember;

	/**
	 * Creates the view used to add party members to a game.
	 * @param controlDesk The ControlDeskView that this PartyView will be added to
	 * @param max The maximum number of people in a party
	 */
	public AddPartyView(ControlDeskView controlDesk, int max) {

		this.controlDesk = controlDesk;
		maxSize = max;
		party = new Vector();
		Vector empty = new Vector();
		empty.add("(Empty)");

		win = new JFrame("Add Party");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(1, 3));

		// Party Panel
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Your Party"));
		
		//Party List
		partyList = createPartyList(empty);
		JScrollPane partyPane = new JScrollPane(partyList);
		partyPanel.add(partyPane);

		// Bowler Database
		JPanel bowlerPanel = new JPanel();
		bowlerPanel.setLayout(new FlowLayout());
		bowlerPanel.setBorder(new TitledBorder("Bowler Database"));

		try {
			bowlerdb = new Vector(BowlerFile.getBowlers());
		} catch (Exception e) {
			System.err.println("File Error");
			bowlerdb = new Vector();
		}
		allBowlers = new JList(bowlerdb);
		allBowlers.setVisibleRowCount(8);
		allBowlers.setFixedCellWidth(120);
		JScrollPane bowlerPane = new JScrollPane(allBowlers);
		bowlerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		allBowlers.addListSelectionListener(this);
		bowlerPanel.add(bowlerPane);

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		buttonPanel.add(createAddPartyPanel());
		buttonPanel.add(createRemPatronPanel());
		buttonPanel.add(createNewPatronPanel());
		buttonPanel.add(createFinishedPanel());

		

		// Clean up main panel
		colPanel.add(partyPanel);
		colPanel.add(bowlerPanel);
		colPanel.add(buttonPanel);

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

	}

	/**
	 * Helper method to create the list of members in the party
	 * @param empty A list of members that is empty and contains an 
	 * 				"empty" object used to display the list's emptyness
	 * @return the created JList of party members
	 */
	private JList createPartyList(Vector empty) {
		this.partyList = new JList(empty);
		partyList.setFixedCellWidth(120);
		partyList.setVisibleRowCount(5);
		partyList.addListSelectionListener(this);
		return partyList;
	}

	/**
	 * Helper method to create the panel in the 
	 * GUI that allows the player to finish their party creation.
	 * @return the panel with the "finished" button
	 */
	private JPanel createFinishedPanel() {
		this.finished = new JButton("Finished");
		JPanel finishedPanel = new JPanel();
		finishedPanel.setLayout(new FlowLayout());
		finished.addActionListener(this);
		finishedPanel.add(finished);
		return finishedPanel;
	}

	/**
	 * Helper method to create the panel in the GUI that 
	 * allows players to be able to add a new member to their party.
	 * @return the panel with the "new patron" button
	 */
	private JPanel createNewPatronPanel() {
		this.newPatron = new JButton("New Patron");
		JPanel newPatronPanel = new JPanel();
		newPatronPanel.setLayout(new FlowLayout());
		newPatron.addActionListener(this);
		newPatronPanel.add(newPatron);
		return newPatronPanel;
	}

	/**
	 * Helper method to create the panel in the GUI that 
	 * allows players to be able to add a new party to the game.
	 * @return the panel with the "add to party" button
	 */
	private JPanel createAddPartyPanel() {
		this.addPatron = new JButton("Add to Party");
		JPanel addPatronPanel = new JPanel();
		addPatronPanel.setLayout(new FlowLayout());
		addPatron.addActionListener(this);
		addPatronPanel.add(addPatron);
		return addPatronPanel;
	}

	/**
	 * Helper method to create the panel in the GUI that 
	 * allows players to be able to remove members from their parties.
	 * @return the panel with the "remove member" button
	 */
	private JPanel createRemPatronPanel() {
		this.remPatron = new JButton("Remove Member");
		JPanel remPatronPanel = new JPanel();
		remPatronPanel.setLayout(new FlowLayout());
		remPatron.addActionListener(this);
		remPatronPanel.add(remPatron);
		return remPatronPanel;
	}


	/**
	 * Method that checks when an action is performed on the buttons on the screen.
	 * The method will perform different checks based on what was pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addPatron)) {
			if (selectedNick != null && party.size() < maxSize) {
				if (party.contains(selectedNick)) {
					System.err.println("Member already in Party");
				} else {
					party.add(selectedNick);
					partyList.setListData(party);
				}
			}
		}
		if (e.getSource().equals(remPatron)) {
			if (selectedMember != null) {
				party.removeElement(selectedMember);
				partyList.setListData(party);
			}
		}
		if (e.getSource().equals(newPatron)) {
			NewPatronView newPatron = new NewPatronView( this );
		}
		if (e.getSource().equals(finished)) {
			if ( party != null && party.size() > 0) {
				controlDesk.updateAddParty( this );
			}
			win.hide();
		}

	}

	/**
	 * Hanfles when the party list is changed by the player adding or removing players
	 * @param e the ListActionEvent that triggered the handler
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource().equals(allBowlers)) {
			selectedNick =
				((String) ((JList) e.getSource()).getSelectedValue());
		}
		if (e.getSource().equals(partyList)) {
			selectedMember =
				((String) ((JList) e.getSource()).getSelectedValue());
		}
	}

	/**
	 * Called by NewPatronView to notify AddPartyView to update
	 * 
	 * @param newPatron the NewPatronView that called this method
	 */
	public void updateNewPatron(NewPatronView newPatron) {
		try {
			Bowler checkBowler = BowlerFile.getBowlerInfo( newPatron.getNick() );
			if ( checkBowler == null ) {
				BowlerFile.putBowlerInfo(
					newPatron.getNick(),
					newPatron.getFull(),
					newPatron.getEmail());
				bowlerdb = new Vector(BowlerFile.getBowlers());
				allBowlers.setListData(bowlerdb);
				party.add(newPatron.getNick());
				partyList.setListData(party);
			} else {
				System.err.println( "A Bowler with that name already exists." );
			}
		} catch (Exception e2) {
			System.err.println("File I/O Error");
		}
	}

	/**
	 * Accessor for Party
	 */
	public Vector getParty() {
		return party;
	}

}
