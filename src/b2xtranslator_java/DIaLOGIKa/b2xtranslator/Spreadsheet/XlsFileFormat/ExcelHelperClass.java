//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:37 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExtractorException;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.AbstractPtg;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.Ptg0x19Sub;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAdd;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgArea;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgArea3d;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAreaErr;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAreaErr3d;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAreaN;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAttrChoose;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAttrGoto;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAttrIf;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAttrSemi;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAttrSpace;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgAttrSum;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgBool;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgConcat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgDiv;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgEq;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgErr;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgExp;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgFunc;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgFuncVar;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgGe;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgGt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgInt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgIsect;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgLe;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgLt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgMemErr;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgMemFunc;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgMissArg;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgMul;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgName;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgNameX;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgNe;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgNotDocumented;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgNum;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgNumber;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgParen;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgPercent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgPower;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgRef;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgRef3d;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgRefErr;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgRefErr3d;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgRefN;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgStr;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgSub;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgUminus;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgUnion;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.PtgUplus;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import java.util.Arrays;

/**
* Assembly of some static methods
*/
public class ExcelHelperClass   
{
    /**
    * This method is used to parse a RK RecordType
    * This is a special MS Excel format that is used to store floatingpoint and integer values
    * The floatingpoint arithmetic is a little bit different to the IEEE standard format.
    * 
    * Integer and floating point values are booth stored in a RK Record. To differ between them
    * it is necessary to check the first two bits from this value.
    * 
    *  @param rk Bytestream from the RK Record
    *  @return The correct parsed number
    */
    public static double numFromRK(byte[] rk) throws Exception {
        double num = 0;
        int high = 1023;
        long number;
        number = System.BitConverter.ToUInt32(rk, 0);
        // Select which type of number
        long type = number & 0x00000003;
        if (type == 0 || type == 1)
        {
            // if the last two bits are 00 or 01 then it is floating point IEEE number
            // type 0 and type 1 expects booth the same arithmetic
            long mant = 0;
            // masking the mantisse
            mant = number & 0x000ffffc;
            // shifting the result by 2
            mant = mant >> 2;
            long exp = 0;
            // masking the exponent
            exp = number & 0x7ff00000;
            // shifting the exponent by 20
            exp = exp >> 20;
            // (1 + (Mantisse / 2^18)) * 2 ^ (Exponent - 1023)
            num = (1 + (mant / Math.pow(2.0,18.0))) * Math.pow(2,(double)(exp - high));
            // now there is a sign bit too, the highest bit from the RK Record
            long signBit = number & 0x80000000;
            // shifting the value by 31 bit
            signBit = signBit >> 31;
            // if the signBit is 0 it is a positive number, otherwise it is a negative number
            if (signBit == 1)
            {
                num *= -1;
            }
             
            if (type == 1)
            {
                num /= 100;
            }
             
        }
        else if (type == 2 || type == 3)
        {
            // if type is 1 then it is an IEEE number * 100
            // if type is 2 or 3 it is an integer value
            // 30 bits for the integer value, 2 bits for the type identification
            long unumber = 0;
            unumber = number & 0xfffffffc;
            // shifting the value by two
            unumber = unumber >> 2;
            num = (double)unumber;
            if (type == 3)
            {
                num /= 100;
            }
             
        }
          
        return num;
    }

    // if type is 3, it has to be multiplicated with 100
    /**
    * converts the integer column value to a string like AB
    * excel binary format has a cap at column 256 -> IV, so there is no need to
    * create an almighty algorithm ;)
    * 
    *  @return String
    */
    public static String intToABCString(int colnumber, String rownumber) throws Exception {
        String value = "";
        int remain = 0;
        if (colnumber < 26)
        {
            value += (char)(colnumber + 65);
        }
        else if (colnumber < Math.pow(26,2))
        {
            remain = colnumber % 26;
            colnumber = colnumber / 26;
            value += (char)(colnumber + 64);
            value = value + (char)(remain + 65);
        }
        else if (colnumber < Math.pow(26,3))
        {
            remain = colnumber % ((int)(Math.pow(26,2)));
            colnumber = colnumber / ((int)(Math.pow(26,2)));
            value += (char)(colnumber + 64);
            colnumber = remain;
            remain = colnumber % 26;
            colnumber = colnumber / 26;
            value = value + (char)(colnumber + 64);
            value = value + (char)(remain + 65);
        }
           
        return value + rownumber;
    }

    /**
    * converts the integer column value to a string like AB
    * excel binary format has a cap at column 256 -> IV, so there is no need to
    * create an almighty algorithm ;)
    * 
    *  @return String
    */
    public static String intToABCString(int colnumber, String rownumber, boolean colRelative, boolean rwRelative) throws Exception {
        String value = "";
        int remain = 0;
        if (colnumber < 26)
        {
            value += (char)(colnumber + 65);
        }
        else if (colnumber < Math.pow(26,2))
        {
            remain = colnumber % 26;
            colnumber = colnumber / 26;
            value += (char)(colnumber + 64);
            value = value + (char)(remain + 65);
        }
        else if (colnumber < Math.pow(26,3))
        {
            remain = colnumber % ((int)(Math.pow(26,2)));
            colnumber = colnumber / ((int)(Math.pow(26,2)));
            value += (char)(colnumber + 64);
            colnumber = remain;
            remain = colnumber % 26;
            colnumber = colnumber / 26;
            value = value + (char)(colnumber + 64);
            value = value + (char)(remain + 65);
        }
           
        if (!colRelative)
            value = "$" + value;
         
        if (!rwRelative)
            rownumber = "$" + rownumber;
         
        return value + rownumber;
    }

    public static Stack<AbstractPtg> getFormulaStack(IStreamReader reader, UInt16 cce) throws Exception {
        Stack<AbstractPtg> ptgStack = new Stack<AbstractPtg>();
        try
        {
            for (uint i = 0;i < cce;i++)
            {
                PtgNumber ptgtype = (PtgNumber)reader.readByte();
                if (((Enum)ptgtype).ordinal() > 0x5D)
                {
                    ptgtype -= 0x40;
                }
                else if (((Enum)ptgtype).ordinal() > 0x3D)
                {
                    ptgtype -= 0x20;
                }
                  
                AbstractPtg ptg = null;
                if (ptgtype == PtgNumber.Ptg0x19Sub)
                {
                    Ptg0x19Sub ptgtype2 = (Ptg0x19Sub)reader.readByte();
                    switch(ptgtype2)
                    {
                        case PtgAttrSum: 
                            ptg = new PtgAttrSum(reader,ptgtype2);
                            break;
                        case PtgAttrIf: 
                            ptg = new PtgAttrIf(reader,ptgtype2);
                            break;
                        case PtgAttrGoto: 
                            ptg = new PtgAttrGoto(reader,ptgtype2);
                            break;
                        case PtgAttrSemi: 
                            ptg = new PtgAttrSemi(reader,ptgtype2);
                            break;
                        case PtgAttrChoose: 
                            ptg = new PtgAttrChoose(reader,ptgtype2);
                            break;
                        case PtgAttrSpace: 
                            ptg = new PtgAttrSpace(reader,ptgtype2);
                            break;
                        case PtgNotDocumented: 
                            ptg = new PtgNotDocumented(reader,ptgtype2);
                            break;
                        default: 
                            break;
                    
                    }
                }
                else if (ptgtype == PtgNumber.Ptg0x18Sub)
                {
                }
                else
                {
                    switch(ptgtype)
                    {
                        case PtgInt: 
                            ptg = new PtgInt(reader,ptgtype);
                            break;
                        case PtgAdd: 
                            ptg = new PtgAdd(reader,ptgtype);
                            break;
                        case PtgSub: 
                            ptg = new PtgSub(reader,ptgtype);
                            break;
                        case PtgMul: 
                            ptg = new PtgMul(reader,ptgtype);
                            break;
                        case PtgDiv: 
                            ptg = new PtgDiv(reader,ptgtype);
                            break;
                        case PtgParen: 
                            ptg = new PtgParen(reader,ptgtype);
                            break;
                        case PtgNum: 
                            ptg = new PtgNum(reader,ptgtype);
                            break;
                        case PtgRef: 
                            ptg = new PtgRef(reader,ptgtype);
                            break;
                        case PtgRefN: 
                            ptg = new PtgRefN(reader,ptgtype);
                            break;
                        case PtgPower: 
                            ptg = new PtgPower(reader,ptgtype);
                            break;
                        case PtgPercent: 
                            ptg = new PtgPercent(reader,ptgtype);
                            break;
                        case PtgBool: 
                            ptg = new PtgBool(reader,ptgtype);
                            break;
                        case PtgGt: 
                            ptg = new PtgGt(reader,ptgtype);
                            break;
                        case PtgGe: 
                            ptg = new PtgGe(reader,ptgtype);
                            break;
                        case PtgLt: 
                            ptg = new PtgLt(reader,ptgtype);
                            break;
                        case PtgLe: 
                            ptg = new PtgLe(reader,ptgtype);
                            break;
                        case PtgEq: 
                            ptg = new PtgEq(reader,ptgtype);
                            break;
                        case PtgNe: 
                            ptg = new PtgNe(reader,ptgtype);
                            break;
                        case PtgUminus: 
                            ptg = new PtgUminus(reader,ptgtype);
                            break;
                        case PtgUplus: 
                            ptg = new PtgUplus(reader,ptgtype);
                            break;
                        case PtgStr: 
                            ptg = new PtgStr(reader,ptgtype);
                            break;
                        case PtgConcat: 
                            ptg = new PtgConcat(reader,ptgtype);
                            break;
                        case PtgUnion: 
                            ptg = new PtgUnion(reader,ptgtype);
                            break;
                        case PtgIsect: 
                            ptg = new PtgIsect(reader,ptgtype);
                            break;
                        case PtgMemErr: 
                            ptg = new PtgMemErr(reader,ptgtype);
                            break;
                        case PtgArea: 
                            ptg = new PtgArea(reader,ptgtype);
                            break;
                        case PtgAreaN: 
                            ptg = new PtgAreaN(reader,ptgtype);
                            break;
                        case PtgFuncVar: 
                            ptg = new PtgFuncVar(reader,ptgtype);
                            break;
                        case PtgFunc: 
                            ptg = new PtgFunc(reader,ptgtype);
                            break;
                        case PtgExp: 
                            ptg = new PtgExp(reader,ptgtype);
                            break;
                        case PtgRef3d: 
                            ptg = new PtgRef3d(reader,ptgtype);
                            break;
                        case PtgArea3d: 
                            ptg = new PtgArea3d(reader,ptgtype);
                            break;
                        case PtgNameX: 
                            ptg = new PtgNameX(reader,ptgtype);
                            break;
                        case PtgName: 
                            ptg = new PtgName(reader,ptgtype);
                            break;
                        case PtgMissArg: 
                            ptg = new PtgMissArg(reader,ptgtype);
                            break;
                        case PtgRefErr: 
                            ptg = new PtgRefErr(reader,ptgtype);
                            break;
                        case PtgRefErr3d: 
                            ptg = new PtgRefErr3d(reader,ptgtype);
                            break;
                        case PtgAreaErr: 
                            ptg = new PtgAreaErr(reader,ptgtype);
                            break;
                        case PtgAreaErr3d: 
                            ptg = new PtgAreaErr3d(reader,ptgtype);
                            break;
                        case PtgMemFunc: 
                            ptg = new PtgMemFunc(reader,ptgtype);
                            break;
                        case PtgErr: 
                            ptg = new PtgErr(reader,ptgtype);
                            break;
                        default: 
                            break;
                    
                    }
                }  
                i += ptg.getLength() - 1;
                ptgStack.Push(ptg);
            }
        }
        catch (Exception ex)
        {
            throw new ExtractorException(ExtractorException.PARSEDFORMULAEXCEPTION,ex);
        }

        return ptgStack;
    }

    public static String parseVirtualPath(String path) throws Exception {
        // NOTE: A virtual path must be a string in the following grammar:
        //
        //    virt-path = volume / unc-volume / rel-volume / transfer-protocol / startup / alt-startup / library / simple-file-path / ole-link
        path = StringSupport.Trim(path);
        if (path.startsWith("\x0001\x0001\x0040"))
        {
            // unc-volume = %x0001 %x0001 %x0040 unc-path
            path = path.substring(3);
        }
        else if (path.startsWith("\x0001\x0001"))
        {
            // volume     = %x0001 %x0001 volume-character file-path
            // path[2] is a volumn character in the range %x0041-%x005A / %x0061-%x007A
            path = path.substring(2, (2) + (1)) + ":\\" + path.substring(3);
        }
        else if (path.startsWith("\x0001\x0002"))
        {
            // rel-volume = %x0001 %x0002 file-path
            path = path.substring(2);
        }
        else if (path.startsWith("\x0001\x0005"))
        {
            // transfer-protocol = %x0001 %x0005 count transfer-path
            // count is ignored
            path = path.substring(3);
        }
        else if (path.startsWith("\x0001\x0006"))
        {
            // startup = %x0001 %x0006 file-path
            // TODO: map startup path
            path = path.substring(2);
        }
        else if (path.startsWith("\x0001\x0007"))
        {
            // alt-startup = %x0001 %x0007 file-path
            path = path.substring(2);
        }
        else if (path.startsWith("\x0001\x0008"))
        {
            // library = %x0001 %x0008 file-path
            // TODO: map library path
            path = "file:///" + path.substring(2);
        }
        else if (path.startsWith("\x0001"))
        {
            // simple-file-path = [%x0001] file-path
            //   (\x0001 is optional, but if it is missing path is
            //    already set to the correct value)
            path = path.substring(1);
        }
                
        /**
        * Replace 0x03 with \
        */
        path = path.replace((char)0x03, '\\');
        return path;
    }

    /**
    * replace ' ' with %20
    * path = path.Replace(" ", "%20");
    * 
    * This method reads x bytes from a IStreamReader to get a string from this
    * 
    *  @param reader 
    *  @param cch 
    *  @param grbit 
    *  @return
    */
    public static String getStringFromBiffRecord(IStreamReader reader, int cch, int grbit) throws Exception {
        String value = "";
        if (grbit == 0)
        {
            for (int i = 0;i < cch;i++)
            {
                value += (char)reader.readByte();
            }
        }
        else
        {
            for (int i = 0;i < cch;i++)
            {
                value += System.BitConverter.ToChar(reader.readBytes(2), 0);
            }
        } 
        return value;
    }

    /**
    * Converts the builtin function id to a string
    * 
    *  @param idstring 
    *  @return
    */
    public static String getNameStringfromBuiltInFunctionID(String idstring) throws Exception {
        char firstChar = (char)Array.get(idstring.toCharArray(), 0);
        switch(firstChar)
        {
            case (char)0x00: 
                return "Consolidate_Area";
            case (char)0x01: 
                return "Auto_Open";
            case (char)0x02: 
                return "Auto_Close";
            case (char)0x03: 
                return "Extract";
            case (char)0x04: 
                return "Database";
            case (char)0x05: 
                return "Criteria";
            case (char)0x06: 
                return "Print_Area";
            case (char)0x07: 
                return "Print_Titles";
            case (char)0x08: 
                return "Recorder";
            case (char)0x09: 
                return "Data_Form";
            case (char)0x0A: 
                return "Auto_Activate";
            case (char)0x0B: 
                return "Auto_Deactivate";
            case (char)0x0C: 
                return "Sheet_Title";
            case (char)0x0D: 
                return "_FilterDatabase";
            default: 
                return idstring;
        
        }
    }

    /**
    * This method reads x bytes from a IStreamReader to get a hyperlink string from this
    * 
    *  @param reader 
    *  @param cch 
    *  @param grbit 
    *  @return
    */
    public static String getHyperlinkStringFromBiffRecord(IStreamReader reader) throws Exception {
        String value = "";
        long length = reader.readUInt32();
        for (int i = 0;i < length;i++)
        {
            value += System.BitConverter.ToChar(reader.readBytes(2), 0);
        }
        return value.Remove(value.length() - 1);
            ;
    }

    /**
    * Escapes special characters in strings so that they can be safely used in formulas.
    * 
    *  @param unescapedString The input string to be escaped.This method currently escapes double and single quotes.
    */
    public static String escapeFormulaString(String unescapedString) throws Exception {
        return unescapedString.replace("\"", "\"\"").replace("'", "''");
    }

}


