//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.OutputHandler;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StorageDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageContext;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.VirtualStream;

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
* Class which represents the root directory entry of a structured storage.
* Author: math
*/
public class RootDirectoryEntry  extends StorageDirectoryEntry 
{
    // The mini stream.
    OutputHandler _miniStream = new OutputHandler(new MemoryStream());
    public OutputHandler getMiniStream() throws Exception {
        return _miniStream;
    }

    /**
    * Constructor.
    * 
    *  @param context the current context
    */
    public RootDirectoryEntry(StructuredStorageContext context) throws Exception {
        super("Root Entry", context);
        setType(DirectoryEntryType.STGTY_ROOT);
        setSid(0x0);
    }

    /**
    * Writes the mini stream chain to the fat and the mini stream data to the output stream of the current context.
    */
    public void writeReferencedStream() throws Exception {
        VirtualStream virtualMiniStream = new VirtualStream(_miniStream.getBaseStream(),getContext().getFat(),getContext().getHeader().getSectorSize(),getContext().getTempOutputStream());
        virtualMiniStream.write();
        this.setStartSector(virtualMiniStream.getStartSector());
        this.setSizeOfStream(virtualMiniStream.getLength());
    }

}


