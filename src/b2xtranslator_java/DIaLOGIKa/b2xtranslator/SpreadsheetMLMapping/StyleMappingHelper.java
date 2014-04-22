//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:31 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FontData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FontElementType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.StyleEnum;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.SuperSubScriptStyle;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.UnderlineStyle;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.StyleMappingHelper;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.StylesMapping;
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
public class StyleMappingHelper   
{
    /**
    * This is the FillPatern mapping, it is used to convert the binary fillpatern to the open xml string
    * 
    *  @param fp 
    *  @return
    */
    public static String getStringFromFillPatern(StyleEnum fp) throws Exception {
        switch(fp)
        {
            case FLSNULL: 
                return "none";
            case FLSSOLID: 
                return "solid";
            case FLSMEDGRAY: 
                return "mediumGray";
            case FLSDKGRAY: 
                return "darkGray";
            case FLSLTGRAY: 
                return "lightGray";
            case FLSDKHOR: 
                return "darkHorizontal";
            case FLSDKVER: 
                return "darkVertical";
            case FLSDKDOWN: 
                return "darkDown";
            case FLSDKUP: 
                return "darkUp";
            case FLSDKGRID: 
                return "darkGrid";
            case FLSDKTRELLIS: 
                return "darkTrellis";
            case FLSLTHOR: 
                return "lightHorizontal";
            case FLSLTVER: 
                return "lightVertical";
            case FLSLTDOWN: 
                return "lightDown";
            case FLSLTUP: 
                return "lightUp";
            case FLSLTGRID: 
                return "lightGrid";
            case FLSLTTRELLIS: 
                return "lightTrellis";
            case FLSGRAY125: 
                return "gray125";
            case FLSGRAY0625: 
                return "gray0625";
            default: 
                return "none";
        
        }
    }

    /**
    * Method converts a colorID to a RGB color value
    * TODO: This conversion works currently only if there is no Palette record.
    * 
    *  @param colorID 
    *  @return
    */
    public static String convertColorIdToRGB(int colorID) throws Exception {
        switch(colorID)
        {
            case 0x0000: 
                return "000000";
            case 0x0001: 
                return "FFFFFF";
            case 0x0002: 
                return "FF0000";
            case 0x0003: 
                return "00FF00";
            case 0x0004: 
                return "0000FF";
            case 0x0005: 
                return "FFFF00";
            case 0x0006: 
                return "FF00FF";
            case 0x0007: 
                return "00FFFF";
            case 0x0008: 
                return "000000";
            case 0x0009: 
                return "FFFFFF";
            case 0x000A: 
                return "FF0000";
            case 0x000B: 
                return "00FF00";
            case 0x000C: 
                return "0000FF";
            case 0x000D: 
                return "FFFF00";
            case 0x000E: 
                return "FF00FF";
            case 0x000F: 
                return "00FFFF";
            case 0x0010: 
                return "800000";
            case 0x0011: 
                return "008000";
            case 0x0012: 
                return "000080";
            case 0x0013: 
                return "808000";
            case 0x0014: 
                return "800080";
            case 0x0015: 
                return "008080";
            case 0x0016: 
                return "C0C0C0";
            case 0x0017: 
                return "808080";
            case 0x0018: 
                return "9999FF";
            case 0x0019: 
                return "993366";
            case 0x001A: 
                return "FFFFCC";
            case 0x001B: 
                return "CCFFFF";
            case 0x001C: 
                return "660066";
            case 0x001D: 
                return "FF8080";
            case 0x001E: 
                return "0066CC";
            case 0x001F: 
                return "CCCCFF";
            case 0x0020: 
                return "000080";
            case 0x0021: 
                return "FF00FF";
            case 0x0022: 
                return "FFFF00";
            case 0x0023: 
                return "00FFFF";
            case 0x0024: 
                return "800080";
            case 0x0025: 
                return "800000";
            case 0x0026: 
                return "008080";
            case 0x0027: 
                return "0000FF";
            case 0x0028: 
                return "00CCFF";
            case 0x0029: 
                return "CCFFFF";
            case 0x002A: 
                return "CCFFCC";
            case 0x002B: 
                return "FFFF99";
            case 0x002C: 
                return "99CCFF";
            case 0x002D: 
                return "FF99CC";
            case 0x002E: 
                return "CC99FF";
            case 0x002F: 
                return "FFCC99";
            case 0x0030: 
                return "3366FF";
            case 0x0031: 
                return "33CCCC";
            case 0x0032: 
                return "99CC00";
            case 0x0033: 
                return "FFCC00";
            case 0x0034: 
                return "FF9900";
            case 0x0035: 
                return "FF6600";
            case 0x0036: 
                return "666699";
            case 0x0037: 
                return "969696";
            case 0x0038: 
                return "003366";
            case 0x0039: 
                return "339966";
            case 0x003A: 
                return "003300";
            case 0x003B: 
                return "333300";
            case 0x003C: 
                return "993300";
            case 0x003D: 
                return "993366";
            case 0x003E: 
                return "333399";
            case 0x003F: 
                return "333333";
            case 0x0040: 
                return "";
            case 0x0041: 
                return "";
            case 0x004D: 
                return "";
            case 0x004E: 
                return "";
            case 0x004F: 
                return "";
            case 0x0051: 
                return "";
            case 0x7FFF: 
                return "Auto";
            default: 
                return "";
        
        }
    }

    // Black
    // White
    // Red
    // Green
    // Blue
    // Yellow
    // Magenta
    // Cyan
    public static String convertBorderStyle(ushort style) throws Exception {
        System.UInt16 __dummyScrutVar2 = style;
        if (__dummyScrutVar2.equals(0x0000))
        {
            return "none";
        }
        else if (__dummyScrutVar2.equals(0x0001))
        {
            return "thin";
        }
        else if (__dummyScrutVar2.equals(0x0002))
        {
            return "medium";
        }
        else if (__dummyScrutVar2.equals(0x0003))
        {
            return "dashed";
        }
        else if (__dummyScrutVar2.equals(0x0004))
        {
            return "dotted";
        }
        else if (__dummyScrutVar2.equals(0x0005))
        {
            return "thick";
        }
        else if (__dummyScrutVar2.equals(0x0006))
        {
            return "double";
        }
        else if (__dummyScrutVar2.equals(0x0007))
        {
            return "hair";
        }
        else if (__dummyScrutVar2.equals(0x0008))
        {
            return "mediumDashed";
        }
        else if (__dummyScrutVar2.equals(0x0009))
        {
            return "dashDot";
        }
        else if (__dummyScrutVar2.equals(0x000A))
        {
            return "mediumDashDot";
        }
        else if (__dummyScrutVar2.equals(0x000B))
        {
            return "dashDotDot";
        }
        else if (__dummyScrutVar2.equals(0x000C))
        {
            return "mediumDashDotDot";
        }
        else if (__dummyScrutVar2.equals(0x000D))
        {
            return "slantDashDot";
        }
        else
        {
            return "none";
        }              
    }

    public static void addFontElement(XmlWriter _writer, FontData font, FontElementType type) throws Exception {
        // begin font element
        if (type == FontElementType.NormalStyle)
        {
            _writer.writeStartElement("font");
        }
        else if (type == FontElementType.String)
        {
            _writer.writeStartElement("rPr");
        }
          
        // font size
        // NOTE: Excel 97, Excel 2000, Excel 2002, Office Excel 2003 and Office Excel 2007 can
        //   save out 0 for certain fonts. This is not valid in ECMA 376
        //
        if (font.size.toPoints() != 0)
        {
            _writer.writeStartElement("sz");
            _writer.WriteAttributeString("val", Convert.ToString(font.size.toPoints(), Locale.GetCultureInfo("en-US")));
            _writer.writeEndElement();
        }
         
        // font name
        if (type == FontElementType.NormalStyle)
            _writer.writeStartElement("name");
        else if (type == FontElementType.String)
            _writer.writeStartElement("rFont");
          
        _writer.writeAttributeString("val", font.fontName);
        _writer.writeEndElement();
        // font family
        if (font.fontFamily != 0)
        {
            _writer.writeStartElement("family");
            _writer.writeAttributeString("val", String.valueOf((((Enum)font.fontFamily).ordinal())));
            _writer.writeEndElement();
        }
         
        // font charset
        if (font.charSet != 0)
        {
            _writer.writeStartElement("charset");
            _writer.writeAttributeString("val", String.valueOf(font.charSet));
            _writer.writeEndElement();
        }
         
        // bool values
        if (font.isBold)
            _writer.WriteElementString("b", "");
         
        if (font.isItalic)
            _writer.WriteElementString("i", "");
         
        if (font.isOutline)
            _writer.WriteElementString("outline", "");
         
        if (font.isShadow)
            _writer.WriteElementString("shadow", "");
         
        if (font.isStrike)
            _writer.WriteElementString("strike", "");
         
        // underline style mapping
        if (font.uStyle != UnderlineStyle.none)
        {
            _writer.writeStartElement("u");
            if (font.uStyle == UnderlineStyle.singleLine)
            {
                _writer.writeAttributeString("val", "single");
            }
            else if (font.uStyle == UnderlineStyle.doubleLine)
            {
                _writer.writeAttributeString("val", "double");
            }
            else
            {
                _writer.writeAttributeString("val", font.uStyle.toString());
            }  
            _writer.writeEndElement();
        }
         
        if (font.vertAlign != SuperSubScriptStyle.none)
        {
            _writer.writeStartElement("vertAlign");
            _writer.writeAttributeString("val", font.vertAlign.toString());
            _writer.writeEndElement();
        }
         
        // colormapping
        StylesMapping.writeRgbColor(_writer,StyleMappingHelper.convertColorIdToRGB(font.color));
        // end font element
        _writer.writeEndElement();
    }

    /**
    * converts the horizontal alignment value to the string required by open xml
    * 
    *  @param horValue 
    *  @return
    */
    public static String getHorAlignmentValue(int horValue) throws Exception {
        switch(horValue)
        {
            case 0x02: 
                return "center";
            case 0x06: 
                return "centerContinuous";
            case 0x07: 
                return "distributed";
            case 0x04: 
                return "fill";
            case 0x00: 
                return "general";
            case 0x05: 
                return "justify";
            case 0x01: 
                return "left";
            case 0x03: 
                return "right";
            default: 
                return "";
        
        }
    }

    /**
    * converts the vertical alignment value to the string required by open xml
    * 
    *  @param verValue 
    *  @return
    */
    public static String getVerAlignmentValue(int verValue) throws Exception {
        switch(verValue)
        {
            case 0x00: 
                return "top";
            case 0x01: 
                return "center";
            case 0x02: 
                return "bottom";
            case 0x03: 
                return "justify";
            case 0x04: 
                return "distributed";
            default: 
                return "";
        
        }
    }

}


