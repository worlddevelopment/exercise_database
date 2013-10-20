/**
 * 
 */
package aufgaben_db;


import java.util.regex.Pattern;
/**
 * Enum, die die regulaeren Ausdruecker verwaltet, mit denen die Aufgabendeklarationen gesucht werden.
 * 
 * J.R.I.B.-W. The default repository for patterns to find exercise declarations.
 * Lecturers can specify their own patterns via the splitby|splitter|pattern scheme in the sheet's filelink. 
 * 
 * @author Schweiner
 *
 */
public enum Muster {
	AUFGABE("Aufgabe"), // hier noch Praefix erlauben.
	EXERCISE("Exercise"), 
	CHARUFGABE("[\\S]*ufgabe[\\S]*"), // Here is the prefix version, i.e. Uebungsaufgabe is covered.
	INTDOTINTDOT("([\\d]+[\\.]){2}"), // ueberfluessig
	INTDOTINT("[\\d]+[\\.][\\d]+"), 
	INTDOTBRACKET("[\\d]+[\\.][)]"),
	INTBRACKET("[\\d]+[\\.][)]"),
	INTCOLON("[\\d]+[\\:]"),
	INTDOT("[\\d]+[\\.]"/*[^\\d]"*/), //Tests ob Leerzeichen folgen, müssen unterscheiden, ob per Wort
	                                  //einzeln oder per Zeile im Ganzen ein Regex geprüft wird. 
	INT("[\\d]+[)]"),                 // leerzeichen danach, niedriger Score!!!
	CHARINTCHAR("[\\D]*[\\d][\\S]*");
	
	// auch noch: nur Zahl (INT)
	
	 private String pattern;

	 private Muster(String c) {
	   pattern = c;
	 }

	 /**
	  * gibt den kompilierten Regulaeren Ausdruck des entsprechenden Musters aus
	  * 
	  * @return kompilierter regulaerer Ausdruck
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
			return 1; /*user customized patterns get 0 points, so obtain lowest priority
			as it's quite probable that more things go wrong for the all patterns not
			specified in this class. If you can think of another good pattern, simply add it. */
		}
	 }
	 
	 
	 /**
	  * This is for quickly determining the Muster to a pattern if any.
	  * e.g. for checking if a pattern is covered in the default repository of exercise declaration patterns.  
	  * @param pattern
	  * @return
	  */
	 public static Muster getMusterFromPattern(Pattern pattern) {
		 for (Muster m : Muster.values()) {
			 if (m.getPattern().equals(pattern)) {
				 return m;
			 }
		 }
		 return null; /*make sure that this quite common case (because of user customizable patterns) is checked for.
		 It's no problem if there is no Muster to a Pattern. These Muster only are the default repository of patterns.*/
	 }
	 
	 
	 
	 /**
	  * 
	  * @param pattern
	  * @return
	  */
	 public static String getValueByPattern(Pattern pattern) {
		 for (Muster m : Muster.values()) {
			 if (m.getPattern().pattern().equals(pattern.pattern())) {
				 return m.name();/*enum name or enum value*/
			 }
		 }
		 return null;
	 }
	 
	 
}
