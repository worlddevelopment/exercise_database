//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:05 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.AutoNumberLevelDescriptor;
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
public class OutlineLiSTData   
{
    /**
    * An array of  ANLV structures describing how heading numbers
    * should be displayed fpr each of Word's 0 outline heading levels
    */
    public AutoNumberLevelDescriptor[] rganlv;
    /**
    * When true, restart heading on section break
    */
    public boolean fRestartHdr;
    /**
    * Reserved
    */
    public boolean fSpareOlst2;
    /**
    * Reserved
    */
    public boolean fSpareOlst3;
    /**
    * Reserved
    */
    public boolean fSpareOlst4;
    /**
    * Text before/after number
    */
    public char[] rgxch;
    /**
    * Creates a new OutlineLiSTData with default values
    */
    public OutlineLiSTData() throws Exception {
        setDefaultValues();
    }

    /**
    * Parses the bytes to retrieve a OutlineLiSTData
    * 
    *  @param bytes The bytes
    */
    public OutlineLiSTData(byte[] bytes) throws Exception {
        if (bytes.length == 248)
        {
            //Fill the rganlv array
            int j = 0;
            for (int i = 0;i < 180;i += 20)
            {
                //copy the 20 byte pages
                byte[] page = new byte[20];
                Array.Copy(bytes, i, page, 0, 20);
                this.rganlv[j] = new AutoNumberLevelDescriptor(page);
                j++;
            }
            //Set the flags
            this.fRestartHdr = DIaLOGIKa.b2xtranslator.Tools.Utils.intToBool((int)bytes[180]);
            this.fSpareOlst2 = DIaLOGIKa.b2xtranslator.Tools.Utils.intToBool((int)bytes[181]);
            this.fSpareOlst3 = DIaLOGIKa.b2xtranslator.Tools.Utils.intToBool((int)bytes[182]);
            this.fSpareOlst4 = DIaLOGIKa.b2xtranslator.Tools.Utils.intToBool((int)bytes[183]);
            //Fill the rgxch array
            j = 0;
            for (int i = 184;i < 64;i += 2)
            {
                this.rgxch[j] = Convert.ToChar(System.BitConverter.ToInt16(bytes, i));
                j++;
            }
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct OLST, the length of the struct doesn't match");
        } 
    }

    private void setDefaultValues() throws Exception {
        this.fRestartHdr = false;
        this.fSpareOlst2 = false;
        this.fSpareOlst3 = false;
        this.fSpareOlst4 = false;
        this.rganlv = new AutoNumberLevelDescriptor[9];
        for (int i = 0;i < 9;i++)
        {
            this.rganlv[i] = new AutoNumberLevelDescriptor();
        }
        this.rgxch = DIaLOGIKa.b2xtranslator.Tools.Utils.ClearCharArray(new char[32]);
    }

}


