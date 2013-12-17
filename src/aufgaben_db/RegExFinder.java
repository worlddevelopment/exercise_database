package aufgaben_db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bouncycastle.util.Arrays;

import HauptProgramm.Declaration;
import HauptProgramm.DeclarationSet;
import HauptProgramm.IndexNumber;




/**
 * Die regulaeren Ausdruecke und Pattern werden hier zugewiesen
 * Das sollte alles noch ergaenzt werden, ist noch nicht komplett
 * <br>
 * <br>
 * ein paar Befehle zu Pattern und Matches, die ich ev. bei Bedarf noch verwende
 * befinden sich noch unten auskommentiert im Dokument, 
 * ...spaeter noch wegloeschen
 * 
 * @author sabine
 * 
 */
public class RegExFinder {
		
//	// Block 10: sucht nach feststehenden Ausdruecken
//	// Hier muessen anschließend noch die 2. Worte untersucht werden
//	
//	// Pattern11 sucht das Wort Aufgabe
//	public static final String p11 = "Aufgabe";
//	public static final Pattern pattern11 = Pattern.compile(p11);
//	// Pattern12 sucht das Wort Exercise
//	public static final String p12 = "Exercise";
//	public static final Pattern pattern12 = Pattern.compile(p12);
//	
//	// ev. noch weitere Einbauen
//	
//	
//	
//	// Block 20: sucht nach Kombinationen 1.1., 1.1, 1.1:,.....
//	// Testung erfolg ueber Zahlenkombination
//		
//	// Pattern21 sucht Ausdruecke wie 4.1.
//	public static final Pattern pattern21 = Pattern.compile("([\\d]+[\\.]){2}");
//	// Pattern22 sucht Ausdruecke wie 4.1
//	public static final Pattern pattern22 = Pattern.compile("[\\d]+[\\.][\\d]+");
//	
//	// es fehlen ev. noch z.B. 4.4:, 4.1.:, 4.1(, .....
//	
//	
//	
//	// Block 30: sucht Ausdruecke mit nur einer Zahl, 
//	// hier muessen ev. 2 Pruefmechanismen durchlaufen werden
//	// nur eine einzelne Zahl ohne sonstige Hinweise ist fast nicht moeglich
//	
//	// Pattern31 sucht Ausdruecke wie 4.
//	public static final Pattern pattern31 = Pattern.compile("[\\d]+[\\.]");
//	// Pattern32 sucht Ausdruecke wie 4.)
//	//noch pruefen, ob der so stimmt!:
//	public static final Pattern pattern32 = Pattern.compile("[\\d]+[\\.][)]");
	
		
	final static int EXERCISE_COUNT_EXPECTED_MAXIMUM_IF_ALL_TEXT_IN_ONE_LINE = 50;
		

		
	/**
	 * 2. Uebergabeparamter geaendert: handelt sich nun um Muster (siehe enum Muster)
	 * 
	 * Sucht im uebergebenen Text (Zeilenweise im String-Array gespeichert) nach Aufgabendeklarationen,
	 * die bestimmte Pattern erfuellen. Gibt ein 2-dimensionales String-Array aus, welches folgenderma�en
	 * aufgebaut ist: <br>
	 * Die erste Dimension gibt um die wievielte Deklaration es sich handelt. <br>
	 * Die zweiter Dimension hat nur 4 (0 bis 3) Eintraege. <br> <br>
	 * String[x][0]: "zahl", Kennzahl, welchen Pattern zugetroffen hat (auch Uebergabeparameter "zahl") <br>
	 * String[x][1]: Der Ausdruck/ das Wort, das mit obigem Pattern gefunden wurde <br>
	 * String[x][2]: Zeilennummer (Anmerkung: Ist das nicht ueberfluessig? --Addition J.R.I.B.: The line number 
	 * 				 is required to know where in the plain-text version of the document the subsequent lines can
	 *               be found if this information should be needed.) <br>
	 * String[x][3]: Falls die Zeile mehr als ein Wort beinhaltet, noch das 2. nachfolgende Wort <br>
	 *  <br>
	 * @param text String-Array (zeilenweise) des zu untersuchenden Texts <br>
	 * @param zahl Kennzahl fuer die Patternsuche: <br>
	 *  <br>
	 * 11 ^= "Aufgabe" <br> 
	 * 12 ^= "Exercise" <br>
	 * 21 ^= "([\\d]+[\\.]){2}" <br>
	 * 22 ^= "[\\d]+[\\.][\\d]+" <br>
	 * 31 ^= "[\\d]+[\\.]" <br>
	 * 32 ^= "[\\d]+[\\.][)]" <br>
	 * 
	 * @return 2-dim String-Array nach obigem Format, falls ein korrekte Kennzahl angegeben wurde (11,12,21,22,31,32)
	 * sonst null
	 */
	// public static String[][] sucheMuster(String[] filename, int zahl) {
	public DeclarationSet sucheMuster(String[] text, Muster muster) {
		
		Pattern pattern = muster.getPattern();
		String[][] ergebnisFuerHashMap = null;
		DeclarationSet foundDeclarations = new DeclarationSet(muster);
           
			
//		Matcher matcher;
		String wort;
		boolean gleich;
		int zaehler = 0;
		if (text == null || text.length < 1) {
			System.out.println(Global.addMessage("Text given to RegExFinder.sucheMuster method was null or empty! Aborting ...", "danger"));
			return null;
		}
		int woerter_to_analyse_maximum = 4;//to cover e.g. 'Solution to Exercise 1:'
		if (text.length == 1) {//Then text extraction did not manage to insert line breaks!
			woerter_to_analyse_maximum = Integer.MAX_VALUE;//so that it will be overwritten!
		    //=>all words will be examined. Because 1 or 2 exercises more is better than finding
			//only one exercise!
		}
		
		int ergebnisFuerHashMap_length = text.length;
		if (ergebnisFuerHashMap_length < EXERCISE_COUNT_EXPECTED_MAXIMUM_IF_ALL_TEXT_IN_ONE_LINE) {
		/*50 exercises are a maximum to be expected, especially
		as this here only serves as a safety feature against the case that all words of the document
		are within one line. If we assume that multiple sheets or multiple pages of one sheet could
		produce one line for each sheet/page, then this 10 page limit with each 3 exercises per page
		will be taken as an upper bound. This results in 10 * 3 = 30 exercises to store. Now to be safe
		10 * 5 = 50 is taken as upper bound.*/
			ergebnisFuerHashMap_length = EXERCISE_COUNT_EXPECTED_MAXIMUM_IF_ALL_TEXT_IN_ONE_LINE;
		}
		
		// Direkte Aufgabendeklarationen angegeben:
		if (muster.isWordedPattern()) {
		// if (zahl < 20) {
			System.out.println("Eine direkte Aufgabendeklaration wird gesucht");
			for (int zeile = 0; zeile < text.length; zeile++) {
				String[] woerter = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
				//if (woerter.length > 0) {
				if (woerter.length < woerter_to_analyse_maximum) {//the minimum out of both
					woerter_to_analyse_maximum = woerter.length;
				}
				
				//Now examining the other words of the line too, because of e.g. 'Loesung zu Aufgabe 1'.
				int word_index = 0;
				for (; word_index < woerter_to_analyse_maximum; word_index++) {
					wort = woerter[word_index];
					String message;
					message = "das " + word_index + ". Wort lautet: " + wort;
					if (woerter.length > word_index + 1) {
						message += " \t nachfolgend: " + woerter[word_index + 1];
					}
					System.out.println(message);//if (wort.equals("") /*&& TODO!emptyLine_accepted_as_exercise_splitter*/) continue;
					gleich = RegExFinder.matches(wort, pattern);
					// matcher = pattern.matcher(wort);
					// gleich = matcher.matches();
					if(gleich) {
						System.out.println("Gleichheit! Zeile: " + zeile);
						
						//der genaue Ausdruck wird gespeichert
						//die passende Zeile wird gespeichert
						Declaration loopDeclaration = new Declaration(muster, wort, zeile, text[zeile]);
						
						// das 2. Wort der Zeile wird gespeichert, wenn es existiert 
						// und falls es eine nonworded regex erfuellt
						// auch als Index gespeichert
						storeSubsequentWordsAndLookForIndexTherein(loopDeclaration, woerter);
						
						// Head = Anfangswoerter der Aufgabe werden gemerkt
						storeHeadWords(loopDeclaration, text, zeile);
					
						
							
						foundDeclarations.declarations.add(loopDeclaration);
						System.out.println("Die Deklaration lautet: " + loopDeclaration.getFirstWord() + " " + loopDeclaration.getSecondWord());
						zaehler++;
					}
				}
			}
		}
			
		//regulaerer Ausdruck angegeben:
		else {
		// if (zahl > 20) {
			int sheetheadingDeclaration_index = 0;//<-- necessary because multiple sheets could
			// be joined in one document! Then we have to dismiss the heading on the new page
			// instead of the absolute total first declaration (at the point of time then most
			// often '1. (Exercise)' because '1. Uebungsblatt' already has been removed.).
			
			for (int zeile = 0; zeile < text.length; zeile++) {
				
				String[] woerter = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
				//if (woerter.length > 0) {
				if (woerter.length < woerter_to_analyse_maximum) {//the minimum out of both
					woerter_to_analyse_maximum = woerter.length;
				}
				
				int word_index = 0;
				for (; word_index < woerter_to_analyse_maximum; word_index++) {
					wort = woerter[word_index];
					System.out.println("das " + word_index + ". Wort lautet: " + wort);
					gleich = RegExFinder.matches(wort, pattern);
					// matcher = pattern.matcher(wort);
					// gleich = matcher.matches();
					if(gleich) {
						System.out.println("Gleichheit! Zeile: " + zeile);
						//der genaue Ausdruck wird gespeichert
						//die passende Zeile wird gespeichert
						Declaration loopDeclaration = new Declaration(muster, wort, zeile, text[zeile]);

						// Die Deklaration wird als Index gespeichert und dann zu den declarations hinzugef�gt
						loopDeclaration.setIndex(RegExFinder.getContainedIndexNumber(wort));
						
						
						// das 2. Wort der Zeile wird gespeichert, wenn es existiert 
						// und falls es eine nonworded regex erfuellt
						// auch als Index gespeichert
						storeSubsequentWordsAndLookForIndexTherein(loopDeclaration, woerter);
						
						
						// Head = Anfangswoerter der Aufgabe werden gemerkt
						storeHeadWords(loopDeclaration, text, zeile);	
						
						// Is this a double?
						boolean is_this_a_double = false;
						for (Declaration foundDeclarationSoFar : foundDeclarations.declarations) {
							if (foundDeclarationSoFar.equals(loopDeclaration)) {
								//is_this_a_double = true;
							}
						}
						if (is_this_a_double) {
							System.out.println("Ausgelassene Deklaration, da doppelt: " + loopDeclaration.getFirstWord() + " "
									+ loopDeclaration.getSecondWord() + " " + loopDeclaration.getThirdWord());
							continue /*with the next word/line to examine for exercise declarations*/;
						}
						
						//that the loopDeclaration is added here is precondition for following header filtering
						foundDeclarations.declarations.add(loopDeclaration);
						System.out.println("Die Deklaration lautet: " + loopDeclaration.getFirstWord() + " "
								+ loopDeclaration.getSecondWord() + " " + loopDeclaration.getThirdWord());
						
						//One of the cases where the sheet's main heading (e.g. Exercisesheet xy)
						//could be recognized as exercise?
						if (muster.equals(Muster.INT)
								|| muster.equals(Muster.INTDOT)
								|| muster.equals(Muster.INTBRACKET)
								|| muster.equals(Muster.INTCOLON)
								|| muster.equals(Muster.INTDOTINT)
								|| muster.equals(Muster.INTDOTINTDOT)
								) {
							/*A special case for that can be solved investigating the numbers of 1st and 2nd exercise:*/
							//We start with potentially removing the total first declaration (probably '1. Uebungsblatt' at this point of time).
							if (foundDeclarations.declarations.size() > 1) {
								
								
								//determine the index of the previously found declaration prior to removing an element (that would result in a false or out of bounds index!) 
								int index_of_previously_found_dec;
								index_of_previously_found_dec = foundDeclarations.declarations.size() - 2;/*loopDeclaration is at position size() - 1*/
								
								//determine if removing of a false exercise declaration because of sheetheadings is required: 
								//only if the loopDeclaration is the immediately following one after the questionable exercise
								// declaration that probably rather is a sheetdraft heading!
								boolean removed_declaration;
								removed_declaration = false;
								
								if (loopDeclaration.equals(foundDeclarations.declarations.get(sheetheadingDeclaration_index + 1))
//											|| loopDeclaration.getIndex().equals(foundDeclarations.declarations.get(1).getIndex())
//											|| loopDeclaration.getIndex().getNumber(0) == foundDeclarations.declarations.get(1).getIndex().getNumber(0)
											) {
									//Filter out headings like 9. Exercise, whenever the number is higher than the exercises start with.
									//First check if it can be parsed to integer, then check if this parsed value is greater than
									//the next exercise's numbering:
									String wordContainingNumbering
									= foundDeclarations.declarations.get(sheetheadingDeclaration_index).getWordContainingNumbering();
									int decFoundFirst_numbering = -1;
									decFoundFirst_numbering = Global.getInt(wordContainingNumbering);
									
									wordContainingNumbering = loopDeclaration.getWordContainingNumbering();
									int loopDeclaration_numbering = -1;
									loopDeclaration_numbering = Global.getInt(wordContainingNumbering);
									
									//greater equal than because of e.g. if '1. Uebung\r\n1. Aufgabe'
									// then 1. Uebung should be removed too.
									if (decFoundFirst_numbering >= loopDeclaration_numbering) {
										System.out.println("Die Deklaration, die zuerst gefunden"
												+ " wurde, scheint die &Uuml;bungsblattnummer darzustellen: "
												+ foundDeclarations.declarations.get(sheetheadingDeclaration_index).getLineContent()
												+ " . Diese Deklaration wird entfernt.");
										foundDeclarations.declarations.remove(sheetheadingDeclaration_index);
										removed_declaration = true;
									}
									//Because it is desired to tolerate several exercises having the same enumeration number.
									//String first_word_previous = foundDeclarations.declarations.get(index_of_previously_found_dec).getFirstWord();
									//						if (Integer.parseInt(first_word_previous) >= Integer.parseInt(loopDeclaration.getFirstWord())) {
									//							foundDeclarations.declarations.remove(index_of_previously_found_dec);
									//						}
									
								}
								
								
								//Now used because if after e.g. '3. Exercise' '1. Uebungsblatt' reoccurs
								// because of multiple sheets in one document. Then we need to filter out the '1. Uebungsblatt' again:
								if (//loopDeclaration.getIndex().compare(foundDeclarations.declarations.get(index_of_previously_found_dec).getIndex()) < 0
										/*=> calling indexNumber is smaller => previous declaration found is smaller, e.g. 3. Exercise previously and now 1. Uebung */
										//||
										/*THE FOLLOWING COVERS GAPS,
									 i.e. transition from 2. Exercise to 8. Uebungsblatt
									 AND the other way round too e.g. transition from 8. Exercise to 2. Uebungsblatt
									 AS WELL AS transitions e.g. from 2. Exercise to 2. Uebungsblatt.
									 (Only 2. Exercise to 3. is spared!, not 2. Exercise to 4. - TODO, this could result in trouble)*/
										!removed_declaration
										&&
										/*Note: The first number of the index is the only important one here because x. Uebung and x.1.1 Exercise, x.1.2 Exercise, .. */
										foundDeclarations.declarations.get(index_of_previously_found_dec).getIndex()
										.getNumber(0) - loopDeclaration.getIndex().getNumber(0) != -1
										/*the above condition: if lastDeclarationIndexNumber - thisDeclarationIndexNumber not exactly apart by 1 (in ascending order, hence the minus sign)*/
										) {
									//Then the new exercise declaration candidate to be removed because it's a sheet heading instead potentially is:
									sheetheadingDeclaration_index = index_of_previously_found_dec + 1;
								}
								
							}
							
						}
						
						
						zaehler++;
					}
				}
			}
		}
		System.out.println("Insgesamt wurden " + zaehler + " passende Ausdruecke gefunden");
		
		return foundDeclarations;			
	}
	
	
	/**
	 * 
	 * @param loopDeclaration
	 * @param text
	 * @param zeile
	 */
	private static void storeHeadWords(Declaration loopDeclaration, String[] text, int zeile) {
		boolean moreLines = true;						
		String[] headWords;
		//Skip short lines? Why? Exercise bodies could be short too so if we look for those this way it makes no sens.
		//while (headWords.length < 5) {=> nothing is skipped anymore, all lines are being exermined
		try {
			zeile++;
			headWords = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
			if (headWords != null) {
				loopDeclaration.setHead(headWords);
			}
			
		} catch (Exception e) {
			moreLines = false;
		}
		//now this is the first line where there are 6 or more words, so potentially the exercise body
		if (!moreLines){//why only if there are more lines left? the exercise body head should be independent of that!?
			System.out.println("Warning: No more lines after exercise declaration!");
		}		
	}
	
	
	/**
	 * 
	 * @param loopDeclaration
	 * @param woerter
	 */
	private static void storeSubsequentWordsAndLookForIndexTherein(Declaration loopDeclaration, String[] woerter) {
		// das 2. Wort der Zeile wird gespeichert, wenn es existiert 
		// und falls es eine nonworded regex erfuellt
		// auch als Index gespeichert
		if (woerter.length > 1) {
			int wordCountToExermine = 1;
			loopDeclaration.setSecondWord(woerter[1]);
			if (woerter.length > 2) {
				wordCountToExermine = 2;
				loopDeclaration.setThirdWord(woerter[2]);
			}
			//Is there the exercise's index number within the following words?
			for (Muster m : Muster.values()){
				//now look for a regex index number within the following words of this declaration line
				if (!m.isWordedPattern()) {
					//examine second and the third word
					for (int words_within_line_index = 0; words_within_line_index < wordCountToExermine; words_within_line_index++) {
						if (RegExFinder.matches(woerter[words_within_line_index], m.getPattern())) {
							loopDeclaration.setIndex(RegExFinder.getContainedIndexNumber(woerter[words_within_line_index]));
							return;//break;//subsequent potential indices are ignored (the first one found is the relevant one because
							//the whole text could be within one single line without linebreaks and then the first 
							//is the correct one unless the first one already has been found in the first word!
						}
					}
				}
			}
		
		}
	}
	
	
	private static boolean matches(String wort, Pattern pattern) {
		Matcher matcher = pattern.matcher(wort);
		return matcher.matches();
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


