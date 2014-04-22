//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ChainCycleDetectedException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ChainSizeMismatchException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.InvalidSectorInChainException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.Header;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.InputHandler;

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
* Abstract class of a Fat in a compound file
* Author: math
*/
public abstract class AbstractFat   
{
    protected Header _header;
    protected InputHandler _fileHandler;
    protected int _addressesPerSector;
    /**
    * Constructor
    * 
    *  @param header Handle to the header of the compound file
    *  @param fileHandler Handle to the file handler of the compound file
    */
    public AbstractFat(Header header, InputHandler fileHandler) throws Exception {
        _header = header;
        _fileHandler = fileHandler;
        _addressesPerSector = (int)_header.getSectorSize() / 4;
    }

    /**
    * Returns the sectors in a chain which starts at a given sector
    * 
    *  @param startSector The start sector of the chain
    *  @param maxCount The maximum count of sectors in a chain
    *  @param name The name of a chain
    */
    public List<BigInteger> getSectorChain(BigInteger startSector, BigInteger maxCount, String name) throws Exception {
        return getSectorChain(startSector,maxCount,name,false);
    }

    /**
    * Returns the sectors in a chain which starts at a given sector
    * 
    *  @param startSector The start sector of the chain
    *  @param maxCount The maximum count of sectors in a chain
    *  @param name The name of a chain
    *  @param immediateCycleCheck Flag whether to check for cycles in every loop
    */
    public List<BigInteger> getSectorChain(BigInteger startSector, BigInteger maxCount, String name, boolean immediateCycleCheck) throws Exception {
        List<BigInteger> result = new ArrayList<BigInteger>();
        result.add(startSector);
        while (true)
        {
            BigInteger nextSectorInStream = this.getNextSectorInChain(result.get(result.size() - 1));
            // Check for invalid sectors in chain
            if (nextSectorInStream == SectorId.DIFSECT || nextSectorInStream == SectorId.FATSECT || nextSectorInStream == SectorId.FREESECT)
            {
                throw new InvalidSectorInChainException();
            }
             
            if (nextSectorInStream == SectorId.ENDOFCHAIN)
            {
                break;
            }
             
            if (immediateCycleCheck)
            {
                if (result.contains(nextSectorInStream))
                {
                    throw new ChainCycleDetectedException(name);
                }
                 
            }
             
            result.add(nextSectorInStream);
            // Chain too BigInteger
            if (/*(BigInteger)*/BigInteger.valueOf( /*new Integer(*/result.size()/*).intValue()*/ ).compareTo(maxCount) > 0 /* > maxCount*/)
            {//BigInteger.signum() == 1 => greater than zero
                throw new ChainSizeMismatchException(name);
            }
             
        }
        return result;
    }

    /**
    * Reads bytes into an array
    * 
    *  @param array The array to read to
    *  @param offset The offset in the array to read to
    *  @param count The number of bytes to read
    *  @return The number of bytes read
    */
    public int uncheckedRead(byte[] array, int offset, int count) throws Exception {
        return _fileHandler.uncheckedRead(array,offset,count);
    }

    /**
    * Reads a byte at the current position of the file stream.
    * Advances the stream pointer accordingly.
    */
    public int uncheckedReadByte() throws Exception {
        return _fileHandler.uncheckedReadByte();
    }

    /**
    * Returns the next sector in a chain
    * 
    *  @param currentSector The current sector in the chain
    *  @return The next sector in the chain
    */
    abstract protected BigInteger getNextSectorInChain(BigInteger currentSector) throws Exception ;

    /**
    * Seeks to a given position in a sector
    * 
    *  @param sector The sector to seek to
    *  @param position The position in the sector to seek to
    *  @return
    */
    abstract public BigInteger seekToPositionInSector(BigInteger sector, BigInteger position) throws Exception ;

    abstract public short/*UInt16*/ getSectorSize() throws Exception ;

}


