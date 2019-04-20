/*
 * Bowler.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log: Bowler.java,v $
 *     Revision 1.3  2003/01/15 02:57:40  ???
 *     Added accessors and and equals() method
 *
 *     Revision 1.2  2003/01/12 22:23:32  ???
 *     *** empty log message ***
 *
 *     Revision 1.1  2003/01/12 19:09:12  ???
 *     Adding Party, Lane, Bowler, and Alley.
 *
 */

/**
 *  Class that holds all bowler info
 *
 */
public class Bowler {

	/**
	 * fullName: the full name of the bowler
	 * nickName: the bowler's chosen nickname for scoring
	 * email: the bowler's email address
	 */
    private String fullName;
    private String nickName;
    private String email;

    public Bowler( String nick, String full, String mail ) {
	nickName = nick;
	fullName = full;
  	email = mail;
    }

	/**
	 * Returns this bowler's nickname
	 * @return this bowler's nickname
	 */
    public String getNickName() {
        return nickName;  
    }

	/**
	 * Returns this bowler's full name
	 * @return this bowler's full name
	 */
	public String getFullName ( ) {
			return fullName;
	}

	/**
	 * Returns this bowler's email address
	 * @return this bowler's email address
	 */
	public String getEmail ( ) {
		return email;	
	}
	

	/**
	 * Returns true if this bowler and b are equal, false if they are not.
	 * @param b the bowler to check equality with
	 * @return true if this bowler and b are equal, false otherwise
	 */
	public boolean equals ( Bowler b) {
		boolean retval = true;
		if ( !(nickName.equals(b.getNickName())) ) {
				retval = false;
		}
		if ( !(fullName.equals(b.getFullName())) ) {
				retval = false;
		}	
		if ( !(email.equals(b.getEmail())) ) {
				retval = false;
		}
		return retval;
	}
}