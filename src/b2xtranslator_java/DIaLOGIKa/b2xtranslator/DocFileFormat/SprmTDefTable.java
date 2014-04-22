//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:07 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Global;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TC80;

public class SprmTDefTable   
{
    public byte numberOfColumns;
    /**
    * An array of 16-bit signed integer that specifies horizontal distance in twips. 
    * MUST be greater than or equal to -31680 and less than or equal to 31680.
    */
    public short[] rgdxaCenter;
    /**
    * An array of TC80 that specifies the default formatting for a cell in the table. 
    * Each TC80 in the array corresponds to the equivalent column in the table.
    * If there are fewer TC80s than columns, the remaining columns are formatted with the default TC80 formatting. 
    * If there are more TC80s than columns, the excess TC80s MUST be ignored.
    */
    public TC80[] rgTc80;
    public SprmTDefTable(byte[] bytes) throws Exception {
        this.numberOfColumns = bytes[0];
        int pointer = 1;
        //read rgdxaCenter
        rgdxaCenter = new short[this.numberOfColumns + 1];
        for (int i = 0;i < this.numberOfColumns + 1;i++)
        {
            rgdxaCenter[i] = System.BitConverter.ToInt16(bytes, pointer);
            pointer += 2;
        }
        //read rgTc80
        rgTc80 = new TC80[this.numberOfColumns];
        for (int i = 0;i < this.numberOfColumns;i++)
        {
            TC80 tc = new TC80();
            if (pointer < bytes.length)
            {
                //the flags
                UInt16 flags = System.BitConverter.ToUInt16(bytes, pointer);
                tc.horzMerge = (byte)DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt((int)flags,0x3);
                tc.textFlow = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TextFlow.values()[DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt((int)flags,0x1C)];
                tc.vertMerge = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalMergeFlag.values()[DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt((int)flags,0x60)];
                tc.vertAlign = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalAlign.values()[DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt((int)flags,0x180)];
                tc.ftsWidth = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.CellWidthType.values()[DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt((int)flags,0xE00)];
                tc.fFitText = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1000);
                tc.fNoWrap = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x2000);
                tc.fHideMark = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x4000);
                pointer += 2;
                //cell width
                tc.wWidth = System.BitConverter.ToInt16(bytes, pointer);
                pointer += 2;
                //border top
                byte[] brcTopBytes = new byte[4];
                Array.Copy(bytes, pointer, brcTopBytes, 0, 4);
                tc.brcTop = new BorderCode(brcTopBytes);
                pointer += 4;
                //border left
                byte[] brcLeftBytes = new byte[4];
                Array.Copy(bytes, pointer, brcLeftBytes, 0, 4);
                tc.brcLeft = new BorderCode(brcLeftBytes);
                pointer += 4;
                //border bottom
                byte[] brcBottomBytes = new byte[4];
                Array.Copy(bytes, pointer, brcBottomBytes, 0, 4);
                tc.brcBottom = new BorderCode(brcBottomBytes);
                pointer += 4;
                //border top
                byte[] brcRightBytes = new byte[4];
                Array.Copy(bytes, pointer, brcRightBytes, 0, 4);
                tc.brcRight = new BorderCode(brcRightBytes);
                pointer += 4;
            }
             
            rgTc80[i] = tc;
        }
    }

}


