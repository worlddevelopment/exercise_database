package docx4j_library;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.TextUtils;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.TraversalUtil.CallbackImpl;
import org.docx4j.finders.ClassFinder;
import org.docx4j.finders.SectPrFinder;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.CTFFCheckBox;
import org.docx4j.wml.FldChar;

public class TraverseFind {

	
	/**
	 * Example of how to find an object in document.xml
	 * via traversal (as opposed to XPath)
	 *  
	 */
	public static void main(String[] args) throws Exception {

		String inputfilepath = System.getProperty("user.dir") + "/checkbox.docx";
				
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(inputfilepath));		
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
				
		ClassFinder finderClass = new ClassFinder(FldChar.class); // <----- change this to suit
		SectPrFinder finder = new SectPrFinder(documentPart);
		new TraversalUtil(documentPart.getContent(), finder);
		
		//System.out.println("got " + finder.results.size() );
		System.out.println("got " + finder.getSectPrList().size() );
		
		for (Object o : finder.getSectPrList()) {
			
			TextUtils.extractText(o, w);
			Object o2 = XmlUtils.unwrap(o);
			// this is ok, provided the results of the Callback
			// won't be marshalled			
			
			if (o2 instanceof org.docx4j.wml.Text) {
				
				org.docx4j.wml.Text txt = (org.docx4j.wml.Text)o2;
				
				System.out.println( txt.getValue() );
				
			} else {
				System.out.println( XmlUtils.marshaltoString(o, true, true));
			}

			
			
		}
								
	}
	
		
}
