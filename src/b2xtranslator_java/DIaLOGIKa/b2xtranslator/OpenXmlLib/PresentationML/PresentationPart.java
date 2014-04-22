//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.HandoutMasterPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.NotePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.NotesMasterPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideMasterPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlidePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ThemePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.VbaProjectPart;

public class PresentationPart  extends ContentPart 
{
    public CSList<SlideMasterPart> SlideMasterParts = new CSList<SlideMasterPart>();
    public CSList<NotesMasterPart> NotesMasterParts = new CSList<NotesMasterPart>();
    public CSList<HandoutMasterPart> HandoutMasterParts = new CSList<HandoutMasterPart>();
    protected static int _slideMasterCounter = 0;
    protected static int _notesMasterCounter = 0;
    protected static int _handoutMasterCounter = 0;
    protected static int _slideCounter = 0;
    protected static int _noteCounter = 0;
    protected static int _themeCounter = 0;
    protected static int _mediaCounter = 0;
    private String _type;
    protected VbaProjectPart _vbaProjectPart;
    public PresentationPart(OpenXmlPartContainer parent, String contentType) throws Exception {
        super(parent, 0);
        _type = contentType;
    }

    public String getContentType() throws Exception {
        return this._type;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.OfficeDocument;
    }

    public String getTargetName() throws Exception {
        return "presentation";
    }

    public String getTargetDirectory() throws Exception {
        return "ppt";
    }

    public SlideMasterPart addSlideMasterPart() throws Exception {
        SlideMasterPart part = new SlideMasterPart(this, ++_slideMasterCounter);
        this.SlideMasterParts.add(part);
        return this.AddPart(part);
    }

    public SlideMasterPart addNotesMasterPart() throws Exception {
        NotesMasterPart part = new NotesMasterPart(this, ++_notesMasterCounter);
        this.NotesMasterParts.add(part);
        return this.AddPart(part);
    }

    public SlideMasterPart addHandoutMasterPart() throws Exception {
        HandoutMasterPart part = new HandoutMasterPart(this, ++_handoutMasterCounter);
        this.HandoutMasterParts.add(part);
        return this.AddPart(part);
    }

    public SlidePart addSlidePart() throws Exception {
        return this.AddPart(new SlidePart(this, ++_slideCounter));
    }

    public SlidePart addNotePart() throws Exception {
        return this.AddPart(new NotePart(this, ++_noteCounter));
    }

    public ThemePart addThemePart() throws Exception {
        return this.AddPart(new ThemePart(this, ++_themeCounter));
    }

    public VbaProjectPart getVbaProjectPart() throws Exception {
        if (_vbaProjectPart == null)
        {
            _vbaProjectPart = this.AddPart(new VbaProjectPart(this));
        }
         
        return _vbaProjectPart;
    }

}


//public AppPropertiesPart AddAppPart()
//{
//    return this.AddPart(new AppPropertiesPart(this));
//}
//public MediaPart AddMediaPart()
//{
//    return this.AddPart(new MediaPart(this, ++_mediaCounter));
//}