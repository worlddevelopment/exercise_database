
<%
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
%>
<%@page import="java.util.Map" %>
<%@page import="swp.SQL_Methods, aufgaben_db.Global, aufgaben_db.Aufgaben_DB, aufgaben_db.Sheetdraft"%>
<%@page	import="swp.MysqlHelper,java.sql.Connection,java.sql.DriverManager,
                java.sql.ResultSet,java.sql.SQLException,java.sql.Statement"%>
<!--REDUNDANT - could mess up everything .. we ajax probs (without end)
<script src="http://code.jquery.com/jquery-latest.js"></script>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript" src="jquery/toolTipPreview.js"></script>

<script src="bootstrap/js/bootstrap.js"></script>
-->

<script type="text/javascript">

	
$('#delete_btn').click(function() {


	var check = 0;
	for (var zaehler = 0; zaehler < (document.getElementsByName("sheetdraft_id[]").length); zaehler++) {
	 if (document.getElementsByName("sheetdraft_id[]")[zaehler].checked) {
	  check++;
	 }
	}
	if (check > 0) {
	  var frage = confirm("M\u00F6chten Sie die " +
	 (check == 1 ? "Datei" : "Dateien") + " wirklich l\u00F6schen?");
	  if (frage == true) {
	   return true;
	  } 
	  else {
	   return false;
	  }
	 } 
	 else {
		 
		alert("Kein Blatt wurde Ausgew\u00E4hlt");
	  	return false;
	 }
});

$('#edit_btn').click(function() {

	var check = 0;
	for (var zaehler = 0; zaehler < (document.getElementsByName("sheetdraft_id[]").length); zaehler++) {
		if (document.getElementsByName("sheetdraft_id[]")[zaehler].checked) {
		   check++;
		}
	}
	if (check == 0) {
		alert("Kein Blatt wurde Ausgew\u00E4hlt");
		return false;
	} 
	else {
	   return true;
	}
	
});

</script>

<div id="dialog"></div>
<div style="float: left; margin-left: 50px; border: 1px solid black;">
	<form id="form" name='tf' action="index.jsp" method="get">
		<table style="border-spacing: 10px;">
			<%
			//retrieve & decide
			boolean table_for_sheetdraft_listing = request.getParameter("exercise_listing") == null;
			//retrieve
			//for both the sjeetdraft and the exercise list
			    //session = (HttpSession)request.getParameter("session");
				String session_user = request.getParameter("session_user");
				String lecturer = new String(request.getParameter("lecturer").getBytes(
						"ISO-8859-1"), "UTF-8");
                String filelink = new String(request.getParameter("filelink").getBytes(
                        "ISO-8859-1"), "UTF-8");;
                
			    String uploader = "";

				Statement statement1 = null;
				MysqlHelper mh = new MysqlHelper();
				Connection con = mh.establishConnection(response);
				try {
					statement1 = con.createStatement();

					
					/**------------------------------------------------------
					 FILELINK GIVEN ?
					 **-----------------------------------------------------*/
					//FILELINK IS EQUAL FOR BOTH CASES! SO THIS DOES NOT WORK:
					//if (!filelink.matches("[Ee]xercise[_]?[0-9]+")) {
				    if (table_for_sheetdraft_listing) {
						/* deliver all sheetdrafts of this lecturer listing */
						out.println("<tr>"
								  + "<th>Dateilink</th>"
                                  + "<th>Dozent</th>"
                                  + "<th>Veranstaltung</th>"
                                  + "<th>Semester</th>"
                                  + "</tr>"
                        );					
						
						
						//retrieve (for sheetdrafts' table listing)
						String semester = new String(request.getParameter("semester").getBytes(
                                "ISO-8859-1"), "UTF-8");
		                String course = new String(request.getParameter("course").getBytes(
		                        "ISO-8859-1"), "UTF-8");
		                String type = new String(request.getParameter("type").getBytes(
		                        "ISO-8859-1"), "UTF-8");
		                
						String str_query5 = "SELECT filelink, sheetdraft.id"
						        + " FROM sheetdraft, lecturer l "
						        + " WHERE semester = '" + semester
								+ "' AND course = '" + course
								+ "' AND type = '" + type //Exercise|Solution
								+ "' AND l.lecturer = '" + lecturer
								+ "' AND l.id = lecturer_id";
						ResultSet res1 = statement1.executeQuery(str_query5);
						//--------------------Datei -----------------------
						while (res1.next()) {
							//Tabelle fuer Blatt generieren

							String sheetdraft_id = res1.getString("id");
							out.println("<tr style='margin: 10px;'>");

							out.println("<td ><a href='" + filelink
									+ "' class = 'screenshot' rel='" + Global.convertToImageLink(filelink)
									+ "'>" + Global.extractFilename(filelink) + "." + Global.extractEnding(filelink)
									+ "</a></td>");
							out.println("<td>" + lecturer + "</td>");
							out.println("<td>" +course + "</td>");
							out.println("<td>" + semester + "</td>");
							out.println("<td><input name='sheetdraft_id[]' value = '"
									+ sheetdraft_id + "' type='checkbox'/></td>");
							out.println("</tr>");
						}
						
						
						
						

					} else {

						//Tabelle fuer Einzelaufgaben generieren
						out.println("<tr>"
								  + "<th>Belongs to Sheet</th>"
								  + "<th>Origin</th>"
                                  + "<th>Filelink</th>"
                                  + "<th>Lecturer</th>"
                                  + "<th>Uploaded By</th>"
                                  + "<th>Split By</th>"
                                  + "</tr>"
                        );      
						
						
						String str_query5 = "SELECT e.sheetdraft_id, e.originsheetdraft_filelink, e.filelink, author, e.splitby"
						        +" FROM exercise e, sheetdraft"/*, lecturer l"*/
								+ " WHERE sheetdraft.filelink = '" + filelink + "'"
								/*filelink is a index => unique for each exercise.
								+ " AND semester = '" + semester + "'"
								+ " AND course = '" + course + "'"
								+ " AND type = '" + type + "'" 
								+ " AND l.lecturer = '" + lecturer + "'"
								+ " AND l.id = sheetdraft.lecturer_id"
								+ " AND e.sheetdraft_id = sheetdraft.id"
								*/
								;
						ResultSet res1 = statement1.executeQuery(str_query5);
						//--------------------Datei -----------------------
						while (res1.next()) {
							//String aufg_id = res1.getString("id");
							String exercise_filelink = res1.getString("filelink");
							//get other missing variables for the table or a possible edit
							String originsheetdraft_filelink = res1.getString("originsheetdraft_filelink");
							String sheetdraft_id = res1.getString("sheetdraft_id");
							//lecturer = res1.getString("lecturer");
							uploader = res1.getString("author");
							String exercise_splitby = res1.getString("splitby");
							
							out.println("<tr>");

							out.println("<td>" + sheetdraft_id + "</td>");
                            out.println("<td>" + originsheetdraft_filelink + "</td>");
							out.println("<td><a href='" + exercise_filelink
									+ "' class = 'screenshot' rel='" + Global.convertToImageLink(exercise_filelink)
									+ "'>" + Global.extractFilename(exercise_filelink) + "." + Global.extractEnding(exercise_filelink)
									+ "</a></td>");
							out.println("<td>" + lecturer + "</td>");
							out.println("<td>" + uploader + "</td>");
							out.println("<td>" + exercise_splitby + "</td>");
							//out.println("<td><input name='aufg_id[]' value='"
							//		+ aufg_id + "' type='checkbox'/></td>");
							out.println("<td><input name='exercise_filelinks[]' value='"
								    + exercise_filelink + "' type='checkbox'/></td>");

							out.println("</tr>");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			%>

		</table>
		
		
		<div id="edit_buttons" style="padding: 5px;">
		<%
		if(table_for_sheetdraft_listing) {
			if(lecturer == session_user/*session.getAttribute("user")*/
			/*|| uploader == session.getAttribute("user") TODO eventually activate*/)
			{
		    %>
			<button id="delete_btn" name="id" class="btn btn-danger"
				title="L&ouml;schen" value="delete">
				<i class="icon-trash icon-white"></i>
            </button>
		    <%
		    }
		    %>
			<button id="edit_btn" name="id" class="btn" title="Bearbeiten"
				value="edit">
				<i class="icon-edit"></i>
			</button>
			
	   <%
	   }
	   %>
			<button name="q" value="add_to_draft" class="btn btn-primary">in
				Entwurf: </button>
			<select name="destination_draft_filelink">			
			    <option value="-1">new draft</option>
			    
			    <%
			    //LOAD USER DRAFTS.
			    //false because drafts always belong to the author/uploader/deriving and not the lecturer
			    Aufgaben_DB.loadAllSheetdrafts(session_user, false);
			    Map<String, Sheetdraft> loadedDrafts = Aufgaben_DB.getLoadedDraftsOnly();
			    Sheetdraft latestChangedDraft = Aufgaben_DB.getLatestChangedDraft();
			    
			    //USER DRAFTS TO BE LOADED PRIOR TO THIS OR ON THE FLY IN GET ALL...!
			    //latest changed draft
			    //No sheetdrafts? This would imply this one being an empty dummy Sheetdraft?
			    //if (!latestChangedDraft.getId() == -1) {
			    if (latestChangedDraft.getFilelink() != null
			    		|| !latestChangedDraft.getFilelink().equals("")
			    		) {
				    out.println("<option value='" + latestChangedDraft.getFilelink()
				    + "'>last draft that has been added to (" + latestChangedDraft.getFileName() + ")</option>");
			    }
			    
			    //available drafts
			    for (Sheetdraft draft : loadedDrafts.values()) {
			    %>
				    <option value="<%=draft.getFilelink()%>">Global.extractFilename(draft.getFilelink())</option>
			    
			    <%
			    } 
			    %>
			    
			</select>
		</div>

	</form>

</div>

