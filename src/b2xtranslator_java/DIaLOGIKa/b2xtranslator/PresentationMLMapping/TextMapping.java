//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:19 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.NumberSupport;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientTextbox;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.TextBooleanProperties;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.DateTimeMCAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.FontCollection;
import DIaLOGIKa.b2xtranslator.PptFileFormat.FontEntityAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.FooterMCAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.GenericDateMCAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Handout;
import DIaLOGIKa.b2xtranslator.PptFileFormat.HeaderMCAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.MasterTextPropAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.MasterTextPropRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Note;
import DIaLOGIKa.b2xtranslator.PptFileFormat.OutlineTextRefAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphMask;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideListWithText;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideNumberMCAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextBytesAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextCharsAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextHeaderAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextMasterStyleAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextRulerAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextSpecialInfoAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextStyleAtom;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.CharacterRunPropsMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ShapeTreeMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
public class TextMapping  extends AbstractOpenXmlMapping implements IMapping<ClientTextbox>
{
    protected ConversionContext _ctx;
    private String lang = "en-US";
    private ShapeTreeMapping parentShapeTreeMapping = null;
    public TextMapping(ConversionContext ctx, XmlWriter writer) throws Exception {
        super(writer);
        _ctx = ctx;
    }

    /**
    * Returns the ParagraphRun of the given style that is active at the given index.
    * 
    *  @param style style to use
    *  @param forIdx index to use
    *  @return ParagraphRun or null in case no run was found
    */
    protected static ParagraphRun getParagraphRun(TextStyleAtom style, uint forIdx) throws Exception {
        if (style == null)
            return null;
         
        uint idx = 0;
        for (ParagraphRun p : style.PRuns)
        {
            if (forIdx < idx + p.Length)
                return p;
             
            idx += p.Length;
        }
        return null;
    }

    /**
    * Returns the MasterTextParagraphRun of the given style that is active at the given index.
    * 
    *  @param style style to use
    *  @param forIdx index to use
    *  @return ParagraphRun or null in case no run was found
    */
    protected static MasterTextPropRun getMasterTextPropRun(MasterTextPropAtom style, uint forIdx) throws Exception {
        if (style == null)
            return new MasterTextPropRun();
         
        uint idx = 0;
        for (MasterTextPropRun p : style.MasterTextPropRuns)
        {
            if (forIdx < idx + p.count)
                return p;
             
            idx += p.count;
        }
        return new MasterTextPropRun();
    }

    /**
    * Returns the CharacterRun of the given style that is active at the given index.
    * 
    *  @param style style to use
    *  @param forIdx index to use
    *  @return CharacterRun or null in case no run was found
    */
    protected static CharacterRun getCharacterRun(TextStyleAtom style, uint forIdx) throws Exception {
        if (style == null)
            return null;
         
        uint idx = 0;
        for (CharacterRun c : style.CRuns)
        {
            if (forIdx < idx + c.Length)
                return c;
             
            idx += c.Length;
        }
        return null;
    }

    protected static uint getCharacterRunStart(TextStyleAtom style, uint forIdx) throws Exception {
        if (style == null)
            return 0;
         
        uint idx = 0;
        for (CharacterRun c : style.CRuns)
        {
            if (forIdx < idx + c.Length)
                return idx;
             
            idx += c.Length;
        }
        return 0;
    }

    public void apply(ClientTextbox textbox) throws Exception {
        apply(null,textbox,"","","");
    }

    public void apply(ShapeTreeMapping pparentShapeTreeMapping, ClientTextbox textbox, String footertext, String headertext, String datetext) throws Exception {
        parentShapeTreeMapping = pparentShapeTreeMapping;
        System.IO.MemoryStream ms = new System.IO.MemoryStream(textbox.Bytes);
        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
        TextHeaderAtom thAtom = null;
        TextStyleAtom style = null;
        FooterMCAtom mca = null;
        TextRulerAtom ruler = null;
        MasterTextPropAtom masterTextProp = null;
        String text = "";
        String origText = "";
        ShapeOptions so = textbox.firstAncestorWithType().<ShapeOptions>FirstChildWithType();
        TextMasterStyleAtom defaultStyle = null;
        int lvl = 0;
        long __dummyScrutVar0 = rec.TypeCode;
        if (__dummyScrutVar0.equals(3999))
        {
            thAtom = (TextHeaderAtom)rec;
            while (ms.Position < ms.Length)
            {
                rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                long __dummyScrutVar1 = rec.TypeCode;
                if (__dummyScrutVar1.equals(0xfa0))
                {
                    //TextCharsAtom
                    text = ((TextCharsAtom)rec).Text;
                    origText = text;
                    thAtom.TextAtom = (TextAtom)rec;
                }
                else if (__dummyScrutVar1.equals(0xfa1))
                {
                    //TextRunStyleAtom
                    style = (TextStyleAtom)rec;
                    style.setTextHeaderAtom(thAtom);
                }
                else if (__dummyScrutVar1.equals(0xfa6))
                {
                    //TextRulerAtom
                    ruler = (TextRulerAtom)rec;
                }
                else if (__dummyScrutVar1.equals(0xfa8))
                {
                    //TextBytesAtom
                    text = ((TextBytesAtom)rec).Text;
                    origText = text;
                    thAtom.TextAtom = (TextAtom)rec;
                }
                else if (__dummyScrutVar1.equals(0xfaa))
                {
                    //TextSpecialInfoAtom
                    TextSpecialInfoAtom sia = (TextSpecialInfoAtom)rec;
                    if (sia.Runs.size() > 0)
                    {
                        if (sia.Runs.get(0).si.lang)
                        {
                            UInt16 __dummyScrutVar2 = sia.Runs.get(0).si.lid;
                            if (__dummyScrutVar2.equals(0x0))
                            {
                            }
                            else // no language
                            if (__dummyScrutVar2.equals(0x13))
                            {
                            }
                            else //Any Dutch language is preferred over non-Dutch languages when proofing the text
                            if (__dummyScrutVar2.equals(0x400))
                            {
                            }
                            else
                            {
                                try
                                {
                                    //no proofing
                                    lang = Locale.GetCultureInfo(sia.Runs.get(0).si.lid).IetfLanguageTag;
                                }
                                catch (Exception __dummyCatchVar0)
                                {
                                }
                            
                            }   
                        }
                         
                    }
                     
                }
                else //ignore
                if (__dummyScrutVar1.equals(0xfa2))
                {
                    //MasterTextPropAtom
                    masterTextProp = (MasterTextPropAtom)rec;
                }
                else if (__dummyScrutVar1.equals(0xfd8))
                {
                    //SlideNumberMCAtom
                    SlideNumberMCAtom snmca = (SlideNumberMCAtom)rec;
                    _writer.WriteStartElement("a", "p", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "fld", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("id", "{18109A10-03E4-4BE3-B6BB-0FCEF851AF87}");
                    _writer.writeAttributeString("type", "slidenum");
                    _writer.WriteElementString("a", "t", OpenXmlNamespaces.DrawingML, "<#>");
                    _writer.writeEndElement();
                    //fld
                    _writer.WriteStartElement("a", "endParaRPr", OpenXmlNamespaces.DrawingML);
                    _writer.writeEndElement();
                    //endParaRPr
                    _writer.writeEndElement();
                    //p
                    text = text.replace(origText.substring(snmca.Position, (snmca.Position) + (1)), "");
                }
                else if (__dummyScrutVar1.equals(0xff7))
                {
                    //DateTimeMCAtom
                    DateTimeMCAtom d = (DateTimeMCAtom)rec;
                    String date = (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a")).format(Calendar.getInstance().getTime());
                    //_writer.WriteStartElement("a", "p", OpenXmlNamespaces.DrawingML);
                    ParagraphRun p = GetParagraphRun(style, 0);
                    MasterTextPropRun tp = GetMasterTextPropRun(masterTextProp, 0);
                    writeP(p,tp,so,ruler,defaultStyle);
                    _writer.WriteStartElement("a", "fld", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("id", "{1023E2E8-AA53-4FEA-8F5C-1FABD68F61AB}");
                    _writer.writeAttributeString("type", "datetime1");
                    CharacterRun r = GetCharacterRun(style, 0);
                    if (r != null)
                    {
                        String dummy = "";
                        String dummy2 = "";
                        String dummy3 = "";
                        RegularContainer slide = textbox.firstAncestorWithType();
                        if (slide == null)
                            slide = textbox.firstAncestorWithType();
                         
                        if (slide == null)
                            slide = textbox.firstAncestorWithType();
                         
                        RefSupport<String> refVar___0 = new RefSupport<String>(dummy);
                        RefSupport<String> refVar___1 = new RefSupport<String>(dummy2);
                        RefSupport<String> refVar___2 = new RefSupport<String>(dummy3);
                        new CharacterRunPropsMapping(_ctx,_writer).apply(r,"rPr",slide,refVar___0,refVar___1,refVar___2,lang,defaultStyle,lvl);
                        dummy = refVar___0.getValue();
                        dummy2 = refVar___1.getValue();
                        dummy3 = refVar___2.getValue();
                    }
                     
                    _writer.WriteElementString("a", "t", OpenXmlNamespaces.DrawingML, date);
                    _writer.writeEndElement();
                    //fld
                    _writer.WriteStartElement("a", "endParaRPr", OpenXmlNamespaces.DrawingML);
                    _writer.writeEndElement();
                    //endParaRPr
                    _writer.writeEndElement();
                    //p
                    text = text.replace(origText.substring(d.Position, (d.Position) + (1)), "");
                    for (CharacterRun run : style.CRuns)
                    {
                        run.Length += (uint)text.length();
                    }
                }
                else if (__dummyScrutVar1.equals(0xff9))
                {
                    //HeaderMCAtom
                    HeaderMCAtom hmca = (HeaderMCAtom)rec;
                    text = text.replace(origText.substring(hmca.Position, (hmca.Position) + (1)), headertext);
                    for (CharacterRun run : style.CRuns)
                    {
                        run.Length += (uint)text.length();
                    }
                }
                else if (__dummyScrutVar1.equals(0xffa))
                {
                    //FooterMCAtom
                    mca = (FooterMCAtom)rec;
                    text = text.replace(origText.substring(mca.Position, (mca.Position) + (1)), footertext);
                    for (CharacterRun run : style.CRuns)
                    {
                        run.Length += (uint)text.length();
                    }
                }
                else if (__dummyScrutVar1.equals(0xff8))
                {
                    //GenericDateMCAtom
                    GenericDateMCAtom gdmca = (GenericDateMCAtom)rec;
                    text = text.replace(origText.substring(gdmca.Position, (gdmca.Position) + (1)), datetext);
                    for (CharacterRun run : style.CRuns)
                    {
                        run.Length += (uint)text.length();
                    }
                }
                else
                {
                }           
            }
        }
        else //TextAtom textAtom = thAtom.TextAtom;
        //text = (textAtom == null) ? "" : textAtom.Text;
        //style = thAtom.TextStyleAtom;
        if (__dummyScrutVar0.equals(3998))
        {
            OutlineTextRefAtom otrAtom = (OutlineTextRefAtom)rec;
            SlideListWithText slideListWithText = _ctx.getPpt().DocumentRecord.RegularSlideListWithText;
            CSList<TextHeaderAtom> thAtoms = slideListWithText.SlideToPlaceholderTextHeaders.get(textbox.firstAncestorWithType().PersistAtom);
            thAtom = thAtoms.get(otrAtom.Index);
            if (thAtom.TextAtom != null)
                text = thAtom.TextAtom.Text;
             
            if (thAtom.TextStyleAtom != null)
                style = thAtom.TextStyleAtom;
             
            while (ms.Position < ms.Length)
            {
                rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                long __dummyScrutVar3 = rec.TypeCode;
                if (__dummyScrutVar3.equals(0xfa6))
                {
                    //TextRulerAtom
                    ruler = (TextRulerAtom)rec;
                }
                else
                {
                } 
            }
        }
        else
        {
            throw new NotSupportedException("Can't find text for ClientTextbox without TextHeaderAtom and OutlineTextRefAtom");
        }  
        uint idx = 0;
        Slide s = textbox.firstAncestorWithType();
        if (s != null)
        {
            try
            {
                SlideAtom a = s.firstChildWithType();
                if (DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(a.Flags, 0x01 << 1) && a.MasterId > 0)
                {
                    Slide m = _ctx.getPpt().findMasterRecordById(a.MasterId);
                    for (Object __dummyForeachVar8 : m.allChildrenWithType())
                    {
                        TextMasterStyleAtom at = (TextMasterStyleAtom)__dummyForeachVar8;
                        //if (at.Instance == 1 && thAtom.TextType == TextType.Other)
                        //{
                        //    defaultStyle = at;
                        //    break;
                        //}
                        if (at.Instance == ((Enum)thAtom.TextType).ordinal())
                        {
                            defaultStyle = at;
                        }
                         
                    }
                }
                 
            }
            catch (Exception __dummyCatchVar1)
            {
                throw __dummyCatchVar1;
            }
        
        }
         
        if (so.OptionsByID.containsKey(PropertyId.hspMaster))
        {
            uint MasterID = so.OptionsByID.get(PropertyId.hspMaster).op;
        }
         
        if (text.length() == 0)
        {
            _writer.WriteStartElement("a", "p", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "endParaRPr", OpenXmlNamespaces.DrawingML);
            // TODO...
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        else
        {
            String[] parlines = text.split(StringSupport.charAltsToRegex(new char[]{ '\r' }));
            //text.Split(new char[] { '\v', '\r' });
            int internalOffset = 0;
            for (String parline : parlines)
            {
                String[] runlines = parline.split(StringSupport.charAltsToRegex(new char[]{ '\v' }));
                //each parline forms a paragraph
                //each runline forms a run
                ParagraphRun p = getParagraphRun(style,idx);
                MasterTextPropRun tp = getMasterTextPropRun(masterTextProp,idx);
                if (p != null)
                    lvl = p.IndentLevel;
                 
                String runText;
                if (runlines.length > 0)
                    if (runlines[0].startsWith("\t"))
                    {
                    }
                     
                 
                writeP(p,tp,so,ruler,defaultStyle);
                uint CharacterRunStart;
                int len;
                boolean first = true;
                boolean textwritten = false;
                for (String line : runlines)
                {
                    uint offset = idx;
                    if (!first)
                    {
                        _writer.WriteStartElement("a", "br", OpenXmlNamespaces.DrawingML);
                        CharacterRun r = getCharacterRun(style,idx + (uint)internalOffset + 1);
                        if (r != null)
                        {
                            String dummy = "";
                            String dummy2 = "";
                            String dummy3 = "";
                            RegularContainer slide = textbox.firstAncestorWithType();
                            if (slide == null)
                                slide = textbox.firstAncestorWithType();
                             
                            if (slide == null)
                                slide = textbox.firstAncestorWithType();
                             
                            RefSupport<String> refVar___3 = new RefSupport<String>(dummy);
                            RefSupport<String> refVar___4 = new RefSupport<String>(dummy2);
                            RefSupport<String> refVar___5 = new RefSupport<String>(dummy3);
                            new CharacterRunPropsMapping(_ctx,_writer).apply(r,"rPr",slide,refVar___3,refVar___4,refVar___5,lang,defaultStyle,lvl);
                            dummy = refVar___3.getValue();
                            dummy2 = refVar___4.getValue();
                            dummy3 = refVar___5.getValue();
                        }
                         
                        _writer.writeEndElement();
                        if (line.length() == 0)
                        {
                            idx++;
                            internalOffset -= 1;
                        }
                         
                        internalOffset += 1;
                    }
                     
                    while (idx < offset + line.length())
                    {
                        textwritten = true;
                        len = line.length();
                        CharacterRun r = null;
                        if (idx + (uint)internalOffset == 0)
                        {
                            r = GetCharacterRun(style, 0);
                            CharacterRunStart = GetCharacterRunStart(style, 0);
                        }
                        else
                        {
                            r = getCharacterRun(style,idx + (uint)internalOffset);
                            CharacterRunStart = getCharacterRunStart(style,idx + (uint)internalOffset);
                        } 
                        if (r != null)
                        {
                            len = (int)(CharacterRunStart + r.Length - idx - internalOffset);
                            if (len > line.length() - idx + offset)
                                len = ((int)((line.length() - idx + offset)));
                             
                            if (len < 0)
                                len = ((int)((line.length() - idx + offset)));
                             
                            runText = line.substring((int)(idx - offset), ((int)(idx - offset)) + (len));
                        }
                        else
                        {
                            runText = line.substring((int)(idx - offset));
                        } 
                        //if (r != null)
                        //if ((idx - offset) + r.Length < line.Length)
                        //{
                        //    runText = line.Substring((int)(idx - offset), (int)r.Length);
                        //}
                        _writer.WriteStartElement("a", "r", OpenXmlNamespaces.DrawingML);
                        String dummy = "";
                        String dummy2 = "";
                        String dummy3 = "";
                        RegularContainer slide = textbox.firstAncestorWithType();
                        if (slide == null)
                            slide = textbox.firstAncestorWithType();
                         
                        if (slide == null)
                            slide = textbox.firstAncestorWithType();
                         
                        if (r != null || defaultStyle != null)
                        {
                            RefSupport<String> refVar___6 = new RefSupport<String>(dummy);
                            RefSupport<String> refVar___7 = new RefSupport<String>(dummy2);
                            RefSupport<String> refVar___8 = new RefSupport<String>(dummy3);
                            new CharacterRunPropsMapping(_ctx,_writer).apply(r,"rPr",slide,refVar___6,refVar___7,refVar___8,lang,defaultStyle,lvl);
                            dummy = refVar___6.getValue();
                            dummy2 = refVar___7.getValue();
                            dummy3 = refVar___8.getValue();
                        }
                        else
                        {
                            _writer.WriteStartElement("a", "rPr", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("lang", lang);
                            _writer.writeEndElement();
                        } 
                        _writer.WriteStartElement("a", "t", OpenXmlNamespaces.DrawingML);
                        _writer.WriteValue(runText.Replace(char.ToString((char)0x05), ""));
                        _writer.writeEndElement();
                        _writer.writeEndElement();
                        idx += (uint)runText.length();
                    }
                    // +1;
                    first = false;
                }
                if (!textwritten)
                {
                    CharacterRun r = getCharacterRun(style,idx + (uint)internalOffset);
                    if (r != null)
                    {
                        String dummy = "";
                        String dummy2 = "";
                        String dummy3 = "";
                        RegularContainer slide = textbox.firstAncestorWithType();
                        if (slide == null)
                            slide = textbox.firstAncestorWithType();
                         
                        if (slide == null)
                            slide = textbox.firstAncestorWithType();
                         
                        RefSupport<String> refVar___9 = new RefSupport<String>(dummy);
                        RefSupport<String> refVar___10 = new RefSupport<String>(dummy2);
                        RefSupport<String> refVar___11 = new RefSupport<String>(dummy3);
                        new CharacterRunPropsMapping(_ctx,_writer).apply(r,"endParaRPr",slide,refVar___9,refVar___10,refVar___11,lang,defaultStyle,lvl);
                        dummy = refVar___9.getValue();
                        dummy2 = refVar___10.getValue();
                        dummy3 = refVar___11.getValue();
                    }
                     
                }
                else
                {
                    _writer.WriteStartElement("a", "endParaRPr", OpenXmlNamespaces.DrawingML);
                    _writer.writeEndElement();
                } 
                _writer.writeEndElement();
                idx += 1;
            }
        } 
    }

    private void writeP(ParagraphRun p, MasterTextPropRun tp, ShapeOptions so, TextRulerAtom ruler, TextMasterStyleAtom defaultStyle) throws Exception {
        int writtenLeftMargin = -1;
        _writer.WriteStartElement("a", "p", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "pPr", OpenXmlNamespaces.DrawingML);
        if (p == null)
        {
            _writer.writeAttributeString("lvl", tp.indentLevel.toString());
            if (defaultStyle != null && defaultStyle.PRuns.size() > tp.indentLevel)
            {
                if (defaultStyle.PRuns[tp.indentLevel].LeftMarginPresent)
                {
                    _writer.writeAttributeString("marL", String.valueOf(Utils.masterCoordToEMU((int)defaultStyle.PRuns[tp.indentLevel].LeftMargin)));
                    writtenLeftMargin = (int)defaultStyle.PRuns[tp.indentLevel].LeftMargin;
                }
                 
                if (defaultStyle.PRuns[tp.indentLevel].IndentPresent)
                {
                    _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU((int)(defaultStyle.PRuns[tp.indentLevel].LeftMargin - defaultStyle.PRuns[tp.indentLevel].Indent))))));
                }
                 
                if (defaultStyle.PRuns[tp.indentLevel].AlignmentPresent)
                {
                    Alignment __dummyScrutVar4 = defaultStyle.PRuns[tp.indentLevel].Alignment;
                    if (__dummyScrutVar4.equals(0x0000))
                    {
                        //Left
                        _writer.writeAttributeString("algn", "l");
                    }
                    else if (__dummyScrutVar4.equals(0x0001))
                    {
                        //Center
                        _writer.writeAttributeString("algn", "ctr");
                    }
                    else if (__dummyScrutVar4.equals(0x0002))
                    {
                        //Right
                        _writer.writeAttributeString("algn", "r");
                    }
                    else if (__dummyScrutVar4.equals(0x0003))
                    {
                        //Justify
                        _writer.writeAttributeString("algn", "just");
                    }
                    else if (__dummyScrutVar4.equals(0x0004))
                    {
                        //Distributed
                        _writer.writeAttributeString("algn", "dist");
                    }
                    else if (__dummyScrutVar4.equals(0x0005))
                    {
                        //ThaiDistributed
                        _writer.writeAttributeString("algn", "thaiDist");
                    }
                    else if (__dummyScrutVar4.equals(0x0006))
                    {
                        //JustifyLow
                        _writer.writeAttributeString("algn", "justLow");
                    }
                           
                }
                 
                if (defaultStyle.PRuns[tp.indentLevel].BulletFlagsFieldPresent)
                {
                    if ((defaultStyle.PRuns[tp.indentLevel].BulletFlags & (ushort)ParagraphMask.HasBullet) == 0)
                    {
                        _writer.WriteElementString("a", "buNone", OpenXmlNamespaces.DrawingML, "");
                    }
                    else
                    {
                        //if (defaultStyle.PRuns[tp.indentLevel].BulletColorPresent)
                        //{
                        //    _writer.WriteStartElement("a", "buClr", OpenXmlNamespaces.DrawingML);
                        //    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                        //    string s = defaultStyle.PRuns[tp.indentLevel].BulletColor.Red.ToString("X").PadLeft(2, '0') + defaultStyle.PRuns[tp.indentLevel].BulletColor.Green.ToString("X").PadLeft(2, '0') + defaultStyle.PRuns[tp.indentLevel].BulletColor.Blue.ToString("X").PadLeft(2, '0');
                        //    _writer.WriteAttributeString("val", s);
                        //    _writer.WriteEndElement();
                        //    _writer.WriteEndElement(); //buClr
                        //}
                        if (defaultStyle.PRuns[tp.indentLevel].BulletSizePresent)
                        {
                            if (defaultStyle.PRuns[tp.indentLevel].BulletSize > 0)
                            {
                                _writer.WriteStartElement("a", "buSzPct", OpenXmlNamespaces.DrawingML);
                                _writer.writeAttributeString("val", (defaultStyle.PRuns[tp.indentLevel].BulletSize * 1000).toString());
                                _writer.writeEndElement();
                            }
                            else
                            {
                            } 
                        }
                         
                        //buSzPct
                        //TODO
                        if (defaultStyle.PRuns[tp.indentLevel].BulletFontPresent)
                        {
                            _writer.WriteStartElement("a", "buFont", OpenXmlNamespaces.DrawingML);
                            FontCollection fonts = _ctx.getPpt().DocumentRecord.firstChildWithType().<FontCollection>FirstChildWithType();
                            FontEntityAtom entity = fonts.entities.get((int)defaultStyle.PRuns[tp.indentLevel].BulletTypefaceIdx);
                            if (entity.TypeFace.indexOf('\0') > 0)
                            {
                                _writer.writeAttributeString("typeface", entity.TypeFace.substring(0, (0) + (entity.TypeFace.indexOf('\0'))));
                            }
                            else
                            {
                                _writer.writeAttributeString("typeface", entity.TypeFace);
                            } 
                            _writer.writeEndElement();
                        }
                         
                        //buChar
                        if (parentShapeTreeMapping != null && parentShapeTreeMapping.ShapeStyleTextProp9Atom != null && parentShapeTreeMapping.ShapeStyleTextProp9Atom.P9Runs.size() > p.IndentLevel && parentShapeTreeMapping.ShapeStyleTextProp9Atom.P9Runs[p.IndentLevel].fBulletHasAutoNumber == 1 && parentShapeTreeMapping.ShapeStyleTextProp9Atom.P9Runs[p.IndentLevel].bulletAutoNumberScheme == -1)
                        {
                            _writer.WriteStartElement("a", "buAutoNum", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("type", "arabicPeriod");
                            _writer.writeEndElement();
                        }
                        else if (defaultStyle.PRuns[tp.indentLevel].BulletCharPresent)
                        {
                            _writer.WriteStartElement("a", "buChar", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("char", defaultStyle.PRuns[tp.indentLevel].BulletChar.toString());
                            _writer.writeEndElement();
                        }
                        else //buChar
                        if (!defaultStyle.PRuns[tp.indentLevel].BulletCharPresent)
                        {
                            _writer.WriteStartElement("a", "buChar", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("char", "�");
                            _writer.writeEndElement();
                        }
                           
                    } 
                }
                 
            }
             
        }
        else
        {
            //buChar
            if (p.IndentLevel > 0)
                _writer.writeAttributeString("lvl", p.IndentLevel.toString());
             
            if (p.getLeftMarginPresent())
            {
                _writer.writeAttributeString("marL", String.valueOf(Utils.masterCoordToEMU((int)p.LeftMargin)));
                writtenLeftMargin = (int)p.LeftMargin;
            }
            else if (ruler != null && ruler.fLeftMargin1 && p.IndentLevel == 0)
            {
                _writer.writeAttributeString("marL", String.valueOf(Utils.masterCoordToEMU(ruler.leftMargin1)));
                writtenLeftMargin = ruler.leftMargin1;
                if (!(p.getIndentPresent() || (defaultStyle != null && defaultStyle.PRuns.size() > p.IndentLevel && defaultStyle.PRuns[p.IndentLevel].IndentPresent) || (ruler != null && ruler.fIndent1 && p.IndentLevel == 0)))
                {
                    _writer.writeAttributeString("indent", String.valueOf(Utils.masterCoordToEMU(-1 * ruler.leftMargin1)));
                }
                 
            }
            else if (ruler != null && ruler.fLeftMargin2 && p.IndentLevel == 1)
            {
                _writer.writeAttributeString("marL", String.valueOf(Utils.masterCoordToEMU(ruler.leftMargin2)));
                writtenLeftMargin = ruler.leftMargin2;
                if (!(p.getIndentPresent() || (defaultStyle != null && defaultStyle.PRuns.size() > p.IndentLevel && defaultStyle.PRuns[p.IndentLevel].IndentPresent) || (ruler != null && ruler.fIndent2 && p.IndentLevel == 1)))
                {
                    _writer.writeAttributeString("indent", String.valueOf(Utils.masterCoordToEMU(-1 * ruler.leftMargin1)));
                }
                 
            }
            else if (ruler != null && ruler.fLeftMargin3 && p.IndentLevel == 2)
            {
                _writer.writeAttributeString("marL", String.valueOf(Utils.masterCoordToEMU(ruler.leftMargin3)));
                writtenLeftMargin = ruler.leftMargin3;
                if (!(p.getIndentPresent() || (defaultStyle != null && defaultStyle.PRuns.size() > p.IndentLevel && defaultStyle.PRuns[p.IndentLevel].IndentPresent) || (ruler != null && ruler.fIndent3 && p.IndentLevel == 2)))
                {
                    _writer.writeAttributeString("indent", String.valueOf(Utils.masterCoordToEMU(-1 * ruler.leftMargin1)));
                }
                 
            }
            else if (ruler != null && ruler.fLeftMargin4 && p.IndentLevel == 3)
            {
                _writer.writeAttributeString("marL", String.valueOf(Utils.masterCoordToEMU(ruler.leftMargin4)));
                writtenLeftMargin = ruler.leftMargin4;
                if (!(p.getIndentPresent() || (defaultStyle != null && defaultStyle.PRuns.size() > p.IndentLevel && defaultStyle.PRuns[p.IndentLevel].IndentPresent) || (ruler != null && ruler.fIndent4 && p.IndentLevel == 3)))
                {
                    _writer.writeAttributeString("indent", String.valueOf(Utils.masterCoordToEMU(-1 * ruler.leftMargin1)));
                }
                 
            }
            else if (ruler != null && ruler.fLeftMargin5 && p.IndentLevel == 4)
            {
                _writer.writeAttributeString("marL", String.valueOf(Utils.masterCoordToEMU(ruler.leftMargin5)));
                writtenLeftMargin = ruler.leftMargin5;
                if (!(p.getIndentPresent() || (defaultStyle != null && defaultStyle.PRuns.size() > p.IndentLevel && defaultStyle.PRuns[p.IndentLevel].IndentPresent) || (ruler != null && ruler.fIndent5 && p.IndentLevel == 4)))
                {
                    _writer.writeAttributeString("indent", String.valueOf(Utils.masterCoordToEMU(-1 * ruler.leftMargin1)));
                }
                 
            }
            else if (so.OptionsByID.containsKey(PropertyId.dxTextLeft) && so.OptionsByID.containsKey(PropertyId.TextBooleanProperties))
            {
                TextBooleanProperties props = new TextBooleanProperties(so.OptionsByID.get(PropertyId.TextBooleanProperties).op);
                if (props.fUsefAutoTextMargin && (props.fAutoTextMargin == false))
                    if (so.OptionsByID.get(PropertyId.dxTextLeft).op > 0)
                        _writer.writeAttributeString("marL", String.valueOf(so.OptionsByID.get(PropertyId.dxTextLeft).op));
                     
                 
            }
                   
            //writtenLeftMargin = Utils.EMUToMasterCoord((int)so.OptionsByID[ShapeOptions.PropertyId.dxTextLeft].op);
            if (p.getIndentPresent())
            {
                _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU((int)(p.LeftMargin - p.Indent))))));
            }
            else if (ruler != null && ruler.fIndent1 && p.IndentLevel == 0)
            {
                _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU(((int)((ruler.leftMargin1 - ruler.indent1))))))));
            }
            else if (ruler != null && ruler.fIndent2 && p.IndentLevel == 1)
            {
                _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU(((int)((ruler.leftMargin2 - ruler.indent2))))))));
            }
            else if (ruler != null && ruler.fIndent3 && p.IndentLevel == 2)
            {
                _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU(((int)((ruler.leftMargin3 - ruler.indent3))))))));
            }
            else if (ruler != null && ruler.fIndent4 && p.IndentLevel == 3)
            {
                _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU(((int)((ruler.leftMargin4 - ruler.indent4))))))));
            }
            else if (ruler != null && ruler.fIndent5 && p.IndentLevel == 4)
            {
                _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU(((int)((ruler.leftMargin5 - ruler.indent5))))))));
            }
            else if (defaultStyle != null && defaultStyle.PRuns.size() > p.IndentLevel && defaultStyle.PRuns[p.IndentLevel].IndentPresent)
            {
                if (writtenLeftMargin == -1)
                {
                    writtenLeftMargin = (int)(defaultStyle.PRuns[p.IndentLevel].LeftMargin);
                }
                 
                _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU(((int)((writtenLeftMargin - defaultStyle.PRuns[p.IndentLevel].Indent))))))));
            }
                   
            if (p.getAlignmentPresent())
            {
                Int16? __dummyScrutVar5 = p.Alignment;
                if (__dummyScrutVar5.equals(0x0000))
                {
                    //Left
                    _writer.writeAttributeString("algn", "l");
                }
                else if (__dummyScrutVar5.equals(0x0001))
                {
                    //Center
                    _writer.writeAttributeString("algn", "ctr");
                }
                else if (__dummyScrutVar5.equals(0x0002))
                {
                    //Right
                    _writer.writeAttributeString("algn", "r");
                }
                else if (__dummyScrutVar5.equals(0x0003))
                {
                    //Justify
                    _writer.writeAttributeString("algn", "just");
                }
                else if (__dummyScrutVar5.equals(0x0004))
                {
                    //Distributed
                    _writer.writeAttributeString("algn", "dist");
                }
                else if (__dummyScrutVar5.equals(0x0005))
                {
                    //ThaiDistributed
                    _writer.writeAttributeString("algn", "thaiDist");
                }
                else if (__dummyScrutVar5.equals(0x0006))
                {
                    //JustifyLow
                    _writer.writeAttributeString("algn", "justLow");
                }
                       
            }
            else if (defaultStyle != null && defaultStyle.PRuns.size() > p.IndentLevel)
            {
                if (defaultStyle.PRuns[p.IndentLevel].AlignmentPresent)
                {
                    Alignment __dummyScrutVar6 = defaultStyle.PRuns[p.IndentLevel].Alignment;
                    if (__dummyScrutVar6.equals(0x0000))
                    {
                        //Left
                        _writer.writeAttributeString("algn", "l");
                    }
                    else if (__dummyScrutVar6.equals(0x0001))
                    {
                        //Center
                        _writer.writeAttributeString("algn", "ctr");
                    }
                    else if (__dummyScrutVar6.equals(0x0002))
                    {
                        //Right
                        _writer.writeAttributeString("algn", "r");
                    }
                    else if (__dummyScrutVar6.equals(0x0003))
                    {
                        //Justify
                        _writer.writeAttributeString("algn", "just");
                    }
                    else if (__dummyScrutVar6.equals(0x0004))
                    {
                        //Distributed
                        _writer.writeAttributeString("algn", "dist");
                    }
                    else if (__dummyScrutVar6.equals(0x0005))
                    {
                        //ThaiDistributed
                        _writer.writeAttributeString("algn", "thaiDist");
                    }
                    else if (__dummyScrutVar6.equals(0x0006))
                    {
                        //JustifyLow
                        _writer.writeAttributeString("algn", "justLow");
                    }
                           
                }
                 
            }
              
            if (p.getDefaultTabSizePresent())
            {
                _writer.writeAttributeString("defTabSz", String.valueOf(Utils.masterCoordToEMU((int)p.DefaultTabSize)));
            }
            else if (defaultStyle != null && defaultStyle.PRuns.size() > p.IndentLevel)
            {
                if (defaultStyle.PRuns[p.IndentLevel].DefaultTabSizePresent)
                {
                    _writer.writeAttributeString("defTabSz", String.valueOf(Utils.masterCoordToEMU((int)defaultStyle.PRuns[p.IndentLevel].DefaultTabSize)));
                }
                 
            }
              
            if (p.getLineSpacingPresent())
            {
                _writer.WriteStartElement("a", "lnSpc", OpenXmlNamespaces.DrawingML);
                //_writer.WriteStartElement("a", "spcPct", OpenXmlNamespaces.DrawingML);
                //_writer.WriteAttributeString("val", (p.LineSpacing * 1000).ToString());
                //_writer.WriteEndElement(); //spcPct
                if (p.LineSpacing < 0)
                {
                    _writer.WriteStartElement("a", "spcPts", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf((-1 * p.LineSpacing * 12)));
                    //TODO: this has to be verified!
                    _writer.writeEndElement();
                }
                else
                {
                    //spcPct
                    _writer.WriteStartElement("a", "spcPct", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf((1000 * p.LineSpacing)));
                    _writer.writeEndElement();
                } 
                //spcPct
                _writer.writeEndElement();
            }
             
            //lnSpc
            if (p.getSpaceBeforePresent())
            {
                _writer.WriteStartElement("a", "spcBef", OpenXmlNamespaces.DrawingML);
                if (p.SpaceBefore < 0)
                {
                    _writer.WriteStartElement("a", "spcPts", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf((-1 * 12 * p.SpaceBefore)));
                    //TODO: the 12 is wrong: find correct value
                    _writer.writeEndElement();
                }
                else
                {
                    //spcPct
                    _writer.WriteStartElement("a", "spcPct", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf((1000 * p.SpaceBefore)));
                    _writer.writeEndElement();
                } 
                //spcPct
                _writer.writeEndElement();
            }
             
            //spcBef
            if (p.getSpaceAfterPresent())
            {
                _writer.WriteStartElement("a", "spcAft", OpenXmlNamespaces.DrawingML);
                if (p.SpaceAfter < 0)
                {
                    _writer.WriteStartElement("a", "spcPts", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf((-1 * 12 * p.SpaceAfter)));
                    //TODO: the 12 is wrong: find correct value
                    _writer.writeEndElement();
                }
                else
                {
                    //spcPct
                    _writer.WriteStartElement("a", "spcPct", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf((1000 * p.SpaceAfter)));
                    _writer.writeEndElement();
                } 
                //spcPct
                _writer.writeEndElement();
            }
             
            //spcAft
            if (p.getBulletFlagsFieldPresent())
            {
                if ((p.BulletFlags & (ushort)ParagraphMask.HasBullet) == 0)
                {
                    _writer.WriteElementString("a", "buNone", OpenXmlNamespaces.DrawingML, "");
                }
                else
                {
                    if (p.getBulletColorPresent())
                    {
                        _writer.WriteStartElement("a", "buClr", OpenXmlNamespaces.DrawingML);
                        String s = StringSupport.PadLeft(NumberSupport.format(p.BulletColor.Red, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(p.BulletColor.Green, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(p.BulletColor.Blue, "X"), 2, '0');
                        byte __dummyScrutVar7 = p.BulletColor.Index;
                        if (__dummyScrutVar7.equals(7))
                        {
                            _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("val", "folHlink");
                            _writer.writeEndElement();
                        }
                        else if (__dummyScrutVar7.equals(6))
                        {
                            _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("val", "hlink");
                            _writer.writeEndElement();
                        }
                        else if (__dummyScrutVar7.equals(3))
                        {
                            _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("val", "tx2");
                            _writer.writeEndElement();
                        }
                        else if (__dummyScrutVar7.equals(2))
                        {
                            _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("val", "bg2");
                            _writer.writeEndElement();
                        }
                        else
                        {
                            _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("val", s);
                            _writer.writeEndElement();
                        }    
                        _writer.writeEndElement();
                    }
                     
                    //buClr
                    if (p.getBulletSizePresent())
                    {
                        if (p.BulletSize > 0)
                        {
                            _writer.WriteStartElement("a", "buSzPct", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("val", (p.BulletSize * 1000).toString());
                            _writer.writeEndElement();
                        }
                        else
                        {
                        } 
                    }
                    else //buSzPct
                    //TODO
                    if (p.getBulletFlagsFieldPresent() && (p.BulletFlags & 0x1 << 3) > 0)
                    {
                        _writer.WriteStartElement("a", "buSzPct", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("val", "75000");
                        _writer.writeEndElement();
                    }
                      
                    //buSzPct
                    if (p.getBulletFontPresent())
                    {
                        if (!(p.getBulletFlagsFieldPresent() && (p.BulletFlags & 0x1 << 1) == 0))
                        {
                            _writer.WriteStartElement("a", "buFont", OpenXmlNamespaces.DrawingML);
                            FontCollection fonts = _ctx.getPpt().DocumentRecord.firstChildWithType().<FontCollection>FirstChildWithType();
                            FontEntityAtom entity = fonts.entities.get((int)p.BulletTypefaceIdx);
                            if (entity.TypeFace.indexOf('\0') > 0)
                            {
                                _writer.writeAttributeString("typeface", entity.TypeFace.substring(0, (0) + (entity.TypeFace.indexOf('\0'))));
                            }
                            else
                            {
                                _writer.writeAttributeString("typeface", entity.TypeFace);
                            } 
                            _writer.writeEndElement();
                        }
                        else
                        {
                            //buChar
                            _writer.WriteElementString("a", "buFontTx", OpenXmlNamespaces.DrawingML, "");
                        } 
                    }
                     
                    if (parentShapeTreeMapping != null && parentShapeTreeMapping.ShapeStyleTextProp9Atom != null && parentShapeTreeMapping.ShapeStyleTextProp9Atom.P9Runs.size() > p.IndentLevel && parentShapeTreeMapping.ShapeStyleTextProp9Atom.P9Runs[p.IndentLevel].fBulletHasAutoNumber == 1 && parentShapeTreeMapping.ShapeStyleTextProp9Atom.P9Runs[p.IndentLevel].bulletAutoNumberScheme == -1)
                    {
                        _writer.WriteStartElement("a", "buAutoNum", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("type", "arabicPeriod");
                        _writer.writeEndElement();
                    }
                    else if (p.getBulletCharPresent())
                    {
                        _writer.WriteStartElement("a", "buChar", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("char", p.BulletChar.toString());
                        _writer.writeEndElement();
                    }
                    else //buChar
                    if (!p.getBulletCharPresent())
                    {
                        _writer.WriteStartElement("a", "buChar", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("char", "�");
                        _writer.writeEndElement();
                    }
                       
                } 
            }
             
        } 
        //buChar
        _writer.writeEndElement();
    }

}


//pPr