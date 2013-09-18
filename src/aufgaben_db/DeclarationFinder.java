/**
 * 
 */
package aufgaben_db;

import HauptProgramm.Declaration;
import java.util.ArrayList;

import Verwaltung.HashMapVerwaltung;
import HauptProgramm.DeclarationSet;

/**
 * "Unterprogramm" welches Methoden zum finden von Aufgabendeklarationen einer Uwbungsaufgabe bereitstellt.
 * 
 * @author Schweiner
 *
 */
public class DeclarationFinder {
	
	private static RegExFinder regExFinder = new RegExFinder();
	
	/**
	 * Untersucht das uebergebene Sheetdraft mit regulaeren Ausdruecken (siehe Muster.java) auf 
	 * Aufgabendeklarationen und gibt den passendsten Satz an Aufgabendeklarationen aus.
	 * 
	 * @param sheet	zu untersuchendes Sheetdraft
	 * @return DeclarationSet, fall eine passender Satz an Deklarationen gefunden wurde <br>
	 * null , falls keine passenden Deklarationen gefunden wurden.
	 */
	public static DeclarationSet findeDeklarationen(Sheetdraft sheet) {

		// SpeicherOrt fuer alle zu findenden Deklarationssets wird angelegt	
		ArrayList<DeclarationSet> foundDeclarationSets = new ArrayList<DeclarationSet>();
		
		// Es wird nach allen bekannten Deklarationsmustern gesucht und abgelegt
		for (Muster m : Muster.values()) {
			foundDeclarationSets.add(regExFinder.sucheMuster(sheet.getPlainText(), m));
		}
		
		System.out.println("Der folgende Schritt ist eventuell kritisch: (declaration aus declarationSet wird geloescht)");
		// Nun wird verglichen, gefiltert und ein Score erstellt, am Ende wird die sinnvollste Deklaration genommen
		
//		for (DeclarationSet set : foundDeclarationSets){
//			if(set.declarations.size() == 0) {
//				foundDeclarationSets.remove(set);
//			}
//		}
		
		// Leere Sets (kein Match aufgetreten) werden gel�scht.
		boolean[] removeEmptyDeclarations = new boolean[foundDeclarationSets.size()];
		for (int i = 0; i < removeEmptyDeclarations.length; i ++) {
			removeEmptyDeclarations[i] = false;
		}
		for (int i = 0; i < foundDeclarationSets.size(); i++) {
			if (foundDeclarationSets.get(i).declarations.size() == 0) {
				removeEmptyDeclarations[i] = true;
			}
		}
		for (int i = foundDeclarationSets.size() - 1; i >= 0; i--) {
			if (removeEmptyDeclarations[i] == true) {
				foundDeclarationSets.remove(i);
			}
		}
		
		System.out.println("Es wurden " + foundDeclarationSets.size() + " moegliche DeklarationsSets gefunden");
		
		for (DeclarationSet set : foundDeclarationSets) {
			for (Declaration dec : set.declarations) {
				System.out.println(dec.toString());
			}
		}
		
		// Bei allen Sets, die als erstes Wort einen Wort-Match haben, wird �berpr�ft, ob Sie auch von 
		// einem Index gefolgt werden, wie es sich f�r brave Aufgaben geh�rt.
		// (Also so etwas wie Aufgabe 1, Aufgabe 2 oder Aufgabe 3.5 etc....)
//		for (DeclarationSet set: foundDeclarationSets) {
//			set.clearWordedDeclarationsWithoutIndices();
//		}	
		
				
		// Es wird ueberprueft, ob die Indices der Deklarationen (sowohl bei "Aufgabe 4..." als auch bei "4..)
		// In der richtigen Reihenfolge stehen. Falls das nicht der Fall ist, steht wahrscheinlich 
		// im Dokument irgendwo am Zeilenfang im Text eine Aufgabenstellung, die keine Aufgabe deklariert.
		// Es wird nacheinander eine steigende Anzahl von Aufgabendeklarationen weggelassen und ueberprueft, 
		// ob die uebrigen Deklarationen Sinn machen (die Zahlen in der richtigen Reihenfolge sind).		
//		for (DeclarationSet set: foundDeclarationSets) {
//			set.removeDeclarationsNotInOrder();
//		}		
		
		
		// ------------------------------------------DEPRECATED----------------------------------------------//
		// -------------------------------- Altlasten von Sabine --------------------------------------------//
		// Infos ueber gestrichene Aufgaben etc.  werden jetzt initialisiert
		// dies ist notwendig, damit spaeter keine nullPointerException auftaucht, wenn spaeter ev. nach der Information gefragt wird
		
		// Info zu: Falls eine Aufgabe gestrichen wurde: welche?
		// Hier wird die Aufgabennummer verwendet, also default 0 bedeutet: keine Aufgabe gestrichen
		HashMapVerwaltung.erweitereHashmapInt(HashMapVerwaltung.gestricheneAufgabe, 0);
		
		
		
		//Info zu: Wurde im Verlauf des Programms eine Aufgabe gestrichen?
		HashMapVerwaltung.erweitereHashmapBoolean(HashMapVerwaltung.eineAufgabeZuviel, false);
		//Info zu: Wurde nur eine Aufgabe gefunden?
		HashMapVerwaltung.erweitereHashmapBoolean(HashMapVerwaltung.genauEineAufgabe, false);
		//Info zu: Wurden im Verlauf des Programms zwar Deklarationen gefunden, es war aber unmoeglich
		// diese in eine Reihenfolge zu bringen? 
		HashMapVerwaltung.erweitereHashmapBoolean(HashMapVerwaltung.keineOrdnungDeklarationen, false);
		
		
		// -------------------------------------------------------------------------------------------------//
		// -------------------------------------------------------------------------------------------------//
		
		
		if (foundDeclarationSets.isEmpty()){
			return null;
		}
		
		DeclarationSet setWithHighestScore = foundDeclarationSets.get(0);
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
			System.out.println("Score der Deklarationen, die mit dem Pattern "+ set.getPattern() + " gefunden wurden : " + score );
		}
		for (int i = 0; i < foundDeclarationSets.size(); i++) {
			if (foundDeclarationSets.get(i).getScore() > setWithHighestScore.getScore()) {
				setWithHighestScore = foundDeclarationSets.get(i);
			}
		}
		
		
		System.out.println("Score wurde erstellt");
		System.out.println(setWithHighestScore.getScore());

		// ------------------------------------------DEPRECATED----------------------------------------------//
		// -------------------------------- Altlasten von Sabine --------------------------------------------//
		int anzahl = setWithHighestScore.declarations.size();
//		Muster muster = setWithHighestScore.getPattern();
		if (anzahl == 1) {
			HashMapVerwaltung.erweitereHashmapBoolean(HashMapVerwaltung.genauEineAufgabe, true);
		}	
			HashMapVerwaltung.erweitereHashmapInt(HashMapVerwaltung.keyAnzahl, anzahl);
			System.out.println("Es wurden " + HashMapVerwaltung.getAufgabenzahlAusHashmap() + " Aufgabendeklarationen gefunden");
//			HashMapVerwaltung.erweitereHashmapMuster(HashMapVerwaltung.keyDeklaration, muster);
			for (int i = 0; i < anzahl; i++) {
				String key = "Aufgabe" + (i + 1);
				int zeile = setWithHighestScore.declarations.get(i).getLine();
				String schluesselWort = setWithHighestScore.declarations.get(i).getFirstWord();
				String aufgabenBezeichnung = setWithHighestScore.declarations.get(i).getSecondWord();
				HashMapVerwaltung.erweitereHashmap(key, schluesselWort,
						aufgabenBezeichnung, zeile);
			}
		// -------------------------------------------------------------------------------------------------//
		// -------------------------------------------------------------------------------------------------//
	
		return setWithHighestScore;
		
	}

	
	/**
	 * Filtert das �bergebene String-Array, sodass nur noch Zahlen in dem Array enthalten bleiben.
	 * Entfernt alle nicht zu Integer parsebaren Zeichen aus dem String-Array
	 * 
	 * 
	 * @param string String[]
	 * @return int[], welches alles zu Integers parsebaren Zeichen aus dem String[] enth�lt 
	 */
	public static int[] extrahiereZahlen(String[] string) {
		
		String wort = "";
		int neu[] = new int[string.length];
		String test = "";
		char c = ' ';
		
		// filtert den String, so dass nur die
		// Zahlen uebrigbleiben
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
			// falls jetzt eine Zahl dabei rauskommt:
			if (isInt(neuZahl)) {
				neu[i] = Integer.parseInt(neuZahl);
				System.out.println("Alt: "+ wort + "; Neu: " + neu[i]);
			}
		}
		return neu;
	}
	
	/**
	 * Ueberprueft, ob das �bergebene int-array Zahlen einer fortlaufenden, unmittelbar aufsteigenden Reihenfolge (z.B. 4, 5, 6, 7 )
	 * enth�lt
	 * 
	 * @param zahlen int-Array
	 * @return true, falls eine Fortlaufende Reihenfolge exisitert
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
	 * Ueberprueft, ob der �bergeben String g�ltig zu einem Integer geparsed werden kann.
	 * 
	 * @param i Zu ueberpruefender String
	 * @return true, falls er geparsed werden kann, false, falls nicht
	 */
	private static boolean isInt(String i) {
		
		// ueberprueft, ob der Wert im String ein Integerwert ist
		try {
			Integer.parseInt(i);
			return true;
		}
		catch(NumberFormatException FehlerZahlenformat) {
			return false;
		}
	}
	
//	/**
//	 * Bestimmt die erste Im String enthaltene Zahl (auch mehrere Ziffern) und gibt diese aus.
//	 * Gibt null aus, falls der String keine Zahl enth�lt.
//	 * 
//	 * @param string zu ueberpruefender String
//	 * @return die erste gefundene Zahl
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
