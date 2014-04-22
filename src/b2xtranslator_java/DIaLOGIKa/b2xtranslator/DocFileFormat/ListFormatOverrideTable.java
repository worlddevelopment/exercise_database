//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListFormatOverride;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListFormatOverrideLevel;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

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
public class ListFormatOverrideTable  extends CSList<ListFormatOverride> 
{
    private static final int LFO_LENGTH = 16;
    private static final int LFOLVL_LENGTH = 6;
    public ListFormatOverrideTable(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
        if (fib.lcbPlfLfo > 0)
        {
            VirtualStreamReader reader = new VirtualStreamReader(tableStream);
            reader.getBaseStream().Seek((long)fib.fcPlfLfo, System.IO.SeekOrigin.Begin);
            //read the count of LFOs
            int count = reader.readInt32();
            for (int i = 0;i < count;i++)
            {
                //read the LFOs
                this.add(new ListFormatOverride(reader,LFO_LENGTH));
            }
            for (int i = 0;i < count;i++)
            {
                for (int j = 0;j < this.get(i).clfolvl;j++)
                {
                    //read the LFOLVLs
                    this.get(i).rgLfoLvl[j] = new ListFormatOverrideLevel(reader,LFOLVL_LENGTH);
                }
            }
        }
         
    }

}


