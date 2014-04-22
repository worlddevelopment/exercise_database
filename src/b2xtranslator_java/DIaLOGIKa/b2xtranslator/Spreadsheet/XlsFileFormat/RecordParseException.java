//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:38 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;

public class RecordParseException  extends Exception 
{
    public RecordParseException(BiffRecord record) throws Exception {
        super(String.format(StringSupport.CSFmtStrToJFmtStr("Error parsing BIFF record with id {0:X02}h at stream offset {1}"),record.getId(),record.getOffset()));
    }

}


