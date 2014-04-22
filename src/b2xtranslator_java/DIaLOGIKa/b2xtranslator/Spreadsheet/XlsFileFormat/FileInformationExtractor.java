//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:36 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExtractorException;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.io.PrintWriter;

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
/**
* Extracts the FileSummaryStream
*/
public class FileInformationExtractor   
{
    public VirtualStream summaryStream;
    // Summary stream
    public VirtualStreamReader SummaryStream;
    // Summary stream
    public String Title;
    public String buffer;
    static class BiffHeader   
    {
        public BiffHeader() {
        }

        public RecordType id = RecordType.NAME;
        public UInt16 length = new UInt16();
    }

    /**
    * Ctor
    * 
    *  @param sum Summary stream
    */
    public FileInformationExtractor(VirtualStream sum) throws Exception {
        this.Title = null;
        if (sum == null)
        {
            throw new ExtractorException(ExtractorException.NULLPOINTEREXCEPTION);
        }
         
        this.summaryStream = sum;
        this.SummaryStream = new VirtualStreamReader(sum);
        this.extractData();
    }

    /**
    * Extracts the data from the stream
    */
    public void extractData() throws Exception {
        BiffHeader bh = new BiffHeader();
        PrintWriter sw = null;
        sw = new PrintWriter(Console.OpenStandardOutput());
        try
        {
            while (Unsupported.throwUnsupported("this.SummaryStream.getBaseStream().Position") < this.SummaryStream.getBaseStream().Length)
            {
                bh.id = (RecordType)this.SummaryStream.readUInt16();
                bh.length = this.SummaryStream.readUInt16();
                byte[] buf = new byte[bh.length];
                if (bh.length != this.SummaryStream.readByte())
                    sw.println("EOF");
                 
                System.out.printf(StringSupport.CSFmtStrToJFmtStr("BIFF {0}\t{1}\t"),bh.id,bh.length);
                //Dump(buffer);
                int count = 0;
                for (byte b : buf)
                {
                    System.out.printf(StringSupport.CSFmtStrToJFmtStr("{0:X02} "),b);
                    count++;
                    if (count % 16 == 0 && count < buf.length)
                        sw.print("\n\t\t\t");
                     
                }
                sw.print("\n");
            }
        }
        catch (Exception ex)
        {
            TraceLogger.error(ex.getMessage());
            TraceLogger.debug(ex.toString());
        }

        this.buffer = sw.toString();
    }

    /**
    * A normal overload ToString Method
    * 
    *  @return
    */
    public String toString() {
        try
        {
            String returnvalue = "Title: " + this.Title;
            return returnvalue;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


