//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:44 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.AbstractPtg;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.XLUnicodeStringNoCch;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

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
* This record specifies a defined name.
* 
* In the old version of the spec this record has been listed as RecordType.NAME (218h)
* whereas in the new version it is listed as RecordType.Lbl (18h)
*/
public class Lbl  extends BiffRecord 
{
    public static final RecordType ID = RecordType.Lbl;
    public enum FunctionCategory
    {
        All,
        Financial,
        DateTime,
        MathTrigonometry,
        Statistical,
        Lookup,
        Database,
        Text,
        Logical,
        Info,
        Commands,
        Customize,
        MacroControl,
        DDEExternal,
        UserDefined,
        Engineering,
        Cube
    }
    /**
    * A bit that specifies whether the defined name is not visible
    * in the list of defined names.
    */
    public boolean fHidden;
    /**
    * A bit that specifies whether the defined name represents an XLM macro.
    * If this bit is 1, fProc MUST also be 1.
    */
    public boolean fFunc;
    /**
    * A bit that specifies whether the defined name represents a Visual Basic
    * for Applications (VBA) macro. If this bit is 1, the fProc MUST also be 1.
    */
    public boolean fOB;
    /**
    * A bit that specifies whether the defined name represents a macro.
    */
    public boolean fProc;
    /**
    * A bit that specifies whether rgce contains a call to a function that can return an array.
    */
    public boolean fCalcExp;
    /**
    * A bit that specifies whether the defined name represents a built-in name.
    */
    public boolean fBuiltin;
    /**
    * An unsigned integer that specifies the function category for the defined name. MUST be less than or equal to 31.
    * The values 17 to 31 are user-defined values. User-defined values are specified in FnGroupName.
    * The values zero to 16 are defined as specified by the FunctionCategory enum.
    */
    public UInt16 fGrp = new UInt16();
    /**
    * A bit that specifies whether the defined name is published. This bit is ignored
    * if the fPublishedBookItems field of the BookExt_Conditional12 structure is zero.
    */
    public boolean fPublished;
    /**
    * A bit that specifies whether the defined name is a workbook parameter.
    */
    public boolean fWorkbookParam;
    /// <summary>
    /// The unsigned integer value of the ASCII character that specifies the shortcut
    /// key for the macro represented by the defined name.
    ///
    /// MUST be zero (No shortcut key) if fFunc is 1 or if fProc is 0. Otherwise MUST
    /// <84> be greater than or equal to 0x41 and less than or equal to 0x5A, or greater
    /// than or equal to 0x61 and less than or equal to 0x7A.
    /// </summary>
    public byte chKey;
    /**
    * An unsigned integer that specifies the number of characters in Name.
    * 
    * MUST be greater than or equal to zero.
    */
    public byte cch;
    /**
    * An unsigned integer that specifies length of rgce in bytes.
    */
    public UInt16 cce = new UInt16();
    /**
    * An unsigned integer that specifies if the defined name is a local name, and if so,
    * which sheet it is on. If this is not 0, the defined name is a local name and the value
    * MUST be a one-based index to the collection of BoundSheet8 records as they appear in the Global Substream.
    */
    public UInt16 itab = new UInt16();
    /**
    * An XLUnicodeStringNoCch that specifies the name for the defined name. If fBuiltin is zero,
    * this field MUST satisfy the same restrictions as the name field of XLNameUnicodeString.
    * 
    * If fBuiltin is 1, this field is for a built-in name. Each built-in name has a zero-based
    * index value associated to it. A built-in name or its index value MUST be used for this field.
    */
    public XLUnicodeStringNoCch Name;
    /**
    * A NameParsedFormula that specifies the formula for the defined name.
    */
    public Stack<AbstractPtg> rgce = new Stack<AbstractPtg>();
    public Lbl(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        //Debug.Assert(this.Id == ID);
        UInt16 flags = reader.readUInt16();
        this.fHidden = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0001);
        this.fFunc = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0002);
        this.fOB = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0004);
        this.fProc = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0008);
        this.fCalcExp = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0010);
        this.fBuiltin = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0020);
        this.fGrp = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToUInt16(flags, 0x07C0);
        this.fPublished = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x2000);
        this.fWorkbookParam = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x4000);
        this.chKey = reader.readByte();
        this.cch = reader.readByte();
        this.cce = reader.readUInt16();
        //read reserved bytes
        reader.readBytes(2);
        this.itab = reader.readUInt16();
        // read 4 reserved bytes
        reader.readBytes(4);
        if (this.cch > 0)
        {
            this.Name = new XLUnicodeStringNoCch(reader, this.cch);
        }
        else
        {
            this.Name = new XLUnicodeStringNoCch();
        } 
        long oldStreamPosition = this.Reader.BaseStream.Position;
        try
        {
            this.rgce = ExcelHelperClass.getFormulaStack(this.Reader, this.cce);
        }
        catch (Exception ex)
        {
            this.Reader.BaseStream.Seek(oldStreamPosition, System.IO.SeekOrigin.Begin);
            this.Reader.BaseStream.Seek(this.cce, System.IO.SeekOrigin.Current);
            TraceLogger.error("Formula parse error in intern name");
            TraceLogger.debug(ex.getStackTrace().toString());
        }

        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


