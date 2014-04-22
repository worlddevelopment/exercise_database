//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Record;

public class ChildAnchor  extends Record 
{
    /**
    * Rectangle that describe sthe bounds of the anchor
    */
    public Rectangle rcgBounds = new Rectangle();
    public int Left;
    public int Top;
    public int Right;
    public int Bottom;
    public ChildAnchor(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        Left = this.Reader.ReadInt32();
        Top = this.Reader.ReadInt32();
        Right = this.Reader.ReadInt32();
        Bottom = this.Reader.ReadInt32();
        this.rcgBounds = new Rectangle(new Point(Left, Top), new Size((Right - Left), (Bottom - Top)));
    }

}


