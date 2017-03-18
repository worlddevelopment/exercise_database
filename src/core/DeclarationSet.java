package core;


import java.util.ArrayList;


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
	 * Whether no declaration has an index assigned to it.
	 *
	 * Only generic patterns (regex, wildcard,...) can produce indexed
	 * results.
	 * An index is a series of numbers, i.e. a numbering scheme.
	 * A pattern is index less if:
	 * * Not all found declaration entries contain at least one number.
	 * * If the numbering is not solely ascending or has gaps.
	 * Else a set of declarations is indexed.
	 * Thus a generic pattern can lead to both indexed,indexless result.
	 * Specific patterns can not result in a declaration with index,
	 * however specific (and generic) markup patterns could lead to a
	 * content part that contains a hard coded text based index.
	 * @return true if this set is entirely index less.
	 */
	public boolean isIndexed() {
		if (this.pattern != null && !this.pattern.canMatchIndex()) {
			return false;
		}
		if (this.declarations.size() < 1) {
			return false;
		}
		//else merged|mixed pattern pool
		for (Declaration dec : this.declarations) {
			Pattern dec_pattern = dec.getMatchedPattern();
			// As soon as one declaration pattern can not match an index
			// there is no longer the possibility of a continuous index:
			// TODO Use canResultContainPattern instead once an index
			// is set when splitting by markup type patterns!
			if (dec_pattern && !dec_pattern.canMatchIndex()) {
				return false;
			}
		}
		// Every pattern could have matched an index.
		// => Check the declaration (and maybe even content part head)
		// in detail:
		for (Declaration dec : this.declarations) {
			Pattern dec_pattern = dec.getMatchedPattern();
			// As soon as one declaration,content part head has no index
			// there is no longer the possibility of a continuous index:
			if (!dec.hasIndex()) {
				return false;
			}
		}
		// All have an index:
		return true;
	}



	/**
	 * Checks whether the declarations of this DeclarationSet
	 * have monotonely ascending Index numbers
	 * and whether there all of the declarations have an index.
	 *
	 * Declarations without index are assumed to be not in order.
	 * Edge case:
	 * In the case of a mixed set, when there is just one
	 * declaration without index and it comes first, then it could
	 * still be in order (TODO questionable?), e.g. listing teachers:
	 * Teacher:
	 * Teacher specific1:
	 * Teacher specific2:
	 * ...
	 * Working code for this can be found within comments in isInOrder.
	 *
	 * Note: Lists without the index already allow it and are considered
	 * in order, e.g.
	 * Teacher:
	 * Teacher astronomy:
	 * ...
	 *
	 * TODO The above edge case (side effect prone and maybe
	 * superfluous because if such decs are matched, then the pattern
	 * is either indexless with the special case 'Teacher:' or
	 * indexed but without 'Teacher:')
	 * is rendered void by the following approach:
	 * Add keyword repetition detection, especially with upper case
	 * first letter to get rid of common words like 'in', 'and'. This
	 * could solve both cases as instead of the index|numbering series
	 * detection, Teacher would be detected which would be correct.
	 * Note: Rigidity of matching a single word is questionable unless
	 * it also contains a supplement like a colon.
	 *
	 * @return true, if indices are in (ascending) order, else false.
	 */
	public boolean isInOrder() {
		// Is entirely indexless? (save costly detail check)
		if (!this.isIndexed()) {
			// Then order can be varied freely.
			return true;
		}
		return DeclarationSet.isInOrder(this.declarations);
	}



	/**
	 * Checks whether the given declarations have monotonously rising
	 * index numbers if any.
	 * If it is a set without any declaration with indices it is
	 * defined to be in order and thus returns true.
	 *
	 * @param delarations The declarations to check.
	 * @return true, if in (ascending) order, otherwise false
	 */
	private static boolean isInOrder(ArrayList<Declaration> declarations) {
		// The caller must check this:
		//if (this.declarations.size() < 1) {
		if (this.declarations.size() < 2) {
			return true;
		}
		// Detail check if its order is ascending:
		/*
		To make the edge case: Teacher:, Teacher 1, Teacher 2,...
		work, i.e. reach this function, the function isIndexed must
		return true even when the first declaration has no index!
		*/
		//boolean has1stDecAnIndex = declarations.get(0).hasIndex();
		//boolean has2ndDecAnIndex = declarations.get(1).hasIndex();
		//boolean areFirstAndSecondInOrder = false;
		//if (has1stDecAnIndex) {
		//	if (!has2ndDecAnIndex) {
		//		return false;
		//	}
		//	if (declarations.get(0).getIndex()
		//			.compare(declarations.get(1).getIndex())
		//			== -1) {
		//		return false;
		//	}
		//	areFirstAndSecondInOrder = true;
		//}
		//else first has no index.

		// Check all others
		for (int i = 0; i < declarations.size() - 1; i++) {
			if (declarations.get(i).hasIndex()
					&& declarations.get(i + 1).hasIndex()) {
				if (declarations.get(i).getIndex()
						.compare(declarations.get(i + 1).getIndex())
						== -1) {
					return false;
				}
			}
			else {
				return false;
			}
		}
		// All others are in order.

		//if (!has1stAnIndex || areFirstAndSecondInOrder) {
		//	return true;
		//}
		//return false;
		return true;
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
					// If yes, then replace the declarations of this
					// set and return true.
					if (DeclarationSet.isInOrder(workingDeclarations)) {
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

		// Only 1 declaration (worst case) can not be out of order:
		else if (!this.isInOrder()) {
			// Negative influence if 1|more declarations out of order
				declarationcountweight = 1;
			}
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
