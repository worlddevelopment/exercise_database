<%@page import="core.Global, java.sql.ResultSet" %>

<%
String id = "lecturer";
if (request.getParameter("node_id") != null) {
	id = request.getParameter("node_id");
}
String default_ = "";
if (request.getParameter("default") != null) {
	default_ = request.getParameter("default");
	%>
		<input type="hidden" name="lecturer_id_old" class="required noSpecialChars"
				id="<%= id %>_id_old" value="<% out.print( default_ ); %>"
				onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'lecturer_id_select');"
		/>
	<%
}

if (request.getParameter("lecturer") != null) {
	%>
		<input type="text" name="lecturer" class="required noSpecialChars" style="display:none;"
				id="<%= id %>" value="<% out.print( Global.decodeUmlauts(request.getParameter("lecturer")) ); %>"
				onkeyup="deactivateIfFilledOrActivateIfEmpty(this, '<%= id %>_id_select');"
		/>
	<%
}
else {
	%>
		<input type="text" name="lecturer" class="required noSpecialChars"
				id="<%= id %>" value=""
				onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'lecturer_id_select');"/>
	<%
}
%>
	<select name="lecturer_id_select" class="" id="<%= id %>_id_select"
			onchange="document.getElementById('<%= id %>').value=this.options[this.selectedIndex].text; <%= request.getParameter("onchange") %>;">
<%
// Fetch all distinct lecturers from database:
ResultSet res = Global.query("SELECT `id`, `lecturer` FROM `lecturer`" /*LIMIT 0, 30*/);
if (res == null) {// || !res.next()) {
	out.print("<option disabled='disabled'>---(leer)---</option>");
}
else {
	String lecturer_id;
	res.beforeFirst();// Because above we already increase by one!
	out.print("<option disabled='disabled'>----------</option>");
	while (res.next()) {
		// Add option:
		lecturer_id = res.getString("id");
		if (res.getString("id") == null) {
			System.out.println(Global.addMessage("Warning! Lecturer id is null. id: " + res.getString("id"), "warning"));
			lecturer_id = "0";
		}
		out.print("<option ");
		if (request.getParameter("default") != null
				&& (lecturer_id.equals(default_)) ) {
			out.print(" selected='selected'");
		}
		out.print("value='"+ lecturer_id + "," + res.getString("lecturer") + "'>");
		out.print( Global.decodeUmlauts(res.getString("lecturer")) );
		out.print("</option>");
	}
	// Tackle memory leaks by closing result set and its statement properly:
	Global.queryTidyUp(res);
}
%>
	</select>

