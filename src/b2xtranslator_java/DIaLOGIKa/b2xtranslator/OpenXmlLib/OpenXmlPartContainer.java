//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Collections.LCC.IEnumerable;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ExternalRelationship;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlWriter;
import java.io.File;
import java.net.URI;

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
public abstract class OpenXmlPartContainer   
{
    protected static final String REL_PREFIX = "rId";
    protected static final String EXT_PREFIX = "extId";
    protected static final String REL_FOLDER = "_rels";
    protected static final String REL_EXTENSION = ".rels";
    protected CSList<OpenXmlPart> _parts = new CSList<OpenXmlPart>();
    protected CSList<OpenXmlPart> _referencedParts = new CSList<OpenXmlPart>();
    protected CSList<ExternalRelationship> _externalRelationships = new CSList<ExternalRelationship>();
    protected static int _nextRelId = 1;
    protected OpenXmlPartContainer _parent = null;
    public String getTargetName() throws Exception {
        return "";
    }

    public String getTargetExt() throws Exception {
        return "";
    }

    public String getTargetDirectory() throws Exception {
        return "";
    }

    public void setTargetDirectory(String value) throws Exception {
    }

    public String getTargetDirectoryAbsolute() throws Exception {
        // build complete path name from all parent parts
        String path = this.getTargetDirectory();
        OpenXmlPartContainer part = this.getParent();
        while (part != null)
        {
            path = (new File(part.getTargetDirectory(), path)).toString();
            part = part.getParent();
        }
        // resolve path (i.e. resolve "../" within path)
        if (!StringSupport.isNullOrEmpty(path))
        {
            String rootPath = (new File(".")).getCanonicalPath();
            String resolvedPath = (new File(path)).getCanonicalPath();
            if (resolvedPath.startsWith(rootPath))
            {
                path = resolvedPath.substring(rootPath.length() + 1);
            }
             
        }
         
        if (StringSupport.equals(path, "ppt\\slides\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\slideLayouts\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\notesSlides\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\slideMasters\\..\\slideLayouts"))
            return "ppt\\slideLayouts";
         
        if (StringSupport.equals(path, "ppt\\slideMasters\\..\\slideLayouts\\..\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\slides\\..\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\slideMasters\\..\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\notesSlides\\..\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\notesMasters\\..\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\slides\\..\\drawings\\..\\media"))
            return "ppt\\media";
         
        if (StringSupport.equals(path, "ppt\\slides\\..\\embeddings"))
            return "ppt\\embeddings";
         
        if (StringSupport.equals(path, "ppt\\notesSlides\\embeddings"))
            return "ppt\\embeddings";
         
        if (StringSupport.equals(path, "ppt\\slideMasters\\..\\slideLayouts\\..\\embeddings"))
            return "ppt\\embeddings";
         
        if (StringSupport.equals(path, "ppt\\slides\\..\\embeddings"))
            return "ppt\\embeddings";
         
        if (StringSupport.equals(path, "ppt\\slideMasters\\..\\embeddings"))
            return "ppt\\embeddings";
         
        if (StringSupport.equals(path, "ppt\\notesSlides\\..\\embeddings"))
            return "ppt\\embeddings";
         
        if (StringSupport.equals(path, "ppt\\notesMasters\\..\\embeddings"))
            return "ppt\\embeddings";
         
        if (StringSupport.equals(path, "ppt\\slides\\..\\drawings"))
            return "ppt\\drawings";
         
        return path;
    }

    public String getTargetFullName() throws Exception {
        return (new File(this.getTargetDirectoryAbsolute(), this.getTargetName())).toString() + this.getTargetExt();
    }

    public OpenXmlPartContainer getParent() throws Exception {
        return _parent;
    }

    public void setParent(OpenXmlPartContainer value) throws Exception {
        _parent = value;
    }

    protected IEnumerable<OpenXmlPart> getParts() throws Exception {
        return _parts;
    }

    protected IEnumerable<OpenXmlPart> getReferencedParts() throws Exception {
        return _referencedParts;
    }

    protected IEnumerable<ExternalRelationship> getExternalRelationships() throws Exception {
        return _externalRelationships;
    }

    public <T extends OpenXmlPart>T addPart(T part) throws Exception {
        // generate a relId for the part
        part.RelId = _nextRelId++;
        _parts.Add(part);
        if (part.HasDefaultContentType)
        {
            part.Package.AddContentTypeDefault(part.TargetExt.Replace(".", ""), part.ContentType);
        }
        else
        {
            String path = "/" + part.TargetFullName.Replace('\\', '/');
            path = path.replace("/ppt/slideMasters/media/", "/ppt/media/").replace("/ppt/slideMasters/../slideLayouts/media/", "/ppt/media/").replace("/ppt/notesSlides/../media/", "/ppt/media/").replace("/ppt/slides/../drawings/../media", "ppt/media/").replace("/ppt/slides/../drawings", "/ppt/drawings");
            part.Package.AddContentTypeOverride(path, part.ContentType);
        } 
        return part;
    }

    public ExternalRelationship addExternalRelationship(String relationshipType, URI externalUri) throws Exception {
        ExternalRelationship rel = new ExternalRelationship(EXT_PREFIX + String.valueOf((_externalRelationships.size() + 1)),relationshipType,externalUri);
        _externalRelationships.add(rel);
        return rel;
    }

    public ExternalRelationship addExternalRelationship(String relationshipType, String externalUri) throws Exception {
        ExternalRelationship rel = new ExternalRelationship(EXT_PREFIX + String.valueOf((_externalRelationships.size() + 1)),relationshipType,externalUri);
        _externalRelationships.add(rel);
        return rel;
    }

    /**
    * Add a part reference without actually managing the part.
    */
    public <T extends OpenXmlPart>T referencePart(T part) throws Exception {
        // We'll use the existing ID here.
        _referencedParts.Add(part);
        if (part.HasDefaultContentType)
        {
            part.Package.AddContentTypeDefault(part.TargetExt.Replace(".", ""), part.ContentType);
        }
        else
        {
            part.Package.AddContentTypeOverride("/" + part.TargetFullName.Replace('\\', '/'), part.ContentType);
        } 
        return part;
    }

    protected void writeRelationshipPart(OpenXmlWriter writer) throws Exception {
        CSList<OpenXmlPart> allParts = new CSList<OpenXmlPart>();
        allParts.addRange(this.getParts());
        allParts.addRange(this.getReferencedParts());
        // write part relationships
        if (allParts.size() > 0 || _externalRelationships.size() > 0)
        {
            String relFullName = (new File((new File(this.getTargetDirectoryAbsolute(), REL_FOLDER)).toString(), getTargetName() + getTargetExt() + REL_EXTENSION)).toString();
            writer.addPart(relFullName);
            writer.writeStartDocument();
            writer.WriteStartElement("Relationships", OpenXmlNamespaces.RelationsshipsPackage);
            for (ExternalRelationship rel : _externalRelationships)
            {
                writer.WriteStartElement("Relationship", OpenXmlNamespaces.RelationsshipsPackage);
                writer.writeAttributeString("Id", rel.getId());
                writer.writeAttributeString("Type", rel.getRelationshipType());
                if (URI.IsWellFormedUriString(rel.getTarget(), UriKind.RelativeOrAbsolute))
                {
                    if (rel.getTargetUri().isAbsolute())
                    {
                        if (rel.getTargetUri().IsFile)
                        {
                            //reform the URI path for Word
                            //Word does not accept forward slahes in the path of a local file
                            writer.writeAttributeString("Target", "file:///" + rel.getTargetUri().AbsolutePath.Replace("/", "\\"));
                        }
                        else
                        {
                            writer.writeAttributeString("Target", rel.getTarget().toString());
                        } 
                    }
                    else
                    {
                        writer.WriteAttributeString("Target", URI.EscapeUriString(rel.getTarget().toString()));
                    } 
                }
                else
                {
                    writer.WriteAttributeString("Target", URI.EscapeUriString(rel.getTarget()));
                } 
                writer.writeAttributeString("TargetMode", "External");
                writer.writeEndElement();
            }
            for (OpenXmlPart part : allParts)
            {
                writer.WriteStartElement("Relationship", OpenXmlNamespaces.RelationsshipsPackage);
                writer.writeAttributeString("Id", part.getRelIdToString());
                writer.writeAttributeString("Type", part.getRelationshipType());
                // write the target relative to the current part
                writer.writeAttributeString("Target", "/" + part.getTargetFullName().replace('\\', '/'));
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
        }
         
    }

}


