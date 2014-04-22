//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextStyleAtom;

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
public class TextRunStyleAtom  extends TextStyleAtom 
{
    public TextRunStyleAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
    }

    /**
    * Can only read data after text header has been set. So automatic verification doesn't work.
    */
    public boolean getDoAutomaticVerifyReadToEnd() throws Exception {
        return false;
    }

    public void afterTextHeaderSet() throws Exception {
        TextAtom textAtom = this.getTextHeaderAtom().TextAtom;
        /* This can legitimately happen... */
        if (textAtom == null)
        {
            this.Reader.BaseStream.Position = this.Reader.BaseStream.Length;
            return ;
        }
         
        uint seenLength = 0;
        while (seenLength < textAtom.Text.length() + 1)
        {
            long pos = this.Reader.BaseStream.Position;
            uint length = this.Reader.ReadUInt32();
            ParagraphRun run = new ParagraphRun(this.Reader,false);
            run.Length = length;
            this.PRuns.add(run);
            /*TraceLogger.DebugInternal("Read paragraph run. Before pos = {0}, after pos = {1} of {2}: {3}",
                                pos, this.Reader.BaseStream.Position, this.Reader.BaseStream.Length,
                                run);*/
            seenLength += length;
        }
        //TraceLogger.DebugInternal();
        seenLength = 0;
        while (seenLength < textAtom.Text.length() + 1)
        {
            uint length = this.Reader.ReadUInt32();
            CharacterRun run = new CharacterRun(this.Reader);
            run.Length = length;
            this.CRuns.add(run);
            seenLength += length;
        }
        this.verifyReadToEnd();
    }

}


