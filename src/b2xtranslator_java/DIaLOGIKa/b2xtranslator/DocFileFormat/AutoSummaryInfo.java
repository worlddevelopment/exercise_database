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
public class AutoSummaryInfo   
{
    /**
    * True if the ASUMYI is valid
    */
    public boolean fValid;
    /**
    * True if AutoSummary View is active
    */
    public boolean fView;
    /**
    * Display method for AutoSummary View: 
    * 0 = Emphasize in current doc
    * 1 = Reduce doc to summary
    * 2 = Insert into doc
    * 3 = Show in new document
    */
    public short iViewBy;
    /**
    * True if File Properties summary information
    * should be updated after the next summarization
    */
    public boolean fUpdateProps;
    /**
    * Dialog summary level
    */
    public short wDlgLevel;
    /**
    * Upper bound for lLevel for sentences in this document
    */
    public int lHighestLevel;
    /**
    * Show document sentences at or below this level
    */
    public int lCurrentLevel;
    /**
    * Parses the bytes to retrieve a AutoSummaryInfo
    * 
    *  @param bytes The bytes
    */
    public AutoSummaryInfo(byte[] bytes) throws Exception {
        if (bytes.length == 12)
        {
            //split byte 0 and 1 into bits
            BitArray bits = new BitArray(new byte[]{ bytes[0], bytes[1] });
            this.fValid = bits[0];
            this.fView = bits[1];
            this.iViewBy = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,2,2));
            this.fUpdateProps = bits[4];
            this.wDlgLevel = System.BitConverter.ToInt16(bytes, 2);
            this.lHighestLevel = System.BitConverter.ToInt32(bytes, 4);
            this.lCurrentLevel = System.BitConverter.ToInt32(bytes, 8);
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct ASUMYI, the length of the struct doesn't match");
        } 
    }

}


