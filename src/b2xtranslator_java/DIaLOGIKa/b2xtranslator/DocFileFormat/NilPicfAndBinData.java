//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PictureDescriptor;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

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
public class NilPicfAndBinData   
{
    /**
    * A signed integer that specifies the size, in bytes, of this structure.
    */
    public int lcb;
    /**
    * An unsigned integer that specifies the number of bytes from the beginning of this structure to the beginning of binData.
    * MUST be 0x44.
    */
    public short cbHeader;
    /**
    * The interpretation of binData depends on the field type of the field containing the
    * picture character and is given by the following table:
    * 
    * REF: HyperlinkFieldData
    * PAGEREF: HyperlinkFieldData
    * NOTEREF: HyperlinkFieldData
    * 
    * FORMTEXT: FormFieldData
    * FORMCHECKBOX: FormFieldData
    * FORMDROPDOWN: FormFieldData
    * 
    * PRIVATE: Custom binary data that is specified by the add-in that inserted this field.
    * ADDIN: Custom binary data that is specified by the add-in that inserted this field.
    * HYPERLINK: HyperlinkFieldData
    */
    public byte[] binData;
    public NilPicfAndBinData(CharacterPropertyExceptions chpx, VirtualStream dataStream) throws Exception {
        //Get start of the NilPicfAndBinData structure
        int fc = PictureDescriptor.getFcPic(chpx);
        if (fc >= 0)
        {
            parse(dataStream,fc);
        }
         
    }

    private void parse(VirtualStream stream, int fc) throws Exception {
        stream.Seek(fc, System.IO.SeekOrigin.Begin);
        VirtualStreamReader reader = new VirtualStreamReader(stream);
        this.lcb = reader.readInt32();
        this.cbHeader = reader.readInt16();
        reader.readBytes(62);
        this.binData = reader.readBytes(this.lcb - this.cbHeader);
    }

}


