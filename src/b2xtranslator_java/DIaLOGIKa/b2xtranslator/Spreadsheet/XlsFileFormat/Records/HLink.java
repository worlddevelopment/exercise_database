//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:44 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

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
public class HLink  extends BiffRecord 
{
    public static final RecordType ID = RecordType.HLink;
    public UInt16 rwFirst = new UInt16();
    public UInt16 rwLast = new UInt16();
    public UInt16 colFirst = new UInt16();
    public UInt16 colLast = new UInt16();
    public long streamVersion;
    public boolean hlstmfIsAbsolute;
    public byte[] hlinkClsid;
    public String displayName;
    public String targetFrameName;
    public String monikerString;
    public String location;
    public byte[] guid;
    public byte[] fileTime;
    public HLink(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        this.rwFirst = this.Reader.ReadUInt16();
        this.rwLast = this.Reader.ReadUInt16();
        this.colFirst = this.Reader.ReadUInt16();
        this.colLast = this.Reader.ReadUInt16();
        this.hlinkClsid = new byte[16];
        // read 16 bytes hlinkClsid
        hlinkClsid = this.Reader.ReadBytes(16);
        streamVersion = this.Reader.ReadUInt32();
        long buffer = this.Reader.ReadUInt32();
        boolean hlstmfHasMoniker = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x01);
        this.hlstmfIsAbsolute = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x02);
        boolean hlstmfSiteGaveDisplayName = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x04);
        boolean hlstmfHasLocationStr = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x08);
        boolean hlstmfHasDisplayName = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x10);
        boolean hlstmfHasGUID = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x20);
        boolean hlstmfHasCreationTime = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x40);
        boolean hlstmfHasFrameName = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x80);
        boolean hlstmfMonikerSavedAsStr = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x100);
        boolean hlstmfAbsFromGetdataRel = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x200);
        if (hlstmfHasDisplayName)
        {
            this.displayName = ExcelHelperClass.getHyperlinkStringFromBiffRecord(this.Reader);
        }
         
        if (hlstmfHasFrameName)
        {
            this.targetFrameName = ExcelHelperClass.getHyperlinkStringFromBiffRecord(this.Reader);
        }
         
        if (hlstmfHasMoniker)
        {
            if (hlstmfMonikerSavedAsStr)
            {
                this.monikerString = ExcelHelperClass.getHyperlinkStringFromBiffRecord(this.Reader);
            }
            else
            {
                // OleMoniker
                // read monikerClsid
                long Part1MonikerClsid = this.Reader.ReadUInt32();
                UInt16 Part2MonikerClsid = this.Reader.ReadUInt16();
                UInt16 Part3MonikerClsid = this.Reader.ReadUInt16();
                byte Part4MonikerClsid = this.Reader.ReadByte();
                byte Part5MonikerClsid = this.Reader.ReadByte();
                byte Part6MonikerClsid = this.Reader.ReadByte();
                byte Part7MonikerClsid = this.Reader.ReadByte();
                byte Part8MonikerClsid = this.Reader.ReadByte();
                byte Part9MonikerClsid = this.Reader.ReadByte();
                byte Part10MonikerClsid = this.Reader.ReadByte();
                byte Part11MonikerClsid = this.Reader.ReadByte();
                // URL Moniker
                if (Part1MonikerClsid == 0x79EAC9E0)
                {
                    long lenght = reader.readUInt32();
                    String value = "";
                    do
                    {
                        // read until the \0 value
                        value += System.BitConverter.ToChar(reader.readBytes(2), 0);
                    }
                    while (value.charAt(value.length() - 1) != '\0');
                    if (value.length() * 2 != lenght)
                    {
                        // read guid serial version and uriflags
                        this.Reader.ReadBytes(24);
                    }
                     
                    value = value.Remove(value.length() - 1);
                    this.monikerString = value;
                }
                else if (Part1MonikerClsid == 0x00000303)
                {
                    UInt16 cAnti = this.Reader.ReadUInt16();
                    long ansiLength = this.Reader.ReadUInt32();
                    String ansiPath = "";
                    for (int i = 0;i < ansiLength;i++)
                    {
                        ansiPath += (char)reader.readByte();
                    }
                    ansiPath = ansiPath.Remove(ansiPath.length() - 1);
                    UInt16 endServer = this.Reader.ReadUInt16();
                    UInt16 versionNumber = this.Reader.ReadUInt16();
                    this.monikerString = ansiPath;
                    // read 20 unused bytes
                    this.Reader.ReadBytes(20);
                    long cbUnicodePathSize = this.Reader.ReadUInt32();
                    String unicodePath = "";
                    if (cbUnicodePathSize != 0)
                    {
                        long cbUnicodePathBytes = this.Reader.ReadUInt32();
                        UInt16 usKeyValue = this.Reader.ReadUInt16();
                        String value = "";
                        for (int i = 0;i < cbUnicodePathBytes / 2;i++)
                        {
                            value += System.BitConverter.ToChar(reader.readBytes(2), 0);
                        }
                        this.monikerString = value;
                    }
                     
                }
                  
            } 
        }
         
        //byte[] monikerClsid = this.Reader.ReadBytes(16);
        //string monikerid = "";
        //for (int i = 0; i < monikerClsid.Length; i++)
        //{
        //    monikerid = monikerid + monikerClsid[i].ToString();
        //}
        if (hlstmfHasLocationStr)
        {
            this.location = ExcelHelperClass.getHyperlinkStringFromBiffRecord(this.Reader);
        }
         
        if (hlstmfHasGUID)
        {
            this.guid = this.Reader.ReadBytes(16);
        }
         
        if (hlstmfHasCreationTime)
        {
            this.fileTime = this.Reader.ReadBytes(8);
        }
         
    }

}


