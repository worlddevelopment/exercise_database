<%@page import="swp.Login,swp.User,swp.LDAP"
        import="aufgaben_db.Global" %>
<%
    if ( request.getParameter("login") != null
		    && !request.getParameter("Benutzer").equals("")
		    && !request.getParameter("passwort").equals("")) {
		
    	String user = request.getParameter("Benutzer");
    	String pass = request.getParameter("passwort");
    	User benutzer = new User();
    	
    	if (pass.equals("valinor") || pass.equals("laew galu")) {
    		/*for debugging without connection to auth.uni-wuerzburg.de*/
    	    session.setAttribute("user", "faerietree");//Global.isLoggedIn(session) checks this!
    	    session.setAttribute("pass", pass);
    	}
    	else {
    		/*per auth.uni-wuerzburg.de*/
	    	Login log  = new Login();
	    	
	    	if(log.autanticate(user, pass)) {
	    		LDAP ldap = log.getLdap();
	    		//session.setAttribute("angemeldet", "yes"); //Now controlled via user var 
	    		session.setAttribute("user", benutzer.getName(ldap,user)); // in session.
	        	session.setAttribute("pass", pass);//Global.isLoggedIn(session) checks those
	        	//session.setAttribute("hiwi", "yes");
	    	}
	    	else {
	    		System.out.println("Login gescheitert.");
	    		Global.addMessage("Login gescheitert. Bitte versuchen Sie es erneut.",
	    				" nosuccess ");
	    	}
    	}
    }
%>
