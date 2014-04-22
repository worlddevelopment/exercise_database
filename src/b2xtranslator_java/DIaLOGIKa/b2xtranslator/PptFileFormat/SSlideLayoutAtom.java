//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PlaceholderEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideLayoutType;

public class SSlideLayoutAtom   
{
    /**
    * A SlideLayoutType that specifies a hint to the user interface which
    * slide layout exists on the corresponding slide.
    * 
    * A slide layout specifies the type and number of placeholder shapes
    * on a slide. A placeholder shape is specified as an OfficeArtSpContainer
    * ([MS-ODRAW] section 2.2.14) that contains a PlaceholderAtom record
    * with a pos field not equal to 0xFFFFFFFF. The placementId field of the
    * PlaceholderAtom record specifies the placeholder shape type.
    */
    public SlideLayoutType Geom = SlideLayoutType.TitleSlide;
    /**
    * An array of PlaceholderEnum enumeration values that specifies
    * a hint to the user interface which placeholder shapes exist on
    * the corresponding slide.
    * 
    * The count of items in the array MUST be 8.
    */
    public PlaceholderEnum[] PlaceholderTypes = new PlaceholderEnum[8];
    public SSlideLayoutAtom(BinaryReader reader) throws Exception {
        int geom = reader.ReadInt32();
        this.Geom = SlideLayoutType.values()[geom];
        for (int i = 0;i < 8;i++)
            this.PlaceholderTypes[i] = (PlaceholderEnum)reader.ReadByte();
    }

    public String toString() {
        try
        {
            String s = String.Join(", ", Array.<PlaceholderEnum, String>ConvertAll(this.PlaceholderTypes));
            return String.format(StringSupport.CSFmtStrToJFmtStr("SSlideLayoutAtom(Geom = {0}, PlaceholderTypes = [{1}])"),this.Geom,s);
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


