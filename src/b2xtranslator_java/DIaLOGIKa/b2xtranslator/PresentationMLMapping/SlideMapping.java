//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:17 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientData;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientTextbox;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DrawingContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.FillStyleBooleanProperties;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GroupContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideLayoutPart;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CStringAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.DocumentAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.HeadersFootersAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.MainMaster;
import DIaLOGIKa.b2xtranslator.PptFileFormat.OEPlaceHolderAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.OutlineTextRefAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PlaceholderEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PPDrawing;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgBinaryTag;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgBinaryTagDataBlob;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgTags;
import DIaLOGIKa.b2xtranslator.PptFileFormat.RoundTripContentMasterId12;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideHeadersFootersContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideLayoutType;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideListWithText;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideShowSlideInfoAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextHeaderAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextStyleAtom;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.AnimationMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.MasterLayoutManager;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.PresentationMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ShapeTreeMapping;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

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
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class SlideMapping  extends PresentationMapping<RegularContainer> 
{
    public Slide Slide;
    public ShapeTreeMapping shapeTreeMapping;
    public SlideMapping(ConversionContext ctx) throws Exception {
        super(ctx, ctx.getPptx().getPresentationPart().addSlidePart());
    }

    /**
    * Get the id of our real main master.
    * 
    * This need not be the id of our immediate master as it can be a title master.
    * 
    *  @param slideAtom SlideAtom of slide to find main master id for
    *  @return Id of main master
    */
    private long getMainMasterId(SlideAtom slideAtom) throws Exception {
        Slide masterSlide = _ctx.getPpt().findMasterRecordById(slideAtom.MasterId);
        // Is our immediate master a title master?
        if (!(masterSlide instanceof MainMaster))
        {
            // Then our main master is the title master's master
            SlideAtom titleSlideAtom = masterSlide.firstChildWithType();
            return titleSlideAtom.MasterId;
        }
         
        return slideAtom.MasterId;
    }

    public void apply(RegularContainer slide) throws Exception {
        this.Slide = (Slide)slide;
        TraceLogger.debugInternal("SlideMapping.Apply");
        // Associate slide with slide layout
        SlideAtom slideAtom = slide.firstChildWithType();
        long mainMasterId = getMainMasterId(slideAtom);
        MasterLayoutManager layoutManager = _ctx.getOrCreateLayoutManagerByMasterId(mainMasterId);
        SlideLayoutPart layoutPart = null;
        RoundTripContentMasterId12 masterInfo = slide.firstChildWithType();
        // PPT2007 OOXML-Layout
        if (masterInfo != null)
        {
            layoutPart = layoutManager.getLayoutPartByInstanceId(masterInfo.ContentMasterInstanceId);
        }
        else // Pre-PPT2007 Title master layout
        if (mainMasterId != slideAtom.MasterId)
        {
            layoutPart = layoutManager.getOrCreateLayoutPartForTitleMasterId(slideAtom.MasterId);
        }
        else
        {
            // Pre-PPT2007 SSlideLayoutAtom primitive SlideLayoutType layout
            MainMaster m = (MainMaster)_ctx.getPpt().findMasterRecordById(slideAtom.MasterId);
            if (m.Layouts.size() == 1 && slideAtom.Layout.Geom == SlideLayoutType.Blank)
            {
                for (String layout : m.Layouts.values())
                {
                    String output = DIaLOGIKa.b2xtranslator.Tools.Utils.replaceOutdatedNamespaces(layout);
                    layoutPart = layoutManager.getOrCreateLayoutPartByCode(output);
                }
            }
            else
            {
                layoutPart = layoutManager.getOrCreateLayoutPartByLayoutType(slideAtom.Layout.Geom,slideAtom.Layout.PlaceholderTypes);
            } 
        }  
        this.targetPart.ReferencePart(layoutPart);
        // Start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("p", "sld", OpenXmlNamespaces.PresentationML);
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "a", null, OpenXmlNamespaces.DrawingML);
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "r", null, OpenXmlNamespaces.Relationships);
        if (DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(slideAtom.Flags, 0x1 << 0) == false)
        {
            _writer.writeAttributeString("showMasterSp", "0");
        }
         
        // TODO: Write slide data of master slide
        _writer.WriteStartElement("p", "cSld", OpenXmlNamespaces.PresentationML);
        //TODO: write background properties (p:bg)
        _writer.WriteStartElement("p", "spTree", OpenXmlNamespaces.PresentationML);
        shapeTreeMapping = new ShapeTreeMapping(_ctx,_writer);
        shapeTreeMapping.parentSlideMapping = this;
        shapeTreeMapping.Apply(slide.firstChildWithType());
        checkHeaderFooter(shapeTreeMapping);
        _writer.writeEndElement();
        //spTree
        _writer.writeEndElement();
        //cSld
        // TODO: Write clrMapOvr
        if (slide.firstChildWithType() != null)
        {
            new AnimationMapping(_ctx,_writer).Apply(slide.firstChildWithType());
        }
         
        //if (shapeTreeMapping.animinfos.Count > 0)
        //{
        //    new AnimationMapping(_ctx, _writer).Apply(shapeTreeMapping.animinfos);
        //}
        if (slide.firstChildWithType() != null)
            if (slide.firstChildWithType().<ProgBinaryTag>FirstChildWithType() != null)
                if (slide.firstChildWithType().<ProgBinaryTag>FirstChildWithType().<ProgBinaryTagDataBlob>FirstChildWithType() != null)
                {
                    new AnimationMapping(_ctx,_writer).Apply(slide.firstChildWithType().<ProgBinaryTag>FirstChildWithType().<ProgBinaryTagDataBlob>FirstChildWithType(), this, shapeTreeMapping.animinfos);
                }
                 
             
         
        // End the document
        _writer.writeEndElement();
        //sld
        _writer.WriteEndDocument();
        _writer.Flush();
    }

    private void insertMasterStylePlaceholders(ShapeTreeMapping stm) throws Exception {
        SlideAtom slideAtom = this.Slide.firstChildWithType();
        for (Slide master : this._ctx.getPpt().MainMasterRecords)
        {
            if (master.PersistAtom.SlideId == slideAtom.MasterId)
            {
                CSList<ShapeContainer> shapes = master.allChildrenWithType().get(0).<DrawingContainer>AllChildrenWithType()[0].<GroupContainer>AllChildrenWithType()[0].<ShapeContainer>AllChildrenWithType();
                for (ShapeContainer shapecontainer : shapes)
                {
                    for (Object __dummyForeachVar1 : shapecontainer.allChildrenWithType())
                    {
                        ClientData data = (ClientData)__dummyForeachVar1;
                        System.IO.MemoryStream ms = new System.IO.MemoryStream(data.bytes);
                        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                        if (rec.TypeCode == 3011)
                        {
                            OEPlaceHolderAtom placeholder = (OEPlaceHolderAtom)rec;
                            if (placeholder != null)
                            {
                                if (placeholder.PlacementId != PlaceholderEnum.MasterFooter)
                                {
                                    stm.apply(shapecontainer,"","","");
                                }
                                 
                            }
                             
                        }
                         
                    }
                }
            }
             
        }
    }

    private String readFooterFromClientTextBox(ClientTextbox textbox) throws Exception {
        System.IO.MemoryStream ms = new System.IO.MemoryStream(textbox.Bytes);
        TextHeaderAtom thAtom = null;
        TextStyleAtom style = null;
        CSList<Integer> lst = new CSList<Integer>();
        while (ms.Position < ms.Length)
        {
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
            long __dummyScrutVar0 = rec.TypeCode;
            if (__dummyScrutVar0.equals(0xf9e))
            {
                //OutlineTextRefAtom
                OutlineTextRefAtom otrAtom = (OutlineTextRefAtom)rec;
                SlideListWithText slideListWithText = _ctx.getPpt().DocumentRecord.RegularSlideListWithText;
                CSList<TextHeaderAtom> thAtoms = slideListWithText.SlideToPlaceholderTextHeaders.get(textbox.firstAncestorWithType().PersistAtom);
                thAtom = thAtoms.get(otrAtom.Index);
                //if (thAtom.TextAtom != null) text = thAtom.TextAtom.Text;
                if (thAtom.TextStyleAtom != null)
                    style = thAtom.TextStyleAtom;
                 
            }
            else if (__dummyScrutVar0.equals(0xf9f))
            {
                //TextHeaderAtom
                thAtom = (TextHeaderAtom)rec;
            }
            else if (__dummyScrutVar0.equals(0xfa0))
            {
                //TextCharsAtom
                thAtom.TextAtom = (TextAtom)rec;
            }
            else if (__dummyScrutVar0.equals(0xfa1))
            {
                //StyleTextPropAtom
                style = (TextStyleAtom)rec;
                style.setTextHeaderAtom(thAtom);
            }
            else if (__dummyScrutVar0.equals(0xfa2))
            {
            }
            else //MasterTextPropAtom
            if (__dummyScrutVar0.equals(0xfa8))
            {
                //TextBytesAtom
                //text = ((TextBytesAtom)rec).Text;
                thAtom.TextAtom = (TextAtom)rec;
                return thAtom.TextAtom.Text;
            }
            else //TextSpecialInfoAtom
            //SlideNumberMCAtom
            if (__dummyScrutVar0.equals(0xfaa) || __dummyScrutVar0.equals(0xfd8) || __dummyScrutVar0.equals(0xff9))
            {
            }
            else //HeaderMCAtom
            if (__dummyScrutVar0.equals(0xffa))
            {
            }
            else //FooterMCAtom
            if (__dummyScrutVar0.equals(0xff8))
            {
            }
            else
            {
            }         
        }
        return "";
    }

    //GenericDateMCAtom
    private void checkHeaderFooter(ShapeTreeMapping stm) throws Exception {
        SlideAtom slideAtom = this.Slide.firstChildWithType();
        String footertext = "";
        String headertext = "";
        String userdatetext = "";
        SlideHeadersFootersContainer headersfooters = this.Slide.firstChildWithType();
        if (headersfooters != null)
        {
            for (Object __dummyForeachVar4 : headersfooters.allChildrenWithType())
            {
                CStringAtom text = (CStringAtom)__dummyForeachVar4;
                long __dummyScrutVar1 = text.Instance;
                if (__dummyScrutVar1.equals(0))
                {
                    userdatetext = text.Text;
                }
                else if (__dummyScrutVar1.equals(1))
                {
                    headertext = text.Text;
                }
                else if (__dummyScrutVar1.equals(2))
                {
                    footertext = text.Text;
                }
                   
            }
        }
         
        //CStringAtom text = headersfooters.FirstChildWithType<CStringAtom>();
        //if (text != null)
        //{
        //    footertext = text.Text;
        //}
        boolean footer = false;
        boolean slideNumber = false;
        boolean date = false;
        boolean userDate = false;
        if (!(_ctx.getPpt().DocumentRecord.firstChildWithType().OmitTitlePlace && this.Slide.firstChildWithType().Layout.Geom == SlideLayoutType.TitleSlide))
            for (Object __dummyForeachVar6 : this._ctx.getPpt().DocumentRecord.allChildrenWithType())
            {
                SlideHeadersFootersContainer c = (SlideHeadersFootersContainer)__dummyForeachVar6;
                long __dummyScrutVar2 = c.Instance;
                if (__dummyScrutVar2.equals(0))
                {
                }
                else //PerSlideHeadersFootersContainer
                if (__dummyScrutVar2.equals(3))
                {
                    for (Object __dummyForeachVar5 : c.allChildrenWithType())
                    {
                        //SlideHeadersFootersContainer
                        HeadersFootersAtom a = (HeadersFootersAtom)__dummyForeachVar5;
                        if (a.fHasFooter)
                            footer = true;
                         
                        if (a.fHasSlideNumber)
                            slideNumber = true;
                         
                        if (a.fHasDate)
                            date = true;
                         
                        if (a.fHasUserDate)
                            userDate = true;
                         
                    }
                    //if (a.fHasHeader) header = true;
                    if (footer && footertext.length() == 0 && c.firstChildWithType() != null)
                    {
                        footertext = c.firstChildWithType().Text;
                    }
                     
                }
                else if (__dummyScrutVar2.equals(4))
                {
                }
                   
            }
         
        //NotesHeadersFootersContainer
        //if (footertext.Length == 0) footer = false;
        if (slideNumber)
        {
            for (Slide master : this._ctx.getPpt().MainMasterRecords)
            {
                if (master.PersistAtom.SlideId == slideAtom.MasterId)
                {
                    CSList<ShapeContainer> shapes = master.allChildrenWithType().get(0).<DrawingContainer>AllChildrenWithType()[0].<GroupContainer>AllChildrenWithType()[0].<ShapeContainer>AllChildrenWithType();
                    for (ShapeContainer shapecontainer : shapes)
                    {
                        for (Object __dummyForeachVar7 : shapecontainer.allChildrenWithType())
                        {
                            ClientData data = (ClientData)__dummyForeachVar7;
                            System.IO.MemoryStream ms = new System.IO.MemoryStream(data.bytes);
                            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                            if (rec.TypeCode == 3011)
                            {
                                OEPlaceHolderAtom placeholder = (OEPlaceHolderAtom)rec;
                                if (placeholder != null)
                                {
                                    if (placeholder.PlacementId == PlaceholderEnum.MasterSlideNumber)
                                    {
                                        stm.apply(shapecontainer,"","","");
                                    }
                                     
                                }
                                 
                            }
                             
                        }
                    }
                }
                 
            }
        }
         
        if (date)
        {
            for (Slide master : this._ctx.getPpt().MainMasterRecords)
            {
                //if (!(userDate & userdatetext.Length == 0))
                //{
                if (master.PersistAtom.SlideId == slideAtom.MasterId)
                {
                    CSList<ShapeContainer> shapes = master.allChildrenWithType().get(0).<DrawingContainer>AllChildrenWithType()[0].<GroupContainer>AllChildrenWithType()[0].<ShapeContainer>AllChildrenWithType();
                    for (ShapeContainer shapecontainer : shapes)
                    {
                        for (Object __dummyForeachVar10 : shapecontainer.allChildrenWithType())
                        {
                            ClientData data = (ClientData)__dummyForeachVar10;
                            System.IO.MemoryStream ms = new System.IO.MemoryStream(data.bytes);
                            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                            if (rec.TypeCode == 3011)
                            {
                                OEPlaceHolderAtom placeholder = (OEPlaceHolderAtom)rec;
                                if (placeholder != null)
                                {
                                    if (placeholder.PlacementId == PlaceholderEnum.MasterDate)
                                    {
                                        stm.apply(shapecontainer,"","","");
                                    }
                                     
                                }
                                 
                            }
                             
                        }
                    }
                }
                 
            }
        }
         
        // }
        if (footer)
            for (Slide master : this._ctx.getPpt().TitleMasterRecords)
            {
                if (master.PersistAtom.SlideId == slideAtom.MasterId)
                {
                    CSList<ShapeContainer> shapes = master.allChildrenWithType().get(0).<DrawingContainer>AllChildrenWithType()[0].<GroupContainer>AllChildrenWithType()[0].<ShapeContainer>AllChildrenWithType();
                    for (ShapeContainer shapecontainer : shapes)
                    {
                        for (Object __dummyForeachVar14 : shapecontainer.allChildrenWithType())
                        {
                            ClientData data = (ClientData)__dummyForeachVar14;
                            System.IO.MemoryStream ms = new System.IO.MemoryStream(data.bytes);
                            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                            if (rec.TypeCode == 3011)
                            {
                                OEPlaceHolderAtom placeholder = (OEPlaceHolderAtom)rec;
                                if (placeholder != null)
                                {
                                    if (placeholder.PlacementId == PlaceholderEnum.MasterFooter)
                                    {
                                        boolean doit = footertext.length() > 0;
                                        if (!doit)
                                        {
                                            for (Object __dummyForeachVar13 : shapecontainer.allChildrenWithType())
                                            {
                                                ShapeOptions so = (ShapeOptions)__dummyForeachVar13;
                                                if (so.OptionsByID.containsKey(PropertyId.FillStyleBooleanProperties))
                                                {
                                                    FillStyleBooleanProperties props = new FillStyleBooleanProperties(so.OptionsByID.get(PropertyId.FillStyleBooleanProperties).op);
                                                    if (props.fFilled && props.fUsefFilled)
                                                        doit = true;
                                                     
                                                }
                                                 
                                            }
                                        }
                                         
                                        if (doit)
                                            stm.apply(shapecontainer,footertext,"","");
                                         
                                        footer = false;
                                    }
                                     
                                }
                                 
                            }
                             
                        }
                    }
                }
                 
            }
         
        if (footer)
            for (Slide master : this._ctx.getPpt().MainMasterRecords)
            {
                if (master.PersistAtom.SlideId == slideAtom.MasterId)
                {
                    CSList<ShapeContainer> shapes = master.allChildrenWithType().get(0).<DrawingContainer>AllChildrenWithType()[0].<GroupContainer>AllChildrenWithType()[0].<ShapeContainer>AllChildrenWithType();
                    for (ShapeContainer shapecontainer : shapes)
                    {
                        for (Object __dummyForeachVar18 : shapecontainer.allChildrenWithType())
                        {
                            ClientData data = (ClientData)__dummyForeachVar18;
                            System.IO.MemoryStream ms = new System.IO.MemoryStream(data.bytes);
                            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                            if (rec.TypeCode == 3011)
                            {
                                OEPlaceHolderAtom placeholder = (OEPlaceHolderAtom)rec;
                                if (placeholder != null)
                                {
                                    if (placeholder.PlacementId == PlaceholderEnum.MasterFooter)
                                    {
                                        if (footertext.length() == 0 & shapecontainer.allChildrenWithType().size() > 0)
                                            footertext = readFooterFromClientTextBox(shapecontainer.firstChildWithType());
                                         
                                        boolean doit = footertext.length() > 0;
                                        if (!doit)
                                        {
                                            for (Object __dummyForeachVar17 : shapecontainer.allChildrenWithType())
                                            {
                                                ShapeOptions so = (ShapeOptions)__dummyForeachVar17;
                                                if (so.OptionsByID.containsKey(PropertyId.FillStyleBooleanProperties))
                                                {
                                                    FillStyleBooleanProperties props = new FillStyleBooleanProperties(so.OptionsByID.get(PropertyId.FillStyleBooleanProperties).op);
                                                    if (props.fFilled && props.fUsefFilled)
                                                        doit = true;
                                                     
                                                }
                                                 
                                            }
                                        }
                                         
                                        if (doit)
                                            stm.apply(shapecontainer,footertext,"","");
                                         
                                        footer = false;
                                    }
                                     
                                }
                                 
                            }
                             
                        }
                    }
                }
                 
            }
         
    }

}


