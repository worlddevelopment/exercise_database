//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientTextbox;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextAtom;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

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
public class StyleTextPropAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public CSList<ParagraphRun> PRuns;
    public CSList<CharacterRun> CRuns;
    public StyleTextPropAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.PRuns = new CSList<ParagraphRun>();
        this.CRuns = new CSList<CharacterRun>();
    }

    public void afterParentSet() throws Exception {
        ClientTextbox textbox = this.getParentRecord() instanceof ClientTextbox ? (ClientTextbox)this.getParentRecord() : (ClientTextbox)null;
        if (textbox == null)
        {
            TraceLogger.debugInternal("Record of type StyleTextPropAtom doesn't have parent of type ClientTextbox: {0}",this);
            return ;
        }
         
        // TODO: FindSiblingByType?
        TextAtom textAtom = textbox.<TextAtom>FirstChildWithType();
        /* This can legitimately happen... */
        if (textAtom == null)
        {
            this.Reader.Read(new char[this.BodySize], 0, (int)this.BodySize);
            return ;
        }
         
        // TODO: Length in bytes? UTF-16 characters? Full width unicode characters?
        //TraceLogger.DebugInternal("New text style for text: {0}", Utils.StringInspect(textAtom.Text));
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
    }

    public String toString(uint depth) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString(depth));
        depth++;
        String indent = indentationForDepth(depth);
        sb.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Paragraph Runs:"),indent));
        for (ParagraphRun pr : this.PRuns)
            sb.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}"),pr.toString(depth + 1)));
        sb.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}Character Runs:"),indent));
        for (CharacterRun cr : this.CRuns)
            sb.append(String.format(StringSupport.CSFmtStrToJFmtStr("\n{0}"),cr.toString(depth + 1)));
        return sb.toString();
    }

}


