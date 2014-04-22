//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.MicrosoftWordContentTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.MicrosoftWordRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.ToolbarsPart;

public class KeyMapCustomizationsPart  extends ContentPart 
{
    private ToolbarsPart _toolbars;
    public KeyMapCustomizationsPart(OpenXmlPartContainer parent) throws Exception {
        super(parent, 0);
    }

    public String getContentType() throws Exception {
        return MicrosoftWordContentTypes.KeyMapCustomization;
    }

    public String getRelationshipType() throws Exception {
        return MicrosoftWordRelationshipTypes.KeyMapCustomizations;
    }

    public String getTargetName() throws Exception {
        return "customizations";
    }

    public String getTargetDirectory() throws Exception {
        return "";
    }

    public ToolbarsPart getToolbarsPart() throws Exception {
        if (_toolbars == null)
        {
            _toolbars = new ToolbarsPart(this);
            this.AddPart(_toolbars);
        }
         
        return _toolbars;
    }

}


