package rtf;


import java.io.IOException;

import aufgaben_db.Sheetdraft;
import aufgaben_db.Global;


/**
 * Text based formats like MD,RST,RTF,TEX require some processing
 * to find the correct positions to cut content parts at to remove
 * as little relevant style, definitions, custom commands, et alia.
 * TODO Other markup formats like Node|Tree based require adaption of
 * data stored in a Declaration. Only then a (post)processor like this
 * can be added for these.
 */
public class TextMarkupProcessor {


	// ======= ATTRIBUTES

	private Sheetdraft sheetdraft = null;

	// Store document begin, end words to know where to stop cutting:
	public String lastWord = null;
	public int lastWordLineNumber = -1;
	public int lastWordPosition = -1; // char index within line

	public String firstWord = null;
	public int firstWordLineNumber = -1;
	public int firstWordPosition = -1; // char index within line




	// ======= CONSTRUCTOR

	public boolean TextMarkupProcessor(Sheetdraft sheetdraft)
		throws IOException {

		this.sheetdraft = sheetdraft;
		findAndPopulateFirstWordData();
		findAndPopulateLastWordData();

		cut();

	}




	// ======= METHODS

	/**
	 * Called from constructor.
	 *
	 * @return true if successful
	 */
	public boolean findAndPopulateFirstWordData() {

		firstWord = findPlainTextFirstWord();
		System.out.println("1st word: " + firstWord);

		int[] linesW1 = Global.getLineNumbersContaining(sheetdraft.getRawContent(), firstWord);
		System.out.println("Occurrences in RTF: " + linesW1.length);

		if (linesW1.length < 1) {
			String text = "DocBegin as derived from plain text was"
				+ " not found in RTF document: " + firstWord
				+ " => Can not remove header.";
			System.out.println(text);
			return false;
		}
		else if (linesW1.length > 1) {
			System.out.println("RTF: The firstWord|DocBegin occurs in"
					+ " more than one line.");
		}


		// 3 possibilities:
		int phraseCountPT = Global.getPhraseCount(plainText, firstWord);

		System.out.println("Occurrences in plain text: " + phraseCountPT);

		if (linesW1.length == phraseCountPT) {
			System.out.println("The word occurs equally as often"
					+ " in the plain text as in the raw content.");
		}
		else if (linesW1.length < phraseCountPT) {
			String text = "The within plain text found word for"
				+ " DocBegin exists less often in the RTF than in"
				+ " the plain text."
				+ " A word may have been split in the RTF."
				+ " The first occurrence is chosen. May be wrong."
				;
			System.out.println(text);
		}
		else if (linesW1.length > phraseCountPT) {
			String text = "The within plain text found word for"
				+ " DocBegin exists more often in the RTF than in"
				+ " the plain text."
				+ " The word may be part of a style or RTF syntax."
				+ " The first occurrence is chosen. May be wrong."
				;
			System.out.println(text);
		}


		// DocBegin => choose first occurrence within RTF
		firstWordLineNumber = linesW1[0];
		firstWordPosition = MethodenRtf
			.indexOf((sheetdraft.getRawContent()[firstWordLineNumber], firstWord);


		// If the first word contains a special character, then it is
		// modified partly already in the .txt-Dokument or at latest
		// in the RTF. It is often split and parts wrapped.
		// TODO Support split words to not have to removes Umlauts.
		// For RTF text this workaround works not so good because
		// the identifiers are not positioned at the beginning
		// of a line.
		// Thus it is only deleted after this occurrence, potentially
		// leaving back the Special char when it is located at the
		// beginning of the word.
		// This first letter then occurs ahead of every found part.

		// FIXME Hard coded special case workaround for only &Uuml;:
		// *bung...:
		// Search for uppercase UE at the beginning:
		// If first found word begins with bung, bungen, bungsblatt,...
		boolean hasUmlaut = firstWord.startsWith("bung");
		int umlautPosition = firstWordPosition;
		if (hasUmlaut) {
			// 'dc steht im rtf-Format fuer "Ue"
			umlautPosition = sheetdraft.getRawContent()[firstWordLineNumber].indexOf("'dc");
			if (umlautPosition == -1
					|| umlautPosition > firstWordPosition) {

				String text = "First word likely begins with Umlaut."
					+ " Unfortunately it could not be identified."
					+ " Likely this character will remain in the doc.";
				System.out.println(text);
				hasUmlaut = false;
			}
			else {
				// Umlaut exists in the same line, before first word
				// in the RTF, then remove starting from that position:
				firstWordPosition = umlautPosition;
				//firstWordPosition = MethodenRtf
				//	.indexOf((sheetdraft.getRawContent()[lineErstesWort], "'dc");
			}
		}

		System.out.println("RTF line where the first word occurs:");
		System.out.println(sheetdraft.getRawContent()[firstWordLineNumber]);
		System.out.println("Index of char: " + firstWordPosition);

		return true;
	}



	/**
	 * Called from constructor.
	 *
	 * @return true if successful
	 */
	public boolean findAndPopulateLastWordData() {

		lastWord = findPlainTextLastWord();
		System.out.println("Last word in plain text: " + lastWord);

		int[] linesW2 = Global.getLineNumbersContaining(sheetdraft.getRawContent(), lastWord);
		System.out.println("Occurrences in RTF: " + linesW2.length);

		if (linesW2.length < 1) {
			String text = "Identifier for DocEnd: " + lastWord
			+ " was not found in the RTF document. => Removal of last"
			+ " exercise impossible.";
			System.out.println(text);
			return null;
		}
		if (linesW2.length > 1) {
			System.out.println("RTF: The lastWord|DocEnd occurs in"
					+ " more than one line.");
		}

		int countInPT = Global.getPhraseCount(plainText, lastWord);
		System.out.println("Occurrences in plain text: " + countInPT);


		// 3 possibilities:
		// TODO Move to function, use both here and above for firstWord
		if (linesW2.length == countInPT) {
			System.out.println("#rtfLastWord==#plainTextLastWord");
		}
		else if (linesW2.length < phraseCountPT) {
			String text = "DocEnd identifier occurs less often"
				+ " in the RTF than in the plain text."
				+ " A word may have been split in the RTF."
				+ " The first occurrence is chosen. May be wrong."
				;
			System.out.println(text);
		}
		else if (linesW2.length > phraseCountPT) {
			String text = "The within plain text found word for"
				+ " DocBegin exists more often in the RTF than in"
				+ " the plain text."
				+ " The word may be part of a style or RTF syntax."
				+ " The first occurrence is chosen. May be wrong."
				;
			System.out.println(text);
		}


		lastWordLineNumber = linesW2[linesW2.length - 1];
		lastWordPosition = MethodenRtf
			.indexOf((sheetdraft.getRawContent()[lastWordLineNumber], lastWord);
		System.out.println("RTF line of lastWord:");
		System.out.println(sheetdraft.getRawContent()[lastWordLineNumber]);

		// Point to the last letter of the word as it is last word:
		lastWordPosition = lastWordPosition + lastWord.length() - 1;
		System.out.println("@line: " + lastWordLineNumber
				+ " ends @position: " + lastWordPosition);

		return true;

	}



	/**
	 * Costly, thus cache the first, last word.
	 * TODO Always ensure the cache is valid.
	 */
	public String findPlainTextFirstWord(String plainText) {
		return WordSearch
			.sucheErstesWortImDoc(plainText);
	}

	public String findPlainTextLastWord(String plainText) {
		return WordSearch
			.sucheLetztesWortImDoc(textdoc);
	}



	/**
	 * Cut and store the content parts to disk or database.
	 */
	@Override
	public boolean cut(DeclarationSet set) throws IOException {

		System.out.println("TextMarkupProcessor: cut()");
		if (set == null) {
			return false;
		}
		int count = set.getDeclarations().size();
		System.out.println("Content parts count: " + count);
		if (count < 1) {
			System.out.println("No decalarations. Aborting ...");
			return false;
		}

		boolean areInitialBracketPairsIntact
			= areBracketPairsIntact(rtf);

		// For every declaration generate|store a content part:
		int d_index = 0;
		//for (Declaration d : set.getDeclarations()) {
		while (d_index < set.getDeclarations().size()) {
			Declaration d = set.getDeclarations().get(d_index);
			if (d == null) {
				System.out.println("cut: declaration is null. Skipping ...");
				continue;
			}

			String[] rtf = sheetdraft.getRawContent().clone();
			int lineNumber = d.getLineNumber();


			boolean isAMiddlePart = d_index > 0 && d_index < count - 1;
			String docBegin = rtf[firstWordLineNumber].substring(
					0, firstWordPosition);
			String docEnd = rtf[lastWordLineNumber].substring(
					lastWordPosition, rtf[lastWordLineNumber].length());


			// All raw content within one line?
			// TODO Operate DeclarationFinder on the raw content! Else
			// the line number may be all 1 only for plain text while
			// for raw content it would be fine, and thus there'd be
			// no useful relation between line numbers which is req.
			boolean allWithinOneLine = true;
			if (firstWordLineNumber < lastWordLineNumber) {
				allWithinOneLine = false;
			}
			if (firstWordLineNumber > lineNumberPartEnd) {
				System.out.println("Part comes before document content."
					+ " This should be impossible. Skipping ...");
				continue;
			}


			// Detect part start, end:
			int partStartLineNumber = lineNumber;
			int partStartPosition = d.getMatchStart();

			// May not exist, thus is called later after having checked
			// that it is not the last content part declaration.
			//int partEndLineNumber = d_succeding.getLineNumber();
			//int partEndPosition = d_succeding.getMatchStart();


			if (allWithinOneLine) {
				// TODO Reuse the inner loop from the else case.
			}
			// not within one line
			else {
				// Is it not 1st part? Only then travel up is required.
				if (d_index > 0 || isAMiddlePart) {
					// Detect part start:
					// Travel towards beginning until no more control word:
					int partStartLineNumberCandidate = -1;
					int partStartPositionCandidate = -1;
					int succedingAlphaNumericCharCount = 0;
					int groupDepth = 0;
					for (int l = partStartLineNumber
							; l > firstWordLineNumber; --l) {
						String line = rtf[l];
						int i = line.length();
						if (l == partStartLineNumber) {
							i = partStartPosition;
						}
						// Travel along control words|symbols.
						while (--i > -1) {
							char c = line.charAt(i);
							// Skip groups entirely:
							if (groupDepth > 0) {
								// One group level terminated?
								if (c != '{') {
									continue;
								}
								else if (c == '{') {
									groupDepth++;
									continue;
								}
								//else
								groupDepth--;
							}
							//else Not within a group!

							if (!c.matches("[a-zA-Z0-9]") && c != ' ') {
								succedingAlphaNumericCharCount = 0;
							}
							// Control words are only lower case.
							// biblioscape.com/rtf15_spec.htm
							// However we are interested in plain text:
							else if (c.matches("[a-zA-Z0-9]")) {
								isSuccedingCharAlphaNumeric++;
								// If this turns out to be plain text,
								// then the succeding char is the start.
								partStartLineNumberCandidate = l;
								partStartPositionCandidate
									= i + succedingAlphaNumericCharCount;
								// TODO Support multiple line breaks:
								if (partStartPositionCandidate > lineL) {
									partStartPositionCandidate %= lineL;
									partStartLineNumberCandidate++;
								}
							}
							else if (c == '}') {
								groupDepth++;
								continue;
							}
							// Stop on the first plain text occurring,
							// id est when a space occurs before text!
							else if (c == ' '
									&& succedingAlphaNumericCharCount > 0) {
								partStartLineNumber
									= partStartLineNumberCandidate;
								partStartPosition
									= partStartPositionCandidate;
								break;
							}
						}
					}
				}

				// Is not last part? Only then travel down is required:
				if (d_index < count - 1 || isAMiddlePart) {
					// Detect part end:
					// Travel from the succeding part's beginning to end
					// until no more closing bracket or one opening bracket.
					Declaration d_succeding
						= set.getDeclarations().get(d_index + 1);
					int partEndLineNumberCandidate = -1;
					int partEndPositionCandidate = -1;
					int succedingAlphaNumericCharCount = 0;
					int groupDepth = 0;
					// Support part start, end at the same line number.
					for (int l = d_succeding.getLineNumber()
							; l > partStartLineNumber - 1; --l) {

						String line = rtf[l];
						int lineL = line.length();
						int i = lineL;
						if (l == d_succeding.getLineNumber()) {
							i = d_succeding.getMatchStart();
						}
						// Travel along control words|symbols.
						while (--i > -1) {
							char c = line.charAt(i);
							// Skip groups entirely:
							// TODO Plain text in groups is possible!
							if (groupDepth > 0) {
								// One group level terminated?
								if (c != '{') {
									continue;
								}
								else if (c == '{') {
									groupDepth++;
									continue;
								}
								//else
								groupDepth--;
							}
							//else Not within a group!

							if (!c.matches("[a-zA-Z0-9]") && c != ' ') {
								succedingAlphaNumericCharCount = 0;
							}
							// Control words are only lower case.
							// biblioscape.com/rtf15_spec.htm
							// However we are interested in plain text:
							else if (c.matches("[a-zA-Z0-9]")) {
								succedingAlphaNumericCharCount++;
								// If this turns out to be plain text,
								// then the succeding char is the start.
								partEndLineNumberCandidate = l;
								partEndPositionCandidate
									= i + succedingAlphaNumericCharCount;
								// TODO Support multiple line breaks:
								if (partEndPositionCandidate > lineL) {
									partEndPositionCandidate %= lineL;
									partEndLineNumberCandidate++;
								}
							}
							else if (c == '}') {
								groupDepth++;
								continue;
							}
							// Stop on the first plain text occurring,
							// id est when a space occurs before text!
							else if (c == ' '
									&& succedingAlphaNumericCharCount > 0) {
								partEndLineNumber
									= partEndLineNumberCandidate;
								partEndPosition
									= partEndPositionCandidate;
								break;
							}
						}
					}

				}
			} // Not within one line -End


			// Bracket test:
			boolean areBracketPairsIntact = false;
			int[] partMiddleBracketPairCount = getBracketPairCount(
					rtf, firstWordLineNumber + 1, lineNumber - 1);
			int[] partStartLineBracketPairCount = getBracketPairCount(
					rtf[partStartLineNumber]);
			int[] partEndLineBracketPairCount = getBracketPairCount(
					rtf[partEndLineNumber]);

			int[] partBracketPairCount = new int[2];
			for (int i = 0; i <= 1; i++) {
				partBracketPairCount[i]
					= partStartLineBracketPairCount[i]
					+ partMiddleBracketPairCount[i]
					+ partEndLineBracketPairCount[i];
				System.out.println("partBracketPairCount: " + i + ": "
						+ partBracketPairCount[i]);
			}

			if (partBracketPairCount[0] == partBracketPairCount[1]) {
				areBracketPairsIntact = true;
			}
			else {
				System.out.println("#{, #} differ. RTF may be invalid");
				if (!areInitialBracketPairsIntact) {
					System.out.println("Initial bracket pairs were"
							+ " already corrupted.");
				}
			}


			// Delete all from content start till the part begins:
			if (allWithinOneLine) {
				rtf[lineNumber] = docBegin + rtf[lineNumber].substring(
					partStartPosition, partEndPosition + 1) + docEnd;
			}
			else {
				// For index validity start removing at the end:

				// Keep footer intact: Skipped if last content part:
				if (d_index < count - 1) {
					// Not last content part.
					rtf[lastWordLineNumber] = docEnd;
					rtf = Sheetdraft.loescheZeilen(rtf
						, lastWordLineNumber + 1
						, lineNumberPartStart - 1);
					rtf = Sheetdraft.loescheZeilen(rtf
						, firstWordLineNumber + 1
						, lineNumberPartStart - 1);
				}

				// Keep header intact: Skipped if first content part:
				if (d_index > 0) {
					// Not first content part.
					rtf[firstWordLineNumber] = docBegin;
					rtf = Sheetdraft.loescheZeilen(rtf
						, lastWordLineNumber + 1
						, lineNumberPartStart - 1);
					rtf = Sheetdraft.loescheZeilen(rtf
						, firstWordLineNumber + 1
						, lineNumberPartStart - 1);
				}
			}


			System.out.println("Extracted content part: " + rtf);

			// Increment earlier to write exercise 0 as 1 to disk.
			d_index++;

			// Store:
			String filePath = Global.buildRootPath(sheetdraft
					.getFilelinkRelative() + "__"
					+ d.getMatchedPattern().name() + "_"
					+ d_index + ".rtf");

			ReadWrite.writeText(rtf, filePath);
			System.out.println(d_index + " was extracted, written as"
					+ filePath);

		}
		return true;
	}



	/**
	 * Get the number of occurrences of a char in a String.
	 *
	 * @param line String to search in
	 * @param char to count
	 * @return number of occurrences
	 */
	public int getCharCount(String line, char c) {

		System.out.println("char to count: " + c);
		int count = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == c) {
				//System.out.println("Found in line: " + i);
				count++;
			}
		}
		System.out.println("#c: " + count);
		return count;
	}



	/**
	 * Get the number of occurrences of a char within an array of String
	 *
	 * @param text String[] to search in.
	 * @param c char to count
	 * @return number of occurrences
	 */
	public int getCharCount(String[] text, char c) {

		int sum = 0;
		for (int i = 0; i < text.length; i++) {
			//System.out.println(text[i]);
			int count = getCharCount(text[i], c);
			sum += count;
		}
		return sum;
	}



	/**
	 * Checks the validity of brackets. (#opening == #closing)
	 *
	 * @param text String[] in which to check the bracket pair validity.
	 * @return true if equal amount of opening, closing brackets.
	 */
	public boolean areBracketPairsIntact(String[] text) {

		int bracketOpeningCount = getCharCount(text, '{');
		int bracketClosingCount = getCharCount(text, '}');
		System.out.println("#{ " + bracketOpeningCount);
		System.out.println("#} " + bracketClosingCount);
		int differenz = bracketOpeningCount - bracketClosingCount;
		return differenz == 0;
	}


	/**
	 * Count the brackets within an array of String in the interval defined
	 * by start and end.
	 *
	 * @param text where the brackets are contained.
	 * @param start inclusive
	 * @param end inclusive
	 * @return array containing 2 entries: #{, #}
	 */
	public int[] getBracketPairCount(String[] text, int start, int end) {

		int bracketOpeningCountTotal = 0;
		int bracketClosingCountTotal = 0;
		char c1 = '{';
		char c2 = '}';
		for (int i = start; i < end + 1; i++) {
			int bracketOpeningCount = getCharCount(text[i], c1);
			bracketOpeningCountTotal += bracketOpeningCount;
			int bracketClosingCount = getCharCount(text[i], c2);
			bracketClosingCountTotal += bracketClosingCount;
		}
		System.out.println("#{ " + bracketOpeningCountTotal);
		System.out.println("#} " + bracketClosingCountTotal);

		int[] bracketPairCount = new int [2];
		bracketPairCount[0] = bracketOpeningCountTotal;
		bracketPairCount[1] = bracketClosingCountTotal;
		return bracketPairCount;
	}



	/**
	 * Get the count of opening and closing brackets within a String.
	 *
	 * @param line The String to search the char in.
	 * @return Array with 2 entries: #{ and #}
	 */
	public int[] getBracketPairCount(String line) {

		char c1 = '{';
		char c2 = '}';
		int bracketOpeningCountTotal = getCharCount(line, c1);
		int bracketClosingCountTotal = getCharCount(line, c2);
		System.out.println("#{ " + bracketOpeningCountTotal);
		System.out.println("#} " + bracketClosingCountTotal);

		int[] bracketPairCount = new int [2];
		bracketPairCount[0] = bracketOpeningCountTotal;
		bracketPairCount[1] = bracketClosingCountTotal;
		return bracketPairCount;
	}



}
