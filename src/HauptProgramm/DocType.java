/**
 * 
 */
package HauptProgramm;

/**
 * Enum, die die unterstützten Dateiendungen auflistet.
 * 
 * 
 * @author Schweiner
 *
 */
public enum DocType {
	RTF(".rtf"), PDF(".pdf"), TEX(".tex"), DOC(".doc"), DOCX(".docx"), TXT(".txt");
	
	 private String ending;

	 private DocType(String c) {
	 	ending = c;
	 }

	 public String getCode() {
	 	return ending;
	 }
	 
	 
	 
	//=======METHODEN --
	/* annotation by JRIBW: EVen though I think e.g. RTF(rtf)
	   is REDUNDANT! I have added all those following lines below */
	public String toString() {
	    return this.ending;
	}
	
	public String getEnding() {
	    return this.ending;
	}
	
	public static DocType getByEnding(String ending) {
	    /*
	    System.out.println(name);
	    if (name.equalsIgnoreCase("title")) {
	        return Resource.Private;
	    }
	    */
	    if (ending != null) {
	        for (DocType cat : DocType.values()) {
	            if (("." + ending).equalsIgnoreCase(cat.ending)) {
	                return cat;
	            }
	        }
	    }
	    return null;
	}
		
	 
	 
	 
	 
}
