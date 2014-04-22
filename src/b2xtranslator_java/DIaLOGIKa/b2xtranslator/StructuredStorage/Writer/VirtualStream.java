//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.AbstractFat;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.OutputHandler;
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
* Class which represents a virtual stream in a structured storage.
* Author: math
*/
public class VirtualStream   
{
    AbstractFat _fat;
    InputStream _stream;
    UInt16 _sectorSize = new UInt16();
    OutputHandler _outputHander;
    // Start sector of the virtual stream.
    long _startSector = SectorId.FREESECT;
    public long getStartSector() throws Exception {
        return _startSector;
    }

    // Lengh of the virtual stream.
    public UInt64 getLength() throws Exception {
        return (UInt64)_stream.Length;
    }

    // Number of sectors used by the virtual stream.
    long _sectorCount;
    public long getSectorCount() throws Exception {
        return _sectorCount;
    }

    /**
    * Constructor.
    * 
    *  @param stream The input stream.
    *  @param fat The fat which is used by this stream.
    *  @param sectorSize The sector size.
    *  @param outputHander
    */
    public VirtualStream(InputStream stream, AbstractFat fat, UInt16 sectorSize, OutputHandler outputHander) throws Exception {
        _stream = stream;
        _fat = fat;
        _sectorSize = sectorSize;
        _outputHander = outputHander;
        _sectorCount = (long)Math.Ceiling((double)_stream.Length / (double)_sectorSize);
    }

    /**
    * Writes the virtual stream chain to the fat and the virtual stream data to the output stream of the current context.
    */
    public void write() throws Exception {
        _startSector = _fat.writeChain(getSectorCount());
        BinaryReader reader = new BinaryReader(_stream);
        reader.BaseStream.Seek(0, SeekOrigin.Begin);
        while (true)
        {
            byte[] bytes = reader.ReadBytes((int)_sectorSize);
            _outputHander.writeSectors(bytes,_sectorSize,(byte)0x0);
            if (bytes.length != _sectorSize)
            {
                break;
            }
             
        }
    }

}


