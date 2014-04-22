//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ITextDataRecord;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextHeaderAtom;

public class TextStyleAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record implements ITextDataRecord
{
    public CSList<ParagraphRun> PRuns = new CSList<ParagraphRun>();
    public CSList<CharacterRun> CRuns = new CSList<CharacterRun>();
    public TextStyleAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
    }

    public String toString(uint depth) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString(depth));
        depth++;
        String indent = indentationForDepth(depth);
        sb.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Paragraph Runs:"),indent));
        for (ParagraphRun pr : this.PRuns)
            sb.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}"),pr.toString(depth + 1)));
        sb.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Character Runs:"),indent));
        for (CharacterRun cr : this.CRuns)
            sb.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}"),cr.toString(depth + 1)));
        return sb.toString();
    }

    public void afterTextHeaderSet() throws Exception {
    }

    private TextHeaderAtom _TextHeaderAtom;
    public TextHeaderAtom getTextHeaderAtom() throws Exception {
        return this._TextHeaderAtom;
    }

    public void setTextHeaderAtom(TextHeaderAtom value) throws Exception {
        this._TextHeaderAtom = value;
        this.afterTextHeaderSet();
    }

}


