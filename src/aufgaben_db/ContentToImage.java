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
 * This class is to be extended. Its heritage provides a function
 * to generate and access an image of the objects' "content"
 * in string representation.
 *
 */
public /*abstract*/ class ContentToImage {


	/*======= ATTRIBUTES
//	protected HttpServletResponse response;
	/**
	 * The filelink, an identifier for each object. It's unique!
	 * i.e. exercise A has a filelink different from
	 * exercise B or sheetdraft 1.
	 * It's not a static variable!
	 */
	protected String filelink;
	/**
	 * The previous filelink.
	 */
	protected String filelinkPrevious;
	/**
	 * Whether the filelink has to be regenerated.
	 */
	protected boolean filelinkHasToBeReGenerated;


	//protected String[] rawContent;
	/**
	 * The lines of the file from the filesystem. The rawContent for
	 * sheetdrafts is not in the DB, exercises are!
	 * It's not needed twice.
	 */
	protected String[] plainText; //<-- plain text is text only




	// ======= CONSTRUCTOR
//	public ContentToImage(HttpServletResponse response, String filelink) {
//		this.response = response;
//		this.filelink = filelink;
//	}




	// ======= METHODS

	/**
	 * Generate Image
	 */
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



	/**
	 * Get command list for image conversion.
	 *
	 * @return An array of Command objects.
	 */
	public Command[] getCommandListForImageConversion() {
		return getCommandListForImageConversion(800);//no change in height - anyway we don't need it
		//anymore as we now use trim option of ImageMagick. A great sorceress. :)
	}



	/**
	 * Get command list for image conversion.
	 *
	 * @param heightToCropTo The height to crop the image to.
	 * @return An array of Command objects.
	 */
	public Command[] getCommandListForImageConversion(
			int heightToCropTo) {
		/*
		This is a local commandList to give over to UnixComandosThread
		for execution. Here it serves to create/update an image
		representation of this file's content.
		*/
		Command[] commandList = new Command[10]; // Array maintains order!

		// ------- Cycle over this bloc to get other previews also?
		// 1. filelink file --> pdf file
		// PDF
		String hypo_pdf_filelink = getPDFLink();// only ending differs
		/*
		From all the tools used it's most likely that libreoffice
		(JODConverter for performance & cross-platform support) can
		handle this format.
		*/
		commandList[0] = UnixComandosThread.getCommand_usingLibreOffice(filelink, "pdf");

		// 2. pdf file --> image file
		// IMAGE
		String options = "";
		// Because strangely when using crop|trim quality issues arise
		// while we don't have issues at the same settings for the
		// original PDF file.
		Global.ImageMagick_qualityScale = 2;
		// Multiply by 100px from the upper edge:
		int offsetY = 100 * Global.ImageMagick_qualityScale;
		if (heightToCropTo != -1 && Global.cropImages) {
			options = " -crop " /*+ width*/ + "x"
				+ (heightToCropTo * Global.ImageMagick_qualityScale)
				+ "+0+" + offsetY /*+ "\\! "*//* + unit*/ + " ";
			//options = " -trim ";// First we resize to x800pixel in
			// height to have an idea for the target height.
		}
		commandList[1] = UnixComandosThread.getCommand_pdf2image_usingImageMagick(getPDFLink(), getImageLink(), options);
		// ------- END-OF-BLOC-PDF-TO-IMAGE

		// 3. return results
		//return hypo_pdf_filelink; // for deletion
		return commandList; // for joining with another commandList e.g.

	}



	/**
	 * Get image link.
	 *
	 * @return The image link that corresponds to the current filelink.
	 */
	public String getImageLink() {
		return this.getImageLink(this.filelink);
	}



	/**
	 * Get PDF link.
	 *
	 * @return The PDF link that corresponds to the current filelink.
	 */
	public String getPDFLink() {
		return this.getPDFLink(this.filelink);
	}



	/**
	 * Get the previous image link.
	 *
	 * @return The image link that did correspond to the current
	 * filelink previously.
	 */
	public String getImageLinkPrevious() {
		return this.getImageLink(this.filelinkPrevious);
	}


	/**
	 * Get the previous PDF link.
	 *
	 * @return The PDF link that did correspond to the current
	 * filelink previously.
	 */
	public String getPDFLinkPrevious() {
		return this.getPDFLink(this.filelinkPrevious);
	}



	/**
	 * Get filelink without ending.
	 *
	 * @return The filelink without ending.
	 */
	public String getFilelinkWithoutEnding() {
		// replace ending with nothing (!)
		return this.filelink.replaceAll(this.getFileEnding(this.filelink)+"$", "");
	}



	/**
	 * Get image link.
	 *
	 * @param filelink
	 */
	public String getImageLink(String filelink) {
		// replace ending ($ indicates that it has to end after
		// the ending - apparently:)
		//return filelink.replaceAll("[.][a-zA-Z]+$", "." + ending);
		//return this.filelink.replaceAll(
		//""+this.getFileEnding(file)+"$", Global.imageTypeToGenerate);
		return filelink + "." + Global.imageTypeToGenerate;
		/*
		Changed with Update v30.83 because
		we need images to each flavour to see the differences!
		*/
	}

	public String getPDFLink(String file) {
		// replace ending
		return this.filelink.replaceAll(""+this.getFileEnding(file)+"$", "pdf");
	}







	/*======= HELPER
	// GET FILE NAME
	public String getFileName() {
		return this.getFileName(this.filelink);
	}
	public String getFileName(String file) {
		return Global.extractFilename(file);
	}

	// GET FILE ENDING
	public String getFileEnding() {
		return this.getFileEnding(this.filelink);
	}
	public String getFileEnding(String file) {
		return file.substring(file.lastIndexOf('.') + 1);
	}

	// GET FILE LINK
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
	 *
	 * @param filelinkNew
	 */
	public void setFilelink(String filelinkNew) {
		this.filelinkPrevious = this.filelink; // store
		this.filelink = filelinkNew; // write new to current link
	}



	/**
	 * Get file link canonical.
	 */
	public String getFilelinkCanonical() throws IOException {
		return new File(filelink).getCanonicalFile().getPath();
	}



	/**
	 * Raw content as lines
	 */
	public String[] getRawContent() throws IOException {
		//return rawContent;
		return ReadWrite.readInputStreamAsLines(this.filelink);
	}



	/**
	 * Get text only - as lines.
	 *
	 * @return An array of plain text lines.
	 */
	public String[] getPlainText() {
		return plainText;
	}



	/**
	 * Get raw content as a string.
	 *
	 * @throws IOException
	 * @return raw content as string
	 */
	public String getRawContentAsString() throws IOException {
		String rawContentAsString = "";
		String[] rawContent = getRawContent();
		for (int line = 0; line < rawContent.length; line++) {
			rawContentAsString += /*"\r\n"*/System.getProperty("line.separator") + rawContent[line];
		}
		return rawContentAsString;
	}



	/**
	 * Text only as a string.
	 *
	 * @return plain text as string
	 */
	public String getPlainTextAsString() {
		String plainTextAsString = "";
		for (int line = 0; line < this.plainText.length; line++) {
			plainTextAsString += /*"\r\n"*/System.getProperty("line.separator") + this.plainText[line];
		}
		return plainTextAsString;
	}



	/**
	 * Create flavours
	 * @return All successfully created flavours.
	 * @throws Exception
	 */
	public List<String> createFlavours() throws Exception {

		// The order the flavours were generated in, here plays no role:
		List<String> generatedFlavours = new ArrayList<String>();
		// If DocType instead of the flavour_filelinks are stored,
		// redundancy may occur.

		// All supported filetypes are the flavours that can ideally
		// be created:
		for (DocType docType : DocType.values()) {

			if (docType.name().equals(Global.extractEnding(filelink))) {
				// Can skip the native format of this sheet happily.
				continue ;
			}

			// WITH THE CHANGE TO THE BACHELOR THESIS, HTML IS THE LONE
			// STORAGE FORMAT.
			if (!docType.equals(Aufgaben_DB.commonFormat)) {
				// <- as of v31.13d this became dynamic.
				continue ;
			}

			generatedFlavours.addAll(
					Global.convertFile(filelink
						, docType.name().toLowerCase())
			);

		}


		Global.addMessage("Created as many flavours as possible to an"
				+ " old alchemist."
				+ " The main ingredients were: " + filelink + " ."
				, "success");

		// Finally for reuse
		return generatedFlavours;


	}










	// EXTRACT PLAIN TEXT (only the visible non-markup-non-binary text)
	/**
	 * There are two methods how to accomplish text conversion:
	 * 1) Convert each file type to PDF. Then extract plain text of it.
	 * 2) Extract plain text directly out of the XML or other file type.
	 *
	 * @throws Exception
	 */
	public String[] extractPlainText() throws Exception {
		String ending = Global.extractEnding(filelink);
		// Raw content
		//TODO Investigate if really necessary to keep a copy in objects
		// By the way this is done in the method extractRawContent().
		HashLog.erweitereLogFile(
					"File: " + filelink + ".\nHint: Save as RTF for a"
					+ " better quality plain text extraction."
					);
		// DOC
		if (ending.equals("doc")) {
			this.plainText = DocConverter.erstelleTextausDoc(filelink);
		}
		// DOCX
		else if (ending.equals("docx")) {
			this.plainText = DocConverter.erstelleTextausDocX(filelink);

			// docx4j alternative:
			// WORKING BUT SUPERFLUOUS AND ALL IN ONE SINGLE LINE
			//WordprocessingMLPackage wordMLPackage
			//	= WordprocessingMLPackage.load(new File(filelink));
			//MainDocumentPart mainDocumentPart
			//	= wordMLPackage.getMainDocumentPart();
			//org.docx4j.wml.Document wmlDocumentEl
			//	= (org.docx4j.wml.Document)mainDocumentPart
			//		.getJaxbElement();
			//
			//String txt_filelink = filelink + ".txt";//Global.replaceEnding(filelink, "txt");
			////txt_filelink = Global.replaceEnding(filelink, "txt");
			//Writer out = new OutputStreamWriter(
			//		new FileOutputStream(txt_filelink));
			//TextUtils.extractText(wmlDocumentEl, out);
			////out.flush();
			//out.close();
			//this.plainText = ReadWrite.loadText(txt_filelink);


			// WORKING
			//txt_filelink = Global.replaceEnding(filelink, "txt.txt");
			//out = new OutputStreamWriter(
			//		new FileOutputStream(txt_filelink));
			////this.plainText=TextConverter.docx2text(filelink).split(
			//System.getProperty("line.separator"));(wmlDocumentEl, out)
			//this.plainText = TextConverter.docx2txt(filelink);
			////out.flush();
			//out.close();
			//this.plainText = ReadWrite.loadText(txt_filelink);

		}
		// HTML
		else if (ending.equals("html") || ending.equals("htm")) {
			// Either remove HTML tags (using e.g. a php striptags()
			// Java equivalent) or DOM:
			this.plainText = TextConverter.html2text(filelink).split(
					System.getProperty("line.separator"));
		}
		//ODF (OpenDocumentFormat: Text => ODT)
		else if (ending.equals("odt")) {
			// Method 1: At this point a pdf file has to be generated.
			//TODO
			//this.plainText = PdfKonverter.textAusPdf(filelink.replaceAll("[.]odt$", ".pdf"));

			// Method 2:
			this.plainText = TextConverter.odt2text(filelink)
				.split(System.getProperty("line.separator"));
			ReadWrite.write(getPlainTextAsString()
					, Global.replaceEnding(filelink, "txt"));

		}
		// IMAGE
		else if (ending.equals("jpg") || ending.equals("png")
				|| ending.equals("svg")) {
//			org.apache.pdfbox.TextToPDF;
//			// How to put images into a PDF file:
//			org.apache.pdfbox.examples.pdmodel.ImageToPDF;
			//TODO Real image text detection.
//			//http://stackoverflow.com/questions/1312832/java-image-encoding-in-xml
//			// ENCODING
//			BufferedImage img = ImageIO.read(new File("image.png"));
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(img, "png", baos);
//			baos.flush();
//			String encodedImage = Base64.encodeToString(baos.toByteArray());
//			baos.close(); // should be inside a finally block
//			node.setTextContent(encodedImage); // store it inside node
//
//			// DECODING
//			String encodedImage = node.getTextContent();
//			byte[] bytes = Base64.decode(encodedImage);
//			BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
			this.plainText = PdfConverter.textAusPdf(filelink);
		}
		// PDF
		else if (ending.equals("pdf")) {
			this.plainText = PdfConverter.textAusPdf(filelink);
			//ReadWrite.write( getPlainTextAsString(), Global.replaceEnding(filelink, "txt") );
		}
		// RTF
		else if (ending.equals("rtf")) {
			this.plainText = RtfConverter.konvertiereRtfZuText(filelink);
			MethodenWortSuche.schreibeErstesLetztesWortAusTextInHashMap(
					plainText);
			if (this.getClass().toString().equals("Sheetdraft")) {
				// Sheetdraft only! Not meant for exercises:
				System.out.println("Further handle the RTF file:");
				rtf.SucheIdentifierRtfNeu.bearbeiteRtf((Sheetdraft)this);
			}
		}
		// TEX
		else if (ending.equals("tex")) {
			// It is assumed that the compiled PDF file resides in the
			// same folder with same file name.
			// Method 2:
			String filelink_absolute_txt;
			filelink_absolute_txt = Global.replaceEnding(filelink, "txt");
			final Command[] cmdList = {
					UnixComandosThread.getCommand_tex2txt(filelink, filelink_absolute_txt)
			};
			new UnixComandosThread(filelink).d_o(cmdList);
			this.plainText = ReadWrite.loadText(filelink_absolute_txt);

			// Method 1:
			// At this point a pdf file has to be generated.
			//TODO e.g. using TexManipulator.java and portable LaTex
			// in root directory.
			//this.plainText = PdfKonverter.textAusPdf(filelink.replaceAll("[.]tex$", ".pdf"));
			//this.rawContent = ReadWrite.loadText(filelink_txt);

		}
		// TXT
		else if (ending.equals("txt")) {
			this.plainText = ReadWrite.loadText(filelink);
		}
		Global.addMessage("Extracted sheet|draft's plain text from "
				+ filelink + ".", "success");


		// Finally for reuse
		return plainText;

	}



	/**
	 * EXTRACT RAW CONTENT DEPENDING ON FILETYPE
	 *
	 * This is useful only for .tex and other filetypes that use a
	 * simple markup like XML or similar.
	 * Effectively it loads the file contents, often resulting in a blob
	 * /binary or other mostly difficult to further parse file types
	 * (for doc/binary, docx/odt/rtf/zip).
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
	public static String[] extractRawContentDependingOnFiletype(
			String filelink)
			throws ZipException, IOException {

		String[] rawDocumentContentOfFileArchive = null;

		// Reads the input stream depending on filetype/ending.
		rawDocumentContentOfFileArchive
			= ReadWrite.readInputStreamAsLines(filelink);

		return rawDocumentContentOfFileArchive;
	}



	/**
	 * IS NATIVE FORMAT
	 *
	 * @return True if the current filelink is in the native format.
	 */
	public boolean isNativeFormat() {
		return Global.isFilelinkNativeFormat(filelink);
	}




	@Override
	public String toString() {
		return this.getClass() + " [filelink:" + this.getFilelinkRelative() + "; isNativeFormat:" + this.isNativeFormat() + "]";
	}



}



