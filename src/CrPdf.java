import java.io.IOException;

import org.apache.pdfbox.examples.pdmodel.ImageToPDF;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class CrPdf {

	/**
	 * @param args
	 * @throws COSVisitorException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws COSVisitorException, IOException {
		try {
			PDDocument document = new PDDocument();
			PDPage blankPage = new PDPage();

			document.addPage( blankPage );
			PDFont font = PDType1Font.HELVETICA_BOLD;
			
			PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
			contentStream.beginText();
			contentStream.setFont( font, 12 );
			contentStream.moveTextPositionByAmount( 100, 700 );
			contentStream.drawString( "1. Erinnerung an Schulgeometrie (2 Punkte)\n" );
			contentStream.drawString(" Schreiben Sie einen Beitrag zu folgendem Thema ins Forum: \n");
			contentStream.drawString("(Hinweis: Jedes Abgabepaar schreibt entweder je einen Beitrag oder einen Beitrag ");
			contentStream.endText();
			contentStream.close();
			document.save("BlankPage.pdf");
			document.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageToPDF im = new ImageToPDF();
		String[] st = {"WiSe2012_Mathe_Muste_Uebung_.png"} ;
		ImageToPDF.main(st);

		//TODO: Use that for converting DOCX files that contain images only. i.e. extract the images and start the pdf conversion.
		im.createPDFFromImage("bla.pdf","WiSe2012_Mathe_Muste_Uebung_.png");

	}

}
