//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.MainDocumentPart;

public class GlossaryPart  extends MainDocumentPart 
{
    public GlossaryPart(OpenXmlPartContainer parent, String contentType) throws Exception {
        super(parent, contentType);
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.GlossaryDocument;
    }

    public String getTargetDirectory() throws Exception {
        return "glossary";
    }

}


