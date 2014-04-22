//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:52 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

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
/**
* This structure specifies sheet specific data including sheet tab color and the published state of this sheet.
*/
public class SheetExtOptional   
{
    /**
    * An unsigned integer that specifies the tab color of this sheet. If the tab has a color
    * assigned to it, the value of this field MUST be greater than or equal to 0x08 and less
    * than or equal to 0x3F, as specified in the color table for Icv. If this value does not
    * equal to icvPlain of the associated SheetExt, the value of icvPlain takes precedence.
    * If the tab has no color assigned to it, the value of this field MUST be 0x7F, and MUST be ignored.
    */
    public long icvPlain;
    /**
    * A bit that specifies whether conditional formatting formulas are evaluated.
    * 
    * MUST be one of the following:
    * 
    * Value   Meaning
    * 0       Conditional formatting formulas in this workbook are not evaluated.
    * 1       Conditional formatting formulas in this workbook are evaluated.
    */
    public boolean fCondFmtCalc;
    /**
    * A bit that specifies whether this sheet is published.
    * 
    * MUST be ignored when this sheet is a chart sheet, dialog sheet, or macro sheet.
    * MUST be a value from the following table:
    * 
    * Value   Meaning
    * 0       The sheet is published.
    * 1       The sheet is not published.
    */
    public boolean fNotPublished;
    // TODO: implement CFColor structure
    public byte[] color;
    public SheetExtOptional(IStreamReader reader) throws Exception {
        long field = reader.readUInt32();
        this.icvPlain = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToUInt32(field, 0x003F);
        this.fCondFmtCalc = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(field, 0x0040);
        this.fNotPublished = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(field, 0x0080);
        this.color = reader.readBytes(16);
    }

}


