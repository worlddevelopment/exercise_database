//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:11 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.WordprocessingDocument;

public class ConversionContext   
{
    private WordprocessingDocument _docx;
    //private Dictionary<Int32, SectionPropertyExceptions> _allSepx;
    //private Dictionary<Int32, ParagraphPropertyExceptions> _allPapx;
    private XmlWriterSettings _writerSettings = new XmlWriterSettings();
    private WordDocument _doc;
    /**
    * The source of the conversion.
    */
    public WordDocument getDoc() throws Exception {
        return _doc;
    }

    public void setDoc(WordDocument value) throws Exception {
        _doc = value;
    }

    /**
    * This is the target of the conversion.
    * The result will be written to the parts of this document.
    */
    public WordprocessingDocument getDocx() throws Exception {
        return _docx;
    }

    public void setDocx(WordprocessingDocument value) throws Exception {
        _docx = value;
    }

    /**
    * The settings of the XmlWriter which writes to the part
    */
    public XmlWriterSettings getWriterSettings() throws Exception {
        return _writerSettings;
    }

    public void setWriterSettings(XmlWriterSettings value) throws Exception {
        _writerSettings = value;
    }

    /**
    * A list thta contains all revision ids.
    */
    public CSList<String> AllRsids;
    public ConversionContext(WordDocument doc) throws Exception {
        this.setDoc(doc);
        this.AllRsids = new CSList<String>();
    }

    /**
    * Adds a new RSID to the list
    * 
    *  @param rsid
    */
    public void addRsid(String rsid) throws Exception {
        if (!this.AllRsids.contains(rsid))
            this.AllRsids.add(rsid);
         
    }

}


