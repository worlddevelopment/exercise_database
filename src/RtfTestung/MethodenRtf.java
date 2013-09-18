package RtfTestung;


public class MethodenRtf {
	
	/**
	 * Gibt die Anzahl des gesuchten Zeichen im String aus.
	 * @param zeile String, in dem gesucht wird
	 * @param zeichen gesuchtes Zeichen
	 * @return Anzahl der Auftritte
	 */
	public static int sucheZeichenZeile(String zeile, char zeichen) {
		
		// zaehlt die Anzahl der gesuchten Zeichen in der angegebenen Zeile
		// wird bisher nur fuer die geschweiften Klammern verwendet, ev. noch mehr
		
		int zaehler = 0;
		char c = zeichen;
		
		for (int i=0; i <= zeile.length()-1; i++ ) {
			char c1 = zeile.charAt(i);
			
			if (c1 == c) {
				//System.out.println("Fund in Zeile: " + i);
				zaehler++;
				//System.out.println(zaehler + " Zeichen gefunden");
			}
		}
		return zaehler;		
	}
	

 /**
  * Gibt die Anzahl des gesuchten Zeichens im String[] aus.
  * @param filename String[] in dem gesucht wird.
  * @param zeichen gesuchtes Zeichen
  * @return Anzahl der Auftritte
  */
public static int sucheZeichenFile(String[] filename, char zeichen) {
		
		// zaehlt das Austreten des Zeichens im gesamten Text
		// diese geschieht ueber die Methode sucheZeichenZeile(...) 
		
		int summe = 0;
		
		for (int i = 0; i < filename.length-1; i++) {
			//System.out.println(filename[i]);
			int zaehler = sucheZeichenZeile(filename[i], zeichen);
			summe = summe + zaehler;
			//System.out.println(zeile);
		}
		return summe;
	}


	
	// Die exakte Position des Beginns des Wortes in der Zeile wird gesucht
	/**
	 * Gibt den index des Wrotes in der Zeile aus
	 * @param zeile String, in dem gesucht wird
	 * @param gesucht gesuchtes Wort
	 * @return index
	 */
	public static int suchePosition(String zeile, String gesucht) {
		String str = zeile;
		int index = str.indexOf(gesucht);
		return index;
	}
	
	
	/**
	 * Prueft die Richtigkeit der Klammern
	 * 
	 * @param filename String[] in dem es die Klammern ueberpreuft werden sollen
	 * @return true, falls alle Klammern passen
	 */
	public static boolean klammernNochKorrekt(String[] filename) {
		
		// Gueltigkeitstest auf Klammern im uebergebenen File
		// Vorsicht, auch das Original kann verschieden viele Klammern haben
		
		char zeichen1 = '{';
		int klammerAuf = MethodenRtf.sucheZeichenFile(filename, zeichen1);
		char zeichen2 = '}';
		int klammerZu = MethodenRtf.sucheZeichenFile(filename, zeichen2);
		System.out.println("Insgesamt gibt es das Zeichen { " + klammerAuf + " Mal.");
		System.out.println("Insgesamt gibt es das Zeichen } " + klammerZu + " Mal.");
		int differenz = klammerAuf - klammerZu;
		if (differenz == 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Zaehlt die Klammern im von anfang und Ende definierten Abschnitt des Dokuments
	 * @param filename
	 * @param anfang
	 * @param ende
	 * @return int Anzahl der Klammern
	 */
	public static int[] klammerZahlimAbschnitt(String[] filename, int anfang, int ende) {
		
		// zaehlt Klammern in Abschnitt des Dokuments
		int[] klammerzahl = new int [2];
		int gesamtoffen = 0;
		int gesamtzu = 0;
		char zeichen1 = '{';
		char zeichen2 = '}';
		int zaehler = 0; //zaehlt die Zeilen, nur fuer print-Ausgabe
		for (int i = anfang; i <= ende; i++) {
			int klammerAuf = MethodenRtf.sucheZeichenZeile(filename[i], zeichen1);
			gesamtoffen = gesamtoffen + klammerAuf;
			int klammerZu = MethodenRtf.sucheZeichenZeile(filename[i], zeichen2);
			gesamtzu = gesamtzu + klammerZu;
			zaehler++;
		}
		System.out.println("Insgesamt gibt es das Zeichen { " + gesamtoffen + " Mal.");
		System.out.println("Insgesamt gibt es das Zeichen } " + gesamtzu + " Mal.");
		klammerzahl[0]=gesamtoffen;
		klammerzahl[1]=gesamtzu;
		return klammerzahl;
	}
	
	/**
	 * Gibt die Anzahl der Klammern im uebergebenen String zurueck.
	 * 
	 * @param zeile
	 * @return Anzahl der Klammern
	 */
	public static int[] klammerZahlinZeile (String zeile) {
		
		// zaehlt Klammern in ganzer Zeile
		int[] klammerzahl = new int [2];
		char zeichen1 = '{';
		char zeichen2 = '}';
		int gesamtoffen = MethodenRtf.sucheZeichenZeile(zeile, zeichen1);
		int gesamtzu = MethodenRtf.sucheZeichenZeile(zeile, zeichen2);
			
		System.out.println("Insgesamt gibt es das Zeichen { " + gesamtoffen + " Mal.");
		System.out.println("Insgesamt gibt es das Zeichen } " + gesamtzu + " Mal.");
		klammerzahl[0]=gesamtoffen;
		klammerzahl[1]=gesamtzu;
		return klammerzahl;
	}
	
	
	
	
	/**
	 * Ueberprueft, ob der uebergebene String ein Integer ist
	 * @param i String
	 * @return true, falls ja, false, falls nein
	 */
	public static boolean isInt(String i) {
		
		// ueberprueft ob der Wert im String ein Integerwert ist
		try {
			Integer.parseInt(i);
			return true;
		}
		catch(NumberFormatException nfe) {
			return false;
		}
	}
	
	
	
/*	public static int sucheZeile(String[] filename, String gesucht) {
		
		// sucht erstes Auftreten von gesucht
		
		
		for (int i = 0; i < filename.length; i++ ) {
			if (filename[i].contains(gesucht)) {
				System.out.println("Suche Zeile erfolgreich: " + filename[i]);
				return i;	
			}
		}
		
		// falls gesucht nicht existiert, wird 0 zurückgegeben
		// in der ersten Zeile steht nie das gesuchte Wort, da diese rtf-intern 
		// verwendet wird, daher ist dies so ok
		return 0;
	}
*/	
	
	
/*	public static boolean mehrAlseinmal(String[] filename, String gesucht) {
		
		int[] array = sucheZeileAlle(filename, gesucht);
		if (array.length > 1) {
			return true;
		}
		return false;
	}
*/	
	
	/**
	 * Sucht alle Vorkommnisse von gesucht im String[] filename.
	 * @param filename
	 * @param gesucht
	 * @return int[], welches an der Stelle i speichert, das wievielte mal gesucht Auftritt,
	 * wobei i die Zeile ist, in der gesucht auftritt.
	 */
	public static int[] sucheZeileAlle(String[] filename, String gesucht) {
	
		// sucht alle Zeilen, in denen gesucht mindestens einmal auftritt
		System.out.println("sucheZeileAlle aufgerufen, gesucht wird nach: " + gesucht);
		int zaehler = 0;
		int[] ergebnis1 = new int[filename.length];
		for (int i = 0; i < filename.length; i++ ) {
			if (filename[i] != null && filename[i].contains(gesucht)) {
				System.out.println(zaehler + ": " + filename[i]);
			
				//hier noch ev. Fall einbauen, dass ein Wort in einer Zeile des Rtfs mehrfach auftaucht	
				
				ergebnis1[zaehler] = i; 
				zaehler++;	
			}
		}
		
		
		
		System.out.println(gesucht +" wurde insgesamt "+ zaehler + " mal gefunden." );
		// der return-Array mit korrekter Laenge wird erzeugt
		int[] ergebnis = new int[zaehler];
		for (int i = 0; i < zaehler; i++) {
			ergebnis[i] = ergebnis1[i];
		}
		System.out.println("Der return-Array hat die Laenge: " + ergebnis.length);
	
		// falls gesucht nicht existiert, wird ein Array der Länge 0 zurückgegeben
	
		return ergebnis;
	}
	
	
	/**
	 * Zaehlt die Anzahl der vorkommnisse von wort im String[] textdoc
	 * @param textdoc String[]
	 * @param wort String
	 * @return inz Anzahl der Vorkommnisse
	 */
	public static int wortAnzahlimText(String[] textdoc, String wort) {
		
		int anzahl = 0;
		String zeile = "";
		
		for (int i = 0; i < textdoc.length; i++) {
			zeile = textdoc[i];

        	boolean matching = false;
        	if (0 != wort.length()) {
        		for (int index = zeile.indexOf(wort, 0); index != -1; index = zeile
                    .indexOf(wort, matching ? index + 1 : index + wort.length())) {
        			anzahl++;
        		}
        	}
		}
		return anzahl;
	}
	
	
	
}	
	
