package aufgaben_db;



/**
 * Utility for finding the first and last word within a document (body).
 * TODO The usefulness of this file is questionable.
 *
 * @author sabine, J.R.I.Balzer-Wein, worlddevelopment
 */
public class WordSearch {



	/**
	 * The method replaces problematic (Java?) special chars and Umlauts
	 * with empty.
	 * (And thus also splits words!)
	 * TODO Use Global.encodeUmlauts instead which not loses information.
	 *
	 * @changelog J.R.I.B.-Wein put in Unicode representations.
	 * @param zeile The string to replace the chars of
	 * @return The string without the potentially dangerous characters.
	 */
	private static String ersetzeSzUndUmLeer(String zeile) {

		String ergebnis = zeile.replaceAll("\u00D6", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00F6", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00DC", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00FC", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00C4", " ");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00E4", " ");
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
	 * Searches the first word of the given document contents
	 * for continued processing of RTF, TEX.
	 * FIXME Special chars and Umlauts are lost. TODO Encode.
	 *
	 * @param filename Array of lines of the file's contents.
	 * @return The first word of the document (body) or "" if none.
	 */
	public static String sucheErstesWortImDoc(String[] filename) {

		System.out.println("Searching 1st word in document.");

		for (int i = 0; i < filename.length; i++) {
			System.out.println("Line: " + i);

			// Words with letter count < 4 are ignored:
			// FIXME 4 is hardcoded, guessed
			if (filename[i].length() < 4 ) {
				continue;
			}

			String zeile = ersetzeSzUndUmLeer(filename[i]);
			System.out.println(zeile);
			// Split into words|word chunks:
			String[] array = zeile.split("\\s+");

			// First word fragment made of > 3 connected chars:
			for (int j = 0; j < array.length; j++) {
				String w = array[j];
				if (w.length() < 4) {
					continue;
				}
				System.out.println("Word length:" + w.length());
				System.out.println("Found first word: " + w);
				return w;
			}

		}
		// => No word found in Document.
		return "";
	}



	/**
	 * Search the last content word of document.
	 *
	 * @param filename Lines of raw content.
	 * @return The last word or "".
	 */
	public static String sucheLetztesWortImDoc(String[] raw_content) {

		for (int i = raw_content.length - 1; i > -1; i--) {
			String line = raw_content[i];
			// Words with letter count < 2 are ignored: FIXME hardcoded
			if (line.length() < 2) {
				continue;
			}

			String zeile = ersetzeSzUndUmLeer(line);
			// Split into words|word chunks:
			String[] array = zeile.split("\\s+");

			for (int j = array.length - 1; j > -1; j--) {
				String w = array[j];
				if (w.length() < 2) {
					continue;
				}
				System.out.println("Word length:" + w.length());
				System.out.println("Found last word: " + w);
				return w;
			}
		}
		// => No word in document!
		return "";

	}



	/**
	 * Shorten the given array to a given length. (removing from tail)
	 * TODO The usefulness of this method is questionable.
	 *
	 * @param array to shorten.
	 * @param zaehler Length to shorten to.
	 * @return The shortened array.
	 */
	public static String[] kuerzeArray(String[] array, int zaehler) {
		//System.out.println("kuerzeArray ...");
		String[] ergebnis  = new String[zaehler];
		for (int i = 0; i < zaehler; i++) {
			ergebnis[i] = array[i];
		}
		return ergebnis;
	}



	/**
	 * Shorten 2 dimensional arrays.
	 * TODO The usefulness of this method is questionable.
	 *
	 * @param array to shorten.
	 * @param zaehler Length to shorten to.
	 * @return The shortened 2 dimensional array.
	 */
	public static String[][] kuerzeArray(String[][] array, int zaehler) {
		// FIXME The 2 is a magic number, no reason|origin given.
		String[][] ergebnis  = new String[zaehler][2];
		for (int i = 0; i < zaehler; i++) {
			ergebnis[i] = array[i];
		}
		return ergebnis;
	}



}
