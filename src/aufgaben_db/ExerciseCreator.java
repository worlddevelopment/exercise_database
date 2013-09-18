package aufgaben_db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import HauptProgramm.Declaration;

/**
 * 
 * @author sabine
 *
 *
 * Diese Klasse bearbeitet nur das Textdokument,
 * nachdem bereits die Aufgabendeklarationen gefunden wurden
 * 
 * Fuer das Textdokument sind DocAnfang und DokEnde unwichtig, auch wie
 * die Aufgabendeklarationen im einzelnen heissen ist unwichtig
 * daher werden nur die Info ueber die Anzahl der Aufgaben und die
 * Zeilennummern benoetigt
 * 
 */

public class ExerciseCreator {
	
	/**
	 * Erstellt aus den Declaration uebergebenen Sheetdraft einzelne Aufgabentexte als String[],
	 * indem der plaintext des Sheetdraft entsprechend geschnitten wird.
	 * 
	 * @state completely reworked
	 * @debugged no
	 * @param sheet Sheetdraft, dessen Einzelaufgaben bestimmt werden sollen.
	 * @return ArrayList von Exercise mit den Aufgaben, die erstellt wurden.
	 */
	public static Map<String, Exercise> erstelleEinzelaufgaben(Sheetdraft sheetdraft) {
		
		// ArrayList der zu schneidenen Aufgaben
		Map<String, Exercise> outputExercises = new HashMap<String, Exercise>();
		
		// Sheets, in denen keine Deklarationen gefunden wurden, abfangen.
		//JRIBW. because they are not splittable into exercises.
		Global.addMessage("Sheet or Draft was not splittable. No Declarations found."
				, "danger");
		if (sheetdraft.getDeclarationSet().declarations.size() == 0) {
			return outputExercises;
		}
		
		// ArrayList der Declarations des sheets, zur einfacheren Referenzierung
		ArrayList<Declaration> sheetDeclarations = sheetdraft.getDeclarationSet().declarations;
		
		// Erzeugt fuer alle Texte zwischen zwei Deklarationen eine neue Aufgabe,
		//letzte Declaration bis zum Ende fehlt noch.
		int ex_count_and_pos = 0;
		String header_of_its_sheet = "";
		String ex_filelink;
		for (int i = 0; i < sheetDeclarations.size() - 1; i++) {
			// Neues String[] mit der groesse des Zeilenabstands zwischen zwei aufeinanderfolgenden Deklarationen
			int exercise_count_according_declarations = (sheetDeclarations.get(i+1).getLine() - sheetDeclarations.get(i).getLine());
			String[] exerciseText = new String[exercise_count_according_declarations];
			String[] exerciseRaw = new String[exercise_count_according_declarations];
//			String lineBeforeNewExerciseDeclaration;
//			if (!(sheetsDeclarations.get(i).getLine() < 0)) {//Prevents out of bounds.
//				lineBeforeNewExerciseDeclaration = sheetdraft.getRawContent()[exercise_count_according_declarations];
//			}
			// von der Zeile der Declaration an wir ins neue String[] ruebergeschrieben
			for (int j = 0; j < exercise_count_according_declarations; j++) {
				int nextLine = j + sheetDeclarations.get(i).getLine();
				exerciseText[j] = sheetdraft.getPlainText()[nextLine];
				exerciseRaw[j] = sheetdraft.getRawContent()[nextLine];
				/*ATTENTION: The first (or last) exercise-raw-content-lines
				 *           have to overlap because there is potential content
				 *           of the previous/next exercise in the same declarations line. */
			}
			String lineAfterNewExerciseDeclaration;
			if (sheetdraft.getRawContent().length > exercise_count_according_declarations) {
				lineAfterNewExerciseDeclaration = sheetdraft.getRawContent()[exercise_count_according_declarations];
			}
			// Erzeugen eines neuen Aufgaben-objekts
			ex_count_and_pos++; //increment here because we start with 1 instead of 0
			ex_filelink = sheetdraft.getFilelinkForExerciseFromPosWithoutEnding(ex_count_and_pos) + "." + sheetdraft.getFileEnding();
			String[] exerciseRawPlusExtraNextLine = new String[exerciseRaw.length + 1];
			System.arraycopy(exerciseRaw, 0, exerciseRawPlusExtraNextLine, 0, exerciseRaw.length);
			Exercise loopExercise = new Exercise(
					ex_filelink
					, sheetDeclarations.get(i)
					, exerciseText
					, exerciseRaw/*TODO determine if the extra line really req.)*/
					, header_of_its_sheet
			);
			outputExercises.put(ex_filelink, loopExercise);
		}
		
		
		//Letzte Declaration bis zum Ende:
		Declaration lastDec = sheetDeclarations.get(sheetDeclarations.size() - 1);
		int exercise_count_according_declarations = sheetdraft.getPlainText().length - lastDec.getLine();
		String[] exerciseText = new String[exercise_count_according_declarations];
		String[] exerciseRaw = new String[exercise_count_according_declarations];
		String[] exerciseRawPlusExtraNextLine = new String[exerciseRaw.length + 1];
		System.arraycopy(exerciseRaw, 0, exerciseRawPlusExtraNextLine, 0, exerciseRaw.length);
		for (int j = 0; j < exerciseText.length; j++) {
			int nextLine = j + lastDec.getLine();
			exerciseText[j] = sheetdraft.getPlainText()[nextLine];
			exerciseRaw[j] = sheetdraft.getRawContent()[nextLine];
			/*ATTENTION: The first (or last) exercise-raw-content-lines
			 *           have to overlap because there is potential content
			 *           of the previous/next exercise in the same declarations line. */
		}
		// Erzeugen eines neuen Aufgaben-objekts
		ex_count_and_pos++; //increment here because we start with 1 instead of 0
		ex_filelink = sheetdraft.getFilelinkForExerciseFromPosWithoutEnding(ex_count_and_pos) + "." + sheetdraft.getFileEnding();
		Exercise loopExercise = new Exercise(
				ex_filelink, lastDec, exerciseText, exerciseRaw,
				/* Aufgaben_DB.extractRawContentDependingOnFiletype(filelink),*/
				//above will not work because the file not yet is written to disk!
				header_of_its_sheet
		); 
		outputExercises.put(ex_filelink, loopExercise);

		
		return outputExercises;
	}

		
		
	/**
	 * Löscht die Inhalte aller angegebenen Zeilen
	 * @param filename Text als String[] (Zeile pro Feld)
	 * @param anfang
	 * @param ende
	 * @return String[] mit allen Zeilen zwischen anfang und ende geloescht.
	 */
	public static String[] loescheZeilen(String[] filename, int anfang, int ende) {
		
		for (int i = anfang; i <= ende; i++ ) {
			filename[i] = "";
			}
		System.out.println("Die Zeilen " + anfang + " bis " + ende + " wurden gelöscht");
		return filename;
	}
	
}
