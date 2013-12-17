package aufgaben_db;

import java.io.File;
import java.io.FileNotFoundException;
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

import docx4j_library.ConvertOutHtml;
import docx4j_library.ConvertOutPDF;



import Verwaltung.HashLog;

import HauptProgramm.DocType;
import HauptProgramm.Konverter.DocKonverter;
import HauptProgramm.Konverter.PdfKonverter;
import HauptProgramm.Konverter.RtfKonverter;
import HauptProgramm.Konverter.TextConverter;

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
	
	
	public String[] /*List<String>*/ getCommandListForImageConversion() {
		return getCommandListForImageConversion(800);//no change in height - anyway we don't need it
		//anymore as we now use trim option of ImageMagick. A great sorceress. :)
	}
	public String[] /*List<String>*/ getCommandListForImageConversion(int heightToCropTo) {
		
		/*This is a local commandList to give over to UnixComandosThread
		 *for execution/here to create/update an image representation of
		 *this file/content. */
//    	List<String> commandList = new ArrayList<String>();
    	String[] commandList = new String[10];
		
		//-----------------perhaps cycle over this bloc to get other previews also?--//
		//1. filelink file --> pdf file
		//PDF
		String hypo_pdf_filelink = getPDFLink();//same filename, only ending differs
    	String cmd = UnixComandosThread.getCommand_usingLibreOffice(filelink, "pdf");
    	//commandList.add(cmd);
    	commandList[0] = cmd;
    	
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
        cmd = UnixComandosThread.getCommand_pdf2image_usingImageMagick(getPDFLink(), getImageLink(), options);
//    	commandList.add(cmd);
        commandList[1] = cmd;
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
		
		
		//all supported filetypes are the flavours that can ideally be created
		for (DocType docType : DocType.values()) {
			
			
			
			if (docType.name().equals(Global.extractEnding(filelink))) {
				//The native format of this sheet can be skipped happily.
				continue ;
			}
			
			String flavour_filelink = Global.replaceEnding(filelink, docType.name());
			String ending = Global.extractEnding(filelink);
			
			
			//DOC SOURCE ------------------------------------------------ //
			if (ending.equals("doc")) {
				if (docType.name().equals("TXT")) {
					DocKonverter.erstelleTextausDoc(filelink);
				}
				else if (docType.name().equals("PDF")) {
					//TODO
				}
			}
			
			//DOCX SOURCE ------------------------------------------------ //
			else if (ending.equals("docx")) {

				if (docType.name().equals("DOC")) {
				}
//				else if (docType.name().equals("DOCX")) {
//				}
				else if (docType.name().equals("HTML")) {
					ConvertOutHtml.process(filelink, Global.replaceEnding(filelink, "html"));
					generatedFlavours.add(docType.name());
				}
				else if (docType.name().equals("ODT")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_usingLibreOffice(filelink, docType.name().toLowerCase())
					};
					new UnixComandosThread(filelink).d_o(cmdList);
					generatedFlavours.add(docType.name());
				}
				else if (docType.name().equals("PDF")) {
					ConvertOutPDF.process(filelink, Global.replaceEnding(filelink, "pdf"));
					generatedFlavours.add(docType.name());
				}
				else if (docType.name().equals("RTF")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_2rtf_usingLibreOffice(filelink)
					};
					new UnixComandosThread(filelink).d_o(cmdList);
					generatedFlavours.add(docType.name());
				}
				else if (docType.name().equals("TEX")) {
					//TODO using docx2tex
					String filelink_new_absolute = Global.replaceEnding(filelink, "tex");
					final String[] cmdList = {
							UnixComandosThread.getCommand_docx2tex(filelink, filelink_new_absolute)
					};
					new UnixComandosThread(filelink).d_o(cmdList);
					generatedFlavours.add(docType.name());
				}
				else if (docType.name().equals("TXT")) {
					//docx4j alternative:
					WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(filelink));
					MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
					org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)mainDocumentPart.getJaxbElement();

					String txt_filelink = filelink + ".txt";//Global.replaceEnding(filelink, "txt");
					Writer out = new OutputStreamWriter(new FileOutputStream(txt_filelink)); 
					
					TextUtils.extractText(wmlDocumentEl, out);
					//out.flush();
					out.close();
					
					generatedFlavours.add(docType.name());
				}
				
				
			}
			
			//HTML SOURCE ------------------------------------------------ //
			else if (ending.equals("html") || ending.equals("htm")) {
				//Remove html tags. (using e.g. a php striptags() Java equivalent.)
				//TODO
				//this.plainText = ;
			}
			
			
			//ODF (OpenDocumentFormat: Text => ODT)  -------------------- //
			else if (ending.equals("odt")) {
				
				//DOC
				if (docType.name().equals("DOC")) {
				}
				//DOCX
				else if (docType.name().equals("DOCX")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_2odt_usingLibreOffice(filelink)
					};
					new UnixComandosThread(filelink).d_o(cmdList);
					
					generatedFlavours.add(docType.name());
				}
				//HTML
				else if (docType.name().equals("HTML")) {
					//TODO
					
				}
				//ODT
//				else if (docType.name().equals("ODT")) {
//				}
				//PDF
				else if (docType.name().equals("PDF")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_2pdf_usingLibreOffice(filelink)
					};
					new UnixComandosThread(filelink).d_o(cmdList);
					
					generatedFlavours.add(docType.name());
					
				}
				//RTF
				else if (docType.name().equals("RTF")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_2rtf_usingLibreOffice(filelink)
					};
					new UnixComandosThread(filelink).d_o(cmdList);

					generatedFlavours.add(docType.name());
				}
				//TEX
				else if (docType.name().equals("TEX")) {
					//TODO
					
				}
				//TXT
				else if (docType.name().equals("TXT")) {
					//At this point a pdf file has to exist in the same folder as the uploaded file.

					//Method 1) At this point a pdf file has to be generated.
					//TODO
					//this.plainText = PdfKonverter.textAusPdf(filelink.replaceAll("[.]odt$", ".pdf"));
					
					//Method 2)
					ReadWrite.write(TextConverter.odt2text(filelink), Global.replaceEnding(filelink, "txt"));
					
					//Method 3)
					//TODO using ODFToolkit

					generatedFlavours.add(docType.name());
				}
				
				
				
			}
			//PDF SOURCE ------------------------------------------------ //
			else if (ending.equals("pdf")) {
				//DOC
				if (docType.name().equals("DOC")) {
				}
				//DOCX
				else if (docType.name().equals("DOCX")) {
					//TODO
				}
				//HTML
				else if (docType.name().equals("HTML")) {
					//TODO
					
				}
				//ODT
				else if (docType.name().equals("ODT")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_2odt_usingLibreOffice(filelink)
					};
					new UnixComandosThread(filelink).d_o(cmdList);

					generatedFlavours.add(docType.name());
				}
				//PDF
//				else if (docType.name().equals("PDF")) {
//				}
				//RTF
				else if (docType.name().equals("RTF")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_usingLibreOffice(filelink, docType.name().toLowerCase())
					};
					new UnixComandosThread(filelink).d_o(cmdList);

					generatedFlavours.add(docType.name());
				}
				//TEX
				else if (docType.name().equals("TEX")) {
					//TODO
					
				}
				//TXT
				else if (docType.name().equals("TXT")) {
					//At this point a pdf file has to exist in the same folder as the uploaded file.

					//Method 1) At this point a pdf file has to be generated.
					ReadWrite.write(PdfKonverter.textAusPdf(filelink), Global.replaceEnding(filelink, "txt"));

					generatedFlavours.add(docType.name());				
				}
				
				
				
			}
			//RTF SOURCE ------------------------------------------------ //
			else if (ending.equals("rtf")) {
				//DOC
				if (docType.name().equals("DOC")) {
				}
				//DOCX
				else if (docType.name().equals("DOCX")) {
					//TODO
					
				}
				//HTML
				else if (docType.name().equals("HTML")) {
					//TODO
					
				}
				//ODT
				else if (docType.name().equals("ODT")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_2odt_usingLibreOffice(filelink)
					};
					new UnixComandosThread(filelink).d_o(cmdList);

					generatedFlavours.add(docType.name());
				}
				//PDF
				else if (docType.name().equals("PDF")) {
					//using libre office
					final String[] cmdList = {
							UnixComandosThread.getCommand_usingLibreOffice(filelink, docType.name().toLowerCase())
					};
					new UnixComandosThread(filelink).d_o(cmdList);

					generatedFlavours.add(docType.name());
				}
				//RTF
//				else if (docType.name().equals("RTF")) {
//				}
				//TEX
				else if (docType.name().equals("TEX")) {
					//TODO
					
				}
				//TXT
				else if (docType.name().equals("TXT")) {
					//At this point a pdf file has to exist in the same folder as the uploaded file.

					//Method 1) At this point a pdf file has to be generated.
					
					//Method 2)
					this.plainText = RtfKonverter.konvertiereRtfZuText(filelink);
					MethodenWortSuche.schreibeErstesLetztesWortAusTextInHashMap(plainText);
					if (this.getClass().toString().equals("Sheetdraft")) {/*Sheetdraft only, not Exercise*/
						System.out.println("Jetzt folgt noch die Weiterbehandlung des Rtf-Dokuments");
						RtfTestung.SucheIdentifierRtfNeu.bearbeiteRtf((Sheetdraft)this);
					}

					generatedFlavours.add(docType.name());
				}

				
			}
			//TEX SOURCE ------------------------------------------------ //
			else if (ending.equals("tex")) {

				//DOC
				if (docType.name().equals("DOC")) {
				}
				//DOCX
				else if (docType.name().equals("DOCX")) {
					//TODO
					
				}
				//HTML
				else if (docType.name().equals("HTML")) {
					//TODO
					
				}
				//ODT
				else if (docType.name().equals("ODT")) {
					//TODO
					
				}
				//PDF
				else if (docType.name().equals("PDF")) {
					//using pdflatex
					final String[] cmdList = {
							UnixComandosThread.getCommand_pdfLaTeX(filelink, docType.name().toLowerCase())
					};
					new UnixComandosThread(filelink).d_o(cmdList);
					
					generatedFlavours.add(docType.name());
				}
				//RTF
				else if (docType.name().equals("RTF")) {
				}
				//TEX
//				else if (docType.name().equals("TEX")) {
//				}
				//TXT
				else if (docType.name().equals("TXT")) {
					//Method 2)
					String filelink_absolute_txt;
					filelink_absolute_txt = Global.replaceEnding(filelink, "txt");
					final String[] cmdList = {
							UnixComandosThread.getCommand_tex2txt(filelink, filelink_absolute_txt)
					};
					new UnixComandosThread(filelink).d_o(cmdList);			
					this.plainText = ReadWrite.loadText(filelink_absolute_txt);
					
					//Method 1)
					//At this point a pdf file has to be generated.
					//TODO e.g. using TexManipulator.java and portable LaTex in root directory.
					//this.plainText = PdfKonverter.textAusPdf(filelink.replaceAll("[.]tex$", ".pdf"));
//					this.rawContent = ReadWrite.loadText(filelink);
					

					generatedFlavours.add(docType.name());
				}
				
			
				
			}
			//TXT SOURCE ------------------------------------------------ //
			else if (ending.equals("txt")) {
				this.plainText = ReadWrite.loadText(filelink);
				HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein Textfile namens " + filelink);
				
				/*HERE THE MISSION IS RATHER TO CREATE DOCUMENTS OUT OF THE TEXT.
				  SUCH THAT THE PLAIN TEXT IS IN THE DOCUMENT FORMAT AND ONE SAVES THE COPY PASTE.*/
				//TODO
				//DOC
				if (docType.name().equals("DOC")) {
				}
				//DOCX
				else if (docType.name().equals("DOCX")) {
					//TODO using docx4j
					
				}
				//HTML
				else if (docType.name().equals("HTML")) {
				}
				//ODT
				else if (docType.name().equals("ODT")) {
					//TODO using odftoolkit
					
				}
				//PDF
				else if (docType.name().equals("PDF")) {
				}
				//RTF
				else if (docType.name().equals("RTF")) {
				}
				//TEX
				else if (docType.name().equals("TEX")) {
				}
				//TXT
//				else if (docType.name().equals("TXT")) {
//				}
				
			}
			
			
			
			
			
			
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
			this.plainText = DocKonverter.erstelleTextausDoc(filelink);
		}
		//DOCX
		else if (ending.equals("docx")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt " +
					"es sich um ein .docx-File namens " + filelink + ". \nAchtung: Falls " + 
					" eine bessere Qualitaet gewuenscht wird, kann das Dokument in Word " +
					"im RTF-Format abgespeichert und dann das rtf-File an dieses " + 
					"Programm uebergeben werden.");

			this.plainText = DocKonverter.erstelleTextausDocX(filelink);
			
			//docx4j alternative:
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(filelink));
			MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
			org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)mainDocumentPart.getJaxbElement();

			String txt_filelink = filelink + ".txt";//Global.replaceEnding(filelink, "txt");
			Writer out = new OutputStreamWriter(new FileOutputStream(txt_filelink)); 
			TextUtils.extractText(wmlDocumentEl, out);
			//out.flush();
			out.close();
			this.plainText = ReadWrite.loadText(filelink + ".txt");//Global.replaceEnding(filelink, "txt"));

			
			txt_filelink = Global.replaceEnding(filelink, "txt.txt");
			out = new OutputStreamWriter(new FileOutputStream(txt_filelink)); 
			//this.plainText = TextConverter.docx2text(filelink).split(System.getProperty("line.separator"));//(wmlDocumentEl, out);
			this.plainText = TextConverter.docx2txt(filelink);//(wmlDocumentEl, out);
			//out.flush();
			out.close();
			//this.plainText = ReadWrite.loadText(filelink + ".txt");//Global.replaceEnding(filelink, "txt"));
			
		}
		//HTML
		else if (ending.equals("html") || ending.equals("htm")) {
			//Remove html tags. (using e.g. a php striptags() Java equivalent.)
			//TODO
			//this.plainText = ;
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
		//PDF
		else if (ending.equals("pdf")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt " +
					"es sich um ein .tex-File namens " + filelink + ".");
			this.plainText = PdfKonverter.textAusPdf(filelink);
			//ReadWrite.write( getPlainTextAsString(), Global.replaceEnding(filelink, "txt") );
		}
		//RTF
		else if (ending.equals("rtf")) {
			HashLog.erweitereLogFile("Bei dem uebergebenen Dokument handelt es sich um ein Rtf-File namens " + filelink);
			this.plainText = RtfKonverter.konvertiereRtfZuText(filelink);
			MethodenWortSuche.schreibeErstesLetztesWortAusTextInHashMap(plainText);
			if (this.getClass().toString().equals("Sheetdraft")) {/*Sheetdraft only, not Exercise*/
				System.out.println("Jetzt folgt noch die Weiterbehandlung des Rtf-Dokuments");
				RtfTestung.SucheIdentifierRtfNeu.bearbeiteRtf((Sheetdraft)this);
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
			final String[] cmdList = {
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

	
	
	
	
	
	
	
	
}

	
	
