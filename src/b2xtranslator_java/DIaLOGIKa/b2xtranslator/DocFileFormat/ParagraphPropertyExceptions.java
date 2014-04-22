//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:05 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
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
public class ParagraphPropertyExceptions  extends PropertyExceptions 
{
    /**
    * Index to style descriptor of the style from which the
    * paragraph inherits its paragraph and character properties
    */
    public UInt16 istd = new UInt16();
    /**
    * Creates a PAPX wich doesn't modify anything.
    * The grpprl list is empty
    */
    public ParagraphPropertyExceptions() throws Exception {
        super();
    }

    /**
    * Parses the bytes to retrieve a PAPX
    * 
    *  @param bytes The bytes starting with the istd
    */
    public ParagraphPropertyExceptions(byte[] bytes, VirtualStream dataStream) throws Exception {
        super(new CSList<Byte>(bytes).GetRange(2, bytes.length - 2).ToArray());
        if (bytes.length != 0)
        {
            this.istd = System.BitConverter.ToUInt16(bytes, 0);
        }
         
        for (SinglePropertyModifier sprm : this.grpprl)
        {
            //There is a SPRM that points to an offset in the data stream,
            //where a list of SPRM is saved.
            if (sprm.OpCode == OperationCode.sprmPHugePapx || ((Enum)sprm.OpCode).ordinal() == 0x6646)
            {
                IStreamReader reader = new VirtualStreamReader(dataStream);
                long fc = System.BitConverter.ToUInt32(sprm.Arguments, 0);
                //parse the size of the external grpprl
                byte[] sizebytes = new byte[2];
                dataStream.read(sizebytes,0,2,(int)fc);
                UInt16 size = System.BitConverter.ToUInt16(sizebytes, 0);
                //parse the external grpprl
                //byte[] grpprlBytes = new byte[size];
                //dataStream.Read(grpprlBytes);
                byte[] grpprlBytes = reader.ReadBytes(size);
                PropertyExceptions externalPx = new PropertyExceptions(grpprlBytes);
                //assign the external grpprl
                this.grpprl = externalPx.grpprl;
                //remove the sprmPHugePapx
                this.grpprl.remove(sprm);
            }
             
        }
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ParagraphPropertyExceptions>)mapping).apply(this);
    }

}


