package aufgaben_db;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;


import HauptProgramm.Declaration;



/**
 * A sheetdraft Exercise is defined by a resulting String - extracted from the
 * sheet it belonged to.
 *  
 * 
 * @author administrator
 *
 */
public class Exercise extends ContentToImage {
	
	
	/*======= ATTRIBUTES ==================================================*/
//	private int sheetdraft_id;
//	//private Sheetdraft sheetdraft_exercise_is_from;
//	private String sheetdraft_filelink_exercise_is_from;//easier to keep integrity	
	/* Now even easier: a method determines the sheetdraft_filelink on the fly.
	   This is possible because the filelink of the exercise contains the filelink
	   to the sheetdraft it belongs to. */
		
	
	//in content to image: private String filelink;
	//within the exercise filelink: private String sheetdraft_filelink = 0;//the exercise belongs to currently
	//private String originsheetdraft_filelink; -- better not store this,load it from db if needed via getOriginsheetdraftFromDB
	
	/* From splitter/split by hint in the filelink we determine the best fitting pattern of enum Muster.java.
	 * We also first try to determine the pattern/Muster on the fly with automatism for several exercise
	 * declarations. The finally successful declarations with all the extra data like line of file, ... will
	 * be stored here AND in the Exercise objects redundantly! */
	private Declaration splitbyDeclaration;//if a splitby/splitter is given, determine best fitting declaration
	
	private String header;		 //each exercise goes with individual formatting
//	private int is_native_format;//<-- storing in db for joining necessary, here not
	
	private Calendar whencreated = Calendar.getInstance();
	private Calendar whenchanged = Calendar.getInstance();
								//Date. TODO: Clear if int or string suitable. 
	

	
	
	
	
	/*======= CONSTRUCTOR ==================================================*/
	//CREATE NEW EXERCISE.
	public Exercise(String filelink, String splitby, String header  
			/*, Sheetdraft sheetdraft*/) throws FileNotFoundException { 
		//CREATE EXERCISE FROM DATABASE (plainText to be read from filesystem)
		//if we need sheetdraft filelink, here it is: getSheetdraftFilelink()
		this(	
				filelink
				, new Declaration(Global.determinePatternBestFittingToSplitterHint(splitby), "", 0)
				, ReadWrite.loadText(filelink)
				//, ReadWrite.loadText(filelink)/*rawContent critical: docx is zip!*/
				, header
				, Global.booleanToInt(Global.isFilelinkNativeFormat(filelink))
				, Calendar.getInstance().getTimeInMillis()
				, Calendar.getInstance().getTimeInMillis()
		);
		
	}
	public Exercise(String filelink, Declaration splitterDeclaration, String header, 
			long whencreated, long whenchanged
			/*, Sheetdraft sheetdraft*/) throws FileNotFoundException { 
		//CREATE EXERCISE FROM DATABASE (plainText to be read from filesystem)
		//if we need sheetdraft filelink, here it is: getSheetdraftFilelink()
		this(	
				filelink
				, splitterDeclaration
				, ReadWrite.loadText(filelink)
				//, ReadWrite.loadText(filelink)/*rawContent critical: docx is zip!*/
				, header
				, Global.booleanToInt(Global.isFilelinkNativeFormat(filelink))
				, whencreated
				, whenchanged
		);
		
	}
	public Exercise(String filelink, Declaration splitterDeclaration, String header  
			/*, Sheetdraft sheetdraft*/) throws FileNotFoundException { 
		//CREATE EXERCISE FROM DATABASE (plainText to be read from filesystem)
		//if we need sheetdraft filelink, here it is: getSheetdraftFilelink()
		this(	
				filelink
				, splitterDeclaration
				, ReadWrite.loadText(filelink)
				//, ReadWrite.loadText(filelink)/*rawContent critical: docx is zip!*/
				, header
				, Global.booleanToInt(Global.isFilelinkNativeFormat(filelink))
				, Calendar.getInstance().getTimeInMillis()
				, Calendar.getInstance().getTimeInMillis()
		);
		
	}
	public Exercise(String filelink, Declaration splitbyDeclaration, String[] plainText
			/*, String[] rawContent*/
			, String header  
			/*, Sheetdraft sheetdraft*/){
		//CREATE EXERCISE
		//if we need sheetdraft filelink, here it is: getSheetdraftFilelink()
		this(	
				filelink
				, splitbyDeclaration
				, plainText
				//, rawContent
				, header
				, Global.booleanToInt(Global.isFilelinkNativeFormat(filelink))
				, Calendar.getInstance().getTimeInMillis()
				, Calendar.getInstance().getTimeInMillis()
		);
		
	}
	public Exercise(/*String sheetdraft_id,*/ String filelink
			/*, String originsheetdraft_filelink*/,
			Declaration declarationSplitbySuccessfully, String[] plainText
			/*, String[] rawContent*/
			, String header
			, int is_native_format, long whencreated, long whenchanged
			/*, Sheetdraft sheetdraft*/){
		
		this.splitbyDeclaration = declarationSplitbySuccessfully;
//		this.sheetdraft_id = sheetdraft_id;
		this.filelink = filelink;
//		this.originsheetdraft_filelink = originsheetdraft_filelink;
		this.plainText = plainText;
		//this.rawContent = rawContent;
		this.header = header;
//		this.isNativeFormat = is_native_format;//WE DETERMINE THAT ON THE FLY
		//IN THE DB WE NEED IT FOR JOINING! HERE WE DON'T!
		this.whencreated.setTimeInMillis(whencreated);
		this.whenchanged.setTimeInMillis(whenchanged);
		
		
		
		//Create files for rawContent and plainText.
		//This 'redundancy' allows online editing of exercises at a later point
		//and still having the original document content in the original file.
		//WARNING: THIS IMPLIES THAT CHANGES DON'T PROPAGATE UPWARDS TO THE SHEET.
		//   TODO: BUTTON FOR QUICKLY CREATING NEW DRAFT OUT OF THE MODIFIED EXER. 
		
		File file = new File(filelink);
		if (!file.exists()) {
			//file.mkdirs();/*ATTENTION: CREATES THE FILE AS DIRECTORY!!! */
		}
//		ReadWrite.writeText(plainText, Global.extractFilename(filelink) + ".txt");
//		ReadWrite.writeRawContentToDiskDependingOnEnding(this);
//		ReadWrite.writeText(rawContent, filelink);
		
	}
	
	
	
	/*======= METHODS =====================================================*/	
	/**
	 * Prior to that you have to set the NEW filelink to this exercise object! 
	 */
	public void moveToNewFilelink(String newFilelink) {
		//Update/set the filelink according to the sheetdraft's filelink!
		this.setFilelink(newFilelink);
		
		//Move the exercise files to the new location according to the newly set ex-filelinks.
	    Global.renameFile(
				Global.root + this.getFilelinkPrevious(),
				Global.root + this.getFilelink()
		);
	    Global.renameFile(
				Global.root + this.getImageLinkPrevious(),
				Global.root + this.getImageLink()
		);
	    Global.renameFile(
				Global.root + this.getPDFLinkPrevious(),
				Global.root + this.getPDFLink()
		);
	    
		System.out.println((Global.message += "Exercise moved - *Done*."));
		System.out.println("-----------------------------------------------");
		
		
	}
	
	/**
	 * Prior to that you have to set the NEW filelink to this exercise object! 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void updateDBToNewFilelink() throws SQLException, IOException {
		
		//Update the new filelinks in the database too.
		System.out.println((Global.message += "Updating exercise filelinks in database."));
		System.out.println("-----------------------------------------------");
		System.out.println((Global.message += "Checking if filelink already exists in database."));
		if (!Global.query("SELECT `filelink` FROM `exercise`"
					+ " WHERE `filelink` = '" + this.getFilelink() + "'").wasNull()) {
			//Attention, query was NOT empty! 					
			//=> ABBRUCH - ABORTING
			System.out.println("->WARNING! New filelink '"
			+ this.filelink 
			+ "' already exists in database.");
			System.out.println("->Aborting ...");
			System.out.println("=>Thus, those databases will be joined.");
			System.out.println("That means, we will skip the database exercise-filelink"
			+ " synchronisation. Thus the potential two candidates are being joined!"
			+ " Nevertheless this is no problem and could even be intentionally, if" +
			" user want to join two REDUNDANT entities. *Done*");
			System.out.println("-----------------------------------------------");	
		}
		
		System.out.println("->GREEN, no problem, filelink is unique. *Done*");
		System.out.println("-----------------------------------------------");
		System.out.println("Beginning update of the exercise's filelink.");
		//update exercise
		Global.query("UPDATE exercise SET filelink='" + this.getFilelink() + "' "
				+ " WHERE filelink='" + this.getFilelinkPrevious() + "' ");
		//update draftexerciseassignment
		Global.query("UPDATE draftexerciseassignment SET filelink='" + this.getFilelink() + "' "
				+ " WHERE filelink='" + this.getFilelinkPrevious() + "' ");
		
	}
	
	
	
	
	
	
	
	
	/*======= HELPER ===================================================== */
	public String getSplitBy() {
		return Global.extractSplitterFromFilelink(filelink);
		//The following is no longer used as we use e.g. INTDOT instead of the
		//corresponding regex directly as the regex easily contains invalid
		//filesystem characters.
		//return this.splitbyDeclaration.getMatchedPattern().toString();
	}
	public String getSplitByRegexPattern() {
		return this.splitbyDeclaration.getMatchedPattern().toString();
	}
	
	public Declaration getDeclaration() {
		return this.splitbyDeclaration;
	}
	
	
	//This result can also be achieved via Global.extractSheetdraftFilelinkFromExerciseFilelink.
	public String getSheetdraftFilelinkFromId() throws SQLException, IOException {
		return Global.extractSheetdraftFilelinkFromExerciseFilelink(filelink);
		//The below is only needed if IDs are used for sheetdrafts! Currently not the case!
//		java.sql.ResultSet res = 
//		Global.query("SELECT sheetdraft.filelink FROM sheetdraft, exercise"
//				+ " WHERE sheetdraft.id = exercise.sheetdraft_id");
//		while (res.next()) {
//			return res.getString("filelink");
//		}
//		Global.addMessage("No sheetdraft! Concerned exercise: "
//				+ filelink + ". Returning empty string.", "warning");
//		return "";
	}
	
	//The entry in the database has once been stored using the Sheetdraft filelink from
	//the generated exercise filelink at exercise creation/extraction time!
	public String getOriginSheetdraftFilelink() throws IOException, SQLException {
		java.sql.ResultSet res = 
		Global.query("SELECT originsheetdraft_filelink FROM exercise WHERE filelink = "
				+ this.filelink);
		while (res.next()) {
			return res.getString("originsheetdraft_filelink");
		}
		Global.addMessage("No originsheetdraft found in database: Concerned exercise: "
				+ filelink + ". Returning empty string.", "warning");
		return "";
	}
	
	
	public String getHeader() {
		return header;
	}
	
	
	
	
	
}
	
	
	
	
	
	
	