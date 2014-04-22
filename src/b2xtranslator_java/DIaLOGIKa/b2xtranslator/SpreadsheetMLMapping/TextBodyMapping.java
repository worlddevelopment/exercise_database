//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:31 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.JavaSupport.util.LocaleSupport;
import CS2JNet.System.DoubleSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Text;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.TextCharacter;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.TextParagraph;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FontData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.SuperSubScriptStyle;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.UnderlineStyle;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
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
public class TextBodyMapping  extends AbstractChartMapping implements IMapping<AttachedLabelSequence>
{
    public TextBodyMapping(ExcelContext workbookContext, ChartContext chartContext) throws Exception {
        super(workbookContext, chartContext);
    }

    public void apply(AttachedLabelSequence attachedLabelSequence) throws Exception {
        // c:txPr
        _writer.WriteStartElement(Chart.Prefix, Chart.ElTxPr, Chart.Ns);
        {
            // a:bodyPr (is empty for legends)
            _writer.WriteElementString(Dml.Prefix, Text.ElBodyPr, Dml.Ns, "");
            // a:lstStyle (is empty for legends)
            _writer.WriteElementString(Dml.Prefix, Text.ElLstStyle, Dml.Ns, "");
            // a:p
            _writer.WriteStartElement(Dml.Prefix, Text.ElP, Dml.Ns);
            {
                // a:pPr
                _writer.WriteStartElement(Dml.Prefix, Text.ElPPr, Dml.Ns);
                {
                    int fontIndex = 0;
                    if (attachedLabelSequence.FontX != null && attachedLabelSequence.FontX.iFont <= this.getWorkbookContext().getXlsDoc().WorkBookData.styleData.FontDataList.Count)
                    {
                        // FontX.iFont is a 1-based index
                        fontIndex = attachedLabelSequence.FontX.iFont - 1;
                    }
                     
                    FontData fontData = this.getWorkbookContext().getXlsDoc().WorkBookData.styleData.FontDataList[fontIndex];
                    // a:defRPr
                    _writer.WriteStartElement(Dml.Prefix, TextParagraph.ElDefRPr, Dml.Ns);
                    //_writer.WriteAttributeString(Dml.TextCharacter.AttrKumimoji, );
                    //_writer.WriteAttributeString(Dml.TextCharacter.AttrLang,  );
                    //_writer.WriteAttributeString(Dml.TextCharacter.AttrAltLang,  );
                    _writer.writeAttributeString(TextCharacter.AttrSz, DoubleSupport.ToString((fontData.size.toPoints() * 100), LocaleSupport.INVARIANT));
                    _writer.writeAttributeString(TextCharacter.AttrB, fontData.isBold ? "1" : "0");
                    _writer.writeAttributeString(TextCharacter.AttrI, fontData.isItalic ? "1" : "0");
                    _writer.writeAttributeString(TextCharacter.AttrU, mapUnderlineStyle(fontData.uStyle));
                    _writer.writeAttributeString(TextCharacter.AttrStrike, fontData.isStrike ? "sngStrike" : "noStrike");
                    //_writer.WriteAttributeString(Dml.TextCharacter.AttrKern,  );
                    //_writer.WriteAttributeString(Dml.TextCharacter.AttrCap,  );
                    //_writer.WriteAttributeString(Dml.TextCharacter.AttrSpc,  );
                    //_writer.WriteAttributeString(Dml.TextCharacter.AttrNormalizeH,  );
                    if (fontData.vertAlign != SuperSubScriptStyle.none)
                    {
                        _writer.writeAttributeString(TextCharacter.AttrBaseline, fontData.vertAlign == SuperSubScriptStyle.superscript ? "30000" : "-25000");
                    }
                     
                    {
                        //_writer.WriteAttributeString(Dml.TextCharacter.AttrNoProof,  );
                        //_writer.WriteAttributeString(Dml.TextCharacter.AttrDirty,  );
                        //_writer.WriteAttributeString(Dml.TextCharacter.AttrErr,  );
                        //_writer.WriteAttributeString(Dml.TextCharacter.AttrSmtClean,  );
                        //_writer.WriteAttributeString(Dml.TextCharacter.AttrSmtId,  );
                        //_writer.WriteAttributeString(Dml.TextCharacter.AttrBmk,  );
                        // a:latin
                        _writer.WriteStartElement(Dml.Prefix, TextCharacter.ElLatin, Dml.Ns);
                        _writer.writeAttributeString(TextCharacter.AttrTypeface, fontData.fontName);
                        _writer.writeEndElement();
                        // a:latin
                        // a:ea
                        _writer.WriteStartElement(Dml.Prefix, TextCharacter.ElEa, Dml.Ns);
                        _writer.writeAttributeString(TextCharacter.AttrTypeface, fontData.fontName);
                        _writer.writeEndElement();
                        // a:ea
                        // a:cs
                        _writer.WriteStartElement(Dml.Prefix, TextCharacter.ElCs, Dml.Ns);
                        _writer.writeAttributeString(TextCharacter.AttrTypeface, fontData.fontName);
                        _writer.writeEndElement();
                    }
                    // a:cs
                    _writer.writeEndElement();
                }
                // a:defRPr
                _writer.writeEndElement();
            }
            // a:pPr
            _writer.writeEndElement();
        }
        // a:p
        _writer.writeEndElement();
    }

    // c:txPr
    private String mapUnderlineStyle(UnderlineStyle uStyle) throws Exception {
        // TODO: map "accounting" variants
        switch(uStyle)
        {
            case none: 
                return "none";
            case singleLine: 
                return "sng";
            case singleAccounting: 
                return "sng";
            case doubleLine: 
                return "dbl";
            case doubleAccounting: 
                return "dbl";
            default: 
                return "none";
        
        }
    }

}


