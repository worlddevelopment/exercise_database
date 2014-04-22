//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideLayoutPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationMLContentTypes;

public class SlideMasterPart  extends ContentPart 
{
    protected static int _slideLayoutCounter;
    public SlideMasterPart(OpenXmlPartContainer parent, int partIndex) throws Exception {
        super(parent, partIndex);
    }

    public String getContentType() throws Exception {
        return PresentationMLContentTypes.SlideMaster;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.SlideMaster;
    }

    public String getTargetName() throws Exception {
        return "slideMaster" + this.PartIndex;
    }

    public String getTargetDirectory() throws Exception {
        return "slideMasters";
    }

    public SlideLayoutPart addSlideLayoutPart() throws Exception {
        SlideLayoutPart part = new SlideLayoutPart(this, ++_slideLayoutCounter);
        part.ReferencePart(this);
        return this.AddPart(part);
    }

}


