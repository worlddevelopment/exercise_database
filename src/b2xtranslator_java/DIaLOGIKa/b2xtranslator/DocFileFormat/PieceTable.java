//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:06 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PieceDescriptor;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import java.util.HashMap;

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
public class PieceTable   
{
    /**
    * A list of PieceDescriptor standing for each piece of text.
    */
    public CSList<PieceDescriptor> Pieces;
    /**
    * A dictionary with character positions as keys and the matching FCs as values
    */
    public HashMap<Integer,Integer> FileCharacterPositions;
    /**
    * A dictionary with file character positions as keys and the matching CPs as values
    */
    public HashMap<Integer,Integer> CharacterPositions;
    /**
    * Parses the pice table and creates a list of PieceDescriptors.
    * 
    *  @param fib The FIB
    *  @param tableStream The 0Table or 1Table stream
    */
    public PieceTable(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
        //Read the bytes of complex file information
        byte[] bytes = new byte[fib.lcbClx];
        tableStream.read(bytes,0,(int)fib.lcbClx,(int)fib.fcClx);
        this.Pieces = new CSList<PieceDescriptor>();
        this.FileCharacterPositions = new HashMap<Integer,Integer>();
        this.CharacterPositions = new HashMap<Integer,Integer>();
        int pos = 0;
        boolean goon = true;
        while (goon)
        {
            try
            {
                byte type = bytes[pos];
                if (type == 2)
                {
                    //check if the type of the entry is a piece table
                    int lcb = System.BitConverter.ToInt32(bytes, pos + 1);
                    //read the piece table
                    byte[] piecetable = new byte[lcb];
                    Array.Copy(bytes, pos + 5, piecetable, 0, piecetable.length);
                    //count of PCD _entries
                    int n = (lcb - 4) / 12;
                    for (int i = 0;i < n;i++)
                    {
                        //and n piece descriptors
                        //read the CP
                        int indexCp = i * 4;
                        int cp = System.BitConverter.ToInt32(piecetable, indexCp);
                        //read the next CP
                        int indexCpNext = (i + 1) * 4;
                        int cpNext = System.BitConverter.ToInt32(piecetable, indexCpNext);
                        //read the PCD
                        int indexPcd = ((n + 1) * 4) + (i * 8);
                        byte[] pcdBytes = new byte[8];
                        Array.Copy(piecetable, indexPcd, pcdBytes, 0, 8);
                        PieceDescriptor pcd = new PieceDescriptor(pcdBytes);
                        pcd.cpStart = cp;
                        pcd.cpEnd = cpNext;
                        //add pcd
                        this.Pieces.add(pcd);
                        //add positions
                        int f = (int)pcd.fc;
                        int multi = 1;
                        if (pcd.encoding == Encoding.Unicode)
                        {
                            multi = 2;
                        }
                         
                        for (int c = pcd.cpStart;c < pcd.cpEnd;c++)
                        {
                            if (!this.FileCharacterPositions.containsKey(c))
                                this.FileCharacterPositions.put(c, f);
                             
                            if (!this.CharacterPositions.containsKey(f))
                                this.CharacterPositions.put(f, c);
                             
                            f += multi;
                        }
                    }
                    int maxCp = this.FileCharacterPositions.size();
                    this.FileCharacterPositions.put(maxCp, fib.fcMac);
                    this.CharacterPositions.put(fib.fcMac, maxCp);
                    //piecetable was found
                    goon = false;
                }
                else if (type == 1)
                {
                    //entry is no piecetable so goon
                    short cb = System.BitConverter.ToInt16(bytes, pos + 1);
                    pos = pos + 1 + 2 + cb;
                }
                else
                {
                    goon = false;
                }  
            }
            catch (Exception __dummyCatchVar0)
            {
                goon = false;
            }
        
        }
    }

    public CSList<Character> getAllChars(VirtualStream wordStream) throws Exception {
        CSList<Character> chars = new CSList<Character>();
        for (PieceDescriptor pcd : this.Pieces)
        {
            //get the FC end of this piece
            int pcdFcEnd = pcd.cpEnd - pcd.cpStart;
            if (pcd.encoding == Encoding.Unicode)
                pcdFcEnd *= 2;
             
            pcdFcEnd += (int)pcd.fc;
            int cb = pcdFcEnd - (int)pcd.fc;
            byte[] bytes = new byte[cb];
            //read all bytes
            wordStream.read(bytes,0,cb,(int)pcd.fc);
            //get the chars
            char[] plainChars = new String(bytes, pcd.encoding.getString()).toCharArray();
            for (char c : plainChars)
            {
                //add to list
                chars.add(c);
            }
        }
        return chars;
    }

    public CSList<Character> getChars(int fcStart, int fcEnd, VirtualStream wordStream) throws Exception {
        CSList<Character> chars = new CSList<Character>();
        for (int i = 0;i < this.Pieces.size();i++)
        {
            PieceDescriptor pcd = this.Pieces.get(i);
            //get the FC end of this piece
            int pcdFcEnd = pcd.cpEnd - pcd.cpStart;
            if (pcd.encoding == Encoding.Unicode)
                pcdFcEnd *= 2;
             
            pcdFcEnd += (int)pcd.fc;
            if (pcdFcEnd < fcStart)
            {
                continue;
            }
            else //this piece is before the requested range
            if (fcStart >= pcd.fc && fcEnd > pcdFcEnd)
            {
                //requested char range starts at this piece
                //read from fcStart to pcdFcEnd
                //get count of bytes
                int cb = pcdFcEnd - fcStart;
                byte[] bytes = new byte[cb];
                //read all bytes
                wordStream.read(bytes,0,cb,((int)(fcStart)));
                //get the chars
                char[] plainChars = new String(bytes, pcd.encoding.getString()).toCharArray();
                for (char c : plainChars)
                {
                    //add to list
                    chars.add(c);
                }
            }
            else if (fcStart <= pcd.fc && fcEnd >= pcdFcEnd)
            {
                //the full piece is part of the requested range
                //read from pc.fc to pcdFcEnd
                //get count of bytes
                int cb = pcdFcEnd - (int)pcd.fc;
                byte[] bytes = new byte[cb];
                //read all bytes
                wordStream.read(bytes,0,cb,(int)pcd.fc);
                //get the chars
                char[] plainChars = new String(bytes, pcd.encoding.getString()).toCharArray();
                for (char c : plainChars)
                {
                    //add to list
                    chars.add(c);
                }
            }
            else if (fcStart < pcd.fc && fcEnd >= pcd.fc && fcEnd <= pcdFcEnd)
            {
                //requested char range ends at this piece
                //read from pcd.fc to fcEnd
                //get count of bytes
                int cb = fcEnd - (int)pcd.fc;
                byte[] bytes = new byte[cb];
                //read all bytes
                wordStream.read(bytes,0,cb,(int)pcd.fc);
                //get the chars
                char[] plainChars = new String(bytes, pcd.encoding.getString()).toCharArray();
                for (char c : plainChars)
                {
                    //add to list
                    chars.add(c);
                }
                break;
            }
            else if (fcStart >= pcd.fc && fcEnd <= pcdFcEnd)
            {
                //requested chars are completly in this piece
                //read from fcStart to fcEnd
                //get count of bytes
                int cb = fcEnd - fcStart;
                byte[] bytes = new byte[cb];
                //read all bytes
                wordStream.read(bytes,0,cb,((int)(fcStart)));
                //get the chars
                char[] plainChars = new String(bytes, pcd.encoding.getString()).toCharArray();
                //set the list
                chars = new CSList<Character>(plainChars);
                break;
            }
            else if (fcEnd < pcd.fc)
            {
                break;
            }
                  
        }
        return chars;
    }

}


//this piece is beyond the requested range