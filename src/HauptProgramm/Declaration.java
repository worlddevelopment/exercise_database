/**
 * 
 */
package HauptProgramm;

import java.util.ArrayList;

import aufgaben_db.Muster;


/**
 * Klasse die eine Aufgabendeklaration repraesentiert
 * 
 * @author Schweiner
 *
 */
public class Declaration {

	// Das Muster, welches auf diese Deklaration gepasst hat
	private Muster matchedPattern;
	
	// Speichert das erste, zweite und dritte Wort der Aufgabendeklaration
	// drittes Wort wird bisher nichts / kaum benutzt.
	private String firstWord = "";
	private String secondWord = "";
	private String thirdWord = "";
	
	// Speichert die Ersten Worte der der Deklaration folgenden Aufgabe.
	private ArrayList<String> head = new ArrayList<String>();
	private boolean hasHead = false;
	
	// Zeile, in der die Deklaration gefunden wurde.
	private int line;
	
	// Index, dem diese Deklaration zugeordnet werden kann
	private IndexNumber index;
	
	public Declaration(Muster pattern, String firstWord, int line) {
		this.setMatchedPattern(pattern);
		this.setFirstWord(firstWord);
		this.setLine(line);
	}

	/**
	 * @return the matchedPattern
	 */
	public Muster getMatchedPattern() {
		return matchedPattern;
	}

	/**
	 * @param matchedPattern the matchedPattern to set
	 */
	public void setMatchedPattern(Muster matchedPattern) {
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
	 * @return the line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @param line the line to set
	 */
	public void setLine(int line) {
		this.line = line;
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
	 * Gibt eine String-Repräsentation der Deklaration zurück.
	 */
	public String toString() {
		String output = "Gefunden mit Muster: " + this.matchedPattern + " \n" +
	"Erstes Wort " + this.firstWord + " \n Zweites Wort: " + this.secondWord + "\n Drittes Wort: " + this.thirdWord;
	if (this.hasIndex()) {
		output = output + " \n IndexNummer: " + this.index.toString();
	} else {
		output = output + " \n Indexnummer: Ohne Index ";
	}
		return output;
	}

	/**
	 * @return the head
	 */
	public ArrayList<String> getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(ArrayList<String> head) {
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
