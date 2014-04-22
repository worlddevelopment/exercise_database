package Verwaltung;

/**
 * 
 * @author sabine
 * 
 * Hier werden die Infos ueber die im Text gefundenen Aufgabendeklarationen
 * unter Verwendung der HashMap "hashMapIdentifier" gespeichert
 * 
 ** 
 * Ausgelesen werden die informationen auch indirekt ueber die Methoden der HasMapVerwaltung
 * siehe dort
 * 
 *
 */


public class IdentifierText {

	// Diese Klasse wird NUR fuer das Speichern und Abfragen 
	// der Aufgabendeklarationen des Textdokuments verwendet,
	// (fuer das erste und letzte Wort im Text ist keine Zeilenangabe noetig,
	// da diese Informationen nicht fuer das Schneiden des Textdokuments benoetigt werden)
	
	
	// Um die Speicherung sinnvoll zu gestalten:
	
	// falls Deklaration mit Schluesselwort: 
	// erstes Wort: schluesselWort
	// zweites Wort: aufgabenBezeichnung
	
	// falls Deklaration ohne Schluesselwort: 
	// erstes Wort: "" 
	// zweites Wort: aufgabenBezeichnung
	
	private String schluesselWort = "";
	private String aufgabenBezeichnung = "";
	
	// die Woerter wurden im .txt-Dokument gefunden in der Zeile:
	private int zeileText = 0; 
	
	
	
	// Konstruktoren:
	
	//leerer Konstruktor, wird eigentlich nicht benoetigt, kann auch weg
	IdentifierText() {
	}
	
	// String, int : Aufgabendeklaration ohne Schluesselwort: aufgabenBezeichnung, Zeile Textdokument
	IdentifierText(String aufgabenBezeichnung, int zeile) {
		this.schluesselWort = "";
		this.aufgabenBezeichnung = aufgabenBezeichnung;
		this.zeileText = zeile;
	}
	
	// String, String, int : Aufgabendeklaration mit Schluesselwort: schluesselWort, aufgabenBezeichnung, Zeile Textdokument
	IdentifierText(String schluesselWort, String aufgabenBezeichnung, int zeile) {
		this.schluesselWort = schluesselWort;
		this.aufgabenBezeichnung = aufgabenBezeichnung;
		this.zeileText = zeile;
	}

	
	// passende Getter und Setter:
	
	public String getSchluesselWort() {
		
		System.out.println("getSchluesselWort() wurde aufgerufen, Schluesselwort:  " + this.schluesselWort);
		return this.schluesselWort;
	}
	
	public void setSchluesselWort(String schluesselWort) {
		this.schluesselWort = schluesselWort;
	}
	
	public String getAufgabenBezeichnung() {
		return this.aufgabenBezeichnung;
	}
	
	public void setAufgabenBezeichnung(String aufgabenBezeichnung) {
		this.aufgabenBezeichnung = aufgabenBezeichnung;
	}
	
	public int getZeileText() {
		return this.zeileText;
	}
	
	public void setZeileText(int zeile) {
		this.zeileText = zeile;
	}
	
}
