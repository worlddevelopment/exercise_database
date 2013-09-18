import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;

import org.apache.jasper.runtime.JspWriterImpl;

import HauptProgramm.*;
public class Test {
	

	public void split_all(String source, String target,String root){
		//Aufgaben_DB myProgram = new Aufgaben_DB();
		String[] entries = new File(source).list();

			for (int i = 0; i < entries.length; i++) {
				//myProgram.run(source + "/" + entries[i],target);
			}

	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		BufferedWriter bw = new 
				BufferedWriter (new FileWriter("D:\\something\\text.txt"));
		bw.write("shit");
		bw.close();

		
//		Test t = new Test();
//		t.split_all(
//				"C:/xampp/htdocs/SWPAufgabenExtraktionSS11OO/uebung_geometrie_rs_ws1112",
//				"D:/gesplittete_dateien_v2/uebung_geometrie_rs_ws1112");
//		
//		t.split_all(
//				"C:/xampp/htdocs/SWPAufgabenExtraktionSS11OO/Uebungen_arithmetik",
//				"D:/gesplittete_dateien_v2/Uebungen_arithmetik");
//		




		//
//		Program myProgram = new Program();
//		ExcerciseSheet ex = myProgram.run("C:/Users/artjom84/Desktop/uebung_geometrie_rs_ws1112/07_ueb_ws11_geo_HS_RS_v2.docx","C:/Users/artjom84/Desktop/gespl");
//		String [] docx = ex.getPlainText();
//		System.out.println("docx: ");
//		for (int i = 0; i < docx.length; i++) {
//			
//			System.out.println(docx[i]);
//		}
//		
//		ExcerciseSheet ex1 = myProgram.run("C:/Users/artjom84/Desktop/uebung_geometrie_rs_ws1112/07_ueb_ws11_geo_HS_RS_v2.pdf","C:/Users/artjom84/Desktop/gespl");
//		String [] pdf = ex1.getPlainText();
//		System.out.println("pdf: ");
//		for (int i = 0; i < pdf.length; i++) {
//			
//			System.out.println(pdf[i]);
//		}
//		
//		ExcerciseSheet ex2 = myProgram.run("C:/Users/artjom84/Desktop/uebung_geometrie_rs_ws1112/07_ueb_ws11_geo_HS_RS_v2.pdf","C:/Users/artjom84/Desktop/gespl");
//		String [] rtf = ex2.getPlainText();
//		System.out.println("rtf: ");
//		for (int i = 0; i < rtf.length; i++) {
//			
//			System.out.println(rtf[i]);
//		}
		
	}
	
}
