//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:31 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SSTData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.StringFormatAssignment;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FontData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FontElementType;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.StyleMappingHelper;

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
public class SSTMapping  extends AbstractOpenXmlMapping implements IMapping<SSTData>
{
    ExcelContext xlsContext;
    /**
    * Ctor
    * 
    *  @param xlsContext The excel context object
    */
    public SSTMapping(ExcelContext xlsContext) throws Exception {
        super(XmlWriter.Create(xlsContext.getSpreadDoc().getWorkbookPart().addSharedStringPart().getStream(), xlsContext.getWriterSettings()));
        this.xlsContext = xlsContext;
    }

    /**
    * The overload apply method
    * Creates the sharedstring xml document
    * 
    *  @param SSTData SharedStringData Object
    */
    public void apply(SSTData sstData) throws Exception {
        _writer.WriteStartDocument();
        _writer.WriteStartElement("sst", OpenXmlNamespaces.SpreadsheetML);
        // count="x" uniqueCount="y"
        _writer.writeAttributeString("count", String.valueOf(sstData.cstTotal));
        _writer.writeAttributeString("uniqueCount", String.valueOf(sstData.cstUnique));
        int count = 0;
        for (String var : sstData.StringList)
        {
            // create the string _entries
            count++;
            CSList<StringFormatAssignment> list = sstData.getFormatingRuns(count);
            _writer.writeStartElement("si");
            if (list.size() == 0)
            {
                // if there is no formatting, there is no run, write only the text
                writeTextNode(_writer,var);
            }
            else
            {
                // if there is no formatting, there is no run, write only the text
                // first text
                if (list.get(0).CharNumber != 0)
                {
                    // no formating for the first letters
                    _writer.writeStartElement("r");
                    writeTextNode(_writer, var.Substring(0, list.get(0).CharNumber));
                    _writer.writeEndElement();
                }
                 
                FontData fd;
                for (int i = 0;i <= list.size() - 2;i++)
                {
                    _writer.writeStartElement("r");
                    fd = this.xlsContext.getXlsDoc().WorkBookData.styleData.FontDataList[list.get(i).FontRecord];
                    StyleMappingHelper.addFontElement(_writer,fd,FontElementType.String);
                    writeTextNode(_writer, var.Substring(list.get(i).CharNumber, list.get(i + 1).CharNumber - list.get(i).CharNumber));
                    _writer.writeEndElement();
                }
                _writer.writeStartElement("r");
                fd = this.xlsContext.getXlsDoc().WorkBookData.styleData.FontDataList[list.get(list.size() - 1).FontRecord];
                StyleMappingHelper.addFontElement(_writer,fd,FontElementType.String);
                writeTextNode(_writer, var.Substring(list.get(list.size() - 1).CharNumber));
                _writer.writeEndElement();
            } 
            _writer.writeEndElement();
        }
        // end si
        // close tags
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        // close writer
        _writer.Flush();
    }

    private void writeTextNode(XmlWriter writer, String text) throws Exception {
        writer.writeStartElement("t");
        if (text.startsWith(" ") || text.endsWith(" ") || text.startsWith("\n") || text.endsWith("\n") || text.startsWith("\r") || text.endsWith("\r"))
        {
            writer.WriteAttributeString("xml", "space", "", "preserve");
        }
         
        writer.writeString(text);
        writer.writeEndElement();
    }

}


