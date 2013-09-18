package Verwaltung;

import java.util.*;
import RtfTestung.*;

/**
 * 
 * @author sabine
 * 
 * Verwaltung der HashMaps
 * 
 *
 *	Bisher 6 Varianten: 
 *	HashMapIdentifier: Key String --> speichert Identifier
 *	HashMapInt: Key String --> speichert Integer
 *	HashMapString: Key String --> speichert String
 *  HashMapBoolean: Key String --> speichert Boolean
 *  HashMapRtf: Key String --> speichert IdentifierRtf
 *  HashMapStringArray: Key String --> Speichert "Dokument"
 */



public class HashMapVerwaltung {
	
	// hashMapIdentifier verwaltet die Identifier-Objekte
	public static HashMap<String, IdentifierText> hashMapIdentifier = new HashMap<String, IdentifierText>();
	
	// hashMapInt verwaltet die Integer-Objekte wie keyAnzahl, etc.
	public static HashMap<String, Integer> hashMapInt = new HashMap<String, Integer>();
	
	// hashMapString verwaltet die String-Objekte wie docTitle, etc.
	public static HashMap<String, String> hashMapString = new HashMap<String, String>();
	
	// hashMapBoolean verwaltet Boolean-Objekte, um Infos innerhalb des Programmablaufs zu speichern
	public static HashMap<String, Boolean> hashMapBoolean = new HashMap<String, Boolean>();
	
	// hashMapRtf verwaltet die IdentifierRtf-Objekte
	public static HashMap<String, IdentifierRtf> hashMapRtf = new HashMap<String, IdentifierRtf>();
	
	// hashMapStringArray verwaltet die String[]-Objekte (Textfile, Rtf-File, ...)
	public static HashMap<String, String[]> hashMapStringArray = new HashMap<String, String[]>();
	

	
	// Die Keys sind alle fest und hier erlaeutert:
	// Fuer die einzelnen Aufgaben lauten die Keys jeweils "Aufgabe1", "Aufgabe2", etc.
	// je nach Anzahl der Aufgaben. Diese Keys werden in for-Schleifen generiert.
	// Alles anderen Keys sind unveraenderbar:
	
	
	//Keys fuer Strings und rtf/tex Identifier:
	public static final String docAnf = "ErstesWortImDokument";
	public static final String docEnde = "LetztesWortImDokument";	
	public static final String docTitle = "TiteldesDokuments";	
	
	
	//Keys fuer String[]:
	public static final String textFile = "TextFile";
	public static final String rtfFile = "RtfFile";	
	
	
	//Keys fuer Integers:
	public static final String keyAnzahl = "Anzahl"; // Anzahl der gefundenen Aufgaben
/////NEU:	
	public static final String keyAnzahlRtf = "AnzahlRtf"; // Anzahl der im rtf-Dokument erkannten Aufgaben
	
	public static final String keyDeklaration = "Deklaration"; // Hinweis auf die gefundene Daklarationsart
	public static final String gestricheneAufgabe = "AufgabenNummer"; // Nummer einer
	// eventuell gestrichenen Aufgabe, falls keine Aufgabe gestrichen wurde: 0;
	
	
	//Keys fuer booleans: Hier werden booleans aus einzelnen Methoden abgelegt
	// auf die sonst nur schwer zugegriffen werden koennte:
	
	public static final String eineAufgabeZuviel = "AufgabeZuviel";
	// eineAufgabeZuviel speichert, ob die Aufgabendeklarationen erst nach Entfernen einer
	// Deklaration in der richtigen Reihenfolge waren
	// dies ist wichtig fuer die Rtf-Bearbeitung
	// default: false
	
	public static final String keineOrdnungDeklarationen = "OrdnungDeklarationen";
	// keineOrdnungDeklarationen wird true, wenn zwar Deklarationen gefunden wurden, sich diese jedoch in keine
	// sinnvolle Reihenfolge bringen lassen
	// default: false
	
	public static final String genauEineAufgabe = "GenauEineAufgabe";
	// hier wird abgefragt, ob bei der Suche nach Aufgabendeklarationen zwischenzeitlich
	// eine einzelne Deklaratione gefunden wurde
	
	
	
	
	
	public static void initialisiereHashMapVerwaltung() {
		hashMapIdentifier.clear();
		hashMapInt.clear();
		hashMapString.clear();
		hashMapBoolean.clear();
		hashMapRtf.clear();
		hashMapStringArray.clear();
	}

	
	// 1. HashMap hashMapIdentifier fuer Konstruktor IdentifierText, 
	//   zustaendig fuer die im Text gefundenen Aufgabendeklarationen
	
	
	
	// fuege neuen Identifier in HashMap ein, Aufgabendeklarationen ohne Schluesselwort
	public static void erweitereHashmap(String key, String aufgabenBezeichnung, int zeile) {
		IdentifierText aktuellerIdentifier = new IdentifierText(aufgabenBezeichnung, zeile);
		hashMapIdentifier.put(key, aktuellerIdentifier);
	}
		
	// fuege neuen Identifier in HashMap ein, Aufgabendeklarationen mit Schluesselwort
	public static void erweitereHashmap(String key, String schluesselWort, String aufgabenBezeichnung, int zeile) {
		IdentifierText aktuellerIdentifier = new IdentifierText(schluesselWort, aufgabenBezeichnung, zeile);
		hashMapIdentifier.put(key, aktuellerIdentifier);
	}
	
	// liest Schluesselwort aus der Hashmap aus
	public static String getSchluesselWortAusHashmap(String key) {
		
		IdentifierText identifier = hashMapIdentifier.get(key);
		String ergebnis = identifier.getSchluesselWort();
		return ergebnis;		
	}
	// liest Aufgabenbezeichnung aus der Hashmap aus
	public static String getAufgabenBezeichnungAusHashmap(String key) {
		IdentifierText identifier = hashMapIdentifier.get(key);
		String ergebnis = identifier.getAufgabenBezeichnung();
		return ergebnis;		
	}
	
	// liest die zum Identifier gehoerende Zeile aus der Hashmap aus	
	public static int getZeileAusHashmap(String key) {
		IdentifierText identifier = hashMapIdentifier.get(key);
		int ergebnis = identifier.getZeileText();	
		return ergebnis;		
	}
	
		
	// 2. HashMap hashMapInt fuer Integer-Objekte:
	
	// fuege Zahl in HashMapInt ein
	// z.B. anzahl Aufgaben, Code fuer Aufgabendeklaration, etc, siehe oben
	public static void erweitereHashmapInt(String key, int zahl) {
		hashMapInt.put(key, zahl);
	}
	
	// liest Gesamtzahl der gefundenen Aufgaben aus
	// key: Aufgabenzahl
	public static int getAufgabenzahlAusHashmap() {
		int ergebnis = hashMapInt.get(keyAnzahl);
		return ergebnis;		
	}
	

///NEU	
	// liest Gesamtzahl der gefundenen Aufgaben aus
	// key: Aufgabenzahl
	public static int getAufgabenzahlRtfAusHashmap() {
		int ergebnis = hashMapInt.get(keyAnzahlRtf);
		return ergebnis;		
	}
	
	
	
	
	// liest Aufgabendeklaration der gefundenen Aufgaben aus
	// key: Aufgabendeklaration
//	public static int getDeklarationAusHashmap() {
//		int ergebnis = hashMapInt.get(keyDeklaration);
//		return ergebnis;		
//	}
	
	
	// liest den Index der gestrichenen Aufgaben aus
	// key: gestricheneAufgabeIndex
	public static int getGestricheneAufgabeAusHashmap() {
		int ergebnis = hashMapInt.get(gestricheneAufgabe);
		return ergebnis;		
	}
	
	
	// 3. HashMap hashMapString fuer String-Objekte:
	
	// fuege String in HashMap ein
	// z.B. Name des Originalfiles, erstes und letztes Wort im Textdokument
	// (hierfuer werden keine Zeilenangaben o.ae. benoetigt, daher einfach String)
	public static void erweitereHashmapString(String key, String string) {
		hashMapString.put(key, string);
	}
	
	// liest zugehoerigen gehoerenden Filenamen aus der HashmapString aus
	// key: DocTitel
	public static String getDocTitleAusHashmap() {
		String ergebnis = hashMapString.get(docTitle);
		return ergebnis;		
	}
	
	// liest erstes Wort aus der HashmapString aus
	// key: DocAnf
	public static String getDocAnfAusHashmap() {
		String ergebnis = hashMapString.get(docAnf);
		return ergebnis;		
	}
	
	// liest letztes Wort aus der HashmapString aus
	// key: DocEnde
	public static String getDocEndeAusHashmap() {
		String ergebnis = hashMapString.get(docEnde);
		return ergebnis;		
	}
	
	
	
	
	
	
	
	// 4. HashMap hashMapBoolean fuer Boolean-Objekte:
	
	// fuege Boolean in HashMap ein
	// dies ist hauptsaechlich wichtig fuer klassenuebergreifende Abfragen 
	
	public static void erweitereHashmapBoolean(String key, boolean bool) {
		hashMapBoolean.put(key, bool);
	}
	
	// liest aus, ob eine Aufgabe gestrichen wurde
	public static boolean getAufgabeZuviel() {
		System.out.println("getAufgabeZuviel aufgerufen");
		boolean ergebnis = (boolean) hashMapBoolean.get(eineAufgabeZuviel);
		System.out.println(ergebnis);
		return ergebnis;		
	}
	
	// liest aus, ob genau eine Aufgabendeklaration gefunden wurde
	public static boolean getGenauEineAufgabe() {
		System.out.println("getGenauEineAufgabe aufgerufen");
		boolean ergebnis = (boolean) hashMapBoolean.get(genauEineAufgabe);
		System.out.println(ergebnis);
		return ergebnis;		
	}
	
	//liest aus, ob Deklarationen gefunden wurden, die sich in keine Reihenfolge bringen liessen
	public static boolean getKeineOrdnungDeklarationen() {
		System.out.println("getKeineOrdnungDeklarationen aufgerufen");
		boolean ergebnis = (boolean) hashMapBoolean.get(keineOrdnungDeklarationen);
		System.out.println(ergebnis);
		return ergebnis;		
	}
	

	
	
	// 5. HashMap hashMapRtf fuer Konstruktor IdentifierRtf:
	
	// fuege neuen Identifier in HashMapRtf ein, Erstes Wort, letztes Wort, alle Aufgaben, etc.
	// jeweils mit Zeilen- und Positionsangaben, alles aus Rtf
	public static void erweitereHashmapRtf(String key, String wort, int zeile, int position) {
		hashMapRtf.put(key, new IdentifierRtf(wort, zeile, position));
	}
	
	// wie oben, zusaetzliche Info, ob Aufgabe tatsaechlich gefunden wurde oder nur "geraten"(==false)
	// 
	public static void erweitereHashmapRtf(String key, String wort, int zeile, int position, boolean gefunden) {
		hashMapRtf.put(key, new IdentifierRtf(wort, zeile, position, gefunden));
	}
	
	
	// liest das gesuchte Wort aus der HashmapRtf aus
	public static String getWortAusHashmapRtf(String key) {
		IdentifierRtf identifier = hashMapRtf.get(key);
		String ergebnis = identifier.getWort();
		return ergebnis;		
	}
	
	// liest die zum IdentifierRtf gehoerenden Positionen aus der HashmapRtf aus	
	public static int getPositionAusHashmapRtf(String key) {
		IdentifierRtf identifier = hashMapRtf.get(key);
		int ergebnis = identifier.getPosition();
		return ergebnis;		
	}
	
	// liest die zum IdentifierRtf gehoerende BooleanVariable gefunden aus der HashmapRtf aus	
	public static boolean getGefundenAusHashmapRtf(String key) {
		IdentifierRtf identifier = hashMapRtf.get(key);
		boolean ergebnis = identifier.getGefunden();
		return ergebnis;		
	}

	// liest die zum IdentifierRtf gehoerenden Zeilen aus der HashmapRtf aus	
	public static int getZeileAusHashmapRtf(String key) {
		IdentifierRtf identifier = hashMapRtf.get(key);
		int ergebnis = identifier.getZeile();
		return ergebnis;		
	}
	
	
	

	
	// 6. HashMap hashMapStringArray fuer String[]:

	// fuege String[] in HashMapStringArray ein
	// der key hat die Form Aufgabe1_Rtf, etc
	public static void erweitereHashmapStringArrayAufgaben(String key, String[] file) {
		hashMapStringArray.put(key, file);
	}

	// fuege rtf-file in HashMapStringArray ein
	public static void erweitereHashmapStringArrayRtfFile(String[] file) {
		hashMapStringArray.put(rtfFile, file);
	}
	
	// fuege Text-file in HashMapStringArray ein
	public static void erweitereHashmapStringArrayTextFile(String[] file) {
		hashMapStringArray.put(textFile, file);
	}
	
	
	
	
	// liest TextFile aus HashMapStringArray aus
	public static String[] getTextFile() {
		String[] ergebnis = hashMapStringArray.get(textFile);
		System.out.println(ergebnis);
		return ergebnis;		
	}
	
	// liest RtfFile aus HashMapStringArray aus
	public static String[] getRtfFile() {
		String[] ergebnis = hashMapStringArray.get(rtfFile);
		System.out.println(ergebnis);
		return ergebnis;		
	}
	
	// liest geschnittene Einzelaufgaben im Rtf-Format aus HashMapStringArray aus
	// (die Aufgaben im Textformat werden direkt im Exercise-Sheet gespeichert):
	// keys fuer Aufgaben im Rtf-Format: ein String: Aufgabe1_Rtf, Aufgabe2_Rtf, etc.
	
	public static String[] getEinzelAufgabeRtf(String aufgabe) {
		String[] ergebnis = hashMapStringArray.get(aufgabe);
		System.out.println(ergebnis);
		return ergebnis;		
	}
	
	
}
