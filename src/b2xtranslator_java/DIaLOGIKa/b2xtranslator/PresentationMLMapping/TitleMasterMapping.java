//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:21 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideLayoutPart;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PPDrawing;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
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
public class TitleMasterMapping  extends PresentationMapping<RegularContainer> 
{
    public TitleMasterMapping(ConversionContext ctx, SlideLayoutPart part) throws Exception {
        super(ctx, part);
    }

    public void apply(RegularContainer slide) throws Exception {
        TraceLogger.debugInternal("TitleMasterMapping.Apply");
        // Start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("p", "sldLayout", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("showMasterSp", "0");
        _writer.writeAttributeString("type", "title");
        _writer.writeAttributeString("preserve", "1");
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "a", null, OpenXmlNamespaces.DrawingML);
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "r", null, OpenXmlNamespaces.Relationships);
        _writer.WriteStartElement("p", "cSld", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "spTree", OpenXmlNamespaces.PresentationML);
        ShapeTreeMapping stm = new ShapeTreeMapping(_ctx,_writer);
        stm.parentSlideMapping = this;
        stm.Apply(slide.firstChildWithType());
        _writer.writeEndElement();
        _writer.writeEndElement();
        // End the document
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

}


