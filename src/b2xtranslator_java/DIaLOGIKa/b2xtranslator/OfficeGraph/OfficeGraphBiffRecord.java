//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:45 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.OfficeGraph.GraphRecordNumber;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecord;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeGraph.UnknownGraphRecord;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import java.util.HashMap;

/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
public abstract class OfficeGraphBiffRecord   
{
    GraphRecordNumber _id = GraphRecordNumber.HEADER;
    long _length;
    long _offset;
    /**
    * Ctor
    * 
    *  @param reader Streamreader
    *  @param id Record ID - Recordtype
    *  @param length The recordlegth
    */
    public OfficeGraphBiffRecord(IStreamReader reader, GraphRecordNumber id, long length) throws Exception {
        _reader = reader;
        _offset = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        _id = id;
        _length = length;
    }

    private static HashMap<UInt16,Class> TypeToRecordClassMapping = new HashMap<UInt16,Class>();
    static {
        try
        {
            UpdateTypeToRecordClassMapping(null /* getExecutingAssembly() */, OfficeGraphBiffRecord.class.Namespace);
        }
        catch (Exception __dummyStaticConstructorCatchVar1)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar1);
        }
    
    }

    public static void updateTypeToRecordClassMapping(Assembly assembly, String ns) throws Exception {
        for (Object __dummyForeachVar1 : assembly.GetTypes())
        {
            Class t = (Class)__dummyForeachVar1;
            if (ns == null || StringSupport.equals(t.Namespace, ns))
            {
                Object[] attrs = t.GetCustomAttributes(OfficeGraphBiffRecordAttribute.class, false);
                OfficeGraphBiffRecordAttribute attr = null;
                if (attrs.length > 0)
                    attr = attrs[0] instanceof OfficeGraphBiffRecordAttribute ? (OfficeGraphBiffRecordAttribute)attrs[0] : (OfficeGraphBiffRecordAttribute)null;
                 
                if (attr != null)
                {
                    for (Object __dummyForeachVar0 : attr.getTypeCodes())
                    {
                        // Add the type codes of the array
                        UInt16 typeCode = (UInt16)__dummyForeachVar0;
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

    public static GraphRecordNumber getNextRecordNumber(IStreamReader reader) throws Exception {
        // read next id
        GraphRecordNumber nextRecord = (GraphRecordNumber)reader.readUInt16();
        // seek back
        reader.getBaseStream().Seek(-, System.IO.SeekOrigin.Current);
        return nextRecord;
    }

    public static OfficeGraphBiffRecord readRecord(IStreamReader reader) throws Exception {
        OfficeGraphBiffRecord result = null;
        try
        {
            UInt16 id = reader.readUInt16();
            UInt16 size = reader.readUInt16();
            Class cls;
            RefSupport<Class> refVar___0 = new RefSupport<Class>();
            boolean boolVar___0 = TypeToRecordClassMapping.TryGetValue(id, refVar___0);
            cls = refVar___0.getValue();
            if (boolVar___0)
            {
                ConstructorInfo constructor = cls.GetConstructor(new Class[]{ IStreamReader.class, GraphRecordNumber.class, UInt16.class });
                try
                {
                    result = (OfficeGraphBiffRecord)constructor.Invoke(new Object[]{ reader, id, size });
                }
                catch (TargetInvocationException e)
                {
                    throw e.InnerException;
                }
            
            }
            else
            {
                result = new UnknownGraphRecord(reader,id,size);
            } 
            return result;
        }
        catch (OutOfMemoryException e)
        {
            throw new Exception("Invalid record", e);
        }
    
    }

    public GraphRecordNumber getId() throws Exception {
        return _id;
    }

    public long getLength() throws Exception {
        return _length;
    }

    public long getOffset() throws Exception {
        return _offset;
    }

    IStreamReader _reader;
    public IStreamReader getReader() throws Exception {
        return _reader;
    }

    public void setReader(IStreamReader value) throws Exception {
        this._reader = value;
    }

}


