package aufgaben_db;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Language {

	ENGLISH,//ordinal 0
	GERMAN, //ordinal 1
	FRENCH; //ordinal 2
//	RUSSIAN,//ordinal 3
//	SPANISH,//ordinal 4
//	CHINESE,//ordinal 5
//	SWEDISH,//ordinal 6
//	NORWEGIAN,//ordinal 7
//  ESPERANTO,//ordinal 8
//  AFRIKAANS, //ordinal 9
//  GREEK, //ordinal 10
//  LATIN, //ordinal 11 (hey, then we need old english too :)
//  URDU, // ??	
//  HINDI, // ??
//  JAPANESE,

	//public static final Map<String, String> translation;
	
	public static final Map<String, String> english;//en2en;
	public static final Map<String, String> german;//en2de;
	public static final Map<String, String> french;//en2fr;
	//The cleaner not working approach using the enums ordinal().
	//public static final Map<String, String>[] translations = new HashMap[Language.values().length];
	static {
		
		/* ENGLISH */
		//position 0 <-- English (see enumeration Languages)
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.clear();
		aMap.put("exercise database", "Exercise Database");
		
		aMap.put("semester", "semester");
		aMap.put("course", "course");
		aMap.put("courses", "courses");
		aMap.put("lecturer", "lecturer");
		aMap.put("type", "type");
		aMap.put("types", "types");

		aMap.put("filelink", "filelink");
		aMap.put("author", "author");
		aMap.put("uploaded by", "uploaded by");
		aMap.put("splitter", "splitter");
		
		aMap.put("filetype", "filetype");
		aMap.put("content", "content");
		
		aMap.put("uebung", "exercise");//TODO redo this completely, adapt search to look within all languages
		aMap.put("loesung", "solution");
		aMap.put("klausur", "exam");
		aMap.put("klausurloesung", "exam solution");
		
		aMap.put("sheet", "sheet");
		aMap.put("sheets", "sheets");
		aMap.put("draft", "draft");
		aMap.put("drafts", "drafts");
		aMap.put("exercise", "exercise");
		aMap.put("exercises", "exercises");
		aMap.put("exercise_sheettype", "exercise");//for distinction simply add '_sheettype'
		aMap.put("exercises_sheettype", "exercise_sheets");
		
		aMap.put("upload", "upload");
		aMap.put("send", "send");
		aMap.put("create", "create");
		aMap.put("to_search", "search");
		aMap.put("search", "search");
		aMap.put("extended search", "extended search");
		aMap.put("more searchcriteria", "more searchcriteria");
		aMap.put("last modified", "last modified");
		aMap.put("hello", "hello");
		aMap.put("view", "view");
		//search
		aMap.put("hits", "hits");
				
		
		//MENU
		aMap.put("start", "start");//home
		aMap.put("add sheet", "add sheet");//home
		aMap.put("add a sheet", "add sheet");//home
		aMap.put("statistics", "statistics");
		aMap.put("profile", "profile");
		
		aMap.put("edit", "edit");
		aMap.put("add to draft", "add to draft");
		
		aMap.put("seconds", "seconds");
		aMap.put("collapse the whole tree", "collapse the whole tree");
		aMap.put("minimize the whole tree", "minimize the whole tree");
		aMap.put("unfold the whole tree", "unfold the whole tree");
		aMap.put("expand the whole tree", "expand the whole tree");
		
		
		english = Collections.unmodifiableMap(aMap);
		//translations[Language.ENGLISH.ordinal()] = Collections.unmodifiableMap(aMap);

		
		
		
		
		
		
		
		/* GERMAN *///a new map because it did always only the last assignment stay active!
		aMap = new HashMap<String, String>();
		//position 1 <-- German (see enumeration Languages)
		//aMap.clear();<-old approach,not working for unmodifiable list,only last assignment is valid
		aMap.put("exercise database", "Aufgaben Datenbank");
		
		aMap.put("semester", "Semester");
		aMap.put("course", "Kurs");
		aMap.put("courses", "Kurse");
		aMap.put("lecturer", "Dozent");
		aMap.put("type", "Typ");
		aMap.put("types", "Typen");

		aMap.put("filelink", "Dateilink");
		aMap.put("author", "Autor");
		aMap.put("uploaded by", "hochgeladen von");
		aMap.put("splitter", "Splitter");
		
		aMap.put("filetype", "Dateityp");
		aMap.put("content", "Inhalt");
		
		aMap.put("uebung", "\u00DCbung");//TODO redo this completely, adapt search to look within all languages
		aMap.put("loesung", "L\u00F6sung");
		aMap.put("klausur", "Klausur");
		aMap.put("klausurloesung", "Klausurl\u00F6sung");
		
		aMap.put("sheet", "Blatt");
		aMap.put("sheets", "Bl\u00E4tter");
		aMap.put("draft", "Entwurf");
		aMap.put("drafts", "Entw\u00FCrfe");
		aMap.put("exercise", "Aufgabe");
		aMap.put("exercises", "Aufgaben");
		aMap.put("exercise_sheettype", "\u00DCbung");//for distinction simply add '_sheettype'
		aMap.put("exercises_sheettype", "\u00DCbungen");
		
		aMap.put("upload", "hochladen");
		aMap.put("send", "abschicken");
		aMap.put("create", "erstellen");
		aMap.put("to_search", "suchen");
		aMap.put("search", "Suche");
		aMap.put("extended search", "erweiterte Suche");
		aMap.put("more searchcriteria", "mehr Suchkriterien");
		aMap.put("last modified", "letzte \u00DCberarbeitung");
		aMap.put("hello", "Hallo");
		aMap.put("view", "Ansicht");
		//search
		aMap.put("hits", "Treffer");
		
		//MENU
		aMap.put("start", "Start");//home
		aMap.put("upload sheet", "Blatt hinzuf\u00FCgen");//home
		aMap.put("add sheet", "Blatt hinzuf\u00FCgen");//home
		aMap.put("add a sheet", "Blatt hinzuf\u00FCgen");//home
		aMap.put("statistics", "Statistik");
		aMap.put("profile", "Profil");
		
		aMap.put("edit", "edit");
		aMap.put("add to draft", "in Entwurf");
		
		aMap.put("seconds", "Sekunden");
		aMap.put("collapse the whole tree", "Den ganzen Baum zusammenklappen");
		aMap.put("minimize the whole tree", "Den ganzen Baum minimieren");
		aMap.put("unfold the whole tree", "Den ganzen Baum aufklappen");
		aMap.put("expand the whole tree", "Den ganzen Baum ausklappen");
		
		
		german = Collections.unmodifiableMap(aMap);
		//translations[Language.GERMAN.ordinal()] = Collections.unmodifiableMap(aMap);
		
		
		
		
		
		
		
		
		
		/* FRENCH */
		aMap = new HashMap<String, String>();
		aMap.clear();
		aMap.put("exercise database", "Base de Données d'Exercice");
		
		aMap.put("semester", "semestre");
		aMap.put("course", "cours");
		aMap.put("courses", "cours");
		aMap.put("lecturer", "chargé de cours");
		aMap.put("type", "type");
		aMap.put("types", "types");

		aMap.put("filelink", "lien du fichier");//URL or URI alternatively, je ne suis pas sûr avec ca!
		aMap.put("author", "auteur");
		aMap.put("uploaded by", "téléchargéer par");
		aMap.put("splitter", "sectionneur");
		
		aMap.put("filetype", "type de fichier");
		aMap.put("content", "contenu");
		
		aMap.put("uebung", "exercice");//TODO redo this completely, adapt search to look within all languages
		aMap.put("loesung", "solution");
		aMap.put("klausur", "exam");
		aMap.put("klausurloesung", "exam solution");
		
		aMap.put("sheet", "feuille");
		aMap.put("sheets", "feuilles");
		aMap.put("draft", "ébauche");
		aMap.put("drafts", "ébauches");
		aMap.put("exercise", "exercice");
		aMap.put("exercises", "exercice");
		aMap.put("exercise_sheettype", "feuille d'exercice");//for distinction simply add '_sheettype'
		aMap.put("exercises_sheettype", "feuilles d'exercice");
		
		aMap.put("upload", "télécharger");
		aMap.put("send", "envoyer");
		aMap.put("create", "erstellen");
		aMap.put("to_search", "chercher");
		aMap.put("search", "recherche");
		aMap.put("extended search", "recherche avancée");
		aMap.put("more searchcriteria", "plus de critères");
		aMap.put("last modified", "dernière révision");
		aMap.put("hello", "salut");
		aMap.put("view", "voir");
		//search
		aMap.put("hits", "hits");
				
		//MENU
		aMap.put("start", "maison");//home
		aMap.put("upload sheet", "ajouter une feuille");//home
		aMap.put("add sheet", "ajouter une feuille");//home
		aMap.put("add a sheet", "ajouter une feuille");//home
		aMap.put("statistics", "statistiques");
		aMap.put("profile", "profil");
		
		aMap.put("edit", "edit");
		aMap.put("add to draft", "ajoute-le au projet"/*à la conception"*/);
		
		aMap.put("seconds", "secondes");
		aMap.put("collapse the whole tree", "plier l'arbre entier");
		aMap.put("minimize the whole tree", "minimiser l'arbre entierDen ganzen Baum ausklappen");
		aMap.put("unfold the whole tree", "développez l'arbre entier");
		aMap.put("expand the whole tree", "développez l'arbre entierDen ganzen Baum ausklappen");
		
		//en2fr
		french = Collections.unmodifiableMap(aMap);
		
		
		
	};
	
	
	
	
	
	
	
	
	//Map<String, String> translation;
	private Language(/*Map<String, String> translation*/) {
		//this.translation = translation;
	
		
	}
	
	
	
	
	
	//used for fishing the correct language strings
	public int toInt() {
		return this.ordinal();
	}
	
	public String toString() {
		return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
	}
	
	
	//static
	public static Language getByName(String langname) {
		for (Language language : Language.values()) {
			if (langname.toLowerCase().equals(language.name().toLowerCase())) {
				return language;
			}
		}
		//if this language was not found
		return null;
	}
	
	
	public Map<String, String> getTranslationMap() {
		try {
			String language = this.name().toLowerCase();
			Object fieldObject = this.getClass().getDeclaredField(language).get(this);
			if (fieldObject instanceof Map<?, ?>) {
				return (Map<String, String>)this.getClass().getDeclaredField(
						this.name().toLowerCase()).get(this);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String getTranslation(String toTranslate) {
		return this.getTranslationMap().get(toTranslate.toLowerCase());
	}
	
	
	
 	
	
}
