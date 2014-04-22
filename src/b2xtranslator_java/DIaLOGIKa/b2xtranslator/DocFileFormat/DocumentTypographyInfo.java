//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:00 AM
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
public class DocumentTypographyInfo   
{
    /**
    * True if we're kerning punctation
    */
    public boolean fKerningPunct;
    /**
    * Kinsoku method of justification:
    * 0 = always expand
    * 1 = compress punctation
    * 2 = compress punctation and kana
    */
    public short iJustification;
    /**
    * Level of kinsoku:
    * 0 = level 1
    * 1 = Level 2
    * 2 = Custom
    */
    public short iLevelOfKinsoku;
    /**
    * "2 page on 1" feature is turned on
    */
    public boolean f2on1;
    /**
    * Old East Asian feature
    */
    public boolean fOldDefineLineBaseOnGrid;
    /**
    * Custom Kinsoku
    */
    public short iCustomKsu;
    /**
    * When set to true, use strict (level 2) Kinsoku rules
    */
    public boolean fJapaneseUseLevel2;
    /**
    * Length of rgxchFPunct
    */
    public short cchFollowingPunct;
    /**
    * Length of rgxchLPunct
    */
    public short cchLeadingPunct;
    /**
    * Array of characters that should never appear at the start of a line
    */
    public char[] rgxchFPunct;
    /**
    * Array of characters that should never appear at the end of a line
    */
    public char[] rgxchLPunct;
    /**
    * Parses the bytes to retrieve a DocumentTypographyInfo
    * 
    *  @param bytes The bytes
    */
    public DocumentTypographyInfo(byte[] bytes) throws Exception {
        if (bytes.length == 310)
        {
            BitArray bits = new BitArray();
            //split byte 0 and 1 into bits
            bits = new BitArray(new byte[]{ bytes[0], bytes[1] });
            this.fKerningPunct = bits[0];
            this.iJustification = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,1,2));
            this.iLevelOfKinsoku = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,3,2));
            this.f2on1 = bits[5];
            this.fOldDefineLineBaseOnGrid = bits[6];
            this.iCustomKsu = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,7,3));
            this.fJapaneseUseLevel2 = bits[10];
            this.cchFollowingPunct = System.BitConverter.ToInt16(bytes, 2);
            this.cchLeadingPunct = System.BitConverter.ToInt16(bytes, 4);
            byte[] fpunctBytes = new byte[202];
            Array.Copy(bytes, 6, fpunctBytes, 0, fpunctBytes.length);
            this.rgxchFPunct = Encoding.Unicode.GetString(fpunctBytes).ToCharArray();
            byte[] lpunctBytes = new byte[102];
            Array.Copy(bytes, 208, lpunctBytes, 0, lpunctBytes.length);
            this.rgxchLPunct = Encoding.Unicode.GetString(lpunctBytes).ToCharArray();
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct DOPTYPOGRAPHY, the length of the struct doesn't match");
        } 
    }

}


