//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.PicfSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Continue;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.GelFrame;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class GelFrameSequence  extends BiffRecordSequence 
{
    public CSList<GelFrame> GelFrames;
    public CSList<Continue> Continues;
    public PicfSequence PicfSequence;
    public GelFrameSequence(IStreamReader reader) throws Exception {
        super(reader);
        // GELFRAME = 1*2GelFrame *Continue [PICF]
        // 1*2GelFrame
        this.GelFrames = new CSList<GelFrame>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.GelFrame)
        {
            this.GelFrames.add((GelFrame)BiffRecord.readRecord(reader));
        }
        // *Continue
        this.Continues = new CSList<Continue>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Continue)
        {
            this.Continues.add((Continue)BiffRecord.readRecord(reader));
        }
        if (BiffRecord.getNextRecordType(reader) == RecordType.Begin)
        {
            this.PicfSequence = new PicfSequence(reader);
        }
         
    }

}


