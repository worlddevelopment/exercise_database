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
* This structure specifies the properties of some form control in a Dialog Sheet.
* 
* The control MUST be a group, radio button, label, button or checkbox.
*/
public class ControlInfo   
{
    /**
    * A bit that specifies whether this control dismisses the Dialog Sheet and performs the default behavior.
    * 
    * If the control is not a button, the value MUST be 0.
    */
    public boolean fDefault;
    /**
    * A bit that specifies whether this control is intended to load context-sensitive help for the Dialog Sheet.
    * 
    * If the control is not a button, the value MUST be 0.
    */
    public boolean fHelp;
    /**
    * A bit that specifies whether this control dismisses the Dialog Sheet and take no action.
    * 
    * If the control is not a button, the value MUST be 0.
    */
    public boolean fCancel;
    /**
    * A bit that specifies whether this control dismisses the Dialog Sheet.
    * 
    * If the control is not a button, the value MUST be 0.
    */
    public boolean fDismiss;
    /**
    * A signed integer that specifies the Unicode character of the control‘s accelerator key.
    * 
    * The value MUST be greater than or equal to 0x0000. A value of 0x0000 specifies there is no accelerator associated with this control.
    */
    public short accel1;
    public ControlInfo(IStreamReader reader) throws Exception {
        UInt16 flags = reader.readUInt16();
        this.fDefault = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0001);
        this.fHelp = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0002);
        this.fCancel = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0004);
        this.fDismiss = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0008);
        this.accel1 = reader.readInt16();
        reader.readBytes(2);
    }

}


