package aufgaben_db;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
		
		

		
	/**
	 * 2. Uebergabeparamter geaendert: handelt sich nun um Muster (siehe enum Muster)
	 * 
	 * Sucht im �bergebenen Text (Zeilenweise im String-Array gespeichert) nach Aufgabendeklarationen,
	 * die bestimmte Pattern erf�llen. Gibt ein 2-dimensionales String-Array aus, welches folgenderma�en
	 * aufgebaut ist: <br>
	 * Die erste Dimension gibt um die wievielte Deklaration es sich handelt. <br>
	 * Die zweiter Dimension hat nur 4 (0 bis 3) Eintr�ge. <br> <br>
	 * String[x][0]: "zahl", Kennzahl, welchen Pattern zugetroffen hat (auch �bergabeparameter "zahl") <br>
	 * String[x][1]: Der Ausdruck/ das Wort, das mit obigem Pattern gefunden wurde <br>
	 * String[x][2]: Zeilennummer (Anmerkung: Ist das nicht �berfl�ssig?) <br>
	 * String[x][3]: Falls die Zeile mehr als ein Wort beinhaltet, noch das 2. nachfolgende Wort <br>
	 *  <br>
	 * @param text String-Array (zeilenweise) des zu untersuchenden Texts <br>
	 * @param zahl Kennzahl f�r die Patternsuche: <br>
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
		
		// Direkte Aufgabendeklarationen angegeben:
		if (muster.isWordedPattern()) {
		// if (zahl < 20) {
			System.out.println("Eine direkte Aufgabendeklaration wird gesucht");
			ergebnisFuerHashMap = new String[text.length][4];
			for (int zeile = 0; zeile < text.length; zeile++) {
				String[] woerter = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
				if (woerter.length > 0) {
					wort = woerter[0];
					System.out.println("das erste Wort lautet: " + wort);
					gleich = RegExFinder.matches(wort, pattern);
					// matcher = pattern.matcher(wort);
					// gleich = matcher.matches();
					if(gleich) {
						System.out.println("Gleichheit! Zeile: " + zeile);
						
						Declaration loopDeclaration = new Declaration(muster, wort, zeile);
						//ev. nicht noetig: welches pattern
						//konnte man auch anders loesen, momentan aber mal so:
						// ergebnis[zaehler][0] = Integer.toString(zahl);
						
						ergebnisFuerHashMap[zaehler][0] = muster.toString();						
						//der genaue Ausdruck wird gespeichert
						ergebnisFuerHashMap[zaehler][1] = wort;
						//die passende Zeile wird gespeichert
						ergebnisFuerHashMap[zaehler][2] = Integer.toString(zeile);
						// das 2. Wort der Zeile wird gespeichert, wenn es existiert 
						// und falls es eine nonworded regex erf�llt
						// auch als Index gespeichert
						if (woerter.length >= 2) {
							for (Muster m : Muster.values()){
								if (!m.isWordedPattern()) {
									if (RegExFinder.matches(woerter[1], m.getPattern())) {
										loopDeclaration.setIndex(RegExFinder.getContainedIndexNumber(woerter[1]));
									}
								}
							}
						
							loopDeclaration.setSecondWord(woerter[1]);
							ergebnisFuerHashMap[zaehler][3] = woerter[1];
						}
						else {
							ergebnisFuerHashMap[zaehler][3] = "";
						}
						// Head = Anfangswörter der Aufgabe werden gemerkt
						boolean noMoreLines = false;						
						String[] headWords = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
						while (headWords.length < 6) {
							try {
								zeile++;
								headWords = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
							} catch (Exception e) {
								noMoreLines = true;
								break;
							}
						}
						if (!noMoreLines){
							ArrayList<String> head = new ArrayList<String>();
							head.add(headWords[2]);
							head.add(headWords[3]);
							head.add(headWords[4]);
							head.add(headWords[5]);
							loopDeclaration.setHead(head) ;
						}						
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
			ergebnisFuerHashMap = new String[text.length][3];
		
			for (int zeile = 0; zeile < text.length; zeile++) {
				
				String[] woerter = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
				if (woerter.length > 0) {
					wort = woerter[0];
					System.out.println("das erste Wort lautet: " + wort);
					gleich = RegExFinder.matches(wort, pattern);
					// matcher = pattern.matcher(wort);
					// gleich = matcher.matches();
					if(gleich) {
						System.out.println("Gleichheit! Zeile: " + zeile);
						// NEU
						Declaration loopDeclaration = new Declaration(muster, wort, zeile);
						
						//ev. nicht noetig: welches pattern
						//konnte man auch anders loesen, momentan aber mal so:
						// ergebnis[zaehler][0] = Integer.toString(zahl);
						ergebnisFuerHashMap[zaehler][0] = muster.toString();
						//der genaue Ausdruck wird gespeichert
						ergebnisFuerHashMap[zaehler][1] = wort;
						//die passende Zeile wird gespeichert
						ergebnisFuerHashMap[zaehler][2] = Integer.toString(zeile);
						
						// Die Deklaration wird als Index gespeichert und dann zu den declarations hinzugef�gt
						loopDeclaration.setIndex(RegExFinder.getContainedIndexNumber(wort));
						
						// Head = Anfangswörter der Aufgabe werden gemerkt
						boolean noMoreLines = false;						
						String[] headWords = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
						while (headWords.length < 5) {
							try {
								zeile++;
								headWords = MethodenWortSuche.teileZeileInWoerter(text[zeile]);
							} catch (Exception e) {
								noMoreLines = true;
								break;
							}
						}
						if (!noMoreLines){
							ArrayList<String> head = new ArrayList<String>();
							head.add(headWords[1]);
							head.add(headWords[2]);
							head.add(headWords[3]);
							head.add(headWords[4]);
							if (head != null) {
								loopDeclaration.setHead(head);
							}
						}			
						
						foundDeclarations.declarations.add(loopDeclaration);
						System.out.println("Die Deklaration lautet: " + loopDeclaration.getFirstWord());
						zaehler++;
					}
				}
			}
		}
		System.out.println("Insgesamt wurden " + zaehler + " passende Ausdruecke gefunden");
		
//		String[][] ergebnisGekuerzt = MethodenWortSuche.kuerzeArray(ergebnisFuerHashMap, zaehler);
		
		return foundDeclarations;			
	}
	
	private static boolean matches(String wort, Pattern pattern) {
		Matcher matcher = pattern.matcher(wort);
		boolean gleich = matcher.matches();
		return gleich;
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
				if (isInt(c)) {
					// Wenn schon zahl gefunden wurde, f�ge Sie zu einer Zahl zusammen.
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
	
	
	/**
	 * Ueberprueft, ob der �bergeben String g�ltig zu einem Integer geparsed werden kann.
	 * 
	 * @param i Zu ueberpruefender String
	 * @return true, falls er geparsed werden kann, false, falls nicht
	 */
	private static boolean isInt(String c) {
		
		// ueberprueft, ob der Wert im String ein Integerwert ist
		try {
			Integer.parseInt(c);
			return true;
		}
		catch(NumberFormatException FehlerZahlenformat) {
			return false;
		}
	}
}


