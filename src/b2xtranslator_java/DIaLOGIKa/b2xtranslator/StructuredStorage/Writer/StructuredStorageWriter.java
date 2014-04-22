//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.BaseDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.EmptyDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StorageDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageContext;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.VirtualStream;
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
* The root class for creating a structured storage
* Author: math
*/
public class StructuredStorageWriter   
{
    StructuredStorageContext _context;
    // The root directory entry of this structured storage.
    public StorageDirectoryEntry getRootDirectoryEntry() throws Exception {
        return _context.getRootDirectoryEntry();
    }

    /**
    * Constructor.
    */
    public StructuredStorageWriter() throws Exception {
        _context = new StructuredStorageContext();
    }

    /**
    * Writes the structured storage to a given stream.
    * 
    *  @param outputStream The output stream.
    */
    public void write(InputStream outputStream) throws Exception {
        _context.getRootDirectoryEntry().recursiveCreateRedBlackTrees();
        CSList<BaseDirectoryEntry> allEntries = _context.getRootDirectoryEntry().recursiveGetAllDirectoryEntries();
        allEntries.Sort();
        for (BaseDirectoryEntry entry : allEntries)
        {
            //foreach (BaseDirectoryEntry entry in allEntries)
            //{
            //    Console.WriteLine(entry.Sid + ":");
            //    Console.WriteLine("{0}: {1}", entry.Name, entry.LengthOfName);
            //    string hexName = "";
            //    string hexNameU = "";
            //    for (int i = 0; i < entry.Name.Length; i++)
            //    {
            //        hexName += String.Format("{0:X2} ", (UInt32)entry.Name[i]);
            //        hexNameU += String.Format("{0:X2} ", (UInt32)entry.Name.ToUpper()[i]);
            //    }
            //    Console.WriteLine("{0}", hexName);
            //    Console.WriteLine("{0}", hexNameU);
            //    UInt32 left = entry.LeftSiblingSid;
            //    UInt32 right = entry.RightSiblingSid;
            //    UInt32 child = entry.ChildSiblingSid;
            //    Console.WriteLine("{0:X02}: Left: {2:X02}, Right: {3:X02}, Child: {4:X02}, Name: {1}, Color: {5}", entry.Sid, entry.Name, (left > 0xFF) ? 0xFF : left, (right > 0xFF) ? 0xFF : right, (child > 0xFF) ? 0xFF : child, entry.Color.ToString());
            //    Console.WriteLine("----------");
            //    Console.WriteLine("");
            //}
            // write Streams
            if (entry.getSid() == 0x0)
            {
                continue;
            }
             
            // root entry
            entry.writeReferencedStream();
        }
        // root entry has to be written after all other streams as it contains the ministream to which other _entries write to
        _context.getRootDirectoryEntry().writeReferencedStream();
        for (BaseDirectoryEntry entry : allEntries)
        {
            // write Directory Entries to directory stream
            entry.write();
        }
        // Directory Entry: 128 bytes
        long dirEntriesPerSector = _context.getHeader().getSectorSize() / 128;
        long numToPad = dirEntriesPerSector - ((long)allEntries.size() % dirEntriesPerSector);
        EmptyDirectoryEntry emptyEntry = new EmptyDirectoryEntry(_context);
        for (int i = 0;i < numToPad;i++)
        {
            emptyEntry.write();
        }
        // write directory stream
        VirtualStream virtualDirectoryStream = new VirtualStream(_context.getDirectoryStream().getBaseStream(),_context.getFat(),_context.getHeader().getSectorSize(),_context.getTempOutputStream());
        virtualDirectoryStream.write();
        _context.getHeader().setDirectoryStartSector(virtualDirectoryStream.getStartSector());
        if (_context.getHeader().getSectorSize() == 0x1000)
        {
            _context.getHeader().setNoSectorsInDirectoryChain4KB((long)virtualDirectoryStream.getSectorCount());
        }
         
        // write MiniFat
        _context.getMiniFat().write();
        _context.getHeader().setMiniFatStartSector(_context.getMiniFat().getMiniFatStart());
        _context.getHeader().setNoSectorsInMiniFatChain(_context.getMiniFat().getNumMiniFatSectors());
        // write fat
        _context.getFat().write();
        // set header values
        _context.getHeader().setNoSectorsInDiFatChain(_context.getFat().getNumDiFatSectors());
        _context.getHeader().setNoSectorsInFatChain(_context.getFat().getNumFatSectors());
        _context.getHeader().setDiFatStartSector(_context.getFat().getDiFatStartSector());
        // write header
        _context.getHeader().write();
        // write temporary streams to the output streams.
        _context.getHeader().writeToStream(outputStream);
        _context.getTempOutputStream().writeToStream(outputStream);
    }

}


