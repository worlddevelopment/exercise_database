
<%@page import="java.net.InetAddress"%>
<%
	response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="java.io.*" %>
<%@page import="java.util.Hashtable" import="javax.naming.Context"
    import="javax.naming.NamingException"
    import="javax.naming.directory.DirContext"
    import="javax.naming.directory.InitialDirContext"
    import="javax.naming.ldap.LdapContext" import="swp.*"
    import="java.sql.Connection" import="java.sql.DriverManager"
    import="java.sql.ResultSet" import="java.sql.SQLException"
    import="java.sql.Statement" import="java.util.Enumeration"
    import="HauptProgramm.*"
    import="aufgaben_db.Global"
    import="aufgaben_db.Aufgaben_DB"
    %>
    <%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%@page import="org.apache.commons.fileupload.portlet.*"%>
<%@page import="org.apache.commons.fileupload.util.*"%>
<%@page import="java.util.*,java.util.*,java.io.File,java.lang.Exception" %>







<!-- ======= SETTINGS ================================================================ -->



<!-- DEFAULTS -->
<%
	//include file="defaults.jsp"//<!-- defines default Global.anzeige  (= pageToLoad), etc.  -->
//Default Login Seite/Anzeige
session.setAttribute("defaultAnzeige", "start");







//<!-- GLOBALS
//GLOBALS - DECLARE -->

	//GLOBALS - INIT
Global.angemeldet = (session.getAttribute("user") != null);
//out.println("Logged in: " + Global.angemeldet + "<br />");
//Zur Ermittlung, was anzuzeigen ist.
if (Global.anzeige  == null) {
    Global.anzeige  = "";
}
if (Global.id  == null) {
    Global.id  = "";
}
//find the real path of the WebContent and save as new root of our apple tree
Global.root = 
pageContext.getServletContext().getRealPath("."/*"WebContent"*/) + "/";
Global.session = session;

//initialize MySQL connection
//Global.msqh = new MysqlHelper();        /* DataBaseConnection */
Global.conn = Global.msqh.establishConnection(response);
//Global.sqlm = new SQL_Methods();
Global.sqlm.setConn(Global.conn);

//get calendar of now -> date/time
Global.now = GregorianCalendar.getInstance();

Global.response = response;





//<!-- DECIDE: Global.anzeige  -->
// Ermitteln, was anzuzeigen ist
//ID - SEITE | PAGE
if (request.getParameter("id") != null
        /*&& !ServletFileUpload.isMultipartContent(request)*/) {
    
    Global.anzeige = request.getParameter("id");
    /*remap*/
    //expand some ids - 
    if (request.getParameter("id").equals("search")) {
        Global.anzeige = "search_result_lucene";
    }
    
}





//SAVE TO SESSION - FOR LOADING THE LAST ACTIVE PAGE
if (Global.anzeige != null && Global.anzeige != "") {
  if (!Global.anzeige.equals(session.getAttribute("anzeige"))) {
      System.out.println("Anzeige-Variable in der Session wurde aktualisiert.");
      session.setAttribute("previousAnzeige", Global.anzeige);
  }
}
//DETERMINE NEW PAGE TO LOAD /ANZEIGE
if (request.getParameter("id") != null
		&& !request.getParameter("id").equals("")) {
	//load the last active page as actual page to load /anzeige
    Global.anzeige = request.getParameter("id").toString();
}
//if available
else if (session.getAttribute("anzeige") != null) {
	//load the last active page as actual page to load /anzeige
    Global.anzeige = session.getAttribute("anzeige").toString();
}
else {
	//load default
	Global.anzeige = session.getAttribute("defaultAnzeige").toString();
}
//save for next time
session.setAttribute("anzeige", Global.anzeige);

		
		
		


//USER ACTION
if (request.getParameter("q") != null) {
%>
    <jsp:include page="action.inc.jsp" />
<%
}


//AFTER THESE CHANGES LOAD ALL USER DATA
if (Global.angemeldet) {
	
	Aufgaben_DB.loadAllSheetdrafts();

}
%>





<!-- ======= FUNCTIONS =============================================================== -->
<%@ include file="functions.inc.jsp" %>








<!-- ======= MANIPULATION END ======================================================== -->
<!-- ======= READ GLOBALS ONLY BELOW THIS LINE ======================================= -->







<!-- ======= THE PAGE - HTML DOCUMENT ================================================ -->
<!DOCTYPE html>
<html >
<head>
<meta charset="UTF-8">


<!-- COMMON SCRIPTS -->
<!-- Bootstrap -->
<script src="bootstrap/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />

<!-- JQuery plugin -->
<script src="jquery/jquery-latest.js"></script>
<!-- 
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
-->
<!-- Autocomplete -->
<script type='text/javascript' src='jquery/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css" href="jquery/jquery.autocomplete.css" />

<!-- Furthermore -->
<script type='text/javascript' src='jquery/fairytale.js'></script>


<!-- Main Css -->
<link rel="stylesheet" href="css/style.css" />



<title>DMUW - Aufgaben DB</title>
</head>
<body id="body" onload="document.getElementsByTagName('input')[0].focus();">
       <!-- WRAPPER -BEGIN -->
    <div id="wrapper">
    <a href="index.jsp?id=start">
    <div id="kopf" class="gestaltung">
        <div style="/*font-variant:small-caps;*/
        font-size:2enpx;min-height:100px; width:400px; color:white; margin-left: 200px; margin-top: 20px;">
        Aufgaben Datenbank</div>
        <%
        if(Global.angemeldet) {
        //    out.println("LOGGED IN");
        %>
            <!--Platzhalter fuer autologout  -->
            <div id="max" style="position:absolute; top:25px;right:100px; width:200px; height:25px;color: white; font-weight: bold;">
            </div>
        <%
        }
        %>
    </div>
    </a>


    <div id="navi" class="gestaltung">
        <%
        if (!Global.angemeldet) { %>
            <form name = 'login_kasten' method='post' action='index.jsp' autocomplete='off'>
                <input name='Benutzer' type='text' class='login' placeholder='Benutzer'/>
                <input style='margin-left:5px;' name='passwort' type='password' class='login' placeholder='Passwort'/>
                <button name='login' class='btn btn-success' value='Login' style='margin-left:5px;'>Login</button>
                <input type='hidden' name ='q' value= 'login' />
            </form>
        <%
        } else {

            out.println("Hallo " + session.getAttribute("user") + "!");
            %>
            <%@include file="menue.tpl.jsp" %>
            <%
        }
        %>




        
    </div>

    <!-- INHALT -->
    <div id="inhalt" class="gestaltung">
        
        
        <table style="width:100%;">
            <tr>
                <td>
                <%
                //preparation
                String fileToInclude = "";//start.jsp ist der Default-wert
                String loggedInAddition = "";
                String loggedInPath = "";
                //
                if (Global.angemeldet) {
                	//nach dem login inkludiere start.in.jsp anstatt start.jsp;
                	//ID-Attribut wird im login.jsp gesetzt(Session)
                	loggedInAddition = ".in";
                	loggedInPath = "";//"in/";
            	}
                
                //nach dem login inkludiere start.in.jsp anstatt start.jsp;
                //ID-Attribut wird im login.jsp gesetzt(Session)
                if (!Global.anzeige.equals("")) {
                    fileToInclude = loggedInPath + Global.anzeige + loggedInAddition + ".jsp";
                    File f = new File(pageContext.getServletContext().getRealPath(fileToInclude));
                    if (!f.exists()) {
                    	Global.message += "<h1>Fehler 404 | Error 404</h1><p>&nbsp;</p><p>&nbsp;</p>"
                    			+ "<p>Die angeforderte Seite wurde nicht gefunden!</p>"
                    			+ "<p><b>Eingeloggt?</b><br /><p>Die Seite " + Global.anzeige + " konnte nicht aufgerufen werden.<br />"
                    		    + "Melden Sie sich bitte (erneut) beim System an.</p>"
                    		    ;
                    }
                    else {
	                    //out.println("including file = " + fileToInclude);
	                    %>
	                    <jsp:include page='<%= fileToInclude %>' />
	                    <%
                    }
                }
                
                //PURPOSE? WHY NOT GIVE ext_search per ID? TODO
                //Why not show something special as overlay?
                if (request.getParameter("show") != null){//for debugging/determining purpose!
                    out.println("FYI, show variable is set! show = " + request.getParameter("show"));
                }
                //PURPOSE?
                //view something special as overlay?
                if (request.getParameter("view") != null){//for determining the purpose!
                    out.println("FYI, view variable is set! view = " + request.getParameter("view"));
                    //fileToInclude = loggedInPath + request.getParameter("view") + loggedInAddition + ".jsp";
                    %>
                    <!-- rename aendern -> edit -->
                    <!--jsp:include page='<%= fileToInclude %>' /-->
                    <%
                }
                

        
        %>
                
                </td>
            </tr>
        </table>
    </div>
    <!-- GLOBAL MESSAGE -->
    <div class="messageWrapper <%= Global.messageClass %>">
        <%= Global.getMessage() %>
    </div>
    <!-- INHALT -E -->



<!-- Autologout  -->	
<%
/* AUTOLOGOUT IF NOT ALREADY AUTO-LOGGED-OUT AND LOGGED IN: */
//if (!request.getAttribute("q").equals("logout")) {
if (Global.angemeldet) {

	int seconds = session.getMaxInactiveInterval();
	String hostname = request.getLocalName();
	
	%>
	<script type="text/javascript">
	var host = window.location.hostname;
	var port = window.location.port;
	var path = window.location.pathname;
	var initi = window.setInterval ('downcount()', 1000);
	var text = 'Autologout ';
	var zahl = <%=seconds%>;
	
	
	var dokument="http://" + host + ":" + port  + "" + path + "?q=logout";
	function downcount()
	{
	  zeige = text + 'in ' + zahl + ' Sekunden';
	  window.status = zeige ;
	
	  document.getElementById('max').innerHTML = zeige ;
	  zahl --;
	  if (zahl < 0 ) {
	    location.href = dokument;
	  }
	}
	
	
	/*Use these two functions to add an onclick event for preventing the timeout from
	expiring and logging out in the middle of a setup what is very undesirable.*/
	addEvent(document, 'onclick', (function() {  zahl = <%=seconds%>;  }));
	
	
	
	</script>
		<!--<div id = "platzhalter"></div> -->
<%
} /* AUTOLOGOUT IF NOT ALREADY AUTO-LOGGED-OUT AND LOGGED IN. -E */
%>





	<div id="fuss" class="gestaltung">

		<%
		
		//experiment
		/* String realPath = 
		pageContext.getServletContext().getRealPath("WebContent/" + Global.anzeige);
		File f = new File(realPath);
		if (f.exists()) {
			for (File file : f.listFiles()) {
				  out.println(file.toString());
			}
		}
		else {
			System.out.println("No file found.");
		} */
		
     	//out.println(anzeige);
		 out.println(Global.anzeige  + "\t|\t<span class=\"lastModified\">Letzte Überarbeitung: "
     	+ Global.filemtime(session, loggedInPath
     	 		+ Global.anzeige + loggedInAddition + ".jsp", pageContext) + "</span>");
		%>

	</div>
	</div>
</body>
</html>