//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.AbstractHeader;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.MagicNumberException;
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
* Encapsulates the header of a compound file
* Author: math
*/
public class Header  extends AbstractHeader 
{
    /**
    * Constructor
    * 
    *  @param fileHandler The Handle to the file handler of the compound file
    */
    public Header(InputHandler fileHandler) throws Exception {
        _ioHandler = fileHandler;
        _ioHandler.setHeaderReference(this);
        readHeader();
    }

    /**
    * Reads the header from the file stream
    */
    private void readHeader() throws Exception {
        InputHandler fileHandler = ((InputHandler)_ioHandler);
        // Determine endian
        byte[] byteArray16 = new byte[2];
        fileHandler.readPosition(byteArray16,0x1C);
        if (byteArray16[0] == 0xFE && byteArray16[1] == 0xFF)
        {
            fileHandler.initBitConverter(true);
        }
        else
        {
            fileHandler.initBitConverter(false);
        } 
        // Check for Magic Number
        if (fileHandler.readUInt64(0x0) != MAGIC_NUMBER)
        {
            throw new MagicNumberException();
        }
         
        setSectorShift(fileHandler.readUInt16(0x1E));
        setMiniSectorShift(fileHandler.readUInt16());
        setNoSectorsInDirectoryChain4KB(fileHandler.readUInt32(0x28));
        setNoSectorsInFatChain(fileHandler.readUInt32());
        setDirectoryStartSector(fileHandler.readUInt32());
        setMiniSectorCutoff(fileHandler.readUInt32(0x38));
        setMiniFatStartSector(fileHandler.readUInt32());
        setNoSectorsInMiniFatChain(fileHandler.readUInt32());
        setDiFatStartSector(fileHandler.readUInt32());
        setNoSectorsInDiFatChain(fileHandler.readUInt32());
    }

}


