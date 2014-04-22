//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.NotImplementedException;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.ClientTextbox;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.GrColorAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.TextAtom;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class StyleTextPropAtom  extends DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record 
{
    public enum ParagraphMask
    {
        None,
        HasCustomBullet,
        HasCustomBulletTypeface,
        HasCustomBulletColor,
        HasCustomBulletSize,
        BulletFlagsFieldPresent,
        BulletTypefacePresent,
        BulletSizePresent,
        BulletColorPresent,
        BulletCharPresent,
        LeftMarginPresent,
        // Bit 9 is unused
        IndentPresent,
        AlignmentPresent,
        LineSpacingPresent,
        SpaceBeforePresent,
        SpaceAfterPresent,
        DefaultTabSizePresent,
        BaseLinePresent,
        HasCustomCharWrap,
        HasCustomWordWrap,
        HasCustomOverflow,
        LineBreakFlagsFieldPresent,
        TabStopsPresent,
        TextDirectionPresent
    }
    public static class ParagraphRun   
    {
        public long Length;
        public UInt16 IndentLevel = new UInt16();
        public ParagraphMask Mask = ParagraphMask.None;
        public boolean getBulletFlagsFieldPresent() throws Exception {
            return (this.Mask & ParagraphMask.BulletFlagsFieldPresent) != 0;
        }

        public boolean getBulletCharPresent() throws Exception {
            return (this.Mask & ParagraphMask.BulletCharPresent) != 0;
        }

        public boolean getBulletTypefacePresent() throws Exception {
            return (this.Mask & ParagraphMask.BulletTypefacePresent) != 0;
        }

        public boolean getBulletSizePresent() throws Exception {
            return (this.Mask & ParagraphMask.BulletSizePresent) != 0;
        }

        public boolean getBulletColorPresent() throws Exception {
            return (this.Mask & ParagraphMask.BulletColorPresent) != 0;
        }

        public boolean getAlignmentPresent() throws Exception {
            return (this.Mask & ParagraphMask.AlignmentPresent) != 0;
        }

        public boolean getLineSpacingPresent() throws Exception {
            return (this.Mask & ParagraphMask.LineSpacingPresent) != 0;
        }

        public boolean getSpaceBeforePresent() throws Exception {
            return (this.Mask & ParagraphMask.SpaceBeforePresent) != 0;
        }

        public boolean getSpaceAfterPresent() throws Exception {
            return (this.Mask & ParagraphMask.SpaceAfterPresent) != 0;
        }

        public boolean getLeftMarginPresent() throws Exception {
            return (this.Mask & ParagraphMask.LeftMarginPresent) != 0;
        }

        public boolean getIndentPresent() throws Exception {
            return (this.Mask & ParagraphMask.IndentPresent) != 0;
        }

        public boolean getDefaultTabSizePresent() throws Exception {
            return (this.Mask & ParagraphMask.DefaultTabSizePresent) != 0;
        }

        public boolean getTabStopsPresent() throws Exception {
            return (this.Mask & ParagraphMask.TabStopsPresent) != 0;
        }

        public boolean getBaseLinePresent() throws Exception {
            return (this.Mask & ParagraphMask.BaseLinePresent) != 0;
        }

        public boolean getLineBreakFlagsFieldPresent() throws Exception {
            return (this.Mask & ParagraphMask.LineBreakFlagsFieldPresent) != 0;
        }

        public boolean getTextDirectionPresent() throws Exception {
            return (this.Mask & ParagraphMask.TextDirectionPresent) != 0;
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
        public UInt16? BaseLine = new UInt16?();
        public UInt16? LineBreakFlags = new UInt16?();
        public UInt16? TextDirection = new UInt16?();
        public ParagraphRun(BinaryReader reader) throws Exception {
            this.Length = reader.ReadUInt32();
            this.IndentLevel = reader.ReadUInt16();
            this.Mask = (ParagraphMask)reader.ReadUInt32();
            // Note: These appear in Mask as well -- there they are true
            // when the flag differs from the Master style.
            // The actual value for the differing flags is stored here.
            // (TODO: This is still a guess. Verify.)
            if (this.getBulletFlagsFieldPresent())
                this.BulletFlags = reader.ReadUInt16();
             
            if (this.getBulletCharPresent())
                this.BulletChar = (char)reader.ReadUInt16();
             
            if (this.getBulletTypefacePresent())
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
                if (tabStopsCount != 0)
                    throw new NotImplementedException("Tab stop reading not yet implemented");
                 
            }
             
            // TODO
            if (this.getBaseLinePresent())
                this.BaseLine = reader.ReadUInt16();
             
            if (this.getLineBreakFlagsFieldPresent())
                this.LineBreakFlags = reader.ReadUInt16();
             
            if (this.getTextDirectionPresent())
                this.TextDirection = reader.ReadUInt16();
             
        }

        public String toString(uint depth) throws Exception {
            StringBuilder result = new StringBuilder();
            result.append(IndentationForDepth(depth));
            result.append(super.toString());
            depth++;
            String indent = IndentationForDepth(depth);
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Length = {1}"),indent,this.Length));
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}IndentLevel = {1}"),indent,this.IndentLevel));
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Mask = {1}"),indent,this.Mask));
            if (this.getBulletFlagsFieldPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletFlags = {1}"),indent,this.BulletFlags));
             
            if (this.getBulletCharPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletChar = {1}"),indent,this.BulletChar));
             
            if (this.getBulletTypefacePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletTypefaceIdx = {1}"),indent,this.BulletTypefaceIdx));
             
            if (this.getBulletSizePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletSize = {1}"),indent,this.BulletSize));
             
            if (this.getBulletColorPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BulletColor = {1}"),indent,this.BulletColor));
             
            if (this.getAlignmentPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Alignment = {1}"),indent,this.Alignment));
             
            if (this.getLineSpacingPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}LineSpacing = {1}"),indent,this.LineSpacing));
             
            if (this.getSpaceBeforePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}SpaceBefore = {1}"),indent,this.SpaceBefore));
             
            if (this.getSpaceAfterPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}SpaceAfter = {1}"),indent,this.SpaceAfter));
             
            if (this.getLeftMarginPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}LeftMargin = {1}"),indent,this.LeftMargin));
             
            if (this.getIndentPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Indent = {1}"),indent,this.Indent));
             
            if (this.getDefaultTabSizePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}DefaultTabSize = {1}"),indent,this.DefaultTabSize));
             
            if (this.getBaseLinePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}BaseLine = {1}"),indent,this.BaseLine));
             
            if (this.getLineBreakFlagsFieldPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}LineBreakFlags = {1}"),indent,this.LineBreakFlags));
             
            if (this.getTextDirectionPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}TextDirection = {1}"),indent,this.TextDirection));
             
            return result.toString();
        }

        public String toString() {
            try
            {
                return this.ToString(0);
            }
            catch (RuntimeException __dummyCatchVar4)
            {
                throw __dummyCatchVar4;
            }
            catch (Exception __dummyCatchVar4)
            {
                throw new RuntimeException(__dummyCatchVar4);
            }
        
        }
    
    }

    public enum CharacterMask
    {
        None,
        // Bit 0 - 15 are used for marking style flag presence
        StyleFlagsFieldPresent,
        TypefacePresent,
        SizePresent,
        ColorPresent,
        PositionPresent,
        // Bit 20 is unused
        FEOldTypefacePresent,
        ANSITypefacePresent,
        SymbolTypefacePresent
    }
    public enum StyleMask
    {
        None,
        IsBold,
        IsItalic,
        IsUnderlined,
        // Bit 3 is unused
        HasShadow,
        HasAsianSmartQuotes,
        // Bit 6 is unused
        HasHorizonNumRendering,
        // Bit 8 is unused
        IsEmbossed,
        ExtensionNibble
    }
    // Bit 10 - 13
    // Bit 14 - 15 are unused
    public static class CharacterRun   
    {
        public long Length;
        public CharacterMask Mask = CharacterMask.None;
        public boolean getStyleFlagsFieldPresent() throws Exception {
            return (this.Mask & CharacterMask.StyleFlagsFieldPresent) != 0;
        }

        public boolean getTypefacePresent() throws Exception {
            return (this.Mask & CharacterMask.TypefacePresent) != 0;
        }

        public boolean getFEOldTypefacePresent() throws Exception {
            return (this.Mask & CharacterMask.FEOldTypefacePresent) != 0;
        }

        public boolean getANSITypefacePresent() throws Exception {
            return (this.Mask & CharacterMask.ANSITypefacePresent) != 0;
        }

        public boolean getSymbolTypefacePresent() throws Exception {
            return (this.Mask & CharacterMask.SymbolTypefacePresent) != 0;
        }

        public boolean getSizePresent() throws Exception {
            return (this.Mask & CharacterMask.SizePresent) != 0;
        }

        public boolean getPositionPresent() throws Exception {
            return (this.Mask & CharacterMask.PositionPresent) != 0;
        }

        public boolean getColorPresent() throws Exception {
            return (this.Mask & CharacterMask.ColorPresent) != 0;
        }

        public StyleMask? Style = StyleMask.None;
        public UInt16? TypefaceIdx = new UInt16?();
        public UInt16? FEOldTypefaceIdx = new UInt16?();
        public UInt16? ANSITypefaceIdx = new UInt16?();
        public UInt16? SymbolTypefaceIdx = new UInt16?();
        public UInt16? Size = new UInt16?();
        public UInt16? Position = new UInt16?();
        public GrColorAtom Color;
        public CharacterRun(BinaryReader reader) throws Exception {
            this.Length = reader.ReadUInt32();
            this.Mask = (CharacterMask)reader.ReadUInt32();
            if (this.getStyleFlagsFieldPresent())
                this.Style = (StyleMask)reader.ReadUInt16();
             
            if (this.getTypefacePresent())
                this.TypefaceIdx = reader.ReadUInt16();
             
            if (this.getFEOldTypefacePresent())
                this.FEOldTypefaceIdx = reader.ReadUInt16();
             
            if (this.getANSITypefacePresent())
                this.ANSITypefaceIdx = reader.ReadUInt16();
             
            if (this.getSymbolTypefacePresent())
                this.SymbolTypefaceIdx = reader.ReadUInt16();
             
            if (this.getSizePresent())
                this.Size = reader.ReadUInt16();
             
            if (this.getPositionPresent())
                this.Position = reader.ReadUInt16();
             
            if (this.getColorPresent())
                this.Color = new GrColorAtom(reader);
             
        }

        public String toString(uint depth) throws Exception {
            StringBuilder result = new StringBuilder();
            result.append(IndentationForDepth(depth));
            result.append(super.toString());
            depth++;
            String indent = IndentationForDepth(depth);
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Length = {1}"),indent,this.Length));
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Mask = {1}"),indent,this.Mask));
            if (this.getStyleFlagsFieldPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Style = {1}"),indent,this.Style));
             
            if (this.getTypefacePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}TypefaceIdx = {1}"),indent,this.TypefaceIdx));
             
            if (this.getFEOldTypefacePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}FEOldTypefaceIdx = {1}"),indent,this.FEOldTypefaceIdx));
             
            if (this.getANSITypefacePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}ANSITypefaceIdx = {1}"),indent,this.ANSITypefaceIdx));
             
            if (this.getSymbolTypefacePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}SymbolTypefaceIdx = {1}"),indent,this.SymbolTypefaceIdx));
             
            if (this.getSizePresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Size = {1}"),indent,this.Size));
             
            if (this.getPositionPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Position = {1}"),indent,this.Position));
             
            if (this.getColorPresent())
                result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Color = {1}"),indent,this.Color));
             
            return result.toString();
        }

        public String toString() {
            try
            {
                return this.ToString(0);
            }
            catch (RuntimeException __dummyCatchVar5)
            {
                throw __dummyCatchVar5;
            }
            catch (Exception __dummyCatchVar5)
            {
                throw new RuntimeException(__dummyCatchVar5);
            }
        
        }
    
    }

    public StyleTextPropAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
    }

    public void afterParentSet() throws Exception {
        // Anmerkung: In OOXML kann ein Character-Properties-Element sich
        // nicht �ber mehrere Paragraphen hinweg erstrecken.
        // TODO: War das im Bin�rformat der Fall?
        // TODO: FindParentByType? FindChildByType? FindSiblingByType?
        ClientTextbox textbox = this.getParentRecord() instanceof ClientTextbox ? (ClientTextbox)this.getParentRecord() : (ClientTextbox)null;
        if (textbox == null)
            throw new Exception("Record of type StyleTextPropAtom doesn't have parent of type ClientTextbox");
         
        TextAtom textAtom = textbox.Children.Find() instanceof TextAtom ? (TextAtom)textbox.Children.Find() : (TextAtom)null;
        /* This can legitimately happen... */
        if (textAtom == null)
        {
            this.Reader.Read(new char[this.BodySize], 0, (int)this.BodySize);
            return ;
        }
         
        // TODO: Length in bytes? UTF-16 characters? Full width unicode characters?
        uint seenLength = 0;
        while (seenLength < textAtom.Text.length())
        {
            ParagraphRun run = new ParagraphRun(this.Reader);
            System.out.println(run.toString());
            System.out.printf(StringSupport.CSFmtStrToJFmtStr("  Text = {0}") + "\n",Utils.stringInspect(textAtom.Text.substring((int)seenLength, ((int)seenLength) + ((int)run.Length))));
            System.out.println();
            seenLength += run.Length;
        }
        seenLength = 0;
        while (seenLength < textAtom.Text.length())
        {
            CharacterRun run = new CharacterRun(this.Reader);
            System.out.println(run.toString());
            System.out.printf(StringSupport.CSFmtStrToJFmtStr("  Text = {0}") + "\n",Utils.stringInspect(textAtom.Text.substring((int)seenLength, ((int)seenLength) + ((int)run.Length))));
            System.out.println();
            seenLength += run.Length;
        }
    }

}


