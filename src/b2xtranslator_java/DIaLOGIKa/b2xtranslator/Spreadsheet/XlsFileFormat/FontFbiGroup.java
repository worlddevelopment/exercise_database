//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Fbi;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Font;

public class FontFbiGroup   
{
    private Font _font;
    private Fbi _fbi;
    public FontFbiGroup(Font font, Fbi fbi) throws Exception {
        this._font = font;
        this._fbi = fbi;
    }

    public Font getFont() throws Exception {
        return this._font;
    }

    public void setFont(Font value) throws Exception {
        this._font = value;
    }

    public Fbi getFbi() throws Exception {
        return this._fbi;
    }

    public void setFbi(Fbi value) throws Exception {
        this._fbi = value;
    }

}


