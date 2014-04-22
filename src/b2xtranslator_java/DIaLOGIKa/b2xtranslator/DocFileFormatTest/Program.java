//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:55 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormatTest;

import DIaLOGIKa.b2xtranslator.DocFileFormatTest.Program;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.PresentationDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.PresentationPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlidePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.MainDocumentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.WordprocessingDocument;
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
public class Program   
{
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    static void Main(String[] args) throws Exception {
        WordprocessingDocument doc = WordprocessingDocument.create("C:\\tmp\\testOpenXmlLib.docx",DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Document);
        MainDocumentPart part = doc.getMainDocumentPart();
        ;
        InputStream stream = part.getStream();
        byte[] buf = (new UTF8Encoding()).GetBytes(docXml);
        stream.write(buf,0,buf.length);
        doc.close();
        PresentationDocument presentation = PresentationDocument.Create("C:\\tmp\\testOpenXmlLib.pptx", DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Document);
        PresentationPart presentationPart = presentation.getPresentationPart();
        SlidePart slide = presentationPart.addSlidePart();
        String presentationXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + 
        "    <p:presentation xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" \r\n" + 
        "        xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" \r\n" + 
        "        xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\" saveSubsetFonts=\"1\">\r\n" + 
        "  <p:sldIdLst>\r\n" + 
        "    <p:sldId id=\"256\" r:id=\"" + slide.RelIdToString + "\"/>\r\n" + 
        "  </p:sldIdLst>\r\n" + 
        "</p:presentation>";
        stream = presentationPart.GetStream();
        buf = (new UTF8Encoding()).GetBytes(presentationXml);
        stream.write(buf,0,buf.length);
        String slideXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + 
        "<p:sld xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">\r\n" + 
        "</p:sld>";
        stream = slide.GetStream();
        buf = (new UTF8Encoding()).GetBytes(slideXml);
        stream.write(buf,0,buf.length);
        presentation.Close();
    }

}


