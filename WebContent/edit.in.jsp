
<%@page import="aufgaben_db.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="swp.MysqlHelper"%>
<%@page import="swp.SQL_Methods"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>


<%
	String filelink = "";
	String type = "";
	String course = "";
	String semester = "";
	String lecturer = "";
	String lecturer_id = "";
	String ext = "";
	String description = "";

	filelink = request.getParameter("filelink");
	semester = request.getParameter("semester");
	course = request.getParameter("course");
	lecturer = request.getParameter("lecturer");
	type = request.getParameter("type");
	description = request.getParameter("description");

	String sheetdraft_filelink = request.getParameter("filelink");//wird im hidden feld uebrgeben
	Sheetdraft sheetdraft = Aufgaben_DB.getSheetdraftByFilelink(sheetdraft_filelink);

	
	HashMap<String, String> hm = new HashMap<String, String>();
	hm.put("semester", semester);
	boolean verschieben = false;
	boolean rename = false;

	//-------UPDATE SEMESTER ------
	//aendere wenn Eintrag in der DB ex und neuer Semester ist unterschiedlich von altem.
	//anderfalls fuege neuen Eintarg in Semeseter Tabelle
	Global.sqlm.executeUpdate("UPDATE sheetdraft SET semester='" + semester
		+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
	out.print(Global.addMessage("Semester erneuert.", " success "));
	verschieben = true;
	hm.clear();
	

	//-------UPDATE COURSE ------
	//aendere wenn Eintrag in der DB ex und neuer Veranstaltung ist unterschiedlich von altem.
	//anderfalls fuege neuen Eintarg in Veranstaltung Tabelle
	hm.put("course", course);
	Global.sqlm.executeUpdate("UPDATE blatt SET course ='" + course
	+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
	out.print(Global.addMessage("Veranstaltung wurde erneuert.", " success "));
	verschieben = true;
	hm.clear();

	
	//-------UPDATE LECTURER ------
	//aendere wenn Eintrag in der DB ex und neuer Dozent ist unterschiedlich von altem.
	//anderfalls fuege neuen Eintarg in Dozent Tabelle
	hm.put("lecturer", lecturer);
	if (!Global.sqlm.exist("lecturer", "lecturer", hm)) {
		Global.sqlm.executeUpdate("INSERT INTO lecturer(lecturer) VALUES('" + lecturer + "');");
		out.print(Global.addMessage("Neuer Dozent eingefügt.", "success"));
		verschieben = true;
		lecturer_id = Global.sqlm.getId("lecturer", "lecturer", lecturer);
		Global.sqlm.executeUpdate("UPDATE sheetdraft SET lecturer_id='" + lecturer_id
		+ "' WHERE filelink=" + sheetdraft_filelink + " ");
	} else if (lecturer_id != null
			&& !lecturer_id.equals(sheetdraft.getLecturerId())) {
		lecturer_id = Global.sqlm.getId("lecturer", "lecturer", lecturer);
		Global.sqlm.executeUpdate("UPDATE sheetdraft SET lecturer_id='" + lecturer_id
		+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
		out.print(Global.addMessage("Dozent zum Blatt erneuert.", " success "));
		verschieben = true;
	} //else nothing to change
	hm.clear();

	
	//-------UPDATE TYPE ------
	hm.put("type", type);
	if (type != null && !type.equals(sheetdraft.getType())) {
		Global.sqlm.executeUpdate("UPDATE sheetdraft SET type='" + type
		+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
		out.print(Global.addMessage("Typ geandert.", " success "));
	}
	hm.clear();

	//-------UPDATE DESCRIPTION ------
	if (!description.equals(sheetdraft.getDescription())) {
		Global.sqlm.executeUpdate("UPDATE sheetdraft SET description='" + description
		+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
		out.print(Global.addMessage("Beschreibung geandert.", " success "));
	}
	
	
	//-------RENAME FILES - DATEIEN VERSCHIEBEN-------
	String filelink_target_new = "uploads/"
	+ Global.encodeUmlauts(semester) + "/"
	+ Global.encodeUmlauts(course) + "/"
	+ Global.encodeUmlauts(lecturer) + "/"
	+ Global.encodeUmlauts(type);

	//Aendere Blattsname?
	if (!filelink.equals(sheetdraft.getFilelink())) {

		rename = true;
		Global.sqlm.executeUpdate("UPDATE sheetdraft SET filelink='" + filelink
		+ "' WHERE filelink='" + sheetdraft_filelink + "' ");

		out.print(Global.addMessage("Sheet Filelink erneuert.", " success "));
	}
	if (rename) {
		Global.sqlm.executeUpdate("UPDATE sheetdraft SET filelink='" + filelink_target_new
		+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
		
	    //First rename/move the filesystem file.
		Global.renameFile(Global.root + sheetdraft.getFilelink(),
		Global.root + filelink_target_new);
		
		//Then update stored filelink (the main, initial e.g. uploaded one).
		//Herein the old filelink gets stored as previous filelink, too!
		sheetdraft.setFilelink(filelink_target_new);
		
		//nenne Bild um
		Global.renameFile(Global.root + sheetdraft.getImageLinkPrevious(),
		Global.root + sheetdraft.getImageLink());
		Global.renameFile(Global.root + sheetdraft.getPDFLinkPrevious(),
		Global.root + sheetdraft.getPDFLinkPrevious());
		
		
		//Verschiebe auch die Aufgaben des Uebungsblatts.
		//Also move the exercises of the sheet.
		sheetdraft.synchronizeExercisesLocationToFilelink();
		
		
		Global.renameFile(Global.root + sheetdraft.getFilelinkPrevious()
				, Global.root + sheetdraft.getFilelink()); 		
	    Global.addMessage("Datei " + (sheetdraft.getFilelink()) + " verschoben."
	    		," success ", request, pageContext);

	}

	//-------ACTION:MOVE-------
	if (verschieben) {
		Global.sqlm.executeUpdate("UPDATE sheetdraft SET filelink='" + filelink_target_new
		+ "' WHERE filelink='" + sheetdraft_filelink + "' ");
		new File(Global.root + filelink_target_new).mkdirs(); //erstelle die Zielordner
		sheetdraft.setFilelink(filelink_target_new);
		Global.moveDir(Global.root + sheetdraft.getFilelinkPrevious(),
		Global.root	+ filelink_target_new, ""); // verschiebe Ordner mit dem Blatt und Aufgaben

		Global.renameFile(Global.root + sheetdraft.getImageLinkPrevious(), Global.root
		+ sheetdraft.getImageLink()); //rename Name von Bild des Blattes
		out.print(Global.addMessage("Blatt verschoben", " success ")); 

	}
%>

<!-- Update Index von Suchmaschine  -->
<% /*@include file="action.update_index.jsp"*/%>
