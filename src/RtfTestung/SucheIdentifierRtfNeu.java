package RtfTestung;

import java.io.IOException;

import aufgaben_db.*;
import Verwaltung.*;



//TODO Sabine: Teilweise noch Teile aus Methoden auslagern und zusammenfassen!


public class SucheIdentifierRtfNeu {
	
	
	// hier wird eingetragen, ob alles richtig erkannt wurde:
	public static boolean erstesWortfertig = false;
	public static boolean letztesWortfertig = false;
	public static boolean aufgabenfertig = false;

	
	
	public static boolean sucheErstesWortImRtf() {

		//Das erste Wort des Dokuments wird im Rtf-File gesucht
		
		String[] rtfdoc = HashMapVerwaltung.getRtfFile();
		String[] textdoc = HashMapVerwaltung.getTextFile();
		
		String erstesWort = HashMapVerwaltung.getDocAnfAusHashmap();
		
		int[] zeilenW1 = MethodenRtf.sucheZeileAlle(rtfdoc, erstesWort);
		System.out.println("Das erste Wort des Dokuments lautet: " + erstesWort);
		System.out.println("Das Wort taucht " + zeilenW1.length + " Mal auf.");
			
		if(zeilenW1.length == 0) {
			String text = "Der im Textdokument gefundene Identifier fuer DocAnfang: " + erstesWort +
			" konnte im Rtf-Dokument nicht aufgefunden werden. Daher ist eine Entfernung des Kopfes nicht moeglich";
			System.out.println(text);
			HashLog.erweitereLogFile(text);
			
			return false;
			// "erstesWortfertig" bleibt auf false
		}
		
		else {
		
			if(zeilenW1.length > 1) {
			
				//Hier gibt es drei Moeglichkeiten:
				
				int anzahlImText = MethodenRtf.wortAnzahlimText(textdoc, erstesWort);
				if (zeilenW1.length == anzahlImText) {
					System.out.println("Schon im Textdokument befand sich das Wort insgesamt " + anzahlImText
						+ "Mal, also alles ok.");
					//Da es sich um DocAnfang handelt, wird das erstes Auftreten im rtf gewaehlt.
					//Hier wird nichts im LogFile vermerkt, da ja wohl alles seine Ordnung hat.
				}
				
				if (zeilenW1.length < anzahlImText) {
					String text = "Der im Textdokument fuer DocAnfang gefundene Identifier ist im " +
					"Rtf-Dokument mehrfach vorhanden, jedoch seltener als im Originaldokument. " +
					"Irgendwo muss im Rtf ein Wort auseinandergerissen worden sein. Es wird das erste " + 
					"Auftreten des Wortes gewaehlt, im unguenstigen Fall kann es jedoch sein, dass " +
					"die Wahl falsch ist und dadurch weitere Fehler auftreten";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
				}
				
				if (zeilenW1.length > anzahlImText) {
					String text = "Der im Textdokument fuer DocAnfang gefundene Identifier ist im " +
					"Rtf-Dokument mehrfach vorhanden, und zwar haeufiger als im Originaldokument. " +
					"Irgendwo muss im Rtf dieses Wort noch in einem anderen Kontext vorkommen. Es wird das erste " + 
					"Auftreten des Wortes gewaehlt, im unguenstigen Fall kann es jedoch sein, dass " +
					"die Wahl falsch ist und dadurch weitere Fehler auftreten";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
				}
			}
			
			
			if (zeilenW1.length == 1) {
				//sowieso alles bestens
				//dieses if braucht man natuerlich nicht, es steht hier nur, damit die
				//Fallunterscheidung vollstaendig ist
			}
			
			// in jedem Fall wird in all diesen Faellen jetzt das erste Auftreten des Wortes abgespeichert:
			
			int zeileErstesWort = zeilenW1[0];
			int positionErstesWort = MethodenRtf.suchePosition(rtfdoc[zeileErstesWort], erstesWort);
			
			
			// Problematik: falls das erste Wort ein Sonderzeichen enthaelt, wird es tw. schon im
			// .txt-Dokument, spaetenstens aber im rtf-Dokument veraendert und meist auseinandergerissen
			// Dem wurde dadurch begegnet, dass Umlaute aus Woertern vor der Suche entfernt wurden.
			// Fuer die .txt-Dokumente reichte dieser Ansatz komplett, da dort die ganze Zeile geloescht wurde
			// Im rtf-Dokumment stehen die gesuchten Identifier nicht am Anfang einer Zeile, daher kann erst ab dem
			// gesuchten Teilwort geloescht werden. In Faellen, in denen das Sonderzeichen/der Umlaut zu Beginn des Wortes
			// auftaucht, wird dies problematisch: Die Anfangsbuchstaben des Wortes bleiben beim Kopf und werden vor jede 
			// Aufgabe vorangestellt.
			
			// Hier Einzelfallloesung fuer den Fall dass das erste Wort mit "Übung..."beginnt:
			
			// Da bisher in den Uebungsblaettern keine anderen Worte am Anfang
			// auftraten und alles, was den Umlaut in der Mitte hat, sehr problematisch ist
			// wird hier nur dieser Einzelfall behandelt:
						
			// erstmal bruteforce-Test: Gesucht wird nach großen UE am Anfang, 
			// falls das erste gefundene Wort mit bung, bungen, bungsblatt, ...etc. beginnt.
			
			boolean erstesWortUmlautUe = erstesWort.startsWith("bung");
			int umlautPosition = positionErstesWort;
			
			if (erstesWortUmlautUe) {
				// 'dc steht im rtf-Format fuer "Ü"
				umlautPosition = rtfdoc[zeileErstesWort].indexOf("'dc");
				//-1 als Ergebnis bedeutet, dass der gesuchte Teilstring sich nicht in der Zeile befindet
				if (umlautPosition == -1 || umlautPosition > positionErstesWort) {
					String text = "Aufgrund des Kontextes wird vermutet, dass das Dokument mit einem Umlaut" +
						"beginnt, dieser konnte aber im Rtf-Dokument nicht identifiziert werden, daher kann es sein, "+
						"dass dieser nicht mit ausgeschnitten wird";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
					erstesWortUmlautUe = false;
				}
			}
			// falls der Umlaut in der gleichen Zeile vor dem ersten Wort im Rtf existiert
			// wird bereits alles ab dem Umlaut rausgeschmissen: 
			if (erstesWortUmlautUe) {
				positionErstesWort = umlautPosition;
//			//positionErstesWort = MethodenRtf.suchePosition(rtfdoc[zeileErstesWort], "'dc");
			}
			
			System.out.println("Die Zeile lautet:");
			System.out.println(rtfdoc[zeileErstesWort]);
			System.out.println("Das Wort befindet sich in Zeile: " + zeileErstesWort +
					" an Position " + positionErstesWort);
		
			HashMapVerwaltung.erweitereHashmapRtf(HashMapVerwaltung.docAnf, erstesWort, zeileErstesWort, positionErstesWort);
			erstesWortfertig = true;
			return true;
		}
	}
	
	
	public static boolean sucheLetztesWortImRtf() {

		//Das letzte Wort des Dokuments wird im Rtf-File gesucht
		
		String[] rtfdoc = HashMapVerwaltung.getRtfFile();
		String[] textdoc = HashMapVerwaltung.getTextFile();
		
		String letztesWort = HashMapVerwaltung.getDocEndeAusHashmap();
		int[] zeilenW2 = MethodenRtf.sucheZeileAlle(rtfdoc, letztesWort);
		
		System.out.println("Das letzte Wort des Dokuments lautet: " + letztesWort);
		System.out.println("Das Wort taucht " + zeilenW2.length + " Mal auf.");
		
		if(zeilenW2.length == 0) {
			String text = "Der im Textdokument gefundene Identifier fuer DocEnde: " + letztesWort +
			" konnte im Rtf-Dokument nicht aufgefunden werden. Daher ist eine Entfernung der letzten Aufgabe nicht moeglich";
			System.out.println(text);
			HashLog.erweitereLogFile(text);
			
			// "letztesWortfertig" bleibt auf false
			return false;
		}
		
		else {
			
			if(zeilenW2.length > 1) {
			
				//Hier gibt es wieder drei Moeglichkeiten:
				
				int anzahlImText = MethodenRtf.wortAnzahlimText(textdoc, letztesWort);
				if (zeilenW2.length == anzahlImText) {
					System.out.println("Schon im Textdokument befand sich das Wort insgesamt " + anzahlImText
						+ "Mal, also alles ok.");
					//Da es sich um DocEnde handelt, wird das letzte Auftreten im rtf gewaehlt.
					//Hier wird nichts im LogFile vermerkt, da ja wohl alles seine Ordnung hat.
				}
				
				if (zeilenW2.length < anzahlImText) {
					String text = "Der im Textdokument fuer DocEnde gefundene Identifier ist im " +
					"Rtf-Dokument mehrfach vorhanden, jedoch seltener als im Originaldokument. " +
					"Irgendwo muss im Rtf ein Wort auseinandergerissen worden sein. Es wird das letzte " + 
					"Auftreten des Wortes gewaehlt, im unguenstigen Fall kann es jedoch sein, dass " +
					"die Wahl falsch ist und dadurch weitere Fehler auftreten";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
				}
				
				if (zeilenW2.length > anzahlImText) {
					String text = "Der im Textdokument fuer DocEnde gefundene Identifier ist im " +
					"Rtf-Dokument mehrfach vorhanden, und zwar haeufiger als im Originaldokument. " +
					"Irgendwo muss im Rtf dieses Wort noch in einem anderen Kontext vorkommen. Es wird das letzte " + 
					"Auftreten des Wortes gewaehlt, im unguenstigen Fall kann es jedoch sein, dass " +
					"die Wahl falsch ist und dadurch weitere Fehler auftreten";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
				}
			}

			if (zeilenW2.length == 1) {
				//sowieso alles bestens, siehe oben
			}
			
			// in jedem Fall wird jetzt das letzte Auftreten des Wortes abgespeichert:
			
			int zeileLetztesWort = zeilenW2[zeilenW2.length-1];
			int positionLetztesWort = MethodenRtf.suchePosition(rtfdoc[(zeileLetztesWort)], letztesWort);
			System.out.println("Die Zeile lautet:");
			System.out.println(rtfdoc[zeileLetztesWort]);
			System.out.println("Das Wort befindet sich in Zeile: " + zeileLetztesWort +
					" an Position " + positionLetztesWort);
			//beim letzten Wort ist die Position des Wortendes wichtig, also
			//Zeiger auf letzten Buchstaben des Wortes:
			positionLetztesWort = positionLetztesWort + letztesWort.length()-1;
			HashMapVerwaltung.erweitereHashmapRtf(HashMapVerwaltung.docEnde, letztesWort, zeileLetztesWort, positionLetztesWort);
			letztesWortfertig = true;
			return true;
		}

	}
		
	
	
	public static int sucheAufgabenDeklarationenMitSWImRtf(int anzahl) {

		System.out.println("sucheAufgabenDeklarationenMitSWImRtf() wurde aufgerufen");
		
		// Es kann der Fall eintreten, dass einzelne Aufgaben gefunden werden,
		// andere jedoch nicht.. Der Zaehler zaehlt, wieviele Aufgaben richtig erkannt wurden
		int zaehler = 0;		
		
		
		String wort1 = "";
		String wort2 = "";
		
		String[] rtfdoc = HashMapVerwaltung.getRtfFile();
		
		// Da das erste Wort bei allen Aufgabendeklarationen gleich ist (= das Schluesselwort)
		// kann man dieses hier direkt auslesen:
		// Da bereits vorher getestet wurde, dass Anzahl nicht 0 ist, 
		// muss zumindest der key "Aufgabe1" existieren:
		String decl = HashMapVerwaltung.getSchluesselWortAusHashmap("Aufgabe1");
		String text = "Die gewaehlte Aufgabendeklaration, nach der gesucht wird, lautet: " + decl; 
		System.out.println(text);
		HashLog.erweitereLogFile(text);
		
		// Falls sich genau "anzahl"-viele deklarierte Aufgaben
		// im Rtf- Dokument befinden kann man sich das zweite Wort sparen, also Test:
		// der Fall anzahl = 0 kann nicht auftreten, da nur eine Zahl >= 1 uebergeben wurde
		
		int[] zeilenListe = MethodenRtf.sucheZeileAlle(rtfdoc, decl);
		System.out.println("Das Wort taucht " + zeilenListe.length + " Mal auf.");
		
		if (zeilenListe.length == 0) {
			text = "Das Wort " + decl + "kann im Rtf-Dokument nicht gefunden werden. " +
				"Es wird noch versucht, eine Unterteilung nach dem 2. Teil der Deklaration " +
				"durchzufuehren, aber das Wort " + decl + " wird wohl in jedem Fall falsch abgeschnitten.";
			System.out.println(text);
			HashLog.erweitereLogFile(text);
			// die Suche nach "DeklarationMitSW" wird abgebrochen,
			// dafuer dann noch die Suche nach "DeklarationenOhneSW" aufgerufen
			// Kennzeichen: Returnwert gleich 0;
			return 0;
		}
		
		
		if (zeilenListe.length == anzahl) {
			
			// Hier vorsichtshalber noch ein Test, ob alle Zeilen verschieden sind,
			// falls nicht, koennten eventuell Probleme auftreten....:
			
			boolean zeilenVerschieden = testeZeilenAngaben(zeilenListe);	

			if(zeilenVerschieden) {
				for (int i = 0; i < anzahl; i++) {
					int zeile = zeilenListe[i];
					int positionAufgabe = MethodenRtf.suchePosition(rtfdoc[zeile], decl);
					System.out.println("Die Zeile lautet: ");
					System.out.println(rtfdoc[zeile]);
					System.out.println("Das Wort befindet sich in Zeile: " + zeile +
						" an Position " + positionAufgabe);
					// kann man sich eigentlich schenken, da immer gleich:
					wort1 = HashMapVerwaltung.getSchluesselWortAusHashmap(decl+(i+1));	
					HashMapVerwaltung.erweitereHashmapRtf(decl+(i+1), wort1, zeile, positionAufgabe);
				}
				System.out.println("Die Aufgabendeklarationen wurden in die HashMapRtf geschrieben");
				aufgabenfertig = true;
				// In diesem Fall konnten Positionen und Zeilen fuer alle Aufgaben eingetragen werden
				return anzahl;
			}
			else {
				// Bisher ist dieser Fall noch nie aufgetreten; falls er spaeter doch Probleme bereiten sollte,
				// wird er hier entdeckt und in das LogFile geschrieben; hier koennte dann
				// noch eine erweiterte Methode zum korrekten Finden der Position rein.
				text = "Leider befinden sich mehrere Aufgabendeklarationen in der gleichen" +
					" Zeile des Rtfs. Daher koennen diese ueber das Schluesselwort nicht zugeordnet werden." +
					" Es wird noch versucht, eine Unterteilung nach dem 2. Teil der Deklaration " +
				"durchzufuehren, aber das Wort " + decl + " wird dadurch falsch abgeschnitten.";
				System.out.println(text);
				HashLog.erweitereLogFile(text);
				return 0;
			}
				
			
		}
		else {
		
			//Moeglichkeit 1:
			//Es waren vorher zuviele Aufgaben und eine wurde rausgeschmissen
			//Dann waere es praktisch zu wissen, welche:  
			
			//(falls noch implementiert werden sollte, dass mehrere Aufgaben rausgeschmissen werden koennen
			// dann muesste dies hier auch beruecksichtigt werden)
			
			//Annahme im Moment: Wenn, dann wurde genau eine Aufgabe entfernt
			//(so wie momentan implementiert):
			
			boolean zuVieleAufgaben = HashMapVerwaltung.getAufgabeZuviel();
			
			if ((zeilenListe.length == (anzahl+1)) && zuVieleAufgaben) {
				System.out.println("Eine Aufgabe wurde zuviel gefunden," +
						"und eine vorher rausgeworfen - das muss stimmen.");
				//dies wird nicht ins LogFile geschrieben, denn das ist fuer den Anwender unwichtig
				
				// also koennen alle Aufgaben bis auf die gestrichenen eingetragen werden:
				int gestricheneAufgabe = HashMapVerwaltung.getGestricheneAufgabeAusHashmap();
				
				int index = 0;
				for (int i = 0; i < anzahl+1; i++) {
					
					if (i != (gestricheneAufgabe-1)) {
						int zeile = zeilenListe[index];
						int positionAufgabe = MethodenRtf.suchePosition(rtfdoc[zeile], decl);
						System.out.println("Die Zeile lautet: ");
						System.out.println(rtfdoc[zeile]);
						System.out.println("Das Wort befindet sich in Zeile: " + zeile +
							" an Position " + positionAufgabe);
						
						wort1 = HashMapVerwaltung.getSchluesselWortAusHashmap(decl+(index+1));
						// Dies waere das Gleiche, da wort1 der Deklaration entspricht....:
						// wort1 = decl;
						HashMapVerwaltung.erweitereHashmapRtf(decl+(index+1), wort1, zeile, positionAufgabe);
						index++;
						
					}
				}
//				// vielleicht ueberfluessig:
				aufgabenfertig = true;
				// anzahl hat sich ja nicht veraendert, also:
				return anzahl;
			}
			
			
			// Falls das nicht der Grund gewesen sein sollte, muss
			// jetzt nach den 2. Woertern der Deklaration gesucht werden.	
			
			// Zuerst wird der Sonderfall abgerueft, dass beide Worte im Rtf direkt
			// hintereinanderstehen, in diesem Fall gibt es auch keine Probleme, allerdings
			// muss dies dann wirklich fuer alle Deklarationen gelten:
			
			
			else {
				
				System.out.println ("Das erste Wort hat nicht gereicht, jetzt kommt das zweite hinzu");
				
				// aufgabenfertig wird auf true gesetzt
				// weil im Folgenden aufgabenfertig auf false gesetzt wird, sobald etwas nicht passt
				aufgabenfertig = true;
				// der Zaehler wird auf 0 gesetzt
				zaehler = 0;
				
				// Alle Aufgaben werden durchlaufen:
				for (int i = 0; i < anzahl; i++) {
					String aufgabe = decl + (i+1);
					wort1 = HashMapVerwaltung.getSchluesselWortAusHashmap(aufgabe);
					wort2 = HashMapVerwaltung.getAufgabenBezeichnungAusHashmap(aufgabe);
					String beideWoerter = wort1 + " " + wort2;
					System.out.println("gesuchte Deklaration: " + beideWoerter);
					int[] zeilen = MethodenRtf.sucheZeileAlle(rtfdoc, beideWoerter);
					//wieder 3 Moeglichkeiten, diesmal fuer jede einzelne Aufgabendeklaration:
					if (zeilen.length == 1) {
						int zeile = zeilen[0];
						System.out.println("Alles passt bei Aufgabe " + i+1);
						int positionAufgabe = MethodenRtf.suchePosition(rtfdoc[zeile], beideWoerter);
						System.out.println("Die Zeile lautet: ");
						System.out.println(rtfdoc[zeilen[0]]);
						System.out.println("Das Wort befindet sich in Zeile: " + zeile +
								" an Position " + positionAufgabe);
						HashMapVerwaltung.erweitereHashmapRtf(decl+(i+1), beideWoerter, zeile, positionAufgabe);
						// in diesem Spezialfall geht alles noch glatt, es erfolgt auch kein Eintrag ins Logfile
						// der Zaehler zaehlt hoch
						zaehler++;
					}
					
					if (zeilen.length > 1) {
						System.out.println("Die Aufgabendeklaration " + beideWoerter +
								" wurde im Rtf-Dokument mehrfach gefunden");
						
						aufgabenfertig = false;	
					}
					
					if (zeilen.length == 0) {
						System.out.println("Die beiden Woerter tauchen im Rtf nicht gemeinsam auf.");
					
						aufgabenfertig = false;
					}
				}
				
				// falls eine einzige Aufgabe nicht korrekt ist, wird aufgabenfertig als false markiert
				// da ja nicht alle Aufgaben erkannt wurden.
				// Dennoch wird weitergesucht, je mehr Aufgaben gefunden werden, desto besser
				// (da man nicht davon ausgehen kann, dass man ueber die Nummerierung mehr Erfolg hat.
			}
			if (aufgabenfertig) {
				// falls vorher nicht abgebrochen wurde, muessen alle Aufgaben richtig durchlaufen worden sein:
				return anzahl;
			}
			else {
				text = "Es konnten " + zaehler + " von " + anzahl + " Aufgabendeklarationen im rtf-Dokument gefunden werden." +
					" Nach dem Rest wird noch ueber die Nummerierung alleine gesucht. Es ist aber davon auszugehen, dass zumindest" +
					" Teile der Aufgabenbloecke falsch ausgeschnitten werden.";
				System.out.println(text);
				HashLog.erweitereLogFile(text);
				return zaehler;
			}
		}
	}	
	
	
	
		
	public static int sucheAufgabenDeklarationenOhneSWImRtf(int anzahl) {
		
		
		// jetzt steht kein Schluesselwort mehr voran
		// und die Aufgabendeklarationen koennen direkt gesucht werden
		
		String aufgabenBezeichnung = "";
		
		System.out.println("sucheAufgabenDeklarationenOhneSWImRtf() wurde aufgerufen");
		
		String[] rtfdoc = HashMapVerwaltung.getRtfFile();
		String[] textdoc = HashMapVerwaltung.getTextFile();
		
		// die Anzahl der gefundenen Aufgaben (fuer die Rueckgabe)
		// wird initialisiert
		
		int gefunden = 0;
		boolean dieseAufgabe = false;
		aufgabenfertig = true;
		
		System.out.println ("Es handelt sich um direkte Aufgabendeklarationen.");
		
		loop1: for (int i = 0; i < anzahl; i++) {
			
			String aufgabe = "Aufgabe" + (i+1);
			
			aufgabenBezeichnung = HashMapVerwaltung.getAufgabenBezeichnungAusHashmap(aufgabe);
		
			// falls die Aufgabe bereits in "sucheAufgabenMitSW" gefunden worden sein sollte
			// (moeglich, falls dort nicht alle Aufgaben gefunden wurden)
			// dann wird erst getestet, ob die Aufgabe bereits eingetragen ist
			// dann braucht sie nicht nochmals gesucht zu werden:
			boolean vorhanden = HashMapVerwaltung.hashMapRtf.containsKey(aufgabe);
			if (vorhanden) {
				dieseAufgabe = true;
				gefunden++;
				System.out.println("Break bei " + aufgabe);
				continue loop1;
			}

			System.out.println("gesuchte Deklaration: " + aufgabenBezeichnung);
			int[] zeilen = MethodenRtf.sucheZeileAlle(rtfdoc, aufgabenBezeichnung);
			
			//wieder 3 Moeglichkeiten fuer jede einzelne Aufgabendeklaration:
			
			if (zeilen.length == 0) {
				String text = "Die Aufgabendeklaration: " + aufgabenBezeichnung + " kann im Rtf nicht gefunden werden.";
				System.out.println(text);
				HashLog.erweitereLogFile(text);
				dieseAufgabe = false;
			}
			
			if (zeilen.length == 1) {
				System.out.println("Alles passt bei  Deklaration: " + aufgabenBezeichnung);
				dieseAufgabe = true;
			}
			
			if (zeilen.length > 1) {
				
				String text = "Ein Problem ist aufgetreten: Die Deklaration " + aufgabenBezeichnung + " wurde im rtf-Dokument mehr als einmal gefunden.";
				System.out.println(text);
				HashLog.erweitereLogFile(text);
				
				// Jetzt erfolgt noch der Vergleich mit der urspruenglichen Anzahl im Dokument
				// Dies wird zwar nicht direkt benoetigt, ist jedoch ev. hilfreich fuer eine
				// spaetere Weiterbearbeitung des Quellcodes und beschreibt die Fehlerquelle im LogFile.
				
				int anzahlImText = MethodenRtf.wortAnzahlimText(textdoc, aufgabenBezeichnung);
				
				if (zeilen.length == anzahlImText) {
					text = "Schon im Textdokument befand sich die Deklaration: " + aufgabenBezeichnung + "insgesamt " + anzahlImText
						+ " Mal, also ist dies an sich erklaerbar, es wird aber nicht einfach, diese richtig zuzuordnen, " +
						" dabei koennen Fehler auftreten.";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
				}
				
				//folgender Fall waere noch denkbar:
				if (zeilen.length < anzahlImText) {
					text = "Die Deklaration: " + aufgabenBezeichnung + " ist im " +
					"Rtf-Dokument mehrfach vorhanden, jedoch seltener als im Originaldokument. " +
					"Irgendwo muss im Rtf ein Wort auseinandergerissen worden sein. Es wird willkuerlich das erste " + 
					"Auftreten des Wortes gewaehlt, da sonst gar keine Bearbeitung moeglich waere. Es kann jedoch sein, dass " +
					"die Wahl falsch ist und dadurch weitere Fehler auftreten";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
				}
				
				// fast nicht denkbar:
				if (zeilen.length > anzahlImText) {
					text = "Die Deklaration: " + aufgabenBezeichnung + " ist im " +
					"Rtf-Dokument mehrfach vorhanden, und zwar haeufiger als im Originaldokument. " +
					"Irgendwo muss im Rtf dieses Wort noch in einem anderen Kontext vorkommen. Es willkuerlich das erste " + 
					"Auftreten des Wortes gewaehlt, da sonst gar keine Bearbeitung moeglich waere. Es kann jedoch sein, dass " +
					"die Wahl falsch ist und dadurch weitere Fehler auftreten";
					System.out.println(text);
					HashLog.erweitereLogFile(text);					
				}
				
				//Egal welcher Grund:
				text = "Es wird versucht, die am besten passende Deklaration herauszufinden, dies wird" +
				" jedoch nicht immer zum richtigen Ergebnis fuehren. Es kann sein, dass ein oder mehrere Aufgaben falsch" +
				" oder nicht ausgeschnitten werden koennen.";
				System.out.println(text);
				HashLog.erweitereLogFile(text);	
				
				// falls es sich um die erste Aufgabe handelt, wird einfach das erste Auftreten der Deklaration eingetragen:
				if (i == 0) {
					System.out.println("Es handelt sich um die erste Aufgabe, daher wird nichts veraendert.");
					dieseAufgabe = true;
				}
				
				// da die Zeilenangabe der folgenden Aufgabe noch nicht feststeht, bleibt
				// nur, die naechstmoegliche Zeile nach der Zeile der vorherigen Aufgabe auszuwaehlen:
				if (i > 0) {
					// nicht verwirren lassen: i bezeichnet ja die i-1. Aufgabe, also:
					int zeileAufgabezuvor = HashMapVerwaltung.getZeileAusHashmapRtf("Aufgabe"+i);
					int j = 0;
					while (j < zeilen.length) {
						if (zeilen[j] <= zeileAufgabezuvor) {
							j++;
							dieseAufgabe = false;
						}
						else {
							dieseAufgabe = true;
							// trage den Wert an der ersten Stelle des Array ein
							// (dort wird er dann im folgenden abgegriffen:
							zeilen[0] = zeilen[j];
							// die while-Schleife wird beendet
							break;
						}
					}	
				}
			}

			//	jetzt werden die Eintragungen fuer diese Aufgabe in die HashMap vorgenommen
			if (dieseAufgabe) {
				
				int zeile = zeilen[0];
				// es wird automatisch das erste Auftreten der Deklaration gewaehlt:
				int positionAufgabe = MethodenRtf.suchePosition(rtfdoc[zeile], aufgabenBezeichnung);
				System.out.println("Die Zeile lautet: ");
				System.out.println(rtfdoc[zeilen[0]]);
				System.out.println("Das Wort befindet sich in Zeile: " + zeile +
						" an Position " + positionAufgabe);
				HashMapVerwaltung.erweitereHashmapRtf("Aufgabe"+(i+1), aufgabenBezeichnung, zeile, positionAufgabe);
				gefunden++;
			}
			// wenn auch nur eine einzige Aufgabe nicht bearbeitet werden konnte:
			else {
				aufgabenfertig = false;
			}
			
		}
		// nur jetzt, nachher weg:
		return gefunden;
	}
	
	
	
//	public static void bearbeiteRtf(String[] textdoc, String[] rtfdoc) {
	public static void bearbeiteRtf(Sheetdraft sheetdraft) throws IOException {
//		String[] textdoc = sheet.getPlainText();
		String[] rtfdoc = sheetdraft.getRawContent();
		
		erstesWortfertig = sucheErstesWortImRtf();
		letztesWortfertig = sucheLetztesWortImRtf();
		
		//Die Aufgabendeklarationen werden gesucht///////////////////////////
		
		int anzahl = HashMapVerwaltung.getAufgabenzahlAusHashmap();
		// diese Anzahl wird erstmal auf fuer das rtf-Dokument eingetragen
		// falls sie spaeter nicht ueberschreiben wird, bleibt sie so stehen
		HashMapVerwaltung.erweitereHashmapInt(HashMapVerwaltung.keyAnzahlRtf, anzahl);
		
		
//		int declaration = HashMapVerwaltung.getDeklarationAusHashmap();
		Muster pattern = sheetdraft.getDeclarationSet().getPattern();
		System.out.println("Anzahl der Aufgaben: " + anzahl);
		
		// Fall 1, Die Aufgabendeklaration besteht aus Schluesselwort + Nummerierung:
		int bereitsAufgabenGefunden = 0;
		if ((pattern.isWordedPattern()) && (anzahl >=1) ) {
			bereitsAufgabenGefunden = sucheAufgabenDeklarationenMitSWImRtf(anzahl);
		}	
		
//		// koennte man anstelle der vielen im Text machen:
		if (bereitsAufgabenGefunden < anzahl) 
			{
				aufgabenfertig = false;
			}
		
		// Fall 2, entweder von Anfang an nur Nummerierung
		// oder aber die Suche nach den Deklaration bei SucheMitSW war zumindest tw. erfolglos:
		int bereitsAufgabenGefunden2 = 0;
		if ((bereitsAufgabenGefunden < anzahl) && (anzahl >=1)) {
			bereitsAufgabenGefunden2 = sucheAufgabenDeklarationenOhneSWImRtf(anzahl);
		}	
		
		System.out.println("Jetzt sind wir hier");
		// hier noch ein Test, ob wenigstens einige der Aufgaben identifiziert werden konnten
		boolean aufgabenteilweisefertig = false;
		int gefunden = 0;
		if (!aufgabenfertig) {
			
			gefunden = Math.max(bereitsAufgabenGefunden, bereitsAufgabenGefunden2); 
			System.out.println("Aufgabenfertig false, gefunden: " + gefunden + ", anzahl:" + anzahl);
			if ((gefunden > 0) && (gefunden < anzahl)) {
				aufgabenteilweisefertig = true;
				System.out.println("Aufgabenteilweisefertig true");
			}
		}
		// so, alles durchgeprueft
		// falls alles gefunden wurde, gehts ans Ausschneiden:

		
		if(erstesWortfertig && letztesWortfertig) {
			
			if (aufgabenfertig) {
				// in diesem Fall waere jetzt alles soweit erledigt, nun
				// gehts ans Ausschneiden:
				String text = "Die Aufgabe(n) werden im Rtf-Format ausgeschnitten";
				System.out.println(text);
				//HashLog.erweitereLogFile(text);
				schneideRtf(rtfdoc);
			}
			else { 
				if (aufgabenteilweisefertig) {
					// in diesem Fall gibt es zwar keine perfekte Loesung, nach Absprache mit Georg
					// sollen aber trotzdem versucht werden, etwas auszuschneiden
					// gehts ans Ausschneiden:
					String text = "Es wurden zwar nicht alle Aufgaben im rtf-Dokument erkannt, " +
					"es wird jetzt trotzdem versucht, zu schneiden. Dabei wird es höchstwahrscheinlich zu Fehlern kommen.";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
					schneideRtfteilweise(rtfdoc, gefunden);
				}
				else {
					String text = "Es konnten keine Identifier im Rtf gefunden werden.";
					System.out.println(text);
					HashLog.erweitereLogFile(text);
					
				}
			}
		}
		else {
			String text = "Das erste und oder letzte Wort des Dokuments konnte im Rtf-Dokument nicht identifiziert werden, damit" +
			"ist es nicht moeglich, das Rtf-Dokument sinnvoll in einzelne Aufgaben zu unterteilen.";
			System.out.println(text);
			HashLog.erweitereLogFile(text);
		}
	}
	
	
	

	
	
	
	/** testet, ob alle Zeilenangaben im Array verschieden und aufsteigend sind
	 * einfachster Test dabei: fuer alle n muss gelten: 
	 * Zeilennummer[n] < Zeilennummer[n+1]
	 */
	public static boolean testeZeilenAngaben(int[] zeilen) {
		
		// bereits wenn der Test einmal fehlschlaegt
		// wird false zurueckgegeben
		for (int i = 0; i < zeilen.length; i++) {
			if (i < zeilen.length-1) {
				if (zeilen[i] >= zeilen[i+1]) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	//testet, ob alle Zeilenangaben der Aufgabendeklarationen monoton aufsteigend sind
	//einfachster Test dabei: fuer alle n muss gelten:
	//Zeilennummer[n] <= Zeilennummer[n+1]
	public static boolean testeZeilenGroesserGleich(int aufgabenzahl) {
	
		int[] zeilen = new int[aufgabenzahl];
		for (int i = 0; i < aufgabenzahl; i++) {
			String decl = "Aufgabe"+(i+1);
			zeilen[i] = HashMapVerwaltung.getZeileAusHashmapRtf(decl);
		}
		// bereits wenn der Test einmal fehlschlaegt
		// wird false zurueckgegeben
		for (int i = 0; i < zeilen.length; i++) {
			if (i < zeilen.length-1) {
				if (zeilen[i] > zeilen[i+1]) {
					return false;
				}
			}
		}
		return true;
	}

	
	
	public static boolean schneideRtf(String[] rtf) throws IOException {
		
		System.out.println("SchneideRtf wurde aufgerufen!)");
		
		// holt endgueltige Aufgabenanzahl
		int aufgabenzahl = HashMapVerwaltung.getAufgabenzahlRtfAusHashmap();
		
/*		
		// zuerst ein allgemeiner Klammertest:
		boolean klammernkorrekt = MethodenRtf.klammernNochKorrekt(rtf);
*/		
		
		// jetzt noch ein allgemeiner Zeilentest fuer die Aufgaben
		// (dies wurde sicherheitsshalber eingebaut, da auch bei Problemen
		// versucht wurde, fuer jede Aufgabe Zeilen und Positionen einzutragen
		
		// bisher ist dieser Fall (aber ev. nur aufgrund der mangelnden Menge an Testfiles) 
		// nicht eingetreten.
/*		boolean alleZeilenok = testeZeilenGroesserGleich(aufgabenzahl);
		if (!alleZeilenok) {
			String text = "Durch die oben geschilderten Probleme haben sich Fehler eingeschlichen." +
					"Es koennen keine Einzelaufgaben im rtf-Format erstellt werden.";
			System.out.println(text);
			HashLog.erweitereLogFile(text);
			
			// hier erfolgt gleichzeitig der Abbruch des Programms!
			return false;
		}
*/		
		
		
		//ansonsten wird jetzt geschnitten:
		System.out.println("Jetzt wird geschnitten!");
		
		
		for (int i = 0; i < aufgabenzahl; i++) {
			rtf = HashMapVerwaltung.getRtfFile();
			
			String[] rtfAufgabe = rtf.clone();
			boolean aufgabeVorhanden = HashMapVerwaltung.getGefundenAusHashmapRtf("Aufgabe"+(i+1));
			System.out.println("Aufgabe" + (i+1) + " wird geschnitten");
			System.out.println("rtfAufgabe hat " + rtfAufgabe.length + " Zeilen");
			if (aufgabeVorhanden) {		
				if (i != aufgabenzahl - 1) { 
					System.out.println("Nicht die letzte Aufgabe");
					//falls es sich nicht um die letzte Aufgabe handelt
					//muss auch alles nach der betreffenden Aufgabe entfernt werden
					rtfAufgabe = schneideNachAufgabe(rtfAufgabe, i+1);	
					// Achtung, die Reihenfolge des Schneidens darf nicht vertauscht werden,
						// schneideNachAufgabe muss vor schneideVorAufgabe aufgerufen werden, 
					// da sonst die Zeilenangaben nicht mehr stimmen
				}
				schneideVorAufgabe(rtfAufgabe, i+1);
				//der Text vor der betreffenden Aufgabe wird entfernt
			}
		}
		return true;
	}

	
	
public static void schneideRtfteilweise(String[] rtf, int gefundeneAufgaben)
		throws IOException {
		
		System.out.println("SchneideRtfteilweise wurde aufgerufen!)");
		
		
		int aufgabenzahl = HashMapVerwaltung.getAufgabenzahlAusHashmap();
		// diese Methode ergaenzt alle benoetigten fehlenden Aufgaben
		// es werden fuer nicht vorhandene Aufgaben jeweils die Zeilen- und Positionsangaben
		// der naechsten vorhandenen Aufgabe eingetragen
		// dies erzeugt den gleichen Effekt, als wenn man die Aufgaben neu durchnummerieren wuerde, 
		// was aber zum einen problematischer waere, da es die keys der HashMapRtf betraefe
		// und zum anderen die Nummern der Aufgaben durcheinanderbraechte
		
		
		// Dies macht jedoch nur solange Sinn, wenn sich hinter einzelnen Aufgaben noch intakte Aufgaben befinden.
		// Ab der letzten intakten Aufgabe muss abgeschnitten werden. Also zuerst ein Test:
		
		int memory = 0;
		for (int d = 1; d <= aufgabenzahl;  d++) {
			String decl = "Aufgabe" + d;
			// gibt es die Aufgabe?
			boolean vorhanden = HashMapVerwaltung.hashMapRtf.containsKey(decl);
			// falls ja, merke die Nummer
			if (vorhanden) {
				memory = d;
			}
		}
		System.out.println("Memory: " + memory);
		
		// Jetzt wird das Ergebnis in die HashMaps eingetragen:
		
		// Auch die Gesamtzahl der Aufgaben hat sich veraendert:
		HashMapVerwaltung.erweitereHashmapInt(HashMapVerwaltung.keyAnzahlRtf, memory);
		
		aufgabenzahl = HashMapVerwaltung.getAufgabenzahlRtfAusHashmap();
		// aufgabenzahl = memory;
		
		for (int k = 1; k <= aufgabenzahl;  k++) {
			String decl = "Aufgabe" + k;
			// gibt es die Aufgabe?
			boolean enthalten = HashMapVerwaltung.hashMapRtf.containsKey(decl);
			// falls nicht:
			if (!enthalten) {
				// zaehle eine Aufgabe weiter
				int l = k+1;
				// und suche von dort aus die naechste vorhandene Aufgabe
				boolean enthaltenWeiter = false;
				String declWeiter = "";
				while (l <= aufgabenzahl) {
					declWeiter = "Aufgabe" + l;
//					int ergebnis2 = 0;
					enthaltenWeiter = HashMapVerwaltung.hashMapRtf.containsKey(declWeiter);
					if (enthaltenWeiter) {
						break;
					}
					else {
						l++;
					}
				}
				// falls es noch eine gibt:
				if (enthaltenWeiter) {
					// dann handelt es sich um declWeiter:
					// die Werte von "declWeiter" werden nun alle fuer die fehlende Aufgabe ubernommen
					// der Wortinhalt wird leergelassen ( źum ev. Erkennen, dass manipuliert wurde
					// das Wort selbst wird im weiteren Verlauf nicht mehr benoetigt
					int zeileErsatz = HashMapVerwaltung.getZeileAusHashmapRtf(declWeiter);
					int positionErsatz = HashMapVerwaltung.getPositionAusHashmapRtf(declWeiter);
					HashMapVerwaltung.erweitereHashmapRtf(decl, "", zeileErsatz, positionErsatz, false);
				}
				// falls keine Aufgabe mehr vorhanden ist, werden die Werte des letzten Wortes im Dokument uebernommen.
				else {
					int zeileErsatz = HashMapVerwaltung.getZeileAusHashmapRtf(HashMapVerwaltung.docEnde);
					int positionErsatz = HashMapVerwaltung.getPositionAusHashmapRtf(HashMapVerwaltung.docEnde);
					HashMapVerwaltung.erweitereHashmapRtf(decl, "", zeileErsatz, positionErsatz, false);
				}
			}	
			
			System.out.println(decl + " war enthalten: " + enthalten);
			
		}
		
		// so, jetzt kann normal weitergearbeitet werden:
		
		System.out.println("Jetzt wird normal weitergeschnitten!!!");
		
		schneideRtf(rtf);
		
		
	}
	
	
	
	
	public static void schneideVorAufgabe(String[] rtf, int aufgabe)
			throws IOException {
		
		// Beachte: es wird die tatsaechliche Nr. der Aufgabe uebergeben, nicht 
		// die Adresse, also 1 mehr....
		
		String startzeileBehalten = "";
		String startzeileAbschneiden = "";

		String endzeileAbschneiden = "";
		String endzeileBehalten = "";
		
		
		//alles ab Kopfanfang wird abgeschnitten
		int zeileDocAnfang = HashMapVerwaltung.getZeileAusHashmapRtf(HashMapVerwaltung.docAnf);
		int positionDocAnfang = HashMapVerwaltung.getPositionAusHashmapRtf(HashMapVerwaltung.docAnf);
		
		System.out.println("Das erste Wort beginnt an Position: " + positionDocAnfang);
		
		startzeileBehalten = rtf[zeileDocAnfang].substring(0, positionDocAnfang); // Teil, der bleibt
		startzeileAbschneiden = rtf[zeileDocAnfang].substring(positionDocAnfang, rtf[zeileDocAnfang].length() );
		
		
		System.out.println(rtf[zeileDocAnfang]);
		System.out.println(startzeileBehalten);
		System.out.println(startzeileAbschneiden);
	
	
		//Ende: das Ende des ausgeschnittenen Abschnitts befindet sich VOR der gesuchten Aufgabe
		String aufgabeNr = "Aufgabe" + aufgabe; 
		
		System.out.println(aufgabeNr);
		
		int zeileAufgabe = HashMapVerwaltung.getZeileAusHashmapRtf(aufgabeNr);
		int positionAufgabe = HashMapVerwaltung.getPositionAusHashmapRtf(aufgabeNr);
		
		System.out.println("Die Aufgabe beginnt an Position: " + positionAufgabe);
	
		endzeileBehalten = rtf[zeileAufgabe].substring( positionAufgabe, rtf[zeileAufgabe].length() );
		endzeileAbschneiden = rtf[zeileAufgabe].substring( 0,positionAufgabe); 
		
		
		System.out.println(rtf[zeileAufgabe]);
		System.out.println("Abschneiden: " + endzeileAbschneiden);
		System.out.println("Behalten: " + endzeileBehalten);
		
				
		// Jetzt der Klammertest:
		boolean klammernpassen = false;
		
		int[] klammernMitte = MethodenRtf.klammerZahlimAbschnitt(rtf, zeileDocAnfang + 1, zeileAufgabe -1);
		int[] klammernAnfang = MethodenRtf.klammerZahlinZeile(startzeileAbschneiden);
		int[] klammernEnde = MethodenRtf.klammerZahlinZeile(endzeileAbschneiden);
		
		int[] klammerngesamt = new int[2];
		for (int i = 0; i <= 1; i++) {
			klammerngesamt[i] = klammernAnfang[i] + klammernMitte[i] + klammernEnde[i];
			System.out.println("Gesamt: " + klammerngesamt[i]);
		}
		
		if (klammerngesamt[0] == klammerngesamt [1]) {
			//das scheint zu passen
			klammernpassen = true;
		}
		
		else {
			
			String text = "Leider ergab der Klammertest in SchneideVorAufgabe einen Fehler; dies kann dazu fuehren, dass die" +
					" ausgeschnittenen Einzelaufgaben kein gueltiges Rtf-File mehr ergeben und sich" +
					" nicht oeffnen lassen.";
				System.out.println(text);
			// dies kam bisher noch nie vor.....daher wurde die Problematik nicht weiter behandelt
			
		
		}
		
		
		// Jetzt wird die Aufgabe ausgeschnitten:

		String[] ergebnis  = rtf.clone();
		ergebnis[zeileDocAnfang] = startzeileBehalten; // in dieser Zeile wird nur der erste Teil behalten
		ergebnis[zeileAufgabe] = endzeileBehalten; // in dieser Zeile wird nur der hintere Teil behalten
		ergebnis = Sheetdraft.loescheZeilen(ergebnis, zeileDocAnfang+1, zeileAufgabe-1);
		
		schreibeEinzelaufgabeRtfInHashMapStringArray(aufgabeNr, ergebnis);
	//	String titel = HashMapVerwaltung.getDocTitleAusHashmap();
		//String nameFile = titel + "_" + aufgabeNr + ".rtf";
		//LiesSchreib.erstelleFile(ergebnis, nameFile);
	
	}

	
	public static void schreibeEinzelaufgabeRtfInHashMapStringArray(String aufgabeNr
			, String [] ergebnis) throws IOException {
		String titel = HashMapVerwaltung.getDocTitleAusHashmap();
		String nameFile = titel + "__" + aufgabeNr + ".rtf";
		System.out.println("Aufgabennummer " + aufgabeNr + " Erster Eintrag des ergebnisses " + ergebnis[0]);
		System.out.println("Arraygroesse: " + ergebnis.length);
		System.out.println("letzter Eintrag:" + ergebnis[ergebnis.length -1]);
		ReadWrite.writeText(ergebnis, nameFile);
		String keyHashMap = aufgabeNr + "_Rtf";
		HashMapVerwaltung.erweitereHashmapStringArrayAufgaben(keyHashMap, ergebnis);
		
		String text = aufgabeNr + " wurde ausgeschnitten und unter " + nameFile + " ausgegeben."; 
		System.out.println(text);
		HashLog.erweitereLogFile(text);
	
	
	}
	
	
	public static String[] schneideNachAufgabe(String[] rtf, int aufgabe) {
		
		
		// Beachte: es wird die tatsaechliche Nr. der Aufgabe uebergeben, nicht 
		// die Adresse, also 1 mehr....
		
		String startzeileBehalten = "";
		String startzeileAbschneiden = "";

		String endzeileAbschneiden = "";
		String endzeileBehalten = "";
		
		
		
		//Ab der nachfolgenden Aufgabe wird abgeschnitten
		String aufgabeNr = "Aufgabe" + (aufgabe + 1); 
		
		System.out.println("Ab " + aufgabeNr + " wird abgeschnitten");
		
		int zeileAufgabe = HashMapVerwaltung.getZeileAusHashmapRtf(aufgabeNr);
		int positionAufgabe = HashMapVerwaltung.getPositionAusHashmapRtf(aufgabeNr);
		
		System.out.println("Das Rtf-File hat: " + rtf.length + " Zeilen");
		
		System.out.println("Die Aufgabe beginnt in Zeile: " + zeileAufgabe);
		System.out.println("Die Aufgabe beginnt an Position: " + positionAufgabe);
		System.out.println("Die entsprechende Zeile hat " + rtf[zeileAufgabe].length() + "Zeichen.");
		
		
		startzeileAbschneiden = rtf[zeileAufgabe].substring( positionAufgabe, rtf[zeileAufgabe].length() );
		startzeileBehalten = rtf[zeileAufgabe].substring( 0,positionAufgabe); 
		
		System.out.println(rtf[zeileAufgabe]);
		System.out.println(startzeileBehalten);
		System.out.println(startzeileAbschneiden);
		
		//alles bis zum Dokumentenende wird abgeschnitten
		int zeileDocEnde = HashMapVerwaltung.getZeileAusHashmapRtf(HashMapVerwaltung.docEnde);
		int positionDocEnde = HashMapVerwaltung.getPositionAusHashmapRtf(HashMapVerwaltung.docEnde);
		
		System.out.println("Das letzte Wort endet an Position: " + positionDocEnde);
		
		endzeileAbschneiden = rtf[zeileDocEnde].substring(0, positionDocEnde+1); // Teil, der abgeschnitten wird
		
		endzeileBehalten = rtf[zeileDocEnde].substring(positionDocEnde+1, rtf[zeileDocEnde].length() );
			
		System.out.println(rtf[zeileDocEnde]);
		System.out.println("Abschneiden: " + endzeileAbschneiden);
		System.out.println("Behalten: " + endzeileBehalten);
		
				
		// Jetzt der Klammertest:
		boolean klammernpassen = false;
		
		int[] klammernMitte = MethodenRtf.klammerZahlimAbschnitt(rtf, zeileAufgabe + 1, zeileDocEnde -1);
		int[] klammernAnfang = MethodenRtf.klammerZahlinZeile(startzeileAbschneiden);
		int[] klammernEnde = MethodenRtf.klammerZahlinZeile(endzeileAbschneiden);
		
		int[] klammerngesamt = new int[2];
		for (int i = 0; i <= 1; i++) {
			klammerngesamt[i] = klammernAnfang[i] + klammernMitte[i] + klammernEnde[i];
			System.out.println("Gesamt: " + klammerngesamt[i]);
		}
		
		if (klammerngesamt[0] == klammerngesamt [1]) {
			//das scheint zu passen
			klammernpassen = true;
		}
		
		else {
			// Erlaeuterung der Problematik siehe oben bei schneideVorAufgabe:
			String text = "Leider ergab der Klammertest in SchneideVorAufgabe einen Fehler; dies kann dazu fuehren, dass die" +
			" asugeschnittenen Einzelaufgaben kein gueltiges Rtf-File mehr ergeben und sich" +
			" nicht oeffnen lassen.";
			System.out.println(text);
		}
		
		
		//Jetzt wird die Aufgabe ausgeschnitten:
		
		String[] ergebnis  = rtf.clone();
		ergebnis[zeileAufgabe] = startzeileBehalten; // in dieser Zeile wird nur der erste Teil behalten
		ergebnis[zeileDocEnde] = endzeileBehalten; // in dieser Zeile wird nur der hintere Teil behalten
		ergebnis = Sheetdraft.loescheZeilen(ergebnis, zeileAufgabe+1, zeileDocEnde-1);
		
		
		return ergebnis;
		
	}	

}

