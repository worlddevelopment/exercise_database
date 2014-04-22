//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:29 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ExternalRelationship;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.CRNData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SupBookData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.XCTData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import java.net.URI;
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
public class ExternalLinkMapping  extends AbstractOpenXmlMapping implements IMapping<SupBookData>
{
    ExcelContext xlsContext;
    /**
    * Ctor
    * 
    *  @param xlsContext The excel context object
    */
    public ExternalLinkMapping(ExcelContext xlsContext) throws Exception {
        super(XmlWriter.Create(xlsContext.getSpreadDoc().getWorkbookPart().addExternalLinkPart().getStream(), xlsContext.getWriterSettings()));
        this.xlsContext = xlsContext;
    }

    /**
    * The overload apply method
    * Creates the Workbook xml document
    * 
    *  @param bsd WorkSheetData
    */
    public void apply(SupBookData sbd) throws Exception {
        URI uri = new URI(sbd.getVirtPath(), UriKind.RelativeOrAbsolute);
        ExternalRelationship er = this.xlsContext.getSpreadDoc().getWorkbookPart().getExternalLinkPart().addExternalRelationship(OpenXmlRelationshipTypes.ExternalLinkPath,uri);
        _writer.WriteStartDocument();
        _writer.WriteStartElement("externalLink", OpenXmlNamespaces.SpreadsheetML);
        _writer.writeStartElement("externalBook");
        _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, er.getId().toString());
        _writer.writeStartElement("sheetNames");
        for (String var : sbd.getRGST())
        {
            _writer.writeStartElement("sheetName");
            _writer.writeAttributeString("val", var);
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        // checks if some externNames exist
        if (sbd.getExternNames().Count > 0)
        {
            _writer.writeStartElement("definedNames");
            for (Object __dummyForeachVar1 : sbd.getExternNames())
            {
                String var = (String)__dummyForeachVar1;
                _writer.writeStartElement("definedName");
                _writer.writeAttributeString("name", var);
                _writer.writeEndElement();
            }
            _writer.writeEndElement();
        }
         
        if (sbd.getXCTDataList().Count > 0)
        {
            _writer.writeStartElement("sheetDataSet");
            int counter = 0;
            for (Object __dummyForeachVar3 : sbd.getXCTDataList())
            {
                XCTData var = (XCTData)__dummyForeachVar3;
                _writer.writeStartElement("sheetData");
                _writer.writeAttributeString("sheetId", String.valueOf(counter));
                counter++;
                for (Object __dummyForeachVar2 : var.getCRNDataList())
                {
                    CRNData crn = (CRNData)__dummyForeachVar2;
                    _writer.writeStartElement("row");
                    _writer.writeAttributeString("r", (crn.rw + 1).toString());
                    for (byte i = crn.colFirst;i <= crn.colLast;i++)
                    {
                        _writer.writeStartElement("cell");
                        _writer.writeAttributeString("r", ExcelHelperClass.intToABCString((int)i,(crn.rw + 1).toString()));
                        if (crn.oper[i - crn.colFirst] instanceof Boolean)
                        {
                            _writer.writeAttributeString("t", "b");
                            if ((boolean)crn.oper[i - crn.colFirst])
                            {
                                _writer.WriteElementString("v", "1");
                            }
                            else
                            {
                                _writer.WriteElementString("v", "0");
                            } 
                        }
                         
                        if (crn.oper[i - crn.colFirst] instanceof Double)
                        {
                            // _writer.WriteAttributeString("t", "b");
                            _writer.WriteElementString("v", Convert.ToString(crn.oper[i - crn.colFirst], Locale.GetCultureInfo("en-US")));
                        }
                         
                        if (crn.oper[i - crn.colFirst] instanceof String)
                        {
                            _writer.writeAttributeString("t", "str");
                            _writer.WriteElementString("v", crn.oper[i - crn.colFirst].toString());
                        }
                         
                        _writer.writeEndElement();
                    }
                    _writer.writeEndElement();
                }
                _writer.writeEndElement();
            }
            _writer.writeEndElement();
        }
         
        _writer.writeEndElement();
        _writer.writeEndElement();
        // close worksheet
        _writer.WriteEndDocument();
        sbd.ExternalLinkId = this.xlsContext.getSpreadDoc().getWorkbookPart().getExternalLinkPart().getRelId();
        sbd.ExternalLinkRef = this.xlsContext.getSpreadDoc().getWorkbookPart().getExternalLinkPart().getRelIdToString();
        // close writer
        _writer.Flush();
    }

}


