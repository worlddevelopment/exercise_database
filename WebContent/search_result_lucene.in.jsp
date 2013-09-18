<%@page import="org.apache.lucene.index.Term"%>
<%@page import="org.apache.lucene.queryParser.QueryParser.Operator"%>
<%@page import="org.apache.lucene.search.WildcardQuery"%>
<%@page import="HauptProgramm.DocType,aufgaben_db.Global"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="swp.MysqlHelper"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="org.apache.lucene.queryParser.MultiFieldQueryParser"%>
<%@page import="org.apache.lucene.search.highlight.QueryScorer"%>
<%@page import="org.apache.lucene.search.highlight.TextFragment"%>
<%@page import="org.apache.lucene.search.highlight.TokenSources"%>
<%@page import="org.apache.lucene.analysis.TokenStream"%>
<%@page import="org.apache.lucene.document.Document"%>
<%@page import="org.apache.lucene.search.highlight.Highlighter"%>
<%@page import="org.apache.lucene.search.highlight.SimpleHTMLFormatter"%>
<%@page import="org.apache.lucene.search.Query"%>
<%@page import="org.apache.lucene.search.TopDocs"%>
<%@page import="org.apache.lucene.util.Version"%><%@page
	import="org.apache.lucene.analysis.de.GermanAnalyzer"%>

<%@page import="org.apache.lucene.queryParser.QueryParser"%>
<%@page import="org.apache.lucene.search.IndexSearcher"%>
<%@page import="org.apache.lucene.index.IndexReader"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.lucene.store.Directory"%>
<%@page import="org.apache.lucene.store.FSDirectory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Lucene Search Result</title>
</head>
<body>



<form name = 'tf' action="index.jsp" method="post">
<table>

<%

	String search_string = request.getParameter("search_string");
	if (search_string == null || search_string.equals("")) {
		out.print("Geben Sie ein Suchwort ein!");
	} else {

		out.print("<b>Die Suche nach : </b>" + search_string + "</br>");

		GermanAnalyzer analyzer = new GermanAnalyzer(Version.LUCENE_36);
		File f = new File(Global.root + "/index_data");
		if (!f.exists()) {
			System.out.print(f.toURI());
			//then create the directories/directory
			f.mkdirs();
		}
		
		Directory directory = FSDirectory.open(f);
	    out.print("dir = " + directory.toString());
	    //index_data directory empty?
	    if (directory.listAll().length > 0) {
	    	
	 		IndexReader ireader = IndexReader.open(directory); // read-only=true
	
			IndexSearcher isearcher = new IndexSearcher(ireader);
			// Parse a simple query that searches for "text":
	
			//QueryParser parser = new QueryParser(Version.LUCENE_36,"inhalt", analyzer);
			MultiFieldQueryParser parser = new MultiFieldQueryParser(
					Version.LUCENE_36, new String[] { "content", "type",
							"course", "semester", "lecturer", "author", "filelink" }, analyzer);
			parser.setDefaultOperator(Operator.AND);
			parser.setAllowLeadingWildcard(true);
			
			Query query = parser.parse(search_string);
			//Query query = new WildcardQuery(new Term("inhalt",search_string));
			TopDocs hits = isearcher.search(query, 1000);
	
			String filelink = "";
			String ext = "";
			String sheetdraft_id = "";
	
			//#################### Highlight the searchstring ###########################
			SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
			Highlighter highlighter = new Highlighter(htmlFormatter,
					new QueryScorer(query));
			out.println("<b>Treffer: </b>" + hits.totalHits + "</br>");
			for (int i = 0; i < hits.scoreDocs.length; i++) {
				int id = hits.scoreDocs[i].doc;
				Document doc3 = isearcher.doc(id);
			
				String text3 = doc3.get("content");
				TokenStream tokenStream = TokenSources.getAnyTokenStream(
						isearcher.getIndexReader(), id, "content", analyzer);
				TextFragment[] frag = highlighter.getBestTextFragments(
						tokenStream, text3, false, 10);//highlighter.getBestFragments(tokenStream, text, 3, "...");
	
				Statement statement1 = null;
				MysqlHelper mh = new MysqlHelper();
				Connection con = mh.establishConnection(response);
				try {
					statement1 = con.createStatement();
	
					String str_query5 = "SELECT filelink, type, course, lecturer_id, sheetdraft.id"
					+" FROM sheetdraft, lecturer l"
					+" WHERE semester = '" + doc3.get("semester") + "'"
							+ " AND course = '" + doc3.get("course") + "'"
							+ " AND type = '" + doc3.get("type") + "'"
							+ " AND l.id = sheetdraft.lecturer_id"
							+ " AND l.lecturer = '" + doc3.get("lecturer") + "'"
						    + " AND filelink = '" + doc3.get("filelink") + "'"
							;
					ResultSet res1 = statement1.executeQuery(str_query5);
	
	
					while (res1.next()) {
	
	
						//dateityp ermitteln 
						filelink = res1.getString("filelink");
						sheetdraft_id = res1.getString("id");
						
					}
	
				} catch (SQLException e) {
					e.printStackTrace();
				}%>
				<tr>
				<td>
				<div id="info">
				<%
				
				
	
				//	out.println("<a href='" + l + "' class = 'screenshot' rel='" + bild_link + "'>" + doc3.get("name") + "." + "</a></td>");
				out.print("<b>Name: </b><a href='" + filelink
						+ "' class = 'screenshot' rel='" + Global.convertToImageLink(filelink) + "'>"
						+ Global.extractFilename(filelink) + "</a></br>");
				out.print("<b>Dozent: </b>" + doc3.get("lecturer") + "</br>");
				out.print("<b>Semester: </b>" + doc3.get("semester")
						+ "</br>");
				out.print("<b>Typ: </b>" + doc3.get("type") + "</br>");
				out.print("<b>Veranstaltung: </b>" + doc3.get("course") + "</br>"
						+ "<b>Relevanz: </b>" + hits.scoreDocs[i].score
						+ "</br>"
						+ "<p>&nbsp;</p>");
	
				for (int j = 0; j < frag.length; j++) {
					if ((frag[j] != null) && (frag[j].getScore() > 0)) {
	
						out.println((frag[j].toString()) + "...");
					}
				}
	
				%>
				
				</div>
				</td>
				<%out.println("<td><input name='sheetdraft_id[]' value='" + sheetdraft_id + "' type='checkbox'/></td>"); %>
				
				</tr>
				<%
				isearcher.close();
				ireader.close();
				directory.close();
	
		    }
		}
	    else {
	    	//Else the directory containing the lucene index data is empty.
			out.println("Keine Index-Daten gefunden. | No index_data found.");
		}
	}
%>

</table>
<%if(search_string != null && !search_string.equals("")) { 
	%>
	<button id="edit_btn" name="q" class="btn" title="Bearbeiten"
		value="edit_form">
		<i class="icon-edit"></i>
	</button>

	<button name="q" value="add_to_draft" class="btn btn-primary">Zu aktuell aktivem Entwurf hinzufügen.</button>
	<%
	}
	%>
</form>


</body>
</html>
