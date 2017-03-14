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
 * Utility for IO | read, write.
 * TODO Rename to IO?
 *
 * @author sabine, J.R.I.B.-W., worlddevelopment
 */
public class ReadWrite {

	/**
	 * Reads a text file from disk into an array of lines.
	 *
	 * @param filename Absolute path to file to read.
	 * @return Array of lines of the raw content.
	 * @throws FileNotFoundException If the file not exists.
	 */
	public static String[] loadText(String filename)
		throws FileNotFoundException {

		BufferedReader reader
			= new BufferedReader(new FileReader(filename));
		System.out.println("Read file from disk: " + filename);
		return readInputStreamAsLines(reader);

	}



	/**
	 * Write 'Exercise Raw Content' to disk.
	 *
	 * @param exercise
	 * @throws IOException
	 */
	//static void writeRawContentToDiskDependingOnEnding(Exercise exercise) throws IOException {
	static void writeRawContentToDiskDependingOnEnding(Exercise exercise)
		throws IOException {

		/*
		At this point we have a rough idea of an exercise
		and want to create it on the harddisk.
		The idea is to copy the complete 'mother' sheet docx/zip/tex
		to a new filename in the same directory and to delete
		the parts not belonging to the exercise.
		*/
		String ext = Global.extractEnding(exercise.filelink);

		// DOCX
		if (ext.equals("docx")) {
			// 1) determine mother sheet to this exercise
			String motherfilelink = Global.extractFilelinkOfMothersheet(exercise.filelink);
			File mothersheetToExerciseFile = new File(motherfilelink);
			if (!mothersheetToExerciseFile.exists()) {
				Global.addMessage("Warning: No mother sheet '"
						+ motherfilelink + "' found for the"
						+ " given exercise: '" + exercise.filelink
						+ "' ! => So no copy can be created"
						+ " to obtain the exercise by deleting"
						+ " obsolete (for the specific exercise)"
						+ " conten.", "warning");
				//mothersheetToExerciseFile.mkdirs();
				return;
			}

			// 2) copy this sheet's docx to new exercise.filelink.docx
			// Requires NIO - NewIO - delivered with JDK7:
//			Files.copy(targetFilelink, exercise.filelink
//					, REPLACE_EXISTING/*NOFOLLOW_LINKS*/);
			File exerciseFilelinkFile = new File(exercise.filelink);
			if (exerciseFilelinkFile.exists()) {
				Global.addMessage("Warning: Exercise '"
						+ exercise.filelink + " to create"
						+ " already exists.", "warning");
				// For now do not overwrite any files:TODO Overwrite it?
				return;
			}
			else {
				Global.addMessage("Creating directories for storing"
						+ " files of content parts.", "info");
				if (exerciseFilelinkFile.mkdirs()) {
					Global.addMessage("- No folder had to be created."
							, "success");
				}
			}
			Global.addMessage("Copying mother sheet file to exercise"
					+ " destination filelink.", "info");
			copyFileUsingStream(mothersheetToExerciseFile
					, exerciseFilelinkFile);

			// 3) Open this newly created archive and delete content
			// that's not needed.
			// e.g. replace the document.xml-Body with the extracted
			// exercise rawContent and
			// * KEEP HEADERS!
			// * Delete line per line,
			// * follow references, delete those no longer in use.
			// Note this is an early outline before it was
			// coded in Exercise.


		}
		else if (ext.equals("doc")) {

		}


		// OPEN / LIBRE OFFICE
		else if (ext.equals("ods")) {

		}


		// GENERAL CASE: single file, non-archive
		else {
			// Write it to a single file for: PLAIN TEXT, PDF
			ReadWrite.writeText(
					readInputStreamAsLines(exercise.filelink)
					, exercise.filelink.replaceAll(
						Global.extractEnding(exercise.filelink)
						+ "$", "txt")
			);
		}


	}



	/**
	 * READ INPUT STREAM AS LINES
	 *
	 * @param filelink
	 */
	public static String[] readInputStreamAsLines(String filelink)
		throws IOException {
		return readInputStreamAsLines(
				Global.getInputStreamDependingOnEnding(filelink));
	}



	/**
	 * @param inputStream
	 */
	public static String[] readInputStreamAsLines(
			InputStream inputStream) {

		// http://stackoverflow.com/questions/4473256/reading-text-files-in-a-zip-archive
		// To decode from binary to text:
		InputStreamReader inputStreamReader
			= new InputStreamReader(inputStream);
		// To load one line at a time:
		BufferedReader reader = new BufferedReader(inputStreamReader);
		return readInputStreamAsLines(reader);

	}



	public static String[] readInputStreamAsLines(BufferedReader reader) {

		String zeile = null;
		ArrayList<String> lines = new ArrayList<String>();
		String[] values = null;
		//System.out.println(zeile);
		try {
			while((zeile = reader.readLine()) != null) {
				lines.add(zeile);
				//System.out.println(i + ": " + zeile);
			}
		} catch (IOException e1) {
			e1.printStackTrace();

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



	/**
	 *
	 * Writes text to file system.
	 * Use for text based formats like plain text .txt or markup like
	 * e.g. .rtf, .md, ...
	 *
	 * @param text array of string to write to file.
	 * @param filelink including ending.
	 * @throws IOException
	 */
	public static void writeText(String[] text, String filelink)
		throws IOException {

		//writeText(new BufferedWriter(new FileWriter(filelink))
		//				, filelink);
		//System.out.println("Trying to write text to file.");
		try {
			BufferedWriter bw
				= new BufferedWriter(new FileWriter(filelink));
			for (int i = 0; i < text.length; i++) {
//				System.out.println(i);
				if (text[i] != null) {
					bw.write(text[i]);
					bw.newLine();
				}
			}
			System.out.println("Create file: " + filelink);
			bw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * File copy class using the quickest possibility available
	 * in Java until now for big files.
	 * (http://www.journaldev.com/861/4-ways-to-copy-file-in-java)
	 *
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	public static void copyFileUsingStream(File source
			, File destination) throws IOException {

		InputStream is = null;
		OutputStream os = null;
		// To not write bytewise or at least keep the conversion
		// of the string os-independent, use e.g. PrintWriter.
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
	 * WRITE text.
	 * TODO Support binary.
	 * http://stackoverflow.com/questions/2885173/java-how-to-create-and-write-to-a-file
	 *
	 * @param string The text to write to file.
	 * @param filelink The destination file.
	 * @param overwrite Set to true by default.
	 */
	public static void write(String[] lines, String filelink) {
		String linesAsString = Global.arrayToString(lines);
		write(linesAsString, filelink);
	}

	public static void write(String string, String filelink) {
		write(string, filelink, false);
	}

	public static void write(String string
			, String filelink, boolean isAppended) {

//		ONLY WORKS IN JAVA 1.7 or if NEW IO (nio) package is available.
//		Charset charset = Charset.forName("UTF-8");
//		try (BufferedWriter writer
//				= Files.newBufferedWriter(file, charset)) {
//			writer.write(string, 0, string.length());
//		} catch (IOException x) {
//			System.err.format("IOException: %s%n", x);
//		}

		Writer writer = null;
		try {
			if (isAppended) {
				writer = new BufferedWriter(
						new FileWriter(filelink, isAppended));
			}
			else {
				writer = new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(filelink)
								, "utf-8"
								)
						);
			}
			writer.write(string);
		}
		catch (IOException e) {
			System.err.format("IOException: %s%n .", e);
			e.printStackTrace();
			Global.addMessage("Failed: Writing binary/text to file: "
					+ filelink, "danger");
		}
		finally {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


//		PrintWriter writer
//			= new PrintWriter("the-file-name.txt", "UTF-8");
//		writer.println("The first line");
//		writer.println("The second line");
//		writer.close();
//
//		// Creating a binary file (will also overwrite the file):
//
//		byte dataToWrite[] = //...
//		FileOutputStream out = new FileOutputStream("the-file-name");
//		out.write(dataToWrite);
//		out.close();


	}





}
