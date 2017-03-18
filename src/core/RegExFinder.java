package aufgaben_db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Functionality from here is called from e.g. DeclarationFinder.
 * TODO As it uses isWordedPattern extensively, this needs to be
 * updated heavily following (best a bit more dynamic approach)
 * https://github.com/worlddevelopment/part_database/issues/1
 *
 * @author sabine, J.R.I. Balzer-Wein, worlddevelopment
 */
public class RegExFinder {


	/**
	50 parts are a maximum to be expected, especially as this
	here only serves as a safety feature against the case that all
	words of the document are within one line. If we assume that
	multiple sheets or multiple pages of one sheet could produce
	one line for each sheet/page, then this 10 page limit with each
	3 parts per page will be taken as an upper bound.
	This results in 10 * 3 = 30 parts to store. Now to be safe
	10 * 5 = 50 is taken as upper bound.
	*/
	final static int PART_COUNT_EXPECTED_MAXIMUM_IF_ALL_TEXT_IN_ONE_LINE = 50;



	/**
	 * Search content part declarations in the given text array of String.
	 *
	 * @param text lines of text to evaluate
	 * @param muster a declaration pattern
	 * @return DeclarationSet: empty if no declarations are found.
	 */
	public DeclarationSet sucheMuster(String[] text, Muster muster) {

		Pattern pattern = muster.getPattern();
		DeclarationSet foundDeclarations;
		foundDeclarations = new DeclarationSet(muster);

		String wort;
		boolean gleich;
		int zaehler = 0;
		if (text == null || text.length < 1) {
			System.out.println(Global.addMessage("Text given to"
						+ " RegExFinder.sucheMuster method was null or"
						+ " empty! Aborting ...", "danger"));
			return null;
		}
		// FIXME Hard coded: To cover e.g. 'Solution to Part 1:'
		int woerter_to_analyse_maximum = 4;
		if (text.length == 1) {
			// Then text extraction did not manage to insert line breaks!
			// Overwrite to find not just the first declaration:
			woerter_to_analyse_maximum = Integer.MAX_VALUE;
		}

		int ergebnisFuerHashMap_length = text.length;
		if (ergebnisFuerHashMap_length
				< PART_COUNT_EXPECTED_MAXIMUM_IF_ALL_TEXT_IN_ONE_LINE
				) {
			rgebnisFuerHashMap_length
				PART_COUNT_EXPECTED_MAXIMUM_IF_ALL_TEXT_IN_ONE_LINE;
		}

		if (muster.isWordedPattern()) {
		// if (zahl < 20) {
			System.out.println("RegExFinder: Search: " + muster);
			for (int zeile = 0; zeile < text.length; zeile++) {
				String[] woerter = text[zeile].split("\\s+");
				// The minimum out of both:
				//if (woerter.length > 0) {
				if (woerter.length < woerter_to_analyse_maximum) {
					woerter_to_analyse_maximum = woerter.length;
				}

				// Now examining the other words of the line too,
				// because of e.g. 'Loesung zu Aufgabe 1'.
				int word_index = 0;
				for (; word_index < woerter_to_analyse_maximum
						; word_index++) {

					wort = woerter[word_index];
					String message;
					message = "Word " + word_index + ": " + wort;
					if (woerter.length > word_index + 1) {
						message += "\t succeding: "
							+ woerter[word_index + 1];
					}
					System.out.println(message);
					//if (wort.equals("")
					///*&& TODO!emptyLineAcceptedAsPartSplitter*/)
					//	continue;
					Matcher matcher = pattern.matcher(wort);
					gleich = matcher.matches();
					if(gleich) {
						System.out.println("Match@Line: " + zeile);

						Declaration loopDeclaration
							= new Declaration(
									muster, wort, zeile, text[zeile]
									, matcher.start(), matcher.end()
									, matcher.group());

						// Store succeding words:
						// Also store index if an index pattern matched.
						storeSubsequentWordsAndLookForIndexTherein(
								loopDeclaration, woerter);

						// head := begin of content part
						storeHeadWords(loopDeclaration, text, zeile);

						// Is this a double?
						boolean is_this_a_double = false;
						for (Declaration foundDeclarationSoFar
								: foundDeclarations.declarations) {

							if (foundDeclarationSoFar
									.equals(loopDeclaration)) {
								// THIS ALREADY WORKS IF IT'S TAKEN
								// OUT THEN IT'S FOR TESTING HEADING
								// REMOVAL
								is_this_a_double = true;
							}
						}

						if (is_this_a_double) {
							System.out.println("Redundant Declaration"
									+ " skipped: "
									+ loopDeclaration.getFirstWord()
									+ " "
									+ loopDeclaration.getSecondWord()
									+ " "
									+ loopDeclaration.getThirdWord());
							continue;// with next word/line to examine
						}


						foundDeclarations.declarations
							.add(loopDeclaration);
						System.out.println("Die Deklaration lautet: "
								+ loopDeclaration.getFirstWord() + " "
								+ loopDeclaration.getSecondWord());
						zaehler++;
					}
				}
			}
		}

		// Regular expression given:
		else {
		// if (zahl > 20) {
			// Because multiple sheets could be joined in one document:
			// The heading on the new page has to be dismissed should
			// it not match the declaration 1 as else this heading
			// prevails, e.g. '1. (Part)' vs. '1. Uebungsblatt'.
			int sheetheadingDeclaration_index = 0;

			for (int zeile = 0; zeile < text.length; zeile++) {

				String[] woerter = text[zeile].split("\\s+");
				// The minimum out of both:
				//if (woerter.length > 0) {
				if (woerter.length < woerter_to_analyse_maximum) {
					woerter_to_analyse_maximum = woerter.length;
				}

				int word_index = 0;
				for (; word_index < woerter_to_analyse_maximum
						; word_index++) {

					wort = woerter[word_index];
					String message = "Word " + word_index + ": " + wort;
					if (woerter.length > word_index + 1) {
						message += "\tsucceding: " + woerter[word_index + 1];
					}
					System.out.println(message);
					Matcher matcher = pattern.matcher(wort);
					gleich = matcher.matches();
					if(gleich) {
						System.out.println("Match@Line: " + zeile);
						Declaration loopDeclaration
							= new Declaration(
									muster, wort, zeile, text[zeile]
									, matcher.start(), matcher.end(),
									matcher.group());

						// Store IndexNumber and store declaration:
						loopDeclaration.setIndex(
								RegExFinder
								.getContainedIndexNumber(wort));

						// Store the succeding words if exists:
						storeSubsequentWordsAndLookForIndexTherein(
								loopDeclaration, woerter);

						// head := beginning of content part
						storeHeadWords(loopDeclaration, text, zeile);

						// Is this a double?
						boolean is_this_a_double = false;
						for (Declaration foundDeclarationSoFar
								: foundDeclarations.declarations) {

							if (foundDeclarationSoFar
									.equals(loopDeclaration)) {

								// THIS ALREADY WORKS IF IT'S TAKEN
								// OUT THEN IT'S FOR TESTING HEADING
								// REMOVAL
								is_this_a_double = true;
							}
						}

						// That the loopDeclaration is added here is
						// precondition for following header filtering:
						foundDeclarations
							.declarations.add(loopDeclaration);
						System.out.println("Die Deklaration lautet: "
								+ loopDeclaration.getFirstWord() + " "
								+ loopDeclaration.getSecondWord() + " "
								+ loopDeclaration.getThirdWord());


						filterSuperfluousHeader(muster
								, foundDeclarations
								, loopDeclaration);


						/*
						This comes after the detection of sheetheadings
						because we need the doubles to remain because
						otherwise there is no chance to detect the
						sheetheading for documents where there are
						multiple identical parts, more sheetheadings.
						*/
						if (is_this_a_double) {
							/*
							In this order (after the detection,
							deletion of sheetheadings) the declaration
							is added first -- only to be removed at
							this point again.
							*/
							foundDeclarations.declarations
								.remove(loopDeclaration);
							System.out.println("Skipped declaration,"
									+ " as it's double: "
									+ loopDeclaration.getFirstWord()
									+ " "
									+ loopDeclaration.getSecondWord()
									+ " "
									+ loopDeclaration.getThirdWord());
							continue; // searching next word|line

						}


					zaehler++;
					}
				}
			}
		}
		System.out.println("Found " + zaehler + " matching phrases.");

		return foundDeclarations;
	}



	/**
	 *
	 * @param loopDeclaration
	 * @param text
	 * @param zeile
	 */
	private static void storeHeadWords(Declaration loopDeclaration
			, String[] text, int zeile) {

		boolean moreLines = true;
		String[] headWords;
		// Skip short lines? Why? Part bodies could be short too so
		// if we look for those this way it makes no sense.
		//while (headWords.length < 5) {
		// => nothing is skipped anymore, all lines are exermined
		try {
			zeile++;
			headWords = text[zeile].split("\\s+");
			if (headWords != null) {
				loopDeclaration.setHead(headWords);
			}

		} catch (Exception e) {
			moreLines = false;
		}
		// Now this is the first line where there are 6 or more words, so potentially the part body
		if (!moreLines) {
			// TODO Why <em>only</em> if there are more lines left? The
			// content part's head should be independent of that!?
			System.out.println("Warning: No more lines after part"
					+ " declaration!");
		}

	}



	/**
	 * Removes extra headers, e.g. when a document contains
	 * multiple sheets in a row like one on every page, then
	 * the declarations|header must be recognized.
	 * TODO Continue searching for another ascending index number series
	 *
	 * @param muster
	 * @param foundDeclarations
	 * @param loopDeclaration
	 */
	public filterSuperfluousHeader(muster
			, foundDeclarations, loopDeclaration) {

		// One of the cases where the sheet's main heading
		// (e.g. Partsheet xy) could be recognized as part?
		if (!muster.isWordedPattern()) {
			/*
			A solution for that is achieved by investigating the
			numbers of 1st and 2nd part:
			*/
			// Start with potentially removing the total first
			// declaration (probably '1. Uebungsblatt').
			if (foundDeclarations.declarations.size() > 1) {

				// Determine the index of the previously found
				// declaration prior to removing an element (that
				// would result in a false or out of bounds index!)
				int index_of_previously_found_dec;
				index_of_previously_found_dec = foundDeclarations
					.declarations.size() - 2;
				// loopDeclaration is at position size() - 1

				// Determine whether removing of a false part
				// declaration because of sheetheadings is required:
				// only if the loopDeclaration is the immediately
				// following one after the questionable part
				// declaration that probably rather is a sheetdraft
				// heading!
				boolean removed_declaration;
				removed_declaration = false;

				// Is this the declaration directly following the
				// sheetheading declaration?
				if (loopDeclaration.equals(foundDeclarations
							.declarations
							.get(sheetheadingDeclaration_index + 1))
//						|| loopDeclaration.getIndex()
//							.equals(foundDeclarations
//								.declarations.get(1).getIndex())
//						|| loopDeclaration.getIndex().getNumber(0)
//							== foundDeclarations.declarations.get(1)
//								.getIndex().getNumber(0)
							) {
					// Filter out headings like 9. Part, whenever
					// the number is higher than the parts start
					// with.
					// First check if it can be parsed to integer,
					// then check if this parsed value is greater than
					// the next part's numbering:
					String wordContainingNumbering = foundDeclarations
						.declarations.get(sheetheadingDeclaration_index)
						.getWordContainingNumbering();

					int decFoundFirst_numbering = -1;
					decFoundFirst_numbering = Global
						.getInt(wordContainingNumbering);
					// Added for INTCHAR to compare
					// by char too:
					int word_decFoundFirst_numbering_index
						= wordContainingNumbering
						.indexOf(decFoundFirst_numbering);

					int word_decFoundFirst_numbering_index_length
						= ("" + word_decFoundFirst_numbering_index)
						.length();

					int decFoundFirst_next_to_compare_index
						= word_decFoundFirst_numbering_index
						+ word_decFoundFirst_numbering_index_length;

					char decFoundFirst_char = 0;
					if (decFoundFirst_next_to_compare_index
							< wordContainingNumbering.length()) {

						decFoundFirst_char = wordContainingNumbering
							.charAt(decFoundFirst_next_to_compare_index);
					}

					// Seek for the first non-space character:
					while (++word_decFoundFirst_numbering_index
							< wordContainingNumbering.length()
							// continue to loop condition:
							&& decFoundFirst_char == ' ') {

						decFoundFirst_char = wordContainingNumbering
							.charAt(decFoundFirst_next_to_compare_index);
					}

					wordContainingNumbering = loopDeclaration
						.getWordContainingNumbering();
					int loopDeclaration_numbering = -1;
					loopDeclaration_numbering = Global
						.getInt(wordContainingNumbering);
					// Added for INTCHAR to compare by char too.
					int word_loopDeclaration_numbering_index
						= wordContainingNumbering
						.indexOf(loopDeclaration_numbering);
					int word_loopDeclaration_numbering_index_length
						= ("" + word_loopDeclaration_numbering_index)
						.length();
					int loopDeclaration_next_to_compare_index
						= word_loopDeclaration_numbering_index
						+ word_loopDeclaration_numbering_index_length;
					char loopDeclaration_char = 0;
					if (loopDeclaration_next_to_compare_index
							< wordContainingNumbering.length()) {
						loopDeclaration_char = wordContainingNumbering
							.charAt(loopDeclaration_next_to_compare_index);
					}
					// Seek for the first non-space character:
					while (++loopDeclaration_next_to_compare_index
							< wordContainingNumbering.length()
							//continue to loop condition:
							&& loopDeclaration_char == ' ') {

						loopDeclaration_char = wordContainingNumbering
							.charAt(loopDeclaration_next_to_compare_index);
					}

					// Greater equal than because of e.g. if
					// '1. Uebung\r\n1. Aufgabe'
					// then 1. Uebung should be removed too.
					if (decFoundFirst_numbering
							>= loopDeclaration_numbering
							// added for INTCHAR otherwise the above
							// is always true for 1a, 1b,... .
							&& decFoundFirst_char >= loopDeclaration_char
							/* _.compareTo(_)  >= 0*/
							// <- compares lexographically, char < char
							// wins. so "B" < "ay" because 'B' < 'a' !
							// but 'b' > 'a' !
							) {
						System.out.println("The first found declaration"
								+ " seems to be a sheet head|number: "
								+ foundDeclarations.declarations
								.get(sheetheadingDeclaration_index)
								.getLineContent()
								+ ". Removing it ...");
						foundDeclarations.declarations
							.remove(sheetheadingDeclaration_index);
						removed_declaration = true;
						sheetheadingDeclaration_index = 0;// reset it
					}

					// Because it is desired to tolerate several
					// parts having the same enumeration number:
					//String first_word_previous = foundDeclarations
					//	.declarations.get(index_of_previously_found_dec)
					//	.getFirstWord();
					//if (Integer.parseInt(first_word_previous)
					//		>= Integer.parseInt(loopDeclaration
					//		.getFirstWord())) {
					//	foundDeclarations.declarations
					//	.remove(index_of_previously_found_dec);
					//}

				}


				// Now used because of the feature of multiple sheets
				// in one document where after e.g. '1. Uebungsblatt',
				// ..., '3. Part', '1. Uebungsblatt' reoccurs.
				// Then we need to filter out the '1. Uebungsblatt'
				// again:
				if (
						//loopDeclaration.getIndex().compare(
						//foundDeclarations.declarations.get(
						//index_of_previously_found_dec).getIndex()) < 0
						/*
						=> calling indexNumber is smaller
						=> previous declaration found is smaller, e.g.
						3. Part previously and now 1. Uebung
						*/
						//||
						/*
						THE FOLLOWING COVERS GAPS
						TODO: 1.Sheet 1.Part 2.Part 3.Sheet 1.Part, ...
						where there is NO GAP because of 2.Ex 3.Sheet
						which theoretically is possible.
						i.e. transition from 2. Part to 8. Uebungsblatt
						AND the other way round too e.g. transition
						from 8. Part to 2. Uebungsblatt.

						AS WELL AS transitions e.g.from 2.Part to 2.Sheet
						(Only 2.Part to 3. is spared!, not 2. Part to 4.
						TODO, this could result in trouble. TODO How?)
						*/
						!removed_declaration
						&&
						/*
						Note: The first number of the index is the
						only important one here because x. Uebung
						and x.1.1 Part, x.1.2 Part, ..
						TODO Unless there is no x.y.z Sheet of course!
						*/
						/*
						Is the lastDeclarationIndexNumber
						- thisDeclarationIndexNumber not exactly |1|?
						(Minus sign (-1) is due to ascending order.)
						*/
						foundDeclarations.declarations
						.get(index_of_previously_found_dec).getIndex()
						.getNumber(0)
						- loopDeclaration.getIndex().getNumber(0)
						!= -1
						) {
					// Then the new part declaration candidate
					// to be removed - because it's a sheet heading
					// instead - potentially is:
					if (!is_this_a_double) {
						// <- If it's a double then this cannot be a
						// candidate as it will be removed in the
						// following steps.
						sheetheadingDeclaration_index
							= index_of_previously_found_dec + 1;
					}
				}

			}

		}

	}



	/**
	 *
	 * @param loopDeclaration
	 * @param woerter
	 */
	private static void storeSubsequentWordsAndLookForIndexTherein(
			Declaration loopDeclaration, String[] woerter) {

		// The declaration succeding words are stored.
		// Possibly also an IndexNumber.
		if (woerter.length > 1) {
			int wordCountToExermine = 1;
			loopDeclaration.setSecondWord(woerter[1]);
			if (woerter.length > 2) {
				wordCountToExermine = 2;
				loopDeclaration.setThirdWord(woerter[2]);
			}
			// TODO Store more than 2 words, maybe entire line if not
			// all is in one line?

			// Is there an index number within the following words?
			for (Muster m : Muster.values()) {
				// Now look for a regex index number within the
				// following words of this declaration line:
				if (!m.isWordedPattern()) {
					// Examine second and the third word
					for (int words_within_line_index = 0
							; words_within_line_index < wordCountToExermine
							; words_within_line_index++) {
						Matcher matcher = m.getPattern()
							.matcher(woerter[words_within_line_index]);
						if (matcher.matches()) {

							loopDeclaration.setIndex(RegExFinder
									.getContainedIndexNumber(
										woerter[words_within_line_index]));
							return;//break;
							// Subsequent potential indices are ignored
							// (the first one found is the relevant one
							// because the whole text could be within
							// one single line without linebreaks and
							// then the first is the correct one unless
							// the first one already has been found
							// in the first word!
						}
					}
				}
			}

		}
	}



	/**
	 * extrahiert aus dem uebergebenen String eine IndexNumber (siehe Klasse IndexNummer)
	 * Dafuer muss sie auf eine der Regexes aus der enum Muster.java gepasst haben
	 *
	 * @param string aus dem die IndexNumber extrahiert werden soll
	 * @return gefundene IndexNumber
	 */
	private static IndexNumber getContainedIndexNumber(String string) {
		IndexNumber index = new IndexNumber();
		Integer j = null;
		// Gibt an, ob im char zuvor schon eine Zahl gefunden wurde
		boolean foundInt = false;
		// Gibt an, ob die letzte Zahl schon weggeschrieben wurde
		boolean intWritten = false;
		char[] charRep = string.toCharArray();
		for (int k = 0; k < charRep.length; k++){
			String c = Character.toString(charRep[k]);
			// Wenn c Integer ist:
			if (Global.isInt(c)) {
				// Wenn schon zahl gefunden wurde, fuege Sie zu einer Zahl zusammen.
				if (foundInt && j != null) {
					j = (j*10) + Integer.parseInt(c);
				// Wenn nicht, setze j auf den gefunden Integer
				} else {
					j = Integer.parseInt(c);
				}
				foundInt = true;
				intWritten = false;
				// Wenn das letzte Feld des char[] betrachtet wird, schreibe Zahl weg
				if (k == charRep.length -1 ) {
					if (j != null) {
						index.addNumber(j);
						intWritten = true;
					}
				}
			// Wenn das Zeichen keine Zahl ist:
			} else {
				if (j != null && !intWritten) {
					index.addNumber(j);
					intWritten = true;
				}
				foundInt = false;
			}
		}
		return index;
	}



//				try {
//					if (foundInt)
//
//					j = Integer.parseInt(Character.toString(c));
//					foundInt = true;
//					// index.addNumber(i);
//					if (foundInt){
//						i = (i * 10) + j;
//					} else {
//						i = j;
//						foundInt = false;
//					}
//					foundInt = true;
//				} catch (Exception e) {
//					// Forfahren
//				}
//			}
//		return i;
//	}


}


