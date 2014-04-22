//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:39 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.AbstractPtg;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgNumber;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.ShortXLUnicodeString;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
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
public class PtgStr  extends AbstractPtg 
{
    public static final PtgNumber ID = PtgNumber.PtgStr;
    public PtgStr(IStreamReader reader, PtgNumber ptgid) throws Exception {
        super(reader, ptgid);
        Debug.Assert(this.getId() == ID);
        this.type = PtgType.Operand;
        this.popSize = 1;
        ShortXLUnicodeString st = new ShortXLUnicodeString(this.getReader());
        // quotes need to be escaped
        this.setData(ExcelHelperClass.escapeFormulaString(st.getValue()));
        this.setLength((uint)(3 + st.rgb.length));
    }

}


// length = 1 byte Ptgtype + 1byte cch + 1byte highbyte