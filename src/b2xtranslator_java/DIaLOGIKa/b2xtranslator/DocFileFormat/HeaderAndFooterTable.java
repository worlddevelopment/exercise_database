//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterRange;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
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
public class HeaderAndFooterTable   
{
    public CSList<CharacterRange> FirstHeaders;
    public CSList<CharacterRange> EvenHeaders;
    public CSList<CharacterRange> OddHeaders;
    public CSList<CharacterRange> FirstFooters;
    public CSList<CharacterRange> EvenFooters;
    public CSList<CharacterRange> OddFooters;
    public HeaderAndFooterTable(WordDocument doc) throws Exception {
        IStreamReader tableReader = new VirtualStreamReader(doc.TableStream);
        FirstHeaders = new CSList<CharacterRange>();
        EvenHeaders = new CSList<CharacterRange>();
        OddHeaders = new CSList<CharacterRange>();
        FirstFooters = new CSList<CharacterRange>();
        EvenFooters = new CSList<CharacterRange>();
        OddFooters = new CSList<CharacterRange>();
        //read the Table
        int[] table = new int[doc.FIB.lcbPlcfHdd / 4];
        doc.TableStream.Seek(doc.FIB.fcPlcfHdd, System.IO.SeekOrigin.Begin);
        for (int i = 0;i < table.length;i++)
        {
            table[i] = tableReader.readInt32();
        }
        int count = (table.length - 8) / 6;
        int initialPos = doc.FIB.ccpText + doc.FIB.ccpFtn;
        //the first 6 _entries are about footnote and endnote formatting
        //so skip these _entries
        int pos = 6;
        for (int i = 0;i < count;i++)
        {
            //Even Header
            if (table[pos] == table[pos + 1])
            {
                this.EvenHeaders.add(null);
            }
            else
            {
                this.EvenHeaders.add(new CharacterRange(initialPos + table[pos],table[pos + 1] - table[pos]));
            } 
            pos++;
            //Odd Header
            if (table[pos] == table[pos + 1])
            {
                this.OddHeaders.add(null);
            }
            else
            {
                this.OddHeaders.add(new CharacterRange(initialPos + table[pos],table[pos + 1] - table[pos]));
            } 
            pos++;
            //Even Footer
            if (table[pos] == table[pos + 1])
            {
                this.EvenFooters.add(null);
            }
            else
            {
                this.EvenFooters.add(new CharacterRange(initialPos + table[pos],table[pos + 1] - table[pos]));
            } 
            pos++;
            //Odd Footer
            if (table[pos] == table[pos + 1])
            {
                this.OddFooters.add(null);
            }
            else
            {
                this.OddFooters.add(new CharacterRange(initialPos + table[pos],table[pos + 1] - table[pos]));
            } 
            pos++;
            //First Page Header
            if (table[pos] == table[pos + 1])
            {
                this.FirstHeaders.add(null);
            }
            else
            {
                this.FirstHeaders.add(new CharacterRange(initialPos + table[pos],table[pos + 1] - table[pos]));
            } 
            pos++;
            //First Page Footers
            if (table[pos] == table[pos + 1])
            {
                this.FirstFooters.add(null);
            }
            else
            {
                this.FirstFooters.add(new CharacterRange(initialPos + table[pos],table[pos + 1] - table[pos]));
            } 
            pos++;
        }
    }

}


