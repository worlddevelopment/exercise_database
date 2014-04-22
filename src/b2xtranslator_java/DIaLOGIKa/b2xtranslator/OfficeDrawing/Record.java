//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:33 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import CS2JNet.JavaSupport.Collections.Generic.IteratorSupport;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.IEnumerable;
import CS2JNet.System.Collections.LCC.IEnumerator;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.InvalidRecordException;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Record;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.UnknownRecord;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

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
public class Record   implements IEnumerable<Record>, IVisitable
{
    public static final uint HEADER_SIZE_IN_BYTES = (16 + 16 + 32) / 8;
    public uint getTotalSize() throws Exception {
        return HeaderSize + BodySize;
    }

    private DIaLOGIKa.b2xtranslator.OfficeDrawing.Record _ParentRecord = null;
    public DIaLOGIKa.b2xtranslator.OfficeDrawing.Record getParentRecord() throws Exception {
        return _ParentRecord;
    }

    public void setParentRecord(DIaLOGIKa.b2xtranslator.OfficeDrawing.Record value) throws Exception {
        if (_ParentRecord != null)
            throw new Exception("Can only set ParentRecord once");
         
        _ParentRecord = value;
        this.afterParentSet();
    }

    public uint HeaderSize = HEADER_SIZE_IN_BYTES;
    public uint BodySize;
    public byte[] RawData;
    protected BinaryReader Reader = new BinaryReader();
    /**
    * Index of sibling, 0 for first child in container, 1 for second child and so on...
    */
    public uint SiblingIdx;
    public uint TypeCode;
    public uint Version;
    public uint Instance;
    public Record() throws Exception {
    }

    public Record(BinaryReader _reader, uint bodySize, uint typeCode, uint version, uint instance) throws Exception {
        this.BodySize = bodySize;
        this.TypeCode = typeCode;
        this.Version = version;
        this.Instance = instance;
        if (this.BodySize <= _reader.BaseStream.Length)
        {
            this.RawData = _reader.ReadBytes((int)this.BodySize);
        }
        else
        {
            this.RawData = _reader.ReadBytes((int)(_reader.BaseStream.Length - _reader.BaseStream.Position));
        } 
        this.Reader = new BinaryReader(new MemoryStream(this.RawData));
    }

    public void afterParentSet() throws Exception {
    }

    public void dumpToStream(InputStream output) throws Exception {
        BinaryWriter writer = new BinaryWriter(output);
        try
        {
            {
                writer.Write(this.RawData, 0, this.RawData.length);
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
    }

    public String getIdentifier() throws Exception {
        StringBuilder result = new StringBuilder();
        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record r = this;
        boolean isFirst = true;
        while (r != null)
        {
            if (!isFirst)
                result.insert(0, " - ");
             
            result.insert(0, String.format(StringSupport.CSFmtStrToJFmtStr("{2}.{0}i{1}p"),r.formatType(),r.Instance,r.SiblingIdx));
            r = r.getParentRecord();
            isFirst = false;
        }
        return result.toString();
    }

    public String formatType() throws Exception {
        boolean isEscherRecord = (this.TypeCode >= 0xF000 && this.TypeCode <= 0xFFFF);
        return String.format(StringSupport.CSFmtStrToJFmtStr(isEscherRecord ? "0x{0:X}" : "{0}"),this.TypeCode);
    }

    public String toString(uint depth) throws Exception {
        return String.Format("{0}{2}:\n{1}Type = {3}, Version = {4}, Instance = {5}, BodySize = {6}", indentationForDepth(depth), indentationForDepth(depth + 1), this.getClass(), this.formatType(), this.Version, this.Instance, this.BodySize);
    }

    public String toString() {
        try
        {
            return this.ToString(0);
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

    public boolean getDoAutomaticVerifyReadToEnd() throws Exception {
        return true;
    }

    public void verifyReadToEnd() throws Exception {
        long streamPos = this.Reader.BaseStream.Position;
        long streamLen = this.Reader.BaseStream.Length;
        if (streamPos != streamLen)
        {
            TraceLogger.debugInternal("Record {3} didn't read to end: (stream position: {1} of {2})\n{0}",this,streamPos,streamLen,this.getIdentifier());
        }
         
    }

    /**
    * Finds the first ancestor of the given type.
    * Type of ancestor to search for
    *  @return First ancestor with appropriate type or null if none was found
    */
    public <T extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record>T firstAncestorWithType() throws Exception {
        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record curAncestor = this.getParentRecord();
        while (curAncestor != null)
        {
            if (curAncestor instanceof T)
                return (T)curAncestor;
             
            curAncestor = curAncestor.getParentRecord();
        }
        return null;
    }

    <T>void iVisitable___Convert(T mapping) throws Exception {
        ((IMapping<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record>)mapping).apply(this);
    }

    public IEnumerator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> getEnumerator() throws Exception {
    }

    IEnumerator iEnumerable___GetEnumerator() throws Exception {
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record : this)
    
    }

    public static String indentationForDepth(uint depth) throws Exception {
        StringBuilder result = new StringBuilder();
        for (uint i = 0;i < depth;i++)
            result.append("  ");
        return result.toString();
    }

    private static HashMap<UInt16,Class> TypeToRecordClassMapping = new HashMap<UInt16,Class>();
    static {
        try
        {
            UpdateTypeToRecordClassMapping(null /* getExecutingAssembly() */, DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.class.Namespace);
        }
        catch (Exception __dummyStaticConstructorCatchVar2)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar2);
        }
    
    }

    /**
    * Updates the Dictionary used for mapping Office record TypeCodes to Office record classes.
    * This is done by querying all classes in the specified assembly filtered by the specified
    * namespace and looking for attributes of type OfficeRecordAttribute.
    * 
    *  @param assembly Assembly to scan
    *  @param ns Namespace to scan or null for all namespaces
    */
    public static void updateTypeToRecordClassMapping(Assembly assembly, String ns) throws Exception {
        for (Object __dummyForeachVar2 : assembly.GetTypes())
        {
            Class t = (Class)__dummyForeachVar2;
            if (ns == null || StringSupport.equals(t.Namespace, ns))
            {
                Object[] attrs = t.GetCustomAttributes(OfficeRecordAttribute.class, false);
                OfficeRecordAttribute attr = null;
                if (attrs.length > 0)
                    attr = attrs[0] instanceof OfficeRecordAttribute ? (OfficeRecordAttribute)attrs[0] : (OfficeRecordAttribute)null;
                 
                if (attr != null)
                {
                    for (UInt16 typeCode : attr.getTypeCodes())
                    {
                        // Add the type codes of the array
                        if (TypeToRecordClassMapping.containsKey(typeCode))
                        {
                            throw new Exception(String.format(StringSupport.CSFmtStrToJFmtStr("Tried to register TypeCode {0} to {1}, but it is already registered to {2}"),typeCode,t,TypeToRecordClassMapping.get(typeCode)));
                        }
                         
                        TypeToRecordClassMapping.put(typeCode, t);
                    }
                }
                 
            }
             
        }
    }

    public static DIaLOGIKa.b2xtranslator.OfficeDrawing.Record readRecord(InputStream stream) throws Exception {
        return readRecord(new BinaryReader(stream));
    }

    public static DIaLOGIKa.b2xtranslator.OfficeDrawing.Record readRecord(BinaryReader reader) throws Exception {
        try
        {
            UInt16 verAndInstance = reader.ReadUInt16();
            uint version = verAndInstance & 0x000F;
            // first 4 bit of field verAndInstance
            uint instance = (verAndInstance & 0xFFF0) >> 4;
            // last 12 bit of field verAndInstance
            UInt16 typeCode = reader.ReadUInt16();
            long size = reader.ReadUInt32();
            boolean isContainer = (version == 0xF);
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record result;
            Class cls;
            RefSupport<Class> refVar___0 = new RefSupport<Class>();
            boolean boolVar___0 = TypeToRecordClassMapping.TryGetValue(typeCode, refVar___0);
            cls = refVar___0.getValue();
            if (boolVar___0)
            {
                ConstructorInfo constructor = cls.GetConstructor(new Class[]{ BinaryReader.class, uint.class, uint.class, uint.class, uint.class });
                if (constructor == null)
                {
                    throw new Exception(String.format(StringSupport.CSFmtStrToJFmtStr("Internal error: Could not find a matching constructor for class {0}"),cls));
                }
                 
                try
                {
                    //TraceLogger.DebugInternal("Going to read record of type {0} ({1})", cls, typeCode);
                    result = (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record)constructor.Invoke(new Object[]{ reader, size, typeCode, version, instance });
                }
                catch (TargetInvocationException e)
                {
                    //TraceLogger.DebugInternal("Here it is: {0}", result);
                    TraceLogger.debugInternal(e.InnerException.toString());
                    throw e.InnerException;
                }
            
            }
            else
            {
                //TraceLogger.DebugInternal("Going to read record of type UnknownRecord ({1})", cls, typeCode);
                result = new UnknownRecord(reader, size, typeCode, version, instance);
            } 
            return result;
        }
        catch (OutOfMemoryException e)
        {
            throw new InvalidRecordException("Invalid record", e);
        }
    
    }

    public Iterator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> iterator() {
        Iterator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> ret = null;
        try
        {
            ret = IteratorSupport.mk(this.getEnumerator());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ret;
    }

}


