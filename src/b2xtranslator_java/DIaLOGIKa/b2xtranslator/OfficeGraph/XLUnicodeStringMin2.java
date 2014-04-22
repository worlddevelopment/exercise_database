//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:46 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import DIaLOGIKa.b2xtranslator.OfficeGraph.XLUnicodeStringNoCch;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
/**
* This structure specifies a Unicode string.
*/
public class XLUnicodeStringMin2   
{
    /**
    * An unsigned integer that specifies the count of characters in the string.
    * 
    * MUST be equal to the number of characters in st.
    */
    public UInt16 cch = new UInt16();
    /**
    * An optional XLUnicodeStringNoCch that specifies the string.
    * 
    * MUST exist if and only if cch is greater than zero.
    */
    public XLUnicodeStringNoCch st;
    public XLUnicodeStringMin2(IStreamReader reader) throws Exception {
        this.cch = reader.readUInt16();
        //st MUST exist if and only if cch is greater than zero.
        if (this.cch > 0)
        {
            this.st = new XLUnicodeStringNoCch(reader,this.cch);
        }
         
    }

}


