//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.Tools;


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
//using DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;
/**
* Specifies an approximation of a real number, where the approximation has a fixed number of digits after the radix point.
* 
* This type is specified in [MS-OSHARED] section 2.2.1.6.
* 
* Value of the real number = Integral + ( Fractional / 65536.0 )
* 
* Integral (2 bytes): A signed integer that specifies the integral part of the real number.
* Fractional (2 bytes): An unsigned integer that specifies the fractional part of the real number.
*/
public class FixedPointNumber   
{
    public UInt16 Integral = new UInt16();
    public UInt16 Fractional = new UInt16();
    public FixedPointNumber(UInt16 integral, UInt16 fractional) throws Exception {
        this.Integral = integral;
        this.Fractional = fractional;
    }

    public FixedPointNumber(long value) throws Exception {
        byte[] bytes = System.BitConverter.GetBytes(value);
        this.Integral = System.BitConverter.ToUInt16(bytes, 0);
        this.Fractional = System.BitConverter.ToUInt16(bytes, 2);
    }

    public FixedPointNumber(byte[] bytes) throws Exception {
        this.Integral = System.BitConverter.ToUInt16(bytes, 0);
        this.Fractional = System.BitConverter.ToUInt16(bytes, 2);
    }

    //public FixedPointNumber(IStreamReader reader)
    //{
    //    this.integral = reader.ReadUInt16();
    //    this.fractional = reader.ReadUInt16();
    //}
    public double toAngle() throws Exception {
        if (this.Fractional != 0)
        {
            return (this.Fractional - 65536.0);
        }
        else // negative angle
        if (this.Integral != 0)
        {
            return (65536.0 - this.Integral);
        }
        else
        {
            return 0.0;
        }  
    }

    //positive angle
    public double getValue() throws Exception {
        return (double)this.Integral + (double)this.Fractional / 65536.0d;
    }

}


