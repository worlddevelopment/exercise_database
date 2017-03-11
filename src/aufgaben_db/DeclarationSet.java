package aufgaben_db;


import java.util.ArrayList;

import Verwaltung.HashMapVerwaltung;


/**
 * Represents a set of declarations that have been found with the same
 * regular expression pattern (see Muster.java).
 *
 * @author Schweiner, J.R.I.B.-Wein, worlddevelopment
 *
 */
public class DeclarationSet {

	/**
	 * The Muster used to find this set of content declarations.
	 * Null if no or a mixed pattern pool was used (Note: each declaration
	 * within this set may have its own pattern attached.)
	 * TODO Omit this redundant value completely, but keep the function?
	 */
	private Muster pattern;
	/**
	 * Score of the DeclarationSet
	 */
	private double score = 0;
	/**
	 * How often the declaration has been found.
	 */
	private int numberOfHits = 0;

	/**
	 * List for storing declarations.
	 */
	public ArrayList<Declaration> declarations
		= new ArrayList<Declaration>();




	// ======= CONSTRUCTORS
	/**
	 * Constructor for DeclarationSet in the homogenous case.
	 * Id est when all declarations have been found using the same one.
	 *
	 * @param pattern Muster this declarations was found with.
	 */
	public DeclarationSet(Muster pattern){
		this.setPattern(pattern);
	}
	/**
	 * Constructor for the heterogenous case.
	 * e.g. if this is a merged or mixed set.
	 */
	public DeclarationSet() {
		this.setPattern(null);
	}




	// ======= METHODS
	/**
	 * @return the pattern
	 */
	public Muster getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(Muster pattern) {
		this.pattern = pattern;
	}


	/**
	 * Increase the hit count of this DeclarationSet.
	 */
	public void addHit() {
		this.numberOfHits++;
	}

	/**
	 * @return the numberOfHits
	 */
	public int getNumberOfHits() {
		return numberOfHits;
	}

	/**
	 * @param numberOfHits the numberOfHits to set
	 */
	public void setNumberOfHits(int numberOfHits) {
		this.numberOfHits = numberOfHits;
	}


	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}


	/**
	 * If this set has a wordedPattern, then declarations that have
	 * no index are removed.
	 * Id est those that are not followed by an index in the text.
	 * and thus also likely are no content declaration.
	 * Note: This is only true for content like paragraphs from e.g. a
	 * sorted list!
	 * TODO: Generalize? Instead of only numeric one could allow *, #, -
	 * Note: The series detection (DeclarationFinder) does currently
	 * not include this requirement, i.e. this function is not called.
	 * Which is good as it allows more flexible declarations, e.g.
	 * finding all similar content like Line1:* , Line4:* ,... or
	 * using recurring formatting (when using XML in a 2nd pass). TODO
	 * <br/>
	 * Pattern muss worded sein und es muessen 2 oder mehr Vorkommen sein, da bei einem Vorkommen
	 * (= nur einer Aufgabe) auch gut einfach nur "Aufgabe" dastehen kann
	 *
	 * @return true upon success, false upon failure
	 */
	public boolean clearWordedDeclarationsWithoutIndices() {
		try {
			// TODO Bug: For merged sets, the pattern could differ
			// on a per declaration basis. This check has to be
			// inside the loop.
			// TODO Pattern could be null.
			if (this.getPattern().isWordedPattern()
					&& this.declarations.size() > 1) {
				for (Declaration dec : this.declarations) {
					if (!dec.hasIndex()) {
						this.declarations.remove(dec);
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks whether the declarations of this DeclarationSet
	 * have monotonely ascending Index numbers
	 * and whether there all of the declarations have an index.
	 *
	 * Declarations without index are assumed to be not in order.
	 * TODO In the case of a mixed set, when there is just one
	 * declaration without index and it comes first, then it could
	 * still be in order, e.g. listing teachers:
	 * Teacher:
	 * Teacher specific1:
	 * Teacher specific2:
	 * ...
	 *
	 * Note: Lists without the index already allow it:
	 * Teacher:
	 * Teacher astronomy:
	 * ...
	 * TODO Add keyword repetition detection, especially with upper case
	 * first letter to get rid of common words like 'in', 'and'. This
	 * could solve both cases as instead of the index|numbering series
	 * detection, Teacher would be detected which would be correct.
	 *
	 * @return true, if indices are in (ascending) order, else false.
	 */
	public boolean isInOrder() {
		boolean correctOrder = true;
		if (this.declarations.size() > 2) {
			for (int i = 0; i < this.declarations.size() - 1; i++) {
				if (this.declarations.get(i).hasIndex()
						&& this.declarations.get(i + 1).hasIndex()) {
					if (this.declarations.get(i).getIndex()
							.compare(this.declarations.get(i + 1)
								.getIndex()) == -1) {
						correctOrder = false;
					}
				}
				else {
					correctOrder = false;
				}
			}
		}
		return correctOrder;
	}


	// TODO Add public boolean isIndexLess() {
	//	if (this.pattern != null && this.pattern.isWorded()) {
	//		return true;
	//	}
	//}


	/**
	 * Checks whether the given declarations have monotonously rising
	 * index numbers.
	 *
	 * @param set TODO rename to declarations,reduce function redundancy
	 * @return true, if in (ascending) order, otherwise false
	 */
	private static boolean isInOrder(ArrayList<Declaration> set) {
		boolean correctOrder = true;
		if (set.size() >= 2) {
			for (int i = 0; i <= set.size() - 2; i++) {
				if (set.get(i).hasIndex()
						&& set.get(i + 1).hasIndex()) {
					if (set.get(i).getIndex()
							.compare(set.get(i + 1).getIndex()) == -1) {
						correctOrder = false;
					}
				}
				else {
					return false;
				}
			}
		}
		return correctOrder;
	}

	/**
	 * Removes declarations that do not fit into the order of the
	 * indices of all declarations.
	 *
	 * TODO This is buggy. Not used yet. May even be counter productive.
	 *
	 * @return true on success else false when no order could be created
	 */
	public boolean removeDeclarationsNotInOrder() {
		if (this.isInOrder()) {
			return true;
		}
		// Copy all declarations to allow safe operation upon:
		ArrayList<Declaration> workingDeclarations
			= new ArrayList<Declaration>();
		for (Declaration dec : this.declarations) {
			workingDeclarations.add(dec);
		}
//		Declaration[] workingDeclarations
//			= new Declaration[this.declarations.size()];
//		for (int i = 0; i < this.declarations.size(); i++){
//			workingDeclarations[i] = this.declarations.get(i);
//		}

		int size = workingDeclarations.size();
		// Amount of variable walks = amount of items deleted;
		for (int i = 1; i < size; i++) {
			// Remember removed declarations:
			int[] removedDeclarations = new int[i];
			// With which value to init first field of the int[]:
			for (int l = 0; l <= (size - i); l++) {// <- bottom up
				// Set each entry of the array to its own + l th pos
				for (int j = l; j < removedDeclarations.length; j++) {
					removedDeclarations[j] = j;
				}
			}
			// Last variable = start incrementing highest position:
			for (int k = removedDeclarations.length - 1; k > -1; k--) {
				// undescribable witch hexing
				while (removedDeclarations[k]
						<= (size - 1)
						- ((removedDeclarations.length - 1) - k)) {
					// Delete the declarations as set in the array:
//					for (int m : removedDeclarations) {
					for (int m = removedDeclarations.length; i > -1;
							i--) {
						workingDeclarations.remove(
								removedDeclarations[m]);
					}
					// Check whether they are in order now.
					// If yes, then replace the declaration of this
					// set and return true.
					if (DeclarationSet.isInOrder(workingDeclarations)) {
						if (removedDeclarations.length == 1) {
							HashMapVerwaltung.erweitereHashmapBoolean(
									HashMapVerwaltung.eineAufgabeZuviel
									, true);
							HashMapVerwaltung.erweitereHashmapInt(
									HashMapVerwaltung
									.gestricheneAufgabe
									, removedDeclarations[0] + 1);
						}
						this.declarations = workingDeclarations;
						return true;
					}

					removedDeclarations[k]++;

					workingDeclarations.clear();
					for (Declaration dec : this.declarations) {
						workingDeclarations.add(dec);
					};

				} removedDeclarations[k]--;
			}
		}
		return false;
	}



	/**
	 * Calculates a score out of the found declarations to allow later
	 * weighting process to decide which declarations are used for
	 * splitting the content parts.
	 * TODO The score calculation never may be ideal. Improve.
	 * <br/>
	 * Parameters that are taken into account so far:<br/>
	 * - Count of declarations found.<br/>
	 * - With which Muster the declarations were found.<br/>
	 * - Are all indices of the content part (if any) in order?<br/>
	 * - ...
	 *
	 * @return Double representing the score of this DeclarationSet.
	 */
	public double calculateScore() {
		double score = 0;
		int numberOfDeclarations = this.declarations.size();
		double declarationcountweight = 100; // start at full

		// Muster influences the score:
		score = score + this.pattern.getScoreOfPattern();

		// Only one declaration found is a very bad sign as it could be
		// anything:
		if (numberOfDeclarations == 1) {
			declarationcountweight = 0.1;
		}

		// Negative influence if one|more declarations are out of order:
		// TODO Convert to else if because one declaration is worst.
		if (!this.isInOrder()) {//TODO Besides it entirely isIndexLess()
			declarationcountweight = 1;
		}

		// Count of declarations is considered: the more the better
		// (besides it is a common word like "in, and, ...")
		score = score + numberOfDeclarations * declarationcountweight;

		this.score = score;
		return score;
	}





	@Override
	public String toString() {
		// dependencies
		if (score == 0.0) {
			calculateScore();
		}
		// Assemble an overview string representing this object:
		return "DeclarationSet { Pattern: " + pattern + ";\t Score: "
			+ score + ";\t NumberOfHits: " + numberOfHits + "; }";
	}


}
