//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:05 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlAttribute;
import CS2JNet.System.Xml.XmlNode;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DrawingContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shape;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideLayoutPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideMasterPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ThemePart;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ColorMappingAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ColorSchemeAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PPDrawing;
import DIaLOGIKa.b2xtranslator.PptFileFormat.RoundTripContentMasterInfo12;
import DIaLOGIKa.b2xtranslator.PptFileFormat.RoundTripOArtTextStyles12;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Theme;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ColorSchemeMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.FillMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.MasterLayoutManager;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.PresentationMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ShapeTreeMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.TextMasterStyleMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
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
public class MasterMapping  extends PresentationMapping<RegularContainer> 
{
    public SlideMasterPart MasterPart;
    protected Slide Master;
    protected long MasterId;
    protected MasterLayoutManager LayoutManager;
    public MasterMapping(ConversionContext ctx) throws Exception {
        super(ctx, ctx.getPptx().getPresentationPart().addSlideMasterPart());
        this.MasterPart = (SlideMasterPart)this.targetPart;
    }

    public void apply(RegularContainer pmaster) throws Exception {
        Slide master = (Slide)pmaster;
        TraceLogger.debugInternal("MasterMapping.Apply");
        long masterId = master.PersistAtom.SlideId;
        _ctx.registerMasterMapping(masterId,this);
        this.Master = master;
        this.MasterId = master.PersistAtom.SlideId;
        this.LayoutManager = _ctx.getOrCreateLayoutManagerByMasterId(this.MasterId);
        // Add PPT2007 roundtrip slide layouts
        CSList<RoundTripContentMasterInfo12> rtSlideLayouts = this.Master.allChildrenWithType();
        for (RoundTripContentMasterInfo12 slideLayout : rtSlideLayouts)
        {
            SlideLayoutPart layoutPart = this.LayoutManager.addLayoutPartWithInstanceId(slideLayout.Instance);
            XmlNode e = slideLayout.XmlDocumentElement;
            XmlNamespaceManager nsm = new XmlNamespaceManager(new NameTable());
            nsm.AddNamespace("a", OpenXmlNamespaces.DrawingML);
            for (Object __dummyForeachVar0 : e.SelectNodes("//a:buBlip", nsm))
            {
                //for the moment remove blips that reference pictures
                XmlNode bublip = (XmlNode)__dummyForeachVar0;
                bublip.getParentNode().removeChild(bublip);
            }
            RefSupport<XmlNode> refVar___0 = new RefSupport<XmlNode>(e);
            DIaLOGIKa.b2xtranslator.Tools.Utils.replaceOutdatedNamespaces(refVar___0);
            e = refVar___0.getValue();
            e.WriteTo(layoutPart.XmlWriter);
            layoutPart.XmlWriter.Flush();
        }
    }

    public void write() throws Exception {
        // Start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("p", "sldMaster", OpenXmlNamespaces.PresentationML);
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "a", null, OpenXmlNamespaces.DrawingML);
        _writer.WriteAttributeString("xmlns", "r", null, OpenXmlNamespaces.Relationships);
        _writer.WriteStartElement("p", "cSld", OpenXmlNamespaces.PresentationML);
        ShapeContainer sc = this.Master.firstChildWithType().<DrawingContainer>FirstChildWithType().<ShapeContainer>FirstChildWithType();
        if (sc != null)
        {
            Shape sh = sc.firstChildWithType();
            ShapeOptions so = sc.firstChildWithType();
            if (so.OptionsByID.containsKey(PropertyId.fillType))
            {
                _writer.WriteStartElement("p", "bg", OpenXmlNamespaces.PresentationML);
                _writer.WriteStartElement("p", "bgPr", OpenXmlNamespaces.PresentationML);
                new FillMapping(_ctx,_writer,this).apply(so);
                _writer.WriteElementString("a", "effectLst", OpenXmlNamespaces.DrawingML, "");
                _writer.writeEndElement();
                //p:bgPr
                _writer.writeEndElement();
            }
            else //p:bg
            if (so.OptionsByID.containsKey(PropertyId.fillColor))
            {
                String colorval;
                if (so.OptionsByID.containsKey(PropertyId.fillColor))
                {
                    colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillColor).op,this.Master,so);
                }
                else
                {
                    colorval = "000000";
                } 
                //TODO: find out which color to use in this case
                _writer.WriteStartElement("p", "bg", OpenXmlNamespaces.PresentationML);
                _writer.WriteStartElement("p", "bgPr", OpenXmlNamespaces.PresentationML);
                _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", colorval);
                if (so.OptionsByID.containsKey(PropertyId.fillOpacity) && so.OptionsByID.get(PropertyId.fillOpacity).op != 65536)
                {
                    _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf(Math.round(((double)so.OptionsByID.get(PropertyId.fillOpacity).op / 65536 * 100000))));
                    //we need the percentage of the opacity (65536 means 100%)
                    _writer.writeEndElement();
                }
                 
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.WriteElementString("a", "effectLst", OpenXmlNamespaces.DrawingML, "");
                _writer.writeEndElement();
                //p:bgPr
                _writer.writeEndElement();
            }
              
        }
         
        //p:bg
        _writer.WriteStartElement("p", "spTree", OpenXmlNamespaces.PresentationML);
        ShapeTreeMapping stm = new ShapeTreeMapping(_ctx,_writer);
        stm.parentSlideMapping = this;
        stm.Apply(this.Master.firstChildWithType());
        _writer.writeEndElement();
        _writer.writeEndElement();
        // Write clrMap
        ColorMappingAtom clrMap = this.Master.firstChildWithType();
        if (clrMap != null)
        {
            // clrMap from ColorMappingAtom wrongly uses namespace DrawingML
            _writer.WriteStartElement("p", "clrMap", OpenXmlNamespaces.PresentationML);
            for (Object __dummyForeachVar2 : clrMap.XmlDocumentElement.getAttributes())
            {
                XmlAttribute attr = (XmlAttribute)__dummyForeachVar2;
                if (!StringSupport.equals(attr.Prefix, "xmlns"))
                    _writer.writeAttributeString(attr.getLocalName(), attr.getValue());
                 
            }
            _writer.writeEndElement();
        }
        else
        {
            // In absence of ColorMappingAtom write default clrMap
            Utils.getDefaultDocument("clrMap").WriteTo(_writer);
        } 
        // Write slide layout part id list
        _writer.WriteStartElement("p", "sldLayoutIdLst", OpenXmlNamespaces.PresentationML);
        CSList<SlideLayoutPart> layoutParts = this.LayoutManager.getAllLayoutParts();
        // Master must have at least one SlideLayout or RepairDialog will appear
        if (layoutParts.size() == 0)
        {
            SlideLayoutPart layoutPart = this.LayoutManager.GetOrCreateLayoutPartByLayoutType(0, null);
            layoutParts.add(layoutPart);
        }
         
        for (SlideLayoutPart slideLayoutPart : layoutParts)
        {
            _writer.WriteStartElement("p", "sldLayoutId", OpenXmlNamespaces.PresentationML);
            _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, slideLayoutPart.RelIdToString);
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        // Write txStyles
        RoundTripOArtTextStyles12 roundTripTxStyles = this.Master.firstChildWithType();
        if (false & roundTripTxStyles != null)
        {
            roundTripTxStyles.XmlDocumentElement.WriteTo(_writer);
        }
        else
        {
            //throw new NotImplementedException("Write txStyles in case of PPT without roundTripTxStyles"); // TODO (pre PP2007)
            //XmlDocument slideLayoutDoc = Utils.GetDefaultDocument("txStyles");
            //slideLayoutDoc.WriteTo(_writer);
            new TextMasterStyleMapping(_ctx,_writer,this).apply(this.Master);
        } 
        // Write theme
        //
        // Note: We need to create a new theme part for each master,
        // even if it they have the same content.
        //
        // Otherwise PPT will complain about the structure of the file.
        ThemePart themePart = _ctx.getPptx().getPresentationPart().addThemePart();
        XmlNode xmlDoc;
        Theme theme = this.Master.firstChildWithType();
        if (theme != null)
        {
            xmlDoc = theme.XmlDocumentElement;
            //Tools.Utils.recursiveReplaceOutdatedNamespaces(ref xmlDoc);
            xmlDoc.WriteTo(themePart.getXmlWriter());
        }
        else
        {
            CSList<ColorSchemeAtom> schemes = this.Master.allChildrenWithType();
            if (schemes.size() > 0)
            {
                new ColorSchemeMapping(_ctx,themePart.getXmlWriter()).apply(schemes);
            }
            else
            {
                // In absence of Theme record use default theme
                xmlDoc = Utils.getDefaultDocument("theme");
                xmlDoc.WriteTo(themePart.getXmlWriter());
            } 
        } 
        themePart.getXmlWriter().Flush();
        this.MasterPart.ReferencePart(themePart);
        // End the document
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

}


