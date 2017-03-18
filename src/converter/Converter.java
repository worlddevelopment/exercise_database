package converter;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.docx4j.TextUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.odftoolkit.odfdom.converter.core.FileURIResolver;
import org.odftoolkit.odfdom.converter.xhtml.XHTMLConverter;
import org.odftoolkit.odfdom.converter.xhtml.XHTMLOptions;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.TeXFormula.TeXIconBuilder;
import org.xml.sax.SAXException;

import command.Command;

import db.UnixComandosThread;
import docx4j_library.ConvertOutHtml;
import docx4j_library.ConvertOutPDF;

import aufgaben_db.Global;
import aufgaben_db.ReadWrite;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.document.DocumentKind;




/**
 * To make a main method available to make the classes callable from
 * the command line.
 * Possible to abstract this because the command line arguments are
 * almost always equal.
 */
public abstract class Converter implements IConverter {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: <" + Converter.class
					+ "> <inputfile> <outputDirectoryForGeneratedFiles>");
			return;
		}

		String source = args[0];//"*.pdf";
		File sourceFile = new File(source);
		if (!sourceFile.exists()) {
			System.err.println("Source file not exists: " + source);
			System.err.println("Usage: <" + Converter.class
					+ "> <inputfile> <outputDirectoryForGeneratedFiles>");
		}

		String destination = args[1];//"directory/";


		convert(sourceFile, new File(destination)
				, null/*=>derive options in subclass*/
				/*, 1200, 1400*/
				);

	}



	/**
	 * Has to be overriden by subclasses.
	 */
	static void convert(File sourceFile, File targetDir, Options options) {
		;
	}



	/**
	 * The returned list contains extra/additionally created filelinks
	 * only. Not the target_filelink for now.
	 * Reasoning behind this is that the target_flavour_filelink is so
	 * important it can't simply be added but has to be checked for
	 * existence in the filesystem first.
	 * It would be unlucky to have to execute
	 * this check in every file multiple times instead of once centrally.
	 */
	public static String hypotheticalTemporaryFilelink = null;
	public static Command commandForRenamingToTargetFile = null;

	/**
	 * Set to true if only pure Java is supported on current platform.
	 */
	public static boolean isCrossplatformRequired = false;

	/**
	 * TO MAKE RESOLVING SUBSEQUENT CONVERTER PIPELINES EASIER
	 */
	public static List<String> doc2docx(String filelink
			, String target_flavour_filelink) {

		return doc2docx(filelink, target_flavour_filelink
				, new File(target_flavour_filelink).getParent());

	}
	public static List<String> doc2docx(String filelink
			, String target_flavour_filelink, String target_directory) {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (!isCrossplatformRequired) {
			// or using jodconverter for instance-reuse:
			// soffice --headless --accept="socket,port=8100;urp;"
			// (decided in d_o())
			Command[] commandList;
			commandList = new Command[2];
			commandList[0]
				= UnixComandosThread.getCommand_2docx_usingLibreOffice(
						filelink, target_directory);
			try {
				new UnixComandosThread(filelink, target_flavour_filelink)
					.d_o(commandList);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			if (new File(hypotheticalTemporaryFilelink).exists()) {
				if (!hypotheticalTemporaryFilelink
						.equals(target_flavour_filelink)) {
					// rename <correct path>/old_filename to
					// <correct path>/target_flavour_filename
					commandList = new Command[1];
					commandList[0] = commandForRenamingToTargetFile;
					try {
						new UnixComandosThread(filelink
								, target_flavour_filelink)
							.d_o(commandList);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				generatedFlavours.add(target_flavour_filelink);
			}
			else {
				System.out.println(Global.addMessage("The expected file"
							+ " that should have been generated for"
							+ " renaming to the target sheetdraft"
							+ " destination, did not exist: "
							+ hypotheticalTemporaryFilelink
							, "nosuccess")
						);
			}
		}

		return generatedFlavours;
	}



	public static List<String> doc2pdf(String filelink
			, String target_flavour_filelink) {

		return doc2pdf(filelink, target_flavour_filelink
				, new File(target_flavour_filelink).getParent());
	}

	public static List<String> doc2pdf(String filelink
			, String target_flavour_filelink, String target_directory) {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink))
			return new ArrayList<String>();
		if (!isCrossplatformRequired) {
			Command[] commandList;
			commandList = new Command[2];
			commandList[0] = UnixComandosThread
				.getCommand_2pdf_usingLibreOffice(
						filelink, target_directory);
			try {
				new UnixComandosThread(filelink, target_flavour_filelink)
					.d_o(commandList);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			if (new File(hypotheticalTemporaryFilelink).exists()) {
				if (!hypotheticalTemporaryFilelink
						.equals(target_flavour_filelink)) {
					// rename <correct path>/old_filename to
					// <correct path>/target_flavour_filename
					commandList = new Command[1];
					commandList[0] = commandForRenamingToTargetFile;
					try {
						new UnixComandosThread(filelink
								, target_flavour_filelink)
							.d_o(commandList);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				generatedFlavours.add(target_flavour_filelink);
			}
			else {
				System.out.println(Global.addMessage("The expected file"
							+ " that should have been generated for"
							+ " renaming to the target sheetdraft"
							+ " destination, did not exist: "
							+ hypotheticalTemporaryFilelink
							, "nosuccess"));
			}
		}

		return generatedFlavours;
	}



	public static List<String> docx2html(String filelink
			, String target_flavour_filelink) {

		try {
			ConvertOutHtml.process(filelink, target_flavour_filelink);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<String>(); // empty list => no special extra intermediate conversions here.

	}



	public static List<String> docx2odt(String filelink
			, String target_flavour_filelink
			, String target_directory
			, String target_type) {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		// using libre office
		final Command[] cmdList = {
				UnixComandosThread.getCommand_usingLibreOffice(filelink
						, target_type.toLowerCase(), target_directory)
		};
		try {
			new UnixComandosThread(filelink, target_flavour_filelink)
				.d_o(cmdList);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if (new File(hypotheticalTemporaryFilelink).exists()) {
			if (!hypotheticalTemporaryFilelink
					.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
				Command[] commandList = new Command[1];
				commandList[0] = commandForRenamingToTargetFile;
				try {
					new UnixComandosThread(filelink
							, target_flavour_filelink).d_o(commandList);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file that"
						+ " should have been generated for renaming to"
						+ " the target sheetdraft destination, did not"
						+ " exist: " + hypotheticalTemporaryFilelink
						, "nosuccess"));
		}

		return generatedFlavours;

	}



	public static List<String> docx2pdf(String filelink
			, String target_flavour_filelink) {

		// TODO Currently only working once per application start.
		// To fix this we have to use templates for caching FACTORY:
		// https://community.oracle.com/thread/1634942?start=0
		try {
			ConvertOutPDF.process(filelink, target_flavour_filelink);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<String>();

	}



	public static List<String> docx2rtf(String filelink
			, String target_flavour_filelink) {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (isCrossplatformRequired) {
			return generatedFlavours;
		}

		// using libre office
		final Command[] cmdList = {
				UnixComandosThread
					.getCommand_2rtf_usingLibreOffice(filelink)
		};
		try {
			new UnixComandosThread(filelink, target_flavour_filelink)
				.d_o(cmdList);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if (new File(hypotheticalTemporaryFilelink).exists()) {
			if (!hypotheticalTemporaryFilelink
					.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
//				commandList = new String[1];
//				commandList[0] = commandForRenamingToTargetFile;
//				new UnixComandosThread(hypotheticalTemporaryFilelink
//						, target_flavour_filelink).d_o(commandList);
				Global.renameFile(hypotheticalTemporaryFilelink
						, target_flavour_filelink);
			}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file that"
						+ " should have been generated for renaming to"
						+ " the target sheetdraft destination, did not"
						+ " exist: " + hypotheticalTemporaryFilelink
						, "nosuccess"));
		}

		return generatedFlavours;

	}



	public static List<String> docx2tex(String filelink
			, String target_flavour_filelink) {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (isCrossplatformRequired) {
			return generatedFlavours;
		}
		// using docx2tex
		final Command[] cmdList = {
				UnixComandosThread.getCommand_docx2tex(filelink
						, target_flavour_filelink)
		};
		try {
			new UnixComandosThread(filelink, target_flavour_filelink)
				.d_o(cmdList);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if (new File(target_flavour_filelink).exists()) {
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file that"
						+ " should have been generated for renaming to"
						+ " the target sheetdraft destination, did not"
						+ " exist: " + hypotheticalTemporaryFilelink
						, "nosuccess"));
		}
		return generatedFlavours;
	}



	public static List<String> docx2text(String filelink
			, String target_flavour_filelink) {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();// docx4j alternative:
		}
		WordprocessingMLPackage wordMLPackage = null;
		try {
			wordMLPackage
				= WordprocessingMLPackage.load(new File(filelink));
		}
		catch (Docx4JException e) {
			e.printStackTrace();
		}
		if (wordMLPackage == null) {
			System.err.println("WordMLPackage is null, load failed: "
					+ wordMLPackage + " filelink: " + filelink);
			return generatedFlavours;
		}
		MainDocumentPart mainDocumentPart
			= wordMLPackage.getMainDocumentPart();
		org.docx4j.wml.Document wmlDocumentEl
			= (org.docx4j.wml.Document)mainDocumentPart.getJaxbElement();

		Writer out = null;
		try {
			out = new OutputStreamWriter(
					new FileOutputStream(target_flavour_filelink));
			TextUtils.extractText(wmlDocumentEl, out);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (out != null) {
				//out.flush();
				try {
					out.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

//		generatedFlavours.add(target_flavour_filelink);
		return generatedFlavours;
	}




	public static List<String> image2pdf(String filelink
			, String target_flavour_filelink)
		throws COSVisitorException, IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink))
			return new ArrayList<String>();
		new org.apache.pdfbox.examples.pdmodel.ImageToPDF()
			.createPDFFromImage(filelink, target_flavour_filelink);

		if (new File(target_flavour_filelink).exists()) {
			System.out.println("ImageToPDF.createPDFFromImage method"
					+ " has generated the pdf: "
					+ target_flavour_filelink);
			return generatedFlavours;
		}
		else {
			System.out.println("ImageToPDF.createPDFFromImage failed");
		}
		return generatedFlavours;
	}



	//======= HTML =======//
	public static List<String> html2png(String filelink
			, String target_flavour_filelink)
		throws IOException, SAXException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		// Whenever there are more conditions than one to reach here,
		// we have to ensure:
		target_flavour_filelink
			= Global.replaceEnding(target_flavour_filelink, "png");

		FileOutputStream os
			= new FileOutputStream(new File(target_flavour_filelink));
		org.fit.cssbox.demo.ImageRenderer r
			= new org.fit.cssbox.demo.ImageRenderer();
		r.renderURL("file://" + filelink, os
				, org.fit.cssbox.demo.ImageRenderer.TYPE_PNG);

		if (os != null) {
			os.close();
		}
		System.out.println("Converted HTML to PNG.");

		return generatedFlavours;
	}



	public static List<String> html2jpg(String filelink
			, String target_flavour_filelink)
		throws IOException, SAXException {

		List<String> generatedFlavours;

		// Whenever there are more conditions than one to reach here,
		// we have to ensure:
		target_flavour_filelink
			= Global.replaceEnding(target_flavour_filelink, "png");
		generatedFlavours = html2png(filelink, target_flavour_filelink);
		// it is ensured that the target_type is used.

		// hopefully png-conversion worked.
		target_flavour_filelink = Global.replaceEnding(target_flavour_filelink, "jpg");
//		String hypo_source_filelink
//			= Global.replaceEnding(target_flavour_filelink, "png");
		// TODO convert from PNG to JPG:
		//convert(PNG->JPG
		//		, Global.replaceEnding(target_flavour_filelink, "jpg"));
		System.out.println("Converting HTML to JPG or rather PNG to JPG"
				+ " is not supported yet. Use PNG-Version instead.");

		return generatedFlavours;
	}



	public static List<String> html2svg(String filelink
			, String target_flavour_filelink)
		throws IOException, SAXException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		// Whenever there are more conditions than one to reach here,
		// we have to ensure:
		target_flavour_filelink
			= Global.replaceEnding(target_flavour_filelink, "svg");

		FileOutputStream os
			= new FileOutputStream(new File(target_flavour_filelink));
		org.fit.cssbox.demo.ImageRenderer r
			= new org.fit.cssbox.demo.ImageRenderer();
		r.renderURL("file://" + filelink, os
				, org.fit.cssbox.demo.ImageRenderer.TYPE_SVG);

		if (os != null) {
			os.close();
		}
		System.out.println("Converted HTML to SVG. location: "
				+ target_flavour_filelink);

		return generatedFlavours;
	}



	public static List<String> html2docx(String filelink
			, String target_flavour_filelink) throws JAXBException
			, Docx4JException, IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink))
			return new ArrayList<String>();

		// using docx4j XHTMLImporter:
		WordprocessingMLPackage wordMLPackage
			= WordprocessingMLPackage.createPackage();

		NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
		wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
		ndp.unmarshalDefaultNumbering();

		XHTMLImporterImpl xHTMLImporter
			= new XHTMLImporterImpl(wordMLPackage);
		xHTMLImporter.setHyperlinkStyle("Hyperlink");

		String xhtmlFromFile;// <- Attention: Has to be well formed XML.
		xhtmlFromFile
			= FileUtils.readFileToString(new File(filelink), "UTF-8");

		wordMLPackage.getMainDocumentPart().getContent().addAll(
				xHTMLImporter.convert(xhtmlFromFile, null)
		);

//		System.out.println(
//				XmlUtils.marshaltoString(wordMLPackage
//				.getMainDocumentPart().getJaxbElement(), true, true)
//		);

		wordMLPackage.save(new java.io.File(target_flavour_filelink));

		return generatedFlavours;

	}



	public static List<String> html2odt(String filelink
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}

		// Make sure it is started with odt.
		// (as target_flavour_filelink could be rtf or pdf too!)
		String odt_filelink
			= Global.replaceEnding(target_flavour_filelink, "odt");

		// EITHER TODO using http://xhtml2odt.org/
		// (still not ported to pure Java)

		// odftoolkit allows only the other direction:
		//new org.odftoolkit.odfdom.converter.xhtml.XHTMLConverter().;


		// OR PANDOC
		// TODO http://johnmacfarlane.net/pandoc/demos.html

		// OR
		// html -> odt using libreoffice
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the use of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; // <- empty.
		}

		final Command[] cmdList = {
				UnixComandosThread.getCommand_2odt_usingLibreOffice(
						filelink, odt_filelink)
		};
		new UnixComandosThread(filelink, odt_filelink).d_o(cmdList);


		// Target flavour filelink had to be overruled here temporarily
		// as it could be either ODT, RTF or PDF.
		// And as we have added an additional odt-flavour here, we also
		// mark it as available in generatedFlavours.
		if (!odt_filelink.equals(target_flavour_filelink)) {
			generatedFlavours.add(odt_filelink);
		}

		return generatedFlavours;

	}



	public static List<String> html2pdf(String filelink
			, String target_flavour_filelink) throws Exception {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}

		// dependencies:
		String hypothetical_source_filelink = Global.replaceEnding(target_flavour_filelink, "odt");
		generatedFlavours.addAll(html2odt(filelink, hypothetical_source_filelink));
		generatedFlavours.add(hypothetical_source_filelink);

		// TODO Find a way to convert from odt to pdf without LibreOffice
		generatedFlavours.addAll(odt2pdf(hypothetical_source_filelink
					, target_flavour_filelink));

		if (new File(target_flavour_filelink).exists()) {
			return generatedFlavours;
		}

		// cross-platform execution?
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; //<-- empty.
		}
		// using odt -> pdf using libreoffice
		final Command[] cmdList = {
				UnixComandosThread.getCommand_2pdf_usingLibreOffice(
						hypothetical_source_filelink
						, Global.replaceEnding(target_flavour_filelink
							, "pdf")
				)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);


		return generatedFlavours;


	}



	public static List<String> html2rtf(String filelink
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; //<-- empty.
		}

		// dependencies:
		String hypothetical_source_filelink
			= Global.replaceEnding(target_flavour_filelink, "odt");
		html2odt(filelink, hypothetical_source_filelink);
		generatedFlavours.add(hypothetical_source_filelink);

		// using libre office
		final Command[] cmdList = {
				UnixComandosThread.getCommand_2rtf_usingLibreOffice(
						hypothetical_source_filelink
						, target_flavour_filelink)
		};
		new UnixComandosThread(hypothetical_source_filelink
				, target_flavour_filelink).d_o(cmdList);


		return generatedFlavours;

	}



	/**
	 * TODO clarify if better to use 2text or as it is currently 2txt
	 * (probably it's good to consistently use the fileendings).
	 */
	public static List<String> html2txt(String filelink
			, String target_flavour_filelink) throws Exception {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		// Method 1) At this point a pdf file has to be generated.
		// (bad choice because PDF itself relies on other formats)
		// TODO (now using LibreOffice ODT)
		//this.plainText = PdfKonverter
		//	.textAusPdf(filelink.replaceAll("[.]odt$", ".pdf"));

		// Method 2)
		ReadWrite.write(TextConverter.odt2text(filelink)
				, target_flavour_filelink);

		// Method 3)
		// TODO using ODFToolkit

		return generatedFlavours;

	}



	//======= ODT =======//
	public static List<String> odt2docx(String filelink
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; //<-- empty.
		}


		// using libre office
		final Command[] cmdList = {
				UnixComandosThread.getCommand_2odt_usingLibreOffice(
						filelink, target_flavour_filelink)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);
		if (new File(hypotheticalTemporaryFilelink).exists()) {
			if (!hypotheticalTemporaryFilelink
					.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
//				commandList = new String[1];
//				commandList[0] = commandForRenamingToTargetFile;
//				new UnixComandosThread(hypotheticalTemporaryFilelink
//						, target_flavour_filelink).d_o(commandList);
				Global.renameFile(hypotheticalTemporaryFilelink
						, target_flavour_filelink);
			}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file that"
						+ " should have been generated for renaming to"
						+ " the target sheetdraft destination, did not"
						+ " exist: " + hypotheticalTemporaryFilelink
						, "nosuccess"));
		}

		return generatedFlavours;

	}



	@SuppressWarnings("deprecation")
	public static List<String> odt2html(String filelink
			, String target_flavour_filelink) throws Exception {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		// TODO also works with xslt and the xslt files from libreoffice
		// installation.
		//org.odftoolkit.odfxsltrunner.;<-conversion,image extraction
		// see also
		//http://incubator.apache.org/odftoolkit/xsltrunner/ODFXSLTRunnerExamples.html

		// TODO Replace with higher level version once this (conversions)
		// is all unified.
		// 1) Load ODT into ODFDOM OdfTextDocument
		InputStream in= new FileInputStream(new File(filelink));
		OdfTextDocument document = OdfTextDocument.loadDocument(in);

		// 2) Prepare XHTML options (here we set the IURIResolver
		// to load images from a "pictures" folder)
		File image_dir = new File(Global
				.replaceEnding(target_flavour_filelink, "pictures"));

		if (!image_dir.exists()) {
			image_dir.mkdirs();
		}
		XHTMLOptions htmlOptions
			= XHTMLOptions.create().URIResolver(new FileURIResolver(
				image_dir
		));

		// 3) Convert OdfTextDocument to XHTML
		OutputStream out
			= new FileOutputStream(new File(target_flavour_filelink));
		XHTMLConverter.getInstance().convert(document, out, htmlOptions);


		document.close();
		in.close();
		out.close();
		return generatedFlavours;

	}



	public static List<String> odt2pdf(String filelink
			, String target_flavour_filelink) throws Exception {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}

		if (!isCrossplatformRequired) {
			//using libre office
			final Command[] cmdList = {
					UnixComandosThread.getCommand_2pdf_usingLibreOffice(
							filelink, target_flavour_filelink)
			};
			new UnixComandosThread(filelink, target_flavour_filelink)
				.d_o(cmdList);
			if (new File(hypotheticalTemporaryFilelink).exists()) {
				if (!hypotheticalTemporaryFilelink
						.equals(target_flavour_filelink)) {
					// rename <correct path>/old_filename to
					// <correct path>/target_flavour_filename
//					commandList = new String[1];
//					commandList[0] = commandForRenamingToTargetFile;
//					new UnixComandosThread(hypotheticalTemporaryFilelink, target_flavour_filelink).d_o(commandList);
					Global.renameFile(hypotheticalTemporaryFilelink
							, target_flavour_filelink);
				}
				generatedFlavours.add(target_flavour_filelink);
			}
			else {
				System.out.println(Global.addMessage("The expected file"
							+ " that should have been generated for"
							+ " renaming to the target sheetdraft"
							+ " destination, did not exist: "
							+ hypotheticalTemporaryFilelink
							, "nosuccess"));
			}
		}
//		else {
//			return generatedFlavours; //<-- empty.
//		}
		System.out.println("The requirement to use Java only prevented"
				+ " the use of UNIX commands for the conversion.");

		/*
		As JODConverter is already platform independent, we rely on it.
		The following should become a fallback if JODConverter realises
		there is no libreoffice (or equivalent) installation.
		ALTERNATIVE USING ODFTOOLKIT AND ITEXT (xdocreport,
		which could be used for other purposes too in future):

		Uses ODFTOOLKIT (see below, so this is the equivalent to
		SimpleAPI for odf conversions)
		USING ODFTOOLKIT DIRECTLY FOR NOW.
		http://code.google.com/p/xdocreport/wiki/Converters
		*/
		// 1) Create options to select well converter from
		// the registry.ODT|DOCX->PDF|HTML
		Options options
			= Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);
		//options.suboptions(org.odftoolkit. ... pdfOptions/ *e.g.* /);

		// 2) Get the converter from the registry:
		//TODO Use this commons interface for all converters?
		IConverter converter
			= ConverterRegistry.getRegistry().getConverter(options);

		// 3) The Conversion:
		InputStream in = new FileInputStream(new File(filelink));
		OutputStream out
			= new FileOutputStream(new File(target_flavour_filelink));
		converter.convert(in, out, options);
/*
		// Or directly using odftoolkit and itext?
		//org.odftoolkit.odfdom.converter.core.FileImageExtractor;
		//import org.odftoolkit.odfdom.converter.xhtml.XHTMLOptions;
		//import org.odftoolkit.odfdom.converter.xhtml.XHTMLConverter;
		// 1) Load ODT into ODFDOM OdfTextDocument
		InputStream in = new FileInputStream(new File(filelink));
		OdfTextDocument document = OdfTextDocument.loadDocument(in);


		org.odftoolkit.odfdom.converter.pdf.PdfOptions pdfOptions
				= org.odftoolkit.odfdom.converter.pdf.PdfOptions.create();

		// 3) Convert OdfTextDocument to PDF
		OutputStream out = new FileOutputStream(new File(target_flavour_filelink));
		org.odftoolkit.odfdom.converter.pdf.PDFConverter.getInstance().convert(document, out, pdfOptions);

		document.close();
*/
		in.close();
		out.close();
		return generatedFlavours;

	}



	public static List<String> odt2rtf(String filelink
			, String target_flavour_filelink) throws Exception {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}

		if (!isCrossplatformRequired) {
			// using libre office
			final Command[] cmdList = {
					UnixComandosThread.getCommand_2rtf_usingLibreOffice(
							filelink, target_flavour_filelink)
			};
			new UnixComandosThread(filelink, target_flavour_filelink)
				.d_o(cmdList);
			if (new File(hypotheticalTemporaryFilelink).exists()) {
				if (!hypotheticalTemporaryFilelink
						.equals(target_flavour_filelink)) {
					// rename <correct path>/old_filename to
					// <correct path>/target_flavour_filename
//					commandList = new String[1];
//					commandList[0] = commandForRenamingToTargetFile;
//					new UnixComandosThread(hypotheticalTemporaryFilelink
//							, target_flavour_filelink).d_o(commandList);
					Global.renameFile(hypotheticalTemporaryFilelink
							, target_flavour_filelink);
				}
				generatedFlavours.add(target_flavour_filelink);
			}
			else {
				System.out.println(Global.addMessage("The expected file"
							+ " that should have been generated for"
							+ " renaming to the target sheetdraft"
							+ " destination, did not exist: "
							+ hypotheticalTemporaryFilelink
							, "nosuccess"));
			}
		}

		return generatedFlavours;

	}



	public static List<String> odt2txt(String filelink
			, String target_flavour_filelink) throws Exception {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}

		// At this point a pdf file has to exist in the same folder
		// as the uploaded file.

		// Method 1) At this point a pdf file has to be generated.
		// TODO
		//this.plainText = PdfKonverter
		//	.textAusPdf(filelink.replaceAll("[.]odt$", ".pdf"));

		// Method 2)
		ReadWrite.write(TextConverter.odt2text(filelink)
				, target_flavour_filelink);

		// Method 3)
		// TODO using ODFToolkit


		return generatedFlavours;

	}



	//======= PDF =======//
	public static List<String> pdf2odt(String filelink
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; //<-- empty.
		}

		// using libre office
		final Command[] cmdList = {
				UnixComandosThread.getCommand_2odt_usingLibreOffice(
						filelink, target_flavour_filelink)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);
		if (new File(hypotheticalTemporaryFilelink).exists()) {
			if (!hypotheticalTemporaryFilelink
					.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
//				commandList = new String[1];
//				commandList[0] = commandForRenamingToTargetFile;
//				new UnixComandosThread(hypotheticalTemporaryFilelink
//						, target_flavour_filelink).d_o(commandList);
				Global.renameFile(hypotheticalTemporaryFilelink
						, target_flavour_filelink);
			}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file that"
						+ " should have been generated for renaming to"
						+ " the target sheetdraft destination, did not"
						+ " exist: " + hypotheticalTemporaryFilelink
						, "nosuccess"));
		}


		return generatedFlavours;

	}



	public static List<String> pdf2rtf(String filelink
			, String target_flavour_filelink, String target_directory
			, String target_type) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; //<-- empty.
		}
		// using libre office
		final Command[] cmdList = {
				UnixComandosThread.getCommand_usingLibreOffice(filelink
						, target_type.toLowerCase()
						, target_directory)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);
		if (new File(hypotheticalTemporaryFilelink).exists()) {
			if (!hypotheticalTemporaryFilelink
					.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
//				commandList = new String[1];
//				commandList[0] = commandForRenamingToTargetFile;
//				new UnixComandosThread(hypotheticalTemporaryFilelink
//				, target_flavour_filelink).d_o(commandList);
				Global.renameFile(hypotheticalTemporaryFilelink
						, target_flavour_filelink);
			}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file that"
						+ " should have been generated for renaming to"
						+ " the target sheetdraft destination, did not"
						+ " exist: " + hypotheticalTemporaryFilelink
						, "nosuccess"));
		}

		return generatedFlavours;

	}



	public static List<String> pdf2txt(String filelink
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		// At this point a pdf file has to exist in the same folder
		// as the uploaded file.

		// Method 1) At this point a pdf file has to have been generated.
		ReadWrite.write(PDFConverter.textAusPdf(filelink)
				, target_flavour_filelink);

		return generatedFlavours;

	}



	/**
	 *
	 * @param filelink
	 * @param target_image_format - May also be a filelink.
	 * @return
	 * @throws IOException
	 */
	public static List<String> pdf2image(String filelink
			, String target_image_format_or_filelink) throws IOException {

		// To make sure the image format is not a filelink:
		String target_image_format
			= Global.extractEnding(target_image_format_or_filelink);
		if (target_image_format == null) {
			target_image_format = target_image_format_or_filelink;
		}

		List<String> generatedFlavours = new ArrayList<String>();

		/*
		see: https://issues.apache.org/jira/browse/PDFBOX-905
		ERROR ProcessOperator (i not supported?)
		Does not support PNG. Use JPG no matter whether PNG is desired.
		TODO: Once there is support, remove the ending replacement.
		*/
		PDFToImage.convert(filelink, /*Global.replaceEnding(*/target_image_format_or_filelink/*, "jpg")*/);
		// <- the other variant.
		// TODO: Compare results of both variants though both use pdfbox.

		/*ERROR: PDPage.converToImage not found!
		PDFToImage.convert( new String[] { filelink, "-imageType", target_image_format, "-startPage", "1", "-endPage", "2" } );
		*/

		return generatedFlavours;

	}



	/**
	 * The following methods are required for the planned auto-resolver
	 * of conversion paths.
	 * TODO Determine if pdf2image can be used too. Though it's not
	 * likely as image2odt, etc. do not exist.
	 * Although it could be resolved it still would be difficult to
	 * further convert as the final conversion format (whether it now has
	 * converted to png, jpg, gif or svg is unknown).
	 */
	public static List<String> pdf2jpg(String filelink
			, String target_filelink) throws IOException {
		return pdf2image(filelink, target_filelink);
	}
	public static List<String> pdf2png(String filelink
			, String target_filelink) throws IOException {
		return pdf2image(filelink, target_filelink);
	}
	public static List<String> pdf2svg(String filelink
			, String target_filelink) throws IOException {
		return pdf2image(filelink, target_filelink);
	}






	//======= RTF =======//
	public static List<String> rtf2odt(String filelink
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; //<- empty.
		}
		// using libre office
		final Command[] cmdList = {
				UnixComandosThread.getCommand_2odt_usingLibreOffice(
						filelink, target_flavour_filelink)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);
		if (new File(hypotheticalTemporaryFilelink).exists()) {
			if (!hypotheticalTemporaryFilelink
					.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
//				commandList = new String[1];
//				commandList[0] = commandForRenamingToTargetFile;
//				new UnixComandosThread(hypotheticalTemporaryFilelink
//						, target_flavour_filelink).d_o(commandList);
				Global.renameFile(hypotheticalTemporaryFilelink
						, target_flavour_filelink);
			}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file that"
						+ " should have been generated for renaming to"
						+ " the target sheetdraft destination, did not"
						+ " exist: " + hypotheticalTemporaryFilelink
						, "nosuccess"));
		}


		return generatedFlavours;

	}



	public static List<String> rtf2pdf(String filelink
			, String target_flavour_filelink, String target_type)
		throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; //<-- empty.
		}


		// using libre office
		final Command[] cmdList = {
				UnixComandosThread.getCommand_usingLibreOffice(filelink
						, target_type.toLowerCase()
						, target_flavour_filelink)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);
		if (new File(hypotheticalTemporaryFilelink).exists()) {
			if (!hypotheticalTemporaryFilelink
					.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
//				commandList = new String[1];
//				commandList[0] = commandForRenamingToTargetFile;
//				new UnixComandosThread(hypotheticalTemporaryFilelink, target_flavour_filelink).d_o(commandList);
				Global.renameFile(hypotheticalTemporaryFilelink
						, target_flavour_filelink);
			}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file that"
						+ " should have been generated for renaming to"
						+ " the target sheetdraft destination, did not"
						+ " exist: " + hypotheticalTemporaryFilelink
						, "nosuccess"));
		}


		return generatedFlavours;

	}



	public static List<String> rtf2txt(String filelink
			, String target_flavour_filelink) {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}

		// Method 1) At this point a pdf file has to have been generated.
//		ReadWrite.write(PDFConverter.textAusPdf(filelink)
//				, target_flavour_filelink);

		// Method 2)
		String[] plainText = RTFConverter.toPlainText(filelink);
		ReadWrite.write(plainText, target_flavour_filelink);


		return generatedFlavours;
	}




	//======= TeX =======//

	/**
	 * Creates an image out of a chunk of tex that gets loaded from
	 * the filesystem.
	 *
	 * @param filelink
	 * @param writeToDisk whether to write the image to the filesystem
	 * @return a buffer of the image
	 * @throws FileNotFoundException
	 */
	public static BufferedImage tex2image(String filelink)
		throws FileNotFoundException {

		return tex2image(filelink, true);
	}

	public static BufferedImage tex2image(String filelink
			, boolean writeToDisk) throws FileNotFoundException {

//		String math = "\\frac {V_m} {K_M+S}";
//		TeXFormula formula = new TeXFormula(math);
//		TeXIcon ti = fomula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);
//		BufferedImage b = new BufferedImage(ti.getIconWidth()
//				, ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//		ti.paintIcon(new JLabel(), b.getGraphics(), 0, 0);
		String tex = Global.arrayToString(ReadWrite.loadText(filelink));

		/*
		TODO the following lines are comments only because the system
		is very unstable currently with the changes for the local
		execution for version v31.13c.
		*/
		TeXFormula formula = new TeXFormula(tex);
		TeXIcon icon = formula.new TeXIconBuilder()
				.setStyle(TeXConstants.STYLE_DISPLAY)
//				.setWidth(TeXConstants.UNIT_CM, 4, TeXConstants.ALIGN_LEFT)
//				.setInterLineSpacing(TeXConstants.UNIT_CM, 0.5f)
				.setSize(20)
				.build();


		icon.setInsets(new Insets(5, 5, 5, 5));

		BufferedImage tex_image_buffer
			= new BufferedImage(icon.getIconWidth(), icon.getIconHeight()
				, BufferedImage.TYPE_INT_ARGB);

		//Graphics2D g2 = image.createGraphics();
		//g2.setColor(Color.white);
		//g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
		//JLabel jl = new JLabel();
		//jl.setForeground(new Color(0, 0, 0));
		//icon.paintIcon(jl, g2, 0, 0);
		if (writeToDisk) {
			File tex_image_file
				= new File(Global.convertToImageLink(filelink));
			try {
				ImageIO.write(tex_image_buffer, "png"
						, tex_image_file.getAbsoluteFile());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		// ALTERNATIVELY
		//formula.createPNG(TeXConstants.STYLE_DISPLAY, 20
		//		, "Example5.png", Color.white, Color.black);

//		return null;
		return tex_image_buffer;

	}



	public static List<String> tex2html(String filelink
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();


		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours; //<-- empty.
		}


		// using LaTeXML
		final Command[] cmdList = {
				// xhtml or html? Chose html as it is more general:
				UnixComandosThread.getCommand_tex2html(filelink
						, target_flavour_filelink)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);


		return generatedFlavours;

	}



	public static List<String> tex2docx(String filelink
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		// METHOD 1: porting tex2docx C# program.
		// TODO continue porting docx2tex.
		// TODO reverse the docx2tex port to tex2docx.
		//tex2docx.convert(filelink, target_flavour_filelink);


		// METHOD 2: using the C# program from the command line. For
		// UNIX use Mono, else .NET.
		/*
		To detect if Mono or NET is available will only work by trial,
		error, catching the exception, fall back to e.g. pure Java port.
		*/
		// using LaTeXML
		final Command[] cmdList = {
				//TODO find command line program to convert from tex2docx
				//UnixComandosThread.getCommand_tex2docx(filelink
				//		, target_flavour_filelink)
		};
//		new UnixComandosThread(filelink, target_flavour_filelink)
//			.d_o(cmdList);


		return generatedFlavours;

	}



	public static List<String> tex2odt(String filelink
			, String target_flavour_filelink
			, String target_directory, String target_type)
		throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();

		// dependencies:
		boolean areAllDependenciesMet = true; //<-- assumption.
		String intermediate_filelink = Global
			.replaceEnding(target_flavour_filelink, ".docx");

		tex2docx(filelink, intermediate_filelink);
		if (!new File(intermediate_filelink).exists()) {
			System.out.println("TEX2DOCX apparently failed as there"
					+ " is no existing docx-file: "
					+ intermediate_filelink);
			areAllDependenciesMet = false;
		}

		if (!areAllDependenciesMet) {
			System.err.println("One or more dependencies are not met."
					+ " This could be due to a failure during the"
					+ " creation of intermediate temporary file"
					+ " flavours that are required to "
					+ "finally convert to this desired target flavour"
					+ " file.");
			return generatedFlavours;
		}

		generatedFlavours.addAll(docx2odt(filelink
					, target_flavour_filelink
					, target_directory, target_type));

		// Crossplatform or UNIX system?
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours;
		}
		// TODO the UNIX command way.

		return generatedFlavours;

	}



	public static List<String> tex2pdf(String filelink
			, String target_flavour_filelink
			, String target_directory, String target_type)
		throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();


		// dependencies:
		boolean areAllDependenciesMet = true; // <- assumption.
		String docx_filelink
			= Global.replaceEnding(target_flavour_filelink, ".docx");
		tex2docx(filelink, docx_filelink);
		if (!new File(docx_filelink).exists()) {
			areAllDependenciesMet = false;
		}

		if (!areAllDependenciesMet) {
			System.err.println("One or more dependencies are not met."
					+ " This could be due to a failure during the"
					+ " creation of intermediate temporary file"
					+ " flavours that are required to finally convert"
					+ " to this desired target flavour file.");
		}


		// METHOD 1:
		generatedFlavours.addAll(docx2pdf(filelink
					, target_flavour_filelink));

		// METHOD 2: TeXLive portable cross-platform:
		// pdfLatex/luaLatex interpreters.

		// Crossplatform or UNIX system?
		// TODO remove if a cross-platform bundled TeXLive gets
		// available. Then write a command for it. Invoke it.
		if (isCrossplatformRequired) {
			System.out.println("The requirement to use Java only"
					+ " prevented the us of UNIX commands for the"
					+ " conversion.");
			return generatedFlavours;
		}

		// Using a portable TeXLive installation that comes with this.
		final Command[] cmdList = {
				UnixComandosThread.getCommand_pdfLaTeX(filelink
						, target_flavour_filelink)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);
		if (new File(hypotheticalTemporaryFilelink).exists()) {
			if (!hypotheticalTemporaryFilelink
					.equals(target_flavour_filelink)) {
				// rename <correct path>/old_filename to
				// <correct path>/target_flavour_filename
//					commandList = new String[1];
//					commandList[0] = commandForRenamingToTargetFile;
//					new UnixComandosThread(
//							hypotheticalTemporaryFilelink
//							, target_flavour_filelink).d_o(commandList);
				Global.renameFile(hypotheticalTemporaryFilelink
						, target_flavour_filelink);
			}
			generatedFlavours.add(target_flavour_filelink);
		}
		else {
			System.out.println(Global.addMessage("The expected file"
						+ " that should have been generated for"
						+ " renaming to the target sheetdraft"
						+ " destination, did not exist: "
						+ hypotheticalTemporaryFilelink
						, "nosuccess"));
		}

		return generatedFlavours;

	}



	public static List<String> tex2rtf(String filelink
			, String target_flavour_filelink
			, String target_directory, String target_type)
		throws Exception {

		List<String> generatedFlavours = new ArrayList<String>();

		// dependencies:
		boolean areAllDependenciesMet = true; // <- assumption.
		String intermediate_filelink
			= Global.replaceEnding(target_flavour_filelink, ".odt");
		tex2odt(filelink, intermediate_filelink
				, target_directory, target_type);
		if (!new File(intermediate_filelink).exists()) {
			System.out.println("TEX2ODT apparently failed as there is"
					+ " no existing odt-file: "
					+ intermediate_filelink);
			areAllDependenciesMet = false;
		}

		if (!areAllDependenciesMet) {
			System.err.println("One or more dependencies are not met."
					+ " This could be due to a failure during the"
					+ " creation of intermediate temporary file"
					+ " flavours that are required to finally convert"
					+ " to this desired target flavour file.");
			return generatedFlavours;
		}

		generatedFlavours
			.addAll(odt2rtf(filelink, target_flavour_filelink));

		return generatedFlavours;

	}



	public static List<String> tex2txt(String filelink
			, String target_flavour_filelink) throws Exception {

		List<String> generatedFlavours = new ArrayList<String>();
		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}

		// Note: opendetex is cross-platform in principle. So here
		// it is not required to stop if cross-platform is forced.

		// Method 2)
		String filelink_absolute_txt;
		filelink_absolute_txt = target_flavour_filelink;
		final Command[] cmdList = {
				UnixComandosThread
					.getCommand_tex2txt(filelink, filelink_absolute_txt)
		};
		new UnixComandosThread(filelink, target_flavour_filelink)
			.d_o(cmdList);

		String[] plainText = ReadWrite.loadText(filelink_absolute_txt);


		// Method 1)
		// At this point a pdf file has to be generated. TODO e.g.
		// using TexManipulator.java, portable LaTex in root directory.
		//this.plainText = PdfKonverter
		//	.textAusPdf(filelink.replaceAll("[.]tex$", ".pdf"));
//		this.rawContent = ReadWrite.loadText(filelink);


		return generatedFlavours;

	}






	//======= TXT =======/
	public static List<String> txt2image(String filelink
			, String target_flavour_filelink) throws IOException {

		if (!isExistingElseMessage(filelink)) {
			return new ArrayList<String>();
		}
		return txt2image(ReadWrite.loadText(filelink)
				, target_flavour_filelink);
	}

	public static List<String> txt2image(String[] plainText
			, String target_flavour_filelink) throws IOException {

		List<String> generatedFlavours = new ArrayList<String>();
		Global.createImageFromText(plainText, 1023
				, target_flavour_filelink);

		if (new File(target_flavour_filelink).exists()) {
			generatedFlavours.add(target_flavour_filelink);
		}
		return generatedFlavours;
	}



	//======= HELPER
	public static boolean isExistingElseMessage(String filelink) {
		if (!new File(filelink).exists()) {
			System.out.println("Source filelink not exists: "
					+ filelink);
			return false;
		}
		return true;
	}



}
