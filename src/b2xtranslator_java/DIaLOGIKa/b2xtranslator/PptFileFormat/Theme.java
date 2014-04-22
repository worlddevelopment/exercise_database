//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlDocument;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlNode;
import CS2JNet.System.Xml.XmlNodeList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.XmlContainer;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipReader;
import java.io.File;
import java.io.InputStream;

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
public class Theme  extends XmlContainer 
{
    public Theme(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
    }

    /**
    * Method that extracts the actual XmlElement that will be used as this XmlContainer's
    * XmlDocumentElement based on the relations and a ZipReader for the OOXML package.
    * 
    * The default implementation simply returns the root of the first referenced part if
    * there is only one part.
    * 
    * Override this in subclasses to implement behaviour for more complex cases.
    * 
    *  @param zipReader ZipReader for reading from the OOXML package
    *  @param rootRels List of Relationship nodes belonging to root part
    *  @return The XmlElement that will become this record's XmlDocumentElement
    */
    protected XmlElement extractDocumentElement(ZipReader zipReader, XmlNodeList rootRels) throws Exception {
        if (rootRels.size() != 1)
            throw new Exception("Expected actly one Relationship in Theme OOXML doc");
         
        String managerPath = rootRels.get(0).getAttributes().get("Target").getValue();
        String managerDirectory = (new File(managerPath)).getParent().replace("\\", "/");
        XmlNodeList managerRels;
        try
        {
            managerRels = getRelations(zipReader,managerPath);
        }
        catch (Exception __dummyCatchVar0)
        {
            this.XmlDocumentElement = null;
            return null;
        }

        if (managerRels.size() != 1)
            throw new Exception("Expected actly one Relationship for Theme manager");
         
        String partPath = String.format(StringSupport.CSFmtStrToJFmtStr("{0}/{1}"),managerDirectory,managerRels.get(0).getAttributes().get("Target").getValue());
        InputStream partStream = zipReader.getEntry(partPath);
        XmlDocument partDoc = new XmlDocument();
        partDoc.load(partStream);
        XmlNode e = partDoc.getDocumentElement();
        RefSupport<XmlNode> refVar___0 = new RefSupport<XmlNode>(e);
        DIaLOGIKa.b2xtranslator.Tools.Utils.replaceOutdatedNamespaces(refVar___0);
        e = refVar___0.getValue();
        return (XmlElement)e;
    }

}


