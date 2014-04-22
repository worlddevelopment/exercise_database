//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipEntryInfo;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipFileEntryInfo;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipFileInfo;
import java.util.ArrayList;
import java.util.List;

/* 
 * Copyright (c) 2006, Clever Age
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Clever Age nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 * Copyright (C) 2001 Gerry Shaw
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *	claim that you wrote the original software. If you use this software
 *	in a product, an acknowledgment in the product documentation would be
 *	appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *	misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 *
 * Gerry Shaw (gerry_shaw@yahoo.com)
 * 
 */
/**
* Support methods for uncompressing zip files.This unzip package allow extract file from .ZIP file, compatible with PKZip 2.04g WinZip, InfoZip tools and compatible.Encryption and multi volume ZipFile (span) are not supported.  Old compressions used by old PKZip 1.x are not supported.Copyright (C) 1998 Gilles Vollant.  http://www.winimage.com/zLibDll/unzip.htmC# wrapper by Gerry Shaw (gerry_shaw@yahoo.com).  http://www.organicbit.com/zip/
*/
public final class ZipLib   
{
    static final String zlibwapi = "zlibwapi.dll";
    // prevent instances of this class from being constructed
    private ZipLib() throws Exception {
    }

    /*
                Create a zipfile.
                pathname contain on Windows NT a filename like "c:\\zlib\\zlib111.zip" or on an Unix computer "zlib/zlib111.zip".
                if the file pathname exist and append=1, the zip will be created at the end of the file. (useful if the file contain a self extractor code)
                If the zipfile cannot be opened, the return value is NULL.
                Else, the return value is a zipFile Handle, usable with other function of this zip package.
            */
    /**
    * Create a zip file.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ IntPtr zipOpen(String fileName, int append) throws Exception ;

    /*
                Open a file in the ZIP for writing.
                filename : the filename in zip (if NULL, '-' without quote will be used
                *zipfi contain supplemental information
                if extrafield_local!=NULL and size_extrafield_local>0, extrafield_local contains the extrafield data the the local header
                if extrafield_global!=NULL and size_extrafield_global>0, extrafield_global contains the extrafield data the the local header
                if comment != NULL, comment contain the comment string
                method contain the compression method (0 for store, Z_DEFLATED for deflate)
                level contain the level of compression (can be Z_DEFAULT_COMPRESSION)
            */
    /**
    * Open a new zip entry for writing.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int zipOpenNewFileInZip(IntPtr handle, String entryName, RefSupport<ZipFileEntryInfo> entryInfoPtr, byte[] extraField, uint extraFieldLength, byte[] extraFieldGlobal, uint extraFieldGlobalLength, String comment, int method, int level) throws Exception ;

    /**
    * Write data to the zip file.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int zipWriteInFileInZip(IntPtr handle, byte[] buffer, uint count) throws Exception ;

    /**
    * Close the current entry in the zip file.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int zipCloseFileInZip(IntPtr handle) throws Exception ;

    /**
    * Close the zip file.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int zipClose(IntPtr handle, String comment) throws Exception ;

    /**
    * Opens a zip file for reading.
    *  @param fileName The name of the zip to open.  At this time only file names with ANSI (8 bit) characters are supported.
    *  @return A handle usable with other functions of the ZipLib class.Otherwise IntPtr.Zero if the zip file could not e opened (file doen not exist or is not valid).
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ IntPtr unzOpen(String fileName) throws Exception ;

    /**
    * Closes a zip file opened with unzipOpen.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .If there are files inside the zip file opened with 
    *  {@link #unzOpenCurrentFile}
    *  these files must be closed with 
    *  {@link #unzCloseCurrentFile}
    *  before call 
    *  {@code unzClose}
    * .
    *  @return Zero if there was no error.Otherwise a value less than zero.  See 
    *  {@link #ErrorCode}
    *  for the specific reason.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzClose(IntPtr handle) throws Exception ;

    /**
    * Get global information about the zip file.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @param globalInfoPtr An address of a 
    *  {@link #ZipFileInfo}
    *  struct to hold the information.  No preparation of the structure is needed.
    *  @return Zero if there was no error.Otherwise a value less than zero.  See 
    *  {@link #ErrorCode}
    *  for the specific reason.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzGetGlobalInfo(IntPtr handle, RefSupport<ZipFileInfo> globalInfoPtr) throws Exception ;

    /**
    * Get the comment associated with the entire zip file.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * 
    *  @param commentBuffer The buffer to hold the comment.
    *  @param commentBufferLength The length of the buffer in bytes (8 bit characters).
    *  @return The number of characters in the comment if there was no error.Otherwise a value less than zero.  See 
    *  {@link #ErrorCode}
    *  for the specific reason.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ Integer unzGetGlobalComment(IntPtr handle, sbyte[] commentBuffer, UIntPtr commentBufferLength) throws Exception ;

    /**
    * Set the current file of the zip file to the first file.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @return Zero if there was no error.Otherwise a value less than zero.  See 
    *  {@link #ErrorCode}
    *  for the specific reason.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzGoToFirstFile(IntPtr handle) throws Exception ;

    /**
    * Set the current file of the zip file to the next file.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @return Zero if there was no error.Otherwise 
    *  {@link #ErrorCode.EndOfListOfFile}
    *  if there are no more entries.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzGoToNextFile(IntPtr handle) throws Exception ;

    /**
    * Try locate the entry in the zip file.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @param entryName The name of the entry to look for.
    *  @param caseSensitivity If 0 use the OS default.  If 1 use case sensitivity like strcmp, Unix style.  If 2 do not use case sensitivity like strcmpi, Windows style.
    *  @return Zero if there was no error.Otherwise 
    *  {@link #ErrorCode.EndOfListOfFile}
    *  if there are no more entries.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzLocateFile(IntPtr handle, String entryName, int caseSensitivity) throws Exception ;

    /**
    * Get information about the current entry in the zip file.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @param entryInfoPtr A ZipEntryInfo struct to hold information about the entry or null.
    *  @param entryNameBuffer An array of sbyte characters to hold the entry name or null.
    *  @param entryNameBufferLength The length of the entryNameBuffer in bytes.
    *  @param extraField An array to hold the extra field data for the entry or null.
    *  @param extraFieldLength The length of the extraField array in bytes.
    *  @param commentBuffer An array of sbyte characters to hold the entry name or null.
    *  @param commentBufferLength The length of theh commentBuffer in bytes.If entryInfoPtr is not null the structure will contain information about the current file.If entryNameBuffer is not null the name of the entry will be copied into it.If extraField is not null the extra field data of the entry will be copied into it.If commentBuffer is not null the comment of the entry will be copied into it.
    *  @return Zero if there was no error.Otherwise a value less than zero.  See 
    *  {@link #ErrorCode}
    *  for the specific reason.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzGetCurrentFileInfo(IntPtr handle, RefSupport<ZipEntryInfo> entryInfoPtr, sbyte[] entryNameBuffer, UIntPtr entryNameBufferLength, byte[] extraField, UIntPtr extraFieldLength, sbyte[] commentBuffer, UIntPtr commentBufferLength) throws Exception ;

    /**
    * Open the zip file entry for reading.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @return Zero if there was no error.Otherwise a value from 
    *  {@link #ErrorCode}
    * .
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzOpenCurrentFile(IntPtr handle) throws Exception ;

    /**
    * Close the file entry opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @return Zero if there was no error.CrcError if the file was read but the Crc does not match.Otherwise a value from 
    *  {@link #ErrorCode}
    * .
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzCloseCurrentFile(IntPtr handle) throws Exception ;

    /**
    * Read bytes from the current zip file entry.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @param buffer Buffer to store the uncompressed data into.
    *  @param count Number of bytes to write from 
    *  {@code buffer}
    * .
    *  @return The number of byte copied if somes bytes are copied.Zero if the end of file was reached.Less than zero with error code if there is an error.  See 
    *  {@link #ErrorCode}
    *  for a list of possible error codes.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzReadCurrentFile(IntPtr handle, byte[] buffer, uint count) throws Exception ;

    /**
    * Give the current position in uncompressed data of the zip file entry currently opened.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @return The number of bytes into the uncompressed data read so far.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ long unztell(IntPtr handle) throws Exception ;

    /**
    * Determine if the end of the zip file entry has been reached.
    *  @param handle The zip file handle opened by 
    *  {@link #unzOpenCurrentFile}
    * .
    *  @return One if the end of file was reached.Zero if elsewhere.
    */
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ int unzeof(IntPtr handle) throws Exception ;

    /**
    * Converts a CLR string to a 8 bit ANSI array of characters.
    *  @param str The string to convert.
    *  @return A 8 bit ANSI array of characters.
    */
    public static sbyte[] stringToAnsi(String str) throws Exception {
        int length = str.length();
        sbyte[] chars = new sbyte[length + 1];
        for (int i = 0;i < length;i++)
        {
            chars[i] = (sbyte)str.charAt(i);
        }
        return chars;
    }

    /**
    * Converst an 8 bit ANSI C style string to a CLR string.
    *  @param chars The array of a characters that holds the string.
    *  @return The CLR string representing the characters passed in.
    */
    public static String ansiToString(sbyte[] chars) throws Exception {
        int length = chars.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i < length;i++)
        {
            builder.append((char)chars[i]);
        }
        return builder.toString();
    }

    /**
    * Resolves a path by interpreting "." and "..".
    * 
    *  @param path The path to resolve.
    *  @return The resolved path.
    */
    public static String resolvePath(String path) throws Exception {
        if (path.lastIndexOf("/../") < 0 && path.lastIndexOf("/./") < 0)
        {
            return path;
        }
         
        String resolvedPath = path;
        List elements = new ArrayList();
        String[] split = path.split(StringSupport.charAltsToRegex(new char[]{ '/', '\\' }));
        int count = 0;
        for (String s : split)
        {
            if ("..".equals(s))
            {
                elements.RemoveAt(count - 1);
                count--;
            }
            else if (".".equals(s))
            {
            }
            else
            {
                // do nothing
                elements.add(s);
                count++;
            }  
        }
        String result = (String)elements.get(0);
        for (int i = 1;i < count;++i)
        {
            result += "/" + elements.get(i);
        }
        return result;
    }

}


