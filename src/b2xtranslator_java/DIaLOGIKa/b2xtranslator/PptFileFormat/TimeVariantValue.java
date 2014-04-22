//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeVariantTypeEnum;

public class TimeVariantValue  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    //can be TimeVariantBool, TimeVariantInt, TimeVariantFloat or TimeVariantString
    public TimeVariantTypeEnum type = TimeVariantTypeEnum.Bool;
    public Integer intValue = null;
    public Boolean boolValue = null;
    public Float floatValue = null;
    public String stringValue = null;
    public TimeVariantValue(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        type = (TimeVariantTypeEnum)this.Reader.ReadByte();
        switch(type)
        {
            case Bool: 
                boolValue = this.Reader.ReadBoolean();
                break;
            case Float: 
                floatValue = this.Reader.ReadSingle();
                break;
            case Int: 
                intValue = this.Reader.ReadInt32();
                break;
            case String: 
                stringValue = Encoding.Unicode.GetString(this.Reader.ReadBytes((int)size - 1));
                stringValue = stringValue.replace("\0", "");
                break;
        
        }
    }

}


