//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:08 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.SprmType;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TablePropertyExceptions;
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
public class TablePropertyExceptions  extends PropertyExceptions 
{
    /**
    * Creates a TAPX wich doesn't modify anything.
    * The grpprl list is empty
    */
    public TablePropertyExceptions() throws Exception {
        super();
    }

    /**
    * Parses the bytes to retrieve a TAPX
    * 
    *  @param bytes The bytes starting with the istd
    */
    public TablePropertyExceptions(byte[] bytes) throws Exception {
        super(bytes);
    }

    //not yet implemented
    /**
    * Extracts the TAPX SPRMs out of a PAPX
    * 
    *  @param papx
    */
    public TablePropertyExceptions(ParagraphPropertyExceptions papx, VirtualStream dataStream) throws Exception {
        this.grpprl = new CSList<SinglePropertyModifier>();
        for (SinglePropertyModifier sprm : papx.grpprl)
        {
            if (sprm.Type == SprmType.TAP)
            {
                this.grpprl.add(sprm);
            }
            else if (((Enum)sprm.OpCode).ordinal() == 0x646b)
            {
                IStreamReader reader = new VirtualStreamReader(dataStream);
                //there is a native TAP in the data stream
                long fc = System.BitConverter.ToUInt32(sprm.Arguments, 0);
                //get the size of the following grpprl
                //byte[] sizebytes = new byte[2];
                //dataStream.Read(sizebytes, 2, (int)fc);
                byte[] sizebytes = reader.ReadBytes(fc, 2);
                UInt16 grpprlSize = System.BitConverter.ToUInt16(sizebytes, 0);
                //read the grpprl
                //byte[] grpprlBytes = new byte[grpprlSize];
                //dataStream.Read(grpprlBytes);
                byte[] grpprlBytes = reader.ReadBytes(grpprlSize);
                //parse the grpprl
                PropertyExceptions externalPx = new PropertyExceptions(grpprlBytes);
                for (SinglePropertyModifier sprmExternal : externalPx.grpprl)
                {
                    if (sprmExternal.Type == SprmType.TAP)
                    {
                        this.grpprl.add(sprmExternal);
                    }
                     
                }
            }
              
        }
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<TablePropertyExceptions>)mapping).apply(this);
    }

}


