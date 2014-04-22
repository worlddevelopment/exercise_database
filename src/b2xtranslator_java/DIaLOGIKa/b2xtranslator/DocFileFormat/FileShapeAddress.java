//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:01 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class FileShapeAddress  extends ByteStructure 
{
    public enum AnchorType
    {
        margin,
        page,
        text
    }
    /**
    * Shape Identifier. Used in conjunction with the office art data
    * (found via fcDggInfo in the FIB) to find the actual data for this shape.
    */
    public int spid;
    /**
    * Left of rectangle enclosing shape relative to the origin of the shape
    */
    public int xaLeft;
    /**
    * Top of rectangle enclosing shape relative to the origin of the shape
    */
    public int yaTop;
    /**
    * Right of rectangle enclosing shape relative to the origin of the shape
    */
    public int xaRight;
    /**
    * Bottom of the rectangle enclosing shape relative to the origin of the shape
    */
    public int yaBottom;
    /**
    * true in the undo doc when shape is from the header doc
    * false otherwise (undefined when not in the undo doc)
    */
    public boolean fHdr;
    /**
    * X position of shape relative to anchor CP
    * 0 relative to page margin
    * 1 relative to top of page
    * 2 relative to text (column for horizontal text; paragraph for vertical text)
    * 3 reserved for future use
    */
    public AnchorType bx = AnchorType.margin;
    /**
    * Y position of shape relative to anchor CP
    * 0 relative to page margin
    * 1 relative to top of page
    * 2 relative to text (column for horizontal text; paragraph for vertical text)
    * 3 reserved for future use
    */
    public AnchorType by = AnchorType.margin;
    /**
    * Text wrapping mode 
    * 0 like 2, but doesn�t require absolute object 
    * 1 no text next to shape 
    * 2 wrap around absolute object 
    * 3 wrap as if no object present 
    * 4 wrap tightly around object 
    * 5 wrap tightly, but allow holes 
    * 6-15 reserved for future use
    */
    public UInt16 wr = new UInt16();
    /**
    * Text wrapping mode type (valid only for wrapping modes 2 and 4)
    * 0 wrap both sides 
    * 1 wrap only on left 
    * 2 wrap only on right 
    * 3 wrap only on largest side
    */
    public UInt16 wrk = new UInt16();
    /**
    * When set, temporarily overrides bx, by,
    * forcing the xaLeft, xaRight, yaTop, and yaBottom fields
    * to all be page relative.
    */
    public boolean fRcaSimple;
    /**
    * true: shape is below text 
    * false: shape is above text
    */
    public boolean fBelowText;
    /**
    * true: anchor is locked 
    * fasle: anchor is not locked
    */
    public boolean fAnchorLock;
    /**
    * Count of textboxes in shape (undo doc only)
    */
    public int cTxbx;
    /**
    * @param reader
    */
    public FileShapeAddress(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        this.spid = _reader.readInt32();
        this.xaLeft = _reader.readInt32();
        this.yaTop = _reader.readInt32();
        this.xaRight = _reader.readInt32();
        this.yaBottom = _reader.readInt32();
        UInt16 flag = _reader.readUInt16();
        this.fHdr = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x0001);
        this.bx = (AnchorType)DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToInt(flag, 0x0006);
        this.by = (AnchorType)DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToInt(flag, 0x0018);
        this.wr = (UInt16)DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToInt(flag, 0x01E0);
        this.wrk = (UInt16)DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToInt(flag, 0x1E00);
        this.fRcaSimple = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x2000);
        this.fBelowText = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x4000);
        this.fAnchorLock = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x8000);
        this.cTxbx = _reader.readInt32();
    }

}


