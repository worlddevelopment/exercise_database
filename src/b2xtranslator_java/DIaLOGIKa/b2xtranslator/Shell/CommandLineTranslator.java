//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.Shell;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.DoubleSupport;
import CS2JNet.System.IO.StreamReader;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Text.EncodingSupport;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
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
public class CommandLineTranslator   
{
    // parsed arguments
    public static String InputFile;
    public static String ChoosenOutputFile;
    public static boolean CreateContextMenuEntry;
    public static void initializeLogger() throws Exception {
        // let the Console listen to the Trace messages
        Trace.Listeners.Add(new TextWriterTraceListener(System.out));
    }

    /**
    * Prints the heading row of the tool
    */
    public static void printWelcome(String toolname, String revisionResource) throws Exception {
        boolean backup = TraceLogger.EnableTimeStamp;
        TraceLogger.EnableTimeStamp = false;
        StringBuilder welcome = new StringBuilder();
        welcome.append("Welcome to ");
        welcome.append(toolname);
        welcome.append(" (r");
        welcome.append(getRevision(revisionResource));
        welcome.append(")\n");
        welcome.append("Copyright (c) 2009, DIaLOGIKa. All rights reserved.");
        welcome.append("\n");
        TraceLogger.simple(welcome.toString());
        TraceLogger.EnableTimeStamp = backup;
    }

    /**
    * Prints the usage row of the tool
    */
    public static void printUsage(String toolname) throws Exception {
        StringBuilder usage = new StringBuilder();
        usage.append(("Usage: " + toolname + " [-c | inputfile] [-o outputfile] [-v level] [-?]") + System.getProperty("line.separator"));
        usage.append(("-o <outputfile>  change output filename") + System.getProperty("line.separator"));
        usage.append(("-v <level>     set trace level, where <level> is one of the following:") + System.getProperty("line.separator"));
        usage.append(("               none (0)    print nothing") + System.getProperty("line.separator"));
        usage.append(("               error (1)   print all errors") + System.getProperty("line.separator"));
        usage.append(("               warning (2) print all errors and warnings") + System.getProperty("line.separator"));
        usage.append(("               info (3)    print all errors, warnings and infos (default)") + System.getProperty("line.separator"));
        usage.append(("               debug (4)   print all errors, warnings, infos and debug messages") + System.getProperty("line.separator"));
        usage.append(("-c             create an entry in context menu") + System.getProperty("line.separator"));
        usage.append(("-?             print this help") + System.getProperty("line.separator"));
        System.out.println(usage.toString());
    }

    /**
    * Returns the revision that is stored in the embedded resource "revision.txt".
    * Returns -1 if something goes wrong
    * 
    *  @return
    */
    private static int getRevision(String revisionResource) throws Exception {
        int rev = -1;
        try
        {
            Assembly a = Assembly.GetEntryAssembly();
            InputStream s = a.GetManifestResourceStream(revisionResource);
            BufferedReader reader = new BufferedReader(StreamReader.make(new BufferedInputStream(s), new EncodingSupport("UTF-8")));
            rev = Integer.valueOf(reader.readLine());
            s.close();
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return rev;
    }

    public static RegistryKey getContextMenuKey(String triggerExtension, String contextMenuText) throws Exception {
        RegistryKey result = null;
        try
        {
            String defaultWord = (String)Registry.ClassesRoot.OpenSubKey(triggerExtension).GetValue("");
            result = Registry.ClassesRoot.CreateSubKey(defaultWord).CreateSubKey("shell").CreateSubKey(contextMenuText);
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return result;
    }

    public static void registerForContextMenu(RegistryKey entryKey) throws Exception {
        if (entryKey != null)
        {
            RegistryKey convertCommand = entryKey.CreateSubKey("Command");
            convertCommand.SetValue("", String.format(StringSupport.CSFmtStrToJFmtStr("\"{0}\" \"%1\""),Assembly.GetCallingAssembly().Location));
        }
         
    }

    /**
    * Parses the arguments of the tool
    * 
    *  @param args The args array
    */
    public static void parseArgs(String[] args, String toolName) throws Exception {
        try
        {
            if (StringSupport.equals(args[0], "-?"))
            {
                printUsage(toolName);
                Environment.Exit(0);
            }
            else if (StringSupport.equals(args[0].toLowerCase(), "-c"))
            {
                CreateContextMenuEntry = true;
            }
            else
            {
                InputFile = args[0];
            }  
            for (int i = 1;i < args.length;i++)
            {
                if (StringSupport.equals(args[i].toLowerCase(), "-v"))
                {
                    //parse verbose level
                    String verbose = args[i + 1].toLowerCase();
                    int vLvl;
                    boolean boolVar___0 = DoubleSupport.tryParse(verbose, refVar___0);
                    if (boolVar___0)
                    {
                        TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.values()[vLvl]);
                    }
                    else
                    {
                        boolean boolVar___1 = StringSupport.equals(verbose, "error");
                        if (boolVar___1)
                        {
                            TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Error);
                        }
                        else
                        {
                            boolean boolVar___2 = StringSupport.equals(verbose, "warning");
                            if (boolVar___2)
                            {
                                TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Warning);
                            }
                            else
                            {
                                boolean boolVar___3 = StringSupport.equals(verbose, "info");
                                if (boolVar___3)
                                {
                                    TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Info);
                                }
                                else
                                {
                                    boolean boolVar___4 = StringSupport.equals(verbose, "debug");
                                    if (boolVar___4)
                                    {
                                        TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.Debug);
                                    }
                                    else
                                    {
                                        RefSupport<Integer> refVar___0 = new RefSupport<Integer>();
                                        boolean boolVar___5 = StringSupport.equals(verbose, "none");
                                        vLvl = refVar___0.getValue();
                                        if (boolVar___5)
                                        {
                                            TraceLogger.setLogLevel(DIaLOGIKa.b2xtranslator.Tools.TraceLogger.LoggingLevel.None);
                                        }
                                         
                                    } 
                                } 
                            } 
                        } 
                    } 
                }
                else if (StringSupport.equals(args[i].toLowerCase(), "-o"))
                {
                    //parse output file name
                    ChoosenOutputFile = args[i + 1];
                }
                  
            }
        }
        catch (Exception __dummyCatchVar2)
        {
            TraceLogger.error("At least one of the required arguments was not correctly set.\n");
            printUsage(toolName);
            Environment.Exit(1);
        }
    
    }

}


