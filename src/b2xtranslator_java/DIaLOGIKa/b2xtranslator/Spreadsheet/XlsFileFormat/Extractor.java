//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:36 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExtractorException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

/**
* Abstract class which implements some extractor properties and methods
*/
public abstract class Extractor   
{
    public VirtualStreamReader StreamReader;
    /**
    * Ctor
    * 
    *  @param sum workbookstream
    */
    public Extractor(VirtualStreamReader reader) throws Exception {
        this.StreamReader = reader;
        if (StreamReader == null)
        {
            throw new ExtractorException(ExtractorException.NULLPOINTEREXCEPTION);
        }
         
    }

    /**
    * extracts the data from the given stream !!!
    */
    public abstract void extractData() throws Exception ;

}


