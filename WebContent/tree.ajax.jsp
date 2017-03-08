<%response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
%>
<!--
< %@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%@page import="java.net.URLDecoder, java.net.URLEncoder" %>
<%@page import="java.util.List, java.util.ArrayList, java.util.TimeZone, java.util.Locale, java.util.GregorianCalendar" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="db.SQL_Methods, aufgaben_db.Global" %>
<%@page import="db.MysqlHelper,java.sql.Connection,java.sql.DriverManager,
		java.sql.ResultSet,java.sql.SQLException,java.sql.Statement"%>
<!--
<script type="text/javascript" src="jquery.treeview/demo/demo.js">
</script>
<script type="text/javascript" src="jquery/tablesorter/jquery.tablesorter.mod.js">
</script>
<script type="text/javascript" src="jquery/tablesorter/jquery.tablesorter.mod.pager.js">
</script>
<script type="text/javascript" src="jquery/tablesorter/jquery.tablesorter.collapsible.js">
</script>
-->
<style type="text/css">@import url("jquery/tablesorter/jquery.tablesorter.pager.css")</style>



<%
/* DYNAMIC GROUPING EXTENSION FOR THE TABLESORTER MOD:
1) sort after category/one of the tags or in OR concatenation.
2) group those by iterating all rows (tr) and removing everywhere and adding "extend-child"
for all subsequent equal ones.
3) Then sort as usual using the tablesorter mod to have it ignore the grouped (sub-)items.
*/



// ANSICHT | VIEW
String varname_chosen = request.getParameter("ansicht");
if (varname_chosen == null) {
	// This is for a version, when javascript is deactivated in the browser!
	// It is functional!! -- but we wanted to save bandwidth.
//	/* statically called by start.in.jsp -- listing loaded completely*/
//	request.setAttribute("varname_chosen", "semester");
//	request.setAttribute("treeviewnum", "0");
//	response.getWriter().write("<div id='start_list_semester' class='start_list' style=''>");
//	// now dynamically - one call, one method, no more multiple source here
//	SQL_Methods.createTreeView(request, response);
//	response.getWriter().write("</div>");
//
//	request.setAttribute("varname_chosen", "lecturer");
//	request.setAttribute("treeviewnum", "1");
//	response.getWriter().write("<div id='start_list_lecturer' class='start_list' style=''>");
//	//now dynamically - one call, one method, no more multiple source here
//	SQL_Methods.createTreeView(request, response);
//	response.getWriter().write("</div>");
//
//	request.setAttribute("varname_chosen", "course");
//	request.setAttribute("treeviewnum", "2");
//	response.getWriter().write("<div id='start_list_course' class='start_list' style=''>");
//	//now dynamically - one call, one method, no more multiple source here
//	SQL_Methods.createTreeView(request, response);
//	response.getWriter().write("</div>");
//
//	request.setAttribute("varname_chosen", "type");
//	request.setAttribute("treeviewnum", "3");
//	response.getWriter().write("<div id='start_list_type' class='start_list' style=''>");
//	//now dynamically - one call, one method, no more multiple source here
//	SQL_Methods.createTreeView(request, response);
//	response.getWriter().write("</div>");
}
else if (varname_chosen.equals("list_all_exercises") || varname_chosen.equals("List all exercises")
		|| varname_chosen.equals("list_all_own_exercises") || varname_chosen.equals("List all own exercises")) {


	/*TODO If the sorting and grouping has no issues with it, then load the exercise rows using AJAX.
		   i.e. At the end we could put a button, load more. Or we use a pager. Even though all of
		   this really creates heaps of problems for JavaScript sorting and grouping.

		   We could for now settle on first ORDER_BY and GROUP_BY via MySQL. Then on this result set
		   perform a refined - but non-complete - sorting and grouping.
	*/

	//session = (HttpSession)request.getParameter("session");
	String session_user = request.getParameter("session_user");
	if (session_user == null) {
		System.out.println(
			Global.addMessage("Session user was not given for determining"
					+ " the permissions. -> No permissions granted!"
					, "info")
		);
		session_user = "";//return ;
	}
	else {
		session_user = URLDecoder.decode(session_user, "utf-8");
	}


	//load x many exercises from start:
	int howManyToLoadCount = 50;
	int start_offset; start_offset = 0;

	if (request.getParameter("limit") != null) {
		howManyToLoadCount = Global.getInt(request.getParameter("limit"));
	}
	if (request.getParameter("offset") != null) {
		start_offset = Global.getInt(request.getParameter("offset"));
	}


	String uploader = "";
	String sql;
	ResultSet res1; res1 = null;
	/* Note: Ajax can be debugged like the following:
	   http://localhost:8080/aufgaben_db_v15/tree.ajax.jsp?debug=debug&ansicht=list_all_exercises
	 */
%>
<!-- EXERCISE LISTING -->
<div id="pager">
<form>
	<input class="first" type="button" value="<<"/>
	<input class="prev" type="button" value="<"/>
	<input class="pagedisplay" type="text" />
	<input class="next" type="button" value=">"/>
	<input class="last" type="button" value=">>"/>
	<select class="pagesize">
		<option value="10">10</option>
		<option>20</option>
		<option>30</option>
		<option>40</option>
		<option selected="selected">50</option>
		<option>100</option>
		<option>200</option>
	</select>
</form>
</div>
<form method="post" action="" onsubmit="/*TODO ajaxise return false;*/">
<table id="exercise-listing" class="listing exercise-listing grid tablesorter">
	<!--
	<colgroup>
		<col width="19" />
		<col width="85" />
		<col width="250" />
		<col width="100" />
		<col width="90" />
		<col width="70" />
	</colgroup>
	-->
<thead>
<%

// EXERCISE LISTING
// Tabelle fuer Einzelaufgaben generieren
out.println("<tr>"
		   + "<th></th>"
		   + "<th>" + Global.display("origin") + " <i class='icon-tree'></i></th>"/* Currently the order is from general left to specific right. */
		   + "<th>" + Global.display("belongs to") + " " + Global.display("sheet") + "<i class='icon-tree'></i></th>"
		   + "<th>" + Global.display("filelink") + "</th>"
		   + "<th>" + Global.display("corresponding solution") + " " + Global.display("or") + " " + Global.display("exercise") + "</th>"
		   + "<th>" + Global.display("upload date") + " <i class='icon-tree'></i></th>"
		   + "<th>" + Global.display("category") + System.getProperty("file.separator")/*<- as hint on underlaying os*/ + Global.display("tags") + "<i class='icon-tree'></i></th>"
		   + "<th>" + Global.display("semester") + "<i class='icon-tree'></i></th>"/*Note: Semester has to be the highest level folder if a course name
		   happens to be changed. e.g. if a course is split into two sub course. This is because
		   for each semester there is one course folder and not one course folder (that determines
		   the course name) and multiple semester folders inside. */
		   + "<th>" + Global.display("course") + "<i class='icon-tree'></i></th>"
		   + "<th>" + Global.display("lecturer") + " <i class='icon-tree'></i></th>"
		   + "<th>" + Global.display("type") + " <i class='icon-tree'></i></th>"
		   //+ "<th>Uploaded By</th>"
		   //+ "<th>Split Pattern</th>"
		   //+ "<th>Action</th>" <- Now the first column and the filelink column contain actions.
		   + "</tr>"
);
%>
</thead>
<tbody>
<%


sql = "SELECT DISTINCT exercise.filelink, exercise.originsheetdraft_filelink, exercise.sheetdraft_filelink, exercise.splitby, exercise.is_native_format, exercise.whenchanged, exercise.whencreated"
		+ " FROM exercise";
if ( varname_chosen.equals("list_all_own_exercises") || varname_chosen.equals("List all own exercises") ) {
	sql += ", sheetdraft, lecturer"
			+ " WHERE "
				+ "(sheetdraft.author = '" + session_user + "' OR (lecturer = '" + session_user + "' AND sheetdraft.lecturer_id = lecturer.id))"//first select, then join.
				+ " AND exercise.sheetdraft_filelink = sheetdraft.filelink"
			;
}
// First group: (to have less rows)
/* As this is what group by is used for: (see https://dev.mysql.com/doc/refman/5.0/en/group-by-modifiers.html)
mysql> SELECT year, SUM(profit) FROM sales GROUP BY year;
+------+-------------+
| year | SUM(profit) |
+------+-------------+
| 2000 |		4525 |
| 2001 |		3010 |
+------+-------------+
String group_by_column = "";
if (request.getParameter("group_by") != null) {
	group_by_column = request.getParameter("group_by");
}
sql += " GROUP BY `" + group_by_column + "`";
*/

// Then sort (the grouped rows):
String sort_by_column = "whencreated";//exercise.whenchanged";
if (request != null && request.getParameter("sort_by") != null) {
	sort_by_column = request.getParameter("sort_by");
}
sql += " ORDER BY `" + sort_by_column + "` DESC";
sql += " LIMIT " + start_offset + "," + howManyToLoadCount;

res1 = Global.query(sql);
if (res1 == null) {
	out.print("<tr><td colspan=\"11\">No exercises found for user "+ session_user +". "/*);*/
	/* return is no good option if this is not wrapped in a function. Unfortunately throwing an
	   exception is no better and results in an ajax error. So we went for the else clause.*/
	/*throw new NullPointerException(*/ + "Exercise List found nothing OR query was invalid. "
			+ " Check keywords used or if generated parameters were empty.</td></tr>");
}
else {
	int all_exercises_index = 0;
	//else list all exercises for each found sheetdraft:
	while (res1.next()) {

		//String aufg_id = res1.getString("id");
		String exercise_filelink = res1.getString("filelink");
		// Get other missing variables for the table or a possible edit
		String originsheetdraft_filelink = res1.getString("originsheetdraft_filelink");
		String sheetdraft_filelink = res1.getString("sheetdraft_filelink");//= filelink
		String exercise_splitby = res1.getString("splitby");


		// ----Fetch more information to the sheetdraft it belongs to ----
		sql = "SELECT semester, course, l.lecturer, l.id, type, author, description, sheetdraft.whencreated"
				+ " FROM sheetdraft, lecturer l"
				+ " WHERE sheetdraft.filelink = '" + sheetdraft_filelink + "'"
				+ " AND l.id = sheetdraft.lecturer_id";
		ResultSet res2;
		res2 = Global.query(sql);

		// Information we wish to fetch:
		String semester; semester = "";
		String course; course = "";
		String lecturer; lecturer = ""; // could be given per request to save the join.
		int lecturer_id; lecturer_id = -1;
		String type; type = "";

		String description = "";
		long whencreated = -1;

		// As sheetdraft filelink is the unique key, only one result row is expected:
		if (res2 != null && res2.next()) {

			semester = res2.getString("semester");
			course = res2.getString("course");
			lecturer = res2.getString("lecturer");
			lecturer_id = res2.getInt("id");
			type = res2.getString("type");

			description = res2.getString("description");
			whencreated = res2.getLong("whencreated");
			uploader = res2.getString("author");

		}
		Global.queryTidyUp(res2);


		// ------ Statistics ------
		// List and count of all semesters this exercise is used in.
		sql = "SELECT DISTINCT sheetdraft.semester, sheetdraft.filelink"//, COUNT(DISTINCT sheetdraft.semester) AS semester_used_in_count"
				+ " FROM sheetdraft, exercise WHERE exercise.filelink='"+ exercise_filelink +"'"
				+ " AND sheetdraft.filelink = exercise.sheetdraft_filelink"
		;
		ResultSet res_statistics = Global.query(sql);
		String exercise_semester_used_in = "";

		int exercise_semester_used_in_count = 0;
		if (res_statistics != null/* && res_semester.next()*/) {
			// There may be more than one sheetdraft filelinks, all having the same COUNT of semester.
			while (res_statistics.next()) {
				// Currently we are only interested in the semesters and the semester count. Not the correpsonding sheetdrafts.
				exercise_semester_used_in += res_statistics.getString("semester");
				exercise_semester_used_in += " (" + Global.extractFilename(res_statistics.getString("filelink")) + ")";
				exercise_semester_used_in += ",";
				exercise_semester_used_in_count++;//SELECT COUNT(DISTINCT sheetdraft.semester) FROM sheetdraft, exercise WHERE exercise.filelink='uploads/WS_2014/Analytik/Weigel/EXERCISES/ADB_uebungsblatt11.odt.odt__Exercise_2__splitby_AUFGABE.odt.odt' AND sheetdraft.filelink = exercise.sheetdraft_filelink;

			}
		}
		Global.queryTidyUp(res_statistics);
		exercise_semester_used_in = exercise_semester_used_in.replaceAll(",$", "");

		// count of all sheetdrafts this exercise is used in.
		sql = "SELECT COUNT(DISTINCT sheetdraft_filelink) AS count"
				+ " FROM exercise WHERE exercise.filelink='"+ exercise_filelink +"'"
				//+ " FROM sheetdraft, exercise WHERE exercise.filelink='"+ exercise_filelink +"'"
				//+ " AND sheetdraft.filelink = exercise.sheetdraft_filelink;"
				;
		res_statistics = Global.query(sql);
		int exercise_how_often_used = 0;
		if (res_statistics != null && res_statistics.next()) {
			exercise_how_often_used = res_statistics.getInt("count");
		}
		Global.queryTidyUp(res_statistics);


		sql = "SELECT COUNT(DISTINCT originsheetdraft_filelink) AS count"
				+ " FROM exercise WHERE exercise.filelink='"+ exercise_filelink +"'"
				;
		res_statistics = Global.query(sql);
		int exercise_how_often_used_derived_total = 0;
		if (res_statistics != null && res_statistics.next()) {
			exercise_how_often_used_derived_total = res_statistics.getInt("count");//SELECT sheetdraft_filelink, semester, COUNT(DISTINCT sheetdraft.semester) FROM sheetdraft, exercise WHERE exercise.filelink='uploads/WS_2014/Analytik/Weigel/EXERCISES/ADB_uebungsblatt11.odt.odt__Exercise_2__splitby_AUFGABE.odt.odt' AND sheetdraft.filelink = exercise.sheetdraft_filelink;
		}
		Global.queryTidyUp(res_statistics);

		String dateRepr = "" + whencreated;
		if (whencreated > 0) {
			/* http://stackoverflow.com/questions/8237193/java-convert-milliseconds-to-date
			DateTime jodaTime = new DateTime(yourmilliseconds,DateTimeZone.forTimeZone(TimeZone.getTimeZone("US/Central")));
			DateTimeFormatter parser1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
			System.out.println("jodaTime "+parser1.print(jodaTime)); */

			java.util.Date date = new java.util.Date(whencreated);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"//:ss, SSS"
					, Locale.GERMAN
			);
//			GregorianCalendar calendar = new GregorianCalendar(/*TimeZone.getTimeZone("GERMAN")*/);
//			calendar.setTimeInMillis(whencreated);

//			dateRepr = sdf.format(calendar.getTime());
			dateRepr = sdf.format(date);

		}


		/* ----Determine the corresponding Exercise to the Solution or Solution to the Exercise.
			   There are some scenarios for differences in the path of an exercise as compared to a solution:
			   1) <same_semester>/<same_course>/<same_lecturer>/<different_TYPE>/<different_mothersheet_filelink>__Exercise_<same_exercise_number>__splitby_<different_splitter>.<same_orig_ending>.<same_ending>
					   e.g.	  Path is equal, splitter different: (would overwrite mothersheet and if we allow only one solution per sheet that's desirable)
							WS_2014/Brunnenbau/Eratosthenes/SOLUTION/Brunnenbau_Blatt_010.odt.odt__Exercise_1__splitby_AUFGABE.odt.luatex
							WS_2014/Brunnenbau/Eratosthenes/SOLUTION/Brunnenbau_Blatt_010.odt.odt__Exercise_1__splitby_LOESUNG.odt.luatex
							Note: Above the path is equal. The motherfilelink too. If chosen wrong TYPE at upload
								  by accident then this would overwrite the motherfile, but not the exercises as long as the splitter is different.
								  So there exists these cases too:
							WS_2014/Brunnenbau/Eratosthenes/EXERCISE/Brunnenbau_Loesung_010.odt.odt__Exercise_1__splitby_LOESUNG.odt.luatex
							WS_2014/Brunnenbau/Eratosthenes/EXERCISE/Brunnenbau_Blatt_010-Lsg.odt.odt__Exercise_1__splitby_LOESUNG.odt.luatex
							WS_2014/Brunnenbau/Eratosthenes/EXERCISE/Brunnenbau_Blatt_010-Loesung.odt.odt__Exercise_1__splitby_LOESUNG.odt.luatex
							WS_2014/Brunnenbau/Eratosthenes/EXERCISE/Brunnenbau_Blatt_010-L.odt.odt__Exercise_1__splitby_LOESUNG.odt.luatex
								and this where the type was chosen correctly:
							WS_2014/Brunnenbau/Eratosthenes/SOLUTION/Brunnenbau_Blatt_010.odt.odt__Exercise_1__splitby_LOESUNG.odt.luatex
								still the Loesung keyword could be added AND the sheettype be chosen correctly:
							WS_2014/Brunnenbau/Eratosthenes/SOLUTION/Brunnenbau_Loesung_010.odt.odt__Exercise_1__splitby_LOESUNG.odt.luatex

							=> Many different possibilities to find a solution that belongs to an exercise.

			   2) <same_semester>/<same_course>/<same_lecturer>/<same_TYPE>/<same_mothersheet_filelink>__Exercise_<same_exercise_number>__splitby_<different_splitter>.<same_orig_ending>.<same_ending>
					   e.g. WS_2014/Brunnenbau/Eratosthenes/EXAM/Brunnenbau_Exam_010.odt.odt__Exercise_1__splitby_AUFGABE.odt.luatex
							WS_2014/Brunnenbau/Eratosthenes/EXAM/Brunnenbau_Exam_010.odt.odt__Exercise_1__splitby_LOESUNG.odt.luatex
		*/
		String correspondingExerciseFilelink = "";//null;
		if ( !( semester.isEmpty() || course.isEmpty() || lecturer.isEmpty() || type.isEmpty() )
				) {

			correspondingExerciseFilelink = Global.resolveCorrespondingFilelinkTo(exercise_filelink);

		}
		// semester, course and lecturer are not empty?
		else {
			if (Global.debug) {
				System.out.println(
					Global.addMessage(
							"The variables semester, course and lecturer are empty but should not."
							, "warning"
							)
				);
			}
		}



		// Now print the information:
		%><tr class="collapsed <% //if ( res1.getRow() % 2 == 0 ) { out.print("expand-child"); }
		%>">
		<!-- ADAPT ROWSPAN IF AN EXTRA ROW FOR PREVIEW IMAGE IS REINTRODUCED. -->
		<td rowspan="1" class="collapsible">
		<input id="exerciseRowAction_mark<%= res1.getRow() %>"
				name="exercise_filelinks[]" value="<%= exercise_filelink %>" type="checkbox"
				title="select" />
		</td>
		<%
		// ORIGINSHEETDRAFT:
		String end = Global.extractEnding(originsheetdraft_filelink);
		String imageType = ".png";
		if (end.equals(".odt") || end.equals("odt")) {
			//imageType = ".svg";<--svg is causing lag if it's just needed too often or contains too many vector nodes.
		}
		out.println("<td class='"+ end +"'>"
				+ "<a href='" + originsheetdraft_filelink
						+ "' class='screenshot' rel='" + Global.convertToImageLink(originsheetdraft_filelink)
						+ "' title='" + Global.extractFilenameAndEnding(originsheetdraft_filelink)
						+ "'>"
					+"<img class='icon' src='img/css/"+ end + imageType +"' title='"+ end + ": "
							+ Global.extractFilenameAndEnding(originsheetdraft_filelink) + "'  align='left' />"
					+"<i class='icon-download'></i> "
					+"<i onmouserover='/*toggleClass(this,*/this.className=\"icon-eye-close\"/*)*/;' onmouseout='/*toggleClass(this,*/this.className=\"icon-eye-open\"/*, icon-eye-close)*/;/*<-is already reversed so do not doubly reverse*/' class='icon-eye-open'></i>"
				+ "</a>");

		// Preview image in the first content as it needs a lot of space:
		// overflow:hidden; css property or similar will break the preview resizing.
		out.println("<label for='exerciseRowAction_mark"+ res1.getRow() +"'>"
				+ "<img src='" + Global.convertToImageLink(exercise_filelink)
				+ "' alt='inline preview image' title='Diese Aufgabe ausw&auml;hlen.' align='left' />"
				+ "</label>"
				+ "<!-- Exercise optical separator --><hr />");

		out.print("</td>");

		// SHEETDRAFT:
		end = Global.extractEnding(sheetdraft_filelink);
		imageType = ".png";
		if (end.equals(".odt") || end.equals("odt")) {
			//imageType = ".svg";<--svg is causing lag if it's just needed too often or contains too many vector nodes.
		}
		out.println("<td class='"+ end +"'>"
				+"<img class='icon' src='img/css/"+ end + imageType +"' title='"+ end + ": " + Global.extractFilenameAndEnding(sheetdraft_filelink) + "' align='left' />"
				+ "<a href='" + sheetdraft_filelink
				+ "' class='screenshot' rel='" + Global.convertToImageLink(sheetdraft_filelink)
				+ "' title='" + Global.extractFilenameAndEnding(sheetdraft_filelink)
				+ "'>"
					+"<i class='icon-download'  title='Dozent: "+ lecturer +" | Hochgeladen von: "+ uploader +"'></i> <i class='icon-eye-open'></i>"
				+ "</a>"
				+ (session_user.equals(lecturer) || session_user.equals(uploader) ?
					"<a href='?id=edit&filelink=" + URLEncoder.encode(sheetdraft_filelink, "utf-8") + "' class='btn-secondary' title='edit'"
							+ " onmouseover='this.className=this.className + \" btn \" '"
							+ " onmouseout='this.className=\"btn-secondary \";'"
							+ ">"
						+ "<i class='icon-edit'></i>"
					+ "</a>"
					+ "<a href='?id=start&q=delete_sheet&filelink=" + URLEncoder.encode(sheetdraft_filelink, "utf-8") + "' class='btn-danger'"
							+" title='delete sheet and all its exercises'"
							+" onmouseover='this.className=\"btn btn-danger\";'"
							+" onmouseout='this.className=\"btn-danger\";'>"
						+ "<i class='icon-trash icon-white'></i>"
					+ "</a>"
					: "")
				/*+ "<br />"*/
				+ "<a href='" + sheetdraft_filelink
						+ "' class='screenshot' rel='" + Global.convertToImageLink(sheetdraft_filelink)
						+ "' title='" + Global.extractFilenameAndEnding(sheetdraft_filelink)
						+ "'>"
					+ Global.extractFilenameAndEnding(sheetdraft_filelink) //readded as of v31.13c
				+ "</a></td>");

		// EXERCISE/SOLUTION FILELINK:
		end = Global.extractEnding(exercise_filelink);
		imageType = ".png";
		if (end.equals(".odt") || end.equals("odt")) {
			//imageType = ".svg";<--svg is causing lag if it's just needed too often or contains too many vector nodes.
		}
		out.println("<td class='"+ end +"'>"
				+ "<a href='" + exercise_filelink
						+ "' class='screenshot' rel='" + Global.convertToImageLink(exercise_filelink)
						+ "'>"
					+"<img class='icon' src='img/css/"+ end + imageType +"' title='"+ end + ": " + Global.extractFilenameAndEnding(exercise_filelink) + "' align='left' />"
					+"<i onclick='/*toggleClass(this, */this.className=\"icon-ok\";' class='icon-download'></i> "
					+"<img onmouseover='/*toggleClass(this,*/this.className=\"icon-eye-close\";' class='icon-eye-open'  title='Derived "
							+ exercise_how_often_used_derived_total + " times. Still referenced "+ exercise_how_often_used +" times. | Used in "+ exercise_semester_used_in_count +" Semester: "+ exercise_semester_used_in +"' />"//<i class='icon-eye-open'></i>"
					+ Global.extractExercisePartOnlyFromExerciseFilelink(exercise_filelink)//Global.extractFilename(exercise_filelink) + "." + Global.extractEnding(exercise_filelink)
				+ "</a></td>");

		//CORRESPONDING EXERCISE SOLUTION FILELINK:
		out.println("<td class='"+ end +"'>");
		if (correspondingExerciseFilelink == null) {
			System.out.println("Looks like the corresponding exercise (solution) could not be found. correspondingExerciseFilelink: " + correspondingExerciseFilelink);
			correspondingExerciseFilelink = "";
		}
		else {
			end = Global.extractEnding(correspondingExerciseFilelink);
//			if (end.equals(".odt") || end.equals("odt")) {
//				//imageType = ".svg";<--svg is causing lag if it's just needed too often or contains too many vector nodes.
//			}

			out.println("<a href='" + correspondingExerciseFilelink
						+ "' class='screenshot' rel='" + Global.convertToImageLink(correspondingExerciseFilelink)
						+ "'>"
					+"<img class='icon' src='img/css/"+ end + imageType +"' title='"+ end + ": " + Global.extractFilenameAndEnding(correspondingExerciseFilelink) + "' align='left' />"
					+"<i class='icon-download'></i> <i class='icon-eye-open'></i>"
					+ Global.extractExercisePartOnlyFromExerciseFilelink(correspondingExerciseFilelink)
					//+ Global.extractFilenameAndEnding(correspondingExerciseFilelink)
				+ "</a>");
		}
		out.println("</td>");

		// FURTHERMORE: date, description, tags, semester, type,...
		String description_input_id = "input_description_" + res1.getRow();
		String description_old_input_id = description_input_id + "_old";
		String onchange = "var description_old=document.getElementById('" + description_old_input_id + "');var description=document.getElementById('" + description_input_id + "'); "
				+ "var el = document.getElementById('btn_ajax_submit_" + res1.getRow() /*all_exercises_index*/ + "');"
				+ " el = el || this.nextElementNode;"
				+ "if (description.value != description_old.value) { "
					+ " el.disabled=false; el.style.display='inline';"
				+ "} else { "
					+ " el.disabled=true; el.style.display='none';"
				+ "}";
		%>
			<td title="<%= whencreated %>">
				<%=dateRepr %>
			</td>
			<td title="<%= Global.display("sheet") %> Information:">
				<jsp:include page="input.generic.jsp">
					<jsp:param name="type" value="hidden" />
					<jsp:param name="name" value="description_old[]" />
					<jsp:param name="default" value="<%=description %>" />
					<jsp:param name="node_id" value="<%=description_old_input_id %>" />
				</jsp:include>
				<!-- TODO add possibility to add tags and description to exercises instead of the sheet as a whole?
				<input type="text" onfocus="makeNextButtonSubmit(this);" onkeyup="makeNextButtonSubmit(this);" />
				-->
				<jsp:include page="input.generic.jsp">
					<jsp:param name="name" value="description" />
					<jsp:param name="default" value="<%=description %>" />
					<jsp:param name="node_id" value="<%=description_input_id %>" />
					<jsp:param name="onfocus" value="<%=onchange %>" />
					<jsp:param name="onkeyup" value="<%=onchange %>" />
				</jsp:include>
				<button id="btn_ajax_submit_<%= res1.getRow() /*all_exercises_index*/ %>" type="button" style="display:none;" disabled="disabled" name="q" value="change_draft_exercise_position" class="btn btn-success" title="Update all!">
				  &larr;<i class="icon-refresh icon-white"></i>&nbsp;&amp;&nbsp;<i class="icon-ok icon-white"></i>&rarr;
				</button>
			</td>
			<td title="For a list of all semesters this exercise can be found in: hover the details icon within the exercise filelink column.">
				<jsp:include page="input.semester.jsp">
					<jsp:param name="default" value="<%=semester %>" />
					<jsp:param name="onchange" value="<%= onchange %>" />
				</jsp:include>
			</td>
			<td>
				<jsp:include page="input.course.jsp">
					<jsp:param name="default" value="<%=course %>" />
					<jsp:param name="onchange" value="<%= onchange %>" />
				</jsp:include>
			</td>
			<td>
				<!--
				<c:set var="default" value="<%=lecturer_id %>" scope="request" />
				<c:set var="lecturer" value="<%=lecturer %>" scope="request" />
				-->
				<jsp:include page="input.lecturer.jsp">
					<jsp:param name="default" value="<%=lecturer_id %>" />
					<jsp:param name="lecturer" value="<%=lecturer %>" />
					<jsp:param name="onchange" value="<%= onchange %>" />
				</jsp:include>
			</td>
			<td>
				<jsp:include page="input.type.jsp">
					<jsp:param name="default" value="<%=type %>" />
					<jsp:param name="onchange" value="<%= onchange %>" />
				</jsp:include>
			</td>
			<!--
			<td>
				<jsp:include page="input.generic.jsp">
					<jsp:param name="name" value="uploader" />
					<jsp:param name="default" value="<%=uploader %>" />
				</jsp:include>
			</td>
			<td>
				<%=exercise_splitby %>
			</td>
			-->
		<%

		//out.println("</tr>");
		//out.println("<tr class='expand-child'>");

		//out.println("<td colspan='8'>");

		//out.println("</td>");


		%></tr><%
		++all_exercises_index;
	}
	// Tackle memory leaks by closing result set and its statement properly:
	Global.queryTidyUp(res1);
}


//Merely for the exercise listing
%>
	</tbody>
	</table><!-- EXERCISE LISTING -END -->


	<!-- EDIT BUTTONS -->
	<div id="edit_buttons" style="padding: 5px; position: fixed; bottom: 0;/*no effect as no longer on same level z-index:2;*/">
		<!-- STEER THE SHIP (now set in the action.add_to_draft automatically)
		<input type="hidden" name="id" value="drafts" />
		-->

		<!-- BUTTONS FOR ADDING EXERCISES TO A DRAFT -->
		<jsp:include page="buttons.add_to_draft.jsp">
			<jsp:param name="session_user" value="<%=session_user %>" />
		</jsp:include>
	</div>
	</form>

<%

}



//TREEVIEW - TODO Settings for desired default view OR store last used option in start.in.jsp.
else {/*ajax request*/
	//now dynamically - one call, one method, no more multiple source here
	SQL_Methods.createTreeView(request, response);
}
%>








<!--
<div id="ajaxcontent">

</div>
-->







<script type="text/javascript" src="js/filter.js">
</script>

<script type="text/javascript">

//tablesorter:
/*
var makeTableSortable = (function(idOrObj) {
	if (typeof idOrObj == 'string') {
		idOrObj = $(idOrObj);
	}
	idOrObj.tablesorter({
		sortList: [[2,0]]//third column is initial sorting order.
	});//without any options as we can do a zebra effect easily via n-th child of table rows.

})('#exercise-listing');

*/


/**
 * sorting a table via tablesorter jquery plugin.
 */
var initSortBy = function (objId, column_index, order_mode) {
	//if given as column, ordermode tuple?
	column_index = column_index || 0;
	order_mode = order_mode || 0;//ascending is default

	var column_tuple = arguments[1];
	if (typeof column_tuple == 'array') {
		column_index = column_tuple[0];
		order_mode = column_tuple[1];
	}

	$table = /*typeof objId == 'object' ? objId : */ $(objId);//$(".tablesorter");
	$table.collapsible("td.collapsible", {// td.collapsible = collapse to the first table row and show +/-
				collapse: true,
				toggleAllSelector: "expand_all",
				showCollapsed: true,
				collapse: false //<--because for some reason this failed, we now use the above css solution.
			})
			.tablesorter({
				// set default sort column
				sortList: [[column_index, order_mode]/*, [], ...*/],/*0=>ascending, 1=>descending*/
				// don't sort by first column
				headers: { 0: {sorter: false}/*,  11: {sorter: false}*/}//first col is for toggling, 11th is the action.
				// set the widgets being used - zebra stripping
				, widgets: ['zebra']
				, onRenderHeader: function (){
					this.wrapInner("<span></span>");
				}
				, debug: false
			})
	;
	$table.tablesorterPager({container: $("#pager"), positionFixed: false});

};





/* The following is required as the ajax loaded content needs to be loaded prior to initiating
   the tablesorter. Thus it may be that the body onload event is triggered before this happened.
*/
var globals = {};
globals["tablesorter_event_already_added"] = false;


/**
 *
 */
var startTablesorter = function() {
	// Sort by date descending first.
	initSortBy(".tablesorter", 5, 1); // <-- don't give a static string '' to jquery $!!'
	globals.tablesorter_event_already_added = true;
//   alert('first initialisation.');
	screenshotPreview();
}


//$(document).ready
addEvent(body, 'load', startTablesorter);

if (globals.tablesorter_event_already_added !== false) {
	//alert('prevented double initialisation. Body onLoad event was occurred after AjaX content was loaded. Thus everything was fine.');
}
else {
	startTablesorter();
}







function toggleClass(obj, one, two) {
	// At the end if nothing fits then always one resides - that's why setAttribute() sets two and not one.
	if (!obj.className) {
		obj.setAttribute('class', two);
	}
	//alert("class='" + obj.className + "'");
	if (obj.className.match(one)) {
		obj.className = obj.className.replace(one, two);
		//alert("two should reside: "+ two + " == class='" + obj.className + "''");
	}
	else if (obj.className.match(two)) {
		obj.className = obj.className.replace(two, one);
		//alert("one should reside: "+ one + " == class='" + obj.className + "''");
	}
	else /* (obj.className == '') */{
		obj.className = obj.className + " " + one;
	}

}


</script>
<!-- STYLESHEET FOR A MORE COMPACT DESIGN  -->
<style type="text/css">
table#exercise-listing select,
table#exercise-listing input,
table#exercise-listing input[type="text"]/*to make it overwrite other rules more easily as it's more specific'*/ {
	width: auto;
	min-width: 20px;
	max-width: 220px;
	background: none;
	border: none;
}
input[type="text"] .textarea {
	max-width: 150px;
}
td {
	min-width: 70px;
}
</style>

