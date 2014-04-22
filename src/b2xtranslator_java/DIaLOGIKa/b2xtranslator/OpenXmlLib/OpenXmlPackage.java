//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import CS2JNet.JavaSupport.Collections.Generic.LCC.CollectionSupport;
import CS2JNet.System.LCC.IDisposable;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.AppPropertiesPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.CorePropertiesPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlContentTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlWriter;
import java.util.HashMap;

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
public abstract class OpenXmlPackage  extends OpenXmlPartContainer implements IDisposable
{
    protected String _fileName;
    protected HashMap<String,String> _defaultTypes = new HashMap<String,String>();
    protected HashMap<String,String> _partOverrides = new HashMap<String,String>();
    protected CorePropertiesPart _coreFilePropertiesPart;
    protected AppPropertiesPart _appPropertiesPart;
    protected int _imageCounter;
    protected int _vmlCounter;
    protected int _oleCounter;
    public enum DocumentType
    {
        Document,
        MacroEnabledDocument,
        MacroEnabledTemplate,
        Template
    }
    protected OpenXmlPackage(String fileName) throws Exception {
        _fileName = fileName;
        _defaultTypes.put("rels", OpenXmlContentTypes.Relationships);
        _defaultTypes.put("xml", OpenXmlContentTypes.Xml);
        _defaultTypes.put("bin", OpenXmlContentTypes.OleObject);
        _defaultTypes.put("vml", OpenXmlContentTypes.Vml);
        _defaultTypes.put("emf", OpenXmlContentTypes.Emf);
        _defaultTypes.put("wmf", OpenXmlContentTypes.Wmf);
    }

    public void dispose() throws Exception {
        this.close();
    }

    public void close() throws Exception {
        // serialize the package on closing
        OpenXmlWriter writer = new OpenXmlWriter();
        writer.open(this.getFileName());
        this.writePackage(writer);
        writer.close();
    }

    public String getFileName() throws Exception {
        return _fileName;
    }

    public void setFileName(String value) throws Exception {
        _fileName = value;
    }

    public CorePropertiesPart getCoreFilePropertiesPart() throws Exception {
        return _coreFilePropertiesPart;
    }

    public void setCoreFilePropertiesPart(CorePropertiesPart value) throws Exception {
        _coreFilePropertiesPart = value;
    }

    public CorePropertiesPart addCoreFilePropertiesPart() throws Exception {
        this.setCoreFilePropertiesPart(new CorePropertiesPart(this));
        return this.AddPart(this.getCoreFilePropertiesPart());
    }

    public AppPropertiesPart getAppPropertiesPart() throws Exception {
        return _appPropertiesPart;
    }

    public void setAppPropertiesPart(AppPropertiesPart value) throws Exception {
        _appPropertiesPart = value;
    }

    public AppPropertiesPart addAppPropertiesPart() throws Exception {
        this.setAppPropertiesPart(new AppPropertiesPart(this));
        return this.AddPart(this.getAppPropertiesPart());
    }

    public void addContentTypeDefault(String extension, String contentType) throws Exception {
        if (!_defaultTypes.containsKey(extension))
            _defaultTypes.put(extension, contentType);
         
    }

    public void addContentTypeOverride(String partNameAbsolute, String contentType) throws Exception {
        if (!_partOverrides.containsKey(partNameAbsolute))
            _partOverrides.put(partNameAbsolute, contentType);
         
    }

    public int getNextImageId() throws Exception {
        _imageCounter++;
        return _imageCounter;
    }

    public int getNextVmlId() throws Exception {
        _vmlCounter++;
        return _vmlCounter;
    }

    public int getNextOleId() throws Exception {
        _oleCounter++;
        return _oleCounter;
    }

    protected void writePackage(OpenXmlWriter writer) throws Exception {
        for (OpenXmlPart part : this.getParts())
        {
            part.writePart(writer);
        }
        this.writeRelationshipPart(writer);
        // write content types
        writer.addPart("[Content_Types].xml");
        writer.writeStartDocument();
        writer.WriteStartElement("Types", OpenXmlNamespaces.ContentTypes);
        for (String extension : CollectionSupport.mk(_defaultTypes.keySet()))
        {
            writer.WriteStartElement("Default", OpenXmlNamespaces.ContentTypes);
            writer.writeAttributeString("Extension", extension);
            writer.writeAttributeString("ContentType", _defaultTypes.get(extension));
            writer.writeEndElement();
        }
        for (String partName : CollectionSupport.mk(_partOverrides.keySet()))
        {
            writer.WriteStartElement("Override", OpenXmlNamespaces.ContentTypes);
            writer.writeAttributeString("PartName", partName);
            writer.writeAttributeString("ContentType", _partOverrides.get(partName));
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.writeEndDocument();
    }

}


