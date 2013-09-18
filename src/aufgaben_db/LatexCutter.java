/**
 * 
 */
package aufgaben_db;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import HauptProgramm.Declaration;

/**
 * Klasse, die Methoden zum schneiden eines .tex-files bereitstellt.
 * 
 * @author Schweiner
 *
 */
public class LatexCutter {

/**
 * Schneidet das uebergenene LAtexSheet Objekt anhand der in diesem gespeicherten DeclarationSet und 
 * gibt die geschnittenen Aufgaben als Exercise-ArrayList auf
 * @param sheetdraft zu untersuchenden LatexSheet
 * @return ArrayList der geschnittenen Exercises (mit header und \begin / \end{document}, also theoretisch direkt komplierbar.
 */
//	public boolean cutExercise(String pathname, String head, String tail) {
	public static ArrayList<Exercise> cutExercises(Sheetdraft sheetdraft) {	
		
		String headermixture = ""; //to determine
		
		System.out.println("LatexCutter wurde aufgerufen.");
		
		BufferedReader texReader = null;
//		ArrayList<String> allLines = new ArrayList<String>();
		String[] allTexLines = sheetdraft.getRawContent();//getTexText();
		int indexOfFirstIdentifier = 0;
		int indexOfLastIdentifier = 0 ;
		int indexOfBeginDoc = 0;
//		File cutExercise;
		BufferedWriter fileWriter = null;
		int indexOfFirstCut = 0;
		int indexOfLastCut = 0;
		boolean lastIdentifierFound = false;
		
		
		// ArrayList der zu schneidenen Aufgaben
		ArrayList<Exercise> outputTexExercises = new ArrayList<Exercise>();
		
		// Sheets, in denen keine Deklarationen gefunden wurden, abfangen.
		if (sheetdraft.getDeclarationSet().declarations.size() == 0) {
			return outputTexExercises;
		}
		
		// ArrayList der heads des sheets, zur einfacheren Referenzierung
		ArrayList<String> heads = new ArrayList<String>();
		
		// Anhand der heads, ArrayList mit den Zeilennummern, an denen geschnitten
		// werden soll, erstellen.
		ArrayList<Integer> linesToCut = new ArrayList<Integer>();
		
		HashMap<Integer, Declaration> lineDecReference = new HashMap<Integer, Declaration>();
		int ex_count_and_pos = 0;
		for (int i = 0; i < allTexLines.length; i++) {
			String singleLine = allTexLines[i];
				for (Declaration dec: sheetdraft.getDeclarationSet().declarations) {
					if (dec.hasHead()) {
						if (dec.getHead() != null && singleLine != null) {				
							System.out.println("Untersuchter Head lautet" + dec.getHead().toString());
							if ((singleLine.contains(dec.getHead().get(0)) && singleLine.contains(dec.getHead().get(1)) && singleLine.contains(dec.getHead().get(2)))
									|| (singleLine.contains(dec.getHead().get(0)) && singleLine.contains(dec.getHead().get(1)) && singleLine.contains(dec.getHead().get(3)))
									|| (singleLine.contains(dec.getHead().get(0)) && singleLine.contains(dec.getHead().get(2)) && singleLine.contains(dec.getHead().get(3)))	
									|| (singleLine.contains(dec.getHead().get(1)) && singleLine.contains(dec.getHead().get(2)) && singleLine.contains(dec.getHead().get(3)))) {
								System.out.println("Head " + dec.getHead() + "gefunden!");
								Integer indexOfCut = i;
								while (!allTexLines[indexOfCut].startsWith("\\begin")) {
									indexOfCut--;
								}
								System.out.println("Cut in Zeile " + indexOfCut);
								linesToCut.add(indexOfCut);
								lineDecReference.put(indexOfCut, dec);
						}
					}
				}
			}
		}
		
		//Zeile mit "\begin{document}" auslesen
		for (int i = 0; i < allTexLines.length; i++) {
			String singleLine = allTexLines[i];
			if (singleLine.startsWith("\\begin{docum")) {
				indexOfBeginDoc = i;
				System.out.println("begindoc gefunden in Zeile " + indexOfBeginDoc);
			}	
		}
		// Und den Header Speichern
		ArrayList<String> header = new ArrayList<String>();
		for (int i = 0; i <= indexOfBeginDoc; i++) {
			header.add(allTexLines[i]);
		}
		
		
		// Erzeugt fuer jeden Abschnitt zwischen 2 Heads eine Aufgabe, fügt den Header vorn
		// und enddocument hinten an, erzeugt eine neue Aufgabe und schreibt diese Weg.
		for (int i = 0; i < linesToCut.size() - 1; i++) {
			// ArrayList für eine geschnittene Aufgabe erzeugen
			ArrayList<String> cutExercise = new ArrayList<String>();
			// von der Zeile des derzeigen Cut-indices bis zur Zeile des nächsten cut-indices
			// schneiden und speichern
			for (int j = linesToCut.get(i); j < linesToCut.get(i + 1); j++ ) {
				cutExercise.add(allTexLines[j]);
			}
			// Header noch davor
			for (int j = header.size()-1; j >= 0; j--) {
				cutExercise.add(0, header.get(j));
			}
			// und enddocument noch dahinter
			cutExercise.add(new String("\\end{document}"));
			// Zum Array umwandeln
			String[] exerciseText = new String[cutExercise.size()];
			for (int j = 0; j < exerciseText.length; j++) {
				exerciseText[j] = cutExercise.get(j);
				exerciseText[j] = ersetzeUmlaute(exerciseText[j]);
			}
			Declaration dec = lineDecReference.get(linesToCut.get(i));
			//creation of file for exercise on harddrive done in writeToHarddisk
			ex_count_and_pos++; //increment here because we start with 1 instead of 0
			Exercise loopExercise = new Exercise(
					sheetdraft.getFilelinkForExerciseFromPosWithoutEnding(ex_count_and_pos) + sheetdraft.getFileEnding()
					, dec
					, exerciseText
					, exerciseText /*TODO the above of those 2 should be plain text*/
					, headermixture
			); 
			outputTexExercises.add(loopExercise);
			
		}
		
		System.out.println("LatexCutter Beendet");
		return outputTexExercises;
	}		

		
		

//			// Suchen des "head"
//			// ueberpruefe, ob die Zeile AnfangsWort oder Endwort enthaelt
//			if (singleLine.contains(head)) {
//				indexOfFirstIdentifier = allLines.size() - 1; // Ort der Zeile, die das Anfangswort enthaelt.
//				System.out.println("FirstIdentifier gefunden in Zeile " + indexOfFirstIdentifier);
//				indexOfFirstCut = indexOfFirstIdentifier;
//				
//				while (!allLines.get(indexOfFirstCut).startsWith("\\begin")) {
//					indexOfFirstCut--;
//				}
//				System.out.println("Cut in Zeile " + indexOfFirstCut);
//			}
////			if (singleLine.startsWith("\\begin")) {
////				System.out.println("begin gefunden");
////			}
//			if (singleLine.contains(tail)) {
//				indexOfLastIdentifier = allLines.size() - 1; // Ort der Zeile, dies Endwort enthaelt.
//				System.out.println("LastIdentifier gefunden in Zeile " + indexOfLastIdentifier);
//				indexOfLastCut = indexOfLastIdentifier;
//				lastIdentifierFound = true;
//			}
//				
//			if (lastIdentifierFound && singleLine.startsWith("\\end")) {
//				indexOfLastCut = allLines.size() - 1;
//				System.out.println("Cut in Zeile " + indexOfLastCut);
//				lastIdentifierFound = false;
//			}
//		
//			
//		cutExercise = new File("E:/Studium/Semester/SS2011/Softwarepraktikum/TestDokumente/testOutputLatexCut2.txt");
//	
//		try {
//			fileWriter = new BufferedWriter(new FileWriter(cutExercise));
//			System.out.println("BufferedWriter ge�ffnet");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Kann File nicht schreiben");
//		}
//		
//		for (int i = 0; i <= allLines.size(); i++) {	
//			try {
//				if (i <= indexOfBeginDoc) {
//					fileWriter.newLine();
//					fileWriter.write(allLines.get(i));
//					System.out.println("Schreibe Zeile " + i + " : \" " + allLines.get(i) + " \" ");
//				}
//				
//				if (i >= indexOfFirstCut && i <= indexOfLastCut){
//					fileWriter.newLine();
//					fileWriter.write(allLines.get(i));
//					System.out.println("Schreibe Zeile " + i + " : \" " + allLines.get(i) + " \" ");
//				}
//
////				fileWriter.write(allLines.get(i));
////				System.out.println("Schreibe Zeile " + i + " : \" " + allLines.get(i) + " \" ");
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println("Kann Zeile nicht schreiben");
//			}
//
//		}
//		try {
//			fileWriter.newLine();
//			fileWriter.write("\\end{document}");
//			fileWriter.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("cant close");
//			e.printStackTrace();
//		}
//		
//		
//		return true;
//		
//	}

	/**
	 * Ersetzt alle Umlate und ß in der ensprechenden Zeile durch Ersatzzeichen
	 * und gibt den veraenderten String wieder aus. 
	 * 
	 * @changelog JRIBW changed Oe -> \"O, etc. It's latex, to be tested! TODO
	 * @param zeile
	 * @return veraenderter String
	 */
	private static String ersetzeUmlaute(String zeile) {
		
		String ergebnis = zeile.replaceAll("Ö", "\\\"O");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("ö", "\\\"o");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("Ü", "\\\"U");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("ü", "\\\"u");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("Ä", "\\\"A");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("ä", "\\\"a");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("ß", "sz");
		return ergebnis;
	}
}	
	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		
//		LatexCutMain myLCM = new LatexCutMain();
//		myLCM.cutExercise("E:/Studium/Semester/SS2011/Softwarepraktikum/Tex/VK-WS1011-B1.tex", "Melden", "Vorkurses ist");
//
//	}
//

