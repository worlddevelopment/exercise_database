/**
 * 
 */
package aufgaben_db;

import java.util.ArrayList;

/**
 * Klasse die einen Index, wie er bei Aufgabenstellung vorkommt, beschreibt.
 * speichert und vergleicht durch ein beliebiges Zeichen getrennte Zahlen (z.B. 2.5 oder 7.12.3)
 * 
 * @author Schweiner
 *
 */
public class IndexNumber {
	
	private ArrayList<Integer> index = new ArrayList<Integer>();
	
	public IndexNumber(){		
	}

	public IndexNumber(int[] intArray){
		for (int i : intArray){
			index.add(i);
		}
	}
	
	public IndexNumber(ArrayList<Integer> intList){
		for (int i : intList){
			index.add(i);
		}
	}
	
	/**
	 * Appends a number to the Index (x = neue Zahl, aus 4.1 wird 4.1.x)
	 *
	 * @param i int, die an den Index angefügt werden soll.
	 */
	public void addNumber(int i) {
		index.add(i);
	}
	
	/**
	 * Liefert die Anzahl, der durch Zeichen getrennten Integer zurück.
	 * @return int
	 */
	public int getSize(){
		return this.index.size();
	}
	
	/**
	 * Gibt den Index zurueck
	 * @return Index als Integer-ArrayList
	 */
	public ArrayList<Integer> getIndex(){
		return this.index;
	}
	
	/**
	 * Gibt die Indexzahl an der Stelle i zurueck
	 * Bsp: Index = 4.2.5, i = 1; Zurueckgegeben wird 2 
	 * @param i Stelle, dessen Zahl ausgegeben werden soll
	 * @return int an der Stelle i
	 */
	public int getNumber(int i){
		int out = 0;
		try {
			out = this.index.get(i);
		} catch (Exception e) {
		}
		return out;
	}
	
	/**
	 * Gibt eine String-Repraesentation des Indexes aus.
	 * 
	 */
	public String toString() {
		String output = "";
		for (int i : this.index) {
			output = output + i + ".";
		}
		
		return output;
	}

	
	/**
	 * Compares two IndexNumber Objects
	 * 
	 * @param givenIndex
	 * @return 1 if calling IndexNumber is greater <br>
	 * 0 if both are equal <br>
	 * -1 if calling IndexNumber is smaller <br>
	 * null if at least one of them contains null (e.g. hasnt been initialisiert) 
	 */
	public Integer compare(IndexNumber givenIndex){
		int maxBound = Math.min(this.index.size(), givenIndex.index.size());
		try {
			for(int i = 0; i <= maxBound - 1; i++) {
				if (this.index.get(i) > givenIndex.getNumber(i)) {
					return 1;
				} else if(this.index.get(i) < givenIndex.getNumber(i)) {
					return -1;
				} else {				
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Beim IndexVergleich ist ein Fehler aufgetreten.");
			return null;
		}
		return 0;
	}
}