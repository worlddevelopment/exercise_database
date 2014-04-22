//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import CS2JNet.System.IO.FileMode;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlDocument;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlNodeList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.XmlRecord;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipFactory;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipReader;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;

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
/**
* XML containers are containers with a zipped XML payload.
*/
public class XmlContainer  extends XmlRecord 
{
    public XmlContainer(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        // Note: XmlContainers contain the data of a partial "unfinished"
        // OOXML file (.zip based) as their body.
        //
        // I really don't like writing the data to a temp file just to
        // be able to open it via ZipUtils.
        //
        // Possible alternatives:
        // 1) Using System.IO.Compression -- supports inflation, but can't parse Zip header data
        // 2) Modifying zlib + minizlib + ZipLib so I can pass in bytes, possible, but not worth the effort
        String tempPath = Path.GetTempFileName();
        try
        {
            FileStreamSupport fs = new FileStreamSupport(tempPath, FileMode.Create);
            try
            {
                {
                    BinaryWriter tempStream = new BinaryWriter(fs);
                    try
                    {
                        {
                            int count = (int)this.Reader.BaseStream.Length;
                            byte[] bytes = this.Reader.ReadBytes(count);
                            tempStream.Write(bytes);
                            tempStream.Flush();
                            fs.Flush();
                            tempStream.Close();
                            fs.close();
                        }
                    }
                    finally
                    {
                        if (tempStream != null)
                            Disposable.mkDisposable(tempStream).dispose();
                         
                    }
                }
            }
            finally
            {
                if (fs != null)
                    Disposable.mkDisposable(fs).dispose();
                 
            }
            ZipReader zipReader = ZipFactory.openArchive(tempPath);
            try
            {
                {
                    this.XmlDocumentElement = extractDocumentElement(zipReader,getRelations(zipReader,""));
                }
            }
            finally
            {
                if (zipReader != null)
                    Disposable.mkDisposable(zipReader).dispose();
                 
            }
        }
        finally
        {
            try
            {
                (new File(tempPath)).delete();
            }
            catch (IOException __dummyCatchVar0)
            {
            }
        
        }
    }

    /* OK */
    /**
    * Get the relations for the specified part.
    * 
    *  @param zipReader ZipReader for reading from the OOXML package
    *  @param forPartPath Part for which to get relations
    *  @return List of Relationship nodes belonging to forFile
    */
    protected static XmlNodeList getRelations(ZipReader zipReader, String forPartPath) throws Exception {
        String relPath = getRelationPath(forPartPath);
        InputStream relStream = zipReader.getEntry(relPath);
        XmlDocument relDocument = new XmlDocument();
        relDocument.load(relStream);
        XmlNodeList rels = relDocument["Relationships"].GetElementsByTagName("Relationship");
        return rels;
    }

    /**
    * Get the relation path for the specified part.
    * 
    *  @param forPartPath Part for which to get relations
    *  @return Relation path
    */
    protected static String getRelationPath(String forPartPath) throws Exception {
        String directoryPath = "";
        String filePath = "";
        if (forPartPath.length() > 0)
        {
            directoryPath = (new File(forPartPath)).getParent().replace("\\", "/") + "/";
            filePath = (new File(forPartPath)).getName();
        }
         
        String relPath = String.format(StringSupport.CSFmtStrToJFmtStr("{0}_rels/{1}.rels"),directoryPath,filePath);
        return relPath;
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
    *  @param rels List of Relationship nodes belonging to root part
    *  @return The XmlElement that will become this record's XmlDocumentElement
    */
    protected XmlElement extractDocumentElement(ZipReader zipReader, XmlNodeList rels) throws Exception {
        if (rels.size() != 1)
            throw new Exception("Expected actly one Relationship in XmlContainer OOXML doc");
         
        String partPath = rels.get(0).getAttributes().get("Target").getValue();
        InputStream partStream = zipReader.getEntry(partPath);
        XmlDocument partDoc = new XmlDocument();
        partDoc.load(partStream);
        return partDoc.getDocumentElement();
    }

}


