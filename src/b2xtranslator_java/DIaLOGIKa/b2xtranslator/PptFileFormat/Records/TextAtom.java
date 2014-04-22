//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;

public class TextAtom  extends DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record 
{
    public String Text;
    public TextAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance, Encoding encoding) throws Exception {
        super(_reader, size, typeCode, version, instance);
        byte[] bytes = new byte[size];
        this.Reader.Read(bytes, 0, (int)size);
        this.Text = new String(encoding.GetChars(bytes)) + "\n";
    }

    public String toString(uint depth) throws Exception {
        return String.format(StringSupport.CSFmtStrToJFmtStr("{0}\n{1}Text = {2}"),super.toString(depth),indentationForDepth(depth + 1),this.Text);
    }

}


