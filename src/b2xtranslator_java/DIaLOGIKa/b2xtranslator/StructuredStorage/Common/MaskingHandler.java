//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;

import CS2JNet.JavaSupport.util.LocaleSupport;
import CS2JNet.System.StringSupport;
import java.util.Locale;

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
* Provides methods for masking/unmasking strings in a path
* Author: math
*/
public class MaskingHandler   
{
    static final long[] CharsToMask = { '%', '\\' };
    /**
    * Masks the given string
    */
    public static String mask(String text) throws Exception {
        String result = text;
        for (long character : CharsToMask)
        {
            result = result.Replace(StringSupport.mkString((char)character, 1), String.Format(LocaleSupport.INVARIANT, "%{0:X4}", character));
        }
        return result;
    }

    /**
    * Unmasks the given string
    */
    public static String unMask(String text) throws Exception {
        String result = text;
        for (long character : CharsToMask)
        {
            result = result.replace(String.format(StringSupport.CSFmtStrToJFmtStr("%{0:X4}"),character), StringSupport.mkString((char)character, 1));
        }
        return result;
    }

}


