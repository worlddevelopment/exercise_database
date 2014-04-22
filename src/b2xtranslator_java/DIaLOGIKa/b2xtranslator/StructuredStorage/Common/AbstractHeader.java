//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common;

import java.math.BigInteger;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.AbstractIOHandler;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.InvalidValueInHeaderException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.UnsupportedSizeException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ValueNotZeroException;

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
* Abstract class fo the header of a compound file.
* Author: math
*/
abstract public class AbstractHeader   
{
    protected static final BigInteger MAGIC_NUMBER = new BigInteger("0xE11AB1A1E011CFD0");
    protected AbstractIOHandler _ioHandler;
    // Sector shift and sector size
    private short _sectorShift = 0;//new UInt16();//<--better shift by 0 by default for a shift.
    public short getSectorShift() throws Exception {
        return _sectorShift;
    }

    public void setSectorShift(short value) throws Exception {
        _sectorShift = value;
        // Calculate sector size
        _sectorSize = (short)Math.pow((double)2,(double)_sectorShift);
        if (_sectorShift != 9 && _sectorShift != 12)
        {
            throw new UnsupportedSizeException("SectorShift");
        }
         
    }

    private short _sectorSize = 0;//new UInt16();
    public short getSectorSize() throws Exception {
        return _sectorSize;
    }

    // Minisector shift and Minisector size
    private short _miniSectorShift = 0;//new UInt16();
    public short getMiniSectorShift() throws Exception {
        return _miniSectorShift;
    }

    public void setMiniSectorShift(short value) throws Exception {
        _miniSectorShift = value;
        // Calculate mini sector size
        _miniSectorSize = (short)Math.pow((double)2,(double)_miniSectorShift);
        if (_miniSectorShift != 6)
        {
            throw new UnsupportedSizeException("MiniSectorShift");
        }
         
    }

    private short _miniSectorSize = 0;//new UInt16();
    public short getMiniSectorSize() throws Exception {
        return _miniSectorSize;
    }

    // CSectDir
    private BigInteger _noSectorsInDirectoryChain4KB;
    public BigInteger getNoSectorsInDirectoryChain4KB() throws Exception {
        return _noSectorsInDirectoryChain4KB;
    }

    public void setNoSectorsInDirectoryChain4KB(BigInteger value) throws Exception {
        if (_sectorSize == 512 && value.compareTo(BigInteger.ZERO) != 0)
        {
            throw new ValueNotZeroException("_csectDir");
        }
         
        _noSectorsInDirectoryChain4KB = value;
    }

    // CSectFat
    private BigInteger _noSectorsInFatChain;
    public BigInteger getNoSectorsInFatChain() throws Exception {
        return _noSectorsInFatChain;
    }

    public void setNoSectorsInFatChain(BigInteger value) throws Exception {
        _noSectorsInFatChain = value;
        if (value.compareTo(_ioHandler.getIOStreamSize().divide(BigInteger.valueOf(getSectorSize())) ) > 0)
        {
            throw new InvalidValueInHeaderException("NoSectorsInFatChain");
        }
         
    }

    // SectDirStart
    private BigInteger _directoryStartSector;
    public BigInteger getDirectoryStartSector() throws Exception {
        return _directoryStartSector;
    }

    public void setDirectoryStartSector(BigInteger value) throws Exception {
        _directoryStartSector = value;
        if (value.compareTo( _ioHandler.getIOStreamSize().divide( BigInteger.valueOf(getSectorSize()) ) ) > 0
        		&& value.compareTo(SectorId.ENDOFCHAIN) != 0)
        {
            throw new InvalidValueInHeaderException("DirectoryStartSector");
        }
         
    }

    // UInt32ULMiniSectorCutoff
    private BigInteger _miniSectorCutoff;
    public BigInteger getMiniSectorCutoff() throws Exception {
        return _miniSectorCutoff;
    }

    public void setMiniSectorCutoff(BigInteger value) throws Exception {
        _miniSectorCutoff = value;
        if (value.compareTo(BigInteger.valueOf(0x1000)) != 0)
        {
            throw new UnsupportedSizeException("MiniSectorCutoff");
        }
         
    }

    // SectMiniFatStart
    private BigInteger _miniFatStartSector;
    public BigInteger getMiniFatStartSector() throws Exception {
        return _miniFatStartSector;
    }

    public void setMiniFatStartSector(BigInteger value) throws Exception {
        _miniFatStartSector = value;
        if (value.compareTo(_ioHandler.getIOStreamSize().divide(BigInteger.valueOf(getSectorSize()) )) > 0 && value != SectorId.ENDOFCHAIN)
        {
            throw new InvalidValueInHeaderException("MiniFatStartSector");
        }
         
    }

    // CSectMiniFat
    private BigInteger _noSectorsInMiniFatChain;
    public BigInteger getNoSectorsInMiniFatChain() throws Exception {
        return _noSectorsInMiniFatChain;
    }

    public void setNoSectorsInMiniFatChain(BigInteger value) throws Exception {
        _noSectorsInMiniFatChain = value;
        if (value.compareTo(_ioHandler.getIOStreamSize().divide(BigInteger.valueOf(getSectorSize()))) > 0)
        {
            throw new InvalidValueInHeaderException("NoSectorsInMiniFatChain");
        }
         
    }

    // SectDifStart
    private BigInteger _diFatStartSector;
    public BigInteger getDiFatStartSector() throws Exception {
        return _diFatStartSector;
    }

    public void setDiFatStartSector(BigInteger value) throws Exception {
        _diFatStartSector = value;
        if (value.compareTo(_ioHandler.getIOStreamSize().divide(BigInteger.valueOf(getSectorSize())) ) > 0 && value != SectorId.ENDOFCHAIN)
        {
            throw new InvalidValueInHeaderException("DiFatStartSector");
        }
         
    }

    // CSectDif
    private BigInteger _noSectorsInDiFatChain;
    public BigInteger getNoSectorsInDiFatChain() throws Exception {
        return _noSectorsInDiFatChain;
    }

    public void setNoSectorsInDiFatChain(BigInteger value) throws Exception {
        _noSectorsInDiFatChain = value;
        if (value.compareTo(_ioHandler.getIOStreamSize().divide(BigInteger.valueOf(getSectorSize()))) > 0)
        {
            throw new InvalidValueInHeaderException("NoSectorsInDiFatChain");
        }
         
    }

}


