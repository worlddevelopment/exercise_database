package aufgaben_db;

import java.util.regex.Pattern;



/**
 * Class representing a an exercise declaration.
 *
 * @author Schweiner, J.R.I.B.-W., worlddevelopment
 *
 */
public class Declaration {

	/*====== ATTRIBUTES
	/**
	 * The Muster (pattern wrapper) matching this declaration.
	 * Use a helper function to determine the Muster from the Pattern.
	 * as the pattern shall be user customizable via the filelink
	 * variable: splitby.
	 */
	private /*Pattern*/Muster matchedPattern;

	/**
	 * Stores the first, second and third word of the declaration.
	 * The third word is hardly used currently.
	 */
	private String firstWord = ""; // of/after declaration!
	private String secondWord = ""; // of/after declaration!
	private String thirdWord = ""; // of/after declaration!
	/**
	 * Alternatively/Additionally for also covering e.g. "Solution to 1)"
	 * lineContent.firstWord is not necessarily == declaration.firstWord
	 */
	private String lineContent = "";

	/**
	 * Stores the first words of the exercise following the declaration:
	 */
	private String[] head;
	private boolean hasHead = false;

	/**
	 * Line in which the declaration has been found.
	 */
	private int lineNumber;
	/**
	 * Store declaration preceding words to assist with allIn1Line docs.
	 * TODO Reevaluate whether lineNumbers, succeding words suffice.
	 */
	private int wordsBeforeDeclarationCount;

	/**
	 * Index, this declaration can be assigned to.
	 */
	private IndexNumber index;



	// ======= CONSTRUCTORS
	/**
	 * Declaration constructor.
	 * lineContent is optional and currently used for debugging
	 * (validation of declaration) purposes only.
	 * TODO Does using it increase rigidity|flexibility of detection?
	 */
	public Declaration(Pattern p, String firstWord, int lineNumber) {
		this(p, firstWord, lineNumber, "");
	}

	public Declaration(Pattern p, String firstWord, int lineNumber
			, String lineContent) {
		this(Muster.getMusterFromPattern(p)//.getPattern()
				, firstWord, lineNumber, lineContent);
	}

	public Declaration(/*Pattern*/Muster pattern, String firstWord
			, int lineNumber) {
		this(pattern, firstWord, lineNumber, "");
	}

	public Declaration(/*Pattern*/Muster pattern, String firstWord
			, int lineNumber, String lineContent) {
		this.setMatchedPattern(pattern);
		this.setFirstWord(firstWord);
		this.setLineNumber(lineNumber);
		this.lineContent = lineContent;
	}




	// ======= METHODS
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
					&& ((Declaration) o).getHead().length
						== this.getHead().length
					&& Global.arrayToString(((Declaration) o).getHead())
						.equals( Global.arrayToString(this.getHead()) )
				)
		;
	}



	/**
	 * @return Null if no word contains a potential exercise numbering.
	 */
	public String getWordContainingNumbering() {
		// First check if it can be parsed to integer then check if this
		// parsed value is greater than the next exercise's numbering:
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
	public /*Pattern*/Muster getMatchedPattern() {
		return matchedPattern;
	}

	/**
	 * @param matchedPattern the matchedPattern to set
	 */
	public void setMatchedPattern(/*Pattern*/Muster matchedPattern) {
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
	 * Convenience method, without it it's still possible to access
	 * the same via plaintext[lineNumber].
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
	 * Reset to empty. Clear this index' number's list of chars.
	 */
	public void resetIndex() {
		try {
			this.index.getIndex().clear();
		} catch (Exception e) {
		}
	}

	/**
	 * Check whether this declaration has an index.
	 *
	 * @return true if there is an index. Else false.
	 */
	public boolean hasIndex() {
		if (this.index != null) {
			if (this.index.getIndex().size() > 0) {
				return true;
			}
			System.out.println("Declar. has index, but length is < 1.");
			//return false; TODO reenable when content check below fails
		}
		// Search the head for an index:
		// TODO For avoiding user confusion by being able to remove it,
		// it may be useful to check for a hard coded index.
		// e.g. with a markup pattern it is possible the content part
		// still harbors such an undesired index.
		return false;
	}

	/**
	 * Get a string representation of the declaration.
	 *
	 * @return A string representation of this declaration.
	 */
	public String toString() {
		String output = "Declaration {found with Muster: "
			+ this.matchedPattern + "; \t" + " First Word: "
			+ this.firstWord + "; \tSecond Wort: " + this.secondWord
			+ ";\t Third Wort: " + this.thirdWord;
		if (this.hasIndex()) {
			output = output + " \t IndexNR.: " + this.index.toString();
		}
		else {
			output = output + " \t IndexNR.: None";
		}
		return output + "}";
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
	public void setHead(String[] head) {
		this.head = head;
		this.hasHead = true;
	}

	/**
	 * Checks whether the declaration has a head (first 3..4 words of
	 * the succeding content(execise|solution|...).
	 *
	 * @return true if there is a head.
	 */
	public boolean hasHead() {
		return hasHead;
	}






}
