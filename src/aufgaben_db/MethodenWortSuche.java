package aufgaben_db;

import Verwaltung.HashMapVerwaltung;


/**
 * 
 * @author sabine
 * 
 * 
 *  Hier werden v.a. das erste und das letzte Wort im Dokument gesucht
 *  und die dazu passenden Methoden befinden sich hier
 *
 */


public class MethodenWortSuche {
	

	
	/**
	 * Die Methode ersetzt problematische Java-Sonderzeichen und Umlaute
	 * durch jeweils ein Leerzeichen (und trennt dadurch auch Woerter!)
	 * 
	 * @param zeile der String, in denen die Zeichen ersetzt werden sollen.
	 * @return String ohne die b�sen Zeichen
	 */
	private static String ersetzeSzUndUmLeer(String zeile) {
		
		String ergebnis = zeile.replaceAll("Ö", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("ö", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("Ü", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("ü", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("Ä", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("ä", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("�", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("¨"," ");
		System.out.println(ergebnis);
		
		return ergebnis;
	}
	
 
	/**
	 * Sucht das erste Wort des uebergebenen Dokuments
	 * fuer die rtf und tex Weiterverarbeitung
	 * Sonderzeichen und Umlaute fallen dabei weg
	 * 
	 * @param filename String[] des zu untersuchenden Textes (Zeilenweise)
	 * @return String des ersten Wortes
	 */
	public static String sucheErstesWortImDoc(String[] filename) {
		
		System.out.println("Suche erstes Wort im Dokument wurde aufgerufen");
		String ergebnis = "";
		boolean erfolgreich = false;
		
		for (int i = 0; i < filename.length; i++) {
			System.out.println("Zeile: "+i);
			if (filename[i].length() > 3 ) { // Woerter < 4 Buchstaben werden sowieso nicht gewertet
				// alle SZ und Umlaute werden durch Leerezeichen ersetzt
				String zeile = ersetzeSzUndUmLeer(filename[i]);
				System.out.println(zeile);
				// jetzt wird die Zeile in 'Woerter'(=Wortbruchstuecke) aufgeteilt
				String[] array = teileZeileInWoerter(zeile);
				
				// das erste Wortfragment, welches aus mind. 4 zusammenhaengenden
				// Zeichen besteht, wird ausgewaehlt:
				
				// 4 ist jetzt reine Abschaetzung, hier laesst sich noch was veraendern
				// durch Betrachtung der ersten Worte entstanden
				for (int j = 0; j < array.length; j++) {
					System.out.println("Laenge des Wortes:"+ array[j].length());
					if (array[j].length() >=4) {
						System.out.println("Eingetragen!");
						ergebnis = array[j];
						System.out.println(ergebnis);
						erfolgreich = true;
						if (erfolgreich) {
							return ergebnis;
							}
					}
				}
				
				
				// falls in der Zeile kein passendes Wort enthalten war
				// wird in der nächsten Zeile weitergesucht
			}
		}
		// falls sich im ganzen Dokument kein Wort fand, wird der leere String zurueckgegeben
		return ergebnis;
	}
	
 
	
	/**
	 * Sucht das letzte Wort des uebergebenen Dokuments
	 * fuer die rtf und tex Weiterverarbeitung
	 * Sonderzeichen und Umlaute fallen dabei weg
	 * 
	 * @param filename String[] des zu untersuchenden Textes (Zeilenweise)
	 * @return String des letzten Wortes
	 */
	public static String sucheLetztesWortImDoc(String[] filename) {
		
		String ergebnis = "";
		
		for (int i = filename.length-1; i >= 0; i--) {
			if (filename[i].length() > 1 ) { // Woerter < 2 Buchstaben werden sowieso nicht gewertet
				// alle SZ und Umlaute werden durch Leerezeichen ersetzt
				String zeile = ersetzeSzUndUmLeer(filename[i]);
				// jetzt wird die Zeile in 'Woerter'(=Wortbruchstuecke) aufgeteilt
				String[] array = teileZeileInWoerter(zeile);
				
				// das letzte Wortfragment, welches aus mind. 2 zusammenhaengenden
				// Zeichen besteht, wird ausgewaehlt:
				for (int j = array.length - 1; j >= 0; j--) {
					if (array[j].length() >=2) {
						ergebnis = array[j];
						return ergebnis;
					}
				// falls in der Zeile kein passendes Wort enthalten war
				// wird in der nächsten Zeile weitergesucht
				}
			}
		}
		// falls sich im ganzen Dokument kein Wort fand, wird der leere String zurueckgegeben
		return ergebnis;
	}
	
	
	
	// teilt eine Zeile in einzelne Woerter auf
	
	/**
	 * Teilt eine Zeile in ihre einzelnen, durch Leerzeichen getrennten Woerter auf
	 * 
	 * @param zeile String der aufzuteilenden Zeile
	 * @return String[] der aufgeteiteilten Zeile (Ein Wort pro Array-Eintrag)
	 */
	public static String[] teileZeileInWoerter(String zeile) {
		//System.out.println("teile Zeile in Woerter wurde aufgerufen");
		
		String[] result = zeile.split("\\s");

		//System.out.println("teile Zeile in Woerter wurde beendet");

		return result;
	}
	
	
	
	//2 Methoden zum Kuerzen von Arrays auf eine vorgegebene Laenge:
	
	
	// kuerzt Arrays auf die richtige Laenge
	
	/**
	 * Kuerzt die Laenge des uebergebenen String[] auf den Wert zaehler
	 * 
	 * @param array zu kuerzendes String[]
	 * @param zaehler Laenge, auf die das String[] gekuerzt werden soll
	 * @return gekuerztes String[]
	 */
	public static String[] kuerzeArray(String[] array, int zaehler) {
		//System.out.println("kuerzeArray String[] wurde aufgerufen");
		String[] ergebnis  = new String[zaehler];
		for (int i = 0; i < zaehler; i++) {
			ergebnis[i] = array[i]; 
		}
		//System.out.println("kuerzeArray String[] wurde beendet");
		return ergebnis;
	}
	

	
	// kuerzt zweidimensionale arrays auf die richtige Laenge
	
	/**
	 * Kuerzt die Laenge des uebergebenen String[][] auf den Wert zaehler
	 * 
	 * @param array zu kuerzendes String[][]
	 * @param zaehler Laenge, auf die das String[][] gekuerzt werden soll
	 * @return gekuerztes String[]
	 */
	public static String[][] kuerzeArray(String[][] array, int zaehler) {
		// die 2 stimmt ev. noch nicht.....:
		String[][] ergebnis  = new String[zaehler][2];
		for (int i = 0; i < zaehler; i++) {
			//ergebnis[i][0] = array[i][0]; 
			//ergebnis[i][1] = array[i][1];
			// so scheint es auch zu gehen:
			ergebnis[i] = array[i];
		}
		return ergebnis;
	}
	
	/**
	 * Schreibt das erste und letzte Wort des uebergebenen String[] in die Hashmap
	 * 
	 * @param textdoc zeilenweise gespiecherter Text als String[]
	 */
	public static void schreibeErstesLetztesWortAusTextInHashMap(String[] textdoc) {
		
		String erstesWort = MethodenWortSuche.sucheErstesWortImDoc(textdoc);
		System.out.println("Das erste Wort des Dokuments lautet: " + erstesWort);
		
		HashMapVerwaltung.erweitereHashmapString(HashMapVerwaltung.docAnf, erstesWort);
		
		
		String letztesWort = MethodenWortSuche.sucheLetztesWortImDoc(textdoc);
		System.out.println("Das letzte Wort des Dokuments lautet: " + letztesWort);
		
		HashMapVerwaltung.erweitereHashmapString(HashMapVerwaltung.docEnde, letztesWort);
	}
	

}
