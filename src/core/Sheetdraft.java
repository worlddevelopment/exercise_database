package core;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.io.IOUtils;
import org.apache.fop.util.XMLUtil;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xpath.FoundIndex;


import org.docx4j.Docx4J;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.TraversalUtil.CallbackImpl;
import org.docx4j.finders.CommentFinder;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.org.xhtmlrenderer.docx.Docx4jDocxOutputDevice;
import org.docx4j.utils.ResourceUtils;
import org.docx4j.wml.Body;
import org.docx4j.wml.CommentRangeEnd;
import org.docx4j.wml.CommentRangeStart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.R.CommentReference;

//import org.dom4j.io.SAXWriter;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.JDomSerializer;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;
//import org.jdom2.Content;
//import org.jdom2.Document;
import org.jdom2.Element;
//import org.jdom2.JDOMException;
//import org.jdom2.Text;
//import org.jdom2.input.SAXBuilder;
//import org.jdom2.output.SAXOutputter;
import org.junit.Assert;
import org.jvnet.jaxb2_commons.ppp.Child;

import core.Part.Docx4JTravelCallback;
import processors.TeXProcessor;

import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.odfdom.pkg.OdfPackageDocument;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Paragraph;
/*For generating images from tex-chunks, i.e. LaTeX parts.*/
//import org.scilab.forge.jlatexmath.TeXConstants;
//import org.scilab.forge.jlatexmath.TeXFormula;
//import org.scilab.forge.jlatexmath.TeXIcon;
import org.w3c.dom.Node;

import docx4j_library.HeaderFooterRemove;

import java.awt.image.BufferedImage;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.ParserConfigurationException;

import core.DeclarationFinder;


/**
 * A Draft is is a container for multiple parts.
 *
 * @author Jan R.I.B.-Wein, worlddevelopment
 *
 */
public class Sheetdraft extends ContentToImage {



	// ======= ATTRIBUTES

	//in content to image: private String filelink;

//	private String originsheetdraft_filelink;//currently not needed

	private String type;

	private String course;

	private String semester;

	/**
	 * If new lecturer then at first N.N., ie. lecturer_id = 0|1 is set.
	 */
	private int lecturer_id;

	private String description = "";

	private String author;

	/**
	 * Store it as an integer but handle it like a bool.
	 */
	private int is_draft;

	private String headermixture;

	private Calendar whencreated = Calendar.getInstance();

	private Calendar whenchanged = Calendar.getInstance();

	/**
	 * For easy fetching of parts using filelink as key.
	 * Store raw, plain text content parts separate for correct counting.
	 */
	private Map<String, Part> allPartsPlainText
		= new TreeMap<String, Part>();

	private Map<String, Part> allPartsRawFormat
		= new TreeMap<String, Part>();

	private Map<String, Part> allPartsCommonFormat
		= new TreeMap<String, Part>();

	private Map<String, String> partEndingsAsKeysSplitterAsValues
		= new HashMap<String, String>();


	/**
	 * It is tried to determine the Muster on the fly by testing
	 * patterns, thus determining several sets of declarations.
	 * A splitter/split by hint in the filelink can be given.
	 * The most promising set of declarations + line number, context,...
	 * is stored here and in the ContentPart objects redundantly!
	 */
	private DeclarationSet declarationSet;//TODO Remove this redundancy!


	/**
	As in an ArrayList the order gets confused, store last found
	Part explicitely.
	(Needed for getting deepest all parts' common parent element!)
	*/
	private Part lastPartPlainText;




	// ======= CONSTRUCTOR

	public Sheetdraft () {
//		this("-1", "dummyfilelink", "dummytype", "dummycourse",
//				"dummysemester", "dummylecturer_id", "dummydescription",
//				"dummyauthor", "dummywhencreated", "dummyis_draft",
//				"dummyheadermixture", "whenchanged");

		// CREATE NEW DRAFT.
		String new_draft_type = "Mix";
		String new_draft_course = "Random_coursename_"
			+ Math.round(Math.random() * 10000);

		String new_draft_semester = Global
			.getSemtermFromCalendar(Calendar.getInstance())
			+ "_" + Global.now.get(Calendar.YEAR);

		int new_draft_lecturer_id = 0; // N.N. or N.A.
		// A draft newly created automatically has no description:
		String new_draft_description = "";
		String new_draft_headermixture = "";

		// Build the filelink
		String new_draft_filelink = Global.uploadTarget
			+ new_draft_semester + System.getProperty("file.separator")
			+ new_draft_course + System.getProperty("file.separator")
			+ "N.N." + /*new_draft_lecturer_id*/""
			+ System.getProperty("file.separator") + new_draft_type
			+ System.getProperty("file.separator")
			/*+ new_draft_course + "_" + new_draft_semester
			+ "-" + new_draft_type + "-"*/ + "randomsheet"
			+ Math.round(Math.random() * 10000)
			// If a sheetdraft is taken as a basis for a draft then
			// the draft ending will be .sheetdraft_ending.draft !
			+ ".draft.draft";
		String author;
		if (Global.session == null
				|| Global.session.getAttribute("user") == null
				|| !(Global.session.getAttribute("user")
					instanceof String)
				|| ((String) Global.session.getAttribute("user")).isEmpty()
				) {
			author = "none";
		}
		else {
			author = (String) Global.session.getAttribute("user");
		}

		setAllAttributes(
				//-1 // new draft id to be set by the database
				new_draft_filelink
				, new_draft_type
				, new_draft_course
				, new_draft_semester
				, new_draft_lecturer_id
				, new_draft_description
				, author
				, Calendar.getInstance() // uses current time as default
				, 1 // read: true
				, new_draft_headermixture
				, Calendar.getInstance() // uses current time as default
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






	// ======= METHODS
	/**
	 * Used by constructor
	 */
	public void setAllAttributes(String filelink, String type
			, String course, String semester/*, int lecturer_id*/
			, String description, String author, Calendar whencreated
			, int is_draft, String headermixture, Calendar whenchanged
			) {
		setAllAttributes(/*-1,*/ filelink, type, course, semester, 0
				/*,lecturer_id*/, description, author, whencreated
				, is_draft, headermixture, whenchanged);
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




	public void loadAllParts() throws SQLException, IOException {
		// Look for this sheetdraft's content parts (all formats):
		ResultSet res = Global.query(
				"SELECT * FROM part WHERE sheetdraft_filelink = '"+ filelink
				+"';");

		while (res.next()) {
			// For easy fetching of parts by filelink as key.
			allPartsRawFormat.put(
				res.getString("filelink")
					,
				new Part(
//					res.getString("sheetdraft_id"),
					res.getString("filelink")
//					,res.getString("originsheetdraft_filelink")
					,new Declaration(
						Global.determinePatternBestFittingToSplitterHint(
							res.getString("splitby"))// in db for joining
							,""/*res.getString("declarationFirstWord")*/
							,0/*res.getString("declarationLineNumber")*/)
					//,res.getString("content")/*raw content*/
					,res.getString("header") /* Needed in db for joining
					sheets AND not bloating resulting files because of
					unnecessary parts of the header still persisting. */
					//,res.getInt("is_native_format") created on the fly
					,res.getLong("whencreated")
					,res.getLong("whenchanged")
				)
			);
		}
		// Tackle memory leaks by closing result set, its statement:
		Global.queryTidyUp(res);

	}



	/**
	 *
	 * @throws SQLException
	 * @throws IOException
	 */
	public void synchronizePartsLocationToFilelink()
		throws SQLException, IOException {

		//int partsL = this.getAllParts().size();
		int i = 0;
		for(Part e : this.getAllParts().values()) {

			for (String ending
					: this.partEndingsAsKeysSplitterAsValues.keySet()
					) {
				System.out.print(
						(Global.message += "<br /><span>Moving"
						+ " parts with ending '" + ending
						+ "'" +  " that were split by pattern:<br />\r\n"
						+ this.partEndingsAsKeysSplitterAsValues
						.get(ending))
				);


				// Update/set the filelink according to the sheetdraft's!
				e.moveToNewFilelink(this.filelink + "__Part_" + (++i)
						+ "__splitby_" + e.getSplitBy()
						+ "." + e.getFileEnding());

				// Update the new filelinks in the database too.
				e.updateDBToNewFilelink();

			}

		}

	}



	/**
	 *
	 * @throws IOException
	 * @throws SQLException
	 */
	public void synchronizeDatabaseToThisInstance() throws IOException
		, SQLException {
		synchronizeDatabaseToThisInstance(false);
	}

	public void synchronizeDatabaseToThisInstance(
			// whether to make this draft independent
			boolean include_parts_thus_possibly_copying_all_referenced_parts)
		throws IOException, SQLException {

		// For determining whether this sheetdraft exists:
		if (Global.sqlm.exist(" sheetdraft ", "*"
					, "filelink = '" + filelink + "'")) {
			// Update already existent entry/row.
			String query = "UPDATE `sheetdraft`"
				+ " SET"
				+ " filelink = '" + this.filelink + "'"
				+ " ,type = '" + this.type + "'"
				+ " ,course = '" + this.course + "'"
				+ " ,semester = '" + this.semester + "'"
				+ " ,lecturer_id = " + this.lecturer_id + ""
				+ " ,description = '" + this.description + "'"
				+ " ,whencreated = UNIX_TIMESTAMP()"
				///*'new Timestamp(*/Global.now.getTimeInMillis()/*)'*/
				+ " ,author = '"
				+ Global.session.getAttribute("user") + "'"
				+ " ,is_draft = " + this.is_draft
				+ " ,headermixture = '" + this.headermixture + "'"
				+ " ,whenchanged = UNIX_TIMESTAMP()"
				+ "";

			// TODO Only update the database if draft content is okay!
			Global.query(query);

		}
		else {
			// Create a new draft in database:
			String query = "INSERT INTO `sheetdraft`"
				+ " (`filelink`, `type`, `course`, `semester`,"
				+ " `lecturer_id`, `description`, `whencreated`,"
				+ " `author`, `is_draft`, `headermixture`, `whenchanged`)"
				+ " VALUES ("
				+ "'" + this.getFilelinkRelative() + "'"
				+ ",'" + this.type + "'"
				+ ",'" + this.course + "'"
				+ ",'" + this.semester + "'"
				+ ", " + this.lecturer_id
				+ ",'" + this.description + "'"
				+ ", UNIX_TIMESTAMP()"
				///*'new Timestamp(*/Global.now.getTimeInMillis()/*)'*/
				+ ",'" + (String)Global.session.getAttribute("user") + "'"
				+ ", " + this.is_draft//TODO:is_draft==1 =>no plain text?
				+ ",'" + this.headermixture + "'"
				+ ", UNIX_TIMESTAMP()"
				+ ")";
			// Execute
			Global.query(query);
		}


		// Include content parts?
		if (include_parts_thus_possibly_copying_all_referenced_parts) {
			/*
			Add all parts (including assigned ones) from this
			instance to the DB as individual parts, effectively
			deleting assignments i.e. to table `part` and not
			to `draftpartassignment`!

			If no more draftpartassignments are left!
			ATTENTION this can include related ones, i.e. those
			assigned by reference which are in draftreferenceassignment.
			*/
			Global.addMessage("Synchronizing from an instance/object"
					+ " to the DB not supported. (Would make"
					+ " part assignments real standalone.)"
					, "nosuccess");

		}

	}



	/**
	 * Synchronize with database, become identical, load all parts.
	 *
	 * @throws SQLException
	 * @throws IOException
	 */
	public Sheetdraft synchronizeWithDatabaseBecomeIdenticalAndLoadAllParts()
		throws IOException, SQLException {

		return synchronizeWithDatabaseLoadFromItBecomeIdentical()
				.loadAssignedAndReferencedSingleSourcePartsFromDatabase();

	}



	/**
	 * SYNCHRONIZE WITH DATABASE LOAD FROM IT, BECOME IDENTICAL.

	 * -------1st possibility
	 * Either load all data from an existent sheetdraft in DB,
	 * become the identity of that sheetdraft, all being the same.
	 *
	 * -------2nd possibility
	 * Or load effectively all data from the database of its own entry.
	 * Any changes made not in the DB but the java object then are lost.
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

		/*
		This is useful for keeping up integrity.

		And DANGEROUS as this sheet/draft will give up all, become the
		other sheetdraft given.

		Of course if this sheet/draft's filelink already exists in the
		database then this will simply load all parts.
		*/
		// Synchronize the draft with itself:
		// Create a new draft in database:
		String query = "SELECT *"
			//filelink, type, course, semester, lecturer_id, description
			//, author, is_draft, headermixture, whenchanged, whencreated
			+ " FROM `sheetdraft`"
			+ " WHERE filelink = '"
			+ this_or_foreign_filelink_to_copy_from + "'"
			+ "";
		// execute
		ResultSet res = Global.query(query);
		res.beforeFirst();
		while (res.next()) {
			/*
			ID and filelink of sheetdraft are never changed as else
			it would be lost. A filelink can only point to one file!
			*/
			if (!this.filelink.equals(res.getString("filelink"))) {
				// filelink empty?
				if (!this.filelink.equals("")) {
					Global.addMessage("Attention: In Synchronization:"
							+ " Filelink was different! Potential danger"
							+ "\r\n<br />Old filelink: " + this.filelink
							+ "\r\n<br />New filelink: "
							+ res.getString("filelink")
							, "danger");
				}
				// THE FILELINK OF THE OTHER FILE IS COPIED, THUS THIS
				// SHEETDRAFT WILL POINT TO THE OTHER SHEETDRAFT's FILES!
				//this.filelink = res.getString("filelink");
				Global.addMessage("Synchronization of Sheet or Draft"
						+ " with its own database-representation."
						+ " If this (sheet)draft has no filelink in"
						+ " the database, nothing will happen as there"
						+ " is nothing to load."
						+ "\r\n<br />Old filelink: " + this.filelink
						+ "\r\n<br />New filelink: " + res.getString("filelink")
						+ "\r\n<br />Those two filelinks should be identical."
						, "info");
			}
			/*
			This would require synchronization of the filelink - btw
			therefore this sheetdraft will become identical to the one
			it copied from! And this is what we want!
			*/
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
					|| !this.headermixture
					.equals(res.getString("headermixture"))) {
				this.headermixture = res.getString("headermixture");
			}
			millis = res.getLong("whenchanged");
			if (!this.whenchanged.equals(millis)) {
				this.whenchanged.setTimeInMillis(millis);
			}
			// About saving babylonians in bits in dual system (on, off)
			//INT(11) for millis would imply a maximum storage capacity:
			//=> 10^11 ms / 1000 / 60 / 60 / 24 / 365.25 = 3.1688 years
			//INT(13) Integer of length 13
			//=> 10^13 /1000 / 60 / 60 / 24 / 365.25 years = 316.8809 years
			//INT(15) => 10^15 - 2^32 >> 0 but
			//INT(15) => 10^15 - 2^64 << 0 hence 64 bits are enough.
		}
		// Tackle memory leaks by closing result set, its statement:
		Global.queryTidyUp(res);
		return this; // for chaining

	}
	/*
	If a foreign filelink=> sheetdraft is copied, then this
	method must ENSURE that this sheetdraft's key (filelink)
	in the array allSheetdrafts of class PartDB|Main are
	resynchronized to the new lecturer = user. Author stays unchanged.
	(3)
	While semester could be updated to the new one, because this
	would make life easy for lecturers/authors to take over old
	part sheets to a new location, unfortunately this REQUIRED
	copy by reference.
	So this (3) is not implemented yet, and it would not be useful
	really because of this application not being for students but
	for teachers, hence whenever a new sheet is uploaded, this sheet
	must be to find at exactly this semester of origin.
	A new version might be uploaded later, but unless this versioning
	(by linking and comparing) becomes reality this point (3) is not
	necessary nor useful.

	(4)
	So semester stays the same and this synchronisation seems not too
	useful for sheets but it is definitely good for drafts, containing
	a mix of other sheets/drafts or being a collection of many sheets
	|drafts in one document.
	Then these loaded parts may be assigned in
	draftpartassignment or simply get dropped.

	=> This method loads allParts of a sheetdraft to a (1) draft.
	This method can be called several times in a row to collect more
	parts until one thinks it is ready.
	*/
//	public Sheetdraft synchronizeWithDatabaseLoadFromIt(
//			int this_or_foreign_id_to_copy_from)
//		throws IOException, SQLException {
//
//		// Create a new draft in database:
//		String query = "SELECT filelink, type, course, semester,"
//		+ " lecturer_id, description"
//		+ ", author, is_draft, header, whenchanged"
//		+ " FROM `sheetdraft`"
//		+ " WHERE id = " + this_or_foreign_id_to_copy_from
//		;
//		// Execute
//		ResultSet res = Global.query(query);
//		while (res.next()) {
//			// Id of sheetdraft is never to be changed to not lose it.
//			if (!this.filelink.equals(res.getString("filelink"))) {
//				// Filelink empty?
//				if (!this.filelink.equals("")) {
//					Global.addMessage("Attention: In Synchronization:"
//					+ " Filelink was different!"
//					+ " Potential danger."
//					+ "\r\n<br />Old filelink: " + this.filelink
//					+ "\r\n<br />New filelink: " + res.getString("filelink")
//					, "danger");
//				}
//				this.filelink = res.getString("filelink");
//			}
//			if (!this.type.equals(res.getString("type"))) {
//				this.type = res.getString("type");
//			}
//			if (!this.course.equals(res.getString("course"))) {
//				this.course = res.getString("course");
//			}
//			if (!this.semester.equals(res.getString("semester"))) {
//				this.semester= res.getString("semester");
//			}
//			if (this.lecturer_id != res.getInt("lecturer_id")) {
//				this.lecturer_id = res.getInt("lecturer_id");
//			}
//			if (!this.description.equals(res.getString("description"))) {
//				this.description = res.getString("description");
//			}
//			long millis = res.getLong("whencreated");
//			if (!this.whencreated.equals(millis)) {
//				this.whencreated.setTimeInMillis(millis);
//			}
//			if (!this.author.equals(res.getString("author"))) {
//				this.author = res.getString("author");
//			}
//			if (this.is_draft != res.getInt("is_draft")) {
//				this.is_draft = res.getInt("is_draft");
//			}
//			if (!this.headermixture.equals(res.getString("headermixture"))) {
//				this.headermixture = res.getString("headermixture");
//			}
//			millis = res.getLong("whenchanged");
//			if (!this.whenchanged.equals(millis)) {
//				this.whenchanged.setTimeInMillis(millis);
//			}
//			// About saving babylonians in bits in dual system (on,off)
//			//INT(11) for millis would imply a maximum storage capacity:
//			//=> 10^11 ms / 1000 / 60 / 60 / 24 / 365.25 = 3.1688 years
//			//INT(13) Integer of length 13
//			//=> 10^13 /1000 / 60 / 60 / 24 / 365.25 years = 316.8809 years
//			//INT(15) => 10^15 - 2^32 >> 0 but
//			//INT(15) => 10^15 - 2^64 << 0 hence 64 bits are enough.
//		}
//		// Tackle memory leaks by closing result set, its statement:
//		Global.queryTidyUp(res);
//		return this; // for chaining
//	}



	/**
	 * Return whether declarations were found.
	 */
	private boolean wereDeclarationsFound() {

		// Already looked for part declarations?
		if (this.getDeclarationSet().declarations == null) {
			Global.addMessage("Declarations were null. (Look into"
					+ " DeclarationFinder to debug.)"
					+ " Calling DeclarationFinder now."
					, "danger");
			// self heal:
			this.declarationSet
				= DeclarationFinder.findeDeklarationen(this);
		}
		// Any part declarations found?
		if (this.getDeclarationSet().declarations.size() == 0) {
			Global.addMessage("Sheet or Draft was not splittable"
					+ " because no Declarations were found."
					, "danger");
			return false;
		}

		// Part declarations were found!
		return true;

	}



	// ------- SHEETDRAFT ONLY METHODS

	/**
	 * EXTRACT PARTS PLAIN TEXT
	 *
	 * @throws IOException
	 */
	public void extractPartsPlainText() throws IOException {

		// Sheets, in denen keine Deklarationen gefunden wurden, abfangen
		// because they are not splittable into parts.
		if(!wereDeclarationsFound()) {
			return ;
		}


		// Creation of part objects to store in the sheet's map.
		extractPartsFromPlainText();

	}



	/**
	 * EXTRACT PARTS RAW/NATIVE FORMAT
	 *
	 * @throws Exception
	 */
//	public void extractPartsNativeFormat() throws Exception {
//		extractPartsNativeFormat(null);
//	}
	public void extractPartsNativeFormat(
			/*String force_source_filelink*/) throws Exception {

		/*
		DANGER: IT'S A REAL PROBLEM TO DETERMINE THE NATIVE FILETYPE
		PART CONTENTS BY USING THE PLAIN TEXT LINE NUMBERS.
		IS IT GUARANTEED THAT THE LINES ARE EQUIVALENT?
		INSTEAD WE LOOK FOR THE XML NODE THAT CONTAINS THE DECLARATION.
		TODO To support markup declarations the node surrounding also
		needs be evaluated, see also
		https://github.com/worlddevelopment/part_database/issues/1

		NOTE: Not storing raw|native content in the DB. Plain text is.
		*/


		// Is this sheet not splittable into parts?
		if(!wereDeclarationsFound()) {
			return ;
		}
//		String filelink = this.filelink;
//		if (force_source_filelink != null
//				&& new File(force_source_filelink).exists()) {
//			System.out.println("Using forced source filelink to"
//					+ " extract parts from instead of "
//					+ " native format: " + force_source_filelink
//					+ "\r\nInstead of: " + this.filelink);
//			filelink = force_source_filelink;
//		}
//
		// CREATION OF PARTS IN THE ORIGINAL FILE FORMAT
		// Link to the individual .type parts is stored in
		// the part map (as keys).
		String ending = Global.extractEnding(filelink);
		// DOC
		if (ending.equals("doc")) {
			extractPartsFromDOC();
		}
		// DOCX
		else if (ending.equals("docx")) {
			// 1) The DOCX WordprocessingML object.
			/*
			Use HWPF Apache POI or DOCX4J for special tasks like
			part creation.
			1) Instantiate object repr according to/for each filetype.
			2) Find the content of the declarations in raw content.
			3) Extract dependent XML-tags, refs, ... to another file.
			4) Store this filelink. Eventually store the
			sheetdraft-part relationship in the DB.
			*/
			extractPartsFromDOCX();

		}
		// HTML
		else if (ending.equals("html") || ending.equals("htm")) {
			/*
			TODO Do for other formats too? For the HTML base (due to
			thesis) currently only HTML is necessary as it's the only
			flavour the parts are extracted from.
			*/
			extractPartsFromHTML(filelink);
		}
		// ODT (OpenDocumentFormat Text)
		else if (ending.equals("odt")) {
			//extractPartsFromODT();
			//extractPartsFromODT_ODFToolkit();
			extractPartsFromODT_ODFToolkitSimpleAPI();

		}
		// PDF
		else if (ending.equals("pdf")) {
			// 1) Extract text.
			// 2) Same as text files.
			// OR
			// 1) Convert to xHTML/tex.
			// 2) Extract parts like in tex/html.
			extractPartsFromPDF();
		}
		// RTF
		else if (ending.equals("rtf")) {
			extractPartsFromRTF();
		}
		// TEX
		else if (ending.equals("tex")) {
			// At this point a pdf file has to be generated again? TODO
			// Similar to the plain text version, but look for begin
			// and end tags.
			this.setParts(TeXProcessor.cutParts(this));
			// Method 1) Generate PDFs out of the parts. Then create image from these PDFs.
			// TODO
			// Method 2) Create an image out of the chunk of LaTex using e.g. JLatexMath/SnuggleTex,JEuclid
			TeXProcessor.createImagesForParts(this);

		}
		// TXT
		else if (ending.equals("txt")) {
			// Identical to the plain text version.
			if (allPartsPlainText != null) {
				allPartsRawFormat = allPartsPlainText;
			}
			else {
				/*
				This case not really exists because in the plain text
				part extraction the part declarations get found
				*/
				extractPartsPlainText();
				allPartsRawFormat = allPartsPlainText;
			}
		}
//		else if (ending.equals("php")) {
			/*
			PHP IS REALLY NOT USEFUL FOR PART SHEETS, ONLY FOR
			PARSING/GENERATION OF SUCH SHEETS.
			*/
			// THE FOLLOWING RATHER BELONGS TO THE extractPlainText method:
//			// 1) Remove/convert php related to HTML output.
//			//TODO
//			String html_only = "";
//			// 2) Same as html part extraction. (see below)
//			String txt_only = html_only;
//			// 3) Now treat it like the txt-File.
//			this.setPartsMap(extractPartsFromPlainText(txt_only));
//		}


		Global.addMessage("Extracted parts in raw format from " + filelink + ".", "success");
	}




	// ======= OPEN OFFICE
	public boolean isODTElementStandAloneCapable(Element e) {
		return e.getQualifiedName().equalsIgnoreCase("p")
				|| e.getQualifiedName().equalsIgnoreCase("p")
				|| e.isRootElement();
		// The root element is stand alone capable per definition.
	}



	/*
	For each part(declaration) we store the way in the XML tree
	we took to get to this declaration in the markup.
	Walk up the tree one by one in the waypath of THE LAST part
	to check if this same Element is in the waypath of the others too!
	If this Element is in the waypath of the others, then we have
	reached the parent Element of the parts, that is the Element
	that is the highest common/same Element containing these two
	(or in an ideal/well created/structured/formatted document all)
	parts.
	Check via .contains(Element) or indexOf(Element).
	*/

	/**
	 * Determines the common parent of all parts to know where
	 * we have to split the document to get the parts completely.
	 *
	 * Also the way up from the element node where the part
	 * declarations were found is revealed up to the
	 * deepestCommonParent element. This way is stored!
	 * This is necessary to provide means to determine the
	 * highest still 'non-common' parent element of each
	 * part (declaration). As this element is expected
	 * between the deepest common parent of the part's
	 * declarations and the elementWhereDeclarationWasFound
	 * to store this partial tree only creates no problems and
	 * the highest non-common element of one and only one
	 * part declaration.
	 *
	 * @return The deepest common parent element to all parts
	 * (if well structured document, else the to two parts
	 * first common (parent) element that occurs from bottom up).
	 */
	int deepestAllPartsCommonParentElement_index = 0;
	// The root element at least it is.
	int elementsTraversed_deepestCommonParentElement_sChildContainingThisPart_index = 1;

	public void/*org.w3c.dom.Node*/
		determineAllPartsDeepestCommonParentElement() {

		//Part lastPart
		//	= this.allPartsRawFormat
		//	.get(allPartsRawFormat.size() - 1);
		Part lastPart = this.allPartsRawFormat.get(
				Global.replaceEnding(lastPartPlainText.getFilelink()
				, this.getFileEnding())
				);
		if (lastPart == null) {
			System.out.print(Global.addMessage("Last part Raw/Native"
						+ " Content is null!", "danger"));
		}

		// Because we travel bottom up and get alerted once the
		// first element is encountered that is contained in
		// the waypath/divingpath of at least two or all part
		// divingpaths.
		//int lastPart_elementsTraversed_index
		//	= lastPart.sheetdraftElementsTraversed_index;
		//int lastPart_elementsTraversed_index
		//	= lastPart.sheetdraftElementsTraversed
		//	.indexOf(lastPart
		//			.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat
		//			);
//		int lastPart_elementsTraversed_depth
//			= lastPart.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_depth;

		/*
		Travel up the last part of the document's
		waypointelements to the previously found part
		declaration. USING Element.getParentElement().
		*/


		// Get the declaration found element (rather one
		// level below) of the lastPart using the
		// determined index.
		// (Can be used because the elementsTraversed paths
		// of the non-last parts always are identicallay
		// contained within the last part's elements
		// traversed path because when searching for the part
		// declaration we always start at the root element no
		// matter which part.)
		// Node elementToCheckFor
		// MUST BE INITIALIZED HERE TO HAVE THE CHANCE TO SKIP
		// THE FIRST ENTRY!
		lastPart.deepestAllPartsCommonParentElement
		= lastPart.deepestAllPartsCommonParentElement_sChildContainingThisPart
		// The when declaration found index is determined
		// in travelUntilDeclarationFound...
		= lastPart.highestParentElementContainingThisPartOnly
		= lastPart.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat;
		//= lastPart.sheetdraftElementsTraversed
		//.get(lastPart.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_index);
		elementsTraversed_deepestCommonParentElement_sChildContainingThisPart_index
		= lastPart.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_index;

		// Keep track of the route towards
		// the deepestAllPartsCommonParentElement
		// (here still equal to its child node)
		lastPart.wayTowardsRoot.clear();
		/* This would result to adding the first element twice! */
		//lastPart.wayTowardsRoot
		//	.add(lastPart.deepestAllPartsCommonParentElement_sChildContainingThisPart);

		// Emerge level by level:
		//while(--lastPart_elementsTraversed_depth > -1) {
		// As the element when the declaration was found is
		// too deep in the XML we emerge by 1 level immediately:
		while(lastPart.deepestAllPartsCommonParentElement
				!= null) {
			// Emerge by one level

			//int elementToCheckFor_index = lastPart.sheetdraftElementsTraversed.indexOf(elementToCheckFor);
			int elementToCheckFor_index
				= lastPart.sheetdraftElementsTraversed
				.indexOf(lastPart.deepestAllPartsCommonParentElement);

			// Check all other (i.e. non-last) parts for if this
			// Element has been traversed too!
			boolean allPartsTraversedThisElementToCheckFor = true;
			boolean noOtherPartsTraversedThisElementToCheckFor = true;
			//Element elementToCheckFor = lastPart
			//	.sheetdraftElementsTraversed
			//	.get(lastPart_elementsTraversed_index);
			//org.w3c.dom.Node elementFound = null;
			int elementFound_index = 0;
			// Check in all parts for the deepest common parent element.
			for (Part partRF : allPartsRawFormat.values()) {
				// The Last Part is the part that must be the
				// reference, because here the waypath is the longest
				// as many elements come before finding the declaration
				//if (partRF.equals(lastPart)) {
				if (partRF == lastPart) {
					// The last part deepest is in the outer loop already!
					continue;
				}

				// Check in this part's waypath if the current
				// Element checked for from the last part's
				// wayelements has been passed on its way to finding
				// this currently investigated part's declaration.

				// Possibility 1: (it's not obvious that this works
				// fine, because it indexOf seems to use equal instead
				// of exactly same reference!)
				//int index = partRF.sheetdraftElementsTraversed
				//		.indexOf(elementToCheckFor);
				int index = partRF.sheetdraftElementsTraversed
					.indexOf(lastPart.deepestAllPartsCommonParentElement);
				//if (index != -1) {
				// Way 2: (not guaranteeing that the element is found
				// within ONE BRANCH! as many branches have been traversed)
				//boolean elementExists
				//	= partRF.sheetdraftElementsTraversed
				//	.get(elementToCheckFor_index) != null;

				// Final method 3:
				// Starting element is the one that was reached when
				// this part's declaration was found.
				//org.w3c.dom.Node elementCandidate
				partRF.deepestAllPartsCommonParentElement
				= partRF.deepestAllPartsCommonParentElement_sChildContainingThisPart
				= partRF.highestParentElementContainingThisPartOnly
				= partRF.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat;
				// Keep track of the route towards the deepestAllPartsCommonParentElement
				partRF.wayTowardsRoot.clear();
				// This results in the waynode being added twice.
				//partRF.wayTowardsRoot.add(partRF
				//		.deepestAllPartsCommonParentElement_sChildContainingThisPart);
				//= partRF.sheetdraftElementsTraversed.get(
				//		partRF.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_index
				//);

				boolean elementExistsInPathEmergingFromBottomUp = false;
				// Emerge up from the starting element, ie. from
				// where declaration was found:
				while ((partRF.deepestAllPartsCommonParentElement)
						!= null) {
				//while ((elementCandidate) != null) {//<-now double safe as emerging by 1 @end/else
									// If we would emerge to the parent
									// element here in the while
									// descriptor then we skip one
									// entry and MUST HAVE THE CHILD
									// initialized (see
									// elementCandidate init above).

					//if (elementCandidate.equals(elementToCheckFor)) {
					// Has to be the same reference so check this way:
					//if (elementCandidate == elementToCheckFor) {
					//if (elementCandidate
					//== lastPart.deepestAllPartsCommonParentElement) {
					if (partRF.deepestAllPartsCommonParentElement
							== lastPart.deepestAllPartsCommonParentElement) {

						// Here we have it.
						elementExistsInPathEmergingFromBottomUp = true;
						// => break/stop the while loop.
						break;

					}
					else {
						// Store this element node as a potential
						// child. While we emerge upwards in the XML
						// we will stop and no longer reach this
						// assignment once the to all parts deepest
						// common parent element is found!
						partRF.deepestAllPartsCommonParentElement_sChildContainingThisPart
						= partRF.deepestAllPartsCommonParentElement;//= elementCandidate;
						// Keep track of the route towards the
						// deepestAllPartsCommonParentElement:
						partRF.wayTowardsRoot.add(partRF.deepestAllPartsCommonParentElement_sChildContainingThisPart);
						/*
						what we are interested in is rather the index
						as this is universally valid i.e. for
						each part, not only for one!
						*/
						// And this covers the other branch so that
						// we don't have an endless loop:
						//elementCandidate = elementCandidate.getParentNode();
						if (partRF.deepestAllPartsCommonParentElement
								instanceof Child) {
							partRF.deepestAllPartsCommonParentElement
								= ((Child)partRF
										.deepestAllPartsCommonParentElement)
										.getParent();
						}
						else if (partRF.deepestAllPartsCommonParentElement
								instanceof JAXBElement) {
							JAXBElement<?> e
								= (JAXBElement<?>) partRF
								.deepestAllPartsCommonParentElement;
							if (e.getValue() instanceof Child) {
								partRF
									.deepestAllPartsCommonParentElement
									= ((Child)e.getValue()).getParent();

							}
							else {
								// debug only
								System.out.println("ROOT JAXBElement?: "
										+ e.getDeclaredType().getName());
							}

						}
						else if (partRF.deepestAllPartsCommonParentElement
								instanceof org.w3c.dom.Node) {
							partRF.deepestAllPartsCommonParentElement
								= ((org.w3c.dom.Node)partRF
										.deepestAllPartsCommonParentElement)
										.getParentNode();
						}
						//partRF.deepestAllPartsCommonParentElement
						//	= elementCandidate;// <- just a check
						//	because one of those part parent is NULL
					}

				}

				// Was this Element from within last part's
				// emerging path found in this part too?
				if (elementExistsInPathEmergingFromBottomUp) {
//					// This element exists in this part's
//					// traverse path to declaration.
//					// Thus we have found ONE the last's and this
//					// part's COMMON PARENT.
//					//elementFound = elementToCheckFor;
//					elementFound = partRF
//						.deepestAllPartsCommonParentElement;
					elementFound_index = elementToCheckFor_index;
					noOtherPartsTraversedThisElementToCheckFor
						= false;// only working for the last part!
					// => so another method is necessary after
					// determineDeepestCommon..:
					// determineHighestParentElementThatContainsOnePartOnly.
					continue; // with next part
				}
				else {
					// If only one of the parts has not
					// traversed this Element, then we have to
					// travel one level higher in the markup. This
					// is a sign for a badly, at least not ideally
					// and clean formatted or structured document.
					// Let's see which software office package
					// is most vulnerable to that.
					allPartsTraversedThisElementToCheckFor = false;
					// read: Did they also traverse exactly this
					// element (not in the loop before, but on its way
					// |travel down to finding the part declaration)?
				}



			}

			if (noOtherPartsTraversedThisElementToCheckFor) {
				lastPart.highestParentElementContainingThisPartOnly
					= lastPart.deepestAllPartsCommonParentElement;
			}

			// Check because one of those part parent is NULL.
			//if (elementFound != null
			if (allPartsTraversedThisElementToCheckFor) {
				// The to all parts deepest common parent element
				deepestAllPartsCommonParentElement_index
					= elementFound_index;
				//lastPart.deepestAllPartsCommonParentElement
				//	= elementFound;
				//return elementFound;
				return;
			}
			else {
				// Store this element node as a potential child.
				// While we emerge upwards in the XML we will stop
				// and no longer reach this assignment once the to all
				// parts deepest common parent element is found!
				lastPart.deepestAllPartsCommonParentElement_sChildContainingThisPart
					= lastPart.deepestAllPartsCommonParentElement;
				//	= elementToCheckFor;

				// In the sheetdraft the index is stored because here
				// we want to keep it object instance independent.
				// Note: For the parts, the index is the same,
				// the object instances may be equal - nevertheless
				// it's too risky as each part has its own and
				// non-equal-reference/non-equal-address Element
				// instances.
				elementsTraversed_deepestCommonParentElement_sChildContainingThisPart_index
					= lastPart.sheetdraftElementsTraversed
					.indexOf(lastPart
							.deepestAllPartsCommonParentElement);

				// Keep track of the route towards the deepestAllPartsCommonParentElement
				lastPart.wayTowardsRoot.add(lastPart
						.deepestAllPartsCommonParentElement_sChildContainingThisPart);

				// Continue with the next higher (i.e. index lower)
				// element traversed:
				if (lastPart
						.deepestAllPartsCommonParentElement
						instanceof Child) {
					lastPart.deepestAllPartsCommonParentElement
						= ((Child)lastPart
								.deepestAllPartsCommonParentElement)
								.getParent();
				}
				else if (lastPart
						.deepestAllPartsCommonParentElement
						instanceof JAXBElement) {
					JAXBElement<?> e = (JAXBElement<?>) lastPart
						.deepestAllPartsCommonParentElement;
					if (e.getValue() instanceof Child) {
						lastPart
							.deepestAllPartsCommonParentElement
							= ((Child)e.getValue())
							.getParent();

					}
					else {
						// Debug only
						System.out.println("ROOT JAXBElement of last"
								+ " part?: "
								+ e.getDeclaredType().getName());
					}

				}
				else if (lastPart
						.deepestAllPartsCommonParentElement
						instanceof org.w3c.dom.Node) {
					lastPart
						.deepestAllPartsCommonParentElement
						= ((org.w3c.dom.Node)lastPart
								.deepestAllPartsCommonParentElement)
								.getParentNode();
				}
			}

		}
		// No highest common element? Then only the root element is
		// left and was not examined.
		//return null;
	}



	/**
	 * Determines the common parent of all parts to know where
	 * we have to split the document to get the parts completely.
	 *
	 * Also the way up from the element node where the part
	 * declarations were found is revealed up to the
	 * deepestCommonParent element. The way|path is stored!
	 * This is necessary to provide means to determine the highest
	 * still 'non-common' parent element of each part
	 * (declaration). As this element is expected between the
	 * deepest common parent of the part's declarations and
	 * the elementWhereDeclarationWasFound to store this partial tree
	 * only creates no problems and the highest non-common element
	 * of one and only one part declaration.
	 *
	 * @return The deepest common parent element to all parts
	 * (if well structured document, else the to two parts'
	 * first common (parent) element that occurs from bottom up).
	 */
	public void/*org.w3c.dom.Node*/
		determineHighestParentThatContainsOneAndOnlyOnePartDeclaration() {
		int part_wayTowardsRoot_index;
		int p_wayTowardsRoot_index;
		boolean noOtherPartDeclarationContainedBelowCurrentElement;


		for (Part part : allPartsRawFormat.values()) {

			noOtherPartDeclarationContainedBelowCurrentElement
				= true;
			// Begin with deepest element
			// (elementWherePartDeclarationWasFound):
			part_wayTowardsRoot_index
				= part.wayTowardsRoot.size();// - 1;
			if (part_wayTowardsRoot_index == 0) {
				System.out.print(
						Global.addMessage("The way towards root (at"
							+ " least until the"
							+ " deepestCommonParentElementOfAllParts"
							+ " is reached) is empty!\r\nHas function"
							+ " determineDeepestAllPartsCommonParent"
							+ " been called?\r\nThe part the"
							+ " wayTowardsRoot belongs to: "
							+ part.toString()
							, "danger")
				);
				continue;
			}

			// Travel down the way from the
			// deepestAllPartsCommonParentElement to where the
			// part declaration was found.
			/*
			The first element added is deeper to the leaves than the
			ones added later. So if we start from the top
			(deepestAllPartsCommonParentElement), we will probably
			have a shorter way and come to an quicker end as the
			part declaration for sure will be surrounded by
			masses of extra-markup resulting in many more elements
			(the more we come to the leaves) between where the
			declaration was found and the
			highestOnlyOnePartDeclarationContainingElement
			than can be expected between the deepest all parts
			common parent element and the highest element containing
			one, and only one, part declaration.
			=> Start from top down.
			*/
			/*
			RUNTIME-ATTENTION: This is a cross product x times x.
			(each parts with each part that is different).
			*/
			Object partWayElementToCheckFor;
			while (--part_wayTowardsRoot_index > -1) {
				partWayElementToCheckFor
					= part.wayTowardsRoot
					.get(part_wayTowardsRoot_index);
				/*-------
				1. Assume true for each new (deeper towards part
				declaration) way element:
				*/
				noOtherPartDeclarationContainedBelowCurrentElement
					= true;


				/*-------
				2. Examine all other part declaration way elements
				*/
				for (Part p : allPartsRawFormat.values()) {
					// Only for performance: if already one
					// waypointelement has been found in one content
					// part p why then look any further in other parts? */
					if (!noOtherPartDeclarationContainedBelowCurrentElement) {
						break;
					}
					// only other parts
					if (p == part
							// Even equal parts have to be skipped
							|| p.equals(part)) {
						continue;
						/*
						Skip the part p that is the identity
						of the current part because if not we
						would not come to an end as there always
						was another part declaration (an
						identical one at same position, the identity!)
						*/
					}

					// Begin with deepest element
					// (elementWherePartDeclarationWasFound):
					p_wayTowardsRoot_index = p.wayTowardsRoot.size();// - 1;

					if (p_wayTowardsRoot_index == 0) {
						System.out.print(
								Global.addMessage("[This message can occur multiple times as it's a nested loop!]"
										+ "\r\nThe way towards root (at least until"
										+ " the deepestCommonParentElementOfAllParts is reached) is empty!"
										+ " \r\nHas function determineDeepestAllPartsCommonParent been called?"
										+ " \r\nThe e(xercise) the wayTowardsRoot belongs to: " + part.toString()
										, "danger"
								)
						);
						continue;
					}

					// Travel down the way from the deepestAllPartsCommonParentElement to where the part declaration was found.
					while (--p_wayTowardsRoot_index > -1) {
						//if (part.wayTowardsRoot.get(e_wayTowardsRoot_index) == partWayElementToCheckFor) {
						if ( XmlUtils.unwrap(p.wayTowardsRoot.get(p_wayTowardsRoot_index))
								== /*.equals(*/XmlUtils.unwrap(
										partWayElementToCheckFor)
									/*)*/ ) {
							// =>The element is in this waypath towards
							// deepestAllPartsCommonParentElement too.
							noOtherPartDeclarationContainedBelowCurrentElement
								= false;
							System.out.print(Global.addMessage(
										"Element to check for was"
										+ " found within the waypath"
										+ " (elements traversed)"
										+ " towards another part"
										+ " declaration! => Two"
										+ " part declaration"
										+ " below this level in this"
										+ " branch!\r\nPerhaps one"
										+ " part way element to"
										+ " check for deeper in the"
										+ " tree (more towards where"
										+ " the declaration was found)"
										+ " will contain one and only"
										+ " one part declaration."
										, "info"));
							break;// Then check the next deeper level
							// element, perhaps there is no other
							// part declaration element below
							// in the branch.
						}
					}


				}


				/* -------
				3. Now check whether we already reached the highest
				only one part declaration (namely this!)
				*/
				if (noOtherPartDeclarationContainedBelowCurrentElement) {
					// The quicker we found the element the better
					// as this nested loop is costly!
					part.highestParentElementContainingThisPartOnly
						= partWayElementToCheckFor;
					break;//return; <-This woud result in only
					// one part having the correct
					// highestParentElementContainingOnlyOnePart
					// assigned! But we need it to be correct for
					// all parts! =>break instead of return
				}
				//else next round or not found
				else if (part_wayTowardsRoot_index == 0) {
					System.out.print(Global.addMessage("No highest"
								+ " parent element containing one"
								+ " and only one element found for"
								+ " this part: "
								+ part.toString(), "danger"));
				}
			}

		}

	}



	/**
	 * ODT: Find and store parts to filesystem.
	 * @throws Exception
	 */
	private void extractPartsFromODT_ODFToolkit() throws Exception {


		// Unzip the openOffice Document
		OdfPackageDocument odfPkgDocument
			= OdfPackageDocument.loadDocument(filelink);
		Assert.assertTrue(odfPkgDocument.getPackage()
				.contains("content.xml"));

		org.w3c.dom.Document odfContent
			= odfPkgDocument.getFileDom("content.xml");
		org.w3c.dom.Node rootElement
			= odfContent.getDocumentElement();
		// Check foreign attribute without namespace
		org.w3c.dom.Element foreignElement
			= (org.w3c.dom.Element) rootElement.getChildNodes().item(0);
		String foreignText = foreignElement.getTextContent();


		/*
		FOR THE SHEETDRAFT ITERATE ALL ELEMENTS UNTIL DELCARATION FOUND

		1) Thus search the declaration in the markup starting from root
			METHOD A) SHEETDRAFT TRAVERSE ONLY, USING INDICES.(As now
				suddenly the indexOf seems to deliver proper results.)
				TODO redesign this approach separately.
			METHOD B) NOW DONE FOR EACH PART INDIVIDUALLY AS
				OTHERWISE THE NODES WILL NOT BE VALID AND MIXED IF NO
				NEW NODES ARE LOADED AND TRAVERSED FOR EACH PART!
				The reason that iginited method B) was the indexOf
				initially not working, thus removing of nodes proved
				impossible (output file was same as input file as if
				nothing was deleted).
		for (Part part : allPartsRawFormat.values()) {

			part.travelDownUntilDeclarationFound(rootElement);

		}


		// 2) Must be timed after all parts have been traversed.
		determineAllPartsDeepestCommonParentElement();
		 */


		// 3) From this parent element on
		// 3a) either delete the part not required (following references).
		// 3b) or extract the individual part's markup,
		// the headers and references.

		// 3a)
		// Copy complete zip archive odt file for each part,
		// delete foreign content, save.
		for (Part part : allPartsRawFormat.values()) {
			Global.copy(filelink, part.filelink);
			// Unzip the openOffice Document
			OdfPackageDocument part_odfPkgDocument = OdfPackageDocument
					.loadDocument(part.filelink);
			org.w3c.dom.Document part_odfContent = odfPkgDocument.getFileDom("content.xml");
			Node part_rootElement = odfContent.getDocumentElement();

			// FOR THE PART ITERATE ALL ELEMENTS UNTIL DELCARATION
			// FOUND TO FILL THE TRAVERSED LIST
			// 1) Therefore search for the declaration in the markup
			// starting from the root.
			part.travelDownUntilDeclarationFound(part_rootElement);

			// Figure out which part comes after this current one:
			Part partSucceding = null;
			// <- null indicates guess which sibling elements still
			// belong to this part's XML
			int partNumber
				= Global
				.extractPartNumberFromFilelink(part.filelink);

			if (partNumber < allPartsRawFormat.size() /*-1 +1*/
					) {
				// We start with 1 instead of 0 in the filesystem!
				String part_filelink
					= this.getFilelinkForPartFromPosWithoutEnding(
							partNumber + 1, partNumber + 1)
					// Here partNumber equals overall position.
					+ ".odt.odt";
				partSucceding = allPartsRawFormat.get(
						// increase by 1 as it's the following part
						part_filelink
				);
			}

			// Start at the deepest common parent element and travel
			// down to delete all the other parts' XML
			// markup/content and all that is referenced AND NOT
			// referenced by other parts.
			// => first delete XML, then at the end delete reference
			// content that is no longer referenced.
			part.deleteAllChildrenOfExceptFor(
					deepestAllPartsCommonParentElement_index
					, partSucceding);

			// 4) Save the part in native format:
			// (overwriting the one loaded from)
			part_odfPkgDocument.save(part.filelink);


		}


		// 4) Store the newly created native filelink parts
		// in the DB:
		// done in action.upload.jsp because of the index also
		// being updated inserting this raw/native format part
		// data into the DB together with the plain text parts.


		// 5) We have (hopefully) changed nothing in the
		// sheetdraft's file.
		odfPkgDocument.close(); // not saving - only closing

}



/**
 * Extract each part according to found declarations.
 */
private void extractPartsFromODT_ODFToolkitSimpleAPI()
	throws Exception {

		//TextDocument textDocument = TextDocument.loadDocument(filelink);
		//org.w3c.dom.Node rootElement = textDocument.getContentRoot();

		// 0) Copy over the parts found as plain text.
		for (Part pPT : allPartsPlainText.values()) {
			// Create a copy
			// TODO Better merge allParts-PlainText, -RawContent?
			String pRC_filelink
				= Global.replaceEnding(pPT.getFilelink(), "odt");
			ReadWrite.write("", pRC_filelink);
			Part pRC
				= new Part(pRC_filelink
						, pPT.getDeclaration(), pPT.getHeader());
			allPartsRawFormat.put(pRC_filelink, pRC);
		}



		// FOR THE SHEETDRAFT ITERATE ALL ELEMENTS UNTIL DELCARATION
		// FOUND.
		/*
		1) Thus search the declaration in the markup,starting from root
		METHOD A: SHEETDRAFT TRAVERSE ONLY AND USING INDICES.
		(as now suddenly the indexOf seems to deliver proper results.)
		TODO redesign this approach separately.
		METHOD B: NOW DONE FOR EACH PART INDIVIDUALLY AS OTHERWISE
		THE NODES WILL NOT BE VALID AND MIXED IF NO NEW NODES ARE
		LOADED AND TRAVERSED FOR EACH PART!
		The reason that iginited method B) was the indexOf initially
		not working, thus removing nodes proved impossible
		(output file was same as input file as if nothing was deleted).
		for (Part part : allPartsRawFormat.values()) {

			part.travelDownUntilDeclarationFound(rootElement);

		}


		// 2) Must be timed after all parts have been traversed.
		// (all parts retrieve their
		// deepestCommonParentElement_sChildElementContainingThePart)
		determineAllPartsDeepestCommonParentElement();
		*/

		// 3) From this parent element on
		// 3a) either delete the part not required (following refs)
		// 3b) or extract the individual part's markup, the headers
		// and references.

		// 3a)
		//textDocument.close();
		// Copy whole zip archive odt file for each part,
		// delete foreign content, save:
		for (Part part : allPartsRawFormat.values()) {

			// 3a.1) Copy native file
			//Global.copy(filelink, part.filelink);

			// 3a.2) Reload the sheetdraft's XML. For each content part
			// it will be saved separately. This is required as the
			// nodes currently known are from the sheetdraft's XML
			// and indices are not necessarily determined correctly
			// because of indexOf(Node) not working here somehow.
			TextDocument part_textDocument
				= TextDocument.loadDocument(filelink);
			org.w3c.dom.Node part_rootElement
				= part_textDocument.getContentRoot();


			// 3a.3) Retraverse it
			// 3a.4) Figure out the content part's following part.
			// 3a.5) Remove unused XML.
			retraverseNativeFormatPartsDetermineSucceedingPartAndRemoveUnused(
					part, part_rootElement
			);

			// 3a.6) Save the part native file
			//part_textDocument.getContentDom()
			//	.replaceChild(part_rootElement
			//	, part.deepestAllPartsCommonParentElement);
			//part_textDocument.getPackage()
			//	.insert(part_textDocument
			//	.getFileDom("content.xml"), "content.xml", "text/xml");
			part_textDocument.getPackage().save(part.filelink);
			part_textDocument.close();
			//part_textDocument.getPackage().save(part.filelink);


			// 3a7) Extract plain text for each part.
			part.extractPlainText();

			// 3a8) Create images native format.
			part.generateImage();


		}


		// 4) Store the newly created native filelink parts in DB:
		// Done in action.upload.jsp because of the index also being
		// updated inserting this raw/native format part data
		// into the db together with the plain text parts.


	}



	public void retraverseNativeFormatPartsDetermineSucceedingPartAndRemoveUnused(
			Part part, org.w3c.dom.Node part_rootElement)
		throws Exception {

		// 3a.3) Retraverse it
		// 1) Search the declaration in the markup starting from root.
		for (Part exer : allPartsRawFormat.values()) {
			exer.clearTraversedAndTextBuffer();// To prevent still
			// having the old data in there.
			part.setDeclarationsOfAllParts(this.declarationSet);
			// For skipping declarations if identical double parts
			// exist! Otherwise the first found match will be taken as
			// correct declaration while it is the declaration of the
			// first occurrence of the same twice existing part.
			exer.travelDownUntilDeclarationFound(part_rootElement);
		}
		/*
		After this loop the parts have traversed this
		part dom's nodes!
		=> So now if we determine the deepest common parent element
		we get the deepest in this current part containing
		only elements from within this DOM.
		*/
		determineAllPartsDeepestCommonParentElement();
		determineHighestParentThatContainsOneAndOnlyOnePartDeclaration();
		//determineHighestParentElementThatContainsOnePartOnly();

		//part_rootElement.removeChild(
		//		part.sheetdraftElementsTraversed
		//		.get(elementsTraversed_deepestCommonParentElement_sChildContainingThisPart_index));

		// IMPORTANT: Copy over the deepest common parent element.
		/*
		The indices are wrong due to matching in indexOf(Node).
		TODO Override equals method for matching org.w3c.Node?
		part.deepestAllPartsCommonParentElement
			= part.sheetdraftElementsTraversed
			.get(deepestAllPartsCommonParentElement_index);
		part.deepestAllPartsCommonParentElement_sChildContainingThisPart
			= part.sheetdraftElementsTraversed
			.get(deepestAllPartsCommonParentElement_sChild_index);
		*/

		// 3a.4) Figure out which part comes after this current one
		// (increases performance as otherwise a cross product check
		// if any part declaration comes after this part where
		// we switch the delete-nodes-mode)
		Part partSucceding = null;
		// <- null indicates guess which sibling elements still belong
		// to this part's xml
		partSucceding = getSuccedingPart(part);

		// 3a.5) remove unused xml.
		// Start at the deepest common parent element and travel down
		// to delete all the other parts' XML markup/content and
		// all that is referenced AND NOT referenced by other parts.
		// => first delete XML, then at the end delete reference
		// content that is no longer referenced.
//		part.deleteAllChildrenOfExceptFor(
//				part.deepestAllPartsCommonParentElement_index, partSucceding);
		travelUpAndRemoveAllSiblingsForEachLevel(
				part, partSucceding, part_rootElement, null);
		/*
		Previously just:
		part.removeAllSiblingsOf(
				(org.w3c.dom.Node)part
				.deepestAllPartsCommonParentElement_sChildContainingThisPart
				, partSucceding
		);
		(now the same is done for all levels up from the
		highestParentElementOnlyContainingOnePart to the root
		or deepest to all parts common parent element)
		*/
		// part_rootElement = part_textDocument.getContentRoot();

	}


	/**
	 * By reusing the fact that the declarations that were found in
	 * the plain text were ordered by line number, we can easily
	 * figure the next, i.e. succeding part to any other.
	 *
	 * @param part The declaration-found-part whose succeding
	 * declaration-found-part we wish to determine.
	 * A declaration-found-part currently in the part database
	 * can either be a solution or part.
	 * @return The succeding content part xor null if there was none.
	 * @throws Exception
	 */
	public Part getSuccedingPart(Part part)
		throws Exception {

		if (allPartsRawFormat == null) {
			System.err.println("No succeding part to " + part
					+ ". allPartsRawNativeFormat was null: "
					+ allPartsRawFormat);
			return null;
		}


		/*
		Note: Since v31.13c the part number is no longer the
		part index/position within allParts. The reason
		for this is that the solutions are now recognised
		automatically too and hence contained in the same list!
		While the parts' and solutions' numbers are all
		maintained separately for each kind (solution, part).
		That's why for determining the succeding part the
		declaration index has to be used:
		*/
		//int declaration_index
		//	= this.declarationSet.declarations
		//	.indexOf(part.getDeclaration());
		int partSucceding_index
			= this.declarationSet.declarations
			.indexOf(part.getDeclaration()) + 1;
		// The partNumber might be the same for two or more
		// parts but that's not true for the index.
		// => Check: Is it out of range/bounds?
		if (partSucceding_index > this.allPartsRawFormat.size() - 1) {
			System.err.println("No succeding part to " + part
					+ " because this one is already the last.");
			return null;
		}

////	int solutionCounter = 0;<- as this is no loop, this can not be
// used. There are alternative methods:
////	int partCounter = 0;
//		String subsequent_filelink;
		Part partSucceding = null;

		// EITHER: (string concatenation. Additionally the procedure
		// might fail at certain edge cases.)
/* NOT CHOSEN BUT SHOULD WORK IN PRINCIPLE
		// Was part found by a solution pattern?
		// <- The declaration index now ensures the correct subsequent
		// pattern is picked.
//		if (false && part.getDeclaration() != null
//				&& part.getDeclaration().getMatchedPattern()
//				.isSolutionPattern()) {
			// The solution comes after the part but has the same number.

			// Note: We start with 1 instead of 0 in the filesystem!

			// Was the previous part a solution too? (indicates
			// that it's probably only solutions in this sheet)
			// OR
			// Try to find the corresponding part that might
			// exist (if it's a mixed sheet) or might not (if it's
			// one kind only).
			int partNumber = Global.extractPartNumberFromFilelink(part.filelink);// Result could be arbritrary so check it!!

			/*
			We ask for the number being valid, though the
			partNumber cannot exceed the declaration_index
			and this i.e. the partSucceding_index was checked.
			Nevertheless it's theoretically possible to love.
			* /
			if (partNumber < allPartsRawFormat.size() /*-1 +1* /) {
				subsequent_filelink
					= this.getFilelinkForPartFromPosWithoutEnding(
							partNumber, partSucceding_index)
					+ "." + getFileEnding() + "." + getFileEnding();
					//+ ".odt.odt";
				/*
				(the sheetdraft's last ending is the part's
				native format (2nd last ending) as it's derived
				from that one)
				* /
				partSucceding = allPartsRawFormat.get(
						subsequent_filelink
				);
			}

			// Was nothing found?
			if (partSucceding == null) {
				// Try the other possibility: increase by 1 but flip
				// to the opposite of this splitby pattern (which is
				// automatically reached via the next part's
				// declaration):
				subsequent_filelink
					= this.getFilelinkForPartFromPosWithoutEnding(
					partNumber + 1, partSucceding_index)
					+ "." + getFileEnding() + "." + getFileEnding();
					//+ ".odt.odt";
				/*
				(the sheetdraft's last ending is the part's
				native format (2nd last ending) as it's derived
				from that one)
				* /
				partSucceding = allPartsRawFormat.get(
						subsequent_filelink
				);
			}

//		}
NOT CHOSEN BUT SHOULD WORK IN PRICIPLE -END */


		// OR:
		/*
		Attention: As a HashMap is not sorted, this not works out of
		the box. The map has to be sorted alphabetically at least for
		Aufgabe and Loesung as well as Part and Solution this
		is true!
		A TreeMap preserves order. It may even be given a custom
		comparator that overrides int compare(, )
		from implements Comparator<Object> .
		*/
		if (partSucceding_index < allPartsRawFormat.size()) {
			// We spare the instanceof here because we know it
			// must be an Part.
//			if (object instanceof Part) {
			return (Part)(allPartsRawFormat.values()
					.toArray()[partSucceding_index]);
//			}
		}


		return partSucceding;

	}



//	private void extractPartsFromODT() throws Exception {
//
//		// Unzip the openOffice Document
//		ZipFile zipFile = new ZipFile(filelink);
//		Enumeration<? extends ZipEntry> entries = zipFile.entries();
//		ZipEntry entry;
//		// Find the content.xml.
//		while (entries.hasMoreElements()) {
//			entry = (ZipEntry) entries.nextElement();
//			if (entry.getName().equals("content.xml")) {
//				SAXBuilder sax = new SAXBuilder();
//				Document doc = sax.build(zipFile.getInputStream(entry));
//				// Divided through 2 because of start and eng tag.
//				int elementsCountEstimation
//					= doc.getContent().toString().split("<").length / 2;
//				/*
//				for lists this is no longer necessary, just out
//				of interest how good such estimates prove to be
//				*/
//				//elementsTraversed = new Element[elementsCountEstimation];
//				Element rootElement = doc.getRootElement();
//				// Extract each part according to found declarations.
//
//
//				// 0) Copy over the parts found as plain text.
//				//for (int ei = 0; ei < allPartsPlainText.size(); ei++) {
//				for (Part pPT : allPartsPlainText.values()) {
//					// create a copy
//					// TODO: Merge allParts-PlainText, -RawContent?
//					String pRC_filelink = pPT.getFilelink().replaceAll("[.]txt$", ".odt");
//					ReadWrite.write("", pRC_filelink);
//					Part pRC = new Part(pRC_filelink
//							, pPT.getDeclaration(), pPT.getHeader());
//					allPartsRawFormat.put(pRC_filelink, pRC);
//				}
//
//
//				// 1) Search the declaration in markup starting at root
//				for (Part part : allPartsRawFormat.values()) {
//
//					part.travelDownUntilDeclarationFound(rootElement);
//
//				}
//
//
//				// 2) Must be timed after all parts have been traversed.
//				org.w3c.dom.Node deepestToAllPartsCommonParent
//				= determineAllPartsDeepestCommonParentElement();
//
//
//				// 3) From this parent element on
//				// 3a) either delete the part not required (following references).
//				// 3b) or extract the individual part's markup, the headers and references.
//
//				// 3a)
//				// Copy complete zip archive odt file for each part
//				for (Part part : allPartsRawFormat.values()) {
//					// 3a.1) Copy native file
//					Global.copy(filelink, part.filelink);
//
//					// 3a.2) Reload it, now for each part
//					Document part_doc
//						= new SAXBuilder().build(Global
//							.getInputStream(filelink, "content.xml"));
//
//					// 3a.3) Retraverse it
//					part.travelDownUntilDeclarationFound(doc.getRootElement());
//
//					//3a.4) Figure out which part comes after this current one:
//					Part partSucceding = null;
//					// <- null indicates guess which sibling elements
//					// still belong to this part's XML
//					int partNumber = Global
//						.extractPartNumberFromFilelink(
//							part.filelink);
//					if (partNumber < allPartsRawFormat.size() /*-1 +1*/) {
//						//We start with 1 instead of 0 in the filesystem!
//						String part_filelink = this
//							.getFilelinkForPartFromPosWithoutEnding(
//								partNumber + 1)
//							+ ".odt.odt";
//						partSucceding = allPartsRawFormat
//							.get(//increase by 1 as it's the following part
//								part_filelink
//						);
//					}
//
//					// 3a.5) Remove unused XML.
//					// Start at the deepest common parent element
//					// and travel down to delete all the other
//					// parts' XML markup/content and all that is referenced
//					// AND NOT referenced by other parts.
//					// => first delete XML, then at the end delete
//					// reference content that is no longer referenced.
//					part.deleteAllChildrenOfExceptFor(
//							deepestAllPartsCommonParentElement_index
//							, partSucceding);
//
//
//					// 3a.6) Save the part native file
////				new SAXOutputter().output(doc);
//				}
//
//
//				// 4) Store the newly created native filelink
//				// parts in the DB:
//				// done in action.upload.jsp because of the index
//				// also being updated inserting this raw/native
//				// format part data into the DB together with
//				// the plain text parts.
//
//
//				break;
//			}
//		}
//
//
//
//	}



	/**
	 * PDF: Find and store parts to filesystem.
	 */
	private void extractPartsFromPDF() {

		//TODO

	}


	/**
	 * RTF: Find and store parts to filesystem.
	 */
	private void extractPartsFromRTF() {

		//TODO

	}

	/**
	 * DOC: Find and store parts to filesystem.
	 */
	private void extractPartsFromDOC() {

		//TODO

	}



	/**
	 * DOCX: Find and store parts to filesystem.
	 *
	 * @throws Exception
	 */
	private void extractPartsFromDOCX() throws Exception {


//		WordprocessingMLPackage wMLPac = Docx4J.load(new File(filelink));
//		MainDocumentPart mainDocumentPart = wMLPac.getMainDocumentPart();
//		org.docx4j.wml.Document wmlDocumentEl
//			= (org.docx4j.wml.Document)mainDocumentPart.getJaxbElement();
//
//		HeaderFooterRemove.removeHFFromFile(new File(filelink));
//		Body body = wmlDocumentEl.getBody();

//		for (Object o : wmlDocumentEl.getContent()) {
//			if (o instanceof Element) {
//				//((Element)o).;
//			}
//		}
		//TODO follow relationships (see facade as a possible reference?)
		//wMLPac.relationships.
		//Docx4J.


		// Extract each part according to found declarations.
		// 0) Copy over the parts found as plain text.
		//for (int ei = 0; ei < allPartsPlainText.size(); ei++) {
		for (Part pPT : allPartsPlainText.values()) {
			// Create a copy
			// TODO: Better join allParts-PlainText, -RawContent?
			String pRC_filelink = Global
				.replaceEnding(pPT.getFilelink(), "docx");
			ReadWrite.write("", pRC_filelink);
			Part pRC = new Part(pRC_filelink, pPT.getDeclaration(), pPT.getHeader());
			/*
			Enable if plainText shall not be reloaded dynamically
			for each part flavour format, instead only once for
			the native format.
			pRC.plainText = pPT.getPlainText();
			*/
			allPartsRawFormat.put(pRC_filelink, pRC);
		}



		// FOR THE SHEETDRAFT ITERATE ALL ELEMENTS UNTIL DECLAR FOUND.
		// 1) Search the declaration in the markup starting from root.
		/*
		METHOD A: SHEETDRAFT TRAVERSE ONLY AND USING INDICES.
		(as now suddenly the indexOf seems to deliver proper results.
		TODO redesign this approach separately ensuring equals works.)
		METHOD B: NOW DONE FOR EACH PART INDIVIDUALLY AS
		OTHERWISE THE NODES WILL NOT BE VALID AND MIXED IF NO NEW
		NODES ARE LOADED AND TRAVERSED FOR EACH PART!
		The reason that iginited method B) was the indexOf initially
		not working, thus removing nodes proved impossible (output
		file was same as input file as if nothing was deleted).
		for (Part part : allPartsRawFormat.values()) {

			part.travelDownUntilDeclarationFound(rootElement);

		}


		// 2) Must be timed after all parts have been traversed.
		// (all parts retrieve their
		// deepestCommonParentElement_sChildElementContainingThePart)
		determineAllPartsDeepestCommonParentElement();
		*/

		// 3) From this parent element on
		// 3a) either delete the part not required (following refs)
		// 3b) or extract the individual part's markup, the
		// headers and references.

		// 3a)
		//textDocument.close();
		// Copy complete zip archive odt file for each part, delete foreign content, save.
		for (Part part : allPartsRawFormat.values()) {

			// 3a.1) copy native file
			//Global.copy(filelink, part.filelink);

			// 3a.2) reload the sheetdraft's XML - now for each
			// part it will be saved separately. This is required
			// as the nodes currently known are from the sheetdraft's
			// XML only!
			// And indices are not necessarily determined correctly
			// because of indexOf(Node) not working here somehow.
			WordprocessingMLPackage wMLPac
				= Docx4J.load(new File(filelink));
			MainDocumentPart mainDocumentPart
				= wMLPac.getMainDocumentPart();
			org.docx4j.wml.Document wmlDocumentEl
				= (org.docx4j.wml.Document)mainDocumentPart
				.getJaxbElement();

			Body rootElement = wmlDocumentEl.getBody();

			// To prevent still having the old data in there:
			part.clearTraversedAndTextBuffer();
			//exer.travelDownUntilDeclarationFound(part_rootElement);
			Part.Docx4JTravelCallback part_traveller
				= part.new Docx4JTravelCallback();
//			part_traveller.setDeclaration(part.getDeclaration());
			// For skipping declarations if identical double
			// parts exist! Otherwise the first found match
			// will be taken as correct declaration while it is
			// the declaration of the first occurrence of the same
			// twice existing part.
			part/*_traveller*/
				.setDeclarationsOfAllParts(this.declarationSet);
			new TraversalUtil(rootElement, part_traveller);

			// 3a.3) Retraverse it
			// because for each part that is examined all
			// other parts have to be considered on exactly
			// the same node tree!
			// 1) Search the declaration in markup starting from root.
			for (Part exer : allPartsRawFormat.values()) {
				if (exer.equals(part)) {
					continue;
				}
				// To prevent still having the old data in there
				exer.clearTraversedAndTextBuffer();
				//exer.travelDownUntilDeclarationFound(
				//		part_rootElement);
				Part.Docx4JTravelCallback finder
					= exer.new Docx4JTravelCallback();
//				finder.setDeclaration(exer.getDeclaration());
				// Skip declarations if identical double parts
				// exist! Otherwise the first found match will be
				// taken as correct declaration while it is the
				// declaration of the first occurrence of the same
				// twice existing part.
				part/*finder*/.setDeclarationsOfAllParts(
						this.declarationSet);
				new TraversalUtil(rootElement, finder);
			}
			/*
			After this loop the parts have traversed this
			part dom's nodes!
			*/
			/*
			=> So now if we determine the deepest common parent
			element in this current part containing only
			elements from within this DOM.
			*/
			determineAllPartsDeepestCommonParentElement();
			determineHighestParentThatContainsOneAndOnlyOnePartDeclaration();
			//determineHighestParentElementThatContainsOnePartOnly();

			//part_rootElement.removeChild(
			//		part.sheetdraftElementsTraversed
			//		.get(elementsTraversed_deepestCommonParentElement_sChildContainingThisPart_index));

			// IMPORTANT: copy over the deepest common parent element
			/*
			The indices are not determined correctly due to matching
			in indexOf(Node)
			part.deepestAllPartsCommonParentElement
				= part.sheetdraftElementsTraversed
				.get(deepestAllPartsCommonParentElement_index);
			part.deepestAllPartsCommonParentElement_sChildContainingThisPart
				= part.sheetdraftElementsTraversed
				.get(deepestAllPartsCommonParentElement_sChild_index);
			*/

			// 3a.4) Figure out which part comes after this:
			Part partSucceding = getSuccedingPart(part);
			// <- null indicates guess which sibling elements still
			// belong to this part's XML
//			int partNumber = Global
//				.extractPartNumberFromFilelink(part.filelink);
//			if (partNumber < allPartsRawFormat.size() /*-1 +1*/) {
//				// We start with 1 instead of 0 in the filesystem!
//				String part_filelink = this
//					.getFilelinkForPartFromPosWithoutEnding(
//						partNumber + 1, partNumber + 1)
//					+ ".docx.docx";
//				partSucceding = allPartsRawFormat
//					.get(
//						// inc by 1 as it's the following content part
//						part_filelink
//				);
//			}


			// 3a.5) Remove unused XML.
			// Start at the deepest common parent element and travel
			// down to delete all the other parts' XML
			// markup/content and all that is referenced AND NOT
			// referenced by other parts.
			// => first delete XML, then at the end delete reference
			// content that is no longer referenced.
//			part.deleteAllChildrenOfExceptFor(
//					part.deepestAllPartsCommonParentElement_index
//					, partSucceding);
			travelUpAndRemoveAllSiblingsForEachLevel(part
					, partSucceding, rootElement, part_traveller);

			//part_rootElement = part_textDocument.getContentRoot();

			// 3a.6) Save the part native file
			//part_textDocument.getContentDom().replaceChild(
			//		part_rootElement
			//		, part.deepestAllPartsCommonParentElement);
			//part_textDocument.getPackage().insert(part_textDocument.getFileDom("content.xml"), "content.xml", "text/xml");
			Docx4J.save(wMLPac, new File(part.filelink)
					, Docx4J.FLAG_NONE);
			//part_textDocument.close();
			//part_textDocument.getPackage()
			//	.save(part.filelink);
			HeaderFooterRemove.removeHFFromFile(
					new File(filelink));// TODO necessary or redundant?

			// 3a7) Extract plain text for each part.
			part.extractPlainText();

			// 3a8) Create images native format.
			part.generateImage();

		}


		// 4) Store the newly created native filelink parts in DB:
		// done in action.upload.jsp because of the index also being
		// updated inserting this raw/native format part
		// data into the DB together with the plain text parts.


//		Using XPath
//		LoadFromZipNG z = new LoadFromZipNG();
//		 WordprocessingMLPackage wordMLPackage
//			= (WordprocessingMLPackage) z.get(new FileInputStream(
//					new java.io.File(Global.root + ".docx")));
//
//		MainDocumentPart documentPart
//			= wordMLPackage.getMainDocumentPart();
//
//		String xpath
//			= "//w:r[w:t[contains(text(),'" + "indicator" + "')]]";
//
//		List<Object> list
//			= documentPart.getJAXBNodesViaXPath(xpath, false);
//
//			for (int i = 0; i < list.size(); i++)
//			{
//				org.docx4j.wml.R r = (org.docx4j.wml.R) list.get(i);
//
//				org.docx4j.wml.P parent
//					= (org.docx4j.wml.P) r.getParent();
//
//				parent.getContent().remove(r);
//			}
//
//			ByteArrayOutputStream fos = new ByteArrayOutputStream();
//			SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
//			saver.save(fos);
//
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//
//		XmlUtils.marshaltoString(parent, true, true);


	}



	public void travelUpAndRemoveAllSiblingsForEachLevel(
			Part part, Part partSucceding
			, Object rootElement
			, Part.Docx4JTravelCallback part_traveller)
		throws Exception {

		// Old approach is in versioning system, the difference is
		// the call of the remove function multiple times for each
		// element from highestOnlyOnePartContainingElement up
		// until root.
		// 1 Start with this part's
		// highestOnlyOnePartDeclarationContainingWayToWhereDeclarationFoundElement
		//  as the current element.
		Object elementNotToRemove
			= part.highestParentElementContainingThisPartOnly;

		while (elementNotToRemove != null
				/*
				The optional condition for keeping the common
				contents like general info, sheet heading et alia:
				*/
				&& !XmlUtils.unwrap(elementNotToRemove)
				.equals(XmlUtils.unwrap(
						part.deepestAllPartsCommonParentElement
						))
				/* Cancel to keep all non-content related settings. */
				&& !XmlUtils.unwrap(elementNotToRemove)
				.equals(XmlUtils.unwrap(rootElement))) {

			System.out.println("ElementNotToRemove: "
					+ XmlUtils.unwrap(elementNotToRemove));
			// 2  Remove all siblings of mentioned current element.
			// DOCX4J: LiveList:ParentElement_sChildrenElements,
			// XML node wrapped in a JAXBElement.
			///*Node*/List<Object> children = TraversalUtil
			//	.getChildrenImpl(((Child)(XmlUtils.unwrap(
			//		elementNotToRemove))).getParent());
			// ODT: remove directly from ParentElement.
			//NodeList children = ((org.w3c.dom.Node)
			//	elementNotToRemove).getParentNode().getChildNodes();
			// TODO <- reminder that odt functionality exists.
			/*
			for (Object child : children) {
				if (child.equals(elementNotToRemove)
						|| XmlUtils.unwrap(child)
						.equals(elementNotToRemove)) {
					continue;
				}
				// Debug Information:
				if (Global.debug) {
					System.out.println("DeepestCommon_sChildElement: "
							+ part
							.deepestAllPartsCommonParentElement_sChildContainingThisPart
							//.getClass()
							+ " \r\nHighestContainingOnlyOnePart: "
							+ part
							.highestParentElementContainingThisPartOnly);
				}
				// Remove:
				//TraversalUtil.getChildrenImpl(
				//		((org.w3c.dom.Node)elementNotToRemove)
				//		.getParentNode() ).remove(child);
				children.remove(child);
			}
			*/
			/*
			THIS IS REQUIRED FOR THE SPECIAL CASE THAT MANY HEADINGS
			AND PARAGRAPHS OF DIFFERENT PARTS ARE WITHIN THE
			SAME PARENT ELEMENT.
			*/
			if (part_traveller != null) {
				part_traveller.removeAllSiblingsOf(
					//part.deepestAllPartsCommonParentElement_sChildContainingThisPart
					//part.highestParentElementContainingThisPartOnly
					elementNotToRemove
					, partSucceding
				);
			}
			else {
				part.removeAllSiblingsOf(
						//(org.w3c.dom.Node)part
						//	.deepestAllPartsCommonParentElement_sChildContainingThisPart
						//(org.w3c.dom.Node)part
						//	.highestParentElementContainingThisPartOnly
						(org.w3c.dom.Node)elementNotToRemove
						, partSucceding
				);
			}

			// 3 Switch to the parent element of the current element
			// as the new current element.
			// Go to 2 (Optional condition: only if current element
			// is not equal to the deepestCommonElement).
			if (XmlUtils.unwrap(elementNotToRemove) instanceof Child) {
				elementNotToRemove = ((Child)XmlUtils
						.unwrap(elementNotToRemove)).getParent();
			}
			else if (elementNotToRemove instanceof org.w3c.dom.Node) {
				elementNotToRemove = ((org.w3c.dom.Node)
						elementNotToRemove).getParentNode();
			}
			else {
				elementNotToRemove = null;
			}

		}

		// 4 Terminate. All non-this-part-related content is
		// removed (besides the to all parts common content:
		// only if Optional condition).
		//return;
	}






	/**
	 * HTML: Find and store parts to filesystem.
	 *
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	private void extractPartsFromHTML()
		throws FileNotFoundException, Exception {
		extractPartsFromHTML(this.filelink);
	}

	private void extractPartsFromHTML(String filelink) throws FileNotFoundException, Exception {

		// Achieve a JDOM or DOM representation after cleaning and
		// tidying up the HTML to XHTML:
		HtmlCleaner cleaner = new HtmlCleaner();

		// Take default cleaner properties:
		CleanerProperties props = cleaner.getProperties();
		props.setOmitComments(true);
		//props.set();

		//new PrettyXmlSerializer(props)
		//	.writeToFile(tagNode, "<fileName>.xml", "utf-8");
		/*
		// Traverse whole DOM and update images to absolute URLs
		node.traverse(new TagNodeVisitor() {
			public boolean visit(TagNode tagNode, HtmlNode htmlNode) {
				if (htmlNode instanceof TagNode) {
					TagNode tag = (TagNode) htmlNode;
					String tagName = tag.getName();
					if ("img".equals(tagName)) {
						String src = tag.getAttributeByName("src");
						if (src != null) {
							tag.setAttribute("src"
								, Utils.fullUrl(siteUrl, src));
						}
					}
				}
				else if (htmlNode instanceof CommentNode) {
					CommentNode comment = ((CommentNode) htmlNode);
					comment.getContent().append(" -- By HtmlCleaner");
				}
				// Tells visitor to continue traversing the DOM tree
				return true;
			}
		});

		SimpleHtmlSerializer serializer =
			new SimpleHtmlSerializer(cleaner.getProperties());
		serializer.writeToFile(node, "<fileName>.html");
		*/




		// 0) Copy over the parts found as plain text.
		//for (int ei = 0; ei < allPartsPlainText.size(); ei++) {
		for (Part pPT : allPartsPlainText.values()) {
			// Create a copy
			// TODO: Merge allParts-PlainText, -Raw/NativeContent?
			String pRC_filelink
				= Global.replaceEnding(pPT.getFilelink(), "html");
			ReadWrite.write("", pRC_filelink);
			Part pRC = new Part(pRC_filelink
					, pPT.getDeclaration(), pPT.getHeader());
			allPartsRawFormat.put(pRC_filelink, pRC);
		}


		// 3) From this parent element on either
		// 3a) delete the part not required (following refs) or
		// 3b) extract the individual part's markup,
		// the headers and references.

		// 3a)
		//textDocument.close();
		// Copy complete zip archive odt file for each part,
		// delete foreign content, save.
		for (Part part : allPartsRawFormat.values()) {

			// 3a.1) copy native file
			//Global.copy(filelink, part.filelink);

			// 3a.2) reload the sheetdraft's xml - now for each
			// part it will be saved separately. This is required
			// as the nodes currently known are from the sheetdraft's
			// XML only!
			// and indices are not necessarily determined correctly
			// because of indexOf(Node) not working here somehow.
			TagNode rootTagNode = cleaner.clean(new File(filelink));
			// <-this.filelink is absolute.
			org.w3c.dom.Document part_document
				= new DomSerializer(props, true).createDOM(rootTagNode);
			org.w3c.dom.Node part_rootElement
				= part_document.getDocumentElement();


			// 3a.3) Retraverse it
			// 3a.4) Figure out the part's following part.
			// 3a.5) Remove unused xml.
			retraverseNativeFormatPartsDetermineSucceedingPartAndRemoveUnused(
					part, part_rootElement
			);

			// 3a.6) save the part native file
			// TODO Wants a TagNode so after serialization we will
			// have to use another method to save.
			//new SimpleHtmlSerializer(props)
			//	.writeToFile(part_rootElement, part.filelink);
			javax.xml.transform.Transformer transformer
				= javax.xml.transform
				.TransformerFactory.newInstance().newTransformer();

			javax.xml.transform.Result target
				= new javax.xml.transform.stream
				.StreamResult(new File(part.filelink));
			// For testing use System.out
			javax.xml.transform.Source source
				= new javax.xml.transform.dom
				.DOMSource(part_document);

			transformer.transform(source, target);
			//part_document.getPackage().save(part.filelink);
//			part_document.close();


			// 3a7) Extract plain text for each part.
			part.extractPlainText();

			// 3a8) Create images native format.
			part.generateImage();


		}


		// 4) Store the newly created native filelink parts
		// in the DB:
		// done in action.upload.jsp because of the index also being
		// updated. Inserting this raw/native format part data
		// into the DB together/with the plain text parts.


	}



	/**
	 * For the text document DocAnfang and DocEnd are not important.
	 * As for plain text documents raw content equals content, the
	 * only important information for splitting into content parts
	 * are the line numbers from the declarations found.
	 *
	 * @author sabine, J.R.I.B.-Wein, worlddevelopment
	 * @state completely reworked. Actually this has nothing to do
	 * with what has been there at the beginning at all.
	 * @param sheet Sheetdraft, to split into content parts.
	 */
	private void extractPartsFromPlainText() {
		extractPartsFromPlainText(this.getPlainText());
	}
	private void extractPartsFromPlainText(String[] plainText) {
		/*
		TODO If the filtering of double declarations leads to
		removed content (so if the declaration may be equal but
		the content was not!) then the declarations should not
		be filtered at all and rather here according to the content
		the filtering should be performed.
		Then the declarations have to be updated (the doubles that
		were filtered here have to be removed) - so as to not have
		to refilter the Native format content what is almost
		impossible anyway.
		*/

		// Creates for all texts between 2 declarations a new
		// part, i.e. all but from last declaration to the end.
		String header_of_its_sheet = "";
		String ex_filelink;
		int ex_count_and_pos = -1;
		int solution_count_and_pos = -1;
		int dec_count_and_pos = 0;
		for (; dec_count_and_pos
				< declarationSet.declarations.size() - 1
				; dec_count_and_pos++) {

			// Create array sized to fit the line number difference
			// of two adjacent declarations.
			int ex_row_count_according_declarations
				= (declarationSet.declarations.get(
							dec_count_and_pos + 1)
						.getLineNumber() - declarationSet.declarations
						.get(dec_count_and_pos).getLineNumber());
			String[] partText
				= new String[ex_row_count_according_declarations];
			//TODOString[] partRaw = new String[part_count_according_declarations];
//			String lineBeforeNewPartDeclaration;
//			// Prevent out of bounds:
//			if (!(sheetsDeclarations.get(i).getLine() < 0)) {
//				lineBeforeNewPartDeclaration
//					= this.getRawContent()[part_count_according_declarations];
//			}
			// From the line of the declaration copy into String[]:
			for (int j = 0; j < ex_row_count_according_declarations
					; j++) {
				int nextLine = j + declarationSet.declarations
					.get(dec_count_and_pos).getLineNumber();
				partText[j] = plainText[nextLine];
				//TODOpartRaw[j] = this.getRawContent()[nextLine];
				/*
				ATTENTION: The first|last part-raw-content-lines
					have to overlap because there is potential content
					of the previous|next part in the same
					line as the declaration's.
				*/
			}
//			String lineAfterNewPartDeclaration;
//			if (this.getRawContent().length
//					> part_count_according_declarations) {
//				lineAfterNewPartDeclaration = this
//					.getRawContent()[part_count_according_declarations];
//			}
			// Create a new content part object:
			// Increment here because we start the part count
			// human readable with 1 instead of 0.
			// DEPENDS ON IF IT'S A SOLUTION DECLARATION OR
			// AN PART DECLARATION.
			// Note: The Part-class is to be renamed to Part!
			// TODO Add more pairs or replace by dynamic method, e.g.
			// populate variables dynamically: pair_member_{a,b} and
			// use them in the following code (distinction is
			// required for e.g. counting).
			if (declarationSet.declarations.get(dec_count_and_pos)
					.getMatchedPattern().isSolutionPattern()) {
				// => Solution.
				ex_filelink
					= getFilelinkForPartFromPosWithoutEnding(
							++solution_count_and_pos + 1
							, dec_count_and_pos + 1)
					+ /*"-Lsg" +*/ "." + this.getFileEnding() + ".txt";
			}
			else {
				// => Part declaration:
				ex_filelink
					= getFilelinkForPartFromPosWithoutEnding(
							++ex_count_and_pos + 1
							, dec_count_and_pos + 1)
					+ "." + this.getFileEnding() + ".txt";
			}
			//TODOString[] partRawPlusExtraNextLine
			//	= new String[partRaw.length + 1];
			//System.arraycopy(partRaw, 0
			//		, partRawPlusExtraNextLine
			//		, 0, partRaw.length);
			Part loopPart = new Part(
					ex_filelink
					, declarationSet.declarations.get(dec_count_and_pos)
					, partText
					//, partRaw
					, header_of_its_sheet
			);
			allPartsPlainText.put(ex_filelink, loopPart);

			// Write to filesystem.
			//ReadWrite.write(partText, ex_filelink);

		}


		// Last declaration till the end (not end of file!, see below)
		int lineToStopBefore = this.getPlainText().length;
		/*
		This did not work reliable enough as double sheets are
		possible and double declarations are filtered! So stop earlier:
		*/
		Declaration lastDec
			= declarationSet.declarations
			.get(declarationSet.declarations.size() - 1);

		// Find earlier line to stop at/before:
		System.out.println("[Extract plain text last part]"
				+ "\r\nTrying to find an earlier line to stop at"
				+ " (more precisely:before)"
				//+ " than the whole end of the text. The reason"
				//+ " is that content might be double and then all"
				//+ " this double content would be included in the"
				//+ " last part."
		);
		// + 1 because we must not start on this line as this matches
		// all the time as it's the last part's dec's line:
		int line_relative = -1 + 1;
		// Only overridden if all text is in 1 line (unlikely here):
		int woerter_to_analyse_maximum = 4;
		while (++line_relative
				< lineToStopBefore - lastDec.getLineNumber()) {

			int line = line_relative + lastDec.getLineNumber();

			// EITHER GIVE COMPLETE ARRAY OF TEXT AND GET ALL
			// DECLARATIONS BACK:
			//if (new RegExFinder().sucheMuster(
			//				this.getPlainText()[line]
			//				, Muster.getMusterThatMatches())) {

			// OR A CUSTOM REDUNDANT REPRODUCTION OF THE TEXT:
			String[] woerter = this.getPlainText()[line].split("\\s+");
			//if (woerter.length > 0) {
			// Choose the minimum out of both:
			if (woerter.length < woerter_to_analyse_maximum) {
				woerter_to_analyse_maximum = woerter.length;
			}

			int word_index = 0;
			for (; word_index < woerter_to_analyse_maximum
					; word_index++) {

				String wort = woerter[word_index];
				String message = "das " + word_index
					+ ". Wort lautet: " + wort;
				if (woerter.length > word_index + 1) {
					message += " \t nachfolgend: "
						+ woerter[word_index + 1];
				}
				System.out.println(message);
				boolean matches =
//						Muster.getMusterByName(Global
//						.extractSplitterFromFilelink(part_filelink))
						lastDec
						.getMatchedPattern()
						.getPattern().matcher(wort).matches();
				// Contains this line a suitable declaration?
				if (matches) {
					// => stop before this line (as from now on the
					// content doubles for sure).
					lineToStopBefore = line;// Refine the line to stop
					// before (so stop earlier than end of file|text).
					break;
				}

			}
		}

		int part_row_count_according_declarations
			= lineToStopBefore - lastDec.getLineNumber();
		String[] partText
			= new String[part_row_count_according_declarations];
		//String[] partRaw
		//	= new String[part_count_according_declarations];
		//String[] partRawPlusExtraNextLine
		//	= new String[partRaw.length + 1];
		//TODO probably overflow here:System.arraycopy(
		//			partRaw, 0
		//			, partRawPlusExtraNextLine, 0
		//			, partRaw.length);
		for (int j = 0; j < partText.length; j++) {
			int nextLine = j + lastDec.getLineNumber();
			partText[j] = this.getPlainText()[nextLine];
			//partRaw[j] = this.getRawContent()[nextLine];
			/*
			ATTENTION: The first (or last) part-raw-content-lines
			have to overlap because there is potential content of the
			previous|next part in the line of the declaration's.
			*/
		}
		// Create a new part object:
		// Increment by 1 because we start with 1 instead of 0:
		if (lastDec.getMatchedPattern().isSolutionPattern()) {
			// => Solution.
			ex_filelink = getFilelinkForPartFromPosWithoutEnding(
					++solution_count_and_pos + 1
					, dec_count_and_pos + 1)
				+ /*"-Lsg" +*/ "." + this.getFileEnding() + ".txt";
		}
		else {
			// => Part declaration:
			ex_filelink = getFilelinkForPartFromPosWithoutEnding(
					++ex_count_and_pos + 1, dec_count_and_pos + 1)
				+ "." + this.getFileEnding() + ".txt";
		}
		Part loopPart = new Part(
				ex_filelink
				, lastDec
				, partText
				//, partRaw
				// Not working because the file not yet written to disk
				//, PartDB.extractRawContentDependingOnFiletype(
				//		filelink)
				, header_of_its_sheet
		);
		this.lastPartPlainText = loopPart;
		allPartsPlainText.put(ex_filelink, loopPart);

		// Write to filesystem.
		//ReadWrite.write(partText, ex_filelink);

		/*
		Sort alphabetically as it's needed for
		determiningSuccedingPart since solutions are supported
		(v31.13c). Alphabetically because Aufgabe comes before
		Loesung and Part before Solution. TODO This has to be
		differentiated between languages where this order is not valid.
		*/

	}



	/**
	 * Empties the contents of all entries of an array as specified
	 * by start..end.
	 *
	 * @param a array of text lines
	 * @param start index
	 * @param end index to empty till, inclusive
	 * @return String[] with all lines between begin, ending deleted.
	 */
	public static String[] loescheZeilen(String[] a, int start, int end
			) {

		for (int i = start; i < end + 1; i++ ) {
			a[i] = "";
		}
		System.out.println("Removed lines " + start + ".." + end);
		return a;
	}



	/**
	 * SET PARTS MAP
	 *
	 * @param partsMapListToSet
	 */
	public void setPartsMap(Map<String, Part> partsMapToSet
			) {

		// Clearing old part list:
		this.allPartsRawFormat = partsMapToSet;

	}



	public void setPartsPlainTextMap(Map<String
			, Part> partsMapToSet) {

		// Clearing old part list
		this.allPartsPlainText = partsMapToSet;

	}



	/**
	 * To avoid a false part count, plain text and raw format
	 * content parts are stored separately.
	 */
	public void setParts(ArrayList<Part> partsListToSet) {
		// Clearing old parts!? TODO investigate what better
		this.allPartsRawFormat = new TreeMap<String, Part>();
		for (Part p : partsListToSet) {
			this.allPartsRawFormat.put(e.filelink, p);
		}

	}



	public void setPartsPlainText(ArrayList<Part> partsListToSet) {
		//Clearing old parts!? TODO investigate what better
		this.allPartsPlainText = new TreeMap<String, Part>();
		for (Part e : partsListToSet) {
			this.allPartsPlainText.put(e.filelink, e);
		}

	}






	// ======= HELPER
//	public int getId() {
//		return id;
//	}

//	public int getOriginSheetdraftFilelink() {
//		return originsheetdraft_filelink;
//	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (!this.type.equals(type)) {
			this.type = type;
			this.filelinkHasToBeReGenerated = true;
		}
	}



	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		if (!this.course.equals(course)) {
			this.course = course;
			this.filelinkHasToBeReGenerated = true;
		}
	}



	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		if (!this.semester.equals(semester)) {
			this.semester = semester;
			this.filelinkHasToBeReGenerated = true;
		}
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



	public Map<String, Part> getAllParts() {
		return allPartsRawFormat;
	}

	public void setAllPartsRawFormat(Map<String, Part> newAllPartsRawFormat) {
		allPartsRawFormat = newAllPartsRawFormat;
	}



	public Map<String, Part> getAllPartsCommonFormat() {
		return allPartsCommonFormat;
	}

	public void setAllPartsCommonFormat(Map<String, Part> newAllPartsCommonFormat) {
		allPartsCommonFormat = newAllPartsCommonFormat;
	}



	public List<String> getAllPartFilelinksAsList() {
		List<String> allPartFilelinks = new ArrayList<String>();
		allPartFilelinks.addAll(allPartsRawFormat.keySet());
		return allPartFilelinks;
	}



	public Part getPartByFilelink(String key_filelink) {
		if (allPartsRawFormat.get(key_filelink) == null) {
			return null;
		}
		return allPartsRawFormat.get(key_filelink);
	}



	public Map<String, Part> getAllPartsPlainText() {
		return allPartsPlainText;
	}



	public String getFilelinkForPartFromPosWithoutEnding(
			int part_num_and_position, int declaration_index) {

		return getFilelinkForPartFromPosWithoutEnding(
				part_num_and_position
				, this.declarationSet.declarations
				.get(declaration_index - 1));

	}



	public String getFilelinkForPartFromPosWithoutEnding(
			int part_num_and_position, Declaration declaration) {

		// For now we keep the . experimentally. If this turns out
		// robust on all systems this is a better solution than __
		// because we then directly can use filelink.
		String to_append_to_filename = //"."/*"__"*/ +
				getFileEnding() // simply add ending instead of . --> _
				+ "__Part_" + part_num_and_position
				+ // ((declaration != null) ?
				//"__splitby_" + this
				//.getSplitterRepresentation(declaration)
				//:
				/*
				Declarations are the same for this sheet/draft's
				plain text AND raw format parts!
				*/
				//"__splitby_" + this.getSplitterRepresentation(
				//		this.declarationSet.declarations
				//		.get(declaration_index - 1))
				"__splitby_"
				+ this.getSplitterRepresentation(declaration)
				//)
				;
		return getFilelinkWithoutEnding() + to_append_to_filename;
	}



	/**
	 * If the splitterDeclaration is a custom one specified via the
	 * filelink __splitby_[splitPattern].
	 * Then we have no equivalent Muster category name like INTDOT or
	 * INTBRACKET for it. That is no problem as regex specific
	 * potentially dangerous characters like \\d for number are not
	 * allowed in paths of filesystems anyway. So those signs should
	 * never occur for the optional customized splitby pattern hint.
	 * => This can be written to the filepath directly.
	 *
	 * The other, preconfigured patterns are more problematic. Here we
	 * use the category name like INTDOT, write this to the filepath.
	 *
	 * @param splitterDeclaration
	 * @return The best bet for a hopefully filepath compliant
	 * splitter pattern or representation (e.g. INTDOT,INTBRACKET,...).
	 */
	public String getSplitterRepresentation(
			Declaration splitterDeclaration) {

		String splitter = null;

		if (splitterDeclaration != null) {
			if (splitterDeclaration.getMatchedPattern() != null) {
				if (splitterDeclaration.getFirstWord().equals("")
						//TODO Eventually store as attribute.
						&& splitterDeclaration.getLineNumber() < 1) {
					// It's a custom splitter to a very high
					// percentage (not 100% because the line was
					// chosen as 0 for this case and theoretically
					// it's still possible to have a successful splitby
					// Declaration in the first line of document
					// and still have no further FirstWord.
					splitter = splitterDeclaration
						.getMatchedPattern().toString();
					return splitter;
				}
				else {
					// It's a preconfigured splitby pattern.
					//splitter = Muster.getValueByPattern(
					//		splitterDeclaration.getMatchedPattern());
					splitter = splitterDeclaration
						.getMatchedPattern().name();
					// Hopefully INTDOT, INTBRACKET, ...
					return splitter;
				}
			}
		}

		// Neither a pattern in the given splitterDeclaration nor the
		// declaration or it was empty.
		splitter = Global.extractSplitterFromFilelink(filelink);
		// Take the one of the sheetdraft.
		// This could and should be equal to the variant when the
		// splitterDeclaration.pattern is an optional customized one.
		return splitter;

	}



	/**
	 *
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public Sheetdraft
		loadAssignedAndReferencedSingleSourcePartsFromDatabase()
		throws IOException, SQLException {

		String query = "SELECT *"
				+ " FROM draftpartassignment, part"
				+ " WHERE sheetdraft_filelink = "/*id*/ + getFilelink()
				+ " AND part_filelink = part.filelink";

		ResultSet res = Global.query(query);
		// Instantiate, add all parts:
		while (res.next()) {
			allPartsRawFormat.put(
					res.getString("part_filelink"),
					new Part(
						//origin_sheetdraft_filelink this part belongs to
						res.getString("filelink")
						, new Declaration(Global
							.determinePatternBestFittingToSplitterHint(
								res.getString("splitby")), "", 0)
						//, origin_sheetdraft_filelink
						//, res.getString("content")//in the filesystem
						, res.getString("header")
						//, res.getInt("is_native_format") //on the fly
						, res.getLong("whencreated")
						, res.getLong("whenchanged")
					)
			);
		}

		// Tackle memory leaks by closing result set, its statement:
		Global.queryTidyUp(res);

		return this; // for chaining

	}



	/**
	 * WRITE TO DISK
	 * Writes the content of this sheetdraft to the filesystem/disk.
	 * For now it only creates two files for each part:
	 * raw content and plain text.
	 * The reason for two and not only one raw content and on the fly
	 * extraction of plain text is that there are images to be
	 * generated from the txt-Files too.
	 *
	 * @throws IOException
	 */
	public void writeSheetdraftContentToHarddisk() throws IOException {
		String ext = Global.extractEnding(filelink);

		// RAW CONTENT: is directly created by either the upload
		// or the file conversion library.
		// => SO THIS IS NOT NECESSARY AT ALL. It could be necessary
		// for part contents though.

		/*
		Ends on '__ext.ext' because then we know its original filetype!
		NO LONGER! SEE BELOW!
		*/
		//ReadWrite.writeText(getRawContent()
		//		, getFilelinkWithoutEnding() + "__" + ext + "." + ext);
		// TODO Add more writing to disk code depending on
		// filetype/ending. Unless it is the native filetype as then
		// the file exists on the disk already.
		if (filelink.endsWith("docx")) {
			if (filelink.endsWith(".docx.docx")) {
				return ;
			}
			// If it is the original filetype then it would be load,
			// change and save.
			// Because here is nothing to change, the original filetype
			// document has not to be saved.
			//ReadWrite.write("", filelink);
			//1) copy
			//2) new DocxWordprocessingML(filelink_of_copy);
			//3) delete unnecessary parts.

		}
		else if (filelink.endsWith("tex")) {
			if (filelink.endsWith(".tex.tex")) {
				return ;
			}
			// TODO

		}
		else if (filelink.endsWith("txt")) {
			if (filelink.endsWith(".txt.txt")) {
				return ;
			}
			// Has not to be written as txt is also raw at same time.
		}
		else if (filelink.endsWith("php")) {
			if (filelink.endsWith(".php.php")) {
				return ;
			}
			// TODO

		}
		else if (filelink.endsWith("html") || filelink.endsWith("htm")) {
			if (filelink.endsWith(".htm.htm")
					|| filelink.endsWith(".html.html")) {
				return ;
			}
			// TODO

		}


		// PLAIN TEXT:
		// Create this because we also need to generate images for
		// plain text. Do this by replacing the last of the double
		// ending with .txt using 'regular expressions':
		ReadWrite.writeText(getPlainText(), getFilelink()
				.replaceAll("[.]" + ext + "$", ".txt"));
	}



	/**
	 * WRITE PARTS TO DISK
	 * Writes the parts of this sheetdraft to the filesystem
	 * |harddisk. For now it only creates two files for each part:
	 * raw content and plain text.
	 * The reason for two and not only one raw content and on the fly
	 * extraction of plain text is that there are images to be
	 * generated from the txt-Files, too.
	 * Even more important is the use of the plain text for finding
	 * declarations. This way the line numbers of plainText and
	 * rawContent are always the equivalent.
	 * This makes part extraction for rawContent way easier:
	 * begin &amp; end are given by the plain text version
	 * (where highest score declarations have been found).
	 * TODO Propagation to the next enclosing tag ends is missing.
	 *
	 * @throws IOException
	 * @state completely rewritten
	 */
	public void writePartsToHarddisk(
			Map<String, Part> partMap) throws IOException {

		//int allPartsL = allParts.keySet().size();
		//Part[] allParts_array
		//	= (Part[])(allParts.values().toArray());
		int pos = 0;
		for (Part part : partMap.values()) {

//			String dirs_reversed
//				= new StringBuffer(Global.extractPathTo(filelink))
//				.reverse().toString();
//			int last_slash = dirs_reversed
//				.indexOf(System.getProperty("file.separator"));
//			String filename_t_reversed
//				= dirs_reversed.substring(0,last_slash);
//			String filename_t = new StringBuffer(filename_t_reversed)
//				.reverse().toString();
//			int punkt = filename_t_reversed.indexOf('.');
//			String filename_t_g_reversed = filename_t_reversed
//				.substring(punkt + 1,filename_t.length());
//			String filename_t_g
//				= new StringBuffer(filename_t_g_reversed)
//				.reverse().toString();

			/*
			We use the original filelink! Why changing it?
			Why not only add an part suffix?
			*/
			String ext = Global.extractEnding(filelink);
			// No longer use a subdirectory for practical reasons.
//			String dir = root + target + filename_t_g + "__" + ext;
//			File f = new File(dir);
//			if (!f.exists()) {
//				f.mkdir(); // create folder with name, format of file
//			}

			// For building the part filelink string.
			// Positioned before assign as parts start at 1.
			pos++;
			// Already generated before
			String part_filelink_generated_for_pos = part.filelink;
			//= getFilelinkForPartFromPosWithoutEnding(pos);


			//ReadWrite.writeText(allParts_array[pos]
			//	.getRawContent()
			//	, part_filelink_generated_for_pos + "." + ext);
			// The above is no longer adequate, because we not only
			// deal with one-file fileformats, but with archived
			// XML too. Therefore we create copies in the filesystem
			// and delete unnecessary parts. Of course we then
			// ALREADY HAVE the FILESYSTEM REPRESENTATION.

			// Create a plain text representation because we also
			// need to generate images for plain text.
			//ReadWrite.writeText(allParts_array[pos]
			//	.getPlainText()
			//	, part_filelink_generated_for_pos  + ".txt");
			ReadWrite.writeText(part.getPlainText()
					, part_filelink_generated_for_pos/* + ".txt"*/);

		}

	}








}
