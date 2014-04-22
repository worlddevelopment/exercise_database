//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.FontFbiGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Fbi;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Font;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.FrtFontList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class FontListSequence  extends BiffRecordSequence 
{
    public FrtFontList FrtFontList;
    //public StartObject StartObject;
    public CSList<FontFbiGroup> Fonts;
    //public EndObject EndObject;
    public FontListSequence(IStreamReader reader) throws Exception {
        super(reader);
        //FrtFontList
        this.FrtFontList = (FrtFontList)BiffRecord.readRecord(reader);
        //StartObject
        //this.StartObject = (StartObject)BiffRecord.ReadRecord(reader);
        //*(Font [Fbi])
        this.Fonts = new CSList<FontFbiGroup>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Font)
        {
            Font font = (Font)BiffRecord.readRecord(reader);
            Fbi fbi = null;
            if (BiffRecord.getNextRecordType(reader) == RecordType.Fbi)
            {
                fbi = (Fbi)BiffRecord.readRecord(reader);
            }
             
            this.Fonts.add(new FontFbiGroup(font,fbi));
        }
    }

}


//EndObject
//this.EndObject = (EndObject)BiffRecord.ReadRecord(reader);