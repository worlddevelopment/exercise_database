//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:07 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;

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
public class ShadowMapping  extends AbstractOpenXmlMapping implements IMapping<ShapeOptions>
{
    protected ConversionContext _ctx;
    private ShapeOptions so;
    public ShadowMapping(ConversionContext ctx, XmlWriter writer) throws Exception {
        super(writer);
        _ctx = ctx;
    }

    private static int counter = 0;
    public void apply(ShapeOptions pso) throws Exception {
        so = pso;
        counter++;
        if (so.OptionsByID.containsKey(PropertyId.shadowType))
        {
            long __dummyScrutVar0 = so.OptionsByID.get(PropertyId.shadowType).op;
            if (__dummyScrutVar0.equals(0))
            {
                //offset
                writeOffset();
            }
            else if (__dummyScrutVar0.equals(1))
            {
                //double
                _writer.WriteStartElement("a", "effectLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "prstShdw", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "shdw13");
                if (so.OptionsByID.containsKey(PropertyId.shadowOffsetX))
                    if (so.OptionsByID.get(PropertyId.shadowOffsetX).op != 0)
                        writeDistDir();
                     
                 
                writeColor();
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
            else if (__dummyScrutVar0.equals(2))
            {
                //rich
                //shadow offset and  a transformation
                _writer.WriteStartElement("a", "effectLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "outerShdw", OpenXmlNamespaces.DrawingML);
                if (so.OptionsByID.containsKey(PropertyId.shadowOffsetX))
                    if (so.OptionsByID.get(PropertyId.shadowOffsetX).op != 0)
                        writeDistDir();
                     
                 
                if (so.OptionsByID.containsKey(PropertyId.shadowOriginX))
                {
                    byte[] bytes = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.shadowOriginX).op);
                    int integral = BitConverter.ToInt16(bytes, 0);
                    uint fractional = BitConverter.ToUInt16(bytes, 2);
                    if (fractional == 0xffff)
                        integral *= -1;
                     
                    double origX = integral;
                    // +((decimal)fractional / (decimal)65536);
                    bytes = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.shadowOriginY).op);
                    integral = BitConverter.ToInt16(bytes, 0);
                    fractional = BitConverter.ToUInt16(bytes, 2);
                    if (fractional == 0xffff)
                        integral *= -1;
                     
                    double origY = integral;
                    // +((decimal)fractional / (decimal)65536);
                    if (origX > 0)
                    {
                        if (origY > 0)
                        {
                            _writer.writeAttributeString("algn", "tl");
                        }
                        else
                        {
                            _writer.writeAttributeString("algn", "b");
                        } 
                    }
                    else
                    {
                        if (origY > 0)
                        {
                        }
                        else
                        {
                            _writer.writeAttributeString("algn", "br");
                        } 
                    } 
                }
                else
                {
                    _writer.writeAttributeString("algn", "b");
                } 
                if (so.OptionsByID.containsKey(PropertyId.shadowScaleXToX))
                {
                    byte[] bytes = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.shadowScaleXToX).op);
                    int integral = -1 * BitConverter.ToInt16(bytes, 0);
                    uint fractional = BitConverter.ToUInt16(bytes, 2);
                    if (fractional == 0xffff)
                        integral *= -1;
                     
                    double result = integral + ((double)fractional / (double)65536);
                    result = 1 - (result / 65536);
                    _writer.writeAttributeString("sx", String.valueOf(Math.floor(result * 100000)));
                }
                 
                if (so.OptionsByID.containsKey(PropertyId.shadowScaleXToY))
                {
                    int scaleXY = Utils.eMUToMasterCoord((int)so.OptionsByID.get(PropertyId.shadowScaleXToY).op);
                }
                 
                if (so.OptionsByID.containsKey(PropertyId.shadowScaleYToX))
                {
                    double scaleYX = (double)(int)so.OptionsByID.get(PropertyId.shadowScaleYToX).op;
                    //_writer.WriteAttributeString("kx", System.Math.Floor(scaleYX / 138790 * 100 * 60000).ToString()); //The 138790 comes from reverse engineering. I can't find a hint in the spec about how to convert this
                    if (scaleYX < 0)
                    {
                        _writer.writeAttributeString("kx", "-2453606");
                    }
                    else
                    {
                        _writer.writeAttributeString("kx", "2453606");
                    } 
                }
                 
                if (so.OptionsByID.containsKey(PropertyId.shadowScaleYToY))
                {
                    byte[] bytes = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.shadowScaleYToY).op);
                    int integral = -1 * BitConverter.ToInt16(bytes, 0);
                    uint fractional = BitConverter.ToUInt16(bytes, 2);
                    if (fractional == 0xffff)
                        integral *= -1;
                     
                    double result = integral;
                    // +((decimal)fractional / (decimal)65536);
                    if (fractional != 0xffff)
                    {
                        result = 1 - (result / 65536);
                    }
                    else
                    {
                        result = (result / 65536);
                    } 
                    if (result == 0)
                    {
                        if (so.OptionsByID.containsKey(PropertyId.shadowScaleYToX))
                        {
                            result = (double)(-0.5);
                        }
                        else
                        {
                            result = (double)(-1);
                        } 
                    }
                     
                    _writer.writeAttributeString("sy", String.valueOf(Math.floor(result * 100000)));
                }
                else
                {
                    _writer.writeAttributeString("sy", "50000");
                } 
                writeColor();
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
            else if (__dummyScrutVar0.equals(3))
            {
            }
            else //shape
            if (__dummyScrutVar0.equals(4))
            {
            }
            else //drawing
            if (__dummyScrutVar0.equals(5))
            {
                //embossOrEngrave
                _writer.WriteStartElement("a", "effectLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "prstShdw", OpenXmlNamespaces.DrawingML);
                if (so.OptionsByID.get(PropertyId.shadowOffsetX).op == 0x319c)
                {
                    _writer.writeAttributeString("prst", "shdw17");
                }
                else
                {
                    _writer.writeAttributeString("prst", "shdw18");
                } 
                if (so.OptionsByID.containsKey(PropertyId.shadowOffsetX))
                    if (so.OptionsByID.get(PropertyId.shadowOffsetX).op != 0)
                        writeDistDir();
                     
                 
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                String colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.shadowColor).op, so.firstAncestorWithType(), so);
                _writer.writeAttributeString("val", colorval);
                if (so.OptionsByID.containsKey(PropertyId.shadowOpacity) && so.OptionsByID.get(PropertyId.shadowOpacity).op != 65536)
                {
                    _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf(Math.round(((double)so.OptionsByID.get(PropertyId.shadowOpacity).op / 65536 * 100000))));
                    //we need the percentage of the opacity (65536 means 100%)
                    _writer.writeEndElement();
                }
                 
                _writer.WriteElementString("a", "gamma", OpenXmlNamespaces.DrawingML, "");
                _writer.WriteStartElement("a", "shade", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", "60000");
                _writer.writeEndElement();
                _writer.WriteElementString("a", "invGamma", OpenXmlNamespaces.DrawingML, "");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
                  
        }
        else
        {
            //default is offset
            writeOffset();
        } 
    }

    private void writeOffset() throws Exception {
        _writer.WriteStartElement("a", "effectLst", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "outerShdw", OpenXmlNamespaces.DrawingML);
        writeDistDir();
        _writer.writeAttributeString("algn", "ctr");
        writeColor();
        _writer.writeEndElement();
        _writer.writeEndElement();
    }

    private void writeColor() throws Exception {
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        String colorval = "808080";
        if (so.OptionsByID.containsKey(PropertyId.shadowColor))
            colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.shadowColor).op, so.firstAncestorWithType(), so);
         
        _writer.writeAttributeString("val", colorval);
        if (so.OptionsByID.containsKey(PropertyId.shadowOpacity) && so.OptionsByID.get(PropertyId.shadowOpacity).op != 65536)
        {
            _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("val", String.valueOf(Math.round(((double)so.OptionsByID.get(PropertyId.shadowOpacity).op / 65536 * 100000))));
            //we need the percentage of the opacity (65536 means 100%)
            _writer.writeEndElement();
        }
         
        _writer.writeEndElement();
    }

    private void writeDistDir() throws Exception {
        int distX = Utils.eMUToMasterCoord(0x6338);
        int distY = Utils.eMUToMasterCoord(0x6338);
        if (so.OptionsByID.containsKey(PropertyId.shadowOffsetX))
            distX = Utils.eMUToMasterCoord((int)so.OptionsByID.get(PropertyId.shadowOffsetX).op);
         
        if (so.OptionsByID.containsKey(PropertyId.shadowOffsetY))
            distY = Utils.eMUToMasterCoord((int)so.OptionsByID.get(PropertyId.shadowOffsetY).op);
         
        String dir = "18900000";
        if (distX < 0)
        {
            if (distY < 0)
            {
                dir = "13500000";
            }
            else
            {
                dir = "8100000";
            } 
        }
        else
        {
            if (distY < 0)
            {
                dir = "18900000";
            }
            else
            {
                dir = "2700000";
            } 
        } 
        if (distX < 0)
        {
            distX *= -1;
        }
         
        if (distY < 0)
        {
            distY *= -1;
        }
         
        int dist = Utils.masterCoordToEMU((int)Math.Round(Math.Sqrt(distX * distX + distY * distY)));
        _writer.writeAttributeString("dist", String.valueOf(dist));
        _writer.writeAttributeString("dir", dir);
    }

}


