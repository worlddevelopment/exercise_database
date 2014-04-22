//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:23 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.VbaProjectPart;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExOleObjStgAtom;

public class VbaProjectMapping  extends AbstractOpenXmlMapping implements IMapping<ExOleObjStgAtom>
{
    private VbaProjectPart _targetPart;
    public VbaProjectMapping(VbaProjectPart targetPart) throws Exception {
        super(null);
        _targetPart = targetPart;
    }

    public void apply(ExOleObjStgAtom vbaProject) throws Exception {
        byte[] bytes = vbaProject.decompressData();
        _targetPart.getStream().write(bytes,0,bytes.length);
    }

}


