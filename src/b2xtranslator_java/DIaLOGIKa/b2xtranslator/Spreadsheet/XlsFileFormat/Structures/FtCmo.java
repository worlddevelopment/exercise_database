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
* This structure specifies the common properties of the Obj record that contains this FtCmo.
*/
public class FtCmo   
{
    public enum ObjectType
    {
        Group,
        Line,
        Rectangle,
        Oval,
        Arc,
        Chart,
        Text,
        Button,
        Picture,
        Polygon,
        __dummyEnum__0,
        Checkbox,
        RadioButton,
        EditBox,
        Label,
        DialogBox,
        SpinControl,
        Scrollbar,
        List,
        GroupBox,
        DropdownList,
        __dummyEnum__1,
        __dummyEnum__2,
        __dummyEnum__3,
        __dummyEnum__4,
        Note,
        __dummyEnum__5,
        __dummyEnum__6,
        __dummyEnum__7,
        __dummyEnum__8,
        OfficeArtObject
    }
    /**
    * Reserved. MUST be 0x15.
    */
    public UInt16 ft = new UInt16();
    /**
    * Reserved. MUST be 0x12.
    */
    public UInt16 cb = new UInt16();
    /**
    * An unsigned integer that specifies the type of object represented by the Obj record that contains this FtCmo
    */
    public ObjectType ot = ObjectType.Group;
    /**
    * An unsigned integer that specifies the identifier of this object. This object identifier
    * is used by other types to refer to this object.
    * 
    * The value of id MUST be unique among all Obj records within the Chart Sheet
    * Substream ABNF, Macro Sheet Substream ABNF and Worksheet Substream ABNF.
    */
    public UInt16 id = new UInt16();
    /**
    * A bit that specifies whether this object is locked.
    */
    public boolean fLocked;
    /**
    * A bit that specifies whether the application is expected to choose the object‘s size.
    */
    public boolean fDefaultSize;
    /// <summary>
    /// A bit that specifies whether this is a chart object that is expected to be published
    /// the next time the sheet containing it is published <158>. This bit is ignored if the
    /// fPublishedBookItems field of the BookExt_Conditional12 structure is zero.
    /// </summary>
    public boolean fPublished;
    /**
    * A bit that specifies whether the image of this object is intended to be included when printed.
    */
    public boolean fPrint;
    /**
    * A bit that specifies whether this object has been disabled.
    */
    public boolean fDisabled;
    /**
    * A bit that specifies whether this is an auxiliary object that can only be automatically
    * inserted by the application (as opposed to an object that can be inserted by a user).
    */
    public boolean fUIObj;
    /**
    * A bit that specifies whether this object is expected to be updated on load to
    * reflect the values in the range associated with the object.
    * 
    * This field MUST be ignored unless the pictfmla.key field of the containing Obj
    * exists and pictfmla.key.fmlaListFillRange.cbFmla of the containing Obj is not equal to 0.
    */
    public boolean fRecalcObj;
    /**
    * A bit that specifies whether this object is expected to be updated whenever
    * the value of a cell in the range associated with the object changes.
    * 
    * This field MUST be ignored unless the pictfmla.key field of the containing Obj
    * exists and pictfmla.key.fmlaListFillRange.cbFmla of the containing Obj is not equal to 0.
    */
    public boolean fRecalcObjAlways;
    public FtCmo(IStreamReader reader) throws Exception {
        this.ft = reader.readUInt16();
        this.cb = reader.readUInt16();
        this.ot = (ObjectType)reader.readUInt16();
        this.id = reader.readUInt16();
        UInt16 flags = reader.readUInt16();
        this.fLocked = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0001);
        this.fDefaultSize = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0004);
        this.fPublished = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0008);
        this.fPrint = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0010);
        this.fDisabled = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0080);
        this.fUIObj = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0100);
        this.fRecalcObj = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0200);
        this.fRecalcObjAlways = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1000);
        reader.readBytes(12);
    }

}


