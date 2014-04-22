//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ExternalRelationship;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml.Sheet;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.WorksheetPart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.AbstractCellData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.ColumnInfoData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.FormulaCell;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.HyperlinkData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.MergeCellData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.RowData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SharedFormulaData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.StringCell;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WorkSheetData;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.DrawingMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.FormulaInfixMapping;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import DIaLOGIKa.b2xtranslator.Tools.TwipsValue;
import java.util.Collections;
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
public class WorksheetMapping  extends AbstractOpenXmlMapping implements IMapping<WorkSheetData>
{
    ExcelContext _xlsContext;
    WorksheetPart _worksheetPart;
    /**
    * Ctor
    * 
    *  @param xlsContext The excel context object
    */
    public WorksheetMapping(ExcelContext xlsContext, WorksheetPart worksheetPart) throws Exception {
        super(worksheetPart.getXmlWriter());
        this._xlsContext = xlsContext;
        this._worksheetPart = worksheetPart;
    }

    /**
    * The overload apply method
    * Creates the Worksheet xml document
    * 
    *  @param bsd WorkSheetData
    */
    public void apply(WorkSheetData bsd) throws Exception {
        _xlsContext.setCurrentSheet(bsd);
        _writer.WriteStartDocument();
        _writer.WriteStartElement("worksheet", OpenXmlNamespaces.SpreadsheetML);
        {
            //if (bsd.emtpyWorksheet)
            //{
            //    _writer.WriteStartElement("sheetData");
            //    _writer.WriteEndElement();
            //}
            //else
            // default info
            if (bsd.defaultColWidth >= 0 || bsd.defaultRowHeight >= 0)
            {
                _writer.writeStartElement("sheetFormatPr");
                if (bsd.defaultColWidth >= 0)
                {
                    double colWidht = (double)bsd.defaultColWidth;
                    _writer.WriteAttributeString("defaultColWidth", Convert.ToString(colWidht, Locale.GetCultureInfo("en-US")));
                }
                 
                if (bsd.defaultRowHeight >= 0)
                {
                    TwipsValue tv = new TwipsValue(bsd.defaultRowHeight);
                    _writer.WriteAttributeString("defaultRowHeight", Convert.ToString(tv.toPoints(), Locale.GetCultureInfo("en-US")));
                }
                 
                if (bsd.zeroHeight)
                {
                    _writer.writeAttributeString("zeroHeight", "1");
                }
                 
                if (bsd.customHeight)
                {
                    _writer.writeAttributeString("customHeight", "1");
                }
                 
                if (bsd.thickTop)
                {
                    _writer.writeAttributeString("thickTop", "1");
                }
                 
                if (bsd.thickBottom)
                {
                    _writer.writeAttributeString("thickBottom", "1");
                }
                 
                _writer.writeEndElement();
            }
             
            // sheetFormatPr
            // Col info
            if (bsd.colInfoDataTable.size() > 0)
            {
                _writer.writeStartElement("cols");
                for (ColumnInfoData col : bsd.colInfoDataTable)
                {
                    _writer.writeStartElement("col");
                    // write min and max
                    // booth values are 0 based in the binary format and 1 based in the oxml format
                    // so you have to add 1 to the value!
                    _writer.writeAttributeString("min", String.valueOf((col.min + 1)));
                    _writer.writeAttributeString("max", String.valueOf((col.max + 1)));
                    if (col.widht != 0)
                    {
                        double colWidht = (double)col.widht / 256;
                        _writer.WriteAttributeString("width", Convert.ToString(colWidht, Locale.GetCultureInfo("en-US")));
                    }
                     
                    if (col.hidden)
                        _writer.writeAttributeString("hidden", "1");
                     
                    if (col.outlineLevel > 0)
                        _writer.writeAttributeString("outlineLevel", String.valueOf(col.outlineLevel));
                     
                    if (col.customWidth)
                        _writer.writeAttributeString("customWidth", "1");
                     
                    if (col.bestFit)
                        _writer.writeAttributeString("bestFit", "1");
                     
                    if (col.phonetic)
                        _writer.writeAttributeString("phonetic", "1");
                     
                    if (col.style > 15)
                    {
                        _writer.WriteAttributeString("style", Convert.ToString(col.style - this._xlsContext.getXlsDoc().WorkBookData.styleData.XFCellStyleDataList.Count, Locale.GetCultureInfo("en-US")));
                    }
                     
                    _writer.writeEndElement();
                }
                // col
                _writer.writeEndElement();
            }
             
            // End col info
            _writer.writeStartElement("sheetData");
            for (Object __dummyForeachVar2 : bsd.rowDataTable.Values)
            {
                //  bsd.rowDataTable.Values
                RowData row = (RowData)__dummyForeachVar2;
                // write row start tag
                // Row
                _writer.writeStartElement("row");
                // the rowindex from the binary format is zero based, the ooxml format is one based
                _writer.writeAttributeString("r", String.valueOf((row.getRow() + 1)));
                if (row.height != null)
                {
                    _writer.WriteAttributeString("ht", Convert.ToString(row.height.toPoints(), Locale.GetCultureInfo("en-US")));
                    if (row.customHeight)
                    {
                        _writer.writeAttributeString("customHeight", "1");
                    }
                     
                }
                 
                if (row.hidden)
                {
                    _writer.writeAttributeString("hidden", "1");
                }
                 
                if (row.outlineLevel > 0)
                {
                    _writer.writeAttributeString("outlineLevel", String.valueOf(row.outlineLevel));
                }
                 
                if (row.collapsed)
                {
                    _writer.writeAttributeString("collapsed", "1");
                }
                 
                if (row.customFormat)
                {
                    _writer.writeAttributeString("customFormat", "1");
                    if (row.style > 15)
                    {
                        _writer.writeAttributeString("s", String.valueOf((row.style - this._xlsContext.getXlsDoc().WorkBookData.styleData.XFCellStyleDataList.Count)));
                    }
                     
                }
                 
                if (row.thickBot)
                {
                    _writer.writeAttributeString("thickBot", "1");
                }
                 
                if (row.thickTop)
                {
                    _writer.writeAttributeString("thickTop", "1");
                }
                 
                if (row.minSpan + 1 > 0 && row.maxSpan > 0 && row.minSpan + 1 < row.maxSpan)
                {
                    _writer.writeAttributeString("spans", String.valueOf((row.minSpan + 1)) + ":" + String.valueOf(row.maxSpan));
                }
                 
                Collections.sort(row.getCells());
                for (AbstractCellData cell : row.getCells())
                {
                    // Col
                    _writer.writeStartElement("c");
                    _writer.writeAttributeString("r", ExcelHelperClass.intToABCString(((int)(cell.getCol())),String.valueOf((cell.getRow() + 1))));
                    if (cell.getTemplateID() > 15)
                    {
                        _writer.writeAttributeString("s", String.valueOf((cell.getTemplateID() - this._xlsContext.getXlsDoc().WorkBookData.styleData.XFCellStyleDataList.Count)));
                    }
                     
                    if (cell instanceof StringCell)
                    {
                        _writer.writeAttributeString("t", "s");
                    }
                     
                    if (cell instanceof FormulaCell)
                    {
                        FormulaCell fcell = (FormulaCell)cell;
                        if (((FormulaCell)cell).calculatedValue instanceof String)
                        {
                            _writer.writeAttributeString("t", "str");
                        }
                        else if (((FormulaCell)cell).calculatedValue instanceof Double)
                        {
                            _writer.writeAttributeString("t", "n");
                        }
                        else if (((FormulaCell)cell).calculatedValue instanceof Byte)
                        {
                            _writer.writeAttributeString("t", "b");
                        }
                        else if (((FormulaCell)cell).calculatedValue instanceof Integer)
                        {
                            _writer.writeAttributeString("t", "e");
                        }
                            
                        // <f>1</f>
                        _writer.writeStartElement("f");
                        if (!fcell.isSharedFormula)
                        {
                            String value = FormulaInfixMapping.mapFormula(fcell.getPtgStack(),this._xlsContext);
                            if (fcell.usesArrayRecord)
                            {
                                _writer.writeAttributeString("t", "array");
                                _writer.writeAttributeString("ref", ExcelHelperClass.intToABCString(((int)(cell.getCol())),String.valueOf((cell.getRow() + 1))));
                            }
                             
                            if (fcell.alwaysCalculated)
                            {
                                _writer.writeAttributeString("ca", "1");
                            }
                             
                            if (value.equals(""))
                            {
                                TraceLogger.debug("Formula Parse Error in Row {0}\t Column {1}\t",String.valueOf(cell.getRow()),String.valueOf(cell.getCol()));
                            }
                             
                            _writer.writeString(value);
                        }
                        else
                        {
                            /**
                            * If this cell is part of a shared formula
                            */
                            SharedFormulaData sfd = bsd.checkFormulaIsInShared(cell.getRow(),cell.getCol());
                            if (sfd != null)
                            {
                                // t="shared"
                                _writer.writeAttributeString("t", "shared");
                                //  <f t="shared" ref="C4:C11" si="0">H4+I4-J4</f>
                                _writer.writeAttributeString("si", String.valueOf(sfd.ID));
                                if (sfd.RefCount == 0)
                                {
                                    /**
                                    * Write value and reference
                                    */
                                    _writer.writeAttributeString("ref", sfd.getOXMLFormatedData());
                                    String value = FormulaInfixMapping.mapFormula(sfd.getPtgStack(), this._xlsContext, sfd.rwFirst, sfd.colFirst);
                                    _writer.writeString(value);
                                    sfd.RefCount++;
                                }
                                 
                            }
                            else
                            {
                                TraceLogger.debug("Formula Parse Error in Row {0}\t Column {1}\t",String.valueOf(cell.getRow()),String.valueOf(cell.getCol()));
                            } 
                        } 
                        _writer.writeEndElement();
                        /**
                        * write down calculated value from a formula
                        */
                        _writer.writeStartElement("v");
                        if (((FormulaCell)cell).calculatedValue instanceof Integer)
                        {
                            _writer.writeString(FormulaInfixMapping.getErrorStringfromCode((Integer)((FormulaCell)cell).calculatedValue));
                        }
                        else
                        {
                            _writer.WriteString(Convert.ToString(((FormulaCell)cell).calculatedValue, Locale.GetCultureInfo("en-US")));
                        } 
                        _writer.writeEndElement();
                    }
                    else
                    {
                        // Data !!!
                        _writer.WriteElementString("v", cell.getValue());
                    } 
                    // add a type to the c element if the formula returns following types
                    _writer.writeEndElement();
                }
                // close cell (c)
                _writer.writeEndElement();
            }
            // close row
            // close tags
            _writer.writeEndElement();
            // close sheetData
            // Add the mergecell part
            //
            // - <mergeCells count="2">
            //        <mergeCell ref="B3:C3" />
            //        <mergeCell ref="E3:F4" />
            //     </mergeCells>
            if (bsd.MERGECELLSData != null)
            {
                _writer.writeStartElement("mergeCells");
                _writer.writeAttributeString("count", bsd.MERGECELLSData.cmcs.toString());
                for (MergeCellData mcell : bsd.MERGECELLSData.mergeCellDataList)
                {
                    _writer.writeStartElement("mergeCell");
                    _writer.writeAttributeString("ref", mcell.getOXMLFormatedData());
                    _writer.writeEndElement();
                }
                // close mergeCells Tag
                _writer.writeEndElement();
            }
             
            // hyperlinks!
            if (bsd.HyperLinkList.size() != 0)
            {
                _writer.writeStartElement("hyperlinks");
                boolean writtenParentElement = false;
                for (HyperlinkData link : bsd.HyperLinkList)
                {
                    //    Uri url;
                    //    if (link.absolute)
                    //    {
                    //        if (link.url.StartsWith("http", true, CultureInfo.GetCultureInfo("en-US"))
                    //            || link.url.StartsWith("mailto", true, CultureInfo.GetCultureInfo("en-US")))
                    //        {
                    //            url = new Uri(link.url, UriKind.Absolute);
                    //        }
                    //        else
                    //        {
                    //            link.url = "file:///" + link.url;
                    //            url = new Uri(link.url, UriKind.Absolute);
                    //        }
                    //    }
                    //    else
                    //    {
                    //        url = new Uri(link.url, UriKind.Relative);
                    //    }
                    //    try
                    //    {
                    //        if (System.Uri.IsWellFormedUriString(url.LocalPath.ToString(), System.UriKind.Absolute))
                    //        {
                    //if (!writtenParentElement)
                    //{
                    //    writtenParentElement = true;
                    //}
                    String refstring;
                    if (link.colLast == link.colFirst && link.rwLast == link.rwFirst)
                    {
                        refstring = ExcelHelperClass.intToABCString((int)link.colLast,(link.rwLast + 1).toString());
                    }
                    else
                    {
                        refstring = ExcelHelperClass.intToABCString((int)link.colFirst,(link.rwFirst + 1).toString()) + ":" + ExcelHelperClass.intToABCString((int)link.colLast,(link.rwLast + 1).toString());
                    } 
                    if (link.url != null)
                    {
                        ExternalRelationship er = this._xlsContext.getSpreadDoc().getWorkbookPart().getWorksheetPart().addExternalRelationship(OpenXmlRelationshipTypes.HyperLink,link.url.replace(" ", ""));
                        _writer.writeStartElement("hyperlink");
                        _writer.writeAttributeString("ref", refstring);
                        _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, er.getId().toString());
                        _writer.writeEndElement();
                    }
                    else if (link.location != null)
                    {
                        _writer.writeStartElement("hyperlink");
                        _writer.writeAttributeString("ref", refstring);
                        _writer.writeAttributeString("location", link.location);
                        if (link.display != null)
                        {
                            _writer.writeAttributeString("display", link.display);
                        }
                         
                        _writer.writeEndElement();
                    }
                      
                }
                /*           }
                                     }
                                        catch (Exception ex)
                                        {
                                            TraceLogger.DebugInternal(ex.Message.ToString());
                                            TraceLogger.DebugInternal(ex.StackTrace.ToString());
                                        }
                                    }*/
                _writer.writeEndElement();
                // hyperlinks
                if (writtenParentElement)
                {
                }
                 
            }
             
            // worksheet margins !!
            if (bsd.leftMargin != null && bsd.topMargin != null && bsd.rightMargin != null && bsd.bottomMargin != null && bsd.headerMargin != null && bsd.footerMargin != null)
            {
                _writer.writeStartElement("pageMargins");
                {
                    _writer.WriteAttributeString("left", Convert.ToString(bsd.leftMargin, Locale.GetCultureInfo("en-US")));
                    _writer.WriteAttributeString("right", Convert.ToString(bsd.rightMargin, Locale.GetCultureInfo("en-US")));
                    _writer.WriteAttributeString("top", Convert.ToString(bsd.topMargin, Locale.GetCultureInfo("en-US")));
                    _writer.WriteAttributeString("bottom", Convert.ToString(bsd.bottomMargin, Locale.GetCultureInfo("en-US")));
                    _writer.WriteAttributeString("header", Convert.ToString(bsd.headerMargin, Locale.GetCultureInfo("en-US")));
                    _writer.WriteAttributeString("footer", Convert.ToString(bsd.footerMargin, Locale.GetCultureInfo("en-US")));
                }
                _writer.writeEndElement();
            }
             
            // pageMargins
            // page setup settings
            if (bsd.getPageSetup() != null)
            {
                _writer.writeStartElement("pageSetup");
                if (!bsd.getPageSetup().fNoPls && bsd.getPageSetup().iPaperSize > 0 && bsd.getPageSetup().iPaperSize < 255)
                {
                    _writer.writeAttributeString("paperSize", bsd.getPageSetup().iPaperSize.toString());
                }
                 
                if (bsd.getPageSetup().iScale >= 10 && bsd.getPageSetup().iScale <= 400)
                {
                    _writer.writeAttributeString("scale", bsd.getPageSetup().iScale.toString());
                }
                 
                _writer.writeAttributeString("firstPageNumber", bsd.getPageSetup().iPageStart.toString());
                _writer.writeAttributeString("fitToWidth", bsd.getPageSetup().iFitWidth.toString());
                _writer.writeAttributeString("fitToHeight", bsd.getPageSetup().iFitHeight.toString());
                if (bsd.getPageSetup().fLeftToRight)
                    _writer.writeAttributeString("pageOrder", "overThenDown");
                 
                if (!bsd.getPageSetup().fNoOrient)
                {
                    if (bsd.getPageSetup().fPortrait)
                        _writer.writeAttributeString("orientation", "portrait");
                    else
                        _writer.writeAttributeString("orientation", "landscape"); 
                }
                 
                //10 <attribute name="usePrinterDefaults" type="xsd:boolean" use="optional" default="true"/>
                if (bsd.getPageSetup().fNoColor)
                    _writer.writeAttributeString("blackAndWhite", "1");
                 
                if (bsd.getPageSetup().fDraft)
                    _writer.writeAttributeString("draft", "1");
                 
                if (bsd.getPageSetup().fNotes)
                {
                    if (bsd.getPageSetup().fEndNotes)
                        _writer.writeAttributeString("cellComments", "atEnd");
                    else
                        _writer.writeAttributeString("cellComments", "asDisplayed"); 
                }
                 
                if (bsd.getPageSetup().fUsePage)
                    _writer.writeAttributeString("useFirstPageNumber", "1");
                 
                switch(bsd.getPageSetup().iErrors)
                {
                    case 0x00: 
                        _writer.writeAttributeString("errors", "displayed");
                        break;
                    case 0x01: 
                        _writer.writeAttributeString("errors", "blank");
                        break;
                    case 0x02: 
                        _writer.writeAttributeString("errors", "dash");
                        break;
                    case 0x03: 
                        _writer.writeAttributeString("errors", "NA");
                        break;
                    default: 
                        _writer.writeAttributeString("errors", "displayed");
                        break;
                
                }
                _writer.writeAttributeString("horizontalDpi", bsd.getPageSetup().iRes.toString());
                _writer.writeAttributeString("verticalDpi", bsd.getPageSetup().iVRes.toString());
                if (!bsd.getPageSetup().fNoPls)
                {
                    _writer.writeAttributeString("copies", bsd.getPageSetup().iCopies.toString());
                }
                 
                _writer.writeEndElement();
            }
             
            // embedded drawings (charts etc)
            if (bsd.ObjectsSequence != null)
            {
                _writer.WriteStartElement(Sheet.ElDrawing, Sml.Ns);
                {
                    _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, this._worksheetPart.getDrawingsPart().getRelIdToString());
                    bsd.ObjectsSequence.Convert(new DrawingMapping(this._xlsContext,this._worksheetPart.getDrawingsPart(),false));
                }
                _writer.writeEndElement();
            }
             
        }
        _writer.writeEndElement();
        // close worksheet
        _writer.WriteEndDocument();
        // close writer
        _writer.Flush();
    }

}


