package aufgaben_db;


import java.util.regex.Pattern;

import aufgaben_db.Global.SheetTypes;


/**
 * Enumeration that contains the default patterns with which the
 * declarations are searched.
 * Lecturers can specify their own patterns via the splitby|splitter|pattern scheme in the sheet's filelink.
 * TODO Outsource patterns to a configuration file to ease reuse?
 *
 * Solution support limitations:
 * * If there is only one type of
 * TODO Generalize to content part instead of exercise, solution.
 */
public enum Muster {

	/**
	 * Patterns used for determining content part declarations of type:
	 * SOLUTION (paired with EXERCISE)
	 *
	 * This is specific to sheets containing both exercise, solution
	 * declarations mixed. If there are only solution declarations
	 * then the solutions are written to files labelled as
	 * ...__Exercise_<number>__... which is not correct but should
	 * still allow to assign solutions to exercises as the type SOLUTION
	 * should have been chosen and stored during upload.
	 * Warning: This depends on user input content part type (exercise
	 * solution, civilization, ...) to be correct. Actually exercise
	 * <-> solution is a paired content part.
	 * TODO Model content part pairs into the existing design,generalize
	 */
	LOESUNG("L(oe|\u00F6)sung", PatternType.SOLUTION, "aufgabe"),
	SOLUTION("Solution", PatternType.SOLUTION, "exercise"),
	//CHARLOESUNG("[\\S]*loesung[\\S]*", /*kind*/0),// allow a prefix

	LOESCOLON("^[Ll](oe|\u00F6)s[\\S]*[ ]*[\\d]*[:]"
			, PatternType.SOLUTION),
	SOLCOLON("^[Ss]ol[\\S]*[ ]*[\\d]*[:]", PatternType.SOLUTION),

	CHARLOESCOLON("^[\\S]*[Ll](oe|\u00F6)s[\\S]*[:]"
			, PatternType.SOLUTION),
	CHARSOLCOLON("^[\\S]*[Sl]ol[\\S]*[:]", PatternType.SOLUTION),

//	LOESINTCOLON("^[Ll]oes[\\S]*[ ]?[\\d]+[:]", PatternType.SOLUTION),
//	// forces to contain an INT.
//	SOLINTCOLON("^[Sl]ol[\\S]*[ ]?[\\d]+[:]", PatternType.SOLUTION),



	/**
	 * EXERCISE (Paired|complemented with SOLUTION):
	 *
	 * TODO Allow more complements|tuple members.
	 * TODO Allow extension without overriding using properties|interface?
	 */
	AUFGABE("Aufgabe", PatternType.EXERCISE, "loesung"),
	EXERCISE("Exercise", PatternType.EXERCISE, "solution"),
	// Allow prefix, e.g. Uebungsaufgabe, Aufgabenteil, ...:
	CHARUFGABE("[\\S]*ufgabe[\\S]*", PatternType.EXERCISE),



	/**
	 * Question, Answer tuple in several languages.
	FRAGE("Frage", PatternType.CONTENT, "antwort"),
	QUESTION("Question", PatternType.CONTENT, "answer"),

	ANTWORT("Antwort", PatternType.CONTENT, "frage"),
	ANSWER("Answer", PatternType.CONTENT, "question"),
	*/



	/**
	 * GENERIC WITH INDEX
	 * TODO Structure into per word and per sentence and per line.
	 * white space afterwards => lower score!
	 */
	//INTDOTINTDOT("([\\d]+[\\.]){2}"), // 1.1.,1.2.,... TODO {1,}
	INTMINUSINT("[\\d]+[-][\\d]+"), // 1-1,1-2,2-1,2-2,2-3,3-1,3-2,...
	INTDOTINT("[\\d]+[\\.][\\d]+"), // 1.2,1.1,... TODO allow 1.3.1
	INTDOTBRACKET("[\\d]+[\\.][)]"), // 1.), 2.) or 1.], 2.]
	//INTDOTBRACKETSQUARE("[\\d]+[\\.][]]"),
	INTBRACKET("[\\d]+[)]"), // 1), 2), ...
	//INTBRACKETSQUARE("[\\d]+[]]"), // 1], 2], ...
	INTCOLON("[\\d]+[\\:]"),
	INTDOT("[\\d]+[\\.]"/*[^\\d]"*/),
	//INT("[\\d]+"), // Not useful as it will match wildly in maths!
	//CHARINTCHAR("^[\\D]*[\\d][\\S]*") // Will match too many e.g." 4 "
	INTCHAR("^[\\d]+[\\S]*") // If there is a preceding CHAR on the same
		// line e.g. "Aufgabe" then it may be stored in the declaration.
	//,INTSINGLECHAR("[\\d]+[\\S]") // 1A,1B,2A,2B,2C,1a,1b,1c,2a,2b
	;



	/**
	 * GENERIC WITHOUT INDEX (indexless)
	 */
	CAMELCASEWORDCOLON("[A-Z][\\S]*[\\:]"),
	//NONWHITESPACECOLON("[\\S]+[\\:]"),
	HASHSENTENCE("^[#]+([ _->+,.]*[\\w]+)+[\\:]?$", PatternType.MD), //<-- e.g. #A.bC  #Header, ## Heading,...
	LEADERTERMCOLON("^([_-+,.*/~#][\\w]+)+[\\:]"), //<-- e.g. *Header_:, +Heading.Footer:,...
	LEADERSENTENCECOLON("^[_-+,.*]([ ]*[\\w]+)+[\\:]"), //<-- e.g. *Header:, ## Heading:,...
	SENTENCECOLON("([\\w]+[ _-+,.*]*){2,}[\\:]"), //<-- e.g. Header is:
	EQUALSIGNORDASH("[=-][=-]+"), //<-- e.g.  ==+, --+




	// ======= ATTRIBUTES
	private String patternString;
	public enum PatternType {
		/* CONTENT CODED (text, media, ...) */
		// Target specific content parts:
		//@DEPRECATED
		EXERCISE, // TODO Allow replacing it with CONTENT_SPECIFIC_*
		SOLUTION, // TODO Allow replacing it with CONTENT_SPECIFIC_*

		// Note: hierarchical|markup (e.g. MD,ODT,DOCX,...) can also
		// filter by content, i.e. target specific content, in theory.
		// In practice it is easier to postprocess the content part head
		CONTENT,//SPECIFIC, This is why CONTENT as Type is still useful.
		// e.g. Text:,text:,Text:,...;1,2,3,4,...
		CONTENT_SPECIFIC_INDEXLESS,

		// # Target leaf content parts in general series:
		CONTENT_GENERIC_INDEXED,

		/* MARKUP CODED */
		MARKUP_SPECIFIC_INDEXLESS,
		MARKUP_GENERIC,
		// # Hierarchy sensitive per document format.markup type:
		MD, // e.g. #,#,...; ##;##;...; ---,---,...;
		ODT,
		DOCX,
		TEX,
		HTML// e.g. h1,h1,...;h2,h2,...;(section,section,..<-no context)
	};
	private PatternType/*SheetTypes*/ patternType;
	private PatternType/*SheetTypes*/ patternTypeComplement;




	// ======= CONSTRUCTOR
	/**
	 * Constructor creating a Generic type pattern. (default)
	 * (boolean had disadvantage to only allow 2 possibilities)
	 *
	 * @param c regular expression string
	 */
	private Muster(String c) {
		this(c, /*SheetTypes*/PatternType.GENERIC);
	}



	/**
	 * Constructor creating a pattern of given type.
	 *
	 * @param c regular expression string
	 * @param patternType The pattern type to use
	 */
	private Muster(String c, PatternType patternType) {
		this(c, patternType, null);
	}



	/**
	 * Constructor creating a pattern of given type.
	 *
	 * @param c regular expression string
	 * @param patternType The pattern type to use
	 * @param patternTypeComplement The pattern type tuple counterpart.
	 */
	private Muster(String c, PatternType patternType
			, String patternTypeComplementName) {
		this.patternString = c;
		this.patternType = patternType;
		this.patternTypeComplement
			= Muster.getByName(patternTypeComplementName);
		String complementName = "null";
		if (patternTypeComplement != null)
			complementName = patternTypeComplement.name();
		System.out.println("Muster: Constructor: Assigned tuple: ("
				+ this.patternType.name() + ", "
				+ complementName
				+ ") | patternTypeComplementName: "
				+ patternTypeComplementName
				);
	}




	// ======= METHODS
	/**
	 * Converts a numbering of another pattern to this pattern.
	 *
	 * @param new_numbering_but_not_converted
	 * @return the converted numbering|index.
	 */
	public String convertNumbering(
			String new_numbering_but_not_converted) {
		return convertNumbering(new_numbering_but_not_converted, -1);
	}
	/**
	 * Converts a numbering of another pattern to this pattern.
	 *
	 * @param new_numbering_but_not_converted
	 * @param forced_new_exercise_position
	 * @return the converted numbering|index.
	 */
	public String convertNumbering(
			String new_numbering_but_not_converted
			, int forced_new_exercise_position) {

//		 if (this.isWordedPattern()) {
//			 if (!numbering.contains(this.patternString)) {
//				 ;
//			 }
//		 }

		/*
		Usually the exercise number (exercise position in a sheet|draft)
		is not given and therefore often is null when
		new_numbering_but_not_converted is a different numbering format
		than INT. It is only given if we convert
		*/

		char[] chars = {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'h'
				, 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q'
				, 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
		};

		if (forced_new_exercise_position != -1) {
			// => convert forced_number from integer to character
			// e.g. 1=>a, 2=>b, ...

			// SOURCE IS NOT ONLY ONE SINGLE INTEGER ?
			if (new_numbering_but_not_converted.length() > 1
					// null => INT
					&& Muster.getMusterThatMatches(
						new_numbering_but_not_converted) != null) {
				// Start from left, replace first number that occurs:
				char[] new_numbering_but_not_converted_chars
					= new_numbering_but_not_converted.toCharArray();
				int first_number_index = -1;
				while (++first_number_index <
						new_numbering_but_not_converted_chars.length) {
					char candidate
						= new_numbering_but_not_converted_chars[first_number_index];
					if (Character.isLetter(candidate)) {
						new_numbering_but_not_converted_chars[first_number_index]
							= ("" + forced_new_exercise_position).charAt(0);
						// Convert char array to string on the fly:
						return new String(new_numbering_but_not_converted_chars);
						//return new_numbering_but_not_converted.replaceFirst(String.valueOf(candidate), replacement);
						//break;
					}
				}
			}
/*
			int exercise_position = -1;
			while (++exercise_position < forced_new_exercise_position) {
				// How many digits? => 1 always. It's not possible to
				// know which are on another level. And even more
				// difficult say how many they are.
				// Not relevant if only 1 digit! TODO: How many
				// declarations per digit/level? A limit? Always ten|3?
			}
			exercise_position + 1;
*/
			/*
			At this point it's only interesting which type to deliver
			as we have only one (1) digit! (see above comment)
			=> Which type comes first in the pattern?
			*/
			String thisPattern = this.name().toLowerCase().trim();
			//thisPattern = thisPattern.replaceAll("space", "");
			// e.g. SPACECHARSPACE (though NEWLINECHARSPACE were better)
			String prefix = "";
			String numbering_converted = "";
			// e.g. if providing splitby:
			if (Pattern.compile("^space").matcher(thisPattern).find()) {
				prefix = " ";
			}
			else if (Pattern.compile("^newline").matcher(thisPattern).find()
					|| Pattern.compile("^rn").matcher(thisPattern).find()) {
				prefix = System.getProperty("line.separator");//"\r\n";
			}


			numbering_converted = prefix;

			// === FIRST PART
			// CHAR
			if (Pattern.compile("^char").matcher(thisPattern).find()) {

				if (forced_new_exercise_position < chars.length) {
					numbering_converted += String.valueOf(
							chars[forced_new_exercise_position - 1]);
				}

			}
			// AUFGABE
			else if (Pattern.compile("^aufgabe").matcher(thisPattern)
					.find()) {
				numbering_converted += "Aufgabe "
					+ forced_new_exercise_position;

			}
			// EXERCISE
			else if (Pattern.compile("^exercise")
					.matcher(thisPattern).find()) {
				numbering_converted += "Exercise "
					+ forced_new_exercise_position;

			}
			// SOLUTION
			else if (Pattern.compile("^solution").matcher(thisPattern)
					.find()) {
				numbering_converted += "Solution "
					+ forced_new_exercise_position;

			}
			// LOESUNG
			else if (Pattern.compile("^loesung").matcher(thisPattern)
					.find()) {
				numbering_converted += "L\u00F6sung " + forced_new_exercise_position;

			}
			// INT
			else {
				numbering_converted += forced_new_exercise_position;
			}


			// === END PART
			//Map<Character, String> charnameMap
			//= new HashMap<Character, String>();

			// DOT
			if (Pattern.compile("dot$").matcher(thisPattern)
					.find()) {
				// NOTE: matches always inserts ^ and $ itself! Meh.
				numbering_converted += ".";

			}
			// COLON
			else if (Pattern.compile("colon$").matcher(thisPattern)
					.find()) {
				numbering_converted += ":";

			}
			// BRACKET
			else if (Pattern.compile("[)]$").matcher(thisPattern)
					.find()) {
				numbering_converted += ")";

			}

			if (!numbering_converted.isEmpty()) {
				return numbering_converted;
			}


			return String.valueOf(forced_new_exercise_position);

		}
		//else convert old to new declaration pattern.


		String numbering_converted = "";
		Muster source_numbering_muster = Muster.getMusterThatMatches(new_numbering_but_not_converted);
		// it may be null if the new_numbering_but_not_converted
		// is INT and only INT (e.g.).
		if (source_numbering_muster == null) {
			// => it's INTeger only. No conversion required.
			// => convert to default: INTDOT
			return new_numbering_but_not_converted + ".";
		}
		//else more difficult:
		// Source and target numbering formatting is already the same?
		if (this.equals(source_numbering_muster)) {
			return new_numbering_but_not_converted;
		}


		// 1) get the exercise_position that corresponds to
		// the new_but_not_converted_numbering.
		/*
		NOTE: THAT'S IMPOSSIBLE TO DERIVE. IF WE HAVE E.G. 1.3.4.
		How many will have been in 1.2.x ?? Impossible.
		SUPERFLUOUS ANYWAY??

		new_numbering_but_not_converted
			= new_numbering_but_not_converted.trim();
		int digit_count
			= new_numbering_but_not_converted.length();
		// Start from the right side with least important digit:
		int digit = digit_count;
		int number_system_base = 10; //=>int weight = 1, 10, 100, .. ;
		int exercise_position = 0;
		while (--digit > -1) {
			// seek it:
			char candidate = new_numbering_but_not_converted.charAt(digit);
			int chars_index = -1;
			while (++chars_index < chars.length) {
				if (chars[chars_index] == candidate) {
					//return chars_index;
					break;
				}
			}
			// found it?
			if (chars_index != -1 && chars_index < chars.length) {
				// => it's a char!
				//numbering_converted = TODO use for conversion directly
				exercise_position += chars_index * (number_system_base ^ digit);
				continue ;
			}
			//else it's an integer already hopefully, so check if it's not some other non-enlisted char:

			if (Integer.valueOf(new_numbering_but_not_converted) == (int)candidate) {

			}


			exercise_position += chars_index * (number_system_base ^ digit);


		}

		*/



		// TODO remove when the bottom which is for more than
		// 1 digit only works.
		if (true) return new_numbering_but_not_converted;


		/*
		Note: If an explicit numbering is given, then it will match the
		target numbering pattern.
		If, on the other hand, a new numbering is not specified
		explicitely (thus it is not user defined|custom), then it
		makes no sense to convert INTDOT exercises to 1.1, 1.2,2.1,2.2.
		Instead it is converted to 1.1, 1.2, 1.3, 1.4,... for INTDOTINT.
		*/
		String[] parts = null;
		parts = new_numbering_but_not_converted.split(source_numbering_muster.getDelimiter());
		int[] numbers;
		/*
		Note: The following is hardly used because we never generate
		numberings like 1.1, 1.2, ... instead we generate 1., 2., ...
		despite the INTDOTINT being the target format.
		The following would allow for INTDOTINT being converted properly
		to INTMINUSINT.
		*/
		//if (this.equals(INTDOTINT) || this.equals(INTMINUSINT)) {
		numbers = new int[parts.length];/* <--This will result in some
				empties to the end but it does not matter for now. */
		int numbers_index = -1;

		// Forced a certain exercise position?
		if (forced_new_exercise_position != -1) {
			numbers = new int[] { forced_new_exercise_position };
		}
		// Get numbers out of splitted parts:
		else {
			// Override generic solution for determining the parts
			// from above?
			switch (source_numbering_muster) {

			// source is in format
			case LOESUNG:
			case SOLUTION:
			case AUFGABE:
			case EXERCISE:
				String relevant = new_numbering_but_not_converted
					.replaceFirst("[Ll](oe|\u00F6)sung[ ]*", "");
				relevant = new_numbering_but_not_converted
					.replaceFirst("[Ss]olution[ ]*", "");
				relevant = new_numbering_but_not_converted
					.replaceFirst("[Aa]ufgabe[ ]*", "");
				relevant = new_numbering_but_not_converted
					.replaceFirst("[Ee]xercise[ ]*", "");

				parts = new String[] { relevant };
				if (Global.containsInt(parts[0])) {
					parts[0] = Global.toNumbering(parts[0]);
				}
				else {
					// Choose a number randomly:
					parts[0] = (Math.random() * 1000) + "";
				}
				break;

			case INTCHAR:
				parts = new String[1];
				if (Global.containsInt(new_numbering_but_not_converted)) {
					parts[0] = Global.toNumbering(
							new_numbering_but_not_converted);
				}
				else {
					// Choose a number randomly:
					parts[0] = (Math.random() * 1000) + "";
				}
				break;

			case CHARUFGABE:
				break;

			default:
				System.out.println("The generic solution above has"
						+ " already treated this declaration pattern. "
						+ source_numbering_muster.toString());

			}

			// Get numbers out of splitted parts
			for (String part : parts) {
				if (Global.isInt(part)) {
					numbers[++numbers_index] = Global.getInt(part);
				}
			}
		}
		String delimiter = getDelimiter();
		numbers_index = 0;// <- reset as this variable is reused
		for (int number : numbers) {
			/*
			For example to convert from 1.1, 1.2, 2. to 1., 2., 3., ...
			it is required to know how many previous
			exercises there have been.
			*/
			switch (this) {

			// target format
			default:// It is probably INT and then everything is okay.
				System.out.println("Unknown source numbering pattern"
						+ " (Muster): "
						+ source_numbering_muster.toString());
			case INTDOT:
			case INTDOTBRACKET:
			case INTBRACKET:
			case INTCOLON:
//			  case INTBRACKETSQUARE:
//			  case INTDOTINTDOT:
//			  case INTMINUSINTMINUS:
				numbering_converted += number + delimiter;
				break;

			case INTDOTINT:
			case INTMINUSINT:
				if (numbers_index != 0) {
					numbering_converted += delimiter;
				}
				if (forced_new_exercise_position != -1) {
					numbering_converted += forced_new_exercise_position;
					return numbering_converted;
				}
				numbering_converted += number;
				break;

//			  case INTDOTCHARUFGABE:
//					int patternString_index = -1;
//					int patternString_length = this.patternString.length();
//					boolean matched = this.patternString.matches(
//							CHARUFGABE.patternString);
//					while (patternString_index < patternString_length
//							&& matched) {
//						matched = patternString
//							substring(patternString_index)
//							.matches(patternString);
//					}
			case LOESUNG:
			case SOLUTION:
			//case CHARLOESUNG:
			case AUFGABE:
			case EXERCISE:
			case CHARUFGABE:
				if (numbers_index == 0) {
					numbering_converted += Global.toFairy(delimiter);
				}
				numbering_converted += number;
				break;

			//TODO fix delimiter:How to get to a proper generic delimiter?
			case INTCHAR:
				if (numbers_index == 0) {
					numbering_converted += Global.toFairy(delimiter);
				}
				numbering_converted += number + Global.toFairy(delimiter);
				break;

			}

			++numbers_index;
		}

		return numbering_converted;
		//}
		//return null;

	}



	/**
	 * Get delimiter.
	 *
	 * @return The delimiter.
	 */
	public String getDelimiter() {
		//a shortcut (for a generic way see below)
		if (this.name().contains("DOTBRACKET")) {
			return ".)";
		}
		else if (this.name().contains("DOT")) {
			return ".";
		}
		else if (this.name().contains("BRACKET")) {
			return ")";
		}
		else if (this.name().contains("COLON")) {
			return ":";
		}
		// Is it a worded pattern, the name is contained in the pattern?
		else if (this.patternString.toLowerCase().contains(this.name().toLowerCase())) {
			return this.name();
		}
		else if (this.equals(CHARUFGABE)) {
			return "Aufgabe";//<-TODO standardize here or not? currently we do.
		}
		else if (this.equals(LOESUNG)) {
			return "L\u00F6sung";// could also be Loesung.
		}
		// A generic way for determining a delimiter:
		String delimiter = this.patternString.replaceAll("\\[\\+dDsS\\]", "");
		delimiter = Global.replaceAllNonEscaped(delimiter, "+");
		//	] abcABC\.abcABC[ shall not be replaced
		//	] abcABC.abcABC[ must be replaced (. represents everything)

		String[] candidates = new String[] { ".", "-", ":", ")"/*, ".)"*/ };
		for (String candidate : candidates) {
			if (Global.isWithinRegularExpressionSquareBrackets(delimiter
						, candidate)) {
				return candidate;
			}
			// Not within brackets [] but escaped? <-- relevant
			// for . and ) only relevant
			// for regex reserved characters: e.g. '.', ']' and ')' .
			delimiter = Global.replaceAllNonEscaped(delimiter, candidate);
			if ( delimiter.contains(".") ) {
				return ".";
			}
		}
		return delimiter.replaceAll("[a-zA-Z]", "");

	}



	/**
	 * Return the compiled regular expression pattern of the
	 * respective Muster.
	 *
	 * @return kompilierter regulaerer Ausdruck
	 */
	public Pattern getPattern() {
		return Pattern.compile(patternString);
	}
	public String getPatternString() {
		return patternString;
	}



	public boolean isMarkupPhraseFilter() {
		return this.patternType.name().toLowerCase().contains("markup");
	}



	public boolean isGeneric() {
		return this.patternType.name().toLowerCase().contains("generic");
	}



	public boolean isSpecific() { // explicit
		return this.patternType.name().toLowerCase().contains("specific");
	}



	// Useful for skipping costly check if a set of decs is indexed.
	// Note: A markup pattern can not exclude that there is a text based
	// index in the content.
	// Attention: Use this only if there is index detection that takes
	// content part head (first words) into account. TODO
	public boolean canResultBeIndexed() {
		return this.isMarkupPhraseFilter()
			// It is not a markup phrase filter:
			|| this.canMatchIndex();
	}



	// Use instead of canResultBeIndexed when ignoring a potential text
	// based (e.g. manually hard-coded) index in the content part.
	public boolean canMatchIndex() {
		return this.isGeneric()
			// A single integer is the minimum required for an index:
			&& this.patternString.contains("\\d")//TODO <- matches?
		;
	}



	public boolean canResultContainNumber() {
		int i = Global.getInt(this.pattern);
		return (!i || i == "") && (
				!this.patternType.name().toLowerCase().contains("generic")
				|| !this.patternString.contains("\\d")//TODO works?
		);
	}



	// TODO Consider the new patterns.
	// TODO Rename to isContentPatternGuaranteedIndexless or
	// isContentPhraseFilterGuaranteedIndexless see also:
	// https://github.com/worlddevelopment/exercise_database/issues/1
	public boolean isWordedPattern() {
//		if ( this.equals(AUFGABE | EXERCISE | CHARUFGABE | LOESUNG | SOLUTION) ) {
		return this.patternString.toLowerCase().contains(
				this.name().toLowerCase())
			|| this.equals(CHARUFGABE);
	}

	public int getScoreOfPattern() {

		switch (this) {

		// The more specific the more weighted (score) it gets.
		case LOESCOLON:
		case SOLCOLON:
			return 1100;

		case CHARLOESCOLON:
		case CHARSOLCOLON:
			return 1050;

		case LOESUNG:
		case SOLUTION:
			return 1000;

		case AUFGABE:
			return 1000;
		case EXERCISE:
			return 800;
		case CHARUFGABE:
			return 500;// <-It might be too risky to allow higher value.

//		case INTDOTINTDOT:
//			return 500;
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
//		case INT:
//			return 50;
		default:
			return 1;
			/*
			user customized patterns get 0 points, so obtain lowest
			priority as it's quite probable that more things go wrong
			for the all patterns not specified in this class.
			*/
		}

	}

	/**
	 * The pattern type, default is GENERIC.
	 */
	public PatternType getPatternType() {
		return patternType;
	}

	/**
	  * If this pattern is solution specific or not.
	  */
	public boolean isSolutionPattern() {
		return this.patternType.equals(PatternType.SOLUTION);
	}

	/**
	  * If this pattern is solution specific or not.
	  */
	public boolean isGenericPattern() {
		return this.patternType.equals(PatternType.GENERIC);
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
		return null;
		/*
		Make sure that this quite common case (because of user
		customizable patterns) is checked for.
		It's no problem if there is no Muster to a Pattern. These
		Muster only are the default repository of patterns.
		*/
	}



	public static Muster getMusterFromPatternString(String patternString) {
		for (Muster m : Muster.values()) {
			if (m.getPatternString().equals(patternString)) {
				return m;
			}
		}
		return null;
		/*
		Make sure that this quite common case (because of user
		customizable patterns) is checked for.
		It's no problem if there is no Muster to a Pattern. These
		Muster only are the default repository of patterns.
		*/
	}



	public static Muster getMusterByName(String name) {
		for (Muster m : Muster.values()) {
			if (m.name().toLowerCase().equals(name.toLowerCase())) {
				return m;
			}
		}
		return null;
		/*
		Make sure that this quite common case (because of user
		customizable patterns) is checked for.
		It's no problem if there is no Muster to a Pattern. These
		Muster only are the default repository of patterns.
		*/
	}



	/**
	 * Get a muster/Pattern-wrapper that matches the given string or null.
	 * @param string
	 * @return
	 */
	public static Muster getMusterThatMatches(String string) {
		if (Global.isInt(string) && (Global.getInt(string)
					+ "").length() == string.length()) {
			// => it's INT and only INT: (would be matched anyway many
			// times, e.g. in INTCHAR, INTDOT, ..)
			return null; //TODO give it a random value or better null?
		}
		for (Muster m : Muster.values()) {
			if (string.matches(m.getPatternString())) {
				return m;
			}
		}
		return null;
		/*
		Make sure that this quite common case (because of user
		customizable patterns) is checked for.
		It's no problem if there is no Muster to a Pattern. These
		Muster only are the default repository of patterns.
		*/
	}



	/**
	 *
	 * @param pattern
	 * @return
	 */
	public static String getValueByPattern(Pattern pattern) {
		for (Muster m : Muster.values()) {
			if (m.getPattern().pattern().equals(pattern.pattern())) {
				return m.name();// enum name or enum value
			}
		}
		return null;
	}





}
