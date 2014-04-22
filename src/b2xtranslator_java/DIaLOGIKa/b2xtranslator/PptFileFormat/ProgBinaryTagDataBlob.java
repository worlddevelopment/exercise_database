//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:57 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;

public class ProgBinaryTagDataBlob  extends RegularContainer 
{
    public ProgBinaryTagDataBlob(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec : Children)
        {
            long __dummyScrutVar0 = rec.TypeCode;
            if (__dummyScrutVar0.equals(0x2b00))
            {
            }
            else //HashCode10Atom
            if (__dummyScrutVar0.equals(0x2b02))
            {
            }
            else //BuildListContainer
            if (__dummyScrutVar0.equals(0x36b1))
            {
            }
            else //DocToolbarStates10Atom
            if (__dummyScrutVar0.equals(0x40d))
            {
            }
            else //GridSpacing10Atom
            if (__dummyScrutVar0.equals(0x7f8))
            {
            }
            else //BlipCollection9
            if (__dummyScrutVar0.equals(0x2eeb))
            {
            }
            else //SlideTime10Atom
            if (__dummyScrutVar0.equals(0xf144))
            {
            }
            else //ExtTimeNodeContainer
            if (__dummyScrutVar0.equals(0xfad))
            {
            }
            else
            {
            }        
        }
    }

}


//TextMasterStyle9Atom