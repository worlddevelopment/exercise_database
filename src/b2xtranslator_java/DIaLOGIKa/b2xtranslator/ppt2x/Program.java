//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:23 AM
//

package DIaLOGIKa.b2xtranslator.ppt2x;

import CS2JNet.JavaSupport.util.LocaleSupport;
import CS2JNet.System.DateTimeSupport;
import CS2JNet.System.DoubleSupport;
import CS2JNet.System.IO.DirectorySupport;
import CS2JNet.System.IO.FileNotFoundException;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.TimeSpan;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.InvalidRecordException;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.PresentationDocument;
import DIaLOGIKa.b2xtranslator.ppt2x.Program;
import DIaLOGIKa.b2xtranslator.PptFileFormat.InvalidStreamException;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PowerpointDocument;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Converter;
import DIaLOGIKa.b2xtranslator.Shell.CommandLineTranslator;
import DIaLOGIKa.b2xtranslator.Shell.ProcessingFile;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.MagicNumberException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.StreamNotFoundException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipCreationException;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
public class Program  extends CommandLineTranslator 
{
    public static String ToolName = "ppt2x";
    public static String RevisionResource = "DIaLOGIKa.b2xtranslator.ppt2x.revision.txt";
    public static String ContextMenuInputExtension = ".ppt";
    public static String ContextMenuText = "Convert to .pptx";
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    public static void Main(String[] args) throws Exception {
        Thread.currentThread().CurrentCulture = LocaleSupport.INVARIANT;
        parseArgs(args,ToolName);
        initializeLogger();
        printWelcome(ToolName,RevisionResource);
        if (CreateContextMenuEntry)
        {
            try
            {
                // create context menu entry
                TraceLogger.info("Creating context menu entry for ppt2x ...");
                registerForContextMenu(getContextMenuKey(ContextMenuInputExtension,ContextMenuText));
                TraceLogger.info("Succeeded.");
            }
            catch (Exception __dummyCatchVar0)
            {
                TraceLogger.info("Failed. Sorry :(");
            }
        
        }
        else
        {
            try
            {
                if (InputFile.contains("*.ppt"))
                {
                    String[] files = DirectorySupport.getFiles(InputFile.replace("*.ppt", ""), "*.ppt");
                    for (String file : files)
                    {
                        if (new File(file).Extension.ToLower().EndsWith("ppt"))
                        {
                            ChoosenOutputFile = null;
                            processFile(file);
                        }
                         
                    }
                }
                else
                {
                    processFile(InputFile);
                } 
            }
            catch (ZipCreationException ex)
            {
                TraceLogger.error("Could not create output file {0}.",ChoosenOutputFile);
                //TraceLogger.Error("Perhaps the specified outputfile was a directory or contained invalid characters.");
                TraceLogger.debug(ex.toString());
            }
            catch (FileNotFoundException ex)
            {
                TraceLogger.error("Could not read input file {0}.",InputFile);
                TraceLogger.debug(ex.toString());
            }
            catch (MagicNumberException __dummyCatchVar1)
            {
                TraceLogger.error("Input file {0} is not a valid PowerPoint 97-2007 file.",InputFile);
            }
            catch (InvalidStreamException e)
            {
                TraceLogger.error("Input file {0} is not a valid PowerPoint 97-2007 file.",InputFile);
            }
            catch (InvalidRecordException __dummyCatchVar2)
            {
                TraceLogger.error("Input file {0} is not a valid PowerPoint 97-2007 file.",InputFile);
            }
            catch (StreamNotFoundException e)
            {
                TraceLogger.error("Input file {0} is not a valid PowerPoint 97-2007 file.",InputFile);
            }
            catch (Exception ex)
            {
                TraceLogger.error("Conversion of file {0} failed.",InputFile);
                TraceLogger.debug(ex.toString());
            }
        
        } 
        TraceLogger.info("End of program");
    }

    private static void processFile(String InputFile) throws Exception {
        // copy processing file
        ProcessingFile procFile = new ProcessingFile(InputFile);
        //make output file name
        if (ChoosenOutputFile == null)
        {
            if (InputFile.contains("."))
            {
                ChoosenOutputFile = InputFile.Remove(InputFile.lastIndexOf(".")) + ".pptx";
            }
            else
            {
                ChoosenOutputFile = InputFile + ".pptx";
            } 
        }
         
        //open the reader
        StructuredStorageReader reader = new StructuredStorageReader(procFile.File.FullName);
        try
        {
            {
                // parse the ppt document
                PowerpointDocument ppt = new PowerpointDocument(reader);
                // detect document type and name
                DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType outType = Converter.detectOutputType(ppt);
                String conformOutputFile = Converter.getConformFilename(ChoosenOutputFile,outType);
                // create the pptx document
                PresentationDocument pptx = PresentationDocument.Create(conformOutputFile, outType);
                //start time
                Date start = Calendar.getInstance().getTime();
                TraceLogger.info("Converting file {0} into {1}",InputFile,conformOutputFile);
                // convert
                Converter.convert(ppt,pptx);
                // stop time
                Date end = Calendar.getInstance().getTime();
                TimeSpan diff = (new TimeSpan(Math.abs(end.getTime() - start.getTime())));
                TraceLogger.info("Conversion of file {0} finished in {1} seconds",InputFile,DoubleSupport.ToString(diff.getTotalSeconds(), LocaleSupport.INVARIANT));
            }
        }
        finally
        {
            if (reader != null)
                Disposable.mkDisposable(reader).dispose();
             
        }
    }

}


