package aufgaben_db;


import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.NotImplementedException;





public class Aufgaben_DB {
	
	
	//======= ATTRIBUTES ==================================================//
	private static Map<String, Sheetdraft> allSheetdrafts = new HashMap<String, Sheetdraft>();
	private static Map<String, Sheetdraft> drafts = new HashMap<String, Sheetdraft>(); 
	private static Sheetdraft latestChangedDraft;
	
	//to allow for switching the context. so native, common and plaintext exercises are stored:
	public static DocType commonFormat = DocType.HTML; //<-- HTML has been chosen common storage format.
	
	
	//======= METHODS =====================================================//
	/**
	 * Erstellt aus dem übergebenen Uebungsballt die einzlenen Aufgaben und gibt sie innerhalb
	 * des Sheetdrafts-Objktes wieder aus. Gibt je nach InputFile nicht nur Sheetdraft
	 * -Objekte, sondern auch Sheetdraft oder RtfSheet aus. <br />
	 * <br />
	 * Achtung: Diese Methode schreibt (au\u00DFer bei rtf-Dateien) noch keine Dateien auf die Festplatte! Rufe dazu in der Aufrufenden 
	 * Methode die jeweiligen toString-Methoden der zurückgegebenen -sheet Objekte auf! <br />
	 * <br />
	 * Achtung: Bei einem filePath der auf ein .tex-file zeigt, geht die Methode davon aus, dass im selben Ordner ein .pdf mit sonst dem
	 * selben Namen (!) liegt, welches das kompilierte .tex-file darstellt. Dies wird zum finden der Aufgaben-heads ben\u00F6tigt,
	 * da es sehr schwer ist, aus einem blo\u00DFen .tex File aufgrund von unterschiedlichen Styles der Ersteller, Aufgabendeklarationen
	 * zu finden und damit Aufgaben herauszuschneiden.
	 * 
	 * 
	 * @param filePath Dateipfad der zu untersuchenden Datei. Soforn diese Datei nicht im selben Ordner liegt, in dem das 
	 * Programm ausgefuehrt wird, muss der Dateipfad absoult sein.
	 * @param root
	 * @param target Target 
	 * @return Sheetdraft Objekt
	 * @throws Exception 
	 */
	/**
	 * Manchmal ist es angebracht, den Dateilink auf dessen Basis wir arbeiten zu aendern (z.B. 
	 * fuer DOC->DOCX, dann DOCX als neue Basis).
	 * Sometimes the filelink on which base we operate should be updated (e.g. for DOC->DOCX which 
	 * we convert to DOCX. Then we continue with the DOCX as the base).
	 * That's why there is the static filelink attribute and the strange looking processUploaded.. 
	 * call without parameter (so that in this method the attribute is used and not the parameter.
	 * This allows to easily change it on the fly.
	 * IMPORTANT NOTE: In the sheetdraft itself still the no-longer-base filelink resides. Update or new Sheetdraft()? 
	 *
	 * @param filelink - absolute filelink to uploaded file.
	 */
	public static String filelink;
	public static Sheetdraft processUploadedSheetdraft(String filelink)
//			throws Exception {
//		//Aufgaben_DB.filelink = filelink; 
//		return processUploadedSheetdraft(filelink);
//	}
	//private static Sheetdraft processUploadedSheetdraft()
			throws Exception {
		//HashLog.initialisiereLogFile(); <-- out as of version 31.01
		
		//--superfluous? -----------------------------------------------------
		// File-Endung wird mit Hilfe der enum DocType ueberprueft und gesetzt
		DocType sheetType;
		sheetType = DocType.getByEnding(Global.extractEnding(filelink));
//			for(DocType type : DocType.values()) {//this is a relict.
//				if (filePath.endsWith(type.getCode())) {
//					sheetType = type;
//					break; //JRIBW: added
//				}
//			}
		if (sheetType == null) {
			throw new NotImplementedException();//JRIBW: shouldn't it be a filetype not supported
			//exception instead of FileNotFoundException? Changed it to NotImplementedException.
		}
		System.out.println("Es handelt sich um ein " + sheetType + "-Dokument");
		//--superfluous? -END--------------------------------------------------
		
		
		
		
		//======= BASEFILE HAS BE CHANGED? =========================================//
		System.out.println("Basefile as uploaded: (prior to compatibility conversion)\r\n" + filelink);
		//Aufgaben_DB.convertInputFormatIntoSupportedFormat();
		//generatedFlavours.addAll(
		if (filelink.endsWith("doc")) {
			Global.addMessage("Unsupported basefile. Trying to convert.", "warning");
			
			String hypo_filelink = Global.replaceEnding(filelink, "docx");
			/*filelink = */Global.convertFile(filelink, "docx")/*.get(0)*/;//<-- the general solution.
			if (new File(hypo_filelink).exists()) {//<-- is already absolute file path.
				Global.addMessage("Unsupported basefile successfully converted to a compatible one: " 
						+ hypo_filelink, "success");
			}
			else {
				Global.addMessage("Unsupported basefile converted but could not be found on storage medium.", "nosuccess");
			}
			
			//not to forget:
			filelink = hypo_filelink;
		}
		//);
		System.out.println("Basefile as supported: " + filelink);

		
		Sheetdraft sheetdraft = new Sheetdraft(filelink);
		
		
		
		//Here follow the methods for dealing with the original files,
		//the filetype or content is being converted, the exercises are cut out. 
		//0) create flavours of this document.
		List<String> flavoursCreated_filelinks = sheetdraft.createFlavours();
		String common_filelink = null;
		if (flavoursCreated_filelinks != null && flavoursCreated_filelinks.size() > 0) {
			for (String flavour : flavoursCreated_filelinks) {
				System.out.println("Generated filetype flavour: " + flavour);
				String commonFormatLowerCase = commonFormat.name().toLowerCase();
				if (flavour.endsWith(commonFormat.name()) || flavour.endsWith(commonFormatLowerCase)) {
					common_filelink = flavour;
					System.out.println("Found " + commonFormatLowerCase + " file.");
					if (new File(common_filelink).exists()) {
						System.out.println("[confirmed] Found it in the filesystem at location: " + flavour);
						break; /*have found what we wanted. No matter if it exists or not. Okay, there are not 
						many flavours anyway, so we only stop if it could be found on disk too. */
					}
				}
			}
		}

		
		//1) plain text / raw content
		sheetdraft.extractPlainText();
		System.out.println(Global.addMessage("[ready] Plain text extracted from Sheet. ----", "success"));
		
		
		//2) The search for exercise declarations.
		int declarationsFoundCount = 0;
		sheetdraft.setDeclarationSet(DeclarationFinder.findeDeklarationen(sheetdraft));
		if (sheetdraft.getDeclarationSet() != null) {
			declarationsFoundCount = sheetdraft.getDeclarationSet().declarations.size();
		}
		System.out.println(Global.addMessage("[ready] Aufgaben- und L\u00F6sungs-Deklarationen wurden gesucht. Found " 
		+ declarationsFoundCount + " in the set with the highest score (total count, may contain mixed types). ----", (declarationsFoundCount > 0 ? "success" : "nosuccess danger")));
		
		if (Global.extractEnding(sheetdraft.getFilelinkRelative()).equals("rtf")
				&& sheetdraft.getClass().toString().equals("Sheetdraft")) {/*Sheetdraft only, not Exercise*/
			MethodenWortSuche.schreibeErstesLetztesWortAusTextInHashMap(sheetdraft.getPlainText());
			System.out.println("Looking for identifiers in the given RTF-Document.");
			rtf.SucheIdentifierRtfNeu.bearbeiteRtf((Sheetdraft)sheetdraft);
		}
		
		
		//3) Create exercises. Look for solutions for each exercise (within the exercise's content). 
		sheetdraft.extractExercisesPlainText();/*TODO either split into Exercise and Solution at once 
				or otherwise employ an algorithm that besides for Loesung/Solution splitting keywords 
				checks for repeating content (must support formulas) too.
				Reason:  No formatting to determine whether something new (e.g. paragraph, 
				         completely new style, ...) begins. */
		System.out.println(Global.addMessage("[ready] Einzelaufgaben (Plain Text) wurden erstellt. ----", "success"));
		
		//Native format:
		sheetdraft.extractExercisesNativeFormat();
		System.out.println(Global.addMessage("[ready] Einzelaufgaben (Nativ Format) wurden erstellt. ----", "success"));
		
		//COMMON FORMAT - CONTEXT SWITCH
		// a1) store the native filelink: 
		String native_filelink = sheetdraft.getFilelink();
		sheetdraft.setFilelink(common_filelink);//<-overwrite, because the only alternative is to pass around arguments, and that proved vulnerable here.
		// b1) store the old native format exercise collection before overriding:
		Map<String, Exercise> allExercisesRawFormat = sheetdraft.getAllExercises();
		sheetdraft.setAllExercisesRawFormat(sheetdraft.getAllExercisesCommonFormat());//<- to operate on the common basis.

		sheetdraft.extractExercisesNativeFormat(/*html_filelink*/);//<- not the same as above as we have changed the context, i.e. the sheetdraft's filelink.
		System.out.println(Global.addMessage("[ready] Einzelaufgaben (Common Format, i.e. " + commonFormat.name() +") wurden erstellt. ----", "success"));
		
		//COMMON FORMAT -END - CONTEXT SWITCH
		// a2) restore the native filelink: (we never operate fully on HTML basis, this way fusion may choose between both.)
		sheetdraft.setFilelink(native_filelink); //prefer native format fusion if all exercises are of one type.
		// b2) restore native format exercises:
		sheetdraft.setAllExercisesCommonFormat(sheetdraft.getAllExercisesCommonFormat());
		sheetdraft.setAllExercisesRawFormat(allExercisesRawFormat);
		
		
		//4) synchronize filesystem/harddrive
		sheetdraft.writeExercisesToHarddisk(sheetdraft.getAllExercisesPlainText());
		//sheetdraft.writeExercisesToHarddisk(sheetdraft.getAllExercises());
		System.out.println(Global.addMessage("[ready] Einzelaufgaben wurden auf Festplatte geschrieben. ----", "success"));
		
		return sheetdraft;
		
	}
	
	
	
	/**
	 * Listet alle Dateien in angegebenen Ordner und zersplittet jede Datei
	 * Diese ist eine zusaetztliche Funktion fuer Zersplittung der Dateien
     * 
     * @author Artiom Kichojal, J.R.I.B.-Wein
	 * @param source
	 *            Quellpfad
	 * @ param target
	 *            Zielpfad 
	 * @throws Exception 
	 */
	public static void splitAllFilesInDirectory(String source/*, String target*/)
			throws Exception {
		//TODO J.R.I.B.-Wein: check for existance and if it's a directory? Added it:
		File sourceFile = new File(source);
		String[] entries = (sourceFile.isDirectory() ? sourceFile.list() 
				: (sourceFile.exists() ? new String[]{source} : new String[0]));
		for (int i = 0; i < entries.length; i++) {
			Aufgaben_DB.processUploadedSheetdraft(source + System.getProperty("file.separator") + entries[i]);
		}

	}
	
	/**
	 * LOAD ALL SHEETDRAFTS
	 * @param user	- DB lecturer/author
	 * @param includeSheetsWhereUserIsLecturer
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void loadAllSheetdrafts() throws SQLException, IOException { /*and hence also the exercises*/
		loadAllSheetdrafts((String)Global.session.getAttribute("user"), false);
	}
	
	public static void loadAllSheetdrafts(boolean includeSheetsWhereUserIsLecturer) throws SQLException, IOException { /*and hence also the exercises*/
		loadAllSheetdrafts((String)Global.session.getAttribute("user"), includeSheetsWhereUserIsLecturer);
	}
	
	public static void loadAllSheetdrafts(String user, boolean includeSheetsWhereUserIsLecturer)
			throws SQLException, IOException { /*and hence also the exercises*/
		
		//if (user != null && !user.isEmpty()) {
		//	Global.session.setAttribute("user", user);
		//}
		
		//look for this user's drafts
		String query = "SELECT DISTINCT *"
				+ " FROM sheetdraft"
				+ (includeSheetsWhereUserIsLecturer ? ", lecturer" : "")
				+ " WHERE author = '"+ user +"'";
		if (includeSheetsWhereUserIsLecturer) {
			//Include sheetdrafts where user is lecturer because hiwis could
			//assist lecturers this way by uploading and editing for them.
			query = query + " OR lecturer = '" + user + "'"
					+ " AND lecturer_id = lecturer.id";
		}
		ResultSet res; res = Global.query(query);
		
		 
//		int i = -1;
//		int drafts_i = -1;
		if (res == null) {
			System.out.println("No sheetdrafts could be loaded for this lecturer: " + user + ".  includeheetsWhereUserIsLecturer: " + includeSheetsWhereUserIsLecturer);
			return ;
		}
		res.last();
		int resL; resL = res.getRow();
		if (resL == 0) {
			//The last changed one is the active draft!
			latestChangedDraft = new Sheetdraft();/*<-- this results in this one ALWAYS
			being the latest changed one if the ressource length is not checked for being zero!*/
		}
		res.beforeFirst();
		List<Map<String, Object>> resRows = new ArrayList<Map<String, Object>>();
		while (res.next()) {
//			++i;
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("filelink", res.getString("filelink"));
			resMap.put("is_draft", res.getInt("is_draft"));
			resRows.add(resMap);
		}
		// tackle memory leaks by closing result set and its statement properly:
		Global.queryTidyUp(res);
		for (Map<String, Object> row : resRows) {
			String filelink_debug = (String)row.get("filelink");
			if (filelink_debug.equals("-1")) {
				//delete such entries from the database
				Global.query("DELETE FROM sheetdraft WHERE filelink = -1 OR filelink = '-1';");
				Global.addMessage("Sheetdraft with erroneous Filelink detected"
						+": Filelink='-1'. Deleted all such entries from the database."
						, "info");
				Global.addMessage("Skipped 'erroneous filelink = -1' entry.", "success");
				continue;
			}
			allSheetdrafts.put(
				filelink_debug,
			    new Sheetdraft(
		    		filelink_debug
					/*
					,res.getString("filelink")
					,res.getString("type")
					,res.getString("course")
					,res.getString("semester")
					,res.getInt("lecturer_id")
					,res.getString("description")
					,res.getString("author")
					,res.getLong("whencreated")
					,res.getInt"is_draft")
					,res.getString("headermixture")
					,res.getLong("whenchanged")
					*/
			    )//.synchronizeWithDatabaseLoadFromIt(res.getInt("id"))
				/*this method must ensure that allSheetdrafts. keys (filelink)
				 *gets resynchronized to the changed lecturer = user*/
			    .synchronizeWithDatabaseLoadFromItBecomeIdentical(filelink_debug)
			);
			//DRAFT?
			//if (Integer.valueOf(res.getString("is_draft")) == 1) {
			if (((int)row.get("is_draft")) == 1) {
//				++drafts_i;
				drafts.put(
						//res.getString("id"),
						(String)row.get("filelink"),
						allSheetdrafts.get((String)row.get("filelink"))
				);
				//determine if this is the new latest changed draft
				Calendar calFromDb = Calendar.getInstance();
				calFromDb.setTimeInMillis(res.getLong("whenchanged"));
				if (latestChangedDraft == null || latestChangedDraft.getWhenchanged() == null
//						Integer.valueOf(res.getString("whenchanged"))
//						> Integer.valueOf(latestChangedDraft.getWhenchanged()) ) {
						|| calFromDb.after(latestChangedDraft.getWhenchanged())
						) {
					//this draft has been changed more recently 
					//latestChangedDraft = allSheetdrafts.get(i);
					latestChangedDraft = allSheetdrafts.get(filelink_debug);
				}
			}
		}
		
	}
	
	
	public static void delete_sheet(String filelink) throws IOException, SQLException {
		
		String message = "";//not to forget: reset after printing out

		//some data that has to be loaded from the db
	    int lecturer_id = 0;//don't delete the lecturer
	    String lecturer = "";//For the message what has been deleted.
	    String semester;
	    
	    boolean delete_highest_level_folder = false; //is set to true if no more sheetdrafts
	    //within the same semester the sheetdraft is can be found. (because semester
	    //is the highest level folder in the Global.uploadTarget filesystem directory)
	    
		
	    //======= DATABASE =======================================================//
		String str_query = "SELECT * FROM sheetdraft WHERE filelink='" + filelink + "';";
		ResultSet res = Global.query(str_query);
		if (res.next()) {
			lecturer_id = res.getInt("lecturer_id");
			semester = res.getString("semester");
		}
		else {
			System.out.println(
				Global.addMessage("No sheetdraft found in database! Sheet/Draft already deleted?", "danger")
			);
			return ;
		}
	    
	    
		
		//Loesche Vorschaubilder von Festplatte
		str_query = "SELECT filelink FROM exercise WHERE sheetdraft_filelink ='" + filelink + "';";
		ResultSet res0 = Global.query(str_query);
		while (res0.next()) {
			File f = new File(Global.root + Global.convertToImageLink(res0.getString("filelink")));
			if (Global.deleteFile(f)) {
			    message += "<p>Das Bild zu Aufgabe " + Global.extractFilename(filelink) + "  wurde gel&ouml;scht.</p>";
			}
		}
		// tackle the island's memory leaks:
		Global.queryTidyUp(res0);
		
		//Print generated message:
		System.out.print(
			Global.addMessage(message, "success")
		);

		
		//Loesche sheetdraft aus Der DB
		str_query = "DELETE FROM sheetdraft WHERE filelink='" + filelink + "';";
		Global.query(str_query);
		
		
		
		
		//Delete Draft exercises:
		str_query = "DELETE FROM draftexerciseassignment WHERE sheetdraft_filelink ='" + filelink + "';";
		Global.query(str_query);/*this should be the case for drafts only (is_draft == 1)*/
		
		//Delete sheet/draft exercises:
		//TODO: Should the originsheedraft_filelink be reset to N.A.?
		//IMPORTANT: Don't delete exercises that are still referenced by drafts! (*)
//		str_query = "DELETE FROM"
//					+ " (SELECT COUNT(*) AS referencecount, exercise_filelink"
//					+ " FROM draftexerciseassignment, exercise"
//					+ " WHERE exercise.sheetdraft_filelink = '" + filelink + "'"
//					+ " AND draftexerciseassignment.exercise_filelink = exercise.filelink)"
//					+ " AS referenced"//=> no hits if no exercise is referenced!
//					+ ", exercise"//if it IS NOT 0 then we keep it as other sheets reference it
//				+ " WHERE"
//				//+ " referenced.referencecount IS 0" /*(*)that's what we deal with here!*/
//				+ " AND exercise.sheetdraft_filelink = '" + filelink + "'"//all exercises of this sheetdraft
//				+ " AND exercise.filelink <> referenced.exercise_filelink"//exercises of this sheetdraft without those that are referenced!
//				//+ " OR referenced.referencecount IS 0 OR referenced IS NULL);"//referenced.exercise_filelink IS NULL
//				//+ " AND exercise.sheetdraft_filelink = refs.sheetdraft_filelink;"
//		;
		//Because DELETE queries don't like nested queries.
		str_query = " SELECT exercise.filelink"
		+ " FROM exercise"
		+ " INNER JOIN ("//<--LEFT JOIN would keep all exercises instead of only the not referenced ones.
				+ " SELECT exercise_filelink"
				+ " FROM draftexerciseassignment, exercise"
				+ " WHERE exercise.sheetdraft_filelink='" + filelink + "'"//->all exercises of this sheetdraft
				+ " AND draftexerciseassignment.exercise_filelink = exercise.filelink"//->exercises of this sheetdraft without those that are referenced!
		+ " ) referenced"
		+ " ON exercise.filelink <> referenced.exercise_filelink";//->filter out the referenced ones!
		//SELECT exercise.filelink FROM exercise INNER JOIN ( SELECT exercise_filelink FROM draftexerciseassignment, exercise WHERE exercise.sheetdraft_filelink='uploads/WS_2013/Mateh/Gauss/Uebung/Blatt7.docx.docx' AND draftexerciseassignment.exercise_filelink = exercise.filelink) referenced ON exercise.filelink <> referenced.exercise_filelink
		//<-- this turned out to not work in all cases
		//So here comes another way:
		str_query = " SELECT exercise.filelink"
				+ " FROM exercise"
				+ " WHERE exercise.sheetdraft_filelink='"+ filelink + "'"
				+ " AND NOT EXISTS("//<--whether to get the not referenced or the referenced ones (ie.those from the subquery for the latter)  
					+ " SELECT exercise_filelink"
					+ " FROM draftexerciseassignment"
					+ " WHERE exercise.sheetdraft_filelink='" + filelink + "'"
					+ " AND draftexerciseassignment.exercise_filelink = exercise.filelink"
				+ ")"
				+ " AND exercise.filelink <> '';"
				;
		ResultSet res_exercises_not_referenced = Global.query(str_query);
		res_exercises_not_referenced.beforeFirst();
		String filelinks_of_exercises_not_referenced = "";
		String comma_or_not = "";
		//Chain all exercises that are not referenced for deletion. <-holocaust is horrible:/
		while (res_exercises_not_referenced.next()) {//On the other hand less exercises in homework makes little fox's pupils happier? :) 
			filelinks_of_exercises_not_referenced = comma_or_not + "'"
					+ res_exercises_not_referenced.getString("filelink") + "'";
			comma_or_not = ",";//using this idea we can skip one the first entry, why did I only get to it now, it's so easy!
		}
		
		res_exercises_not_referenced.last();
		int exercises_not_referenced_count = res_exercises_not_referenced.getRow();
		// tackle memory leaks by closing result set and its statement properly:
		Global.queryTidyUp(res_exercises_not_referenced);
		
		
		str_query = "DELETE FROM exercise WHERE sheetdraft_filelink = '" + filelink + "'";
		//Any exercises that are referenced by other drafts/sheetdrafts?
		if (exercises_not_referenced_count > 1) {
			//Then restrict delete action on those.
			str_query += " AND filelink IN(" + filelinks_of_exercises_not_referenced + ")";
		}
		else if (exercises_not_referenced_count > 0) {
			//Then restrict delete action on this exercise.
			str_query += " AND filelink = '" + filelinks_of_exercises_not_referenced + "'";
		}
		//Else all exercises of this sheetdraft that is being deleted can be deleted too.
		Global.query(str_query);
		
		/*ATTENTION: Eventually make all exercise assignments real where the
		 * sheetdraft to be deleted is set as originsheetdraft! ie. reference -> instance.*/
		//Fetch the sheetdraft to which the exercise has been assigned to/been referenced from.
		str_query = "SELECT draftexerciseassignment.sheetdraft_filelink, exercise_filelink"
				+ " FROM draftexerciseassignment, exercise"
				+ " WHERE exercise.sheetdraft_filelink = '" + filelink + "'"
				+ " AND exercise.filelink = draftexerciseassignment.exercise_filelink";
		//SELECT draftexerciseassignment.sheetdraft_filelink, exercise_filelink FROM draftexerciseassignment, exercise WHERE exercise.sheetdraft_filelink = 'uploads/WS_2013/Mateh/Gauss/Uebung/Blatt7.docx.docx' AND exercise.filelink = draftexerciseassignment.exercise_filelink
		
		//Iterate over all referenced exercises. (the others are already deleted at this point.)
		ResultSet res_exercises_referenced = Global.query(str_query);
		Map<String, List<String>> map_exercise_to_sheetdraftsThatReferenceThisExercise = new HashMap<String, List<String>>();
		List<String> referencingSheetdrafts = new ArrayList<String>();
		String exercise_filelink_previously = "";
		if (res_exercises_referenced.next()) {
			int exercises_referenced_count = res_exercises_referenced.getRow();
			res_exercises_referenced.beforeFirst();
			while (res_exercises_referenced.next()) {
				
				//get the filelinks from the result set:
				String exercise_filelink = res_exercises_referenced.getString("exercise_filelink");
				String sheetdraft_that_references_filelink = res_exercises_referenced.getString("sheetdraft_filelink");
				
				if (!exercise_filelink_previously.equals(exercise_filelink)) {
					//if they are not equal we have to react: store off the list, clear it
					map_exercise_to_sheetdraftsThatReferenceThisExercise.put(
							exercise_filelink_previously, new ArrayList<String>(referencingSheetdrafts) 
							);
					//now that we have copied the values over clear the helper list:
					referencingSheetdrafts = new ArrayList<String>();
				}
				
				//fill in the current entry
				referencingSheetdrafts.add(sheetdraft_that_references_filelink);
				
				//always store the previous exercise filelink:
				exercise_filelink_previously = exercise_filelink;
				
			}
			//not to forget to handle the last entry:
			res_exercises_referenced.last();
			String lastEntry_exercise_filelink = res_exercises_referenced.getString("exercise_filelink");
			if (!exercise_filelink_previously.equals(lastEntry_exercise_filelink)) {
				//then there is only 1 element in the referencingSheetdrafts list
				map_exercise_to_sheetdraftsThatReferenceThisExercise.put(
						lastEntry_exercise_filelink, new ArrayList<String>(referencingSheetdrafts) 
				);
			}
			else {
				//As this and previous exercise filelink are equal we have added to the list,
				//and the only remaining is to put this now complete list into the map.  
				map_exercise_to_sheetdraftsThatReferenceThisExercise.put(
						exercise_filelink_previously, new ArrayList<String>(referencingSheetdrafts) 
				);
			}
		}
		// tackle memory leaks by closing result set and its statement properly:
		Global.queryTidyUp(res_exercises_referenced);
		//react on that: select the best fitting, new sheetdraft owners, ie. for the
		//exercises of one and the same deleted sheetdraft the new owner should be the
		//owner of as many of those referenced exercises as possible.
		//TODO
		
		
		/*This is the short way, that interestingly was what I had created first:*/
		str_query = "UPDATE exercise, draftexerciseassignment"
				+ " SET exercise.sheetdraft_filelink = draftexerciseassignment.sheetdraft_filelink"
				+ " WHERE exercise.sheetdraft_filelink = '" + filelink + "'"
				+ " AND exercise.filelink = draftexerciseassignment.exercise_filelink";
		 /* My debug sequence: (J. R.I.B. Wein)
		  
		 //insert reference to exercise 3: (if more than one reference the same exercise,e.g.3, this is no problem as it does not matter which exercise becomes the new owner of this exercise!)
		 INSERT INTO draftexerciseassignment VALUES('uploads/WS_2013/Mateh/Gauss/Uebung/Blatt7.odt.odt', 0, 'uploads/WS_2013/Mateh/Gauss/Uebung/Blatt7.docx.docx__Exercise_3__splitby_INTDOT.docx.txt');
		 //insert reference to exercise 2: (a tex sheetdraft references)
		 INSERT INTO draftexerciseassignment VALUES('uploads/WS_2013/Mateh/Gauss/Uebung/Blatt7.tex.tex', 0, 'uploads/WS_2013/Mateh/Gauss/Uebung/Blatt7.docx.docx__Exercise_2__splitby_INTDOT.docx.txt');
	    
	     //reset exercises:
		 UPDATE exercise SET exercise.sheetdraft_filelink = 'uploads/WS_2013/Mateh/Gauss/Uebung/Blatt7.docx.docx';
		
		 //the test (if each from different draft (tex vs. odt) referenced exercise gets the correct new sheetdraft_filelink value): 
		 UPDATE exercise, draftexerciseassignment SET exercise.sheetdraft_filelink = draftexerciseassignment.sheetdraft_filelink WHERE exercise.sheetdraft_filelink = 'uploads/WS_2013/Mateh/Gauss/Uebung/Blatt7.docx.docx' AND exercise.filelink = draftexerciseassignment.exercise_filelink;
		 
		 => it works in this test! the correct sheetdraft_filelink gets written to the referenced exercise.
		 */
		//now that it's safe to use this query, let's use it:
		Global.query(str_query);
		
		
		
		
		
		
		
		
		/*
		 * AT THIS POINT THE EXERCISES THAT ARE NO LONGER REFERENCED ARE DELETED.
		 * THE SHEETDRAFT TO WHICH THE STILL REFERENCED EXERCISES BELONG TO ARE REASSIGNED.
		 * -> TODO move those in the filesystem too. 
		 */
		//1) Extract new sheetdraft filelinks: (already happend above in the failed complicated attempt)
		//2) Iterate them, 3) get the newly assigned sheetdraft_filelink and rename all exercise flavours
		//   of this referenced exercise!
		for (String exercise_filelink : map_exercise_to_sheetdraftsThatReferenceThisExercise.keySet()) {
			
			//3) Fetch the newly assigned sheetdraft filelink:
			str_query = "SELECT sheetdraft_filelink, filelink"
					+ " FROM exercise"
					+ " WHERE filelink = '" + exercise_filelink + "'"
					;
			ResultSet res_new_sheetdraft_filelink = Global.query(str_query);
			//make this (hopefully 1) sheetdraft_filelink available
			String sheetdraft_filelink = null;
			while (res_new_sheetdraft_filelink.next()) {
				sheetdraft_filelink = res_new_sheetdraft_filelink.getString("sheetdraft_filelink");
			}
			// tackle memory leaks by closing result set and its statement properly:
			Global.queryTidyUp(res_new_sheetdraft_filelink);
			
			
			//4) Rename all exercise flavours of this referenced exercise in the filesystem!
			if (sheetdraft_filelink != null) {
				String sheetdraft_directory = new File(Global.root + sheetdraft_filelink).getParentFile().getAbsolutePath();
				String destination = sheetdraft_directory + System.getProperty("file.separator")
						+ Global.extractFilename(exercise_filelink) + "." + Global.extractEnding(exercise_filelink);
				Global.renameFile(Global.root + exercise_filelink, destination);
				
				//all derivatives and flavours and each images:
				Global.renameAllDerivativesOfFilelink(exercise_filelink, sheetdraft_directory);
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Check if this was the only sheet in this semester. As semester is the highest
		//level directory in the filesystem, we then can and must delete it completely. 
		str_query = "SELECT COUNT(*) AS sheetcountofsheetdraftsemester FROM sheetdraft WHERE semester = '" + semester + "';";
		ResultSet res5 = Global.query(str_query);
		if (res5.next()) {
		    if(res5.getString("sheetcountofsheetdraftsemester").equals("0")){
		    	delete_highest_level_folder = true;
	        }
		}
		// tackle memory leaks by closing result set and its statement properly:
		Global.queryTidyUp(res5);
		
		//loesche Antraege aus der Dozent-Tabelle, if this lecturer has no more sheetdrafts
		str_query = "SELECT COUNT(*) AS row_count FROM sheetdraft WHERE lecturer_id=" + lecturer_id + ";";
		ResultSet res7 = Global.query(str_query);
		if (res7.next()) {
			if(res7.getString("row_count").equals("0")){
				
				str_query = "DELETE FROM lecturer WHERE id = " + lecturer_id + ";";
				Global.query(str_query);
			}
		}
		// tackle memory leaks by closing result set and its statement properly:
		Global.queryTidyUp(res7);
		
		
		
		//======= FILESYSTEM =====================================================//
		//loesche Blatt von der Festplatte
		File f = new File(Global.root + filelink);
		String filename_and_ending = Global.extractFilename(filelink) + "." + Global.extractEnding(filelink);
		if(Global.deleteFile(f)){
		    System.out.print(
	    		Global.addMessage("<p>Die Datei: <strong>" + filename_and_ending
	    						+ "</strong> wurde gel&ouml;scht.</p>"
	    				, "success")
    		);
		}
		else {
			System.out.print(
				Global.addMessage("<p>Die Datei: <strong>" + filename_and_ending +  "</strong> existiert nicht.</p>"
						, "nosuccess")
			);
		}
		
		//loesche Ordner mit den Aufgaben
		String exercise_dir =  "";//same directory as sheetdraft
		if (delete_highest_level_folder) {
			Global.deleteDir(
					f //Attention: There are several files/sheetdrafts within this directory!
					.getParentFile() /*above type*/
					.getParentFile() /*above lecturer*/
					.getParentFile() /*above course*/
					//.getParentFile() /*above semester =>in uploadTarget/ <--no deletion!*/
		    );
			boolean keep_database_in_synch = false;
			if (keep_database_in_synch/*truncate_database_too*/) {
				// Truncate the whole database but the lecturers too then.
				Aufgaben_DB.clearDatabase();
			}
		}
		
		
		//Try to delete all possible file derivations. Double endings ensure that
		//no other sheetdraft in the same directory is being deleted accidentially.
		Global.deleteAllDerivativesOfFilelink(filelink);
		
		
		
	}
	
	
	/**
	 * Clears the database using TRUNCATE. TODO Does SQL support it? 
	 * @throws IOException
	 */
	public static void clearDatabaseAllButLecturer() throws IOException {
		clearTable("draftexerciseassignment");
		clearTable("exercise");
		clearTable("sheetdraft");
	}
	public static void clearDatabase() throws IOException {
		// Truncate the whole database but the lecturers too then.
		clearDatabaseAllButLecturer();
		clearTable("lecturer");
	}
	public static void clearTable(String table) throws IOException {
		String sql = "TRUNCATE `" + table + "`";//DELETE FROM 
		Global.query(sql);
	}
	
	
	
	
	
	
	//======= ON THE FLY LOADING METHODS ==================================//
	/**
	 * 
	 */
//	public static Sheetdraft[] getAllDraftsOf(String user) {
//		
//		
//		
//	}
	
	
	
	
	
	
	

	//======= HELPER ======================================================//
//	public static Sheetdraft getSheetdraftById(String sheetdraft_id) {
//		if (allSheetdrafts.get(sheetdraft_id) == null) {
//			return null;
//		}
//		return allSheetdrafts.get(sheetdraft_id);
//	}
	public static Sheetdraft getSheetdraftByFilelink(String sheetdraft_filelink) {
		if (allSheetdrafts.get(sheetdraft_filelink) == null) {
			return null;
		}
		return allSheetdrafts.get(sheetdraft_filelink);
	}
	

	public static Map<String, Sheetdraft> getLoadedSheetdrafts() {
		return allSheetdrafts;
	}
	public static Map<String, Sheetdraft> getLoadedDraftsOnly() {
		return drafts;
	}
	
	public static Sheetdraft getLatestChangedDraft() {
		return latestChangedDraft;
	}
	
	
	
	
}
