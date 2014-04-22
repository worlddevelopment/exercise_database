//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.AbstractHeader;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.InternalBitConverter;
import java.io.InputStream;
import java.math.BigInteger;

import org.apache.commons.io.input.CountingInputStream;

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
/**
* Abstract class for input and putput handlers.
* Author: math
*/
abstract public class AbstractIOHandler   
{
    protected CountingInputStream _stream;
    protected AbstractHeader _header;
    protected InternalBitConverter _bitConverter;
    abstract public /*UInt64*/BigInteger getIOStreamSize() throws Exception ;

    /**
    * Initializes the internal bit converter
    * 
    *  @param isLittleEndian flag whether big endian or little endian is used
    */
    public void initBitConverter(boolean isLittleEndian) throws Exception {
        _bitConverter = new InternalBitConverter(isLittleEndian);
    }

    /**
    * Initializes the reference to the header
    * 
    *  @param header
    */
    public void setHeaderReference(AbstractHeader header) throws Exception {
        _header = header;
    }

    /**
    * Closes the file associated with this handler
    */
    public void closeStream() throws Exception {
        if (_stream != null)
        {
            _stream.close();
        }
         
    }

}


