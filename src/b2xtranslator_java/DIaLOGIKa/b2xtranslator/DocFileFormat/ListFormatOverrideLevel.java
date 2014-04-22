//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
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
public class ListFormatOverrideLevel  extends ByteStructure 
{
    /**
    * Start-at value if fFormatting==false and fStartAt==true.
    * If fFormatting == true, the start is stored in the LVL
    */
    public int iStartAt;
    /**
    * The level to be overridden
    */
    public byte ilvl;
    /**
    * True if the start-at value is overridden
    */
    public boolean fStartAt;
    /**
    * True if the formatting is overridden
    */
    public boolean fFormatting;
    private static final int LFOLVL_LENGTH = 6;
    /**
    * Parses the bytes to retrieve a ListFormatOverrideLevel
    * 
    *  @param bytes The bytes
    */
    public ListFormatOverrideLevel(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        long startPos = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        this.iStartAt = _reader.readInt32();
        int flag = (int)_reader.readInt16();
        this.ilvl = (byte)(flag & 0x000F);
        this.fStartAt = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x0010);
        this.fFormatting = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x0020);
        //it's a complete override, so the fix part is followed by LVL struct
        if (this.fFormatting)
        {
        }
         
        _reader.getBaseStream().Seek(startPos, System.IO.SeekOrigin.Begin);
        _rawBytes = _reader.readBytes(LFOLVL_LENGTH);
    }

}


