//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BOF;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.FrtWrapper;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.UnknownBiffRecord;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import java.util.HashMap;

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
public abstract class BiffRecord   
{
    IStreamReader _reader;
    RecordType _id = RecordType.NAME;
    long _length;
    long _offset;
    /**
    * Ctor
    * 
    *  @param reader Streamreader
    *  @param id Record ID - Recordtype
    *  @param length The recordlegth
    */
    public BiffRecord(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        _reader = reader;
        _offset = Unsupported.throwUnsupported("_reader.getBaseStream().Position");
        _id = id;
        _length = length;
    }

    private static HashMap<UInt16,Class> TypeToRecordClassMapping = new HashMap<UInt16,Class>();
    static {
        try
        {
            UpdateTypeToRecordClassMapping(null /* getExecutingAssembly() */, BOF.class.Namespace);
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
                Object[] attrs = t.GetCustomAttributes(BiffRecordAttribute.class, false);
                BiffRecordAttribute attr = null;
                if (attrs.length > 0)
                    attr = attrs[0] instanceof BiffRecordAttribute ? (BiffRecordAttribute)attrs[0] : (BiffRecordAttribute)null;
                 
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

    public static RecordType getNextRecordType(IStreamReader reader) throws Exception {
        long position = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        // read type of the next record
        RecordType nextRecord = (RecordType)reader.readUInt16();
        UInt16 length = reader.readUInt16();
        // skip leading StartBlock/EndBlock records
        if (nextRecord == RecordType.StartBlock || nextRecord == RecordType.EndBlock || nextRecord == RecordType.StartObject || nextRecord == RecordType.EndObject || nextRecord == RecordType.ChartFrtInfo)
        {
            // skip the body of the record
            reader.ReadBytes(length);
            return getNextRecordType(reader);
        }
        else // get the type of the next record
        if (nextRecord == RecordType.FrtWrapper)
        {
            // return type of wrapped Biff record
            FrtWrapper frtWrapper = new FrtWrapper(reader, nextRecord, length);
            Unsupported.throwUnsupported("reader.getBaseStream().Position = position");
            return frtWrapper.wrappedRecord.Id;
        }
        else
        {
            // seek back to the begin of the current record
            Unsupported.throwUnsupported("reader.getBaseStream().Position = position");
            return nextRecord;
        }  
    }

    public static BiffRecord readRecord(IStreamReader reader) throws Exception {
        BiffRecord result = null;
        try
        {
            RecordType id = (RecordType)reader.readUInt16();
            UInt16 length = reader.readUInt16();
            // skip leading StartBlock/EndBlock records
            if (id == RecordType.StartBlock || id == RecordType.EndBlock || id == RecordType.StartObject || id == RecordType.EndObject || id == RecordType.ChartFrtInfo)
            {
                // skip the body of this record
                reader.ReadBytes(length);
                return readRecord(reader);
            }
            else // get the next record
            if (id == RecordType.FrtWrapper)
            {
                // return type of wrapped Biff record
                FrtWrapper frtWrapper = new FrtWrapper(reader, id, length);
                return frtWrapper.wrappedRecord;
            }
              
            Class cls;
            RefSupport<Class> refVar___0 = new RefSupport<Class>();
            boolean boolVar___0 = TypeToRecordClassMapping.TryGetValue((UInt16)id, refVar___0);
            cls = refVar___0.getValue();
            if (boolVar___0)
            {
                ConstructorInfo constructor = cls.GetConstructor(new Class[]{ IStreamReader.class, RecordType.class, UInt16.class });
                try
                {
                    result = (BiffRecord)constructor.Invoke(new Object[]{ reader, id, length });
                }
                catch (TargetInvocationException e)
                {
                    throw e.InnerException;
                }
            
            }
            else
            {
                result = new UnknownBiffRecord(reader,(RecordType)id,length);
            } 
            return result;
        }
        catch (OutOfMemoryException e)
        {
            throw new Exception("Invalid BIFF record", e);
        }
    
    }

    public RecordType getId() throws Exception {
        return _id;
    }

    public long getLength() throws Exception {
        return _length;
    }

    public long getOffset() throws Exception {
        return _offset;
    }

    public IStreamReader getReader() throws Exception {
        return _reader;
    }

    public void setReader(IStreamReader value) throws Exception {
        this._reader = value;
    }

}


