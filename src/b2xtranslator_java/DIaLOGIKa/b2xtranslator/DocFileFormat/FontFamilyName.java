//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:02 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
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
public class FontFamilyName  extends ByteStructure 
{
    public static class FontSignature   
    {
        public FontSignature() {
        }

        public long UnicodeSubsetBitfield0;
        public long UnicodeSubsetBitfield1;
        public long UnicodeSubsetBitfield2;
        public long UnicodeSubsetBitfield3;
        public long CodePageBitfield0;
        public long CodePageBitfield1;
    }

    /**
    * When true, font is a TrueType font
    */
    public boolean fTrueType;
    /**
    * Font family id
    */
    public byte ff;
    /**
    * Base weight of font
    */
    public short wWeight;
    /**
    * Character set identifier
    */
    public byte chs;
    /**
    * Pitch request
    */
    public byte prq;
    /**
    * Name of font
    */
    public String xszFtn;
    /**
    * Alternative name of the font
    */
    public String xszAlt;
    /**
    * Panose
    */
    public byte[] panose;
    /**
    * Font sinature
    */
    public FontSignature fs = new FontSignature();
    public FontFamilyName(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        long startPos = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        //FFID
        int ffid = (int)_reader.readByte();
        int req = ffid;
        req = req << 6;
        req = req >> 6;
        this.prq = (byte)req;
        this.fTrueType = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(ffid,0x04);
        int family = ffid;
        family = family << 1;
        family = family >> 4;
        this.ff = (byte)family;
        this.wWeight = _reader.readInt16();
        this.chs = _reader.readByte();
        //skip byte 5
        _reader.readByte();
        //read the 10 bytes panose
        this.panose = _reader.readBytes(10);
        //read the 24 bytes FontSignature
        this.fs = new FontSignature();
        this.fs.UnicodeSubsetBitfield0 = _reader.readUInt32();
        this.fs.UnicodeSubsetBitfield1 = _reader.readUInt32();
        this.fs.UnicodeSubsetBitfield2 = _reader.readUInt32();
        this.fs.UnicodeSubsetBitfield3 = _reader.readUInt32();
        this.fs.CodePageBitfield0 = _reader.readUInt32();
        this.fs.CodePageBitfield1 = _reader.readUInt32();
        //read the next \0 terminated string
        long strStart = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        long strEnd = searchTerminationZero(_reader);
        this.xszFtn = Encoding.Unicode.GetString(_reader.readBytes((int)(strEnd - strStart)));
        this.xszFtn = this.xszFtn.replace("\0", "");
        long readBytes = Unsupported.throwUnsupported("_reader.getBaseStream().Position") - startPos;
        if (readBytes < _length)
        {
            //read the next \0 terminated string
            strStart = Unsupported.throwUnsupported("reader.getBaseStream().Position");
            strEnd = searchTerminationZero(_reader);
            this.xszAlt = Encoding.Unicode.GetString(_reader.readBytes((int)(strEnd - strStart)));
            this.xszAlt = this.xszAlt.replace("\0", "");
        }
         
    }

    private long searchTerminationZero(VirtualStreamReader reader) throws Exception {
        long strStart = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        while (reader.readInt16() != 0)
        {
                ;
        }
        long pos = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        reader.getBaseStream().Seek(strStart, System.IO.SeekOrigin.Begin);
        return pos;
    }

}


