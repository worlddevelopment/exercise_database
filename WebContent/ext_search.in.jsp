<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");
%>
<%@page import="java.net.URLEncoder, java.sql.ResultSet"%>
<%@page import="java.util.Map, java.util.HashMap, java.util.List, java.util.ArrayList"%>
<%@page import="core.Global,core.DocType"%>

<script type="text/javascript">

$(document).ready(function() {
	$("#semester").autocomplete("get_autocomplete_data.jsp?change=semester", {
		width: 260,
		matchContains: true,
		selectFirst: false
	});
	$("#course").autocomplete("get_autocomplete_data.jsp?change=course", {
		width: 260,
		matchContains: true,
		selectFirst: false
	});
	$("#lecturer").autocomplete("get_autocomplete_data.jsp?change=lecturer", {
		width: 260,
		matchContains: true,
		selectFirst: false
	});
});

</script>
<script>

$(document).ready(function() {
	var hidden_ellements  = new Array();
	var j = "";

	if($("#search").val() =="") {
		//$("#hide1").hide();
		hidden_ellements.push(1);
		j = j+1;
	}
	if($("#semester").val() =="") {
		//$("#hide2").hide();
		j = j+2;
		hidden_ellements.push(2);
	}
	if($("#course").val() =="") {
		//$("#hide3").hide();
		j = j+3;
		hidden_ellements.push(3);
	}
	if($("#lecturer").val() =="") {
		//$("#hide4").hide();
		j = j+4;
		hidden_ellements.push(4);
	}
	if($("#type :selected").text() == "") {
		j = j+5;
		//$("#hide5").hide();
		hidden_ellements.push(5);
	}
	if($("#datei_typ :selected").text() == "") {
		//$("#hide6").hide();
		j = j+6;
		hidden_ellements.push(6);
	}

	if(hidden_ellements.length>0 && hidden_ellements.length<6 ) {

		$("#ausgabe").append("");
		for(var i= 0;i<=hidden_ellements.length;i++) {

			$("#hide" + hidden_ellements[i]).hide();
		}


		$("#showr").click(function (event) {
			event.preventDefault(); // sehr wichtig,somit wird die seite nicht neu geladen
			for(var i= 0;i<=hidden_ellements.length;i++) {

				$("#hide" + hidden_ellements[i]).show();

			}
			$("#showr").hide();
		});
	}
	else
	{
		$("#ausgabe").append("Geben Sie ein Suchwort ein!");
		$("#showr").hide();

	}

});
</script>


<div style="float: left;width: 30%">
<form id="ext_search_form" method="get" action="?id=ext_search&view=ext_search_result">
	<table >
		<tr id="hide0">
			<td ><label for="search"><%=Global.display("search term")%>: </label></td>
			<td style="width: 500px"><input name="search" id="search" type="text"
				style="min-width: 200px;font-size : 16px;" value ='<%
				//deal with special character (new as of v30.91) to fix that the search did not work
				String search_string = Global.getParameterEncoded(request, "search");
				if (search_string != null) {
					// reprint the query if is sent
					out.print(Global.decodeUmlauts(search_string));
				}
				%>'/></td>

		</tr>
		<tr></tr>
	<%
	for (int i = 0; i < Global.indexedFields.length; i++) {

		String field = Global.indexedFields[i];

		out.println("<tr id='hide" + (i + 1) + "'>");
		out.println("<td ><label for='" + field + "'>" + Global.toFairy(field) + ": </label></td>");
		%>
		<td style='width: 500px'>
		<%
		if ( request.getParameter(field) == null &&
				(field.equals("course") || field.equals("lecturer")) ) {
			%>
			<select name="<%=field %>" class="" id="<%=field %>"
					onchange="document.getElementById('<%=field %>').value=this.value">
				<%
				// fetch all distinct entries of this field from database
				ResultSet res = Global.query("SELECT DISTINCT `" + field + "` FROM `sheetdraft`, `lecturer`");
				// get length
				int resL = 0;
				List<Map<String, Object>> resRows = new ArrayList<Map<String, Object>>();
				if (res != null) {//res.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
					while (res.next()) {
						Map<String, Object> rowMap = new HashMap<String, Object>();
						rowMap.put(field, res.getString(field));
						resRows.add(rowMap);
					}
					resL = resRows.size();
				}
				// generate the option fields
				if (res == null || resL == 0) {
					out.print("<option selected='selected' disabled='disabled'>-------</option>");
				} else {
					for (Map<String, Object> resRow : resRows) {
						// add option
						out.print("<option value='"+ ((String)resRow.get(field)) +"'>"
								+ Global.decodeUmlauts((String)resRow.get(field))
								+ "</option>");
					}
					// Tackle memory leaks by closing result set and its statement properly:
					Global.queryTidyUp(res);
				}

				%>
		   </select>
		   <%
		   continue;
		}


		//else
		out.println("<input name='" + Global.indexedFields[i] + "' id='"
				+ Global.indexedFields[i] + "' type='text' style='min-width:200px;font-size:16px;' value='");

		// Deal with special character (new as of v30.91) to fix that the search did not work
		String parameter = Global.getParameterEncoded(request, Global.indexedFields[i]);
		if (parameter != null) {
			// reprint the query if is sent
			out.print(Global.decodeUmlauts(parameter));
		}

		out.println("'/></td></tr><tr></tr>");

	}
	%>

		<tr id="hide6">
			<td><label for="datei_typ">Dateityp: </label></td>
			<td style="width: 500px">
			<select style="min-width: 200px;font-size : 16px;" name="datei_typ" id="datei_typ">
				<option style="min-width: 200px;font-size : 16px;"></option>
				<%
				for (DocType docType : DocType.values()) {
				%>
					<option style="min-width:200px; font-size:16px;"
						<%if (request.getParameter("datei_typ") != null && request.getParameter("datei_typ").equals(docType.name().toLowerCase())){
							%>selected="selected"<%
									}%>
					><% out.print( Global.display(docType.name()) ); %></option>
				<%
				}
				%>
			</select></td>
		</tr>
		<tr>
		<td></td>
		<td >
			<input id='showr' style="min-width: 220px;font-size:16px;text-align:left" class="btn btn-secondary"
					value="<%=Global.display("more searchcriteria") %>"/>
		</td>
		</tr>
		<tr>
		<td></td>
		<td> <button type="submit" name="view" value="ext_search_result"><%=Global.display("search") %></button></td>
		</tr>
	</table>

</form>
</div>

<div style="float: right;max-width: 375px;">
	<div style="background-color: #49AFCD;padding: 0.4em">
	<h2 style="font-size: 0.7em;">Suchtipps</h2>
	</div>
	<div style="font-size: 0.7em;border: 1px solid #49AFCD;padding: 0.4em">
		<p></p>
		<strong><%=Global.display("upper and lower case")%></strong><%=Global.translate("are not distinguished at search")%>.
		<p></p>
		<strong>*</strong><%=Global.translate("replaces zero or more characters")%><br />
		<strong>?</strong><%=Global.translate("replaces exactly one character")%>
		<p></p>
		<strong>&lt;&gt;</strong>: <%=Global.translate("terms surrounded by quotation marks are searched exactly as entered")%>
		<p></p>
		<strong><%=Global.display("combination") + Global.translate("search term") + "+" + Global.translate("field(s)")%></strong>:
		<%=Global.display("the search is conducted within the contents of the file as well as in the database value of the corresponding field(s).")%>

	</div>
</div>
<div id="ausgabe" style="clear: both;width: 30%"></div>
<!--  <p id="add_result"><p> -->
<%
if (request.getParameter("view") != null
&& request.getParameter("view").equals("ext_search_result")) {

	%>
	<jsp:include page='ext_search_result.jsp?session_user=<% out.print(Global.getUserURLEncoded(session)); %>' />
	<%
}
%>
