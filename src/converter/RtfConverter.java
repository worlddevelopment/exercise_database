package converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import aufgaben_db.Global;

/**
 * 
 * @author sabine
 * 
 *         Konverter, der Rtf-files zu Text konvertiert Ausgabeformat ist hier
 *         ein String[], welcher den Text direkt enthaelt
 */
public class RtfConverter extends TextConverter {

	public static String[] konvertiereRtfZuText(String filename) {

		try {
			RTFEditorKit rtf = new RTFEditorKit();
			javax.swing.text.Document doc = rtf.createDefaultDocument();
			
			BufferedReader input = new BufferedReader(new FileReader(filename));
			
			rtf.read(input, doc, 0);
			
			input.close();

			String text = doc.getText(0, doc.getLength());
			String[] textInLines = text.split(Global.lineSeparatorPattern);

			return textInLines;

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Falls kein Plaintext enthalten ist, wird ein leerer Array
		// zurueckgegeben:
		String[] leer = new String[1];
		leer[0] = "";
		return leer;
	}
	
	
	
    public static String rtf2text(InputStream is) throws Exception {
        DefaultStyledDocument styledDoc = new DefaultStyledDocument();
        new RTFEditorKit().read(is, styledDoc, 0);
        return styledDoc.getText(0, styledDoc.getLength());
    }
	
	
}
