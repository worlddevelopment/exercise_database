//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.GCSupport;
import CS2JNet.System.IO.FileNotFoundException;
import CS2JNet.System.LCC.IDisposable;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipEntryInfo;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipEntryNotFoundException;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipException;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipLib;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipReader;
import java.io.File;
import java.io.InputStream;

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
public class ZlibZipReader  extends ZipReader implements IDisposable
{
    private IntPtr handle = new IntPtr();
    private boolean disposed = false;
    public ZlibZipReader(String path) throws Exception {
        String resolvedPath = ZipLib.resolvePath(path);
        if (!(new File(resolvedPath)).exists())
        {
            throw new FileNotFoundException("File does not exist:" + path);
        }
         
        this.handle = ZipLib.unzOpen(resolvedPath);
        if (handle == IntPtr.Zero)
        {
            throw new ZipException("Unable to open ZIP file:" + path);
        }
         
    }

    protected void finalize() throws Throwable {
        try
        {
            dispose(false);
        }
        finally
        {
            super.finalize();
        }
    }

    public void dispose() throws Exception {
        dispose(true);
        GCSupport.SuppressFinalize(this) /* CS2J: this call does nothing */;
    }

    public void dispose(boolean disposing) throws Exception {
        if (!disposed)
        {
            if (disposing)
            {
            }
             
            // Dispose managed resources : none here
            close();
        }
         
    }

    public void close() throws Exception {
        if (handle != IntPtr.Zero)
        {
            int result = ZipLib.unzClose(this.handle);
            handle = IntPtr.Zero;
            // Question: should we raise this exception ?
            if (result != 0)
            {
                throw new ZipException("Error closing file - Errorcode: " + result);
            }
             
        }
         
    }

    public InputStream getEntry(String relativePath) throws Exception {
        String resolvedPath = ZipLib.resolvePath(relativePath);
        if (ZipLib.unzLocateFile(this.handle,resolvedPath,0) != 0)
        {
            throw new ZipEntryNotFoundException("Entry not found:" + relativePath);
        }
         
        ZipEntryInfo entryInfo = new ZipEntryInfo();
        RefSupport<ZipEntryInfo> refVar___0 = new RefSupport<ZipEntryInfo>();
        int result = ZipLib.unzGetCurrentFileInfo(this.handle, refVar___0, null, UIntPtr.Zero, null, UIntPtr.Zero, null, UIntPtr.Zero);
        entryInfo = refVar___0.getValue();
        if (result != 0)
        {
            throw new ZipException("Error while reading entry info: " + relativePath + " - Errorcode: " + result);
        }
         
        result = ZipLib.unzOpenCurrentFile(this.handle);
        if (result != 0)
        {
            throw new ZipException("Error while opening entry: " + relativePath + " - Errorcode: " + result);
        }
         
        byte[] buffer = new byte[entryInfo.UncompressedSize.ToUInt64()];
        int bytesRead = 0;
        if ((bytesRead = ZipLib.unzReadCurrentFile(this.handle,buffer,(uint)entryInfo.UncompressedSize)) < 0)
        {
            throw new ZipException("Error while reading entry: " + relativePath + " - Errorcode: " + result);
        }
         
        result = ZipLib.unzCloseCurrentFile(handle);
        if (result != 0)
        {
            throw new ZipException("Error while closing entry: " + relativePath + " - Errorcode: " + result);
        }
         
        return new MemoryStream(buffer, 0, bytesRead);
    }

}


