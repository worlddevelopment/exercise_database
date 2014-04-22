//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:54 AM
//

package b2xtranslator_java.CompoundFileExtractTest;

import aufgaben_db.Global;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.DirectoryEntry;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

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
* Test application which extracts streams from compound files.
* Author: math
*/
public class Program   
{
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    static void Main(String[] args) throws Exception {
        ;
        //char[] invalidChars = File.GetInvalidFileNameChars();
        String validChars = "a-zA-Z0-9\\[\\]-_";//using whitelist principle.
//        TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Error);
//        ConsoleTraceListener consoleTracer = new ConsoleTraceListener();
//        Trace.Listeners.Add(consoleTracer);
//        Trace.AutoFlush = true;
        if (args.length < 1)
        {
            System.out.println("No parameter found. Please specify one or more compound document file(s).");
            return ;
        }
         
        for (String file : args)
        {
            StructuredStorageReader storageReader = null;
            Date begin = Calendar.getInstance().getTime();
            long extractionTime = begin.getTime();
            try
            {
                // init StorageReader
                storageReader = new StructuredStorageReader(file);
                // read stream _entries
                Collection<DirectoryEntry> streamEntries = storageReader.getAllEntries();
                // create valid path names
                HashMap<String,String> PathNames = new HashMap<String,String>();
                for (DirectoryEntry entry : streamEntries)
                {
                    String name = entry.getPath();
//                    for (int i = 0;i < invalidChars.length;i++)
//                    {
                    //only valid chars stay (because of the negation ^): 
                    name = name.replaceAll("[^"+ validChars + "]", "_");
//                    }
                    PathNames.put(entry.getPath(), name);
                }
                // create output directory
                String outputDir = '_' + file.replace('.', '_');
                outputDir = outputDir.replace(':', '_');
                (new File(outputDir)).mkdirs();
                for (String key : PathNames.keySet())
                {
                    // for each stream
                    // get virtual stream by path name //TODO exchange VirtualStream with DataStream.
                    IStreamReader streamReader = new VirtualStreamReader(storageReader.getStream(key));
                    // read bytes from stream, write them back to disk
                    FileOutputStream fs = new FileOutputStream(outputDir + "\\" + PathNames.get(key) + ".stream", FileMode.Create);
                    Writer writer = new /*Binary*/OutputStreamWriter(fs);
                    //http://stackoverflow.com/questions/236861/how-do-you-determine-the-ideal-buffer-size-when-using-fileinputstream
                    int bytesToReadAtOnce; //TODO hide the details using BufferedInputStream or strive for performance with self-optimization (OS and block size recognition).
                    //or leave it to the OS and read the file at once (if small enough). NTFS is 4K(4*1014) it's said? But the other?
                    //=> determine filesize, round up(?) and 
                    bytesToReadAtOnce = 4 * 1024;//<--always keep as a power of two.
                    byte[] array = new byte[bytesToReadAtOnce];
                    int bytesRead;
                    do
                    {
                        bytesRead = streamReader.read(array);
                        writer.write(Global.byteToCharArray(array), 0, bytesRead);
                        writer.flush();
                    }
                    while (bytesRead == array.length);
                    writer.close();
                    fs.close();
                }
                // close storage
                storageReader.close();
                storageReader = null;
                extractionTime = Calendar.getInstance().getTimeInMillis() - begin.getTime();
                System.out.println("Streams extracted in " + extractionTime + "s. (File: " + file + ")");
            }
            catch (Exception e)
            {
                System.out.println("*** Error: " + e.getMessage() + " (File: " + file + ")");
            }
            finally
            {
                if (storageReader != null)
                {
                    storageReader.close();
                }
                 
            }
        }
    }

}


