//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;

public class SSlideLayoutAtom   
{
    public int Geom;
    public byte[] PlaceholderIds = new byte[8];
    public SSlideLayoutAtom(BinaryReader reader) throws Exception {
        this.Geom = reader.ReadInt32();
        for (int i = 0;i < 8;i++)
            this.PlaceholderIds[i] = reader.ReadByte();
    }

    public String toString() {
        try
        {
            String s = String.Join(", ", Array.<Byte, String>ConvertAll(this.PlaceholderIds));
            return String.format(StringSupport.CSFmtStrToJFmtStr("SSlideLayoutAtom(Geom = {0}, PlaceholderIds = [{1}])"),this.Geom,s);
        }
        catch (RuntimeException __dummyCatchVar3)
        {
            throw __dummyCatchVar3;
        }
        catch (Exception __dummyCatchVar3)
        {
            throw new RuntimeException(__dummyCatchVar3);
        }
    
    }

}


