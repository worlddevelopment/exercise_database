package converter;

import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.apache.poi.hwpf.converter.WordToTextConverter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import aufgaben_db.Global;
import aufgaben_db.ReadWrite;



/**
 * Convert Doc, DocX-Documents to plain text files.
 *
 * @author sabine, J.R.I.Balzer-Wein, worlddevelopment
 */
public class DocConverter extends TextConverter {


	/**
	 * Extracts plain text (unformatted text) from DOC.
	 *
	 * @param filelink file UnifiedResourceLocator (=: URL)
	 * @return array of String containing the lines of plain text.
	 * @throws FileNotFoundException If the file path is invalid.
	 * @throws IOException If HWPFDocument converter fails.
	 */
	public static String[] erstelleTextausDoc(String filelink)
		throws FileNotFoundException, IOException{

		File file = null;
		String[] textInLines = null;
		HWPFDocument doc = null;
		try {
			// Read the Doc file
			file = new File(filelink);
			System.out.println("Doc file read"
			FileInputStream fis
				= new FileInputStream(file.getAbsolutePath());
			System.out.println("File stream generated");
			doc = new HWPFDocument(fis);
			System.out.println("HWPFDoc created");
			WordExtractor ex = new WordExtractor(doc);
			System.out.println("WordExtractor generated");
			String text = ex.getText();
			System.out.println("Text extracted");

			textInLines = text.split("\\r?\\n");

			// Write the text to a txt file
			ReadWrite.write(text, file.getAbsolutePath()
				.replaceAll(Global.extractEnding(filelink) + "$", "txt"));

		}
		catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.toString());
		}
		catch (IOException e) {
			System.out.println("Other IO error: " + e.toString());
		}


		return textInLines;
	}



	/**
	 * Extracted the plain text from a .docx document.
	 *
	 * @param filelink File URL
	 * @return array of String containing the lines of plain text
	 * @throws FileNotFoundException If the file path is invalid.
	 * @throws IOException If the XHWPFDocument converter failed.
	 */
	public static String[] erstelleTextausDocX(String filelink)
		throws FileNotFoundException, IOException {

		File file = null;
		String[] textInLines = null;
		XWPFDocument doc = null;

		try {
			// Read the Doc file
			file = new File(filelink);
			System.out.println("DocX file read");
			FileInputStream fis
				= new FileInputStream(file.getAbsolutePath());
			System.out.println("File stream generated");
			doc = new XWPFDocument(fis);

			System.out.println("XWPFDoc generated");
			XWPFWordExtractor ex = new XWPFWordExtractor(doc);
			System.out.println("WordExtractor generated");
			String text = ex.getText();
			System.out.println("Text extracted");

			textInLines = text.split(Global.lineSeparatorPattern);

			// Write the text to a txt file
			ReadWrite.write(text, Global
					.replaceEnding(file.getAbsolutePath(), "txt") );

		}
		catch (FileNotFoundException e) {
			System.out.println("Could not find File: "
					+ file.getAbsolutePath() + e.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return textInLines;
	}



	/**
	 * For the source see abstract superclass.
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String[] doc2text(String fileName) throws IOException {
		return DocConverter.erstelleTextausDoc(fileName);
		//WordDocument wd = new WordDocument(fileName);
		//StringWriter docTextWriter = new StringWriter();
		//wd.writeAllText(new PrintWriter(docTextWriter));
		//docTextWriter.close();
		//return docTextWriter.toString();
	}



	/**
	 * This will keep all images and data inline.
	 *
	 * @param filelink
	 * @return
	 */
	public static boolean doc2html(String filelink) {
		String target_filelink = Global.replaceEnding(filelink, "html");
		return doc2html(filelink, target_filelink);
	}

	public static boolean doc2html(String filelink
			, String target_filelink) {


		// Override this to make it handle images differently.
		// Currently they are embedded? TODO
		//org.apache.poi.hwpf.converter.AbstractWordConverter
		//.processImage(Element, boolean, Picture);
		try {

			// Read the Doc file
			File file = new File(filelink);
			FileInputStream fis
				= new FileInputStream(file.getAbsolutePath());

			HWPFDocument doc = new HWPFDocument(fis);

//			WordExtractor wordExtractor = new WordExtractor(fis);

			org.w3c.dom.Document domDocument
				= DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument();

			WordToHtmlConverter wordToHtmlConverter
				= new WordToHtmlConverter(domDocument);

			wordToHtmlConverter.processDocument(
					doc/*WordToHtmlUtils.loadDoc(fis)*/);

			StringWriter stringWriter = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.METHOD, "html");

			transformer.transform(new DOMSource(wordToHtmlConverter
						.getDocument()), new StreamResult(stringWriter) );

			String html = stringWriter.toString();
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(
									new File(target_filelink))
								, "UTF-8"
						)
				);

				out.write(html);

			}
			catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			finally {
				if (out != null) {
					out.close();
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}


		return true;

	}




}

