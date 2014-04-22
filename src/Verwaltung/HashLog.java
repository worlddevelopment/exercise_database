package Verwaltung;

import java.util.HashMap;

/**
 * 
 * @author sabine
 * 
 * Klasse HashLog dient zum Erstellen des LogFiles
 *
 */



public class HashLog {
	
	//hashLog verwaltet das LogFile
	public static HashMap<Integer, String> hashLog = new HashMap<Integer, String>();
	private static int key = 0;
	
	
	public static void initialisiereLogFile() {
		hashLog.clear();
		key = 0;
	}
	
	
	public static void erweitereLogFile(String text) {

		hashLog.put(key, text);
		key++;
		//System.out.println("Logfile erweitert, key steht nun bei: " + key);
	}
	
	public static String[] erstelleLogFile() {
		
		String[] ergebnis = new String[key];
		
		for (int i = 0; i < key; i++) {
			ergebnis[i] = hashLog.get(i);
		}
		return ergebnis;
	}

}
