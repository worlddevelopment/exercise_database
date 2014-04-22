//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:14 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormFieldData;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormFieldData.FormFieldType;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormFieldData.TextboxType;
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
public class FormFieldDataMapping  extends AbstractOpenXmlMapping implements IMapping<FormFieldData>
{
    private static final int UNDEFINED_RESULT = 25;
    public FormFieldDataMapping(XmlWriter writer) throws Exception {
        super(writer);
    }

    public void apply(FormFieldData ffd) throws Exception {
        _writer.WriteStartElement("w", "ffData", OpenXmlNamespaces.WordprocessingML);
        //name
        _writer.WriteStartElement("w", "name", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.xstzName);
        _writer.writeEndElement();
        //calcOnExit
        _writer.WriteStartElement("w", "calcOnExit", OpenXmlNamespaces.WordprocessingML);
        if (ffd.fRecalc)
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "1");
        else
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "0"); 
        _writer.writeEndElement();
        //entry macro
        if (ffd.xstzEntryMcr != null && ffd.xstzEntryMcr.length() > 0)
        {
            _writer.WriteStartElement("w", "entryMacro", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.xstzEntryMcr);
            _writer.writeEndElement();
        }
         
        //exit macro
        if (ffd.xstzExitMcr != null && ffd.xstzExitMcr.length() > 0)
        {
            _writer.WriteStartElement("w", "exitMacro", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.xstzExitMcr);
            _writer.writeEndElement();
        }
         
        //help text
        if (ffd.xstzHelpText != null && ffd.xstzHelpText.length() > 0)
        {
            _writer.WriteStartElement("w", "helpText", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "text");
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.xstzHelpText);
            _writer.writeEndElement();
        }
         
        //status text
        if (ffd.xstzStatText != null && ffd.xstzStatText.length() > 0)
        {
            _writer.WriteStartElement("w", "statusText", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "text");
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.xstzStatText);
            _writer.writeEndElement();
        }
         
        //start custom properties
        switch(ffd.iType)
        {
            case iTypeText: 
                _writer.WriteStartElement("w", "textInput", OpenXmlNamespaces.WordprocessingML);
                //type
                if (ffd.iTypeTxt != TextboxType.regular)
                {
                    _writer.WriteStartElement("w", "type", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.iTypeTxt.toString());
                    _writer.writeEndElement();
                }
                 
                //length
                if (ffd.cch > 0)
                {
                    _writer.WriteStartElement("w", "maxLength", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.cch.toString());
                    _writer.writeEndElement();
                }
                 
                //textformat
                if (ffd.xstzTextFormat != null && ffd.xstzTextFormat.length() > 0)
                {
                    _writer.WriteStartElement("w", "format", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.xstzTextFormat);
                    _writer.writeEndElement();
                }
                 
                //default text
                if (ffd.xstzTextDef != null && ffd.xstzTextDef.length() > 0)
                {
                    _writer.WriteStartElement("w", "default", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.xstzTextDef);
                    _writer.writeEndElement();
                }
                 
                break;
            case iTypeChck: 
                _writer.WriteStartElement("w", "checkBox", OpenXmlNamespaces.WordprocessingML);
                //checked <w:checked w:val="0"/>
                if (ffd.iRes != UNDEFINED_RESULT)
                {
                    _writer.WriteStartElement("w", "checked", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.iRes.toString());
                    _writer.writeEndElement();
                }
                 
                //size
                if (ffd.hps >= 2 && ffd.hps <= 3168)
                {
                    _writer.WriteStartElement("w", "size", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.hps.toString());
                    _writer.writeEndElement();
                }
                else
                {
                    _writer.WriteStartElement("w", "sizeAuto", OpenXmlNamespaces.WordprocessingML);
                    _writer.writeEndElement();
                } 
                //default setting
                _writer.WriteStartElement("w", "default", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.wDef.toString());
                _writer.writeEndElement();
                break;
            case iTypeDrop: 
                _writer.WriteStartElement("w", "ddList", OpenXmlNamespaces.WordprocessingML);
                //selected item
                if (ffd.iRes != UNDEFINED_RESULT)
                {
                    _writer.WriteStartElement("w", "result", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ffd.iRes.toString());
                    _writer.writeEndElement();
                }
                 
                break;
            default: 
                //default
                //_entries
                _writer.WriteStartElement("w", "textInput", OpenXmlNamespaces.WordprocessingML);
                break;
        
        }
        //close custom properties
        _writer.writeEndElement();
        //close ffData
        _writer.writeEndElement();
    }

}


