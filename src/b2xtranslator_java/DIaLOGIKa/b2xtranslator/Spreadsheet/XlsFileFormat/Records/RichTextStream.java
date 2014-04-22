//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:47 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FrtHeader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class RichTextStream  extends BiffRecord 
{
    public FrtHeader frtHeader;
    public long dwCheckSum;
    public long cb;
    public String rgb;
    public RichTextStream(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        this.frtHeader = new FrtHeader(reader);
        this.dwCheckSum = reader.readUInt32();
        this.cb = reader.readUInt32();
        Encoding codepage = Encoding.GetEncoding(1252);
        this.rgb = new String(reader.readBytes((int)this.cb), codepage.getString());
    }

}


