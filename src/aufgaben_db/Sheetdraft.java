package aufgaben_db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import HauptProgramm.Declaration;
import HauptProgramm.DeclarationSet;
import Verwaltung.HashLog;

import aufgaben_db.LatexCutter;




/**
 * A Draft is is a container for multiple exercises. 
 * 
 * @author Jan R.I.B.-Wein
 *
 */
public class Sheetdraft extends ContentToImage {
	
	
	/*======= ATTRIBUTES ==================================================*/

//	private int id;
	//in content to image: private String filelink;
//	private String originsheetdraft_filelink;//currently not needed
	private String type;
	private String course;
	private String semester;
	private int lecturer_id;
	private String description = "";
	private String author;
	private int is_draft;	/*store it as an integer but handle it like a bool*/
	private String headermixture;
	private Calendar whencreated = Calendar.getInstance();
	private Calendar whenchanged = Calendar.getInstance();
	                                //Date. TODO: Clear if int or string suitable.
									//Funny that now it turned out as Calendar. :)
									//So I was completely wrong ... (-:

	
	//For easy fetching of exercises out of the mass by filelink as key!
	/* To avoid a false exercise count, plain text and raw format exercises are stored separately.*/
	private Map<String, Exercise> allExercisesRawFormat = new HashMap<String, Exercise>();
	private Map<String, Exercise> allExercisesPlainText = new HashMap<String, Exercise>();
	private Map<String, String> exerciseEndingsAsKeysSplitterAsValues = new HashMap<String, String>();	
	
	
	/* From splitter/split by hint in the filelink we determine the best fitting pattern of enum Muster.java.
	 * We also first try to determine the pattern/Muster on the fly with automatism for several exercise
	 * declarations. The finally successful declarations with all the extra data like line of file, ... will
	 * be stored here AND in the Exercise objects redundantly! */
	private DeclarationSet declarationSet;//TODO not need! exercises bear them!
	
	
	
	
	
	/*======= CONSTRUCTOR ==================================================*/
	public Sheetdraft () {
//		this("-1", "dummyfilelink", "dummytype", "dummycourse",
//				"dummysemester", "dummylecturer_id", "dummydescription",
//				"dummyauthor", "dummywhencreated", "dummyis_draft",
//				"dummyheadermixture", "whenchanged");

		//CREATE NEW DRAFT.
		String new_draft_type = "Mix";
		String new_draft_course = "Random-coursename-" + Math.round(Math.random() * 10000);
		String new_draft_semester = Global.getSemtermFromCalendar(Calendar.getInstance()) + Global.now.get(Calendar.YEAR);
		int new_draft_lecturer_id = 0; /*N.N. or N.A.*/
		String new_draft_description = "";//a draft newly created automatically has no description
		String new_draft_headermixture = "";     //the same applies here. 
		
		//build the filelink
		String new_draft_filelink = new_draft_semester + "/" + new_draft_course
				+ "/N.N." + /*new_draft_lecturer_id*/"" + "/" + new_draft_type
				+ "/" + new_draft_course + "_" + new_draft_semester
				+ "_" + new_draft_type + "_randomsheet" + Math.round(Math.random() * 10000) + ".tex";
		
		setAllAttributes(
				//-1	/*new draft id -- to be set by the database!*/
			    new_draft_filelink
				, new_draft_type
				, new_draft_course
				, new_draft_semester
				, new_draft_lecturer_id
				, new_draft_description
				, (String)Global.session.getAttribute("user")
				, Calendar.getInstance()	/*uses actual current time as default*/
				, 1							/*read: true*/
				, new_draft_headermixture
				, Calendar.getInstance()	/*uses actual current time as default*/
		);
		
		

	}
	public Sheetdraft(String filelink) throws IOException, SQLException {
		this(
				filelink
				, Global.extractTypeFromFilelink(filelink)
				, Global.extractCourseFromFilelink(filelink)
				, Global.extractSemesterFromFilelink(filelink)
				, Global.extractLecturerIdFromFilelink(filelink)
		);
	}
	public Sheetdraft(String filelink, String type, String course,
			String semester, int lecturer_id){
		setAllAttributes(filelink, type, course, semester, lecturer_id
				/*the following use the default this.attribute value*/
				, description, author, whencreated, is_draft, headermixture, whenchanged);
		
	}
	public Sheetdraft(String filelink, String type, String course,
			String semester, int lecturer_id, String description,
			String author, Calendar whencreated, int is_draft, String headermixture,
			Calendar whenchanged){
		
		setAllAttributes(filelink, type, course, semester, lecturer_id
				, description, author, whencreated, is_draft, headermixture, whenchanged);
		
	}
	
	
	public void setAllAttributes(String filelink, String type, String course,
			String semester/*, int lecturer_id*/, String description,
			String author, Calendar whencreated, int is_draft, String headermixture,
			Calendar whenchanged){
		setAllAttributes(/*-1,*/ filelink, type, course, semester, 0/*,lecturer_id*/
				, description, author, whencreated, is_draft, headermixture, whenchanged);
	}
	public void setAllAttributes(/*int id,*/ String filelink, String type,
			String course, String semester, int lecturer_id, String description,
			String author, Calendar whencreated, int is_draft, String headermixture,
			Calendar whenchanged){
//		this.id = id;
		this.filelink = filelink;
		this.type = type;
		this.course = course;
		this.semester = semester;
		this.lecturer_id = lecturer_id;
		this.description = description;
		this.author = author;
		this.is_draft = is_draft;
		this.whencreated = whencreated;
		this.headermixture = headermixture;
		this.whenchanged = whenchanged;
		
	}
	
	
	
	
	
	
	
	
	/*======= METHODS =====================================================*/	
	public void loadAllExercises() throws SQLException, IOException {
		//look for this sheetdraft's exercises (different datatype versions)
		ResultSet res = Global.query(
				"SELECT * FROM exercise WHERE sheetdraft_filelink = '"+ filelink
				+"';");
		
		while (res.next()) {
			//For easy fetching of exercises out of the mass by filelink as key!
			allExercisesRawFormat.put(
				res.getString("filelink")
					,
			    new Exercise(
//					res.getString("sheetdraft_id"),
					res.getString("filelink")
//					,res.getString("originsheetdraft_filelink")			
					,new Declaration(Global.determinePatternBestFittingToSplitterHint(
							res.getString("splitby"))/*needed in db for joining sheets*/
							,""/*res.getString("declarationFirstWord")*//*currently not due to be stored*/
							,0/*res.getString("declarationLineNumber")*/)
					//,res.getString("content")/*raw content*/
					,res.getString("header") /*Needed in db for joining sheets AND not bloating resulting files because of unnecessary parts of the header still persisting.*/
					//,res.getInt("is_native_format") created on the fly now!
					,res.getLong("whencreated")
					,res.getLong("whenchanged")
			    )
			);
		}
		
	}
		
	
	
	
	/**
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public void synchronizeExercisesLocationToFilelink() throws SQLException, IOException {
		
		//int exercisesL = this.getAllExercises().size();
		int i = 0;
		for(Exercise e : this.getAllExercises().values()) {
			
			for (String ending : this.exerciseEndingsAsKeysSplitterAsValues.keySet()) {
			    System.out.print(
			    		(Global.message += "<br /><span>Moving exercises with ending '"
			            + ending + "'" +  " that were split by pattern:<br />\n\r"
			    		+ this.exerciseEndingsAsKeysSplitterAsValues.get(ending))
			    );

			    
			    //Update/set the filelink according to the sheetdraft's filelink!
			    //TODO
			    e.moveToNewFilelink(this.getFilelink() + "-Exercise_" + (++i)
			    		+ "-splitby_" + e.getSplitBy() +"." + e.getFileEnding());
			    
				//Update the new filelinks in the database too.
				e.updateDBToNewFilelink();
				
				
			}
			
		}
		
	}

	

	
	/**
	 * 
	 * @throws IOException
	 * @throws SQLException 
	 */
	public void synchronizeDatabaseToThisInstance() throws IOException, SQLException {
		synchronizeDatabaseToThisInstance(false);
	}
	public void synchronizeDatabaseToThisInstance(
			boolean include_exercises_thus_possibly_copying_all_referenced_exercises)//making this draft independent 
			throws IOException, SQLException {

		//For determining if this sheetdrat exists:
		ResultSet res = Global.query(
				"SELECT is_native_format"
						+ " FROM `sheetdraft`"
						+ " WHERE filelink = " + filelink);
		//determine length
	   	int resL = 0;
	   	if (res.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
	        resL = res.getRow();
	   	    res.beforeFirst();/*because afterwards follows a next()*/
	   	}
		if (resL > 0) {
			//Update already existent entry/row.
		    String query = "UPDATE `sheetdraft`"
		    		   + " SET"
		    		   + " filelink = " 	+ "'" + this.filelink + "'"
		    		   + " ,type = " 		+ "'" + this.type + "'"
		    		   + " ,course = "		+ "'" + this.course + "'"
		    		   + " ,semester = "	+ "'" + this.semester + "'"
		    		   + " ,lecturer_id = "	+ "" + this.lecturer_id + ""
		    		   + " ,description = "	+ "'" + this.description + "'"
		    		   + " ,whencreated = "	+ "'" + new Timestamp(Global.now.getTimeInMillis()) + "'"
		    		   + " ,author = "		+ "'" + Global.session.getAttribute("user") + "'"
		    		   + " ,is_draft = "	+ " " + this.is_draft
		    		   + " ,headermixture ="+ "'" + this.headermixture + "'"
		    		   + " ,whenchanged = "	+ "NOW()"//CURRENT_TIMESTAMP() OR CURRENT_TIMESTAMP" all valid
		    		   + "";
		    		          
		    //execute
		    Global.query(query);
		    
		}
		else {
			
			//Create a new draft in database:
		    String query = "INSERT INTO `sheetdraft`"
		    		   + " (filelink, type, course, semester, lecturer_id, description"
		    		   + ", whencreated"
		    		   + ", author, is_draft, headermixture, whenchanged)"
		    		   + " VALUES ("
		    		           + "'" + this.filelink + "'"
		    		           + ",'" + this.type + "'"
		    		           + ",'" + this.course + "'"
		    		           + ",'" + this.semester + "'"
		    		           + "," + this.lecturer_id + ""
		    		           + ",'" + this.description + "'"
		    		           + ",'" + new Timestamp(Global.now.getTimeInMillis()) + "'"
	  		        		   + ",'" + Global.session.getAttribute("user") + "'"
	  		        		   + ", " + this.is_draft
	  		        		   + ",'" + this.headermixture + "'"
	  		        		   + ", NOW()" //CURRENT_TIMESTAMP() OR CURRENT_TIMESTAMP" all valid
		    		   + ")";
		    //execute
		    Global.query(query);
		}
	    
		    
		    
	    //INCLUDE EXERCISES?
	    if (include_exercises_thus_possibly_copying_all_referenced_exercises) {
		    //Add all exercises (including assigned ones) from this instance
		    //to the db as individual exercises, effectively deleting assignments
		    //i.e. to table `exercise` and not to `draftexerciseassignment`!
	    	//If no more draftexerciseassignments are left!
		    //ATTENTION this can include related ones, i.e. those assigned by
		    //reference which are in draftreferenceassignment.
	    	Global.addMessage("Functionality for synchronizing DB to Instance"
	    			+ "/Object not implemented.", "nosuccess");
	    	
	    }
	    
	}
	

	
	/**
	 * SYNCHRONIZE WITH DATABASE BECOME IDENTICAL AND LOAD ALL EXERCISES
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public Sheetdraft synchronizeWithDatabaseBecomeIdenticalAndLoadAllExercises()
			throws IOException, SQLException {
		return synchronizeWithDatabaseLoadFromItBecomeIdentical()
				.loadAssignedAndReferencedSingleSourceExercisesFromDatabase();
	}
	
	
	/**
	 * SYNCHRONIZE WITH DATABASE LOAD FROM IT BECOME IDENTICAL

	 * -------1st 
	 * THIS METHOD EITHER LOADS ALL DATA FROM AN EXISTANT SHEETDRAFT IN DB.
	 * AND BECOMES THE IDENTITY OF THAT SHEETDRAFT, all being the same.
	 * 
	 * -------2nd
	 * OR LOADS EFFECTIVELY ALL DATA FROM THE DATABASE OF ITS OWN ENTRY.
	 * ANY CHANGES MADE NOT IN THE DB BUT THE JAVA OBJECT THEN ARE LOST.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	private Sheetdraft synchronizeWithDatabaseLoadFromItBecomeIdentical()
			throws IOException, SQLException {
		/*this is useful for keeping up integrity*/
		//synchronize the draft with itself
		return synchronizeWithDatabaseLoadFromItBecomeIdentical(this.filelink);
	}
	public Sheetdraft synchronizeWithDatabaseLoadFromItBecomeIdentical(
			String this_or_foreign_filelink_to_copy_from)
			throws IOException, SQLException {
		/*This is useful for keeping up integrity.*/
		
		/*And DANGEROUS as this sheet/draft will give up all and become the
		 *other one sheetdraft given as the.
		 *
		 *Of course if this sheet/draft's filelink already exists in the database
		 *then this will simply load all exercises.  
		 */
		//synchronize the draft with itself
		//Create a new draft in database:
	    String query = "SELECT *" /*filelink, type, course, semester, lecturer_id, description"
	    		   + ", author, is_draft, headermixture, whenchanged, whencreated"*/
	    		   + " FROM `sheetdraft`"
	    		   + " WHERE filelink = '" + this_or_foreign_filelink_to_copy_from + "'"
	    		   + "";
	    //execute
	    ResultSet res = Global.query(query);
	    res.beforeFirst();
	    while (res.next()) {
	    	/*ID and filelink of sheetdraft are never to be changed
	    	 *  as elsewise it would be lost. A filelink can only point to
	    	 *  one file! */
	    	if (!this.filelink.equals(res.getString("filelink"))) {
	    		//filelink empty?
	    		if (!this.filelink.equals("")) {
		    		Global.addMessage("Attention: In Synchronization: Filelink was different!"
		    				+ " Potential danger."
		    				+ "\r\n<br />Old filelink: " + this.filelink
		    				+ "\r\n<br />New filelink: " + res.getString("filelink")
		    				, "danger");
	    		}
	    		//Explicitely: WE COPY THE FILELINK OF THE OTHER FILE AND
	    		//THUS THIS SHEETDRAFT WILL POINT TO THE OTHER SHEETDRAFT's FILE(s)!
	    		//this.filelink = res.getString("filelink");
	    		Global.addMessage("Synchronization of Sheet or Draft with its"
	    				+ " own database-representation. If this (sheet)draft"
	    				+ " has no filelink in the database, nothing will happen"
	    				+ " as there is nothing to load."
	    				+ "\r\n<br />Old filelink: " + this.filelink
	    				+ "\r\n<br />New filelink: " + res.getString("filelink")
	    				+ "\r\n<br />Those two filelinks should be identical."
	    				, "info");
	    	}
	    	/* This would require synchronization of the filelink - btw
	    	 * therefore this sheetdraft will become identical to the one
	    	 * it copied from! And this is what we want! */ 
	    	if (!this.type.equals(res.getString("type"))) {
	    		this.type = res.getString("type");
	    	}
	    	if (!this.course.equals(res.getString("course"))) {
	    		this.course = res.getString("course");
	    	}
	    	if (!this.semester.equals(res.getString("semester"))) {
	    		this.semester= res.getString("semester");
	    	}
	    	if (this.lecturer_id != res.getInt("lecturer_id")) {
	    		this.lecturer_id = res.getInt("lecturer_id");
	    	}
	    	if (!this.description.equals(res.getString("description"))) {
	    		this.description = res.getString("description");
	    	}
	    	long millis = res.getLong("whencreated");
	    	if (!this.whencreated.equals(millis)) {
	    		this.whencreated.setTimeInMillis(millis);
	    	}
	    	if (this.author == null
	    			|| !this.author.equals(res.getString("author"))) {
	    		this.author = res.getString("author");
	    	}
	    	if (this.is_draft != res.getInt("is_draft")) {
	    		this.is_draft = res.getInt("is_draft");
	    	}
	    	if (this.headermixture == null
	    			|| !this.headermixture.equals(res.getString("headermixture"))) {
	    		this.headermixture = res.getString("headermixture");
	    	}
	    	millis = res.getLong("whenchanged");
	    	if (!this.whenchanged.equals(millis)) {
	    		this.whenchanged.setTimeInMillis(millis);
	    	}
	    	//no idea if this isn't wrong? I guess it is .. because it's mine.
	    	//about saving babylonians in bits in dual system (two states: on, off)
	    	//INT(11) for millis would imply a maximum storage capacity:
	    	//=> 10^11 ms / 1000 / 60 / 60 / 24 / 365.25 = 3.1688 years
	    	//INT(13) Integer of length 13
	    	//=> 10^13 /1000 / 60 / 60 / 24 / 365.25 years = 316.8809 years
	    	//INT(15) => 10^15 - 2^32 >> 0 but
	    	//INT(15) => 10^15 - 2^64 << 0 hence 64 bits are enough.
	    }
	    
	    return this; //for chaining

	}
	/* If a foreign filelink=> sheetdraft is being copied,
	 * then this method must ENSURE that this sheetdraft's
	 * key (filelink) in the array allSheetdrafts of class Aufgaben_DB
	 * are resynchronized to the new lecturer = user !
	 * Author stays the same.
	 * (3)
	 * While semester could be updated to the new one,
	 * because this would make life easy for lecturers/authors
	 * to take over old exercise sheets to a new location.
	 * Unfortunately this REQUIRES copy by reference.
	 * So this (3) is not implemented yet, and it would
	 * not be useful really because of this application
	 * not being for students but for teachers, hence
	 * whenever a new sheet is uploaded, this sheet
	 * must be to find at exactly this semester of origin.
	 * A new version might be uploaded later, but unless
	 * this versioning (by linking and compare) gets reality,
	 * this point (3) is not necessary nor useful.
	 * 
	 * (4)
	 * So semester stays the same and this synchronisation
	 * seems not too useful for sheets but it is definitely
	 * good for drafts, containing a mix of other sheets/drafts
	 * or being a collection of many sheets/drafts in one document.
	 * Then these loaded exercises may be assigned in
	 * draftexerciseassignment or simply get dropped.
	 * 
	 * => So, this method loads allExercises of a sheetdraft
	 * to this one draft.
	 * This method can be called several times in a row to
	 * collect more exercises. until one thinks it is ready. 
	 */
//	public Sheetdraft synchronizeWithDatabaseLoadFromIt(int this_or_foreign_id_to_copy_from)
//			throws IOException, SQLException {
//		
//		//Create a new draft in database:
//	    String query = "SELECT filelink, type, course, semester, lecturer_id, description"
//	    		   + ", author, is_draft, header, whenchanged"
//	    		   + " FROM `sheetdraft`"
//	    		   + " WHERE id = " + this_or_foreign_id_to_copy_from + ""
//	    		   + "";
//	    //execute
//	    ResultSet res = Global.query(query);
//	    while (res.next()) {
//	    	/*ID of sheetdraft is never to be changed as elsewise it would be lost.*/
//	    	if (!this.filelink.equals(res.getString("filelink"))) {
//	    		//filelink empty?
//	    		if (!this.filelink.equals("")) {
//		    		Global.addMessage("Attention: In Synchronization: Filelink was different!"
//		    				+ " Potential danger."
//		    				+ "\r\n<br />Old filelink: " + this.filelink
//		    				+ "\r\n<br />New filelink: " + res.getString("filelink")
//		    				, "danger");
//	    		}
//	    		this.filelink = res.getString("filelink");
//	    	}
//	    	if (!this.type.equals(res.getString("type"))) {
//	    		this.type = res.getString("type");
//	    	}
//	    	if (!this.course.equals(res.getString("course"))) {
//	    		this.course = res.getString("course");
//	    	}
//	    	if (!this.semester.equals(res.getString("semester"))) {
//	    		this.semester= res.getString("semester");
//	    	}
//	    	if (this.lecturer_id != res.getInt("lecturer_id")) {
//	    		this.lecturer_id = res.getInt("lecturer_id");
//	    	}
//	    	if (!this.description.equals(res.getString("description"))) {
//	    		this.description = res.getString("description");
//	    	}
//	    	long millis = res.getLong("whencreated");
//	    	if (!this.whencreated.equals(millis)) {
//	    		this.whencreated.setTimeInMillis(millis);
//	    	}
//	    	if (!this.author.equals(res.getString("author"))) {
//	    		this.author = res.getString("author");
//	    	}
//	    	if (this.is_draft != res.getInt("is_draft")) {
//	    		this.is_draft = res.getInt("is_draft");
//	    	}
//	    	if (!this.headermixture.equals(res.getString("headermixture"))) {
//	    		this.headermixture = res.getString("headermixture");
//	    	}
//	    	millis = res.getLong("whenchanged");
//	    	if (!this.whenchanged.equals(millis)) {
//	    		this.whenchanged.setTimeInMillis(millis);
//	    	}
//	    	//no idea if this isn't wrong? I guess it is .. because it's mine.
//	    	//about saving babylonians in bits in dual system (two states: on, off)
//	    	//INT(11) for millis would imply a maximum storage capacity:
//	    	//=> 10^11 ms / 1000 / 60 / 60 / 24 / 365.25 = 3.1688 years
//	    	//INT(13) Integer of length 13
//	    	//=> 10^13 /1000 / 60 / 60 / 24 / 365.25 years = 316.8809 years
//	    	//INT(15) => 10^15 - 2^32 >> 0 but
//	    	//INT(15) => 10^15 - 2^64 << 0 hence 64 bits are enough.
//	    }
//	    
//	    return this; //for chaining
//	}
	
	
	
	//RETURN IF NO DECLARATIONS WERE FOUND 
	private boolean wereDeclarationsFound() {
		
		/* Already looked for exercise declarations? */
		if (this.getDeclarationSet().declarations == null) {
			Global.addMessage("Declarations were null. (Look into DeclarationFinder to debug.) Calling DeclarationFinder now."
					, "danger");
			//self heal:
			this.declarationSet = DeclarationFinder.findeDeklarationen(this);
		}
		/* Any exercise declarations found? */
		if (this.getDeclarationSet().declarations.size() == 0) {
			Global.addMessage("Sheet or Draft was not splittable because no Declarations were found."
					, "danger");
			return false;
		}
		
		/* Exercise declarations were found! */
		return true;
		
	}
	
	
	
	//SHEETDRAFT ONLY METHODS:
	/**
	 * EXTRACT EXERCISES PLAIN TEXT
	 * 
	 * @throws IOException
	 */
	public void extractExercisesPlainText() throws IOException {
		
		// Sheets, in denen keine Deklarationen gefunden wurden, abfangen
		// because they are not splittable into exercises.
		if(!wereDeclarationsFound()) {
			return ;
		}
		
		
		/* Creation of exercise objects to store in the sheet's map. */
		extractExercisesFromPlainText();		
	}
	
	
	
	/**
	 * EXTRACT EXERCISES RAW/NATIVE FORMAT
	 * 
	 * @throws IOException
	 */
	public void extractExercisesNativeFormat() throws IOException {
		/*DANGER: IT'S A REAL PROBLEM TO DETERMINE THE RAW FILETYPE EXERCISE CONTENTS BY
		  USING THE PLAIN TEXT LINE NUMBERS. IS IT GUARANTEED THAT THE LINES ARE EQUIVALENT?
		  FOR NOW: => We should not store raw content.*/
		
		
		// Sheets, in denen keine Deklarationen gefunden wurden, abfangen
		// because they are not splittable into exercises.
		if(!wereDeclarationsFound()) {
			return ;
		}
		
		
		/* THE CREATION OF EXERCISES IN THE ORIGINAL FILE FORMAT */
		//We store the link to the individual .type exercises in the exercise map (as keys).
		String ending = Global.extractEnding(filelink);
		//PDF
		if (ending.equals("pdf")) {
			HashLog.erweitereLogFile("Extract exercises PDF: Filelink: " + filelink + ".");
			//1) Extract text.
			//2) Same as text files.
			//OR
			//1) Convert to xHTML/tex.
			//2) Extract exercises like in tex/html.
			extractExercisesFromPDF();
		}
		//RTF
		else if (ending.equals("rtf")) {
			HashLog.erweitereLogFile("Extract exercises RTF: Filelink: " + filelink + ".");
			extractExercisesFromRTF();
		}
		//DOC
		else if (ending.equals("doc")) {
			HashLog.erweitereLogFile("Extract exercises DOC: Filelink: " + filelink + ".");
			extractExercisesFromDOC();
		}
		//DOCX
		else if (ending.equals("docx")) {
			HashLog.erweitereLogFile("Extract exercises DOCX: Filelink: " + filelink + ".");
			//1) The DOCX WordprocessingML object.
			/*
			Use HWPF Apache POI or DOCX4J for special tasks like exercise creation.
			1) Instantiate object representation according to/for each filetype.
			2) Find the content of the declarations in the formatted raw content.
			3) Extract all dependant XML-tags, references, et alia to another file.
			4) Store this filelink. Eventually put the sheetdraft-exercise relationship in the DB.
			*/
			extractExercisesFromDOCX();
			WordprocessingMLPackage wMLPac = new WordprocessingMLPackage();
			//TODO
		}
		//TEX
		else if (ending.equals("tex")) {
			HashLog.erweitereLogFile("Extract exercises TEX: Filelink: " + filelink + ".");
			//At this point a pdf file has to be generated again? TODO
			//Similar to the plain text version, but look for begin and end tags.
			this.setExercises(LatexCutter.cutExercises(this));
		}
		//TXT
		else if (ending.equals("txt")) {
			HashLog.erweitereLogFile("Extract exercises TXT: Filelink: " + filelink + ".");
			//Identical to the plain text version.
			if (allExercisesPlainText != null) {
				allExercisesRawFormat = allExercisesPlainText;
			}
			else {
				/* This case not really exists because in the plain text
				 * exercise extraction the exercise declarations get found!*/
				extractExercisesPlainText();
				allExercisesRawFormat = allExercisesPlainText;
			}
		}
//		else if (ending.equals("php")) {
			/*PHP IS REALLY NOT USEFUL FOR EXERCISE SHEETS, ONLY FOR GENERATION OF SUCH SHEETS.*/
//			HashLog.erweitereLogFile("Extract exercises PHP: Filelink: " + filelink + ".");
		    /*THE FOLLOWING RATHER BELONGS TO THE extractPlainText-Method.*/
//			//1) Remove/convert php related to HTML output.
//			//TODO
//			String html_only = "";
//		    //2) Same as html exercise extraction. (see below)
//			String txt_only = html_only;
//			//3) Now treat it like the txt-File.
//			this.setExercisesMap(extractExercisesFromPlainText(txt_only));
//		}
		else if (ending.equals("html") || ending.equals("htm")) {
			HashLog.erweitereLogFile("Extract exercises (x)HTML: Filelink: " + filelink + ".");
			extractExercisesFromHTML();
		}
		
		Global.addMessage("Extracted exercises in raw format from " + filelink + ".", "success");
	}
	
	
	

	/**
	 * PDF: Find and store exercises to filesystem. 
	 */
	private void extractExercisesFromPDF() {
		
		//TODO
		
	}
		
	
	/**
	 * RTF: Find and store exercises to filesystem. 
	 */
	private void extractExercisesFromRTF() {
		
		//TODO
		
	}
	
	/**
	 * DOC: Find and store exercises to filesystem. 
	 */
	private void extractExercisesFromDOC() {
		
		//TODO
		
	}
	
	
	
	/**
	 * DOCX: Find and store exercises to filesystem. 
	 */
	private void extractExercisesFromDOCX() {
		
		
		
	}
	
	
	/**
	 * HTML: Find and store exercises to filesystem. 
	 */
	private void extractExercisesFromHTML() {
		// Erzeugt fuer alle Texte zwischen zwei Deklarationen eine neue Aufgabe,
		// letzte Declaration bis zum Ende fehlt noch.
		
		//TODO
		
	}
	
	
	/**
	 * @author sabine, J.R.I.B.-Wein
	 * Fuer das Textdokument sind DocAnfang und DokEnde unwichtig, auch wie
	 * die Aufgabendeklarationen im einzelnen heissen ist unwichtig
	 * daher werden nur die Info ueber die Anzahl der Aufgaben und die
	 * Zeilennummern benoetigt.
	 * 
	 * Erstellt anhand der gefundenen Deklarationen des Sheetdraft einzelne Aufgabentexte
	 * als String[], indem der plaintext des Sheetdraft entsprechend geschnitten wird.
	 * 
	 * @state completely reworked by J.R.I.B.-Wein
	 * @param sheet Sheetdraft, dessen Einzelaufgaben bestimmt werden sollen.
	 */
	private void extractExercisesFromPlainText() {
		extractExercisesFromPlainText(this.getPlainText());
	}
	private void extractExercisesFromPlainText(String[] plainText) {
		
		// Erzeugt fuer alle Texte zwischen zwei Deklarationen eine neue Aufgabe,
		//letzte Declaration bis zum Ende fehlt noch.
		String header_of_its_sheet = "";
		String ex_filelink;
		int ex_count_and_pos = 0;
		for (; ex_count_and_pos < declarationSet.declarations.size() - 1; ex_count_and_pos++) {
			// Neues String[] mit der groesse des Zeilenabstands zwischen zwei aufeinanderfolgenden Deklarationen
			int ex_row_count_according_declarations
			= (declarationSet.declarations.get(ex_count_and_pos + 1).getLine() - declarationSet.declarations.get(ex_count_and_pos).getLine());
			String[] exerciseText = new String[ex_row_count_according_declarations];
			//TODOString[] exerciseRaw = new String[exercise_count_according_declarations];
//					String lineBeforeNewExerciseDeclaration;
//					if (!(sheetsDeclarations.get(i).getLine() < 0)) {//Prevents out of bounds.
//						lineBeforeNewExerciseDeclaration = this.getRawContent()[exercise_count_according_declarations];
//					}
			// von der Zeile der Declaration an wir ins neue String[] ruebergeschrieben
			for (int j = 0; j < ex_row_count_according_declarations; j++) {
				int nextLine = j + declarationSet.declarations.get(ex_count_and_pos).getLine();
				exerciseText[j] = plainText[nextLine];
				//TODOexerciseRaw[j] = this.getRawContent()[nextLine];
				/*ATTENTION: The first (or last) exercise-raw-content-lines
				 *           have to overlap because there is potential content
				 *           of the previous/next exercise in the same declarations line. */
			}
//					String lineAfterNewExerciseDeclaration;
//					if (this.getRawContent().length > exercise_count_according_declarations) {
//						lineAfterNewExerciseDeclaration = this.getRawContent()[exercise_count_according_declarations];
//					}
			// Erzeugen eines neuen Aufgaben-objekts
			//increment here because we start the exercise count human readable with 1 instead of 0
			ex_filelink = getFilelinkForExerciseFromPosWithoutEnding(ex_count_and_pos + 1) + "." + this.getFileEnding() + ".txt";
			//TODOString[] exerciseRawPlusExtraNextLine = new String[exerciseRaw.length + 1];
			//System.arraycopy(exerciseRaw, 0, exerciseRawPlusExtraNextLine, 0, exerciseRaw.length);
			Exercise loopExercise = new Exercise(
					ex_filelink
					, declarationSet.declarations.get(ex_count_and_pos)
					, exerciseText
					//, exerciseRaw/*TODO determine if the extra line really is req.)*/
					, header_of_its_sheet
			);
			allExercisesPlainText.put(ex_filelink, loopExercise);
			
			//Write to filesystem.
			//ReadWrite.write(exerciseText, ex_filelink);
			
			
			
		}
		
		
		//Letzte Declaration bis zum Ende:
		Declaration lastDec = declarationSet.declarations.get(declarationSet.declarations.size() - 1);
		int exercise_count_according_declarations = this.getPlainText().length - lastDec.getLine();
		String[] exerciseText = new String[exercise_count_according_declarations];
		//String[] exerciseRaw = new String[exercise_count_according_declarations];
		//String[] exerciseRawPlusExtraNextLine = new String[exerciseRaw.length + 1];
		//TODO probably overflow here:System.arraycopy(exerciseRaw, 0, exerciseRawPlusExtraNextLine, 0, exerciseRaw.length);
		for (int j = 0; j < exerciseText.length; j++) {
			int nextLine = j + lastDec.getLine();
			exerciseText[j] = this.getPlainText()[nextLine];
			//exerciseRaw[j] = this.getRawContent()[nextLine];
			/*ATTENTION: The first (or last) exercise-raw-content-lines
			 *           have to overlap because there is potential content
			 *           of the previous/next exercise in the same declarations line. */
		}
		// Erzeugen eines neuen Aufgaben-objekts
									/*increment here because we start with 1 instead of 0 */
		ex_filelink = this.getFilelinkForExerciseFromPosWithoutEnding(ex_count_and_pos + 1) + "." + this.getFileEnding() + ".txt";
		Exercise loopExercise = new Exercise(
				ex_filelink
				, lastDec
				, exerciseText
				//, exerciseRaw	/* Aufgaben_DB.extractRawContentDependingOnFiletype(filelink),*/
				//above will not work because the file not yet is written to disk!
				, header_of_its_sheet
		); 
		allExercisesPlainText.put(ex_filelink, loopExercise);
		
		//Write to filesystem.
		//ReadWrite.write(exerciseText, ex_filelink);
		
	}

			
			
	/**
	 * Löscht die Inhalte aller angegebenen Zeilen
	 * @param filename Text als String[] (Zeile pro Feld)
	 * @param anfang
	 * @param ende
	 * @return String[] mit allen Zeilen zwischen anfang und ende geloescht.
	 */
	public static String[] loescheZeilen(String[] filename, int anfang, int ende) {
		
		for (int i = anfang; i <= ende; i++ ) {
			filename[i] = "";
			}
		System.out.println("Die Zeilen " + anfang + " bis " + ende + " wurden gelöscht");
		return filename;
	}
			
	
	
	
	/**
	 * SET EXERCISES MAP
	 * @param exercisesListToSet
	 */
	public void setExercisesMap(Map<String, Exercise> exercisesMapToSet) {
		//clearing old exercise list
		this.allExercisesRawFormat = exercisesMapToSet;
		
	}
	
	public void setExercisesPlainTextMap(Map<String, Exercise> exercisesMapToSet) {
		//clearing old exercise list
		this.allExercisesPlainText = exercisesMapToSet;
		
	}
	
	/* To avoid a false exercise count, plain text and raw format content exercises are
	 * stored separately.*/
	public void setExercises(ArrayList<Exercise> exercisesListToSet) {
		//Clearing old exercises!? TODO investigate what better
		this.allExercisesRawFormat = new HashMap<String, Exercise>();
		for (Exercise e : exercisesListToSet) {
			this.allExercisesRawFormat.put(e.filelink, e);
		}
		
	}
	
	public void setExercisesPlainText(ArrayList<Exercise> exercisesListToSet) {
		//Clearing old exercises!? TODO investigate what better
		this.allExercisesPlainText = new HashMap<String, Exercise>();
		for (Exercise e : exercisesListToSet) {
			this.allExercisesPlainText.put(e.filelink, e);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*======= HELPER ===================================================== */
//	public int getId() {
//		return id;
//	}
	
//	public int getOriginSheetdraftFilelink() {
//		return originsheetdraft_filelink;
//	}

	public String getType() {
		return type;
	}

	public String getCourse() {
		return course;
	}

	public String getSemester() {
		return semester;
	}

	public int getLecturerId() {
		return lecturer_id;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}

	public Calendar getWhencreated() {
		return whencreated;
	}

	public int getIsDraft() {
		return is_draft;
	}
	public boolean isDraft() {
		return Global.intToBoolean(is_draft);
	}

	public String getHeadermixture() {
		return headermixture;
	}

	public Calendar getWhenchanged() {
		return whenchanged;
	}
	
	
	public DeclarationSet getDeclarationSet() {
		return declarationSet;
	}
	public void setDeclarationSet(DeclarationSet declarationSet) {
		this.declarationSet = declarationSet;
	}
	
	

	public Map<String, Exercise> getAllExercises() {
		return allExercisesRawFormat;
	}
	public List<String> getAllExerciseFilelinksAsList() {
		List<String> allExerciseFilelinks = new ArrayList<String>();
		allExerciseFilelinks.addAll(allExercisesRawFormat.keySet());
		return allExerciseFilelinks;
	}
	public Exercise getExerciseByFilelink(String key_filelink) {
		if (allExercisesRawFormat.get(key_filelink) == null) {
			return null;
		}
		return allExercisesRawFormat.get(key_filelink);
	}

	public Map<String, Exercise> getAllExercisesPlainText() {
		return allExercisesPlainText;
	}
	
	
	public String getFilelinkForExerciseFromPosWithoutEnding(int exercise_num_and_position) {
		//For now we keep the . experimentally. If this turns out robust on all systems
		//this is a better solution than __ because we then directly can use filelink.
		String to_append_to_filename = //"."/*"__"*/ +
				getFileEnding() /*simply add ending instead of . --> _*/
				+ "__Exercise_" + exercise_num_and_position
				+
				// ((declaration != null) ?
				//	"__splitby_" + this.getSplitterRepresentation(declaration)
				//:
				/*Declarations are the same for this sheet/draft's plain text AND raw format exercises!*/
					"__splitby_" + this.getSplitterRepresentation(this.declarationSet.declarations.get(exercise_num_and_position - 1))
				//)
				;
		return getFilelinkWithoutEnding() + to_append_to_filename;
	}

	/**
	 * If the splitterDeclaration is a custom one specified via the filelink __splitby_[splitPattern].
	 * Then we have no equivalent Muster category name like INTDOT or INTBRACKET for it. That
	 * is no problem as regex specific potentially dangerous characters like \\d for number are not
	 * allowed in paths of filesystems anyway. So those signs should never occur for the optional
	 * customized splitby pattern hint. => This can be written to the filepath directly.
	 *  
	 * The other preconfigured repository/catalogue patterns are more problematic. Here we 
	 * use the category name (INTDOTINTDOT e.g.) and write this to the filepath.
	 * 
	 * @param splitterDeclaration
	 * @return The best bet for a hopefully filepath compliant splitter pattern or representation
	 * (e.g. INTDOT, INTBRACKET, ...).
	 */
	public String getSplitterRepresentation(Declaration splitterDeclaration) {
		String splitter = null;
		
		if (splitterDeclaration != null) {
			if (splitterDeclaration.getMatchedPattern() != null) {
				if (splitterDeclaration.getFirstWord().equals("")//TODO evtlly store as attribute.
						&& splitterDeclaration.getLine() < 1) {
					/*It's a custom splitter to a very high percentage (not 100% because the line was chosen as 0 for this case and theoretically
					it's still possible to have a successful splitbyDeclaration in the first line of document 
					and still have no further FirstWord.*/
					splitter = splitterDeclaration.getMatchedPattern().toString();
					return splitter;
				}
				else {
					/*It's a preconfigured repository/common splitby pattern.*/
					splitter = Muster.getValueByPattern(splitterDeclaration.getMatchedPattern());
					//Hopefully INTDOT, INTBRACKET, ...
					return splitter;
				}
			}
		}
		
		/*No pattern at all in the given splitterDeclaration or no or an empty declaration given.*/
		splitter = Global.extractSplitterFromFilelink(filelink);//Take the one of the sheetdraft.
		//This could and should be equal to the variant when the splitterDeclaration.pattern is
		//a optional customized one.
		return splitter;

	}
	
	
	/**
	 * 
	 * @return 
	 * @throws IOException
	 * @throws SQLException
	 */
	public Sheetdraft loadAssignedAndReferencedSingleSourceExercisesFromDatabase()
			throws IOException, SQLException {
		String query = "SELECT *"
				+ " FROM draftexerciseassignment, exercise" 
				+ " WHERE sheetdraft_filelink = "/*id*/ + getFilelink()//getId()
				+ " AND exercise_filelink = exercise.filelink";/*JOIN*/
		
		ResultSet res = Global.query(query);
		//instantiate and add all exercises.
		while (res.next()) {
			allExercisesRawFormat.put(
					res.getString("exercise_filelink"),
					new Exercise(
							//origin_sheetdraft_filelink this exercise belongs to
							res.getString("filelink")
							,new Declaration(Global.determinePatternBestFittingToSplitterHint(res.getString("splitby")), "", 0)
							//origin_sheetdraft_filelink
							//,res.getString("content")//we have it in the filesystem!
							,res.getString("header")
							//,res.getInt("is_native_format") //created on the fly
							,res.getLong("whencreated")
							,res.getLong("whenchanged")
					)
			);
		}
		
		return this;	//for chaining
		
	}
	
	
	
	
	
	/**
	 * WRITE TO HARDDISK
	 * Writes the content of this sheetdraft to the filesystem/harddisk.
	 * For now it only creates two files for each exercise: raw content and plain text.
	 * The reason for two and not only one raw content and on the fly extraction of
	 * plain text is that there are images to be generated from the txt-Files too.
	 * @throws IOException 
	 * @state this method has been added
	 */
	public void writeSheetdraftContentToHarddisk() throws IOException {
		String ext = Global.extractEnding(filelink);
		
		//RAW CONTENT: is directly created by either the upload or the file conversion library.
		//=> SO THIS IS NOT NECESSARY AT ALL. It could be necessary for exercise contents though.
		
		/* Ends on '__ext.ext' because then we know if it's the original filetype! NO LONGER! SEE BELOW!*/
		//ReadWrite.writeText(getRawContent(), getFilelinkWithoutEnding() + "__" + ext + "." + ext);
		//TODO ... complicated -- depending on filetype/ending.
		//If not is native filetype, because we then already have the file representation on the harddisk.
		if (filelink.endsWith("docx") && !filelink.endsWith(".docx.docx")) {
			//If it is the original filetype then it would be load, change and save.
			//Because here is nothing to change, the original filetype document has not to be saved.
			//ReadWrite.write("", filelink);
			//1) copy
			//2) new DocxWordprocessingML(filelink_of_copy);
			//3) delete unnecessary parts.
			
		}
		else if (filelink.endsWith("tex") && !filelink.endsWith(".tex.tex")) {
			
		}
		else if (filelink.endsWith("txt") && !filelink.endsWith(".txt.txt")) {
			//Has not to be written as txt is raw and text format at the same time.
		}
		else if (filelink.endsWith("php") && !filelink.endsWith(".php.php")) {
			
		}
		else if (filelink.endsWith("html") && !filelink.endsWith(".html.html")
				|| filelink.endsWith("htm") && !filelink.endsWith(".htm.htm")) {
			
		}
		
		
		//PLAIN TEXT:
		//Also create this because we also need to generate images for plain text.
		//Do this by replacing the last of the double ending with .txt using 'Regular Expressions':
		ReadWrite.writeText(getPlainText(), getFilelink().replaceAll("[.]" + ext + "$", ".txt"));
	}
	
	
	/**
	 * WRITE EXERCISES TO HARDDISK
	 * Writes the exercises of this sheetdraft to the filesystem/harddisk.
	 * For now it only creates two files for each exercise: raw content and plain text.
	 * The reason for two and not only one raw content and on the fly extraction of
	 * plain text is that there are images to be generated from the txt-Files too.
	 * Even more important is the use of the plain text for finding declarations.
	 * This way the line numbers of plainText and rawContent are always the equivalent.
	 * This makes exercise extraction for rawContent way easier: begin & end are given by
	 * the plain text version (where highest score declarations have been found).
	 * Merely PROPAGATION to the next enclosing element/tag ends IS MISSING. 
	 * @throws IOException 
	 * @state completely rewritten
	 */
	public void writeExercisesToHarddisk(Map<String, Exercise> exerciseMap) throws IOException {
		//int allExercisesL = allExercises.keySet().size();
		//Exercise[] allExercises_array = (Exercise[])(allExercises.values().toArray());
		int pos = 0;
		for (Exercise exercise : exerciseMap.values()) {
			
//			String dirs_reversed = new StringBuffer(Global.extractPathTo(filelink)).reverse().toString();
//			int last_slash =  dirs_reversed.indexOf("/");
//			String filename_t_reversed = dirs_reversed.substring(0,last_slash);
//			String filename_t = new StringBuffer(filename_t_reversed).reverse().toString();
//			int punkt = filename_t_reversed.indexOf('.');
//			String filename_t_g_reversed = filename_t_reversed.substring(punkt + 1,filename_t.length());
//			String filename_t_g = new StringBuffer(filename_t_g_reversed).reverse().toString();

			/*We use the original filelink! Why changing it? Why not only add an exercise suffix?*/
			String ext = Global.extractEnding(filelink);
			/*We don't use a subdirectory anymore for now for practical reasons.*/
//			String dir = root + target + filename_t_g + "__" + ext;
//			File f = new File(dir);
//			if (!f.exists()) {
//				f.mkdir(); //erstelle Ordner mit bestehend aus der Name und Format der Datei
//				
//			}
			
			//For building the exercise filelink string. Positioned here because exercises start at 1.
			pos++;
			String exercise_filelink_generated_for_pos = exercise.filelink;//already generated before
			//= getFilelinkForExerciseFromPosWithoutEnding(pos);
			
			
			//ReadWrite.writeText(allExercises_array[pos].getRawContent(), exercise_filelink_generated_for_pos + "." + ext);
			//The above is no longer adequate, because we not only deal with one-file fileformats,
			//but with archived OpenXML too. Therefore we create copies in the filesystem and
			//delete unnecessary parts. Of course we then ALREADY HAVE the FILESYSTEM REPRESENTATION.
			
			//Create a plain text representation because we also need to generate images for plain text.
			//ReadWrite.writeText(allExercises_array[pos].getPlainText(), exercise_filelink_generated_for_pos  + ".txt");
			ReadWrite.writeText(exercise.getPlainText(), exercise_filelink_generated_for_pos/*  + ".txt"*/);
			
		}
		
	}
	
	
	



	
	
}
