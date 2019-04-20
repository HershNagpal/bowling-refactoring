/*
 * Alley.java
 *
 * Version:
 *   $Id$
 *
 * Revisions:
 *   $Log: Alley.java,v $
 *   Revision 1.4  2003/02/02 20:28:58  ???
 *   fixed sleep thread bug in lane
 *
 *   Revision 1.3  2003/01/12 22:23:32  ???
 *   *** empty log message ***
 *
 *   Revision 1.2  2003/01/12 20:44:30  ???
 *   *** empty log message ***
 *
 *   Revision 1.1  2003/01/12 19:09:12  ???
 *   Adding Party, Lane, Bowler, and Alley.
 *
 */

/**
 *  Class that is the outer container for the bowling alley.
 *
 */
public class Alley {
	public ControlDesk controldesk;

	/**
	 * Constructor for the bowling alley
	 * @param numLanes the number of lanes in the alley
	 */
    public Alley( int numLanes ) {
        controldesk = new ControlDesk( numLanes );
    }

	/**
	 * Returns the control desk managing the game
	 * @return the control desk for the game
	 */
	public ControlDesk getControlDesk() {
		return controldesk;
	}
	
}


    
