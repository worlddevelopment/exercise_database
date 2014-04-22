//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PropertyExceptions;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

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
public class ListLevel  extends ByteStructure 
{
    public enum FollowingChar
    {
        tab,
        space,
        nothing
    }
    /**
    * Start at value for this list level
    */
    public int iStartAt;
    /**
    * Number format code (see anld.nfc for a list of options)
    */
    public byte nfc;
    /**
    * Alignment (left, right, or centered) of the paragraph number.
    */
    public byte jc;
    /**
    * True if the level turns all inherited numbers to arabic,
    * false if it preserves their number format code (nfc)
    */
    public boolean fLegal;
    /**
    * True if the level�s number sequence is not restarted by
    * higher (more significant) levels in the list
    */
    public boolean fNoRestart;
    /**
    * Word 6.0 compatibility option: equivalent to anld.fPrev (see ANLD)
    */
    public boolean fPrev;
    /**
    * Word 6.0 compatibility option: equivalent to anld.fPrevSpace (see ANLD)
    */
    public boolean fPrevSpace;
    /**
    * True if this level was from a converted Word 6.0 document. 
    * If it is true, all of the Word 6.0 compatibility options become
    * valid otherwise they are ignored.
    */
    public boolean fWord6;
    /**
    * Contains the character offsets into the LVL�s XST of the inherited numbers of previous levels. 
    * The XST contains place holders for any paragraph numbers contained in the text of the number,
    * and the place holder contains the ilvl of the inherited number,
    * so lvl.xst[lvl.rgbxchNums[0]] == the level of the first inherited number in this level.
    */
    public byte[] rgbxchNums;
    /**
    * The type of character following the number text for the paragraph.
    */
    public FollowingChar ixchFollow = FollowingChar.tab;
    /**
    * Word 6.0 compatibility option: equivalent to anld.dxaSpace (see ANLD). 
    * For newer versions indent to remove if we remove this numbering.
    */
    public int dxaSpace;
    /**
    * Word 6.0 compatibility option: equivalent to anld.dxaIndent (see ANLD).
    * Unused in newer versions.
    */
    public int dxaIndent;
    /**
    * Length, in bytes, of the LVL�s grpprlChpx.
    */
    public byte cbGrpprlChpx;
    /**
    * Length, in bytes, of the LVL�s grpprlPapx.
    */
    public byte cbGrpprlPapx;
    /**
    * Limit of levels that we restart after.
    */
    public byte ilvlRestartLim;
    /**
    * A grfhic that specifies HTML incompatibilities of the level.
    */
    public byte grfhic;
    /**
    * 
    */
    public ParagraphPropertyExceptions grpprlPapx;
    /**
    * 
    */
    public CharacterPropertyExceptions grpprlChpx;
    /**
    * 
    */
    public String xst;
    /**
    * Parses the given StreamReader to retrieve a LVL struct
    * 
    *  @param bytes
    */
    public ListLevel(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        long startPos = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        //parse the fix part
        this.iStartAt = _reader.readInt32();
        this.nfc = _reader.readByte();
        int flag = _reader.readByte();
        this.jc = (byte)(flag & 0x03);
        this.fLegal = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x04);
        this.fNoRestart = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x08);
        this.fPrev = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x10);
        this.fPrevSpace = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x20);
        this.fWord6 = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x40);
        this.rgbxchNums = new byte[9];
        for (int i = 0;i < 9;i++)
        {
            rgbxchNums[i] = _reader.readByte();
        }
        this.ixchFollow = (FollowingChar)_reader.readByte();
        this.dxaSpace = _reader.readInt32();
        this.dxaIndent = _reader.readInt32();
        this.cbGrpprlChpx = _reader.readByte();
        this.cbGrpprlPapx = _reader.readByte();
        this.ilvlRestartLim = _reader.readByte();
        this.grfhic = _reader.readByte();
        //parse the variable part
        //read the group of papx sprms
        //this papx has no istd, so use PX to parse it
        PropertyExceptions px = new PropertyExceptions(_reader.ReadBytes(this.cbGrpprlPapx));
        this.grpprlPapx = new ParagraphPropertyExceptions();
        this.grpprlPapx.grpprl = px.grpprl;
        //read the group of chpx sprms
        this.grpprlChpx = new CharacterPropertyExceptions(_reader.ReadBytes(this.cbGrpprlChpx));
        //read the number text
        short strLen = _reader.readInt16();
        this.xst = Encoding.Unicode.GetString(_reader.ReadBytes(strLen * 2));
        long endPos = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        _reader.getBaseStream().Seek(startPos, System.IO.SeekOrigin.Begin);
        _rawBytes = _reader.readBytes((int)(endPos - startPos));
    }

}


