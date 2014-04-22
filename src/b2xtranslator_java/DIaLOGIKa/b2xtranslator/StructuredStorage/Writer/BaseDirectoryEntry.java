//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.AbstractDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryColor;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.OutputHandler;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageContext;
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
* Common base class for stream and storage directory entries
* Author: math
*/
abstract public class BaseDirectoryEntry  extends AbstractDirectoryEntry 
{
    private StructuredStorageContext _context;
    public StructuredStorageContext getContext() throws Exception {
        return _context;
    }

    /**
    * @param name Name of the directory entry.
    *  @param context the current context
    */
    public BaseDirectoryEntry(String name, StructuredStorageContext context) throws Exception {
        _context = context;
        setName(name);
        setInitialValues();
    }

    /**
    * Set the initial values
    */
    private void setInitialValues() throws Exception {
        this.setChildSiblingSid(SectorId.FREESECT);
        this.setLeftSiblingSid(SectorId.FREESECT);
        this.setRightSiblingSid(SectorId.FREESECT);
        this.setClsId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        this.setColor(DirectoryEntryColor.DE_BLACK);
        this.setStartSector(0x0);
        this.setClsId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        this.setUserFlags(0x0);
        this.setSizeOfStream(0x0);
    }

    /**
    * Writes the directory entry to the directory stream of the current context
    */
    public void write() throws Exception {
        OutputHandler directoryStream = _context.getDirectoryStream();
        char[] unicodeName = _name.toCharArray();
        int paddingCounter = 0;
        for (Object __dummyForeachVar0 : unicodeName)
        {
            UInt16 unicodeChar = (UInt16)__dummyForeachVar0;
            directoryStream.writeUInt16(unicodeChar);
            paddingCounter++;
        }
        while (paddingCounter < 32)
        {
            directoryStream.writeUInt16(0x0);
            paddingCounter++;
        }
        directoryStream.writeUInt16(this.getLengthOfName());
        directoryStream.writeByte((byte)(((Enum)this.getType()).ordinal()));
        directoryStream.writeByte((byte)(((Enum)this.getColor()).ordinal()));
        directoryStream.writeUInt32(this.getLeftSiblingSid());
        directoryStream.writeUInt32(this.getRightSiblingSid());
        directoryStream.writeUInt32(this.getChildSiblingSid());
        directoryStream.write(this.getClsId().ToByteArray());
        directoryStream.writeUInt32(this.getUserFlags());
        //FILETIME set to 0x0
        directoryStream.write(new byte[16]);
        directoryStream.writeUInt32(this.getStartSector());
        directoryStream.writeUInt64(this.getSizeOfStream());
    }

    // Does nothing in the base class implementation.
    public void writeReferencedStream() throws Exception {
        return ;
    }

}


