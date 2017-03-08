<%@page import="java.sql.ResultSet,java.net.URLEncoder,db.*,aufgaben_db.Global" %>


<%
boolean found = false;

String search_string = request.getParameter("search_string");


if (search_string == null) {
	out.print(Global.addMessage("Ohne Suchwort, findet es sich an keinem Ort.", "warning"));
	return ;
}

search_string = new String(search_string.getBytes("ISO-8859-1"), "UTF-8");
search_string = Global.encodeUmlauts(search_string);



//String str_query1 = "SELECT * FROM blatt WHERE content LIKE '%" + search_string +"%'";
//out.print(search_string);
   ResultSet res = Global.query(
   		 //OHNE BOOLEAN MODE : ES WEREDN KEINE ERGEBNISSE ANGEZEIGT WENN SCORE KLEINER ALS 0.5 IST
   		 //Use urldecoder() mysql custom function (see WEB-INF-deleted-libs) OR as we do encode inquiry!
   		"SELECT *"
   		 + " MATCH (plain_text)"
   		 + " AGAINST ('" + Global.encodeUmlauts(search_string) + "') AS score"
   		 //+ URLEncoder.encode(search_string, "utf-8") + "\"') AS score"
   		 + " FROM sheetdraft"
   		 + " WHERE MATCH (plain_text) AGAINST ('\"" 
   				+ Global.encodeUmlauts(search_string) + "\"' IN BOOLEAN MODE);"
   		 //+ URLEncoder.encode(search_string, "utf-8") + "\"' IN BOOLEAN MODE);"
	);
   
   if(res == null) {// || !res.next()) {
	    out.print(Global.display("no success") + ". res = " + res);
   	
   }
   else {
// 	  	res.beforeFirst();//Because above we already increase by one!!
	 	
	   //################## Durchsuche Blatt content ##################################
	   while(res.next()) {
		    String filelink = res.getString("filelink");
			String type = res.getString("type");
			String course = res.getString("course");
			String semester = res.getString("semester");
			String lecturer_id = res.getString("lecturer_id");
			String description = res.getString("description");
			String author = res.getString("author");
			//String whencreated = res.getString("whencreated");
			String score = res.getString("score");
			String content = res.getString("content");    //<-- TODO
			
			/* ResultSet res3 = Global.query("SELECT course FROM course WHERE id = '" + courseId + "'");
			res3.next();
			String course = res3.getString("course"); */
			
			ResultSet res4 = Global.query("SELECT lecturer FROM lecturer WHERE id=" + lecturer_id);
			if (res4 != null || res4.next()) {
				String  lecturer = res4.getString("lecturer");
				
				
				
				int start = content.indexOf(search_string);
				int laenge = search_string.length();
			   	int end = (start + laenge);
			   	//out.print("start" + start + "End: "+ end + "l&auml;nge : " + search_string.length());
			   	
			   	StringBuilder str_b = new StringBuilder(content);
			   	//str_b.replace(start, end, "<b>"+search_string+"</b>");
			   	//content = str_b.toString(); 
			   	//out.print(content);
			   	
				
			    out.print("<div>");
			    out.print("<a href='"+ filelink + "' class='screenshot' rel='" + Global.convertToImageLink(filelink) + "'>" + filelink  + "</a><input type='checkbox'/></br>");
			    out.print( "<small><b>Typ: </b>" + type + "</small></br>");
			    out.print( "<small><b>Semester: </b>" + semester + "</small></br>");
			    out.print( "<small><b>Kurs: </b>" + course + "</small></br>");
			    out.print( "<small><b>Dozent: </b>" + lecturer + "</small></br>");
			       out.print( "<small><b>Beschreibung: </b>" + description + "</small></br>");
			    out.print( "<small><b>Score: </b>" + score + "</small></br>");
			    //out.print( "<small><b>Content: </b></br></br>" + content + "</small></br><hr>");
			    out.print( "</div>");
			    //found = true;
			}
			// tackle memory leaks by closing result set and its statement properly:
	        Global.queryTidyUp(res4);
	   }
	   // tackle memory leaks by closing result set and its statement properly:
       Global.queryTidyUp(res);
 }
    
    
%>
