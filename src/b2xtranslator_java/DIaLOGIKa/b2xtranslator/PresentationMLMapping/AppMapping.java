//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:02 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.AppPropertiesPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;

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
public class AppMapping  extends AbstractOpenXmlMapping implements IMapping<IVisitable>
{
    public AppMapping(AppPropertiesPart appPart, XmlWriterSettings xws) throws Exception {
        super(XmlWriter.Create(appPart.getStream(), xws));
    }

    public void apply(IVisitable x) throws Exception {
        // Start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("Properties", "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties");
        // Force declaration of these namespaces at document start
        //_writer.WriteAttributeString("xmlns", null, null, OpenXmlRelationshipTypes.ExtendedProperties);
        _writer.WriteAttributeString("xmlns", "vt", null, OpenXmlNamespaces.docPropsVTypes);
        //TotalTime
        _writer.WriteElementString("TotalTime", "0");
        //Words
        _writer.WriteElementString("Words", "0");
        //PresentationFormat
        _writer.WriteElementString("PresentationFormat", "Custom");
        //Paragraphs
        _writer.WriteElementString("Paragraphs", "0");
        //Slides
        _writer.WriteElementString("Slides", "0");
        //Notes
        _writer.WriteElementString("Notes", "0");
        //HiddenSlides
        _writer.WriteElementString("HiddenSlides", "0");
        //MMClips
        _writer.WriteElementString("MMClips", "0");
        //ScaleCrop
        _writer.WriteElementString("ScaleCrop", "false");
        //HeadingPairs
        //TitlesOfParts
        //LinksUpToDate
        _writer.WriteElementString("LinksUpToDate", "false");
        //SharedDoc
        _writer.WriteElementString("SharedDoc", "false");
        //HyperlinksChanged
        _writer.WriteElementString("HyperlinksChanged", "false");
        //AppVersion
        _writer.WriteElementString("AppVersion", "12.0000");
        // End the document
        _writer.writeEndElement();
        //Properties
        _writer.WriteEndDocument();
        _writer.Flush();
    }

}


