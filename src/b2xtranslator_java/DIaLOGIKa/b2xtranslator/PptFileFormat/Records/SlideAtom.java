//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.SSlideLayoutAtom;

public class SlideAtom  extends DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record 
{
    public SSlideLayoutAtom Layout;
    public int MasterId;
    public int NotesId;
    public UInt16 Flags = new UInt16();
    public SlideAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.Layout = new SSlideLayoutAtom(this.Reader);
        this.MasterId = this.Reader.ReadInt32();
        this.NotesId = this.Reader.ReadInt32();
        this.Flags = this.Reader.ReadUInt16();
        this.Reader.ReadUInt16();
    }

    // Throw away undocumented data
    public String toString(uint depth) throws Exception {
        return String.Format("{0}\n{1}Layout = {2}\n{1}MasterId = {3}, NotesId = {4}, Flags = {5})", super.toString(depth), indentationForDepth(depth + 1), this.Layout, this.MasterId, this.NotesId, this.Flags);
    }

}


