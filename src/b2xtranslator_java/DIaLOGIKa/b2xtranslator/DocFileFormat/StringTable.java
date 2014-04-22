//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:07 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Text.EncodingSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StringTable;
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
public class StringTable   implements IVisitable
{
    public boolean fExtend;
    public int cData;
    public UInt16 cbExtra = new UInt16();
    public CSList<String> Strings;
    public CSList<ByteStructure> Data;
    //public List<ByteStructure> ExtraData;
    private Encoding _enc;
    public StringTable(Class dataType, VirtualStreamReader reader) throws Exception {
        this.Strings = new CSList<String>();
        this.Data = new CSList<ByteStructure>();
        parse(dataType,reader,(long)Unsupported.throwUnsupported("reader.getBaseStream().Position"));
    }

    public StringTable(Class dataType, VirtualStream tableStream, long fc, long lcb) throws Exception {
        this.Strings = new CSList<String>();
        this.Data = new CSList<ByteStructure>();
        if (lcb > 0)
        {
            tableStream.Seek((long)fc, System.IO.SeekOrigin.Begin);
            parse(dataType,new VirtualStreamReader(tableStream),fc);
        }
         
    }

    private void parse(Class dataType, VirtualStreamReader reader, long fc) throws Exception {
        //read fExtend
        if (reader.readUInt16() == 0xFFFF)
        {
            //if the first 2 bytes are 0xFFFF the STTB contains unicode characters
            this.fExtend = true;
            _enc = Encoding.Unicode;
        }
        else
        {
            //else the STTB contains 1byte characters and the fExtend field is non-existend
            //seek back to the beginning
            this.fExtend = false;
            _enc = EncodingSupport.GetEncoder("ASCII");
            reader.getBaseStream().Seek((long)fc, System.IO.SeekOrigin.Begin);
        } 
        //read cData
        long cDataStart = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        UInt16 c = reader.readUInt16();
        if (c != 0xFFFF)
        {
            //cData is a 2byte unsigned Integer and the read bytes are already cData
            this.cData = (int)c;
        }
        else
        {
            //cData is a 4byte signed Integer, so we need to seek back
            reader.getBaseStream().Seek((long)fc + cDataStart, System.IO.SeekOrigin.Begin);
            this.cData = reader.readInt32();
        } 
        //read cbExtra
        this.cbExtra = reader.readUInt16();
        for (int i = 0;i < this.cData;i++)
        {
            //read the strings and extra datas
            int cchData = 0;
            int cbData = 0;
            if (this.fExtend)
            {
                cchData = (int)reader.readUInt16();
                cbData = cchData * 2;
            }
            else
            {
                cchData = (int)reader.readByte();
                cbData = cchData;
            } 
            long posBeforeType = Unsupported.throwUnsupported("reader.getBaseStream().Position");
            if (dataType == String.class)
            {
                //It's a real string table
                this.Strings.add(new String(reader.readBytes(cbData), _enc.getString()));
            }
            else
            {
                //It's a modified string table that contains custom data
                ConstructorInfo constructor = dataType.GetConstructor(new Class[]{ VirtualStreamReader.class, int.class });
                ByteStructure data = (ByteStructure)constructor.Invoke(new Object[]{ reader, cbData });
                this.Data.add(data);
            } 
            reader.getBaseStream().Seek(posBeforeType + cbData, System.IO.SeekOrigin.Begin);
            //skip the extra byte
            reader.ReadBytes(cbExtra);
        }
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<StringTable>)mapping).apply(this);
    }

}


