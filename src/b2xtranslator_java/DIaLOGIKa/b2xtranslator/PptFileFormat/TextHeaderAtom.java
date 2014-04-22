//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.NotImplementedException;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ITextDataRecord;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextStyleAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextType;

public class TextHeaderAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public TextType TextType = TextType.Title;
    public TextAtom TextAtom;
    public TextStyleAtom TextStyleAtom;
    public TextHeaderAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.TextType = (TextType)this.Reader.ReadUInt32();
    }

    public void handleTextDataRecord(ITextDataRecord tdRecord) throws Exception {
        tdRecord.setTextHeaderAtom(this);
        TextAtom textAtom = tdRecord instanceof TextAtom ? (TextAtom)tdRecord : (TextAtom)null;
        TextStyleAtom tsAtom = tdRecord instanceof TextStyleAtom ? (TextStyleAtom)tdRecord : (TextStyleAtom)null;
        if (textAtom != null)
        {
            this.TextAtom = textAtom;
        }
        else if (tsAtom != null)
        {
            this.TextStyleAtom = tsAtom;
        }
        else
        {
            throw new NotImplementedException("Unhandled text data record type " + tdRecord.getClass().toString());
        }  
    }

}


