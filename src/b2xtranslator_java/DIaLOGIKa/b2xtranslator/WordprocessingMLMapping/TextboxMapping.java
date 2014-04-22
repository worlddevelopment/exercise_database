//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:21 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BreakDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.FooterPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.HeaderPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.MainDocumentPart;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DocumentMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TableInfo;

public class TextboxMapping  extends DocumentMapping 
{
    public static int TextboxCount = 0;
    private int _textboxIndex;
    public TextboxMapping(ConversionContext ctx, int textboxIndex, ContentPart targetpart, XmlWriter writer) throws Exception {
        super(ctx, targetpart, writer);
        TextboxCount++;
        _textboxIndex = textboxIndex;
    }

    public TextboxMapping(ConversionContext ctx, ContentPart targetpart, XmlWriter writer) throws Exception {
        super(ctx, targetpart, writer);
        TextboxCount++;
        _textboxIndex = TextboxCount - 1;
    }

    public void apply(WordDocument doc) throws Exception {
        _doc = doc;
        _writer.WriteStartElement("v", "textbox", OpenXmlNamespaces.VectorML);
        _writer.WriteStartElement("w", "txbxContent", OpenXmlNamespaces.WordprocessingML);
        int cp = 0;
        int cpEnd = 0;
        BreakDescriptor bkd = null;
        int txtbxSubdocStart = doc.FIB.ccpText + doc.FIB.ccpFtn + doc.FIB.ccpHdr + doc.FIB.ccpAtn + doc.FIB.ccpEdn;
        if (_targetPart.getClass() == MainDocumentPart.class)
        {
            cp = txtbxSubdocStart + doc.TextboxBreakPlex.CharacterPositions.get(_textboxIndex);
            cpEnd = txtbxSubdocStart + doc.TextboxBreakPlex.CharacterPositions.get(_textboxIndex + 1);
            bkd = (BreakDescriptor)doc.TextboxBreakPlex.Elements.get(_textboxIndex);
        }
         
        if (_targetPart.getClass() == HeaderPart.class || _targetPart.getClass() == FooterPart.class)
        {
            txtbxSubdocStart += doc.FIB.ccpTxbx;
            cp = txtbxSubdocStart + doc.TextboxBreakPlexHeader.CharacterPositions.get(_textboxIndex);
            cpEnd = txtbxSubdocStart + doc.TextboxBreakPlexHeader.CharacterPositions.get(_textboxIndex + 1);
            bkd = (BreakDescriptor)doc.TextboxBreakPlexHeader.Elements.get(_textboxIndex);
        }
         
        //convert the textbox text
        _lastValidPapx = _doc.AllPapxFkps.get(0).grppapx[0];
        while (cp < cpEnd)
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
        _writer.writeEndElement();
        _writer.writeEndElement();
        _writer.Flush();
    }

}


