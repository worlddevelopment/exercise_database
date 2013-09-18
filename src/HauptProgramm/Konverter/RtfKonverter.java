package HauptProgramm.Konverter;


import java.io.*;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.text.*;

/**
 * 
 * @author sabine
 * 
 *Konverter, der Rtf-files zu Text konvertiert
 *Ausgabeformat ist hier ein String[], welcher
 *den Text direkt enthaelt
 */
//

public class RtfKonverter {
	

	public static String[] konvertiereRtfZuText(String filename) {
	   
		 try {
	      RTFEditorKit rtf = new RTFEditorKit();
	      Document doc = rtf.createDefaultDocument();
	      BufferedReader input = new BufferedReader(new FileReader(filename));
	      rtf.read(input,doc,0);
	      input.close();
	      
	      String text = (doc.getText(0,doc.getLength()));
	      String[] textInLines = text.split("\\r?\\n");

	      return textInLines;
	      
	   } catch (Exception e) {
	      e.printStackTrace();
	   }
	   
	   // Falls kein Plaintext enthalten ist, wird ein leerer Array zurueckgegeben:
	   String[] leer = new String[1];
	   leer[0] = "";
	   return leer;
	}
}
