//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

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
public class SlidePersistAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    /**
    * logical reference to the slide persist object
    */
    public long PersistIdRef;
    /**
    * Bit 1: Slide outline view is collapsed
    * Bit 2: Slide contains shapes other than placeholders
    */
    public long Flags;
    /**
    * number of placeholder texts stored with the persist object.
    * Allows to display outline view without loading the slide persist objects
    */
    public int NumberText;
    /**
    * Unique slide identifier, used for OLE link monikers for example
    */
    public long SlideId;
    public SlidePersistAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.PersistIdRef = this.Reader.ReadUInt32();
        this.Flags = this.Reader.ReadUInt32();
        this.NumberText = this.Reader.ReadInt32();
        this.SlideId = this.Reader.ReadUInt32();
        this.Reader.ReadUInt32();
    }

    // Throw away reserved field
    public String toString(uint depth) throws Exception {
        return String.Format("{0}\n{1}PsrRef = {2}\n{1}Flags = {3}, NumberText = {4}, SlideId = {5})", super.toString(depth), indentationForDepth(depth + 1), this.PersistIdRef, this.Flags, this.NumberText, this.SlideId);
    }

}


