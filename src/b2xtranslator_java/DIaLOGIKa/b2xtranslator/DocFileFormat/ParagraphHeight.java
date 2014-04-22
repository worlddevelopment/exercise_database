//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:05 AM
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
public class ParagraphHeight   
{
    /**
    * Complex shape layout in this paragraph
    */
    public boolean fVolatile;
    /**
    * ParagraphHeight is valid when fUnk is true
    */
    public boolean fUnk;
    /**
    * When true, total height of paragraph is known but lines in
    * paragraph have different heights
    */
    public boolean fDiffLines;
    /**
    * When fDiffLines is 0, is number of lines in paragraph
    */
    public short clMac;
    /**
    * Width of lines in paragraph
    */
    public int dxaCol;
    /**
    * When fDiffLines is true, is height of every line in paragraph in pixels
    */
    public int dymLine;
    /**
    * When fDiffLines is true, is the total height in pixels of the paragraph
    */
    public int dymHeight;
    /**
    * If not == 0, used as a hint when finding the next row.
    * (this value is only set if the PHE is stored in a PAP whose fTtp field is set)
    */
    public short dcpTtpNext;
    /**
    * Height of table row.
    * (this value is only set if the PHE is stored in a PAP whose fTtp field is set)
    */
    public int dymTableHeight;
    /**
    * Reserved
    */
    public boolean fSpare;
    /**
    * Creates a new empty ParagraphHeight with default values
    */
    public ParagraphHeight() throws Exception {
        //set default values
        setDefaultValues();
    }

    /**
    * Parses the bytes to retrieve a ParagraphHeight
    * 
    *  @param bytes The bytes
    *  @param fTtpMode 
    * The flag which indicates if the
    * ParagraphHeight is stored in a ParagraphProperties whose fTtp field is set
    */
    public ParagraphHeight(byte[] bytes, boolean fTtpMode) throws Exception {
        //set default values
        setDefaultValues();
        if (bytes.length == 12)
        {
            // The ParagraphHeight is placed in a ParagraphProperties whose fTtp field is set,
            //so used another bit setting
            if (fTtpMode)
            {
                this.fSpare = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(System.BitConverter.ToInt16(bytes, 0), 0x0001);
                this.fUnk = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(System.BitConverter.ToInt16(bytes, 0), 0x0002);
                this.dcpTtpNext = System.BitConverter.ToInt16(bytes, 0);
                this.dxaCol = System.BitConverter.ToInt32(bytes, 4);
                this.dymTableHeight = System.BitConverter.ToInt32(bytes, 8);
            }
            else
            {
                this.fVolatile = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(System.BitConverter.ToInt16(bytes, 0), 0x0001);
                this.fUnk = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(System.BitConverter.ToInt16(bytes, 0), 0x0002);
                this.fDiffLines = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(System.BitConverter.ToInt16(bytes, 0), 0x0004);
                this.clMac = Convert.ToInt16(((int)System.BitConverter.ToUInt16(bytes, 0)) & 0x00FF);
                this.dxaCol = System.BitConverter.ToInt32(bytes, 4);
                this.dymLine = System.BitConverter.ToInt32(bytes, 8);
                this.dymHeight = System.BitConverter.ToInt32(bytes, 8);
            } 
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct ParagraphHeight, the length of the struct doesn't match");
        } 
    }

    private void setDefaultValues() throws Exception {
        this.clMac = 0;
        this.dcpTtpNext = 0;
        this.dxaCol = 0;
        this.dymHeight = 0;
        this.dymLine = 0;
        this.dymTableHeight = 0;
        this.fDiffLines = false;
        this.fSpare = false;
        this.fUnk = false;
        this.fVolatile = false;
    }

}


