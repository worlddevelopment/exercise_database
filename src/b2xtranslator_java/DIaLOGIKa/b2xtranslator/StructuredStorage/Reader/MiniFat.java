//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ChainSizeMismatchException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.AbstractFat;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.Fat;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.Header;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.InputHandler;

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
* Represents the MiniFat in a compound file
* Author: math
*/
public class MiniFat  extends AbstractFat 
{
    CSList<Long> _sectorsUsedByMiniFat = new CSList<Long>();
    CSList<Long> _sectorsUsedByMiniStream = new CSList<Long>();
    Fat _fat;
    long _miniStreamStart;
    UInt64 _sizeOfMiniStream = new UInt64();
    public UInt16 getSectorSize() throws Exception {
        return _header.getMiniSectorSize();
    }

    /**
    * Constructor
    * 
    *  @param fat Handle to the Fat of the compound file
    *  @param header Handle to the header of the compound file
    *  @param fileHandler Handle to the file handler of the compound file
    *  @param miniStreamStart Address of the sector where the mini stream starts
    */
    public MiniFat(Fat fat, Header header, InputHandler fileHandler, long miniStreamStart, UInt64 sizeOfMiniStream) throws Exception {
        super(header, fileHandler);
        _fat = fat;
        _miniStreamStart = miniStreamStart;
        _sizeOfMiniStream = sizeOfMiniStream;
        init();
    }

    /**
    * Seeks to a given position in a sector of the mini stream
    * 
    *  @param sector The sector to seek to
    *  @param position The position in the sector to seek to
    *  @return The new position in the stream.
    */
    public long seekToPositionInSector(long sector, long position) throws Exception {
        int sectorInMiniStreamChain = (int)((sector * _header.getMiniSectorSize()) / _fat.getSectorSize());
        int offsetInSector = (int)((sector * _header.getMiniSectorSize()) % _fat.getSectorSize());
        if (position < 0)
        {
            throw new ArgumentOutOfRangeException("position");
        }
         
        return _fileHandler.SeekToPositionInSector(_sectorsUsedByMiniStream.get(sectorInMiniStreamChain), offsetInSector + position);
    }

    /**
    * Returns the next sector in a chain
    * 
    *  @param currentSector The current sector in the chain
    *  @return The next sector in the chain
    */
    protected long getNextSectorInChain(long currentSector) throws Exception {
        long sectorInFile = _sectorsUsedByMiniFat.get((int)(currentSector / _addressesPerSector));
        // calculation of position:
        // currentSector % _addressesPerSector = number of address in the sector address
        // address uses 32 bit = 4 bytes
        _fileHandler.SeekToPositionInSector(sectorInFile, 4 * ((int)currentSector % _addressesPerSector));
        return _fileHandler.readUInt32();
    }

    /**
    * Initalizes the Fat
    */
    private void init() throws Exception {
        readSectorsUsedByMiniFAT();
        readSectorsUsedByMiniStream();
        checkConsistency();
    }

    /**
    * Reads the sectors used by the MiniFat
    */
    private void readSectorsUsedByMiniFAT() throws Exception {
        if (_header.getMiniFatStartSector() == SectorId.ENDOFCHAIN || _header.getNoSectorsInMiniFatChain() == 0x0)
        {
            return ;
        }
         
        _sectorsUsedByMiniFat = _fat.GetSectorChain(_header.getMiniFatStartSector(), _header.getNoSectorsInMiniFatChain(), "MiniFat");
    }

    /**
    * Reads the sectors used by the MiniFat
    */
    private void readSectorsUsedByMiniStream() throws Exception {
        if (_miniStreamStart == SectorId.ENDOFCHAIN)
        {
            return ;
        }
         
        _sectorsUsedByMiniStream = _fat.getSectorChain(_miniStreamStart,(UInt64)Math.Ceiling((double)_sizeOfMiniStream / _header.getSectorSize()),"MiniStream");
    }

    /**
    * Checks whether the size specified in the header matches the actual size
    */
    private void checkConsistency() throws Exception {
        if (_sectorsUsedByMiniFat.size() != _header.getNoSectorsInMiniFatChain())
        {
            throw new ChainSizeMismatchException("MiniFat");
        }
         
        if (_sectorsUsedByMiniStream.size() != Math.Ceiling((double)_sizeOfMiniStream / _header.getSectorSize()))
        {
            throw new ChainSizeMismatchException("MiniStream");
        }
         
    }

}


