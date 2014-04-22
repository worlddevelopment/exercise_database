//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:04 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.FileShapeAddress;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import java.util.HashMap;

public class OfficeDrawingTable  extends HashMap<Integer,FileShapeAddress> 
{
    public enum OfficeDrawingTableType
    {
        Header,
        MainDocument
    }
    private static final int FSPA_LENGTH = 26;
    public OfficeDrawingTable(WordDocument doc, OfficeDrawingTableType type) throws Exception {
        VirtualStreamReader reader = new VirtualStreamReader(doc.TableStream);
        //FSPA has size 26 + 4 byte for the FC = 30 byte
        int n = 0;
        long startFc = 0;
        if (type == OfficeDrawingTableType.MainDocument)
        {
            startFc = doc.FIB.fcPlcSpaMom;
            n = ((int)(Math.floor((double)doc.FIB.lcbPlcSpaMom / 30)));
        }
        else if (type == OfficeDrawingTableType.Header)
        {
            startFc = doc.FIB.fcPlcSpaHdr;
            n = ((int)(Math.floor((double)doc.FIB.lcbPlcSpaHdr / 30)));
        }
          
        //there are n+1 FCs ...
        doc.TableStream.Seek(startFc, System.IO.SeekOrigin.Begin);
        int[] fcs = new int[n + 1];
        for (int i = 0;i < (n + 1);i++)
        {
            fcs[i] = reader.readInt32();
        }
        for (int i = 0;i < n;i++)
        {
            //followed by n FSPAs
            FileShapeAddress fspa = null;
            if (type == OfficeDrawingTableType.Header)
            {
            }
            else //fspa = new FileShapeAddress(reader, doc.OfficeArtContent);
            if (type == OfficeDrawingTableType.MainDocument)
            {
            }
              
            //fspa = new FileShapeAddress(reader, doc.OfficeArtContent);
            this.put(fcs[i], fspa);
        }
    }

}


