
<%-- <%@page import="com.sun.org.apache.xpath.internal.functions.Function"%>
<%@page import="com.sun.org.apache.xpath.internal.functions.FuncBoolean"%> --%>
<%@page import="com.mysql.jdbc.Statement, java.net.URLEncoder"%>

<%@page import="org.apache.pdfbox.examples.pdmodel.AddMessageToEachPage"%>

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

<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%@page import="org.apache.commons.fileupload.portlet.*"%>
<%@page import="org.apache.commons.fileupload.util.*"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>

<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.io.FileInputStream, java.io.File, java.io.IOException"%>
<%@page import="java.lang.Exception"%>
<%@page import="java.util.List,java.util.ArrayList,java.util.HashMap"%>
<%@page import="java.util.Iterator"%>

<%@page import="db.UnixComandosThread"%>
<%@page import="core.Global, core.PartDB"%>
<%@page import="core.Part, core.Sheetdraft"%>



<%
// Redirect/correct target page: start/overview
Global.redirectTo("start");


// Variables to be filled
String dir = "-1"; // For indicating that something might have gone wrong.
String filelink = "-1"; // The file link no longer contains ".original." before ending.
//String content = "-1"; // because this now easily can be achieved via compare
String ext = "-1"; // of -->_orig_ext<--.ext to  _orig_ext.-->ext<-- .

int aufg_anz = 0;
String pdf_link = "";  // Bei Erstellung Vorschaubildes wird fuer jede Datei eine PDF-Datei erstellt, die wir spaeter loeschen muessen
String tex_link = "";  // Another derivation of the original if not already original's file in_type.


//key = FormFieldName, value =  FormFieldValue
HashMap<String, String> form_data = new HashMap<String, String>();

// To get the content type information from JSP Request Header
FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);

//if (request.getContentType().equals("multipart/form-data"))
try {
	List<?> items = upload.parseRequest(request);
	Iterator<?> iterator = items.iterator();


	FileItem fileItem = null;
	String filename_and_ext = "";
	String message = "";
	// Another form field transmitted? hopefully at least all required ones: course, lecturer, file
	while (iterator.hasNext()) {

		FileItem item = (FileItem) iterator.next();
		// Preprocess items
		if (item.isFormField()) {

			String name = item.getFieldName();
			String value = item.getString();
			value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
			//value = Global.encodeUmlauts(value);
			form_data.put(name, value);
			message += "name = " + name + " => ";
			message += " value = " + form_data.get(name) + "</br>\n\r";
			//dir += form_data.get(name) + "\\";

			continue; /* the same as if an else statement was to follow */

		}
		//else
		// ITEM IS A FILE !
		ext = Global.extractEndingPerReverse(item.getName());
		// Build all data containing filename
		filename_and_ext = Global.encodeUmlauts(item.getName())/* + ".original_" + ext*/ + "." + ext;
		// Results in a double ending. That is required for always knowing which filetype is the original file format.
		//Global.addMessage("Uploaded-Filename = " + filename, "info");

		// Store the fileitem for later saving as filesystem FILE.
		fileItem = item;

	} // <-- Here's the long looked for end of while. -END
	//Global.addMessage(message, "info"/*, request, pageContext*/);





	/*..........................................................................*/
	/*..........................................................................
	FILE PROCESSING - DON'T PUT IT IN THE WHILE LOOP BECAUSE IT'S NOT
	GUARANTEED THAT THE FILE (sent by the <form>) IS ALWAYS LAST IN THE ORDER.
	*/
	/* That here in the path id not occurrs implies course+lecturer is unique.*/
	dir = Global.buildPathTo(
		form_data.get("semester"),
		form_data.get("course"),
		form_data.get("lecturer"),
		form_data.get("type")
	);
	//out.print("dir: " + dir);
	//String fileName = item.getName();

	File path = new File(Global.root + Global.uploadTarget + dir);
	//out.print("Global.root:" + Global.root + "</br>");
	if (!path.exists()) {
		if(path.mkdirs()) {
			Global.addMessage("Path did not exist yet. => Created path: " + path + "", "success");
		}
		else {
			Global.addMessage("Path did not exist yet. => Failed to create path: " + path + "", "nosuccess");
		}
	}

	File uploadedFile = new File(path + System.getProperty("file.separator") + filename_and_ext);
	//String dir_for_link = dir.replaceAll("\\\\", System.getProperty("file.separator"));
	filelink = Global.uploadTarget + dir + filename_and_ext;
	form_data.put("filelink", filelink);
	pdf_link = Global.replaceEnding(filelink, "pdf");
	tex_link = Global.replaceEnding(filelink, "tex");
	//link = Global.encodeUmlauts(link);
	//out.println("upl_file: "+ link + "</br>");
	fileItem.write(uploadedFile);/*write the file to harddisk*/
	//out.print("uplf_path: " + uploadedFile.getAbsolutePath() + "</br>");


	//=========================================================================
	//=======			 GENERATE FLAVOURS							 =======
	//=========================================================================

	//=========================================================================
	//=======			 EXTRACT EXERCISES (plain text & native format)=======
	//=========================================================================

	//=========================================================================
	//=======			 AND MORE PROCESSING						   =======
	//=========================================================================
	//String uploadedFileLink = uploadedFile.getAbsolutePath();
	Sheetdraft sheetdraft = PartDB.processUploadedSheetdraft(
			uploadedFile.getAbsolutePath());//=>The sheetdraft.getFilelink is absolute.

	/* IMPORTANT: From here only always use sheetdraft.getFilelink !! as it may have
	been changed in processUploadedSheetdraft, for example if it's non-directly
	supported input format.
	TODO always check the correct filelink is used below!!
	*/




	//=========================================================================
	//=======			 INDEX UPDATE								  =======
	//=========================================================================
	// INDEX-UPDATE
	Analyzer analyzer = new GermanAnalyzer(Version.LUCENE_36);
	// Store the index in memory:
	File index_dir = new File(Global.root +"index_data/");
	if (!index_dir.exists()) {
		index_dir.mkdirs();
	}
	Directory directory = FSDirectory.open(index_dir);

	/* INDEX WRITER LOCKED CHECK */
	/* Automatically check if the index directory is locked for a too long time.
	If this makes trouble one could still open a new directory. Though this
	would mess things up.
	The automatic removal of e.g. a timed out index comes in handy because noone has to
	log onto the server to remove the file manually!
	*/
	/* Assume 10 cycles / ms, then counter reaches zero in
	t_to_zero =  (4294967296 / 10) / 1000 / 60 minutes = 7158 minutes = 119 hours.
	This is way too long.
	So let's pick a target time interval: .5 min
	To reach this we have to divide by (t_to_zero * 2). So we get for the starting value:
	t_to_zero_reasonable = (2^32 / 10) / (t_to_zero * 2) = 429496730 / (7158 * 2).
	*/
	int timer = 30000;//Integer.MAX_VALUE;
	while (--timer > 0 && IndexWriter.isLocked(directory)) {
		// wait until lock is gone, so take the next chance
	}
	// Now has the index directory been freed?
	if (IndexWriter.isLocked(directory)) {
		// => still locked. Now we must no longer wait. Unlock the directory as the creator IndexWriter object probably got lost.
		IndexWriter.unlock(directory);
	}

	/* INDEX WRITER BEGIN */
	IndexWriterConfig index_config = new IndexWriterConfig(Version.LUCENE_36,analyzer);
	index_config.setOpenMode(OpenMode.CREATE_OR_APPEND);
	// Now we can reuse the index directory.
	IndexWriter iwriter = new IndexWriter(directory, index_config);
	Document doc = new Document();
	doc.add(new Field("filelink", form_data.get("filelink"), Field.Store.YES,Field.Index.ANALYZED));
	doc.add(new Field("semester", form_data.get("semester") , Field.Store.YES,Field.Index.ANALYZED));
	doc.add(new Field("course", form_data.get("course") , Field.Store.YES,Field.Index.ANALYZED));
	doc.add(new Field("lecturer", form_data.get("lecturer"), Field.Store.YES,Field.Index.ANALYZED));
	doc.add(new Field("type", form_data.get("type") , Field.Store.YES,Field.Index.ANALYZED));
	doc.add(new Field("description", form_data.get("description"), Field.Store.YES,Field.Index.ANALYZED));
	doc.add(new Field("content", Global.arrayToString(sheetdraft.getPlainText()), Field.Store.YES,Field.Index.ANALYZED)); //
	//doc.add(new Field("datei_typ", ext , Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));


	// ORIGINAL SHEETDRAFT IMAGE
	// Create image for sheetdraft, at least for the originally uploaded one.
	// In general it is possible to use the PDF-Image-Method for TeX-/HTML-Documents also.
	// TODO: Investigate if it is really necessary to generate preview images for each derivation.

	try {
		// Hopefully in this Thread happens the creations/conversions of tex-/html-files
		// Note the result depends on the image magick version, configuration.
		Global.ImageMagick_qualityScale = 1; // This quality/density is high enough here
		UnixComandosThread p = new UnixComandosThread(
			response,
			sheetdraft.getFilelink(),//uploadedFile.getAbsolutePath(),
			Global.root + Global.uploadTarget + dir
		);
		p.d_o();
		//p.start(); //for debug purposes better not start another thread, so currently
		//p.start() is not used. Instead the cmd-execution is started in the constructor.
	} catch (IOException e) {
		e.printStackTrace();
		if (iwriter != null) {
			iwriter.close();
		}
	}
//	finally {
//	}

	// PLAIN TEXT EXERCISES: (within try statement now to free the lock of the index writer in all cases.)

	/* At this point the parts are extracted, stored in the filesystem and the
	relationships between part-sheetdrafts are stored in the database via filelink as id.*/
	ArrayList<Part> al = new ArrayList<Part>();
	al.addAll(sheetdraft.getAllPartsPlainText().values());
	aufg_anz = al.size();
	System.out.println(Global.addMessage("" + aufg_anz + " Aufgaben gefunden.</br>",
			aufg_anz == 0 ? "nosuccess" : "success"));
	//aufg_filelink = (String[])(sheetdraft.getAllParts().keySet().toArray());gave a cast error

	try {
		// Generate images for part:
		for (int i = 0; i < aufg_anz; i++) {

			String[] plainText = al.get(i).getPlainText();

			Global.createImageFromText(
				plainText,
				1024, /*width*/
				al.get(i).getImageLink()
				/*
				Global.root + Global.uploadTarget + dir + System.getProperty("file.separator"),//pathTo
				"" + aufg_filelink + "__" + (i + 1) + "__" + ext,//filename
				"jpg"
				*/
			);
			doc.add(new Field(al.get(i).getFilelink(), al.get(i).getPlainTextAsString(), Field.Store.YES, Field.Index.ANALYZED));
		}
		Global.addMessage("Created images to each part (plain text) ...", " success "/*, request, pageContext*/);



		// NATIVE FORMAT EXERCISES
		//al.addAll(sheetdraft.getAllParts().values());
		// Generate images for part:
		for (Part part : sheetdraft.getAllParts().values()) {

			String sheetdraft_ending = Global.extractEnding(sheetdraft.getFilelink());
			String part_ending = Global.extractEnding(part.getFilelink());

			// Figure out which plain text part that has been generated corresponds to
			// this raw/native format one:
			int partNumber = Global.extractPartNumberFromFilelink(part.getFilelink());
			String partPT_filelink
			= sheetdraft.getFilelinkForPartFromPosWithoutEnding(partNumber, partNumber)
					+ "." + sheetdraft_ending + ".txt" //+ part_ending
					;

			Part partPT = sheetdraft.getAllPartsPlainText().get(partPT_filelink);
			String correspondingPlainText = "";
			if (partPT != null) {
				correspondingPlainText = partPT.getPlainTextAsString();
				//doc.add(new Field(part.getFilelink(), correspondingPlainText, Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("content", correspondingPlainText, Field.Store.YES, Field.Index.ANALYZED));
			}
			else {
				Global.addMessage("No corresponding plain text part found.", "danger");
			}

			//for now this would overwrite odt.txt -> odt.png!! Now using triple endings for images only?
			//part.generateImage();

			al.add(part);

		}



		// COMMON FORMAT EXERCISES (currently HTML as of v31.13c)
		if (sheetdraft.getAllPartsCommonFormat() != null) {
			al.addAll(sheetdraft.getAllPartsCommonFormat().values());
		}



		// Not to forget
		aufg_anz = al.size();


	} catch (IOException e) {
		e.printStackTrace();
	}
	finally {
		if (iwriter != null) {
			iwriter.addDocument(doc);
			iwriter.close();
		}
	}


	/* INDEX WRITER -END */


	/* File processing (splitting in parts, ...) -END */
	/*..........................................................................*/
	/*..........................................................................*/








	// INPUT DATA FROM FORM
	//String in_filelink = form_data.get("filelink");
	String in_type = form_data.get("type");
	String in_course = form_data.get("course");
	String in_semester = form_data.get("semester");
	String in_lecturer = Global.encodeUmlauts(form_data.get("lecturer"));//<--this is the the new lecturer

	if (in_lecturer == null) {
		System.out.print(
			//Global.addMessage(
				"No lecturer given? If no lecturerID given, too => then we will use default lecturer: N.N."
			//	, "info"
			//)
		);
	}
	String in_lecturer_id_select = form_data.get("lecturer_id_select");
	int in_lecturer_id = -1;
	String in_lecturer_according_to_id = null;
	if ((in_lecturer_id_select == null || in_lecturer_id_select == "") && in_lecturer == null) {
		System.out.print(
			//Global.addMessage(
				"Neither existing lecturerId nor new lecturer data transmitted? => Using default lecturer: N.N."
				//	  , "warning"
			//)
		);
		in_lecturer_id = 0; /*requires N.N. to have ID of 0*/
	}
	else if (in_lecturer_id_select == null || in_lecturer_id_select == "") {
		System.out.print(
//			Global.addMessage(
				"No lecturerId transmitted by form? => It's a new lecturer."
//				, "info"
//			)
		);
	}
	else {/*split given parameter tuple into two separate strings */
		String[] parts = in_lecturer_id_select.split(",");//if empty->new lecturer to db
		in_lecturer_id = Integer.valueOf(parts[0].replace(" ", ""));
		in_lecturer_according_to_id = parts[1]; /*<- required for checking if a new lecturer
											shall be inserted (as it's thinkable that
											user first select one of the lecturers of
											the select-list and then modify the entry
											because they realized that this is a new
											lecturer!)
											*/
		if (!in_lecturer.equals(in_lecturer_according_to_id)) {
			//then clear the in(coming)_id => insert new lecturer to database
			//because they are different, hence the new different one has to be inserted
			in_lecturer_id = -1;
			Global.addMessage("Did you want to insert a new/different Lecturer with a name"
					+ " that already exists? Theoretically this is possible:"
					+ " To achieve that you have to RESET"
					+ " the SELECT field (Dozenten-Auswahlliste), best just type the lecturer's name manually.",
					"Warning");
		}
	}
	String in_description = form_data.get("description");
	//String in_author = form_data.get("author");//TAKE FROM SESSSION!!
	//String in_whencreated = form_data.get("whencreated");//DETERMINE AT STORE TIME!
	int is_draft = 0; //it's no draft if the sheetdraft results from an upload!
	//String header = ""; //only relevant for drafts.










	// ALSO STORE IT IN THE DATABASE - WHY OH WHY? --------------------------------------
	// FOR LUCENE SEARCH ENGINE? CAN'T WE USE SQLite IF WE WANT SAME PERFORMANCE?
	// THEN WE HAVE FILESYSTEM AND DATABASE PERFORMANCE - BOTH IN ONE - without redundancy.
	// OKAY, I SEE AS THIS WILL SOMETIME BE A VERY BIG DATABASE, MYSQL IS OKAY.


	HashMap<String, String> column_value = new HashMap<String, String>();

	//------------------Speichere Daten in DB---------------------------------
	//------Holle ids - potentially overwrites in_lecturer_id
	//----------------fetch-lecturer_id---------------only if a new lecturer to be inserted
	/* No or at least no valid id value been transmitted? */
	if (/*in_lecturer_id == null ||*/ in_lecturer_id == -1) { //now it can be overwritten
		// => aha new one to be inserted

		column_value.put("lecturer", Global.encodeUmlauts(in_lecturer));
		// Is name of potential new lecturer already in database?
		if (!Global.sqlm.exist("lecturer", "lecturer", column_value)) {

			in_lecturer_id = (int)Math.round(Math.random() * 10000);
			String query = "INSERT INTO lecturer"
					+ "(`id`, `lecturer`) VALUES ("+ in_lecturer_id //<--For SQL-MySQL compatibility.
					+ ", '" + Global.encodeUmlauts(in_lecturer) + "');";
			//execute
			Global.sqlm.executeUpdate(query);

		}
		else {
			// Lecturer exists. No new one was inserted.
			Global.addMessage("Lecturer already exists. As long as this time-saving tool is for our DMUW-Chair only, I doubt this was intentional because we have few lecturers with the same name. => No new one inserted.", "warning");
			// ATTENTION --> Btw it's really highly connected to the fishing of the lecturer id
			// (see below). If we want to activate this feature, we have to think
			// of a new way of fishing the ID!! <-- ATTENTION

			// Fetch the id of the lecturer -overwrite given one as this is now obsolete
			in_lecturer_id = Global.sqlm.getId("lecturer", "lecturer", Global.encodeUmlauts(in_lecturer));
		}
		column_value.remove("lecturer");


		// Make known
		column_value.put("lecturer_id", in_lecturer_id + "");//will be reconverted in getId

	}


	//----------------fetch-sheetdraft_id---------------
	/*
	column_value.put("sheetdraft_id", form_data.get("sheetdraft_id"));
	// Speichere Daten,falls sie nict dupliziert werden
	if (!(Global.sqlm.exist("lecturer", "lecturer", column_value))) {
		String query = "INSERT INTO lecturer"
				+ "(lecturer) VALUES ('" + form_data.get("lecturer") + "');";
		// execute
		Global.sqlm.exQuery(query);
	}
	column_value.remove("sheetdraft_id");

	// Fetch the id of the sheetdraft
	String lecturer_id = Global.sqlm.getId("lecturer", "lecturer", in_lecturer;
	// Make known to SQL
	column_value.put("lecturer_id", lecturer_id);
	*/


	// Make directly passed inputs known to database (i.e. here was no id-fishing needed)
	column_value.put("course", in_course);
	column_value.put("semester", in_semester);
	column_value.put("type",  in_type);
	//column_value.put("datei_typ", ext);

	column_value.put("filelink", filelink);

	// Is this file already in the database?
	if (!Global.sqlm.exist("sheetdraft", "filelink", column_value)) {

		//=========================================================================
		//=======			  INSERT SHEET(OR)DRAFT						=======
		//=========================================================================
		String query = Global.QUERY_INSERT_INTO_sheetdraft(filelink,
				in_type,
				in_course,
				in_semester,
				in_lecturer_id,
				in_description,
				request.getParameter("uploader"),//session.getAttribute("user"),
				is_draft,
				sheetdraft.getPlainTextAsString()
		);
		//Global.addMessage("query: " + query, "info");
		// execute
		Global.sqlm.executeUpdate(query);





		// holle sheetdraft id
//		int sheetdraft_id = Global.sqlm.getId("sheetdraft", column_value);
		//String sheetdraft_id = Global.sqlm.getId("sheetdraft", "", "");


		//######################  Diese Mitteilug sollte vll unter dem Hochladeform angezeigt
		message = "<!-- centered fixed message wrapper --><div id='summary' class='info centered fixed' onclick=''>"
				+"<b>Hochgeladene Dateien: </b><br /><br />"
				+ "<a href='" + filelink + "' class='screenshot' rel='"
				+ Global.getImageLinkFromFile(filelink) + "'>"
				+ form_data.get("filelink") + "</a>"
				+"</br>"
				+"</br>";
		//=========================================================================
		//=======			 INSERT EXERCISES							  =======
		//=========================================================================
		for(int i = 0; i < aufg_anz; i++) {


			query = Global.getQUERY_INSERT_INTO_part(al.get(i));

//			query = "INSERT INTO part ("
//						+ "sheetdraft_filelink"
//						+ ", `filelink`"
//						+ ", `originsheetdraft_filelink`"
//						+ ", `splitby`"
//						+ ", is_native_format"
//						//+ ", whenchanged"
//						+ ", whencreated"
//					+ ")"
//					+ "VALUES ("
//				+ "'" + part_sheetdraft_filelink + "'"//sheetdraft_filelink
//				+ ",'" + al.get(i).getFilelinkRelative() + "'"
//				+ ",'" + part_sheetdraft_filelink + "'"//originsheetdraft_filelink
//				+ ",'" + al.get(i).getSplitBy() + "'"
//				//+ ",'" + Global.sqlm.mysql_real_escape_string(al.get(i).getPlainTextAsString())
//				+ ", " + al.get(i).isNativeFormat() //is_native_format <-- all directly uploaded sheets are initially in native format
//				//whenchanged - never changed so far
//				+ ", UNIX_TIMESTAMP() " /*whencreated - automatically given the current db date*/
//				+ ");";
//			//Global.addMessage("query: " + query, "info");
			// execute
			Global.sqlm.executeUpdate(query);
			message += "<a href='" + al.get(i).getFilelinkRelative() + "' class='screenshot' rel='"
			+ Global.getImageLinkFromFile(al.get(i).getFilelinkRelative()) + "'>" + al.get(i).getFilelinkRelative() + "</a></br>";

		}
		//onclick => removes centered fixed classes such that the message will be displayed flowing with the content again.
		message += "<p id='add_result'></p>";
		message += "</div><!-- centered fixed message wrapper -END -->";
		Global.addMessage(message, ""); //add_result - innere Verweis
		Global.message += "<div id='overlay' class='overlay inline' style='background-color:rgba(255,255,255,.7);' onclick='$(this).hide();var el = document.getElementById(\"summary\");el.setAttribute(\"style\", \"position:static; margin-left:0; width:100%;\");/*el.style.marginLeft=0;$(\"#summary\").hide()*/;'></div>";

	}







} catch (FileUploadException e) {
	e.printStackTrace();
} catch (Exception e) {
	e.printStackTrace();
}


/* After fishing the form_data we have the values sent via httprequest post. */
%>
