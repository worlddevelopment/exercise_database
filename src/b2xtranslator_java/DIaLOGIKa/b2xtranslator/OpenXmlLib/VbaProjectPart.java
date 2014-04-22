//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:50 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.MicrosoftWordContentTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.MicrosoftWordRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.VbaDataPart;

public class VbaProjectPart  extends ContentPart 
{
    public VbaProjectPart(OpenXmlPartContainer parent) throws Exception {
        super(parent, 0);
    }

    public String getContentType() throws Exception {
        return MicrosoftWordContentTypes.VbaProject;
    }

    public String getRelationshipType() throws Exception {
        return MicrosoftWordRelationshipTypes.VbaProject;
    }

    public String getTargetName() throws Exception {
        return "vbaProject";
    }

    public String getTargetExt() throws Exception {
        return ".bin";
    }

    public String getTargetDirectory() throws Exception {
        return "";
    }

    protected VbaDataPart _vbaDataPart;
    public VbaDataPart getVbaDataPart() throws Exception {
        if (_vbaDataPart == null)
        {
            _vbaDataPart = this.AddPart(new VbaDataPart(this));
        }
         
        return _vbaDataPart;
    }

}


