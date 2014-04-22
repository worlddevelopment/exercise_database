//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:55 AM
//

package DIaLOGIKa.b2xtranslator.PptDump;

import CS2JNet.System.IO.DirectorySupport;
import CS2JNet.System.IO.FileMode;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.UnknownRecord;
import DIaLOGIKa.b2xtranslator.PptDump.Program;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PowerpointDocument;
import DIaLOGIKa.b2xtranslator.Shell.ProcessingFile;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.io.File;

public class Program   
{
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    static void Main(String[] args) throws Exception {
        TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.DebugInternal);
        ;
        if ((new File(outputDir)).exists())
            DirectorySupport.delete(outputDir, true);
         
        (new File(outputDir)).mkdirs();
        String inputFile = args[0];
        ProcessingFile procFile = new ProcessingFile(inputFile);
        StructuredStorageReader file = new StructuredStorageReader(procFile.File.FullName);
        PowerpointDocument pptDoc = new PowerpointDocument(file);
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record : pptDoc)
        {
            // Dump unknown records
            if (record instanceof UnknownRecord)
            {
                String filename = String.format(StringSupport.CSFmtStrToJFmtStr("{0}\\{1}.record"),outputDir,record.getIdentifier());
                FileStreamSupport fs = new FileStreamSupport(filename, FileMode.Create);
                try
                {
                    {
                        record.dumpToStream(fs);
                    }
                }
                finally
                {
                    if (fs != null)
                        Disposable.mkDisposable(fs).dispose();
                     
                }
            }
             
        }
        // Output record tree
        System.out.println(pptDoc);
        System.out.println();
        // Let's make development as easy as pie.
        System.Diagnostics.Debugger.Break();
    }

}


