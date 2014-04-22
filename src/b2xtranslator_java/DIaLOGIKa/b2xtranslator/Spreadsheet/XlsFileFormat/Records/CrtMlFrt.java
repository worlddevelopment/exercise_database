//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:42 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FrtHeader;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkChain;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class CrtMlFrt  extends BiffRecord 
{
    public FrtHeader frtHeader;
    /**
    * An unsigned integer that specifies the size, in bytes,
    * of the XmlTkChain structure starting in the xmltkChain field,
    * including the data contained in the optional CrtMlFrtContinue records.
    * MUST be less than or equal to 0x7FFFFFEB.
    */
    public long cb;
    public XmlTkChain xmltkChain;
    public CrtMlFrt(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        long pos = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        this.frtHeader = new FrtHeader(reader);
        this.cb = reader.readUInt32();
        this.xmltkChain = new XmlTkChain(reader);
        reader.readBytes(4);
        // unused
        Unsupported.throwUnsupported("reader.getBaseStream().Position = pos + length");
    }

}


