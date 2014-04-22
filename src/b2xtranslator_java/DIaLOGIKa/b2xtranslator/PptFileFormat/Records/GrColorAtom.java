//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;

/*        public override string ToString(uint depth)
        {
            return String.Format("{0}\n{1}RunLength = {2}",
                base.ToString(depth), IndentationForDepth(depth + 1), this.RunLength);
        }*/
public class GrColorAtom   
{
    public byte Red;
    public byte Green;
    public byte Blue;
    public byte Index;
    public boolean getIsSchemeColor() throws Exception {
        return this.Index != 0xFE;
    }

    public GrColorAtom(BinaryReader reader) throws Exception {
        this.Red = reader.ReadByte();
        this.Green = reader.ReadByte();
        this.Blue = reader.ReadByte();
        this.Index = reader.ReadByte();
    }

    public String toString() {
        try
        {
            return String.Format("GrColorAtom({0}, {1}, {2}): Index = {3}", this.Red, this.Green, this.Blue, this.Index);
        }
        catch (RuntimeException __dummyCatchVar6)
        {
            throw __dummyCatchVar6;
        }
        catch (Exception __dummyCatchVar6)
        {
            throw new RuntimeException(__dummyCatchVar6);
        }
    
    }

}


