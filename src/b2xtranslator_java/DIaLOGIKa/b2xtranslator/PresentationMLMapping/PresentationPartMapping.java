//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:07 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.DocumentAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.DocumentContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Handout;
import DIaLOGIKa.b2xtranslator.PptFileFormat.MainMaster;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Note;
import DIaLOGIKa.b2xtranslator.PptFileFormat.NotesAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PowerpointDocument;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideListWithText;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlidePersistAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextMasterStyleAtom;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.HandoutMasterMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.MasterMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.NoteMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.NotesMasterMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.PresentationMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.SlideMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.TextMasterStyleMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.VbaProjectMapping;

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
public class PresentationPartMapping  extends PresentationMapping<PowerpointDocument> 
{
    public CSList<SlideMapping> SlideMappings = new CSList<SlideMapping>();
    private CSList<NoteMapping> NoteMappings = new CSList<NoteMapping>();
    public PresentationPartMapping(ConversionContext ctx) throws Exception {
        super(ctx, ctx.getPptx().getPresentationPart());
    }

    public void apply(PowerpointDocument ppt) throws Exception {
        DocumentContainer documentRecord = ppt.DocumentRecord;
        // Start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("p", "presentation", OpenXmlNamespaces.PresentationML);
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "r", null, OpenXmlNamespaces.Relationships);
        createMainMasters(ppt);
        createNotesMasters(ppt);
        createHandoutMasters(ppt);
        createVbaProject(ppt);
        createSlides(ppt,documentRecord);
        writeMainMasters(ppt);
        writeSlides(ppt,documentRecord);
        // sldSz and notesSz
        writeSizeInfo(ppt,documentRecord);
        writeDefaultTextStyle(ppt,documentRecord);
        // End the document
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

    private void writeDefaultTextStyle(PowerpointDocument ppt, DocumentContainer documentRecord) throws Exception {
        _writer.WriteStartElement("p", "defaultTextStyle", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("a", "defPPr", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "defRPr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("lang", "en-US");
        _writer.writeEndElement();
        //defRPr
        _writer.writeEndElement();
        //defPPr
        TextMasterStyleAtom defaultStyle = _ctx.getPpt().DocumentRecord.firstChildWithType().<TextMasterStyleAtom>FirstChildWithType();
        TextMasterStyleMapping map = new TextMasterStyleMapping(_ctx,_writer,null);
        for (int i = 0;i < defaultStyle.IndentLevelCount;i++)
        {
            map.writepPr(defaultStyle.CRuns.get(i),defaultStyle.PRuns.get(i),null,i,false,true);
        }
        for (int i = defaultStyle.IndentLevelCount;i < 9;i++)
        {
            map.writepPr(defaultStyle.CRuns.get(0),defaultStyle.PRuns.get(0),null,i,false,true);
        }
        _writer.writeEndElement();
    }

    //defaultTextStyle
    private void writeSizeInfo(PowerpointDocument ppt, DocumentContainer documentRecord) throws Exception {
        DocumentAtom doc = documentRecord.firstChildWithType();
        // Write slide size and type
        writeSlideSizeInfo(doc);
        // Write notes size
        writeNotesSizeInfo(doc);
    }

    private void writeNotesSizeInfo(DocumentAtom doc) throws Exception {
        int notesWidth = Utils.masterCoordToEMU(doc.NotesSize.X);
        int notesHeight = Utils.masterCoordToEMU(doc.NotesSize.Y);
        _writer.WriteStartElement("p", "notesSz", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("cx", String.valueOf(notesWidth));
        _writer.writeAttributeString("cy", String.valueOf(notesHeight));
        _writer.writeEndElement();
    }

    private void writeSlideSizeInfo(DocumentAtom doc) throws Exception {
        int slideWidth = Utils.masterCoordToEMU(doc.SlideSize.X);
        int slideHeight = Utils.masterCoordToEMU(doc.SlideSize.Y);
        String slideType = Utils.slideSizeTypeToXMLValue(doc.SlideSizeType);
        _writer.WriteStartElement("p", "sldSz", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("cx", String.valueOf(slideWidth));
        _writer.writeAttributeString("cy", String.valueOf(slideHeight));
        _writer.writeAttributeString("type", slideType);
        _writer.writeEndElement();
    }

    private void createSlides(PowerpointDocument ppt, DocumentContainer documentRecord) throws Exception {
        for (Object __dummyForeachVar5 : ppt.DocumentRecord.allChildrenWithType())
        {
            SlideListWithText lst = (SlideListWithText)__dummyForeachVar5;
            if (lst.Instance == 0)
            {
                for (SlidePersistAtom at : lst.SlidePersistAtoms)
                {
                    for (Slide slide : ppt.SlideRecords)
                    {
                        if (slide.PersistAtom == at)
                        {
                            SlideMapping sMapping = new SlideMapping(_ctx);
                            sMapping.apply(slide);
                            this.SlideMappings.add(sMapping);
                        }
                         
                    }
                }
            }
             
            boolean found = false;
            if (lst.Instance == 2)
            {
                for (SlidePersistAtom at : lst.SlidePersistAtoms)
                {
                    //notes
                    found = false;
                    for (Note note : ppt.NoteRecords)
                    {
                        if (note.PersistAtom.SlideId == at.SlideId)
                        {
                            NotesAtom a = note.firstChildWithType();
                            for (SlideMapping slideMapping : this.SlideMappings)
                            {
                                if (slideMapping.Slide.PersistAtom.SlideId == a.SlideIdRef)
                                {
                                    NoteMapping nMapping = new NoteMapping(_ctx,slideMapping);
                                    nMapping.apply(note);
                                    this.NoteMappings.add(nMapping);
                                    found = true;
                                }
                                 
                            }
                        }
                         
                    }
                    if (!found)
                    {
                        String s = "";
                    }
                     
                }
            }
             
        }
    }

    private void writeSlides(PowerpointDocument ppt, DocumentContainer documentRecord) throws Exception {
        _writer.WriteStartElement("p", "sldIdLst", OpenXmlNamespaces.PresentationML);
        for (SlideMapping sMapping : this.SlideMappings)
        {
            writeSlide(sMapping);
        }
        _writer.writeEndElement();
    }

    private void writeSlide(SlideMapping sMapping) throws Exception {
        Slide slide = sMapping.Slide;
        _writer.WriteStartElement("p", "sldId", OpenXmlNamespaces.PresentationML);
        SlideAtom slideAtom = slide.firstChildWithType();
        _writer.writeAttributeString("id", String.valueOf(slide.PersistAtom.SlideId));
        _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, sMapping.targetPart.getRelIdToString());
        _writer.writeEndElement();
    }

    private void createVbaProject(PowerpointDocument ppt) throws Exception {
        if (ppt.VbaProject != null)
        {
            ppt.VbaProject.Convert(new VbaProjectMapping(_ctx.getPptx().getPresentationPart().getVbaProjectPart()));
        }
         
    }

    private void createMainMasters(PowerpointDocument ppt) throws Exception {
        for (Slide m : ppt.MainMasterRecords)
        {
            _ctx.getOrCreateMasterMappingByMasterId(m.PersistAtom.SlideId).apply(m);
        }
    }

    private void createNotesMasters(PowerpointDocument ppt) throws Exception {
        for (Note m : ppt.NotesMasterRecords)
        {
            _ctx.GetOrCreateNotesMasterMappingByMasterId(0).Apply(m);
        }
    }

    private void createHandoutMasters(PowerpointDocument ppt) throws Exception {
        for (Handout m : ppt.HandoutMasterRecords)
        {
            _ctx.GetOrCreateHandoutMasterMappingByMasterId(0).Apply(m);
        }
    }

    private void writeMainMasters(PowerpointDocument ppt) throws Exception {
        _writer.WriteStartElement("p", "sldMasterIdLst", OpenXmlNamespaces.PresentationML);
        for (MainMaster m : ppt.MainMasterRecords)
        {
            this.writeMainMaster(ppt,m);
        }
        _writer.writeEndElement();
        writeNoteMaster(ppt);
        writeHandoutMaster(ppt);
    }

    /// <summary>
    /// Writes a slide master.
    ///
    /// A slide master can either be a main master (type MainMaster) or title master (type Slide).
    /// <param name="ppt">PowerpointDocument record</param>
    /// <param name="m">Main master record</param>
    private void writeMainMaster(PowerpointDocument ppt, MainMaster m) throws Exception {
        _writer.WriteStartElement("p", "sldMasterId", OpenXmlNamespaces.PresentationML);
        MasterMapping mapping = _ctx.getOrCreateMasterMappingByMasterId(m.PersistAtom.SlideId);
        mapping.write();
        String relString = mapping.targetPart.getRelIdToString();
        _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, relString);
        _writer.writeEndElement();
    }

    private void writeNoteMaster(PowerpointDocument ppt) throws Exception {
        if (ppt.NotesMasterRecords.size() > 0)
        {
            _writer.WriteStartElement("p", "notesMasterIdLst", OpenXmlNamespaces.PresentationML);
            for (Note m : ppt.NotesMasterRecords)
            {
                this.writeNoteMaster2(ppt,m);
            }
            _writer.writeEndElement();
        }
         
    }

    /// <summary>
    /// Writes a notes master.
    ///
    /// <param name="ppt">PowerpointDocument record</param>
    /// <param name="m">Notes master record</param>
    private void writeNoteMaster2(PowerpointDocument ppt, Note m) throws Exception {
        _writer.WriteStartElement("p", "notesMasterId", OpenXmlNamespaces.PresentationML);
        NotesMasterMapping mapping = _ctx.GetOrCreateNotesMasterMappingByMasterId(0);
        mapping.write();
        String relString = mapping.targetPart.getRelIdToString();
        _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, relString);
        _writer.writeEndElement();
    }

    private void writeHandoutMaster(PowerpointDocument ppt) throws Exception {
        if (ppt.HandoutMasterRecords.size() > 0)
        {
            _writer.WriteStartElement("p", "handoutMasterIdLst", OpenXmlNamespaces.PresentationML);
            for (Handout m : ppt.HandoutMasterRecords)
            {
                this.writeHandoutMaster2(ppt,m);
            }
            _writer.writeEndElement();
        }
         
    }

    /// <summary>
    /// Writes a handout master.
    ///
    /// <param name="ppt">PowerpointDocument record</param>
    /// <param name="m">Handout master record</param>
    private void writeHandoutMaster2(PowerpointDocument ppt, Handout m) throws Exception {
        _writer.WriteStartElement("p", "handoutMasterId", OpenXmlNamespaces.PresentationML);
        HandoutMasterMapping mapping = _ctx.GetOrCreateHandoutMasterMappingByMasterId(0);
        mapping.write();
        String relString = mapping.targetPart.getRelIdToString();
        _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, relString);
        _writer.writeEndElement();
    }

}


