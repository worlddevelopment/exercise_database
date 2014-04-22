//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:32 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.PathSegment;

public class PathParser   
{
    private CSList<Point> __Values;
    public CSList<Point> getValues() {
        return __Values;
    }

    public void setValues(CSList<Point> value) {
        __Values = value;
    }

    private CSList<PathSegment> __Segments;
    public CSList<PathSegment> getSegments() {
        return __Segments;
    }

    public void setSegments(CSList<PathSegment> value) {
        __Segments = value;
    }

    public UInt16 cbElemVert = new UInt16();
    public PathParser(byte[] pSegmentInfo, byte[] pVertices) throws Exception {
        // parse the segments
        this.setSegments(new CSList<PathSegment>());
        if (pSegmentInfo.length > 0)
        {
            UInt16 nElemsSeg = System.BitConverter.ToUInt16(pSegmentInfo, 0);
            UInt16 nElemsAllocSeg = System.BitConverter.ToUInt16(pSegmentInfo, 2);
            UInt16 cbElemSeg = System.BitConverter.ToUInt16(pSegmentInfo, 4);
            for (int i = 6;i < pSegmentInfo.length;i += 2)
            {
                this.getSegments().add(new PathSegment(System.BitConverter.ToUInt16(pSegmentInfo, i)));
            }
        }
         
        // parse the values
        this.setValues(new CSList<Point>());
        UInt16 nElemsVert = System.BitConverter.ToUInt16(pVertices, 0);
        UInt16 nElemsAllocVert = System.BitConverter.ToUInt16(pVertices, 2);
        cbElemVert = System.BitConverter.ToUInt16(pVertices, 4);
        if (cbElemVert == 0xfff0)
            cbElemVert = 4;
         
        for (int i = 6;i < pVertices.length;i += cbElemVert)
        {
            this.getValues().add(new Point(System.BitConverter.ToInt16(pVertices, i), System.BitConverter.ToInt16(pVertices, i + cbElemVert / 2)));
        }
    }

}


