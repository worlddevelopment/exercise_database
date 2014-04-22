//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextCFExceptionAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextMasterStyleAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextPFExceptionAtom;

public class Environment  extends RegularContainer 
{
    public Environment(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec : Children)
        {
            long __dummyScrutVar0 = rec.TypeCode;
            if (__dummyScrutVar0.equals(0x7d5))
            {
            }
            else //FontCollectionContainer
            if (__dummyScrutVar0.equals(0xfa3))
            {
                //TextMasterStyleAtom
                TextMasterStyleAtom a = (TextMasterStyleAtom)rec;
            }
            else if (__dummyScrutVar0.equals(0xfa4))
            {
                //TextCFExceptionAtom
                TextCFExceptionAtom ce = (TextCFExceptionAtom)rec;
            }
            else if (__dummyScrutVar0.equals(0xfa5))
            {
                //TextPFExceptionAtom
                TextPFExceptionAtom e = (TextPFExceptionAtom)rec;
            }
            else if (__dummyScrutVar0.equals(0xfa9))
            {
            }
            else //TextSIEExceptionAtom
            if (__dummyScrutVar0.equals(0xfc8))
            {
            }
            else
            {
            }      
        }
    }

}


//KinsokuContainer