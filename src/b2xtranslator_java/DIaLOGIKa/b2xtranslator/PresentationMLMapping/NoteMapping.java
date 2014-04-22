//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:06 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientData;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DrawingContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GroupContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CStringAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.HeadersFootersAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Note;
import DIaLOGIKa.b2xtranslator.PptFileFormat.NotesAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.OEPlaceHolderAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PlaceholderEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PPDrawing;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideHeadersFootersContainer;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.PresentationMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ShapeTreeMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.SlideMapping;
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
public class NoteMapping  extends PresentationMapping<RegularContainer> 
{
    public Note Note;
    private SlideMapping SlideMapping;
    public NoteMapping(ConversionContext ctx, SlideMapping slideMapping) throws Exception {
        super(ctx, ctx.getPptx().getPresentationPart().addNotePart());
        SlideMapping = slideMapping;
    }

    public void apply(RegularContainer note) throws Exception {
        this.Note = (Note)note;
        TraceLogger.debugInternal("NoteMapping.Apply");
        // Associate slide with slide layout
        NotesAtom notesAtom = note.firstChildWithType();
        //Add relationship to slide
        this.targetPart.ReferencePart(SlideMapping.targetPart);
        SlideMapping.targetPart.ReferencePart(this.targetPart);
        //Add relationship to notes master
        // Start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("p", "notes", OpenXmlNamespaces.PresentationML);
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "a", null, OpenXmlNamespaces.DrawingML);
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "r", null, OpenXmlNamespaces.Relationships);
        // TODO: Write slide data of master slide
        _writer.WriteStartElement("p", "cSld", OpenXmlNamespaces.PresentationML);
        //TODO: write background properties (p:bg)
        _writer.WriteStartElement("p", "spTree", OpenXmlNamespaces.PresentationML);
        ShapeTreeMapping stm = new ShapeTreeMapping(_ctx,_writer);
        stm.parentSlideMapping = this;
        stm.Apply(note.firstChildWithType());
        checkHeaderFooter(stm);
        _writer.writeEndElement();
        //spTree
        _writer.writeEndElement();
        //cSld
        // TODO: Write clrMapOvr
        // End the document
        _writer.writeEndElement();
        //sld
        _writer.WriteEndDocument();
        _writer.Flush();
    }

    private void checkHeaderFooter(ShapeTreeMapping stm) throws Exception {
        NotesAtom slideAtom = this.Note.firstChildWithType();
        String footertext = "";
        String headertext = "";
        String userdatetext = "";
        SlideHeadersFootersContainer headersfooters = this.Note.firstChildWithType();
        if (headersfooters != null)
        {
            for (Object __dummyForeachVar0 : headersfooters.allChildrenWithType())
            {
                CStringAtom text = (CStringAtom)__dummyForeachVar0;
                long __dummyScrutVar0 = text.Instance;
                if (__dummyScrutVar0.equals(0))
                {
                    userdatetext = text.Text;
                }
                else if (__dummyScrutVar0.equals(1))
                {
                    headertext = text.Text;
                }
                else if (__dummyScrutVar0.equals(2))
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
        boolean header = false;
        boolean slideNumber = false;
        boolean date = false;
        boolean userDate = false;
        for (Object __dummyForeachVar2 : this._ctx.getPpt().DocumentRecord.allChildrenWithType())
        {
            SlideHeadersFootersContainer c = (SlideHeadersFootersContainer)__dummyForeachVar2;
            long __dummyScrutVar1 = c.Instance;
            if (__dummyScrutVar1.equals(0))
            {
            }
            else //PerSlideHeadersFootersContainer
            if (__dummyScrutVar1.equals(3))
            {
            }
            else //SlideHeadersFootersContainer
            if (__dummyScrutVar1.equals(4))
            {
                for (Object __dummyForeachVar1 : c.allChildrenWithType())
                {
                    //NotesHeadersFootersContainer
                    HeadersFootersAtom a = (HeadersFootersAtom)__dummyForeachVar1;
                    if (a.fHasFooter)
                        footer = true;
                     
                    if (a.fHasHeader)
                        header = true;
                     
                    if (a.fHasSlideNumber)
                        slideNumber = true;
                     
                    if (a.fHasDate)
                        date = true;
                     
                    if (a.fHasUserDate)
                        userDate = true;
                     
                }
            }
               
        }
        //if (slideNumber)
        //{
        //    foreach (Slide master in this._ctx.Ppt.MainMasterRecords)
        //    {
        //        if (master.PersistAtom.SlideId == slideAtom.MasterId)
        //        {
        //            List<OfficeDrawing.ShapeContainer> shapes = master.AllChildrenWithType<PPDrawing>()[0].AllChildrenWithType<OfficeDrawing.DrawingContainer>()[0].AllChildrenWithType<OfficeDrawing.GroupContainer>()[0].AllChildrenWithType<OfficeDrawing.ShapeContainer>();
        //            foreach (OfficeDrawing.ShapeContainer shapecontainer in shapes)
        //            {
        //                foreach (OfficeDrawing.ClientData data in shapecontainer.AllChildrenWithType<OfficeDrawing.ClientData>())
        //                {
        //                    System.IO.MemoryStream ms = new System.IO.MemoryStream(data.bytes);
        //                    OfficeDrawing.Record rec = OfficeDrawing.Record.ReadRecord(ms, 0);
        //                    if (rec.TypeCode == 3011)
        //                    {
        //                        OEPlaceHolderAtom placeholder = (OEPlaceHolderAtom)rec;
        //                        if (placeholder != null)
        //                        {
        //                            if (placeholder.PlacementId == PlaceholderEnum.MasterSlideNumber)
        //                            {
        //                                stm.Apply(shapecontainer, "");
        //                            }
        //                        }
        //                    }
        //                }
        //            }
        //        }
        //    }
        //}
        //if (date)
        //{
        //if (!(userDate & userdatetext.Length == 0))
        //    {
        //    foreach (Slide master in this._ctx.Ppt.MainMasterRecords)
        //    {
        //        if (master.PersistAtom.SlideId == slideAtom.MasterId)
        //        {
        //            List<OfficeDrawing.ShapeContainer> shapes = master.AllChildrenWithType<PPDrawing>()[0].AllChildrenWithType<OfficeDrawing.DrawingContainer>()[0].AllChildrenWithType<OfficeDrawing.GroupContainer>()[0].AllChildrenWithType<OfficeDrawing.ShapeContainer>();
        //            foreach (OfficeDrawing.ShapeContainer shapecontainer in shapes)
        //            {
        //                foreach (OfficeDrawing.ClientData data in shapecontainer.AllChildrenWithType<OfficeDrawing.ClientData>())
        //                {
        //                    System.IO.MemoryStream ms = new System.IO.MemoryStream(data.bytes);
        //                    OfficeDrawing.Record rec = OfficeDrawing.Record.ReadRecord(ms, 0);
        //                    if (rec.TypeCode == 3011)
        //                    {
        //                        OEPlaceHolderAtom placeholder = (OEPlaceHolderAtom)rec;
        //                        if (placeholder != null)
        //                        {
        //                            if (placeholder.PlacementId == PlaceholderEnum.MasterDate)
        //                            {
        //                                stm.Apply(shapecontainer, "");
        //                            }
        //                        }
        //                    }
        //                }
        //            }
        //        }
        //    }
        //  }
        //}
        if (header)
        {
            String s = "";
        }
         
        if (footer)
            for (Note master : this._ctx.getPpt().NotesMasterRecords)
            {
                CSList<ShapeContainer> shapes = master.allChildrenWithType().get(0).<DrawingContainer>AllChildrenWithType()[0].<GroupContainer>AllChildrenWithType()[0].<ShapeContainer>AllChildrenWithType();
                for (ShapeContainer shapecontainer : shapes)
                {
                    for (Object __dummyForeachVar3 : shapecontainer.allChildrenWithType())
                    {
                        ClientData data = (ClientData)__dummyForeachVar3;
                        System.IO.MemoryStream ms = new System.IO.MemoryStream(data.bytes);
                        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                        if (rec.TypeCode == 3011)
                        {
                            OEPlaceHolderAtom placeholder = (OEPlaceHolderAtom)rec;
                            if (placeholder != null)
                            {
                                if (placeholder.PlacementId == PlaceholderEnum.MasterFooter)
                                {
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


