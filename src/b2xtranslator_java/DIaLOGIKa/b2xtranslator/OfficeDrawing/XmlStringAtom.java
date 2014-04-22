//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import CS2JNet.System.Xml.XmlDocument;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.XmlRecord;
import java.io.InputStream;

/**
* XML string atoms are atom elements which have a XML payload string as their content.
*/
public class XmlStringAtom  extends XmlRecord 
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
    public XmlStringAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        byte[] bytes = this.Reader.ReadBytes((int)size);
        InputStream partStream = new MemoryStream(bytes);
        XmlDocument partDoc = new XmlDocument();
        partDoc.load(partStream);
        this.XmlDocumentElement = partDoc.getDocumentElement();
    }

}


