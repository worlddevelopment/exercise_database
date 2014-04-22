//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
* Wrapper of the class BitConverter in order to support big endian
* Author: math
*/
public class InternalBitConverter   
{
    private boolean _IsLittleEndian = true;
    public InternalBitConverter(boolean isLittleEndian) throws Exception {
        _IsLittleEndian = isLittleEndian;
    }

    public BigInteger toUInt64(byte[] value) throws Exception {
        if (BitConverter.IsLittleEndian ^ _IsLittleEndian)
        {
            Array.Reverse(value);
        }
         
        return BitConverter.ToUInt64(value, 0);
    }

    public BigInteger toUInt32(byte[] value) throws Exception {
        if (BitConverter.IsLittleEndian ^ _IsLittleEndian)
        {
            Array.Reverse(value);
        }
         
        return BitConverter.ToUInt32(value, 0);
    }

    public short toUInt16(byte[] value) throws Exception {
        if (BitConverter.IsLittleEndian ^ _IsLittleEndian)
        {
            Array.Reverse(value);
        }
         
        return BitConverter.ToUInt16(value, 0);
    }

    public String toString(byte[] value) throws Exception {
        if (BitConverter.IsLittleEndian ^ _IsLittleEndian)
        {
            Array.Reverse(value);
        }
         
        UnicodeEncoding enc = new UnicodeEncoding();
        String result = enc.GetString(value);
        if (result.contains("\0"))
        {
            result = result.Remove(result.indexOf("\0"));
        }
         
        return result;
    }

    public byte[] getBytes(BigInteger value) throws Exception {
        byte[] result = BitConverter.GetBytes(value);
        if (BitConverter.IsLittleEndian ^ _IsLittleEndian)
        {
            Array.Reverse(result);
        }
         
        return result;
    }

    public byte[] getBytes(BigInteger value) throws Exception {
        byte[] result = BitConverter.GetBytes(value);
        if (BitConverter.IsLittleEndian ^ _IsLittleEndian)
        {
            Array.Reverse(result);
        }
         
        return result;
    }

    public byte[] getBytes(BigInteger value) throws Exception {
        byte[] result = BitConverter.GetBytes(value);
        if (BitConverter.IsLittleEndian ^ _IsLittleEndian)
        {
            Array.Reverse(result);
        }
         
        return result;
    }

    public List<Byte> getBytes(List<BigInteger> input) throws Exception {
        List<Byte> output = new ArrayList<Byte>();
        for (BigInteger entry : input)
        {
            output.addRange(getBytes(entry));
        }
        return output;
    }

}


