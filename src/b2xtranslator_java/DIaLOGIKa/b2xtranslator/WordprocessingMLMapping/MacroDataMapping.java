//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:15 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import DIaLOGIKa.b2xtranslator.DocFileFormat.MacroData;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DocumentMapping;

public class MacroDataMapping  extends DocumentMapping 
{
    public MacroDataMapping(ConversionContext ctx) throws Exception {
        super(ctx, ctx.getDocx().getMainDocumentPart().getVbaProjectPart().getVbaDataPart());
        _ctx = ctx;
    }

    public void apply(WordDocument doc) throws Exception {
        VirtualStreamReader reader = new VirtualStreamReader(doc.Storage.getStream("\\Macros\\PROJECTwm"));
        _writer.WriteStartElement("wne", "vbaSuppData", OpenXmlNamespaces.MicrosoftWordML);
        _writer.WriteStartElement("wne", "mcds", OpenXmlNamespaces.MicrosoftWordML);
        for (int i = 0;i < doc.CommandTable.MacroDatas.size();i++)
        {
            _writer.WriteStartElement("wne", "mcd", OpenXmlNamespaces.MicrosoftWordML);
            MacroData mcd = doc.CommandTable.MacroDatas.get(i);
            if (doc.CommandTable.MacroNames != null)
            {
                _writer.WriteAttributeString("wne", "macroName", OpenXmlNamespaces.MicrosoftWordML, doc.CommandTable.MacroNames.get(mcd.ibst));
            }
             
            if (doc.CommandTable.CommandStringTable != null)
            {
                _writer.WriteAttributeString("wne", "name", OpenXmlNamespaces.MicrosoftWordML, doc.CommandTable.CommandStringTable.Strings[mcd.ibstName]);
            }
             
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        _writer.writeEndElement();
        reader.close();
        _writer.Flush();
    }

}


