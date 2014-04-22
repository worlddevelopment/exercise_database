//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.AbstractHeader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DiFatInconsistentException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.OutputHandler;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageContext;
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
* Class which represents the header of a structured storage.
* Author: math
*/
public class Header  extends AbstractHeader 
{
    CSList<Byte> _diFatSectors = new CSList<Byte>();
    int _diFatSectorCount = 0;
    StructuredStorageContext _context;
    /**
    * Constructor.
    * 
    *  @param context the current context
    */
    public Header(StructuredStorageContext context) throws Exception {
        _ioHandler = new OutputHandler(new MemoryStream());
        _ioHandler.setHeaderReference(this);
        _ioHandler.initBitConverter(true);
        _context = context;
        setHeaderDefaults();
    }

    /**
    * Initializes header defaults.
    */
    void setHeaderDefaults() throws Exception {
        setMiniSectorShift(6);
        setSectorShift(9);
        setNoSectorsInDirectoryChain4KB(0);
        setMiniSectorCutoff(4096);
    }

    /**
    * Writes the next difat sector (which is one of the first 109) to the header.
    * 
    *  @param sector
    */
    public void writeNextDiFatSector(long sector) throws Exception {
        if (_diFatSectorCount >= 109)
        {
            throw new DiFatInconsistentException();
        }
         
        _diFatSectors.addRange(_context.getInternalBitConverter().getBytes(sector));
        _diFatSectorCount++;
    }

    /**
    * Writes the header to the internal stream.
    */
    public void write() throws Exception {
        OutputHandler outputHandler = ((OutputHandler)_ioHandler);
        // Magic number
        outputHandler.write(BitConverter.GetBytes(MAGIC_NUMBER));
        // CLSID
        outputHandler.write(new byte[16]);
        // Minor version
        outputHandler.writeUInt16(0x3E);
        // Major version: 512 KB sectors
        outputHandler.writeUInt16(0x03);
        // Byte ordering: little Endian
        outputHandler.writeUInt16(0xFFFE);
        outputHandler.writeUInt16(getSectorShift());
        outputHandler.writeUInt16(getMiniSectorShift());
        // reserved
        outputHandler.writeUInt16(0x0);
        outputHandler.writeUInt32(0x0);
        // cSectDir: 0x0 for 512 KB
        outputHandler.writeUInt32(getNoSectorsInDirectoryChain4KB());
        outputHandler.writeUInt32(getNoSectorsInFatChain());
        outputHandler.writeUInt32(getDirectoryStartSector());
        // reserved
        outputHandler.writeUInt32(0x0);
        outputHandler.writeUInt32(getMiniSectorCutoff());
        outputHandler.writeUInt32(getMiniFatStartSector());
        outputHandler.writeUInt32(getNoSectorsInMiniFatChain());
        outputHandler.writeUInt32(getDiFatStartSector());
        outputHandler.writeUInt32(getNoSectorsInDiFatChain());
        // First 109 FAT Sectors
        outputHandler.write(((byte[]) _diFatSectors.toArray()));
        // Pad the rest
        if (getSectorSize() == 4096)
        {
            outputHandler.write(new byte[4096 - 512]);
        }
         
    }

    /**
    * Writes the internal header stream to the given stream.
    * 
    *  @param stream The stream to which is written to.
    */
    public void writeToStream(InputStream stream) throws Exception {
        OutputHandler outputHandler = ((OutputHandler)_ioHandler);
        outputHandler.writeToStream(stream);
    }

}


