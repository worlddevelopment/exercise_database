//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:15 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.JavaSupport.Collections.Generic.LCC.CollectionSupport;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SectionPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DocumentMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.SectionPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TableInfo;

public class MainDocumentMapping  extends DocumentMapping 
{
    public MainDocumentMapping(ConversionContext ctx, ContentPart targetPart) throws Exception {
        super(ctx, targetPart);
    }

    public void apply(WordDocument doc) throws Exception {
        _doc = doc;
        //start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("w", "document", OpenXmlNamespaces.WordprocessingML);
        //write namespaces
        _writer.WriteAttributeString("xmlns", "v", null, OpenXmlNamespaces.VectorML);
        _writer.WriteAttributeString("xmlns", "o", null, OpenXmlNamespaces.Office);
        _writer.WriteAttributeString("xmlns", "w10", null, OpenXmlNamespaces.OfficeWord);
        _writer.WriteAttributeString("xmlns", "r", null, OpenXmlNamespaces.Relationships);
        _writer.WriteStartElement("w", "body", OpenXmlNamespaces.WordprocessingML);
        //convert the document
        _lastValidPapx = _doc.AllPapxFkps.get(0).grppapx[0];
        int cp = 0;
        while (cp < doc.FIB.ccpText)
        {
            int fc = _doc.PieceTable.FileCharacterPositions.get(cp);
            ParagraphPropertyExceptions papx = findValidPapx(fc);
            TableInfo tai = new TableInfo(papx);
            if (tai.fInTable)
            {
                //this PAPX is for a table
                cp = writeTable(cp,tai.iTap);
            }
            else
            {
                //this PAPX is for a normal paragraph
                cp = writeParagraph(cp);
            } 
        }
        //write the section properties of the body with the last SEPX
        int lastSepxCp = 0;
        for (int sepxCp : CollectionSupport.mk(_doc.AllSepx.keySet()))
        {
            lastSepxCp = sepxCp;
        }
        SectionPropertyExceptions lastSepx = _doc.AllSepx.get(lastSepxCp);
        lastSepx.Convert(new SectionPropertiesMapping(_writer,_ctx,_sectionNr));
        //end the document
        _writer.writeEndElement();
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

}


