//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetContentSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BOF;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ChartFrtInfo;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class ChartSheetSequence  extends BiffRecordSequence implements IVisitable
{
    public BOF BOF;
    public ChartFrtInfo ChartFrtInfo;
    public ChartSheetContentSequence ChartSheetContentSequence;
    public ChartSheetSequence(IStreamReader reader) throws Exception {
        super(reader);
        //BOF
        this.BOF = (BOF)BiffRecord.readRecord(reader);
        // [ChartFrtInfo] (not specified)
        if (BiffRecord.getNextRecordType(reader) == RecordType.ChartFrtInfo)
        {
            this.ChartFrtInfo = (ChartFrtInfo)BiffRecord.readRecord(reader);
        }
         
        //CHARTSHEETCONTENT
        this.ChartSheetContentSequence = new ChartSheetContentSequence(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ChartSheetSequence>)mapping).apply(this);
    }

}


