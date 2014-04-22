//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:57 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Collections.LCC.IEnumerator;
import CS2JNet.System.LCC.Disposable;

/**
* Regular containers are containers with Record children.
* 
* (There also is containers that only have a zipped XML payload.
*/
public class RegularContainer  extends DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record 
{
    public CSList<DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record> Children = new CSList<DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record>();
    public RegularContainer(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        uint readSize = 0;
        while (readSize < this.BodySize)
        {
            DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record child = DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record.readRecord(this.Reader);
            this.Children.add(child);
            child.setParentRecord(this);
            child.verifyReadToEnd();
            readSize += child.getTotalSize();
        }
    }

    public String toString(uint depth) throws Exception {
        StringBuilder result = new StringBuilder(super.toString(depth));
        depth++;
        if (this.Children.size() > 0)
        {
            result.AppendLine();
            result.append(indentationForDepth(depth));
            result.append("Children:");
        }
         
        for (DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record record : this.Children)
        {
            result.AppendLine();
            result.append(record.toString(depth + 1));
        }
        return result.toString();
    }

    public IEnumerator<DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record> getEnumerator() throws Exception {

        for (DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record recordChild : this.Children)
            for (DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record record : recordChild)
    
    }

}


