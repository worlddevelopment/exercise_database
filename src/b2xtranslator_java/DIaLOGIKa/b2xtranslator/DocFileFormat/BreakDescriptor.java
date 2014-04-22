//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:57 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

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
public class BreakDescriptor  extends ByteStructure 
{
    /**
    * Except in textbox BKD, index to PGD in plfpgd that describes the page this break is on
    */
    public short ipgd;
    /**
    * In textbox BKD
    */
    public short itxbxs;
    /**
    * Number of cp's considered for this break; note that the CP's described by cpDepend in this break reside in the next BKD
    */
    public short dcpDepend;
    /**
    * 
    */
    public UInt16 icol = new UInt16();
    /**
    * When true, this indicates that this is a table break.
    */
    public boolean fTableBreak;
    /**
    * When true, this indicates that this is a column break.
    */
    public boolean fColumnBreak;
    /**
    * Used temporarily while Word is running.
    */
    public boolean fMarked;
    /**
    * In textbox BKD, when true indicates cpLim of this textbox is not valid
    */
    public boolean fUnk;
    /**
    * In textbox BKD, when true indicates that text overflows the end of this textbox
    */
    public boolean fTextOverflow;
    public BreakDescriptor(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        this.ipgd = reader.readInt16();
        this.itxbxs = this.ipgd;
        this.dcpDepend = reader.readInt16();
        int flag = (int)reader.readInt16();
        this.icol = (UInt16)DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt(flag,0x00FF);
        this.fTableBreak = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x0100);
        this.fColumnBreak = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x0200);
        this.fMarked = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x0400);
        this.fUnk = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x0800);
        this.fTextOverflow = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x1000);
    }

}


