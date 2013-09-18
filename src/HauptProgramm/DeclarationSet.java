/**
 * 
 */
package HauptProgramm;

import java.util.ArrayList;

import aufgaben_db.Muster;

import Verwaltung.HashMapVerwaltung;

/**
 * Klasse, die ein Set an Aufgabendeklarationen, die ueber den selben regulaeren Ausdruck gefunden wurden, repraesentiert
 * 
 * @author Schweiner
 *
 */
public class DeclarationSet {
	
	// Welches Pattern?
	private Muster pattern;	
	//Score des Deklarationssatzes
	private double score = 0;
	// Wie oft die Deklaration gefunden
	private int numberOfHits = 0;
	
	// Zu Faul die Verwaltungsmethoden nochmal zu implementieren, daher public
	public ArrayList<Declaration> declarations = new ArrayList<Declaration>();
	
	// Mit welchem Muster diese Deklarationen gefunden wurden.
	public DeclarationSet(Muster pattern){
		this.setPattern(pattern);
	}

	/**
	 * add One Hit to this DeclarationSet
	 */
	public void addHit() {
		this.numberOfHits++;
	}
	
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
	 * Wenn dieses Set ein wordedPattern hat, schmeisst es declarations raus, die keinen index haben,
	 * d.h. im Text von keinem Index gefolgt sind und daher wahrscheinlich keine Aufgabendeklaration
	 * sind. <br>
	 * Pattern muss worded sein und es muessen 2 oder mehr Vorkommen sein, da bei einem Vorkommen
	 * (= nur einer Aufgabe) auch gut einfach nur "Aufgabe" dastehen kann
	 * 
	 * @return true upon success, false upon failure
	 */
	public boolean clearWordedDeclarationsWithoutIndices() {
		try {
			if (this.getPattern().isWordedPattern() && (this.declarations.size() >= 2)) {
				for (Declaration dec : this.declarations){
					// Wenn das zweite Wort eh keine Zahl enthaelt, weg damit.
					if (!dec.hasIndex()){
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
	 * Ueberpreuft, ob die Declarationen im uebergebenen DeclarationSet monoton steigende
	 * IndexNumbers haben.
	 * 
	 * @return true, if in order, otherwise false
	 */
	public boolean isInOrder() {
		boolean correctOrder = true;
		if (this.declarations.size() > 2) {
			for (int i = 0; i < this.declarations.size() - 1; i++) {
				if (this.declarations.get(i).hasIndex() && this.declarations.get(i+1).hasIndex()) {
					if (this.declarations.get(i).getIndex().compare(this.declarations.get(i+1).getIndex()) == -1) {
						correctOrder = false;
					}
				} else {
					correctOrder = false;
				}
			}
		}
		return correctOrder;
	}
	
	/**
	 * Ueberpreuft, ob die uebergebenen Declarationen monoton steigende
	 * IndexNumbers haben.
	 * 
	 * @return true, if in order, otherwise false
	 */
	private static boolean isInOrder(ArrayList<Declaration> set) {
		boolean correctOrder = true;
		if (set.size() >= 2) {
			for (int i = 0; i <= set.size() - 2; i++) {
				if (set.get(i).hasIndex() && set.get(i+1).hasIndex()) {
					if (set.get(i).getIndex().compare(set.get(i+1).getIndex()) == -1){
						correctOrder = false;
					}
				} else {
					return false;
				}
			}
		}	
		return correctOrder;
	}
	
	/**
	 * Entfernt Declarations, die nicht in die Reihenfolge der Indices aller Deklarationenen
	 * passen. 
	 * 
	 * ==> Noch verbuggt, daher noch nicht benutzt <==
	 * 
	 * @return true bei Erfolg und hergestellter Reihenfolge <br>
	 * false bei Miserfolg, wenn keine korrekte Reihenfolge hergestellt werden konnte.
	 */
	public boolean removeDeclarationsNotInOrder(){
		if (this.isInOrder()) {
			return true;
		} else {
//			try {
				// Kopiere alle Declarations dieses Set in ein Array, auf der gearbeitet wird.
			
				//Declaration[] workingDeclarations = new Declaration[this.declarations.size()];
			
				ArrayList<Declaration> workingDeclarations = new ArrayList<Declaration>();
 				for (Declaration dec : this.declarations) {
					workingDeclarations.add(dec);
				}
				
//				for (int i = 0; i < this.declarations.size(); i++){
//					workingDeclarations[i] = this.declarations.get(i);
//				}
				
				
				int size = workingDeclarations.size();
				// Wieviele Variablen laufen = wieviele Elemente werden in diesem Durchgang gel�scht
				for (int i = 1; i <= size - 1; i++) {  
					// merkt sich, welche Aufgaben in diesem durchgang gel�scht werden sollen
					int[] removedDeclarations = new int[i];
					// Mit welchem Wert wird das erste Feld des int[] initialisiert
					for (int l = 0; l <= (size - i); l++) {
						// Jeden Eintrag des Variablen-Arrays auf seine eigene + l-te Stelle setzen
						for (int j = l; j <= removedDeclarations.length -1; j++) { 
							removedDeclarations[j] = j;
						}
					}
					// Die hinterste Variable = h�chste Stelle anfangen hochzuz�hlen
					for (int k = removedDeclarations.length - 1; k >= 0; k--) {
						// undescribable evil witch hexing
						while(removedDeclarations[k] <=(size - 1) - ((removedDeclarations.length - 1) - k)) {
							// L�sche die im int[] gesetzten Declarations
//							for (int m : removedDeclarations) {
							for (int m = removedDeclarations.length; i >= 0; i--) {
								workingDeclarations.remove(removedDeclarations[m]);
							}
							// Ueberpruefe nun ob sie inOrder sind, wenn ja, ersetze die Declarations dieses
							// Sets und gebe true aus.
							if (DeclarationSet.isInOrder(workingDeclarations)) {
								if (removedDeclarations.length == 1) {
									HashMapVerwaltung.erweitereHashmapBoolean(HashMapVerwaltung.eineAufgabeZuviel, true);
									HashMapVerwaltung.erweitereHashmapInt(HashMapVerwaltung.gestricheneAufgabe, removedDeclarations[0]+1);
								}
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
//			} catch (Exception e) {
//				System.out.println("Fehler beim OrderTesting aufgetreten.");
//				return false;
//			}
		}
		return false;	
	}

	/**
	 * Errechnet aus den gefunden Deklarationen einen Score zur spaeteren Gewichtung,
	 * welche Deklarationen zum schneiden der Aufgaben verwendet werden.
	 * Der Score ist noch in der Alpha-Phase und bedarf weiterer Konfiguration
	 * <br><br>
	 * Parameter, die bisher einfließen:<br>
	 * - Anzahl der gefundenen Deklarationen<br>
	 * - Mit welchem Muster die Deklarationen gefunden wurden<br>
	 * - Sind alle Indices der Aufgaben in der richtigen Reihenfolge?<br>
	 * - ... <br><br>
	 * 
	 * Dieser Score ist noch erweiterbar!
	 * 
	 * 
	 * @return Double, die den Score des DeclarationSets repräsentiert.
	 */
	public double calculateScore() {
		double score = 0;
		int numberOfDeclarations = this.declarations.size();
	    double declarationcountweight = 100;
		
		// Muster wird gewichtet
		score = score + this.pattern.getScoreOfPattern();
		
		if (numberOfDeclarations == 1) {
			declarationcountweight = 0.1;
		}
		

		
		// Es wird negativ gewichtet, wenn eine oder mehrere Deklarationen aus der Reihe fallen.
		if (!this.isInOrder()) {
			declarationcountweight = 1;
		}
		
		// Blo�e Anzahl der Deklarationen wird gewichtet
		score = score + numberOfDeclarations * declarationcountweight;
		
		this.score = score;
		return score;
	}
}
