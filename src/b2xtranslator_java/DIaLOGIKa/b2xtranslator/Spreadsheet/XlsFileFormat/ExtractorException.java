//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:38 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;


public class ExtractorException  extends Exception 
{
    /**
    * some public static attributes
    */
    public static final String NULLPOINTEREXCEPTION = "Null pointer exception!!";
    public static final String NOFILEFOUNDEXCEPTION = "No file found!!";
    public static final String PARSEDFORMULAEXCEPTION = "Formula is not valid !!";
    public static final String WORKBOOKSTREAMNOTFOUND = "Workbook stream not found!!";
    public static final String FILEENCRYPTED = "This file is encrypted!!";
    /**
    * Overridden ctor
    */
    public ExtractorException() throws Exception {
    }

    /**
    * Overridden ctor
    * 
    *  @param message The exception message
    */
    public ExtractorException(String message) throws Exception {
        super(message);
    }

    /**
    * Overridden ctor
    * 
    *  @param message The exception message
    *  @param inner
    */
    public ExtractorException(String message, Exception inner) throws Exception {
        super(message, inner);
    }

}


