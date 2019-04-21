import java.util.Vector;
import java.io.*;

/**
 * The main runner class of the bowling alley.
 */
public class drive {

	public static void main(String[] args) {

		/**
		 * numLanes: The number of lanes in the alley
		 * maxPatronsPerParty: the maximum number of patrons that can be in a party
		 */
		int numLanes = 3;
		int maxPatronsPerParty=5;

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );

	}
}
