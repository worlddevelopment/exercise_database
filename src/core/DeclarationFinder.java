package core;


import java.util.ArrayList;
import java.util.List;


/**
 * Serves to find a part and solution declarations within a sheet.
 *
 * @author Schweiner, J.R.I.B.-Wein, worlddevelopment
 *
 */
public class DeclarationFinder {

	private static RegExFinder regExFinder = new RegExFinder();

	/**
	 * Searches the given sheetdraft using regular expressions (see
	 * also Muster.java) for part declarations and puts them into
	 * a the corresponding set of declarations (the possible candidates).
	 *
	 * Prerequisite:
	 * Requires the sheetdraft's plain text to be extracted at this point!
	 *
	 * @param sheet Sheetdraft
	 * @param isToBeFiltered If true then the returned list only
	 * contains the most matching set.
	 * @return List of determined declaration sets containing either all
	 * or only 1 DeclarationSet depending on {@param isToBeFiltered}
	 * or null if no matching set of declarations was found.
	 */
	public static List<DeclarationSet> findDeclarationSets(
			String[] plainText, boolean isToBeFiltered) {
		if (/*filter == null ||*/ !isToBeFiltered) {
			return findeAlleDeklarationen(plainText);
		}
		List<DeclarationSet> foundDeclarationSets
			= new ArrayList<DeclarationSet>();
		foundDeclarationSets.add(findeDeklarationen(plainText));
		return foundDeclarationSets;
	}



	/**
	 * Only the most optimal fit set is returned.
	 *
	 * @param sheet Sheetdraft
	 * @return The most matching set of declarations.
	 */
	public static DeclarationSet findeDeklarationen(Sheetdraft sheet) {
		return findeDeklarationen(sheet.getPlainText());
	}



	/**
	 * Searches the given sheetdraft using regular expressions (see
	 * also Muster.java) for part declarations and puts them into
	 * a the corresponding set of declarations (the possible candidates).
	 *
	 * Prerequisite:
	 * Requires the sheetdraft's plain text to be extracted at this point!
	 *
	 * @param plainText Array of lines of plain text.
	 * @return List of all determined declaration sets.
	 */
	public static List<DeclarationSet> findeAlleDeklarationen(
			String[] plainText) {
		List<DeclarationSet> foundDeclarationSets = new ArrayList<DeclarationSet>();

		// Search for all known declaration patterns and store them:
		for (Muster m : Muster.values()) {
			if (Global.debug) {
				System.out.println(m.toString());
			}
			foundDeclarationSets.add(regExFinder.sucheMuster(plainText, m));
		}

		return foundDeclarationSets;

	}



	/**
	 * Find the fittest set of declarations.
	 *
	 * @param plainText An array of lines of plain text.
	 * @return The most matching DeclarationSet. Null if none was found.
	 */
	public static DeclarationSet findeDeklarationen(
			String[] plainText) {

		List<DeclarationSet> foundDeclarationSets
			= new ArrayList<DeclarationSet>();

		// Filter all out but one by default:
		foundDeclarationSets = findeAlleDeklarationen(plainText);

		System.out.println("Attention: Potentially critical step:"
				+ "Declarations are removed from declaration set!");
		// Compare, filter and create a score:
		// In the end the most sensible declaration is taken.

//		for (DeclarationSet set : foundDeclarationSets){
//			if(set.declarations.size() == 0) {
//				foundDeclarationSets.remove(set);
//			}
//		}

		// Mark empty sets (where no match occurred) for removal:
		boolean[] removeEmptyDeclarations
			= new boolean[foundDeclarationSets.size()];
		// Initialize:
		for (int i = 0; i < removeEmptyDeclarations.length; i ++) {
			removeEmptyDeclarations[i] = false;
		}
		for (int i = 0; i < foundDeclarationSets.size(); i++) {
			//TODO Prevent empty declarations from being added.
			if (foundDeclarationSets.get(i).declarations.size() == 0) {
				removeEmptyDeclarations[i] = true;
			}
		}
		// Because in a list the indices are shifted if an element is
		// removed, the operation is split across several loops.
		// It is important to start at the end to keep indices valid:
		for (int i = foundDeclarationSets.size() - 1; i >= 0; i--) {
			if (removeEmptyDeclarations[i] == true) {
				foundDeclarationSets.remove(i);
			}
		}

		System.out.println("Found " + foundDeclarationSets.size()
				+ " possible DeclarationSets.");

		for (DeclarationSet set : foundDeclarationSets) {
			System.out.println("\r\nDeclarations within Set: "
					+ set.toString());
			for (Declaration dec : set.declarations) {
				System.out.println(dec.toString());
			}
		}

		// With all sets that feature a word match with the first word
		// it is checked whether they are also followed by an index
		// as it is expected for many gentle parts.
		// (i.e. something like Aufgabe 1, Aufgabe 2 or Aufgabe 3.5 etc)
//		for (DeclarationSet set: foundDeclarationSets) {
//			set.clearWordedDeclarationsWithoutIndices();
//		}


		// It is checked whether the indices of the declarations
		// (for "Aufgabe 4..." as well as for "4..")
		// are in the correct sort order.
		// Important: If not, then likely somewhere at the beginning of
		// a line in the document there is a false candidate, e.g.
		// an index that does not declare an part despite looking
		// as if it would.
		// To mitigate it, a progressively increasing number of
		// declarations is left out to check if the remaining
		// declarations make more sense then (the numbers are ordered
		// correctly).
//		for (DeclarationSet set: foundDeclarationSets) {
//			set.removeDeclarationsNotInOrder();
//		}


		if (foundDeclarationSets.isEmpty()) {
			return null;
		}


		DeclarationSet setWithHighestScoreSolution = null;
		/* = foundDeclarationSets.get(0); <-- null by default
		Hence only part declarations are expected by default.*/
		DeclarationSet setWithHighestScore = null;
		// = foundDeclarationSets.get(0);
		double score = 0;
//		for (int i = 0; i < foundDeclarationSets.size(); i++) {
//			score = foundDeclarationSets.get(i).calculateScore();
//			if (score > highestScore) {
//				setWithHighestScore = foundDeclarationSets.get(i);
//				highestScore = score;
//			}
//		}
		for (DeclarationSet set : foundDeclarationSets) {
			score = set.calculateScore();
			System.out.println("Score of the declarations found with"
					+ " the pattern '" + set.getPattern() + "': "
					+ score );
		}
		for (int i = 0; i < foundDeclarationSets.size(); i++) {
			if (foundDeclarationSets.get(i)
					.getPattern().isSolutionPattern()) {

				// set of solution declarations:
				if (setWithHighestScoreSolution == null /*<-This ensures
				we only have nonnull set if there exist solution sets.*/
						|| foundDeclarationSets.get(i).getScore()
						> setWithHighestScoreSolution.getScore()) {

					setWithHighestScoreSolution = foundDeclarationSets.get(i);

				}

			}
			else {

				// set of part declarations:
				if (setWithHighestScore == null
						|| foundDeclarationSets.get(i).getScore()
						> setWithHighestScore.getScore()) {

					setWithHighestScore = foundDeclarationSets.get(i);

				}

			}
		}


		// Check if neither part nor solution have been found.
		if (!(setWithHighestScore != null
					|| setWithHighestScoreSolution != null)) {
			System.out.println("Neither part nor solution found.");
			return null;
		}
		// No solution declarations exist/ were found?
		else if (setWithHighestScoreSolution == null) {
			// => Then there is nothing to merge and we can return:
			System.out.println("Part score: " + setWithHighestScore
					.getScore() + setWithHighestScore.getPattern());
			return setWithHighestScore;
		}
		// No part declarations found?
		else if (setWithHighestScore == null) {
			System.out.println("Solution score: "
					+ setWithHighestScoreSolution.getScore()
					+ setWithHighestScoreSolution.getPattern());
			return setWithHighestScoreSolution;
		}
		//else

		// both solutions and parts exist. => we have to merge.
		System.out.println("Part score: "
				+ setWithHighestScore.getScore()
				+ setWithHighestScore.getPattern());
		System.out.println("Solution score: "
				+ setWithHighestScoreSolution.getScore()
				+ setWithHighestScoreSolution.getPattern());


		/*
		Merge both the part declarations and those for the solutions
		according to the line number where they were found.
		If that's equal then the word count will be compared.
		An earlier found declaration should come earlier in the merged
		declaration list to keep order.
		*/
		DeclarationSet mergedPartAndSolutionDeclarationSet
			= new DeclarationSet();
		int setWithHighestScoreParts_count = 0;
		int setWithHighestScoreSolutions_start_index = 0;
		// In the end this must be equal to the set's size!

		// Note: Here we use that each set's declarations are already
		// sorted by occurrence, i.e. line number!
		for (Declaration part_dec : setWithHighestScore
				.declarations) {

			// Examine the not-yet-stored-in-merged-set solutions'
			// occurences:
			//for (Declaration solution_dec
			//		: setWithHighestScoreSolution.declarations) {
			int setWithHighestScoreSolutions_index
				= setWithHighestScoreSolutions_start_index - 1;
			while (++setWithHighestScoreSolutions_index
					< setWithHighestScoreSolution.declarations.size()) {

				Declaration solution_dec = setWithHighestScoreSolution
					.declarations
					.get(setWithHighestScoreSolutions_index);

				// Is this solution occurring earlier in the text
				// than the part declaration?
				// If it's equal then we have to examine the word count.
				// (important if all decs were found in one single line)
				if (solution_dec.getLineNumber()
						< part_dec.getLineNumber()
						/*TODO Add if required for all in a signle line:
						  || solution_dec.getWordPriorToDeclarationCount() < part_dec.getWordPriorToDeclarationCount()*/) {

					// Then add/store the solution declaration.
					mergedPartAndSolutionDeclarationSet.declarations.add(solution_dec);
					// Increase the start index as this Declaration is
					// now out/already stored:
					++setWithHighestScoreSolutions_start_index;
					// == dec_index + 1

					//break;<-- check next part solution too.

					/*
					Too early to tell that the next solution is not
					earlier too! (the occurrence should be followed
					strictly no matter whether each part has a
					solution in the end or not.)
					*/
				}
				// If instead the part occurred prior to this
				// solution dec then we can stop examining solution
				// decs as they are already sorted by occurrence.
				else /*TODO if (solution_dec.getLineNumber()
						> part_dec.getLineNumber())*/ {
					break; // Only if the part is earlier than the
					// solution, we stop to store the part.
					// If both line numbers, i.e. occurrences, are equal
					// then we prefer the part. TODO determine if
					// this is rigid enough or whether we need to
					// examine the word count too.
				}

			}

			// => At this point all (solutions') declarations that
			// occurred earlier are already stored.

			/*
			Then now the earliest still available/non-stored
			declaration is this part declaration,
			so we add/store it in the merged set:
			*/
			mergedPartAndSolutionDeclarationSet.declarations.add(part_dec);

		}


		/*
		Add missing solution declarations that have occurred later
		than the last part (hence the loop stopped with some or all
		the solution declarations still not having been added.):
		*/
		int solution_declarations_index
		= setWithHighestScoreSolutions_start_index - 1; /* because we
		increment instantly below in the while loop condition: */
		while (++solution_declarations_index < setWithHighestScoreSolution.declarations.size()) {
			mergedPartAndSolutionDeclarationSet.declarations.add(
					setWithHighestScoreSolution.declarations.get(solution_declarations_index)
			);
		}


		/*
		At this point the merged Part And Solution Declaration Set
		should contain
		all declarations in the order of occurrence in the plain text.
		 */

		return mergedPartAndSolutionDeclarationSet;

	}



	/**
	 * Filters the given array of strings such that only numbers remain.
	 *
	 * Removes all characters that are not Integer from the strings.
	 *
	 * @param string Array of string that contains integers to extract.
	 * @return array of int that contain all Integer chars from String[]
	 */
	public static int[] extrahiereZahlen(String[] string) {

		String wort = "";
		int neu[] = new int[string.length];
		String test = "";
		char c = ' ';

		// Filter string such that only numbers prevail:
		for (int i = 0; i < string.length; i++) {
			wort = string[i];
			//default: 0
			neu[i] = 0;
			String neuZahl = "";
			boolean zahl = false;
			for (int j = 0; j < wort.length(); j++) {
				c = wort.charAt(j);
				test = "" + c;
				zahl = isInt(test);
				if (zahl) {
					neuZahl = neuZahl + test;
				}
			}
			// Is it a number?
			if (isInt(neuZahl)) {
				neu[i] = Integer.parseInt(neuZahl);
				System.out.println("Old: "+ wort + "; New: " + neu[i]);
			}
		}
		return neu;
	}



	/**
	 * Checks whether the given array of integer contains directly
	 * ascending numbers (e.g. 4, 5, 6, 7).
	 *
	 * @param zahlen Numbers to determine whether its a direct series.
	 * @return true if all numbers shape a direct series.
	 */
	@SuppressWarnings("unused")
	private static boolean richtigeReihenfolge(int[] zahlen) {

		// ueberprueft, ob die uebergebenen Zahlen fortlaufend sind
		boolean stimmts = true;
		int falsch = 0;
		// falls irgendwo in der Reihe ein Fehler vorhanden ist
		// wird falsch ausgegeben
		for (int i=0; i < zahlen.length-1; i++) {
			if((zahlen[i] + 1) != zahlen[i+1]) {
				stimmts = false;
				falsch++;
			}
		}
		System.out.println("Es kam zu " + falsch + " Fehlern!");
		return stimmts;
	}



	/**
	 * Checks whether the given string can be parsed to an integer.
	 *
	 * @param i String to check.
	 * @return true if it can be parsed to integer successfully.
	 */
	private static boolean isInt(String i) {

		try {
			Integer.parseInt(i);
			return true;
		}
		catch(NumberFormatException FehlerZahlenformat) {
			return false;
		}
	}



//	/**
//	 * Determines the first in the string contained number (may be more
//	 * characters than one) and returns it.
//	 * Returns null if the string did not contain numbers.
//	 *
//	 * @param string A string to check.
//	 * @return The first found number or null if there was no number.
//	 */
//	public Integer getFirstContainedNumber(String string) {
//		Integer i = null;
//		Integer j = null;
//		boolean foundInt = false;
//		char[] charRep = string.toCharArray();
//			for (char c : charRep){
//				try {
//					j = Integer.parseInt(Character.toString(c));
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
