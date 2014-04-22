//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:06 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;

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
public class PropertyExceptions   implements IVisitable
{
    /**
    * A list of the sprms that encode the differences between
    * CHP for a character and the PAP for the paragraph style used.
    */
    public CSList<SinglePropertyModifier> grpprl;
    public PropertyExceptions() throws Exception {
        this.grpprl = new CSList<SinglePropertyModifier>();
    }

    public PropertyExceptions(byte[] bytes) throws Exception {
        this.grpprl = new CSList<SinglePropertyModifier>();
        if (bytes.length != 0)
        {
            //read the sprms
            int sprmStart = 0;
            boolean goOn = true;
            while (goOn)
            {
                //enough bytes to read?
                if (sprmStart + 2 < bytes.length)
                {
                    //make spra
                    DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode opCode = (DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode)System.BitConverter.ToUInt16(bytes, sprmStart);
                    byte spra = (byte)(((Enum)opCode).ordinal() >> 13);
                    // get size of operand
                    short opSize = (short)SinglePropertyModifier.getOperandSize(spra);
                    byte lenByte = 0;
                    //operand has variable size
                    if (opSize == 255)
                    {
                        //some opCode need special treatment
                        switch(opCode)
                        {
                            case sprmTDefTable: 
                            case sprmTDefTable10: 
                                //The opSize of the table definition is stored in 2 bytes instead of 1
                                lenByte = 2;
                                opSize = System.BitConverter.ToInt16(bytes, sprmStart + 2);
                                //Word adds an additional byte to the opSize to compensate the additional
                                //byte needed for the length
                                opSize--;
                                break;
                            case sprmPChgTabs: 
                                //The tab operand can be bigger than 255 bytes (length byte is set to 255).
                                //In this case a special calculation of the opSize is needed
                                lenByte = 1;
                                opSize = bytes[sprmStart + 2];
                                if (opSize == 255)
                                {
                                    byte itbdDelMax = bytes[sprmStart + 3];
                                    byte itbdAddMax = bytes[sprmStart + 3 + 2 * itbdDelMax];
                                    opSize = (short)((itbdDelMax * 4 + itbdAddMax * 3) - 1);
                                }
                                 
                                break;
                            default: 
                                //The variable length stand in the byte after the opcode
                                lenByte = 1;
                                opSize = bytes[sprmStart + 2];
                                break;
                        
                        }
                    }
                     
                    //copy sprm to array
                    //length is 2byte for the opCode, lenByte for the length, opSize for the length of the operand
                    byte[] sprmBytes = new byte[2 + lenByte + opSize];
                    if (bytes.length >= sprmStart + sprmBytes.length)
                    {
                        Array.Copy(bytes, sprmStart, sprmBytes, 0, sprmBytes.length);
                        //parse
                        SinglePropertyModifier sprm = new SinglePropertyModifier(sprmBytes);
                        grpprl.add(sprm);
                        sprmStart += sprmBytes.length;
                    }
                    else
                    {
                        goOn = false;
                    } 
                }
                else
                {
                    goOn = false;
                } 
            }
        }
         
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<PropertyExceptions>)mapping).apply(this);
    }

}


