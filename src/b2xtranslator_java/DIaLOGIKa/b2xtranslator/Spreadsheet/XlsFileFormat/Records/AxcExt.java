//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:40 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.DateUnit;
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
* This record specifies additional extension properties of a date axis,
* along with a CatSerRange record.
*/
public class AxcExt  extends BiffRecord 
{
    public static final RecordType ID = RecordType.AxcExt;
    /// <summary>
    /// An unsigned integer that specifies the minimum date, as a date in the
    /// date system specified by the Date1904 record, in the units defined by duBase.
    ///
    /// SHOULD <21> be less than or equal to catMax. If fAutoMin is set to 1,
    /// MUST be ignored. If fDateAxis is set to 0, MUST be ignored.
    /// </summary>
    public UInt16 catMin = new UInt16();
    /// <summary>
    /// An unsigned integer that specifies the maximum date, as a date in the
    /// date system specified by the Date1904 record, in the units defined by duBase.
    /// SHOULD <22> be greater than or equal to catMin. If fAutoMax is set to 1,
    /// MUST be ignored. If fDateAxis is set to 0, MUST be ignored.
    /// </summary>
    public UInt16 catMax = new UInt16();
    /**
    * An unsigned integer that specifies the interval at which the major tick marks
    * are displayed on the axis, in the unit defined by duMajor.
    * 
    * MUST be greater than or equal to catMinor when duMajor is equal to duMinor.
    * If fAutoMajor is set to 1, MUST be ignored. If fDateAxis is set to 0, MUST be ignored.
    */
    public UInt16 catMajor = new UInt16();
    /**
    * A DateUnit that specifies the unit of time to use for catMajor when
    * the axis is a date axis. If fDateAxis is set to 0, MUST be ignored.
    */
    public DateUnit duMajor = DateUnit.Days;
    /**
    * An unsigned integer that specifies the interval at which the minor tick marks
    * are displayed on the axis, in a unit defined by duMinor.
    * 
    * MUST be less than or equal to catMajor when duMajor is equal to duMinor.
    * If fAutoMinor is set to 1, MUST be ignored. If fDateAxis is set to 0, MUST be ignored.
    */
    public UInt16 catMinor = new UInt16();
    /**
    * A DateUnit that specifies the unit of time to use for catMinor when the
    * axis is a date axis. If fDateAxis is set to 0, MUST be ignored.
    */
    public DateUnit duMinor = DateUnit.Days;
    /**
    * A DateUnit that specifies the smallest unit of time used by the axis.
    * 
    * If fAutoBase is set to 1, this field MUST be ignored. If fDateAxis is set to 0, MUST be ignored.
    */
    public DateUnit duBase = DateUnit.Days;
    /**
    * An unsigned integer that specifies at which date, as a date in the date system
    * specified by the Date1904 record, in the units defined by duBase, the value axis
    * crosses this axis. If fDateAxis is set to 0, MUST be ignored.
    * If fAutoCross is set to 1, MUST be ignored.
    */
    public UInt16 catCrossDate = new UInt16();
    /**
    * A bit that specifies whether catMin is calculated automatically.
    * 
    * If fDateAxis is set to 0, MUST be ignored.
    * MUST be a value from the following table:
    * 
    * Value       Meaning
    * 0           The value specified by catMin is used and catMin is not calculated automatically.
    * 1           catMin is calculated such that the minimum data points value can be displayed.
    */
    public boolean fAutoMin;
    /**
    * A A bit that specifies whether catMax is calculated automatically.
    * 
    * If fDateAxis is set to 0, then fAutoMax MUST be ignored. If the value of the fMaxCross
    * field in the CatSerRange record is 1, then fAutoMax MUST be ignored.
    * 
    * MUST be a value from the following table:
    * 
    * Value       Meaning
    * 0           The value specified by catMax is used and catMax is not calculated automatically.
    * 1           catMax is calculated such that the minimum data points value can be displayed.
    */
    public boolean fAutoMax;
    /**
    * A bit that specifies whether catMajor is calculated automatically.
    * 
    * If fDateAxis is set to 0, MUST be ignored.
    * 
    * Value       Meaning
    * 0           The value specified by catMajor is used and catMajor is not calculated automatically.
    * 1           catMajor is calculated automatically.
    */
    public boolean fAutoMajor;
    /**
    * A bit that specifies whether catMinor is calculated automatically.
    * 
    * If fDateAxis is set to 0, MUST be ignored.
    * 
    * Value       Meaning
    * 0           The value specified by catMinor is used and catMinor is not calculated automatically.
    * 1           catMinor is calculated automatically.
    */
    public boolean fAutoMinor;
    /**
    * A bit that specifies whether the axis is a date axis.
    * 
    * MUST be a value from the following table:
    * 
    * Value       Meaning
    * 0           The axis is not a date axis.
    * 1           The axis is a date axis.
    */
    public boolean fDateAxis;
    /**
    * A bit that specifies whether the units of the date axis are chosen automatically.
    * 
    * If fDateAxis is set to 0, MUST be ignored.
    * MUST be a value from the following table:
    * 
    * Value       Meaning
    * 0           The value specified by duBase is used and duBase is not computed automatically.
    * 1           duBase is calculated automatically.
    */
    public boolean fAutoBase;
    /**
    * A bit that specifies whether catCrossDate is calculated automatically.
    * 
    * MUST be a value from the following table:
    * 
    * Value       Meaning
    * 0           The value specified by catCrossDate is used and catCrossDate is not calculated automatically.
    * 1           catCrossDate is calculated automatically such that it can be displayed.
    */
    public boolean fAutoCross;
    /**
    * A bit that specifies whether the axis type is detected automatically.
    * 
    * MUST be a value from the following table:
    * 
    * Value       Meaning
    * 0           The axis will stay as specified by the fDateAxis field.
    * 1           The axis will automatically become a date axis when the data it is related to contains date values; otherwise the axis will be a category axis.
    */
    public boolean fAutoDate;
    public AxcExt(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.catMin = reader.readUInt16();
        this.catMax = reader.readUInt16();
        this.catMajor = reader.readUInt16();
        this.duMajor = (DateUnit)reader.readUInt16();
        this.catMinor = reader.readUInt16();
        this.duMinor = (DateUnit)reader.readUInt16();
        this.duBase = (DateUnit)reader.readUInt16();
        this.catCrossDate = reader.readUInt16();
        UInt16 flags = reader.readUInt16();
        this.fAutoMin = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0001);
        this.fAutoMax = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0002);
        this.fAutoMajor = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0004);
        this.fAutoMinor = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0008);
        this.fDateAxis = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0010);
        this.fAutoBase = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0020);
        this.fAutoCross = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0040);
        this.fAutoDate = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0080);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


