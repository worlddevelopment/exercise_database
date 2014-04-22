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
public class DropCapSpecifier   
{
    /**
    * drop cap type can be:
    * 0 no drop cap
    * 1 normal drop cap
    * 2 drop cap in margin
    */
    public byte Type;
    /**
    * Count of lines to drop
    */
    public byte Count;
    /**
    * Creates a new DropCapSpecifier with default values
    */
    public DropCapSpecifier() throws Exception {
        setDefaultValues();
    }

    /**
    * Parses the bytes to retrieve a DropCapSpecifier
    * 
    *  @param bytes
    */
    public DropCapSpecifier(byte[] bytes) throws Exception {
        if (bytes.length == 2)
        {
            byte val = bytes[0];
            this.Type = Convert.ToByte((int)val & ((int)(0x0007)));
            this.Count = Convert.ToByte((int)val & ((int)(0x00F8)));
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct DCS, the length of the struct doesn't match");
        } 
    }

    private void setDefaultValues() throws Exception {
        this.Count = 0;
        this.Type = 0;
    }

}


