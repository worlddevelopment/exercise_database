//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.Tools;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Text.EncodingSupport;
import CS2JNet.System.Xml.XmlDocument;
import CS2JNet.System.Xml.XmlNode;
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
    //Microsoft Office 2007 Beta 2 used namespaces that are not valid anymore.
    //This method should be used for all xml-code inside the binary Powerpoint file.
    //e.g. Themes, Layouts
    public static void replaceOutdatedNamespaces(RefSupport<XmlNode> e) throws Exception {
        String s = replaceOutdatedNamespaces(e.getValue().getOuterXml());
        XmlDocument d2 = new XmlDocument();
        d2.loadXml(s);
        e.setValue(d2.getDocumentElement());
    }

    public static String replaceOutdatedNamespaces(String input) throws Exception {
        String output = input.replace("http://schemas.openxmlformats.org/drawingml/2006/3/main", "http://schemas.openxmlformats.org/drawingml/2006/main");
        output = output.replace("http://schemas.openxmlformats.org/presentationml/2006/3/main", "http://schemas.openxmlformats.org/presentationml/2006/main");
        return output;
    }

    public static String readWString(InputStream stream) throws Exception {
        byte[] cch = new byte[1];
        stream.read(cch,0,cch.length);
        byte[] chars = new byte[2 * cch[0]];
        stream.read(chars,0,chars.length);
        return Encoding.Unicode.GetString(chars);
    }

    /**
    * Read a length prefixed Unicode string from the given stream.
    * The string must have the following structure:
    * byte 1 - 4:         Character count (cch)
    * byte 5 - (cch*2)+4: Unicode characters terminated by \0
    * 
    *  @param stream 
    *  @return
    */
    public static String readLengthPrefixedUnicodeString(InputStream stream) throws Exception {
        byte[] cchBytes = new byte[4];
        stream.read(cchBytes,0,cchBytes.length);
        int cch = System.BitConverter.ToInt32(cchBytes, 0);
        //dont read the terminating zero
        byte[] stringBytes = new byte[cch * 2];
        stream.read(stringBytes,0,stringBytes.length);
        return Encoding.Unicode.GetString(stringBytes, 0, stringBytes.length - 2);
    }

    /**
    * Read a length prefixed ANSI string from the given stream.
    * The string must have the following structure:
    * byte 1-4:       Character count (cch)
    * byte 5-cch+4:   ANSI characters terminated by \0
    * 
    *  @param stream 
    *  @return
    */
    public static String readLengthPrefixedAnsiString(InputStream stream) throws Exception {
        byte[] cchBytes = new byte[4];
        stream.read(cchBytes,0,cchBytes.length);
        int cch = System.BitConverter.ToInt32(cchBytes, 0);
        //dont read the terminating zero
        byte[] stringBytes = new byte[cch];
        stream.read(stringBytes,0,stringBytes.length);
        if (cch > 0)
            return new String(stringBytes, 0, stringBytes.length - 1, EncodingSupport.GetEncoder("ASCII").getCharset());
        else
            return null; 
    }

    public static String readXstz(byte[] bytes, int pos) throws Exception {
        byte[] xstz = new byte[System.BitConverter.ToInt16(bytes, pos) * 2];
        Array.Copy(bytes, pos + 2, xstz, 0, xstz.length);
        return Encoding.Unicode.GetString(xstz);
    }

    public static String readXst(InputStream stream) throws Exception {
        byte[] cch = new byte[2];
        stream.read(cch,0,cch.length);
        byte[] xstz = new byte[System.BitConverter.ToInt16(cch, 0) * 2];
        stream.read(xstz,0,xstz.length);
        return Encoding.Unicode.GetString(xstz);
    }

    public static String readXstz(InputStream stream) throws Exception {
        String xst = readXst(stream);
        //skip the termination
        byte[] termiantion = new byte[2];
        stream.read(termiantion,0,termiantion.length);
        return xst;
    }

    public static String readShortXlUnicodeString(InputStream stream) throws Exception {
        byte[] cch = new byte[1];
        stream.read(cch,0,cch.length);
        byte[] fHighByte = new byte[1];
        stream.read(fHighByte,0,fHighByte.length);
        int rgbLength = cch[0];
        if (fHighByte[0] >= 0)
        {
            //double byte characters
            rgbLength *= 2;
        }
         
        byte[] rgb = new byte[rgbLength];
        stream.read(rgb,0,rgb.length);
        if (fHighByte[0] >= 0)
        {
            return Encoding.Unicode.GetString(rgb);
        }
        else
        {
            Encoding enc = Encoding.GetEncoding(1252);
            return new String(rgb, enc.getString());
        } 
    }

    public static int arraySum(byte[] values) throws Exception {
        int ret = 0;
        for (byte b : values)
        {
            ret += b;
        }
        return ret;
    }

    public static boolean bitmaskToBool(int value, int mask) throws Exception {
        return ((value & mask) == mask);
    }

    public static boolean bitmaskToBool(long value, long mask) throws Exception {
        return ((value & mask) == mask);
    }

    public static byte bitmaskToByte(long value, long mask) throws Exception {
        value = value & mask;
        while ((mask & 0x1) != 0x1)
        {
            value = value >> 1;
            mask = mask >> 1;
        }
        return Convert.ToByte(value);
    }

    public static int bitmaskToInt(int value, int mask) throws Exception {
        int ret = value & mask;
        //shift for all trailing zeros
        BitArray bits = new BitArray(new int[]{ mask });
        for (Object __dummyForeachVar1 : bits)
        {
            boolean bit = (Boolean)__dummyForeachVar1;
            if (!bit)
                ret = ret >> 1;
            else
                break; 
        }
        return ret;
    }

    public static int bitmaskToInt32(int value, int mask) throws Exception {
        value = value & mask;
        while ((mask & 0x1) != 0x1)
        {
            value = value >> 1;
            mask = mask >> 1;
        }
        return value;
    }

    public static long bitmaskToUInt32(long value, long mask) throws Exception {
        value = value & mask;
        while ((mask & 0x1) != 0x1)
        {
            value = value >> 1;
            mask = mask >> 1;
        }
        return value;
    }

    public static UInt16 bitmaskToUInt16(long value, long mask) throws Exception {
        return Convert.ToUInt16(bitmaskToUInt32(value,mask));
    }

    public static boolean intToBool(int value) throws Exception {
        if (value == 1)
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    public static boolean byteToBool(byte value) throws Exception {
        if (value == 1)
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    public static char[] clearCharArray(char[] values) throws Exception {
        char[] ret = new char[values.length];
        for (int i = 0;i < values.length;i++)
        {
            ret[i] = Convert.ToChar(0);
        }
        return ret;
    }

    public static int[] clearIntArray(int[] values) throws Exception {
        int[] ret = new int[values.length];
        for (int i = 0;i < values.length;i++)
        {
            ret[i] = 0;
        }
        return ret;
    }

    public static short[] clearShortArray(ushort[] values) throws Exception {
        short[] ret = new short[values.length];
        for (int i = 0;i < values.length;i++)
        {
            ret[i] = 0;
        }
        return ret;
    }

    public static long bitArrayToUInt32(BitArray bits) throws Exception {
        double ret = 0;
        for (int i = 0;i < bits.Count;i++)
        {
            if (bits[i])
            {
                ret += Math.pow((double)2,(double)i);
            }
             
        }
        return (long)ret;
    }

    public static BitArray bitArrayCopy(BitArray source, int sourceIndex, int copyCount) throws Exception {
        boolean[] ret = new boolean[copyCount];
        int j = 0;
        for (int i = sourceIndex;i < (copyCount + sourceIndex);i++)
        {
            ret[j] = source[i];
            j++;
        }
        return new BitArray(ret);
    }

    public static String getHashDump(byte[] bytes) throws Exception {
        int colCount = 16;
        String ret = String.format(StringSupport.CSFmtStrToJFmtStr("({0:X04}) "),0);
        int colCounter = 0;
        for (int i = 0;i < bytes.length;i++)
        {
            if (colCounter == colCount)
            {
                colCounter = 0;
                ret += "\n" + String.format(StringSupport.CSFmtStrToJFmtStr("({0:X04}) "),i);
            }
             
            ret += String.format(StringSupport.CSFmtStrToJFmtStr("{0:X02} "),bytes[i]);
            colCounter++;
        }
        return ret;
    }

    // Would have been nice to use an extension method here... -- flgr
    public static String stringInspect(String s) throws Exception {
        StringBuilder result = new StringBuilder("\"");
        for (char c : s.toCharArray())
        {
            switch(c)
            {
                case '\r': 
                    result.append("\\r");
                    break;
                case '\n': 
                    result.append("\\n");
                    break;
                case '\v': 
                    result.append("\\v");
                    break;
                default: 
                    if (char.IsControl(c))
                        result.append(String.format(StringSupport.CSFmtStrToJFmtStr("\\x{0:X2}"),(int)c));
                    else
                        result.append(c); 
                    break;
            
            }
        }
        result.append("\"");
        return result.toString();
    }

    public static String getWritableString(String s) throws Exception {
        StringBuilder result = new StringBuilder();
        for (char c : s.toCharArray())
        {
            if ((int)c >= 0x20)
            {
                result.append(c);
            }
             
        }
        return result.toString();
    }

}


