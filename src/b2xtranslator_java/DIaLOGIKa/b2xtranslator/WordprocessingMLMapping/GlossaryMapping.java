//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:14 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DocumentMapping;

public class GlossaryMapping  extends DocumentMapping 
{
    public GlossaryMapping(ConversionContext ctx, ContentPart targetPart) throws Exception {
        super(ctx, targetPart);
    }

    public void apply(WordDocument doc) throws Exception {
        _doc = doc;
        //start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("w", "glossaryDocument", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteStartElement("w", "docParts", OpenXmlNamespaces.WordprocessingML);
        for (int i = 0;i < _doc.AutoTextPlex.CharacterPositions.size() - 2;i++)
        {
            int cpStart = _doc.AutoTextPlex.CharacterPositions.get(i);
            int cpEnd = _doc.AutoTextPlex.CharacterPositions.get(i + 1);
            writeAutoTextDocPart(cpStart,cpEnd,i);
        }
        //end the document
        _writer.writeEndElement();
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

    private void writeAutoTextDocPart(int startCp, int endCp, int index) throws Exception {
        _writer.WriteStartElement("w", "docPart", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteStartElement("w", "docPartPr", OpenXmlNamespaces.WordprocessingML);
        //write the name
        _writer.WriteStartElement("w", "name", OpenXmlNamespaces.WordprocessingML);
        String name = _doc.AutoTextNames.Strings.get(index);
        if ((int)name.charAt(name.length() - 1) == 1)
        {
            name = name.Remove(name.length() - 1);
        }
         
        _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, name);
        _writer.writeEndElement();
        //write the category
        _writer.WriteStartElement("w", "category", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteStartElement("w", "name", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "General");
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "gallery", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "autoTxt");
        _writer.writeEndElement();
        _writer.writeEndElement();
        //write behaviors
        _writer.WriteStartElement("w", "behaviors", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteStartElement("w", "behavior", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "content");
        _writer.writeEndElement();
        _writer.writeEndElement();
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "docPartBody", OpenXmlNamespaces.WordprocessingML);
        writeParagraph(startCp,endCp,false);
        _writer.writeEndElement();
        _writer.writeEndElement();
    }

}


