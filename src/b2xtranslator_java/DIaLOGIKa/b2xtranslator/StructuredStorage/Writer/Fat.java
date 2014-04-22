//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DiFatInconsistentException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.AbstractFat;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageContext;

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
* Class which represents the fat of a structured storage.
* Author: math
*/
public class Fat  extends AbstractFat 
{
    CSList<Long> _diFatEntries = new CSList<Long>();
    // Number of sectors used by the fat.
    long _numFatSectors;
    public long getNumFatSectors() throws Exception {
        return _numFatSectors;
    }

    // Number of sectors used by the difat.
    long _numDiFatSectors;
    public long getNumDiFatSectors() throws Exception {
        return _numDiFatSectors;
    }

    // Start sector of the difat.
    long _diFatStartSector;
    public long getDiFatStartSector() throws Exception {
        return _diFatStartSector;
    }

    /**
    * Constructor
    * 
    *  @param context the current context
    */
    public Fat(StructuredStorageContext context) throws Exception {
        super(context);
    }

    /**
    * Writes the difat entries to the fat
    * 
    *  @param sectorCount Number of difat sectors.
    *  @return Start sector of the difat.
    */
    private long writeDiFatEntriesToFat(long sectorCount) throws Exception {
        if (sectorCount == 0)
        {
            return SectorId.ENDOFCHAIN;
        }
         
        long startSector = _currentEntry;
        for (int i = 0;i < sectorCount;i++)
        {
            _currentEntry++;
            _entries.add(SectorId.DIFSECT);
        }
        return startSector;
    }

    /**
    * Writes the difat sectors to the output stream of the current context
    * 
    *  @param fatStartSector
    */
    private void writeDiFatSectorsToStream(long fatStartSector) throws Exception {
        for (long i = 0;i < _numFatSectors;i++)
        {
            // Add all entries of the difat
            _diFatEntries.add(fatStartSector + i);
        }
        for (int i = 0;i < 109;i++)
        {
            // Write the first 109 entries into the header
            if (i < _diFatEntries.size())
            {
                _context.getHeader().writeNextDiFatSector(_diFatEntries.get(i));
            }
            else
            {
                _context.getHeader().writeNextDiFatSector(SectorId.FREESECT);
            } 
        }
        if (_diFatEntries.size() <= 109)
        {
            return ;
        }
         
        // handle remaining difat entries
        CSList<Long> greaterDiFatEntries = new CSList<Long>();
        for (int i = 0;i < _diFatEntries.size() - 109;i++)
        {
            greaterDiFatEntries.add(_diFatEntries.get(i + 109));
        }
        long diFatLink = _diFatStartSector + 1;
        int addressesInSector = _context.getHeader().getSectorSize() / 4;
        int sectorSplit = addressesInSector;
        while (greaterDiFatEntries.size() >= sectorSplit)
        {
            // split difat at sector boundary and add link to next difat sector
            greaterDiFatEntries.add(sectorSplit - 1, diFatLink);
            diFatLink++;
            sectorSplit += addressesInSector;
        }
        for (int i = greaterDiFatEntries.size();i % (_context.getHeader().getSectorSize() / 4) != 0;i++)
        {
            // pad sector
            greaterDiFatEntries.add(SectorId.FREESECT);
        }
        greaterDiFatEntries.remove((int));
        greaterDiFatEntries.add(SectorId.ENDOFCHAIN);
        CSList<Byte> output = _context.getInternalBitConverter().getBytes(greaterDiFatEntries);
        // consistency check
        if (output.size() % _context.getHeader().getSectorSize() != 0)
        {
            throw new DiFatInconsistentException();
        }
         
        // write remaining difat sectors to stream
        _context.getTempOutputStream().writeSectors(((byte[]) output.toArray()),_context.getHeader().getSectorSize(),SectorId.FREESECT);
    }

    /**
    * Marks the difat and fat sectors in the fat and writes the difat and fat data to the output stream of the current context.
    */
    public void write() throws Exception {
        // calculation of _numFatSectors and _numDiFatSectors (depending on each other)
        _numDiFatSectors = 0;
        while (true)
        {
            long numDiFatSectorsOld = _numDiFatSectors;
            _numFatSectors = (long)Math.Ceiling((double)(_entries.size() * 4) / (double)_context.getHeader().getSectorSize()) + _numDiFatSectors;
            _numDiFatSectors = (_numFatSectors <= 109) ? 0 : (long)Math.Ceiling((double)((_numFatSectors - 109) * 4) / (double)(_context.getHeader().getSectorSize() - 1));
            if (numDiFatSectorsOld == _numDiFatSectors)
            {
                break;
            }
             
        }
        // writeDiFat
        _diFatStartSector = writeDiFatEntriesToFat(_numDiFatSectors);
        writeDiFatSectorsToStream(_currentEntry);
        for (int i = 0;i < _numFatSectors;i++)
        {
            // Denote Fat entries in Fat
            _entries.add(SectorId.FATSECT);
        }
        // write Fat
        _context.getTempOutputStream().writeSectors(((byte[]) (_context.getInternalBitConverter().getBytes(_entries)).toArray()),_context.getHeader().getSectorSize(),SectorId.FREESECT);
    }

}


