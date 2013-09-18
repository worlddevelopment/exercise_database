package aufgaben_db;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import HauptProgramm.DeclarationSet;
import Verwaltung.HashLog;

import aufgaben_db.LatexCutter;




/**
 * A Draft is is a container for multiple exercises. 
 * 
 * @author administrator
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
	private Calendar whencreated = Calendar.getInstance();
	private int is_draft;	/*store it as an integer but handle it like a bool*/
	private String headermixture;
	private Calendar whenchanged = Calendar.getInstance();
	                                //Date. TODO: Clear if int or string suitable.
									//Funny that now it turned out as Calendar. :)
									//So I was completely wrong ... (-:

	
	//For easy fetching of exercises out of the mass by filelink as key!
	private Map<String, Exercise> allExercises = new HashMap<String, Exercise>();
	private Map<String, String> exerciseEndingsAsKeysSplitterAsValues = new HashMap<String, String>();	
	
	
	/* Analogon to splitter/split by. But with auto mode from SWP? */
	private DeclarationSet declarationSet;//TODO not need! exercises bear them!
	
	
	
	
	
	/*======= CONSTRUCTOR ==================================================*/
	public Sheetdraft () {
//		this("-1", "dummyfilelink", "dummytype", "dummycourse",
//				"dummysemester", "dummylecturer_id", "dummydescription",
//				"dummyauthor", "dummywhencreated", "dummyis_draft",
//				"dummyheadermixture", "whenchanged");

		//CREATE NEW DRAFT.
		String new_draft_type = "Mix";
		String new_draft_course = "Random-coursename_" + Math.round(Math.random() * 10000);
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
			allExercises.put(
				res.getString("filelink")
					,
			    new Exercise(
//					res.getString("sheetdraft_id"),
					res.getString("filelink")
//					,res.getString("originsheetdraft_filelink")
					,res.getString("splitby")/*needed in db for joining sheets*/
					//,res.getString("content")/*raw content*/
					,res.getString("header") /*needed in db for joining sheets*/
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
		
		//int exercisesL = sheetdraft.getAllExercises().size();
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
	 * key (filelink) in allSheetdrafts of class Aufgaben_DB
	 * are resynchronized to the new lecturer = user!
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
	

	
	
	
	//SHEETDRAFT ONLY METHOD:
	public void extractExercises() {
		String ending = Global.extractEnding(filelink);
		if (ending == "pdf") {
			HashLog.erweitereLogFile("Extract exercises PDF: Filelink: " + filelink + ".");
		}
		else if (ending == "rtf") {
			HashLog.erweitereLogFile("Extract exercises RTF: Filelink: " + filelink + ".");
		}
		else if (ending == "doc") {
			HashLog.erweitereLogFile("Extract exercises DOC: Filelink: " + filelink + ".");
		}
		else if (ending == "docx") {
			HashLog.erweitereLogFile("Extract exercises DOCX: Filelink: " + filelink + ".");
		}
		else if (ending == "tex") {
			HashLog.erweitereLogFile("Extract exercises RTF: Filelink: " + filelink + ".");
			//At this point a pdf file has to be generated again? TODO
			this.setExercises(LatexCutter.cutExercises(this));
		}
		else if (ending == "txt") {
			HashLog.erweitereLogFile("Extract exercises RTF: Filelink: " + filelink + ".");
		}
		
		/* THE SEARCH FOR EXERCISES */
		this.setDeclarationSet(DeclarationFinder.findeDeklarationen(this));
		this.setExercisesMap(ExerciseCreator.erstelleEinzelaufgaben(this));
		
		Global.addMessage("Extracted plain text from " + filelink + ".", "success");
	}
	
	
	/**
	 * SET EXERCISES MAP
	 * @param exercisesListToSet
	 */
	public void setExercisesMap(Map<String, Exercise> exercisesListToSet) {
		//clearing old exercise list
		this.allExercises = exercisesListToSet;
		
	}
	public void setExercises(ArrayList<Exercise> exercisesListToSet) {
		//Clearing old exercises!? TODO investigate what better
		this.allExercises = new HashMap<String, Exercise>();
		for (Exercise e : exercisesListToSet) {
			this.allExercises.put(e.filelink, e);
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
		return allExercises;
	}
	public List<String> getAllExerciseFilelinksAsList() {
		List<String> allExerciseFilelinks = new ArrayList<String>();
		allExerciseFilelinks.addAll(allExercises.keySet());
		return allExerciseFilelinks;
	}
	public Exercise getExerciseByFilelink(String key_filelink) {
		if (allExercises.get(key_filelink) == null) {
			return null;
		}
		return allExercises.get(key_filelink);
	}

	
	
	public String getFilelinkForExerciseFromPosWithoutEnding(int exercise_num_and_position) {
		//For now we keep the . experimentally. If this turns out robust on all systems
		//this is a better solution than __ because we then directly can use filelink.
		String to_append_to_filename = "."/*"__"*/ + getFileEnding() /*simply . --> _*/
				+ "__Exercise_" + exercise_num_and_position
				+ "__splitby_" + Global.extractSplitterFromFilelink(filelink);
		return getFilelinkWithoutEnding() + to_append_to_filename; 
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
		Exercise ex;
		while (res.next()) {
			allExercises.put(
					res.getString("exercise_filelink"),
					(ex = new Exercise(
							//origin_sheetdraft_filelink this exercise belongs to
							res.getString("filelink")
							,res.getString("splitby")
							//origin_sheetdraft_filelink
							//,res.getString("content")//we have it in the filesystem!
							,res.getString("header")
							//,res.getInt("is_native_format") //created on the fly
							,res.getLong("whencreated")
							,res.getLong("whenchanged")
					))
			);
		}
		
		return this;	//for chaining
		
	}
	
	
	
	
	
	//FROM SWP -- TODO ---------------------------------------------------------
	
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
		/* Ends on '__ext.ext' because then we now if its the original filetype! */
		ReadWrite.writeText(getRawContent(), getFilelinkWithoutEnding() + "__" + ext + "." + ext);
		//Also create this because we also need to generate images for plain text.
		ReadWrite.writeText(getPlainText(), getFilelinkWithoutEnding() + "__" + ext + "." + ".txt");
	}
	
	
	/**
	 * WRITE EXERCISES TO HARDDISK
	 * Writes the exercises of this sheetdraft to the filesystem/harddisk.
	 * For now it only creates two files for each exercise: raw content and plain text.
	 * The reason for two and not only one raw content and on the fly extraction of
	 * plain text is that there are images to be generated from the txt-Files too.
	 * Even more important is the use of the plain text for finding declarations.
	 * This way the lines of plainText and rawContent are always the same. Makes
	 * exercise extraction for rawContent way easier: begin & end are given by
	 * the plain text version (where highest score declarations have been found).
	 * Merely PROPAGATION to the next enclosing element/tag ends IS MISSING. 
	 * @throws IOException 
	 * @state completely rewritten
	 */
	public void writeExercisesToHarddisk() throws IOException {
		int allExercisesL = allExercises.keySet().size();
		Exercise[] allExercises_array = (Exercise[])(allExercises.keySet().toArray());
		for (int pos = 0; pos < allExercisesL; pos++) {
			
			String dirs_reversed = new StringBuffer(Global.extractPathTo(filelink)).reverse().toString();
			int last_slash =  dirs_reversed.indexOf("/");
			String filename_t_reversed = dirs_reversed.substring(0,last_slash);
			String filename_t = new StringBuffer(filename_t_reversed).reverse().toString();
			int punkt = filename_t_reversed.indexOf('.');
			String filename_t_g_reversed = filename_t_reversed.substring(punkt + 1,filename_t.length());
			String filename_t_g = new StringBuffer(filename_t_g_reversed).reverse().toString();

			/*We use the original filelink! Why changing it? Why not only add an exercise suffix?*/
			String ext = Global.extractEnding(filelink);
			/*We don't use a subdirectory anymore for now for practical reasons.*/
//			String dir = root + target + filename_t_g + "__" + ext;
//			File f = new File(dir);
//			if (!f.exists()) {
//				f.mkdir(); //erstelle Ordner mit bestehend aus der Name und Format der Datei
//				
//			}
			String exercise_filelink_generated_for_pos = getFilelinkForExerciseFromPosWithoutEnding(pos);
			ReadWrite.writeText(allExercises_array[pos].getRawContent(), exercise_filelink_generated_for_pos + "." + ext);
			//Also create this because we also need to generate images for plain text.
			ReadWrite.writeText(allExercises_array[pos].getPlainText(), exercise_filelink_generated_for_pos  + ".txt");
			
		}
		
	}
	
	
	




	/*======= OLD - of Artiom =============================================*/
	private ArrayList<String> aufg_ids;
	private ArrayList<String> bl_ids; 

	public ArrayList<String> getBl_ids() {
		return bl_ids;
	}

	public void setBl_ids(ArrayList<String> bl_ids) {
		this.bl_ids = bl_ids;
	}

	public ArrayList<String> getAufg_ids() {
		return aufg_ids;
	}

	public void setAufg_ids(ArrayList<String> aufg_ids) {
		this.aufg_ids = aufg_ids;
	}
	
	
	
	
	

	
	
}
