//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class HeadersFootersAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public short formatId;
    public boolean fHasDate;
    public boolean fHasTodayDate;
    public boolean fHasUserDate;
    public boolean fHasSlideNumber;
    public boolean fHasHeader;
    public boolean fHasFooter;
    public HeadersFootersAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        formatId = this.Reader.ReadInt16();
        short mask = this.Reader.ReadInt16();
        fHasDate = ((mask & (1)) != 0);
        fHasTodayDate = ((mask & (1 << 1)) != 0);
        fHasUserDate = ((mask & (1 << 2)) != 0);
        fHasSlideNumber = ((mask & (1 << 3)) != 0);
        fHasHeader = ((mask & (1 << 4)) != 0);
        fHasFooter = ((mask & (1 << 5)) != 0);
    }

}


