//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:54 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Font;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Font.FontWeight;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Format;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Style;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.XF;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.BorderData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.BorderPartData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FillData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FontData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.FormatData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.StyleData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.StyleEnum;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.SuperSubScriptStyle;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.UnderlineStyle;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.XFData;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import DIaLOGIKa.b2xtranslator.Tools.TwipsValue;

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
public class StyleData   implements IVisitable
{
    protected CSList<FormatData> formatDataList;
    public CSList<FormatData> getFormatDataList() throws Exception {
        return formatDataList;
    }

    protected CSList<XFData> xfCellDataList;
    public CSList<XFData> getXFCellDataList() throws Exception {
        return xfCellDataList;
    }

    protected CSList<XFData> xfCellStyleDataList;
    public CSList<XFData> getXFCellStyleDataList() throws Exception {
        return xfCellStyleDataList;
    }

    protected CSList<Style> styleList;
    public CSList<Style> getStyleList() throws Exception {
        return styleList;
    }

    private CSList<FillData> fillDataList;
    public CSList<FillData> getFillDataList() throws Exception {
        return fillDataList;
    }

    public void setFillDataList(CSList<FillData> value) throws Exception {
        fillDataList = value;
    }

    private CSList<FontData> fontDataList;
    public CSList<FontData> getFontDataList() throws Exception {
        return fontDataList;
    }

    private CSList<BorderData> borderDataList;
    public CSList<BorderData> getBorderDataList() throws Exception {
        return this.borderDataList;
    }

    private CSList<RGBColor> colorDataList;
    public CSList<RGBColor> getColorDataList() throws Exception {
        return this.colorDataList;
    }

    /**
    * This class stores every format from a document
    */
    public StyleData() throws Exception {
        this.formatDataList = new CSList<FormatData>();
        this.xfCellDataList = new CSList<XFData>();
        this.xfCellStyleDataList = new CSList<XFData>();
        this.styleList = new CSList<Style>();
        this.fillDataList = new CSList<FillData>();
        this.fontDataList = new CSList<FontData>();
        this.borderDataList = new CSList<BorderData>();
        this.colorDataList = new CSList<RGBColor>();
        // fill fillList with none and grey value
        FillData none = new FillData(StyleEnum.FLSNULL,0x0040,0x0040);
        this.fillDataList.add(none);
        FillData grey = new FillData(StyleEnum.FLSGRAY125,0x0040,0x0040);
        this.fillDataList.add(grey);
    }

    /**
    * Add the format biff record data to the style data model
    * 
    *  @param formatbiffrec
    */
    public void addFormatValue(Format formatbiffrec) throws Exception {
        FormatData fd = new FormatData(formatbiffrec.ifmt, formatbiffrec.rgb);
        this.formatDataList.add(fd);
    }

    /**
    * Add a xf biff record to the internal data list
    * 
    *  @param xf
    */
    public void addXFDataValue(XF xf) throws Exception {
        XFData xfdata = new XFData();
        xfdata.fStyle = xf.fStyle;
        xfdata.ifmt = xf.ifmt;
        xfdata.ixfParent = xf.ixfParent;
        if (xf.fWrap != 0)
        {
            xfdata.wrapText = true;
            xfdata.hasAlignment = true;
        }
         
        if (xf.alc != 0xFF)
        {
            xfdata.hasAlignment = true;
            xfdata.horizontalAlignment = xf.alc;
        }
         
        if (xf.alcV != 0x02)
        {
            xfdata.hasAlignment = true;
        }
         
        xfdata.verticalAlignment = xf.alcV;
        if (xf.fJustLast != 0)
        {
            xfdata.hasAlignment = true;
            xfdata.justifyLastLine = true;
        }
         
        if (xf.fShrinkToFit != 0)
        {
            xfdata.hasAlignment = true;
            xfdata.shrinkToFit = true;
        }
         
        if (xf.trot != 0)
        {
            xfdata.hasAlignment = true;
            xfdata.textRotation = xf.trot;
        }
         
        if (xf.cIndent != 0)
        {
            xfdata.hasAlignment = true;
            xfdata.indent = xf.cIndent;
        }
         
        if (xf.iReadOrder != 0)
        {
            xfdata.hasAlignment = true;
            xfdata.readingOrder = xf.iReadOrder;
        }
         
        // the first three fontids are zero based
        // beginning with four the fontids are one based
        if (xf.ifnt > 4)
        {
            xfdata.fontId = xf.ifnt - 1;
        }
        else
        {
            xfdata.fontId = xf.ifnt;
        } 
        if (xf.fStyle == 1)
        {
            this.xfCellStyleDataList.add(xfdata);
        }
        else
        {
            this.xfCellDataList.add(xfdata);
        } 
        int countxf = this.getXFCellDataList().size() + this.xfCellStyleDataList.size();
        FillData fd = new FillData(StyleEnum.values()[xf.fls],xf.icvFore,xf.icvBack);
        int fillDataId = this.addFillDataValue(fd);
        TraceLogger.debugInternal(fd.toString() + " -- Number XF " + String.valueOf(countxf) + " -- Number FillData: " + this.fillDataList.size());
        xfdata.fillId = fillDataId;
        // add border data
        BorderData borderData = new BorderData();
        // diagonal value
        borderData.diagonalValue = (ushort)xf.grbitDiag;
        // create and add borderparts
        BorderPartData top = new BorderPartData((ushort)xf.dgTop,xf.icvTop);
        borderData.top = top;
        BorderPartData bottom = new BorderPartData((ushort)xf.dgBottom,xf.icvBottom);
        borderData.bottom = bottom;
        BorderPartData left = new BorderPartData((ushort)xf.dgLeft,xf.icvLeft);
        borderData.left = left;
        BorderPartData right = new BorderPartData((ushort)xf.dgRight,xf.icvRight);
        borderData.right = right;
        BorderPartData diagonal = new BorderPartData((ushort)xf.dgDiag,xf.icvDiag);
        borderData.diagonal = diagonal;
        int borderId = this.addBorderDataValue(borderData);
        xfdata.borderId = borderId;
    }

    /**
    * Add the style biff record data to the style data model
    * 
    *  @param formatbiffrec
    */
    public void addStyleValue(Style stylebiff) throws Exception {
        this.styleList.add(stylebiff);
    }

    /**
    * Adds the fill data object to the internal list if it doesn't already exists
    * 
    * 
    *  @param fd Fill data object
    *  @return The zero based ID from the FillDataList Object
    */
    public int addFillDataValue(FillData fd) throws Exception {
        int listId = this.getFillDataList().IndexOf(fd);
        if (listId < 0)
        {
            this.fillDataList.add(fd);
            return this.fillDataList.size() - 1;
        }
        else
        {
            return listId;
        } 
    }

    /**
    * @param bd 
    *  @return
    */
    public int addBorderDataValue(BorderData bd) throws Exception {
        int listId = this.borderDataList.IndexOf(bd);
        if (listId < 0)
        {
            this.borderDataList.add(bd);
            return this.borderDataList.size() - 1;
        }
        else
        {
            return listId;
        } 
    }

    public void addFontData(Font font) throws Exception {
        FontData fontdata = new FontData();
        // fill the objectdatafields
        fontdata.fontName = font.fontName.getValue();
        // size in twips
        fontdata.size = new TwipsValue(font.dyHeight);
        fontdata.fontFamily = font.bFamily;
        fontdata.charSet = font.bCharSet;
        // boolean values
        fontdata.isItalic = font.fItalic;
        fontdata.isOutline = font.fOutline;
        fontdata.isShadow = font.fShadow;
        fontdata.isStrike = font.fStrikeOut;
        fontdata.isBold = font.bls == FontWeight.Bold;
        // TODO avoid cast
        fontdata.uStyle = (UnderlineStyle)font.uls;
        fontdata.vertAlign = (SuperSubScriptStyle)font.sss;
        fontdata.color = font.icv;
        // add the value to the list
        this.fontDataList.add(fontdata);
    }

    public void setColorList(CSList<RGBColor> colorList) throws Exception {
        this.colorDataList = colorList;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<StyleData>)mapping).apply(this);
    }

}


