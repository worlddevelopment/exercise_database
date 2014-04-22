//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryColor;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryType;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.InvalidValueInDirectoryEntryException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.MaskingHandler;

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
* Abstract class for a directory entry in a structured storage.
* Athor: math
* J.R.I.B.-Wein:
* http://stackoverflow.com/questions/5322949/convert-c-sharp-datatype-to-equivalent-java-datatype
*/
abstract public class AbstractDirectoryEntry   
{
    BigInteger _sid;
    public BigInteger getSid() throws Exception {
        return _sid;
    }

    public void setSid(BigInteger value) throws Exception {
        _sid = value;
    }

    protected String _path;
    public String getPath() throws Exception {
        return _path + getName();
    }

    // Name
    protected String _name;
    public String getName() throws Exception {
        return MaskingHandler.mask(_name);
    }

    public void setName(String value) throws Exception {
        _name = value;
        if (_name.length() >= 32)
        {
            throw new InvalidValueInDirectoryEntryException("_ab");
        }
         
    }

    short _lengthOfName = 0;//new UInt16();
    public short getLengthOfName() throws Exception {
        if (_name.length() == 0)
        {
            _lengthOfName = 0;
            return 0;
        }
         
        // length of name in bytes including unicode 0;
        _lengthOfName = (short)((_name.length() + 1) * 2);
        return _lengthOfName;
    }

    // Type
    DirectoryEntryType _type = DirectoryEntryType.STGTY_INVALID;
    public DirectoryEntryType getType() throws Exception {
        return _type;
    }

    public void setType(DirectoryEntryType value) throws Exception {
        if ((value).ordinal() < 0 || (value).ordinal() > 5)
        {
            throw new InvalidValueInDirectoryEntryException("_mse");
        }
         
        _type = value;
    }

    // Color
    DirectoryEntryColor _color = DirectoryEntryColor.DE_RED;
    public DirectoryEntryColor getColor() throws Exception {
        return _color;
    }

    public void setColor(DirectoryEntryColor value) throws Exception {
        if ( (value).ordinal() < 0 || (value).ordinal() > 1 )
        {
            throw new InvalidValueInDirectoryEntryException("_bflags");
        }
         
        _color = value;
    }

    // Left sibling sid
    BigInteger _leftSiblingSid;
    public BigInteger getLeftSiblingSid() throws Exception {
        return _leftSiblingSid;
    }

    public void setLeftSiblingSid(BigInteger value) throws Exception {
        _leftSiblingSid = value;
    }

    // Right sibling sid
    BigInteger _rightSiblingSid;
    public BigInteger getRightSiblingSid() throws Exception {
        return _rightSiblingSid;
    }

    public void setRightSiblingSid(BigInteger value) throws Exception {
        _rightSiblingSid = value;
    }

    // Child sibling sid
    BigInteger _childSiblingSid;
    public BigInteger getChildSiblingSid() throws Exception {
        return _childSiblingSid;
    }

    public void setChildSiblingSid(BigInteger value) throws Exception {
        _childSiblingSid = value;
    }

    //CLSID
    UUID _clsId;
    public UUID getClsId() throws Exception {
        return _clsId;
    }

    public void setClsId(UUID value) throws Exception {
        _clsId = value;
    }

    // User flags
    BigInteger _userFlags;
    public BigInteger getUserFlags() throws Exception {
        return _userFlags;
    }

    public void setUserFlags(BigInteger value) throws Exception {
        _userFlags = value;
    }

    // Start sector
    BigInteger _startSector;
    public BigInteger getStartSector() throws Exception {
        return _startSector;
    }

    public void setStartSector(BigInteger value) throws Exception {
        _startSector = value;
    }

    // Size of stream in bytes
    BigInteger _sizeOfStream;
    public BigInteger getSizeOfStream() throws Exception {
        return _sizeOfStream;
    }

    public void setSizeOfStream(BigInteger value) throws Exception {
        _sizeOfStream = value;
    }

    public AbstractDirectoryEntry() throws Exception {
        this(null/*0x0*/);
    }

    public AbstractDirectoryEntry(BigInteger sid) throws Exception {
        _sid = sid;
    }

}


