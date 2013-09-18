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
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;



import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.HashMap;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
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
	public static enum SUPPORTED_FILETYPES { pdf, tex, docx, doc, txt };
	public static boolean debug = true;
	public static boolean angemeldet = false;
	public static String anzeige = "start";
	public static String id = "start";	//former aktion replacement
	
	public static String message = "";
	public static String messageClass = "neutral";
	//out of e.g. default, success, failure
	
	public static String root; //root - to be written once at startup
	public static String uploadTarget = "uploads" + "/";
	public static HttpSession session;
	public static Calendar now = new GregorianCalendar();
	
	//MySQL attributes
	public static MysqlHelper msqh = new MysqlHelper();
	public static Connection conn;
	public static SQL_Methods sqlm = new SQL_Methods();
	
	//Response -- TODO is this individual for each jsp-File?
	//public static HttpServletResponse request;
	public static HttpServletResponse response;
	

	
	
	//======= GLOBALS - FUNCTIONS ===============================================// 
	/*menueItem, page to be re-defined/overwritten/adapted somewhere
	     previously (mostly immediately before include of this scriptlet)*/
	
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
		return Global.getFilelinkNativeFormat(filelink)
				.equals(last_ext_hence_current_format);
	}
	public static String getFilelinkNativeFormat(String filelink) {
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
	public static ResultSet query(String query, HttpServletRequest request, PageContext pageContext)
			throws IOException {
		return query(query, request, pageContext, "no_callee_information_given");
	}
	public static ResultSet query(String query, HttpServletRequest request, PageContext pageContext,
			String information_where_this_method_was_called)
			throws IOException {
		try {
//			String str_query = "SELECT *, MATCH (inhalt) AGAINST ('\"" + search_string + "\"') AS score FROM blatt WHERE MATCH (inhalt) AGAINST ('\"" + search_string + "\"' in boolean mode);";
//			//OHNR BOOLEAN MODE : ES WEREDN KEINE ERGEBNISSE ANGEZEIGT WENN SCORE KLEINER ALS 0.5 IST
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
			if (query.contains("DELETE ") || query.contains("INSERT ") || query.contains("UPDATE ")) {

				updateInsertDeleteHappened = true;
				int rowCountOrNothingToReturn = statement.executeUpdate(query);
				if (rowCountOrNothingToReturn == 0) {
					addMessage("No result to show for query:\r\n<br/>"
							+ query,"nosuccess", request, pageContext);
//					System.out.print(message);
				}
				
			}
			else {
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
			}
		 
		    //finally
		    return res;
		  
		} catch (SQLException e) {
			
		    e.printStackTrace();
		}
		
		//that's the case that is error prone -> check if null at caller 
		return null;
		
	}
	/**
	 * 
	 * @param query
	 * @throws IOException 
	 */
	public static ResultSet query(String query) throws IOException {

		return query(query, null, null);
	
	}
	public static ResultSet query(String query, String information_where_this_method_was_called) throws IOException {

		return query(query, null, null, information_where_this_method_was_called);
	
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
            if (Global.angemeldet) {
            	//nach dem login inkludiere start.in.jsp anstatt start.jsp;
            	//ID-Attribut wird im login.jsp gesetzt(Session)
            	//loggedInAddition = ".in";
            	loggedInPath = "";//"in/";
        	}
	        //add error to request for auto display in the index.jsp!
	        session.setAttribute("errors", fileType + " '" + filename + "' not found!");
	        Global.addMessage("An error occurred: " + fileType + " '" + filename + "' not found!"
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
	

	public static String getImageLinkFromFile(String filelink) {
		String[] parts = filelink.split(".");
		if (parts.length < 2) {
			addMessage("Filelink has no ending, after split it was null instead!"
					+ " (getImageLinkFromFile)<br />filelink = " + filelink, "success");
			return "ENDING WAS NULL";
		}
		String filelink_ending = parts[parts.length - 1];
		String imagelink = filelink.replaceAll(filelink_ending, "jpg");
		//TODO - optional:
		//Test if file exists here.
		return imagelink;
	}
	

	
	

	//======= GLOBALS - ARTIOMS IMPORTANT Global =============================//
    public static void createImageFromText(String[] str, int width, String path,
    		String name, String ext_desired) throws IOException {
		
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		Font font = new Font("Serif", Font.PLAIN, 12);
		int height = str.length*(font.getSize() + 10) ;//abhaengig von der schriftgroesse,anzahl der zeilen und abstand zwischen zeilen 10 un
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

       //g2.setFont(new Font(arg0, arg1, arg2))
        g2.fillRect(0, 0, width, height);
        
        g2.setFont(font);
        g2.setColor(Color.black);
        int x = 20;
        int y = 20;
        
        for (int i = 0; i < str.length; i++) {
        	g2.drawString(str[i],x,y+=20);

		}
        
        
        File outputfile = new File(path + name + "." + ext_desired);
        ImageIO.write(img, ext_desired, outputfile);
	}
	
	
	public static String getInhalt_as_String(String[] str) {
		String res = "";
		for (int i = 0; i < str.length; i++) {
			res += str[i] + "\n";
		}
		return res;
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
			str = str.replaceAll("/", "---");
		}
		if(str.contains(" ")){
			str = str.replaceAll(" ", "_");
		}

		return str;
	}
	
	
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}
	public static boolean deleteFile(File file) {
		boolean success = false;
		if(file.exists() && file.isFile()){
			file.delete();
			success = true;
			}
			return success;
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
	public static boolean moveDir(String toBeMoved,String target_dir, String new_filename) throws IOException {
		// File (or directory) to be moved
		File file = new File(toBeMoved);

		// Destination directory
		File dir = new File(target_dir);

		// Move file to new directory
		boolean success = file.renameTo(new File(dir, new_filename));
		if (!success) {
		    return false;
		}
		else {
			return true;
		}
	}
	
	//RENAME FILE
	public static void renameFile(String old,String new_) {
		File oldFile = new File(old);
		if (!oldFile.exists()) {
			System.out.println("In Global.renameFile: Old file "
				    + old + " not exists!");
		}
		File newFile = new File(new_);
//		if (!newFile.exists()) {
//			System.out.println("In Global.renameFile: The new file " +
//		        new_ + " not exists!");
//		}
		oldFile.renameTo(newFile);
	}

	//CONVERT TO IMAGELINK
	public static String convertToImageLink(String filelink) {
		return filelink.replace("[.][a-zA-Z]+$", ".jpg");
	}
	//EXTRACT FILENAME
	public static String extractFilename(String filelink) {
		String[] parts = filelink.split("/");
		String candidate = parts[parts.length - 1];
		return candidate.replaceAll("[.][a-zA-Z]+$", "");
	}
	//EXTRACT PARENT PATH / PATH TO
	public static String extractPathTo(String filelink) {
		return filelink.replaceAll(
				Global.extractFilename(filelink) + "." + Global.extractEnding(filelink) + "$"
				, "" /*replace with nothing => path to file left*/
		);
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
		return filelink.replace("[.]" + extractEnding(filelink) + "$i", newext);
	}
	//EXTRACT SPLITTER
	public static String extractSplitterFromFilelink(String filelink) {
		return Global.extractSplitByFromFilelink(filelink);
	}
	public static String extractSplitByFromFilelink(String filelink) {
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
			//=> determine automatically (ideal solution if it works) <-- TODO
			//USE DECLARATIONS: 1., 2., ... e.g. (stored at sheetdraft.declarations)
			//TODO
			addMessage("NO SPLITTER SPECIFIED IN FILELINK OF UPLOADED DOCUMENT!"
					+ "\r\n<br/>TODO: Auto-detect of declarations for the non-text-only"
					+ " case if no splitter specified in the document's filename."
					, "danger");
			
			//=> or simply use a default splitter that has been defined as a convention
			//e.g. Aufgabe. <-- this is what we use at first!!!
			addMessage("=> USING 'Aufgabe' OR 'Exercise' instead."
					, "info");
			return "Aufgabe";
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
		ResultSet res =
		Global.query("SELECT id FROM lecturer WHERE lecturer = '"
		+ Global.extractLecturerFromFilelink(filelink) + "'");
		
		int lecturer_id = -1;
		while (res.next()) {
			lecturer_id = res.getInt("id");
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
	
	
	
	//http://jcsnippets.atspace.com/java/input-output/create-zip-file.html
    private static final int BUFFER_SIZE = 4096;
        
    public static void addFileToZip(ZipOutputStream zos, File file)
    {
        byte [] data = new byte[BUFFER_SIZE]; 
        int len; 
        
        FileInputStream fis = null;
        try
        {
            ZipEntry entry = new ZipEntry(file.getName()); 
            entry.setSize(file.length()); 
            entry.setTime(file.lastModified()); 
            
            zos.putNextEntry(entry); 
            fis = new FileInputStream(file); 
            
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
    
    public static void createZipFile(String directory)
    {
        File dir = new File(directory);
        if (dir.isDirectory())
        {
            File [] files = dir.listFiles();
            if (files != null)
            {
                File zip = new File(dir.getName() + ".zip");
                
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
        else
        {
            System.out.println(dir.getName() + " is not a directory");
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
	

	//http://stackoverflow.com/questions/14603319/getinputstream-for-a-zipentry-from-zipinputstream-with-out-using-zipfile
	static InputStream getInputStream(File zip, String entry) throws IOException {
		ZipInputStream zin = new ZipInputStream(new FileInputStream(zip));
		for (ZipEntry e; (e = zin.getNextEntry()) != null;) {
			if (e.getName().equals(entry)) {
				return zin;
			}
		}
		throw new EOFException("Cannot find zip-entry: " + entry + " !");
	}
	
	
    
    
}
