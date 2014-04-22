//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:31 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Style;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.BorderData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FillData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FontData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FontElementType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FormatData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.StyleData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.XFData;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.StyleMappingHelper;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.StylesMapping;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;

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
public class StylesMapping  extends AbstractOpenXmlMapping implements IMapping<StyleData>
{
    ExcelContext xlsContext;
    /**
    * Ctor
    * 
    *  @param xlsContext The excel context object
    */
    public StylesMapping(ExcelContext xlsContext) throws Exception {
        super(XmlWriter.Create(xlsContext.getSpreadDoc().getWorkbookPart().addStylesPart().getStream(), xlsContext.getWriterSettings()));
        this.xlsContext = xlsContext;
    }

    /**
    * The overload apply method
    * Creates the Styles xml document
    * 
    *  @param sd StyleData Object
    */
    public void apply(StyleData sd) throws Exception {
        _writer.WriteStartDocument();
        _writer.WriteStartElement("styleSheet", OpenXmlNamespaces.SpreadsheetML);
        // Format mapping
        _writer.writeStartElement("numFmts");
        _writer.writeAttributeString("count", String.valueOf(sd.getFormatDataList().size()));
        for (FormatData format : sd.getFormatDataList())
        {
            _writer.writeStartElement("numFmt");
            _writer.writeAttributeString("numFmtId", String.valueOf(format.ifmt));
            _writer.writeAttributeString("formatCode", format.formatString);
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        /**
        * Font Mapping
        */
        //<fonts count="1">
        //<font>
        //<sz val="10"/>
        //<name val="Arial"/>
        //</font>
        //</fonts>
        _writer.writeStartElement("fonts");
        _writer.writeAttributeString("count", String.valueOf(sd.getFontDataList().size()));
        for (FontData font : sd.getFontDataList())
        {
            /**
            * 
            */
            StyleMappingHelper.addFontElement(_writer,font,FontElementType.NormalStyle);
        }
        // write fonts end element
        _writer.writeEndElement();
        /**
        * Fill Mapping
        */
        //<fills count="2">
        //<fill>
        //<patternFill patternType="none"/>
        //</fill>
        _writer.writeStartElement("fills");
        _writer.writeAttributeString("count", String.valueOf(sd.getFillDataList().size()));
        for (FillData fd : sd.getFillDataList())
        {
            _writer.writeStartElement("fill");
            _writer.writeStartElement("patternFill");
            _writer.writeAttributeString("patternType", StyleMappingHelper.getStringFromFillPatern(fd.getFillpatern()));
            // foreground color
            writeRgbForegroundColor(_writer,StyleMappingHelper.convertColorIdToRGB(fd.getIcvFore()));
            // background color
            writeRgbBackgroundColor(_writer,StyleMappingHelper.convertColorIdToRGB(fd.getIcvBack()));
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        /**
        * Border Mapping
        */
        //<borders count="1">
        //  <border>
        //      <left/>
        //      <right/>
        //      <top/>
        //      <bottom/>
        //      <diagonal/>
        //  </border>
        //</borders>
        _writer.writeStartElement("borders");
        _writer.writeAttributeString("count", String.valueOf(sd.getBorderDataList().size()));
        for (BorderData borderData : sd.getBorderDataList())
        {
            _writer.writeStartElement("border");
            // write diagonal settings
            if (borderData.diagonalValue == 1)
            {
                _writer.writeAttributeString("diagonalDown", "1");
            }
            else if (borderData.diagonalValue == 2)
            {
                _writer.writeAttributeString("diagonalUp", "1");
            }
            else if (borderData.diagonalValue == 3)
            {
                _writer.writeAttributeString("diagonalDown", "1");
                _writer.writeAttributeString("diagonalUp", "1");
            }
            else
            {
            }   
            // do nothing !
            String borderStyle = "";
            // left border
            _writer.writeStartElement("left");
            borderStyle = StyleMappingHelper.convertBorderStyle(borderData.left.style);
            if (!borderStyle.equals("none"))
            {
                _writer.writeAttributeString("style", borderStyle);
                writeRgbColor(_writer,StyleMappingHelper.convertColorIdToRGB(borderData.left.colorId));
            }
             
            _writer.writeEndElement();
            // right border
            _writer.writeStartElement("right");
            borderStyle = StyleMappingHelper.convertBorderStyle(borderData.right.style);
            if (!borderStyle.equals("none"))
            {
                _writer.writeAttributeString("style", borderStyle);
                writeRgbColor(_writer,StyleMappingHelper.convertColorIdToRGB(borderData.right.colorId));
            }
             
            _writer.writeEndElement();
            // top border
            _writer.writeStartElement("top");
            borderStyle = StyleMappingHelper.convertBorderStyle(borderData.top.style);
            if (!borderStyle.equals("none"))
            {
                _writer.writeAttributeString("style", borderStyle);
                writeRgbColor(_writer,StyleMappingHelper.convertColorIdToRGB(borderData.top.colorId));
            }
             
            _writer.writeEndElement();
            // bottom border
            _writer.writeStartElement("bottom");
            borderStyle = StyleMappingHelper.convertBorderStyle(borderData.bottom.style);
            if (!borderStyle.equals("none"))
            {
                _writer.writeAttributeString("style", borderStyle);
                writeRgbColor(_writer,StyleMappingHelper.convertColorIdToRGB(borderData.bottom.colorId));
            }
             
            _writer.writeEndElement();
            // diagonal border
            _writer.writeStartElement("diagonal");
            borderStyle = StyleMappingHelper.convertBorderStyle(borderData.diagonal.style);
            if (!borderStyle.equals("none"))
            {
                _writer.writeAttributeString("style", borderStyle);
                writeRgbColor(_writer,StyleMappingHelper.convertColorIdToRGB(borderData.diagonal.colorId));
            }
             
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        // end border
        _writer.writeEndElement();
        // end borders
        /**
        * 
        */
        // xfcellstyle mapping
        _writer.writeStartElement("cellStyleXfs");
        _writer.writeAttributeString("count", String.valueOf(sd.getXFCellStyleDataList().size()));
        for (XFData xfcellstyle : sd.getXFCellStyleDataList())
        {
            _writer.writeStartElement("xf");
            _writer.writeAttributeString("numFmtId", String.valueOf(xfcellstyle.ifmt));
            _writer.writeAttributeString("fontId", String.valueOf(xfcellstyle.fontId));
            _writer.writeAttributeString("fillId", String.valueOf(xfcellstyle.fillId));
            _writer.writeAttributeString("borderId", String.valueOf(xfcellstyle.borderId));
            if (xfcellstyle.hasAlignment)
            {
                StylesMapping.writeCellAlignment(_writer,xfcellstyle);
            }
             
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        ///<cellXfs count="6">
        ///<xf numFmtId="0" fontId="0" fillId="0" borderId="0" xfId="0"/>
        // xfcell mapping
        _writer.writeStartElement("cellXfs");
        _writer.writeAttributeString("count", String.valueOf(sd.getXFCellDataList().size()));
        for (XFData xfcell : sd.getXFCellDataList())
        {
            _writer.writeStartElement("xf");
            _writer.writeAttributeString("numFmtId", String.valueOf(xfcell.ifmt));
            _writer.writeAttributeString("fontId", String.valueOf(xfcell.fontId));
            _writer.writeAttributeString("fillId", String.valueOf(xfcell.fillId));
            _writer.writeAttributeString("borderId", String.valueOf(xfcell.borderId));
            _writer.writeAttributeString("xfId", String.valueOf(xfcell.ixfParent));
            // applyNumberFormat="1"
            if (xfcell.ifmt != 0)
            {
                _writer.writeAttributeString("applyNumberFormat", "1");
            }
             
            // applyBorder="1"
            if (xfcell.borderId != 0)
            {
                _writer.writeAttributeString("applyBorder", "1");
            }
             
            // applyFill="1"
            if (xfcell.fillId != 0)
            {
                _writer.writeAttributeString("applyFill", "1");
            }
             
            // applyFont="1"
            if (xfcell.fontId != 0)
            {
                _writer.writeAttributeString("applyFont", "1");
            }
             
            if (xfcell.hasAlignment)
            {
                StylesMapping.writeCellAlignment(_writer,xfcell);
            }
             
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        /**
        * write cell styles
        */
        _writer.writeStartElement("cellStyles");
        for (Style style : sd.getStyleList())
        {
            //_writer.WriteAttributeString("count", sd.StyleList.Count.ToString());
            _writer.writeStartElement("cellStyle");
            if (style.rgch != null)
            {
                _writer.writeAttributeString("name", style.rgch);
            }
             
            // theres a bug with the zero based reading from the referenz id
            // so the style.ixfe value is reduzed by one
            if (style.ixfe != 0)
            {
                _writer.writeAttributeString("xfId", (style.ixfe - 1).toString());
            }
            else
            {
                _writer.writeAttributeString("xfId", (style.ixfe).toString());
            } 
            _writer.writeAttributeString("builtinId", String.valueOf(style.istyBuiltIn));
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        // close tags
        // write color table !!
        if (sd.getColorDataList() != null && sd.getColorDataList().size() > 0)
        {
            _writer.writeStartElement("colors");
            _writer.writeStartElement("indexedColors");
            for (RGBColor item : sd.getColorDataList())
            {
                // <rgbColor rgb="00000000"/>
                _writer.writeStartElement("rgbColor");
                _writer.writeAttributeString("rgb", String.format(StringSupport.CSFmtStrToJFmtStr("{0:x2}"),item.Alpha).toString() + item.SixDigitHexCode);
                _writer.writeEndElement();
            }
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
         
        // end color
        _writer.writeEndElement();
        // close
        _writer.WriteEndDocument();
        // close writer
        _writer.Flush();
    }

    /**
    * @param writer 
    *  @param color
    */
    public static void writeRgbColor(XmlWriter writer, String color) throws Exception {
        if (!StringSupport.isNullOrEmpty(color) && !StringSupport.equals(color, "Auto"))
        {
            writer.writeStartElement("color");
            writer.writeAttributeString("rgb", "FF" + color);
            writer.writeEndElement();
        }
         
    }

    // <color indexed="63"/>
    public static void writeRgbForegroundColor(XmlWriter writer, String color) throws Exception {
        if (!StringSupport.isNullOrEmpty(color) && !StringSupport.equals(color, "Auto"))
        {
            writer.writeStartElement("fgColor");
            writer.writeAttributeString("rgb", "FF" + color);
            writer.writeEndElement();
        }
         
    }

    public static void writeRgbBackgroundColor(XmlWriter writer, String color) throws Exception {
        if (!StringSupport.isNullOrEmpty(color) && !StringSupport.equals(color, "Auto"))
        {
            writer.writeStartElement("bgColor");
            writer.writeAttributeString("rgb", "FF" + color);
            writer.writeEndElement();
        }
         
    }

    public static void writeCellAlignment(XmlWriter _writer, XFData xfcell) throws Exception {
        _writer.writeStartElement("alignment");
        if (xfcell.wrapText)
        {
            _writer.writeAttributeString("wrapText", "1");
        }
         
        if (xfcell.horizontalAlignment != 0xFF)
        {
            _writer.writeAttributeString("horizontal", StyleMappingHelper.getHorAlignmentValue(xfcell.horizontalAlignment));
        }
         
        if (xfcell.verticalAlignment != 0x02)
        {
            _writer.writeAttributeString("vertical", StyleMappingHelper.getVerAlignmentValue(xfcell.verticalAlignment));
        }
         
        if (xfcell.justifyLastLine)
        {
            _writer.writeAttributeString("justifyLastLine", "1");
        }
         
        if (xfcell.shrinkToFit)
        {
            _writer.writeAttributeString("shrinkToFit", "1");
        }
         
        if (xfcell.textRotation != 0x00)
        {
            _writer.writeAttributeString("textRotation", String.valueOf(xfcell.textRotation));
        }
         
        if (xfcell.indent != 0x00)
        {
            _writer.writeAttributeString("indent", String.valueOf(xfcell.indent));
        }
         
        if (xfcell.readingOrder != 0x00)
        {
            _writer.writeAttributeString("readingOrder", String.valueOf(xfcell.readingOrder));
        }
         
        _writer.writeEndElement();
    }

}


