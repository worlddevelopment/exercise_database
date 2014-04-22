//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:14 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DocumentMapping;

public class FootnotesMapping  extends DocumentMapping 
{
    public FootnotesMapping(ConversionContext ctx) throws Exception {
        super(ctx, ctx.getDocx().getMainDocumentPart().getFootnotesPart());
        _ctx = ctx;
    }

    public void apply(WordDocument doc) throws Exception {
        _doc = doc;
        int id = 0;
        _writer.WriteStartElement("w", "footnotes", OpenXmlNamespaces.WordprocessingML);
        int cp = doc.FIB.ccpText;
        while (cp < (doc.FIB.ccpText + doc.FIB.ccpFtn - 2))
        {
            _writer.WriteStartElement("w", "footnote", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "id", OpenXmlNamespaces.WordprocessingML, String.valueOf(id));
            cp = writeParagraph(cp);
            _writer.writeEndElement();
            id++;
        }
        _writer.writeEndElement();
        _writer.Flush();
    }

}


