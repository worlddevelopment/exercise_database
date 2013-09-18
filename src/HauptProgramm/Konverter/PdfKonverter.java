package HauptProgramm.Konverter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;

/**
 * 
 * @author sabine
 *
 *Konverter, der Pdf-files zu Text konvertiert
 *Ausgabeformat ist hier ein String[], welcher den Text direkt enthaelt
 *
 *Der Code wurde so uebernommen und nur modifiziert
 */


public class PdfKonverter {
    
    PDFParser parser;
    String text;
    PDFTextStripper pdfStripper;
    PDDocument pdDoc;
    COSDocument cosDoc;
    PDDocumentInformation pdDocInfo;
    
    //Konstructor 
    public PdfKonverter() {
    }
    
    // eigentliche Methode, welche vom Hauptprogramm aufgerufen wird
    
    /**
     * 
     * Ruft PdfKonverter.pdftoText(String fileName) auf, um den Text aus einem pdf-Dokument zu extrahieren.
     * Trennt die Zeilen und speichert sie einzeln in einem String-Array 
     * 
     * @param filename Absoluter Pfad des zu konvertierenden pdfs
     * @return String-Array, das die Zeilen des im pdf enthaltenen Textes beinhaltet
     */
    public static String[] textAusPdf(String filename) {
    	
    	PdfKonverter pdfTextParserObj = new PdfKonverter();
        String text = pdfTextParserObj.pdftoText(filename);
        String[] textInLines = text.split("\\r?\\n");

        return textInLines;
    }
    
    
    
    //interne Methode, extrahiert Text aus dem PDF Document, wird von textAusPdf() aufgerufen
    
    /**
     * 
     * Extrahiert den Text aus einem PDF Document und gibt ihn aus
     * 
     * @param fileName Absoluter Ort der pdf-Datei
     * @return String, der den in der pdf enthaltenen Text enth�lt
     */
    private String pdftoText(String fileName) {
        
        System.out.println("PDF-Konverter aufgerufen fuer: " + fileName);
        File f = new File(fileName);
        
        if (!f.isFile()) {
            System.out.println("Das File " + fileName + " existiert nicht.");
            return null;
        }
        
        try {
            parser = new PDFParser(new FileInputStream(f));
        } catch (Exception e) {
            System.out.println("Der PDF Parser laesst sich nicht oeffnen.");
            return null;
        }
        
        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            text = pdfStripper.getText(pdDoc); 
        } catch (Exception e) {
            System.out.println("Fehler beim Parsen!");
            e.printStackTrace();
            try {
                   if (cosDoc != null) cosDoc.close();
                   if (pdDoc != null) pdDoc.close();
               } catch (Exception e1) {
               e.printStackTrace();
            }
            return null;
        }      
        System.out.println("Fertig!!!!");
        return text;
    }
    
   
   

}
    
    

