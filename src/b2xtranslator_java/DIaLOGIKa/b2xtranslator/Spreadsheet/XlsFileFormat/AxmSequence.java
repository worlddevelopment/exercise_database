//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.YMult;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AxmSequence  extends BiffRecordSequence 
{
    public YMult YMult;
    //public StartObject StartObject;
    public AttachedLabelSequence AttachedLabelSequence;
    //public EndObject EndObject;
    public AxmSequence(IStreamReader reader) throws Exception {
        super(reader);
        //AXM = YMult StartObject ATTACHEDLABEL EndObject
        //YMult
        this.YMult = (YMult)BiffRecord.readRecord(reader);
        //StartObject
        //this.StartObject = (StartObject)BiffRecord.ReadRecord(reader);
        //ATTACHEDLABEL
        this.AttachedLabelSequence = new AttachedLabelSequence(reader);
    }

}


//EndObject
//this.EndObject = (EndObject)BiffRecord.ReadRecord(reader);