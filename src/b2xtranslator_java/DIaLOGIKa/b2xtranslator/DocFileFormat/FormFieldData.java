//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:02 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormFieldData;

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
public class FormFieldData   implements IVisitable
{
    /**
    * An unsigned integer that MUST be 0xFFFFFFFF.
    */
    public long version;
    /**
    * Specifies the type of the form field.
    */
    public FormFieldType iType = FormFieldType.iTypeText;
    /**
    * An unsigned integer.
    * If iType is iTypeText (0), then iRes MUST be 0.
    * If iType is iTypeChck (1), then iRes specifies the state of the checkbox and
    * MUST be 0 (unchecked), 1 (checked), or 25 (undefined).
    * Undefined checkboxes are treated as unchecked.
    * If iType is iTypeDrop (2), then iRes specifies the current selected list box item.
    * A value of 25 specifies the selection is undefined.
    * Otherwise, iRes is a zero-based index into FFData.hsttbDropList.
    */
    public UInt16 iRes = new UInt16();
    /**
    * A bool that specifies whether the form field has custom help text in xstzHelpText. 
    * If fOwnHelp is false, then xstzHelpText contains an empty or auto-generated string.
    */
    public boolean fOwnHelp;
    /**
    * A bool that specifies whether the form field has custom status bar text in xstzStatText. 
    * If fOwnStat is false, then xstzStatText contains an empty or auto-generated string.
    */
    public boolean fOwnStat;
    /**
    * A bool that specifies whether the form field is protected and its value cannot be changed.
    */
    public boolean fProt;
    /**
    * A bit that specifies whether a checkbox�s size is automatically determined
    * by the text size where the checkbox is located. 
    * 
    * MUST be 0 if iType is not iTypeChck (1).
    */
    public byte iSize;
    /**
    * Specifies the type of the textbox
    */
    public TextboxType iTypeTxt = TextboxType.regular;
    /**
    * A bool that specifies whether the field�s value is automatically calculated after the field is modified.
    */
    public boolean fRecalc;
    /// <summary>
    /// A bool that specifies that the form field has a list box. <br/<br/>
    ///
    /// MUST be true if iType is iTypeDrop.
    /// Otherwise, MUST be false.
    /// </summary>
    public boolean fHasListBox;
    /**
    * An unsigned integer that specifies the maximum length, in characters,
    * of the value of the textbox.
    * 
    * MUST NOT exceed 32767.
    * A value of 0 means there is no maximum length of the value of the textbox.
    * MUST be 0 if iType is not iTypeText (0).
    */
    public UInt16 cch = new UInt16();
    /**
    * An unsigned integer.
    * 
    * If iType is iTypeChck (1), then hps specifies the size, in half-points,
    * of the checkbox and MUST be between 2 and 3168, inclusive.
    * If bitiType is not iTypeChck (1), then hps is undefined and MUST be ignored.
    */
    public UInt16 hps = new UInt16();
    /**
    * An string that specifies the name of this form field.
    * 
    * The length MUST NOT exceed 20.
    */
    public String xstzName;
    /**
    * An optional Xstz that specifies the default text of this textbox.
    * 
    * This structure MUST exist if and only if iType is iTypeTxt (0).
    * The length MUST NOT exceed 255.
    * If iTypeTxt is either iTypeTxtCurDate (3) or iTypeTxtCurTime (4),
    * then xstzTextDef MUST be an empty string.
    * If iTypeTxt is iTypeTxtCalc (5), then xstzTextDef specifies an expression to calculate.
    */
    public String xstzTextDef;
    /**
    * An optional unsigned integer that specifies the default state of the checkbox or dropdown list box.
    * 
    * MUST exist if and only if iType is iTypeChck (1) or iTypeDrop (2).
    * If iType is iTypeChck (1), then wDef MUST be 0 or 1 and specify
    * the default state of the checkbox as unchecked or checked, respectively.
    * If iType is iTypeDrop (2), then wDef MUST be less than the number of
    * items in the dropdown list box and specify the default item selected (zero-based index).
    */
    public UInt16 wDef = new UInt16();
    /**
    * An string that specifies the string format of the textbox. 
    * 
    * MUST be an empty string if iType is not iTypeTxt (0).
    * The length MUST NOT exceed 64.
    * Valid formatting strings are specified in [ECMA-376] part 4, section 2.16.22 format (Text Box Form Field Formatting).
    */
    public String xstzTextFormat;
    /**
    * An string that specifies the help text for the form field.
    * 
    * The length MUST NOT exceed 255.
    */
    public String xstzHelpText;
    /**
    * An string that specifies the status bar text for the form field.
    * 
    * The length MUST NOT exceed 138.
    */
    public String xstzStatText;
    /**
    * An string that specifies a macro to run upon entry of the form field.
    * 
    * The length MUST NOT exceed 32.
    */
    public String xstzEntryMcr;
    /**
    * An string that specifies a macro to run after the value of the form field has changed. 
    * 
    * The length MUST NOT exceed 32.
    */
    public String xstzExitMcr;
    /**
    * An optional STTB that specifies the _entries in the dropdown list box. 
    * 
    * MUST exist if and only if iType is iTypeDrop (2).
    * Entries are Unicode strings and do not have extra data.
    * MUST NOT exceed 25 elements.
    */
    public String[] hsttbDropList;
    /**
    * Creates a new FFData by reading the data from the given stream.
    * The position must already be set.
    * 
    *  @param dataStream
    */
    public FormFieldData(byte[] bytes) throws Exception {
        int pos = 0;
        this.version = System.BitConverter.ToUInt32(bytes, pos);
        if (this.version == 0xFFFFFFFF)
        {
            pos += 4;
            int bits = (int)System.BitConverter.ToUInt16(bytes, pos);
            this.iType = FormFieldType.values()[DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt(bits,0x3)];
            this.iRes = (UInt16)DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt(bits,0x7C);
            this.fOwnHelp = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bits,0x80);
            this.fOwnStat = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bits,0x100);
            this.fProt = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bits,0x200);
            this.iSize = (byte)DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt(bits,0x400);
            this.iTypeTxt = TextboxType.values()[DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt(bits,0x3800)];
            this.fRecalc = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bits,0x4000);
            this.fHasListBox = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bits,0x8000);
            pos += 2;
            this.cch = System.BitConverter.ToUInt16(bytes, pos);
            pos += 2;
            this.hps = System.BitConverter.ToUInt16(bytes, pos);
            pos += 2;
            //read the name
            this.xstzName = DIaLOGIKa.b2xtranslator.Tools.Utils.readXstz(bytes,pos);
            pos += (this.xstzName.length() * 2) + 2 + 2;
            //read text def
            if (this.iType == FormFieldType.iTypeText)
            {
                this.xstzTextDef = DIaLOGIKa.b2xtranslator.Tools.Utils.readXstz(bytes,pos);
                pos += (this.xstzTextDef.length() * 2) + 2 + 2;
            }
             
            //definition
            if (this.iType == FormFieldType.iTypeChck || this.iType == FormFieldType.iTypeDrop)
            {
                this.wDef = System.BitConverter.ToUInt16(bytes, pos);
                pos += 2;
            }
             
            //read the text format
            this.xstzTextFormat = DIaLOGIKa.b2xtranslator.Tools.Utils.readXstz(bytes,pos);
            pos += (this.xstzTextFormat.length() * 2) + 2 + 2;
            //read the help test
            this.xstzHelpText = DIaLOGIKa.b2xtranslator.Tools.Utils.readXstz(bytes,pos);
            pos += (this.xstzHelpText.length() * 2) + 2 + 2;
            //read the status
            this.xstzStatText = DIaLOGIKa.b2xtranslator.Tools.Utils.readXstz(bytes,pos);
            pos += (this.xstzStatText.length() * 2) + 2 + 2;
            //read the entry macro
            this.xstzEntryMcr = DIaLOGIKa.b2xtranslator.Tools.Utils.readXstz(bytes,pos);
            pos += (this.xstzEntryMcr.length() * 2) + 2 + 2;
            //read the exit macro
            this.xstzExitMcr = DIaLOGIKa.b2xtranslator.Tools.Utils.readXstz(bytes,pos);
            pos += (this.xstzExitMcr.length() * 2) + 2 + 2;
        }
         
    }

    public enum FormFieldType
    {
        /**
        * Specifies that the form field is a textbox.
        */
        iTypeText,
        /**
        * Specifies that the form field is a checkbox.
        */
        iTypeChck,
        /**
        * Specifies that the form field is a dropdown list box.
        */
        iTypeDrop
    }
    public enum TextboxType
    {
        /**
        * Specifies that the textbox value is regular text.
        */
        regular,
        /**
        * Specifies that the textbox value is a number.
        */
        number,
        /**
        * Specifies that the textbox value is a date or time.
        */
        date,
        /**
        * Specifies that the textbox value is the current date.
        */
        currentDate,
        /**
        * Specifies that the textbox value is the current time.
        */
        currentTime,
        /**
        * Specifies that the textbox value is calculated from an expression.
        * The expression is given by xstzTextDef.
        */
        calculated
    }
    public <T>void convert(T mapping) throws Exception {
        ((IMapping<FormFieldData>)mapping).apply(this);
    }

}


