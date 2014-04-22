//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ObjProtect;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Password;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Protect;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ScenarioProtect;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class ProtectionSequence  extends BiffRecordSequence 
{
    public Protect Protect;
    public ScenarioProtect ScenarioProtect;
    public ObjProtect ObjProtect;
    public Password Password;
    public ProtectionSequence(IStreamReader reader) throws Exception {
        super(reader);
        // PROTECTION = [Protect] [ScenarioProtect] [ObjProtect] [Password]
        // [Protect]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Protect)
        {
            this.Protect = (Protect)BiffRecord.readRecord(reader);
        }
         
        // [ScenarioProtect]
        if (BiffRecord.getNextRecordType(reader) == RecordType.ScenarioProtect)
        {
            this.ScenarioProtect = (ScenarioProtect)BiffRecord.readRecord(reader);
        }
         
        // [ObjProtect]
        if (BiffRecord.getNextRecordType(reader) == RecordType.ObjProtect)
        {
            this.ObjProtect = (ObjProtect)BiffRecord.readRecord(reader);
        }
         
        // [Password]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Password)
        {
            this.Password = (Password)BiffRecord.readRecord(reader);
        }
         
    }

}


