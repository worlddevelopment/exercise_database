//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:02 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPage;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPagePAPX;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphHeight;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;

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
public class FormattedDiskPagePAPX  extends FormattedDiskPage 
{
    /**
    * An array of the BX data structure.
    * BX is a 13 byte data structure. The first byte of each is the word offset of the PAPX.
    */
    public DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPagePAPX.BX[] rgbx;
    /**
    * grppapx consists of all of the PAPXs stored in FKP concatenated end to end.
    * Each PAPX begins with a count of words which records its length padded to a word boundary.
    */
    public ParagraphPropertyExceptions[] grppapx;
    public static class BX   
    {
        public BX() {
        }

        public byte wordOffset;
        public ParagraphHeight phe;
    }

    public FormattedDiskPagePAPX(VirtualStream wordStream, int offset, VirtualStream dataStream) throws Exception {
        this.Type = FKPType.Paragraph;
        this.WordStream = wordStream;
        //read the 512 bytes (FKP)
        byte[] bytes = new byte[512];
        wordStream.read(bytes,0,512,offset);
        //get the count
        this.crun = bytes[511];
        //create and fill the array with the adresses
        this.rgfc = new int[this.crun + 1];
        int j = 0;
        for (int i = 0;i < rgfc.length;i++)
        {
            rgfc[i] = System.BitConverter.ToInt32(bytes, j);
            j += 4;
        }
        //create arrays
        this.rgbx = new DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPagePAPX.BX[this.crun];
        this.grppapx = new ParagraphPropertyExceptions[this.crun];
        j = 4 * (this.crun + 1);
        for (int i = 0;i < rgbx.length;i++)
        {
            //read the 12 for PHE
            byte[] phe = new byte[12];
            Array.Copy(bytes, j + 1, phe, 0, phe.length);
            //fill the rgbx array
            DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPagePAPX.BX bx = new DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPagePAPX.BX();
            bx.wordOffset = bytes[j];
            bx.phe = new ParagraphHeight(phe,false);
            rgbx[i] = bx;
            j += 13;
            if (bx.wordOffset != 0)
            {
                //read first byte of PAPX
                //PAPX is stored in a FKP; so the first byte is a count of words
                byte padbyte = 0;
                byte cw = bytes[bx.wordOffset * 2];
                //if that byte is zero, it's a pad byte, and the word count is the following byte
                if (cw == 0)
                {
                    padbyte = 1;
                    cw = bytes[bx.wordOffset * 2 + 1];
                }
                 
                if (cw != 0)
                {
                    //read the bytes for papx
                    byte[] papx = new byte[cw * 2];
                    Array.Copy(bytes, (bx.wordOffset * 2) + padbyte + 1, papx, 0, papx.length);
                    //parse PAPX and fill grppapx
                    this.grppapx[i] = new ParagraphPropertyExceptions(papx,dataStream);
                }
                 
            }
            else
            {
                //create a PAPX which doesn't modify anything
                this.grppapx[i] = new ParagraphPropertyExceptions();
            } 
        }
    }

    /**
    * Parses the 0Table (or 1Table) for FKP _entries containing PAPX
    * 
    *  @param fib The FileInformationBlock
    *  @param wordStream The WordDocument stream
    *  @param tableStream The 0Table stream
    *  @return
    */
    public static CSList<FormattedDiskPagePAPX> getAllPAPXFKPs(FileInformationBlock fib, VirtualStream wordStream, VirtualStream tableStream, VirtualStream dataStream) throws Exception {
        CSList<FormattedDiskPagePAPX> list = new CSList<FormattedDiskPagePAPX>();
        //get bintable for PAPX
        byte[] binTablePapx = new byte[fib.lcbPlcfBtePapx];
        tableStream.read(binTablePapx,0,binTablePapx.length,(int)fib.fcPlcfBtePapx);
        //there are n offsets and n-1 fkp's in the bin table
        int n = (((int)fib.lcbPlcfBtePapx - 4) / 8) + 1;
        for (int i = (n * 4);i < binTablePapx.length;i += 4)
        {
            //Get the indexed PAPX FKPs
            //indexed FKP is the xth 512byte page
            int fkpnr = System.BitConverter.ToInt32(binTablePapx, i);
            //so starts at:
            int offset = fkpnr * 512;
            //parse the FKP and add it to the list
            list.add(new FormattedDiskPagePAPX(wordStream,offset,dataStream));
        }
        return list;
    }

    /**
    * Returns a list of all PAPX FCs between they given boundaries.
    * 
    *  @param fcMin The lower boundary
    *  @param fcMax The upper boundary
    *  @param fib The FileInformationBlock
    *  @param wordStream The VirtualStream "WordStream"
    *  @param tableStream The VirtualStream "0Table" or "1Table"
    *  @return The FCs
    */
    public static CSList<Integer> getFileCharacterPositions(int fcMin, int fcMax, FileInformationBlock fib, VirtualStream wordStream, VirtualStream tableStream, VirtualStream dataStream) throws Exception {
        CSList<Integer> list = new CSList<Integer>();
        CSList<FormattedDiskPagePAPX> fkps = FormattedDiskPagePAPX.getAllPAPXFKPs(fib,wordStream,tableStream,dataStream);
        for (int i = 0;i < fkps.size();i++)
        {
            FormattedDiskPage fkp = fkps.get(i);
            //the last entry of each is always the same as the first entry of the next FKP
            //so, ignore all last _entries except for the last FKP.
            int max = fkp.rgfc.length;
            if (i < fkps.size() - 1)
                max--;
             
            for (int j = 0;j < max;j++)
            {
                if (fkp.rgfc[j] >= fcMin && fkp.rgfc[j] < fcMax)
                    list.add(fkp.rgfc[j]);
                 
            }
        }
        return list;
    }

    /**
    * Returnes a list of all ParagraphPropertyExceptions which correspond to text
    * between the given offsets.
    * 
    *  @param fcMin The lower boundary
    *  @param fcMax The upper boundary
    *  @param fib The FileInformationBlock
    *  @param wordStream The VirtualStream "WordStream"
    *  @param tableStream The VirtualStream "0Table" or "1Table"
    *  @return The FCs
    */
    public static CSList<ParagraphPropertyExceptions> getParagraphPropertyExceptions(int fcMin, int fcMax, FileInformationBlock fib, VirtualStream wordStream, VirtualStream tableStream, VirtualStream dataStream) throws Exception {
        CSList<ParagraphPropertyExceptions> list = new CSList<ParagraphPropertyExceptions>();
        CSList<FormattedDiskPagePAPX> fkps = FormattedDiskPagePAPX.getAllPAPXFKPs(fib,wordStream,tableStream,dataStream);
        for (int i = 0;i < fkps.size();i++)
        {
            FormattedDiskPagePAPX fkp = fkps.get(i);
            for (int j = 0;j < fkp.grppapx.length;j++)
            {
                if (fkp.rgfc[j] >= fcMin && fkp.rgfc[j] < fcMax)
                    list.add(fkp.grppapx[j]);
                 
            }
        }
        return list;
    }

}


