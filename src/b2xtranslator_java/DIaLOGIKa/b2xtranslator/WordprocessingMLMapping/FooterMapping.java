//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:14 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterRange;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.FooterPart;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DocumentMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TableInfo;

public class FooterMapping  extends DocumentMapping 
{
    private CharacterRange _ftr;
    public FooterMapping(ConversionContext ctx, FooterPart part, CharacterRange ftr) throws Exception {
        super(ctx, part);
        _ftr = ftr;
    }

    public void apply(WordDocument doc) throws Exception {
        _doc = doc;
        _writer.WriteStartDocument();
        _writer.WriteStartElement("w", "ftr", OpenXmlNamespaces.WordprocessingML);
        //convert the footer text
        _lastValidPapx = _doc.AllPapxFkps.get(0).grppapx[0];
        int cp = _ftr.CharacterPosition;
        int cpMax = _ftr.CharacterPosition + _ftr.CharacterCount;
        //the CharacterCount of the footers also counts the guard paragraph mark.
        //this additional paragraph mark shall not be converted.
        cpMax--;
        while (cp < cpMax)
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
        _writer.WriteEndDocument();
        _writer.Flush();
    }

}


