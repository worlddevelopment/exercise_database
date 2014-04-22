//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:32 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class PathSegment   
{
    public enum SegmentType
    {
        msopathLineTo,
        msopathCurveTo,
        msopathMoveTo,
        msopathClose,
        msopathEnd,
        msopathEscape,
        msopathClientEscape,
        msopathInvalid
    }
    private SegmentType __Type = SegmentType.msopathLineTo;
    public SegmentType getType() {
        return __Type;
    }

    public void setType(SegmentType value) {
        __Type = value;
    }

    private int __Count;
    public int getCount() {
        return __Count;
    }

    public void setCount(int value) {
        __Count = value;
    }

    private int __EscapeCode;
    public int getEscapeCode() {
        return __EscapeCode;
    }

    public void setEscapeCode(int value) {
        __EscapeCode = value;
    }

    private int __VertexCount;
    public int getVertexCount() {
        return __VertexCount;
    }

    public void setVertexCount(int value) {
        __VertexCount = value;
    }

    public PathSegment(UInt16 segment) throws Exception {
        this.setType((SegmentType)Utils.BitmaskToInt(segment, 0xE000));
        if (getType() == SegmentType.msopathEscape)
        {
            this.setEscapeCode(Utils.BitmaskToInt(segment, 0x1F00));
            this.setVertexCount(Utils.BitmaskToInt(segment, 0x00FF));
        }
        else
        {
            this.setCount(Utils.BitmaskToInt(segment, 0x1FFF));
        } 
    }

}


