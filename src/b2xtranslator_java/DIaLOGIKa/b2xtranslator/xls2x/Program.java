//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:23 AM
//

package DIaLOGIKa.b2xtranslator.xls2x;

import CS2JNet.JavaSupport.util.LocaleSupport;
import CS2JNet.System.DateTimeSupport;
import CS2JNet.System.DoubleSupport;
import CS2JNet.System.IO.FileNotFoundException;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.TimeSpan;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.SpreadsheetDocument;
import DIaLOGIKa.b2xtranslator.Shell.CommandLineTranslator;
import DIaLOGIKa.b2xtranslator.Shell.ProcessingFile;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ParseException;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.XlsDocument;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.Converter;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import DIaLOGIKa.b2xtranslator.xls2x.Program;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipCreationException;
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
public class Program  extends CommandLineTranslator 
{
    public static String ToolName = "xls2x";
    public static String RevisionResource = "DIaLOGIKa.b2xtranslator.xls2x.revision.txt";
    public static String ContextMenuInputExtension = ".xls";
    public static String ContextMenuText = "Convert to .xlsx";
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    static void Main(String[] args) throws Exception {
        parseArgs(args,ToolName);
        initializeLogger();
        printWelcome(ToolName,RevisionResource);
        if (CreateContextMenuEntry)
        {
            try
            {
                // create context menu entry
                TraceLogger.info("Creating context menu entry for xls2x ...");
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
                //copy processing file
                ProcessingFile procFile = new ProcessingFile(InputFile);
                //make output file name
                if (ChoosenOutputFile == null)
                {
                    if (InputFile.contains("."))
                    {
                        ChoosenOutputFile = InputFile.Remove(InputFile.lastIndexOf(".")) + ".xlsx";
                    }
                    else
                    {
                        ChoosenOutputFile = InputFile + ".xlsx";
                    } 
                }
                 
                //parse the document
                StructuredStorageReader reader = new StructuredStorageReader(procFile.File.FullName);
                try
                {
                    {
                        XlsDocument xlsDoc = new XlsDocument(reader);
                        DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType outType = Converter.detectOutputType(xlsDoc);
                        String conformOutputFile = Converter.getConformFilename(ChoosenOutputFile,outType);
                        SpreadsheetDocument spreadx = SpreadsheetDocument.create(conformOutputFile,outType);
                        try
                        {
                            {
                                //start time
                                Date start = Calendar.getInstance().getTime();
                                TraceLogger.info("Converting file {0} into {1}",InputFile,conformOutputFile);
                                Converter.convert(xlsDoc,spreadx);
                                Date end = Calendar.getInstance().getTime();
                                TimeSpan diff = (new TimeSpan(Math.abs(end.getTime() - start.getTime())));
                                TraceLogger.info("Conversion of file {0} finished in {1} seconds",InputFile,DoubleSupport.ToString(diff.getTotalSeconds(), LocaleSupport.INVARIANT));
                            }
                        }
                        finally
                        {
                            if (spreadx != null)
                                Disposable.mkDisposable(spreadx).dispose();
                             
                        }
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
            }
            catch (ParseException ex)
            {
                TraceLogger.error("Could not convert {0} because it was created by an unsupported application (Excel 95 or older).",InputFile);
                TraceLogger.debug(ex.toString());
            }
            catch (DirectoryNotFoundException ex)
            {
                TraceLogger.Error(ex.Message);
                TraceLogger.debug(ex.toString());
            }
            catch (FileNotFoundException ex)
            {
                TraceLogger.error(ex.getMessage());
                TraceLogger.debug(ex.toString());
            }
            catch (ZipCreationException ex)
            {
                TraceLogger.error("Could not create output file {0}.",ChoosenOutputFile);
                TraceLogger.debug(ex.toString());
            }
            catch (Exception ex)
            {
                TraceLogger.error("Conversion of file {0} failed.",InputFile);
                TraceLogger.debug(ex.toString());
            }
        
        } 
    }

}


