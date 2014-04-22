//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Collection;

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
* Provides methods for accessing a compound file.
* Author: math
*/
public class StructuredStorageReader implements IStructuredStorageReader
{
    InputHandler _fileHandler;
    Header _header;
    Fat _fat;
    MiniFat _miniFat;
    private DirectoryTree _directory;
    /**
    * // 
    * // Collection of all entry names contained in a compound file
    * //
    */
    //public ReadOnlyCollection<string> NamesOfAllEntries
    //{
    //    get { return _directory.GetNamesOfAllEntries(); }
    //}
    /**
    * // 
    * // Collection of all stream entry names contained in a compound file
    * //
    */
    //public ReadOnlyCollection<string> NamesOfAllStreamEntries
    //{
    //    get { return _directory.GetNamesOfAllStreamEntries(); }
    //}
    /**
    * Collection of all entry names contained in a compound file
    */
    public Collection<String> getFullNameOfAllEntries() throws Exception {
        return _directory.getPathsOfAllEntries();
    }

    /**
    * Collection of all stream entry names contained in a compound file
    */
    public Collection<String> getFullNameOfAllStreamEntries() throws Exception {
        return _directory.getPathsOfAllStreamEntries();
    }

    /**
    * Collection of all _entries contained in a compound file
    */
    public Collection<DirectoryEntry> getAllEntries() throws Exception {
        return _directory.getAllEntries();
    }

    /**
    * Collection of all stream _entries contained in a compound file
    */
    public Collection<DirectoryEntry> getAllStreamEntries() throws Exception {
        return _directory.getAllStreamEntries();
    }

    /**
    * Returns a handle to the RootDirectoryEntry.
    */
    public DirectoryEntry getRootDirectoryEntry() throws Exception {
        return _directory.getDirectoryEntry(BigInteger.ZERO);//0x0);
    }

    /**
    * Initalizes a handle to a compound file based on a stream
    * 
    *  @param stream The stream to the storage
    */
    public StructuredStorageReader(InputStream stream) throws Exception {
        _fileHandler = new InputHandler(stream);
        _header = new Header(_fileHandler);
        _fat = new Fat(_header,_fileHandler);
        _directory = new DirectoryTree(_fat,_header,_fileHandler);
        _miniFat = new MiniFat(_fat,_header,_fileHandler,_directory.getMiniStreamStart(),_directory.getSizeOfMiniStream());
    }

    /**
    * Initalizes a handle to a compound file with the given name
    * 
    *  @param fileName The name of the file including its path
    */
    public StructuredStorageReader(String fileName) throws Exception {
        _fileHandler = new InputHandler(fileName);
        _header = new Header(_fileHandler);
        _fat = new Fat(_header,_fileHandler);
        _directory = new DirectoryTree(_fat,_header,_fileHandler);
        _miniFat = new MiniFat(_fat,_header,_fileHandler,_directory.getMiniStreamStart(),_directory.getSizeOfMiniStream());
    }

    /**
    * Returns a handle to a stream with the given name/path.
    * If a path is used, it must be preceeded by '\'.
    * The characters '\' ( if not separators in the path) and '%' must be masked by '%XXXX'
    * where 'XXXX' is the unicode in hex of '\' and '%', respectively
    * 
    *  @param path The path of the virtual stream.
    *  @return An object which enables access to the virtual stream.
    */
    public VirtualStream getStream(String path) throws Exception {
        DirectoryEntry entry = _directory.getDirectoryEntry(path);
        if (entry == null)
        {
            throw new StreamNotFoundException(path);
        }
         
        if (entry.getType() != DirectoryEntryType.STGTY_STREAM)
        {
            throw new WrongDirectoryEntryTypeException();
        }
         
        // only streams up to long.MaxValue are supported
        if (entry.getSizeOfStream() > Long.MAX_VALUE)
        {
            throw new UnsupportedSizeException(entry.getSizeOfStream().toString());
        }
         
        // Determine whether this stream is a "normal stream" or a stream in the mini stream
        if (entry.getSizeOfStream() < _header.getMiniSectorCutoff())
        {
            return new VirtualStream(_miniFat,entry.getStartSector(),(long)entry.getSizeOfStream(),path);
        }
        else
        {
            return new VirtualStream(_fat,entry.getStartSector(),(long)entry.getSizeOfStream(),path);
        } 
    }

    /**
    * Returns a handle to a directory entry with the given name/path.
    * If a path is used, it must be preceeded by '\'.
    * The characters '\' ( if not separators in the path) and '%' must be masked by '%XXXX'
    * where 'XXXX' is the unicode in hex of '\' and '%', respectively
    * 
    *  @param path The path of the directory entry.
    *  @return An object which enables access to the directory entry.
    */
    public DirectoryEntry getEntry(String path) throws Exception {
        DirectoryEntry entry = _directory.getDirectoryEntry(path);
        if (entry == null)
        {
            throw new DirectoryEntryNotFoundException(path);
        }
         
        return entry;
    }

    /**
    * Closes the file handle
    */
    public void close() throws Exception {
        _fileHandler.closeStream();
    }

    public void dispose() throws Exception {
        this.close();
    }

}


