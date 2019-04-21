/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

 /**
	* Object used to store player scores.
  */
public class Score {

		/**
		 * nick: The nickname of the player
		 * date: The date the score was achieved
		 * score: the score achieved by the player
		 */
    private String nick;
    private String date;
    private String score;

		/**
		 * Constructor for the score object
		 */
    public Score( String nick, String date, String score ) {
		this.nick=nick;
		this.date=date;
		this.score=score;
    }

		/**
		 * returns the nickname of the player assiciated with this score.
		 * @return the player's nickname who achieved this score
		 */
    public String getNickName() {
        return nick;  
    }

		/**
		 * Returns the date this score was achieved.
		 * @return the date of this score
		 */
		public String getDate() {
			return date;
		}

		/**
		 * Returns the player's score stored in this object.
		 * @return the numeric score achieved
		 */
		public String getScore() {
			return score;
		}

		/**
		 * Converts this score object into a formatted string.
		 * @return a formatted score string
		 */
		public String toString() {
			return nick + "\t" + date + "\t" + score;
		}

}
