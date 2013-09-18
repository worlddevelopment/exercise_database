package swp;
import aufgaben_db.Global;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


import org.docx4j.convert.out.pdf.*;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class Test {
	public void getMyString() throws Exception {
		WordprocessingMLPackage wordMLPackage =  
				WordprocessingMLPackage.load(new java.io.File("tut6_loes.docx"));
		//wordMLPackage.setFontMapper(  new  IdentityPlusMapper());
		PdfConversion c  = new  org.docx4j.convert.out.pdf.viaXSLFO.Conversion(wordMLPackage);
			
		OutputStream os =   new   java.io.FileOutputStream("tut6_loes.docx" +  ".pdf"  ); 
		c.output(os, null);
		System.out.println("hallo");
		
	}
	public static void main(String[] args) throws IOException {
//		try {
//			new Test().getMyString();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		new File("/home/artjom/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/aufgaben_db/uploads/sose4/mathe3/dozent/Uebung").mkdirs();
//		new File("bla/foo.txt").createNewFile();
//		Global.moveDir("/home/artjom/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/aufgaben_db/uploads/sose3/mathe3/dozent/Uebung", "/home/artjom/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/aufgaben_db/uploads/sose4/mathe3/dozent/Uebung", "");
		
		Global.renameFile("bla6", "bla7");
		
	}
	
}
