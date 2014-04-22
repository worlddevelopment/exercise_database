//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideMasterPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationMLContentTypes;

public class NotesMasterPart  extends SlideMasterPart 
{
    public NotesMasterPart(OpenXmlPartContainer parent, int partIndex) throws Exception {
        super(parent, partIndex);
    }

    public String getContentType() throws Exception {
        return PresentationMLContentTypes.NotesMaster;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.NotesMaster;
    }

    public String getTargetName() throws Exception {
        return "notesMaster" + this.PartIndex;
    }

    public String getTargetDirectory() throws Exception {
        return "notesMasters";
    }

}


