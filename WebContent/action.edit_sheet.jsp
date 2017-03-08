<%@page import="aufgaben_db.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File, java.net.URLDecoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%
//String filelink = "";<-- changing this is not a permitted operation.

String type_new = "";//<-- All the following implies that the filelink has to be moved too.
String course_new = "";//The path to the files of this sheet will change on these changed too!
String semester_new = "";
String lecturer_new = "";
int lecturer_id_new = -1;

String description_new = "";


//key = FormFieldName, value =  FormFieldValue
HashMap<String, String> form_data = new HashMap<String, String>();

// UNCOMMENT IF NO FILE IS BEING SENT FOR REPLACEMENT!
// THIS WOULD ANYWAY RENDER EQUIVALENT TO UPLOADING A NEW ONE. TODO check if useful!
form_data.put("semester", request.getParameter("semester"));
form_data.put("course", request.getParameter("course"));
form_data.put("lecturer", request.getParameter("lecturer"));
form_data.put("lectuer_id", request.getParameter("lecturer_id"));//if the id is set,then it's an existing lecturer, else it could be a new one.
form_data.put("type", request.getParameter("type"));

form_data.put("description", request.getParameter("description"));


/* UNCOMMENT IF FILE IS ALSO TO BE REPLACED DURING THE UPDATE PROCESS!
// To get the content type information from JSP Request Header
FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);

try {
	List<?> items = upload.parseRequest(request);
	Iterator<?> iterator = items.iterator();


	FileItem fileItem = null;
	String ext;
	String filename = "";
	String message = "";
	// Another form field transmitted? hopefully at least all required ones: course, lecturer, file
	while (iterator.hasNext()) {

		FileItem item = (FileItem) iterator.next();
		// Preprocess items
		if (item.isFormField()) {

			String name = item.getFieldName();
			String value = item.getString();
			value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
			//value = Global.encodeUmlauts(value);
			form_data.put(name, value);
			message += "name = " + name + " => ";
			message += " value = " + form_data.get(name) + "</br>\n\r";
			//dir += form_data.get(name) + "\\";

			continue; // the same as if an else statement was to follow

		}
		//else
		// ITEM IS A FILE !
		ext = Global.extractEndingPerReverse(item.getName());
		// Build all data containing filename
		filename = Global.encodeUmlauts(item.getName()) + "." + ext;
		// Results in a double ending. That is required for always knowing the filetype of the original file.
		//Global.addMessage("Uploaded-Filename = " + filename, "info");

		//Store the fileitem for later saving as filesystem FILE.
		fileItem = item;

	} // <-- Here's the long looked for end of while. -END
	//Global.addMessage(message, "info");

} catch (FileUploadException e) {
	e.printStackTrace();
} catch (Exception e) {
	e.printStackTrace();
}
*/



// The parameters given per GET/POST request.
//filelink = request.getParameter("filelink");
semester_new = form_data.get("semester");
course_new = form_data.get("course");
lecturer_new = Global.decodeUmlauts(form_data.get("lecturer"));
lecturer_id_new = Global.getInt(form_data.get("lecturer_id"));
type_new = form_data.get("type");


description_new = form_data.get("description");//URLDecoder.decode(request.getParameter("description"));

// The identifier for the sheetdraft.
String sheetdraft_filelink = request.getParameter("filelink");//wird im hidden feld uebrgeben
//String sheetdraft_filelink =  URLDecoder.decode(request.getParameter("filelink"));

// TODO clarify if this is a good idea or not? Is the following too risky?
//Sheetdraft sheetdraft = Aufgaben_DB.getSheetdraftByFilelink(sheetdraft_filelink);


HashMap<String, String> hm = new HashMap<String, String>();
boolean verschieben = false;
boolean rename = false;


// -------UPDATE SEMESTER ------
// Aendere wenn Eintrag in der DB ex und neuer Semester ist unterschiedlich von altem.
// Anderfalls fuege neuen Eintarg in Semeseter Tabelle. jrib: Keine extra-Tabelle mehr noetig.
if (semester_new != null /*&& !semester_new.equals(sheetdraft.getSemester())*/) {
	hm.put("semester", semester_new);
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET semester='" + semester_new
			+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
	System.out.println(Global.addMessage("Semester erneuert.", " success "));
	verschieben = true;
	hm.clear();
}

// -------UPDATE COURSE ------
// Aendere wenn Eintrag in der DB ex und neuer Veranstaltung ist unterschiedlich von altem.
// Anderfalls fuege neuen Eintarg in Veranstaltung Tabelle
if (course_new != null /*&& !course_new.equals(sheetdraft.getCourse())*/) {
	hm.put("course", course_new);
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET course ='" + course_new
i			+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
	System.out.println(Global.addMessage("Veranstaltung wurde erneuert.", " success "));
	verschieben = true;
	hm.clear();
}


// -------UPDATE LECTURER ------
// Aendere wenn Eintrag in der DB ex und neuer Dozent ist unterschiedlich von altem.
// Anderfalls fuege neuen Eintarg in Dozent Tabelle
if (lecturer_new != null || lecturer_id_new > -1) {
	hm.put("lecturer", lecturer_new);

	// Lecturer exists in database?
	if (!Global.sqlm.exist("lecturer", "lecturer", hm)) {
		Global.sqlm.executeUpdate("INSERT INTO lecturer(id, lecturer) VALUES("
		+ (int)Math.round(Math.random() * Integer.MAX_VALUE) +", '" + lecturer_new + "');");
		System.out.println(Global.addMessage("Neuer Dozent eingefuegt.", "success"));
	}

	if (lecturer_new != "") {
		lecturer_id_new = Global.sqlm.getId("lecturer", "lecturer", lecturer_new);
	}
	//else use old one (nothing to change)

	// Now the lecturer's id is available and has changed?
	if (lecturer_id_new != -1 /*&& lecturer_new_id != sheetdraft.getLecturerId()*/) {

		Global.sqlm.executeUpdate("UPDATE sheetdraft SET lecturer_id=" + lecturer_id_new
				+ " WHERE filelink='" + sheetdraft_filelink + "' ");
		System.out.println("\n" + Global.addMessage("Dozent zum Blatt " + sheetdraft_filelink + " erneuert: " + lecturer_new, " success "));

		verschieben = true;
	}

	hm.clear();
}


// -------UPDATE TYPE ------
if (type_new != null /*&& !type_new.equals(sheetdraft.getType())*/) {
	hm.put("type", type_new);
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET type='" + type_new
			+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
	System.out.println(Global.addMessage("Typ geandert.", " success "));
	hm.clear();
}

// -------UPDATE DESCRIPTION ------
if (description_new != null /*&& !description_new.equals(sheetdraft.getDescription())*/) {
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET description='" + description_new
			+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
	System.out.println(Global.addMessage("Beschreibung geandert.", " success "));
}


// -------RENAME FILES - DATEIEN VERSCHIEBEN-------
String filelink_target_new = Global.uploadTarget
		+ Global.encodeUmlauts(semester_new) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(course_new) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(lecturer_new) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(type_new) + System.getProperty("file.separator");

String filelink_new = filelink_target_new + Global.extractFilename(sheetdraft_filelink)
		//+ "." + Global.extractEndingDouble(sheetdraft_filelink)
		+ "." + Global.extractEnding(sheetdraft_filelink)//<--now we fetch both at once
;

/*
// Modify sheet name? Has to be propagated to all derived files and exercises.
if (!filelink.equals(sheetdraft.getFilelink())) {

	rename = true;
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET filelink='" + filelink
			+ "' WHERE filelink='" + sheetdraft_filelink + "' ");

	out.println(Global.addMessage("Sheet Filelink erneuert.", " success "));
}
*/

// -------ACTION:MOVE-------
if ((verschieben || rename) && !filelink_new.equals(sheetdraft_filelink)) {
	/*
	ATTENTION:
	Here for each file in this dir replace old filename by new filename would be required!
	Global.renameFile(Global.root + sheetdraft.getImageLinkPrevious(), Global.root
			+ sheetdraft.getImageLink()); //rename Name von Bild des Blattes
	out.println(Global.addMessage("All files renamed. | Alle Dateien umbenannt.", " success "));
	*/

	// Create the target directories if they don't exist already:
	File target_directory_file_new = new File(Global.root + filelink_target_new);
	if (!target_directory_file_new/*.getParentFile()*/.exists()) {
		target_directory_file_new/*.getParentFile()*/.mkdirs(); //erstelle die Zielordner
	}


	// Rename/move the filesystem file.
	String absolute_filelink = sheetdraft_filelink;//sheetdraft.getFilelink();
	if (!absolute_filelink.startsWith(System.getProperty("file.separator"))) {
		absolute_filelink = Global.root + absolute_filelink;
	}
	Global.renameFile(absolute_filelink, Global.root + filelink_new);

	// Then update stored filelink (the main, initial e.g. uploaded one).
	// Herein the old filelink gets stored as previous filelink, too!
	//sheetdraft.setFilelink(filelink_new);

	// Rename sheet:
	String r = Global.root;
//	Global.renameFile(Global.root + sheetdraft.getFilelinkPrevious()
//			, Global.root + sheetdraft.getFilelink());
	// Image:
	if (new File(r + Global.getImageLinkFromFile(sheetdraft_filelink)).exists()) {
		Global.renameFile(
			r + Global.getImageLinkFromFile(sheetdraft_filelink),//sheetdraft.getImageLinkPrevious(),
			r + Global.getImageLinkFromFile(filelink_new)//.getImageLink());
		);
	}
	// TXT too:
	if (new File(r + Global.replaceEnding(sheetdraft_filelink, "txt")).exists()) {
		Global.renameFile(
			r + Global.replaceEnding(sheetdraft_filelink, "txt"),
			r + Global.replaceEnding(filelink_new, "txt")
		);
	}
	// PDF too:
	if (new File(r + Global.replaceEnding(sheetdraft_filelink, "pdf")).exists()) {
		Global.renameFile(
			r + Global.replaceEnding(sheetdraft_filelink, "pdf"),//sheetdraft.getPDFLinkPrevious(),
			r + Global.replaceEnding(filelink_new, "pdf")//sheetdraft.getPDFLink()
		);
	}
	if (new File(r + Global.replaceEnding(sheetdraft_filelink, "pdf--tmp--pdf-creation")).exists()) {
		Global.renameFile(
			r + Global.replaceEnding(sheetdraft_filelink, "pdf--tmp--pdf-creation"),//sheetdraft.getPDFLinkPrevious(),
			r + Global.replaceEnding(filelink_new, "pdf--tmp--pdf-creation")
		);//sheetdraft.getPDFLink());
	}

	// COMMON FORMAT: currently HTML.
	String t = Aufgaben_DB.commonFormat.name().toLowerCase();
	String existing_filelink = r + Global.replaceEnding(sheetdraft_filelink, t);
	if (new File(existing_filelink).exists()) {
		Global.renameFile(
			existing_filelink,
			r + Global.replaceEnding(filelink_new, t)
		);
	}

	existing_filelink = r + Global.replaceEnding(sheetdraft_filelink, t + "." + Global.imageTypeToGenerate);
	// Rename potentially existing image?
	if (new File(existing_filelink).exists()) {
		Global.renameFile(
			existing_filelink,
			r + Global.replaceEnding(filelink_new, t + "." + Global.imageTypeToGenerate)
		);
	}

	String source_filelink = Global.replaceEnding(sheetdraft_filelink, "pictures");
	File html_picture_folder = new File(r + source_filelink);
	if (html_picture_folder.exists()) {
		// Then move it too:
		String target_filelink = Global.replaceEnding(filelink_new, "pictures");
		if (Global.moveDir(html_picture_folder.getAbsolutePath(), r + target_filelink, "")) {
			System.out.println(
				Global.addMessage(
					"Ordner " + source_filelink + " erfolgreich verschoben nach: " + target_filelink
					," success "
					, request
					, pageContext
				)
			);
			}
		}

		System.out.println(
			Global.addMessage(
				"Moved file " + sheetdraft_filelink + " et alia (pdf, " + Global.imageTypeToGenerate + ")."
					+ " New location: " + filelink_new
				," success "
				, request
				, pageContext
			)
		);


		Global.sqlm.executeUpdate("UPDATE sheetdraft"
				+ " SET filelink = '" + filelink_new + "'"
				+ " WHERE filelink = '" + sheetdraft_filelink + "'"
		);
		Global.sqlm.executeUpdate("UPDATE draftexerciseassignment"
				+ " SET sheetdraft_filelink = '" + filelink_new + "'"
				+ " WHERE sheetdraft_filelink = '" + sheetdraft_filelink + "'"
		);
		Global.sqlm.executeUpdate("UPDATE exercise" //<-- done here now for consistency
				+ " SET sheetdraft_filelink = '" + filelink_new + "'"
				+ " WHERE sheetdraft_filelink = '" + sheetdraft_filelink + "'"
		);

		// Verschiebe auch die Aufgaben des Uebungsblatts.
		// Also move the exercises of the sheet.
		// TODO debug: sheetdraft.synchronizeExercisesLocationToFilelink();

		// To know the exercises' old filelinks for building the new ones:
		ResultSet res = Global.query("SELECT exercise.filelink FROM exercise"
				  + " WHERE exercise.sheetdraft_filelink = '" + filelink_new + "'");
				  // <--depends on whether exercise mod comes before or after this.
		while (res.next()) {
			String exercise_filelink = res.getString("filelink");
			// Build the new filelink to this exercise
			String exercise_filelink_new = filelink_target_new
					+ Global.extractFilename(exercise_filelink)
					+ "." + Global.extractEnding(exercise_filelink);
					//TODO 1st of double ext contained in filename?

			Global.renameFile(r + exercise_filelink,  r + exercise_filelink_new);
			// Image link:
			if (new File(r + Global.replaceEnding(exercise_filelink, Global.imageTypeToGenerate)).exists()) {
				Global.renameFile(
					r + Global.replaceEnding(exercise_filelink, Global.imageTypeToGenerate),
					r + Global.replaceEnding(exercise_filelink, Global.imageTypeToGenerate)
				);
			}
			if (new File(r + Global.getImageLinkFromFile(exercise_filelink)).exists()) {
				Global.renameFile(
					r + Global.getImageLinkFromFile(exercise_filelink),
					r + Global.getImageLinkFromFile(exercise_filelink_new)
				);
			}
			// TXT too:
			if (new File(r + Global.replaceEnding(exercise_filelink, "txt")).exists()) {
				Global.renameFile(
					r + Global.replaceEnding(exercise_filelink, "txt"),
					r + Global.replaceEnding(exercise_filelink_new, "txt")
				);
			}
			if (new File(r + Global.replaceEnding(exercise_filelink, "txt." + Global.imageTypeToGenerate)).exists()) {
				Global.renameFile(
					r + Global.replaceEnding(exercise_filelink, "txt." + Global.imageTypeToGenerate),
					r + Global.replaceEnding(exercise_filelink_new, "txt." + Global.imageTypeToGenerate)
				);
			}
			// PDF too:
			if (new File(r + Global.replaceEnding(exercise_filelink, "pdf")).exists()) {
				Global.renameFile(
					r + Global.replaceEnding(exercise_filelink, "pdf"),//sheetdraft.getPDFLinkPrevious(),
					r + Global.replaceEnding(exercise_filelink_new, "pdf")//sheetdraft.getPDFLink());
				);
			}
			if (new File(r + Global.replaceEnding(exercise_filelink, "pdf--tmp--pdf-creation")).exists()) {
				Global.renameFile(
					r + Global.replaceEnding(exercise_filelink, "pdf--tmp--pdf-creation"),
					r + Global.replaceEnding(exercise_filelink_new, "pdf--tmp--pdf-creation")
				);
			}
			// COMMON FORMAT: currently HTML.
			String t =  Aufgaben_DB.commonFormat.name().toLowerCase();
			if (new File(r + Global.replaceEnding(exercise_filelink, t)).exists()) {
				Global.renameFile(
					r + Global.replaceEnding(exercise_filelink, t),
					r + Global.replaceEnding(exercise_filelink_new, t)
				);
			}
			if (new File(r + Global.replaceEnding(exercise_filelink, t + "." + Global.imageTypeToGenerate)).exists()) {
				Global.renameFile(
					r + Global.replaceEnding(exercise_filelink, t + "." + Global.imageTypeToGenerate),
					r + Global.replaceEnding(exercise_filelink_new, t + "." + Global.imageTypeToGenerate)
				);
			}

			Global.sqlm.executeUpdate("UPDATE exercise"
					+ " SET filelink='" + exercise_filelink_new + "'"
					+ " WHERE filelink = '" + exercise_filelink + "'"
			);
			Global.sqlm.executeUpdate("UPDATE draftexerciseassignment"
					+ " SET exercise_filelink = '" + exercise_filelink_new + "'"
					+ " WHERE exercise_filelink = '" + exercise_filelink + "'"
			);

		}
		// tackle memory leaks by closing result set and its statement properly:
		Global.queryTidyUp(res);

//		Global.sqlm.executeUpdate("UPDATE exercise" <-- done before changing the exercises' files' location for safety!
//				 + " SET sheetdraft_filelink = '" + filelink_new + "'"
//				 + " WHERE sheetdraft_filelink = '" + sheetdraft_filelink + "'"
//		);

		System.out.println(Global.addMessage(Global.display("exercises moved"), " success "));

	}



	Global.redirectTo("start");


%>
<!-- Update Index of search engine -->
<%@include file="action.update_index.jsp" %>

