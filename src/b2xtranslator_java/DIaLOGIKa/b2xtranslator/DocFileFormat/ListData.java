//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListLevel;
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
public class ListData  extends ByteStructure 
{
    /**
    * Unique List ID
    */
    public int lsid;
    /**
    * Unique template code
    */
    public int tplc;
    /**
    * Array of shorts containing the istd�s linked to each level of the list,
    * or ISTD_NIL (4095) if no style is linked.
    */
    public short[] rgistd;
    /**
    * True if this is a simple (one-level) list.
    * False if this is a multilevel (nine-level) list.
    */
    public boolean fSimpleList;
    /**
    * Word 6.0 compatibility option:
    * True if the list should start numbering over at the beginning of each section.
    */
    public boolean fRestartHdn;
    /**
    * To emulate Word 6.0 numbering: 
    * True if Auto numbering
    */
    public boolean fAutoNum;
    /**
    * When true, this list was there before we started reading RTF.
    */
    public boolean fPreRTF;
    /**
    * When true, list is a hybrid multilevel/simple (UI=simple, internal=multilevel)
    */
    public boolean fHybrid;
    /**
    * Array of ListLevel describing the several levels of the list.
    */
    public ListLevel[] rglvl;
    /**
    * A grfhic that specifies HTML incompatibilities of the list.
    */
    public byte grfhic;
    public static final short ISTD_NIL = 4095;
    private static final int LSTF_LENGTH = 28;
    /**
    * Parses the StreamReader to retrieve a ListData
    * 
    *  @param bytes The bytes
    */
    public ListData(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        long startPos = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        this.lsid = _reader.readInt32();
        this.tplc = _reader.readInt32();
        this.rgistd = new short[9];
        for (int i = 0;i < 9;i++)
        {
            this.rgistd[i] = _reader.readInt16();
        }
        //parse flagbyte
        int flag = (int)_reader.readByte();
        this.fSimpleList = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x01);
        if (this.fSimpleList)
            this.rglvl = new ListLevel[1];
        else
            this.rglvl = new ListLevel[9]; 
        this.fRestartHdn = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x02);
        this.fAutoNum = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x04);
        this.fPreRTF = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x08);
        this.fHybrid = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x10);
        this.grfhic = _reader.readByte();
        _reader.getBaseStream().Seek(startPos, System.IO.SeekOrigin.Begin);
        _rawBytes = _reader.readBytes(LSTF_LENGTH);
    }

}


