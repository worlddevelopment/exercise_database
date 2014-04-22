//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:21 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.NumberSupport;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BitmapBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.MetafilePictBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.BlipCollection9Container;
import DIaLOGIKa.b2xtranslator.PptFileFormat.BlipEntityAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ColorSchemeAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.FontCollection;
import DIaLOGIKa.b2xtranslator.PptFileFormat.FontEntityAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.GrColorAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.List;
import DIaLOGIKa.b2xtranslator.PptFileFormat.MainMaster;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphMask;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun9;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgBinaryTag;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgBinaryTagDataBlob;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgTags;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextMasterStyle9Atom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextMasterStyleAtom;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.CharacterRunPropsMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.PresentationMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ShapeTreeMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor.ByteOrder;
import java.io.InputStream;

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
public class TextMasterStyleMapping  extends AbstractOpenXmlMapping 
{
    protected ConversionContext _ctx;
    public RegularContainer _Master;
    public PresentationMapping<RegularContainer> _parentSlideMapping = null;
    private int lastSpaceBefore = 0;
    private String lastColor = "";
    private String lastBulletFont = "";
    private String lastBulletChar = "";
    private String lastBulletColor = "";
    private String lastSize = "";
    private String lastTypeface = "";
    public TextMasterStyleMapping(ConversionContext ctx, XmlWriter writer, PresentationMapping<RegularContainer> parentSlideMapping) throws Exception {
        super(writer);
        _ctx = ctx;
        _parentSlideMapping = parentSlideMapping;
    }

    public CSList<TextMasterStyleAtom> titleAtoms = new CSList<TextMasterStyleAtom>();
    public CSList<TextMasterStyleAtom> bodyAtoms = new CSList<TextMasterStyleAtom>();
    public CSList<TextMasterStyleAtom> CenterBodyAtoms = new CSList<TextMasterStyleAtom>();
    public CSList<TextMasterStyleAtom> CenterTitleAtoms = new CSList<TextMasterStyleAtom>();
    public CSList<TextMasterStyleAtom> noteAtoms = new CSList<TextMasterStyleAtom>();
    public void apply(RegularContainer Master) throws Exception {
        _Master = Master;
        CSList<TextMasterStyleAtom> atoms = Master.allChildrenWithType();
        CSList<TextMasterStyle9Atom> body9atoms = new CSList<TextMasterStyle9Atom>();
        CSList<TextMasterStyle9Atom> title9atoms = new CSList<TextMasterStyle9Atom>();
        for (Object __dummyForeachVar3 : Master.allChildrenWithType())
        {
            ProgTags progtags = (ProgTags)__dummyForeachVar3;
            for (Object __dummyForeachVar2 : progtags.allChildrenWithType())
            {
                ProgBinaryTag progbinarytag = (ProgBinaryTag)__dummyForeachVar2;
                for (Object __dummyForeachVar1 : progbinarytag.allChildrenWithType())
                {
                    ProgBinaryTagDataBlob blob = (ProgBinaryTagDataBlob)__dummyForeachVar1;
                    for (Object __dummyForeachVar0 : blob.allChildrenWithType())
                    {
                        TextMasterStyle9Atom atom = (TextMasterStyle9Atom)__dummyForeachVar0;
                        if (atom.Instance == 0)
                            title9atoms.add(atom);
                         
                        if (atom.Instance == 1)
                            body9atoms.add(atom);
                         
                    }
                }
            }
        }
        for (TextMasterStyleAtom atom : atoms)
        {
            if (atom.Instance == 0)
                titleAtoms.add(atom);
             
            if (atom.Instance == 1)
                bodyAtoms.add(atom);
             
            if (atom.Instance == 5)
                CenterBodyAtoms.add(atom);
             
            if (atom.Instance == 6)
                CenterTitleAtoms.add(atom);
             
        }
        _writer.WriteStartElement("p", "txStyles", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "titleStyle", OpenXmlNamespaces.PresentationML);
        ParagraphRun9 pr9 = null;
        for (TextMasterStyleAtom atom : titleAtoms)
        {
            lastSpaceBefore = 0;
            lastBulletFont = "";
            lastBulletChar = "";
            lastColor = "";
            lastBulletColor = "";
            lastSize = "";
            for (int i = 0;i < atom.IndentLevelCount;i++)
            {
                pr9 = null;
                if (title9atoms.size() > 0 && title9atoms.get(0).pruns.size() > i)
                    pr9 = title9atoms.get(0).pruns.get(i);
                 
                writepPr(atom.CRuns.get(i),atom.PRuns.get(i),pr9,i,true);
            }
            for (int i = atom.IndentLevelCount;i < 9;i++)
            {
                pr9 = null;
                if (title9atoms.size() > 0 && title9atoms.get(0).pruns.size() > i)
                    pr9 = title9atoms.get(0).pruns.get(i);
                 
                writepPr(atom.CRuns.get(0),atom.PRuns.get(0),pr9,i,true);
            }
        }
        _writer.writeEndElement();
        //titleStyle
        _writer.WriteStartElement("p", "bodyStyle", OpenXmlNamespaces.PresentationML);
        for (TextMasterStyleAtom atom : bodyAtoms)
        {
            lastSpaceBefore = 0;
            lastColor = "";
            lastBulletFont = "";
            lastBulletChar = "";
            lastBulletColor = "";
            lastSize = "";
            for (int i = 0;i < atom.IndentLevelCount;i++)
            {
                pr9 = null;
                if (body9atoms.size() > 0 && body9atoms.get(0).pruns.size() > i)
                    pr9 = body9atoms.get(0).pruns.get(i);
                 
                writepPr(atom.CRuns.get(i),atom.PRuns.get(i),pr9,i,false);
            }
            for (int i = atom.IndentLevelCount;i < 9;i++)
            {
                pr9 = null;
                if (body9atoms.size() > 0 && body9atoms.get(0).pruns.size() > i)
                    pr9 = body9atoms.get(0).pruns.get(i);
                 
                writepPr(atom.CRuns.get(0),atom.PRuns.get(0),pr9,i,false);
            }
        }
        _writer.writeEndElement();
        //bodyStyle
        _writer.writeEndElement();
    }

    //txStyles
    public void applyNotesMaster(RegularContainer notesMaster) throws Exception {
        _Master = notesMaster;
        MainMaster m = this._ctx.getPpt().MainMasterRecords.get(0);
        CSList<TextMasterStyleAtom> atoms = m.allChildrenWithType();
        for (TextMasterStyleAtom atom : atoms)
        {
            if (atom.Instance == 2)
                noteAtoms.add(atom);
             
        }
        _writer.WriteStartElement("p", "notesStyle", OpenXmlNamespaces.PresentationML);
        ParagraphRun9 pr9 = null;
        for (TextMasterStyleAtom atom : noteAtoms)
        {
            lastSpaceBefore = 0;
            lastBulletFont = "";
            lastBulletChar = "";
            lastBulletColor = "";
            lastColor = "";
            lastSize = "";
            for (int i = 0;i < atom.IndentLevelCount;i++)
            {
                pr9 = null;
                writepPr(atom.CRuns.get(i),atom.PRuns.get(i),pr9,i,true);
            }
            for (int i = atom.IndentLevelCount;i < 9;i++)
            {
                pr9 = null;
                writepPr(atom.CRuns.get(0),atom.PRuns.get(0),pr9,i,true);
            }
        }
        _writer.writeEndElement();
    }

    public void applyHandoutMaster(RegularContainer handoutMaster) throws Exception {
        _Master = handoutMaster;
        MainMaster m = this._ctx.getPpt().MainMasterRecords.get(0);
        CSList<TextMasterStyleAtom> atoms = m.allChildrenWithType();
        for (TextMasterStyleAtom atom : atoms)
        {
            if (atom.Instance == 2)
                noteAtoms.add(atom);
             
        }
        _writer.WriteStartElement("p", "handoutStyle", OpenXmlNamespaces.PresentationML);
        ParagraphRun9 pr9 = null;
        for (TextMasterStyleAtom atom : noteAtoms)
        {
            lastSpaceBefore = 0;
            lastBulletFont = "";
            lastBulletChar = "";
            lastBulletColor = "";
            lastColor = "";
            lastSize = "";
            for (int i = 0;i < atom.IndentLevelCount;i++)
            {
                pr9 = null;
                writepPr(atom.CRuns.get(i),atom.PRuns.get(i),pr9,i,true);
            }
            for (int i = atom.IndentLevelCount;i < 9;i++)
            {
                pr9 = null;
                writepPr(atom.CRuns.get(0),atom.PRuns.get(0),pr9,i,true);
            }
        }
        _writer.writeEndElement();
    }

    public void writepPr(CharacterRun cr, ParagraphRun pr, ParagraphRun9 pr9, int IndentLevel, boolean isTitle) throws Exception {
        writepPr(cr,pr,pr9,IndentLevel,isTitle,false);
    }

    public void writepPr(CharacterRun cr, ParagraphRun pr, ParagraphRun9 pr9, int IndentLevel, boolean isTitle, boolean isDefault) throws Exception {
        //TextMasterStyleAtom defaultStyle = _ctx.Ppt.DocumentRecord.FirstChildWithType<DIaLOGIKa.b2xtranslator.PptFileFormat.Environment>().FirstChildWithType<TextMasterStyleAtom>();
        _writer.WriteStartElement("a", "lvl" + String.valueOf((IndentLevel + 1)) + "pPr", OpenXmlNamespaces.DrawingML);
        //marL
        if (pr.getLeftMarginPresent() && !isDefault)
            _writer.writeAttributeString("marL", String.valueOf(Utils.masterCoordToEMU((int)pr.LeftMargin)));
         
        //marR
        //lvl
        if (pr.IndentLevel > 0)
            _writer.writeAttributeString("lvl", pr.IndentLevel.toString());
         
        //indent
        if (pr.getIndentPresent() && !isDefault)
            _writer.writeAttributeString("indent", String.valueOf((-1 * (Utils.masterCoordToEMU((int)(pr.LeftMargin - pr.Indent))))));
         
        //algn
        if (pr.getAlignmentPresent())
        {
            Int16? __dummyScrutVar0 = pr.Alignment;
            if (__dummyScrutVar0.equals(0x0000))
            {
                //Left
                _writer.writeAttributeString("algn", "l");
            }
            else if (__dummyScrutVar0.equals(0x0001))
            {
                //Center
                _writer.writeAttributeString("algn", "ctr");
            }
            else if (__dummyScrutVar0.equals(0x0002))
            {
                //Right
                _writer.writeAttributeString("algn", "r");
            }
            else if (__dummyScrutVar0.equals(0x0003))
            {
                //Justify
                _writer.writeAttributeString("algn", "just");
            }
            else if (__dummyScrutVar0.equals(0x0004))
            {
                //Distributed
                _writer.writeAttributeString("algn", "dist");
            }
            else if (__dummyScrutVar0.equals(0x0005))
            {
                //ThaiDistributed
                _writer.writeAttributeString("algn", "thaiDist");
            }
            else if (__dummyScrutVar0.equals(0x0006))
            {
                //JustifyLow
                _writer.writeAttributeString("algn", "justLow");
            }
                   
        }
         
        //defTabSz
        if (pr.getDefaultTabSizePresent())
        {
            _writer.writeAttributeString("defTabSz", String.valueOf(Utils.masterCoordToEMU((int)pr.DefaultTabSize)));
        }
         
        //rtl
        if (pr.getTextDirectionPresent())
        {
            UInt16? __dummyScrutVar1 = pr.TextDirection;
            if (__dummyScrutVar1.equals(0x0000))
            {
                _writer.writeAttributeString("rtl", "0");
            }
            else if (__dummyScrutVar1.equals(0x0001))
            {
                _writer.writeAttributeString("rtl", "1");
            }
              
        }
        else
        {
            _writer.writeAttributeString("rtl", "0");
        } 
        //eaLnkBrk
        //fontAlgn
        if (pr.getFontAlignPresent())
        {
            UInt16? __dummyScrutVar2 = pr.FontAlign;
            if (__dummyScrutVar2.equals(0x0000))
            {
                //Roman
                _writer.writeAttributeString("fontAlgn", "base");
            }
            else if (__dummyScrutVar2.equals(0x0001))
            {
                //Hanging
                _writer.writeAttributeString("fontAlgn", "t");
            }
            else if (__dummyScrutVar2.equals(0x0002))
            {
                //Center
                _writer.writeAttributeString("fontAlgn", "ctr");
            }
            else if (__dummyScrutVar2.equals(0x0003))
            {
                //UpholdFixed
                _writer.writeAttributeString("fontAlgn", "b");
            }
                
        }
         
        //latinLnBrk
        //hangingPunct
        //lnSpc
        //spcBef
        if (pr.getSpaceBeforePresent())
        {
            _writer.WriteStartElement("a", "spcBef", OpenXmlNamespaces.DrawingML);
            if (pr.SpaceBefore < 0)
            {
                _writer.WriteStartElement("a", "spcPts", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", String.valueOf((-1 * 12 * pr.SpaceBefore)));
                //TODO: the 12 is wrong
                _writer.writeEndElement();
            }
            else
            {
                //spcPct
                _writer.WriteStartElement("a", "spcPct", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", String.valueOf((1000 * pr.SpaceBefore)));
                _writer.writeEndElement();
            } 
            //spcPct
            _writer.writeEndElement();
            //spcBef
            lastSpaceBefore = (int)pr.SpaceBefore;
        }
        else
        {
            if (lastSpaceBefore != 0)
            {
                _writer.WriteStartElement("a", "spcBef", OpenXmlNamespaces.DrawingML);
                if (lastSpaceBefore < 0)
                {
                    _writer.WriteStartElement("a", "spcPts", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf((-1 * 12 * lastSpaceBefore)));
                    //TODO: the 12 is wrong
                    _writer.writeEndElement();
                }
                else
                {
                    //spcPct
                    _writer.WriteStartElement("a", "spcPct", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf((1000 * lastSpaceBefore)));
                    _writer.writeEndElement();
                } 
                //spcPct
                _writer.writeEndElement();
            }
             
        } 
        //spcBef
        //spcAft
        if (pr.getSpaceAfterPresent())
        {
            _writer.WriteStartElement("a", "spcAft", OpenXmlNamespaces.DrawingML);
            if (pr.SpaceAfter < 0)
            {
                _writer.WriteStartElement("a", "spcPts", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", String.valueOf((-1 * pr.SpaceAfter)));
                //TODO: this has to be verified!
                _writer.writeEndElement();
            }
            else
            {
                //spcPct
                _writer.WriteStartElement("a", "spcPct", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", pr.SpaceAfter.toString());
                _writer.writeEndElement();
            } 
            //spcPct
            _writer.writeEndElement();
        }
         
        //spcAft
        //EG_TextBulletColor
        //EG_TextBulletSize
        //EG_TextBulletTypeFace
        //EG_TextBullet
        boolean bulletwritten = false;
        if (pr9 != null)
        {
            if (pr9.getBulletBlipReferencePresent())
                for (Object __dummyForeachVar14 : _ctx.getPpt().DocumentRecord.firstChildWithType().<ProgTags>AllChildrenWithType())
                {
                    ProgTags progtags = (ProgTags)__dummyForeachVar14;
                    for (Object __dummyForeachVar13 : progtags.allChildrenWithType())
                    {
                        ProgBinaryTag bintags = (ProgBinaryTag)__dummyForeachVar13;
                        for (Object __dummyForeachVar12 : bintags.allChildrenWithType())
                        {
                            ProgBinaryTagDataBlob data = (ProgBinaryTagDataBlob)__dummyForeachVar12;
                            for (Object __dummyForeachVar11 : data.allChildrenWithType())
                            {
                                BlipCollection9Container blips = (BlipCollection9Container)__dummyForeachVar11;
                                if (blips.Children.size() > pr9.bulletblipref & pr9.bulletblipref > -1)
                                {
                                    ImagePart imgPart = null;
                                    BitmapBlip b = ((BlipEntityAtom)blips.Children.get(pr9.bulletblipref)).blip;
                                    if (b == null)
                                    {
                                        MetafilePictBlip mb = ((BlipEntityAtom)blips.Children.get(pr9.bulletblipref)).mblip;
                                        imgPart = _parentSlideMapping.targetPart.addImagePart(ShapeTreeMapping.getImageType(mb.TypeCode));
                                        imgPart.setTargetDirectory("..\\media");
                                        InputStream outStream = imgPart.getStream();
                                        byte[] decompressed = mb.decrompress();
                                        outStream.write(decompressed,0,decompressed.length);
                                    }
                                    else
                                    {
                                        //outStream.Write(mb.m_pvBits, 0, mb.m_pvBits.Length);
                                        imgPart = _parentSlideMapping.targetPart.addImagePart(ShapeTreeMapping.getImageType(b.TypeCode));
                                        imgPart.setTargetDirectory("..\\media");
                                        InputStream outStream = imgPart.getStream();
                                        outStream.write(b.m_pvBits,0,b.m_pvBits.length);
                                    } 
                                    _writer.WriteStartElement("a", "buBlip", OpenXmlNamespaces.DrawingML);
                                    _writer.WriteStartElement("a", "blip", OpenXmlNamespaces.DrawingML);
                                    _writer.WriteAttributeString("r", "embed", OpenXmlNamespaces.Relationships, imgPart.getRelIdToString());
                                    _writer.writeEndElement();
                                    //blip
                                    _writer.writeEndElement();
                                    //buBlip
                                    bulletwritten = true;
                                }
                                 
                            }
                        }
                    }
                }
             
        }
         
        if (!bulletwritten & !isTitle)
        {
            if (pr.getBulletFlagsFieldPresent() & (pr.BulletFlags & (ushort)ParagraphMask.HasBullet) == 0)
            {
                _writer.WriteElementString("a", "buNone", OpenXmlNamespaces.DrawingML, "");
            }
            else
            {
                if (pr.getBulletColorPresent() && (!(pr.getBulletFlagsFieldPresent() && (pr.BulletFlags & 1 << 2) == 0)))
                {
                    RefSupport<String> refVar___0 = new RefSupport<String>(lastBulletColor);
                    writeBuClr((RegularContainer)this._Master,pr.BulletColor,refVar___0);
                    lastBulletColor = refVar___0.getValue();
                }
                else if (lastBulletColor.length() > 0)
                {
                    _writer.WriteStartElement("a", "buClr", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", lastBulletColor);
                    _writer.writeEndElement();
                    _writer.writeEndElement();
                }
                  
                //buClr
                if (pr.getBulletSizePresent())
                {
                    if (pr.BulletSize > 0 && pr.BulletSize != 100)
                    {
                        _writer.WriteStartElement("a", "buSzPct", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("val", (pr.BulletSize * 1000).toString());
                        _writer.writeEndElement();
                    }
                    else
                    {
                    } 
                }
                 
                //buSzPct
                //TODO
                if (pr.getBulletFontPresent())
                {
                    _writer.WriteStartElement("a", "buFont", OpenXmlNamespaces.DrawingML);
                    FontCollection fonts = _ctx.getPpt().DocumentRecord.firstChildWithType().<FontCollection>FirstChildWithType();
                    FontEntityAtom entity = fonts.entities.get((int)pr.BulletTypefaceIdx);
                    if (entity.TypeFace.indexOf('\0') > 0)
                    {
                        _writer.writeAttributeString("typeface", entity.TypeFace.substring(0, (0) + (entity.TypeFace.indexOf('\0'))));
                    }
                    else
                    {
                        _writer.writeAttributeString("typeface", entity.TypeFace);
                    } 
                    _writer.writeEndElement();
                    //buChar
                    lastBulletFont = entity.TypeFace;
                }
                else if (lastBulletFont.length() > 0)
                {
                    _writer.WriteStartElement("a", "buFont", OpenXmlNamespaces.DrawingML);
                    if (lastBulletFont.indexOf('\0') > 0)
                    {
                        _writer.writeAttributeString("typeface", lastBulletFont.substring(0, (0) + (lastBulletFont.indexOf('\0'))));
                    }
                    else
                    {
                        _writer.writeAttributeString("typeface", lastBulletFont);
                    } 
                    _writer.writeEndElement();
                }
                  
                //buChar
                if (pr.getBulletCharPresent())
                {
                    _writer.WriteStartElement("a", "buChar", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("char", pr.BulletChar.toString());
                    _writer.writeEndElement();
                    //buChar
                    lastBulletChar = pr.BulletChar.toString();
                }
                else if (lastBulletChar.length() > 0)
                {
                    _writer.WriteStartElement("a", "buChar", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("char", lastBulletChar);
                    _writer.writeEndElement();
                }
                  
            } 
        }
         
        //buChar
        //tabLst
        //defRPr
        //extLst
        RefSupport<String> refVar___1 = new RefSupport<String>(lastColor);
        RefSupport<String> refVar___2 = new RefSupport<String>(lastSize);
        RefSupport<String> refVar___3 = new RefSupport<String>(lastTypeface);
        new CharacterRunPropsMapping(_ctx,_writer).apply(cr,"defRPr",(RegularContainer)_Master,refVar___1,refVar___2,refVar___3,"en-US",null,IndentLevel);
        lastColor = refVar___1.getValue();
        lastSize = refVar___2.getValue();
        lastTypeface = refVar___3.getValue();
        _writer.writeEndElement();
    }

    //lvlXpPr
    public void writeBuClr(RegularContainer slide, GrColorAtom color, RefSupport<String> lastColor) throws Exception {
        if (color.getIsSchemeColor())
        {
            //TODO: to be fully implemented
            //_writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
            if (slide == null)
            {
                /**
                * /TODO: what shall be used in this case (happens for default text style in presentation.xml)
                */
                //_writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                //_writer.WriteAttributeString("val", "000000");
                //_writer.WriteEndElement();
                _writer.WriteStartElement("a", "buClr", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                byte __dummyScrutVar3 = color.Index;
                if (__dummyScrutVar3.equals(0x00))
                {
                    _writer.writeAttributeString("val", "bg1");
                }
                else //background
                if (__dummyScrutVar3.equals(0x01))
                {
                    _writer.writeAttributeString("val", "tx1");
                }
                else //text
                if (__dummyScrutVar3.equals(0x02))
                {
                    _writer.writeAttributeString("val", "dk1");
                }
                else //shadow
                if (__dummyScrutVar3.equals(0x03))
                {
                    _writer.writeAttributeString("val", "tx1");
                }
                else //title text
                if (__dummyScrutVar3.equals(0x04))
                {
                    _writer.writeAttributeString("val", "bg2");
                }
                else //fill
                if (__dummyScrutVar3.equals(0x05))
                {
                    _writer.writeAttributeString("val", "accent1");
                }
                else //accent1
                if (__dummyScrutVar3.equals(0x06))
                {
                    _writer.writeAttributeString("val", "accent2");
                }
                else //accent2
                if (__dummyScrutVar3.equals(0x07))
                {
                    _writer.writeAttributeString("val", "accent3");
                }
                else //accent3
                if (__dummyScrutVar3.equals(0xFE))
                {
                    //sRGB
                    lastColor.setValue(StringSupport.PadLeft(NumberSupport.format(color.Red, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(color.Green, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(color.Blue, "X"), 2, '0'));
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar3.equals(0xFF))
                {
                }
                          
                //undefined
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
            else
            {
                ColorSchemeAtom MasterScheme = null;
                SlideAtom ato = slide.firstChildWithType();
                CSList<ColorSchemeAtom> colors;
                if (ato != null && DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(ato.Flags, 0x1 << 1) && ato.MasterId != 0)
                {
                    colors = _ctx.getPpt().findMasterRecordById(ato.MasterId).allChildrenWithType();
                }
                else
                {
                    colors = slide.allChildrenWithType();
                } 
                for (ColorSchemeAtom colorsch : colors)
                {
                    if (colorsch.Instance == 1)
                        MasterScheme = colorsch;
                     
                }
                if (color.Index != 0xFF)
                {
                    _writer.WriteStartElement("a", "buClr", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                    byte __dummyScrutVar4 = color.Index;
                    if (__dummyScrutVar4.equals(0x00))
                    {
                        //background
                        lastColor.setValue(new RGBColor(MasterScheme.Background,ByteOrder.RedFirst).SixDigitHexCode);
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else if (__dummyScrutVar4.equals(0x01))
                    {
                        //text
                        lastColor.setValue(new RGBColor(MasterScheme.TextAndLines,ByteOrder.RedFirst).SixDigitHexCode);
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else if (__dummyScrutVar4.equals(0x02))
                    {
                        //shadow
                        lastColor.setValue(new RGBColor(MasterScheme.Shadows,ByteOrder.RedFirst).SixDigitHexCode);
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else if (__dummyScrutVar4.equals(0x03))
                    {
                        //title
                        lastColor.setValue(new RGBColor(MasterScheme.TitleText,ByteOrder.RedFirst).SixDigitHexCode);
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else if (__dummyScrutVar4.equals(0x04))
                    {
                        //fill
                        lastColor.setValue(new RGBColor(MasterScheme.Fills,ByteOrder.RedFirst).SixDigitHexCode);
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else if (__dummyScrutVar4.equals(0x05))
                    {
                        //accent1
                        lastColor.setValue(new RGBColor(MasterScheme.Accent,ByteOrder.RedFirst).SixDigitHexCode);
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else if (__dummyScrutVar4.equals(0x06))
                    {
                        //accent2
                        lastColor.setValue(new RGBColor(MasterScheme.AccentAndHyperlink,ByteOrder.RedFirst).SixDigitHexCode);
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else if (__dummyScrutVar4.equals(0x07))
                    {
                        //accent3
                        lastColor.setValue(new RGBColor(MasterScheme.AccentAndFollowedHyperlink,ByteOrder.RedFirst).SixDigitHexCode);
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else if (__dummyScrutVar4.equals(0xFE))
                    {
                        //sRGB
                        lastColor.setValue(StringSupport.PadLeft(NumberSupport.format(color.Red, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(color.Green, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(color.Blue, "X"), 2, '0'));
                        _writer.writeAttributeString("val", lastColor.getValue());
                    }
                    else
                    {
                    }         
                    //undefined
                    _writer.writeEndElement();
                    _writer.writeEndElement();
                }
                 
            } 
        }
        else
        {
            //_writer.WriteEndElement();
            _writer.WriteStartElement("a", "buClr", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            lastColor.setValue(StringSupport.PadLeft(NumberSupport.format(color.Red, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(color.Green, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(color.Blue, "X"), 2, '0'));
            _writer.writeAttributeString("val", lastColor.getValue());
            _writer.writeEndElement();
            _writer.writeEndElement();
        } 
    }

}


