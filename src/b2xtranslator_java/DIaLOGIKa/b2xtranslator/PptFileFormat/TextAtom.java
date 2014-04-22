//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ITextDataRecord;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextHeaderAtom;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

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
public class TextAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record implements ITextDataRecord
{
    public String Text;
    public TextAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance, Encoding encoding) throws Exception {
        super(_reader, size, typeCode, version, instance);
        byte[] bytes = new byte[size];
        this.Reader.Read(bytes, 0, (int)size);
        this.Text = new String(encoding.GetChars(bytes));
    }

    public String toString(uint depth) throws Exception {
        return String.format(StringSupport.CSFmtStrToJFmtStr("{0}\n{1}Text = {2}"),super.toString(depth),indentationForDepth(depth + 1),Utils.stringInspect(this.Text));
    }

    private TextHeaderAtom _TextHeaderAtom;
    public TextHeaderAtom getTextHeaderAtom() throws Exception {
        return this._TextHeaderAtom;
    }

    public void setTextHeaderAtom(TextHeaderAtom value) throws Exception {
        this._TextHeaderAtom = value;
    }

}


