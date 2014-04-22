//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:53 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkBackWallThicknessFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkBaseTimeUnitFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkColorMappingOverride;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkDispBlanksAsFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkEndSurface;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkFloorThicknessFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkFormatCodeFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkHeader;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkHeightPercent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkLogBaseFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkMajorUnitFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkMajorUnitTypeFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkMaxFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkMinFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkMinorUnitFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkMinorUnitTypeFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkNoMultiLvlLbl;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkOverlay;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkPerspectiveFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkPieComboFrom12Frt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkRAngAxOffFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkRotXFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkRotYFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkShowDLblsOverMax;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkSpb;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkStartSurface;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkStyle;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkSymbolFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkThemeOverride;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkTickLabelPositionFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkTickLabelSkipFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkTickMarkSkipFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XmlTkTpb;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import java.util.ArrayList;

/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
public class XmlTkChain   
{
    public byte recordVersion;
    public UInt16 xmltkParent = new UInt16();
    public ArrayList chainRecords = new ArrayList();
    public XmlTkChain(IStreamReader reader) throws Exception {
        this.recordVersion = reader.readByte();
        //unused
        reader.readByte();
        this.xmltkParent = reader.readUInt16();
        long pos;
        XmlTkHeader h;
        UInt16 __dummyScrutVar0 = this.xmltkParent;
        if (__dummyScrutVar0.equals(0x01))
        {
            //chainRecords = [XmlTkMaxFrt] [XmlTkMinFrt] [XmlTkLogBaseFrt]
            if (getNextXmlTkTag(reader) == 0x55)
            {
                this.chainRecords.add(new XmlTkMaxFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x56)
            {
                this.chainRecords.add(new XmlTkMinFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x0)
            {
                this.chainRecords.add(new XmlTkLogBaseFrt(reader));
            }
             
        }
        else if (__dummyScrutVar0.equals(0x02))
        {
            //chainRecords = [XmlTkStyle] [XmlTkThemeOverride] [XmlTkColorMappingOverride]
            if (getNextXmlTkTag(reader) == 0x03)
            {
                this.chainRecords.add(new XmlTkStyle(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x33)
            {
                this.chainRecords.add(new XmlTkThemeOverride(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x34)
            {
                this.chainRecords.add(new XmlTkColorMappingOverride(reader));
            }
             
        }
        else if (__dummyScrutVar0.equals(0x04))
        {
            //chainRecords = [XmlTkNoMultiLvlLbl] [XmlTkTickLabelSkipFrt] [XmlTkTickMarkSkipFrt] [XmlTkMajorUnitFrt]
            //[XmlTkMinorUnitFrt] [XmlTkTickLabelPositionFrt] [XmlTkBaseTimeUnitFrt] [XmlTkFormatCodeFrt] [XmlTkMajorUnitTypeFrt] [XmlTkMinorUnitTypeFrt]
            if (getNextXmlTkTag(reader) == 0x2E)
            {
                this.chainRecords.add(new XmlTkNoMultiLvlLbl(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x51)
            {
                this.chainRecords.add(new XmlTkTickLabelSkipFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x52)
            {
                this.chainRecords.add(new XmlTkTickMarkSkipFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x53)
            {
                this.chainRecords.add(new XmlTkMajorUnitFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x54)
            {
                this.chainRecords.add(new XmlTkMinorUnitFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x5C)
            {
                this.chainRecords.add(new XmlTkTickLabelPositionFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x5F)
            {
                this.chainRecords.add(new XmlTkBaseTimeUnitFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x64)
            {
                this.chainRecords.add(new XmlTkFormatCodeFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x6A)
            {
                this.chainRecords.add(new XmlTkMajorUnitTypeFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x6B)
            {
                this.chainRecords.add(new XmlTkMinorUnitTypeFrt(reader));
            }
             
        }
        else if (__dummyScrutVar0.equals(0x05))
        {
            //chainRecords = [XmlTkShowDLblsOverMax] [XmlTkBackWallThicknessFrt] [XmlTkFloorThicknessFrt] [XmlTkDispBlanksAsFrt] [SURFACE]
            //SURFACE = XmlTkStartSurface [XmlTkFormatCodeFrt [XmlTkSpb]] [XmlTkTpb] XmlTkEndSurface
            boolean first = true;
            if (getNextXmlTkTag(reader) == 0x5B)
            {
                this.chainRecords.add(new XmlTkShowDLblsOverMax(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x35)
            {
                this.chainRecords.add(new XmlTkBackWallThicknessFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x36)
            {
                this.chainRecords.add(new XmlTkFloorThicknessFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x66)
            {
                this.chainRecords.add(new XmlTkDispBlanksAsFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x59)
            {
                if (first)
                {
                    this.chainRecords.add(new XmlTkStartSurface(reader));
                    first = false;
                }
                else
                {
                    this.chainRecords.add(new XmlTkEndSurface(reader));
                } 
            }
             
            if (getNextXmlTkTag(reader) == 0x64)
            {
                this.chainRecords.add(new XmlTkFormatCodeFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x1E)
            {
                this.chainRecords.add(new XmlTkSpb(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x20)
            {
                this.chainRecords.add(new XmlTkTpb(reader));
            }
             
        }
        else if (__dummyScrutVar0.equals(0x0F))
        {
            //chainRecords = [XmlTkOverlay]
            this.chainRecords.add(new XmlTkOverlay(reader));
        }
        else if (__dummyScrutVar0.equals(0x13))
        {
            //chainRecords = [XmlTkSymbolFrt]
            this.chainRecords.add(new XmlTkSymbolFrt(reader));
        }
        else if (__dummyScrutVar0.equals(0x16))
        {
            //chainRecords = [XmlTkPieComboFrom12Frt]
            this.chainRecords.add(new XmlTkPieComboFrom12Frt(reader));
        }
        else if (__dummyScrutVar0.equals(0x19))
        {
            //chainRecords = [XmlTkOverlay]
            this.chainRecords.add(new XmlTkOverlay(reader));
        }
        else if (__dummyScrutVar0.equals(0x37))
        {
            //chainRecords = [XmlTkRAngAxOffFrt] [XmlTkPerspectiveFrt] [XmlTkRotYFrt] [XmlTkRotXFrt] [XmlTkHeightPercent]
            if (getNextXmlTkTag(reader) == 0x50)
            {
                this.chainRecords.add(new XmlTkRAngAxOffFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x4D)
            {
                this.chainRecords.add(new XmlTkPerspectiveFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x4F)
            {
                this.chainRecords.add(new XmlTkRotYFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x4E)
            {
                this.chainRecords.add(new XmlTkRotXFrt(reader));
            }
             
            if (getNextXmlTkTag(reader) == 0x65)
            {
                this.chainRecords.add(new XmlTkHeightPercent(reader));
            }
             
        }
                 
    }

    // ignore remaing record data
    //reader.ReadBytes(8);
    public UInt16 getNextXmlTkTag(IStreamReader reader) throws Exception {
        long pos = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        XmlTkHeader header = new XmlTkHeader(reader);
        Unsupported.throwUnsupported("reader.getBaseStream().Position = pos");
        return header.xmlTkTag;
    }

}


