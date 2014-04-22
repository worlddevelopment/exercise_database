//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListFormatOverrideLevel;
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
public class ListFormatOverride  extends ByteStructure 
{
    /**
    * List ID of corresponding ListData
    */
    public int lsid;
    /**
    * Count of levels whose format is overridden
    */
    public byte clfolvl;
    /**
    * Specifies the field this LFO represents.
    * MUST be a value from the following table:
    * 0x00:   This LFO is not used for any field.
    * 0xFC:   This LFO is used for the AUTONUMLGL field.
    * 0xFD:   This LFO is used for the AUTONUMOUT field.
    * 0xFE:   This LFO is used for the AUTONUM field.
    * 0xFF:   This LFO is not used for any field.
    */
    public byte ibstFltAutoNum;
    /**
    * A grfhic that specifies HTML incompatibilities.
    */
    public byte grfhic;
    /**
    * Array of all levels whose format is overridden
    */
    public ListFormatOverrideLevel[] rgLfoLvl;
    private static final int LFO_LENGTH = 16;
    /**
    * Parses the given Stream Reader to retrieve a ListFormatOverride
    * 
    *  @param bytes The bytes
    */
    public ListFormatOverride(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        long startPos = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        this.lsid = _reader.readInt32();
        _reader.readBytes(8);
        this.clfolvl = _reader.readByte();
        this.ibstFltAutoNum = _reader.readByte();
        this.grfhic = _reader.readByte();
        _reader.readByte();
        this.rgLfoLvl = new ListFormatOverrideLevel[this.clfolvl];
        _reader.getBaseStream().Seek(startPos, System.IO.SeekOrigin.Begin);
        _rawBytes = _reader.readBytes(LFO_LENGTH);
    }

}


