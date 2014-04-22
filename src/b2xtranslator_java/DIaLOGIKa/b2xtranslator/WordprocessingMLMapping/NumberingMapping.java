//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:15 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListData;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListFormatOverride;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListLevel;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.CharacterPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ParagraphPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.StyleSheetMapping;

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
public class NumberingMapping  extends AbstractOpenXmlMapping implements IMapping<ListTable>
{
    private ConversionContext _ctx;
    private WordDocument _parentDoc;
    private enum LevelJustification
    {
        left,
        center,
        right
    }
    public NumberingMapping(ConversionContext ctx, WordDocument parentDoc) throws Exception {
        super(XmlWriter.Create(ctx.getDocx().getMainDocumentPart().getNumberingDefinitionsPart().GetStream(), ctx.getWriterSettings()));
        _ctx = ctx;
        _parentDoc = parentDoc;
    }

    public void apply(ListTable rglst) throws Exception {
        _writer.WriteStartElement("w", "numbering", OpenXmlNamespaces.WordprocessingML);
        for (int i = 0;i < rglst.size();i++)
        {
            ListData lstf = rglst.get(i);
            //start abstractNum
            _writer.WriteStartElement("w", "abstractNum", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "abstractNumId", OpenXmlNamespaces.WordprocessingML, String.valueOf(i));
            //nsid
            _writer.WriteStartElement("w", "nsid", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:X8}"),lstf.lsid));
            _writer.writeEndElement();
            //multiLevelType
            _writer.WriteStartElement("w", "multiLevelType", OpenXmlNamespaces.WordprocessingML);
            if (lstf.fHybrid)
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "hybridMultilevel");
            else if (lstf.fSimpleList)
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "singleLevel");
            else
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "multilevel");  
            _writer.writeEndElement();
            //template
            _writer.WriteStartElement("w", "tmpl", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:X8}"),lstf.tplc));
            _writer.writeEndElement();
            for (int j = 0;j < lstf.rglvl.length;j++)
            {
                //writes the levels
                ListLevel lvl = lstf.rglvl[j];
                _writer.WriteStartElement("w", "lvl", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "ilvl", OpenXmlNamespaces.WordprocessingML, String.valueOf(j));
                //starts at
                _writer.WriteStartElement("w", "start", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.valueOf(lvl.iStartAt));
                _writer.writeEndElement();
                //number format
                _writer.WriteStartElement("w", "numFmt", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, GetNumberFormat(lvl.nfc));
                _writer.writeEndElement();
                //suffix
                _writer.WriteStartElement("w", "suff", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, lvl.ixchFollow.toString());
                _writer.writeEndElement();
                //style
                //The style id is used for a reverse reference.
                //It can happen that the reference points to the wrong style.
                short styleIndex = lstf.rgistd[j];
                if (styleIndex != ListData.ISTD_NIL)
                {
                    _writer.WriteStartElement("w", "pStyle", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, StyleSheetMapping.MakeStyleId(_ctx.getDoc().Styles.Styles[styleIndex]));
                    _writer.writeEndElement();
                }
                 
                //Number level text
                _writer.WriteStartElement("w", "lvlText", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, getLvlText(lvl.xst));
                _writer.writeEndElement();
                //jc
                _writer.WriteStartElement("w", "lvlJc", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ((LevelJustification)lvl.jc).toString());
                _writer.writeEndElement();
                //pPr
                lvl.grpprlPapx.Convert(new ParagraphPropertiesMapping(_writer,_ctx,_parentDoc,null));
                //rPr
                lvl.grpprlChpx.Convert(new CharacterPropertiesMapping(_writer,_parentDoc,new RevisionData(lvl.grpprlChpx),lvl.grpprlPapx,false));
                _writer.writeEndElement();
            }
            //end abstractNum
            _writer.writeEndElement();
        }
        for (int i = 0;i < _ctx.getDoc().ListFormatOverrideTable.size();i++)
        {
            //write the overrides
            ListFormatOverride lfo = _ctx.getDoc().ListFormatOverrideTable.get(i);
            //start num
            _writer.WriteStartElement("w", "num", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "numId", OpenXmlNamespaces.WordprocessingML, String.valueOf((i + 1)));
            int index = findIndexbyId(rglst,lfo.lsid);
            _writer.WriteStartElement("w", "abstractNumId", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.valueOf(index));
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        _writer.Flush();
    }

    public static int findIndexbyId(CSList<ListData> list, int id) throws Exception {
        int ret = -1;
        for (int i = 0;i < list.size();i++)
        {
            if (list.get(i).lsid == id)
            {
                ret = i;
                break;
            }
             
        }
        return ret;
    }

    /**
    * Converts the number text of the binary format to the number text of OOXML.
    * OOXML uses different placeholders for the numbers.
    * 
    *  @param numberText The number text of the binary format
    *  @return
    */
    private String getLvlText(String numberText) throws Exception {
        String ret = numberText;
        ret = ret.replace(StringSupport.mkString((char)0x0000, 1), "%1");
        ret = ret.replace(StringSupport.mkString((char)0x0001, 1), "%2");
        ret = ret.replace(StringSupport.mkString((char)0x0002, 1), "%3");
        ret = ret.replace(StringSupport.mkString((char)0x0003, 1), "%4");
        ret = ret.replace(StringSupport.mkString((char)0x0004, 1), "%5");
        ret = ret.replace(StringSupport.mkString((char)0x0005, 1), "%6");
        ret = ret.replace(StringSupport.mkString((char)0x0006, 1), "%7");
        ret = ret.replace(StringSupport.mkString((char)0x0007, 1), "%8");
        ret = ret.replace(StringSupport.mkString((char)0x0008, 1), "%9");
        return ret;
    }

    /**
    * Converts the number format code of the binary format.
    * 
    *  @param nfc The number format code
    *  @return The OOXML attribute value
    */
    public static String getNumberFormat(int nfc) throws Exception {
        switch(nfc)
        {
            case 0: 
                return "decimal";
            case 1: 
                return "upperRoman";
            case 2: 
                return "lowerRoman";
            case 3: 
                return "upperLetter";
            case 4: 
                return "lowerLetter";
            case 5: 
                return "ordinal";
            case 6: 
                return "cardinalText";
            case 7: 
                return "ordinalText";
            case 8: 
                return "hex";
            case 9: 
                return "chicago";
            case 10: 
                return "ideographDigital";
            case 11: 
                return "japaneseCounting";
            case 12: 
                return "aiueo";
            case 13: 
                return "iroha";
            case 14: 
                return "decimalFullWidth";
            case 15: 
                return "decimalHalfWidth";
            case 16: 
                return "japaneseLegal";
            case 17: 
                return "japaneseDigitalTenThousand";
            case 18: 
                return "decimalEnclosedCircle";
            case 19: 
                return "decimalFullWidth2";
            case 20: 
                return "aiueoFullWidth";
            case 21: 
                return "irohaFullWidth";
            case 22: 
                return "decimalZero";
            case 23: 
                return "bullet";
            case 24: 
                return "ganada";
            case 25: 
                return "chosung";
            case 26: 
                return "decimalEnclosedFullstop";
            case 27: 
                return "decimalEnclosedParen";
            case 28: 
                return "decimalEnclosedCircleChinese";
            case 29: 
                return "ideographEnclosedCircle";
            case 30: 
                return "ideographTraditional";
            case 31: 
                return "ideographZodiac";
            case 32: 
                return "ideographZodiacTraditional";
            case 33: 
                return "taiwaneseCounting";
            case 34: 
                return "ideographLegalTraditional";
            case 35: 
                return "taiwaneseCountingThousand";
            case 36: 
                return "taiwaneseDigital";
            case 37: 
                return "chineseCounting";
            case 38: 
                return "chineseLegalSimplified";
            case 39: 
                return "chineseCountingThousand";
            case 40: 
                return "koreanDigital";
            case 41: 
                return "koreanCounting";
            case 42: 
                return "koreanLegal";
            case 43: 
                return "koreanDigital2";
            case 44: 
                return "vietnameseCounting";
            case 45: 
                return "russianLower";
            case 46: 
                return "russianUpper";
            case 47: 
                return "none";
            case 48: 
                return "numberInDash";
            case 49: 
                return "hebrew1";
            case 50: 
                return "hebrew2";
            case 51: 
                return "arabicAlpha";
            case 52: 
                return "arabicAbjad";
            case 53: 
                return "hindiVowels";
            case 54: 
                return "hindiConsonants";
            case 55: 
                return "hindiNumbers";
            case 56: 
                return "hindiCounting";
            case 57: 
                return "thaiLetters";
            case 58: 
                return "thaiNumbers";
            case 59: 
                return "thaiCounting";
            default: 
                return "decimal";
        
        }
    }

}


