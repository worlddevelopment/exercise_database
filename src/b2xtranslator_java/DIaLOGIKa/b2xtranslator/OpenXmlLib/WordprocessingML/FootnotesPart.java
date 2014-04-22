//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingMLContentTypes;

public class FootnotesPart  extends ContentPart 
{
    public FootnotesPart(OpenXmlPartContainer parent) throws Exception {
        super(parent, 0);
    }

    public String getContentType() throws Exception {
        return WordprocessingMLContentTypes.Footnotes;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.Footnotes;
    }

    public String getTargetName() throws Exception {
        return "footnotes";
    }

    public String getTargetDirectory() throws Exception {
        return "";
    }

}


