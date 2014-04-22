//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:38 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.Ptg0x18Sub;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.Ptg0x19Sub;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgNumber;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import java.util.Locale;

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
public abstract class AbstractPtg   
{
    IStreamReader _reader;
    PtgNumber _id = PtgNumber.PtgExp;
    long _offset;
    String data;
    uint length;
    protected uint popSize;
    protected PtgType type = PtgType.Operand;
    /**
    * Ctor
    * 
    *  @param reader Streamreader
    *  @param id Ptg Id
    *  @param length The recordlength
    */
    public AbstractPtg(IStreamReader reader, PtgNumber ptgid) throws Exception {
        _reader = reader;
        _offset = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        _id = ptgid;
        this.data = "";
    }

    /**
    * Ctor
    * 
    *  @param reader Streamreader
    *  @param id Ptg Id
    *  @param length The recordlength
    */
    public AbstractPtg(IStreamReader reader, Ptg0x18Sub ptgid) throws Exception {
        _reader = reader;
        _offset = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        _id = (PtgNumber)ptgid;
        this.data = "";
    }

    /**
    * Ctor
    * 
    *  @param reader Streamreader
    *  @param id Ptg Id
    *  @param length The recordlength
    */
    public AbstractPtg(IStreamReader reader, Ptg0x19Sub ptgid) throws Exception {
        _reader = reader;
        _offset = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        _id = (PtgNumber)ptgid;
        this.data = "";
    }

    public PtgNumber getId() throws Exception {
        return _id;
    }

    public long getOffset() throws Exception {
        return _offset;
    }

    public IStreamReader getReader() throws Exception {
        return _reader;
    }

    public void setReader(IStreamReader value) throws Exception {
        this._reader = value;
    }

    protected String getData() throws Exception {
        return this.data;
    }

    protected void setData(String value) throws Exception {
        this.data = value;
    }

    protected uint getLength() throws Exception {
        return this.length;
    }

    protected void setLength(uint value) throws Exception {
        this.length = value;
    }

    public uint getLength() throws Exception {
        return this.length;
    }

    public String getData() throws Exception {
        return Convert.ToString(this.data, Locale.GetCultureInfo("en-US"));
    }

    public uint popSize() throws Exception {
        return this.popSize;
    }

    public PtgType opType() throws Exception {
        return this.type;
    }

}


