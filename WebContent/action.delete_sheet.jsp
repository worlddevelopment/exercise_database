<%@page import="aufgaben_db.Global, aufgaben_db.Aufgaben_DB"%>
<%@page import="java.io.File, java.net.URLDecoder"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="aufgaben_db.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    if (request.getParameter("q") == null || !request.getParameter("q").matches("^delete.*")) {
    	return ;   /* IMPORTANT: Better not delete if this parameter is missing! */
    }
    

    //for multiple sheetdraft deletion at once:
    String [] values = request.getParameterValues("sheetdraft_filelinks[]");
    
    
    if (values == null) {
    	//Then delete only the filelink or id sent via request.
	    //for single sheetdraft deletion (had to be shifted here because of scope conflict)
        String filelink = request.getParameter("filelink");
    	
    	if (filelink == null) {
	    	System.out.print(
	    			Global.addMessage("No sheet selected for deletion.", "warning")
			);
		    return ;
    	}
    	//else
   		Aufgaben_DB.delete_sheet(filelink);
    	
    }
    else {
    	
		for (int i = 0; i < values.length; i++) {
			
			values[i] = URLDecoder.decode(values[i], "utf-8");
			Aufgaben_DB.delete_sheet(values[i]);

		} 
    }
        
    %>
 
 <!-- Update Index von Suchmaschine  --> 
<%@include file="action.update_index.jsp"%>
    