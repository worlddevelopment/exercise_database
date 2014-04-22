//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingMLContentTypes;

public class SettingsPart  extends OpenXmlPart 
{
    public SettingsPart(OpenXmlPartContainer parent) throws Exception {
        super(parent, 0);
    }

    public String getContentType() throws Exception {
        return WordprocessingMLContentTypes.Settings;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.Settings;
    }

    public String getTargetName() throws Exception {
        return "settings";
    }

    public String getTargetDirectory() throws Exception {
        return "";
    }

}


