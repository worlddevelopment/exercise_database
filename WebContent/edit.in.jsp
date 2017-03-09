<%@page
	import="java.util.*"
	import="java.text.*"
	import="aufgaben_db.*, aufgaben_db.DocType"
	import="java.net.URLDecoder"
	import="java.sql.ResultSet"
%>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");

// Preconditions not met?
if (request.getParameter("filelink") == null) {
	Global.addMessage("No filelink or sheetdraft id was given. Without"
			+ " that, nothing can be edited. Use the treeview"
			+ " of the start page for editing sheetdrafts.", "warning");
	return ;
}


%>
	<jsp:include page="js/validateForm.js.jsp" />
<%




String filelink = URLDecoder.decode(request.getParameter("filelink"), "utf-8");

// The values as so far assignment made available for later use.
String semester = "";
String course = "";
int lecturer_id = 0;
String lecturer = "N.N.";
String type = "";//request.getParameter("type");

String description = "";


// LOAD required data from database (exactly one sheet/draft).
ResultSet res = Global.query(
		"SELECT sheetdraft.*, lecturer.lecturer"
		+ " FROM sheetdraft, lecturer"
		+ " WHERE filelink = '" + filelink + "'"
		+ " AND lecturer.id = sheetdraft.lecturer_id"
);

while (res.next()) {
	semester = res.getString("semester");
	course = res.getString("course");
	lecturer_id = res.getInt("lecturer_id");
	lecturer = res.getString("lecturer");
	type = res.getString("type");

	description = res.getString("description");
}

// Tackle memory leaks by closing result set and its statement properly:
Global.queryTidyUp(res);
%>






<!-- FORM -->
<div id="form">
<!-- MULTIPART CONTENT FORM
request.getAttribute("attr") will be NULL if not in action part!!! if multiform/form-data
see also http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
-->
<form class="cmxform" id="commentForm" method="post"  action="index.jsp?id=edit&q=edit_sheet#add_result">
	<!-- To steer the ship. -->
	<!--
	<input type="hidden" name="id" value="edit" /><!-- load the edit page -- >
	<input type="hidden" name="q" value="edit_sheet" />
	-->
	<p class="auto-style1 screenshot"><%=Global.display("edit sheet")%>:
		<a id="info_tooltip" href='#' rel="tooltip" title="alphanumeric and _ . space / ) ( symbols">
			<img src="pictures/info3.png" />
		</a>
	</p>
	<table >

		<!-- SEMESTER -->
		<tr>
			<td><label for="semester"><%=Global.display("semester")%>: *</label></td>
			<td>
				<jsp:include page="input.semester.jsp">
					<jsp:param name="default" value="<%=semester %>"></jsp:param>
				</jsp:include>
			</td>

		</tr>


		<!-- COURSE -->
		<tr>
			<td><label for="course"><%=Global.display("course")%>: *</label></td>
			<td>
				<jsp:include page="input.course.jsp">
					<jsp:param name="default" value="<%=course %>"/>
					<jsp:param name="node_id" value="course_edit"/>
				</jsp:include>
			</td>

		</tr>


		<!-- TYPE -->
		<tr>
			<td><label for="type"><%=Global.display("type")%>: *</label></td>
			<td>
				<jsp:include page="input.type.jsp">
					<jsp:param name="default" value="<%=type %>"/>
				</jsp:include>
			</td>
		</tr>


		<!-- LECTURER ID (html id different because otherwise
		a DOM conflict with upload overlay occurs) -->
		<tr>
			<td><label for="lecturer"><%=Global.display("lecturer")%>: *</label></td>
			<td>
				<jsp:include page="input.lecturer.jsp">
					<jsp:param name="default" value="<%=lecturer_id %>"/>
					<jsp:param name="lecturer" value="<%=lecturer %>"/>
					<jsp:param name="node_id" value="lecturer_edit"/>
				</jsp:include>
			</td>
		</tr>


		<!-- DESCRIPTION -->
		<tr>
			<td><label for="description"><%=Global.display("description")%> &amp; Tags: </label></td>
			<td colspan="2">
				<jsp:include page="input.generic.jsp">
					<jsp:param name="name" value="description"></jsp:param>
					<jsp:param name="default" value="<%=description %>"></jsp:param>
				</jsp:include>
			</td>
		</tr>


		<!-- EMPTY -->
		<tr>
			<td colspan="2">
			</td>
		</tr>


		<!-- FILE -->
		<tr>
			<td><label for="File"><%=Global.display("file")%>: * <span class="anno">(currently not editable)</span></label></td>
			<td><input class="falseFile" disabled="disabled" name="File" type="" type_that_will_change_action.edit_sheet="file" value="<%=Global.extractFilename(filelink) %>" /></td>
		</tr>
		<tr>
			<td>
				<!-- FILELINK -->
				<input type="hidden" name="filelink" class="" value="<%=filelink %>"/>
			</td>
			<td>
				<!-- SUBMIT -->
				<button name="q" type="submit" value="edit_sheet" class="btn btn-primary"
					onclick="show_loader();" style="font-size : 16px;">OK</button>
			</td>
		</tr>

	</table>

 </form>
 <br/>
 <small><b>* <%=Global.display("required information")%></b></small>
</div>
<%
//if (request.getParameter("form") != null
//&& request.getParameter("form").equals("multi")) {
%>
	<!--jsp:include page='action.upload.jsp' /-->
<%
//}
%>
	<p id="add_result"></p>

