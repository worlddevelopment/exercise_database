<!--
<script type="text/javascript" src="jquery/toolTipPreview.js"></script>
-->
<%@page import="swp.splitting_functions.*" %>
<%@page import="java.util.ArrayList,java.util.List"%>
<%@page import="java.sql.ResultSet,
                java.sql.SQLException,
                aufgaben_db.Global" %>
<!-- theoretically this bean works, unfortuneately it does not fit into this site until now
<jsp:useBean id="draft" class="aufgaben_db.Sheetdraft" scope="session"></jsp:useBean>
-->
<%
// Preconditions
if (Global.session == null) {
	Global.addMessage("Session was null in page 'drafts'!", "danger");
	return ;
}

// Load all drafts.
ResultSet res = Global.query("SELECT * FROM sheetdraft, lecturer"
	    + " WHERE"
	    + " (author = '" + Global.session.getAttribute("user") + "'"
	        + " OR (lecturer.lecturer = '" + Global.session.getAttribute("user") + "' AND lecturer_id = lecturer.id)"
	    + " )"
	    + " AND (is_draft = 1 OR is_draft = '1');"
);


List<String> drafts = new ArrayList<String>();
// LIST ALL DRAFTS, CREATE BUTTONS FOR OPERATIONS TOO.
String draft_filelink;
while (res.next()) {
    // Make the filelinks of the drafts available.
    draft_filelink = res.getString("filelink");
	drafts.add(draft_filelink);
	%>
	<!-- DRAFTFORM -->
	<form action="" method="post" class="draftform">
    <div class="draftrow">
    
        <!-- DRAFTDATA -->
        <div class="draftdata">
		    <input type="hidden" name="draft_filelink" value="<% out.print(draft_filelink); %>" />
		    <div id='draft_img'>
		        <img src="<% out.print(Global.convertToImageLink(draft_filelink)); %>" />
	        </div>
	        <button name="q" value="delete_draft" class="delete btn btn-danger" title="l&ouml;schen">
	           <i class="icon-trash icon-white"></i>
	        </button>
        </div>



	    <!-- EXERCISES TO DRAFT FORM -->
	    <div class="draftexerciserows">
	    <%
	    // Load exercises of the current draft item.
	    String str_query = "SELECT exercise.*"
	    		   + " FROM exercise, draftexerciseassignment dea"
	    		   + " WHERE dea.sheetdraft_filelink = '" + draft_filelink +"'"//select first
	    		   + " AND exercise.filelink = dea.exercise_filelink";//join remaining
	    ResultSet res1 = Global.query(str_query);
	    
	    while (res1.next()){
	    	String draft_exercise_filelink = res1.getString("filelink");
	    	%>
	    	<!-- DRAFT EXERCISE ROW -->
	        <div class="draft_exercise_row">
	            <input type="hidden" name="draft_exercise_filelink" value="<% out.print(draft_exercise_filelink); %>" />
	            <div id='draft_exercise_img'>
	                <img src="<% out.print(Global.convertToImageLink(draft_exercise_filelink)); %>" />
	            </div>
	            <button name="q" value="delete_draft_exercise" class="delete btn btn-danger" title="l&ouml;schen">
	               <i class="icon-trash icon-white"></i>
	            </button>
	        </div>
	        <%
	    }
	
	
	    %>
        </div><!-- EXERCISES TO DRAFT FORM -END -->  
    </div>  
    </form><!-- DRAFTFORM -END -->
    <%
	
	
	
	
	
	
	
	
	
}



//NO DRAFTS FOUND?
if(drafts == null || drafts.size() == 0) {
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


%>