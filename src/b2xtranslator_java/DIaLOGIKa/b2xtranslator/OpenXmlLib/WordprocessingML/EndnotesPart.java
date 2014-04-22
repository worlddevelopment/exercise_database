//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:50 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingMLContentTypes;

public class EndnotesPart  extends ContentPart 
{
    public EndnotesPart(OpenXmlPartContainer parent) throws Exception {
        super(parent, 0);
    }

    public String getContentType() throws Exception {
        return WordprocessingMLContentTypes.Endnotes;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.Endnotes;
    }

    public String getTargetName() throws Exception {
        return "endnotes";
    }

    public String getTargetDirectory() throws Exception {
        return "";
    }

}


