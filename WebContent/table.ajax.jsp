<%@page import="cz.vutbr.web.csskit.antlr.CSSParser.statement_return"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
%>
<%@page import="java.net.URLDecoder, java.net.URLEncoder" %>
<%@page import="java.util.Map" %>
<%@page import="db.SQL_Methods,aufgaben_db.Global,aufgaben_db.Aufgaben_DB,aufgaben_db.Sheetdraft"%>
<%@page import="db.MysqlHelper,java.sql.Connection,java.sql.DriverManager,java.sql.ResultSet,java.sql.SQLException,java.sql.Statement"%>

<script type="text/javascript" src="jquery/toolTipPreview.js"></script>
<script type="text/javascript" src="js/validateSelectionDependingOnButton.js"></script>




<div class="ajax-loaded-content">
	<!-- MULTI SELECTION FORM (send multiple sheets for deletion at the same time) -->
	<form id="form" name='tf' action="index.jsp" method="get">
	<%
	// retrieve & decide
	boolean table_for_sheetdraft_listing = (request.getParameter("exercise_listing") == null);
	// retrieve
	// for both the sheetdraft and the exercise list
	//session = (HttpSession)request.getParameter("session");
	String session_user = request.getParameter("session_user");
	if (session_user == null) {
		System.out.print(
			Global.addMessage("Session user was not given for determining"
					+ "the permissions. -> No permissions granted!"
					, "info")
		);
		session_user = "";//return ;
	}
	else {
		session_user = URLDecoder.decode(session_user, "utf-8");
	}


	String filelink = request.getParameter("filelink");
	if (filelink == null && !table_for_sheetdraft_listing) {
		out.print(
			Global.addMessage("Without a key (filelink or id) of a sheet/draft"
					+ " an exercise listing cannot be generated. -> Aborting ..."
					, "warning")
		);
		return ;
	}
	else if (filelink != null) {

		filelink = new String(request.getParameter("filelink").getBytes("ISO-8859-1"), "UTF-8");
		filelink = URLDecoder.decode(request.getParameter("filelink"), "utf-8");
	}


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
			%>
				<!-- SHEETDRAFT LISTING -->
				<table class='listing sheetdraft-listing'>
			<%
			// SHEETDRAFT LISTING
			// deliver all sheetdrafts of this lecturer listing
			out.println("<tr onclick=\"var o = this.parentNode/*tbody*/.parentNode.parentNode/*form*/.parentNode/*div-ajax-content*/.parentNode/*div-target-container*/; o.style ? o.style.display='none' : o.setAttribute('style', 'display:none;')\">"
					  + "<th>Dateilink</th>"
					  + "<th>Dozent</th>"
					  + "<th>Veranstaltung</th>"
					  + "<th>Semester</th>"
					  + "<th>Action</th>"
					  + "</tr>"
			);


			// For building the directory of where to fetch the exercises from.
			// retrieve (for sheetdrafts' table listing) TODO alternatively extract from filelink
			String semester = request.getParameter("semester");
			String course = request.getParameter("course");
			String lecturer = request.getParameter("lecturer");
			String type = request.getParameter("type");

			String selectAllSheetdraftsInThisDirectory = null;

			// All individual folders given?
			if (semester == null || course == null || lecturer == null || type == null
					&& filelink != null) {
				// => required parameters missing, go another route
				String pathTo = Global.extractPathTo(filelink);

				selectAllSheetdraftsInThisDirectory = "SELECT filelink, sheetdraft.id, author"
						+ " FROM sheetdraft"//WHERE INSTR(column, '') > 0
						+ " WHERE filelink LIKE '" + pathTo + "%'"
						;

			}
			else {
				// => all parameters are defined
				semester = new String(request.getParameter("semester").getBytes("ISO-8859-1"), "UTF-8");
				semester = URLDecoder.decode(request.getParameter("semester"), "utf-8");

				course = new String(request.getParameter("course").getBytes("ISO-8859-1"), "UTF-8");
				course = URLDecoder.decode(request.getParameter("course"), "utf-8");

				lecturer = new String(request.getParameter("lecturer").getBytes("ISO-8859-1"), "UTF-8");
				lecturer = URLDecoder.decode(request.getParameter("lecturer"), "utf-8");

				type = new String(request.getParameter("type").getBytes("ISO-8859-1"), "UTF-8");
				type = URLDecoder.decode(request.getParameter("type"), "utf-8");

				selectAllSheetdraftsInThisDirectory = "SELECT filelink"//, sheetdraft.id"
						+ ", author"
						+ " FROM sheetdraft, lecturer l "
						+ " WHERE (semester = '" + Global.encodeUmlauts(semester) + "' OR semester = '" + semester + "')"
						+ " AND (course = '" + Global.encodeUmlauts(course) + "' OR course = '" + course + "')"
						+ " AND (type = '" + Global.encodeUmlauts(type) + "' OR type = '" + type + "')" //Exercise|Solution|Exam
						+ " AND (l.lecturer = '" + Global.encodeUmlauts(lecturer) + "' OR lecturer = '" + lecturer + "')"
						+ " AND l.id = lecturer_id";
			}


			ResultSet res1 = statement1.executeQuery(selectAllSheetdraftsInThisDirectory);
			//-------------------- File -----------------------
			if (res1 != null) {
				while (res1.next()) {
					// Generate table for sheetdraft

					//int sheetdraft_id = res1.getInt("id");
					uploader = res1.getString("author");
					%>
						<tr style='margin: 10px;'>
						<td >
					<%
					out.println("<a href='" + res1.getString("filelink")//filelink
							+ "' class='screenshot' rel='" + Global.convertToImageLink(res1.getString("filelink"))
							+ "'><i class='icon-download'></i> <i class='icon-eye-open'></i> "
							+ Global.extractFilename(res1.getString("filelink")) + "." + Global.extractEnding(res1.getString("filelink"))
							+ "</a></td>");
					out.println("<td title='"+ uploader +"'>" + Global.decodeUmlauts(lecturer) + "</td>");
					out.println("<td>" +course + "</td>");
					out.println("<td>" + Global.decodeUmlauts(semester) + "</td>");
					%>
						<td>
						<!--<input name='sheetdraft_id[]' value='' type='checkbox'/>
						-->
						<input name='sheetdraft_filelinks[]' value='<%=filelink %>' type='checkbox'/>
					<%
					//Merely the lecturer of this course and the uploader/author get permission:
					if(lecturer.equals(session_user)/*session.getAttribute("user")*/
							|| uploader.equals(session_user)) {
					%>
						<a href="?id=edit&filelink=<% out.print(URLEncoder.encode(filelink, "utf-8")); /*to be decoded accordingly*/%>"
								class="btn btn-secondary" title="edit">
							<i class="icon-edit"></i>
						</a>
						<a href="?id=start&q=delete_sheet&filelink=<% out.print(URLEncoder.encode(filelink, "utf-8")); /*to be decoded accordingly*/%>"
								class="btn btn-danger" title="delete">
							<i class="icon-trash icon-white"></i>
						</a>
					<%
					}
					%>
						</td>
						</tr>
						<!-- EDIT FORM -INLINE VERSION -->
						<!--
						<tr>
						<td colspan='5'>
						<form action="edit.in.jsp" method="post">
							<input type="hidden" name="filelink" value="<% out.print(filelink); %>" />
							<input type="text" name="filename" value="<% out.print(Global.extractFilename(filelink)); %>" disabled="disabled" />
							<input type="text" name="lecturer" value="<% out.print(lecturer); %>" disabled="disabled" />
							<input type="text" name="course" value="<% out.print(lecturer); %>" disabled="disabled" />
							<input type="text" name="semester" value="<% out.print(lecturer); %>" disabled="disabled" />
							<input type="text" name="type" value="<% out.print(lecturer); %>" disabled="disabled" />
							<button type="submit" name="q" value="edit_sheetdraft" class="submit"></button>
						</form>
						</td>
						</tr>
						-->
					<%
					/* The above had to be omitted because it gets into trouble because there is a global form
					to index.jsp and a form in a form is not allowed.*/

				}
				// Tackle memory leaks by closing result set and its statement properly:
				res1.close();
			}


			%>
				</table><!-- SHEETDRAFT LISTING -END -->

				<!-- EDIT BUTTONS -->
				<div id="edit_buttons" style="padding: 5px;">
			<%
			// Sheet/Draft listing only:
			// Merely the lecturer of this course and the uploader/author get permission:
			if(lecturer.equals(session_user)//session.getAttribute("user")
					|| uploader.equals(session_user)) {
				%>
					<button id="delete_btn" name="q" class="btn btn-danger"
							title="L&ouml;schen" value="delete_sheet">
						<i class="icon-trash icon-white"></i>
					</button>
					<!-- currently only single sheet/draft edit at a time is possible
					<button id="edit_btn" name="id" class="btn btn-secondary" title="<%=Global.display("edit") %>"
							value="edit">
						<i class="icon-edit"></i>
					</button>
					-->
				<%
				}
				%>
					<!-- STEER THE SHIP
					<input type="hidden" name="id" value="drafts" />
					-->
					<!-- BUTTONS FOR ADDING EXERCISES TO A DRAFT -->
					<jsp:include page="buttons.add_to_draft.jsp">
						<jsp:param name="session_user" value="<%=session_user %>" />
					</jsp:include>
				</div>
				<%











		}
		else {

			%>
				<!-- EXERCISE LISTING -->
				<table class="listing exercise-listing">
			<%
			//EXERCISE LISTING
			//Tabelle fuer Einzelaufgaben generieren
			out.println("<tr onclick=\"var o = this.parentNode/*tbody*/.parentNode.parentNode/*form*/.parentNode/*div-ajax-content*/.parentNode/*div-target-container*/; o.style ? o.style.display='none' : o.setAttribute('style', 'display:none;')\">"
					  + "<th>Belongs to Sheet</th>"
					  + "<th>Origin</th>"
					  + "<th>Exercise/Solution</th>"
					  //+ "<th>Corresponding Filelink (Solution to Exercise or the Exercise to the Solution)</th>"
					  + "<th>Lecturer</th>"
					  + "<th>Uploaded By</th>"
					  + "<th>Split Pattern</th>"
					  + "</tr>"
			);


			String lecturer = request.getParameter("lecturer");
			if (lecturer != null) {
				lecturer = new String(request.getParameter("lecturer").getBytes("ISO-8859-1"), "UTF-8");
				lecturer = URLDecoder.decode(request.getParameter("lecturer"), "utf-8");
			}

			String str_query5 = "SELECT e.sheetdraft_filelink"
					+ ", e.originsheetdraft_filelink"
					+ ", e.filelink"
					+ ", e.splitby"
					+ (lecturer == null ?  ", l.lecturer" : "")
					+ ", author"
					+ " FROM exercise e, sheetdraft"
					+ (lecturer == null ?  ", lecturer l" : "")
					+ " WHERE sheetdraft.filelink = '" + filelink + "'"
					+ (lecturer == null ?  " AND l.id = sheetdraft.lecturer_id" : "")
					+ " AND sheetdraft.filelink = e.sheetdraft_filelink"
					/* filelink is a index => unique for each exercise.
					+ " AND semester = '" + semester + "'"
					+ " AND course = '" + course + "'"
					+ " AND type = '" + type + "'"
					+ " AND e.sheetdraft_id = sheetdraft.id"
					*/
					;
			ResultSet res1 = statement1.executeQuery(str_query5);
			//--------------------Datei -----------------------
			while (res1.next()) {
				//String aufg_id = res1.getString("id");
				String exercise_filelink = res1.getString("filelink");
				// Get other missing variables for the table or a possible edit
				String originsheetdraft_filelink = res1.getString("originsheetdraft_filelink");
				String sheetdraft_filelink = res1.getString("sheetdraft_filelink");
				//String lecturer = res1.getString("lecturer"); given per request to save the join
				uploader = res1.getString("author");
				String exercise_splitby = res1.getString("splitby");

				out.println("<tr>");


				out.println("<td>" + sheetdraft_filelink + "</td>");
				out.println("<td>" + originsheetdraft_filelink + "</td>");
				out.println("<td><a href='" + exercise_filelink
						+ "' class='screenshot' rel='" + Global.convertToImageLink(exercise_filelink)
						+ "'><i class='icon-download'></i> <i class='icon-eye-open'></i>"
						+ Global.extractExercisePartOnlyFromExerciseFilelink(exercise_filelink) /*+ "." + Global.extractEnding(exercise_filelink)*/
						+ "</a></td>");
				out.println("<td>" + lecturer + "</td>");
				out.println("<td>" + uploader + "</td>");
				out.println("<td>" + exercise_splitby + "</td>");
				//out.println("<td><input name='aufg_id[]' value='"
				//		+ aufg_id + "' type='checkbox'/></td>");
				out.println("<td rowspan='2'><input id='exerciseRowAction_mark"+ res1.getRow() +"'"
						+ " name='exercise_filelinks[]' value='" + exercise_filelink + "' type='checkbox'"
						+ " title='select' /></td>");

				out.println("</tr>");
				out.println("<tr>");

				out.println("<td colspan='6'><label for='exerciseRowAction_mark"+ res1.getRow() +"'>"
						+ "<img src='" + Global.convertToImageLink(exercise_filelink)
						+ "' alt='inline preview image' title='Diese Aufgabe ausw&auml;hlen.' />"
						+ "</label>"
						+ "<!-- Exercise optical separator --><hr /></td>");


				out.println("</tr>");
			}


			//Merely for the exercise listing
			%>
				</table><!-- EXERCISE LISTING -END -->

				<!-- EDIT BUTTONS -->
				<div id="edit_buttons" style="padding: 5px; position: fixed; bottom: 0;">
					<!-- STEER THE SHIP (now set in the action.add_to_draft automatically)
					<input type="hidden" name="id" value="drafts" />
					-->
					<!-- BUTTONS FOR ADDING EXERCISES TO A DRAFT -->
					<jsp:include page="buttons.add_to_draft.jsp">
						<jsp:param name="session_user" value="<%=session_user %>" />
					</jsp:include>
				</div>

			<%


			}



		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			// if this happens it will likely fail before. So this is just to be safe (e.g. if it becomes null by accident).
			if (statement1 != null) {
				statement1.close();
			}

		}
		%>





	</form>

</div>

