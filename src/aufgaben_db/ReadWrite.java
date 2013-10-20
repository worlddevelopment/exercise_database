package aufgaben_db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;


//import java.nio.file.*;
//import static java.nio.File.StandardCopyOption.*;
//import java.nio.file.attribute.*;


/**
 * 
 * @author sabine
 * @author J.R.I.B.-W. complete overhaul.
 * 
 * Die Klasse dient als Utility-Klasse der Kategorie IO/Read&Write.
 * Textdateien können eingelesen oder zur Festplatte geschrieben werden.
 * 
 * Beim Einlesen erhaelt man als Rueckgabe jeweils String[];
 */



public class ReadWrite {
	
	/**
	 * 
	 * Liest eine Textdatei ein und gibt diese Zeilenweise als String-Array zurueck
	 * 
	 * @param filename Absoluter Pfad der Datei
	 * @return String-Array mit den Zeilen der Textdatei als Inhalt
	 * @throws FileNotFoundException Wenn die angegebene Datei nicht vorhanden ist.
	 */
	public static String[] loadText(String filename) throws FileNotFoundException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		System.out.println("Das File " + filename + " wurde erfolgreich eingelesen.");
        return readInputStreamAsLines(reader);
        
    }
	
	
	
	
	
	/**
	 * Write 'Exercise Raw Content' to disk.
	 * @param exercise
	 * @throws IOException
	 */
	//static void writeRawContentToDiskDependingOnEnding(Exercise exercise) throws IOException {
	static void writeRawContentToDiskDependingOnEnding(Exercise exercise) throws IOException {
		/* At this point we have the rough idea of an exercise and want
		 * to create it on the harddisk. The idea is to copy the complete
		 * mother sheet docx/zip/tex to a new filename in the same directory
		 * and to delete the parts not belonging to the exercise we want.
		 */
		String ext = Global.extractEnding(exercise.filelink);
		//MICROSOFT
		if (ext.equals("docx")) {
			//1) determine mother sheet to this exercise
			String motherfilelink = exercise.filelink.replaceAll(
					"/" + Global.extractFilename(exercise.filelink)
					+ "." + Global.extractEnding(exercise.filelink) + "$"
					, ""
			);
			File mothersheetToExerciseFile = new File(motherfilelink);
			if (!mothersheetToExerciseFile.exists()) {
				Global.addMessage("Warning: No mother sheet '" + motherfilelink + "' found for the"
							+ " given exercise: '" + exercise.filelink + "' ! => So no copy can be created"
							+ " to obtain the exercise by deleting obsolete (for the specific exercise) conten.", "warning");
				//mothersheetToExerciseFile.mkdirs();
				return;
			}
			
			//2) copy this (mother) sheet's docx/zip completely to new exercise.filelink.docx
			//needs NIO - NewIO - delivered with JDK7
//			Files.copy(targetFilelink, exercise.filelink, REPLACE_EXISTING/*NOFOLLOW_LINKS*/);
			File exerciseFilelinkFile = new File(exercise.filelink);
			if (exerciseFilelinkFile.exists()) {
				Global.addMessage("Warning: Exercise '" + exercise.filelink + " to create"
							+ " already exists.", "warning");
				//overwrite it?? TODO
				return;//for now better exit, no overwritten files so far!!
			} else {
				//
				Global.addMessage("Important: If the exercise files are to be stored in a different folder then make sure the next line is uncommented. (see source code)", "info");
				if (exerciseFilelinkFile.mkdirs()) {
					Global.addMessage("--> No folder had to be created ...", "success");
				}
			}
			Global.addMessage("Copying mother sheet file to exercise destination filelink.", "info");
			copyFileUsingStream(mothersheetToExerciseFile, exerciseFilelinkFile);

			
			//3) Open this newly created archive and delete content that's not needed.
			//   e.g. replace the document.xml-Body with the extracted exercise rawContent.
			//        KEEP HEADERS!
			//        Delete line per line,
			//        follow references, delete them if no more any references too.
			//TODO using docx4j
			
			
			
		}	
		else if (ext.equals("doc")) {
			
		}
		
		//OPEN / LIBRE OFFICE
		else if (ext.equals("ods")) {
			
		}

		
		//The general case: i.e. for all SINGLE-FILE AND NON-ARCHIVE DATATYPES!!!
		else {
			//Simply write it to a single file for: PLAIN TEXT, PDF
			ReadWrite.writeText(
					readInputStreamAsLines(exercise.filelink)
					, exercise.filelink.replaceAll(Global.extractEnding(exercise.filelink) + "$", "txt")
			);
		}
		
				
	}
	
	
	
	
	
	//READ INPUT STREAM AS LINES
	public static String[] readInputStreamAsLines(String  filelink) throws IOException {
		return readInputStreamAsLines(Global.getInputStreamDependingOnEnding(filelink));
	}
	
	//READ FROM STREAM
	public static String[] readInputStreamAsLines(InputStream inputStream) {
		//http://stackoverflow.com/questions/4473256/reading-text-files-in-a-zip-archive
		//To decode from binary to text:
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream); 
		//To load one line at a time:
		BufferedReader reader = new BufferedReader(inputStreamReader);
		return readInputStreamAsLines(reader);
	}
	public static String[] readInputStreamAsLines(BufferedReader reader) {
		
		String zeile = null;
		ArrayList<String> lines = new ArrayList<String>();
		String[] values = null;
		//System.out.println(zeile);
		while(true) {
			
			try {
				zeile = reader.readLine();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Fehler beim Einlesen der Zeilen.");
			}
			if (zeile == null) {
				break;
			}
				
            lines.add(zeile);
            //System.out.println(i + ": " + zeile);
        }
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        values = new String[lines.size()];
        values = lines.toArray(values);

        return values;
    }
	
	
	
	
	
	
	
	

	// Methode zum Erstellen von Text Files,
	// Wird sowohl fuer das Textfile als auch
	// fuer die einzelnen Aufgabebloecke
	// sowohl im .txt als auch im .rtf-Format verwendet 
	// Eingabe: textfile: file, welches erstellt werden soll
	// filename: so soll es dann genannt werden (mit .txt bzw. .rtf)
	
	/**
	 * 
	 * Methode zum Erstellen von Text Files, wird sowohl fuer das Textfile, als auch fuer die einzelnen Aufgabenbl�cke
	 * sowohl im .txt. als auch im .rtf-Format verwendet
	 * 
	 * @param text file, welches erstellt werden soll
	 * @param filelink so soll es dann genannt werden (mit .txt bzw. .rtf)
	 * @throws IOException 
	 */
	public static void writeText(String[] text, String filelink) throws IOException {
		//writeText(new BufferedWriter(new FileWriter(filelink)), filelink);
		//System.out.println("Jetzt wird der Text in die neue Datei geschrieben!");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filelink));
			for (int i = 0; i < text.length; i++) {
//				System.out.println(i);
				if (text[i] != null) {
					bw.write(text[i]);
					bw.newLine();
				}
			}
			System.out.println("Die neue Datei : " + filelink + " wurde erstellt.");
			bw.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	
	
	/**
	 * File copy class using the quickest possibility available in JAVA until now for big files.
	 * (http://www.journaldev.com/861/4-ways-to-copy-file-in-java)
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	public static void copyFileUsingStream(File source, File destination) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(destination);
			byte[] bytes = new byte[1024];
			int length;
			while ( (length=is.read(bytes)) > 0 ) {
				os.write(bytes, 0, length);
			}
		}
		finally {
			is.close();
			os.close();
		}
	}
	
	
	
	/**
	 * WRITE
	 * Text, binary currently not supported.
	 * (http://stackoverflow.com/questions/2885173/java-how-to-create-and-write-to-a-file)
	 * @param string    - The text to write to file.
	 * @param filelink  - The destination file. Will be created or overwritten.
	 * @param overwrite - Unless explicitely stated not to overwrite (default: true).
	 */
	public static void write(String[] lines, String filelink) {
		String linesAsString = "";
		for (String line : lines) {
			linesAsString += line + "\r\n";
		}
		write(linesAsString, filelink);
	}
	
	public static void write(String string, String filelink) {
		
//		ONLY WORKS IN JAVA 1.7 or if NEW IO (nio) package is available.
//		Charset charset = Charset.forName("UTF-8");
//		try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
//			writer.write(string, 0, string.length());
//		} catch (IOException x) {
//			System.err.format("IOException: %s%n", x);
//		}
		
		Writer writer = null;
		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(filelink), "utf-8"
					)
			);
			writer.write(string);
		}
		catch (IOException e) {
			System.err.format("IOException: %s%n .", e);
			e.printStackTrace();
			Global.addMessage("Failed: Writing binary/text to file: " + filelink, "danger");
		}
		finally {
			try { writer.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		
		
//      //The non-proper way as this is for 
//		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
//		writer.println("The first line");
//		writer.println("The second line");
//		writer.close();
//
//		//Creating a binary file (will also overwrite the file):
//
//		byte dataToWrite[] = //...
//		FileOutputStream out = new FileOutputStream("the-file-name");
//		out.write(dataToWrite);
//		out.close();
		
		
	}

	
	
	
	
}
