package aufgaben_db;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.util.ZipInputStreamZipEntrySource;

import com.ibm.icu.util.Calendar;

import HauptProgramm.DocType;
import Verwaltung.HashLog;




public class Aufgaben_DB {
	
	
	//======= ATTRIBUTES ==================================================//
	private static Map<String, Sheetdraft> allSheetdrafts = new HashMap<String, Sheetdraft>();
	private static Map<String, Sheetdraft> drafts = new HashMap<String, Sheetdraft>(); 
	private static Sheetdraft latestChangedDraft;
	
	
	//======= METHODS =====================================================//
	/**
	 * Erstellt aus dem übergebenen Uebungsballt die einzlenen Aufgaben und gibt sie innerhalb
	 * des Sheetdrafts-Objktes wieder aus. Gibt je nach InputFile nicht nur Sheetdraft
	 * -Objekte, sondern auch Sheetdraft oder RtfSheet aus. <br />
	 * <br />
	 * Achtung: Diese Methode schreibt (außer bei rtf-Dateien) noch keine Dateien auf die Festplatte! Rufe dazu in der Aufrufenden 
	 * Methode die jeweiligen toString-Methoden der zurückgegebenen -sheet Objekte auf! <br />
	 * <br />
	 * Achtung: Bei einem filePath der auf ein .tex-file zeigt, geht die Methode davon aus, dass im selben Ordner ein .pdf mit sonst dem
	 * selben Namen (!) liegt, welches das kompilierte .tex-file darstellt. Dies wird zum finden der Aufgaben-heads benötigt,
	 * da es sehr schwer ist, aus einem bloßen .tex File aufgrund von unterschiedlichen Styles der Ersteller, Aufgabendeklarationen
	 * zu finden und damit Aufgaben herauszuschneiden.
	 * 
	 * 
	 * @param filePath Dateipfad der zu untersuchenden Datei. Soforn diese Datei nicht im selben Ordner liegt, in dem das 
	 * Programm ausgefuehrt wird, muss der Dateipfad absoult sein.
	 * @param root
	 * @param target Target 
	 * @return Sheetdraft Objekt
	 * @throws FileNotFoundException Falls keine File mit dem angegebenen filePath existiert. <br>
	 * Bei LatexFiles: Auch, falls keine .pdf-Datei mit dem angegebenen Filenamen (und der Endung .pdf anstatt .tex) existiert.
	 * @throws IOException Falls Fehler beim Lesen und Schreiben auftreten.
	 * @throws SQLException 
	 */
	public static Sheetdraft processUploadedSheetdraft(String filelink)
			throws FileNotFoundException, IOException, SQLException {
		HashLog.initialisiereLogFile();
		
		//--superfluous too-----------------------------------------------------
		// File-Endung wird mit Hilfe der enum DocType ueberprueft und gesetzt
		DocType sheetType;
		sheetType = DocType.getByEnding(Global.extractEnding(filelink));
//			for(DocType type : DocType.values()) {
//				if (filePath.endsWith(type.getCode())) {
//					sheetType = type;
//					break; //JRIBW: added
//				}
//			}
		if (sheetType == null) {
			throw new FileNotFoundException();//JRIBW: shouldn't it be a filetype not supported exception?
		}
		System.out.println("Es handelt sich um ein " + sheetType + "-Dokument");
		//--superfluous too-----------------------------------------------------
		
		
		
		System.out.println(filelink);
		Sheetdraft sheetdraft = new Sheetdraft(filelink);
		
		//Here follow the methods for dealing with the original files,
		//the filetype or content is being converted, the exercises are cut out. 
		//1) plain text / raw content
		sheetdraft.extractPlainText();
		//2) 
		sheetdraft.setDeclarationSet(DeclarationFinder.findeDeklarationen(sheetdraft));
		System.out.println("Deklarationen wurden erstellt.");
		//3)
		sheetdraft.extractExercises();
		System.out.println("Einzelaufgaben wurden erstellt.");
		//4) synchronize filesystem/harddrive
		sheetdraft.writeExercisesToHarddisk();
		
		return sheetdraft;
		
	}
		
	public static void loadAllSheetdrafts() throws SQLException, IOException { /*and hence also the exercises*/
		loadAllSheetdrafts((String)Global.session.getAttribute("user"), false);
	}
	
	public static void loadAllSheetdrafts(boolean includeSheetsWhereUserIsLecturer) throws SQLException, IOException { /*and hence also the exercises*/
		loadAllSheetdrafts((String)Global.session.getAttribute("user"), includeSheetsWhereUserIsLecturer);
	}
	
	public static void loadAllSheetdrafts(String user, boolean includeSheetsWhereUserIsLecturer)
			throws SQLException, IOException { /*and hence also the exercises*/
		
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
		ResultSet res = Global.query(query);
		
		//The last changed one is the active draft!
		latestChangedDraft = new Sheetdraft();
		int i = -1;
//		int drafts_i = -1;
		while (res.next()) {
			++i;
			String filelink_debug = res.getString("filelink");
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
			if (Integer.valueOf(res.getString("is_draft")) == 1) {
//				++drafts_i;
				drafts.put(
						//res.getString("id"),
						res.getString("filelink"),
						allSheetdrafts.get(res.getString("filelink"))
				);
				//determine if this is the new latest changed draft
				Calendar calFromDb = Calendar.getInstance();
				calFromDb.setTimeInMillis(res.getLong("whenchanged"));
				if (latestChangedDraft == null ||
//						Integer.valueOf(res.getString("whenchanged"))
//						> Integer.valueOf(latestChangedDraft.getWhenchanged()) ) {
						calFromDb.after(latestChangedDraft.getWhenchanged())
						) {
					//this draft has been changed more recently 
					latestChangedDraft = allSheetdrafts.get(i);
				}
			}
		}
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
	
	/**
	 * @throws IOException 
	 * @throws ZipException 
	 * 
	 */
	public static String[] extractRawContentDependingOnFiletype(String filelink)
			throws ZipException, IOException {
		
		String[] rawDocumentContentOfFileArchive = null;
		File file = new File(filelink);
		String ending = Global.extractEnding(filelink);
		if (ending.equals("docx")) {
			//
			String entry = "word/document.xml";
			ZipFile docx = new ZipFile(file);
//			IOUtils.copy(docx.getInputStream(entry),);
			Global.addMessage("docx-Contents:\r\n<br />" + renderZipContents(docx)
					, "info");
//			ZipEntry docxXML = docx.getEntry(entry);
			
			//GET INPUT STREAM
			boolean entry_found = false;
			ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
			for (ZipEntry e; (e = zin.getNextEntry()) != null;) {
				//zin.getNext moves pointer of InputStream to the next entry.
				if (e.getName().equals(entry)) {
					//now we have it at the correct position in the stream: the hydro plant ..
					entry_found = true;
					break;
				}
			}
			//We have input stream here, if an entry has been found.
			if (!entry_found) {
				rawDocumentContentOfFileArchive
				= ReadWrite.readInputStreamAsLines(Global.getInputStream(file, entry));
			}
		    rawDocumentContentOfFileArchive = ReadWrite.readInputStreamAsLines(zin);
		}
		return rawDocumentContentOfFileArchive;
	}
	
	
	
	
	//RENDER DOCX CONTENTS
	public static String renderDocXContents(String filelink)
			throws IOException {
		return renderZipContents(new ZipFile(filelink));
	}
	public static String renderZipContents(ZipFile docx) {
		String zipContents = "";
		Enumeration<? extends ZipEntry> entriesIter = docx.entries();
		while (entriesIter.hasMoreElements()) {
			ZipEntry zipEntry = entriesIter.nextElement();
			if (zipEntry.getName().equals("word/document.xml")) {
				Global.addMessage("renderZipContents: document.xml found!","success");
			}
			zipContents += zipEntry.getName() + "\r\n<br />";
		}
		return zipContents;
	}
	
	

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
