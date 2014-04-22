//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import CS2JNet.System.Text.EncodingSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlWriter;
import java.io.InputStream;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
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
public abstract class OpenXmlPart  extends OpenXmlPartContainer 
{
    protected int _relId = 0;
    protected int _partIndex = 0;
    protected MemoryStream _stream = new MemoryStream();
    protected XmlWriter _xmlWriter;
    public OpenXmlPart(OpenXmlPartContainer parent, int partIndex) throws Exception {
        _parent = parent;
        _partIndex = partIndex;
        _stream = new MemoryStream();
        XmlWriterSettings xws = new XmlWriterSettings();
        xws.OmitXmlDeclaration = false;
        xws.CloseOutput = false;
        xws.Encoding = EncodingSupport.GetEncoder("UTF-8");
        xws.Indent = true;
        xws.ConformanceLevel = ConformanceLevel.Document;
        _xmlWriter = getXmlWriter().Create(_stream, xws);
    }

    public String getTargetExt() throws Exception {
        return ".xml";
    }

    public abstract String getContentType() throws Exception ;

    public abstract String getRelationshipType() throws Exception ;

    public boolean getHasDefaultContentType() throws Exception {
        return false;
    }

    public InputStream getStream() throws Exception {
        _stream.Seek(0, SeekOrigin.Begin);
        return _stream;
    }

    public XmlWriter getXmlWriter() throws Exception {
        return _xmlWriter;
    }

    public int getRelId() throws Exception {
        return _relId;
    }

    public void setRelId(int value) throws Exception {
        _relId = value;
    }

    public String getRelIdToString() throws Exception {
        return REL_PREFIX + String.valueOf(_relId);
    }

    protected int getPartIndex() throws Exception {
        return _partIndex;
    }

    public OpenXmlPackage getPackage() throws Exception {
        OpenXmlPartContainer partContainer = this.getParent();
        while (partContainer.getParent() != null)
        {
            partContainer = partContainer.getParent();
        }
        return partContainer instanceof OpenXmlPackage ? (OpenXmlPackage)partContainer : (OpenXmlPackage)null;
    }

    public void writePart(OpenXmlWriter writer) throws Exception {
        for (OpenXmlPart part : this.getParts())
        {
            part.writePart(writer);
        }
        writer.addPart(this.getTargetFullName());
        writer.write(this.getStream());
        this.writeRelationshipPart(writer);
    }

}


