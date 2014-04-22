//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ChainCycleDetectedException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryType;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.Measures;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.StreamNotFoundException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.DirectoryEntry;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.Fat;
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
* Represents the directory structure of a compound file
* Author: math
*/
public class DirectoryTree   
{
    Fat _fat;
    Header _header;
    InputHandler _fileHandler;
    List<Long> _sectorsUsedByDirectory;
    List<DirectoryEntry> _directoryEntries = new ArrayList<DirectoryEntry>();
    /**
    * Constructor
    * 
    *  @param fat Handle to the Fat of the compound file
    *  @param header Handle to the header of the compound file
    *  @param fileHandler Handle to the file handler of the compound file
    */
    public DirectoryTree(Fat fat, Header header, InputHandler fileHandler) throws Exception {
        _fat = fat;
        _header = header;
        _fileHandler = fileHandler;
        init(_header.getDirectoryStartSector());
    }

    /**
    * Inits the directory
    * 
    *  @param startSector The sector containing the root of the directory
    */
    private void init(BigInteger startSector) throws Exception {
        if (_header.getNoSectorsInDirectoryChain4KB().compareTo(BigInteger.ZERO) > 0)
        {
            _sectorsUsedByDirectory = _fat.getSectorChain(startSector, _header.getNoSectorsInDirectoryChain4KB(), "Directory");
        }
        else
        {
            _sectorsUsedByDirectory = _fat.getSectorChain(startSector,BigInteger.valueOf(Math.ceil((double)_fileHandler.getIOStreamSize() / _header.getSectorSize()))
            		,"Directory"
            		,true
            );
        } 
        getAllDirectoryEntriesRecursive(BigInteger.ZERO, "");
    }

    /**
    * Determines the directory _entries in a compound file recursively
    * 
    *  @param sid start sid
    */
    private void getAllDirectoryEntriesRecursive(BigInteger sid, String path) throws Exception {
        DirectoryEntry entry = readDirectoryEntry(sid,path);
        BigInteger left = entry.getLeftSiblingSid();
        BigInteger right = entry.getRightSiblingSid();
        BigInteger child = entry.getChildSiblingSid();
        //Console.WriteLine("{0:X02}: Left: {2:X02}, Right: {3:X02}, Child: {4:X02}, Name: {1}, Color: {5}", entry.Sid, entry.Name, (left > 0xFF)? 0xFF : left, (right > 0xFF)? 0xFF : right, (child > 0xFF)? 0xFF : child, entry.Color.ToString() );
        // Check for cycle
        if (_directoryEntries.exists())
        {
            throw new ChainCycleDetectedException("DirectoryEntries");
        }
         
        _directoryEntries.add(entry);
        // Left sibling
        if (left != SectorId.NOSTREAM)
        {
            getAllDirectoryEntriesRecursive(left,path);
        }
         
        // Right sibling
        if (right != SectorId.NOSTREAM)
        {
            getAllDirectoryEntriesRecursive(right,path);
        }
         
        // Child
        if (child != SectorId.NOSTREAM)
        {
            getAllDirectoryEntriesRecursive(child,path + ((sid == 0) ? "" : entry.getName()) + "\\");
        }
         
    }

    /**
    * Returns a directory entry for a given sid
    */
    private DirectoryEntry readDirectoryEntry(BigInteger sid, String path) throws Exception {
        seekToDirectoryEntry(sid);
        DirectoryEntry result = new DirectoryEntry(_header,_fileHandler,sid,path);
        return result;
    }

    /**
    * Seeks to the start sector of the directory entry of the given sid
    */
    private void seekToDirectoryEntry(BigInteger sid) throws Exception {
        int sectorInDirectoryChain = (int)(sid * Measures.DirectoryEntrySize) / _header.getSectorSize();
        if (sectorInDirectoryChain < 0)
        {
            throw new Exception("ArgumentOutOfRange: sectorInDirectoryChain");
        }
         
        _fileHandler.SeekToPositionInSector(_sectorsUsedByDirectory.get(sectorInDirectoryChain), (sid * Measures.DirectoryEntrySize) % _header.getSectorSize());
    }

    /**
    * Returns the directory entry with the given name/path
    */
    public DirectoryEntry getDirectoryEntry(String path) throws Exception {
        if (path.length() < 1)
        {
            return null;
        }
         
        if (path.charAt(0) == '\\')
        {
            return _directoryEntries.Find();
        }
         
        return _directoryEntries.Find();
    }

    /**
    * Returns the directory entry with the given sid
    */
    public DirectoryEntry getDirectoryEntry(BigInteger sid) throws Exception {
        return _directoryEntries.Find();
    }

    /**
    * Returns the start sector of the mini stream
    */
    public BigInteger getMiniStreamStart() throws Exception {
        DirectoryEntry root = GetDirectoryEntry(0);
        if (root == null)
        {
            throw new StreamNotFoundException("Root Entry");
        }
         
        return root.getStartSector();
    }

    /**
    * Returns the size of the mini stream
    */
    public UInt64 getSizeOfMiniStream() throws Exception {
        DirectoryEntry root = GetDirectoryEntry(0);
        if (root == null)
        {
            throw new StreamNotFoundException("Root Entry");
        }
         
        return root.getSizeOfStream();
    }

    /**
    * Returns all entry names contained in a compound file
    */
    public Collection<String> getNamesOfAllEntries() throws Exception {
        List<String> result = new List<String>();
        for (DirectoryEntry entry : _directoryEntries)
        {
            result.add(entry.getName());
        }
        return new Collection<String>(result);
    }

    /**
    * Returns all entry paths contained in a compound file
    */
    public Collection<String> getPathsOfAllEntries() throws Exception {
        List<String> result = new List<String>();
        for (DirectoryEntry entry : _directoryEntries)
        {
            result.add(entry.getPath());
        }
        return new Collection<String>(result);
    }

    /**
    * Returns all stream entry names contained in a compound file
    */
    public Collection<String> getNamesOfAllStreamEntries() throws Exception {
        List<String> result = new List<String>();
        for (DirectoryEntry entry : _directoryEntries)
        {
            if (entry.getType() == DirectoryEntryType.STGTY_STREAM)
            {
                result.add(entry.getName());
            }
             
        }
        return new Collection<String>(result);
    }

    /**
    * Returns all stream entry paths contained in a compound file
    */
    public Collection<String> getPathsOfAllStreamEntries() throws Exception {
        java.util.List<String> result = new List<String>();
        for (DirectoryEntry entry : _directoryEntries)
        {
            if (entry.getType() == DirectoryEntryType.STGTY_STREAM)
            {
                result.add(entry.getPath());
            }
             
        }
        return new Collection<String>(result);
    }

    /**
    * Returns all _entries contained in a compound file
    */
    public Collection<DirectoryEntry> getAllEntries() throws Exception {
        return new Collection<DirectoryEntry>(_directoryEntries);
    }

    /**
    * Returns all stream _entries contained in a compound file
    */
    public Collection<DirectoryEntry> getAllStreamEntries() throws Exception {
        return new Collection<DirectoryEntry>(_directoryEntries.FindAll());
    }

}


