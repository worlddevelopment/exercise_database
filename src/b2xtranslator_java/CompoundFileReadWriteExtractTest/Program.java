//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:55 AM
//

package CompoundFileReadWriteExtractTest;

import CompoundFileReadWriteExtractTest.Program;
import CS2JNet.JavaSupport.Collections.Generic.LCC.CollectionSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Collections.LCC.ICollection;
import CS2JNet.System.IO.FileAccess;
import CS2JNet.System.IO.FileMode;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.IO.PathSupport;
import CS2JNet.System.StringSplitOptions;
import CS2JNet.System.StringSupport;
import CS2JNet.System.TimeSpan;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.DirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StorageDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageWriter;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.VirtualStream;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class Program   
{
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    static void Main(String[] args) throws Exception {
        ;
        char[] invalidChars = Path.GetInvalidFileNameChars();
        TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Error);
        ConsoleTraceListener consoleTracer = new ConsoleTraceListener();
        Trace.Listeners.Add(consoleTracer);
        Trace.AutoFlush = true;
        if (args.length < 1)
        {
            System.out.println("No parameter found. Please specify one or more compound document file(s).");
            return ;
        }
         
        for (String file : args)
        {
            StructuredStorageReader storageReader = null;
            Date begin = Calendar.getInstance().getTime();
            TimeSpan extractionTime = new TimeSpan();
            try
            {
                // init StorageReader
                storageReader = new StructuredStorageReader(file);
                // read stream _entries
                ICollection<DirectoryEntry> streamEntries = storageReader.getAllStreamEntries();
                //ICollection<DirectoryEntry> allEntries = storageReader.AllEntries;
                //allEntries.Add(storageReader.RootDirectoryEntry);
                CSList<DirectoryEntry> allEntries = new CSList<DirectoryEntry>();
                allEntries.addRange(storageReader.getAllEntries());
                allEntries.Sort();
                //foreach (DirectoryEntry entry in allEntries)
                //{
                //    Console.WriteLine(entry.Sid + ":");
                //    Console.WriteLine("{0}: {1}", entry.Name, entry.LengthOfName);
                //    Console.WriteLine("CLSID: " + entry.ClsId);
                //    string hexName = "";
                //    for (int i = 0; i < entry.Name.Length; i++)
                //    {
                //        hexName += String.Format("{0:X2} ", (UInt32)entry.Name[i]);
                //    }
                //    Console.WriteLine("{0}", hexName);
                //    UInt32 left = entry.LeftSiblingSid;
                //    UInt32 right = entry.RightSiblingSid;
                //    UInt32 child = entry.ChildSiblingSid;
                //    Console.WriteLine("{0:X02}: Left: {2:X02}, Right: {3:X02}, Child: {4:X02}, Name: {1}, Color: {5}", entry.Sid, entry.Name, (left > 0xFF) ? 0xFF : left, (right > 0xFF) ? 0xFF : right, (child > 0xFF) ? 0xFF : child, entry.Color.ToString());
                //    Console.WriteLine("----------");
                //    Console.WriteLine("");
                //}
                // create valid path names
                HashMap<DirectoryEntry,Entry<String,UUID>> pathNames1 = new HashMap<DirectoryEntry,Entry<String,UUID>>();
                for (DirectoryEntry entry : allEntries)
                {
                    String name = entry.getPath();
                    for (int i = 0;i < invalidChars.length;i++)
                    {
                        name = name.replace(invalidChars[i], '_');
                    }
                    pathNames1.put(entry, new Entry<String,UUID>(name, entry.getClsId()));
                }
                // Create Directory Structure
                StructuredStorageWriter sso = new StructuredStorageWriter();
                sso.getRootDirectoryEntry().setClsId(storageReader.getRootDirectoryEntry().getClsId());
                for (DirectoryEntry entry : CollectionSupport.mk(pathNames1.keySet()))
                {
                    StorageDirectoryEntry sde = sso.getRootDirectoryEntry();
                    String[] storages = StringSupport.Split(entry.getPath(), new char[]{ '\\' }, StringSplitOptions.RemoveEmptyEntries);
                    for (int i = 0;i < storages.length;i++)
                    {
                        if (entry.getType() == DirectoryEntryType.STGTY_ROOT)
                        {
                            continue;
                        }
                         
                        if (entry.getType() == DirectoryEntryType.STGTY_STORAGE || i < storages.length - 1)
                        {
                            StorageDirectoryEntry result = sde.addStorageDirectoryEntry(storages[i]);
                            sde = (result == null) ? sde : result;
                            if (i == storages.length - 1)
                            {
                                sde.setClsId(entry.getClsId());
                            }
                             
                            continue;
                        }
                         
                        VirtualStream vstream = storageReader.getStream(entry.getPath());
                        sde.AddStreamDirectoryEntry(storages[i], vstream);
                    }
                }
                // Write sso to stream
                MemoryStream myStream = new MemoryStream();
                sso.write(myStream);
                // Close input storage
                storageReader.close();
                storageReader = null;
                // Write stream to file
                byte[] array = new byte[bytesToReadAtOnce];
                int bytesRead;
                String outputFileName = Path.GetFileNameWithoutExtension(file) + "_output" + PathSupport.getExtension(file);
                String path = (new File((new File(file)).getCanonicalPath())).getParent();
                outputFileName = path + "\\" + outputFileName;
                FileStreamSupport outputFile = new FileStreamSupport(outputFileName, FileMode.Create, FileAccess.Write);
                myStream.Seek(0, SeekOrigin.Begin);
                do
                {
                    bytesRead = myStream.Read(array, 0, bytesToReadAtOnce);
                    outputFile.write(array,0,bytesRead);
                }
                while (bytesRead == array.length);
                outputFile.close();
                // --------- extract streams from written file
                storageReader = new StructuredStorageReader(outputFileName);
                // read stream _entries
                streamEntries = storageReader.getAllStreamEntries();
                // create valid path names
                HashMap<String,String> pathNames2 = new HashMap<String,String>();
                for (DirectoryEntry entry : streamEntries)
                {
                    String name = entry.getPath();
                    for (int i = 0;i < invalidChars.length;i++)
                    {
                        name = name.replace(invalidChars[i], '_');
                    }
                    pathNames2.put(entry.getPath(), name);
                }
                // create output directory
                String outputDir = '_' + ((new File(outputFileName)).getName()).replace('.', '_');
                outputDir = outputDir.replace(':', '_');
                outputDir = (new File(outputFileName)).getParent() + "\\" + outputDir;
                (new File(outputDir)).mkdirs();
                for (String key : CollectionSupport.mk(pathNames2.keySet()))
                {
                    // for each stream
                    // get virtual stream by path name
                    IStreamReader streamReader = new VirtualStreamReader(storageReader.getStream(key));
                    // read bytes from stream, write them back to disk
                    FileStreamSupport fs = new FileStreamSupport(outputDir + "\\" + pathNames2.get(key) + ".stream", FileMode.Create);
                    BinaryWriter writer = new BinaryWriter(fs);
                    array = new byte[bytesToReadAtOnce];
                    do
                    {
                        bytesRead = streamReader.read(array);
                        writer.Write(array, 0, bytesRead);
                        writer.Flush();
                    }
                    while (bytesRead == array.length);
                    writer.Close();
                    fs.close();
                }
                // close storage
                storageReader.close();
                storageReader = null;
                extractionTime = Calendar.getInstance().getTime() - begin;
                System.out.println("Streams extracted in " + String.format(StringSupport.CSFmtStrToJFmtStr("{0:N2}"),extractionTime.getTotalSeconds()) + "s. (File: " + file + ")");
            }
            catch (Exception e)
            {
                System.out.println("*** Error: " + e.getMessage() + " (File: " + file + ")");
                System.out.println("*** StackTrace: " + e.getStackTrace().toString() + " (File: " + file + ")");
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


