<%
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
%>
<%@page import="org.apache.lucene.index.Term"%>
<%@page import="org.apache.lucene.queryParser.QueryParser.Operator"%>
<%@page import="org.apache.lucene.search.WildcardQuery"%>
<%@page import="aufgaben_db.DocType, aufgaben_db.Global"%>
<%@page import="java.net.URLDecoder, java.net.URLEncoder"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="db.MysqlHelper"%>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>Lucene Search Result</title>
</head>
<body>
-->
<!-- TOOLTIP PREVIEW -->
<script src="jquery/toolTipPreview.js" type="text/javascript"></script>


<!-- MULTIPLE SELECTION FORM -->
<form name = 'tf' action="index.jsp" method="post">
<%


String search_string = request.getParameter("search_string");

if (search_string == null || search_string.equals("")) {
	out.println(Global.display("Please") + Global.translate("enter a search term"));
}

else {
	search_string = new String(search_string.getBytes("ISO-8859-1"), "UTF-8");
	// Another not working option is to encode using javascript prior to submitting the sheetdraft form
	//search_string = URLDecoder.decode(search_string, "utf-8");//"iso-8859-1");
	search_string = Global.encodeUmlauts(search_string);

	// For still having a different scope if this search_result is being included somewhere:
	// otherwise a duplicate variable error would occur:
	//String session_user = URLDecoder.decode(request.getParameter("session_user"), "utf-8");
	String session_user = Global.getUser(session);

	%>
		<!-- WRAPPER TABLE -->
		<table>
	<%
	out.println("<b>" + Globla.display("search result for") + ": </b><span id='show_search_string'>"
			+ Global.decodeUmlauts(search_string) + "</span></br>");

	// TODO Internationalize, e.g. act depending on Global.Language.
	GermanAnalyzer analyzer = new GermanAnalyzer(Version.LUCENE_36);
	File f = new File(Global.root + "index_data");
	if (!f.exists()) {
		System.out.print(f.toURI());
		// Then create the directories/directory
		f.mkdirs();
	}

	Directory directory = FSDirectory.open(f);
	System.out.print("Lucene directory = " + directory.toString());
	// Index_data directory empty?
	if (directory.listAll().length > 0) {

		Global.setIndexReader(IndexReader.open(directory)); // read-only=true

		IndexSearcher isearcher = new IndexSearcher(Global.getIndexReader());
		// Parse a simple query that searches for "text":

		//QueryParser parser = new QueryParser(Version.LUCENE_36,"inhalt", analyzer);
		MultiFieldQueryParser parser = new MultiFieldQueryParser(
				Version.LUCENE_36
				, Global.indexedFields
				, analyzer
		);
		parser.setDefaultOperator(Operator.AND);
		parser.setAllowLeadingWildcard(true);

		Query query = parser.parse(search_string);
		//Query query = new WildcardQuery(new Term("inhalt",search_string));
		TopDocs hits = isearcher.search(query, 1000);

		String filelink = "";
		String sheetdraft_id = "";
		String semester;
		String course;
		String lecturer;
		String type;

		// ####### Highlight the searchstring #######
		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
		Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
		out.println("<b>" + Global.display("hits") + ": </b>" + hits.totalHits + "</br>");
		for (int i = 0; i < hits.scoreDocs.length; i++) {
			int id = hits.scoreDocs[i].doc;
			Document doc3 = isearcher.doc(id);

			String text3 = doc3.get("content");
			TokenStream tokenStream = TokenSources.getAnyTokenStream(
					isearcher.getIndexReader(), id, "content", analyzer);
			TextFragment[] frag = highlighter.getBestTextFragments(
					tokenStream, text3, false, 10);//highlighter.getBestFragments(tokenStream, text, 3, "...");

			/*
			Statement statement1 = null;
			MysqlHelper mh = new MysqlHelper();
			Connection con = mh.establishConnection(response);
			try {
				statement1 = con.createStatement();

				String str_query5 = "SELECT filelink, semester, course, type"
						+ ", lecturer_id, lecturer, sheetdraft.id AS 'sheetdraft_id'"
						+ " FROM sheetdraft, lecturer l"
						+ " WHERE filelink = '" + doc3.get("filelink") + "'"
							+ " AND semester = '" + doc3.get("semester") + "'"
							+ " AND course = '" + doc3.get("course") + "'"
							+ " AND type = '" + doc3.get("type") + "'"
							+ " AND l.lecturer = '" + doc3.get("lecturer") + "'"
							+ " AND l.id = sheetdraft.lecturer_id"
						;
				ResultSet res1 = statement1.executeQuery(str_query5);

				// The only thing this code once wanted to fetch seems to be the sheetdraft id (bl_id)!
				while (res1.next()) {

					filelink = res1.getString("filelink");
					sheetdraft_id = res1.getString("sheetdraft_id");

					semester = res1.getString("semester");
					course = res1.getString("course");
					lecturer = res1.getString("lecturer");
					type = res1.getString("type");

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
			%>
				<tr>
				<td>
				<div id="info">
			<%


			//out.println("<a href='" + l + "' class = 'screenshot' rel='" + bild_link + "'>" + doc3.get("name") + "." + "</a></td>");
			out.print("<b>" + Global.display("filelink") + ": </b> <i class='icon-eye-open'></i>"
					+ " <a href='" + doc3.get("filelink")
					+ "' class='screenshot' rel='" + Global.convertToImageLink(doc3.get("filelink")) + "'>"
					+ Global.extractFilenameAndEnding(doc3.get("filelink")) +  "</a></br>");
			out.print("<b>" + Global.display("lecturer") + ": </b>" + Global.decodeUmlauts(doc3.get("lecturer")) + "</br>");
			out.print("<b>" + Global.display("semester") + ": </b>" + Global.decodeUmlauts(doc3.get("semester"))
					+ "</br>");
			out.print("<b>" + Global.display("type") + ": </b>" + Global.decodeUmlauts(doc3.get("type")) + "</br>");
			out.print("<b>" + Global.display("course") + ": </b>" + Global.decodeUmlauts(doc3.get("course")) + "</br>"
					+ "<b>" + Global.display("relevance") + ": </b>" + hits.scoreDocs[i].score
					+ "</br>"
					+ "<p>&nbsp;</p>");

			for (int j = 0; j < frag.length; j++) {
				if ((frag[j] != null) && (frag[j].getScore() > 0)) {

					out.println("<p class='search_result_fragment'>" + (frag[j].toString()) + "...</p>");
				}
			}

			%>

				</div>
				</td>
				<td><!--
					<input name='sheetdraft_id[]' value='<%out.print(sheetdraft_id);%>'
							type='checkbox'/>
					-->
					<input name='sheetdraft_filelinks[]' value='<%out.print(filelink);%>'
							type='checkbox'/>
				</td>

				</tr>
			<%

			// not to forget
			isearcher.close();
			//Global.getIndexReader().close();
			directory.close();

		}
	}
	else {
		// The directory containing the lucene index data is empty.
		out.println(Global.display("no index data found"));
	}

	%>
		</table><!-- WRAPPER TABLE -END -->

		<!-- BUTTONS FOR ADDING TO A DRAFT THAT IS ONE OUT OF A LOADED DRAFT LIST:  -->
		<jsp:include page="buttons.add_to_draft.jsp">
			<jsp:param name="session_user" value="<%=session_user %>" />
		</jsp:include>

	<%

}
%>


</form><!-- MULTIPLE SELECTION FORM -END -->

<!--
</body>
</html>
-->
