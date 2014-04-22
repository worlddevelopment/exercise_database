//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:58 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteParseException;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DateAndTime;
import java.util.Date;

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
public class DateAndTime   implements IVisitable
{
    /**
    * minutes (0-59)
    */
    public short mint;
    /**
    * hours (0-23)
    */
    public short hr;
    /**
    * day of month (1-31)
    */
    public short dom;
    /**
    * month (1-12)
    */
    public short mon;
    /**
    * year (1900-2411)-1900
    */
    public short yr;
    /**
    * weekday
    * 0 Sunday
    * 1 Monday
    * 2 Tuesday
    * 3 Wednesday
    * 4 Thursday
    * 5 Friday
    * 6 Saturday
    */
    public short wdy;
    /**
    * Creates a new DateAndTime with default values
    */
    public DateAndTime() throws Exception {
        setDefaultValues();
    }

    /**
    * Parses the byte sto retrieve a DateAndTime
    * 
    *  @param bytes The bytes
    */
    public DateAndTime(byte[] bytes) throws Exception {
        if (bytes.length == 4)
        {
            BitArray bits = new BitArray(bytes);
            this.mint = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,0,6));
            this.hr = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,6,5));
            this.dom = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,11,5));
            this.mon = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,16,4));
            this.yr = (short)(1900 + DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,20,9)));
            this.wdy = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,29,3));
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct DTTM, the length of the struct doesn't match");
        } 
    }

    public Date toDateTime() throws Exception {
        if (this.yr == 1900 && this.mon == 0 && this.dom == 0 && this.hr == 0 && this.mint == 0)
        {
            return new Date(1900, 1, 1, 0, 0, 0);
        }
        else
        {
            return new Date(this.yr, this.mon, this.dom, this.hr, this.mint, 0);
        } 
    }

    private void setDefaultValues() throws Exception {
        this.dom = 0;
        this.hr = 0;
        this.mint = 0;
        this.mon = 0;
        this.wdy = 0;
        this.yr = 0;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<DateAndTime>)mapping).apply(this);
    }

}


