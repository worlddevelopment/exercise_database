package converter;


import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;


/**
 * @author sabine, Jan R.B.-Wein, worlddevelopment
 *
 * Extract plain text from PDF.
 */
public class PdfConverter extends TextConverter {

	// ======= ATTRIBUTES
	PDFParser parser;
	String text;
	PDFTextStripper pdfStripper;
	PDDocument pdDoc;
	COSDocument cosDoc;
	PDDocumentInformation pdDocInfo;




	// ======= CONSTRUCTOR
	public PdfConverter() {
	}


	/**
	 * Get plain text split into lines as an array of String.
	 *
	 * @param filename Absolute file path
	 * @return array of String
	 */
	public static String[] textAusPdf(String filename) {

		PdfConverter pdfTextParserObj = new PdfConverter();
		String text = pdfTextParserObj.pdftoText(filename);
		String[] textInLines = text.split("\\r?\\n");

		return textInLines;
	}



	/**
	 * Extracts the plain text from a PDF document.
	 *
	 * @param fileName Absolute path to file
	 * @return plain text as String
	 */
	private String pdftoText(String fileName) {

		File f = new File(fileName);

		if (!f.isFile()) {
			System.out.println("File " + fileName + " existiert nicht.");
			return null;
		}

		try {
			parser = new PDFParser(new FileInputStream(f));
		}
		catch (Exception e) {
			System.out.println("PDF parser failed. " + e.toString()
					+ "\r\nAborting ...");
			return null;
		}

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			text = pdfStripper.getText(pdDoc);
		}
		catch (Exception e) {
			System.out.println("Error while parsing PDF file.");
			e.printStackTrace();
			try {
				if (cosDoc != null) {
					cosDoc.close();
				}
				if (pdDoc != null) {
					pdDoc.close();
				}
			}
			catch (Exception e1) {
				e.printStackTrace();
			}
			return null;
		}

		return text;
	}



	/**
	 * For the source see the abstract TextConverter.java.
	 * @param fileName
	 * @return plain text of type String
	 */
	public static String pdf2text(String fileName) {
		//return PdfKonverter.textAusPdf(fileName);
		PDFParser parser;
		String parsedText;
		PDFTextStripper pdfStripper;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		File f = new File(fileName);
		try {
			parser = new PDFParser(new FileInputStream(f));
		}
		catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			return null;
		}
		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
			cosDoc.close();
			pdDoc.close();
		}
		catch (Exception e) {
			System.out.println("An error occurred while parsing PDF.");
			e.printStackTrace();
			try {
				if (cosDoc != null) {
					cosDoc.close();
				}
				if (pdDoc != null) {
					pdDoc.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}
		System.out.println("Done.");
		return parsedText;
	}


}

