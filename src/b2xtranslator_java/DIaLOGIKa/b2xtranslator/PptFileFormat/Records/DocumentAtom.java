//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.GPointAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.GRatioAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.OfficeRecordAttribute;

public class DocumentAtom  extends DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record 
{
    public GPointAtom SlideSize;
    public GPointAtom NotesSize;
    public GRatioAtom ServerZoom;
    public long NotesMasterPersist;
    public long HandoutMasterPersist;
    public UInt16 FirstSlideNum = new UInt16();
    public short SlideSizeType;
    public boolean SaveWithFonts;
    public boolean OmitTitlePlace;
    public boolean RightToLeft;
    public boolean ShowComments;
    public DocumentAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.SlideSize = new GPointAtom(this.Reader);
        this.NotesSize = new GPointAtom(this.Reader);
        this.ServerZoom = new GRatioAtom(this.Reader);
        this.NotesMasterPersist = this.Reader.ReadUInt32();
        this.HandoutMasterPersist = this.Reader.ReadUInt32();
        this.FirstSlideNum = this.Reader.ReadUInt16();
        this.SlideSizeType = this.Reader.ReadInt16();
        this.SaveWithFonts = this.Reader.ReadByte() != 0;
        this.OmitTitlePlace = this.Reader.ReadByte() != 0;
        this.RightToLeft = this.Reader.ReadByte() != 0;
        this.ShowComments = this.Reader.ReadByte() != 0;
    }

    public String toString(uint depth) throws Exception {
        return String.Format("{0}\n{1}SlideSize = {2}, NotesSize = {3}, ServerZoom = {4}\n{1}" + "NotesMasterPersist = {5}, HandoutMasterPersist = {6}, FirstSlideNum = {7}, SlideSizeType = {8}\n{1}" + "SaveWithFonts = {9}, OmitTitlePlace = {10}, RightToLeft = {11}, ShowComments = {12}", super.toString(depth), indentationForDepth(depth + 1), this.SlideSize, this.NotesSize, this.ServerZoom, this.NotesMasterPersist, this.HandoutMasterPersist, this.FirstSlideNum, this.SlideSizeType, this.SaveWithFonts, this.OmitTitlePlace, this.RightToLeft, this.ShowComments);
    }

}


