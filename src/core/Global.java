package core;


import command.Command;

import converter.Converter;
import converter.DocConverter;
import converter.PDFToImage;
import converter.PDFConverter;
import converter.RTFConverter;
import converter.TextConverter;

import db.MysqlHelper;
import db.SQL_Methods;
import db.ScriptRunner;
import db.UnixComandosThread;

import docx4j_library.ConvertOutHtml;
import docx4j_library.ConvertOutPDF;
import docx4j_library.MergeUtil;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.document.DocumentKind;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.net.URLEncoder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

//import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.http.HttpRequest;
import org.apache.lucene.index.IndexReader;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import org.docx4j.Docx4J;
import org.docx4j.TextUtils;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.model.datastorage.XPathEnhancerParser.orExpr_return;
import org.docx4j.openpackaging.io3.stores.ZipPartStore;
import org.docx4j.openpackaging.io3.stores.ZipPartStore.ByteArray;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.Parts;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart.AddPartBehaviour;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.Body;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.fit.cssbox.demo.ImageRenderer;
import org.jvnet.jaxb2_commons.ppp.Child;
import org.odftoolkit.simple.TextDocument;
import org.w3c.dom.NodeList;

import org.odftoolkit.odfdom.converter.core.IURIResolver;
import org.odftoolkit.odfdom.converter.core.FileURIResolver;
import org.odftoolkit.odfdom.converter.xhtml.XHTMLOptions;
import org.odftoolkit.odfdom.converter.xhtml.XHTMLConverter;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import sun.rmi.log.ReliableLog;




/**
 * Contains global variables and functions which are useful for many
 * files. First it was tried to include Globals for all JSP files
 * i.e. within the <!% %> or <% %>.
 *
 * @author Jan R. Balzer-Wein
 */
public class Global {


	// ======= ATTRIBUTES
	// ------- Declare
	public static String version = "v31.15";

	/**
	 * TODO PARTS -> SHEET ?
	 * TODO Model pairs|tuples by providing an extra parameter.
	 */
	public static enum SheetTypes {
		PARTS/*_SHEET*/,
		PARTS_SOLUTION,
		EXAM,
		EXAM_SOLUTION
	};

	//public static enum SUPPORTED_FILETYPES {
	//	DOCX, DOC, /*HTML,*/ ODT, PDF, RTF, TEX, TXT
	//};



	// ------- Conversion parameters:

	public static boolean forceAllCommandsIntoOwnThread = false;

	/**
	 * Used from all converters to determine which format to convert to.
	 * TODO: pdfbox does not seem to support png.
	 * TODO: Rework everything to JPG. Note imageMagick is tuned for PNG.
	 */
	public static String imageTypeToGenerate = "png";

	public static String lineSeparatorPattern = "\\r?\\n";

	/**
	 * Used for image generation out of native, plain text.
	 */
	public static final int charactersPerLine = 80;

	public static int ImageMagick_qualityScale = 1;

	public static int ImageMagick_density = 150;//144

	public static String[] indexedFields = {
		"content", "type", "course", "semester"
		, "lecturer", "author", "filelink"
	};

	public static String[] semterm = { "SS", "WS" };

	public static boolean debug = true;

	/**
	 * TODO deactivate if image or formula encountered,
	 * because plain text line count not takes it into account.
	 */
	public static boolean cropImages = false;

	public static String anzeige = "start";

	public static String id = "start"; //former aktion replacement

	public static String message = "";

	/**
	 * out of e.g. default, success, failure
	 */
	public static String messageClass = "neutral";

	public static String root; //root - to be written once at startup

	public static String uploadTarget = "uploads"
		+  System.getProperty("file.separator");

	public static HttpSession session;

	public static IndexReader indexReader;

	public static Calendar now = new GregorianCalendar();



	// ------- MySQL attributes
	/**
	 * DBConnect seems to fail when it is e.g. called DB.
	 */
	public static MysqlHelper msqh = new MysqlHelper();

	public static Connection conn;

	public static SQL_Methods sqlm = new SQL_Methods();


	// Response -- TODO is this individual for each jsp-File?
	//public static HttpServletResponse request;
	public static HttpServletResponse response;



	// ------- SETTINGS DEFAULTS
	// Not final as it can be changed by users.
	public static Language LANGUAGE = Language.getByName("German");






	// ======= FUNCTIONS

	public static String toFairy(String english) {
		return display(english);
	}



	public static String display(String english) {
		String translated = translate(english);
		if (translated == null) {
			System.out.println(
					Global.addMessage("Found no translation for "
						+ english + " .", "danger")
			);
			// Prevents the server of a null pointer exception
			// and prevents showing emtpy fields.
			return english.trim().substring(0, 1).toUpperCase()
				+ english.trim().substring(1).replace("_", " ");
		}
		// If it's not english and a translation was found then it
		// could contain an encoded Umlaut.
		translated = Global.decodeUmlauts(translated);
		return translated.trim().substring(0, 1).toUpperCase()
			+ translated.trim().substring(1).replace("_", " ");
	}



	public static String translate(String english) {
		String translation = Global.LANGUAGE.getTranslation(english);
		if (translatation == null)
			return null;
		return " " + translation + " ";
		//return Language.translations[Global.LANGUAGE.ordinal()]
		//	.get(english);
	}



	public static boolean isLoggedIn(HttpSession session) {
		return session.getAttribute("user") != null;
	}



	public static String getUser(HttpSession session) {
		return session.getAttribute("user").toString();
	}



	public static String getUserURLEncoded(HttpSession session)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(session.getAttribute("user")
				.toString(), "utf-8");
	}



	/*
	menueItem, page to be re-defined/overwritten/adapted somewhere
	previously (mostly immediately before include of this scriptlet)
	*/
	// Protected seems to be the default visibility
	public static String getSupportedFiletypesAsString() {
		int i = 0;
		String docTypesSupported = "";
		for (DocType docType : DocType.values()) {
			if (i > 0) {
				docTypesSupported += ", ";
			}
			docTypesSupported += docType.name();
			//.toString();<-- will have .<ending>
			i++;
		}
		return docTypesSupported;
	}



	// Index reader (especially for multiple interrupts/threads to
	// have it volatile is important).
	public static void setIndexReader(IndexReader indexReader) {
		Global.indexReader = indexReader;
	}



	public static IndexReader getIndexReader() {
		return Global.indexReader;
	}



	/**
	 *
	 * @param part
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void QUERY_INSERT_INTO_part(Part part)
			throws IOException, SQLException {
		QUERY_INSERT_INTO_part(
				part.getFilelinkRelative()
				, part.getOriginSheetdraftFilelink()
				, part.getSplitBy()
				, part.isNativeFormat()
		);
	}



	public static void QUERY_INSERT_INTO_part(
			String part_filelink) {
		QUERY_INSERT_INTO_part(part_filelink, null, null, null);
	}



	public static void QUERY_INSERT_INTO_part(String part_filelink
			, String originsheetdraftFilelink
			, String splitby, Boolean is_native_format) {

		try {
			if (Global.sqlm.exist("part", "filelink"
						, " filelink = '"
						+ Global.removeRootPath(part_filelink)
						+ "'")) {
				System.out.println("Part already exists in the system: "
						+ part_filelink
						+ "\r\nNot going to be inserted twice. Skipping ...");
				return ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String query; query = getQUERY_INSERT_INTO_part(
				part_filelink
				, originsheetdraftFilelink, splitby, is_native_format);
		//Global.addMessage("query: " + query, "info");
		// Execute
		Global.sqlm.executeUpdate(query);

	}



	/**
	 *
	 * @param part
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static String getQUERY_INSERT_INTO_part(
			Part part)
			throws IOException, SQLException {

		return getQUERY_INSERT_INTO_part(
				part.getFilelinkRelative()
				/* null as originsheetdraft implies using the
				sheetdraft filelink for that too. */
				, null/* Never use:
				part.getOriginSheetdraftFilelink() as this
				queries the db, but we don't have a entry in there.
				*/
				, part.getSplitBy()
				, part.isNativeFormat()
		);

	}



	public static String getQUERY_INSERT_INTO_part(String part_filelink) {
		return getQUERY_INSERT_INTO_part(part_filelink, null
				, null, null);
	}



	public static String getQUERY_INSERT_INTO_part(String part_filelink
			, String originsheetdraftFilelink
			, String splitby, Boolean is_native_format) {

		// Convert the absolute part to the relative one.
		String part_filelink_relative = Global
			.extractRelativePartOfFilelinkAtEndOnly(part_filelink);
		System.out.print(
				//Global.addMessage(
					"Generated relative filelink: "
					+ part_filelink_relative
					//, "info"
				//)
		);
		String part_sheetdraft_filelink_relative
			= Global.extractSheetdraftFilelinkFromPartFilelink(
				part_filelink_relative);
		if (originsheetdraftFilelink == null) {
			originsheetdraftFilelink = part_sheetdraft_filelink_relative;
		}
		if (splitby == null) {
			splitby = Global.extractSplitByFromFilelink(part_filelink_relative);
		}
		if (is_native_format == null) {
			is_native_format = Global.isFilelinkNativeFormat(part_filelink_relative);
		}


//		if (!multipleAtOnce) {
		String query; query = "INSERT INTO part ("
			+ "sheetdraft_filelink"
			+ ", `filelink`"
			+ ", `originsheetdraft_filelink`"
			+ ", `splitby`"
			+ ", is_native_format"
			//+ ", whenchanged"
			+ ", whencreated"
			+ ")"
			+ "VALUES ("
		+ "'" + part_sheetdraft_filelink_relative + "'"
		+ ",'" + part_filelink_relative + "'"
		+ ",'" + part_sheetdraft_filelink_relative + "'"// origin
		+ ",'" + splitby + "'"
		//+ ",'" + Global.sqlm.mysql_real_escape_string(
		//	al.get(i).getPlainTextAsString())
		+ ", " + booleanToInt(is_native_format) /* <-- all directly
			uploaded sheets are initially in native format */
		/* whenchanged - never changed so far */
		+ ", UNIX_TIMESTAMP() "/* whencreated - the current db date */
			+ ")";
//		}
//		else {
//			return query + ",";
//		}
		return query;

	}



	/**
	 *
	 * @param filelink
	 * @param type
	 * @param course
	 * @param semester
	 * @param lecturer_id
	 * @param description
	 * @param author
	 * @param is_draft
	 * @param plainTextAsString
	 * @return
	 */
	public static String QUERY_INSERT_INTO_sheetdraft(String filelink
			, String type, String course, String semester
			, int lecturer_id, String description
			, Object author, int is_draft, String plainTextAsString
			) {

		if (author == null || author == "") {
			if (session.getAttribute("user") != null) {
				if (session.getAttribute("user") instanceof String) {
					author = (String) session.getAttribute("user");
				}
				else {
					System.out.println("Session user attribute not of"
							+ " type String, instead: "
							+ session.getAttribute("user").getClass());
				}
			}
		}

		return "INSERT INTO sheetdraft "
				+ "(`filelink`, `type`, `course`, `semester`"
				+ ", `lecturer_id`, `description`, `author`"
				+ ", `is_draft`, `whencreated`, `plain_text`)"
			+ "VALUES ("
				+ "'" + filelink + "'"
				+ ",'" +  type + "'"
				+ ",'" + course + "'"
				+ ",'"  + semester + "'"
				+ "," + lecturer_id + ""
				+ ",'" + description + "'"
				+ ",'" + author + "'"
				+ ", " + is_draft // <- uploaded ones usually no draft
				+ ", UNIX_TIMESTAMP()"
				+ ",'" + Global.sqlm.mysql_real_escape_string(
						Global.encodeUmlauts(plainTextAsString)) + "'"
				// Alternatively use URLEncoder.encode():
				//+"'"+URLEncoder.encode(plainTextAsString,"utf-8")+"'"
			+ ");";
	}



	/**
	 * Builds a path by inserting the file separator.
	 *
	 * @param parts Array of String, the directories|files|parts
	 * @return the path as string
	 */
	public static String buildRootPath(String[] parts) {
		String path = Global.root;
		for (String p : parts) {
			path += System.getProperty("file.separator") + p;
		}
		return path;
	}



	/**
	 * BUILD PATH TO filelink -- a convention
	 * @param semester
	 * @param course
	 * @param lecturer
	 * @param type
	 * @return the path to the file, the basename dir or better:
	 * parent directory.
	 */
	public static String buildPathTo(String semester, String course
			, String lecturer, String type) {
		return Global.encodeUmlauts(semester)
			+ System.getProperty("file.separator")
			+ Global.encodeUmlauts(course)
			+ System.getProperty("file.separator")
			+ Global.encodeUmlauts(lecturer)
			+ System.getProperty("file.separator")
			+ Global.encodeUmlauts(type)
			+ System.getProperty("file.separator");
	}








	public static boolean isFilelinkNativeFormat(String filelink) {
		// For ease of debugging store it in a local variable first.
		String last_ext_hence_current_format
			= Global.extractEnding(filelink);
		return getNativeFormatEnding(filelink)
			.equalsIgnoreCase(last_ext_hence_current_format);
	}



	public static String extractFilelinkOfMothersheet(String filelink) {
		return filelink.replaceAll(filelink
				.substring(filelink.indexOf("__Part")) + "$", "");
		// TODO ALERT HARDCODED
	}



	/**
	 * Get the stored ending .origExt out of .origExt.currentExt.
	 */
	public static String getNativeFormatEnding(String filelink) {
		String candidate;
		String[] parts = filelink.split("[.]");
		/*
		1) If an part has a double ending at the end:
		Then a .tex part results from a .originalExt$ PART.
		*/
		candidate = parts[parts.length - 2];
		// No supported filetype with more than 4 digits.
		// Safe because furthermore the filenames are much longer
		// as they obtain splitby data, part position/number, ...
		if (candidate.length() < 5) {
			return candidate;
		}

		/*
		2) If an part has no double ending at the end:
		Then a .tex part results from a SHEETDRAFT .docx|ext.TEX.
		*/
		parts = filelink.split("[.]" + parts[parts.length - 1]);
		// There may be many dots before e.g. splitby defs, numbering.
		parts = parts[0].split("[.]");
		// However as only the last is taken it's okay.
		candidate = parts[parts.length - 1];
		// This is bonus only for this case:
		// No supported filetype with more than 4 digits.
		// Safe because furthermore the filenames are much longer
		// as they obtain splitby data, part position/number, ...
		if (candidate.length() < 5) {
			return candidate;
		}


		//----------------------------------------------------------//
		// Continue searching within our valid file extensions list
		// if nothing was found so far.
		//----------------------------------------------------------//

		// Compare the stored ending __ext to the .ext at the end
		// of filelink.
		String value = null;

		// Extract them:
		int __ext_in_filelink_index = -1;
		int filetypes_index = -1;
		int filetypesL = DocType.values().length;

		// First iterate over all prefixes because the last
		// option "_" has to be tested as final emergency solution.
		// "_" is vulnerable for errors!
		String[] prefixes = {"__", "-", "_"};
		String prefix_successful = null;
		for (String prefix : prefixes) {

			// Give this prefix a try with all endings:
			while (__ext_in_filelink_index == -1
					&& ++filetypes_index < filetypesL) {

				// Give it a try, find this ending:
				__ext_in_filelink_index = filelink.indexOf(prefix
					+ DocType.values()[filetypes_index].toString());
			}


			if (__ext_in_filelink_index > 0) { /* != -1 */
				prefix_successful = prefix;
				break;
			}

		}

		// success?
		int index_start = __ext_in_filelink_index;
		if (index_start == -1) {
			// No original ending could be found
			Global.addMessage("No original fileending could be found."
					+ " Assuming it"
					+ " is in the native file format.", "warning");

			// Cancel further analysis
			//return true;
			return Global.extractEnding(filelink);// This will result in null.
		}


		// PART 3
		// Compare found original extension against the fileending.
		String substring_beginning = prefix_successful;

		/*
		At this point we have the start index of the stored original
		ending.
		*/
		//String[] parts = filelink.split("__");
		int index = index_start;
		// Beginning, termination of the substring
		// where __varname_value__ will be:
		int substring_beginning_index = 0;
		int substring_beginning_L = substring_beginning.length();

		String termination_buffer = "";
		boolean terminatingUnderscoreOccurredOnce = false;
		while (index < filelink.length()) {
			char ch = filelink.charAt(index);
	//		if (Character.isLetter(ch)) {
			// Because this way no index out of bounds error will show:
			if (substring_beginning_index >= substring_beginning_L
					|| ch != substring_beginning
					.charAt(substring_beginning_index)) {
				// Reached the end?
				if (ch == '_' || ch == '.' || ch == '-') {
					if (terminatingUnderscoreOccurredOnce
							|| ch == '.'
							|| ch == '-') {
						// The 2nd time in series an underscore =>STOP
						// Terminate this while loop, we have reached
						// the end of the splitter:
						break;
					}
					termination_buffer += "_";
					terminatingUnderscoreOccurredOnce = true;
				}
				else {
					// Last was a single _ and not __, so the ending
					// buffer stands for a space, so we have to add
					// it to the splitter/split_by.
					value += termination_buffer;
					// Clear the ending buffer as it has been an
					// ordinary space:
					termination_buffer = "";
					// Finally add the current char. Easy to forget.
					value += ch;
				}
	//			}
			}
			index++;
			substring_beginning_index++;

		}
		// if those are equal then it is in native_format:
		//return (value == last_ext_hence_current_format);
		return value;
	}



	/**
	 * The map for closing result sets and its statements properly.
	 * Use queryTidyUp function to close both and remove the
	 * statement result set pair from the map too.
	 */
	public static Map<ResultSet, Statement> mapResultSetToStatement
		= new HashMap<ResultSet, Statement>();

	/**
	 * Tackle memory leaks, especially the server went out of
	 * PermSpace very often.
	 * This effort goes into ensuring to close both result sets
	 * and statements.
	 *
	 * @param res The Result set. Used to find the statement that
	 * created it, as this is stored in a map with the result set
	 * object as key.
	 */
	public static void queryTidyUp(ResultSet res) {
		if (res == null) {
			System.out.println("Could not tidy up (close) result set,"
					+ " statement as the result set given is null.");
			return ;
		}
		try {
			if (Global.mapResultSetToStatement.get(res) != null) {
				Global.mapResultSetToStatement.get(res).close();
				Global.mapResultSetToStatement.remove(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	/**
	 * query
	 *
	 * @param query			- the query to execute
	 * @param request	   - additional debug context | null
	 * @param pageContext   - additional debug context | null
	 * @return The result (set) of the database query.
	 * @throws IOException
	 */
	public static ResultSet query(String query) throws IOException {

		return query(query, null, null);

	}



	public static ResultSet query(String query
			, String information_where_this_method_was_called)
		throws IOException {

		return query(query, null, null
				, information_where_this_method_was_called);

	}



	public static ResultSet query(String query
			, HttpServletRequest request, PageContext pageContext)
		throws IOException {

		return query(query, request, pageContext
				, "no_callee_information_given");

	}



	public static ResultSet query(String query
			, HttpServletRequest request, PageContext pageContext
			, String information_where_this_method_was_called)
		throws IOException {

		Statement statement = null;
		ResultSet res = null;
		try {
//			String str_query = "SELECT *, MATCH (content) AGAINST ('\""
//				+ search_string + "\"') AS score"
//				+ " FROM sheetdraft WHERE MATCH (content) AGAINST ('\""
//				+ search_string + "\"' in boolean mode);";
//			// Without BOOLEAN MODE: {results | score < .5} are omitted
			if (conn == null) {
				addMessage("Connection in query is null =>creating new"
						, "warning", request, pageContext);
				conn = msqh.establishConnection(null);
			}
			;
			statement = conn.createStatement();
			if (statement == null) {
				addMessage("Statement could not be created. Is the"
						+ " mysql service started? Probably it has"
						+ " to be reinstalled if not."
						+ " Save mysql-databases before!", "nosuccess");
				return null;
			}
			// Execute
			boolean updateInsertDeleteHappened = false;
			// DELETE INSERT UPDATE?
			boolean insert = false;
			boolean update = false;
			if (query.contains("DELETE ")
					|| (insert = query.contains("INSERT "))
					|| (update = query.contains("UPDATE "))) {
				String operation = "Deleted ";
				if (insert) {
					operation = "Inserted ";
				}
				else if (update) {
					operation = "Updated ";
				}
				updateInsertDeleteHappened = true;
				// Execute
				int rowCountOrNothingToReturn
					= statement.executeUpdate(query);
				if (rowCountOrNothingToReturn == 0) {
					addMessage("No result to show for query:\r\n<br/>"
							+ query,"nosuccess", request, pageContext);
//					System.out.print(message);
				}
				else {
					System.out.print(
							addMessage(operation
								+ rowCountOrNothingToReturn
								+ " row|s.\r\n<br/>"
								+ query,"success"
								, request, pageContext)
					);
				}

			}
			// SELECT
			else {
				// Execute
				res = statement.executeQuery(query);
				if (res == null) {
					addMessage("Result set is null.\r\n"
							,"nosuccess", request, pageContext);
//					System.out.print(message);
				}
				/*
				else if (!res.next()) {
					System.out.print(
						addMessage("No entries were found.\r\n<br />"
							+ "Query: "
							+ query + " #Global.java__LINE__243#"
							+ information_where_this_method_was_called
							, "nosuccess", request, pageContext)
					);
				}
				//SQLite issue:res.beforeFirst();//As above incs by 1!
				*/
			}


			// Finally
			//statement.close(); This would render the ResultSet useless.
			if (res != null) {
				mapResultSetToStatement.put(res, statement);
			}
			else {
				// Result is null, tidy up immediately:
				statement.close();
			}
			return res;

		} catch (SQLException e) {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (res != null) {
				try {
					res.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}

		// This is the error prone case. => Check if null at caller!
		return null;

	}



	/**
	 * If menue item is active return its classes + active classes.
	 *
	 * @param session
	 * @param menueItem
	 * @return
	 */
	public static String getIfActive(HttpSession session
			, String menueItem) {
		String page_ = "";
		if (session.getAttribute("anzeige") != null) {
			page_ = session.getAttribute("anzeige").toString();
		}
		if ( page_.equals(menueItem) || page_ == menueItem + ".jsp"
			|| (page_ == ""
				&& (menueItem == "start"
					|| menueItem == "start.jsp")) ) {
			return " btn-info active ";
			//out.print(" active");
		}
		return "";
	}



	public static String getIfRequested(HttpServletRequest request
			, String key, String should_value
			, String whatToGet) {
		return getIfEqual(request.getParameter(key)
				, should_value, whatToGet);
	}



	public static String getIfEqualSelectedAttribute(String value
			, String should_value) {
		return getIfEqual(value, should_value, " selected=\"selected\" ");
	}



	public static String getIfEqual(String value
			, String should_value, String whatToGet) {
		return value != null ?
			(value.equals(should_value) ?  whatToGet : "") : "";
	}



	public static String relativeToAbsolutePath(String path
			, PageContext pageContext) {
		System.out.println("Path: " + path);
		String realPath = pageContext.getServletContext()
			.getRealPath(path);
		System.out.println("Real path: " + realPath);
		return realPath;
	}



	public static boolean fileExists(String filepath
			, PageContext pageContext) {
		return new File(Global.relativeToAbsolutePath(
					filepath, pageContext)).exists();
	}



	/**
	 * filemtime (PHP equivalent) - used in the page footer.
	 *
	 * @param session
	 * @param filename
	 * @return
	 */
	//
	public static String filemtime(HttpSession session
			, String filename
			, PageContext pageContext) {

		return Global.filemtime(session, filename
				, System.getProperty("file.separator")
				, "d.M.y, H:mm z"
				, pageContext);//webapps/core/

	}



	/**
	 * filemtime
	 *
	 * @param session
	 * @param filename
	 * @param pathTo
	 * @param dateFormat
	 * @return String:Date formatted according to dateFormat or empty
	 */
	public static String filemtime(HttpSession session
			, String filename
			, String pathTo, String dateFormat
			, PageContext pageContext) {

		// Path preparation
		String path = pathTo + System.getProperty("file.separator")
			+ filename;
		String realPath = relativeToAbsolutePath(path, pageContext);

		// The file is checked for existence later:
		File file = new File(realPath);// already throwing an exception

		// Determine type of theoretical file
		String fileType = "File";
		if (file.isDirectory()) {
			fileType = "Directory";
		}
		// File exists, i.e. found?
		if (file.exists()) {
			return Global.filemtime(file, dateFormat);
		}
		else {
			String loggedInAddition = "";
			String loggedInPath = "";
			if (Global.isLoggedIn(session)) {
				// After the login,include start.in.jsp for start.jsp;
				// Id-Attribute is set in login.jsp (Session).
				//loggedInAddition = ".in";
				loggedInPath = "";//"in/";
			}
			// Add error to request for auto display in the index.jsp:
			session.setAttribute("errors", fileType
					+ " '" + filename + "' not found!");
			Global.addMessage("An error occurred: " + fileType
					+ " '" + filename + "' not found! "
					+ loggedInPath + filename + loggedInAddition
					, "nosuccess");

			return "";
		}

	}



	/**
	 * since v31.13c, used for download page.
	 *
	 * @param file to check
	 * @return Formatted date of last modification of file.
	 */
	public static String filemtime(File file, String dateFormat) {
		if (!file.exists()) {
			return null;
		}
		long timeInMs = file.lastModified();
		java.util.Date date = new java.util.Date(timeInMs);
		Format formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(date);
	}



	/**
	 * Attention: This function removes whole directories
	 * and all the files contained!! Use with precaution!
	 *
	 * @param dir directory to be deleted.
	 * @throws IOException
	 */
	public static void deleteDirRecursively(File dir)
		throws IOException {
		if (dir.exists() && dir.isDirectory()) {
			// delete:
			File[] filesOrDirs = dir.listFiles();
			for (File fOrD : filesOrDirs) {
				if (fOrD.isFile()) {
					FileDeleteStrategy.FORCE.delete(fOrD);
				}
				else {
					deleteDirRecursively(fOrD);
				}
			}
		}
	}



	/**
	 * Runs a sql script. Useful for e.g. loading MySQL exported
	 * tables into SQLite.
	 * Note: SQLite only knows TEXT, CHAR(), REAL, INT ... REAL=double
	 * and this is no bad thing. Types can even be mixed.
	 *
	 * @param sql_filelink
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void runScript(String sql_filelink)
		throws FileNotFoundException, IOException, SQLException {
		runScript(sql_filelink, Global.conn);
	}



	public static void runScript(String sql_filelink, Connection conn)
		throws FileNotFoundException, IOException, SQLException {

		ScriptRunner runner = new ScriptRunner(conn, false, false);
		//<--[booleanAutoCommit], [booleanStopOnerror]
		runner.runScript(new BufferedReader(
					new FileReader(sql_filelink)));

	}






	//======= HELPER
	public static String getMessage() {
		return Global.getMessage(true);
	}

	public static String getMessage(boolean reset) {
		String message = Global.message;
		if (reset) {
			Global.message = "";
			Global.message += "<div class='info'>Message cleared.</div>";
		}
		return message;
	}



	/**
	 * Adds a message to the global stack to be displayed in the UI.
	 *
	 * @param message The message to show in the interface.
	 * @param messageClass The kind of message: success,nosuccess,info
	 * @param request Additional context for debug purposes or null.
	 * @param pageContext -"-
	 * @return The message again for chaining or cascading.
	 */
	public static String addMessage(String message
			, String messageClass) {
		return addMessage(message, messageClass, null, null);
	}



	/**
	 * @param message
	 * @param messageClass
	 */
	public static String addMessage(String message
			, String messageClass, HttpServletRequest request
			, PageContext pageContext) {

		Global.message += "<div class='"+ messageClass +"'>";
		Global.message += message;
		// Extended debug mode?
		if (Global.debug) {
			// Is enough context given for extended message generation?
			if (request != null || pageContext != null) {
				StackTraceElement thror = new Throwable().getStackTrace()[1];
				Global.message += "<span>[in file: "
					+ request.getRequestURI() +", compiled as: "
					+ pageContext.getPage().getClass().getName()
					+ ", requested by/coming from: "
					+ request.getRequestURI() + " #line"
					+ thror.getLineNumber() +"]</span>";
				Global.message += "<span>Trace: file "
					+ thror.getFileName()
					+ " - class " + thror.getClassName()
					+ " - method " + thror.getMethodName()
					+ " - line " + thror.getLineNumber();
			}
			else {
				Global.message += "<div class='info'>Extended debug"
					+ " message not possible (no request given, i.e."
					+ " collapsed 2-param-version called).</div>";
			}
		}
		// Extended debug mode? - END
		Global.message += "</div>";
		return message;
	}



//	public static void determineRoot() {
//		// Not used as this file maybe in a different the main content.
//	}



	public static String arrayToString(String[] array) {
		String str = "";
		for (String line : array) {
			str += line + "\r\n";
		}
		return str;
	}



	public static boolean isExactlyOnePath(String path) {
		//TODO This potentially breaks with spaces in the path.
		// Ha, not paths in space. =>Spaceflight.
		return !path.contains(" ");
	}



	/**
	 * Remove root path alias.
	 *
	 * @return The filelink without the root path at the beginning.
	 */
	public static String extractRelativePartOfFilelinkAtEndOnly(String filelink) {
		return removeRootPath(filelink);
	}



	/**
	 * Remove root path
	 * @return Relative path starting with the upload target directory!
	 */
	public static String removeRootPath(String filelink) {

		return filelink.replace(Global.root, "");

	}



	public static String getImageLinkFromFile(String filelink) {

		return getImageLinkFromFile(filelink
				, Global.imageTypeToGenerate);

	}



	public static String getImageLinkFromFile(String filelink
			, String ending) {
		String[] parts = filelink.split("[.]");
		if (parts.length < 2) {
			addMessage("Filelink has no ending, after split it"
					+ " was null instead!"
					+ " (getImageLinkFromFile)<br />"
					+ " filelink = " + filelink, "success");
			return "ENDING WAS NULL";
		}
		String filelink_ending = parts[parts.length - 1];
		//String imagelink = filelink
		//	.replaceAll(filelink_ending + "$", ending);
		String imagelink = filelink + "." + ending;
		// TODO - optional: Test if file exists.
		return imagelink;
	}



	public static String getTxtLinkFromFilelink(String filelink) {
		String[] parts = filelink.split("[.]");
		if (parts.length < 2) {
			addMessage("Filelink has no ending, after split it"
					+ " was null instead!"
					+ " (getImageLinkFromFile)<br />"
					+ " filelink = " + filelink, "success");
			return "ENDING WAS NULL";
		}
		String filelink_ending = parts[parts.length - 1];
		String imagelink = filelink
			.replaceAll(filelink_ending + "$", "txt");
		// TODO - optional: Test if file exists.
		return imagelink;
	}






	// ======= IMAGE GENERATION based with contributions by Artiom K.
	public static void createImageFromText(String[] text, int width
			, String path, String filename
			, String ext_desired_to_be_added) throws IOException {

		File file = new File(path);
		// Directory?
		if (file.isDirectory() && !file.exists()) {
			file.mkdirs();
		}
		// File?
		else if (file.getParentFile() != null
				&& !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		createImageFromText(text, width
				, path + filename + "." + ext_desired_to_be_added);

	}



	public static void createImageFromText(String[] text, int width
			, String imagelink) throws IOException {

		//String pathTo = imagelink
		//	.replaceAll(filelink_ending + "$", ending);
		String pathTo = Global.extractPathTo(imagelink);
		File filePath = new File(pathTo);
		// Directory?
		if (filePath.isDirectory() && !filePath.exists()) {
			filePath.mkdirs();
		}
		// File?
		else if (filePath.getParentFile() != null
				&& !filePath.getParentFile().exists()) {
			filePath.getParentFile().mkdirs();
		}

		Font font = new Font("Serif", Font.PLAIN, 12);

		// @since 19. March 2014:
		int height = text.length * (font.getSize() + 10) ;
		if (text == null || text.length == 0) {
			System.err.print("The text's length is null or zero (0): "
					+ text
					+ " Please check how a part declaration may"
					+ " come to contain 0 lines or no text at all.");
			height = 1;
		}
		// Depending on font size, line count, line distance: 10 un
		// @since 03. November 2013:
		if (text.length == 1) {
			height = Global.arrayToString(text).length()
				* (font.getSize() + 10) / Global.charactersPerLine;
		}

		BufferedImage img = new BufferedImage(
				width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();

	   //g2.setFont(new Font(arg0, arg1, arg2))
		g2.fillRect(0, 0, width, height);

		g2.setFont(font);
		g2.setColor(Color.black);
		int x = 20;
		int y = 20;

		for (int i = 0; i < text.length; i++) {
			g2.drawString(
					System.getProperty("line.separator") + text[i]
					, x, y += 20);
		}

		String ext_and_format = Global.extractEnding(imagelink)
			.replaceFirst("^[.]", "");
		File outputfile = new File(imagelink);
		ImageIO.write(img, ext_and_format, outputfile);
	}



	/**
	 * Deals with special characters and encodes those.
	 *
	 * @param request
	 * @param param_key
	 * @return The parameter value or null.
	 */
	public static String getParameterEncoded(HttpServletRequest request
			, String param_key) {
		return getParameterEncodedOrEmpty(request, param_key, null);
	}



	public static String getParameterEncodedOrEmpty(HttpServletRequest request
			, String param_key, String param_key_alternative) {
		String encoded = request.getParameter(param_key);
		// Is null?
		if (encoded == null && param_key_alternative != null) {
			encoded = request.getParameter(param_key_alternative);
		}
		// Still null?
		if (encoded == null) {
			return null;//"";
		}
		// Deal with special character (new as of v30.91) to fix
		// that the search did not work:
		try {
			encoded = new String(encoded.getBytes("ISO-8859-1")
					, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		encoded = Global.encodeUmlauts(encoded);
		return encoded;
	}



	/**
	 * @changelog: JRIBW: Now this function no is static. Furthermore now a generic method for
	 * encoding is utilized, so that other planet programs can decode automatically/dynamically.
	 */
	private static final int[] unicode = new int[] {
		  0x00E4, 0x00C4
		, 0x00F6, 0x00D6
		, 0x00DF
		, 0x00FC, 0x00DC
		//, ' ' <-- we handle this separately because 0x0020
		// instead of _ will be too long a filepath.
	};

//	private static final String[] replacement = new String[] {
//			  "0ae", "0Ae"
//			, "0oe", "0Oe"
//			, "0sz"
//			, "0ue", "0Ue",
//			"_"/* PREVIOUSLY THIS WAS replaced by empty "" */
//			/* Now replacement is dynamically derived. */
//	};



	public static String encodeUmlauts(String str) {
		//str = str.toLowerCase();//<-- this has destroyed information

		/* Leading 0 (zero) was introduced to avoid confusion:
		e.g. 'Puerto' became P\u00DCrto and Bauer Ba\u00DCr, now this
		can happen no more.
		*/

		if (str.contains(System.getProperty("file.separator"))) {
			/*
			-- is category splitter as single - might be used in text.
			/  is replaced by triple - (---) hence an empty category
			   will be --<empty>--==----
			   and will not be matched by the ---.
			*/
			str = str.replaceAll(System.getProperty("file.separator")
					, "---");
			//TODO Track potential issues if slash->underscore was used
		}
		if (str.contains(" ")) {
			str = str.replaceAll(" ", "_");
		}
		String search;
		String replacement;
		for (int i = 0; i < unicode.length; i++) {
			search = String.valueOf(Character.toChars(unicode[i]));
			search = String.valueOf((char) unicode[i]);
			//replacement = unicode[i].replaceFirst("\\\\", "");
			// <--omitting the slash\ is a more generic solution.
			replacement = "u" + toHexString(unicode[i], 4);
			// <-- without the u decoded will break for double umlauts.
			str = str.replaceAll(search, replacement);
		}
		return str;
	}



	/**
	 * Convert a number to a hexadecimal string.
	 *
	 * @param l
	 * return The hexadecimal string representing the number.
	 */
	public static String toHexString(long l) {
		return toHexString(l, 0);
	}

	public static String toHexString(long l, int minimum_digit_count) {
		String hexString = Long.toHexString(l);
		//fill with leading zeroes
		while (hexString.length() < minimum_digit_count) {
			hexString = "0" + hexString;
		}
		return hexString.toUpperCase();
	}



	public static String decodeUmlauts(String str) {
		if (str == null) {
			System.out.println("DecodeUmlauts: Was given " + str);
			return null;
		}
		//str = str.toLowerCase();
		if (str.contains("---")) {
			str = str.replaceAll("---"
					, System.getProperty("file.separator"));
		}
		if (str.contains("_")) {
			str = str.replaceAll("_", " ");
		}
		String search;
		String replacement;
		for (int i = 0; i < unicode.length; i++) {
			// Without u multiple umlauts in a row will break!
			search = "u" + toHexString(unicode[i], 4).toUpperCase();
			// leading \?
//			if (!str.contains("\\\\u")) {
//				search = unicode[i].replaceFirst("\\\\", "");
//
//			}
			//Long.parseLong(<hex_string>, 16);
			replacement = String.valueOf((char) unicode[i]);
			// Replace case insensitively, e.g. both 00e4 and 00E4:
			str = str.replaceAll("(?i)" + search, replacement);
		}

		return str;
	}



	/* http://stackoverflow.com/questions/3436118/is-java-regex-case-insensitive#answer-3439064
	 * polygenelubricants
	 */
	public static void regexFlagSwitchingAtCorrectPosition() {
		/*
		To demonstrate, here's a similar example of collapsing runs
		of letters like "AaAaaA" to just "A".
		*/
		System.out.println(
			"AaAaaA eeEeeE IiiIi OoooOo uuUuUuu"
				.replaceAll("(?i)\\b([A-Z])\\1+\\b", "$1")
		); // A e I O u
		/*
		Now suppose that we specify that the run should only be
		collapsed if it starts with an uppercase letter.
		Then we must put the (?i) in the appropriate place:
		*/
		System.out.println(
			"AaAaaA eeEeeE IiiIi OoooOo uuUuUuu"
				.replaceAll("\\b([A-Z])(?i)\\1+\\b", "$1")
		); // A eeEeeE I O uuUuUuu
	}



	public static boolean renameAllDerivativesOfFilelink(
			String filelink, String destination) {

		// Are the preconditions met? (Source and destination exist?)
		File sourceFile = new File(Global.root + filelink);
		if (!sourceFile.exists()) {
			System.out.print(
				Global.addMessage("Source not exists:"
					+ " renameAllDerivativesOfFilelink."
					+ " Filelink = source = " + filelink
					+ "\r\n<br />Operation aborted!"
					, "danger")
			);
			return false;
		}
		File destinationFile = new File(Global.root + destination);
		if (!destinationFile.exists()) {
			destinationFile = new File(Global.root
					+ Global.extractPathTo(destination));
			if (!destinationFile.exists()) {
				System.out.print(
					Global.addMessage("Destination not exists:"
						+ " renameAllDerivativesOfFilelink."
						+ " Destination = target directory or file = "
						+ destination + "\r\n<br />Operation aborted!"
						, "danger")
				);
				return false;
			}
		}

		String destination_directory_absolute
			= destinationFile.getAbsolutePath();
		if (!destinationFile.isDirectory()) {
			System.out.print("Destination given was a file, extracted"
					+ " parent directory of it. destination: "
					+ destinationFile.getAbsolutePath());
			destination_directory_absolute
				= destinationFile.getParentFile().getAbsolutePath();
		}

		// Try to delete all possible file derivations.
		// Double endings ensure that no other sheetdraft
		// in the same directory is deleted accidentially.
		String filelink_derivative;
		boolean all_derivatives_been_renamed = true;
		boolean all_images_been_renamed = true;
		for (DocType docType : DocType.values()) {

			// Build hypothetical filelink of derivative
			filelink_derivative = filelink.replaceAll(
					"[.]" + Global.extractEnding(filelink) + "$"
					, /*"." +*/ docType.toString()
			);
			// Build destination for derivative
			String destination_for_derivative = destination_directory_absolute
					+ System.getProperty("file.separator")
					+ Global.extractFilename(filelink_derivative)
					+ "." + Global.extractEnding(filelink_derivative);

			// Rename derivative
			if (Global.renameFile(Global.root + filelink_derivative
						, destination_for_derivative)) {
				System.out.print(
					Global.addMessage("<p>The file: <strong>"
						+ Global.extractFilename(filelink_derivative)
						+ " Flavour: ."
						+ Global.extractEnding(filelink_derivative)
						+ "</strong> was deleted.</p>"
						, "success")
				);
			}
			else {
				all_derivatives_been_renamed = false;
				System.out.print(
					Global.addMessage("<p>The file: <strong>"
						+ Global.extractFilename(filelink_derivative)
						+ "."
						+ Global.extractEnding(filelink_derivative)
						+  "</strong> does not exist.</p>"
						, "nosuccess")
				);
			}

			// Build destination for derivative's image
			String destination_for_derivative_s_image
				= destination_directory_absolute
				+ System.getProperty("file.separator")
				+ Global.extractFilename(
						Global.convertToImageLink(filelink_derivative))
				//+ "." + Global.extractEnding(
				//	Global.convertToImageLink(filelink_derivative));
				+ "." + Global.imageTypeToGenerate;

			// Rename images to each derivative
			if (Global.renameFile(Global.root
						+ Global.convertToImageLink(
							filelink_derivative)
						, destination_for_derivative_s_image)) {

				System.out.print(
					Global.addMessage("<p>The file: <strong>"
						+ Global.extractFilename(filelink_derivative)
						+ " Flavour: ." + Global.imageTypeToGenerate
						+ "</strong> was deleted.</p>"
						, "success")
				);
			}
			else {
				all_images_been_renamed = false;
				System.out.print(
					Global.addMessage("<p>The file: <strong>"
						+ Global.extractFilename(filelink_derivative)
						+ ".png </strong> not exists.</p>"
						, "nosuccess")
				);
			}

		}

		return all_derivatives_been_renamed && all_images_been_renamed;

	}



	public static boolean deleteAllDerivativesOfFilelink(
			String filelink) {

		// Try to delete all possible file derivations.
		// Double endings ensure that no other sheetdraft
		// in the same directory is deleted accidentially.
		String filelink_derivative;
		boolean all_derivatives_been_deleted = true;
		boolean all_images_been_deleted = true;
		for (DocType docType : DocType.values()) {

			filelink_derivative = filelink.replaceAll(
					"[.]" + Global.extractEnding(filelink) + "$"
					, /*"." +*/ docType.toString()
			);
			// Derivative
			if (Global.deleteFile(
						new File(Global.root + filelink_derivative))
					) {
				System.out.print(
					Global.addMessage("<p>The file: <strong>"
						+ Global.extractFilename(filelink_derivative)
						+ " Flavour: ."
						+ Global.extractEnding(filelink_derivative)
						+ "</strong> was deleted.</p>"
						, "success")
				);
			}
			else {
				all_derivatives_been_deleted = false;
				System.out.print(
					Global.addMessage("<p>The file: <strong>"
						+ Global.extractFilename(filelink_derivative)
						+ "." + Global.extractEnding(
							filelink_derivative)
						+  "</strong> does not exist.</p>"
						, "nosuccess")
				);
			}

			// Images to each derivative
			if (Global.deleteFile(
						new File(Global.root + Global
							.convertToImageLink(filelink_derivative))
						)) {
				System.out.print(
					Global.addMessage("<p>The file: <strong>"
						+ Global.extractFilename(filelink_derivative)
						+ " Flavour: .png</strong> was deleted.</p>"
						, "success")
				);
			}
			else {
				all_images_been_deleted = false;
				System.out.print(
					Global.addMessage("<p>Die Datei: <strong>"
						+ Global.extractFilename(filelink_derivative)
						+ ".png </strong> not exists.</p>"
						, "nosuccess")
				);
			}

		}

		return all_derivatives_been_deleted && all_images_been_deleted;

	}



	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				if (!deleteDir(new File(dir, children[i]))) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}



	public static boolean deleteFile(File file) {
		if (file.exists() && file.isFile()) {
			file.delete();
			return true;
		}
		return false;
	}



	public static void createDirs (String dir) {
		File f = new File(dir);
		f.mkdirs();
	}



	/**
	 * Moves the folder or the file into a different location.
	 *
	 * @param toBeMoved Path to file|folder to be moved.
	 * @param target Path to the target folder.
	 * @return true if moved successfully, else false
	 * @throws IOException
	 */
	public static boolean moveDir(String toBeMoved, String target_dir
			, String child_dir_or_filename) throws IOException {
		// File (or directory) to be moved
		File file = new File(toBeMoved);

		// Destination directory
		File dir = new File(target_dir);

		// Move file to new directory
		boolean success = file.renameTo(new File(dir
					, child_dir_or_filename != null
					? child_dir_or_filename : ""));
		if (!success) {
			return false;
		}
		else {
			return true;
		}
	}



	/**
	 * Rename file
	 */
	public static boolean renameFile(String old, String new_) {
		// Overwrite existing by default:
		return renameFile(old, new_, true);
	}

	public static boolean renameFile(String old, String new_
			, boolean overwrite) {

		File oldFile = new File(old);
		if (!oldFile.exists()) {
			System.out.println(
				Global.addMessage("In Global.renameFile: Old file "
						+ old + " not exists!", "danger")
			);
			return false;
		}
		File newFile = new File(new_);
		if (newFile.exists()) {
			System.out.println("In Global.renameFile: The new file"
					+ " destination " +
					+ new_ + " already exists and will"
					+ (overwrite ? "" : " NOT") + " get overwritten!");
			if (!overwrite) {
				return false;
			}
		}
		return oldFile.renameTo(newFile);

	}



	/**
	 * Convert to imagelink
	 */
	public static String convertToImageLink(String filelink) {
		return convertToImageLink(filelink, Global.imageTypeToGenerate);
	}



	public static String convertToImageLink(String filelink
			, String ending) {
		//return filelink.replaceAll("[.][a-zA-Z]+$", "." + ending);
		return filelink + "." + ending;/*Changed with Update v30.83 !!
		because we need images to each flavour to see the differences!*/
	}



	/**
	 * Extract filename + fileending
	 */
	public static String extractFilenameAndEnding(String filelink) {
		String[] parts = filelink
			.split(System.getProperty("file.separator"));
		String candidate = parts[parts.length - 1];
		return candidate;
	}



	/**
	 * Extract filename
	 */
	public static String extractFilename(String filelink) {
		String[] parts = filelink
			.split(System.getProperty("file.separator"));
		String candidate = parts[parts.length - 1];
		// Replace only the last ending:
		return candidate.replaceAll("[.][a-zA-Z]+$", "");
	}



	/**
	 * EXTRACT FILENAME WITHOUT ANY EXTENSION
	 * The first approach using reversing: good idea, Artiom created it
	 */
	public String extractFilenameWithoutAnyExtension(String filelink) {
		// Build name of PDF file.
		StrBuilder str_b = new StrBuilder(filelink);
		String rev_str = str_b.reverse().toString();
		int firstOccurrenceOfPoint = rev_str.indexOf(".");
		String without_ext_rev
			= rev_str.substring(firstOccurrenceOfPoint + 1);

		String inFileEndingRev
			= rev_str.substring(0, firstOccurrenceOfPoint);
		String ending
			= new StrBuilder(inFileEndingRev).reverse().toString();

		String filePathWithoutEnding
			= new StrBuilder(without_ext_rev).reverse().toString();
		//this.filePathWithoutEndingAndNotMarkedAsOriginal
		//	= this.filePathWithoutEnding
		//	.replaceAll("/[.]original(e)?$/i", "");

		return filePathWithoutEnding;

	}



	/**
	 * EXTRACT PARENT PATH / PATH TO
	 */
	public static String extractPathTo(String filelink) {
		return filelink.replaceAll(
				Global.extractFilename(filelink) + "."
				+ Global.extractEnding(filelink) + "$"
				, "" // replace with nothing => path to file left
		);
	}



	/**
	 * EXTRACT ORIGINSHEETDRAFT FILELINK
	 */
	public static String extractSheetdraftFilelinkFromPartFilelink(
			String filelink) {
		String[] parts = filelink.split("__");
		String result = "";
		// The last two parts belong to the part only:
		// 1) __Part_<number>
		// 2) __splitby_<splitter>.<native_ending>.<derived_ending>
		// Thus these two are to be skipped!
		for (int i = 0; i < parts.length - 2; i++) {
			result += parts[i]; // The remainder is concatenated.
		}
		if (!new File(Global.root + result).exists()) {
			System.out.println(
					Global.addMessage("The sheetdraft the part"
						+ " originates from could not"
						+ " be found in the filesystem."
						+ " Review Global.getOriginsheetdraftFilelink."
						, "danger")
			);
		}
		return result;
	}



//	public String getSheetdraftFilelink() {
//		// IT HAS TO BE ENSURED THAT __Part is being added at
//		// part AS FIRST ADDITION.
//		// OR USE Global.extractSheetdraftFilelinkFromPartFilelink
//		int filelink_to_remove_index_start
//			= filelink.indexOf("__Part");
//		return filelink.substring(0, filelink_to_remove_index_start-1);
//	}



	/**
	 * EXTRACT PART PART ONLY FROM FILE LINK
	 */
	public static String extractPartPartOnlyFromPartFilelink(
			String filelink) {
		if (filelink == null || filelink.isEmpty()) {
			return "";
		}
		String[] parts = filelink.split("__");
		String result = "";
		// The last two parts belong to the part only:
		// 1) __Part_<number>
		// 2) __splitby_<splitter>.<native_ending>.<derived_ending>
		// => So these two have to get concatenated!
		for (int i = parts.length - 2; i < parts.length; i++) {
			if (i > parts.length - 2) {
				result += "__"; // what been destroyed be rebuilt.
			}
			result += parts[i]; // The remainder is concatenated.
		}
		// No existence check required here as the part in the
		// filesystem includes the parent filelink at the beginning,
		// the part only part is only a suffix
		// to the filelink of the originsheetdraft.
		return result;
	}



	/**
	 * EXTRACT ENDING DOUBLE
	 */
	public static String extractEndingDouble(String filelink) {
		String[] parts = filelink.split("[.]");
		// Has one ending?
		if (parts.length < 2) {
			addMessage("Filelink has not even one ending, after split"
					+ " it was null instead!"
					+ " (extractEndingDouble)<br />filelink = "
					+ filelink
					, "danger");
			return "ENDING WAS NULL";
		}
		// Has a double ending?
		if (parts.length < 3) {
			addMessage("Filelink has no double ending, after split"
					+ " it was null instead!"
					+ " (extractEndingDouble)<br />filelink = "
					+ filelink
					, "danger");
			return "ENDING WAS NULL";
		}
		return parts[parts.length - 2] + "." + parts[parts.length - 1];
	}



	/**
	 * EXTRACT ENDING
	 */
	public static String extractEnding(String filelink) {
		String[] parts = filelink.split("[.]");
		if (parts.length < 2) {
			addMessage("Filelink has no ending, after split"
					+ " it was null instead!"
					+ " (extractEnding)<br />filelink = "
					+ filelink
					, "success");
			return null;//"ENDING WAS NULL";
		}
		return parts[parts.length - 1];
	}



	/**
	 * EXTRACT ENDING PER REVERSE (VARIANT OF THE UPPER)
	 */
	public static String extractEndingPerReverse(String filelink) {
		String filename_reversed = new StringBuffer(filelink)
			.reverse().toString();
		int punkt = filename_reversed.indexOf(".");
		String ext_reversed = filename_reversed.substring(0, punkt);
		return new StringBuffer(ext_reversed).reverse().toString();
	}



	/**
	 * REPLACE ENDING
	 */
	public static String replaceEnding(String filelink, String newext) {
		if (newext == null) {
			throw new IllegalArgumentException("Global.replaceEnding"
					+ " parameter was null: newext .");
			//return filelink;
		}
		if (!newext.startsWith(".")) {
			newext = "." + newext;
		}
		return filelink.replaceAll(
				"[.]" + extractEnding(filelink) + "$", newext);
	}



	/**
	 * EXTRACT PART NUMBER FROM FILELINK
	 */
	public static int extractPartNumberFromFilelink(
			String filelink) {

		filelink = Global.extractPartPartOnlyFromPartFilelink(
				filelink);

		// Split in part_<number> and splitby_<splitter>:
		String[] parts = filelink.split("[_][_]");
		int candidate;
		if (parts != null) {
			candidate = Global.getInt(parts[0]);
			if (candidate != Integer.MAX_VALUE) {
				return candidate;
			}
			else {
				parts = parts[0].split("_");
				if (Global.isInt(parts[1])) {
					return Integer.parseInt(parts[0]);
				}
			}

		}

		// To be extracted:
		String partNumber = "";
		//
		String substring_beginning = "Part_";
		int splitter_start_filelink_index = filelink
			.indexOf(substring_beginning);
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "__part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "-Part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "-part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		/* hopefully we have success before those emergency solutions: */
		if (splitter_start_filelink_index == -1) {
			substring_beginning = ".Part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = ".part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "_Part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "_part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "Part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "part_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		// success?
		int index_start = splitter_start_filelink_index;
		if (index_start == -1) {
			addMessage("No part number could be extracted!"
					+ " filelink: " + filelink, "blackwhite");
			return -1;
		}
		int index = index_start;
		int substring_beginning_index = 0;
		int substring_beginning_L = substring_beginning.length();
		String ending_buffer = "";
		boolean terminatingUnderscoreOccurredOnce = false;
		while (index < filelink.length()) {
			char ch = filelink.charAt(index);
//			if (Character.isLetter(ch)) {
				// Prevent index out of bounds error:
				if (substring_beginning_index >= substring_beginning_L
						|| ch != substring_beginning.charAt(substring_beginning_index)) {
					// Reached the end?
					if (ch == '_' || ch == '.' || ch == '-') {
						if (terminatingUnderscoreOccurredOnce
								|| ch == '.' || ch == '-') {
							// The 2nd time in series an underscore.
							// => STOP.
							// Terminate this while loop, we have
							// reached the end of the splitter:
							break;
						}
						ending_buffer += "_";
						terminatingUnderscoreOccurredOnce = true;
					}
					else {
						// Last was a single _ and not __, so the
						// ending buffer stands for a space, hence
						// we have to add it to the splitter/split_by.
						partNumber += ending_buffer;
						// Clear ending buffer as it is only a space:
						ending_buffer = "";
						// Add the current char, too. Easy to forget.
						partNumber += ch;
					}
//				}
			}
			index++;
			substring_beginning_index++;
		}

		// Finally the part within __splitby_<this part>__ is extracted
		return Integer.parseInt(partNumber);
	}



	/**
	 * EXTRACT SPLITTER
	 *
	 * @param filelink Must be part filelink. Sheetdraft
	 * filelinks do not contain it.
	 * @return
	 */
	public static String extractSplitterFromFilelink(String filelink) {
		return Global.extractSplitByFromFilelink(filelink);
	}
	public static String extractSplitByFromFilelink(String filelink) {

		filelink = Global.extractPartPartOnlyFromPartFilelink(
				filelink);

		// To be extracted:
		String splitter = "";
		//
		String substring_beginning = "__splitby_";
		int splitter_start_filelink_index = filelink
			.indexOf(substring_beginning);
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "__splitter_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "-splitby_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "-splitter_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		// Hopefully successful before those emergency solutions:
		if (splitter_start_filelink_index == -1) {
			substring_beginning = ".splitby_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = ".splitter_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "_splitby_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "_splitter_";
			splitter_start_filelink_index = filelink
				.indexOf(substring_beginning);
		}
		// success?
		int index_start = splitter_start_filelink_index;
		if (index_start == -1) {
			// no split by/splitter defined
			// => determine automatically (ideal if works and works!)
			// USE DECLARATIONS: 1., 2., ... e.g. (stored at
			// sheetdraft.declarations)
			// Now we progagate up and use INTDOT/INTBRACKET/...
			// depending on which fits best on an
			// optionally specified custom __splitby_ in the filelink.
			addMessage(
					"No custom splitter specified in upload filelink."
					+ "\r\n<br/>=>Auto-detecting part declarations"
					, "blackwhite");
			// TODO These comments are historic as now this all works.
			// Alternatively use a per convention default splitter
			// e.g. Aufgabe. <-- this has been successfully coded
			//addMessage("=> USING 'Aufgabe' OR 'Part' instead."
			//		"info");
			return "";
		}
		int index = index_start;
		int substring_beginning_index = 0;
		int substring_beginning_L = substring_beginning.length();
		String ending_buffer = "";
		boolean terminatingUnderscoreOccurredOnce = false;
		while (index < filelink.length()) {
			char ch = filelink.charAt(index);
//			if (Character.isLetter(ch)) {
				// Prevent index out of bounds error:
				if (substring_beginning_index >= substring_beginning_L
						|| ch != substring_beginning
						.charAt(substring_beginning_index)) {
					// Reached the end?
					if (ch == '_' || ch == '.' || ch == '-') {
						if (terminatingUnderscoreOccurredOnce
								|| ch == '.' || ch == '-') {
							// The 2nd time in series an underscore.
							// => STOP
							// Terminate this while loop, we have
							// reached the end of the splitter.
							break;
						}
						ending_buffer += "_";
						terminatingUnderscoreOccurredOnce = true;
					}
					else {
						// Last was a single _ and not __, so the
						// ending buffer stands for a space so it has
						// to be added to the splitter/split_by.
						splitter += ending_buffer;
						// Clear the ending buffer as is only a space:
						ending_buffer = "";
						// Add the current char. Easy to forget that.
						splitter += ch;
					}
//				}
			}
			index++;
			substring_beginning_index++;
		}

		// Finally the part within __splitby_<this part>__ is extracted
		return splitter;
	}



	/**
	 * EXTRACT SEMESTER FROM FILELINK
	 */
	public static String extractSemesterFromFilelink(String filelink) {
		String[] parts = filelink
			.split("[" + System.getProperty("file.separator") + "]");
		//parts[0] = upload_directory:uploads/ or / is first character!
		//parts[1] = semester;
		//parts[2] = course;
		//parts[3] = lecturer;
		//parts[4] = type;
//		return parts[1];
		//parts[length-6] = upload_directory:uploads/  or / is 1st char
		//parts[length-5] = semester;
		//parts[length-4] = course;
		//parts[length-3] = lecturer;
		//parts[length-2] = type;
		//parts[length-1] = filename; // last array entry
		return parts[parts.length - 5];
	}



	/**
	 * EXTRACT COURSE FROM FILELINK
	 */
	public static String extractCourseFromFilelink(String filelink) {
		String[] parts = filelink
			.split("["+ System.getProperty("file.separator") +"]");
		//parts[0] = upload_directory:uploads/ or / is first character!
		//parts[1] = semester;
		//parts[2] = course;
		//parts[3] = lecturer;
		//parts[4] = type;
//		return parts[2];
		//parts[length-6] = upload_directory:uploads/  or / is 1st char
		//parts[length-5] = semester;
		//parts[length-4] = course;
		//parts[length-3] = lecturer;
		//parts[length-2] = type;
		//parts[length-1] = filename; // last array entry
		return parts[parts.length - 4];
	}



	/**
	 * EXTRACT LECTURER FROM FILELINK
	 */
	public static String extractLecturerFromFilelink(String filelink)
			throws IOException, SQLException {
//		Global.addMessage("filelink to extract lecturer from = "
//				+ filelink, "info");
		String[] parts = filelink.split("["
				+ System.getProperty("file.separator") + "]");
		//parts[0] = upload_directory:uploads/ or / is first character!
		//parts[1] = semester;
		//parts[2] = course;
		//parts[3] = lecturer;	//TODO this implies lecturer is unique.
								//Weigel->WolfgangWeigel
								//		->WWeigel
		//parts[4] = type;
//		return parts[3];
		//parts[length-6] = upload_directory:uploads/  or / is 1st char
		//parts[length-5] = semester;
		//parts[length-4] = course;
		//parts[length-3] = lecturer;
		//parts[length-2] = type;
		//parts[length-1] = filename; // last array entry
		return parts[parts.length - 3];
	}



	/**
	 * Extract lecturer id from filelink.
	 */
	public static int extractLecturerIdFromFilelink(String filelink)
			throws IOException, SQLException {
		String query = "SELECT id FROM lecturer WHERE lecturer = '"
				+ Global.extractLecturerFromFilelink(filelink) + "'";
		ResultSet res =	Global.query(query);

		int lecturer_id = -1;
		while (res.next()) {
			lecturer_id = res.getInt("id");
		}
		// Tackle memory leaks by closing result set, its statement:
		Global.queryTidyUp(res);

		if (lecturer_id == -1) {
			// Because the first entry (id = 0) is N.N.
			// which is to be used if no lecturer is given:
			lecturer_id = 0;
			Global.addMessage("Using N.N. as the lecturer id could not"
					+ " be determined in the database (was -1)."
					+ " This happens when the lecturer is a new one."
					//+ "Query: " + query
					, "warning");
		}

		return lecturer_id;
	}



	/**
	 * EXTRACT TYPE FROM FILELINK
	 */
	public static String extractTypeFromFilelink(String filelink) {
		String[] parts = filelink
			.split("["+ System.getProperty("file.separator") +"]");
		//parts[0] = upload_directory:uploads/ or / is first character!
		//parts[1] = semester;
		//parts[2] = course;
		//parts[3] = lecturer;
		//parts[4] = type;
//		return parts[4];
		//parts[length-6] = upload_directory:uploads/  or / is first character!;
		//parts[length-5] = semester;
		//parts[length-4] = course;
		//parts[length-3] = lecturer;
		//parts[length-2] = type;
		//parts[length-1] = filename; //last array entry
		return Global.decodeUmlauts(parts[parts.length - 2]);

	}



	public static Pattern determinePatternBestFittingToSplitterHint(
			String splitter) {

		for (Muster m : Muster.values()) {
			if (m.name().equalsIgnoreCase(splitter.trim())
					|| m.getPattern().matcher(splitter.trim())
					.matches()) {
				return m.getPattern();
			}
		}

		return Pattern.compile(splitter);

	}




//	public String getPartFilelinkExtensionPattern() {
//		return "";
//	}







	/**
	 * GET SEMTERM FROM NOW
	 */
	public static String getSemtermFromCalendar(Calendar calendar) {

		Map<String, Map<String, Calendar>> semtermsAndTimesMap
			= new HashMap<String, Map<String, Calendar>>();
		Calendar semtermStart = Calendar.getInstance();
		Calendar semtermEnd = Calendar.getInstance();
		Map<String, Calendar> timesMap
			= new HashMap<String, Calendar>();

		// SUMMER TERM year, month, day
		semtermStart.set( calendar.get(Calendar.YEAR),  3 - 1,  1 );
		semtermEnd.set( calendar.get(Calendar.YEAR),  10 - 1,  1 );
		timesMap.put("start", semtermStart);
		timesMap.put("end", semtermEnd);
		semtermsAndTimesMap.put("SS", timesMap);//TODO: INTO CONFIG!

		// WINTER TERM
		timesMap = new HashMap<String, Calendar>();
		semtermStart = semtermEnd;
		// now.year + 1 !! Because the end is the following year:
		semtermEnd.set( calendar.get(Calendar.YEAR) + 1
				,  3/* - 1*/ // month
				,  1 // day
				);
		// No longer -1 because in upload we have less equal comparison
		// while here we have before!
		timesMap.put("start", semtermEnd);
		timesMap.put("end", semtermStart);
		semtermsAndTimesMap.put("WS", timesMap);//TODO: PUT IN CONFIG!

		// Finally determine in which term the calendar date falls
		if (calendar.after(semtermsAndTimesMap.get("SS").get("start"))
				&& calendar.before(semtermsAndTimesMap.get("SS")
					.get("end"))) {
			return "SS";// SUMMER_TERM
		}
		//else
		return "WS";// WINTER_TERM
	}






	/**
	 * INT TO BOOLEAN
	 */
	public static boolean intToBoolean(int i) {
		if (i < 0 || i > 1) {
			throw new IllegalArgumentException(
					"Argument of intToBoolean must be 0 or 1.");
		}
		return i > 0 ? true : false;
	}



	/**
	 * BOOLEAN TO INT
	 */
	public static int booleanToInt(boolean b) {
		return b ? 1 : 0;
	}






	/**
	 * ADD FILES TO EXISTING ZIP ARCHIVE
	 * http://stackoverflow.com/questions/3048669/how-can-i-add-entries-to-an-existing-zip-file-in-java
	 */
	public static void addFilesToExistingZip(File zipFile
			, File[] files) throws IOException {
		// For saving of the old entries in the Zip-File.
		// (it will get deleted).
		File tempFile = File.createTempFile(zipFile.getName(), null);
		// Delete it, otherwise you cannot rename your existing zip
		// file to it.
		tempFile.delete();
		boolean renameOk = zipFile.renameTo(tempFile);
		if (!renameOk) {
			throw new RuntimeException("Could not rename the file "
					+ zipFile.getAbsolutePath() + "to"
					+ tempFile.getAbsolutePath());
		}

		byte[] buffer = new byte[1024];
		ZipInputStream zin = new ZipInputStream(
				new FileInputStream(tempFile));
		ZipOutputStream zout = new ZipOutputStream(
				new FileOutputStream(zipFile));

		ZipEntry entry = zin.getNextEntry();
		while (entry != null) {
			String entryName = entry.getName();
			boolean notFoundInFiles = true;
			for (File f : files) {
				if (f.getName().equals(entryName)) {
					notFoundInFiles = false;
					break;
				}
			}
			if (notFoundInFiles) {
				// Add this zip entry to the output stream.
				zout.putNextEntry(new ZipEntry(entryName));
				// Transfer bytes from ZIP to the output target file:
				int len;
				while ((len = zin.read(buffer)) > 0) {
					zout.write(buffer, 0, len);
				}
			}
			entry = zin.getNextEntry();
		}
		// Close the zip input stream.
		zin.close();
		// Compress the files.
		for (int i = 0; i < files.length; i++) {
			InputStream in = new FileInputStream(files[i]);
			// Add the ZIP entry to the output stream:
			zout.putNextEntry(new ZipEntry(files[i].getName()));
			// Transfer bytes from the file to the ZIP file. (?)
			int len;
			while ((len = in.read(buffer)) > 0) {
				zout.write(buffer, 0, len);
			}
			// Complete the entry.
			zout.closeEntry();
			// Close the input stream because now the new ZIP
			// should include all: both the new and the old entries.
			in.close();
		}
		// Complete the ZIP file
		zout.close();
		tempFile.delete();


//		ALTERNATIVE USING COMMONS.IO FROM APACHE:
//		ZipFile zipFile = new ZipFile("foo.zip");
//		InputStream in = null;
//		ByteArrayOutputStream out = null;
//		try {
//			in = zipFile.getInputStream(
//					pFile.getEntry("fileInTheZip"));
//			out = new ByteArrayOutputStream();
//			IOUtils.copy(in, out);
//		} finally {
//			IOUtils.closeQuietly(in);
//			IOUtils.closeQuietly(out);
//		}

	}



	/**
	 * Adds an arbitrary file to a zip archive file.
	 * Several flavours are available:
	 *
	 * @param give a zipFilelink and a filelink_to_add;
	 * @param give a zipFile and a file_to_add;
	 * @param give a zip output stream and a file.
	 */
	//http://jcsnippets.atspace.com/java/input-output/create-zip-file.html
	private static final int BUFFER_SIZE = 4096;


	//2 params
	public static void addFileToZip(String zipFilelink
			, String filelink_to_add_to_zip)
		throws FileNotFoundException {

		addFileToZip(new File(zipFilelink)
				, new File(filelink_to_add_to_zip));

	}

	public static void addFileToZip(File zipFile
			, File file_to_add_to_zip) throws FileNotFoundException {
		addFileToZip(
				new ZipOutputStream(new FileOutputStream(zipFile))
				, file_to_add_to_zip);
	}

	public static void addFileToZip(ZipOutputStream zos
			, File file_to_add) {
		addFileToZip(zos, file_to_add, file_to_add.getName());
	}


	//3 params
	public static void addFileToZip(String zipFilelink
			, String filelink_to_add_to_zip
			, String entry_path_filename_within_zip)
		throws FileNotFoundException {

		addFileToZip(new File(zipFilelink)
				, new File(filelink_to_add_to_zip)
				, entry_path_filename_within_zip);

	}

	public static void addFileToZip(File zipFile
			, File file_to_add_to_zip
			, String entry_path_filename_within_zip)
		throws FileNotFoundException {

		addFileToZip(new ZipOutputStream(
					new FileOutputStream(zipFile))
				, file_to_add_to_zip, entry_path_filename_within_zip);

	}

	public static void addFileToZip(ZipOutputStream zos
			, File file_to_add
			, String entry_path_filename_within_zip)
	{
		byte [] data = new byte[BUFFER_SIZE];
		int len;

		FileInputStream fis = null;
		ZipEntry entry = new ZipEntry(entry_path_filename_within_zip);
		try
		{
			entry.setSize(file_to_add.length());
			entry.setTime(file_to_add.lastModified());

			zos.putNextEntry(entry);
			fis = new FileInputStream(file_to_add);

			CRC32 crc32 = new CRC32();
			while ((len = fis.read(data)) > -1)
			{
				zos.write(data, 0, len);
				crc32.update(data, 0, len);
			}
			entry.setCrc(crc32.getValue());

			zos.closeEntry();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			try
			{
				if (fis != null)
				{
					fis.close();
				}
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}



	/**
	 * Creates a zip file from either a directory string given or an
	 * array of files of type File.
	 * @param directory to be checked if it is a directory or not.
	 */
	public static void createZipFile(String directory)
	{
		File dir = new File(directory);
		createZipFile(dir);
	}

	public static void createZipFile(File dir) {
		if (dir.isDirectory())
		{
			File[] files = dir.listFiles();
			createZipFile(files, dir.getName());
		}
		else
		{
			System.out.println(dir.getAbsolutePath()
					+ " is not a directory. Zipping a single file"
					+ " arhive now instead.");
			File[] filesToBeZipped = new File[1];
			filesToBeZipped[0] = dir;
			createZipFile(filesToBeZipped, dir.getName());
		}
	}



	public static void createZipFile(File[] files, String zipName) {
		if (files != null)
		{
			File zip = new File(zipName + ".zip");

			FileOutputStream fos = null;
			ZipOutputStream  zos = null;

			try
			{
				fos = new FileOutputStream(zip);
				zos = new ZipOutputStream(fos);
				zos.setMethod(ZipOutputStream.DEFLATED);

				for (int i = 0; i < files.length; i++)
				{
					if (files[i].isFile())
					{
						System.out.println("Zipping "
								+ files[i].getName());
						addFileToZip(zos, files[i]);
					}
				}
			}
			catch (FileNotFoundException fnfe)
			{
				fnfe.printStackTrace();
			}
			finally
			{
				if (zos != null)
				{
					try
					{
						zos.close();
					}
					catch (IOException ioe)
					{
						ioe.printStackTrace();
					}
				}
				if (fos != null)
				{
					try
					{
						fos.close();
					}
					catch (IOException ioe)
					{
						ioe.printStackTrace();
					}
				}
			}
		}
	}



//	public static void main(String [] args)
//	{
//		if (args.length == 1) {
//			Global.createZipFile(args[0]);
//		}
//		else {
//			System.out.println("usage: java Global <directory>");
//		}
//	}



	static InputStream getInputStream(String zip
			, String entry) throws IOException
	{
		return getInputStream(new File(zip), entry);
	}

	//http://stackoverflow.com/questions/14603319/getinputstream-for-a-zipentry-from-zipinputstream-with-out-using-zipfile
	static InputStream getInputStream(File zip
			, String entry) throws IOException {
		ZipInputStream zin
			= new ZipInputStream(new FileInputStream(zip));

		for (ZipEntry e; (e = zin.getNextEntry()) != null;) {
			// zin.getNext moves pointer of InputStream to next entry.
			if (e.getName().equals(entry)) {
				// Mow we have it at the correct position in the
				// stream: the hydro plant ..
				return zin;
			}
		}
		throw new EOFException("Cannot find zip-entry: " + entry);

	}



	/**
	 * GET INPUT STREAM DEPENDING ON ENDING
	 */
	static InputStream getInputStreamDependingOnEnding(String filelink)
		throws IOException {

		File file = new File(filelink);
		if (!file.exists()) {
			Global.addMessage("Global.getInputStreamDependingOnEnding:"
					+ " File not found!", "danger");
			return null;
		}

		// DOCX
		if (filelink.endsWith(".docx")/*
				|| filelink.endsWith(".zip")
				|| filelink.endsWith(".tar")
				|| filelink.endsWith(".gz")
				|| filelink.endsWith(".7z")
				|| filelink.endsWith(".rar")*/) {
			ZipFile docx = new ZipFile(file);
//			IOUtils.copy(docx.getInputStream(entry),);
//			ZipEntry docxXML = docx.getEntry(entry);
			Global.addMessage("docx-Contents:\r\n<br />"
					+ renderZipContents(docx), "info");
			// word/document.xml entry of the zip contains the content
			return getInputStream(new File(filelink), "word"
					+ /*System.getProperty("file.separator")*/ "/"
					+"document.xml");
		}

		// ODT OpenDocumentFormat:Text OpenXML (Libre/OpenOffice)
		if (filelink.endsWith(".odt")/*
				|| filelink.endsWith(".zip")
				|| filelink.endsWith(".tar")
				|| filelink.endsWith(".gz")
				|| filelink.endsWith(".7z")
				|| filelink.endsWith(".rar")*/) {
			ZipFile zip = new ZipFile(file);
//			  IOUtils.copy(docx.getInputStream(entry),);
//			  ZipEntry docxXML = docx.getEntry(entry);
			Global.addMessage("ZIP-Contents:\r\n<br />"
					+ renderZipContents(zip), "info");
			//content.xml entry of the archive contains the content
			return getInputStream(new File(filelink), "content.xml");
		}


		// All single source (non-binary) files
		return new FileInputStream(new File(filelink));
	}






	/**
	 * RENDER DOCX CONTENTS
	 */
	public static String renderDocXContents(String filelink)
			throws IOException {
		return renderZipContents(new ZipFile(filelink), "word"
				+ /*System.getProperty("file.separator")*/ "/"
				+ "document.xml");
	}



	/**
	 * RENDER DOCX CONTENTS
	 */
	public static String renderODTContents(String filelink)
			throws IOException {
		return renderZipContents(new ZipFile(filelink), "content.xml");
	}



	/**
	 * RENDER ZIP CONTENTS
	 */
	public static String renderZipContents(ZipFile zip) {
		return renderZipContents(zip, "");
	}

	public static String renderZipContents(ZipFile zip, String entryToLookFor) {
		String zipContents = "";
		Enumeration<? extends ZipEntry> entriesIter = zip.entries();
		while (entriesIter.hasMoreElements()) {
			ZipEntry zipEntry = entriesIter.nextElement();
			if (zipEntry.getName().equals(entryToLookFor)) {
				Global.addMessage("renderZipContents: "
						+ entryToLookFor + " found!","success");
			}
			zipContents += zipEntry.getName() + "\r\n<br />";
		}
		return zipContents;
	}



	/**
	 * Using Apache native american's wise help. :)
	 * @throws IOException
	 */
	public static int copyZip(String source_filelink
			, String target_filelink) throws IOException {

		ZipInputStream zin = new ZipInputStream(
				new FileInputStream(source_filelink));
		ZipOutputStream zout = new ZipOutputStream(
				new FileOutputStream(target_filelink));

		return IOUtils.copy(zin, zout);

	}

	public static int copy(String source_filelink
			, String target_filelink) throws IOException {

		InputStream in = new FileInputStream(source_filelink);
		OutputStream out = new FileOutputStream(target_filelink);
		int result = IOUtils.copy(in, out);
		in.close();
		out.close();
		return result;

	}



	/**
	 * Checks if the given string can be parsed to an integer.
	 *
	 * @param i String to check.
	 * @return true, if it can be parsed to an integer. Else false.
	 */
	public static boolean isInt(String string) {

		string = string.replace(".", " ").trim();
		// The gentle and complicated (under the hood) way:
		String numPattern = ".*[\\d]+.*";//"[0-9]+";
		// Works, note the .* above in the pattern is important:
		if (string.matches(numPattern)
				|| Pattern.compile(numPattern)
				.matcher(string).matches()) {
			return true;
		}

		// still null! then the last ressort Artiom already used below:
		try {
			Integer.parseInt(string);
			return true;
		}
		catch (NumberFormatException e) {
			// No stacktrace printing this time, the exception
			// is really a cruel method
			// of figuring out if it contains a number.
			return false;
		}

	}



	/**
	 * Take care that only one number is contained as all digits
	 * will be chained together which may lead to false results!
	 *
	 * @param string Furthermore this parameter will lose commata et al
	 * @return the int or Integer.MAX_VALUE;
	 */
	//public static int string2int(String string) {
	// It is only string2int of a part! Thus rather call it getInt
	public static int getInt(String string) {
		int i = 0;
		String parsable = "";
		// From left to right:
		while (i < string.length()) {
			String s = String.valueOf(string.charAt(i));
			if (Global.isInt(s)) {
				parsable += s;
			}
			i++;
		}
		if (!parsable.isEmpty()) {
			return Integer.parseInt(parsable);
		}

		return Integer.MAX_VALUE;

	}



	public static String toNumbering(String string) {
		int i = 0;
		String parsable = "";
		// From left to right:
		boolean previous_was_number = false;
		while (i < string.length()) {
			String s = String.valueOf(string.charAt(i));
			boolean is_number = Global.isInt(s);
			if ( is_number || (s.equals('.') && previous_was_number)
					|| (s.matches("[abcdefgh]")
						&& previous_was_number) ) {//1a, 1b, 2a, 2b,...
				parsable += s;
				// Do not assign true directly! It could be a '.' too.
				previous_was_number = is_number;
			}
			// Prev was number but now it's neither nor delimiter'.'?
			else if (previous_was_number) {
				// stop the examination
				return parsable;
			}
			else {
				previous_was_number = false;

			}

			i++;
		}
		return parsable;

	}



	/**
	 * @param string
	 * @return
	 */
	public static boolean containsInt(String string) {
		return getInt(string) != Integer.MAX_VALUE;
	}



	/**
	 * Source: Bill Zhao:
	 * http://stackoverflow.com/questions/5585779/how-to-convert-string-to-int-in-java/18731580#18731580
	 */
	public static int strToInt(String str) {
		  int i = 0;
		  int num = 0;
		  boolean isNeg = false;

		  if ( str.charAt(0) == '-') {
			isNeg = true;
			i = 1;
		  }

		  while( i < str.length()) {
			num *= 10;
			num += str.charAt(i++) - '0';
		  }

		  if (isNeg)
			num = -num;
		  return num;
	}



	/**
	 *
	 * @param partFilelinks
	 * @param targetFiletype
	 * @param target_sheetdraft_filelink
	 * @throws Exception
	 */
	//create Global.createFile(String[] partFilelinks,targetFiletype)
	public static void joinToFile(String[] partFilelinks
			, String targetFiletype
			, String target_sheetdraft_filelink) throws Exception {

		joinToFile(partFilelinks, targetFiletype
				, target_sheetdraft_filelink, null, null);

	}
	public static void joinToFile(String[] partFilelinks
			, String targetFiletype
			, String target_sheetdraft_filelink
			, String[] part_numbering_old
			, String[] part_numbering_new)
		throws Exception {
		/*
		The file does not exist in the beginning,
		it is created in the process.
		*/

		// Target sheetdraft filelink exists?
		if (!target_sheetdraft_filelink.startsWith(
					System.getProperty("file.separator") )) {
			target_sheetdraft_filelink
				= Global.root + target_sheetdraft_filelink;
		}
		File targetFile = new File(target_sheetdraft_filelink);
		if (!targetFile.exists()) {
			File target_directory = new File(targetFile.getParent());
			if (!target_directory.exists()) {
				target_directory.mkdirs();
			}
		}
		//TODO The target sheetdraft filelink probably does not
		// exist yet. It will be created by copying the first
		// part of the draft (by loading from the first's
		// part source and rewriting to the new/target
		// sheetdraft filelink) and renaming it in the process
		// (to the sheetdraft's target filelink).
		//
		// Then the subsequent parts will be appended under
		// examination of conflicting relations and while merging
		// of styles.

		//1. CALL_TARGET_CONVERSION_FOR_EACH_PART
		//   SAVE_THOSE_IN_FILESYSTEM_FOR_BEING_EDITABLE
		List<String> successfullyGeneratedParts_filelinks;
		successfullyGeneratedParts_filelinks = new ArrayList<String>();

		int part_number = 0;
		for (String part_filelink : partFilelinks) {

			//part filelink exists?
			File sourceFile = new File(Global.root + part_filelink);
			if (sourceFile.exists()) {
				//Convert to desired target filetype:
				String part_part = Global
					.extractPartPartOnlyFromPartFilelink(
							part_filelink);
				if (part_part.equals("")) {
					System.out.println(Global.addMessage(
							"Probably joining a sheetdraft and"
							+ " parts OR even sheetdrafts only"
							+ " because there was no part part"
							+ " found at the end of the filelink: "
							+ part_filelink
							+ "\n<br/>This sheetdraft thus is skipped."
							+ " TODO Support sheetdrafts joining as a"
							+ " whole in Global.java in joinToFile."
							, "warning"));
					continue;// Only join parts. TODO: +sheets
				}
				String target_filelink_for_deriving_flavours;
				target_filelink_for_deriving_flavours
						= target_sheetdraft_filelink
						+ "__" + part_part;

				List<String> generated_target_filelinks
					= new ArrayList<String>();

				// Can conversion be skipped?
				if (!part_filelink.equals(
							Global.replaceEnding(
								target_filelink_for_deriving_flavours
								, targetFiletype))
						/*!part_filelink.endsWith(targetFiletype)*/
						) {
					// The conversion to hopefully a statue of Rhodes:
					// a list is returned but it contains only one URI
					generated_target_filelinks = Global.convertFile(
							// regenerating it currently. So not
							// checking if it already exists.
							Global.root + part_filelink
							, targetFiletype
							, target_filelink_for_deriving_flavours
					);
				}
				else {
					System.out.println("Part to be joined already"
							+ " in target format and in correct"
							+ " position in filesystem: "
							+ part_filelink
							+ ". Skipped conversion. Could lead to"
							+ " declaration anomaly.");
					generated_target_filelinks.add(part_filelink);
				}
				successfullyGeneratedParts_filelinks
					.addAll(generated_target_filelinks);
				String part_in_target_format_filelink;
				if (generated_target_filelinks.size() > 0) {

					part_in_target_format_filelink
						= generated_target_filelinks.get(0);

					// 2.INSERT THE NEW PARTS INTO THE PARTS DB
					// TABLE REFERENCING THE DRAFT AS MOTHERSHEETDRAFT:
					QUERY_INSERT_INTO_part(
							part_in_target_format_filelink
							, Global.extractFilelinkOfMothersheet(
								// null means determine automatically:
								part_filelink), null, null);


					// 3.OPTIONALLY UNIFY_SPLITTER

					// 4.ADAPT_POSITION (MORE PRECISELY: CHANGE THE
					// PART NUMBERING TO THE GIVEN POSITION)
					if (part_numbering_old != null
							&& part_numbering_new != null
							&& part_number < part_numbering_old.length
							&& part_numbering_old[part_number] != null
							&& part_number < part_numbering_new.length
							&& part_numbering_new[part_number] != null
							) {

						if (!part_numbering_new[part_number]
								.equals(part_numbering_old[part_number])) {

							Global.adaptPartNumbering(
									part_in_target_format_filelink
									, part_numbering_new[part_number]
									, Muster.INTDOT
							);

						}

					}
					else if (part_numbering_new != null
							&& part_number < part_numbering_new.length
							&& part_numbering_new[part_number] != null
							/*
							&& !part_numbering_new[part_number]
							.isEmpty()
							<-- omitted because it could be desired
							to reset/remove the part numbering
							(e.g. for unifying the numbering to lists)
							*/) {

						Global.adaptPartNumbering(
								part_in_target_format_filelink
								, part_numbering_new[part_number]
								, Muster.INTDOT
						);

					}
					else {

						Global.adaptPartNumbering(
								part_in_target_format_filelink
								, (++part_number) + ""
								, Muster.INTDOT
						);

					}
					// TODO update / remove from draft part
					// assignment database table here?
					/* To be able to regenerate/overwrite the parts
					 * we copied over here and perhaps edited,
					   we should just update the sheetdraft filelink
					   (which now has a real ending).
					   The draftpartassignment should stay as is.
					 */

					// 5.APPEND TO THE SHEETDRAFT FILESYSTEM FILE.
//					if (!target_sheetdraft_filelink.startsWith(
//							System.getProperty("file.separator"))) {
//						target_sheetdraft_filelink
//							= Global.root + target_sheetdraft_filelink;
//					}

					// First successful part?
					if (successfullyGeneratedParts_filelinks.size() == 1) {
						// Then use this part file as start point.
						FileUtils.copyFile(
								new File(part_in_target_format_filelink)
								, new File(target_sheetdraft_filelink)
								);
					}
					else {
						// Then append:
						Global.mergeSourceFileIntoTargetFile(
								part_in_target_format_filelink
								, target_sheetdraft_filelink
						);

					}


				}


			}
			else {
				Global.addMessage("Part file not exists at this"
						+ " location: " + part_filelink
						, "nosuccess");
			}
		}


		System.out.println("Joined " + part_number
				+ " parts. Generating images ...");


		// Only the successfully converted parts can be
		// joined/merged as a uniform datatype is required.
		for (String part_filelink : successfullyGeneratedParts_filelinks) {
			// 2. INSERT THE NEW PARTS INTO THE PARTS DB
			// TABLE REFERENCING THE DRAFT AS MOTHERSHEETDRAFT:
			// 3. OPTIONALLY UNIFY_SPLITTER
			// 4. ADAPT PART NUMBERING TO THE POSITION
			// 5. and append
			/* NOTE: All this is already done above via Global.merge().
			Only postprocess here: */

			if (!part_filelink.endsWith("html")) {
				Global.convertFile(part_filelink, "pdf"
						, Global.replaceEnding(part_filelink, "pdf"));
			}
			Global.convertFile(part_filelink
					, Global.imageTypeToGenerate
					, Global.getImageLinkFromFile(part_filelink));
			Global.convertFile(part_filelink
					, PartDB.commonFormat.name().toLowerCase());
		}


		// The built target sheetdraft wants a photoshooting too.
		if (!target_sheetdraft_filelink.endsWith("html")) {
			Global.convertFile(target_sheetdraft_filelink, "pdf"
					, Global.replaceEnding(target_sheetdraft_filelink
						, "pdf"));
		}
		Global.convertFile(target_sheetdraft_filelink
				, Global.imageTypeToGenerate
				, Global.getImageLinkFromFile(target_sheetdraft_filelink));
		Global.convertFile(target_sheetdraft_filelink
				, PartDB.commonFormat.name().toLowerCase());

	}



	/**
	 *
	 * @param filelink
	 * @return
	 */
	//TODO Does this work for non-existing files too?
	public static String getParentDirOfFile(String filelink) {
		return new File(filelink).getAbsoluteFile().getParent();
	}



	/**
	 * Thread d_o is called if sequence is important else start is being called.
	 * @param filelink
	 * @param target_type
	 * @return
	 * @throws Exception
	 */
	public static List<String> convertFile(String filelink, String target_type)
			throws Exception {
		return convertFile(filelink, target_type, filelink);
	}
	public static List<String> convertFile(String filelink
			, String target_type
			, String filelinkForDerivingTargetFilelink)
		throws Exception {
		return convertFile(filelink, target_type
				, filelinkForDerivingTargetFilelink, false);
	}
	public static List<String> convertFile(String filelink
			, String target_type
			, String filelinkForDerivingTargetFilelink
			, boolean isJavaOnlyDesired) throws Exception {

		// TODO WRITE ACCESS IN THE DIRECTORIES?
		List<String> generatedFlavours = new ArrayList<String>();

		String source_ending = Global.extractEnding(filelink);
		String target_flavour_filelink = Global
			.replaceEnding(filelinkForDerivingTargetFilelink
				, target_type.toLowerCase());
		Command[] commandList;
		String target_directory = Global
			.getParentDirOfFile(target_flavour_filelink);

		// The same file type (at source and target) doesn't mean
		// that the two filelinks are equal.
		if (target_type.equalsIgnoreCase(source_ending)) {
			// TODO USE TARGET FILE FOR ALL CONVERSIONS !!
			// <-IS THIS DONE CONSISTENTLY ALREADY?
			if (filelink.equals(target_flavour_filelink)) {
				// The native format of this sheet can be skipped
				// because the filelinks are equal.
				return generatedFlavours;
			}
			else {
				/*
				Copy the file over to the target location.
				THIS WILL SAVE ONE EXTRA RENAME AND
				ONE REMOVE COMMAND PER CONVERSION!
				*/
				File targetFile = new File(
						/*Global.root + */target_flavour_filelink);
				if (targetFile.exists()) {
					System.out.println(Global
							.addMessage("Join target file already"
								+ " exists!" + target_flavour_filelink
								, "warning")
					);
					if (!target_flavour_filelink.contains(".draft")) {
						System.out.println(Global
								.addMessage("As the already existing"
									+ " target flavour filelink"
									+ " contains no .draft indicator,"
									+ " the target is not overwritten."
									+ "\n<br />Aborting ..."
									, "nosuccess")
						);
						return generatedFlavours;
					}
				}
				// Otherwise the way is free and the file is copied:
				FileUtils.copyFile(new File(filelink), targetFile);
				//<--Attention, write permissions for others have to
				//be granted in all folders!
				generatedFlavours.add(target_flavour_filelink);
				return generatedFlavours; //<- as of v31.14
			}

		}


		// Setup converter settings:
		// For checking if the temporarily created file exists
		// where it is expected.
		String hypotheticalTemporaryFilelink
			= target_directory + System.getProperty("file.separator")
			+ Global.extractFilename(filelink) + "."
			+ target_type.toLowerCase();

		Converter.hypotheticalTemporaryFilelink
			= hypotheticalTemporaryFilelink;

		Command commandForRenamingToTargetFile
			= new Command("mv ", hypotheticalTemporaryFilelink
					, " ", target_flavour_filelink);

		Converter.commandForRenamingToTargetFile
			= commandForRenamingToTargetFile;

		Converter.isCrossplatformRequired = isJavaOnlyDesired;
		// Equal meaning. The left more general, thus to be preferred.




		//DOC SOURCE ------------------------------------------------ //
		if (source_ending.equalsIgnoreCase("doc")) {
			if (target_type.equals("TXT")) {
				String[] lines = DocConverter.erstelleTextausDoc(filelink);
				ReadWrite.write(Global.arrayToString(lines), target_flavour_filelink);

			}
			else if (target_type.equalsIgnoreCase("DOCX")) {
				generatedFlavours.addAll(Converter.doc2docx(filelink, target_flavour_filelink, target_directory));
			}
			else if (target_type.equalsIgnoreCase("HTML")) {
				// also saves the html to target filelink:
				DocConverter.doc2html(filelink, target_flavour_filelink);
			}
			else if (target_type.equalsIgnoreCase("PDF")) {
				generatedFlavours.addAll(Converter
						.doc2docx(filelink
							, target_flavour_filelink
							, target_directory));
			}
		}

		// DOCX SOURCE ----------------------------------
		else if (source_ending.equalsIgnoreCase("docx")) {

			if (target_type.equals("DOC")) {
				// This conversion is rejected as not useful.
			}
//			else if (target_type.equals("DOCX")) {
//			}
			else if (target_type.equalsIgnoreCase("HTML")) {
				generatedFlavours.addAll(Converter
						.docx2html(filelink, target_flavour_filelink));

			}
			else if (target_type.equalsIgnoreCase("ODT")) {
				Converter.docx2odt(filelink, target_flavour_filelink
						, target_directory, target_type);

			}
			else if (target_type.equalsIgnoreCase("PDF")) {
				Converter.docx2pdf(filelink, target_flavour_filelink);

			}
			else if (target_type.equalsIgnoreCase("RTF")) {
				Converter.docx2rtf(filelink, target_flavour_filelink);

			}
			else if (target_type.equalsIgnoreCase("TEX")) {
				Converter.docx2tex(filelink, target_flavour_filelink);
			}
			else if (target_type.equalsIgnoreCase("TXT")) {
				// docx4j alternative:
				generatedFlavours.addAll(Converter
						.docx2text(filelink, target_flavour_filelink));
			}


		}

		// IMAGE SOURCE ---------------------------------
		else if (source_ending.equalsIgnoreCase("jpg")
				|| source_ending.equalsIgnoreCase("png")) {
			if (target_type.equalsIgnoreCase("PDF")) {
				Converter.image2pdf(filelink, target_flavour_filelink);
			}
		}

		//HTML SOURCE -----------------------------------
		else if (source_ending.equalsIgnoreCase("html")
				|| source_ending.equalsIgnoreCase("htm")
				|| source_ending.equalsIgnoreCase("xhtml")) {


			//IMAGE from HTML is required often, so it comes first:
			if (target_type.equalsIgnoreCase("PNG")
					|| target_type.equalsIgnoreCase("JPG")) {
				//new org.fit.cssbox.demo.ImageRenderer();
				Converter.html2png(filelink, target_flavour_filelink);
			}
			if (target_type.equalsIgnoreCase("JPG")) {
				// builds upon png conversion
				Converter.html2jpg(filelink, target_flavour_filelink);
			}
			if (target_type.equalsIgnoreCase("SVG")) {
				//new org.fit.cssbox.demo.ImageRenderer();
				Converter.html2svg(filelink, target_flavour_filelink);

			}

			// DOC
			if (target_type.equalsIgnoreCase("DOC")) {//<--this branch
				// is kept only for possible performance benefits.
				// This is neither output format nor an intermediate
				// format, hence can never be a valid target format.
			}

			// DOCX
			else if (target_type.equalsIgnoreCase("DOCX")) {
				// PDF conversion build ontop of DOCX
				// using docx4j XHTMLImporter:
				generatedFlavours.addAll(Converter
						.html2docx(filelink, target_flavour_filelink));

			}
//			// HTML
//			else if (target_type.equals("HTML")) {
//			}
			// ODT
			else if (target_type.equalsIgnoreCase("ODT")) {
				generatedFlavours.addAll(Converter
						.html2odt(filelink, target_flavour_filelink));

			}
			// PDF
			else if (target_type.equalsIgnoreCase("PDF")) {
				// This relies on source->odt->pdf.
				generatedFlavours.addAll(Converter
						.html2pdf(filelink, target_flavour_filelink));

			}
			// RTF
			else if (target_type.equalsIgnoreCase("RTF")) {
				// This is neither output format nor an intermediate
				// format, hence can never be a valid target format.
				generatedFlavours.addAll(Converter
						.html2rtf(filelink, target_flavour_filelink));
			}
			// TEX
			else if (target_type.equalsIgnoreCase("TEX")) {
				//TODO
				//generatedFlavours.addAll(Converter
				//		.html2tex(filelink, target_flavour_filelink));
			}
			// TXT
			else if (target_type.equalsIgnoreCase("TXT")) {
				//<-- relies on successful odt conversion.
				// At this point an odt file has to exist in
				// the same folder as the uploaded file.
				Converter.html2txt(filelink, target_flavour_filelink);

			}



		}




		// ODF SOURCE (OpenDocumentFormat: Text => ODT)  -
		else if (source_ending.equalsIgnoreCase("odt")) {

			// DOC
			if (target_type.equalsIgnoreCase("DOC")) {//<--this branch is kept only for possible performance benefits.
			}
			// DOCX
			else if (target_type.equalsIgnoreCase("DOCX")) {
				//using libre office
				Converter.odt2docx(filelink, target_flavour_filelink);

			}
			// HTML
			else if (target_type.equalsIgnoreCase("HTML")) {
				Converter.odt2html(filelink, target_flavour_filelink);

			}
			// ODT
//			else if (target_type.equalsIgnoreCase("ODT")) {
//			}
			// PDF
			else if (target_type.equalsIgnoreCase("PDF")) {//<-- ATTENTION: builds ontop of docx conversion!
				Converter.odt2pdf(filelink, target_flavour_filelink);
			}
			// RTF
			else if (target_type.equalsIgnoreCase("RTF")) {
				Converter.odt2rtf(filelink, target_flavour_filelink);

			}
			// TEX
			else if (target_type.equalsIgnoreCase("TEX")) {
				//TODO

			}
			// TXT
			else if (target_type.equalsIgnoreCase("TXT")) {
				//At this point a pdf file has to exist in the same folder as the uploaded file.
				Converter.odt2txt(filelink, target_flavour_filelink);
			}



		}
		// PDF SOURCE -----------------------------------
		else if (source_ending.equalsIgnoreCase("pdf")) {
			// DOC
			if (target_type.equalsIgnoreCase("DOC")) {
			}
			// DOCX
			else if (target_type.equalsIgnoreCase("DOCX")) {
				//TODO
			}
			// HTML
			else if (target_type.equalsIgnoreCase("HTML")) {
				//TODO


			}
			// IMAGES
			else if (target_type.equalsIgnoreCase("jpg")
					|| target_type.equalsIgnoreCase("png")
					) {

				//if (target_type.equalsIgnoreCase("jpg")) { <-- using fallback instead to not miss the point when PDFBox is capable of it.
					Options options = Options.getFrom("pdf");
					options.to(Global
							.extractEnding(target_flavour_filelink));
					options.setProperty("convertFirstPageOnly", 1);
					// <--custom property see PDFToImage class.
					PDFToImage.convert(new File(filelink)
							, new File(target_flavour_filelink)
							, options);
				//}

				if (!new File(target_flavour_filelink).exists()
						&& !isJavaOnlyDesired) {
					// Because the default commandos are not pure
					// java yet. TODO update if this changes.
					new UnixComandosThread(filelink
							, target_flavour_filelink).d_o();
				}


			}
			// ODT
			else if (target_type.equalsIgnoreCase("ODT")) {
				Converter.pdf2odt(filelink, target_flavour_filelink);

			}
			// PDF
//			else if (target_type.equalsIgnoreCase("PDF")) {
//			}
			// RTF
			else if (target_type.equalsIgnoreCase("RTF")) {
				Converter.pdf2rtf(filelink, target_flavour_filelink, target_directory, target_type);

			}
			// TEX
			else if (target_type.equalsIgnoreCase("TEX")) {
				//TODO

			}
			// TXT
			else if (target_type.equalsIgnoreCase("TXT")) {
				Converter.pdf2txt(filelink, target_flavour_filelink);
			}



		}
		// RTF SOURCE ----------------------------------
		else if (source_ending.equalsIgnoreCase("rtf")) {
			// DOC
			if (target_type.equalsIgnoreCase("DOC")) {
			}
			// DOCX
			else if (target_type.equalsIgnoreCase("DOCX")) {
				//TODO

			}
			// HTML
			else if (target_type.equalsIgnoreCase("HTML")) {
				//TODO

			}
			// ODT
			else if (target_type.equalsIgnoreCase("ODT")) {
				// using libre office
				Converter.rtf2odt(filelink, target_flavour_filelink);

			}
			// PDF
			else if (target_type.equalsIgnoreCase("PDF")) {
				Converter.rtf2pdf(filelink, target_flavour_filelink, target_type);

			}
			// RTF
//			else if (target_type.equalsIgnoreCase("RTF")) {
//			}
			// TEX
			else if (target_type.equalsIgnoreCase("TEX")) {
				//TODO

			}
			// TXT
			else if (target_type.equalsIgnoreCase("TXT")) {
				Converter.rtf2txt(filelink, target_flavour_filelink);
			}



		}
		// TEX SOURCE -----------------------------------
		else if (source_ending.equalsIgnoreCase("tex")) {

			// DOC
			if (target_type.equalsIgnoreCase("DOC")) {
			}
			// DOCX
			else if (target_type.equalsIgnoreCase("DOCX")) {
				//TODO further port tex2docx C# program.
				Converter.tex2docx(filelink, target_flavour_filelink);
			}
			// IMAGES
			//TODO
			// HTML
			else if (target_type.equalsIgnoreCase("HTML")) {
				Converter.tex2html(filelink, target_flavour_filelink);

			}
			// ODT
			else if (target_type.equalsIgnoreCase("ODT")) {
				//TODO using ODFtoolkit OR tex2docx --> docx2odt
				Converter.tex2odt(filelink, target_flavour_filelink, target_directory, target_type);

			}
			// PDF
			else if (target_type.equalsIgnoreCase("PDF")) {
				Converter.tex2pdf(filelink, target_flavour_filelink, target_directory, target_type);

			}
			// RTF
			if (target_type.equalsIgnoreCase("RTF")) {
				// using 1) odftoolkit to convert to .odt,
				// 2) libreoffice from odt to rtf? TODO clarify.
				Converter.tex2rtf(filelink, target_flavour_filelink
						, target_directory, target_type);

			}
			// TEX
//			else if (target_type.equalsIgnoreCase("TEX")) {
//			}
			// TXT
			else if (target_type.equalsIgnoreCase("TXT")) {
				// --- Via PDF:
				// Convert file to PDF.
				// TODO
				// Extract plain text from the PDF file.
				// TODO

				// --- Via removing all tex-related tags.
				// using opendetex:
				Converter.tex2txt(filelink, target_flavour_filelink);

			}



		}
		// TXT SOURCE ----------------------------------
		else if (source_ending.equalsIgnoreCase("txt")) {
			String[] plainText = ReadWrite.loadText(filelink);

			/* HERE THE MISSION IS TO CREATE DOCUMENTS OUT OF THE TEXT.
			SUCH THAT THE PLAIN TEXT IS IN THE DOCUMENT FORMAT
			AND ONE SAVES THE COPY PASTE. */

			// DOC
			if (target_type.equalsIgnoreCase("DOC")) {
			}
			// DOCX
			else if (target_type.equalsIgnoreCase("DOCX")) {
				// using docx4j
				WordprocessingMLPackage wordMLPackage
					= WordprocessingMLPackage.createPackage();
				wordMLPackage.getMainDocumentPart()
					.addStyledParagraphOfText("title"
							, Global.arrayToString(plainText));
				wordMLPackage
					.save(new java.io.File(target_flavour_filelink));
				// using styled runs instead:
				// (omitted for keeping it clean)
				// Docx4JCreation. <-- see for further examples.
			}
			// IMAGES
			else if (target_type.equalsIgnoreCase("jpg")
					|| target_type.equalsIgnoreCase("png")
					) {
				Converter.txt2image(plainText, target_flavour_filelink);
			}
			// HTML
			else if (target_type.equalsIgnoreCase("HTML")) {
			}
			// ODT
			else if (target_type.equalsIgnoreCase("ODT")) {
				// using odftoolkit simple API
				TextDocument odt;
				odt = TextDocument.newTextDocument();
				// add image
				//odt.newImage(new URI(""));
				odt.addParagraph(Global.arrayToString(plainText));

				/*
				org.odftoolkit.simple.text.list.List list = odt.addList();
				String[] items;
				items[0] = "Part 1";
				items[1] = "Part 2";
				list.addItem(0, "");
				//list.addItems(0, new String[] {""});
				list.addItems(items);

				// add a table
				org.odftoolkit.simple.table.Table table
					= odt.addTable(2, 2);
				org.odftoolkit.simple.table.Cell cell
					= table.getCellByPosition(0, 0);
				cell.setStringValue(text);
				*/
				odt.save(target_flavour_filelink);
			}
			// PDF
			else if (target_type.equalsIgnoreCase("PDF")) {
				// no data format that is handled for joining
				// parts directly. Data format
				// for handling is OpenDocumentFormat (ODF Text).
			}
			// RTF
			else if (target_type.equalsIgnoreCase("RTF")) {
			}
			// TEX
			else if (target_type.equalsIgnoreCase("TEX")) {
			}
			// TXT
//			else if (target_type.equalsIgnoreCase("TXT")) {
//			}

		}

		/*
		Did we succeed in generating the target file?
		*/
		if (new File(target_flavour_filelink).exists()) {
			//if (!hypotheticalTemporaryFilelink.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
//				commandList = new String[1];
//				commandList[0] = commandForRenamingToTargetFile;
//				new UnixComandosThread(hypotheticalTemporaryFilelink
//						, target_flavour_filelink).d_o(commandList);
				//Global.renameFile(hypotheticalTemporaryFilelink, target_flavour_filelink);
			//}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The target file"
						+ " could not be generated: "
						+ target_flavour_filelink
						, "nosuccess"));
		}


		return generatedFlavours;

	}






	/**
	 *
	 * @param pageId -- Page to show.
	 */
	public static void redirectTo(String pageId) {
		//request.setAttribute("id", pageId);
		Global.anzeige = pageId;
		session.setAttribute("id", pageId);
		session.setAttribute("anzeige", pageId);
		//<-- this is the one that is used as page switch!
		//TODO unify to page!
	}



	/**
	 *
	 * @param source_filelink[s] single array of source filelinks to be merged.
	 * @param merge_target_filelink the target where the joined, merged files will end up.
	 * @throws Exception
	 */
	public static void mergeSourceFileIntoTargetFile(String source_filelink, String merge_target_filelink)
			throws Exception {

		mergeSourceFileIntoTargetFile(new String[] { source_filelink }
				, merge_target_filelink);


	}

	public static void mergeSourceFileIntoTargetFile(
			String[] source_filelinks, String merge_target_filelink)
		throws Exception {

		/*
		Also used for indication of which filetype's merge routine
		to use by examining those for the one being not NULL.
		*/
		TextDocument odt; odt = null;
		WordprocessingMLPackage wmlPackage; wmlPackage = null;

		String merge_target_ending
			= Global.extractEnding(merge_target_filelink);

		if (merge_target_ending.equalsIgnoreCase("ODT")) {
			odt = TextDocument
				.loadDocument(new File(merge_target_filelink));
		}
		else if (merge_target_ending.equalsIgnoreCase("DOCX")) {
			wmlPackage = Docx4J/*.WordprocessingMLPackage*/
				.load(new File(merge_target_filelink));

		}
		else if (merge_target_ending.equalsIgnoreCase("TXT")) {

		}
		else if (merge_target_ending.equalsIgnoreCase("TEX")) {

		}
//		else if (merge_target_ending.equalsIgnoreCase("RTF")) {
//
//		}
		else if (merge_target_ending.equalsIgnoreCase("HTML")) {

		}
		else {
			System.out.println(Global.addMessage("Merging "
						+ merge_target_ending
					+ "-files does either not make sense or is not"
					+ " supported yet. (ODT, DOCX, TXT, TEX are"
					+ " considered as useful target format currently.)"
					, "nosuccess")
			);
			return ;
		}


		// Merge all subsequent part files into merge target file:
		for (String source_filelink : source_filelinks) {

			String source_ending = Global.extractEnding(source_filelink);
			if (!source_ending.equals(merge_target_ending)) {
				System.out.println(Global.addMessage(
							"Part could not be merged"
							+ " into target because the filetype is"
							+ " not the same even though there were"
							+ " though there were efforts to convert"
							+ " it automatically previously: "
							+ source_ending + " != "
							+ merge_target_ending, "nosuccess"));
				continue ;
			}


			// merge
			if (odt != null) {// ODF
				//http://incubator.apache.org/odftoolkit/simple/document/cookbook/Text%20Document.html#Generate%20TextDocument
				TextDocument source_odt;
				source_odt = TextDocument.loadDocument(new File(source_filelink));
				NodeList childNodes = odt.getContentRoot().getChildNodes();
				// Fetch reference to the first paragraph starting
				// from the document end:
				int childNodes_index = childNodes.getLength() - 1;
				org.odftoolkit.simple.text.Paragraph target_paragraph;
				target_paragraph = null;
				while (--childNodes_index > -1) {
					if (childNodes.item(childNodes_index)
							.getLocalName().equalsIgnoreCase("p")
							|| childNodes.item(childNodes_index)
							instanceof org.odftoolkit.simple.text
							.Paragraph) {
						target_paragraph
							= (org.odftoolkit.simple.text.Paragraph)
							childNodes.item(childNodes_index);
					}
				}
				if (target_paragraph != null) {
					//odt.insertContentFromDocumentBefore(source_odt, target_paragraph, true/*copy styles*/);
					odt.insertContentFromDocumentAfter(source_odt
							, target_paragraph, true/*copy styles*/);
				}
				// TODO copy over resources like images.
			}


			else if (wmlPackage != null) {// DOCX
//				docx4j_library.AltChunkAddOfTypeDocx;
//				docx4j_library.MergeDocx;
				/*
				iterate all nodes, follow relationships and copy
				those over to the target - inclusive the related files.
				OR
				Load the source document. Take the whole content of
				its root element node, append it to the target's
				root content.
				*/
				// start with a clean setup:
				MergeUtil.rId_changesMap.clear();
//				MergeUtil.source_alreadyAddedPart_oldNameToNewNameMap.clear();
				MergeUtil.source_alreadyAddedPart_oldNameToByteArrayMap.clear();


				MainDocumentPart mainDocumentPart
					= wmlPackage.getMainDocumentPart();
				org.docx4j.wml.Document target_wmlDocumentEl
					= (org.docx4j.wml.Document)
					mainDocumentPart.getJaxbElement();

				WordprocessingMLPackage source_wMLPac = Docx4J
					.load(new File(source_filelink));
				org.docx4j.wml.Document source_wmlDocumentEl
					= (org.docx4j.wml.Document)
					source_wMLPac.getMainDocumentPart().getJaxbElement();



				// TODO not working for adding multiple source files
				// at once without prior saving. PartName is unique
				// only within one document and we don't check for
				// multiple packages. Instead only within the target
				// package gets examined.

				// TODO handle styles too.
				//for (StyleDefinitionsPart styleDefinitionsPart
				//		: (StyleDefinitionsPart[])source_wMLPac
				//		.getMainDocumentPart()
				//		.getStylesInUse().toArray() ) {

				// TODO should other relationships parts really
				// be copied too? e.g. the wmlPackage ones?
				List<RelationshipsPart> target_allRelationshipsParts
					= new ArrayList<RelationshipsPart>();
//				target_allRelationshipsParts
//					.add(wmlPackage.getRelationshipsPart());
//					// <-- for headers, footers, ..
//					// (otherwise save complaints about missing parts.)
				target_allRelationshipsParts
					.add(wmlPackage.getMainDocumentPart()
							.getRelationshipsPart());

				List<RelationshipsPart> source_allRelationshipsParts
					= new ArrayList<RelationshipsPart>();
//				source_allRelationshipsParts
//					.add(source_wMLPac.getRelationshipsPart());
//					// <-- for headers, footers, ..
//					// (otherwise save complaints about missing parts.)
				source_allRelationshipsParts.add(source_wMLPac.getMainDocumentPart().getRelationshipsPart());


//				wmlPackage.setTargetPartStore(
//						wmlPackage.getSourcePartStore());
//				wmlPackage.setSourcePartStore(
//						source_wMLPac.getSourcePartStore());
//						// <-- loading from here when saving.

				int allRelationshipsParts_index = -1;
				while (++allRelationshipsParts_index
						< source_allRelationshipsParts.size()) {
					// Concentrate on main document relationship
					// parts only. Hence all parts that are
					// referenced via a relationship there.

					// IMPORTANT NOTE: If a relationshipPart with
					// this part as target does not have at least
					// one relationship, it won't be saved!!
					RelationshipsPart target_rp
						= target_allRelationshipsParts
						.get(allRelationshipsParts_index);
					RelationshipsPart source_rp
						= source_allRelationshipsParts
						.get(allRelationshipsParts_index);

					// copy relationships, maintain valid references
					// (i.e. relIds of objects and parts(?)).
					// => maintain/rename relId to keep them unique.

					// effort 4
//					MergeUtil.traverseAndCopyRelationships(
//							source_rp, wmlPackage);
//					MergeUtil.traverseAndCopyElements(
//							source_rp, source_wMLPac
//							.getMainDocumentPart().getContent()
//							, wmlPackage);

					// complete algorithm effort 3:
					MergeUtil.traverseAndCopyRelationships(source_rp
							, wmlPackage, target_rp, source_wMLPac);
					//<--TODO works without target_rp too?

					/*
					Next RelationshipsPart's target parts we wish
					to add from the source to the target package.
					*/

				}


				// add the objects. Replace the xml's r:id s from
				// the old to the new values (if changed).
				MergeUtil.traverseAndReplaceRelIdsUnique(
						source_wMLPac, wmlPackage);



				// restore binary content that may have been unset
				// by the VM or in another way not was set.
//TESTING		for (Part part_that_was_added : MergeUtil
//						.source_alreadyAddedPart_oldNameToByteArrayMap
//						.keySet()) {
//					byte[] bytes = MergeUtil
//						.source_alreadyAddedPart_oldNameToByteArrayMap
//						.get(part_that_was_added);
//					if (bytes != null) {
//						((BinaryPart) wmlPackage.getParts()
//						.get(part_that_was_added.getPartName()))
//						.setBinaryData(bytes);
//					}
//				}
				wmlPackage.save(new File(merge_target_filelink));
				// not reset before saving. here reset has no effect
				// too. so .. well .. it's superfluous.
//				wmlPackage.setSourcePartStore(wmlPackage
//					.getTargetPartStore());

			}



			else if (merge_target_ending.equalsIgnoreCase("HTML")
					|| merge_target_ending.equalsIgnoreCase("HTM")) {

				System.out.println("Trying to load sheetdraft and"
						+ " part contents.");
				String old = Global.arrayToString(ReadWrite
						.loadText(merge_target_filelink));
				if (old != null) {
					// replace the end tags. similar to tex the
					// \end{document}. The format might not be
					// unified well. Still it is joined.
					// And that's the goal for now.
					old.replaceAll("[<][/]body[>].*[<][/]html[>]", "");
				}
				else {
					old = "<html><head><title>AVS merged HTML-parts</title></head>"
						+ System.getProperty("line.separator")
						+ "<body>";
				}

				String toAppend = Global.arrayToString(
						ReadWrite.loadText(source_filelink));
				// TODO join arrays instead?
				System.out.println("Trying to write new HTML-content:");
				// That's already performed in the callee function.
				//if (old.isEmpty()
				//		|| old.trim().length()
				//		!= toAppend.trim().length()) {
					ReadWrite.write(
							old + toAppend + "</body>"
							+ System.getProperty("line.separator")
							+ "</html>"
							, merge_target_filelink
							, false /*isToBeAppended*/
					);
//				}
//				else {
//					ReadWrite.write(
//							toAppend
//							, merge_target_filelink
//							, false /*isToBeAppended*/
//					);
//				}
				System.out.println("(Still alive afterwards.)");

			}

			// TXT
			else if (merge_target_ending.equalsIgnoreCase("TXT")) {

				System.out.println("Trying to load sheetdraft and"
						+ " part contents.");
//				String old = Global.arrayToString(
//						ReadWrite.loadText(merge_target_filelink));
				String toAppend = Global.arrayToString(
						ReadWrite.loadText(source_filelink));
				// TODO join arrays instead?
				System.out.println("Trying to append txt-part: ");
				// That's already performed in the callee function.
				//if (old.isEmpty() || old.trim().length()
				//		!= toAppend.trim().length()) {
					ReadWrite.write(
							/*old + */toAppend
							, merge_target_filelink
							, true /*isToBeAppended*/
					);
//				}
//				else {
//					ReadWrite.write(
//							toAppend
//							, merge_target_filelink
//							, false /*isToBeAppended*/
//					);
//				}
				System.out.println("(Still alive afterwards.)");

			}


			else if (merge_target_ending.equalsIgnoreCase("TEX")) {
				// Here document has to be replaced or shifted/moved,
				// so that it is always at the bottom.
				//TODO
				System.out.println("Trying to load sheetdraft and"
						+ " part contents.");
				String old = Global.arrayToString(
						ReadWrite.loadText(merge_target_filelink));
				if (old != null) {
					// Replace the end tags. For TeX that's the
					// \end{document}. TODO anything more to replace?
					/*
					The format might not be unified well. Still
					it is joined. And that's the goal for now.
					*/
					old.replaceAll("\\\\end[{]document[}]", "");
				}
				else {
					old = "\begin{document}"
						+ System.getProperty("line.separator")
						+ "";
				}

				String toAppend = Global.arrayToString(
						ReadWrite.loadText(source_filelink));
				// TODO join arrays instead?
				System.out.println("Trying to write new HTML-content:");
				// That's already performed in the callee function.
				//if (old.isEmpty() || old.trim().length()
				//		!= toAppend.trim().length()) {
					ReadWrite.write(
							old + toAppend + ""
							+ System.getProperty("line.separator")
							+ "\\end{document}"
							, merge_target_filelink
							, false /*isToBeAppended*/
					);
//				}
//				else {
//					ReadWrite.write(
//							toAppend
//							, merge_target_filelink
//							, false /*isToBeAppended*/
//					);
//				}
				System.out.println("(Still alive afterwards.)");

			}



		}

	}





	/**
	 *
	 * @param part_filelink
	 * @param new_part_numbering
	 * @param targetNumberingPattern
	 * @throws Exception
	 */
	public static void adaptPartNumbering(String part_filelink
			, String new_part_numbering
			, Muster targetNumberingPattern)
		throws Exception {

		//if (part_container is list for each part
		//		=> so part numbering is correct automatically
		//		unless no specific starting number is specified
		//		) {
			//delete specific starting numbering
		//}

		Part part = new Part(part_filelink);
		//<-- calls determineDeclaration
		String oldWordWithNumber = null;

		String replacementNumbering
				= determinePartNumberReplacement(
						new_part_numbering
						, targetNumberingPattern);

		String newWordWithNumber = replacementNumbering;
		// Is preceding old word to be added?
		if (part != null && part.getDeclaration() != null
				&& part.getDeclaration()
				.getWordContainingNumbering() != null) {

			oldWordWithNumber = part.getDeclaration()
				.getWordContainingNumbering();
			String oldNumbering = toNumbering(oldWordWithNumber);

			newWordWithNumber = oldWordWithNumber
				.replaceFirst(oldNumbering, replacementNumbering);
		}


		String part_ending = Global.extractEnding(part_filelink);

		if (part_ending.equalsIgnoreCase("ODT")) {
			TextDocument odt; odt = null;
			odt = TextDocument.loadDocument(new File(part_filelink));
			/*
			Assumption: There really is only one part in
			an part file.
			*/

			// 1. find element where part declaration was found
			part.travelDownUntilDeclarationFound(
					odt.getContentRoot()
					, part.getDeclaration());


			// 3. adapt the numbering:
			// part_container is of type list but it's
			// not guaranteed for all parts
			if (part.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat
					!= null) {
				// set specific starting number
				if (part.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat
						instanceof org.w3c.dom.Node) {

					  // find first valid (non-text) node that
					  // contains the numbering:
					  Object deepest_node_containing_numbering
						  = part.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat;
					  org.w3c.dom.Node parent;
					  while (deepest_node_containing_numbering != null
							  && ( parent = ((org.w3c.dom.Node)
									deepest_node_containing_numbering)
								.getParentNode() ) != null
							  && ( deepest_node_containing_numbering
								instanceof org.w3c.dom.Text
								|| ( deepest_node_containing_numbering
									instanceof org.w3c.dom.Element
									&& (
										((org.w3c.dom.Element)
										deepest_node_containing_numbering)
										.getTagName()
										.equals("text:span")
										/*
										=> Runs/spans also are not
										enough, it should be a block
										element.
										*/
										|| ((org.w3c.dom.Element)
											deepest_node_containing_numbering)
										.getTagName()
										.equals("text:td") )
										/*
										otherwise if numbering and
										declaration text are in
										different table columns there
										would be a problem.
										*/
										)
										|| ( parent instanceof
												org.w3c.dom.Element
												&& ((org.w3c.dom.Element) parent)
												.getTagName()
												.equals("text:td") )
										/* <-- Mostly there is a
										paragraph inside the table
										cell. So is this (block)
										element's parent node a
										table column? Then we
										continue going up to Tr,
										i.e. table row.
										*/
									|| parent.getChildNodes()
									.getLength() < 2 /* <- OR if
									the parent element has only 1
									child node. */
									)
							) {
						// climb higher:
						deepest_node_containing_numbering = parent;
					}

					// We use the parent though we are probably
					// only one sibling too far to be sure:
					org.w3c.dom.Text textNode
						= (org.w3c.dom.Text)Global
						.getTextNodeThatContainsPartNumberDirectlyRecursively(
							deepest_node_containing_numbering
							/*getPreviousSibling()*/, part);

					if (textNode != null) {
						if (oldWordWithNumber != null
								&& !oldWordWithNumber.isEmpty()) {
							textNode.replaceWholeText(
									textNode.getWholeText()
									.replaceFirst(
										oldWordWithNumber/*part
										.getDeclaration().getIndex()
										.toString()*/
										, newWordWithNumber)
							);
						}
						else {
							// prepend a new line with the new declaration (numbering).
							textNode.replaceWholeText(newWordWithNumber
									+ System.getProperty("line.separator")
									+ textNode.getWholeText()
							);
						}
					}
					else {
						System.out.println("Could not get the Text"
								+ " Node that contains the part"
								+ " declaration: oldWordWithNumber: "
								+ oldWordWithNumber + ". textNode: "
								+ textNode
								+ System.getProperty("line.separator")
								+ "This had been the replacement if"
								+ " it had been found: "
								+ newWordWithNumber
								+ " (newWordWithNumber)");
					}

				}
			}
			else {
				System.out.println("No element found containing the"
						+ " part declaration directly.");
			}

			// save changes
			odt.save(new File(part_filelink));

		}


		else if (part_ending.equalsIgnoreCase("DOCX")) {

			WordprocessingMLPackage wmlPackage; wmlPackage = null;
			wmlPackage = WordprocessingMLPackage
				.load(new File(part_filelink));
			MainDocumentPart mainDocumentPart
				= wmlPackage.getMainDocumentPart();
			org.docx4j.wml.Document wmlDocumentEl
				= (org.docx4j.wml.Document)mainDocumentPart
				.getJaxbElement();

			/*
			Assumption: There really is only one part in an
			part file.
			*/
			// 1. find element where part declaration was found
			Body rootElement = wmlDocumentEl.getBody();
			// To prevent still having the old data in there:
			part.clearTraversedAndTextBuffer();
			//exer.travelDownUntilDeclarationFound(part_rootElement);
			Part.Docx4JTravelCallback part_traveller
				= part.new Docx4JTravelCallback();
//			part_traveller.setDeclaration(part.getDeclaration());
			new TraversalUtil(rootElement, part_traveller);


			// 3. adapt the numbering:
			/*
			part_container is of type list but it's
			not guaranteed for all parts
			*/
			if (part.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat
					!= null) {
				// set specific starting number
				if (XmlUtils.unwrap(part
							.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat)
						instanceof Child) {

					// Find first non-text parent: (it's not enough
					// if e.g. numbering and declaration text are
					// in different table columns.)
					Object deepest_valid_node
						= part.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat;
					while (deepest_valid_node != null
							&& ((Child)XmlUtils.unwrap(
									deepest_valid_node)).getParent()
							!= null
							&& (XmlUtils.unwrap(deepest_valid_node)
								instanceof org.docx4j.wml.Text
								/*
								As we don't compare the parent node,
								we even go one level higher than
								minimally required. Just to be safe.
								*/
								|| XmlUtils.unwrap(deepest_valid_node)
								instanceof org.docx4j.wml.R
								/*=> Runs/spans also are not enough,
								 * it should be a block element.*/
								|| XmlUtils.unwrap(deepest_valid_node)
								instanceof org.docx4j.wml.Tc
								/*=> Table row is okay. Table column
								 * could eventually not contain the
								 * numbering. See above comment.*/
								|| XmlUtils.unwrap(((Child)XmlUtils
										.unwrap(deepest_valid_node))
									.getParent())
								instanceof org.docx4j.wml.Tc
								/*<-- Mostly there is a paragraph
								inside the table cell. So is this
								block element's parent node a table
								column? */
								|| (  (ContentAccessor) XmlUtils
										.unwrap( ((Child)XmlUtils
												.unwrap(deepest_valid_node))
											.getParent() )  )
								.getContent().size() < 2
								/* <- OR if the parent element
								has only 1 child node. */
								)
							) {
						// climb higher:
						deepest_valid_node = ((Child)XmlUtils.unwrap(deepest_valid_node)).getParent();
					}

					org.docx4j.wml.Text textNode = (org.docx4j.wml.Text)XmlUtils.unwrap(Global
							.getTextNodeThatContainsPartNumberDirectlyRecursively(
									deepest_valid_node, part));

					if (textNode != null) {
						if (oldWordWithNumber != null
								&& !oldWordWithNumber.isEmpty()) {
							textNode.setValue(textNode.getValue()
									.replaceFirst(
									oldWordWithNumber/*part
									.getDeclaration().getIndex()
									.toString()*/
									, newWordWithNumber)//".")
							);
						}
						else {
							// prepend a new line with the new
							// declaration (numbering).
							textNode.setValue(newWordWithNumber
									+ System.getProperty(
										"line.separator")
									+ textNode.getValue()
							);
						}
					}
					else {
						System.out.println("Could not get the Text"
								+ " Node that contains the part"
								+ " declaration: oldWordWithNumber: "
								+ oldWordWithNumber + ". textNode: "
								+ textNode
								+ System.getProperty("line.separator")
								+ "This had been the replacement if"
								+ " it had been found: "
								+ newWordWithNumber
								+ " (newWordWithNumber)");
					}

				}
			}
			else {
				System.out.println("No element found containing"
						+ " the part declaration directly.");
			}

			// save changes
//			Docx4J.save(wmlPackage, new File(part_filelink), FLAGS);
			wmlPackage.save(new File(part_filelink));


		}


		else if (part_ending.equalsIgnoreCase("TXT")
				|| part_ending.equalsIgnoreCase("HTML")) {

			String[] oldText = ReadWrite.loadText(part_filelink);
			String newText = newWordWithNumber
				+ System.getProperty("line.separator")
					+ Global.arrayToString(oldText);
			if (oldWordWithNumber != null
					&& !oldWordWithNumber.isEmpty()) {
				newText = Global.arrayToString(oldText)
					.replaceFirst(oldWordWithNumber, newWordWithNumber);
			}
			//else is default init see above newText.
			ReadWrite.write(newText, part_filelink);

		}


		else if (part_ending.equalsIgnoreCase("TEX")) {

			// As it's only a content change, the tex will stay
			// compilable unless no encoding problems occur.
			String[] oldText = ReadWrite.loadText(part_filelink);
			String newText = newWordWithNumber
				+ System.getProperty("line.separator")
				+ Global.arrayToString(oldText);
			if (oldWordWithNumber != null
					&& !oldWordWithNumber.isEmpty()) {
				newText = Global.arrayToString(oldText)
					.replaceFirst(oldWordWithNumber, newWordWithNumber);
			}
			//else is default init see above newText.
			ReadWrite.write(newText, part_filelink);

		}


//		else if (part_ending.equalsIgnoreCase("RTF")) {
//
//		}


		else {
			System.out.println(Global.addMessage("Merging "
						+ part_ending
						+ "-files does either not make sense or"
						+ " is not supported yet. (ODT, DOCX, TXT, TEX"
						+ " are seen as useful currently.)"
					, "nosuccess")
			);
			return ;
		}


	}



	/**
	 * Works for all characters but the slash!
	 * The slash cannot be replaced.
	 * Special charaters! All those reserved for regular expressions.
	 *
	 * @param string
	 * @param to_replace
	 * @return
	 */
	public static String replaceAllNonEscaped(String string
			, String to_replace) {
		if (to_replace.equals("(") || to_replace.equals(")")
				|| to_replace.equals("[") || to_replace.equals("]")
				|| to_replace.equals(".") || to_replace.equals("+")
				|| to_replace.equals("*") || to_replace.equals("?")
				|| to_replace.equals("^") || to_replace.equals("$")
				) {
			if (to_replace.equals("[") || to_replace.equals("]")
					|| to_replace.equals("(") || to_replace.equals(")")
					) {
				to_replace = "[\\" + to_replace + "]";
				//<- 4x2 so that 2 occur in the regex.
			}
			else {
				to_replace = "[" + to_replace + "]";
				//<- 4x2 so that 2 occur in the regex.
			}
		}
		return string.replaceAll("\\\\" + to_replace + "", "\\\\")
				// <- ()brackets to keep the slashes to be resolved
				// from right instead of left in combination with
				// the above special addition if parantheses.
				.replaceAll("\\" + to_replace, "")
				// <- replace those that were not escaped
				.replaceAll("\\\\\\\\\\\\\\\\", to_replace);
				/*
				<-re add the formerly escaped
				candidates, this time non-escaped (without slash in
				front)
				Note: To match one slash, four slashes are
				required.(4x4 = 16)
				*/
	}



	/**
	 * True if regular expression pattern contains
	 * '[arbitrary&lt;needle&gt;arbitrary]'
	 * e.g. [.].
	 *
	 * @param haystack
	 * @param needle
	 * @return
	 */
	public static boolean isWithinRegularExpressionSquareBrackets(
			String haystack, String needle) {
		if (needle.equals(".")
			|| needle.equals("(") || needle.equals(")")
			|| needle.equals(".") || needle.equals("+")
			|| needle.equals("*") || needle.equals("?")
			) {

			// Because of the use of . et alia in regular expressions.
			needle = "[" + needle + "]";

			if (needle.equals("[") || needle.equals("]")
					|| needle.equals("^")
					|| needle.equals(":?") //not sure if $ has a
					// special meaning within [] too.
					//|| needle.equals("/") <- really crazy special!
					) {
				needle = "\\\\\\\\" + needle; // 4x2 as we wish to
				// have 2 slashes in the regex that follows.
			}

		}
		// Is within square brackets?
		if (haystack.matches("[^]][a-zA-Z][.][a-zA-Z][^\\[]")
				// Note: Java RegEx [[] think the inner [ opens a
				// nested selection.
				|| haystack
				.replaceAll("\\][ a-zA-Z]*([^\\]\\[.])+[ a-zA-Z]*\\["
					, "")
				/*
				Now and only now the following is possible:
				(as otherwise .* would
				match a closing ] before the '.' we look for)
				*/
				.matches("\\[.*" + needle + ".*\\]")
				) {
			return true;
		}
		return false;
	}



	/**
	 *
	 * @param new_part_number
	 * @param targetNumberingPattern
	 * @return
	 */
	public static String determinePartNumberReplacement(
			String new_part_number, Muster targetNumberingPattern
			) {

		boolean isMatchingTargetPattern = new_part_number.matches(
				targetNumberingPattern/*Muster.AUFGABE*/
				.getPatternString());

		if (isMatchingTargetPattern) {
			return new_part_number;
		}

		if (Global.isInt(new_part_number)
				// and only integer and nothing else?
				&& (Global.getInt(new_part_number) + "").length()
				== new_part_number.length()) {

			return targetNumberingPattern.convertNumbering(
					new_part_number
					// Forced part number for performance reasons:
					, Global.getInt(new_part_number)
					);

		}

		return targetNumberingPattern.convertNumbering(
				new_part_number);

	}



	/**
	 *
	 * @param candidate
	 * @param part
	 * @return
	 * @throws Exception
	 */
	public static Object getTextNodeThatContainsPartNumberDirectlyRecursively(
			Object candidate
			, Part part) throws Exception {

		String value = "";
		if (candidate instanceof org.w3c.dom.Text/*Node*/) {
			value = ((org.w3c.dom.Text)candidate).getTextContent()
				/*NodeValue()*/;
		}
		else if (org.docx4j.XmlUtils.unwrap(candidate)
				instanceof org.docx4j.wml.Text) {
			value = ((org.docx4j.wml.Text)org.docx4j.XmlUtils
					.unwrap(candidate)).getValue();
		}

		// Termination condition:
		if (value.contains(
				//part.getDeclaration().getIndex().toString()
				Global.toNumbering(part.getDeclaration()
					.getWordContainingNumbering()))
				) {
			return candidate;
			/* <- that text node value now has to be adapted */
		}



		int childNodes_index;
		// ODT
		if (candidate instanceof org.w3c.dom.Node) {

			org.w3c.dom.NodeList childNodes
				= ((org.w3c.dom.Node) candidate).getChildNodes();
			if (childNodes == null) {
				return null;
			}
			childNodes_index = childNodes.getLength();
			while (--childNodes_index > -1) {
				org.w3c.dom.Node nodeContainingPartNumber
					= childNodes.item(childNodes_index);
				if (nodeContainingPartNumber != null) {
//					if (nodeContainingPartNumber
//							instanceof org.w3c.dom.Text) {
//						return nodeContainingPartNumber;
//						// <-that text node value now has to be adapted
//					}
//					else {
						candidate
						= getTextNodeThatContainsPartNumberDirectlyRecursively(
								nodeContainingPartNumber, part);
						if (candidate != null) {
							// found => have to bring our found now
							// back to the top level by level depth!
							return candidate;
						}
						//else continue the loop
//					}
				}
			}

		}

		// DOCX
		else if (org.docx4j.XmlUtils.unwrap(candidate)
				instanceof ContentAccessor) {

			List<Object> childNodes = ((ContentAccessor)
					org.docx4j.XmlUtils.unwrap(candidate))
				.getContent();
			if (childNodes == null) {
				return null;
			}
			childNodes_index = childNodes.size();
			while (--childNodes_index > -1) {
				Object nodeContainingPartNumber
					= childNodes.get(childNodes_index);
				if (nodeContainingPartNumber != null) {
//					if (nodeContainingPartNumber
//							instanceof org.docx4j.wml.Text) {
//						return nodeContainingPartNumber;
//						// <-that text node value now has to be adapted
//					}
//					else {
						candidate = getTextNodeThatContainsPartNumberDirectlyRecursively(
								childNodes.get(childNodes_index), part);
						if (candidate != null) {
							// found => have to bring our found now
							// back to the top level by level depth!
							return candidate;
						}
						//else continue the loop
//					 }
				}
			}

		}

		return null;

	}






	/**
	 * Using JODConverter for not having to restart a headless
	 * libreoffice instance for each conversion but to reuse
	 * one and the same instance throughout the program's execution.
	 */
	public static OfficeManager officeManager;

	public static OfficeDocumentConverter officeConverter;


	public static boolean openConversionInstance() {

		officeManager = new DefaultOfficeManagerConfiguration()
				//.setOfficeHome(System.getProperty("file.separator")
				//+ "usr"+ System.getProperty("file.separator") +"lib"
				//+ System.getProperty("file.separator")
				//+"libreoffice")// <- I hope it simply uses
				//libreoffice without this being specified.
				//.setConnectionProtocol(OfficeConnectionProtocol.PIPE)
				//.setPipeNames("officeinstance1_open"
				//		, "officeinstance2_libre")
				.setTaskExecutionTimeout(30000L) // 30 seconds max exec
				.buildOfficeManager();

		officeManager.start();
		boolean all_went_fine = officeManager.isRunning();
		if (!all_went_fine) {
			return false;
		}
		officeConverter = new OfficeDocumentConverter(officeManager);
		return true;
	}



	/**
	 * JOD Converter: Convert using Oepn/LibreOffice.
	 * @param source Input file.
	 * @param target Target file.
	 */
	public static boolean convert(File source, File target) {
		if (!source.exists()) {

			Global.tidyUp();

			throw new IllegalArgumentException("Source file given as"
					+ " parameter does not exist: "
					+ source.toString());

		}
		if (!target.exists()) {
			// warn
			System.out.println(
					Global.addMessage(
					"Target file already exists. Overwrite? "
					+ target.toString()
					+ "\r\nPerforming default action. -> overwriting"
					+ " target file ..."
					, "warning")
			);
		}
		/*
		check if the conversion has been initiated already. This
		allows to simply call this method to not have to waste
		the memory for creation of those instances even they are
		not required.
		*/
		if (officeConverter == null) {
			if (!openConversionInstance()) {

				/*
				fallback 1: try any other converter, could end up
				using multiple threads too. Nevertheless the goal
				is to have a Java-only method for each conversion
				in Global.convertFile(). TODO
				*/
				try {
					Global.convertFile(
							source.getAbsolutePath()
							, Global.extractEnding(
								target.getAbsolutePath())
							, target.getAbsolutePath()
					);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
					// initiate fallback to multiinstance office command.
				}

				return true;

			}
		}

		officeConverter.convert(source, target);

		// tidy up .. best to call in all exceptions!
		/*Global.tidyUp(); <- don't call it here as then it makes
		no sense to use JODConverter at all as it's for reusing
		the same instance. => Don't close the instance.
		*/
		return true;
	}



	public static void closeConversionInstance() {

		if (officeManager != null) {
			officeManager.stop();

		}

	}



	/**
	 * tidy up .. best to call whenever an exception occurs!
	 */
	public static void tidyUp() {

		// all programs that have to be stopped.
		closeConversionInstance();
		/*...*/


	}



	/**
	 *
	 * @param bArray byte array.
	 * @return char array.
	 */
	public static char[]  byteToCharArray(byte[] bArray) {
		char[] cArr = new char[bArray.length];
		int i = -1;
		while (++i < bArray.length) {
			/* http://stackoverflow.com/questions/17912640/byte-and-char-conversion-in-java
			 * A character in Java is a Unicode code-unit which is
			 * treated as an unsigned number.
			 * So if you perform c = (char)b the value you get is
			 * 2^16 - 56 or 65536 - 56.
			 * To get the right point use char c = (char) (b & 0xFF)
			 * which first converts
			 * the byte value of b to the positive integer 200.
			 */
			cArr[i] = (char) (bArray[i] & 0xFF);
			// Is code unit, not code POINT.
			// Use charAtCodePoint instead of charAt?
		}
		return cArr;
	}



	/**
	 * It's a total reset function to the initial beginning (0).
	 * Unlike the normal <stream>.reset which is reset to a marked
	 * marked starting point (depending on mark this then may or not
	 * be the absolute beginning unlike this total reset that ensures
	 * to reset to 0 on cost of losing the marked position in the
	 * stream).
	 * http://stackoverflow.com/questions/18793840/java-resetting-inputstream
	 */
	public boolean resetStreamToInitialBeginning(
			/*apache Counting*/InputStream stream) {

		if (stream.markSupported()) {
			// This limit is okay for FileInputStream but for
			// in-memory buffers that might allocate 2GB it will crash!
			// First mark the reset point into 'nowhere'(?).
			stream.mark(Integer.MAX_VALUE);
			// TODO =>use BufferedInputStream. (StringBuilder or
			// for binary: ByteArrayOutputStream).
			// Note: The BufferedInputStream copy variant will only
			// work if enough memory available!!
			// If enough memory then:
			try {
				byte[] bytes = IOUtils.toByteArray(stream);
				// <--this can now be reused as often as wished.
				// (but may need too much RAM)
				stream.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}



	/**
	 *
	 * @param stream
	 * @throws IOException
	 * http://stackoverflow.com/questions/3324857/javas-datainputstream-readutf-and-number-of-bytes-read
	 */
	public void readBytewiseWithFeedback(DataInputStream stream)
		throws IOException {

//		buffer = new byte[length];
//		/*DataInputStream*/in.readFully(buffer);
//
		int length = stream.available();//TODO:length;//available()
		/* at least those bytes that can be read unblocked in a row. */

		byte[] buffer = new byte[length];
		int totalRead = 0;
		int percentage = 0;
		int bytesLeft = length;

		while (totalRead < length)
		{
			bytesLeft = length - totalRead;

			int bytesRead = stream.read(
					buffer, totalRead, (length - totalRead));

			if (bytesRead < 0)
				throw new IOException("Data stream ended prematurely");

			totalRead += bytesRead;
			double progress = ((totalRead*1.0) / length) * 100;
			if ((int)progress > percentage)
			{
				percentage = (int)progress;
				System.out.println("Downloading: " + percentage + "%");
			}
		}


	}



	/**
	 * Had to be written (isFile) to allow for check for target
	 * filelinks that might not yet exist.
	 * Otherwise  new File().isFile() always checks for existence
	 * which fails because target filelink might not exist.
	 * This method works around this issue by checking for the parent
	 * directory to exist.
	 * TODO This might still be a limitation.
	 *
	 * @param filelink
	 * @return
	 */
	public static boolean isFile(String filelink) {
		return isFile(new File(filelink));
	}

	public static boolean isFile(File dirOrFile) {
		return !(dirOrFile.getParentFile().isDirectory());
	}






	/**
	 */
	public static String resolveCorrespondingFilelinkTo(String part_filelink) throws IOException, SQLException {

		String sql;

		// 0. Determine metadata:
		String semester
			= Global.extractSemesterFromFilelink(part_filelink);
		String course
			= Global.extractCourseFromFilelink(part_filelink);
		String lecturer
			= Global.extractLecturerFromFilelink(part_filelink);
		String type
			= Global.extractTypeFromFilelink(part_filelink);

		// 1. Determine mothersheet:
		String mothersheetFilelink = Global.extractFilelinkOfMothersheet(part_filelink);
		String mothersheetFilename = Global.extractFilename(mothersheetFilelink);

		/*
		First guess: all equal but the splitby criteria (pattern).
		 */
		String partPart
			= Global.extractPartPartOnlyFromPartFilelink(
					part_filelink);
		String splitby
			= Global.extractSplitByFromFilelink(part_filelink);
		Muster part_split_pattern
			= Muster.getMusterByName(splitby);

		for (Muster m : Muster.values()) {

			// One is solution pattern the other part pattern?
			if (part_split_pattern.isSolutionPattern()
					!= m.isSolutionPattern()) {
				// => then this might be the corresponding filelink.

				// Don't skip this pattern m because it's not the
				// same (impossible any TODO examine)?
//				if (!m.name().equals(splitby)
//						|| !m.equals(part_split_pattern)) {
					String candidate = mothersheetFilelink + "__"
						+ partPart.replaceAll(splitby, m.name());
					if (new File(Global.root + candidate).exists()) {
						return candidate;
						// <- best bet for the corresponding filelink.
					}
//				}
			}

		}
		System.out.print("Could not find corresponding"
				+ " part/solution filelink on the first guess.");


		/*
		Next guess: (General solution)
		Only assumption: Part sheet and corresponding solution
		sheet filename contain the same number.
		*/
		// 2. Type is part_solution or exam_solution ?
		// (see Global.sheetTypes)
		String target_type; target_type = type.replace("_SOLUTION", "");
		// <- result:EXAM or PART

		target_type = type.replace("_solution", "");
		// <- result:exam or part

		// Has already been EXAM or PART?
		if (type.equals(target_type)) {
			target_type = type + "_SOLUTION";
			//target_type = type + "_solution";
		}

		String mothersheetFilelink_with_target_type
			= mothersheetFilelink.replaceFirst(type, target_type);
		for (Muster m : Muster.values()) {

			// One is solution pattern the other part pattern?
			if (part_split_pattern.isSolutionPattern()
					!= m.isSolutionPattern()) {
				// => then this might be the corresponding filelink.

				// Don't skip this pattern m because it's not the same (impossible any TODO examine)?
//				if (!m.name().equals(splitby)
//						|| !m.equals(part_split_pattern)) {
					String candidate
						= mothersheetFilelink_with_target_type + "__"
						+ partPart.replaceAll(splitby, m.name());
					if (new File(Global.root + candidate).exists()) {
						return candidate;
						// <- best bet for the corresponding filelink.
					}
//				}
			}

		}
		System.out.print("Could not find corresponding"
				+ " part/solution filelink by adapting the"
				+ " on upload selected sheet type and the above"
				+ " splitby variation.");


		/*
		Next guess: Involve the database.
		 */
		String correspondingFilelinkGuess;
		String firstMatch = Global.uploadTarget + semester
			+ System.getProperty("file.separator")
			+ course + System.getProperty("file.separator")
			+ lecturer + System.getProperty("file.separator")
			+ target_type + System.getProperty("file.separator");

		correspondingFilelinkGuess = firstMatch + mothersheetFilename;
		//mothersheetFilelink.replace(System.getProperty("file.separator")
		//		+ type + System.getProperty("file.separator")
		//		, System.getProperty("file.separator") + target_type
		//		+ System.getProperty("file.separator"));

		// 3. Try to get a number out of the mothersheet's filename.
		String mothersheetFilename_semesterRemoved
			= mothersheetFilename.replaceAll("[wWsS][sS][0-9]+", "");
		int part_sheet_number = Global
			.getInt(mothersheetFilename_semesterRemoved);

		if (part_sheet_number != Integer.MAX_VALUE) {

			String secondMatch = part_sheet_number + "";
			if (Global.debug) {
				System.out.println("Figured number label of sheet to be "
						+ part_sheet_number);
			}
			// Select all parts to this mothersheet:
			sql = "SELECT filelink FROM part"
				+ " WHERE sheetdraft_filelink LIKE '"
				+ firstMatch + "%'"
				+ " AND sheetdraft_filelink LIKE '%" + secondMatch + "%'";
			// This should match e.g. 'Loesung to part1' too.


		}
		else {

			System.out.println("Mothersheet is not indicated by a"
					+ " number. Now trying to find a sheet that"
					+ " is equal but has either a '-lsg' or"
					+ " '-solution' appended or not.");
			/*
			Specific solution:
			Assumption: An part sheet may be called
			part_8_to_course.<ending>.
			Then the solution must be indicated by appending
			Lsg or solution.
			*/
			// After the first match we only have parts from
			// the same semester, course & lecturer.
			sql = "SELECT filelink FROM part"
				+ " WHERE sheetdraft_filelink LIKE '" + firstMatch + "%'"
				+ " AND (";

			correspondingFilelinkGuess = mothersheetFilename
				.replaceAll("[-_](solution|[lL]sg|[lL]oesung|[lL]oes)"
						, "");
			// Nothing has changed? So there was no
			// lsg loesung solution, ... appended?
			if (mothersheetFilename.equals(correspondingFilelinkGuess)
					) {
				sql += " sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "-Lsg%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "-lsg%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "-Loes%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "-loes%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "-Loesung%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "-loesung%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "-Solution%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "-solution%'"

					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "_Lsg%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "_lsg%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "_Loes%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "_loes%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "_Loesung%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "_loesung%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "_Solution%'"
					+ " OR sheetdraft_filelink LIKE '%"
					+ mothersheetFilename + "_solution%'"
				;

			}
			else {

				sql += " sheetdraft_filelink LIKE '%"
					+ correspondingFilelinkGuess + "%'";

			}

			sql += ")";

		}

		String correspondingPartFilelink = null;
		ResultSet resForCorrespondingCandidates = Global.query(sql);
		List<String> correspondingCandidates = new ArrayList<String>();
			int partPartNumber = Global.getInt(
				Global.extractPartPartOnlyFromPartFilelink(
					part_filelink)
		);
		while (resForCorrespondingCandidates.next()) {

			String candidateFilelink
				= resForCorrespondingCandidates.getString("filelink");
			String candidateFilename
				= Global.extractFilename(candidateFilelink);
			String candidatePartPart
				= Global.extractPartPartOnlyFromPartFilelink(
						candidateFilelink);

			int candidatePartPartNumber
				= Global.getInt(candidatePartPart);
			if (candidatePartPartNumber == partPartNumber) {
				correspondingCandidates.add(candidateFilelink);
			}

		}
		// Tackle memory leaks by closing result set, its statement:
		Global.queryTidyUp(resForCorrespondingCandidates);

		int correspondingCandidates_index = correspondingCandidates.size();
		if (correspondingCandidates_index > 1) {
			if (Global.debug) {
				System.out.println("Too many corresponding parts"
						+ " found. There is no similarity algorithm"
						+ " yet. So we take the first candidate.");
			}
		}

		// As it's possible that the number is not enough we also
		// check the other parts (splitter).
		while (--correspondingCandidates_index > -1
				&& correspondingPartFilelink == null) {

			String candidateFilelink = correspondingCandidates
				.get(correspondingCandidates_index);
			//String candidateFilename = Global
			//	.extractFilename(candidateFilelink);
			String candidatePartPart = Global
				.extractPartPartOnlyFromPartFilelink(
						candidateFilelink);

			/*
			At this point the candidatePartPartNumber and
			the partPartNumber are equal.
			*/
			String[] parts
				= candidatePartPart.split(partPartNumber + "");

			String candidatePartSplitter
				= Global.extractSplitByFromFilelink(candidateFilelink);
			String partSplitter
				= Global.extractSplitByFromFilelink(part_filelink);

			if (candidatePartSplitter.equals(partSplitter)) {
//				correspondingPartFilelink = candidateFilelink;
				return candidateFilelink;
			}

		}



		/*
		TODO .. this could be done with a loop around the first
		two cases. Still I'm not sure if this doesn't create
		issues as it surely does if e.g.
		sheet1__Ex_1__splitby_INTDOT.odt.odt and
		sheet1__Ex_1__splitby_INTCHAR.odt.odt.
		So I think this will give incorrect results.
		Next guess: (now the split criteria doesn't matter anymore,
		we pick the next best that is not us and has the same ending.)
		Assumption: Part sheet and corresponding solution sheet
		filename contain the same number.
		And Part number/position is equal too.
		*/
		// IN PRINCIPLE EQUAL TO FIRST GUESS BUT MORE TOLERANT.
//		String candidate = mothersheetFilelink + "__"
//			+ partPart.replaceAll("", "");
//		for (Muster m : Muster.values()) {
//
//			// One is solution pattern the other part pattern?
////			if (part_split_pattern.isSolutionPattern()
////						!= m.isSolutionPattern()) {
//				// => Then this might be the corresponding filelink.
//
//				// Don't skip this pattern m because it's not the
//				// same (impossible any TODO examine)?
//				if (!m.name().equals(splitby)
//						|| !m.equals(part_split_pattern)) {
//
//					candidate = mothersheetFilelink + "__"
//						+ partPart.replaceAll(splitby, m.name());
//					if (new File(Global.root + candidate).exists()) {
//						return candidate;
//						// <- best bet for the corresponding filelink.
//					}
//				}
////			}
//
//		}
//		System.out.print("Could not find corresponding"
//		+ " part/solution filelink if all equal but the split criteria.");
//




		/*
		Next guess: (find system wide by searching either in the
		filesystem or in the database. Generic.)
		Assumption: Part sheet and corresponding solution sheet
		filename contain the same number.
		And Part position number is equal too.
		*/
		int part_number
			= Global.extractPartNumberFromFilelink(partPart);
		// It's been extracted anyway and yet we give the correct part.
		char mothersheet_number = '0';
		if (Global.isInt(mothersheetFilename)) {
			mothersheet_number = ("" + Global
					.getInt(mothersheetFilename)).charAt(0);
			// <- leading zeros are away anyway.
		}
//		int number_position = mothersheetFilelink
//			.lastIndexOf(mothersheet_number);
//		String mothersheetFilename_part = mothersheetFilename;
//		if (number_position > char_count / 2 - 1) {
//			// remove the last part from the number on:
//			mothersheetFilename_part = Global
//				.replaceEnding(mothersheetFilename
//						.substring(number_position + 1) + "$", "");
//		}
//		else if (number_position < char_count / 2) {
//			// remove the first part until the number is met:
//			mothersheetFilename_part
//				= Global.replaceEnding(mothersheetFilename
//				.substring(0, number_position - 1), "");
//		}
//		sql = "SELECT filelink FROM part WHERE filelink LIKE '%"
//			+ TODO + "'";


		String sheet_double_ending
			= Global.extractEndingDouble(mothersheetFilelink);
		String part_double_ending
			= Global.extractEndingDouble(part_filelink);
		//.*<sheet_number>.*<ending>[.]<ending>__Part_<part_number>.*[.]<ending>[.]<ending>
		sql = "SELECT filelink FROM part"
			+ " WHERE filelink <> '" + part_filelink + "'"
			+ " AND filelink REGEXP '.*" + mothersheet_number + ".*"
			+ sheet_double_ending + "__Part_" + part_number
			+ ".*" + part_double_ending + "$'";

		ResultSet res_corresponding_regexp = Global.query(sql);
		while (res_corresponding_regexp != null
				&& res_corresponding_regexp.next()) {

			String candidate
				= res_corresponding_regexp.getString("filelink");
			System.out.print("SQL Regex found: " + candidate);
			// Is at least the type or the filename different?
			// Otherwise it could be just another semester, and that
			// isn't the corresponding file then but a redundant double
			String candidate_type
				= Global.extractTypeFromFilelink(candidate);
			if (/*!candidate.equals(part_filelink)
			      <--already checked for in database query. &&*/
					!candidate_type.equals(type)
					&& !Global.extractFilename(candidate)
					.equals(Global.extractFilename(part_filelink))
					) {
				return candidate;
			}

		}


		return correspondingPartFilelink;

	}



	/**
	 * Counts the occurrences of word in the given array of string.
	 *
	 * @param text String[]
	 * @param phrase String
	 * @return count of phrase
	 */
	public int getPhraseCount(String[] text, String phrase) {

		int count = 0;
		String line = "";
		int phraseL = phrase.length();
		if (phraseL < 1) {
			System.out.println("Phrase was of length < 1: " + phrase);
			return 0;
		}
		for (int i = 0; i < text.length; i++) {
			line = text[i];
			int lineL = line.length();
			if (lineL != 0) {
				int searchStartIndex = 0;
				int index = line.indexOf(phrase, searchStartIndex);
				while (index != -1) {
					count++;
					searchStartIndex = index + 1;
					if (searchStartIndex > lineL - 1) {
						break; // inner loop
					}
					// Search for more occurrences:
					index = line.indexOf(phrase, searchStartIndex);
				}
			}
		}
		return count;
	}



	/**
	 * Get number of phrase occurrences per line in a map.
	 *
	 * @param text array of String
	 * @param phrase what to search
	 * @return map of line number to number of occurrences of
	 * phrase within this line.
	 */
	public HashMap<Integer, Integer> getPhraseCountPerLine(
			String[] text, String phrase) {

		Map<Integer, Integer> phraseCountPerLine
			= new HashMap<Integer, Integer>();

		System.out.println("getPhraseCountPerLine: " + phrase);
		int[] r = new int[text.length];
		int phraseCountTotal = 0;

		for (int i = 0; i < text.length; i++ ) {
			r[i] = 0;
			if (text[i] != null) {
				int lineL = text[i].length;
				int searchStartIndex = 0;
				int index = text[i].indexOf(phrase);
				while (index != -1) {
					r[i]++;
					searchStartIndex = index + 1;
					if (searchStartIndex > lineL - 1) {
						break; // inner loop
					}
					index = text[i].indexOf(phrase, searchStartIndex);
				}
				phraseCountTotal += r[i];
				System.out.println("#phrase@line " + i + ": " + r[i]);
			}
		}

		System.out.println("Total count: " + phraseCountTotal);
		for (int i = 0; i < r.length; ++i) {
			if (r[i] < 1) {
				continue;
			}
			phraseCountPerLine.push(i, r[i]);
		}

		return phraseCountPerLine;
	}



	/**
	 * Get an array of line numbers where phrase occurs at least once.
	 *
	 * @param text array of String
	 * @param phrase to search
	 * @return array of line numbers
	 */
	public int[] getLineNumbersContaining(String[] text, String phrase) {

		System.out.println("getLineNumbersContaining: " + phrase);
		int[] r = new int[text.length];
		int lineContainingCount = 0;

		for (int i = 0; i < text.length; i++ ) {
			if (text[i] != null) {
				if (text[i].indexOf(phrase) != -1) {
					r[lineContainingCount] = i;
					// To not overwrite the stored index|line number:
					lineContainingCount += 1;
				}
			}
		}

		System.out.println("#lines containing the phrase: "
				+ lineContainingCount);
		int[] lineNumbersContaining = new int[lineContainingCount];
		for (int i = 0; i < lineContainingCount; ++i) {
			lineNumbersContaining[i] = r[i];
		}

		return lineNumbersContaining;
	}



}

