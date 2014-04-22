//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:04 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Text.EncodingSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.StreamNotFoundException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.DirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.OleObject;
import java.util.HashMap;
import java.util.UUID;

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
public class OleObject   implements IVisitable
{
    public enum LinkUpdateOption
    {
        NoLink,
        Always,
        __dummyEnum__0,
        OnCall
    }
    public String ObjectId;
    public UUID ClassId;
    /**
    * The path of the object in the storage
    */
    public String Path;
    /**
    * The the value is true, the object is a linked object
    */
    public boolean fLinked;
    /**
    * Display name of the linked object or embedded object.
    */
    public String UserType;
    public String ClipboardFormat;
    public String Link;
    public String Program;
    public LinkUpdateOption UpdateMode = LinkUpdateOption.NoLink;
    public HashMap<String,VirtualStream> Streams;
    private StructuredStorageReader _docStorage;
    public OleObject(CharacterPropertyExceptions chpx, StructuredStorageReader docStorage) throws Exception {
        this._docStorage = docStorage;
        this.ObjectId = getOleEntryName(chpx);
        this.Path = "\\ObjectPool\\" + this.ObjectId + "\\";
        processOleStream(this.Path + "\u0001Ole");
        if (this.fLinked)
        {
            processLinkInfoStream(this.Path + "\u0003LinkInfo");
        }
        else
        {
            processCompObjStream(this.Path + "\u0001CompObj");
        } 
        //get the storage entries of this object
        this.Streams = new HashMap<String,VirtualStream>();
        for (String streamname : docStorage.getFullNameOfAllStreamEntries())
        {
            if (streamname.startsWith(this.Path))
            {
                this.Streams.put(streamname.substring(streamname.lastIndexOf("\\") + 1), docStorage.getStream(streamname));
            }
             
        }
        for (DirectoryEntry entry : docStorage.getAllEntries())
        {
            //find the class if of this object
            if (StringSupport.equals(entry.getName(), this.ObjectId))
            {
                this.ClassId = entry.getClsId();
                break;
            }
             
        }
    }

    private void processLinkInfoStream(String linkStream) throws Exception {
        try
        {
            VirtualStreamReader reader = new VirtualStreamReader(_docStorage.getStream(linkStream));
            //there are two versions of the Link string, one contains ANSI characters, the other contains
            //unicode characters.
            //Both strings seem not to be standardized:
            //The length prefix is a character count EXCLUDING the terminating zero
            //Read the ANSI version
            short cch = reader.readInt16();
            byte[] str = reader.ReadBytes(cch);
            this.Link = new String(str, EncodingSupport.GetEncoder("ASCII").getString());
            //skip the terminating zero of the ANSI string
            //even if the characters are ANSI chars, the terminating zero has 2 bytes
            reader.readBytes(2);
            //skip the next 4 bytes (flags?)
            reader.readBytes(4);
            //Read the Unicode version
            cch = reader.readInt16();
            str = reader.ReadBytes(cch * 2);
            this.Link = Encoding.Unicode.GetString(str);
            //skip the terminating zero of the Unicode string
            reader.readBytes(2);
        }
        catch (StreamNotFoundException __dummyCatchVar0)
        {
        }
    
    }

    private void processCompObjStream(String compStream) throws Exception {
        try
        {
            VirtualStreamReader reader = new VirtualStreamReader(_docStorage.getStream(compStream));
            //skip the CompObjHeader
            reader.readBytes(28);
            this.UserType = DIaLOGIKa.b2xtranslator.Tools.Utils.readLengthPrefixedAnsiString(reader.getBaseStream());
            this.ClipboardFormat = DIaLOGIKa.b2xtranslator.Tools.Utils.readLengthPrefixedAnsiString(reader.getBaseStream());
            this.Program = DIaLOGIKa.b2xtranslator.Tools.Utils.readLengthPrefixedAnsiString(reader.getBaseStream());
        }
        catch (StreamNotFoundException __dummyCatchVar1)
        {
        }
    
    }

    private void processOleStream(String oleStream) throws Exception {
        try
        {
            VirtualStreamReader reader = new VirtualStreamReader(_docStorage.getStream(oleStream));
            //skip version
            reader.readBytes(4);
            //read the embedded/linked flag
            int flag = reader.readInt32();
            this.fLinked = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(flag,0x1);
            //Link update option
            this.UpdateMode = LinkUpdateOption.values()[reader.readInt32()];
        }
        catch (StreamNotFoundException __dummyCatchVar2)
        {
        }
    
    }

    private String getOleEntryName(CharacterPropertyExceptions chpx) throws Exception {
        String ret = null;
        for (SinglePropertyModifier sprm : chpx.grpprl)
        {
            if (sprm.OpCode == OperationCode.sprmCPicLocation)
            {
                ret = "_" + System.BitConverter.ToUInt32(sprm.Arguments, 0);
                break;
            }
             
        }
        return ret;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<OleObject>)mapping).apply(this);
    }

}


