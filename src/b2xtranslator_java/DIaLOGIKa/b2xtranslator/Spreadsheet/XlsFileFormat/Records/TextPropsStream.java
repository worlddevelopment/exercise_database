//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:50 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FrtHeader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class TextPropsStream  extends BiffRecord 
{
    public static final RecordType ID = RecordType.TextPropsStream;
    /**
    * An FrtHeader. The FrtHeader.rt field MUST be 0x08A5.
    */
    public FrtHeader frtHeader;
    public long dwChecksum;
    public long cb;
    public String rgb;
    public TextPropsStream(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.frtHeader = new FrtHeader(reader);
        this.dwChecksum = reader.readUInt32();
        this.cb = reader.readUInt32();
        byte[] rgbBytes = reader.readBytes((int)this.cb);
        Encoding codepage = Encoding.GetEncoding(1252);
        this.rgb = new String(rgbBytes, codepage.getString());
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


