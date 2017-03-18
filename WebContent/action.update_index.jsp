
<%@page import="core.Global"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.io.File, java.net.URLDecoder"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="org.apache.lucene.analysis.Analyzer"%>
<%@page import="org.apache.lucene.index.IndexWriterConfig.OpenMode"%>
<%@page import="org.apache.lucene.index.IndexWriterConfig"%>
<%@page import="org.apache.lucene.util.Version"%>
<%@page import="org.apache.lucene.analysis.de.GermanAnalyzer"%>
<%@page import="org.apache.lucene.store.FSDirectory"%>
<%@page import="org.apache.lucene.document.Field"%>
<%@page import="org.apache.lucene.document.Fieldable"%>
<%@page import="org.apache.lucene.document.Document"%>
<%@page import="org.apache.lucene.index.IndexWriter"%>
<%@page import="org.apache.lucene.store.Directory"%>

<%
//String id = "";
String filelink = "";
String type = "";
String course = "";
String semester = "";
String lecturer = "";
String description = "";
//String author = "";
//String whencreated = "";
//String is_draft = "";
//String headermixture = "";
//String whenchanged = "";
String plain_text = "";

// Delete index_data directory
Global.deleteDir(new File(Global.root + "index_data"));

// Recreate
File f = new File(Global.root + "index_data");


try {
	ResultSet res =
	Global.query("SELECT filelink, semester," //l.id,
			+ " course, l.lecturer, type, description, plain_text" /*, author "*/
			+ " FROM sheetdraft, lecturer l"
			+ " WHERE l.id = lecturer_id"); /*join over id, lecturer,sheetdraft*/
	int count = 0;
	while (res.next()) {
		count++;
		//out.print("count " + count + "</br>");
//		id = res.getString("id");
		filelink = res.getString("filelink");
		semester = res.getString("semester");
		course = res.getString("course");
		lecturer = res.getString("lecturer");
		type = res.getString("type");
		description = res.getString("description");
//		author = res.getString("author");
		//whencreated = res.getString("whencreated");
		//is_draft = res.getString("is_draft");
		//headermixture = res.getString("headermixture");
		//whenchanged = res.getString("whenchanged");
		//plain_text = URLDecoder.decode(res.getString("plain_text"), "utf-8");
		plain_text = Global.decodeUmlauts(res.getString("plain_text"));

		Analyzer analyzer = new GermanAnalyzer(Version.LUCENE_36);

		// Store the index in memory:
		File index_dir = new File(Global.root + "index_data/");

		Directory directory = FSDirectory.open(index_dir);
		// To store an index on disk, use this instead:
		//Directory directory = FSDirectory.open("/tmp/testindex");
		IndexWriterConfig index_config = new IndexWriterConfig(
				Version.LUCENE_36, analyzer);
		index_config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter iwriter = new IndexWriter(directory, index_config);

		Document doc = new Document();
		doc.add(new Field("filelink", filelink, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("semester", semester, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("course", course, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("lecturer", lecturer, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("type", type, Field.Store.YES, Field.Index.ANALYZED));
//		doc.add(new Field("author", filelink, Field.Store.YES, Field.Index.ANALYZED));
		if (description != null) {
			doc.add(new Field("description", description, Field.Store.YES, Field.Index.ANALYZED));
		}
		if (plain_text != null) {
			doc.add(new Field("content", plain_text, Field.Store.YES, Field.Index.ANALYZED));
		}


		iwriter.addDocument(doc);
		iwriter.close();

	}
	// tackle memory leaks by closing result set and its statement properly:
	Global.queryTidyUp(res);

} catch (SQLException e) {
	e.printStackTrace();
}


%>
