//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import CS2JNet.System.Xml.XmlElement;

/**
* XML records are containers with a XML payload.
*/
public class XmlRecord  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    /**
    * Standard constructor. Simply calls the inherited Record constructor.
    * 
    *  @param _reader Underlying reader for parent constructor. Shouldn't be used directly.
    *  @param size Size of record body in bytes.
    *  @param typeCode Type code of record.
    *  @param version Version field of record.
    *  @param instance Instance field of record.
    */
    public XmlRecord(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
    }

    /**
    * The root element of element's XML content.
    */
    public XmlElement XmlDocumentElement;
}


