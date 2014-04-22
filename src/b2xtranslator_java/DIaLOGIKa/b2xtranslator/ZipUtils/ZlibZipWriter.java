//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.LCC.Disposable;
import DIaLOGIKa.b2xtranslator.ZipUtils.CompressionLevel;
import DIaLOGIKa.b2xtranslator.ZipUtils.CompressionMethod;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipCreationException;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipException;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipFileEntryInfo;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipLib;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipWriter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

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
 */
public class ZlibZipWriter  extends ZipWriter 
{
    private IntPtr handle = new IntPtr();
    private boolean entryOpened;
    public ZlibZipWriter(String path) throws Exception {
        this.Filename = path;
        String resolvedPath = ZipLib.resolvePath(path);
        this.handle = ZipLib.zipOpen(resolvedPath,0);
        if (this.handle == IntPtr.Zero)
        {
            try
            {
                // Small trick to get exact error message...
                FileStreamSupport writer = new BufferedOutputStream(new FileOutputStream(path));
                try
                {
                    {
                        writer.WriteByte(0);
                    }
                }
                finally
                {
                    if (writer != null)
                        Disposable.mkDisposable(writer).dispose();
                     
                }
                (new File(path)).delete();
                throw new ZipCreationException();
            }
            catch (Exception ex)
            {
                throw new ZipCreationException(ex.getMessage());
            }
        
        }
         
        this.entryOpened = false;
    }

    public void addEntry(String relativePath) throws Exception {
        String resolvedPath = ZipLib.resolvePath(relativePath);
        ZipFileEntryInfo info = new ZipFileEntryInfo();
        info.DateTime = Calendar.getInstance().getTime();
        if (this.entryOpened)
        {
            ZipLib.zipCloseFileInZip(this.handle);
            this.entryOpened = false;
        }
         
        RefSupport<ZipFileEntryInfo> refVar___0 = new RefSupport<ZipFileEntryInfo>();
        int result = ZipLib.zipOpenNewFileInZip(this.handle, resolvedPath, refVar___0, null, 0, null, 0, "", ((Enum)CompressionMethod.Deflated).ordinal(), ((Enum)CompressionLevel.Default).ordinal());
        info = refVar___0.getValue();
        if (result < 0)
        {
            throw new ZipException("Error while opening entry for writing: " + relativePath + " - Errorcode: " + result);
        }
         
        this.entryOpened = true;
    }

    public void close() throws Exception {
        if (handle != IntPtr.Zero)
        {
            int result;
            if (this.entryOpened)
            {
                result = ZipLib.zipCloseFileInZip(this.handle);
                if (result != 0)
                {
                    throw new ZipException("Error while closing entry - Errorcode: " + result);
                }
                 
                this.entryOpened = false;
            }
             
            result = ZipLib.zipClose(this.handle,"");
            handle = IntPtr.Zero;
            // Should we raise this exception ?
            if (result != 0)
            {
                throw new ZipException("Error while closing ZIP file - Errorcode: " + result);
            }
             
        }
         
    }

    public int read(byte[] buffer, int offset, int count) throws Exception {
        throw new ZipException("Error, Read not allowed on this stream");
    }

    public void write(byte[] buffer, int offset, int count) throws Exception {
        int result;
        if (offset != 0)
        {
            byte[] newBuffer = new byte[count];
            Array.Copy(buffer, offset, newBuffer, 0, count);
            result = ZipLib.zipWriteInFileInZip(handle,newBuffer,(uint)count);
        }
        else
        {
            result = ZipLib.zipWriteInFileInZip(handle,buffer,(uint)count);
        } 
        if (result < 0)
        {
            throw new ZipException("Error while writing entry - Errorcode: " + result);
        }
         
    }

    public long seek(long offset, SeekOrigin origin) throws Exception {
        return 0;
    }

    public void setLength(long value) throws Exception {
        return ;
    }

    public void flush() throws Exception {
        return ;
    }

    public long getPosition() throws Exception {
        throw new ZipException("Position not available on this stream");
    }

    public void setPosition(long value) throws Exception {
        throw new ZipException("Position not available on this stream");
    }

    public long getLength() throws Exception {
        return 0;
    }

    public boolean getCanRead() throws Exception {
        return false;
    }

    public boolean getCanWrite() throws Exception {
        return true;
    }

    public boolean getCanSeek() throws Exception {
        return false;
    }

}


