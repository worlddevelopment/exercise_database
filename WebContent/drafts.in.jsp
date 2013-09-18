<!--
<script type="text/javascript" src="jquery/toolTipPreview.js"></script>
-->
<%@page import="swp.splitting_functions.*" %>
<%@page import="java.util.ArrayList"%>
<%@page import="swp.SQL_Methods"%>
<%@page import="swp.MysqlHelper,
                java.sql.Connection,
                java.sql.DriverManager,
                java.sql.ResultSet,
                java.sql.SQLException,
                java.sql.Statement,
                aufgaben_db.Global" %>
<jsp:useBean id="draft" class="aufgaben_db.Sheetdraft" scope="session"></jsp:useBean>
<%


ArrayList<String> aufg_ids = draft.getAufg_ids();
ArrayList<String> bl_ids = draft.getBl_ids();
if(aufg_ids == null || bl_ids == null) {
	out.print("Ihre Entwurfskammer beinhaltet zur Zeit keine &Uuml;bungsblätter.");
	out.print("<br />");
	out.print("<form method='get' action='?id=drafts'>"
    + "<button class='btn btn-secondary' name='q' value='create_draft'>Entwurf erstellen!</button>"
			+ "</form>");
	out.print("<br />");
	out.print("<br />Gehen sie anschließend auf die <a href='?id=start'>Startseite</a> und f&uumlgen Sie Aufgaben von anderen Bl&auml;ttern zu Ihrem aktuell aktiven Entwurf hinzu.");
	out.print("<br />Es wird versucht, weitestgehend die wichtigsten Formatierungen zu erhalten.");
	out.print("<br />Einzig TeX- oder PDF-Dokumente können derzeit erstellt werden.");
	out.print("<br />Auf dieser Seite können Sie auch Aufgaben entfernen und ganze Entwürfe/Aufgabensammlungen löschen.");
}
else {




%>
<form action="index.php" method="post">
<table>
<%
String b_id = "";
for(int i = 0;i < bl_ids.size();i++) {
	

	b_id = bl_ids.get(i);

	Statement statement1 = null;
	
	
	
	MysqlHelper mh = new MysqlHelper();
    Connection con = mh.establishConnection(response);
	try {
		
		//Aufgaben
		statement1 = con.createStatement();
		String str_query = "SELECT content, filelink FROM sheetdraft WHERE id = '" + b_id +"'";
		statement1.execute(str_query);
	    ResultSet res1 = statement1.getResultSet();
		
		while (res1.next()){
			String inhalt = res1.getString("content");
			String filelink = res1.getString("filelink");
			out.print("<tr><td><input name = 'bl_id[]' value = '" + b_id + "' type='checkbox'/></td>");
			out.print("<td><div  id = 'draft_img'><img src='" + Global.convertToImageLink(filelink) + "'/></div></td></tr>"); 
		}
	}
	catch (SQLException e) {
        e.printStackTrace();
    }
}

String aufg_id = "";
for(int i = 0;i < aufg_ids.size();i++) {
	

	aufg_id = aufg_ids.get(i);
	Statement statement1 = null;
	
	
	
	MysqlHelper mh = new MysqlHelper();
    Connection con = mh.establishConnection(response);
	try {
		
		//Aufgaben
		statement1 = con.createStatement();
		String str_query = "select inhalt from aufgabe where id = '" + aufg_id +"'";
		statement1.execute(str_query);
	    ResultSet res1 = statement1.getResultSet();
		

		while (res1.next()){
			String inhalt = res1.getString("inhalt");
			out.print("<tr><td><input name = 'bl_id[]' value = '" + aufg_id + "' type='checkbox'/></td>");
			out.print("<td><div  id = 'draft_img'>" + inhalt +"</div></td></tr>"); 
		}
	}
	catch (SQLException e) {
        e.printStackTrace();
    }
}


%>
</table>
<button name="q" value="draft_delete_exercise_former_hinz">Löschen</button>
</form>
<%
}
%>