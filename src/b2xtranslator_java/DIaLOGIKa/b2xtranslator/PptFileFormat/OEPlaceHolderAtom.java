//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PlaceholderEnum;

public class OEPlaceHolderAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    /**
    * A signed integer that specifies an ID for the placeholder shape.
    * It SHOULD be unique among all PlaceholderAtom records contained in the corresponding slide.
    * The value 0xFFFFFFFF specifies that the corresponding shape is not a placeholder shape.
    */
    public int Position;
    /**
    * A PlaceholderEnum enumeration that specifies the type of the placeholder shape.
    */
    public PlaceholderEnum PlacementId = PlaceholderEnum.None;
    /**
    * A PlaceholderSize enumeration that specifies the preferred size of the placeholder shape.
    */
    public byte PlaceholderSize;
    public OEPlaceHolderAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.Position = this.Reader.ReadInt32();
        this.PlacementId = (PlaceholderEnum)this.Reader.ReadByte();
        this.PlaceholderSize = this.Reader.ReadByte();
        // Throw away additional junk
        this.Reader.ReadUInt16();
    }

    public String toString(uint depth) throws Exception {
        return String.Format("{0}\n{1}Position = {2}, PlacementId = {3}, PlaceholderSize = {4}", super.toString(depth), indentationForDepth(depth + 1), this.Position, this.PlacementId, this.PlaceholderSize);
    }

    public boolean isObjectPlaceholder() throws Exception {
        return this.PlacementId == PlaceholderEnum.Object;
    }

}


