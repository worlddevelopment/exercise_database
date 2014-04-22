//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:47 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlContentTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;

public class EmbeddedObjectPart  extends OpenXmlPart 
{
    public enum ObjectType
    {
        Excel,
        Word,
        Powerpoint,
        Other
    }
    private DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType _format = DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType.Excel;
    public EmbeddedObjectPart(DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType format, OpenXmlPartContainer parent, int partIndex) throws Exception {
        super(parent, partIndex);
        _format = format;
    }

    public String getContentType() throws Exception {
        switch(_format)
        {
            case Excel: 
                return OpenXmlContentTypes.MSExcel;
            case Word: 
                return OpenXmlContentTypes.MSWord;
            case Powerpoint: 
                return OpenXmlContentTypes.MSPowerpoint;
            case Other: 
                return OpenXmlContentTypes.OleObject;
            default: 
                return OpenXmlContentTypes.OleObject;
        
        }
    }

    public boolean getHasDefaultContentType() throws Exception {
        return true;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.OleObject;
    }

    public String getTargetName() throws Exception {
        return "oleObject" + this.getPartIndex();
    }

    //public override string TargetDirectory { get { return "embeddings"; } }
    private String targetdirectory = "embeddings";
    public String getTargetDirectory() throws Exception {
        return targetdirectory;
    }

    public void setTargetDirectory(String value) throws Exception {
        targetdirectory = value;
    }

    public String getTargetExt() throws Exception {
        switch(_format)
        {
            case Excel: 
                return ".xls";
            case Word: 
                return ".doc";
            case Powerpoint: 
                return ".ppt";
            case Other: 
                return ".bin";
            default: 
                return ".bin";
        
        }
    }

}


