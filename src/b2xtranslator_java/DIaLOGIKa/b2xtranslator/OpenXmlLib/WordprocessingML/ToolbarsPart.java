//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.MicrosoftWordContentTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.MicrosoftWordRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;

public class ToolbarsPart  extends ContentPart 
{
    public ToolbarsPart(OpenXmlPartContainer parent) throws Exception {
        super(parent, 0);
    }

    public String getContentType() throws Exception {
        return MicrosoftWordContentTypes.Toolbars;
    }

    public String getRelationshipType() throws Exception {
        return MicrosoftWordRelationshipTypes.Toolbars;
    }

    public String getTargetName() throws Exception {
        return "attachedToolbars";
    }

    public String getTargetExt() throws Exception {
        return ".bin";
    }

    public String getTargetDirectory() throws Exception {
        return "";
    }

}


