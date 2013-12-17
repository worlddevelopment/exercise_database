package docx4j_library;



import java.util.List;



import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.wml.Body;
import org.docx4j.wml.ContentAccessor;
//import org.docx4j.wml.P;
//import org.docx4j.wml.Tbl;
//import org.docx4j.wml.Tc;
//import org.docx4j.wml.Text;
//import org.docx4j.wml.Tr;
////import org.docx4j.wml.CommentRangeEnd;
////import org.docx4j.wml.CommentRangeStart;
////import org.docx4j.wml.R.CommentReference;
import org.jvnet.jaxb2_commons.ppp.Child;


//import org.docx4j.jaxb.WmlSchema;
//import org.docx4j.model.datastorage.BindingHandler;


//import com.google.common.base.Function;
//import com.google.common.collect.Lists;



public class DocxUtils {
	
	
	
	public static boolean remove(List<Object> theList, Object bm) 	{
		// Can't just remove the object from the parent,
		// since in the parent, it may be wrapped in a JAXBElement
		for (Object ox : theList) {
			if (XmlUtils.unwrap(ox).equals(bm) || ox.equals(bm)) {
				return theList.remove(ox);
			}
		}
		return false;
	}
	
//
//	//http://stackoverflow.com/questions/15827498/docx4j-images-in-the-document
//	public static main(String[] args) {	
//		
//		wordMLPackage = getTemplate("C:\\template.docx");
//		
//		factory = Context.getWmlObjectFactory();
//		
//		List elemetns = getAllElementFromObject(wordMLPackage.getMainDocumentPart(), Tbl.class);
//		
//		for(Object obj : elemetns){
//		   if(obj instanceof Tbl){
//		      Tbl table = (Tbl) obj;
//		         List rows = getAllElementFromObject(table, Tr.class);
//		            for(Object trObj : rows){
//		        Tr tr = (Tr) trObj;
//		        List cols = getAllElementFromObject(tr, Tc.class);
//		        for(Object tcObj : cols){
//		           Tc tc = (Tc) tcObj;
//		           List texts = getAllElementFromObject(tc, Text.class);
//		           for(Object textObj : texts){
//		             Text text = (Text) textObj;
//		                     if(text.getValue().equalsIgnoreCase("${MY_PLACE_HOLDER}")){
//		                        File file = new File("C:\\image.jpeg");
//		                P paragraphWithImage = addInlineImageToParagraph(createInlineImage(file));
//		                        tc.getContent().remove(0);
//		              
//		                        tc.getContent().add(paragraphWithImage);
//		             }
//		                  }
//		          System.out.println("here");
//		        }
//		           }
//		        System.out.println("here");
//		    }
//		}
//		
//		wordMLPackage.save(new java.io.File("C:\\result.docx"));
//	}
//
//
//	
//	private static P addInlineImageToParagraph(Inline inline) {
//	        // Now add the in-line image to a paragraph
//	        ObjectFactory factory = new ObjectFactory();
//	        P paragraph = factory.createP();
//	        R run = factory.createR();
//	        paragraph.getContent().add(run);
//	        Drawing drawing = factory.createDrawing();
//	        run.getContent().add(drawing);
//	        drawing.getAnchorOrInline().add(inline);
//	        return paragraph;
//	}
//	
//	
//	 private static Inline createInlineImage(File file) throws Exception {
//	        byte[] bytes = convertImageToByteArray(file);
//	 
//	        BinaryPartAbstractImage imagePart =
//	            BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
//	 
//	        int docPrId = 1;
//	        int cNvPrId = 2;
//	 
//	        return imagePart.createImageInline("Filename hint",
//	                "Alternative text", docPrId, cNvPrId, false);
//	 }
//	
//	
//	private static byte[] convertImageToByteArray(File file)
//	            throws FileNotFoundException, IOException {
//	     InputStream is = new FileInputStream(file );
//	     long length = file.length();
//	     // You cannot create an array using a long, it needs to be an int.
//	     if (length > Integer.MAX_VALUE) {
//	            System.out.println("File too large!!");
//	     }
//	     byte[] bytes = new byte[(int)length];
//	     int offset = 0;
//	     int numRead = 0;
//	     while (offset= 0) {
//	            offset += numRead;
//	     }
//	     // Ensure all the bytes have been read
//	     if (offset < bytes.length) {
//	            System.out.println("Could not completely read file "+file.getName());
//	     }
//	     is.close();
//	     return bytes;
//	 }
//	
//	
//	 private static WordprocessingMLPackage getTemplate(String name) 
//	                        throws Docx4JException, FileNotFoundException {
//	   WordprocessingMLPackage template = 
//	           WordprocessingMLPackage.load(new FileInputStream(new File(name)));
//	    return template;
//	}
//	
//	private static List getAllElementFromObject(Object obj, Class toSearch) {
//	   List result = new ArrayList();
//	   if (obj instanceof JAXBElement) 
//	       obj = ((JAXBElement) obj).getValue();
//	     
//	   if (obj.getClass().equals(toSearch)){
//	         result.add(obj);
//	   }
//	   else if (obj instanceof ContentAccessor) {
//	         List children = ((ContentAccessor) obj).getContent();
//	         for (Object child : children) {
//	        result.addAll(getAllElementFromObject(child, toSearch));
//	         }
//	     
//	   }
//	   return result;
//	}
//
//	
//
//
//	
//	
//	
//	
//
//	
//	
//	//www.docx4java.org/forums/docx-java-f6/replace-paragraph-remove-the-original-one-read-null-t1624.html#wrap
//	private static WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
//	
//		name ="C:\\Users\\nitspl\\Desktop\\template001.docx";
//		WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(name));
//		return template;
//	}
//	private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
//		List<Object> result = new ArrayList<Object>();
//		if (obj instanceof JAXBElement)
//			obj = ((JAXBElement<?>) obj).getValue();
//		
//		if (obj.getClass().equals(toSearch))
//			result.add(obj);
//		else if (obj instanceof ContentAccessor) {
//			List<?> children = ((ContentAccessor) obj).getContent();
//			for (Object child : children) {
//				result.addAll(getAllElementFromObject(child, toSearch));
//			}
//		
//		}
//		return result;
//	}
//	@SuppressWarnings("unused")
//	private void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder ) {
//		List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
//	
//		for (Object text : texts) {
//			Text textElement = (Text) text;
//			if (textElement.getValue().equals(placeholder)) {
//				textElement.setValue(name);
//			}
//		}
//	}
//	
//	@SuppressWarnings({ "rawtypes" })
//	private static void replaceParagraph(String placeholder, String toAdd, WordprocessingMLPackage template, ContentAccessor addTo) {
//		// 1. get the paragraph
//		List<Object> paragraphs = getAllElementFromObject(template.getMainDocumentPart(), P.class);
//		
//		P toReplace =new P();
//		for (Object p : paragraphs) {
//			List<Object> texts = getAllElementFromObject(p, Text.class);
//			for (Object t : texts) {
//				Text content = (Text) t;
//				if (content.getValue().equals(placeholder)) {
//					toReplace = (P) p;
//					break;
//				}
//			}
//		}
//	
//		// we now have the paragraph that contains our placeholder: toReplace
//		// 2. split into seperate lines
//		String as[] = StringUtils.splitPreserveAllTokens(toAdd, '\n');
//		
//		for (int i = 0; i < as.length; i++) {
//			String ptext = as[i];
//		
//			System.out.println(ptext);
//			
//			// 3. copy the found paragraph to keep styling correct
//			P copy = (P) XmlUtils.deepCopy(toReplace);
//		
//			// replace the text elements from the copy
//			List texts = getAllElementFromObject(copy, Text.class);
//			if (texts.size() > 0) {
//				Text textToReplace = (Text) texts.get(0);
//				textToReplace.setValue(ptext);
//			}
//	
//			// add the paragraph to the document
//			addTo.getContent().add(copy);
//		}
//	
//		// 4. remove the original one
//	
//		((ContentAccessor)toReplace.getParent()).getContent().remove(toReplace);
//	
//	}
//	
//	
//	
//	private static void replaceTable(String[] placeholders, List<Map<String, String>> textToAdd,
//		WordprocessingMLPackage template) throws Docx4JException, JAXBException {
//		List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);
//		
//		// 1. find the table
//		Tbl tempTable = getTemplateTable(tables, placeholders[0]);
//		List<Object> rows = getAllElementFromObject(tempTable, Tr.class);
//		
//		// first row is header, second row is content
//		if (rows.size() == 2) {
//			// this is our template row
//			Tr templateRow = (Tr) rows.get(1);
//			
//			for (Map<String, String> replacements : textToAdd) {
//			// 2 and 3 are done in this method
//				addRowToTable(tempTable, templateRow, replacements);
//			}
//		
//			// 4. remove the template row
//			tempTable.getContent().remove(templateRow);
//		}
//	}
//	
//	
//	
//	@SuppressWarnings("rawtypes")
//	private static void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) {
//		Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
//		List textElements = getAllElementFromObject(workingRow, Text.class);
//		for (Object object : textElements) {
//			Text text = (Text) object;
//			String replacementValue = (String) replacements.get(text.getValue());
//			if (replacementValue != null)
//				text.setValue(replacementValue);
//		}
//		
//		reviewtable.getContent().add(workingRow);
//	}
//	
//	
//	private static Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
//		for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
//			Object tbl = iterator.next();
//			List<?> textElements = getAllElementFromObject(tbl, Text.class);
//			for (Object text : textElements) {
//				Text textElement = (Text) text;
//				if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
//					return (Tbl) tbl;
//			}
//		}
//		return null;
//	}
//	
//	
//	
//	static WordprocessingMLPackage template;
//
//	public static void main(String[] args) throws Docx4JException, JAXBException, FileNotFoundException {
//		String placeholder = "SJ_EX1";
//		String toAdd = "jos";
//	
//		// FileInputStream fis=new FileInputStream("C:\\Users\\nitspl\\Desktop\\template001.docx");
//		
//		// WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(fis);
//		// template=wordMLPackage;
//		
//		String name="C:\\Users\\nitspl\\Desktop\\template001.docx";
//		template= DocxUtils.getTemplate(name);
//		
//		System.out.println(template);
//		
//		replaceParagraph(placeholder, toAdd, template, template.getMainDocumentPart());
//		
//		Map<String,String> repl1 = new HashMap<String, String>();
//		repl1.put("SJ_FUNCTION", "function1");
//		repl1.put("SJ_DESC", "desc1");
//		repl1.put("SJ_PERIOD", "period1");
//		
//		Map<String,String> repl2 = new HashMap<String,String>();
//		repl2.put("SJ_FUNCTION", "function2");
//		repl2.put("SJ_DESC", "desc2");
//		repl2.put("SJ_PERIOD", "period2");
//		
//		Map<String,String> repl3 = new HashMap<String,String>();
//		repl3.put("SJ_FUNCTION", "function3");
//		repl3.put("SJ_DESC", "desc3");
//		repl3.put("SJ_PERIOD", "period3");
//		
//		replaceTable(new String[]{"SJ_FUNCTION","SJ_DESC","SJ_PERIOD"}, Arrays.asList(repl1,repl2,repl3), template);
//	
//	}
//	
//	
//	
//	
//	
//	
//
//	//http://www.docx4java.org/forums/docx-java-f6/remove-table-from-document-t1368.html
//	
//	
//	public static JAXBContext context = org.docx4j.jaxb.Context.jc; 
//
//	private final static boolean DEBUG = true;
//	private final static boolean SAVE = true;
//
//
//	public static void main(String[] args) throws Exception {
//
//		// the docx 'template'
//		String input_DOCX = "e:/temp/docs/test-cc.docx";
//
//
//		// resulting docx
//		String OUTPUT_DOCX = "e:/temp/docs/test-replaced.docx";
//
//		// Load input_template.docx
//		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(
//				new java.io.File(input_DOCX));
//
//		String itemId = "{B3833859-E0FA-4C6C-BAC6-DE5EFEB0B0B0}".toLowerCase();
//		CustomXmlDataStoragePart customXmlDataStoragePart = wordMLPackage.getCustomXmlDataStorageParts().get(itemId);
//		CustomXmlDataStorageImpl customXmlDataStorage = (CustomXmlDataStorageImpl)customXmlDataStoragePart.getData();
//		customXmlDataStorage.setNodeValueAtXPath("/adl/facIdentificationNumber", "MyfacIdentificationNumber",null); 
//		customXmlDataStorage.setNodeValueAtXPath("/adl/transmittalLetterDate", "MytransmittalLetterDate",null); 
//		customXmlDataStorage.setNodeValueAtXPath("/adl/granteeName", "MygranteeName",null); 
//		customXmlDataStorage.setNodeValueAtXPath("/adl/goName", "MygranteeName",null); 
//		customXmlDataStorage.setNodeValueAtXPath("/adl/granteeAddress", "MygranteeAddress",null);
//		customXmlDataStorage.setNodeValueAtXPath("/adl/formattedAuditNumber", "MyformattedAuditNumber",null);
//		List<Object> tables = getAllElementFromObject(wordMLPackage.getMainDocumentPart(), Tbl.class);
//		Tbl findingTable = getTemplateTable(tables, "auditFindingsTBL");
//		wordMLPackage.getMainDocumentPart().getContent().remove(findingTable);
//		
//
////		SaveToZipFile  saveToZipFile= new SaveToZipFile(wordMLPackage);
////        ByteArrayOutputStream  bos= new ByteArrayOutputStream();
////        saveToZipFile.save(bos);
////        bos.toByteArray();
//		wordMLPackage.save(new java.io.File(OUTPUT_DOCX) );
//
//	}
//	private static Child getParagraph(String text){
//		org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
//		org.docx4j.wml.P  p = factory.createP();
//		org.docx4j.wml.Text  t = factory.createText();
//		t.setValue(text);
//		org.docx4j.wml.R  run = factory.createR();
//		run.getContent().add(t);
//		p.getContent().add(run);
//		return p;
//	}
//	
//	static Function<String, List<Object>>  paragraphText= new Function<String, List<Object>>() {
//		@Override
//		public List<Object> apply(String input) {
//			try{
//				List<Object>  paragraphs=Lists.newArrayList();
//				
//				//paragraphs.add
//				if(StringUtils.isBlank(input)){
//					paragraphs.add(getParagraph(" "));
//				}else{
//					String htmlText=HTML2Text.convert(input) ;
//					for(LineIterator lineIterator= IOUtils.lineIterator(new StringReader(htmlText)); lineIterator.hasNext();){
//						paragraphs.add(getParagraph(lineIterator.nextLine()));
//					}
//				}	
//				//paragraphs.add(getParagraph(" "));
//				return paragraphs;
//			}catch (Exception e) {
//				throw  new RuntimeException(e);
//			}
//		}
//	};
//
//	private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
//		List<Object> result = new ArrayList<Object>();
//		if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
// 
//		if (obj.getClass().equals(toSearch))
//			result.add(obj);
//		else if (obj instanceof ContentAccessor) {
//			List<?> children = ((ContentAccessor) obj).getContent();
//			for (Object child : children) {
//				result.addAll(getAllElementFromObject(child, toSearch));
//			}
// 
//		}
//		return result;
//	}
//	
//	
//	private static Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
//		for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
//			Object tbl = iterator.next();
//			List<?> textElements = getAllElementFromObject(tbl, Text.class);
//			for (Object text : textElements) {
//				Text textElement = (Text) text;
//				if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
//					return (Tbl) tbl;
//			}
//		}
//		return null;
//	}	
//	static void fillTable(String placeholder, List<Object>  childObjects , Object  parentObject) {
//		try{
//			List<Object> tables = getAllElementFromObject(parentObject, Tbl.class);
//			Tbl tempTable = getTemplateTable(tables, placeholder);
//			if(tempTable==null)return;
//			List<Object> tcs = getAllElementFromObject(tempTable, Tc.class);
//			Tc  tc=(Tc)tcs.get(0);
//			List<Object> ps = getAllElementFromObject(tc, P.class);
//			tc.getContent().remove(ps.get(0));
//			for(Object  object  :childObjects){
//				tc.getContent().add(object);
//			}
//		}catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	
//	
//	
//	//http://www.docx4java.org/forums/docx-java-f6/most-generic-way-to-delete-an-element-t641.html
///*
//	Postby jason » Wed Mar 02, 2011 11:16 pm
//	There isn't any similar method, and yes, we could add this.
//
//	My only concern is those cases where, owing to the bug in JAXB, getParent returns the wrong thing (see earlier posts). We might be able to identify these cases and throw an exception.
//TRAVERSAL UTIL IS GIVING SOME EFFORT TO CORRECTLY SET THE PARENT. See CommentsDeleter.
//TODO check if removal of an object deeper than the immediate child node level is removed. Otherwise this will not work!
//*/
	public List<Object> getParentContent(Child child) throws Docx4JException {
        
		
		Child parent;
		//Class<?> parentClass = null;
		/*in TraversalUtil the traversed child nodes already get the correct parent node set
		 *as JAXB-Implementation seems to not always give correct results. */
		//this skips the first parent because if the 
		//while (child.equals(parent) && !(parent instanceof Body)) {
		do {	
			parent = (Child)child.getParent();
			//parentClass = parent.getClass();  //needed information
			
			if (parent instanceof ContentAccessor) {
				return ((ContentAccessor)parent).getContent();
			}
			//this overwrites child node reference with parent's address, loosing child node
			//and leaving us with finally deleting the child's parent if we remove child from the 
			//content of the parent because the parent then is the parent of the parent and the child the parent.
			child = parent;//so that the overwriting of child has no meaning IF AND ONLY IF we really delete it.
			//The overwriting is no problem as the node has never been removed from the XML/DOM 
			//and therefore will not result in any difference if it is decided not to remove the node(s). 
			/*
			try {
				// try to find a getContent method if available, else continue up towards root
				parentClass.getDeclaredMethod("getContent");
			}
			catch (NoSuchMethodException e) {
				// results in climbing one level higher towards the root:
				child = parent; //because if child will be replaced by the parent, 
			}
			*/
		} while (child == parent && !(parent instanceof Body));
		/*
		List<Object> contentOfParent;
		if (!child.equals(parent)) {
			contentOfParent = ((ContentAccessor)parent).getContent();
		}
		if (!(contentOfParent instanceof List<?>)) {
			return null;
		}
		return contentOfParent;
		 */
		return null;
	}
	
	
	
	
	
	
	
	
	//http://stackoverflow.com/questions/5184399/get-xml-nodes-from-certain-tree-level
//	public class TestXPath {
//
//	    private static final String FILE = "a.xml" ;
//	    private static final String XPATH = "/script/findme";
//	    public static void main(String[] args) {
//
//	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	        docFactory.setNamespaceAware(true);
//	        DocumentBuilder builder;
//	        try {
//	            builder = docFactory.newDocumentBuilder();
//	            Document doc = builder.parse(FILE);
//	            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(XPATH);
//	            Object hits = expr.evaluate(doc, XPathConstants.NODESET ) ;
//	            if ( hits instanceof NodeList ) {
//	                NodeList list = (NodeList) hits ;
//	                for (int i = 0; i < list.getLength(); i++ ) {
//	                    System.out.println( list.item(i).getTextContent() );
//	                }
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }
//	}	
	
	
	
}

