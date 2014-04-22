//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:22 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Globalization.NumberStyles;
import CS2JNet.System.IntegerSupport;
import CS2JNet.System.NotImplementedException;
import CS2JNet.System.NumberSupport;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlDocument;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shape;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ColorSchemeAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PlaceholderEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideLayoutType;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideSizeType;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor.ByteOrder;
import java.io.InputStream;

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
public class Utils   
{
    private static final double MC_PER_EMU = 1587.5;
    public static int masterCoordToEMU(int mc) throws Exception {
        return ((int)((mc * MC_PER_EMU)));
    }

    public static int eMUToMasterCoord(int emu) throws Exception {
        return ((int)((emu / MC_PER_EMU)));
    }

    public static XmlDocument getDefaultDocument(String filename) throws Exception {
        Assembly a = null /* getExecutingAssembly() */;
        InputStream s = a.GetManifestResourceStream(String.format(StringSupport.CSFmtStrToJFmtStr("{0}.Defaults.{1}.xml"),Utils.class.Namespace,filename));
        XmlDocument doc = new XmlDocument();
        doc.load(s);
        return doc;
    }

    public static String slideSizeTypeToXMLValue(SlideSizeType sst) throws Exception {
        // OOXML Spec � 4.8.22
        switch(sst)
        {
            case A4Paper: 
                return "A4";
            case Banner: 
                return "banner";
            case Custom: 
                return "custom";
            case LetterSizedPaper: 
                return "letter";
            case OnScreen: 
                return "screen4x3";
            case Overhead: 
                return "overhead";
            case Size35mm: 
                return "35mm";
            default: 
                throw new NotImplementedException(String.format(StringSupport.CSFmtStrToJFmtStr("Can't convert slide size type {0} to XML value"),sst));
        
        }
    }

    public static String placeholderIdToXMLValue(PlaceholderEnum pid) throws Exception {
        switch(pid)
        {
            case MasterDate: 
                return "dt";
            case MasterSlideNumber: 
                return "sldNum";
            case MasterFooter: 
                return "ftr";
            case MasterHeader: 
                return "hdr";
            case MasterTitle: 
            case Title: 
                return "title";
            case MasterBody: 
            case Body: 
            case NotesBody: 
            case MasterNotesBody: 
                return "body";
            case MasterCenteredTitle: 
            case CenteredTitle: 
                return "ctrTitle";
            case MasterSubtitle: 
            case Subtitle: 
                return "subTitle";
            case ClipArt: 
                return "clipArt";
            case Graph: 
                return "chart";
            case OrganizationChart: 
                return "dgm";
            case MediaClip: 
                return "media";
            case Table: 
                return "tbl";
            case NotesSlideImage: 
            case MasterNotesSlideImage: 
                return "sldImg";
            default: 
                throw new NotImplementedException("Don't know how to map placeholder id " + pid);
        
        }
    }

    public static String slideLayoutTypeToFilename(SlideLayoutType type, PlaceholderEnum[] placeholderTypes) throws Exception {
        switch(type)
        {
            case BigObject: 
                return "objOnly";
            case Blank: 
                return "blank";
            case FourObjects: 
                return "fourObj";
            case TitleAndBody: 
                {
                    PlaceholderEnum body = placeholderTypes[1];
                    if (body == PlaceholderEnum.Table)
                    {
                        return "tbl";
                    }
                    else if (body == PlaceholderEnum.OrganizationChart)
                    {
                        return "dgm";
                    }
                    else if (body == PlaceholderEnum.Graph)
                    {
                        return "chart";
                    }
                    else
                    {
                        return "obj";
                    }   
                }
            case TitleOnly: 
                return "titleOnly";
            case TitleSlide: 
                return "title";
            case TwoColumnsAndTitle: 
                {
                    PlaceholderEnum leftType = placeholderTypes[1];
                    PlaceholderEnum rightType = placeholderTypes[2];
                    if (leftType == PlaceholderEnum.Body && rightType == PlaceholderEnum.Object)
                    {
                        return "txAndObj";
                    }
                    else if (leftType == PlaceholderEnum.Object && rightType == PlaceholderEnum.Body)
                    {
                        return "objAndTx";
                    }
                    else if (leftType == PlaceholderEnum.Body && rightType == PlaceholderEnum.ClipArt)
                    {
                        return "txAndClipArt";
                    }
                    else if (leftType == PlaceholderEnum.ClipArt && rightType == PlaceholderEnum.Body)
                    {
                        return "clipArtAndTx";
                    }
                    else if (leftType == PlaceholderEnum.Body && rightType == PlaceholderEnum.Graph)
                    {
                        return "txAndChart";
                    }
                    else if (leftType == PlaceholderEnum.Graph && rightType == PlaceholderEnum.Body)
                    {
                        return "chartAndTx";
                    }
                    else if (leftType == PlaceholderEnum.Body && rightType == PlaceholderEnum.MediaClip)
                    {
                        return "txAndMedia";
                    }
                    else if (leftType == PlaceholderEnum.MediaClip && rightType == PlaceholderEnum.Body)
                    {
                        return "mediaAndTx";
                    }
                    else
                    {
                        return "twoObj";
                    }        
                }
            case TwoColumnsLeftTwoRows: 
                {
                    PlaceholderEnum rightType = placeholderTypes[2];
                    if (rightType == PlaceholderEnum.Object)
                    {
                        return "twoObjAndObj";
                    }
                    else if (rightType == PlaceholderEnum.Body)
                    {
                        return "twoObjAndTx";
                    }
                    else
                    {
                        throw new NotImplementedException(String.format(StringSupport.CSFmtStrToJFmtStr("Don't know how to map TwoColumnLeftTwoRows with rightType = {0}"),rightType));
                    }  
                }
            case TwoColumnsRightTwoRows: 
                {
                    PlaceholderEnum leftType = placeholderTypes[1];
                    if (leftType == PlaceholderEnum.Object)
                    {
                        return "objAndTwoObj";
                    }
                    else if (leftType == PlaceholderEnum.Body)
                    {
                        return "txAndTwoObj";
                    }
                    else
                    {
                        throw new NotImplementedException(String.format(StringSupport.CSFmtStrToJFmtStr("Don't know how to map TwoColumnRightTwoRows with leftType = {0}"),leftType));
                    }  
                }
            case TwoRowsAndTitle: 
                {
                    PlaceholderEnum topType = placeholderTypes[1];
                    PlaceholderEnum bottomType = placeholderTypes[2];
                    if (topType == PlaceholderEnum.Body && bottomType == PlaceholderEnum.Object)
                    {
                        return "txOverObj";
                    }
                    else if (topType == PlaceholderEnum.Object && bottomType == PlaceholderEnum.Body)
                    {
                        return "objOverTx";
                    }
                    else
                    {
                        throw new NotImplementedException(String.format(StringSupport.CSFmtStrToJFmtStr("Don't know how to map TwoRowsAndTitle with topType = {0} and bottomType = {1}"),topType,bottomType));
                    }  
                }
            case TwoRowsTopTwoColumns: 
                return "twoObjOverTx";
            default: 
                throw new NotImplementedException("Don't know how to map slide layout type " + type);
        
        }
    }

    public static String getRGBColorFromOfficeArtCOLORREF(uint value, RegularContainer slide, DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions so) throws Exception {
        String dummy = "";
        RefSupport<String> refVar___0 = new RefSupport<String>(dummy);
        String resVar___0 = getRGBColorFromOfficeArtCOLORREF(value,slide,so,refVar___0);
        dummy = refVar___0.getValue();
        return resVar___0;
    }

    public static String getRGBColorFromOfficeArtCOLORREF(uint value, RegularContainer slide, DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions so, RefSupport<String> SchemeType) throws Exception {
        byte[] bytes = BitConverter.GetBytes(value);
        Boolean fPaletteIndex = (bytes[3] & 1) != 0;
        Boolean fPaletteRGB = (bytes[3] & (1 << 1)) != 0;
        Boolean fSystemRGB = (bytes[3] & (1 << 2)) != 0;
        Boolean fSchemeIndex = (bytes[3] & (1 << 3)) != 0;
        Boolean fSysIndex = (bytes[3] & (1 << 4)) != 0;
        CSList<ColorSchemeAtom> colors = slide.allChildrenWithType();
        ColorSchemeAtom MasterScheme = null;
        for (ColorSchemeAtom color : colors)
        {
            if (color.Instance == 1)
                MasterScheme = color;
             
        }
        if (fSysIndex)
        {
            UInt16 val = BitConverter.ToUInt16(bytes, 0);
            String result = "";
            UInt16 __dummyScrutVar3 = val & 0x00ff;
            if (__dummyScrutVar3.equals(0xF0))
            {
                //shape fill color
                if (so.OptionsByID.containsKey(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.fillColor))
                {
                    result = getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.fillColor).op,slide,so);
                }
                else
                {
                    result = new RGBColor(MasterScheme.Fills,ByteOrder.RedFirst).SixDigitHexCode;
                } 
            }
            else //TODO: find out which color to use in this case
            if (__dummyScrutVar3.equals(0xF1))
            {
                //shape line color if it is a line else shape fill color TODO!!
                if (so.firstAncestorWithType().<Shape>FirstChildWithType().Instance == 1)
                {
                    if (so.OptionsByID.containsKey(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.fillColor))
                    {
                        result = getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.fillColor).op,slide,so);
                    }
                    else
                    {
                        result = new RGBColor(MasterScheme.Fills,ByteOrder.RedFirst).SixDigitHexCode;
                    } 
                }
                else
                {
                    //TODO: find out which color to use in this case
                    if (so.OptionsByID.containsKey(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.lineColor))
                    {
                        result = getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.lineColor).op,slide,so);
                    }
                    else
                    {
                        result = new RGBColor(MasterScheme.TextAndLines,ByteOrder.RedFirst).SixDigitHexCode;
                    } 
                } 
            }
            else //TODO: find out which color to use in this case
            if (__dummyScrutVar3.equals(0xF2))
            {
                //shape line color
                if (so.OptionsByID.containsKey(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.lineColor))
                {
                    result = getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.lineColor).op,slide,so);
                }
                else
                {
                    result = new RGBColor(MasterScheme.TextAndLines,ByteOrder.RedFirst).SixDigitHexCode;
                } 
            }
            else //TODO: find out which color to use in this case
            if (__dummyScrutVar3.equals(0xF3))
            {
                //shape shadow color
                result = getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.shadowColor).op,slide,so);
            }
            else //current or last used color
            if (__dummyScrutVar3.equals(0xF4) || __dummyScrutVar3.equals(0xF5))
            {
                //shape fill background color
                result = getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.fillBackColor).op,slide,so);
            }
            else if (__dummyScrutVar3.equals(0xF6))
            {
                //shape line background color
                result = getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.lineBackColor).op,slide,so);
            }
            else if (__dummyScrutVar3.equals(0xF7))
            {
                //shape fill color if shape contains a fill else line color
                result = getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.fillColor).op,slide,so);
            }
            else if (__dummyScrutVar3.equals(0xFF))
            {
                return "";
            }
                    
            //undefined
            if (result.length() == 0)
                return "";
             
            Integer red = IntegerSupport.parse(result.substring(0, (0) + (2)), NumberStyles.getHexNumber());
            Integer green = IntegerSupport.parse(result.substring(2, (2) + (2)), NumberStyles.getHexNumber());
            Integer blue = IntegerSupport.parse(result.substring(4, (4) + (2)), NumberStyles.getHexNumber());
            Integer v = (int)bytes[2];
            Integer res;
            return result;
        }
         
        //switch (val & 0xff00)
        //{
        //    case 0x100:
        //        if (blue == 0xff) return result;
        //        if (blue == 0x00) return "000000";
        //        res = int.Parse(result, System.Globalization.NumberStyles.HexNumber);
        //        if (!so.OptionsByID.ContainsKey(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.ShadowStyleBooleanProperties))
        //        res -= v; //this is wrong for shadow17
        //        if (res < 0) res = 0;
        //        return res.ToString("X").PadLeft(6, '0');
        //    case 0x200:
        //        if (blue == 0xff) return result;
        //        if (blue == 0x00) return "FFFFFF";
        //        res = int.Parse(result, System.Globalization.NumberStyles.HexNumber);
        //        res += v;
        //        return res.ToString("X").PadLeft(6, '0');
        //    case 0x300:
        //        red += v;
        //        green += v;
        //        blue += v;
        //        if (red > 0xff) red = 0xff;
        //        if (green > 0xff) green = 0xff;
        //        if (blue > 0xff) blue = 0xff;
        //        return red.ToString("X").PadLeft(2, '0') + green.ToString("X").PadLeft(2, '0') + blue.ToString("X").PadLeft(2, '0');
        //    case 0x400:
        //        red -= v;
        //        green -= v;
        //        blue -= v;
        //        if (red < 0) red = 0x0;
        //        if (green < 0) green = 0x0;
        //        if (blue < 0) blue = 0x0;
        //        return red.ToString("X").PadLeft(2, '0') + green.ToString("X").PadLeft(2, '0') + blue.ToString("X").PadLeft(2, '0');
        //    case 0x500:
        //        red = v - red;
        //        green = v - green;
        //        blue = v - blue;
        //        if (red < 0) red = 0x0;
        //        if (green < 0) green = 0x0;
        //        if (blue < 0) blue = 0x0;
        //        return red.ToString("X").PadLeft(2, '0') + green.ToString("X").PadLeft(2, '0') + blue.ToString("X").PadLeft(2, '0');
        //    default:
        //        break;
        //}
        if (fSchemeIndex)
        {
            //red is the index to the color scheme
            //List<ColorSchemeAtom> colors = slide.AllChildrenWithType<ColorSchemeAtom>();
            //ColorSchemeAtom MasterScheme = null;
            //foreach (ColorSchemeAtom color in colors)
            //{
            //    if (color.Instance == 1) MasterScheme = color;
            //}
            byte __dummyScrutVar4 = bytes[0];
            if (__dummyScrutVar4.equals(0x00))
            {
                //background
                SchemeType.setValue("bg1");
                return new RGBColor(MasterScheme.Background,ByteOrder.RedFirst).SixDigitHexCode;
            }
            else if (__dummyScrutVar4.equals(0x01))
            {
                //text
                SchemeType.setValue("tx1");
                return new RGBColor(MasterScheme.TextAndLines,ByteOrder.RedFirst).SixDigitHexCode;
            }
            else if (__dummyScrutVar4.equals(0x02))
            {
                return new RGBColor(MasterScheme.Shadows,ByteOrder.RedFirst).SixDigitHexCode;
            }
            else //shadow
            if (__dummyScrutVar4.equals(0x03))
            {
                return new RGBColor(MasterScheme.TitleText,ByteOrder.RedFirst).SixDigitHexCode;
            }
            else //title
            if (__dummyScrutVar4.equals(0x04))
            {
                //fill
                SchemeType.setValue("accent1");
                return new RGBColor(MasterScheme.Fills,ByteOrder.RedFirst).SixDigitHexCode;
            }
            else if (__dummyScrutVar4.equals(0x05))
            {
                //accent1
                SchemeType.setValue("accent2");
                return new RGBColor(MasterScheme.Accent,ByteOrder.RedFirst).SixDigitHexCode;
            }
            else if (__dummyScrutVar4.equals(0x06))
            {
                //accent2
                SchemeType.setValue("hlink");
                return new RGBColor(MasterScheme.AccentAndHyperlink,ByteOrder.RedFirst).SixDigitHexCode;
            }
            else if (__dummyScrutVar4.equals(0x07))
            {
                return new RGBColor(MasterScheme.AccentAndFollowedHyperlink,ByteOrder.RedFirst).SixDigitHexCode;
            }
            else //accent3
            if (__dummyScrutVar4.equals(0xFE))
            {
                return StringSupport.PadLeft(NumberSupport.format(bytes[0], "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(bytes[1], "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(bytes[3], "X"), 2, '0');
            }
            else //sRGB
            if (__dummyScrutVar4.equals(0xFF))
            {
            }
                      
        }
         
        return StringSupport.PadLeft(NumberSupport.format(bytes[0], "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(bytes[1], "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(bytes[2], "X"), 2, '0');
    }

    //undefined
    //public static string getPrstForPattern(string blipNamePattern)
    //{
    //    switch (blipNamePattern)
    //    {
    //        case "5%":
    //            return  "pct5";
    //        case "10%":
    //            return  "pct10";
    //        case "20%":
    //            return  "pct20";
    //        case "25%":
    //            return  "pct25";
    //        case "30%":
    //            return  "pct30";
    //        case "40%":
    //            return  "pct40";
    //        case "50%":
    //            return  "pct50";
    //        case "60%":
    //            return  "pct60";
    //        case "70%":
    //            return  "pct70";
    //        case "75%":
    //            return  "pct75";
    //        case "80%":
    //            return  "pct80";
    //        case "90%":
    //            return  "pct90";
    //        case "dark horizontal":
    //            return  "dkHorz";
    //        case "dark vertical":
    //            return  "dkVert";
    //        case "dark downward diagonal":
    //            return  "dkDnDiag";
    //        case "dark upward diagonal":
    //            return  "dkUpDiag";
    //        case "dashed downward diagonal":
    //            return  "dashDnDiag";
    //        case "dashed horizontal":
    //            return  "dashHorz";
    //        case "dashed vertical":
    //            return  "dashVert";
    //        case "dashed upward diagonal":
    //            return  "dashUpDiag";
    //        case "diagonal brick":
    //            return  "diagBrick";
    //        case "divot":
    //            return  "divot";
    //        case "dotted grid":
    //            return  "dotGrid";
    //        case "dotted diamond":
    //            return  "dotDmnd";
    //        case "horizontal brick":
    //            return  "horzBrick";
    //        case "large checker board":
    //            return  "lgCheck";
    //        case "large confetti":
    //            return  "lgConfetti";
    //        case "large grid":
    //            return  "lgGrid";
    //        case "light downward diagonal":
    //            return  "ltDnDiag";
    //        case "light horizontal":
    //            return  "ltHorz";
    //        case "light upward diagonal":
    //            return  "ltUpDiag";
    //        case "light vertical":
    //            return  "ltVert";
    //        case "narrow horizontal":
    //            return  "narHorz";
    //        case "narrow vertical":
    //            return  "narVert";
    //        case "outlined diamond":
    //            return  "openDmnd";
    //        case "small confetti":
    //            return  "smConfetti";
    //        case "small checker board":
    //            return  "smCheck";
    //        case "small grid":
    //            return  "smGrid";
    //        case "solid diamond":
    //            return  "solidDmnd";
    //        case "plaid":
    //            return  "plaid";
    //        case "shingle":
    //            return  "shingle";
    //        case "sphere":
    //            return  "sphere";
    //        case "trellis":
    //            return  "trellis";
    //        case "wave":
    //            return  "wave";
    //        case "weave":
    //            return  "weave";
    //        case "wide downward diagonal":
    //            return  "wdDnDiag";
    //        case "wide upward diagonal":
    //            return  "wdUpDiag";
    //        case "zig zag":
    //            return  "zigZag";
    //        default:
    //            return  "zigZag";
    //    }
    //}
    public static String getPrstForPatternCode(int code) throws Exception {
        if (code == 0xC4)
            return "pct5";
         
        if (code == 0xC5)
            return "pct50";
         
        if (code == 0xC6)
            return "ltDnDiag";
         
        if (code == 0xC7)
            return "ltVert";
         
        if (code == 0xC8)
            return "dashDnDiag";
         
        if (code == 0xC9)
            return "zigZag";
         
        if (code == 0xCA)
            return "divot";
         
        if (code == 0xCB)
            return "smGrid";
         
        if (code == 0xCC)
            return "pct10";
         
        if (code == 0xCD)
            return "pct60";
         
        if (code == 0xCE)
            return "ltUpDiag";
         
        if (code == 0xCF)
            return "ltHorz";
         
        if (code == 0xD0)
            return "dashUpDiag";
         
        if (code == 0xD1)
            return "wave";
         
        if (code == 0xD2)
            return "dotGrid";
         
        if (code == 0xD3)
            return "lgGrid";
         
        if (code == 0xD4)
            return "pct20";
         
        if (code == 0xD5)
            return "pct70";
         
        if (code == 0xD6)
            return "dkDnDiag";
         
        if (code == 0xD7)
            return "narVert";
         
        if (code == 0xD8)
            return "dashHorz";
         
        if (code == 0xD9)
            return "diagBrick";
         
        if (code == 0xDA)
            return "dotDmnd";
         
        if (code == 0xDB)
            return "smCheck";
         
        if (code == 0xDC)
            return "pct25";
         
        if (code == 0xDD)
            return "pct75";
         
        if (code == 0xDE)
            return "dkUpDiag";
         
        if (code == 0xDF)
            return "narHorz";
         
        if (code == 0xE0)
            return "dashVert";
         
        if (code == 0xE1)
            return "horzBrick";
         
        if (code == 0xE2)
            return "shingle";
         
        if (code == 0xE3)
            return "lgCheck";
         
        if (code == 0xE4)
            return "pct30";
         
        if (code == 0xE5)
            return "pct80";
         
        if (code == 0xE6)
            return "wdDnDiag";
         
        if (code == 0xE7)
            return "dkVert";
         
        if (code == 0xE8)
            return "smConfetti";
         
        if (code == 0xE9)
            return "weave";
         
        if (code == 0xEA)
            return "trellis";
         
        if (code == 0xEB)
            return "openDmnd";
         
        if (code == 0xEC)
            return "pct40";
         
        if (code == 0xED)
            return "pct90";
         
        if (code == 0xEE)
            return "wdUpDiag";
         
        if (code == 0xEF)
            return "dkHorz";
         
        if (code == 0xF0)
            return "lgConfetti";
         
        if (code == 0xF1)
            return "plaid";
         
        if (code == 0xF2)
            return "sphere";
         
        if (code == 0xF3)
            return "solidDmnd";
         
        return "";
    }

    public static String getPrstForShape(uint shapeInstance) throws Exception {
        long __dummyScrutVar5 = shapeInstance;
        if (__dummyScrutVar5.equals(0x0))
        {
            return "";
        }
        else //NotPrimitive
        if (__dummyScrutVar5.equals(0x1))
        {
            return "rect";
        }
        else //Rectangle
        if (__dummyScrutVar5.equals(0x2))
        {
            return "roundRect";
        }
        else //RoundRectangle
        if (__dummyScrutVar5.equals(0x3))
        {
            return "ellipse";
        }
        else //ellipse
        if (__dummyScrutVar5.equals(0x4))
        {
            return "diamond";
        }
        else //diamond
        if (__dummyScrutVar5.equals(0x5))
        {
            return "triangle";
        }
        else //triangle
        if (__dummyScrutVar5.equals(0x6))
        {
            return "rtTriangle";
        }
        else //right triangle
        if (__dummyScrutVar5.equals(0x7))
        {
            return "parallelogram";
        }
        else //parallelogram
        if (__dummyScrutVar5.equals(0x8))
        {
            return "nonIsoscelesTrapezoid";
        }
        else //trapezoid
        if (__dummyScrutVar5.equals(0x9))
        {
            return "hexagon";
        }
        else //hexagon
        if (__dummyScrutVar5.equals(0xA))
        {
            return "octagon";
        }
        else //octagon
        if (__dummyScrutVar5.equals(0xB))
        {
            return "mathPlus";
        }
        else //Plus
        if (__dummyScrutVar5.equals(0xC))
        {
            return "star5";
        }
        else //Star
        //Arrow
        if (__dummyScrutVar5.equals(0xD) || __dummyScrutVar5.equals(0xE))
        {
            return "rightArrow";
        }
        else //ThickArrow
        if (__dummyScrutVar5.equals(0xF))
        {
            return "homePlate";
        }
        else //HomePlate
        if (__dummyScrutVar5.equals(0x10))
        {
            return "cube";
        }
        else //Cube
        if (__dummyScrutVar5.equals(0x11))
        {
            return "wedgeEllipseCallout";
        }
        else //Balloon
        if (__dummyScrutVar5.equals(0x12))
        {
            return "star16";
        }
        else //Seal
        if (__dummyScrutVar5.equals(0x13))
        {
            return "curvedConnector2";
        }
        else //Arc
        if (__dummyScrutVar5.equals(0x14))
        {
            return "line";
        }
        else //Line
        if (__dummyScrutVar5.equals(0x15))
        {
            return "plaque";
        }
        else //Plaque
        if (__dummyScrutVar5.equals(0x16))
        {
            return "can";
        }
        else //Cylinder
        if (__dummyScrutVar5.equals(0x17))
        {
            return "donut";
        }
        else //Donut
        //TextSimple
        //TextOctagon
        //TextHexagon
        //TextCurve
        //TextWave
        //TextRing
        //TextOnCurve
        if (__dummyScrutVar5.equals(0x18) || __dummyScrutVar5.equals(0x19) || __dummyScrutVar5.equals(0x1A) || __dummyScrutVar5.equals(0x1B) || __dummyScrutVar5.equals(0x1C) || __dummyScrutVar5.equals(0x1D) || __dummyScrutVar5.equals(0x1E) || __dummyScrutVar5.equals(0x1F))
        {
            return "";
        }
        else //TextOnRing
        if (__dummyScrutVar5.equals(0x20))
        {
            return "straightConnector1";
        }
        else //StraightConnector1
        if (__dummyScrutVar5.equals(0x21))
        {
            return "bentConnector2";
        }
        else //BentConnector2
        if (__dummyScrutVar5.equals(0x22))
        {
            return "bentConnector3";
        }
        else //BentConnector3
        if (__dummyScrutVar5.equals(0x23))
        {
            return "bentConnector4";
        }
        else //BentConnector4
        if (__dummyScrutVar5.equals(0x24))
        {
            return "bentConnector5";
        }
        else //BentConnector5
        if (__dummyScrutVar5.equals(0x25))
        {
            return "curvedConnector2";
        }
        else //CurvedConnector2
        if (__dummyScrutVar5.equals(0x26))
        {
            return "curvedConnector3";
        }
        else //CurvedConnector3
        if (__dummyScrutVar5.equals(0x27))
        {
            return "curvedConnector4";
        }
        else //CurvedConnector4
        if (__dummyScrutVar5.equals(0x28))
        {
            return "curvedConnector5";
        }
        else //CurvedConnector5
        if (__dummyScrutVar5.equals(0x29))
        {
            return "callout1";
        }
        else //Callout1
        if (__dummyScrutVar5.equals(0x2A))
        {
            return "callout2";
        }
        else //Callout2
        if (__dummyScrutVar5.equals(0x2B))
        {
            return "callout3";
        }
        else //Callout3
        if (__dummyScrutVar5.equals(0x2C))
        {
            return "accentCallout1";
        }
        else //AccentCallout1
        if (__dummyScrutVar5.equals(0x2D))
        {
            return "accentCallout2";
        }
        else //AccentCallout2
        if (__dummyScrutVar5.equals(0x2E))
        {
            return "accentCallout3";
        }
        else //AccentCallout3
        if (__dummyScrutVar5.equals(0x2F))
        {
            return "borderCallout1";
        }
        else //BorderCallout1
        if (__dummyScrutVar5.equals(0x30))
        {
            return "borderCallout2";
        }
        else //BorderCallout2
        if (__dummyScrutVar5.equals(0x31))
        {
            return "borderCallout3";
        }
        else //BorderCallout3
        if (__dummyScrutVar5.equals(0x32))
        {
            return "accentBorderCallout1";
        }
        else //AccentBorderCallout1
        if (__dummyScrutVar5.equals(0x33))
        {
            return "accentBorderCallout2";
        }
        else //accentBorderCallout2
        if (__dummyScrutVar5.equals(0x34))
        {
            return "accentBorderCallout3";
        }
        else //accentBorderCallout3
        if (__dummyScrutVar5.equals(0x35))
        {
            return "ribbon";
        }
        else //Ribbon
        if (__dummyScrutVar5.equals(0x36))
        {
            return "ribbon2";
        }
        else //Ribbon2
        if (__dummyScrutVar5.equals(0x37))
        {
            return "chevron";
        }
        else //Chevron
        if (__dummyScrutVar5.equals(0x38))
        {
            return "pentagon";
        }
        else //Pentagon
        if (__dummyScrutVar5.equals(0x39))
        {
            return "noSmoking";
        }
        else //noSmoking
        if (__dummyScrutVar5.equals(0x3A))
        {
            return "star8";
        }
        else //Seal8
        if (__dummyScrutVar5.equals(0x3B))
        {
            return "star16";
        }
        else //Seal16
        if (__dummyScrutVar5.equals(0x3C))
        {
            return "star32";
        }
        else //Seal32
        if (__dummyScrutVar5.equals(0x3D))
        {
            return "wedgeRectCallout";
        }
        else //WedgeRectCallout
        if (__dummyScrutVar5.equals(0x3E))
        {
            return "wedgeRoundRectCallout";
        }
        else //WedgeRRectCallout
        if (__dummyScrutVar5.equals(0x3F))
        {
            return "wedgeEllipseCallout";
        }
        else //WedgeEllipseCallout
        if (__dummyScrutVar5.equals(0x40))
        {
            return "wave";
        }
        else //Wave
        if (__dummyScrutVar5.equals(0x41))
        {
            return "foldedCorner";
        }
        else //FolderCorner
        if (__dummyScrutVar5.equals(0x42))
        {
            return "leftArrow";
        }
        else //LeftArrow
        if (__dummyScrutVar5.equals(0x43))
        {
            return "downArrow";
        }
        else //DownArrow
        if (__dummyScrutVar5.equals(0x44))
        {
            return "upArrow";
        }
        else //UpArrow
        if (__dummyScrutVar5.equals(0x45))
        {
            return "leftRightArrow";
        }
        else //LeftRightArrow
        if (__dummyScrutVar5.equals(0x46))
        {
            return "upDownArrow";
        }
        else //UpDownArrow
        if (__dummyScrutVar5.equals(0x47))
        {
            return "irregularSeal1";
        }
        else //IrregularSeal1
        if (__dummyScrutVar5.equals(0x48))
        {
            return "irregularSeal2";
        }
        else //IrregularSeal2
        if (__dummyScrutVar5.equals(0x49))
        {
            return "lightningBolt";
        }
        else //LightningBolt
        if (__dummyScrutVar5.equals(0x4A))
        {
            return "heart";
        }
        else //Heart
        if (__dummyScrutVar5.equals(0x4B))
        {
            return "rect";
        }
        else //PictureFrame
        //return "frame";
        if (__dummyScrutVar5.equals(0x4C))
        {
            return "quadArrow";
        }
        else //QuadArrow
        if (__dummyScrutVar5.equals(0x4D))
        {
            return "leftArrowCallout";
        }
        else //LeftArrowCallout
        if (__dummyScrutVar5.equals(0x4E))
        {
            return "rightArrowCallout";
        }
        else //RightArrowCallout
        if (__dummyScrutVar5.equals(0x4F))
        {
            return "upArrowCallout";
        }
        else //UpArrowCallout
        if (__dummyScrutVar5.equals(0x50))
        {
            return "downArrowCallout";
        }
        else //DownArrowCallout
        if (__dummyScrutVar5.equals(0x51))
        {
            return "leftRightArrowCallout";
        }
        else //LeftRightArrowCallout
        if (__dummyScrutVar5.equals(0x52))
        {
            return "upDownArrowCallout";
        }
        else //UpDownArrowCallout
        if (__dummyScrutVar5.equals(0x53))
        {
            return "quadArrowCallout";
        }
        else //QuadArrowCallout
        if (__dummyScrutVar5.equals(0x54))
        {
            return "bevel";
        }
        else //Bevel
        if (__dummyScrutVar5.equals(0x55))
        {
            return "leftBracket";
        }
        else //LeftBracket
        if (__dummyScrutVar5.equals(0x56))
        {
            return "rightBracket";
        }
        else //RightBracket
        if (__dummyScrutVar5.equals(0x57))
        {
            return "leftBrace";
        }
        else //LeftBrace
        if (__dummyScrutVar5.equals(0x58))
        {
            return "rightBrace";
        }
        else //RightBrace
        if (__dummyScrutVar5.equals(0x59))
        {
            return "leftUpArrow";
        }
        else //LeftUpArrow
        if (__dummyScrutVar5.equals(0x5A))
        {
            return "bentUpArrow";
        }
        else //BentUpArrow
        if (__dummyScrutVar5.equals(0x5B))
        {
            return "bentArrow";
        }
        else //BentArrow
        if (__dummyScrutVar5.equals(0x5C))
        {
            return "star24";
        }
        else //Seal24
        if (__dummyScrutVar5.equals(0x5D))
        {
            return "stripedRightArrow";
        }
        else //stripedRightArrow
        if (__dummyScrutVar5.equals(0x5E))
        {
            return "notchedRightArrow";
        }
        else //notchedRightArrow
        if (__dummyScrutVar5.equals(0x5F))
        {
            return "blockArc";
        }
        else //BlockArc
        if (__dummyScrutVar5.equals(0x60))
        {
            return "smileyFace";
        }
        else //SmileyFace
        if (__dummyScrutVar5.equals(0x61))
        {
            return "verticalScroll";
        }
        else //verticalScroll
        if (__dummyScrutVar5.equals(0x62))
        {
            return "horizontalScroll";
        }
        else //horizontalScroll
        //circularArrow
        if (__dummyScrutVar5.equals(0x63) || __dummyScrutVar5.equals(0x64))
        {
            return "circularArrow";
        }
        else //notchedCircularArrow
        if (__dummyScrutVar5.equals(0x65))
        {
            return "uturnArrow";
        }
        else //uturnArrow
        if (__dummyScrutVar5.equals(0x66))
        {
            return "curvedRightArrow";
        }
        else //curvedRightArrow
        if (__dummyScrutVar5.equals(0x67))
        {
            return "curvedLeftArrow";
        }
        else //curvedLeftArrow
        if (__dummyScrutVar5.equals(0x68))
        {
            return "curvedUpArrow";
        }
        else //curvedUpArrow
        if (__dummyScrutVar5.equals(0x69))
        {
            return "curvedDownArrow";
        }
        else //curvedDownArrow
        if (__dummyScrutVar5.equals(0x6A))
        {
            return "cloudCallout";
        }
        else //CloudCallout
        if (__dummyScrutVar5.equals(0x6B))
        {
            return "ellipseRibbon";
        }
        else //EllipseRibbon
        if (__dummyScrutVar5.equals(0x6C))
        {
            return "ellipseRibbon2";
        }
        else //EllipseRibbon2
        if (__dummyScrutVar5.equals(0x6D))
        {
            return "flowChartProcess";
        }
        else //flowChartProcess
        if (__dummyScrutVar5.equals(0x6E))
        {
            return "flowChartDecision";
        }
        else //flowChartDecision
        if (__dummyScrutVar5.equals(0x6F))
        {
            return "flowChartInputOutput";
        }
        else //flowChartInputOutput
        if (__dummyScrutVar5.equals(0x70))
        {
            return "flowChartPredefinedProcess";
        }
        else //flowChartPredefinedProcess
        if (__dummyScrutVar5.equals(0x71))
        {
            return "flowChartInternalStorage";
        }
        else //flowChartInternalStorage
        if (__dummyScrutVar5.equals(0x72))
        {
            return "flowChartDocument";
        }
        else //flowChartDocument
        if (__dummyScrutVar5.equals(0x73))
        {
            return "flowChartMultidocument";
        }
        else //flowChartMultidocument
        if (__dummyScrutVar5.equals(0x74))
        {
            return "flowChartTerminator";
        }
        else //flowChartTerminator
        if (__dummyScrutVar5.equals(0x75))
        {
            return "flowChartPreparation";
        }
        else //flowChartPreparation
        if (__dummyScrutVar5.equals(0x76))
        {
            return "flowChartManualInput";
        }
        else //flowChartManualInput
        if (__dummyScrutVar5.equals(0x77))
        {
            return "flowChartManualOperation";
        }
        else //flowChartManualOperation
        if (__dummyScrutVar5.equals(0x78))
        {
            return "flowChartConnector";
        }
        else //flowChartConnector
        if (__dummyScrutVar5.equals(0x79))
        {
            return "flowChartPunchedCard";
        }
        else //flowChartPunchedCard
        if (__dummyScrutVar5.equals(0x7A))
        {
            return "flowChartPunchedTape";
        }
        else //flowChartPunchedTape
        if (__dummyScrutVar5.equals(0x7B))
        {
            return "flowChartSummingJunction";
        }
        else //flowChartSummingJunction
        if (__dummyScrutVar5.equals(0x7C))
        {
            return "flowChartOr";
        }
        else //flowChartOr
        if (__dummyScrutVar5.equals(0x7D))
        {
            return "flowChartCollate";
        }
        else //flowChartCollate
        if (__dummyScrutVar5.equals(0x7E))
        {
            return "flowChartSort";
        }
        else //flowChartSort
        if (__dummyScrutVar5.equals(0x7F))
        {
            return "flowChartExtract";
        }
        else //flowChartExtract
        if (__dummyScrutVar5.equals(0x80))
        {
            return "flowChartMerge";
        }
        else //flowChartMerge
        if (__dummyScrutVar5.equals(0x81))
        {
            return "flowChartOfflineStorage";
        }
        else //flowChartOfflineStorage
        if (__dummyScrutVar5.equals(0x82))
        {
            return "flowChartOnlineStorage";
        }
        else //flowChartOnlineStorage
        if (__dummyScrutVar5.equals(0x83))
        {
            return "flowChartMagneticTape";
        }
        else //flowChartMagneticTape
        if (__dummyScrutVar5.equals(0x84))
        {
            return "flowChartMagneticDisk";
        }
        else //flowChartMagneticDisk
        if (__dummyScrutVar5.equals(0x85))
        {
            return "flowChartMagneticDrum";
        }
        else //flowChartMagneticDrum
        if (__dummyScrutVar5.equals(0x86))
        {
            return "flowChartDisplay";
        }
        else //flowChartDisplay
        if (__dummyScrutVar5.equals(0x87))
        {
            return "flowChartDelay";
        }
        else //flowChartDelay
        //TextPlainText
        //TextStop
        //TextTriangle
        //TextTriangleInverted
        //TextChevron
        //TextChevronInverted
        //TextRingInside
        //TextRingOutside
        //TextArchUpCurve
        //TextArchDownCurve
        //TextCircleCurve
        //TextButtonCurve
        //TextArchUpPour
        //TextArchDownPour
        //TextCirclePout
        //TextButtonPout
        //TextCurveUp
        //TextCurveDown
        //TextCascadeUp
        //TextCascadeDown
        //TextWave1
        //TextWave2
        //TextWave3
        //TextWave4
        //TextInflate
        //TextDeflate
        //TextInflateBottom
        //TextDeflateBottom
        //TextInflateTop
        //TextDeflateTop
        //TextDeflateInflate
        //TextDeflateInflateDeflate
        //TextFadeRight
        //TextFadeLeft
        //TextFadeUp
        //TextFadeDown
        //TextSlantUp
        //TextSlantDown
        //TextCanUp
        if (__dummyScrutVar5.equals(0x88) || __dummyScrutVar5.equals(0x89) || __dummyScrutVar5.equals(0x8A) || __dummyScrutVar5.equals(0x8B) || __dummyScrutVar5.equals(0x8C) || __dummyScrutVar5.equals(0x8D) || __dummyScrutVar5.equals(0x8E) || __dummyScrutVar5.equals(0x8F) || __dummyScrutVar5.equals(0x90) || __dummyScrutVar5.equals(0x91) || __dummyScrutVar5.equals(0x92) || __dummyScrutVar5.equals(0x93) || __dummyScrutVar5.equals(0x94) || __dummyScrutVar5.equals(0x95) || __dummyScrutVar5.equals(0x96) || __dummyScrutVar5.equals(0x97) || __dummyScrutVar5.equals(0x98) || __dummyScrutVar5.equals(0x99) || __dummyScrutVar5.equals(0x9A) || __dummyScrutVar5.equals(0x9B) || __dummyScrutVar5.equals(0x9C) || __dummyScrutVar5.equals(0x9D) || __dummyScrutVar5.equals(0x9E) || __dummyScrutVar5.equals(0x9F) || __dummyScrutVar5.equals(0xA0) || __dummyScrutVar5.equals(0xA1) || __dummyScrutVar5.equals(0xA2) || __dummyScrutVar5.equals(0xA3) || __dummyScrutVar5.equals(0xA4) || __dummyScrutVar5.equals(0xA5) || __dummyScrutVar5.equals(0xA6) || __dummyScrutVar5.equals(0xA7) || __dummyScrutVar5.equals(0xA8) || __dummyScrutVar5.equals(0xA9) || __dummyScrutVar5.equals(0xAA) || __dummyScrutVar5.equals(0xAB) || __dummyScrutVar5.equals(0xAC) || __dummyScrutVar5.equals(0xAD) || __dummyScrutVar5.equals(0xAE) || __dummyScrutVar5.equals(0xAF))
        {
            return "";
        }
        else //TextCanDown
        if (__dummyScrutVar5.equals(0xB0))
        {
            return "flowChartAlternateProcess";
        }
        else //flowchartAlternateProcess
        if (__dummyScrutVar5.equals(0xB1))
        {
            return "flowChartOffpageConnector";
        }
        else //flowchartOffpageConnector
        if (__dummyScrutVar5.equals(0xB2))
        {
            return "callout1";
        }
        else //Callout90
        if (__dummyScrutVar5.equals(0xB3))
        {
            return "accentCallout1";
        }
        else //AccentCallout90
        if (__dummyScrutVar5.equals(0xB4))
        {
            return "borderCallout1";
        }
        else //BorderCallout90
        if (__dummyScrutVar5.equals(0xB5))
        {
            return "accentBorderCallout1";
        }
        else //AccentBorderCallout90
        if (__dummyScrutVar5.equals(0xB6))
        {
            return "leftRightUpArrow";
        }
        else //LeftRightUpArrow
        if (__dummyScrutVar5.equals(0xB7))
        {
            return "sun";
        }
        else //Sun
        if (__dummyScrutVar5.equals(0xB8))
        {
            return "moon";
        }
        else //Moon
        if (__dummyScrutVar5.equals(0xB9))
        {
            return "bracketPair";
        }
        else //BracketPair
        if (__dummyScrutVar5.equals(0xBA))
        {
            return "bracePair";
        }
        else //BracePair
        if (__dummyScrutVar5.equals(0xBB))
        {
            return "star4";
        }
        else //Seal4
        if (__dummyScrutVar5.equals(0xBC))
        {
            return "doubleWave";
        }
        else //DoubleWave
        if (__dummyScrutVar5.equals(0xBD))
        {
            return "actionButtonBlank";
        }
        else //ActionButtonBlank
        if (__dummyScrutVar5.equals(0xBE))
        {
            return "actionButtonHome";
        }
        else //ActionButtonHome
        if (__dummyScrutVar5.equals(0xBF))
        {
            return "actionButtonHelp";
        }
        else //ActionButtonHelp
        if (__dummyScrutVar5.equals(0xC0))
        {
            return "actionButtonInformation";
        }
        else //ActionButtonInformation
        if (__dummyScrutVar5.equals(0xC1))
        {
            return "actionButtonForwardNext";
        }
        else //ActionButtonForwardNext
        if (__dummyScrutVar5.equals(0xC2))
        {
            return "actionButtonBackPrevious";
        }
        else //ActionButtonBackPrevious
        if (__dummyScrutVar5.equals(0xC3))
        {
            return "actionButtonEnd";
        }
        else //ActionButtonEnd
        if (__dummyScrutVar5.equals(0xC4))
        {
            return "actionButtonBeginning";
        }
        else //ActionButtonBeginning
        if (__dummyScrutVar5.equals(0xC5))
        {
            return "actionButtonReturn";
        }
        else //ActionButtonReturn
        if (__dummyScrutVar5.equals(0xC6))
        {
            return "actionButtonDocument";
        }
        else //ActionButtonDocument
        if (__dummyScrutVar5.equals(0xC7))
        {
            return "actionButtonSound";
        }
        else //ActionButtonSound
        if (__dummyScrutVar5.equals(0xC8))
        {
            return "actionButtonMovie";
        }
        else //ActionButtonMovie
        if (__dummyScrutVar5.equals(0xC9))
        {
            return "";
        }
        else //HostControl (do not use)
        if (__dummyScrutVar5.equals(0xCA))
        {
            return "rect";
        }
        else
        {
            return "";
        }                                                                                                                                                           
    }

}


//TextBox