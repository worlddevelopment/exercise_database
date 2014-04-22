//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:09 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DocumentProperties;
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
public class ApplicationPropertiesMapping  extends AbstractOpenXmlMapping implements IMapping<DocumentProperties>
{
    public ApplicationPropertiesMapping(AppPropertiesPart appPart, XmlWriterSettings xws) throws Exception {
        super(XmlWriter.Create(appPart.getStream(), xws));
    }

    public void apply(DocumentProperties dop) throws Exception {
        //start Properties
        _writer.WriteStartElement("w", "Properties", OpenXmlNamespaces.WordprocessingML);
        //Application
        //AppVersion
        //Company
        //DigSig
        //DocSecurity
        //HeadingPairs
        //HiddenSlides
        //HLinks
        //HyperlinkBase
        //HyperlinksChanged
        //LinksUpToDate
        //Manager
        //MMClips
        //Notes
        //PresentationFormat
        //ScaleCrop
        //SharedDoc
        //Slides
        //Template
        //TitlesOfParts
        //TotalTime
        //WordCount statistics
        //CharactersWithSpaces
        _writer.writeStartElement("CharactersWithSpaces");
        _writer.writeString(String.valueOf(dop.cChWS));
        _writer.writeEndElement();
        //Characters
        _writer.writeStartElement("Characters");
        _writer.writeString(String.valueOf(dop.cCh));
        _writer.writeEndElement();
        //Lines
        _writer.writeStartElement("Lines");
        _writer.writeString(String.valueOf(dop.cLines));
        _writer.writeEndElement();
        //Pages
        _writer.writeStartElement("Pages");
        _writer.writeString(String.valueOf(dop.cPg));
        _writer.writeEndElement();
        //Paragraphs
        _writer.writeStartElement("Paragraphs");
        _writer.writeString(String.valueOf(dop.cParas));
        _writer.writeEndElement();
        //Words
        _writer.writeStartElement("Words");
        _writer.writeString(String.valueOf(dop.cWords));
        _writer.writeEndElement();
        //end Properties
        _writer.writeEndElement();
    }

}


