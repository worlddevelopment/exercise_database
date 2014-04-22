//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:18 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Xml.XmlAttribute;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterRange;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SectionPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.FooterPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.HeaderPart;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.FooterMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.HeaderMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.NumberingMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;

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
public class SectionPropertiesMapping  extends PropertiesMapping implements IMapping<SectionPropertyExceptions>
{
    private XmlElement _sectPr;
    private int _sectNr;
    private ConversionContext _ctx;
    private SectionType _type = SectionType.nextPage;
    private enum SectionType
    {
        continuous,
        nextColumn,
        nextPage,
        evenPage,
        oddPage
    }
    private enum PageOrientation
    {
        __dummyEnum__0,
        portrait,
        landscape
    }
    private enum DocGridType
    {
        Default,
        lines,
        linesAndChars,
        snapToChars
    }
    private enum FootnoteRestartCode
    {
        continuous,
        eachSect,
        eachPage
    }
    private enum PageNumberFormatCode
    {
        Decimal,
        upperRoman,
        lowerRoman,
        upperLetter,
        lowerLetter,
        ordinal,
        cardinalText,
        ordinalText,
        hex,
        chicago,
        ideographDigital,
        japaneseCounting,
        Aiueo,
        Iroha,
        decimalFullWidth,
        decimalHalfWidth,
        japaneseLegal,
        japaneseDigitalTenThousand,
        decimalEnclosedCircle,
        decimalFullWidth2,
        aiueoFullWidth,
        irohaFullWidth,
        decimalZero,
        bullet,
        ganada,
        chosung,
        decimalEnclosedFullstop,
        decimalEnclosedParen,
        decimalEnclosedCircleChinese,
        ideographEnclosedCircle,
        ideographTraditional,
        ideographZodiac,
        ideographZodiacTraditional,
        taiwaneseCounting,
        ideographLegalTraditional,
        taiwaneseCountingThousand,
        taiwaneseDigital,
        chineseCounting,
        chineseLegalSimplified,
        chineseCountingThousand,
        Decimal2,
        koreanDigital
    }
    private int _colNumber;
    private short[] _colSpace;
    private short[] _colWidth;
    private short _pgWidth, _marLeft, _marRight;
    /**
    * Creates a new SectionPropertiesMapping which writes the
    * properties to the given writer
    * 
    *  @param writer The XmlWriter
    */
    public SectionPropertiesMapping(XmlWriter writer, ConversionContext ctx, int sectionNr) throws Exception {
        super(writer);
        _ctx = ctx;
        _sectPr = _nodeFactory.createElement("w", "sectPr", OpenXmlNamespaces.WordprocessingML);
        _sectNr = sectionNr;
    }

    /**
    * Creates a new SectionPropertiesMapping which appends
    * the properties to a given node.
    * 
    *  @param sectPr The sectPr node
    */
    public SectionPropertiesMapping(XmlElement sectPr, ConversionContext ctx, int sectionNr) throws Exception {
        super(null);
        _ctx = ctx;
        _nodeFactory = sectPr.getOwnerDocument();
        _sectPr = sectPr;
        _sectNr = sectionNr;
    }

    /**
    * Converts the given SectionPropertyExceptions
    * 
    *  @param sepx
    */
    public void apply(SectionPropertyExceptions sepx) throws Exception {
        XmlElement pgMar = _nodeFactory.createElement("w", "pgMar", OpenXmlNamespaces.WordprocessingML);
        XmlElement pgSz = _nodeFactory.createElement("w", "pgSz", OpenXmlNamespaces.WordprocessingML);
        XmlElement docGrid = _nodeFactory.createElement("w", "docGrid", OpenXmlNamespaces.WordprocessingML);
        XmlElement cols = _nodeFactory.createElement("w", "cols", OpenXmlNamespaces.WordprocessingML);
        XmlElement pgBorders = _nodeFactory.createElement("w", "pgBorders", OpenXmlNamespaces.WordprocessingML);
        XmlElement paperSrc = _nodeFactory.createElement("w", "paperSrc", OpenXmlNamespaces.WordprocessingML);
        XmlElement footnotePr = _nodeFactory.createElement("w", "footnotePr", OpenXmlNamespaces.WordprocessingML);
        XmlElement pgNumType = _nodeFactory.createElement("w", "pgNumType", OpenXmlNamespaces.WordprocessingML);
        //convert headers of this section
        if (_ctx.getDoc().HeaderAndFooterTable.OddHeaders.size() > 0)
        {
            CharacterRange evenHdr = _ctx.getDoc().HeaderAndFooterTable.EvenHeaders.get(_sectNr);
            if (evenHdr != null)
            {
                HeaderPart evenPart = _ctx.getDocx().getMainDocumentPart().addHeaderPart();
                _ctx.getDoc().Convert(new HeaderMapping(_ctx,evenPart,evenHdr));
                appendRef(_sectPr, "headerReference", "even", evenPart.RelIdToString);
            }
             
            CharacterRange oddHdr = _ctx.getDoc().HeaderAndFooterTable.OddHeaders.get(_sectNr);
            if (oddHdr != null)
            {
                HeaderPart oddPart = _ctx.getDocx().getMainDocumentPart().addHeaderPart();
                _ctx.getDoc().Convert(new HeaderMapping(_ctx,oddPart,oddHdr));
                appendRef(_sectPr, "headerReference", "default", oddPart.RelIdToString);
            }
             
            CharacterRange firstHdr = _ctx.getDoc().HeaderAndFooterTable.FirstHeaders.get(_sectNr);
            if (firstHdr != null)
            {
                HeaderPart firstPart = _ctx.getDocx().getMainDocumentPart().addHeaderPart();
                _ctx.getDoc().Convert(new HeaderMapping(_ctx,firstPart,firstHdr));
                appendRef(_sectPr, "headerReference", "first", firstPart.RelIdToString);
            }
             
        }
         
        //convert footers of this section
        if (_ctx.getDoc().HeaderAndFooterTable.OddFooters.size() > 0)
        {
            CharacterRange evenFtr = _ctx.getDoc().HeaderAndFooterTable.EvenFooters.get(_sectNr);
            if (evenFtr != null)
            {
                FooterPart evenPart = _ctx.getDocx().getMainDocumentPart().addFooterPart();
                _ctx.getDoc().Convert(new FooterMapping(_ctx,evenPart,evenFtr));
                appendRef(_sectPr,"footerReference","even",evenPart.getRelIdToString());
            }
             
            CharacterRange oddFtr = _ctx.getDoc().HeaderAndFooterTable.OddFooters.get(_sectNr);
            if (oddFtr != null)
            {
                FooterPart oddPart = _ctx.getDocx().getMainDocumentPart().addFooterPart();
                _ctx.getDoc().Convert(new FooterMapping(_ctx,oddPart,oddFtr));
                appendRef(_sectPr,"footerReference","default",oddPart.getRelIdToString());
            }
             
            CharacterRange firstFtr = _ctx.getDoc().HeaderAndFooterTable.FirstFooters.get(_sectNr);
            if (firstFtr != null)
            {
                FooterPart firstPart = _ctx.getDocx().getMainDocumentPart().addFooterPart();
                _ctx.getDoc().Convert(new FooterMapping(_ctx,firstPart,firstFtr));
                appendRef(_sectPr,"footerReference","first",firstPart.getRelIdToString());
            }
             
        }
         
        for (SinglePropertyModifier sprm : sepx.grpprl)
        {
            switch(sprm.OpCode)
            {
                case sprmSDxaLeft: 
                    //page margins
                    //left margin
                    _marLeft = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(pgMar,"left",String.valueOf(_marLeft));
                    break;
                case sprmSDxaRight: 
                    //right margin
                    _marRight = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(pgMar,"right",String.valueOf(_marRight));
                    break;
                case sprmSDyaTop: 
                    //top margin
                    appendValueAttribute(pgMar,"top",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSDyaBottom: 
                    //bottom margin
                    appendValueAttribute(pgMar,"bottom",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSDzaGutter: 
                    //gutter margin
                    appendValueAttribute(pgMar,"gutter",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSDyaHdrTop: 
                    //header margin
                    appendValueAttribute(pgMar,"header",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSDyaHdrBottom: 
                    //footer margin
                    appendValueAttribute(pgMar,"footer",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSXaPage: 
                    //page size and orientation
                    //width
                    _pgWidth = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(pgSz,"w",String.valueOf(_pgWidth));
                    break;
                case sprmSYaPage: 
                    //height
                    appendValueAttribute(pgSz,"h",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSBOrientation: 
                    //orientation
                    appendValueAttribute(pgSz,"orient",((PageOrientation)sprm.Arguments[0]).toString());
                    break;
                case sprmSDmBinFirst: 
                    //paper source
                    appendValueAttribute(paperSrc,"first",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSDmBinOther: 
                    appendValueAttribute(paperSrc,"other",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSBrcTop80: 
                case sprmSBrcTop: 
                    //page borders
                    //top
                    XmlElement topBorder = _nodeFactory.createElement("w", "top", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),topBorder);
                    addOrSetBorder(pgBorders,topBorder);
                    break;
                case sprmSBrcLeft80: 
                case sprmSBrcLeft: 
                    //left
                    XmlElement leftBorder = _nodeFactory.createElement("w", "left", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),leftBorder);
                    addOrSetBorder(pgBorders,leftBorder);
                    break;
                case sprmSBrcBottom80: 
                case sprmSBrcBottom: 
                    //left
                    XmlElement bottomBorder = _nodeFactory.createElement("w", "bottom", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),bottomBorder);
                    addOrSetBorder(pgBorders,bottomBorder);
                    break;
                case sprmSBrcRight80: 
                case sprmSBrcRight: 
                    //left
                    XmlElement rightBorder = _nodeFactory.createElement("w", "right", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),rightBorder);
                    addOrSetBorder(pgBorders,rightBorder);
                    break;
                case sprmSRncFtn: 
                    //footnote porperties
                    //restart code
                    FootnoteRestartCode fncFtn = FootnoteRestartCode.continuous;
                    //open office uses 1 byte values instead of 2 bytes values:
                    if (sprm.Arguments.length == 2)
                    {
                        fncFtn = (FootnoteRestartCode)System.BitConverter.ToInt16(sprm.Arguments, 0);
                    }
                     
                    if (sprm.Arguments.length == 1)
                    {
                        fncFtn = (FootnoteRestartCode)sprm.Arguments[0];
                    }
                     
                    appendValueElement(footnotePr,"numRestart",fncFtn.toString(),true);
                    break;
                case sprmSFpc: 
                    //position code
                    short fpc = 0;
                    if (sprm.Arguments.length == 2)
                        fpc = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    else
                        fpc = (short)sprm.Arguments[0]; 
                    if (fpc == 2)
                        appendValueElement(footnotePr,"pos","beneathText",true);
                     
                    break;
                case sprmSNfcFtnRef: 
                    //number format
                    short nfc = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueElement(footnotePr, "numFmt", NumberingMapping.GetNumberFormat(nfc), true);
                    break;
                case sprmSNFtn: 
                    short nFtn = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueElement(footnotePr,"numStart",String.valueOf(nFtn),true);
                    break;
                case sprmSDyaLinePitch: 
                    //doc grid
                    appendValueAttribute(docGrid,"linePitch",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSDxtCharSpace: 
                    appendValueAttribute(docGrid,"charSpace",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSClm: 
                    appendValueAttribute(docGrid,"type",((DocGridType)System.BitConverter.ToInt16(sprm.Arguments, 0)).toString());
                    break;
                case sprmSCcolumns: 
                    //columns
                    _colNumber = (int)(System.BitConverter.ToInt16(sprm.Arguments, 0) + 1);
                    _colSpace = new short[_colNumber];
                    appendValueAttribute(cols,"num",String.valueOf(_colNumber));
                    break;
                case sprmSDxaColumns: 
                    //evenly spaced columns
                    appendValueAttribute(cols,"space",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmSDxaColWidth: 
                    //there is at least one width set, so create the array
                    if (_colWidth == null)
                        _colWidth = new short[_colNumber];
                     
                    byte index = sprm.Arguments[0];
                    short w = System.BitConverter.ToInt16(sprm.Arguments, 1);
                    _colWidth[index] = w;
                    break;
                case sprmSDxaColSpacing: 
                    //there is at least one space set, so create the array
                    if (_colSpace == null)
                        _colSpace = new short[_colNumber];
                     
                    _colSpace[sprm.Arguments[0]] = System.BitConverter.ToInt16(sprm.Arguments, 1);
                    break;
                case sprmSFBiDi: 
                    //bidi
                    appendFlagElement(_sectPr,sprm,"bidi",true);
                    break;
                case sprmSFTitlePage: 
                    //title page
                    appendFlagElement(_sectPr,sprm,"titlePg",true);
                    break;
                case sprmSFRTLGutter: 
                    //RTL gutter
                    appendFlagElement(_sectPr,sprm,"rtlGutter",true);
                    break;
                case sprmSBkc: 
                    //type
                    _type = (SectionType)sprm.Arguments[0];
                    break;
                case sprmSVjc: 
                    //align
                    appendValueElement(_sectPr,"vAlign",String.valueOf(sprm.Arguments[0]),true);
                    break;
                case sprmSNfcPgn: 
                    //pgNumType
                    PageNumberFormatCode pgnFc = (PageNumberFormatCode)sprm.Arguments[0];
                    appendValueAttribute(pgNumType,"fmt",pgnFc.toString());
                    break;
                case sprmSPgnStart: 
                    appendValueAttribute(pgNumType,"start",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
            
            }
        }
        //build the columns
        if (_colWidth != null)
        {
            //set to unequal width
            XmlAttribute equalWidth = _nodeFactory.createAttribute("w","equalWidth",OpenXmlNamespaces.WordprocessingML);
            equalWidth.setValue("0");
            cols.getAttributes().add(equalWidth);
            //calculate the width of the last column:
            //the last column width is not written to the document because it can be calculated.
            if (_colWidth[_colWidth.length - 1] == 0)
            {
                short lastColWidth = (short)(_pgWidth - _marLeft - _marRight);
                for (int i = 0;i < _colWidth.length - 1;i++)
                {
                    lastColWidth -= _colSpace[i];
                    lastColWidth -= _colWidth[i];
                }
                _colWidth[_colWidth.length - 1] = lastColWidth;
            }
             
            for (int i = 0;i < _colWidth.length;i++)
            {
                //append the xml elements
                XmlElement col = _nodeFactory.createElement("w", "col", OpenXmlNamespaces.WordprocessingML);
                XmlAttribute w = _nodeFactory.createAttribute("w","w",OpenXmlNamespaces.WordprocessingML);
                XmlAttribute space = _nodeFactory.createAttribute("w","space",OpenXmlNamespaces.WordprocessingML);
                w.setValue(String.valueOf(_colWidth[i]));
                space.setValue(String.valueOf(_colSpace[i]));
                col.getAttributes().add(w);
                col.getAttributes().add(space);
                cols.appendChild(col);
            }
        }
         
        //append the section type
        appendValueElement(_sectPr,"type",_type.toString(),true);
        //append footnote properties
        if (footnotePr.getChildNodes().size() > 0)
        {
            _sectPr.appendChild(footnotePr);
        }
         
        //append page size
        if (pgSz.getAttributes().size() > 0)
        {
            _sectPr.appendChild(pgSz);
        }
         
        //append borders
        if (pgBorders.getChildNodes().size() > 0)
        {
            _sectPr.appendChild(pgBorders);
        }
         
        //append margin
        if (pgMar.getAttributes().size() > 0)
        {
            _sectPr.appendChild(pgMar);
        }
         
        //append paper info
        if (paperSrc.getAttributes().size() > 0)
        {
            _sectPr.appendChild(paperSrc);
        }
         
        //append columns
        if (cols.getAttributes().size() > 0 || cols.getChildNodes().size() > 0)
        {
            _sectPr.appendChild(cols);
        }
         
        //append doc grid
        if (docGrid.getAttributes().size() > 0)
        {
            _sectPr.appendChild(docGrid);
        }
         
        //numType
        if (pgNumType.getAttributes().size() > 0)
        {
            _sectPr.appendChild(pgNumType);
        }
         
        if (_writer != null)
        {
            //write the properties
            _sectPr.WriteTo(_writer);
        }
         
    }

    private void appendRef(XmlElement parent, String element, String refType, String refId) throws Exception {
        XmlElement headerRef = _nodeFactory.createElement("w", element, OpenXmlNamespaces.WordprocessingML);
        XmlAttribute headerRefType = _nodeFactory.createAttribute("w","type",OpenXmlNamespaces.WordprocessingML);
        headerRefType.setValue(refType);
        headerRef.getAttributes().add(headerRefType);
        XmlAttribute headerRefId = _nodeFactory.createAttribute("r","id",OpenXmlNamespaces.Relationships);
        headerRefId.setValue(refId);
        headerRef.getAttributes().add(headerRefId);
        parent.appendChild(headerRef);
    }

}


