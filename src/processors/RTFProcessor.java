package rtf;


import java.io.IOException;

import core.Sheetdraft;
import core.Global;


public class RTFProcessor extends TextMarkupProcessor {


	// ======= CONSTRUCTOR

	public boolean RTFProcessor(Sheetdraft sheetdraft)
		throws IOException {
		super(sheetdraft);
	}




	// ======= METHODS

	/**
	 * Cut and store the content parts to disk or database.
	 */
	public boolean cut(DeclarationSet set) throws IOException {

		System.out.println("RTFProcessor: cut()");
		if (set == null) {
			return false;
		}
		int count = set.getDeclarations().size();
		System.out.println("RTF: Content parts count: " + count);
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

			// Increment earlier to write part 0 as 1 to disk.
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




}
