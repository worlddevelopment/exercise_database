//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:56 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteParseException;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class AutoNumberLevelDescriptor   
{
    /**
    * Number format code
    */
    public byte nfc;
    /**
    * Offset into anld.rgxch that is the limit of the text that will be
    * displayed as the prefix of the auto number text
    */
    public byte cxchTextBefore;
    /**
    * anld.cxchTextBefore will be the beginning offset of the text in
    * the anld.rgxch that will be displayed as the suffix of an auto number.
    * The sum of anld.cxchTextBefore + anld.cxchTextAfter will be the limit
    * of the auto number suffix in anld.rgxch
    */
    public byte cxchTextAfter;
    /**
    * Justification code
    * 0 left justify
    * 1 center
    * 2 right justify
    * 3 left and right justfy
    */
    public byte jc;
    /**
    * When true, number generated will include previous levels
    */
    public boolean fPrev;
    /**
    * When true, number will be displayed using hanging indent
    */
    public boolean fHang;
    /**
    * When true, boldness of number will be determined by fBold
    */
    public boolean fSetBold;
    /**
    * When true, italicness of number will be determined by fItalic
    */
    public boolean fSetItalic;
    /**
    * When true, fSmallCaps will determine wheter number will be
    * displayed in small caps or not.
    */
    public boolean fSetSmallCaps;
    /**
    * When true, fCaps will determine wheter number will be
    * displayed capitalized or not.
    */
    public boolean fSetCaps;
    /**
    * When true, fStrike will determine wheter the number will be
    * displayed using strikethrough or not.
    */
    public boolean fSetStrike;
    /**
    * When true, kul will determine the underlining state of
    * the auto number
    */
    public boolean fSetKul;
    /**
    * When true, auto number will be displayed with a single
    * prefixing space character
    */
    public boolean fPrevSpace;
    /**
    * Determines boldness of auto number when fSetBold is true
    */
    public boolean fBold;
    /**
    * Determines italicness of auto number when fSetItalic is true
    */
    public boolean fItalic;
    /**
    * Determines wheter auto number will be displayed using
    * small caps when fSetSmallCaps is true
    */
    public boolean fSmallCaps;
    /**
    * Determines wheter auto number will be displayed using
    * caps when fSetCaps is true
    */
    public boolean fCaps;
    /**
    * Determines wheter auto number will be displayed using
    * caps when fSetStrike is true
    */
    public boolean fStrike;
    /**
    * Determines wheter auto number will be displayed with
    * underlining when fSetKul is true
    */
    public byte kul;
    /**
    * Color of auto number for Word 97.
    * Unused in Word 2000
    */
    public byte ico;
    /**
    * Font code of auto number
    */
    public short ftc;
    /**
    * Font half point size (0 = auto)
    */
    public UInt16 hps = new UInt16();
    /**
    * Starting value
    */
    public UInt16 iStartAt = new UInt16();
    /**
    * Width of prefix text (same as indent)
    */
    public UInt16 dxaIndent = new UInt16();
    /**
    * Minimum space between number and paragraph
    */
    public UInt16 dxaSpace = new UInt16();
    /**
    * 24-bit color for Word 2000
    */
    public int cv;
    /**
    * Creates a new AutoNumberedListDataDescriptor with default values
    */
    public AutoNumberLevelDescriptor() throws Exception {
        setDefaultValues();
    }

    /**
    * Parses the bytes to retrieve a AutoNumberLevelDescriptor
    * 
    *  @param bytes The bytes
    */
    public AutoNumberLevelDescriptor(byte[] bytes) throws Exception {
        if (bytes.length == 20)
        {
            this.nfc = bytes[0];
            this.cxchTextBefore = bytes[1];
            this.cxchTextAfter = bytes[2];
            int b3 = (int)bytes[3];
            this.jc = Convert.ToByte(b3 & 0x03);
            this.fPrev = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b3,0x04);
            this.fHang = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b3,0x08);
            this.fSetBold = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b3,0x10);
            this.fSetItalic = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b3,0x20);
            this.fSetSmallCaps = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b3,0x40);
            this.fSetCaps = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b3,0x80);
            int b4 = (int)bytes[4];
            this.fSetStrike = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b4,0x01);
            this.fSetKul = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b4,0x02);
            this.fPrevSpace = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b4,0x04);
            this.fBold = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b4,0x08);
            this.fItalic = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b4,0x10);
            this.fSmallCaps = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b4,0x20);
            this.fCaps = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b4,0x40);
            this.fStrike = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(b4,0x80);
            int b5 = (int)bytes[5];
            this.kul = (byte)(b5 & 0x07);
            this.ico = (byte)(b5 & 0xF1);
            this.ftc = System.BitConverter.ToInt16(bytes, 6);
            this.hps = System.BitConverter.ToUInt16(bytes, 8);
            this.iStartAt = System.BitConverter.ToUInt16(bytes, 10);
            this.dxaIndent = System.BitConverter.ToUInt16(bytes, 12);
            this.dxaSpace = System.BitConverter.ToUInt16(bytes, 14);
            this.cv = System.BitConverter.ToInt32(bytes, 16);
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct AutoNumberLevelDescriptor, the length of the struct doesn't match");
        } 
    }

    private void setDefaultValues() throws Exception {
        this.cv = 0;
        this.cxchTextAfter = 0;
        this.cxchTextBefore = 0;
        this.dxaIndent = 0;
        this.dxaSpace = 0;
        this.fBold = false;
        this.fCaps = false;
        this.fHang = false;
        this.fItalic = false;
        this.fPrev = false;
        this.fPrevSpace = false;
        this.fSetBold = false;
        this.fSetCaps = false;
        this.fSetItalic = false;
        this.fSetKul = false;
        this.fSetSmallCaps = false;
        this.fSetStrike = false;
        this.fSmallCaps = false;
        this.fStrike = false;
        this.ftc = 0;
        this.hps = 0;
        this.ico = 0;
        this.iStartAt = 0;
        this.jc = 0;
        this.kul = 0;
        this.nfc = 0;
    }

}


