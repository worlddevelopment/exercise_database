//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:51 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

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
/**
* Window1: Window Information (3Dh)
* 
* The Window1 record contains workbook-level window attributes.
* 
* The xWn and yWn fields contain the location of the window in units of 1/20th of a point,
* relative to the upper-left corner of the Excel window client area.
* 
* The dxWn and dyWn fields contain the window size, also in units of 1/20th  of a point.
*/
public class Window1  extends BiffRecord 
{
    public static final RecordType ID = RecordType.Window1;
    /**
    * Horizontal position of the window in units of 1/20th of a point.
    */
    public UInt16 xWn = new UInt16();
    /**
    * Vertical position of the window in units of 1/20th of a point.
    */
    public UInt16 yWn = new UInt16();
    /**
    * Width of the window in units of 1/20th of a point.
    */
    public UInt16 dxWn = new UInt16();
    /**
    * Height of the window in units of 1/20th of a point.
    */
    public UInt16 dyWn = new UInt16();
    /**
    * Option flags
    */
    public UInt16 grbit = new UInt16();
    /**
    * Index of the selected workbook tab (0-based).
    */
    public UInt16 itabCur = new UInt16();
    /**
    * Index of the first displayed workbook tab (0-based).
    */
    public UInt16 itabFirst = new UInt16();
    /**
    * Number of workbook tabs that are selected.
    */
    public UInt16 ctabSel = new UInt16();
    /**
    * Ratio of the width of the workbook tabs to the width of the horizontal scroll bar;
    * to obtain the ratio, convert to decimal and then divide by 1000.
    */
    public UInt16 wTabRatio = new UInt16();
    // The grbit field contains the following option flags:
    // Field                        Offset	Bits    Mask	Name	Contents
    public boolean fHidden;
    //  0	    0       01h		=1 if the window is hidden
    public boolean fIconic;
    //     	    1	    02h     =1 if the window is currently displayed as an icon
    public boolean reserved0;
    //     	    2	    04h
    public boolean fDspHScroll;
    //     	    3	    08h	    =1 if the horizontal scroll bar is displayed
    public boolean fDspVScroll;
    //    	    4	    10h	    =1 if the vertical scroll bar is displayed
    public boolean fBotAdornment;
    //     	    5	    20h	    =1 if the workbook tabs are displayed
    public boolean fNoAFDateGroup;
    //     	    6	    40h	    =1 if the AutoFilter should not group dates (Excel 11 (2003) behavior), new for Office Excel 2007
    public boolean reserved1;
    //     	    7	    80h
    public byte reserved2;
    //  1       7�0	    FFh
    public Window1(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        xWn = reader.readUInt16();
        yWn = reader.readUInt16();
        dxWn = reader.readUInt16();
        dyWn = reader.readUInt16();
        grbit = reader.readUInt16();
        fHidden = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbit, 0x01);
        fIconic = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbit, 0x02);
        reserved0 = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbit, 0x04);
        fDspHScroll = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbit, 0x08);
        fDspVScroll = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbit, 0x10);
        fBotAdornment = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbit, 0x20);
        fNoAFDateGroup = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbit, 0x40);
        reserved1 = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbit, 0x80);
        reserved2 = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToByte(grbit, 0xFF00);
        itabCur = reader.readUInt16();
        itabFirst = reader.readUInt16();
        ctabSel = reader.readUInt16();
        wTabRatio = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


