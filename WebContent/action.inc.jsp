<%@page import="aufgaben_db.Global" %>
<%



    //SWITCH QUEST
    String q = request.getParameter("q");
    Global.addMessage("q = " + q, "info");
    
	if (q.equals("logout")) {//LOGOUT
		session.removeAttribute("user");
		Global.anzeige = session.getAttribute("defaultAnzeige").toString();
		Global.angemeldet = false;
	}
	//if (request.getParameter("q").equals("login")) {  //LOGIN
    //CREATE DRAFT
    else if (q.equals("create_draft")) {
		/*Aufgaben_DB.addSheetdraft(new Sheetdraft(
				"filelink",
				
				));
		
				 */
		 if (true) {
			 Global.message += "<div class='success'>Entwurf erfolgreich erstellt.</div>";
		 }
	}
	else {
	        %>
	        <jsp:include page='<%= ("action." + q + ".jsp") %>' />
	        <%
	       
	    //}
	}

%>