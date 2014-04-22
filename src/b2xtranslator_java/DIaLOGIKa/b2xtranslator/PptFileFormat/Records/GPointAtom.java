//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;

public class GPointAtom   
{
    public int X;
    public int Y;
    public GPointAtom(BinaryReader reader) throws Exception {
        this.X = reader.ReadInt32();
        this.Y = reader.ReadInt32();
    }

    public String toString() {
        try
        {
            return String.format(StringSupport.CSFmtStrToJFmtStr("PointAtom({0}, {1})"),this.X,this.Y);
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

}


