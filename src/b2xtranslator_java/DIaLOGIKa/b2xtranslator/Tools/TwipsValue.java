//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.Tools;

import DIaLOGIKa.b2xtranslator.Tools.TwipsValue;

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
public class TwipsValue   
{
    /**
    * The dots per inch value that should be used.
    */
    public static final double Dpi = 72.0;
    private double value;
    /**
    * Creates a new TwipsValue for the given value.
    * 
    *  @param value
    */
    public TwipsValue(double value) throws Exception {
        this.value = value;
    }

    /**
    * Converts the twips to pt
    * 
    *  @return
    */
    public double toPoints() throws Exception {
        return this.value / 20;
    }

    /**
    * Converts the twips to inch
    * 
    *  @return
    */
    public double toInch() throws Exception {
        return this.value / (TwipsValue.Dpi * 20);
    }

    /**
    * Converts the twips to mm
    * 
    *  @return
    */
    public double toMm() throws Exception {
        return toInch() * 25.399931;
    }

    /**
    * Converts the twips to cm
    * 
    *  @return
    */
    public double toCm() throws Exception {
        return toMm() / 10.0;
    }

}


