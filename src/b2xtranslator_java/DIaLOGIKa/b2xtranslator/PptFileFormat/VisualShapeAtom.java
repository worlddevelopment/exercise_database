//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ElementTypeEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeVisualElementEnum;

public class VisualShapeAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    //can be VisualSoundAtom, VisualShapeChartElementAtom, VisualShapeGeneralAtom
    public TimeVisualElementEnum type = TimeVisualElementEnum.Shape;
    public ElementTypeEnum refType = ElementTypeEnum.ShapeType;
    public uint shapeIdRef;
    public int data1;
    public int data2;
    public VisualShapeAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        type = (TimeVisualElementEnum)this.Reader.ReadInt32();
        refType = (ElementTypeEnum)this.Reader.ReadInt32();
        shapeIdRef = this.Reader.ReadUInt32();
        data1 = this.Reader.ReadInt32();
        data2 = this.Reader.ReadInt32();
    }

}


