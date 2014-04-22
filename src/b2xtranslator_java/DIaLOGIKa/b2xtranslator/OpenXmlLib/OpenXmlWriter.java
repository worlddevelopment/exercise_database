//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import CS2JNet.System.NotImplementedException;
import CS2JNet.System.Text.EncodingSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipFactory;
import DIaLOGIKa.b2xtranslator.ZipUtils.ZipWriter;
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
public class OpenXmlWriter  extends XmlWriter 
{
    protected XmlWriter _delegateWriter;
    protected XmlWriterSettings _delegateWriterSettings = new XmlWriterSettings();
    protected ZipWriter _zipOutputStream;
    public OpenXmlWriter() throws Exception {
        XmlWriterSettings _delegateWriterSettings = new XmlWriterSettings();
        _delegateWriterSettings.OmitXmlDeclaration = false;
        _delegateWriterSettings.CloseOutput = false;
        _delegateWriterSettings.Encoding = EncodingSupport.GetEncoder("UTF-8");
        _delegateWriterSettings.Indent = true;
        _delegateWriterSettings.ConformanceLevel = ConformanceLevel.Document;
    }

    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            this.close();
        }
         
        super.Dispose(disposing);
    }

    public void open(String fileName) throws Exception {
        if (_zipOutputStream != null || _delegateWriter != null)
        {
            this.close();
        }
         
        _zipOutputStream = ZipFactory.createArchive(fileName);
    }

    public void close() throws Exception {
        // close streams
        if (_delegateWriter != null)
        {
            _delegateWriter.Close();
            _delegateWriter = null;
        }
         
        if (_zipOutputStream != null)
        {
            _zipOutputStream.close();
            _zipOutputStream.Dispose();
            _zipOutputStream = null;
        }
         
    }

    public void addPart(String fullName) throws Exception {
        if (_delegateWriter != null)
        {
            _delegateWriter.Close();
            _delegateWriter = null;
        }
         
        // the path separator in the package should be a forward slash
        _zipOutputStream.addEntry(fullName.replace('\\', '/'));
    }

    protected XmlWriter getXmlWriter() throws Exception {
        if (_delegateWriter == null)
        {
            _delegateWriter = getXmlWriter().Create(_zipOutputStream, _delegateWriterSettings);
        }
         
        return _delegateWriter;
    }

    public void writeRawBytes(byte[] buffer, int index, int count) throws Exception {
        _zipOutputStream.write(buffer,index,count);
    }

    public void write(InputStream stream) throws Exception {
        ;
        byte[] buffer = new byte[blockSize];
        int bytesRead;
        while ((bytesRead = stream.read(buffer,0,blockSize)) > 0)
        {
            _zipOutputStream.write(buffer,0,bytesRead);
        }
    }

    public void writeStartElement(String prefix, String localName, String ns) throws Exception {
        this.getXmlWriter().WriteStartElement(prefix, localName, ns);
    }

    public void writeEndElement() throws Exception {
        this.getXmlWriter().writeEndElement();
    }

    public void writeStartAttribute(String prefix, String localName, String ns) throws Exception {
        this.getXmlWriter().WriteStartAttribute(prefix, localName, ns);
    }

    public void writeAttributeValue(String prefix, String localName, String ns, String value) throws Exception {
        this.getXmlWriter().WriteAttributeString(prefix, localName, ns, value);
    }

    public void writeEndAttribute() throws Exception {
        this.getXmlWriter().WriteEndAttribute();
    }

    public void writeString(String text) throws Exception {
        this.getXmlWriter().writeString(text);
    }

    public void writeFullEndElement() throws Exception {
        this.getXmlWriter().WriteFullEndElement();
    }

    public void writeCData(String s) throws Exception {
        this.getXmlWriter().WriteCData(s);
    }

    public void writeComment(String s) throws Exception {
        this.getXmlWriter().WriteComment(s);
    }

    public void writeProcessingInstruction(String name, String text) throws Exception {
        this.getXmlWriter().WriteProcessingInstruction(name, text);
    }

    public void writeEntityRef(String name) throws Exception {
        this.getXmlWriter().WriteEntityRef(name);
    }

    public void writeCharEntity(char c) throws Exception {
        this.getXmlWriter().WriteCharEntity(c);
    }

    public void writeWhitespace(String s) throws Exception {
        this.getXmlWriter().WriteWhitespace(s);
    }

    public void writeSurrogateCharEntity(char lowChar, char highChar) throws Exception {
        this.getXmlWriter().WriteSurrogateCharEntity(lowChar, highChar);
    }

    public void writeChars(char[] buffer, int index, int count) throws Exception {
        this.getXmlWriter().WriteChars(buffer, index, count);
    }

    public void writeRaw(char[] buffer, int index, int count) throws Exception {
        this.getXmlWriter().WriteRaw(buffer, index, count);
    }

    public void writeRaw(String data) throws Exception {
        this.getXmlWriter().WriteRaw(data);
    }

    public void writeBase64(byte[] buffer, int index, int count) throws Exception {
        this.getXmlWriter().WriteBase64(buffer, index, count);
    }

    public WriteState getWriteState() throws Exception {
        return this.getXmlWriter().WriteState;
    }

    public void flush() throws Exception {
        this.getXmlWriter().Flush();
    }

    public String lookupPrefix(String ns) throws Exception {
        return this.getXmlWriter().LookupPrefix(ns);
    }

    public void writeDocType(String name, String pubid, String sysid, String subset) throws Exception {
        throw new NotImplementedException();
    }

    public void writeEndDocument() throws Exception {
        this.getXmlWriter().WriteEndDocument();
    }

    public void writeStartDocument(boolean standalone) throws Exception {
        this.getXmlWriter().WriteStartDocument(standalone);
    }

    public void writeStartDocument() throws Exception {
        this.getXmlWriter().WriteStartDocument();
    }

}


