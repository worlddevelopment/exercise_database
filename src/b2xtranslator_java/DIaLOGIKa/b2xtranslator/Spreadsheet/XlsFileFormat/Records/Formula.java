//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:43 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.AbstractPtg;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
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
public class Formula  extends BiffRecord 
{
    public static final RecordType ID = RecordType.Formula;
    /**
    * Rownumber
    */
    public UInt16 rw = new UInt16();
    /**
    * Colnumber
    */
    public UInt16 col = new UInt16();
    /**
    * index to the XF record
    */
    public UInt16 ixfe = new UInt16();
    /**
    * 8 byte calculated number / string / error of the formular
    */
    public byte[] val;
    /**
    * option flags
    */
    public UInt16 grbit = new UInt16();
    /**
    * used for performance reasons only
    * can be ignored
    */
    public long chn;
    /**
    * length of the formular data !!
    */
    public UInt16 cce = new UInt16();
    /**
    * this attribute indicates if the formula is a shared formula
    */
    public boolean fShrFmla;
    /**
    * LinkedList with the Ptg records !!
    */
    public Stack<AbstractPtg> ptgStack = new Stack<AbstractPtg>();
    /**
    * this is the calculated value
    */
    public double calculatedValue;
    public boolean boolValueSet;
    public byte boolValue;
    public int errorValue;
    public boolean fAlwaysCalc;
    public Formula(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        this.val = new byte[8];
        this.rw = reader.readUInt16();
        this.col = reader.readUInt16();
        this.ixfe = reader.readUInt16();
        this.boolValueSet = false;
        long oldStreamPosition = this.Reader.BaseStream.Position;
        this.val = reader.readBytes(8);
        // read 8 bytes for the value of the formular
        if (this.val[6] == 0xFF && this.val[7] == 0xFF)
        {
            // this value is a string, an error or a boolean value
            byte firstOffset = this.val[0];
            if (firstOffset == 1)
            {
                // this is a boolean value
                this.boolValue = val[2];
                this.boolValueSet = true;
            }
             
            if (firstOffset == 2)
            {
                // this is a error value
                this.errorValue = (int)val[2];
            }
             
        }
        else
        {
            this.Reader.BaseStream.Seek(oldStreamPosition, System.IO.SeekOrigin.Begin);
            this.calculatedValue = reader.readDouble();
        } 
        this.grbit = reader.readUInt16();
        this.chn = reader.readUInt32();
        // this is used for performance reasons only
        this.cce = reader.readUInt16();
        this.ptgStack = new Stack<AbstractPtg>();
        // reader.ReadBytes(this.cce);
        // check always calc mode
        this.fAlwaysCalc = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)grbit,0x01);
        // check if shared formula
        this.fShrFmla = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)grbit,0x08);
        oldStreamPosition = this.Reader.BaseStream.Position;
        if (!fShrFmla)
        {
            try
            {
                this.ptgStack = ExcelHelperClass.getFormulaStack(this.Reader, this.cce);
            }
            catch (Exception ex)
            {
                this.Reader.BaseStream.Seek(oldStreamPosition, System.IO.SeekOrigin.Begin);
                this.Reader.BaseStream.Seek(this.cce, System.IO.SeekOrigin.Current);
                TraceLogger.error("Formula parse error in Row {0} Column {1}",this.rw,this.col);
                TraceLogger.debug(ex.getStackTrace().toString());
                TraceLogger.debug("Inner exception: {0}",((Exception)ex.getCause()).getStackTrace().toString());
            }
        
        }
        else
        {
            reader.ReadBytes(this.cce);
        } 
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

    public String toString() {
        try
        {
            return "Fomula at position: Row - " + this.rw.toString() + " | Col - " + this.col.toString();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


