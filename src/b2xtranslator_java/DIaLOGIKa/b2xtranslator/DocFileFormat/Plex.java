//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:06 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
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
public class Plex <T>  
{
    protected static final int CP_LENGTH = 4;
    public CSList<Integer> CharacterPositions;
    public CSList<T> Elements;
    public Plex(int structureLength, VirtualStream tableStream, long fc, long lcb) throws Exception {
        tableStream.Seek((long)fc, System.IO.SeekOrigin.Begin);
        VirtualStreamReader reader = new VirtualStreamReader(tableStream);
        int n = 0;
        if (structureLength > 0)
        {
            //this PLEX contains CPs and Elements
            n = ((int)lcb - CP_LENGTH) / (structureLength + CP_LENGTH);
        }
        else
        {
            //this PLEX only contains CPs
            n = ((int)lcb - CP_LENGTH) / CP_LENGTH;
        } 
        //read the n + 1 CPs
        this.CharacterPositions = new CSList<Integer>();
        for (int i = 0;i < n + 1;i++)
        {
            this.CharacterPositions.add(reader.readInt32());
        }
        //read the n structs
        this.Elements = new CSList<T>();
        Class genericType = T.class;
        if (genericType == short.class)
        {
            this.Elements = new CSList<T>();
            for (int i = 0;i < n;i++)
            {
                short value = reader.readInt16();
                T genericValue = (T)Convert.ChangeType(value, T.class);
                this.Elements.add(genericValue);
            }
        }
        else if (structureLength > 0)
        {
            for (int i = 0;i < n;i++)
            {
                ConstructorInfo constructor = genericType.GetConstructor(new Class[]{ VirtualStreamReader.class, int.class });
                Object value = constructor.Invoke(new Object[]{ reader, structureLength });
                T genericValue = (T)Convert.ChangeType(value, T.class);
                this.Elements.add(genericValue);
            }
        }
          
    }

    /**
    * Retruns the struct that matches the given character position.
    * 
    *  @param cp The character position
    *  @return The matching struct
    */
    public T getStruct(int cp) throws Exception {
        int index = -1;
        for (int i = 0;i < this.CharacterPositions.size();i++)
        {
            if (this.CharacterPositions.get(i) == cp)
            {
                index = i;
                break;
            }
             
        }
        if (index >= 0 && index < this.Elements.size())
        {
            return this.Elements.get(index);
        }
        else
        {
            return /* [UNSUPPORTED] default expressions are not yet supported "default T" */;
        } 
    }

}


