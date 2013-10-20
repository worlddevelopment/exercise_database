
<%-- <%@page import="com.sun.org.apache.xpath.internal.functions.Function"%>
<%@page import="com.sun.org.apache.xpath.internal.functions.FuncBoolean"%> --%>
<%@page import="com.mysql.jdbc.Statement"%>

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
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%@page import="org.apache.commons.fileupload.portlet.*"%>
<%@page import="org.apache.commons.fileupload.util.*"%>

<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.io.FileInputStream, java.io.File, java.io.IOException"%>
<%@page import="java.lang.Exception,java.sql.*"%>
<%@page import="java.util.List,java.util.ArrayList,java.util.HashMap"%>
<%@page import="java.util.Iterator"%>

<%@page import="HauptProgramm.*, swp.*"%>
<%@page import="aufgaben_db.Global, aufgaben_db.Aufgaben_DB"%>
<%@page import="aufgaben_db.Exercise, aufgaben_db.Sheetdraft"%>



<%
	/* variables to be filled */
	String dir = "-1";     // For indicating that something might have gone wrong.
	String filelink = "-1";// The file link no longer contains ".original." before ending.
	String content = "-1"; // because this now easily can be achieved via compare
	String ext = "-1";     // of -->_orig_ext<--.ext to  _orig_ext.-->ext<-- .
	
	int aufg_anz = 0;
	String pdf_link = "";  // Bei Erstellung Vorschaubildes wird fuer jede Datei eine PDF-Datei erstellt, die wir spaeter loeschen muessen
	String tex_link = "";  // Another derivation of the original if not already original's file in_type.


	/*key = FormFieldName, value =  FormFieldValue*/
	HashMap<String, String> form_data = new HashMap<String, String>();

	//to get the content type information from JSP Request Header  
	FileItemFactory factory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(factory);
	
	try {
	    List<?> items = upload.parseRequest(request);
	    Iterator<?> iterator = items.iterator();

	    
	    FileItem fileItem = null;
	    String filename = "";
	    String message = "";
	    //Another form field transmitted? hopefully at least all required ones: course, lecturer, file
	    while (iterator.hasNext()) {
	        
	FileItem item = (FileItem) iterator.next();
	//preprocess items
	if (item.isFormField()) {
	
		String name = item.getFieldName();
		String value = item.getString(); //feldwert
		value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
		//value = Global.encodeUmlauts(value);
		//speichere in hashmapp felnamen und feldwert
		form_data.put(name, value);
		message += "name = " + name + " => ";
		message += " value = " + form_data.get(name) + "</br>\n\r";
		//dir += form_data.get(name) + "\\";
		
		continue; /* the same as if an else statement was to follow */
		
	}
	//ELSE
	//ITEM IS A FILE !!!
	//Datei-endung ermitteln
	ext = Global.extractEndingPerReverse(item.getName());
	//build all data containing filename
	filename = Global.encodeUmlauts(item.getName())/* + ".original_" + ext*/ + "." + ext;
	/*Results in a double ending. That is required for always knowing which filetype is the original file format.*/
	Global.addMessage("Uploaded-Filename = " + filename, "info");
	//Store the fileitem for later saving as filesystem FILE.
	fileItem = item;
		    
	    } /* <-- Here's the long looked for end of while. -END */
	    //Global.addMessage(message, "info"/*, request, pageContext*/);
	    
	    
	    
	    
	    
	    /*..........................................................................*/
	    /*..........................................................................
	       FILE PROCESSING - DON'T PUT IT IN THE WHILE LOOP BECAUSE IT'S NOT
	       GUARANTEED THAT THE FILE (sent by the <form>) IS ALWAYS LAST IN THE ORDER. 
	     */
	    /*That here in the path id not occurrs implies course+lecturer is unique.*/
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
                Global.addMessage("Path not exists. => Created path: " + path + "", "success");
            }
        }
        
        File uploadedFile = new File(path + "/" + filename);
        //String dir_for_link = dir.replaceAll("\\", "/");
        filelink = "uploads" + "/" + dir + filename;
        form_data.put("filelink", filelink);
        pdf_link = Global.replaceEnding(filelink, "pdf");
        tex_link = Global.replaceEnding(filelink, "tex");
        //link = Global.encodeUmlauts(link);
        //out.println("upl_file: "+ link + "</br>");
        fileItem.write(uploadedFile);
        //out.print("uplf_path: " + uploadedFile.getAbsolutePath() + "</br>");

        //=========================================================================
        //=======             EXTRACT EXERCISES                             =======
        //=========================================================================
        String uploadedFileLink = uploadedFile.getAbsolutePath();
        Sheetdraft sheetdraft = Aufgaben_DB.processUploadedSheetdraft(
                uploadedFile.getAbsolutePath());
        
        content = Global.arrayToString(sheetdraft.getPlainText());
        //out.print(content);
        
        
        
        
        
        //=========================================================================
        //=======             INDEX UPDATE                                  =======
        //=========================================================================
        //INDEX-UPDATE
        Analyzer analyzer = new GermanAnalyzer(Version.LUCENE_36);
        // Store the index in memory:
        File index_dir = new File(Global.root +"index_data/");
        Directory directory = FSDirectory.open(index_dir);
        
        /* INDEX WRITER BEGIN */
        IndexWriterConfig index_config = new IndexWriterConfig(Version.LUCENE_36,analyzer);
        index_config.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter iwriter = new IndexWriter(directory, index_config);
        Document doc = new Document();
        doc.add(new Field("content", content, Field.Store.YES,Field.Index.ANALYZED)); //
        doc.add(new Field("filelink", form_data.get("filelink"), Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("lecturer", form_data.get("lecturer"), Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("description", form_data.get("description"), Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("semester", form_data.get("semester") , Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("course", form_data.get("course") , Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field(" in_type", form_data.get("type") , Field.Store.YES,Field.Index.ANALYZED));
        //doc.add(new Field("datei_typ", ext , Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
        
        
        //ORIGINAL SHEETDRAFT IMAGE
        //Erzeuge Bild für Uebungssheetdraft - zunaechst nur fuer das Original Dokument.
        //Though in general it is possible to use the PDF-Image-Method for TeX-/HTML-Documents also.  
        //TODO: Investigate if it is really necessary to generate preview images for each derivation.
        //It's now being created on the fly, so it's no longer a question.
        //bild_link ="bilder/" + bild_name + "__" + ext + ".jpg";
        try {
            //hopefully in this Thread happens the creations/conversions of tex-/html-files
            UnixComandosThread p = new UnixComandosThread(
                    response,
                    uploadedFile.getAbsolutePath(),
                    Global.root + Global.uploadTarget + dir
            );
            //p.start(); //for debug purposes better not start another thread, so currently
            //p.start() is not used. Instead the cmd-execution is started in the constructor.
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //PLAIN TEXT:
        
        /* At this point the exercises are extracted, stored in the filesystem and the 
        relationships between exercise-sheetdrafts are stored in the database via filelink as id.*/
        ArrayList<Exercise> al = new ArrayList<Exercise>();
        al.addAll(sheetdraft.getAllExercisesPlainText().values());
        aufg_anz = al.size();
        System.out.println(Global.addMessage("" + aufg_anz + " Aufgaben gefunden.</br>",
        		aufg_anz == 0 ? "nosuccess" : "success"));
        //aufg_filelink = (String[])(sheetdraft.getAllExercises().keySet().toArray());gave a cast error
        
        //Generate images for exercise:
        for (int i = 0; i < aufg_anz; i++) {
        
            String[] plainText = al.get(i).getPlainText(); 
            
            Global.createImageFromText(
                    plainText,
                    1024, /*width*/
                    al.get(i).getImageLink()
                    /*
                    Global.root + Global.uploadTarget + dir + "/",//pathTo
                    "" + aufg_filelink + "__" + (i + 1) + "__" + ext,//filename
                    "jpg"
                    */
            );
            doc.add(new Field(al.get(i).getFilelink(), al.get(i).getPlainTextAsString(), Field.Store.YES, Field.Index.ANALYZED));
        }
        Global.addMessage("Created images to each exercise ...", " success "/*, request, pageContext*/);
        
        
        
        iwriter.addDocument(doc);
        iwriter.close();
        /* INDEX WRITER -END */
        

        /* File processing (splitting in exercises, ...) -END */
        /*..........................................................................*/
        /*..........................................................................*/
	    
        
        
        
        
	        
	        
	    
	    //INPUT DATA FROM FORM
	    //String in_filelink = form_data.get("filelink"); 
	    String in_type = form_data.get("type");
	    String in_course = form_data.get("course");
	    String in_semester = form_data.get("semester");
	    String in_lecturer = form_data.get("lecturer");//<--this is the the new lecturer
	    
	    if (in_lecturer == null) {
	        Global.addMessage("No lecturer given? If no lecturerID given, too => then we will use default lecturer: N.N.", "info");
	    }
	    String in_lecturer_id_select = form_data.get("lecturer_id_select");
	    String in_lecturer_id = null;
	    String in_lecturer_according_to_id = null;
	    if ((in_lecturer_id_select == null || in_lecturer_id_select == "") && in_lecturer == null) {
	        Global.addMessage("Neither existant lecturerID nor new lecturer data transmitted? => Using default lecturer: N.N.", "warning");
	        in_lecturer_id = "0"; /*requires N.N. to have ID of 0*/
	    }
	    else if (in_lecturer_id_select == null || in_lecturer_id_select == "") {
	        Global.addMessage("No lecturerID transmitted by form? => Inserting new one.", "info");
	    }
	    else {/*split given parameter tuple into two separate strings */
	        String[] parts = in_lecturer_id_select.split(",");//if empty->new lecturer to db
	        in_lecturer_id = parts[0].replace(" ", "");//Integer.valueOf(parts[0]);
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
	            in_lecturer_id = "";
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
	    
	    
	
	    
	
	    
	    
	    
	    
	    
	    //ALSO STORE IT IN THE DATABASE - WHY OH WHY? --------------------------------------
	    //FOR LUCENE SEARCH ENGINE? CAN'T WE USE SQLite IF WE WANT SAME PERFORMANCE?
	    //THEN WE HAVE FILESYSTEM AND DATABASE PERFORMANCE - BOTH IN ONE - without redundancy.
	    //OKAY, I SEE AS THIS WILL SOMETIME BE A VERY BIG DATABASE, MYSQL IS OKAY.
	
	    
	    HashMap<String, String> column_value = new HashMap<String, String>();
	    
	    //------------------Speichere Daten in DB---------------------------------
	    //------Holle ids - potentially overwrites in_lecturer_id
	    //----------------fetch-lecturer_id---------------only if a new lecturer to be inserted
	    /* No or at least no valid id value been transmitted? */
	    if (in_lecturer_id == null || in_lecturer_id == "") { //now it can be overwritten
	        //=> aha new one to be inserted
	        
	        column_value.put("lecturer", in_lecturer);
	        //Is name of potential new lecturer already in database?
	        if (!Global.sqlm.exist("lecturer", "lecturer", column_value)) {
	            String query = "INSERT INTO lecturer"
	                    + "(`lecturer`) VALUES ('" + in_lecturer + "');";
	                    //execute
	            Global.sqlm.executeUpdate(query);
	        }
	        else {
	            //not inserted!
	            Global.addMessage("Lecturer already exists. As long as this time-saving tool is for our DMUW-Chair only, I doubt this was intentionally. => No new one inserted.", "warning");
	            //ATTENTION --> Btw it's really highly connected to the fishing of the lecturer id
	            //(see below). If we want to activate this feature, we have to think
	            //of a new way of fishing the ID!! <-- ATTENTION
	        }
	        column_value.remove("lecturer");
	        
	        //fetch the id of the lecturer -overwrite given one as this is now obsolete
	        in_lecturer_id = Global.sqlm.getId("lecturer", "lecturer", in_lecturer);
	        //make known
	        column_value.put("lecturer_id", in_lecturer_id);
	        
	    }
	    
	
	    //----------------fetch-sheetdraft_id---------------
	    /* 
	    column_value.put("sheetdraft_id", form_data.get("sheetdraft_id"));
	    //Speichere Daten,falls sie nict dupliziert werden
	    if (!(Global.sqlm.exist("lecturer", "lecturer", column_value))) {
	        String query = "INSERT INTO lecturer"
	                + "(lecturer) VALUES ('" + form_data.get("lecturer") + "');";
	        //execute
	        Global.sqlm.exQuery(query);
	    }
	    column_value.remove("sheetdraft_id");
	    
	    //fetch the id of the sheetdraft
	    String lecturer_id = Global.sqlm.getId("lecturer", "lecturer", in_lecturer;
	    //make known to SQL
	    column_value.put("lecturer_id", lecturer_id);
	    */
	    
	    
	    //make directly passed inputs known to database (i.e. here was no id-fishing needed)
	    column_value.put("course", in_course);
	    column_value.put("semester", in_semester);
	    column_value.put("type",  in_type);
	    //column_value.put("datei_typ", ext);
	    
	    column_value.put("filelink", filelink);
	
	    //Is this file already in database?
	    if (!Global.sqlm.exist("sheetdraft", "filelink", column_value)) {
	        
	        //=========================================================================
	        //=======              INSERT SHEET(OR)DRAFT                        =======
	        //=========================================================================
	        String query = "INSERT INTO sheetdraft "
	        + "(`filelink`, `type`, `course`, `semester`, `lecturer_id`, `description`, `author`, `is_draft`)"
	                    + "VALUES ("
	                            + "'" + filelink + "'"
	                            + ",'" +  in_type + "'"
	                            + ",'" + in_course + "'"
	                            + ",'"  + in_semester + "'"
	                            + "," + in_lecturer_id + ""
	                            + ",'" + in_description + "'"
	                            + ",'" + session.getAttribute("user") + "'"
	                            //+"','" + Global.sqlm.mysql_real_escape_string(content) + "'"
	                            + ", " + is_draft /* <- uploaded ones usually are no drafts but sheets */
	                            + ");";
	        Global.addMessage("query: " + query, "info");
	        //execute
	        Global.sqlm.executeUpdate(query);
	        
	        
	        
	        
	        
	        //holle sheetdraft id
	        String sheetdraft_id = Global.sqlm.getIds("sheetdraft", column_value);
	        //String sheetdraft_id = Global.sqlm.getId("sheetdraft", "", "");
	
	        
	        //######################  Diese Mitteilug sollte vll unter dem Hochladeform angezeigt
	        message = "<b>Hochgeladene Dateien: </b><br /><br />"
	          + "<a href='" + filelink + "' class='screenshot' rel='"
	          + Global.getImageLinkFromFile(filelink) + "'>"
	          + form_data.get("filelink") + "." + Global.extractEnding(filelink) + "</a>"
	                  +"</br>"
	                  +"</br>";
	        //=========================================================================
	        //=======             INSERT EXERCISES                              =======
	        //=========================================================================
	        for(int i = 0; i < aufg_anz; i++) { 
	        	//The convert the absolute part to the relative one.
	        	
	        	String exercise_filelink_relative_part = Global.extractRelativePartOfFilelinkAtEndOnly(al.get(i).getFilelink());
	        	Global.addMessage("Generated relative filelink: " + exercise_filelink_relative_part, "info");
	        	String exercise_sheetdraft_filelink = Global.extractSheetdraftFilelinkFromExerciseFilelink(al.get(i).getFilelinkRelative());
	            query = "INSERT INTO exercise ("
	            		   + "sheetdraft_filelink"
	            		   + ", filelink"
	            		   + ", `originsheetdraft_filelink`"
          				   + ", `splitby`"
        				   + ", is_native_format"
    					   //+ ", whenchanged"
        				   + ", whencreated"
      				   + ")"
	                   + "VALUES ("
		               		+ "'" + exercise_sheetdraft_filelink + "'"//sheetdraft_filelink
			                + ",'" + al.get(i).getFilelinkRelative() + "'"
			                + ",'" + exercise_sheetdraft_filelink + "'"//originsheetdraft_filelink
			                + ",'" + al.get(i).getSplitBy() + "'"
			                //+ ",'" + Global.sqlm.mysql_real_escape_string(al.get(i).getPlainTextAsString())
			                + ", " + al.get(i).isNativeFormat() /*is_native_format <-- all directly uploaded sheets are initially in native format*/
			                /*whenchanged - never changed so far*/
			                + ", UNIX_TIMESTAMP() " /*whencreated - automatically given the current db date*/
	                   + ");";
	            //Global.addMessage("query: " + query, "info");
	            //execute
	            Global.sqlm.executeUpdate(query);
	            message += "<a href='" + exercise_filelink_relative_part + "' class='screenshot' rel='"
	            + Global.getImageLinkFromFile(al.get(i).getFilelinkRelative()) + "'>" + al.get(i).getFilelinkRelative() + "</a></br>";
	            
	        }
	        message += "<p id='add_result'></p>";
	        Global.addMessage(message, "info"); //add_result - innere Verweis
	        
	        
	    }
	        
        
        
        
        
        
        
	} catch (FileUploadException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}


    /* After fishing the form_data we have the values send via httprequest post. */
%>

