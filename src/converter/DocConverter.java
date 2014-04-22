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
 *  Klasse zum Konvertieren von Doc und DocX-Dokumenten in Text-Files
 * @author sabine
 *
 *Methoden wurden von Rainer uebernommen
 *Rueckgaben bisher void, da .txt file erstellt wird
	dies laesst sich aber noch aendern und an pdf und rtf
	angleichen, dann waere kein ausgabeName mehr noetig
	und der Code waere kuerzer
 *Oder aber wir aendern noch die pdf und rtf einlesetechnik
 */

public class DocConverter extends TextConverter {

	
	
	// Konvertiert .docX-file in .txt-file und speichert dieses
	
	/**
	 * Extrahiert den Plaintext aus einem .doc-Dokument und gibt ihn Als String[]
	 * (eine Zeile pro Feld) aus.
	 * 
	 * @param filename Pfad der Datei
	 * @return String[] mit dem Plaintext
	 * @throws FileNotFoundException Wenn der Pfadname ungueltig ist.
	 * @throws IOException Wenn der HWPFDocument converter nicht funktioniert.
	 */
	public static String[] erstelleTextausDoc(String filelink) throws FileNotFoundException, IOException{
		
		File file = null;
		String[] textInLines = null;
		HWPFDocument doc = null;
		try {
			// Read the Doc file
			file = new File(filelink);
			System.out.println("Doc-File gelesen");
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			System.out.println("FileStream generiert");	
			doc = new HWPFDocument(fis);
			System.out.println("HWPFDoc generiert");
			WordExtractor ex = new WordExtractor(doc);
			System.out.println("WordExtractor generiert");
			String text = ex.getText();
			System.out.println("Text extrahiert");
			
	        textInLines = text.split("\\r?\\n");
			
			//write the text in txt file
			ReadWrite.write(text, file.getAbsolutePath().replaceAll(Global.extractEnding(filelink) + "$", "txt"));
//			File fil = new File (filename + ausgabeName);
//			System.out.println("Output File generiert");
//			Writer output = new BufferedWriter(new FileWriter(fil));
//			System.out.println("BufferedWriter erzeugt");
//			output.write(text);
//			System.out.println("Output geschrieben");
//			output.close();
//			System.out.println("Output geschlossen");

		} catch (FileNotFoundException e) {
			System.out.println("File nicht gefunden - Fehler!: " + e.toString());
		} catch (IOException e) {
			System.out.println("Anderer Fehler!");
		}
		
		
		return textInLines;
	}


	// Konvertiert .docX-file in .txt-file und speichert dieses

	
	/**
	 * Extrahiert den Plaintext aus einem .docx-Dokument und gibt ihn Als String[]
	 * (eine Zeile pro Feld) aus.
	 * 
	 * @param filelink Pfad der Datei
	 * @return String[] mit dem Plaintext
	 * @throws FileNotFoundException Wenn der Pfadname ungueltig ist.
	 * @throws IOException Wenn der XHWPFDocument converter nicht funktioniert.
	 */
public static String[] erstelleTextausDocX(String filelink)throws FileNotFoundException, IOException{
		
		File file = null;
		String[] textInLines = null;
		XWPFDocument doc = null;
		
		try {
			// Read the Doc file
			file = new File(filelink);
			System.out.println("DocX-File gelesen");
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			System.out.println("FileStream generiert");
			doc = new XWPFDocument(fis);
			
			System.out.println("XWPFDoc generiert");
			XWPFWordExtractor ex = new XWPFWordExtractor(doc);
			System.out.println("WordExtractor generiert");
			String text = ex.getText();
			System.out.println("Text extrahiert");
			
			textInLines = text.split(Global.lineSeparatorPattern);
			
			//write the text in txt file
			ReadWrite.write( text, Global.replaceEnding(file.getAbsolutePath(), "txt") );
//			File fil = new File (filename + ausgabeName);
//			System.out.println("Output File generiert");
//			Writer output = new BufferedWriter(new FileWriter(fil));
//			System.out.println("BufferedWriter erzeugt");
//			output.write(text);
//			System.out.println("Output geschrieben");
//			output.close();
//			System.out.println("Output geschlossen");
//			
		} catch (FileNotFoundException e) {
			System.out.println("Could not find File: " + file.getAbsolutePath() + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textInLines;
	}












	/**
	 * For the source see abstract superclass.
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String[] doc2text(String fileName) throws IOException {
		return DocConverter.erstelleTextausDoc(fileName);
	//    WordDocument wd = new WordDocument(fileName);
	//    StringWriter docTextWriter = new StringWriter();
	//    wd.writeAllText(new PrintWriter(docTextWriter));
	//    docTextWriter.close();
	//    return docTextWriter.toString();
	}



	/**
	 * This will keep all images and data inline.
	 * @param filelink
	 * @return
	 */
	public static boolean doc2html(String filelink) {
		String target_filelink = Global.replaceEnding(filelink, "html");
		return doc2html(filelink, target_filelink);
	}
	public static boolean doc2html(String filelink, String target_filelink) {
		
		
		// Overwrite this to make it handle images differently. Currently they are embedded? TODO
		//org.apache.poi.hwpf.converter.AbstractWordConverter.processImage(Element, boolean, Picture);
		try {
			
			// Read the Doc file
			File file = new File(filelink);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			
			HWPFDocument doc = new HWPFDocument(fis);
			
//			WordExtractor wordExtractor = new WordExtractor(fis);
			
			org.w3c.dom.Document domDocument =  DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(domDocument);
			wordToHtmlConverter.processDocument(doc/*WordToHtmlUtils.loadDoc(fis)*/);
			
			
			StringWriter stringWriter = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty( OutputKeys.INDENT, "yes");
			transformer.setOutputProperty( OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty( OutputKeys.METHOD, "html");
			
			
			transformer.transform(new DOMSource(wordToHtmlConverter.getDocument()), new StreamResult(stringWriter) );

			
			String html = stringWriter.toString();
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(new File(target_filelink)), "UTF-8"
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

