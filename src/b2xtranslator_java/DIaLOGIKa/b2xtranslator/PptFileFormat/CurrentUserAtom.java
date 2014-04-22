//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

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
public class CurrentUserAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    /**
    * Encoding to use for decoding ANSI strings.
    */
    private static final Encoding ANSIEncoding = Encoding.GetEncoding("iso-8859-1");
    /**
    * An unsigned integer that specifies the length, in bytes, of the fixed-length portion of the record.
    * It MUST be 0x00000014.
    */
    public long Size;
    /**
    * An unsigned integer that specifies a token used to identify whether the file is encrypted.
    * 
    * It MUST be a value from the following table:
    * 0xE391C05F: The file SHOULD NOT be an encrypted document.
    * 0xF3D1C4DF: The file MUST be an encrypted document.
    */
    public long HeaderToken;
    /**
    * An unsigned integer that specifies an offset, in bytes, from the beginning of the
    * PowerPoint DocumentRecord Stream to the UserEditAtom record for the most recent user edit.
    */
    public long OffsetToCurrentEdit;
    /**
    * An unsigned integer that specifies the length, in bytes, of the  ansiUserName field.
    * It MUST be less than or equal to 255.
    */
    public UInt16 LengthUserName = new UInt16();
    /**
    * An unsigned integer that specifies the document file version of the file.
    * It MUST be 0x03F4.
    */
    public UInt16 DocFileVersion = new UInt16();
    /**
    * An unsigned integer that specifies the major version of the storage format.
    * It MUST be 0x03.
    */
    public byte MajorVersion;
    /**
    * An unsigned integer that specifies the minor version of the storage format.
    * It MUST be 0x00.
    */
    public byte MinorVersion;
    /**
    * A PrintableAnsiString that specifies the user name of the last user to
    * modify the file. The length, in bytes, of the field is specified by the
    * lenUserName field.
    */
    public String UserNameANSI;
    /**
    * An unsigned integer that specifies the release version of the file format.
    * 
    * MUST be a value from the following table:
    * 0x00000008: The file contains one or more main master slide.
    * 0x00000009: The file contains more than one main master slide. It SHOULD NOT be used.
    */
    public long ReleaseVersion;
    /**
    * An optional PrintableUnicodeString that specifies the user name of the
    * last user to modify the file.
    * 
    * The length, in bytes, of the field is specified by 2 * lenUserName.
    * 
    * This user name supersedes that specified by the ansiUserName field.
    * 
    * It MAY be omitted.
    */
    public String UserNameUnicode;
    /**
    * UserNameUnicode if present, else UserNameANSI.
    */
    public String getUserName() throws Exception {
        return (this.UserNameUnicode != null) ? this.UserNameUnicode : this.UserNameANSI;
    }

    public CurrentUserAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.Size = this.Reader.ReadUInt32();
        this.HeaderToken = this.Reader.ReadUInt32();
        long __dummyScrutVar0 = this.HeaderToken;
        if (__dummyScrutVar0.equals(0xE391C05F))
        {
        }
        else /* regular PPT file */
        if (__dummyScrutVar0.equals(0xF3D1C4DF))
        {
            throw new NotSupportedException("Encryped PPT files aren't supported at this time");
        }
        else
        {
            throw new NotSupportedException(String.format(StringSupport.CSFmtStrToJFmtStr("File doesn't seem to be a PPT file. Magic Bytes = {0}"),this.HeaderToken));
        }  
        /* encrypted PPT file */
        this.OffsetToCurrentEdit = this.Reader.ReadUInt32();
        this.LengthUserName = this.Reader.ReadUInt16();
        this.DocFileVersion = this.Reader.ReadUInt16();
        this.MajorVersion = this.Reader.ReadByte();
        this.MinorVersion = this.Reader.ReadByte();
        // Throw away reserved data
        this.Reader.ReadUInt16();
        byte[] ansiUserNameBytes = this.Reader.ReadBytes(this.LengthUserName);
        this.UserNameANSI = new String(ansiUserNameBytes, ANSIEncoding.getString());
        this.ReleaseVersion = this.Reader.ReadUInt32();
        if (this.Reader.BaseStream.Position != this.Reader.BaseStream.Length)
        {
            byte[] unicodeUserNameBytes = this.Reader.ReadBytes(this.LengthUserName * 2);
            this.UserNameUnicode = Encoding.Unicode.GetString(unicodeUserNameBytes);
        }
         
    }

    public String toString(uint depth) throws Exception {
        return String.Format("{0}\n{1}Size = {2}, Magic = {3}, OffsetToCurrentEdit = {4}\n{1}" + "LengthUserName = {5}, DocFileVersion = {6}, MajorVersion = {7}, MinorVersion = {8}\n{1}" + "UserNameANSI = {9}, ReleaseVersion = {10}, UserNameUnicode = {11}", super.toString(depth), indentationForDepth(depth + 1), this.Size, this.HeaderToken, this.OffsetToCurrentEdit, this.LengthUserName, this.DocFileVersion, this.MajorVersion, this.MinorVersion, this.UserNameANSI, this.ReleaseVersion, this.UserNameUnicode);
    }

}


