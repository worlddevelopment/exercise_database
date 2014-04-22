//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:07 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteParseException;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Global;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ShadingDescriptor;

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
public class ShadingDescriptor   implements IVisitable
{
    public enum ShadingPattern
    {
        Automatic,
        Solid,
        Percent_5,
        Percent_10,
        Percent_20,
        Percent_25,
        Percent_30,
        Percent_40,
        Percent_50,
        Percent_60,
        Percent_70,
        Percent_75,
        Percent_80,
        Percent_90,
        DarkHorizontal,
        DarkVertical,
        DarkForwardDiagonal,
        DarkBackwardDiagonal,
        DarkCross,
        DarkDiagonalCross,
        Horizontal,
        Vertical,
        ForwardDiagonal,
        BackwardDiagonal,
        Cross,
        DiagonalCross,
        Percent_2_5,
        Percent_7_5,
        Percent_12_5,
        Percent_15,
        Percent_17_5,
        Percent_22_5,
        Percent_27_5,
        Percent_32_5,
        Percent_35,
        Percent_37_5,
        Percent_42_5,
        Percent_45,
        Percent_47_5,
        Percent_52_5,
        Percent_55,
        Percent_57_5,
        Percent_62_5,
        Percent_65,
        Percent_67_5,
        Percent_72_5,
        Percent_77_5,
        Percent_82_5,
        Percent_85,
        Percent_87_5,
        Percent_92_5,
        Percent_95,
        Percent_97_5,
        Percent_97
    }
    /**
    * 24-bit foreground color
    */
    public long cvFore;
    /**
    * Foreground color.
    * Only used if cvFore is not set
    */
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier icoFore = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier.auto;
    /**
    * 24-bit background color
    */
    public long cvBack;
    /**
    * Background color.
    * Only used if cvBack is not set.
    */
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier icoBack = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier.auto;
    /**
    * Shading pattern
    */
    public ShadingPattern ipat = ShadingPattern.Automatic;
    /**
    * Creates a new ShadingDescriptor with default values
    */
    public ShadingDescriptor() throws Exception {
        setDefaultValues();
    }

    /**
    * Parses the bytes to retrieve a ShadingDescriptor.
    * 
    *  @param bytes The bytes
    */
    public ShadingDescriptor(byte[] bytes) throws Exception {
        if (bytes.length == 10)
        {
            //it's a Word 2000/2003 descriptor
            this.cvFore = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(new BitArray(new byte[]{ bytes[2], bytes[1], bytes[0] }));
            this.cvBack = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(new BitArray(new byte[]{ bytes[6], bytes[5], bytes[4] }));
            this.ipat = (ShadingPattern)System.BitConverter.ToUInt16(bytes, 8);
        }
        else if (bytes.length == 2)
        {
            //it's a Word 97 SPRM
            short val = System.BitConverter.ToInt16(bytes, 0);
            this.icoFore = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier)((val << 11) >> 11);
            this.icoBack = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier)((val << 2) >> 7);
            this.ipat = (ShadingPattern)(val >> 10);
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct SHD, the length of the struct doesn't match");
        }  
    }

    private void setDefaultValues() throws Exception {
        this.cvBack = 0;
        this.cvFore = 0;
        this.icoBack = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier.auto;
        this.icoFore = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier.auto;
        this.ipat = ShadingPattern.Automatic;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ShadingDescriptor>)mapping).apply(this);
    }

}


