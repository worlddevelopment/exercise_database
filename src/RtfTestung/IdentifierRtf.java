package RtfTestung;



// Konstruktor fuer die gefundenen Textstellen im .rtf Dokument
// wird dann indirekt ueber HashMapRtf erstellt und ausgelesen

public class IdentifierRtf {

	// gefundene Textstelle, gesuchtes Wort:
	private String wort = "";
		
	// Wort befindet sich im rtf in Zeile:
	private int zeile = 0;
	
	// Wort befindet sich an der Position:
	private int position = 0;
	
	// Die Textstelle wurde gefunden
	private boolean gefunden = true;
	
	
	
	// Konstruktoren:
	
	//leerer Konstruktor
	public IdentifierRtf() {
	}

	/**
	 * Konstruktor
	 * 
	 * @param wort
	 * @param zeile
	 * @param position
	 */
	public IdentifierRtf(String wort, int zeile, int position) {
		this.wort = wort;
		this.zeile = zeile;
		this.position = position;
		this.gefunden = true;
	}
	
	
	// falls man eine explizite Aussage dazu machen moechte, ob die Aufgabe im Rtf erkannt wurde
	// eigentlich nur interessant, falls gefunden == false; 
	
	/**
	 * Konstruktor
	 * 
	 * @param wort
	 * @param zeile
	 * @param position
	 * @param gefunden
	 */
	public IdentifierRtf(String wort, int zeile, int position, boolean gefunden) {
		this.wort = wort;
		this.zeile = zeile;
		this.position = position;
		this.gefunden = gefunden;
	}
	
	// Getter und Setter:
	
	/**
	 * @return Wort
	 */
	public String getWort() {
		return this.wort;
	}
	
	/**
	 * @param wort
	 */
	public void setWort(String wort) {
		this.wort = wort;
	}
	

	/**
	 * 
	 * @return int Zeile
	 */
	public int getZeile() {
		return this.zeile;
	}
	
	/**
	 * 
	 * @param set zeile
	 */
	// setzt Zeilenangabe zu Rtf-Dokument
	public void setZeile(int zeile) {
		this.zeile = zeile;
	}

	/**
	 * 
	 * @return position
	 */
	public int getPosition() {
		return this.position;
	}
	
	/**
	 * set position
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * 
	 * @return gefunden
	 */
	public boolean getGefunden() {
		return this.gefunden;
	}
	
	/**
	 * set gefunden
	 * @param gefunden
	 */
	public void setGefunden(boolean gefunden) {
		this.gefunden = gefunden;
	}
}
