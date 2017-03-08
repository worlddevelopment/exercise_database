<%@page import="aufgaben_db.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File, java.net.URLDecoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%


String semester_old = "";
String type_old = "";// All the following implies that the filelink has to be moved too.
String course_old = "";// The path to the files of this sheet will change on these changed too!
String lecturer_old = "";
int lecturer_old_id = -1;

String semester_new = "";
String type_new = "";// All the following implies that the filelink has to be moved too.
String course_new = "";// The path to the files of this sheet will change on these changed too!
String lecturer_new = "";
int lecturer_new_id = -1;



// Globally editable parameters: i.e. those that can affect multitudes of files, mostly directories only!

// The parameters given per GET/POST request.
semester_old = request.getParameter("semester_old");
course_old = request.getParameter("course_old");
lecturer_old = Global.decodeUmlauts(request.getParameter("lecturer_old"));
type_old = request.getParameter("type_old");

semester_new = request.getParameter("semester");
course_new = request.getParameter("course");
lecturer_new = Global.decodeUmlauts(request.getParameter("lecturer"));
type_new = request.getParameter("type");
if (semester_new == null) {
	semester_new = request.getParameter("semester_new");
}
if (course_new == null) {
	course_new = request.getParameter("course_new");
}
if (type_new == null) {
	type_new = request.getParameter("type_new");
}
if (lecturer_new == null) {
	lecturer_new = request.getParameter("lecturer_new");
}

HashMap<String, String> hm = new HashMap<String, String>();
boolean hasAnyParameterBeenGloballyChanged = false;

// -------UPDATE SEMESTER ------
// Aendere wenn Eintrag in der DB ex und neuer Semester ist unterschiedlich von altem.
// Anderfalls fuege neuen Eintarg in Semeseter Tabelle. jrib: Keine extra-Tabelle mehr noetig.
if (semester_new != null /*&& !semester_new.equals(sheetdraft.getSemester())*/) {
	hm.put("semester", semester_new);
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET semester='" + semester_new
			+ "' WHERE semester='" + semester_old + "' ");
	System.out.print(Global.addMessage("Semester erneuert.", " success "));
	hasAnyParameterBeenGloballyChanged = true;
	hm.clear();
}

// -------UPDATE COURSE ------
// Aendere wenn Eintrag in der DB ex und neuer Veranstaltung ist unterschiedlich von altem.
// Anderfalls fuege neuen Eintarg in Veranstaltung Tabelle
if (course_new != null && course_old != null && !course_new.equals(course_old)) {
	hm.put("course", course_new);
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET course ='" + course_new
			+ "' WHERE course='" + course_old + "' ");
	System.out.print(Global.addMessage("Veranstaltung wurde erneuert.", " success "));
	hasAnyParameterBeenGloballyChanged = true;
	hm.clear();
}


// -------UPDATE LECTURER ------
// Aendere wenn Eintrag in der DB ex und neuer Dozent ist unterschiedlich von altem.
// Anderfalls fuege neuen Eintarg in Dozent Tabelle
if (lecturer_new != null && lecturer_old != null && !lecturer_new.equals(lecturer_old)) {
	hm.put("lecturer", lecturer_new);
	// Lecturer not exists?
	if (!Global.sqlm.exist("lecturer", "lecturer", hm)) {
		Global.sqlm.executeUpdate("INSERT INTO lecturer(lecturer) VALUES('" + lecturer_new + "');");
		System.out.print(Global.addMessage("Neuer Dozent eingefuegt.", "success"));
	}

	// Figure out id:
	if (lecturer_new != "" && lecturer_old != "") {// TODO Use N.N. as placeholder instead? Or ""?
		lecturer_old_id = Global.sqlm.getId("lecturer", "lecturer", lecturer_old);
		lecturer_new_id = Global.sqlm.getId("lecturer", "lecturer", lecturer_new);
	}
	//else nothing to change

	// Now the lecturer id is available?
	if (lecturer_new_id != -1 && lecturer_old_id != -1 && lecturer_new_id != lecturer_old_id
			//lecturer_id != null   && !lecturer_id.equals(sheetdraft.getLecturerId())
			) {
		Global.sqlm.executeUpdate("UPDATE sheetdraft SET lecturer_id='" + lecturer_new_id
				+ "' WHERE lecturer_id=" + lecturer_old_id + " ");
		System.out.print(Global.addMessage("Dozent zum Blatt erneuert.", " success "));
		hasAnyParameterBeenGloballyChanged = true;
	}


	hm.clear();
}


// -------UPDATE TYPE ------
if (type_new != null && type_old != null && !type_new.equals(type_old)) {
	hm.put("type", type_new);
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET `type`='" + type_new
			+ "' WHERE `type`='" + type_old + "' ");
	System.out.print(Global.addMessage("Typ geandert.", " success "));
	hasAnyParameterBeenGloballyChanged = true;
	hm.clear();

}



// -------RENAME FILES - DATEIEN VERSCHIEBEN-------
String target_old = Global.uploadTarget
		+ Global.encodeUmlauts(semester_old) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(course_old) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(lecturer_old) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(type_old) + System.getProperty("file.separator");

String target_new = Global.uploadTarget
		+ Global.encodeUmlauts(semester_new) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(course_new) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(lecturer_new) + System.getProperty("file.separator")
		+ Global.encodeUmlauts(type_new) + System.getProperty("file.separator");









//boolean isChangeGlobalAndNotForThisSheetOnly = false; //<-- then whole directories can be moved instead of individual files.
//if (request.getParameter("global") != null) {
//	isChangeGlobalAndNotForThisSheetOnly = true;
//}
//if (isChangeGlobalAndNotForThisSheetOnly) {
if (hasAnyParameterBeenGloballyChanged) {


	// Move complete directory containing all exercises, images, ...
	// Only possible if the lecturer, semester or type changed globally. As other sheets may be in there too!
	if (Global.moveDir(Global.root + target_old, Global.root + target_new, ""))  {
		// verschiebe Ordner mit allen Blaettern und Aufgaben. (as of v31.14):
		System.out.println(
				Global.addMessage("Ordner " + Global.root + target_old + " erfolgreich verschoben nach: " + Global.root + target_new
						, " success ", request, pageContext)
		);
	}
	System.out.print(Global.addMessage("All sheets affected by the global Type, Course, Semester or Lecturer renaming moved."
			+ " | Alle vom global ge&auml;nderten Typ, Kurs, Semester oder Dozent betroffenen Blatt verschoben.", " success "));

	// Update filelinks now that we are still alive after the filesystem renaming venture:
	Global.sqlm.executeUpdate("UPDATE sheetdraft"
			+ " SET filelink = REPLACE(filelink, '" + target_old + "', '" + target_new + "')"
			+ " WHERE filelink LIKE '" + target_old + "%'"
	);
	Global.sqlm.executeUpdate("UPDATE draftexerciseassignment"
			+ " SET sheetdraft_filelink = REPLACE(sheetdraft_filelink, '" + target_old + "', '" + target_new + "')"
			+ " WHERE sheetdraft_filelink LIKE '" + target_old + "%'"
	);
	Global.sqlm.executeUpdate("UPDATE exercise" //<-- done here now for consistency
			+ " SET sheetdraft_filelink = REPLACE(sheetdraft_filelink, '" + target_old + "', '" + target_new + "')"
			+ " WHERE sheetdraft_filelink LIKE '" + target_old + "%'"
	);

	Global.sqlm.executeUpdate("UPDATE exercise"
			+ " SET filelink = REPLACE(filelink, '" + target_old + "', '" + target_new + "')"
			+ " WHERE filelink LIKE '" + target_old + "%'"
	);
	Global.sqlm.executeUpdate("UPDATE draftexerciseassignment"
			+ " SET exercise_filelink = REPLACE(exercise_filelink, '" + target_old + "', '" + target_new + "')"
			+ " WHERE exercise_filelink LIKE '" + target_old + "%'"
	);

}

