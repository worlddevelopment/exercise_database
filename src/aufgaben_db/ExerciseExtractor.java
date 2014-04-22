package aufgaben_db;

//wenn man was testet braucht man die importe manchmal wieder
//daher hab ich sie drin gelassen
/*import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
*/


import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.examples.NewSheet;


/**
 * dient nur zum Testen und zum Aufruf des eigentlichen Programms
 * 
 * desweiteren werden hier die Dokumente uebergeben
 * 
 * @author sabine
 * 
 */

public class ExerciseExtractor {

	
	/**
	 * In String[] args k�nnen soetwas sein wie:
	 * - Pfad zur Datei
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		
//		Program Programm = new Program();
//		// soetwas wie: Programm.erstelleAufgaben(String Pathname);
//		
////		DocType sheetType = null;
////		for(DocType type : DocType.values()) {
////			if ("05-SWT-Uebung.docx".endsWith(type.getCode())) {
////				sheetType = type;
////			}
////		}
////		System.out.println(sheetType);
//		
////		Muster m = Muster.AUFGABE;
////		System.out.println();
////		int[] bla = {0, 3, 5, 7, 11};
////		for (int i = 0; i <= bla.length - 1; i++) {
////			System.out.println(bla[i]);
////		}
//		
//		//Beispiele mit und ohne Schluesselwort:
////		int i = 3;
////		System.out.println(i);
//		ExcerciseSheet newSheet = null;
//		try {
//			newSheet = Programm.run("VK-WS1011-B1.tex");
//		} catch (FileNotFoundException e) {
//			System.out.println("Ungueltiger Dateipfad");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("Fehler beim Lesen oder schreiben");
//			e.printStackTrace();
//		}
//		
//		System.out.println("Es wurden " + newSheet.getExcercises().size() + " Aufgaben gefunden");
//		int k = 1;
//		for (Excercise Ex : newSheet.getExcercises()) {
//			System.out.println("Aufabe Nr " + k);
//			for (int i = 0; i < Ex.getPlainText().length; i++) {
//				System.out.println(Ex.getPlainText()[i]);
//			}
//			k++;
//		}
////		
//		//Programm.erstelleAufgaben("05-SWT-Uebung.doc");//SWT Bsp.docX
//		//Programm.erstelleAufgaben("08-swt-uebungpdf.pdf"); //SWT Bsp.doc
//		//Programm.erstelleAufgaben("03-SWT-Uebung.pdf"); //SWT SS 09
//		//Programm.erstelleAufgaben("uebung_2.rtf"); //Datenbanken 1
//		//Programm.erstelleAufgaben("uebung10-lsg.pdf"); //OOP mit Lsg SS 09
//		//Programm.erstelleAufgaben("MfInf_III_UeB_14.pdf"); //MatheIII
//		//Programm.erstelleAufgaben("ws09--blatt2.rtf"); // Informationsuebertragung WS 0910
//		//Programm.erstelleAufgaben("u13.pdf"); //REA SS10 <<<<<<< .mine
//		//Programm.erstelleAufgaben("Blatt14.pdf"); //MatheII, EZTMueller, Einf.Alg:Mueller =======
//		//Programm.erstelleAufgaben("ueb2.pdf"); //MatheII, EZTMueller, Einf.Alg:Mueller >>>>>>> .r15
//		//Programm.erstelleAufgaben("ProGeo_UeB_11.pdf"); //Projektive Geometrie Rosehr
//		//Programm.erstelleAufgaben("blatt12.pdf"); // Algor. Graphentheorie Wolff SS11;
//		//Programm.erstelleAufgaben("praesenz1.pdf"); //Logik Wagner Praesenzaufgaben
//		//Programm.erstelleAufgaben("ueb2.pdf"); //Lina II SS10 Steuding
//		//Programm.erstelleAufgaben("b01.pdf"); //Rechnernetze, Tran-Gia WS 1011
//		//Programm.erstelleAufgaben("TheoInf-Hausaufgabenblatt06.pdf"); //Theo-Info, Glasser SS 11
//		//Programm.erstelleAufgaben("UEbung_6.pdf"); //Messtechnik, Rohmer
//		//Programm.erstelleAufgaben("Ü12 Angabe (Probeklausur).pdf"); //Betriebssysteme Albert WS 1011
//		//Programm.erstelleAufgaben("ADS09_uebung12.pdf"); //AlgoDat, Wolff, WS 1011
//		//Programm.erstelleAufgaben("06-SWT-Uebung.rtf");//SWT Bsp.rtf
//		
//		//Programm.erstelleAufgaben("Uebung_8.pdf"); //DB
//		
//	}
}
