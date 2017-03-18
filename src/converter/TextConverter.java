package converter;
/**
 * This is not split up into several classes as several methods are reused.
 * Type2TextConverter, converts
 * pdf
 * odt
 * doc
 * docx
 * ppt
 * xls
 * rtf
 * to pure/plain text.(splittable by \n or \r\n or best System.getProperty("line.separator"))
 */
//Originates from:
//http://codezrule.wordpress.com/2010/03/24/extract-text-from-pdf-office-files-doc-ppt-xls-open-office-files-rtf-and-textplain-files-in-java/
//Fixed by J.R.I.B.-Wein
//DEPENDENCIES:
//	FontBox-0.1.0-dev.jar
//	jdom.jar (we use jdom2 now JRIBWein)
//	log4j-1.2.15.jar
//	PDFBox-0.7.3-dev.jar
//	poi-2.5.1-final-20040804.jar
//	poi-contrib-2.5.1-final-20040804.jar
//	poi-scratchpad-2.5.1-final-20040804.jar
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.bind.JAXBElement;
import org.apache.commons.lang.NotImplementedException;
//import org.apache.poi.hdf.extractor.WordDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.LittleEndian;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jvnet.jaxb2_commons.ppp.Child;

import org.docx4j.Docx4J;
import org.docx4j.TraversalUtil;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.Br;
import org.docx4j.wml.CommentRangeEnd;
import org.docx4j.wml.CommentRangeStart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.SdtBlock;
import org.docx4j.wml.R.CommentReference;
import org.docx4j.wml.R.Tab;

import core.Global;
import core.ReadWrite;

import java.io.BufferedReader;
import java.io.FileReader;


// TODO Employ as little as toPlainText and let subclasses override it.
public abstract class TextConverter {

	static StringBuffer sb = new StringBuffer(8192);
	static StringBuffer textBuffer = new StringBuffer();


	public static String pdf2text(String fileName) {
		//TODO Use reflection or file existence check instead?
		//if (Global.root + )
		try {
			return PDFConverter.pdf2text(fileName);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("PDFConverter plugin not found in package 'converter'.");
		}

		return null;

	}

	public static String rtf2text(InputStream is) throws Exception {
		return RTFConverter.rtf2text(is);
	}

	public static String ppt2text(String fileName) throws Exception {
		POIFSReader poifReader = new POIFSReader();
		poifReader.registerListener(new TextConverter.MyPOIFSReaderListener());
		poifReader.read(new FileInputStream(fileName));
		return sb.toString();
	}

	static class MyPOIFSReaderListener implements POIFSReaderListener {

		public void processPOIFSReaderEvent(POIFSReaderEvent event) {
			char ch0 = (char) 0;
			char ch11 = (char) 11;
			try {
				DocumentInputStream dis = null;
				dis = event.getStream();
				byte btoWrite[] = new byte[dis.available()];
				dis.read(btoWrite, 0, dis.available());
				for (int i = 0; i < btoWrite.length - 20; i++) {
					long type = LittleEndian.getUShort(btoWrite, i + 2);
					long size = LittleEndian.getUInt(btoWrite, i + 4);
					if (type == 4008) {
						try {
							String s = new String(btoWrite, i + 4 + 1, (int) size + 3).replace(ch0, ' ').replace(ch11, ' ');
							if (s.trim().startsWith("Click to edit") == false) {
								sb.append(s);
							}
						}
						catch (Exception ee) {
							System.out.println("error:" + ee);
						}
					}
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		}
	}


	public static String xls2text(InputStream in) throws Exception {
		HSSFWorkbook excelWb = new HSSFWorkbook(in);
		StringBuffer result = new StringBuffer(4096);
		int numberOfSheets = excelWb.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			HSSFSheet sheet = excelWb.getSheetAt(i);
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			if (numberOfRows > 0) {
				if (excelWb.getSheetName(i) != null && excelWb.getSheetName(i).length() != 0) {
// append sheet name to content
					if (i > 0) {
						result.append("\n\n");
					}
					result.append(excelWb.getSheetName(i).trim());
					result.append(":\n\n");
				}

				Iterator<Row> rowIt = sheet.rowIterator();
				while (rowIt.hasNext()) {
					HSSFRow row = (HSSFRow)rowIt.next();
					if (row != null) {
						boolean hasContent = false;
						Iterator<Cell> it = row.cellIterator();
						while (it.hasNext()) {
							HSSFCell cell = (HSSFCell)it.next();
							String text = null;
							try {
								switch (cell.getCellType()) {
									case HSSFCell.CELL_TYPE_BLANK:
									case HSSFCell.CELL_TYPE_ERROR:
// ignore all blank or error cells
										break;
									case HSSFCell.CELL_TYPE_NUMERIC:
										text = Double.toString(cell.getNumericCellValue());
										break;
									case HSSFCell.CELL_TYPE_BOOLEAN:
										text = Boolean.toString(cell.getBooleanCellValue());
										break;
									case HSSFCell.CELL_TYPE_STRING:
									default:
										text = cell.getStringCellValue();
										break;
								}
							} catch (Exception e) {
							}
							if ((text != null) && (text.length() != 0)) {
								result.append(text.trim());
								result.append(' ');
								hasContent = true;
							}
						}
						if (hasContent) {
// append a newline at the end of each row that has content
							result.append('\n');
						}
					}
				}
			}
		}
		return result.toString();
	}


	//======= OPEN OFFICE
	/*Added support for headings, proper insertion of line separators, rearrangements.
	 *Features will be added more and more depending on about how many new tags we find out.*/
	public static void processElement(Object o) {
		if (o instanceof Element) {
			Element e = (Element) o;
			String elementName = e.getQualifiedName();


			//add additional formatting
			//standalone elements
			if (elementName.equals("text:tab")) { // add tab for text:tab
				textBuffer.append("\t");
				//return;
			}
			else if (elementName.equals("text:line-break")) {
				textBuffer.append(System.getProperty("line.separator"));
				//return;
			}
			else if (elementName.equals("text:s")) { // add space for text:s
				textBuffer.append(" ");
				//return;
			}

			//non-standalone nodes
			if (elementName.equals("text:p") || elementName.equals("text:h")
					|| elementName.equals("text:list")
					/*|| elementName.equals("text:list-item")*/
				//DOCX
				|| elementName.equals("w:p") || elementName.equals("w:hdr")
					) {
				/*formerly simply \n without rewind \r*/
				textBuffer.append(System.getProperty("line.separator"));
			}
			else if (elementName.equals("text:note-citation")) {
				textBuffer.append(" (");
			}
			else if (elementName.equals("text:span")
					|| elementName.equals("w:r")) {
				//textBuffer.append(" ");<-- this creates a mess as it liberately adds spaces! it's assumed spaces are where they are required already.
			}

			/*Text only nodes or better said its text-only contents will be added here:*/
			List<Content> children = e.getContent();
			Iterator<?> iterator = children.iterator();
			while (iterator.hasNext()) {
				Object child = iterator.next();
				processElement(child); // Recursively process the child element
			}

			//Another line break to be inserted?
			if (elementName.equals("text:p") || elementName.equals("text:h")
					|| elementName.equals("text:list")
					|| elementName.equals("text:list-item")
					) {
				/*formerly simply \n without rewind \r*/
				textBuffer.append(System.getProperty("line.separator"));
			}
			else if (elementName.equals("text:note-citation")) {
				textBuffer.append(") ");
			}
			else if (elementName.equals("text:span") || elementName.equals("w:r")) {
				//textBuffer.append(" ");<-- this creates a mess as it liberately adds spaces! it's assumed spaces are where they are required already.
			}
		}
		//If Child is a Text Node, then append the text
		else if (o instanceof Text) {
			Text t = (Text) o;
			textBuffer.append(t.getValue());
			//textBuffer.append(System.getProperty("line.separator"));
		}

	}


	/**
	 * Extract pure/plain text from the DOM using JDOM instead of org.w3c.DOM here.
	 * @param filelink The file to load the ODT from (hence the input or source).
	 * @return Text of all elements concatenated.
	 * @throws Exception If the SAX parsing or ZipFile handling fails.
	 */
	public static String odt2text(String filelink) throws Exception {
		textBuffer = new StringBuffer();
//Unzip the openOffice Document
		ZipFile zipFile = new ZipFile(filelink);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		ZipEntry entry;
		while (entries.hasMoreElements()) {
			entry = (ZipEntry) entries.nextElement();
			if (entry.getName().equals("content.xml")) {
				textBuffer = new StringBuffer();
				SAXBuilder sax = new SAXBuilder();
				Document doc = sax.build(zipFile.getInputStream(entry));
				Element rootElement = doc.getRootElement();
				processElement(rootElement);
				break;
			}
		}
		zipFile.close();
		return textBuffer.toString();
	}



	/**
	 * Extract pure/plain text from HTML reusing the way it's done for ODT above.
	 * @param filelink The file to load the HTML from (hence the input or source).
	 * @return Text of all elements concatenated.
	 */
	public static String html2text(String filelink) {
		textBuffer = new StringBuffer();
		org.htmlcleaner.HtmlCleaner cleaner = new org.htmlcleaner.HtmlCleaner();
		org.htmlcleaner.TagNode rootNode = cleaner.clean(filelink);
		Element rootElement = new org.htmlcleaner.JDomSerializer(cleaner.getProperties(), true)
				.createJDom(rootNode).getRootElement();
		processElement(rootElement);
		return textBuffer.toString();
	}
//	public static String[] html2text(String filelink) {
//		return html2text(filelink).split(System.getProperty("line.separator"));
//	}







	//======= BILL GATES


	public static String docx2text(String fileName) throws Exception {
		textBuffer = new StringBuffer();
		//Unzip the openOffice Document
		ZipFile zipFile = new ZipFile(fileName);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		ZipEntry entry;
		while (entries.hasMoreElements()) {
			entry = (ZipEntry) entries.nextElement();
			if (entry.getName().equals("word/document.xml")) {
				textBuffer = new StringBuffer();
				SAXBuilder sax = new SAXBuilder();
				Document doc = sax.build(zipFile.getInputStream(entry));
				Element rootElement = doc.getRootElement();
				processElement(rootElement);
				//DOCX_travelAllElements(rootElement);
				break;
			}
		}
		return textBuffer.toString();
	}

	public static String[] docx2txt(String filelink) throws Exception {
		WordprocessingMLPackage wMLPac = Docx4J.load(new File(filelink));
		MainDocumentPart mainDocumentPart = wMLPac.getMainDocumentPart();
		org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)mainDocumentPart.getJaxbElement();

		Body rootElement = wmlDocumentEl.getBody();

		DOCX_travelDownUntilDeclarationFound(rootElement);
		ReadWrite.write(textBuffer.toString(), Global.replaceEnding(filelink, ".txt.txt"));

		return textBuffer.toString().split(System.getProperty("line.separator"));
	}





public static void DOCX_travelDownUntilDeclarationFound(Object o) {

	List<Object> children = null;

	//=======1a)
	//TEXT
	//To find the declaration the text tags have to be evaluated.
	if (o instanceof org.docx4j.wml.Text) {
		//If object is a Text Node, then append the text.
		org.docx4j.wml.Text t = (org.docx4j.wml.Text) o;
		textBuffer.append(t.getValue());
		//textBuffer.append(System.getProperty("line.separator"));//TODO former line.separator
	}
	//TEXT VIA JAXBElement.
	else if (o instanceof JAXBElement) {
		JAXBElement e = (JAXBElement) o;
		String tagname = e.getName().getLocalPart();//getQualifiedName();
		System.out.println(tagname);
		//Child unwrapped = (Child)XmlUtils.unwrap(o);


		//TEXT
		//if (tagname.equals("t")) {
		if (e.getValue() instanceof org.docx4j.wml.Text) {
			textBuffer.append(
					((org.docx4j.wml.Text) e.getValue()).getValue()
			);
		}
		//}



		//Get childnodes wrapped into JAXBElement.
		//General approach:
		else if (e.getValue() instanceof ContentAccessor) {
			children = ((org.docx4j.wml.ContentAccessor) e.getValue()).getContent();
		}

		//Specific approach (TODO use if tables have to be dealt with more closely)
		//TABLE
		//if (tagname.equals("tbl")) {
		else if (e.getValue() instanceof org.docx4j.wml.Tbl) {
			children = ((org.docx4j.wml.Tbl) e.getValue()).getContent();//getChildren(e.getValue());
		}
		//}
		//if (tagname.equals("tr")) {
		else if (e.getValue() instanceof org.docx4j.wml.Tr) {
			children = ((org.docx4j.wml.Tr) e.getValue()).getContent();//getChildren(e.getValue());
		}
		//}
	}



	//======= 2)
	//No pure text to add. Then we have to add some styling elemens (standalone et alia).
	//Add additional formatting:

	//-------standalone elements
	if (o instanceof Br) {
		textBuffer.append(System.getProperty("line.separator"));
		//return;
	}
	else if (o instanceof Drawing) {

	}
	else if (o instanceof org.docx4j.wml.R.Separator) {//TODO clarify
		textBuffer.append(System.getProperty(" "));
		//return;
	}
	else if (o instanceof Tab
			|| o instanceof org.docx4j.wml.R.Ptab) {
		textBuffer.append(System.getProperty("\t"));
		//return;
	}



	//-------non-standalone elements
	//Paragraph, Header or Table only
	else if (o instanceof org.docx4j.wml.P
			|| o instanceof org.docx4j.wml.Hdr
			|| o instanceof org.docx4j.wml.Tbl) {
		textBuffer.append(System.getProperty("line.separator"));
		//return;
	}
	//P only
	if (o instanceof org.docx4j.wml.P) {
		org.docx4j.wml.P p = (org.docx4j.wml.P) o;

//		if (p.getPPr() != null && p.getPPr().getPStyle() != null) {
//		}

//		if (p.getPPr() != null && p.getPPr().getRPr() != null) {
//		}
		//getRunContent( p.getContent(p), parent, pdfParagraph);

//		if (parent instanceof Document) {
//			((Document)parent).add(pdfParagraph);
//		} else if (parent instanceof PdfPTable) {
//
//			((PdfPTable)parent).addCell(
//					new PdfPCell(pdfParagraph)
//			);
//		} else {
//			log.error("Trying to add paragraph to " + parent.getClass().getName() );
//		}
//

	//LIST TODO - the following identifies a list, list seems to be a paragraph?
		//http://stackoverflow.com/questions/7799585/how-to-append-new-list-item-to-a-list
		//public List<Object> apply(Object obj) {
		//if (obj instanceof org.docx4j.wml.P) {
			Object deepCopy = null;

//			if (p.getPPr() != null) {
//				if (p.getPPr().getPStyle() != null) {
//					if ((p.getPPr().getPStyle().getVal().equals("Akapitzlist")) && (akapListCounter < 10)) {
//
//						if ((p.getPPr().getPStyle() != null) {
//							if (((p.getPPr().getPStyle().getVal().equals("Akapitzlist"))) {
//								deepCopy = XmlUtils.deepCopy(o);
//								akapListCounter++;
//								int indexOf = wmlDocumentEl.getBody().getContent().indexOf(obj);
//
//
//								List<Object> content = ((org.docx4j.wml.P) deepCopy).getContent();
//								for (Object el : content) {
//									System.out.println("class1:" + el.getClass().toString());
//									if (el instanceof org.docx4j.wml.R) {
//										List<Object> subc = ((org.docx4j.wml.R) el).getContent();
//										for (Object r : subc) {
//											((javax.xml.bind.JAXBElement) r).setValue("tetetete");
//										}
//									}
//
//								}// end for
//
//
//								wmlDocumentEl.getBody().getContent().add(indexOf + 1, deepCopy);
//
//
//							}
//						}//end get style
//
//					}
//				}
//			} else {}


		//}
		//	return null;
		//}



	}
	//else if (elementName.equals("text:note-citation")) {
	else if (o instanceof org.docx4j.wml.CTFootnotes) {
		textBuffer.append(" (");
	}
	//else if (elementName.equals("text:span")) {
	else if (o instanceof org.docx4j.wml.R) {
		textBuffer.append("");//if we append something here (e.g. a space)
		//then we get into serious trouble if we want to find e.g. Uebung, but it's
		//then Ue bung what is possible as a R(un)/SPAN tag allows styling of individual
		//characters.
	}
	else if (o instanceof org.docx4j.wml.SdtBlock) {
		org.docx4j.wml.SdtBlock sdt = (org.docx4j.wml.SdtBlock) o;
		// Don't bother looking in SdtPr
//		traverseBlockLevelContent(sdt.getSdtContent().getContent(),
//				parent);
//		} else if (o instanceof org.docx4j.wml.SdtContentBlock) {
//
//			org.docx4j.wml.SdtBlock sdt = (org.docx4j.wml.SdtBlock) o;
//
//			// Don't bother looking in SdtPr
//
//			traverseMainDocumentRecursive(sdt.getSdtContent().getEGContentBlockContent(),
//					fontsDiscovered, stylesInUse);

	}
	else if (o instanceof org.docx4j.wml.Tbl) {
		//If object is a Table Node, then dive deeper to rows and along the columns.
		org.docx4j.wml.Tbl table = (org.docx4j.wml.Tbl) o;
//		List<Object> children = getChildren(table);
	}
	else if (o instanceof org.w3c.dom.Node) {
		// If Xerces is on the path, this will be a org.apache.xerces.dom.NodeImpl;
		// otherwise, it will be com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

		// Ignore these, eg w:bookmarkStart
		//log.debug("not traversing into unhandled Node: " + ((org.w3c.dom.Node)o).getNodeName() );
	}
	else if (o instanceof JAXBElement) {
		//log.debug( "Encountered " + ((JAXBElement) o).getDeclaredType().getName() );
//			if (o instanceof javax.xml.bind.JAXBElement
//			&& (((JAXBElement)o).getName().getLocalPart().equals("commentReference")
//					|| ((JAXBElement)o).getName().getLocalPart().equals("commentRangeStart")
//					|| ((JAXBElement)o).getName().getLocalPart().equals("commentRangeEnd")
//					)) {
		System.out.println(((JAXBElement)o).getName().getLocalPart());
		//Child unwrapped = (Child)XmlUtils.unwrap(o);
		//TEXT
//			if (((JAXBElement)o).getName().getLocalPart().equals("t")) {
//				textBuffer.append(((JAXBElement)((JAXBElement) o).getValue()).getValue());
//			}
		//sheetdraftElementsTraversed.add( (Child)XmlUtils.unwrap(o) );

		//			if (((JAXBElement) o).getDeclaredType().getName().equals(
		//					"org.docx4j.wml.P")) {
		//				org.docx4j.wml.P p = (org.docx4j.wml.P) ((JAXBElement) o)
		//						.getValue();

	}
	else if (o instanceof CommentReference ||  o instanceof CommentRangeStart
			|| o instanceof CommentRangeEnd) {
		System.out.println(o.getClass().getName());
		//sheetdraftElementsTraversed.add((Child)o);
	}
	else {
		System.out.println( "UNEXPECTED: " + o.getClass().getName() );
	}



	/*Text only nodes of deeper level/depth or better said its text-only contents will be added here:*/
	//Had the children been created via the JAXBElement way?
	if (children == null) {
	/*List<Object>*/ children = TraversalUtil.getChildrenImpl(o);//checks for ContentAccessor.getContent, other
	}
	if (children != null) {				   //elements' XML content and if Text then null!
		//Iterator<?> iterator = children.iterator();
		//while (iterator.hasNext()) {
		//	Object child = iterator.next();
		for (int i = 0; i < children.size(); i++) {


			Object child = children.get(i);


			// if its wrapped in javax.xml.bind.JAXBElement, get its
			// value; this is ok, provided the results of the Callback
			// won't be marshalled -- THEY WILL BE MARSHALLED FOR SAVING THE DOCUMENT!
			// SO AT LEAST IT IS REQUIRED TO unmarschalString again. Anyway, we use
			// remove function that handles this temporary(?) unwrap.
			//child = XmlUtils.unwrap(child);//<-- not marshalled means it will not be saved (e.g. if copying this node)


			// workaround for broken getParent (since 3.0.0)
			if (child instanceof Child) {
				if (o instanceof SdtBlock) {
					((Child)child).setParent( ((SdtBlock)o).getSdtContent() );
						/*
						 * getParent on eg a P in a SdtBlock should return SdtContentBlock, as
						 * illustrated by the following code:
						 *
								SdtBlock sdtBloc = Context.getWmlObjectFactory().createSdtBlock();
								SdtContentBlock sdtContentBloc = Context.getWmlObjectFactory().createSdtContentBlock();
								sdtBloc.setSdtContent(sdtContentBloc);
								P p = Context.getWmlObjectFactory().createP();
								sdtContentBloc.getContent().add(p);
								String result = XmlUtils.marshaltoString(sdtBloc, true);
								System.out.println(result);
								SdtBlock rtp = (SdtBlock)XmlUtils.unmarshalString(result, Context.jc, SdtBlock.class);
								P rtr = (P)rtp.getSdtContent().getContent().get(0);
								System.out.println(rtr.getParent().getClass().getName() );
						 *
						 * Similarly, P is the parent of R; the p.getContent() list is not the parent
						 *
								P p = Context.getWmlObjectFactory().createP();
								R r = Context.getWmlObjectFactory().createR();
								p.getContent().add(r);
								String result = XmlUtils.marshaltoString(p, true);
								P rtp = (P)XmlUtils.unmarshalString(result);
								R rtr = (R)rtp.getContent().get(0);
								System.out.println(rtr.getParent().getClass().getName() );
						 */
				// TODO: other corrections
				} else {
					((Child)child).setParent(o);
				}
			}
			else if (child instanceof javax.xml.bind.JAXBElement
//					&& (((JAXBElement)child).getName().getLocalPart().equals("commentReference")
//							|| ((JAXBElement)child).getName().getLocalPart().equals("commentRangeStart")
//							|| ((JAXBElement)child).getName().getLocalPart().equals("commentRangeEnd"))
							) {
				((Child)((JAXBElement)child).getValue()).setParent(/*XmlUtils.unwrap(*/ o /*)*/
				);//o := parent here
			}



			// Recursively process the child element
			DOCX_travelDownUntilDeclarationFound(child);


		}


	}/*if children != null -END*/






	//-------non-standalone elements
	//Another line break to be inserted?
	if (o instanceof org.docx4j.wml.P
			|| o instanceof org.docx4j.wml.Hdr
			|| o instanceof org.docx4j.wml.Tbl) {
		textBuffer.append(System.getProperty("line.separator"));
		//return;
	}
	//else if (elementName.equals("text:note-citation")) {
	else if (o instanceof org.docx4j.wml.CTFootnotes) {
		textBuffer.append(") ");
	}
	//else if (elementName.equals("text:span")) {
	else if (o instanceof org.docx4j.wml.R) {
		textBuffer.append("");//same as above at the opening R tag is true here too!
							  //Better don't append anything here!
	}





	//=> We reached the end, no more elements and still the termination condition did
	//not grip!! => No such element we looked for was found in this branch!
	return ;
}











	//======= HELPER
	public static String fileToStringNow(File f) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String nextLine = "";
		StringBuffer sbuff = new StringBuffer();
		while ((nextLine = br.readLine()) != null) {
			sbuff.append(nextLine);
			sbuff.append(System.getProperty("line.separator"));
		}
		br.close();
		return sbuff.toString();
	}
















	//======= MAIN
//	public static void main(String[] args) throws Exception {
//		//TextConverter rff = new TextConverter();
//		System.out.print("Enter File Name => ");
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String fileName = br.readLine();
//		File f = new File(fileName);
//		if (!f.exists()) {
//			System.out.println("Sorry File does not Exists!");
//		} else {
//			if (f.getName().endsWith(".pdf") || f.getName().endsWith(".PDF")) {
//				System.out.println(pdf2text(fileName));
//			} else if (f.getName().endsWith(".doc") || f.getName().endsWith(".DOC")) {
//				System.out.println(doc2text(fileName));
//			} else if (f.getName().endsWith(".rtf") || f.getName().endsWith(".RTF")) {
//				System.out.println(rtf2text(new FileInputStream(f)));
//			} else if (f.getName().endsWith(".ppt") || f.getName().endsWith(".PPT")) {
//				System.out.println(ppt2text(fileName));
//			} else if (f.getName().endsWith(".xls") || f.getName().endsWith(".XLS")) {
//				System.out.println(xls2text(new FileInputStream(f)));
//			} else if (f.getName().endsWith(".odt") || f.getName().endsWith(".ODT") || f.getName().endsWith(".ods") || f.getName().endsWith(".ODS") || f.getName().endsWith(".odp") || f.getName().endsWith(".ODP")) {
//				System.out.println(odt2text(fileName));
//			} else {
//				System.out.println(fileToStringNow(f));
//			}
//		}
//		br.close();
//	}
}
