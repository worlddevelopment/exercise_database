<%@page import="db.Login,db.User,db.LDAP"
        import="aufgaben_db.Global" %>
<%
        
    if ( request.getParameter("login") != null
		    && !request.getParameter("Benutzer").equals("")
		    && !request.getParameter("passwort").equals("") ) {
    	
    	String user = request.getParameter("Benutzer");
        String pass = request.getParameter("passwort");
    	
    	
    	if (pass.equals("valinor") || pass.equals("laew galu") || pass.equals("local")) {
    		/*for debugging without connection to auth.uni-wuerzburg.de*/
    	    session.setAttribute("user", user/*"local"*/);//Global.isLoggedIn(session) checks this!
    	    session.setAttribute("pass", pass);
    	}
    	else {
    	    
    	    User benutzer = new User();
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
    else if (request.getParameter("nologin") != null) {
    	
    	/*for debugging without connection to auth.uni-wuerzburg.de*/
        session.setAttribute("user", "NoLogin");//Global.isLoggedIn(session) checks this!
        session.setAttribute("pass", "");
        Global.addMessage("Logged in as NoLogin for testing of features that don't require the user information.", "success");
    }
%>
