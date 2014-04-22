//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:43 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.ShortXLUnicodeString;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
/**
* This record specifies a font and font formatting information.
*/
public class Font  extends BiffRecord 
{
    public static final RecordType ID = RecordType.Font;
    public enum FontWeight
    {
        Default,
        Normal,
        Bold
    }
    public enum ScriptStyle
    {
        NormalScript,
        SuperScript,
        SubScript
    }
    public enum UnderlineStyle
    {
        None,
        Single,
        Double
    }
    public enum FontFamily
    {
        NotApplicable,
        Roman,
        Swiss,
        Modern,
        Script,
        Decorative
    }
    /// <summary>
    /// An unsigned integer that specifies the height of the font in twips.
    ///
    /// SHOULD <49> be greater than or equal to 20 and less than or equal to 8191.
    /// MUST be greater than or equal to 20 and less than 8181, or 0.
    /// </summary>
    public UInt16 dyHeight = new UInt16();
    /**
    * A bit that specifies whether the font is italic.
    */
    public boolean fItalic;
    /**
    * A bit that specifies whether the font has strikethrough formatting applied.
    */
    public boolean fStrikeOut;
    /**
    * A bit that specifies whether the font has an outline effect applied.
    */
    public boolean fOutline;
    /**
    * A bit that specifies whether the font has a shadow effect applied.
    */
    public boolean fShadow;
    /**
    * A bit that specifies whether the font is condensed or not. If the value is 1, the font is condensed.
    */
    public boolean fCondense;
    /**
    * A bit that specifies whether the font is extended or not. If the value is 1, the font is extended.
    */
    public boolean fExtend;
    /// <summary>
    /// An unsigned integer that specifies the color of the font.
    ///
    /// The value SHOULD <50> be an IcvFont value.
    /// This value MUST be an IcvFont value, or 0.
    /// </summary>
    public UInt16 icv = new UInt16();
    // TODO: implement IcvFont structure and color mapping
    /// <summary>
    /// An unsigned integer that specifies the font weight.
    ///
    /// The value SHOULD <51> be a value from the following table.
    /// This value MUST be 0, or a value from the following table.
    /// </summary>
    public FontWeight bls = FontWeight.Default;
    /**
    * An unsigned integer that specifies whether superscript, subscript, or normal script is used.
    */
    public ScriptStyle sss = ScriptStyle.NormalScript;
    /**
    * An unsigned integer that specifies the underline style.
    */
    public UnderlineStyle uls = UnderlineStyle.None;
    /**
    * An unsigned integer that specifies the font family, as defined by
    * Windows API LOGFONT structure in [MSDN-FONTS].
    * 
    * MUST be greater than or equal to 0 and less than or equal to 5.
    */
    public FontFamily bFamily = FontFamily.NotApplicable;
    /**
    * An unsigned integer that specifies the character set, as defined
    * by Windows API LOGFONT structure in [MSDN-FONTS].
    */
    public byte bCharSet;
    /**
    * A ShortXLUnicodeString that specifies the name of this font. String
    * length MUST be greater than or equal to 1 and less than or equal to 31.
    * The fontName.fHighByte field MUST equal 1. MUST NOT contain any null characters.
    */
    public ShortXLUnicodeString fontName;
    public Font(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.dyHeight = reader.readUInt16();
        UInt16 flags = reader.readUInt16();
        // 0x0001 is unused
        this.fItalic = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0002);
        // 0x0004 is unused
        this.fStrikeOut = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0008);
        this.fOutline = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0010);
        this.fShadow = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0020);
        this.fCondense = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0040);
        this.fExtend = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0080);
        this.icv = reader.readUInt16();
        this.bls = (FontWeight)reader.readUInt16();
        this.sss = (ScriptStyle)reader.readUInt16();
        this.uls = (UnderlineStyle)reader.readByte();
        this.bFamily = (FontFamily)reader.readByte();
        this.bCharSet = reader.readByte();
        // skip unused byte
        reader.readByte();
        this.fontName = new ShortXLUnicodeString(reader);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


