package aufgaben_db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;

import org.docx4j.TextUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import command.Command;

import converter.DocConverter;
import converter.PdfConverter;
import converter.RtfConverter;
import converter.TextConverter;

import db.UnixComandosThread;
import docx4j_library.ConvertOutHtml;
import docx4j_library.ConvertOutPDF;



import Verwaltung.HashLog;



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
	protected boolean filelinkHasToBeReGenerated;
	
    							//<-- main attribute (can be every dataformat).
	//protected String[] rawContent;//Contains the lines of the file from the
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
	
	public void generateImage() throws Exception {
		int height = 800;//"<width>x<height> = x800";
		// Try to derive optimal height from line count of plainText:
		if (this.plainText == null) {
			this.extractPlainText();
		}
		height = this.plainText.length * (/*font.getSize()*/15 + 20);
		if (this.plainText.length == 1) {
			height = this.getPlainTextAsString().length() * (/*font.getSize()*/15 + 20) / Global.charactersPerLine;
		}
		new UnixComandosThread(filelink, Global.extractPathTo(filelink))
		.d_o(getCommandListForImageConversion(height));
	}
	
	
	public Command[] /*List<String>*/ getCommandListForImageConversion() {
		return getCommandListForImageConversion(800);//no change in height - anyway we don't need it
		//anymore as we now use trim option of ImageMagick. A great sorceress. :)
	}
	public Command[] /*List<String>*/ getCommandListForImageConversion(int heightToCropTo) {
		
		/*This is a local commandList to give over to UnixComandosThread
		 *for execution/here to create/update an image representation of
		 *this file/content. */
//    	List<String> commandList = new ArrayList<String>();<--not suitable because of ordering probs. 
    	Command[] commandList = new Command[10];
		
		//-----------------perhaps cycle over this bloc to get other previews also?--//
		//1. filelink file --> pdf file
		//PDF
		String hypo_pdf_filelink = getPDFLink();//same filename, only ending differs
		/* From all our tools used it's most likely that libreoffice (JODConverter for performance 
		   & cross-platformness)  can handle this format.
		   */
		commandList[0] = UnixComandosThread.getCommand_usingLibreOffice(filelink, "pdf");
    	
		//2. pdf file --> image file
    	//IMAGE
    	String options = "";
    	Global.ImageMagick_qualityScale = 2;//because strangely when using crop or trim we have quality issues
    	//while we don't have these issues at the same settings for the original pdf.
    	int offsetY = 100 * Global.ImageMagick_qualityScale;//by 100px from the upper edge
    	if (heightToCropTo != -1 && Global.cropImages) {
    		options = " -crop " /*+ width*/ + "x" + (heightToCropTo * Global.ImageMagick_qualityScale) + "+0+" + offsetY /*+ "\\! "*//* + unit*/ + " ";
    		//options = " -trim ";//first we resize to x800pixel in height to have an idea for the target height
    	}
    	commandList[1] = UnixComandosThread.getCommand_pdf2image_usingImageMagick(getPDFLink(), getImageLink(), options);
    	
    	
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
	
	public String getImageLink(String filelink) {
		//replace ending ($ indicates that it has to end after the ending - apparently:)
		//return filelink.replaceAll("[.][a-zA-Z]+$", "." + ending);
		//return this.filelink.replaceAll(""+this.getFileEnding(file)+"$", Global.imageTypeToGenerate);
		return filelink + "." + Global.imageTypeToGenerate;/*Changed with Update v30.83 !!
		because we need images to each flavour to see the differences!*/
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
	public String getFilelinkRelative() {
		return Global.removeRootPath(filelink);
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
	
	
	
	//GET FILE LINK CANONICAL
	public String getFilelinkCanonical() throws IOException {
		return new File(filelink).getCanonicalFile().getPath();
	}
	
	
	
	
	
	

	
	
	
	//RAW CONTENT AS LINES
	public String[] getRawContent() throws IOException {
		//return rawContent;
		return ReadWrite.readInputStreamAsLines(this.filelink);
	}
	//TEXT ONLY AS LINES
	public String[] getPlainText() {
		return plainText;
	}
	
	//RAW CONTENT AS A STRING
	public String getRawContentAsString() throws IOException {
		String rawContentAsString = "";
		String[] rawContent = getRawContent();
		for (int line = 0; line < rawContent.length; line++) {
			rawContentAsString += /*"\r\n"*/System.getProperty("line.separator") + rawContent[line];
		}
		return rawContentAsString;
	}
	//TEXT ONLY AS A STRING
	public String getPlainTextAsString() {
		String plainTextAsString = "";
		for (int line = 0; line < this.plainText.length; line++) {
			plainTextAsString += /*"\r\n"*/System.getProperty("line.separator") + this.plainText[line];
		}
		return plainTextAsString;
	}
	
	
	
	
	
	
	
	//CREATE FLAVOURS
	/**
	 * 
	 * @return All successfully created flavours.
	 * @throws Exception
	 */
	public List<String> createFlavours() throws Exception {

		//the order in which the flavours were generated here plays no role:
		List<String> generatedFlavours = new ArrayList<String>();
		/*If DocType instead of the flavour_filelinks are stored, redundancy may occur.*/
		
		//all supported filetypes are the flavours that can ideally be created
		for (DocType docType : DocType.values()) {
			
			if (docType.name().equals(Global.extractEnding(filelink))) {
				//The native format of this sheet can be skipped happily.
				continue ;
			}
			
			//WITH THE CHANGE TO THE BACHELOR THESIS, HTML IS THE LONE STORAGE FORMAT. 
			if (!docType.equals(Aufgaben_DB.commonFormat)) {//DocType.HTML)) { <- as of v31.13d this became dynamic.
				continue ;
			}
			
			generatedFlavours.addAll(
					Global.convertFile(filelink, docType.name().toLowerCase())
			);
			
		}

		
		Global.addMessage("Created as many flavours as possible to an old alchemist."
				+ " The main ingredients were: " + filelink + " .", "success");
		
		//finally for reuse
		return generatedFlavours;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	//EXTRACT PLAIN TEXT ONLY (only the visible non-markup-non-binary text)
	/**
	 * There are two methods how to accomplish text conversion:
	 * 1) Convert each file type to PDF. Then extract plain text of this generated PDF.
	 * 2) Extract plain text directly out of the XML or any other file type.
	 * @throws Exception 
	 */
	public String[] extractPlainText() throws Exception {
		String ending = Global.extractEnding(filelink);
		//raw content TODO INVESTIGATE IF REALLY NECESSARY TO KEEP A COPY IN THE OBJECTS.
		//            BTW this is done in the method extractRawContent().
		//DOC
		if (ending.equals("doc")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt " +
					"es sich um ein .doc-File namens " + filelink + ". \nAchtung: Falls " + 
					" eine bessere Qualitaet gewuenscht wird, kann das Dokument in Word " +
					"im RTF-Format abgespeichert und dann das rtf-File an dieses " + 
					"Programm uebergeben werden.");
			this.plainText = DocConverter.erstelleTextausDoc(filelink);
		}
		//DOCX
		else if (ending.equals("docx")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt " +
					"es sich um ein .docx-File namens " + filelink + ". \nAchtung: Falls " + 
					" eine bessere Qualitaet gewuenscht wird, kann das Dokument in Word " +
					"im RTF-Format abgespeichert und dann das rtf-File an dieses " + 
					"Programm uebergeben werden.");

			this.plainText = DocConverter.erstelleTextausDocX(filelink);
			
			//docx4j alternative:
//WORKING BUT SUPERFLUOUS AND ALL IN ONE SINGLE LINE			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(filelink));
//			MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
//			org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)mainDocumentPart.getJaxbElement();
//
//			String txt_filelink = filelink + ".txt";//Global.replaceEnding(filelink, "txt");
//			Writer out = new OutputStreamWriter(new FileOutputStream(txt_filelink)); 
//			TextUtils.extractText(wmlDocumentEl, out);
//			//out.flush();
//			out.close();
//			this.plainText = ReadWrite.loadText(filelink + ".txt");//Global.replaceEnding(filelink, "txt"));

			
//WORKING			txt_filelink = Global.replaceEnding(filelink, "txt.txt");
//			out = new OutputStreamWriter(new FileOutputStream(txt_filelink)); 
//			//this.plainText = TextConverter.docx2text(filelink).split(System.getProperty("line.separator"));//(wmlDocumentEl, out);
//			this.plainText = TextConverter.docx2txt(filelink);//(wmlDocumentEl, out);
//			//out.flush();
//			out.close();
			//this.plainText = ReadWrite.loadText(filelink + ".txt");//Global.replaceEnding(filelink, "txt"));
			
		}
		//HTML
		else if (ending.equals("html") || ending.equals("htm")) {
			//Either remove html tags. (using e.g. a php striptags() Java equivalent.) or DOM:
			this.plainText = TextConverter.html2text(filelink).split(System.getProperty("line.separator"));
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein HTML-file namens " + filelink);
		}
		//ODF (OpenDocumentFormat: Text => ODT)
		else if (ending.equals("odt")) {
			//At this point a pdf file has to exist in the same folder as the uploaded file.
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein OpenDocumentFormat:Text-file namens " + filelink);
			
			//Method 1) At this point a pdf file has to be generated.
			//TODO
			//this.plainText = PdfKonverter.textAusPdf(filelink.replaceAll("[.]odt$", ".pdf"));
			
			//Method 2)
			this.plainText = TextConverter.odt2text(filelink).split(System.getProperty("line.separator"));
			ReadWrite.write( getPlainTextAsString(), Global.replaceEnding(filelink, "txt") );
			
		}
		//IMAGE
		else if (ending.equals("jpg") || ending.equals("png") || ending.equals("svg")) {
//			org.apache.pdfbox.TextToPDF;
//			org.apache.pdfbox.examples.pdmodel.ImageToPDF;<-- only putting images into the PDF. 
			//TODO
//			 //http://stackoverflow.com/questions/1312832/java-image-encoding-in-xml	
//			 //  ENCODING
//			 BufferedImage img = ImageIO.read(new File("image.png"));    
//			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			 ImageIO.write(img, "png", baos);    
//			 baos.flush();
//			 String encodedImage = Base64.encodeToString(baos.toByteArray());
//			 baos.close(); // should be inside a finally block
//			 node.setTextContent(encodedImage); // store it inside node
//
//			 // DECODING
//			 String encodedImage = node.getTextContent();
//			 byte[] bytes = Base64.decode(encodedImage);
//			 BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
			this.plainText = PdfConverter.textAusPdf(filelink);
		}
		//PDF
		else if (ending.equals("pdf")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt " +
					"es sich um ein .tex-File namens " + filelink + ".");
			this.plainText = PdfConverter.textAusPdf(filelink);
			//ReadWrite.write( getPlainTextAsString(), Global.replaceEnding(filelink, "txt") );
		}
		//RTF
		else if (ending.equals("rtf")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein Rtf-File namens " + filelink);
			this.plainText = RtfConverter.konvertiereRtfZuText(filelink);
			MethodenWortSuche.schreibeErstesLetztesWortAusTextInHashMap(plainText);
			if (this.getClass().toString().equals("Sheetdraft")) {/*Sheetdraft only, not Exercise*/
				System.out.println("Jetzt folgt noch die Weiterbehandlung des Rtf-Dokuments");
				rtf.SucheIdentifierRtfNeu.bearbeiteRtf((Sheetdraft)this);
			}
		}
		//TEX
		else if (ending.equals("tex")) {
			// Dieser Abschnitt geht davon aus, dass im selben Ordner des uebergegeben 
			// Texfiles das kompilierte pdf mit dem selben Namen liegt.
			// Dann wird das pdf in ein Textfile umgewandelt, auf diesem 
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein TeX-Dokument namens "
					+ filelink + ".");
			//Method 2)
			String filelink_absolute_txt;
			filelink_absolute_txt = Global.replaceEnding(filelink, "txt");
			final Command[] cmdList = {
					UnixComandosThread.getCommand_tex2txt(filelink, filelink_absolute_txt)
			};
			new UnixComandosThread(filelink).d_o(cmdList);			
			this.plainText = ReadWrite.loadText(filelink_absolute_txt);
			
			//Method 1)
			//At this point a pdf file has to be generated.
			//TODO e.g. using TexManipulator.java and portable LaTex in root directory.
			//this.plainText = PdfKonverter.textAusPdf(filelink.replaceAll("[.]tex$", ".pdf"));
//			this.rawContent = ReadWrite.loadText(filelink);
			
		}
		//TXT
		else if (ending.equals("txt")) {
			this.plainText = ReadWrite.loadText(filelink);
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein Textfile namens " + filelink);
			
		}
		Global.addMessage("Extracted sheet/draft's plain text from " + filelink + ".", "success");
		
		
		//finally for reuse
		return plainText;
		
	}
	
	
	
	
	/**
	 * EXTRACT RAW CONTENT DEPENDING ON FILETYPE
	 * 
	 * This is useful only for .tex and other filetypes that use a simple markup
	 * like xml or similar.
	 * Effectively it loads the file contents, often resulting in a blob/binary
	 * or other mostly difficult to further parse file types (for doc/binary, docx/odt/rtf/zip).
	 * 
	 * Old description:
	 * If the document is a docx-Zip/Archive for example, the content of 
	 * word/document.xml will be loaded. This is no longer true as this 
	 * method has been abandoned due to keeping the raw/native format 
	 * in the filesystem only! (single source principle here)
	 * 
	 * @throws IOException 
	 * @throws ZipException 
	 * 
	 */
	public static String[] extractRawContentDependingOnFiletype(String filelink)
			throws ZipException, IOException {
		
		String[] rawDocumentContentOfFileArchive = null;
		
		//Reads the input stream depending on filetype/ending.
		rawDocumentContentOfFileArchive	= ReadWrite.readInputStreamAsLines(filelink);
		
		return rawDocumentContentOfFileArchive;
	}
	
	
	
	
	
	//IS NATIVE FORMAT
	public boolean isNativeFormat() {
		return Global.isFilelinkNativeFormat(filelink);
	}

	
	
	
	@Override
	public String toString() {
		return this.getClass() + " [filelink:" + this.getFilelinkRelative() + "; isNativeFormat:" + this.isNativeFormat() + "]"; 
	}
	
	
	
}

	
	
