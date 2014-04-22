//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.AbstractDirectoryEntry;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryColor;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryType;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.Header;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.InputHandler;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

import java.math.BigInteger;
import java.util.UUID;

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
* Encapsulates a directory entry
* Author: math
*/
public class DirectoryEntry  extends AbstractDirectoryEntry 
{
    InputHandler _fileHandler;
    Header _header;
    /**
    * Constructor
    * 
    *  @param header Handle to the header of the compound file
    *  @param fileHandler Handle to the file handler of the compound file
    *  @param sid The sid of the directory entry
    */
    public DirectoryEntry(Header header, InputHandler fileHandler, BigInteger sid, String path) throws Exception {
        super(sid);
        _header = header;
        _fileHandler = fileHandler;
        //_sid = sid;
        readDirectoryEntry();
        _path = path;
    }

    /**
    * Reads the values of the directory entry. The position of the file handler must be at the start of a directory entry.
    * TODO if replacing all BigInteger to BigInteger was okay.
    */
    private void readDirectoryEntry() throws Exception {
        setName(_fileHandler.readString(64));
        // Name length check: lengthOfName = length of the element in bytes including Unicode NULL
        short lengthOfName = _fileHandler.readUInt16();
        // Commented out due to trouble with odd unicode-named streams in PowerPoint -- flgr
        /*if (lengthOfName != (_name.Length + 1) * 2)
                    {
                        throw new InvalidValueInDirectoryEntryException("_cb");
                    }*/
        // Added warning - math
        if (lengthOfName != (_name.length() + 1) * 2)
        {
            TraceLogger.warning("Length of the name (_cb) of stream '" + getName() + "' is not correct.");
        }
         
        setType((DirectoryEntryType)_fileHandler.readByte());
        setColor((DirectoryEntryColor)_fileHandler.readByte());
        setLeftSiblingSid(_fileHandler.readUInt32());
        setRightSiblingSid(_fileHandler.readUInt32());
        setChildSiblingSid(_fileHandler.readUInt32());
        byte[] array = new byte[16];
        _fileHandler.read(array);
        setClsId(new UUID(array));
        setUserFlags(_fileHandler.readUInt32());
        // Omit creation time
        _fileHandler.readUInt64();
        // Omit modification time
        _fileHandler.readUInt64();
        setStartSector(_fileHandler.readUInt32());
        short sizeLow = _fileHandler.readUInt32();
        short sizeHigh = _fileHandler.readUInt32();
        if (_header.getSectorSize() == 512 && sizeHigh != 0x0)
        {
            // Must be zero according to the specification. However, this requirement can be ommited.
            TraceLogger.warning("ul_SizeHigh of stream '" + getName() + "' should be zero as sector size is 512.");
            sizeHigh = 0x0;
        }
         
        setSizeOfStream(((BigInteger)sizeHigh << 32) + sizeLow);
    }

}


