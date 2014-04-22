//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.InternalBitConverter;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.Fat;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.Header;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.MiniFat;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.OutputHandler;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.RootDirectoryEntry;

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
/**
* Class which pools the different elements of a structured storage in a context.
* Author math.
*/
public class StructuredStorageContext   
{
    private long _sidCounter = 0x0;
    // The header of this context.
    Header _header;
    public Header getHeader() throws Exception {
        return _header;
    }

    // The fat of this context.
    Fat _fat;
    public Fat getFat() throws Exception {
        return _fat;
    }

    // The mini fat of this context.
    MiniFat _miniFat;
    public MiniFat getMiniFat() throws Exception {
        return _miniFat;
    }

    // The handler of the output stream of this context.
    OutputHandler _tempOutputStream;
    public OutputHandler getTempOutputStream() throws Exception {
        return _tempOutputStream;
    }

    // The handler of the directory stream of this context.
    OutputHandler _directoryStream;
    public OutputHandler getDirectoryStream() throws Exception {
        return _directoryStream;
    }

    // The internal bit converter of this context.
    InternalBitConverter _internalBitConverter;
    public InternalBitConverter getInternalBitConverter() throws Exception {
        return _internalBitConverter;
    }

    // The root directroy entry of this context.
    private RootDirectoryEntry _rootDirectoryEntry;
    public RootDirectoryEntry getRootDirectoryEntry() throws Exception {
        return _rootDirectoryEntry;
    }

    /**
    * Constructor.
    */
    public StructuredStorageContext() throws Exception {
        _tempOutputStream = new OutputHandler(new MemoryStream());
        _directoryStream = new OutputHandler(new MemoryStream());
        _header = new Header(this);
        _internalBitConverter = new InternalBitConverter(true);
        _fat = new Fat(this);
        _miniFat = new MiniFat(this);
        _rootDirectoryEntry = new RootDirectoryEntry(this);
    }

    /**
    * Returns a new sid for directory entries in this context.
    * 
    *  @return The new sid.
    */
    public long getNewSid() throws Exception {
        return ++_sidCounter;
    }

}


