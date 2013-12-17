package aufgaben_db;


import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.lucene.index.IndexReader;

import HauptProgramm.DocType;



import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import swp.MysqlHelper;
import java.sql.Connection;
import swp.SQL_Methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//import org.apache.catalina.core.ApplicationContext;


/**
 * GLOBAL
 * @author Jan R. Balzer-Wein
 * 
 * Contains global variables and functions which are accessible from ALL files. That means,
 * not only in the file, the <!% %> is located in.  (that was, what i tried before)
 */
public class Global {
	
	
	//======= GLOBALS - ATTRIBUTES ===============================================//
	//GLOBALS - DECLARE
	public static String version = "v30.93";
	public static enum SheetTypes { EXERCISE_SHEET, SOLUTION, EXAM, EXAM_SOLUTION };
	public static enum SUPPORTED_FILETYPES { DOCX, DOC, /*HTML,*/ ODT, PDF, RTF, TEX, TXT };
	public static String imageTypeToGenerate = "png";
	public static final int charactersPerLine = 80; //used for image generation out of native, plain text.
	public static int ImageMagick_qualityScale = 1;
	public static int ImageMagick_density = 150;//144 
	public static String[] indexedFields = { "content", "type", "course", "semester"
			, "lecturer", "author", "filelink" };
	public static String[] semterm = { "SS", "WS" };
	public static boolean debug = true;
	public static boolean cropImages = false;//TODO deactivate if image or formula encountered.
											 //because plain text line count not takes it into account.
	public static String anzeige = "start";
	public static String id = "start";	//former aktion replacement
	
	public static String message = "";
	public static String messageClass = "neutral";
	//out of e.g. default, success, failure
	
	public static String root; //root - to be written once at startup
	public static String uploadTarget = "uploads/";
	public static HttpSession session;
	public static IndexReader indexReader;
	public static Calendar now = new GregorianCalendar();
	
	
	//MySQL attributes
	public static MysqlHelper msqh = new MysqlHelper();
	public static Connection conn;
	public static SQL_Methods sqlm = new SQL_Methods();
	
	//Response -- TODO is this individual for each jsp-File?
	//public static HttpServletResponse request;
	public static HttpServletResponse response;
	
	//SETTINGS - DEFAULTS
	public static Language LANGUAGE = Language.getByName("German");//non-final as it can be changed
	

	
	
	//======= GLOBALS - FUNCTIONS ===============================================//
	public static String toFairy(String english) {
		return display(english);
	}
	public static String display(String english) {
		String translated = translate(english);
		if (translated == null) {
			System.out.println(
					Global.addMessage("Found no translation for " + english + " .", "danger")
			);//prevents the server of a null pointer exception and prevents showing emtpy fields.
			return english.substring(0, 1).toUpperCase() + english.substring(1).replace("_", " ");
		}
		return translated.substring(0, 1).toUpperCase() + translated.substring(1).replace("_", " ");
	}
	public static String translate(String english) {
		return Global.LANGUAGE.getTranslation(english);
		//return Language.translations[Global.LANGUAGE.ordinal()].get(english);
	}
	public static boolean isLoggedIn(HttpSession session) {
		return session.getAttribute("user") != null;
	}
	public static String getUser(HttpSession session) {
		return session.getAttribute("user").toString();
	}
	public static String getUserURLEncoded(HttpSession session)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(session.getAttribute("user").toString(), "utf-8");
	}
	/*menueItem, page to be re-defined/overwritten/adapted somewhere
	     previously (mostly immediately before include of this scriptlet)*/
	//protected seems to be the default visibility
	public static String getSupportedFiletypesAsString() {
		int i = 0;
        String docTypesSupported = "";
		for (DocType docType : DocType.values()) {
			if (i > 0) {
				docTypesSupported += ", ";
			}
			docTypesSupported += docType.name();//.toString();<-- will have .<ending>
			i++;
		}
		return docTypesSupported;
	}
	//Index reader (especially for multiple interrupts/threads to have it volatile is important).
	public static void setIndexReader(IndexReader indexReader) {
		Global.indexReader = indexReader;
	}
	public static IndexReader getIndexReader() {
		return Global.indexReader;
	}
	
	/**
	 * BUILD PATH TO filelink -- a convention
	 * @param semester
	 * @param course
	 * @param lecturer
	 * @param type
	 * @return the path to the file, the basename dir or better: parent directory.
	 */
	public static String buildPathTo(String semester, String course
			, String lecturer, String type) {
		return Global.encodeUmlauts(semester) + "/"
                + Global.encodeUmlauts(course) + "/"
                + Global.encodeUmlauts(lecturer) + "/"
                + Global.encodeUmlauts(type) + "/";
	}
	
	
	public static boolean isFilelinkNativeFormat(String filelink) {
		//For ease of debugging store it in a local variable first.
		String last_ext_hence_current_format = Global.extractEnding(filelink);
		return getNativeFormatEnding(filelink).equals(last_ext_hence_current_format);
	}
 
	public static String extractFilelinkOfMothersheet(String filelink) {
		return filelink.replaceAll(filelink.substring(filelink.indexOf("__Exercise")) + "$", "");
	}
	
	public static String getNativeFormatEnding(String filelink) {
		//Get the stored ending .origext out of .origext.currentext.
		String candidate;
		String[] parts = filelink.split("[.]");
		/*1) If an exercise has a double ending at the end:
		 *   Then a .tex exercise results from a .originalext$ EXERCISE.*/
		candidate = parts[parts.length - 2];
		if (candidate.length() < 5) {/*No supported filetype with more than 4 digits.*/
			return candidate;		 /*Furthermore the filenames are much longer as they
									   obtain splitby data, exercise position/number, ...*/
		}
	
		/*2) If an exercise has no double ending at the end:
		 *   Then a .tex exercise results from a SHEETDRAFT .docx|ext.TEX.*/
		parts = filelink.split("[.]" + parts[parts.length - 1]);
		parts = parts[0].split("[.]");/* There may be many dots before in e.g. splitby definitions/hints et alia.*/
		candidate = parts[parts.length - 1];/*However as only the last is taken it's okay.*/
		//This is bonus only for this case.
		if (candidate.length() < 5) {/*No supported filetype with more than 4 digits.*/
			return candidate;		 /*Furthermore the filenames are much longer as they
									   obtain splitby data, exercise position/number, ...*/
		}
		

	
		
		//-----------------------------------------------------------------------------//
		//Continue searching within our valid file extensions list if nothing found so far. 
		//-----------------------------------------------------------------------------//
		
		
		//Compare the stored ending __ext to the .ext at the end of filelink.
		String value = null;
		
		//Extract them:
		int __ext_in_filelink_index = -1;
		int filetypes_index = -1;
		int filetypesL = Global.SUPPORTED_FILETYPES.values().length;
		//
		//First iterate over all prefixes because the last option "_" has to 
		//be tested as final emergency solution. "_" is vulnerable for errors!
		String[] prefixes = {"__", "-", "_"};
		String prefix_successful = null;
		for (String prefix : prefixes) {
			
			//give this prefix a try with all endings
			
			while (__ext_in_filelink_index == -1 && ++filetypes_index < filetypesL) {
			
				//give it a try, find this ending.
				__ext_in_filelink_index = filelink.indexOf(
					prefix + Global.SUPPORTED_FILETYPES.values()[filetypes_index].toString()
				);
			}
		
			
			if (__ext_in_filelink_index > 0) { /* != -1 */
				prefix_successful = prefix;
				break;
			}
			
		}
	
		/*success?*/
		int index_start = __ext_in_filelink_index;
		if (index_start == -1) {
			//no original ending could be found
			Global.addMessage("No original fileending could be found. Assuming it"
					+ " is in the native file format.", "warning");
			
			//cancel further analysis
			//return true;
			return Global.extractEnding(filelink); //this will result in null.
		}
		
		
		
		// PART 3
		//- compare found original extension against the fileending
		String substring_beginning = prefix_successful;
		
	
		/*At this point we have the start index of the stored original ending.*/
		//String[] parts = filelink.split("__");
		int index = index_start;
		int substring_beginning_index = 0;
		int substring_beginning_L = substring_beginning.length();
		/*beginning and termination of the substring where __varname_value__ will be*/
		String termination_buffer = "";
		boolean terminatingUnderscoreOccurredOnce = false;
		while (index < filelink.length()) {
			char ch = filelink.charAt(index);
	//		if (Character.isLetter(ch)) {
				//Because this way no index out of bounds error will show:
				if (substring_beginning_index >= substring_beginning_L
						|| ch != substring_beginning.charAt(substring_beginning_index)) {
					//reached the end?
					if (ch == '_' || ch == '.' || ch == '-') {/*. is 2nd termination criteria*/
						if (terminatingUnderscoreOccurredOnce || ch == '.' || ch == '-') {
							//The 2nd time in series an underscore. => STOP.-
							//terminate this while loop, we have reached the end of the splitter
							break;
						}
						termination_buffer += "_";
						terminatingUnderscoreOccurredOnce = true;
					} else {
						//Last was a single _ and not __, so the ending buffer stands
						//for a space, so we have to add it to the splitter/split_by.
						value += termination_buffer;
						//Clear the ending buffer as it has been an ordinary space!
						termination_buffer = "";
						//Finally add the current char, too. Easy to forget that.
						value += ch;
					}
	//			}
			}
			index++;
			substring_beginning_index++;
		
		}
		//if those are equal, it is in native_format:
		//return (value == last_ext_hence_current_format);
		return value;
	}
	
	
	/**
	 * query
	 * 
	 * @param query			- the query to execute
	 * @param request       - additional debug context | null 
	 * @param pageContext   - additional debug context | null
	 * @return The result (set) of the database query.
	 * @throws IOException 
	 */
	public static ResultSet query(String query) throws IOException {
		
		return query(query, null, null);
		
	}
	public static ResultSet query(String query, String information_where_this_method_was_called) throws IOException {
		
		return query(query, null, null, information_where_this_method_was_called);
		
	}
	public static ResultSet query(String query, HttpServletRequest request, PageContext pageContext)
			throws IOException {
		return query(query, request, pageContext, "no_callee_information_given");
	}
	public static ResultSet query(String query, HttpServletRequest request, PageContext pageContext,
			String information_where_this_method_was_called)
			throws IOException {
		try {
//			String str_query = "SELECT *, MATCH (inhalt) AGAINST ('\"" + search_string + "\"') AS score FROM blatt WHERE MATCH (inhalt) AGAINST ('\"" + search_string + "\"' in boolean mode);";
//			//OHNE BOOLEAN MODE : ES WEREDN KEINE ERGEBNISSE ANGEZEIGT WENN SCORE KLEINER ALS 0.5 IST
			if (conn == null) {
				addMessage("conn in query was null -> new instance created ","warning", request, pageContext);
				conn = msqh.establishConnection(null);
			}
			Statement statement = conn.createStatement();
			if (statement == null) {
				addMessage("Statement could not be created. Is the mysql service started?"
							+ " Probably it has to be reinstalled if not."
						+" Save mysql-databases before!", "nosuccess");
				return null;
			}
			//execute
			ResultSet res = null;
			boolean updateInsertDeleteHappened = false;
			//DELETE INSERT UPDATE?
			boolean insert = false;
			boolean update = false;
			if (query.contains("DELETE ") || (insert = query.contains("INSERT ")) || (update = query.contains("UPDATE "))) {
				String operation = "Deleted ";
				if (insert) {
					operation = "Inserted ";
				}
				else if (update) {
					operation = "Updated ";
				}
				updateInsertDeleteHappened = true;
				//execute
				int rowCountOrNothingToReturn = statement.executeUpdate(query);
				if (rowCountOrNothingToReturn == 0) {
					addMessage("No result to show for query:\r\n<br/>"
							+ query,"nosuccess", request, pageContext);
//					System.out.print(message);
				}
				else {
					System.out.print(
							addMessage(operation + rowCountOrNothingToReturn + " rows.\r\n<br/>"
									+ query,"success", request, pageContext)
					);
				}
				
			}
			//SELECT
			else {
				//execute
				res = statement.executeQuery(query);
				if (res == null) {
					addMessage("Result set is null.\r\n","nosuccess", request, pageContext);
//					System.out.print(message);
				}
				else if(!res.next()) {
			    	addMessage("Es konnten keine Eintraege gefunden werden.\r\n<br />"
			    			+ "There were no entries matching this query: "
			    			+ query + " #Global.java__LINE__243#" + information_where_this_method_was_called, "nosuccess", request, pageContext);
			    	System.out.print(message);
			    }
				res.beforeFirst();//Because above we already increase by one!!
			}
			
			
		    //finally
			//statement.close(); => This would render the ResultSet useless.
		    return res;
		  
		} catch (SQLException e) {
			
		    e.printStackTrace();
		}
		
		//that's the case that is error prone -> check if null at caller 
		return null;
		
	}

	
	
	
	     
	/**
	 * getIfActive
	 * 
	 * @param session
	 * @param menueItem
	 * @return
	 */
	//GET IF ACTIVE
	public static String getIfActive(HttpSession session, String menueItem) {
	    String page_ = "";
	    if (session.getAttribute("anzeige") != null) {
	        page_ = session.getAttribute("anzeige").toString();
	    }
	    if ( page_.equals(menueItem) || page_ == menueItem + ".jsp"
	        || (page_ == "" && (menueItem == "start" || menueItem == "start.jsp")) ) {
	        return " btn-info active ";
	        //out.print(" active");
	    } 
	    return "";
	}



	/**
	 * filemtime 
	 * 
	 * @param session
	 * @param filename
	 * @return
	 */
	//modified and this filemtime added by jan
	//FILEMTIME (php equivalent) - used in the page footer
	public static String filemtime(HttpSession session, String filename,
			PageContext pageContext) {

	    return Global.filemtime(session, filename, "/", "d.M.y, H:mm z", pageContext);//webapps/aufgaben_db/
	           
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
	public static String filemtime(HttpSession session,
			String filename, String pathTo, String dateFormat, PageContext pageContext) {

		//path preparation
		String path = pathTo + "/" + filename;
		System.out.println("Path: " + path);
		String realPath = pageContext.getServletContext().getRealPath(path);
		System.out.println("Real path: " + realPath);
		
		//create a file from this path, hopefully there is a file, check later.
	    File file = new File(realPath);//already throwing an exception

	    //determine type of theoretical file
	    String fileType = "File";
	    if (file.isDirectory()) {
	        fileType = "Directory";
	    }
	    //file exists, i.e. found?
	    if (file.exists()) {
	        long timeInMs = file.lastModified();
	        java.util.Date date = new java.util.Date(timeInMs);
	        Format formatter = new SimpleDateFormat(dateFormat);
	        return formatter.format(date);
	    }
	    else {
	    	//
	    	String loggedInAddition = "";
	    	String loggedInPath = "";
            if (Global.isLoggedIn(session)) {
            	//nach dem login inkludiere start.in.jsp anstatt start.jsp;
            	//ID-Attribut wird im login.jsp gesetzt(Session)
            	//loggedInAddition = ".in";
            	loggedInPath = "";//"in/";
        	}
	        //add error to request for auto display in the index.jsp!
	        session.setAttribute("errors", fileType + " '" + filename + "' not found!");
	        Global.addMessage("An error occurred: " + fileType + " '" + filename + "' not found! "
	        		+ loggedInPath + filename + loggedInAddition , "nosuccess");
	        
	        return "";
	    }
	           
	}

	
	
	
	
	
	
	
	
	//======= GLOBALS - HELPER ==================================================//
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
	 * addMessage
	 * 
	 * @param message		-- The message to show in the interface.
	 * @param messageClass	-- The kind of message: success, nosuccess, info
	 * @param request		-- Additional context for debug purposes or null.
	 * @param pageContext   -- 			-"-
	 * @return The message again for chaining or cascading.
	 */
	public static String addMessage(String message, String messageClass) {
		return addMessage(message, messageClass, null, null);
	}
	/**
	 * @param message
	 * @param messageClass
	 */
	public static String addMessage(String message, String messageClass, HttpServletRequest request, PageContext pageContext) {
		Global.message += "<div class='"+ messageClass +"'>";
	    Global.message += message;
	    /* Extended debug mode? */
	    if (Global.debug) {
	    	/* Are there enough contexts given for extended message generation? */
	    	if (request != null || pageContext != null) {
	    		StackTraceElement thror = new Throwable().getStackTrace()[1];
	    		Global.message += "<span>[in file: "+ request.getRequestURI() +", compiled as: "
	    		        + pageContext.getPage().getClass().getName()
	    				+ ", requested by/coming from: " + request.getRequestURI() + " #line"
	    				+ thror.getLineNumber() +"]</span>";
	    		Global.message += "<span>Trace: file "+ thror.getFileName()
	    				+ " - class " + thror.getClassName()
	    				+ " - method " + thror.getMethodName()
	    				+ " - line " + thror.getLineNumber();
	    	} 
	    	else {
	    		Global.message += "<div class='info'>Extended debug message not possible (no"
	    	                     +" request given, i.e. collapsed 2-param-version called).</div>";
	    	}
	    }
	    /* Extended debug mode? - END */
   		Global.message += "</div>";
   		return message;
	}
	

	public static void determineRoot() {
		//better not use that as this is in a different directory than the main con!
	}
	
	public static String arrayToString(String[] array) {
		String str = "";
		for (String line : array) {
			str += line + "\r\n";
		}
		return str;
	}
	
	

	//REMOVE ROOT PATH alias
	public static String extractRelativePartOfFilelinkAtEndOnly(String filelink) {
		return removeRootPath(filelink);
	}
	
	//REMOVE ROOT PATH (returns relative path starting with the upload target directory!)
	public static String removeRootPath(String filelink) {
		
		return filelink.replaceFirst(Global.root, "");
		
	}

	public static String getImageLinkFromFile(String filelink) {
		
		return getImageLinkFromFile(filelink, Global.imageTypeToGenerate);
		
	}
	
	public static String getImageLinkFromFile(String filelink, String ending) {
		String[] parts = filelink.split("[.]");
		if (parts.length < 2) {
			addMessage("Filelink has no ending, after split it was null instead!"
					+ " (getImageLinkFromFile)<br />filelink = " + filelink, "success");
			return "ENDING WAS NULL";
		}
		String filelink_ending = parts[parts.length - 1];
		//String imagelink = filelink.replaceAll(filelink_ending + "$", ending);
		String imagelink = filelink + "." + ending;
		//TODO - optional:
		//Test if file exists here.
		return imagelink;
	}
	
	

	public static String getTxtLinkFromFilelink(String filelink) {
		String[] parts = filelink.split("[.]");
		if (parts.length < 2) {
			addMessage("Filelink has no ending, after split it was null instead!"
					+ " (getImageLinkFromFile)<br />filelink = " + filelink, "success");
			return "ENDING WAS NULL";
		}
		String filelink_ending = parts[parts.length - 1];
		String imagelink = filelink.replaceAll(filelink_ending + "$", "txt");
		//TODO - optional:
		//Test if file exists here.
		return imagelink;
	}
	
	

	//======= GLOBALS - ARTIOMS IMPORTANT Global =============================//
    public static void createImageFromText(String[] text, int width, String path,
    		String filename, String ext_desired_to_be_added) throws IOException {
		
		File file = new File(path);
		//IF A DIRECTORY
    	if (file.isDirectory() && !file.exists()) {
    		file.mkdirs();
    	}
    	//IF A FILE
    	else if (file.getParentFile() != null && !file.getParentFile().exists()) {
    		file.getParentFile().mkdirs();
    	}
		
		createImageFromText(text, width, path + filename + "." + ext_desired_to_be_added);
		
    }
    public static void createImageFromText(String[] text, int width, String imagelink)
    		throws IOException {
		
    	//String pathTo = imagelink.replaceAll(filelink_ending + "$", ending);
    	String pathTo = Global.extractPathTo(imagelink);
    	File filePath = new File(pathTo);
    	//IF A DIRECTORY
    	if (filePath.isDirectory() && !filePath.exists()) {
    		filePath.mkdirs();
    	}
    	//IF A FILE
    	else if (filePath.getParentFile() != null && !filePath.getParentFile().exists()) {
    		filePath.getParentFile().mkdirs();
    	}
    	
		Font font = new Font("Serif", Font.PLAIN, 12);
		
		int height = text.length * (font.getSize() + 10) ;
		//abhaengig von der schriftgroesse,anzahl der zeilen und abstand zwischen zeilen 10 un
		//@since 03. November 2013:
		if (text.length == 1) {
			height = Global.arrayToString(text).length() * (font.getSize() + 10) / Global.charactersPerLine;
		}
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

       //g2.setFont(new Font(arg0, arg1, arg2))
        g2.fillRect(0, 0, width, height);
        
        g2.setFont(font);
        g2.setColor(Color.black);
        int x = 20;
        int y = 20;
        
        for (int i = 0; i < text.length; i++) {
        	g2.drawString(System.getProperty("line.separator") + text[i], x, y += 20);

		}
        
        String ext_and_format = Global.extractEnding(imagelink).replaceFirst("^[.]", "");
        File outputfile = new File(imagelink);
        ImageIO.write(img, ext_and_format, outputfile);
	}
	
    
    /**
     * Deals with special characters and encodes those. 
     * @param request
     * @param param_key
     * @return The parameter value or null or better an empty string ("")?
     */
    public static String getParameterEncoded(HttpServletRequest request
    		, String param_key) {
    	return getParameterEncodedOrEmpty(request, param_key, null);
    }
    public static String getParameterEncodedOrEmpty(HttpServletRequest request
    		, String param_key, String param_key_alternative) {
    	String encoded = request.getParameter(param_key);
        // is null?
        if (encoded == null && param_key_alternative != null) {
            encoded = request.getParameter(param_key_alternative);
        }
        // still null?
        if (encoded == null) {
        	return null;//"";
        }
        //deal with special character (new as of v30.91) to fix that the search did not work
        try {
			encoded = new String(encoded.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        encoded = Global.encodeUmlauts(encoded);
        return encoded;
    }
	
	
	public static String encodeUmlauts(String str) {
		//str = str.toLowerCase();
		
		if(str.contains("ä")) {
			str = str.replaceAll("ä", "ae");
		}
		if(str.contains("ü")) {
			str = str.replaceAll("ü", "ue");
		}
		if(str.contains("ö")){
			str = str.replaceAll("ö", "oe");
		}
		if(str.contains("ß")) {
			str.replaceAll("ß", "ss");
		}
		if(str.contains("Ä")) {
			str = str.replaceAll("Ä", "Ae");
		}
		if(str.contains("Ü")) {
			str = str.replaceAll("Ü", "Ue");
		}
		if(str.contains("Ö")){
			str = str.replaceAll("Ö", "Oe");
		}
		if(str.contains("/")){
			str = str.replaceAll("/", "---"); /*ATTENTION CHANGED HERE!
			TODO KEEP TRACK OF POTENTIAL CONSEQUENCES IF THIS WAS USED SOMEWHERE!
			PREVIOUSLY THIS WAS REPLACED BY "_"*/
		}
		if(str.contains(" ")){
			str = str.replaceAll(" ", "_"); /*PREVIOUSLY THIS WAS replaced by empty""*/
		}

		return str;
	}
	
	//
	public static String decodeUmlauts(String str) {
		//str = str.toLowerCase();
		
		if(str.contains("ae")) {
			str = str.replaceAll("ae", "ä");
		}
		if(str.contains("ue")) {
			str = str.replaceAll("ue", "ü");
		}
		if(str.contains("oe")){
			str = str.replaceAll("oe", "ö");
		}
		if(str.contains("ss")) {
			str.replaceAll("ss", "ß");
		}
		if(str.contains("Ae")) {
			str = str.replaceAll("Ae", "Ä");
		}
		if(str.contains("Ue")) {
			str = str.replaceAll("Ue", "Ü");
		}
		if(str.contains("Oe")){
			str = str.replaceAll("Oe", "Ö");
		}
		if(str.contains("---")){
			str = str.replaceAll("---", "/");
		}
		if(str.contains("_")){
			str = str.replaceAll("_", " ");
		}

		return str;
	}
	
	public static boolean renameAllDerivativesOfFilelink(String filelink, String destination) {
		
		//Are the preconditions met? (Source and destination exist?) 
		File sourceFile = new File(Global.root + filelink);
		if (!sourceFile.exists()) {
			System.out.print(
				Global.addMessage("Source not exists: renameAllDerivativesOfFilelink."
					+ " Filelink = source = " + filelink + "\r\n<br />Operation aborted!"
					, "danger")
			);
			return false;
		}
		File destinationFile = new File(Global.root + destination);
		if (!destinationFile.exists()) {
			destinationFile = new File(Global.root + Global.extractPathTo(destination));
			if (!destinationFile.exists()) {
				System.out.print(
					Global.addMessage("Destination not exists: renameAllDerivativesOfFilelink."
						+ " Destination = target directory or file = " + destination + "\r\n<br />Operation aborted!"
						, "danger")
				);
				return false;
			}
		}
		
		String destination_directory_absolute = destinationFile.getAbsolutePath();
		if (!destinationFile.isDirectory()) {
			System.out.print("Destination given was a file, extracted parent directory of it. destination: "
					+ destinationFile.getAbsolutePath());
			destination_directory_absolute = destinationFile.getParentFile().getAbsolutePath();
		}
		
		//Try to delete all possible file derivations. Double endings ensure that
		//no other sheetdraft in the same directory is being deleted accidentially.
		String filelink_derivative;
		boolean all_derivatives_been_renamed = true;
		boolean all_images_been_renamed = true;
		for (DocType docType : DocType.values()) {
		
			//build hypothetical filelink of derivative
			filelink_derivative = filelink.replaceAll(
					"[.]" + Global.extractEnding(filelink) + "$"
					, /*"." +*/ docType.toString()
			);
			//build destination for derivative
			String destination_for_derivative = destination_directory_absolute
					+ System.getProperty("line.separator")
					+ Global.extractFilename(filelink_derivative)
					+ "." + Global.extractEnding(filelink_derivative);
			
			//RENAME DERIVATIVE
            if (Global.renameFile(Global.root + filelink_derivative, destination_for_derivative)) {
                System.out.print(
            		Global.addMessage("<p>Die Datei: <strong>" + Global.extractFilename(filelink_derivative)
            			     + " Flavour: ." + Global.extractEnding(filelink_derivative) + "</strong> wurde gelöscht.</p>"
            			     , "success")
           		);
            }
            else {
            	all_derivatives_been_renamed = false;
                System.out.print(
            		Global.addMessage("<p>Die Datei: <strong>" + Global.extractFilename(filelink_derivative)
            			    + "." + Global.extractEnding(filelink_derivative) +  "</strong> existiert nicht.</p>"
            			    , "nosuccess")
                );
            }
            
            //build destination for derivative's image
			String destination_for_derivative_s_image = destination_directory_absolute
					+ System.getProperty("line.separator")
					+ Global.extractFilename(Global.convertToImageLink(filelink_derivative))
					//+ "." + Global.extractEnding(Global.convertToImageLink(filelink_derivative));
					+ "." + Global.imageTypeToGenerate;
            
            //RENAME images to each derivative
            if (Global.renameFile(Global.root + Global.convertToImageLink(filelink_derivative), destination_for_derivative_s_image)) {
                System.out.print(
            		Global.addMessage("<p>Die Datei: <strong>" + Global.extractFilename(filelink_derivative)
            			     + " Flavour: ." + Global.imageTypeToGenerate + "</strong> wurde gelöscht.</p>"
            			     , "success")
           		);
            }
            else {
            	all_images_been_renamed = false;
                System.out.print(
            		Global.addMessage("<p>Die Datei: <strong>" + Global.extractFilename(filelink_derivative)
            			    + ".png </strong> existiert nicht.</p>"
            			    , "nosuccess")
                );
            }
            
		}
		
		return all_derivatives_been_renamed && all_images_been_renamed;
		
	}
	
	
	public static boolean deleteAllDerivativesOfFilelink(String filelink) {
		
		//Try to delete all possible file derivations. Double endings ensure that
		//no other sheetdraft in the same directory is being deleted accidentially.
		String filelink_derivative;
		boolean all_derivatives_been_deleted = true;
		boolean all_images_been_deleted = true;
		for (DocType docType : DocType.values()) {
		
			filelink_derivative = filelink.replaceAll(
					"[.]" + Global.extractEnding(filelink) + "$"
					, /*"." +*/ docType.toString()
			);
			//derivative
            if (Global.deleteFile(new File(Global.root + filelink_derivative))) {
                System.out.print(
            		Global.addMessage("<p>Die Datei: <strong>" + Global.extractFilename(filelink_derivative)
            			     + " Flavour: ." + Global.extractEnding(filelink_derivative) + "</strong> wurde gelöscht.</p>"
            			     , "success")
           		);
            }
            else {
            	all_derivatives_been_deleted = false;
                System.out.print(
            		Global.addMessage("<p>Die Datei: <strong>" + Global.extractFilename(filelink_derivative)
            			    + "." + Global.extractEnding(filelink_derivative) +  "</strong> existiert nicht.</p>"
            			    , "nosuccess")
                );
            }
            
            //images to each derivative
            if (Global.deleteFile(new File(Global.root + Global.convertToImageLink(filelink_derivative)))) {
                System.out.print(
            		Global.addMessage("<p>Die Datei: <strong>" + Global.extractFilename(filelink_derivative)
            			     + " Flavour: .png</strong> wurde gelöscht.</p>"
            			     , "success")
           		);
            }
            else {
            	all_images_been_deleted = false;
                System.out.print(
            		Global.addMessage("<p>Die Datei: <strong>" + Global.extractFilename(filelink_derivative)
            			    + ".png </strong> existiert nicht.</p>"
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
		if(file.exists() && file.isFile()){
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
	 * Verschiebt den Ordner oder Datei in ein anderen Orner.
	 * @param toBeMoved Pfad zu Ordner, der verschoben werden soll
	 * @param target Pfad von Zielordner 
	 * @return true falls verschoben,anderfalls false
	 * @throws IOException 
	 */
	public static boolean moveDir(String toBeMoved, String target_dir, String child_dir_or_filename) throws IOException {
		// File (or directory) to be moved
		File file = new File(toBeMoved);

		// Destination directory
		File dir = new File(target_dir);

		// Move file to new directory
		boolean success = file.renameTo(new File(dir, child_dir_or_filename));
		if (!success) {
		    return false;
		}
		else {
			return true;
		}
	}
	
	//RENAME FILE
	public static boolean renameFile(String old, String new_) {
		return renameFile(old, new_, true);//Overwrite existing by default!
	}
	public static boolean renameFile(String old, String new_, boolean overwrite) {
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
			System.out.println("In Global.renameFile: The new file destination " +
					new_ + " already exists and will" + (overwrite ? "" : " NOT") + " get overwritten!");
			if (!overwrite) {
				return false;
			}
		}
		return oldFile.renameTo(newFile);
	}

	//CONVERT TO IMAGELINK
	public static String convertToImageLink(String filelink) {
		return convertToImageLink(filelink, Global.imageTypeToGenerate);
	}
	public static String convertToImageLink(String filelink, String ending) {
		//return filelink.replaceAll("[.][a-zA-Z]+$", "." + ending);
		return filelink + "." + ending;/*Changed with Update v30.83 !!
		because we need images to each flavour to see the differences!*/
	}
	
	//EXTRACT FILENAME + FILEENDING
	public static String extractFilenameAndEnding(String filelink) {
		String[] parts = filelink.split("/");
		String candidate = parts[parts.length - 1];
		return candidate;
	}
	
	//EXTRACT FILENAME
	public static String extractFilename(String filelink) {
		String[] parts = filelink.split("/");
		String candidate = parts[parts.length - 1];
		return candidate.replaceAll("[.][a-zA-Z]+$", "");//replaces only the last ending!
	}
	
	//EXTRACT FILENAME WITHOUT ANY EXTENSION - the first approach using reversing, no bad idea, Artiom created it.
	public String extractFilenameWithoutAnyExtension(String filelink) {
    	//Build name of pdf file.
        StrBuilder str_b = new StrBuilder(filelink);
        String rev_str = str_b.reverse().toString();
        int firstOccurrenceOfPoint = rev_str.indexOf("."); 
        String without_ext_rev = rev_str.substring(firstOccurrenceOfPoint + 1);
        
        String inFileEndingRev = rev_str.substring(0, firstOccurrenceOfPoint);
        String ending = new StrBuilder(inFileEndingRev).reverse().toString();
        
        String filePathWithoutEnding = new StrBuilder(without_ext_rev).reverse().toString();
    	//this.filePathWithoutEndingAndNotMarkedAsOriginal = this.filePathWithoutEnding.replaceAll("/[.]original(e)?$/i", "");
        return filePathWithoutEnding;
        
    }
	
	
	
	//EXTRACT PARENT PATH / PATH TO
	public static String extractPathTo(String filelink) {
		return filelink.replaceAll(
				Global.extractFilename(filelink) + "." + Global.extractEnding(filelink) + "$"
				, "" /*replace with nothing => path to file left*/
		);
	}
	
	//EXTRACT ORIGINSHEETDRAFT FILELINK
	public static String extractSheetdraftFilelinkFromExerciseFilelink(String filelink) {
		String[] parts = filelink.split("__");
		String result = "";
		//The last two parts belong to the exercise only:
		//		1) __Exercise_<number>
		//      2) __splitby_<splitter>.<native_ending>.<derived_ending>
		//So these two are to be skipped!
		for (int i = 0; i < parts.length - 2; i++) {
			result += parts[i];	// The rest is concatenated.
		}
		if (!new File(Global.root + result).exists()) {
			System.out.println(
					Global.addMessage("The sheetdraft the exercise originates from could not"
							+ " be found in the filesystem."
							+ "Review Global.getOriginsheetdraftFilelink.", "danger")
			);
		}
		return result;
	}
//	public String getSheetdraftFilelink() {
//		//IT HAS TO BE ENSURED THAT __Exercise is being added at exercise 
//		//AS FIRST ADDITION.
//		//OR USE Global.extractSheetdraftFilelinkFromExerciseFilelink.
//		int filelink_to_remove_index_start = filelink.indexOf("__Exercise");
//		return filelink.substring(0, filelink_to_remove_index_start - 1);
//	}
	
	
	//EXTRACT EXERCISE PART ONLY FROM FILE LINK
	public static String extractExercisePartOnlyFromExerciseFilelink(String filelink) {
		String[] parts = filelink.split("__");
		String result = "";
		//The last two parts belong to the exercise only:
		//		1) __Exercise_<number>
		//      2) __splitby_<splitter>.<native_ending>.<derived_ending>
		//=> So these two have to get concatenated!
		for (int i = parts.length - 2; i < parts.length; i++) {
			if (i > parts.length - 2) {
				result += "__";//what been destroyed be rebuilt.
			}
			result += parts[i];	// The rest is concatenated.
		}
		//No existance check required here as the exercise in the filesystem includes
		//the parent filelink at the beginning, the exercise only part is only a suffix
		//to the filelink of the originsheetdraft.
		return result;
	}
	
	//EXTRACT ENDING DOUBLE
	public static String extractEndingDouble(String filelink) {
		String[] parts = filelink.split("[.]");
		//Has one ending?
		if (parts.length < 2) {
			addMessage("Filelink has not even one ending, after split it was null"
					+ " instead! (extractEndingDouble)<br />filelink = " + filelink, "danger");
			return "ENDING WAS NULL";
		}
		//Has a double ending?
		if (parts.length < 3) {
			addMessage("Filelink has no double ending, after split it was null"
					+ " instead! (extractEndingDouble)<br />filelink = " + filelink, "danger");
			return "ENDING WAS NULL";
		}
		return parts[parts.length - 2] + "." + parts[parts.length - 1];
	}
	
	//EXTRACT ENDING
	public static String extractEnding(String filelink) {
		String[] parts = filelink.split("[.]");
		if (parts.length < 2) {
			addMessage("Filelink has no ending, after split it was null"
					+ " instead! (extractEnding)<br />filelink = " + filelink, "success");
			return "ENDING WAS NULL";
		}
		return parts[parts.length - 1];
	}
	//EXTRACT ENDING PER REVERSE (VARIANT OF THE UPPER)
	public static String extractEndingPerReverse(String filelink) {
		String filename_reversed = new StringBuffer(filelink).reverse().toString();
		int punkt = filename_reversed.indexOf(".");
		String ext_reversed = filename_reversed.substring(0, punkt);//file endung
		return new StringBuffer(ext_reversed).reverse().toString();
	}
	//REPLACE ENDING
	public static String replaceEnding(String filelink, String newext) {
		if (!newext.startsWith(".")) {
			newext = "." + newext;
		}
		return filelink.replaceAll("[.]" + extractEnding(filelink) + "$", newext);
	}
	
	
	//EXTRACT EXERCISE NUMBER FROM FILELINK
	public static int extractExerciseNumberFromFilelink(String filelink) {
		
		filelink = Global.extractExercisePartOnlyFromExerciseFilelink(filelink); 
		
		//split in exercise_<number> and splitby_<splitter>:
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
		
		
		
		
		//To be extracted:
		String exerciseNumber = "";
		//
		String substring_beginning = "Exercise_";
		int splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "__exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "-Exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "-exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		/* hopefully we have success before those emergency solutions: */
		if (splitter_start_filelink_index == -1) {
			substring_beginning = ".Exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = ".exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "_Exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "_exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "Exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "exercise_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		/*success?*/
		int index_start = splitter_start_filelink_index;
		if (index_start == -1) {
			addMessage("No exercise number could be extracted! filelink: " + filelink, "blackwhite");
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
				//Because this way no index out of bounds error will show:
				if (substring_beginning_index >= substring_beginning_L
						|| ch != substring_beginning.charAt(substring_beginning_index)) {
					//reached the end?
					if (ch == '_' || ch == '.' || ch == '-') {/*. is 2nd termination criteria*/
						if (terminatingUnderscoreOccurredOnce || ch == '.' || ch == '-') {
							//The 2nd time in series an underscore. => STOP.-
							//terminate this while loop, we have reached the end of the splitter
							break;
						}
						ending_buffer += "_";
						terminatingUnderscoreOccurredOnce = true;
					} else {
						//Last was a single _ and not __, so the ending buffer stands
						//for a space, so we have to add it to the splitter/split_by.
						exerciseNumber += ending_buffer;
						//Clear the ending buffer as it has been an ordinary space!
						ending_buffer = "";
						//Finally add the current char, too. Easy to forget that.
						exerciseNumber += ch;
					}
//				}
			}
			index++;
			substring_beginning_index++;
		}
		
		//Finally the part within __splitby_<this part>__ is extracted.
		return Integer.parseInt(exerciseNumber); 
	}
	
	
	//EXTRACT SPLITTER
	public static String extractSplitterFromFilelink(String filelink) {
		return Global.extractSplitByFromFilelink(filelink);
	}
	public static String extractSplitByFromFilelink(String filelink) {
		
		filelink = Global.extractExercisePartOnlyFromExerciseFilelink(filelink); 
		
		//To be extracted:
		String splitter = "";
		//
		String substring_beginning = "__splitby_";
		int splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "__splitter_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "-splitby_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "-splitter_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		/* hopefully we have success before those emergency solutions: */
		if (splitter_start_filelink_index == -1) {
			substring_beginning = ".splitby_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = ".splitter_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "_splitby_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		if (splitter_start_filelink_index == -1) {
			substring_beginning = "_splitter_";
			splitter_start_filelink_index = filelink.indexOf(substring_beginning);
		}
		/*success?*/
		int index_start = splitter_start_filelink_index;
		if (index_start == -1) {
			//no split by/splitter defined
			//=> determine automatically (ideal solution if it works) <-- TODO and ready in DeclarationFinder
			//USE DECLARATIONS: 1., 2., ... e.g. (stored at sheetdraft.declarations)
			//Now we progagate up and use INTDOT/INTBRACKET/... depending on which fits best
			//on a optionally specified custom __splitby_ in the filelink.
			addMessage("NO OPTIONAL CUSTOM SPLITTER SPECIFIED IN FILELINK OF UPLOADED DOCUMENT!"
					+ "\r\n<br/>=> Auto-detecting exercise declarations ..."
					, "blackwhite");
			
			//=> or simply use a default splitter that has been defined as a convention
			//e.g. Aufgabe. <-- this is what we use at first!!!
			//addMessage("=> USING 'Aufgabe' OR 'Exercise' instead.", "info");
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
				//Because this way no index out of bounds error will show:
				if (substring_beginning_index >= substring_beginning_L
						|| ch != substring_beginning.charAt(substring_beginning_index)) {
					//reached the end?
					if (ch == '_' || ch == '.' || ch == '-') {/*. is 2nd termination criteria*/
						if (terminatingUnderscoreOccurredOnce || ch == '.' || ch == '-') {
							//The 2nd time in series an underscore. => STOP.-
							//terminate this while loop, we have reached the end of the splitter
							break;
						}
						ending_buffer += "_";
						terminatingUnderscoreOccurredOnce = true;
					} else {
						//Last was a single _ and not __, so the ending buffer stands
						//for a space, so we have to add it to the splitter/split_by.
						splitter += ending_buffer;
						//Clear the ending buffer as it has been an ordinary space!
						ending_buffer = "";
						//Finally add the current char, too. Easy to forget that.
						splitter += ch;
					}
//				}
			}
			index++;
			substring_beginning_index++;
		}
		
		//Finally the part within __splitby_<this part>__ is extracted.
		return splitter; 
	}
	
	
	//EXTRACT SEMESTER FROM FILELINK
	public static String extractSemesterFromFilelink(String filelink) {
		String[] parts = filelink.split("[/]");
		//parts[0] = upload_directory:uploads/  or / is first character!;
		//parts[1] = semester;
		//parts[2] = course;
		//parts[3] = lecturer;
		//parts[4] = type;
//		return parts[1];
		//parts[length-6] = upload_directory:uploads/  or / is first character!;
		//parts[length-5] = semester;
		//parts[length-4] = course;
		//parts[length-3] = lecturer;
		//parts[length-2] = type;
		//parts[length-1] = filename; //last array entry
		return parts[parts.length - 5];
	}
	//EXTRACT COURSE FROM FILELINK
	public static String extractCourseFromFilelink(String filelink) {
		String[] parts = filelink.split("[/]");
		//parts[0] = upload_directory:uploads/  or / is first character!;
		//parts[1] = semester;
		//parts[2] = course;
		//parts[3] = lecturer;
		//parts[4] = type;
//		return parts[2];
		//parts[length-6] = upload_directory:uploads/  or / is first character!;
		//parts[length-5] = semester;
		//parts[length-4] = course;
		//parts[length-3] = lecturer;
		//parts[length-2] = type;
		//parts[length-1] = filename; //last array entry
		return parts[parts.length - 4];
	}
	//EXTRACT LECTURER FROM FILELINK
	public static String extractLecturerFromFilelink(String filelink)
			throws IOException, SQLException {
//		Global.addMessage("filelink to extract lecturer from = " + filelink, "info");
		String[] parts = filelink.split("[/]");
		//parts[0] = upload_directory:uploads/  or / is first character!;
		//parts[1] = semester;
		//parts[2] = course;
		//parts[3] = lecturer;	//TODO this implies lecturer is unique.
								//Weigel->WolfgangWeigel
								//		->WWeigel
		//parts[4] = type;
//		return parts[3];
		//parts[length-6] = upload_directory:uploads/  or / is first character!;
		//parts[length-5] = semester;
		//parts[length-4] = course;
		//parts[length-3] = lecturer;
		//parts[length-2] = type;
		//parts[length-1] = filename; //last array entry
		return parts[parts.length - 3];
	}
	public static int extractLecturerIdFromFilelink(String filelink)
			throws IOException, SQLException {
		String query = "SELECT id FROM lecturer WHERE lecturer = '"
				+ Global.extractLecturerFromFilelink(filelink) + "'";
		ResultSet res =	Global.query(query);
		
		int lecturer_id = -1;
		while (res.next()) {
			lecturer_id = res.getInt("id");
		}
		if (lecturer_id == -1) {
			lecturer_id = 0; /*because the first entry (id = 0) is N.N. which is to be used if no lecturer is given*/
			Global.addMessage("Using N.N. for now because the lecturer id could not be determined in the database (was -1). This happens when the lecturer is a new one." /*+ "Query: " + query*/, "warning");
		}
		
		return lecturer_id;
	}
	//EXTRACT TYPE FROM FILELINK
	public static String extractTypeFromFilelink(String filelink) {
		String[] parts = filelink.split("[/]");
		//parts[0] = upload_directory:uploads/  or / is first character!;
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
	
	
	
	
	public static Pattern determinePatternBestFittingToSplitterHint(String splitter) {
		
		for (Muster m : Muster.values()) {
			if (m.equals(splitter) || m.getPattern().matcher(splitter).matches()) {
				return m.getPattern();
			}
		}
		
		return Pattern.compile(splitter);
		
	}
	
	
	

	
//	Global.getExerciseFilelinkExtensionPattern() {
//		return "";
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    //GET SEMTERM FROM NOW
	public static String getSemtermFromCalendar(Calendar calendar) {
		//
        Map<String, Map<String, Calendar>> semtermsAndTimesMap = new HashMap<String, Map<String, Calendar>>();
        Calendar semtermStart = Calendar.getInstance();
        Calendar semtermEnd = Calendar.getInstance();
        Map<String, Calendar> timesMap = new HashMap<String, Calendar>();
        
        //SUMMER TERM
        semtermStart.set( calendar.get(Calendar.YEAR),  3 - 1,  1 ); /*year, month, day*/
        semtermEnd.set( calendar.get(Calendar.YEAR),  10 - 1,  1 ); /*year, month, day*/
        timesMap.put("start", semtermStart);
        timesMap.put("end", semtermEnd);
        semtermsAndTimesMap.put("SS", timesMap);//TODO: PUT IN CONFIGURATION-FILE!

        //WINTER TERM
        timesMap = new HashMap<String, Calendar>();
        semtermStart = semtermEnd;    /* now.year + 1 !!! because the end is the following year*/
        semtermEnd.set( calendar.get(Calendar.YEAR) + 1,  3 - 1,  1); /*year, month, day*/
        timesMap.put("start", semtermEnd);
        timesMap.put("end", semtermStart);
        semtermsAndTimesMap.put("WS", timesMap);//TODO: PUT IN CONFIGURATION-FILE!
        
        //finally determine in which term the calendar date falls
        if (calendar.after(semtermsAndTimesMap.get("SS").get("start"))
        		&& calendar.before(semtermsAndTimesMap.get("SS").get("end"))) {
        	//SUMMER TERM
        	return "SS";
        }
        //else
    	//WINTER TERM
        return "WS";
	}
	
	
	//INT TO BOOLEAN
	public static boolean intToBoolean(int i) {
		if (i < 0 || i > 1) {
			throw new IllegalArgumentException("Argument of intToBoolean must be"
					+ " 0 or 1.");
		}
		return i > 0 ? true : false;
	}
	//BOOLEAN TO INT
	public static int booleanToInt(boolean b) {
		return b ? 1 : 0;
	}
	
	
	
	
	
	
	
	
	//ADD FILES TO EXISTING ZIP ARCHIVE
	//
	//http://stackoverflow.com/questions/3048669/how-can-i-add-entries-to-an-existing-zip-file-in-java
	public static void addFilesToExistingZip(File zipFile, File[] files)
			throws IOException {
		//For saving of the old entries in the Zip-File. (it will get deleted).
		File tempFile = File.createTempFile(zipFile.getName(), null);
		//Delete it, otherwise you cannot rename your existing zip file to it.
		tempFile.delete();
		boolean renameOk = zipFile.renameTo(tempFile);
		if (!renameOk) {
			throw new RuntimeException("Could not rename the file + "
					+ zipFile.getAbsolutePath() + "to" + tempFile.getAbsolutePath());
		}
		byte[] buffer = new byte[1024];
		ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));
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
				//Add this zip entry to the output stream.
				zout.putNextEntry(new ZipEntry(entryName));
				//Transfer bytes from the ZIP file to the output destination file.
				int len;
				while ((len = zin.read(buffer)) > 0) {
					zout.write(buffer, 0, len);
				}
			}
			entry = zin.getNextEntry();
		}
		//Close the streams.
		zin.close();
		//Compress the files.
		for (int i = 0; i < files.length; i++) {
			InputStream in = new FileInputStream(files[i]);
			//Add the ZIP entry to the output stream:
			zout.putNextEntry(new ZipEntry(files[i].getName()));
			//Transfer bytes from the file to the ZIP file. (?)
			int len;
			while ((len = in.read(buffer)) > 0) {
				zout.write(buffer, 0, len);
			}
			//Complete the entry.
			zout.closeEntry();
			//Close the input stream because now the new ZIP should include all
			//both the new and the old entries.
			in.close();
		}
		//Complete the ZIP file
		zout.close();
		tempFile.delete();
		
		
//      ALTERNATIVELY USING COMMONS.IO FROM APACHE.
//		ZipFile zipFile = new ZipFile("foo.zip");
//		InputStream in = null;
//		ByteArrayOutputStream out = null;
//		try {
//		    in = zipFile.getInputStream(zipFile.getEntry("fileInTheZip"));
//		    out = new ByteArrayOutputStream();
//		    IOUtils.copy(in, out);
//		} finally {
//		    IOUtils.closeQuietly(in);
//		    IOUtils.closeQuietly(out);
//		}
	}
	
	
	
	/**
	 * Adds an arbitrary file to a zip archive file.
	 * Several flavours are available:
	 * @param give a zipFilelink and a filelink_to_add;
	 * @param give a zipFile and a file_to_add;
	 * @param give a zip output stream and a file.
	 */
	//http://jcsnippets.atspace.com/java/input-output/create-zip-file.html
    private static final int BUFFER_SIZE = 4096;
        
    //2 params
    public static void addFileToZip(String zipFilelink, String filelink_to_add_to_zip)
    		throws FileNotFoundException {
    	addFileToZip(new File(zipFilelink), new File(filelink_to_add_to_zip));
    }
    public static void addFileToZip(File zipFile, File file_to_add_to_zip) throws FileNotFoundException {
    	addFileToZip(new ZipOutputStream(new FileOutputStream(zipFile)), file_to_add_to_zip);
    }
    public static void addFileToZip(ZipOutputStream zos, File file_to_add) {
    	addFileToZip(zos, file_to_add, file_to_add.getName());
    }
    //3 params
    public static void addFileToZip(String zipFilelink, String filelink_to_add_to_zip, String entry_path_filename_within_zip)
    		throws FileNotFoundException {
    	addFileToZip(new File(zipFilelink), new File(filelink_to_add_to_zip), entry_path_filename_within_zip);
    }

    public static void addFileToZip(File zipFile, File file_to_add_to_zip, String entry_path_filename_within_zip)
    		throws FileNotFoundException {
    	addFileToZip(new ZipOutputStream(new FileOutputStream(zipFile)), file_to_add_to_zip, entry_path_filename_within_zip);
    }

    public static void addFileToZip(ZipOutputStream zos, File file_to_add, String entry_path_filename_within_zip)
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
     * Creates a zip file from either a directory string given or an array of files of type File.
     * @param directory <-- being checked if it is a directory or not.
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
            		+ " is not a directory. Zipping a single file arhive now instead.");
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
                        System.out.println("Zipping " + files[i].getName());
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
    
    
//    public static void main(String [] args)
//    {
//        if (args.length == 1) {
//            Global.createZipFile(args[0]);
//        }
//        else {
//            System.out.println("usage: java Global <directory>");
//        }
//    }
//    
	

    static InputStream getInputStream(String zip, String entry) throws IOException {
    	return getInputStream(new File(zip), entry);
    }
	//http://stackoverflow.com/questions/14603319/getinputstream-for-a-zipentry-from-zipinputstream-with-out-using-zipfile
	static InputStream getInputStream(File zip, String entry) throws IOException {
		ZipInputStream zin = new ZipInputStream(new FileInputStream(zip));
		
		for (ZipEntry e; (e = zin.getNextEntry()) != null;) {
			//zin.getNext moves pointer of InputStream to the next entry.
			if (e.getName().equals(entry)) {
				//now we have it at the correct position in the stream: the hydro plant ..
				return zin;
			}
		}
		throw new EOFException("Cannot find zip-entry: " + entry + " !");
		
	}

	
	//GET INPUT STREAM DEPENDING ON ENDING 
	static InputStream getInputStreamDependingOnEnding(String filelink) throws IOException {
		File file = new File(filelink);
		if (!file.exists()) {
			Global.addMessage("Global.getInputStreamDependingOnEnding: File not found!", "danger");
			return null;
		}
		
		//DOCX
		if (filelink.endsWith(".docx")/*
				|| filelink.endsWith(".zip")
				|| filelink.endsWith(".tar")
				|| filelink.endsWith(".gz")
				|| filelink.endsWith(".7z")
				|| filelink.endsWith(".rar")*/) {
			ZipFile docx = new ZipFile(file);
//			IOUtils.copy(docx.getInputStream(entry),);
//			ZipEntry docxXML = docx.getEntry(entry);
			Global.addMessage("docx-Contents:\r\n<br />" + renderZipContents(docx), "info");
			//word/document.xml entry of the zip/archive contains the content
			return getInputStream(new File(filelink), "word/document.xml");
		}
		
		//ODT OpenDocumentFormat:Text OpenXML (Libre/OpenOffice)
		if (filelink.endsWith(".odt")/*
				|| filelink.endsWith(".zip")
				|| filelink.endsWith(".tar")
				|| filelink.endsWith(".gz")
				|| filelink.endsWith(".7z")
				|| filelink.endsWith(".rar")*/) {
			ZipFile zip = new ZipFile(file);
//			  IOUtils.copy(docx.getInputStream(entry),);
//			  ZipEntry docxXML = docx.getEntry(entry);
			Global.addMessage("ZIP-Contents:\r\n<br />" + renderZipContents(zip), "info");
			//word/document.xml entry of the zip/archive contains the content
			return getInputStream(new File(filelink), "content.xml");
		}
		
		
		//all single source (non-binary) files
		return new FileInputStream(new File(filelink));
	}
	
	
	//RENDER DOCX CONTENTS
	public static String renderDocXContents(String filelink)
			throws IOException {
		return renderZipContents(new ZipFile(filelink), "word/document.xml");
	}
	//RENDER DOCX CONTENTS
	public static String renderODTContents(String filelink)
			throws IOException {
		return renderZipContents(new ZipFile(filelink), "content.xml");
	}
	//RENDER ZIP CONTENTS
	public static String renderZipContents(ZipFile zip) {
		return renderZipContents(zip, "");
	}
	public static String renderZipContents(ZipFile zip, String entryToLookFor) {
		String zipContents = "";
		Enumeration<? extends ZipEntry> entriesIter = zip.entries();
		while (entriesIter.hasMoreElements()) {
			ZipEntry zipEntry = entriesIter.nextElement();
			if (zipEntry.getName().equals(entryToLookFor)) {
				Global.addMessage("renderZipContents: " + entryToLookFor + " found!","success");
			}
			zipContents += zipEntry.getName() + "\r\n<br />";
		}
		return zipContents;
	}

	
	/**
	 * Using Apache native american's wise help. :)
	 * @throws IOException 
	 */
	public static int copyZip(String source_filelink, String target_filelink)
			throws IOException {
		
		ZipInputStream zin = new ZipInputStream(new FileInputStream(source_filelink));
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(target_filelink));
		return IOUtils.copy(zin, zout);
		
	}
	public static int copy(String source_filelink, String target_filelink)
			throws IOException {
		
		InputStream in = new FileInputStream(source_filelink);
		OutputStream out = new FileOutputStream(target_filelink);
		int result = IOUtils.copy(in, out);
		in.close();
		out.close();
		return result;
		
	}
	
	
	
	
	/**
	 * Ueberprueft, ob der uebergebene String gueltig zu einem Integer geparsed werden kann.
	 * 
	 * @param i Zu ueberpruefender String
	 * @return true, falls er geparsed werden kann, false, falls nicht
	 */
	public static boolean isInt(String string) {

		string = string.replace(".", " ").trim();
		//The gentle and complicated (under the hood) way:
		String numPattern = ".*[\\d]+.*";//"[0-9]+";
		if (string.matches(numPattern)
				|| Pattern.compile(numPattern).matcher(string).matches()) {
			return true;
		}
		
		//still null! then the last ressort Artiom already has used below:
		try {
			Integer.parseInt(string);
			return true;
		}
		catch (NumberFormatException e) {
			//no stacktrace printing this time, the exception is really a cruel method
			//of figuring out if it contains a number
			return false;
		}
	
		
	}
	
	
	/**
	 * Pay attention that only one number is contained, all digits will be chained together!
	 * @param string -- Furthermore this parameter will loose commata et alia.
	 * @return the int or Integer.MAX_VALUE;
	 */
//	public static int string2int(String string) {<--it's only string2int of a part!
//		return getInt(string);
//	}
	public static int getInt(String string) {
		int i = 0;
		String parsable = "";
		//from left to right
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

	/**
	 * Better use string2int directly
	 * @param string
	 * @return
	 */
	public static boolean containsInt(String string) {
		return getInt(string) != Integer.MAX_VALUE;
	}
	
	/*Source: Bill Zhao: http://stackoverflow.com/questions/5585779/how-to-convert-string-to-int-in-java/18731580#18731580 */
	public static int strToInt( String str ){
		  int i = 0;
		  int num = 0;
		  boolean isNeg = false;

		  if( str.charAt(0) == '-') {
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
	
    
}
