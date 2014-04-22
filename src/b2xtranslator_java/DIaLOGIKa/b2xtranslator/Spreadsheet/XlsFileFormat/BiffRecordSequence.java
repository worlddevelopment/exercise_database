//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class BiffRecordSequence   
{
    IStreamReader _reader;
    public IStreamReader getReader() throws Exception {
        return _reader;
    }

    public void setReader(IStreamReader value) throws Exception {
        this._reader = value;
    }

    public BiffRecordSequence(IStreamReader reader) throws Exception {
        _reader = reader;
    }

}


