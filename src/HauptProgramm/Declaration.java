/**
 * 
 */
package HauptProgramm;

import java.util.ArrayList;
import java.util.regex.Pattern;

import aufgaben_db.Global;
import aufgaben_db.Muster;


/**
 * Klasse die eine Aufgabendeklaration repraesentiert
 * 
 * @author Schweiner, J.R.I.B.-W.
 *
 */
public class Declaration {

	/*====== ATTRIBUTES ==========================================================*/
	// Das Muster, welches auf diese Deklaration gepasst hat.
	// CHANGED: Use a helper function to determine the Muster from the Pattern
	//          as the pattern shall be user customizable via the filelink variable: splitby. 
	private Pattern matchedPattern;
	
	// Speichert das erste, zweite und dritte Wort der Aufgabendeklaration
	// drittes Wort wird bisher nichts / kaum benutzt.
	private String firstWord = ""; 	/*of/after declaration!*/
	private String secondWord = "";	/*of/after declaration!*/
	private String thirdWord = ""; 	/*of/after declaration!*/
	// Alternatively/Additionally for also covering "Lösung zu 1)" for example?
	private String lineContent = "";/*firstWord here not necessarily == declaration.firstWord*/
	
	// Speichert die Ersten Worte der der Deklaration folgenden Aufgabe.
	private String[] head;
	private boolean hasHead = false;
	
	// Zeile, in der die Deklaration gefunden wurde.
	private int lineNumber;
	
	// Index, dem diese Deklaration zugeordnet werden kann
	private IndexNumber index;
	
	
	
	/*====== CONSTRUCTORS ========================================================*/
	//lineContent is optional and currently used for debugging (validation of declaration) purposes only.
	public Declaration(Muster m, String firstWord, int lineNumber) {
		this(m.getPattern(), firstWord, lineNumber, "");
	}
	public Declaration(Muster m, String firstWord, int lineNumber, String lineContent) {
		this(m.getPattern(), firstWord, lineNumber, lineContent);
	}
	public Declaration(Pattern pattern, String firstWord, int lineNumber) {
		this(pattern, firstWord, lineNumber, "");
	}
	public Declaration(Pattern pattern, String firstWord, int lineNumber, String lineContent) {
		this.setMatchedPattern(pattern);
		this.setFirstWord(firstWord);
		this.setLineNumber(lineNumber);
	}


	
	/*====== METHODS =============================================================*/
	@Override
	public boolean equals(Object o) {
		return o == this
				||
				
				(o instanceof Declaration)
				//&& ((Declaration) o).getIndex().equals(this.getIndex())
				&& ((Declaration) o).toString().equals(this.toString())
				&& 
				(
						!((Declaration) o).hasHead() && !this.hasHead()
						||
						((Declaration) o).hasHead() && this.hasHead()
						&& ((Declaration) o).getHead().length == this.getHead().length
						&& Global.arrayToString(((Declaration) o).getHead())
						.equals( Global.arrayToString(this.getHead()) )
				)
		;
	}
	/**
	 * @return Null if no word contains a potential exercise numbering.
	 */
	public String getWordContainingNumbering() {
		//First check if it can be parsed to integer, then check if this parsed value is greater than
		//the next exercise's numbering:
		if (Global.containsInt(firstWord)) {
			return firstWord;
		}
		if (Global.containsInt(secondWord)) {
			return secondWord;
		}
		if (Global.containsInt(thirdWord)) {
			return thirdWord;
		}
		
		return null;
			
	}
	
	
	
	
	
	
	/**
	 * @return the matchedPattern
	 */
	public Pattern getMatchedPattern() {
		return matchedPattern;
	}

	/**
	 * @param matchedPattern the matchedPattern to set
	 */
	public void setMatchedPattern(Pattern matchedPattern) {
		this.matchedPattern = matchedPattern;
	}

	/**
	 * @return the firstWord
	 */
	public String getFirstWord() {
		return firstWord;
	}

	/**
	 * @param firstWord the firstWord to set
	 */
	public void setFirstWord(String firstWord) {
		this.firstWord = firstWord;
	}

	/**
	 * @return the secondWord
	 */
	public String getSecondWord() {
		return secondWord;
	}

	/**
	 * @param secondWord the secondWord to set
	 */
	public void setSecondWord(String secondWord) {
		this.secondWord = secondWord;
	}

	/**
	 * @return the thirdWord
	 */
	public String getThirdWord() {
		return thirdWord;
	}

	/**
	 * @param thirdWord the thirdWord to set
	 */
	public void setThirdWord(String thirdWord) {
		this.thirdWord = thirdWord;
	}
	
	/**
	 * Convenience method, without it it's still possible to access the same via plaintext[lineNumber].
	 * @return The complete line of the found declaration.
	 */
	public String getLineContent() {
		return lineContent;
	}
	
	/**
	 * @return the line
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param line the line to set
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the index
	 */
	public IndexNumber getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(IndexNumber index) {
		this.index = index;
	}
	
	/**
	 * Setz den Index dieser Deklaration zurueck (leert die entsprechende ArrayList)
	 */
	public void resetIndex() {
		try {
			this.index.getIndex().clear();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Ueberprueft, ob diese Deklaration einen Index besitzt.
	 * @return true, falls ja, false, falls nein.
	 */
	public boolean hasIndex() {
		try {
			if (this.index.getIndex().size() >= 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Gibt eine String-Repraesentation der Deklaration zurueck.
	 */
	public String toString() {//hi wie geht es dir? wie einer GeWolke, es regnet den ganzen Tag.
		String output = "Deklaration {gefunden mit Muster: " + this.matchedPattern + "; \t" +
		" First Word: " + this.firstWord + "; \tSecond Wort: " + this.secondWord + ";\t Third Wort: " + this.thirdWord;
		if (this.hasIndex()) {
			output = output + " \t IndexNR.: " + this.index.toString();
		} else {
			output = output + " \t IndexNR.: None";
		}
		return output;
	}
	
	/**
	 * @return the head
	 */
	public String[] getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(String[] head) {//ArrayList<String> head) {
		this.head = head;
		this.hasHead = true;
	}
	
	/**
	 * Ueberprueft, ob die Deklaration einen Head (Erste 3 bis 4 Wörter der folgenden Aufgabe) hat.
	 * @return
	 */
	public boolean hasHead() {
		return hasHead;
	}
	
	
	
	
	
	
}
