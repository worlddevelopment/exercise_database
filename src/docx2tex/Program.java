//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:56 AM
//

package docx2tex;

import docx2tex.ConsoleOutput;
import docx2tex.Library.Docx2TexWorker;
import docx2tex.Library.StaticConfigHelper;
import docx2tex.Program;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;

public class Program   
{
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    static void Main(String[] args) throws Exception {
        ConsoleOutput consoleOutput = new ConsoleOutput();
        consoleOutput.writeLine("docx2tex was created by Krisztian Pocza in 2007-2008 under the terms of the BSD licence");
        consoleOutput.writeLine("info: kpocza@kpocza.net");
        consoleOutput.writeLine();
        //            Console.ReadLine();
        if (args.length < 2)
        {
            consoleOutput.writeLine("Usage:");
            consoleOutput.writeLine("docx2tex.exe source.docx dest.tex");
            return ;
        }
         
        String inputDocxPath = resolveFullPath(args[0]);
        String outputTexPath = resolveFullPath(args[1]);
        StaticConfigHelper.setDocxPath(inputDocxPath);
        if ((new Docx2TexWorker()).process(inputDocxPath,outputTexPath,consoleOutput))
        {
            BufferedReader sr = new BufferedReader(StreamReader.make(new BufferedInputStream(new FileInputStream(outputTexPath))));
            try
            {
                {
                    System.out.println(TextReaderSupport.readToEnd(sr));
                }
            }
            finally
            {
                if (sr != null)
                    Disposable.mkDisposable(sr).dispose();
                 
            }
        }
         
    }

    private static String resolveFullPath(String path) throws Exception {
        if (Path.IsPathRooted(path))
        {
            return path;
        }
         
        return Path.Combine(Environment.CurrentDirectory, path);
    }

}


