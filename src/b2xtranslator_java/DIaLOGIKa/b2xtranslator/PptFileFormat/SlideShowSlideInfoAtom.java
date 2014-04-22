//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

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
public class SlideShowSlideInfoAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public int slideTime;
    public uint soundIdRef;
    public byte effectDirection;
    public byte effectType;
    public boolean fManualAdvance;
    public boolean fHidden;
    public boolean fSound;
    public boolean fLoopSound;
    public boolean fStopSound;
    public boolean fAutoAdvance;
    public boolean fCursorVisible;
    public byte speed;
    public SlideShowSlideInfoAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        slideTime = this.Reader.ReadInt32();
        soundIdRef = this.Reader.ReadUInt32();
        effectDirection = this.Reader.ReadByte();
        effectType = this.Reader.ReadByte();
        int flags = this.Reader.ReadInt16();
        fManualAdvance = Utils.bitmaskToBool(flags,0x1);
        fHidden = Utils.bitmaskToBool(flags,0x1 << 2);
        fSound = Utils.bitmaskToBool(flags,0x1 << 4);
        fLoopSound = Utils.bitmaskToBool(flags,0x1 << 6);
        fStopSound = Utils.bitmaskToBool(flags,0x1 << 8);
        fAutoAdvance = Utils.bitmaskToBool(flags,0x1 << 10);
        fCursorVisible = Utils.bitmaskToBool(flags,0x1 << 12);
        speed = this.Reader.ReadByte();
        this.Reader.BaseStream.Position = this.Reader.BaseStream.Length;
    }

}


