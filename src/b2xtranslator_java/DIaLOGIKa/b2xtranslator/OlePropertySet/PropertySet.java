//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:46 AM
//

package DIaLOGIKa.b2xtranslator.OlePropertySet;

import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.NotImplementedException;
import DIaLOGIKa.b2xtranslator.OlePropertySet.ValueProperty;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class PropertySet  extends CSList<Object> 
{
    private long size;
    private long numProperties;
    private long[] identifiers;
    private long[] offsets;
    public PropertySet(VirtualStreamReader stream) throws Exception {
        long pos = Unsupported.throwUnsupported("stream.getBaseStream().Position");
        //read size and number of properties
        this.size = stream.readUInt32();
        this.numProperties = stream.readUInt32();
        //read the identifier and offsets
        this.identifiers = new long[this.numProperties];
        this.offsets = new long[this.numProperties];
        for (int i = 0;i < this.numProperties;i++)
        {
            this.identifiers[i] = stream.readUInt32();
            this.offsets[i] = stream.readUInt32();
        }
        for (int i = 0;i < this.numProperties;i++)
        {
            //read the properties
            if (this.identifiers[i] == 0)
            {
                throw new NotImplementedException("Dictionary Properties are not yet implemented!");
            }
            else
            {
                // dictionary property
                // value property
                this.add(new ValueProperty(stream));
            } 
        }
        // seek to the end of the property set to avoid crashes
        stream.getBaseStream().Seek(pos + size, SeekOrigin.Begin);
    }

}


