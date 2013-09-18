/**
 * 
 */
package aufgaben_db;


import java.util.regex.Pattern;
/**
 * Enum, die die regulaeren Ausdruecker verwaltet, mit denen die Aufgabendeklarationen gesucht werden.
 * 
 * @author Schweiner
 *
 */
public enum Muster {
	AUFGABE("Aufgabe"), // hier noch Praefix erlauben.
	EXERCISE("Exercise"), 
	CHARUFGABE("[\\S]*ufgabe[\\S]*"),
	INTDOTINTDOT("([\\d]+[\\.]){2}"),  // ueberfluessig
	INTDOTINT("[\\d]+[\\.][\\d]+"), 
	INTDOTBRACKET("[\\d]+[\\.][)]"),
	INTBRACKET("[\\d]+[\\.][)]"),
	INTCOLON("[\\d]+[\\:]"),
	INTDOT("[\\d]+[\\.]"),  // hier noch abgrenzen, dass danach leerzeichen / keine Zahl mehr kommt
	INT("[\\d]+[)]"), // leerzeichen danach, niedriger Score!!!
	CHARINTCHAR("[\\D]*[\\d][\\S]*");
	
	// auch noch: nur Zahl (INT)
	
	 private String pattern;

	 private Muster(String c) {
	   pattern = c;
	 }

	 /**
	  * gibt den kompilierten Regul�ren Ausdruck des entsprechenden Musters aus
	  * 
	  * @return kompilierter regul�rer Ausdruck
	  */
	 public Pattern getPattern() {
	   return Pattern.compile(pattern);
	 }
	 
	 public boolean isWordedPattern() {
		if (this.equals(AUFGABE) || this.equals(EXERCISE) || this.equals(CHARUFGABE)) {
			return true;
		} else {
			return false;
		}		 
	 }
	 
	 public int getScoreOfPattern() {
		 
		 switch (this) {
		case AUFGABE:
			return 1000;
		case EXERCISE:
			return 800;
		case INTDOTINTDOT:
			return 500;
		case INTDOTINT:
			return 300;
		case INTDOTBRACKET:
			return 200;
		case INTBRACKET:
			return 150;
		case INTCOLON:
			return 125;
		case INTDOT:
			return 100;
		case INT:
			return 50;
		default:
			return 1;
		}
	 }
	 
}
