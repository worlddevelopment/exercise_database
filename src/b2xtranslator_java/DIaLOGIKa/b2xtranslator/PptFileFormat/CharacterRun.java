//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterMask;
import DIaLOGIKa.b2xtranslator.PptFileFormat.GrColorAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.StyleMask;

public class CharacterRun   
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
        try
        {
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
             
            if (this.getColorPresent())
                this.Color = new GrColorAtom(reader);
             
            if (this.getPositionPresent())
                this.Position = reader.ReadUInt16();
             
        }
        catch (EndOfStreamException e)
        {
            String s = e.toString();
        }
    
    }

    //ignore
    public String toString(uint depth) throws Exception {
        StringBuilder result = new StringBuilder();
        String indent = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.indentationForDepth(depth);
        result.append(indent);
        result.append(super.toString());
        depth++;
        indent = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.indentationForDepth(depth);
        result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Length = {1}"),indent,this.Length));
        result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Mask = {1}"),indent,this.Mask));
        if (this.Style != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Style = {1}"),indent,this.Style));
         
        if (this.TypefaceIdx != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}TypefaceIdx = {1}"),indent,this.TypefaceIdx));
         
        if (this.FEOldTypefaceIdx != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}FEOldTypefaceIdx = {1}"),indent,this.FEOldTypefaceIdx));
         
        if (this.ANSITypefaceIdx != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}ANSITypefaceIdx = {1}"),indent,this.ANSITypefaceIdx));
         
        if (this.SymbolTypefaceIdx != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}SymbolTypefaceIdx = {1}"),indent,this.SymbolTypefaceIdx));
         
        if (this.Size != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Size = {1}"),indent,this.Size));
         
        if (this.Position != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Position = {1}"),indent,this.Position));
         
        if (this.Color != null)
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Color = {1}"),indent,this.Color));
         
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


