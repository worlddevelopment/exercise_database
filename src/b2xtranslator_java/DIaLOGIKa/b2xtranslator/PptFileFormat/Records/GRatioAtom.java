//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;

public class GRatioAtom   
{
    public int Numer;
    public int Denom;
    public GRatioAtom(BinaryReader reader) throws Exception {
        this.Numer = reader.ReadInt32();
        this.Denom = reader.ReadInt32();
    }

    public String toString() {
        try
        {
            return String.format(StringSupport.CSFmtStrToJFmtStr("RatioAtom({0}, {1})"),this.Numer,this.Denom);
        }
        catch (RuntimeException __dummyCatchVar2)
        {
            throw __dummyCatchVar2;
        }
        catch (Exception __dummyCatchVar2)
        {
            throw new RuntimeException(__dummyCatchVar2);
        }
    
    }

}


