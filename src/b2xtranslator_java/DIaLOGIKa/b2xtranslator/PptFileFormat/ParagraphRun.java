//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.PptFileFormat.GrColorAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphMask;

public class ParagraphRun   
{
    public static class TabStop   
    {
        public UInt16 Distance = new UInt16();
        public UInt16 Alignment = new UInt16();
        public TabStop(BinaryReader reader) throws Exception {
            this.Distance = reader.ReadUInt16();
            this.Alignment = reader.ReadUInt16();
        }
    
    }

    public long Length;
    public UInt16 IndentLevel = new UInt16();
    public ParagraphMask Mask = ParagraphMask.None;
    public TabStop[] TabStops;
    public boolean getBulletFlagsFieldPresent() throws Exception {
        return (this.Mask & ParagraphMask.BulletFlagsFieldExists) != 0;
    }

    public boolean getBulletCharPresent() throws Exception {
        return (this.Mask & ParagraphMask.BulletChar) != 0;
    }

    public boolean getBulletFontPresent() throws Exception {
        return (this.Mask & ParagraphMask.BulletFont) != 0;
    }

    public boolean getBulletHasFont() throws Exception {
        return (this.Mask & ParagraphMask.BulletHasFont) != 0;
    }

    public boolean getBulletSizePresent() throws Exception {
        return (this.Mask & ParagraphMask.BulletSize) != 0;
    }

    public boolean getBulletHasSize() throws Exception {
        return (this.Mask & ParagraphMask.BulletHasSize) != 0;
    }

    public boolean getBulletColorPresent() throws Exception {
        return (this.Mask & ParagraphMask.BulletColor) != 0;
    }

    public boolean getBulletHasColor() throws Exception {
        return (this.Mask & ParagraphMask.BulletHasColor) != 0;
    }

    public boolean getAlignmentPresent() throws Exception {
        return (this.Mask & ParagraphMask.Align) != 0;
    }

    public boolean getLineSpacingPresent() throws Exception {
        return (this.Mask & ParagraphMask.LineSpacing) != 0;
    }

    public boolean getSpaceBeforePresent() throws Exception {
        return (this.Mask & ParagraphMask.SpaceBefore) != 0;
    }

    public boolean getSpaceAfterPresent() throws Exception {
        return (this.Mask & ParagraphMask.SpaceAfter) != 0;
    }

    public boolean getLeftMarginPresent() throws Exception {
        return (this.Mask & ParagraphMask.LeftMargin) != 0;
    }

    public boolean getIndentPresent() throws Exception {
        return (this.Mask & ParagraphMask.Indent) != 0;
    }

    public boolean getDefaultTabSizePresent() throws Exception {
        return (this.Mask & ParagraphMask.DefaultTabSize) != 0;
    }

    public boolean getTabStopsPresent() throws Exception {
        return (this.Mask & ParagraphMask.TabStops) != 0;
    }

    public boolean getFontAlignPresent() throws Exception {
        return (this.Mask & ParagraphMask.FontAlign) != 0;
    }

    public boolean getLineBreakFlagsFieldPresent() throws Exception {
        return (this.Mask & ParagraphMask.WrapFlagsFieldExists) != 0;
    }

    public boolean getTextDirectionPresent() throws Exception {
        return (this.Mask & ParagraphMask.TextDirection) != 0;
    }

    public UInt16? BulletFlags = new UInt16?();
    public Character BulletChar;
    public UInt16? BulletTypefaceIdx = new UInt16?();
    public Short BulletSize;
    public GrColorAtom BulletColor;
    public Short Alignment;
    public Short LineSpacing;
    public Short SpaceBefore;
    public Short SpaceAfter;
    public Short LeftMargin;
    public Short Indent;
    public Short DefaultTabSize;
    public UInt16? FontAlign = new UInt16?();
    public UInt16? LineBreakFlags = new UInt16?();
    public UInt16? TextDirection = new UInt16?();
    public ParagraphRun(BinaryReader reader, boolean noIndentField) throws Exception {
        try
        {
            this.IndentLevel = noIndentField ? (ushort)0 : reader.ReadUInt16();
            this.Mask = (ParagraphMask)reader.ReadUInt32();
            // Note: These appear in Mask as well -- there they are true
            // when the flag differs from the Master style.
            // The actual value for the differing flags is stored here.
            // (TODO: This is still a guess. Verify.)
            if (this.getBulletFlagsFieldPresent())
                this.BulletFlags = reader.ReadUInt16();
             
            if (this.getBulletCharPresent())
                this.BulletChar = (char)reader.ReadUInt16();
             
            if (this.getBulletFontPresent())
                this.BulletTypefaceIdx = reader.ReadUInt16();
             
            if (this.getBulletSizePresent())
                this.BulletSize = reader.ReadInt16();
             
            if (this.getBulletColorPresent())
                this.BulletColor = new GrColorAtom(reader);
             
            if (this.getAlignmentPresent())
                this.Alignment = reader.ReadInt16();
             
            if (this.getLineSpacingPresent())
                this.LineSpacing = reader.ReadInt16();
             
            if (this.getSpaceBeforePresent())
                this.SpaceBefore = reader.ReadInt16();
             
            if (this.getSpaceAfterPresent())
                this.SpaceAfter = reader.ReadInt16();
             
            if (this.getLeftMarginPresent())
                this.LeftMargin = reader.ReadInt16();
             
            if (this.getIndentPresent())
                this.Indent = reader.ReadInt16();
             
            if (this.getDefaultTabSizePresent())
                this.DefaultTabSize = reader.ReadInt16();
             
            if (this.getTabStopsPresent())
            {
                UInt16 tabStopsCount = reader.ReadUInt16();
                this.TabStops = new TabStop[tabStopsCount];
                for (int i = 0;i < tabStopsCount;i++)
                {
                    this.TabStops[i] = new TabStop(reader);
                }
            }
             
            if (this.getFontAlignPresent())
                this.FontAlign = reader.ReadUInt16();
             
            if (this.getLineBreakFlagsFieldPresent())
                this.LineBreakFlags = reader.ReadUInt16();
             
            if (this.getTextDirectionPresent())
                this.TextDirection = reader.ReadUInt16();
             
        }
        catch (Exception e)
        {
            String s = e.toString();
        }
    
    }

    //if (this.TabStopsPresent)
    //{
    //    UInt16 tabStopsCount = reader.ReadUInt16();
    //    this.TabStops = new TabStop[tabStopsCount];
    //    for (int i = 0; i < tabStopsCount; i++)
    //    {
    //        this.TabStops[i] = new TabStop(reader);
    //    }
    //}
    public String toString(uint depth) throws Exception {
        StringBuilder result = new StringBuilder();
        String indent = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.indentationForDepth(depth);
        result.append(indent);
        result.append(super.toString());
        depth++;
        indent = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.indentationForDepth(depth);
        result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Length = {1}"),indent,this.Length));
        result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}IndentLevel = {1}"),indent,this.IndentLevel));
        result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Mask = {1}"),indent,this.Mask));
        if (this.BulletFlags != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletFlags = {1}"),indent,this.BulletFlags));
         
        if (this.BulletChar != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletChar = {1}"),indent,this.BulletChar));
         
        if (this.BulletTypefaceIdx != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletTypefaceIdx = {1}"),indent,this.BulletTypefaceIdx));
         
        if (this.BulletSize != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletSize = {1}"),indent,this.BulletSize));
         
        if (this.BulletColor != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletColor = {1}"),indent,this.BulletColor));
         
        if (this.Alignment != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Alignment = {1}"),indent,this.Alignment));
         
        if (this.LineSpacing != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}LineSpacing = {1}"),indent,this.LineSpacing));
         
        if (this.SpaceBefore != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}SpaceBefore = {1}"),indent,this.SpaceBefore));
         
        if (this.SpaceAfter != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}SpaceAfter = {1}"),indent,this.SpaceAfter));
         
        if (this.LeftMargin != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}LeftMargin = {1}"),indent,this.LeftMargin));
         
        if (this.Indent != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Indent = {1}"),indent,this.Indent));
         
        if (this.DefaultTabSize != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}DefaultTabSize = {1}"),indent,this.DefaultTabSize));
         
        if (this.FontAlign != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}FontAlign = {1}"),indent,this.FontAlign));
         
        if (this.LineBreakFlags != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}LineBreakFlags = {1}"),indent,this.LineBreakFlags));
         
        if (this.TextDirection != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}TextDirection = {1}"),indent,this.TextDirection));
         
        return result.toString();
    }

    public String toString() {
        try
        {
            return this.ToString(0);
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


