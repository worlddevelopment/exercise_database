//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ChainSizeMismatchException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.AbstractFat;
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
* Represents the Fat in a compound file
* Author: math
*/
public class Fat  extends AbstractFat 
{
    CSList<Long> _sectorsUsedByFat = new CSList<Long>();
    CSList<Long> _sectorsUsedByDiFat = new CSList<Long>();
    public UInt16 getSectorSize() throws Exception {
        return _header.getSectorSize();
    }

    /**
    * Constructor
    * 
    *  @param header Handle to the header of the compound file
    *  @param fileHandler Handle to the file handler of the compound file
    */
    public Fat(Header header, InputHandler fileHandler) throws Exception {
        super(header, fileHandler);
        init();
    }

    /**
    * Seeks to a given position in a sector
    * 
    *  @param sector The sector to seek to
    *  @param position The position in the sector to seek to
    *  @return The new position in the stream.
    */
    public long seekToPositionInSector(long sector, long position) throws Exception {
        return _fileHandler.seekToPositionInSector(sector,position);
    }

    /**
    * Returns the next sector in a chain
    * 
    *  @param currentSector The current sector in the chain
    *  @return The next sector in the chain
    */
    protected long getNextSectorInChain(long currentSector) throws Exception {
        long sectorInFile = _sectorsUsedByFat.get((int)(currentSector / _addressesPerSector));
        // calculation of position:
        // currentSector % _addressesPerSector = number of address in the sector address
        // address uses 32 bit = 4 bytes
        _fileHandler.SeekToPositionInSector(sectorInFile, 4 * (currentSector % _addressesPerSector));
        return _fileHandler.readUInt32();
    }

    /**
    * Initalizes the Fat
    */
    private void init() throws Exception {
        readFirst109SectorsUsedByFAT();
        readSectorsUsedByFatFromDiFat();
        checkConsistency();
    }

    /**
    * Reads the first 109 sectors of the Fat stored in the header
    */
    private void readFirst109SectorsUsedByFAT() throws Exception {
        // Header sector: -1
        _fileHandler.seekToPositionInSector(-1,0x4C);
        long fatSector;
        for (int i = 0;i < 109;i++)
        {
            fatSector = _fileHandler.readUInt32();
            if (fatSector == SectorId.FREESECT)
            {
                break;
            }
             
            _sectorsUsedByFat.add(fatSector);
        }
    }

    /**
    * Reads the sectors of the Fat which are stored in the DiFat
    */
    private void readSectorsUsedByFatFromDiFat() throws Exception {
        if (_header.getDiFatStartSector() == SectorId.ENDOFCHAIN || _header.getNoSectorsInDiFatChain() == 0x0)
        {
            return ;
        }
         
        _fileHandler.SeekToSector(_header.getDiFatStartSector());
        boolean lastFatSectorFound = false;
        _sectorsUsedByDiFat.add(_header.getDiFatStartSector());
        while (true)
        {
            for (int i = 0;i < _addressesPerSector - 1;i++)
            {
                // Add all addresses contained in the current difat sector except the last address (it points to next difat sector)
                long fatSector = _fileHandler.readUInt32();
                if (fatSector == SectorId.FREESECT)
                {
                    lastFatSectorFound = true;
                    break;
                }
                 
                _sectorsUsedByFat.add(fatSector);
            }
            if (lastFatSectorFound)
            {
                break;
            }
             
            // Last address in difat sector points to next difat sector
            long nextDiFatSector = _fileHandler.readUInt32();
            if (nextDiFatSector == SectorId.FREESECT || nextDiFatSector == SectorId.ENDOFCHAIN)
            {
                break;
            }
             
            _sectorsUsedByDiFat.add(nextDiFatSector);
            _fileHandler.SeekToSector(nextDiFatSector);
            if (_sectorsUsedByDiFat.size() > _header.getNoSectorsInDiFatChain())
            {
                throw new ChainSizeMismatchException("DiFat");
            }
             
        }
    }

    /**
    * Checks whether the sizes specified in the header matches the actual sizes
    */
    private void checkConsistency() throws Exception {
        if (_sectorsUsedByDiFat.size() != _header.getNoSectorsInDiFatChain() || _sectorsUsedByFat.size() != _header.getNoSectorsInFatChain())
        {
            throw new ChainSizeMismatchException("Fat/DiFat");
        }
         
    }

}


