//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.RoundTripContentMasterInfo12;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import java.util.HashMap;

public class MainMaster  extends Slide 
{
    public HashMap<String,String> Layouts = new HashMap<String,String>();
    public MainMaster(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec : Children)
        {
            if (rec instanceof RoundTripContentMasterInfo12)
            {
                RoundTripContentMasterInfo12 info = (RoundTripContentMasterInfo12)rec;
                String xml = info.XmlDocumentElement.getOuterXml();
                xml = xml.replace("http://schemas.openxmlformats.org/drawingml/2006/3/main", "http://schemas.openxmlformats.org/drawingml/2006/main");
                if (info.XmlDocumentElement.getAttributes().get("type") != null)
                {
                    String title = info.XmlDocumentElement.getAttributes().get("type").getInnerText();
                    Layouts.put(title, xml);
                }
                 
            }
             
        }
    }

}


