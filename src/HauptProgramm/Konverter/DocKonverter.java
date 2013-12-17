package HauptProgramm.Konverter;

import java.io.*;

import org.apache.poi.hwpf.*;
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

public class DocKonverter {

	
	
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
//			
		} catch (FileNotFoundException e) {
			System.out.println("File nicht gefunden - Fehler!: " + e.toString());
		} catch (Exception e) {
			System.out.println("Anderer Fehler!");
		}
		return textInLines;
	}
}

