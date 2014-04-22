//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:50 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.MicrosoftWordContentTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.MicrosoftWordRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;

public class VbaDataPart  extends ContentPart 
{
    public VbaDataPart(OpenXmlPartContainer parent) throws Exception {
        super(parent, 0);
    }

    public String getContentType() throws Exception {
        return MicrosoftWordContentTypes.VbaData;
    }

    public String getRelationshipType() throws Exception {
        return MicrosoftWordRelationshipTypes.VbaData;
    }

    public String getTargetName() throws Exception {
        return "vbaData";
    }

    public String getTargetExt() throws Exception {
        return ".xml";
    }

    public String getTargetDirectory() throws Exception {
        return "";
    }

}


