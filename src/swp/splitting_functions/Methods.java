package swp.splitting_functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


import aufgaben_db.Aufgaben_DB;


/**
 * Diese Klasse enthaelt zus�tztliche Funktionen fuer Zersplittung der Dateien
 * 
 * @author Artiom Kichojal
 * 
 */
public class Methods {
	
	/**Quelpfad vo */
	private String source_path;
	private String target_path;
	

	/**
	 * Listet alle Dateien in angegebenen Ordner und zersplittet jede Datei
	 * @param source
	 *            Quellpfad
	 * @param target
	 *            Zielpfad
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void split_all(String source, String target)
			throws SQLException, FileNotFoundException, IOException {
		String[] entries = new File(source).list();

		for (int i = 0; i < entries.length; i++) {
			Aufgaben_DB.processUploadedSheetdraft(source + "/" + entries[i]);
		}

	}
	public static void pr() {
		System.out.println("<p>Hallo</p>");
	}
	
}
