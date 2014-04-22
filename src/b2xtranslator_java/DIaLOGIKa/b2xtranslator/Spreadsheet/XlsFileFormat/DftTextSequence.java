//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DataLabExt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DefaultText;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class DftTextSequence  extends BiffRecordSequence 
{
    public DataLabExt DataLabExt;
    //public StartObject StartObject;
    public DefaultText DefaultText;
    public AttachedLabelSequence AttachedLabelSequence;
    //public EndObject EndObject;
    public DftTextSequence(IStreamReader reader) throws Exception {
        super(reader);
        // DFTTEXT = [DataLabExt StartObject] DefaultText ATTACHEDLABEL [EndObject]
        // [DataLabExt StartObject]
        if (BiffRecord.getNextRecordType(reader) == RecordType.DataLabExt)
        {
            this.DataLabExt = (DataLabExt)BiffRecord.readRecord(reader);
        }
         
        //this.StartObject = (StartObject)BiffRecord.ReadRecord(reader);
        // DefaultText
        this.DefaultText = (DefaultText)BiffRecord.readRecord(reader);
        // ATTACHEDLABEL
        this.AttachedLabelSequence = new AttachedLabelSequence(reader);
    }

}


// [EndObject]
//if (BiffRecord.GetNextRecordType(reader) == RecordType.EndObject)
//{
//    this.EndObject = (EndObject)BiffRecord.ReadRecord(reader);
//}