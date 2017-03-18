package aufgaben_db;


import java.util.ArrayList;


/**
 * Represents a numbering index of typical outlines.
 * (e.g. 2.5, 7.12.3, 1a, 2-1, B), 3a), ...)
 *
 * @author Schweiner, Jan R.I.B.-Wein, worlddevelopment
 *
 */
public class IndexNumber {

	private ArrayList<Integer> index = new ArrayList<Integer>();

	public IndexNumber() {
	}

	public IndexNumber(int[] intArray) {
		for (int i : intArray) {
			index.add(i);
		}
	}

	public IndexNumber(ArrayList<Integer> intList) {
		for (int i : intList) {
			index.add(i);
		}
	}

	/**
	 * Appends a number to the Index (x = new number, e.g. 4.1 -> 4.1.x)
	 *
	 * @param i integer that shall be appended to the index.
	 */
	public void addNumber(int i) {
		index.add(i);
	}

	/**
	 * Delivers the count of integers.
	 *
	 * @return int
	 */
	public int getSize() {
		return this.index.size();
	}

	/**
	 * Delivers the index.
	 *
	 * @return Index as arraylist of Integer
	 */
	public ArrayList<Integer> getIndex() {
		return this.index;
	}

	/**
	 * Returns the number at position i within the index (numbers).
	 *
	 * e.g. index := 4.2.5, i = 1 => returns 1
	 * @param i position to be returned.
	 * @return int at position i
	 */
	public int getNumber(int i) {
		int out = 0;
		try {
			out = this.index.get(i);
		} catch (Exception e) {
		}
		return out;
	}

	/**
	 * The index data encoded into a string.
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
	 * Compares two IndexNumber objects
	 *
	 * @param givenIndex
	 * @return 1 if calling IndexNumber is greater <br/>
	 * 0 if both are equal <br/>
	 * -1 if calling IndexNumber is smaller <br/>
	 * null if at least one of them contains null
	 * (e.g. has not been initialized)
	 */
	public Integer compare(IndexNumber givenIndex) {
		int maxBound = Math.min(this.index.size(), givenIndex.index.size());
		try {
			for (int i = 0; i < maxBound; i++) {
				if (this.index.get(i) > givenIndex.getNumber(i)) {
					return 1;
				}
				else if (this.index.get(i) < givenIndex.getNumber(i)) {
					return -1;
				}
				else {
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println(
					"An error occurred when comparing index numbers.");
			return null;
		}
		return 0;
	}
}
