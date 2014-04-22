//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:31 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.JavaSupport.util.LocaleSupport;
import CS2JNet.System.DoubleSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.ChartsheetPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml.Sheet;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.PageSetupSequence;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import java.util.Locale;

/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
public class PageSetupMapping  extends AbstractOpenXmlMapping implements IMapping<PageSetupSequence>
{
    ExcelContext _xlsContext;
    ChartsheetPart _chartsheetPart;
    public PageSetupMapping(ExcelContext xlsContext, ChartsheetPart targetPart) throws Exception {
        super(targetPart.getXmlWriter());
        this._xlsContext = xlsContext;
        this._chartsheetPart = targetPart;
    }

    public void apply(PageSetupSequence pageSetupSequence) throws Exception {
        // page margins
        _writer.WriteStartElement(Sheet.ElPageMargins, Sml.Ns);
        {
            double leftMargin = pageSetupSequence.LeftMargin != null ? pageSetupSequence.LeftMargin.value : 0.75;
            double rightMargin = pageSetupSequence.RightMargin != null ? pageSetupSequence.RightMargin.value : 0.75;
            double topMargin = pageSetupSequence.TopMargin != null ? pageSetupSequence.TopMargin.value : 1.0;
            double bottomMargin = pageSetupSequence.BottomMargin != null ? pageSetupSequence.BottomMargin.value : 1.0;
            _writer.writeAttributeString(Sheet.AttrLeft, DoubleSupport.ToString(leftMargin, LocaleSupport.INVARIANT));
            _writer.writeAttributeString(Sheet.AttrRight, DoubleSupport.ToString(rightMargin, LocaleSupport.INVARIANT));
            _writer.writeAttributeString(Sheet.AttrTop, DoubleSupport.ToString(topMargin, LocaleSupport.INVARIANT));
            _writer.writeAttributeString(Sheet.AttrBottom, DoubleSupport.ToString(bottomMargin, LocaleSupport.INVARIANT));
            _writer.writeAttributeString(Sheet.AttrHeader, DoubleSupport.ToString(pageSetupSequence.Setup.numHdr, LocaleSupport.INVARIANT));
            _writer.writeAttributeString(Sheet.AttrFooter, DoubleSupport.ToString(pageSetupSequence.Setup.numFtr, LocaleSupport.INVARIANT));
        }
        _writer.writeEndElement();
        // page setup
        if (pageSetupSequence.Setup != null)
        {
            _writer.WriteStartElement(Sheet.ElPageSetup, Sml.Ns);
            _writer.writeAttributeString(Sheet.AttrPaperSize, String.valueOf(pageSetupSequence.Setup.iPaperSize));
            if (pageSetupSequence.Setup.fUsePage)
            {
                _writer.writeAttributeString(Sheet.AttrFirstPageNumber, String.valueOf(pageSetupSequence.Setup.iPageStart));
            }
             
            if (pageSetupSequence.Setup.fNoPls == false && pageSetupSequence.Setup.fNoOrient == false)
            {
                // If fNoPls is 1, the value is undefined and MUST be ignored.
                // If fNoOrient is 1, the value is undefined and MUST be ignored.
                _writer.writeAttributeString(Sheet.AttrOrientation, pageSetupSequence.Setup.fPortrait ? "portrait" : "landscape");
            }
            else
            {
                // use landscape as default
                _writer.writeAttributeString(Sheet.AttrOrientation, "landscape");
            } 
            _writer.writeAttributeString(Sheet.AttrUseFirstPageNumber, pageSetupSequence.Setup.fUsePage ? "1" : "0");
            if (!pageSetupSequence.Setup.fNoPls)
            {
                _writer.writeAttributeString(Sheet.AttrHorizontalDpi, String.valueOf(pageSetupSequence.Setup.iRes));
                _writer.writeAttributeString(Sheet.AttrVerticalDpi, String.valueOf(pageSetupSequence.Setup.iVRes));
            }
             
            _writer.writeEndElement();
        }
         
    }

}


