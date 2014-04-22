//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:56 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.AnnotationReferenceDescriptorExtra;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class AnnotationReferenceExtraTable  extends CSList<AnnotationReferenceDescriptorExtra> 
{
    private static final int ARTDPost10_LENGTH = 16;
    public AnnotationReferenceExtraTable(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
        if (fib.nFib >= FibVersion.Fib2002)
        {
            tableStream.Seek((long)fib.fcAtrdExtra, System.IO.SeekOrigin.Begin);
            VirtualStreamReader reader = new VirtualStreamReader(tableStream);
            int n = (int)fib.lcbAtrdExtra / ARTDPost10_LENGTH;
            for (int i = 0;i < n;i++)
            {
                //read the n ATRDPost10 structs
                this.add(new AnnotationReferenceDescriptorExtra(reader,ARTDPost10_LENGTH));
            }
        }
         
    }

}


