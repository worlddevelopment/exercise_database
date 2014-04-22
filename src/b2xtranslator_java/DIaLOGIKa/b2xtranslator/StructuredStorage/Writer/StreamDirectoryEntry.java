//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.BaseDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageContext;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.VirtualStream;
import java.io.InputStream;

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
* Represents a stream directory entry in a structured storage.
* Author: math
*/
public class StreamDirectoryEntry  extends BaseDirectoryEntry 
{
    InputStream _stream;
    /**
    * Constructor.
    * 
    *  @param name Name of the stream directory entry.
    *  @param stream The stream referenced by the stream directory entry.
    *  @param context The current context.
    */
    public StreamDirectoryEntry(String name, InputStream stream, StructuredStorageContext context) throws Exception {
        super(name, context);
        _stream = stream;
        setType(DirectoryEntryType.STGTY_STREAM);
    }

    /**
    * Writes the referenced stream chain to the fat and the referenced stream data to the output stream of the current context.
    */
    public void writeReferencedStream() throws Exception {
        VirtualStream vStream = null;
        if (_stream.Length < getContext().getHeader().getMiniSectorCutoff())
        {
            vStream = new VirtualStream(_stream,getContext().getMiniFat(),getContext().getHeader().getMiniSectorSize(),getContext().getRootDirectoryEntry().getMiniStream());
        }
        else
        {
            vStream = new VirtualStream(_stream,getContext().getFat(),getContext().getHeader().getSectorSize(),getContext().getTempOutputStream());
        } 
        vStream.write();
        this.setStartSector(vStream.getStartSector());
        this.setSizeOfStream(vStream.getLength());
    }

}


