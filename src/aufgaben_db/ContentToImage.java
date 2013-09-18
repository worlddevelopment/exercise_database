package aufgaben_db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hslf.blip.Metafile.Header;

import Verwaltung.HashLog;

import HauptProgramm.DeclarationSet;
import HauptProgramm.Konverter.DocKonverter;
import HauptProgramm.Konverter.PdfKonverter;
import HauptProgramm.Konverter.RtfKonverter;

import swp.UnixComandosThread;

/**
 * This class is to be extended. Its heritage provides a function to generate
 * and access and image of the objects' "content" in string representation.  
 * 
 * @author administrator
 *
 */
public /*abstract*/ class ContentToImage {
	
	
	/*======= ATTRIBUTES ==================================================*/
//	protected HttpServletResponse response;
	protected String filelink;	//Important: This is individual for each object.
								//i.e. exercise A has a filelink different from
	                            //from exercise B or sheetdraft 1.
								//It's no static variable!! It's a difference!
	protected String filelinkPrevious;
	
    							//<-- main attribute (can be every dataformat).
	protected String[] rawContent;//Contains the lines of the file from the
								//filesystem. The rawContent for
								//sheetdrafts is not in the DB, exercises are!
								//We don't need it doubled.
	protected String[] plainText;     //<-- plain text is text only
	
	
	
	
	/*======= CONSTRUCTOR ==================================================*/
//	public ContentToImage(HttpServletResponse response, String filelink) {
//		this.response = response;
//		this.filelink = filelink;
//	}
	
	
	
	
	/*======= METHODS =====================================================*/
	
	public void generateImage() throws IOException {
		new UnixComandosThread(filelink, Global.extractPathTo(filelink))
		.d_o(getCommandListForImageConversion());
	}
	
	
	
	public List<String> getCommandListForImageConversion() {
		
		/*This is a local commandList to give over to UnixComandosThread
		 *for execution/here to create/update an image representation of
		 *this file/content. */
    	List<String> commandList = new ArrayList<String>();
    	
		//-----------------perhaps cycle over this bloc to get other previews also?--//
		//1. filelink file --> pdf file
		//PDF
		String hypo_pdf_filelink = getPDFLink();//same filename, only ending differs
    	String cmd = "libreoffice --headless -convert-to pdf " + filelink
    			+ " -outdir " + Global.extractPathTo(filelink);
    	commandList.add(cmd);
    	
		//2. pdf file --> image file
    	//IMAGE
    	String hypo_jpg_filelink = getImageLink();
        cmd = "convert -density 150 -quality 200 -resize 800x " + hypo_pdf_filelink + "[0] " + hypo_jpg_filelink;
    	commandList.add(cmd);
    	//-----------------END-OF-BLOC-PDF-TO-IMAGE----------------------------------//
		
    	
		//3. return results
		//return hypo_pdf_filelink; /*for deletion*/
    	return commandList; 		/*for joining with another commandList e.g.*/
    	
	}
	
	
	
	
	
	
	
	
	public String getImageLink() {
		return this.getImageLink(this.filelink);
	}
	public String getPDFLink() {
		return this.getPDFLink(this.filelink);
	}
	public String getImageLinkPrevious() {
		return this.getImageLink(this.filelinkPrevious);
	}
	public String getPDFLinkPrevious() {
		return this.getPDFLink(this.filelinkPrevious);
	}
	

	
	public String getFilelinkWithoutEnding() {
		//replace ending with nothing (!)
		return this.filelink.replaceAll(this.getFileEnding(this.filelink)+"$", "");
	}
	
	public String getImageLink(String file) {
		//replace ending ($ indicates that it has to end after the ending - apparently:)
		return this.filelink.replaceAll(""+this.getFileEnding(file)+"$", "jpg");
	}
	
	public String getPDFLink(String file) {
		//replace ending
		return this.filelink.replaceAll(""+this.getFileEnding(file)+"$", "pdf");
	}
	
	
	
	
	
	
	
	/*======= HELPER ===================================================== */
	//GET FILE NAME
	public String getFileName() {
		return this.getFileName(this.filelink);
	}
	public String getFileName(String file) {
		return Global.extractFilename(file);
	}
	
	//GET FILE ENDING
	public String getFileEnding() {
		return this.getFileEnding(this.filelink);
	}
	public String getFileEnding(String file) {
		return file.substring(file.lastIndexOf('.') + 1);
	}
	
	//GET FILE LINK
	public String getFilelink() {
		return filelink;
	}
	public String getFilelinkPrevious() {
		return filelinkPrevious;
	}
	
	/**
	 * Prior to setting the filelink the old value gets stored.
	 * @return
	 */
	public void setFilelink(String filelinkNew) {
		this.filelinkPrevious = this.filelink;	//store
		this.filelink = filelinkNew;			//write new to current link
	}
	
	
	
	
	
	
	

	
	
	
	//RAW CONTENT AS LINES
	public String[] getRawContent() {
		return rawContent;
	}
	//TEXT ONLY AS LINES
	public String[] getPlainText() {
		return plainText;
	}
	
	
	//RAW CONTENT AS A STRING
	public String getRawContentAsString() {
		String rawContentAsString = "";
		for (int line = 0; line < this.rawContent.length; line++) {
			rawContentAsString += this.rawContent[line];
		}
		return rawContentAsString;
	}
	//TEXT ONLY AS A STRING
	public String getPlainTextAsString() {
		String plainTextAsString = "";
		for (int line = 0; line < this.plainText.length; line++) {
			plainTextAsString += this.plainText[line];
		}
		return plainTextAsString;
	}
	
	
	//TEXT ONLY
	public String[] extractPlainText() throws FileNotFoundException, IOException {
		String ending = Global.extractEnding(filelink);
		//raw content
		this.rawContent = ReadWrite.loadText(filelink);	
		if (ending.equals("pdf")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt " +
					"es sich um ein .tex-File namens " + filelink + ".");
			this.plainText = PdfKonverter.textAusPdf(filelink);
		}
		else if (ending.equals("rtf")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein Rtf-File namens " + filelink);
			this.plainText = RtfKonverter.konvertiereRtfZuText(filelink);
			MethodenWortSuche.schreibeErstesLetztesWortAusTextInHashMap(plainText);
			if (this.getClass().toString().equals("Sheetdraft")) {
				System.out.println("Jetzt folgt noch die Weiterbehandlung des Rtf-Dokuments");
				RtfTestung.SucheIdentifierRtfNeu.bearbeiteRtf((Sheetdraft)this);
			}
		}
		else if (ending.equals("doc")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt " +
					"es sich um ein .doc-File namens " + filelink + ". \nAchtung: Falls " + 
					" eine bessere Qualitaet gewuenscht wird, kann das Dokument in Word " +
					"im RTF-Format abgespeichert und dann das rtf-File an dieses " + 
					"Programm uebergeben werden.");
			this.plainText = DocKonverter.erstelleTextausDoc(filelink);
		}
		else if (ending.equals("docx")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt " +
					"es sich um ein .docx-File namens " + filelink + ". \nAchtung: Falls " + 
					" eine bessere Qualitaet gewuenscht wird, kann das Dokument in Word " +
					"im RTF-Format abgespeichert und dann das rtf-File an dieses " + 
					"Programm uebergeben werden.");

			this.plainText = DocKonverter.erstelleTextausDocX(filelink);
		}
		else if (ending.equals("tex")) {
			// Dieser Abschnitt geht davon aus, dass im selben Ordner des �bergegeben 
			// Texfiles das kompilierte pdf mit dem selben Namen liegt.
			// Dann wird das pdf in ein Textfile umgewandelt, auf diesem 
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein TeX-Dokument namens "
					+ filelink + ".");
			//At this point a pdf file has to be generated. TODO
			this.plainText = PdfKonverter.textAusPdf(filelink.replaceAll("[.]tex$", ".pdf"));
			
		}
		else if (ending.equals("txt")) {
			this.plainText = ReadWrite.loadText(filelink);
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein Textfile namens " + filelink);
			
		}
		Global.addMessage("Extracted plain text from " + filelink + ".", "success");
		
		//finally for reuse
		return plainText;
		
	}
	
	
	public boolean isNativeFormat() {
		return Global.isFilelinkNativeFormat(filelink);
	}

	
	
	
	
	
	
	
	
}

	
	
