//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:23 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.doc2x;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.doc2x.Program;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.ByteParseException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.UnspportedFileVersionException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.WordprocessingDocument;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.Shell.CommandLineTranslator;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.Shell.ProcessingFile;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.MagicNumberException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ReadBytesAmountMismatchException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.Converter;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.MappingException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.ZipUtils.ZipCreationException;
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
    public static String ToolName = "doc2x";
    public static String RevisionResource = "DIaLOGIKa.b2xtranslator.doc2x.revision.txt";
    public static String ContextMenuInputExtension = ".doc";
    public static String ContextMenuText = "Convert to .docx";
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    public static void Main(String[] args) throws Exception {
        parseArgs(args,ToolName);
        initializeLogger();
        printWelcome(ToolName,RevisionResource);
        if (CreateContextMenuEntry)
        {
            try
            {
                // create context menu entry
                System.out.println("Creating context menu entry for doc2x ...");
                registerForContextMenu(getContextMenuKey(ContextMenuInputExtension,ContextMenuText));
                System.out.println("Succeeded.");
            }
            catch (Exception __dummyCatchVar0)
            {
                System.out.println("Failed. Sorry :(");
            }
        
        }
        else
        {
            try
            {
                // convert
                //copy processing file
                ProcessingFile procFile = new ProcessingFile(InputFile);
                //make output file name
                if (ChoosenOutputFile == null)
                {
                    if (InputFile.contains("."))
                    {
                        ChoosenOutputFile = InputFile.Remove(InputFile.lastIndexOf(".")) + ".docx";
                    }
                    else
                    {
                        ChoosenOutputFile = InputFile + ".docx";
                    } 
                }
                 
                //open the reader
                StructuredStorageReader reader = new StructuredStorageReader(procFile.File.FullName);
                try
                {
                    {
                        //parse the input document
                        WordDocument doc = new WordDocument(reader);
                        //prepare the output document
                        DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType outType = Converter.detectOutputType(doc);
                        String conformOutputFile = Converter.getConformFilename(ChoosenOutputFile,outType);
                        WordprocessingDocument docx = WordprocessingDocument.create(conformOutputFile,outType);
                        //start time
                        Date start = Calendar.getInstance().getTime();
                        System.out.println("Converting file " + InputFile + " into " +conformOutputFile);
                        //convert the document
                        Converter.convert(doc,docx);
                        Date end = Calendar.getInstance().getTime();
                        long diff = Math.abs(end.getTime() - start.getTime());
                        System.out.println("Conversion of file {0} finished in {1} seconds",InputFile,DoubleSupport.ToString(diff.getTotalSeconds(), LocaleSupport.INVARIANT));
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
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
            catch (ReadBytesAmountMismatchException ex)
            {
                TraceLogger.error("Input file {0} is not a valid Microsoft Word 97-2003 file.",InputFile);
                TraceLogger.debug(ex.toString());
            }
            catch (MagicNumberException ex)
            {
                TraceLogger.error("Input file {0} is not a valid Microsoft Word 97-2003 file.",InputFile);
                TraceLogger.debug(ex.toString());
            }
            catch (UnspportedFileVersionException ex)
            {
                TraceLogger.error("File {0} has been created with a Word version older than Word 97.",InputFile);
                TraceLogger.debug(ex.toString());
            }
            catch (ByteParseException ex)
            {
                TraceLogger.error("Input file {0} is not a valid Microsoft Word 97-2003 file.",InputFile);
                TraceLogger.debug(ex.toString());
            }
            catch (MappingException ex)
            {
                TraceLogger.error("There was an error while converting file {0}: {1}",InputFile,ex.getMessage());
                TraceLogger.debug(ex.toString());
            }
            catch (ZipCreationException ex)
            {
                TraceLogger.error("Could not create output file {0}.",ChoosenOutputFile);
                //TraceLogger.Error("Perhaps the specified outputfile was a directory or contained invalid characters.");
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


