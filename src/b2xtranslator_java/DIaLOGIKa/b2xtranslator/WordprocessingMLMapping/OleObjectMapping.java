//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:15 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.JavaSupport.Collections.Generic.LCC.CollectionSupport;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PictureDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ExternalRelationship;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageWriter;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.OleObject;
import java.net.URI;

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
public class OleObjectMapping  extends AbstractOpenXmlMapping implements IMapping<OleObject>
{
    ContentPart _targetPart;
    WordDocument _doc;
    PictureDescriptor _pict;
    public OleObjectMapping(XmlWriter writer, WordDocument doc, ContentPart targetPart, PictureDescriptor pict) throws Exception {
        super(writer);
        _targetPart = targetPart;
        _doc = doc;
        _pict = pict;
    }

    public void apply(OleObject ole) throws Exception {
        _writer.WriteStartElement("o", "OLEObject", OpenXmlNamespaces.Office);
        DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType type = DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType.Excel;
        if (StringSupport.equals(ole.ClipboardFormat, "Biff8"))
        {
            type = DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType.Excel;
        }
        else if (StringSupport.equals(ole.ClipboardFormat, "MSWordDoc"))
        {
            type = DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType.Word;
        }
        else if (StringSupport.equals(ole.ClipboardFormat, "MSPresentation"))
        {
            type = DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType.Powerpoint;
        }
        else
        {
            type = DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType.Other;
        }   
        //type
        if (ole.fLinked)
        {
            URI link = new URI(ole.Link);
            ExternalRelationship rel = _targetPart.addExternalRelationship(OpenXmlRelationshipTypes.OleObject,link);
            _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, rel.getId());
            _writer.writeAttributeString("Type", "Link");
            _writer.writeAttributeString("UpdateMode", ole.UpdateMode.toString());
        }
        else
        {
            EmbeddedObjectPart part = _targetPart.addEmbeddedObjectPart(type);
            _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, part.getRelIdToString());
            _writer.writeAttributeString("Type", "Embed");
            //copy the object
            copyEmbeddedObject(ole,part);
        } 
        //ProgID
        _writer.writeAttributeString("ProgID", ole.Program);
        //ShapeId
        _writer.writeAttributeString("ShapeID", _pict.ShapeContainer.hashCode().toString());
        //DrawAspect
        _writer.writeAttributeString("DrawAspect", "Content");
        //ObjectID
        _writer.writeAttributeString("ObjectID", ole.ObjectId);
        _writer.writeEndElement();
    }

    /**
    * Writes the embedded OLE object from the ObjectPool of the binary file to the OpenXml Package.
    * 
    *  @param ole
    */
    private void copyEmbeddedObject(OleObject ole, EmbeddedObjectPart part) throws Exception {
        //create a new storage
        StructuredStorageWriter writer = new StructuredStorageWriter();
        // Word will not open embedded charts if a CLSID is set.
        if (ole.Program.startsWith("Excel.Chart") == false)
        {
            writer.getRootDirectoryEntry().setClsId(ole.ClassId);
        }
         
        for (String oleStream : CollectionSupport.mk(ole.Streams.keySet()))
        {
            //copy the OLE streams from the old storage to the new storage
            writer.getRootDirectoryEntry().addStreamDirectoryEntry(oleStream,ole.Streams.get(oleStream));
        }
        //write the storage to the xml part
        writer.write(part.getStream());
    }

}


