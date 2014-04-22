//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:04 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BitmapBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DrawingGroup;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.MetafilePictBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Handout;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Note;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PPDrawingGroup;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.PresentationMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ShapeTreeMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
import DIaLOGIKa.b2xtranslator.Tools.FixedPointNumber;
import java.io.InputStream;
import java.util.HashMap;

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
public class FillMapping  extends AbstractOpenXmlMapping 
{
    protected ConversionContext _ctx;
    protected PresentationMapping<RegularContainer> _parentSlideMapping;
    public FillMapping(ConversionContext ctx, XmlWriter writer, PresentationMapping<RegularContainer> parentSlideMapping) throws Exception {
        super(writer);
        _ctx = ctx;
        _parentSlideMapping = parentSlideMapping;
    }

    public void apply(ShapeOptions so) throws Exception {
        RegularContainer slide = so.firstAncestorWithType();
        if (slide == null)
            slide = so.firstAncestorWithType();
         
        if (slide == null)
            slide = so.firstAncestorWithType();
         
        String colorval = "";
        String colorval2 = "";
        uint fillType = 0;
        if (so.OptionsByID.containsKey(PropertyId.fillType))
            fillType = so.OptionsByID.get(PropertyId.fillType).op;
         
        long __dummyScrutVar0 = fillType;
        if (__dummyScrutVar0.equals(0x0))
        {
            //solid
            String SchemeType = "";
            if (so.OptionsByID.containsKey(PropertyId.fillColor))
            {
                RefSupport<String> refVar___0 = new RefSupport<String>(SchemeType);
                colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillColor).op,(RegularContainer)slide,so,refVar___0);
                SchemeType = refVar___0.getValue();
            }
            else
            {
                colorval = "FFFFFF";
            } 
            //TODO: find out which color to use in this case
            _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
            if (SchemeType.length() == 0)
            {
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", colorval);
            }
            else
            {
                _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", SchemeType);
            } 
            if (so.OptionsByID.containsKey(PropertyId.fillOpacity) && so.OptionsByID.get(PropertyId.fillOpacity).op != 65536)
            {
                _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", String.valueOf(Math.round(((double)so.OptionsByID.get(PropertyId.fillOpacity).op / 65536 * 100000))));
                //we need the percentage of the opacity (65536 means 100%)
                _writer.writeEndElement();
            }
             
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        else if (__dummyScrutVar0.equals(0x1))
        {
            //pattern
            uint blipIndex1 = so.OptionsByID.get(PropertyId.fillBlip).op;
            DrawingGroup gr1 = (DrawingGroup)this._ctx.getPpt().DocumentRecord.firstChildWithType().Children[0];
            BlipStoreEntry bse1 = (BlipStoreEntry)gr1.firstChildWithType().Children[(int)blipIndex1 - 1];
            BitmapBlip b1 = (BitmapBlip)_ctx.getPpt().PicturesContainer._pictures.get(bse1.foDelay);
            _writer.WriteStartElement("a", "pattFill", OpenXmlNamespaces.DrawingML);
            _writer.WriteAttributeString("prst", Utils.getPrstForPatternCode(b1.m_bTag));
            //Utils.getPrstForPattern(blipNamePattern));
            _writer.WriteStartElement("a", "fgClr", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("val", Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillColor).op,slide,so));
            _writer.writeEndElement();
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "bgClr", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            if (so.OptionsByID.containsKey(PropertyId.fillBackColor))
            {
                colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillBackColor).op,slide,so);
            }
            else
            {
                colorval = "ffffff";
            } 
            //TODO: find out which color to use in this case
            _writer.writeAttributeString("val", colorval);
            _writer.writeEndElement();
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        else //texture
        if (__dummyScrutVar0.equals(0x2) || __dummyScrutVar0.equals(0x3))
        {
            //picture
            uint blipIndex = 0;
            if (so.OptionsByID.containsKey(PropertyId.fillBlip))
            {
                blipIndex = so.OptionsByID.get(PropertyId.fillBlip).op;
            }
            else
            {
                blipIndex = so.OptionsByID.get(PropertyId.Pib).op;
            } 
            //string blipName = Encoding.UTF8.GetString(so.OptionsByID[ShapeOptions.PropertyId.fillBlipName].opComplex);
            String rId = "";
            DrawingGroup gr = (DrawingGroup)this._ctx.getPpt().DocumentRecord.firstChildWithType().Children[0];
            if (blipIndex <= gr.firstChildWithType().Children.Count)
            {
                BlipStoreEntry bse = (BlipStoreEntry)gr.firstChildWithType().Children[(int)blipIndex - 1];
                if (_ctx.getPpt().PicturesContainer._pictures.ContainsKey(bse.foDelay))
                {
                    DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = _ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
                    ImagePart imgPart = null;
                    if (rec instanceof BitmapBlip)
                    {
                        BitmapBlip b = (BitmapBlip)_ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
                        imgPart = _parentSlideMapping.targetPart.addImagePart(ShapeTreeMapping.getImageType(b.TypeCode));
                        imgPart.setTargetDirectory("..\\media");
                        InputStream outStream = imgPart.getStream();
                        outStream.write(b.m_pvBits,0,b.m_pvBits.length);
                    }
                    else
                    {
                        MetafilePictBlip b = (MetafilePictBlip)_ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
                        imgPart = _parentSlideMapping.targetPart.addImagePart(ShapeTreeMapping.getImageType(b.TypeCode));
                        imgPart.setTargetDirectory("..\\media");
                        InputStream outStream = imgPart.getStream();
                        byte[] decompressed = b.decrompress();
                        outStream.write(decompressed,0,decompressed.length);
                    } 
                    rId = imgPart.getRelIdToString();
                    _writer.WriteStartElement("a", "blipFill", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("dpi", "0");
                    _writer.writeAttributeString("rotWithShape", "1");
                    _writer.WriteStartElement("a", "blip", OpenXmlNamespaces.DrawingML);
                    _writer.WriteAttributeString("r", "embed", OpenXmlNamespaces.Relationships, rId);
                    _writer.writeEndElement();
                    _writer.WriteElementString("a", "srcRect", OpenXmlNamespaces.DrawingML, "");
                    if (fillType == 0x3)
                    {
                        _writer.WriteStartElement("a", "stretch", OpenXmlNamespaces.DrawingML);
                        _writer.WriteElementString("a", "fillRect", OpenXmlNamespaces.DrawingML, "");
                        _writer.writeEndElement();
                    }
                    else
                    {
                        _writer.WriteStartElement("a", "tile", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("tx", "0");
                        _writer.writeAttributeString("ty", "0");
                        _writer.writeAttributeString("sx", "100000");
                        _writer.writeAttributeString("sy", "100000");
                        _writer.writeAttributeString("flip", "none");
                        _writer.writeAttributeString("algn", "tl");
                        _writer.writeEndElement();
                    } 
                    _writer.writeEndElement();
                }
                 
            }
             
        }
        else //shade
        //shadecenter
        if (__dummyScrutVar0.equals(0x4) || __dummyScrutVar0.equals(0x5) || __dummyScrutVar0.equals(0x6))
        {
            //shadeshape
            _writer.WriteStartElement("a", "gradFill", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("rotWithShape", "1");
            _writer.WriteStartElement("a", "gsLst", OpenXmlNamespaces.DrawingML);
            boolean useFillAndBack = true;
            if (so.OptionsByID.containsKey(PropertyId.fillShadeColors))
            {
                byte[] colors = so.OptionsByID.get(PropertyId.fillShadeColors).opComplex;
                if (colors.length > 0)
                {
                    useFillAndBack = false;
                    DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry type = so.OptionsByID.get(PropertyId.fillShadeType);
                    UInt16 nElems = System.BitConverter.ToUInt16(colors, 0);
                    UInt16 nElemsAlloc = System.BitConverter.ToUInt16(colors, 2);
                    UInt16 cbElem = System.BitConverter.ToUInt16(colors, 4);
                    CSList<String> positions = new CSList<String>();
                    UInt16 __dummyScrutVar1 = nElems;
                    if (__dummyScrutVar1.equals(1) || __dummyScrutVar1.equals(2) || __dummyScrutVar1.equals(3) || __dummyScrutVar1.equals(4) || __dummyScrutVar1.equals(5))
                    {
                        positions.add("0");
                        positions.add("30000");
                        positions.add("65000");
                        positions.add("90000");
                        positions.add("100000");
                    }
                    else
                    {
                        positions.add("0");
                        positions.add("8000");
                        positions.add("13000");
                        positions.add("21000");
                        positions.add("52000");
                        positions.add("56000");
                        positions.add("58000");
                        positions.add("71000");
                        positions.add("94000");
                        positions.add("100000");
                    } 
                    String[] alphas = new String[nElems];
                    if (so.OptionsByID.containsKey(PropertyId.fillOpacity))
                    {
                        double end = Math.round(((double)so.OptionsByID.get(PropertyId.fillOpacity).op / 65536 * 100000));
                        double start = Math.round(((double)so.OptionsByID.get(PropertyId.fillBackOpacity).op / 65536 * 100000));
                        alphas[0] = String.valueOf(start);
                        for (int i = 1;i < nElems - 1;i++)
                        {
                            alphas[i] = String.valueOf(Math.round(start + (end - start) / 3 * i));
                        }
                        //alphas[1] = Math.Round(start + (end - start) / 3).ToString();
                        //alphas[2] = Math.Round(start + (end - start) / 3 * 2).ToString();
                        //alphas[3] = Math.Round(start + (end - start) / 3 * 3).ToString();
                        alphas[nElems - 1] = String.valueOf(end);
                    }
                     
                    for (int i = 0;i < nElems * cbElem;i += cbElem)
                    {
                        colorval = Utils.getRGBColorFromOfficeArtCOLORREF(System.BitConverter.ToUInt32(colors, 6 + i), slide, so);
                        _writer.WriteStartElement("a", "gs", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("pos", positions.get(i / cbElem));
                        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("val", colorval);
                        if (so.OptionsByID.containsKey(PropertyId.fillOpacity) && so.OptionsByID.get(PropertyId.fillOpacity).op != 65536)
                        {
                            _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
                            _writer.writeAttributeString("val", alphas[i / cbElem]);
                            //we need the percentage of the opacity (65536 means 100%)
                            _writer.writeEndElement();
                        }
                         
                        _writer.writeEndElement();
                        _writer.writeEndElement();
                    }
                }
                 
            }
             
            if (useFillAndBack)
            {
                colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillColor).op,slide,so);
                _writer.WriteStartElement("a", "gs", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("pos", "0");
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", colorval);
                if (so.OptionsByID.containsKey(PropertyId.fillOpacity) && so.OptionsByID.get(PropertyId.fillOpacity).op != 65536)
                {
                    _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf(Math.round(((double)so.OptionsByID.get(PropertyId.fillOpacity).op / 65536 * 100000))));
                    //we need the percentage of the opacity (65536 means 100%)
                    _writer.writeEndElement();
                }
                 
                _writer.writeEndElement();
                _writer.writeEndElement();
                if (so.OptionsByID.containsKey(PropertyId.fillBackColor))
                {
                    colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillBackColor).op,slide,so);
                }
                else
                {
                    if (so.OptionsByID.containsKey(PropertyId.shadowColor))
                    {
                        colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.shadowColor).op,slide,so);
                    }
                    else
                    {
                    } 
                } 
                //use filColor
                _writer.WriteStartElement("a", "gs", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("pos", "100000");
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", colorval);
                if (so.OptionsByID.containsKey(PropertyId.fillBackOpacity) && so.OptionsByID.get(PropertyId.fillBackOpacity).op != 65536)
                {
                    _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf(Math.round(((double)so.OptionsByID.get(PropertyId.fillBackOpacity).op / 65536 * 100000))));
                    //we need the percentage of the opacity (65536 means 100%)
                    _writer.writeEndElement();
                }
                 
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
             
            _writer.writeEndElement();
            //gsLst
            long __dummyScrutVar2 = fillType;
            if (__dummyScrutVar2.equals(0x5) || __dummyScrutVar2.equals(0x6))
            {
                _writer.WriteStartElement("a", "path", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("path", "shape");
                _writer.WriteStartElement("a", "fillToRect", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("l", "50000");
                _writer.writeAttributeString("t", "50000");
                _writer.writeAttributeString("r", "50000");
                _writer.writeAttributeString("b", "50000");
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
            else
            {
                //path
                _writer.WriteStartElement("a", "path", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("path", "rect");
                _writer.WriteStartElement("a", "fillToRect", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("r", "100000");
                _writer.writeAttributeString("b", "100000");
                _writer.writeEndElement();
                _writer.writeEndElement();
            } 
            //path
            _writer.writeEndElement();
        }
        else //gradFill
        if (__dummyScrutVar0.equals(0x7))
        {
            //shadescale
            _writer.WriteStartElement("a", "gradFill", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("rotWithShape", "1");
            _writer.WriteStartElement("a", "gsLst", OpenXmlNamespaces.DrawingML);
            double angle = 90;
            boolean switchColors = false;
            if (so.OptionsByID.containsKey(PropertyId.fillAngle))
            {
                if (so.OptionsByID.get(PropertyId.fillAngle).op != 0)
                {
                    byte[] bytes = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.fillAngle).op);
                    int integral = BitConverter.ToInt16(bytes, 0);
                    uint fractional = BitConverter.ToUInt16(bytes, 2);
                    double result = integral + ((double)fractional / (double)65536);
                    angle = 65536 - fractional;
                    //I have no idea why this works!!
                    angle = angle - 90;
                    if (angle < 0)
                    {
                        angle += 360;
                        switchColors = true;
                    }
                     
                }
                 
            }
             
            HashMap<Integer,String> shadeColorsDic = new HashMap<Integer,String>();
            CSList<String> shadeColors = new CSList<String>();
            if (so.OptionsByID.containsKey(PropertyId.fillShadeColors) && so.OptionsByID.get(PropertyId.fillShadeColors).opComplex.length > 0)
            {
                uint length = so.OptionsByID.get(PropertyId.fillShadeColors).op;
                //An IMsoArray record that specifies colors and their relative positions.
                //Each element of the array contains an OfficeArtCOLORREF record color and a FixedPoint, as specified in [MS-OSHARED]
                //section 2.2.1.6, that specifies its relative position along the gradient vector.
                byte[] data = so.OptionsByID.get(PropertyId.fillShadeColors).opComplex;
                int pos = 0;
                String colval;
                FixedPointNumber fixedpoint;
                UInt16 nElems = BitConverter.ToUInt16(data, pos);
                pos += 2;
                UInt16 nElemsAlloc = BitConverter.ToUInt16(data, pos);
                pos += 2;
                UInt16 cbElem = BitConverter.ToUInt16(data, pos);
                pos += 2;
                if (cbElem == 0xFFF0)
                {
                }
                else
                {
                    while (pos < length)
                    {
                        //If this value is 0xFFF0 then this record is an array of truncated 8 byte elements. Only the 4 low-order bytes are recorded. Each element's 4 high-order bytes equal 0x00000000 and each element's 4 low-order bytes are contained in data.
                        colval = Utils.getRGBColorFromOfficeArtCOLORREF(BitConverter.ToUInt32(data, pos), slide, so);
                        pos += 4;
                        fixedpoint = new FixedPointNumber(BitConverter.ToUInt16(data, pos), BitConverter.ToUInt16(data, pos + 2));
                        shadeColors.add(0, colval);
                        pos += 4;
                    }
                } 
            }
            else
            {
                boolean switchcolors = false;
                if (switchColors & so.OptionsByID.containsKey(PropertyId.fillBackColor))
                {
                    colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillBackColor).op,slide,so);
                }
                else
                {
                    if (so.OptionsByID.containsKey(PropertyId.fillColor))
                    {
                        colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillColor).op,slide,so);
                    }
                    else
                    {
                        colorval = "FFFFFF";
                        //TODO: find out which color to use in this case
                        switchcolors = true;
                    } 
                } 
                if (switchColors | !so.OptionsByID.containsKey(PropertyId.fillBackColor))
                {
                    colorval2 = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillColor).op,slide,so);
                }
                else
                {
                    colorval2 = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillBackColor).op,slide,so);
                } 
                if (switchcolors)
                {
                    //this is a workaround for a bug. Further analysis necessarry
                    String dummy = colorval;
                    colorval = colorval2;
                    colorval2 = dummy;
                }
                 
                shadeColors.add(colorval);
                shadeColors.add(colorval2);
            } 
            int gspos;
            String col;
            for (int i = 0;i < shadeColors.size();i++)
            {
                col = shadeColors.get(i);
                if (i == 0)
                {
                    gspos = 0;
                }
                else if (i == shadeColors.size() - 1)
                {
                    gspos = 100000;
                }
                else
                {
                    gspos = i * 100000 / shadeColors.size();
                }  
                _writer.WriteStartElement("a", "gs", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("pos", String.valueOf(gspos));
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", col);
                if (so.OptionsByID.containsKey(PropertyId.fillOpacity) && so.OptionsByID.get(PropertyId.fillOpacity).op != 65536)
                {
                    _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf(Math.round(((double)so.OptionsByID.get(PropertyId.fillOpacity).op / 65536 * 100000))));
                    //we need the percentage of the opacity (65536 means 100%)
                    _writer.writeEndElement();
                }
                 
                if (so.OptionsByID.containsKey(PropertyId.fillShadeType))
                {
                    uint flags = so.OptionsByID.get(PropertyId.fillShadeType).op;
                    boolean none = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1);
                    boolean gamma = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1 << 1);
                    boolean sigma = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1 << 2);
                    boolean band = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1 << 3);
                    boolean onecolor = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1 << 4);
                    if (gamma)
                        _writer.WriteElementString("a", "gamma", OpenXmlNamespaces.DrawingML, "");
                     
                    if (band)
                    {
                        _writer.WriteStartElement("a", "shade", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("val", "37255");
                        _writer.writeEndElement();
                    }
                     
                    if (gamma)
                        _writer.WriteElementString("a", "invGamma", OpenXmlNamespaces.DrawingML, "");
                     
                }
                 
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
            /**
            * /new colorval
            */
            //_writer.WriteStartElement("a", "gs", OpenXmlNamespaces.DrawingML);
            //_writer.WriteAttributeString("pos", "100000");
            //_writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            //_writer.WriteAttributeString("val", colorval2);
            //if (so.OptionsByID.ContainsKey(ShapeOptions.PropertyId.fillBackOpacity))
            //{
            //    _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
            //    _writer.WriteAttributeString("val", Math.Round(((decimal)so.OptionsByID[ShapeOptions.PropertyId.fillBackOpacity].op / 65536 * 100000)).ToString()); //we need the percentage of the opacity (65536 means 100%)
            //    _writer.WriteEndElement();
            //}
            //_writer.WriteEndElement();
            //_writer.WriteEndElement();
            _writer.writeEndElement();
            //gsLst
            _writer.WriteStartElement("a", "lin", OpenXmlNamespaces.DrawingML);
            angle *= 60000;
            if (angle > 5400000)
                angle = 5400000;
             
            _writer.writeAttributeString("ang", String.valueOf(angle));
            _writer.writeAttributeString("scaled", "1");
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        else //shadetitle
        if (__dummyScrutVar0.equals(0x8) || __dummyScrutVar0.equals(0x9))
        {
        }
              
    }

}


//background