<%
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
%>
<%@page import="org.apache.lucene.search.vectorhighlight.FieldQuery"%>
<%@page import="org.apache.lucene.search.Explanation"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.lucene.search.BooleanClause"%>
<%@page import="org.apache.lucene.LucenePackage"%>
<%@page import="org.apache.lucene.search.TermQuery"%>
<%@page import="org.apache.lucene.search.BooleanQuery"%>
<%@page import="org.apache.lucene.index.Term"%>
<%@page import="org.apache.lucene.queryParser.QueryParser.Operator"%>
<%@page import="org.apache.lucene.search.WildcardQuery"%>
<%@page import="aufgaben_db.DocType"%>
<%@page import="aufgaben_db.Global"%>
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



<form name='tf' action="index.jsp" method="post">
	<table>
		<%
			String fields[] = new String[] {
			        "content", "semester", "course", "lecturer", "type"
			        };
			int treffer = 0;
			HashMap<String, String> suchfelder = new HashMap<String, String>();
			String search_query = "";
			
			//deal with special character (new as of v30.91) to fix that the search did not work
			String search = Global.getParameterEncodedOrEmpty(request, "search", "search_string");
			String semester = Global.getParameterEncoded(request, "semester");
			String course = Global.getParameterEncoded(request, "course");
			String lecturer = Global.getParameterEncoded(request, "lecturer");
			String type = Global.getParameterEncoded(request, "type");
 			//String datei_typ = request.getParameter("datei_typ");
            //TODO:MAKE DOCTYP SEARCHABLE
		            
			if (search != null && !search.equals("")
				    && (semester == null || semester.equals(""))
					&& (course == null || course.equals(""))
					&& (lecturer == null || lecturer.equals(""))
					&& (type == null || type.equals(""))) {
				search_query = search;
			} else {

			            
				if (!search.equals("")) {
					//suchfelder.put("inhalt", search); 
					search_query = search + " ";
				}
				
				if (semester != null && !semester.equals("")) {
					suchfelder.put("semester", Global.encodeUmlauts(semester));
				}

				if (course != null && !course.equals("")) {
					suchfelder.put("course", Global.encodeUmlauts(course));
				}

				if (lecturer != null && !lecturer.equals("")) {
					suchfelder.put("lecturer", Global.encodeUmlauts(lecturer));
				}

				if (type != null && !type.equals("")) {
					suchfelder.put("type", Global.encodeUmlauts(type));
				}

			}

			String root = Global.root;//


			int count = 0;
			//search_query = "";
			for (Map.Entry<String, String> pairs : suchfelder.entrySet()) {

				count++;
				if (pairs.getValue() != null && !pairs.getValue().equals("")) {

					if (count == suchfelder.size()
							|| (count == 1 && suchfelder.size() == 1)) {
						search_query += pairs.getKey() + ":" + pairs.getValue()
								+ " ";
					} else {
						search_query += pairs.getKey() + ":" + pairs.getValue()
								+ "  AND ";
					}
				}

			}
			//out.print("Query: " + search_query + "</br>");
			if (suchfelder.size() != 0 || !search.equals("")) {
				//out.print("<b>Die Suche nach : </b>" + search_string + "</br>");

				GermanAnalyzer analyzer = new GermanAnalyzer(Version.LUCENE_36);

				Directory directory = FSDirectory.open(new File(root + "index_data"));

				Global.setIndexReader(IndexReader.open(directory)); // read-only=true

				IndexSearcher isearcher = new IndexSearcher(Global.getIndexReader());
				// Parse a simple query that searches for "text":

				//QueryParser parser = new QueryParser(Version.LUCENE_36,"inhalt", analyzer);
				//####	
				MultiFieldQueryParser parser = new MultiFieldQueryParser(
						Version.LUCENE_36, fields, analyzer);
				parser.setDefaultOperator(Operator.AND);
				parser.setAllowLeadingWildcard(true);
				//String kriterium = " AND course:Didaktik";

				Query query = parser.parse(search_query);// "(+inhalt:uebung +semester:\"SoSe 12/14\")"
				out.println("</br><strong>Query: </strong>" + Global.decodeUmlauts(search_query)
						+ "</br><p></p>");
				//#####			

				TopDocs hits = isearcher.search(query, 1000);

				String filelink = "";
				String sheetdraft_id = "";

				//#################### Highlight the searchstring ###########################
				SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
				Highlighter highlighter = new Highlighter(htmlFormatter,
						new QueryScorer(query));

				treffer = hits.totalHits;

				out.println("<b>" + Global.display("hits") + ": </b>" + hits.totalHits + "</br>");
				for (int i = 0; i < hits.scoreDocs.length; i++) {

					int id = hits.scoreDocs[i].doc;
					Document doc3 = isearcher.doc(id);
					String text3 = doc3.get("content");
					TokenStream tokenStream = TokenSources.getAnyTokenStream(
							isearcher.getIndexReader(), id, "content", analyzer);
					TextFragment[] frag = highlighter.getBestTextFragments(
							tokenStream, text3, false, 10);//highlighter.getBestFragments(tokenStream, text, 3, "...");

					/* Statement statement1 = null;
					MysqlHelper mh = new MysqlHelper();
					Connection con = mh.establishConnection(response);
					try {
						statement1 = con.createStatement();

						String str_query5 = "SELECT sheetdraft.id"
						        + " FROM sheetdraft, lecturer l, course"
						        + " WHERE semester = '"	+ doc3.get("semester") + "'"
						        + " AND course = '" + doc3.get("course") + "'"
						        + " AND type = '" + doc3.get("type") + "'"
						        + " AND l.lecturer = '" + doc3.get("lecturer") + "'"
						        + " AND l.id = sheetdraft.lecturer_id "
								+ " AND sheetdraft.filelink = '" + doc3.get("filelink") + "'"
								;
						ResultSet res1 = statement1.executeQuery(str_query5);

						while (res1.next()) {

							sheetdraft_id = res1.getString("id");
							
						}
						// added as of v31.13c to tackle memory leaks:
						if (res1 != null) {
							res1.close();
						}
						if (statement1 != null) {
							statement1.close();
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					} */
		%>

		<tr>
			<td>

				<div id="info">
					<%
					out.print("<small>");

					//	out.println("<a href='" + l + "' class = 'screenshot' rel='" + bild_link + "'>" + doc3.get("name") + "." + "</a></td>");
					out.print("<b>" + Global.display("filelink") + ": </b><a href='" + doc3.get("filelink")
							+ "' class = 'screenshot' rel='" + Global.convertToImageLink(doc3.get("filelink")) + "'>"
							+ Global.extractFilenameAndEnding(doc3.get("filelink")) + "</a></br>");
					out.print("<b>" + Global.display("semester") + ": </b>" + Global.decodeUmlauts(doc3.get("semester"))
                            + "</br>");
					out.print("<b>" + Global.display("lecturer") + ": </b>" + Global.decodeUmlauts(doc3.get("lecturer")) + "</br>");
					out.print("<b>" + Global.display("course") + ": </b>" + Global.decodeUmlauts(doc3.get("course")) + "</br>");
					out.print("<b>" + Global.display("type") + ": </b>" + Global.decodeUmlauts(doc3.get("type")) + "</br>"
							+ "<b>Score: </b>" + hits.scoreDocs[i].score
							+ "</br><p></p>");

					for (int j = 0; j < frag.length; j++) {
						if ((frag[j] != null) && (frag[j].getScore() > 0)) {

							out.println((frag[j].toString()) + "...");
						}
					}

					out.print("</small>");
					%>
				</div>
			</td>
			<%
			    out.println("<td><input name = 'sheetdraft_filelinks[]' value='" + doc3.get("filelink")
                    + "' type='checkbox'/></td>");
				//out.println("<td><input name = 'sheetdraft_id[]' value = '" + sheetdraft_id
				//				+ "' type='checkbox'/></td>");
			%>

		</tr>
		<%
			}
				isearcher.close();
				Global.getIndexReader().close();
				directory.close();

			}
		%>

	</table>
	<%
		if (treffer != 0 && !search.equals("")) {
	%>

	<button id="edit_btn" name="id" class="btn" title="Bearbeiten"
		value="edit">
		<i class="icon-edit"></i>
	</button>

	<!-- BUTTONS FOR ADDING TO A DRAFT THAT IS ONE OUT OF A LOADED DRAFT LIST:  -->
    <jsp:include page="buttons.add_to_draft.jsp?session_user=<%=Global.getUserURLEncoded(session) %>" />
	<%
		}
	%>
</form>

