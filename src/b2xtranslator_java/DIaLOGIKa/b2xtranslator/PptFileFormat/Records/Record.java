//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:57 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.JavaSupport.Collections.Generic.IteratorSupport;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.IEnumerable;
import CS2JNet.System.Collections.LCC.IEnumerator;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.UnknownRecord;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class Record   implements IEnumerable<Record>
{
    public static final uint HEADER_SIZE_IN_BYTES = (16 + 16 + 32) / 8;
    public uint getTotalSize() throws Exception {
        return HeaderSize + BodySize;
    }

    private DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record _ParentRecord = null;
    public DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record getParentRecord() throws Exception {
        return _ParentRecord;
    }

    public void setParentRecord(DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record value) throws Exception {
        if (_ParentRecord != null)
            throw new Exception("Can only set ParentRecord once");
         
        _ParentRecord = value;
        this.afterParentSet();
    }

    public uint HeaderSize = HEADER_SIZE_IN_BYTES;
    public uint BodySize;
    public byte[] RawData;
    protected BinaryReader Reader = new BinaryReader();
    public uint TypeCode;
    public uint Version;
    public uint Instance;
    public Record(BinaryReader _reader, uint bodySize, uint typeCode, uint version, uint instance) throws Exception {
        this.BodySize = bodySize;
        this.TypeCode = typeCode;
        this.Version = version;
        this.Instance = instance;
        this.RawData = _reader.ReadBytes((int)this.BodySize);
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
        DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record r = this;
        boolean isFirst = true;
        while (r != null)
        {
            if (!isFirst)
                result.insert(0, "-");
             
            result.insert(0, String.format(StringSupport.CSFmtStrToJFmtStr("{0}i{1}"),r.formatType(),r.Instance));
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

    public void verifyReadToEnd() throws Exception {
        long streamPos = this.Reader.BaseStream.Position;
        long streamLen = this.Reader.BaseStream.Length;
        if (streamPos != streamLen)
        {
            throw new Exception(String.Format("Record {3} didn't read to end: (stream position: {1} of {2})\n{0}", this, streamPos, streamLen, this.getIdentifier()));
        }
         
    }

    public IEnumerator<DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record> getEnumerator() throws Exception {
    }

    IEnumerator iEnumerable___GetEnumerator() throws Exception {
        for (DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record record : this)
    
    }

    public static String indentationForDepth(uint depth) throws Exception {
        StringBuilder result = new StringBuilder();
        for (uint i = 0;i < depth;i++)
            result.append("  ");
        return result.toString();
    }

    public static HashMap<UInt16,Class> TypeToRecordClassMapping = getTypeToRecordClassMapping();
    private static HashMap<UInt16,Class> getTypeToRecordClassMapping() throws Exception {
        HashMap<UInt16,Class> result = new HashMap<UInt16,Class>();
        for (Object __dummyForeachVar1 : null /* getExecutingAssembly() */.GetTypes())
        {
            // Note: We return a Dictionary that maps Office record TypeCodes to Office record classes.
            // We do this by querying all classes in the current assembly, filtering by namespace
            // PptFileFormat.Records and looking for attributes of type OfficeRecord.
            //
            // If in doubt see usage below.
            Class t = (Class)__dummyForeachVar1;
            if (t.Namespace == DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record.class.Namespace)
            {
                Object[] attrs = t.GetCustomAttributes(OfficeRecordAttribute.class, false);
                OfficeRecordAttribute attr = null;
                if (attrs.length > 0)
                    attr = attrs[0] instanceof OfficeRecordAttribute ? (OfficeRecordAttribute)attrs[0] : (OfficeRecordAttribute)null;
                 
                if (attr != null)
                {
                    UInt16 typeCode = attr.TypeCode;
                    if (result.containsKey(typeCode))
                    {
                        throw new Exception(String.format(StringSupport.CSFmtStrToJFmtStr("Tried to register TypeCode {0} to {1}, but it is already registered to {2}"),typeCode,t,result.get(typeCode)));
                    }
                     
                    result.put(attr.TypeCode, t);
                }
                 
            }
             
        }
        return result;
    }

    public static DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record readRecord(InputStream stream) throws Exception {
        return readRecord(new BinaryReader(stream));
    }

    public static DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record readRecord(BinaryReader reader) throws Exception {
        UInt16 verAndInstance = reader.ReadUInt16();
        uint version = verAndInstance & 0x000F;
        // first 4 bit of field verAndInstance
        uint instance = (verAndInstance & 0xFFF0) >> 4;
        // last 12 bit of field verAndInstance
        UInt16 typeCode = reader.ReadUInt16();
        long size = reader.ReadUInt32();
        boolean isContainer = (version == 0xF);
        DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record result;
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
                result = (DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record)constructor.Invoke(new Object[]{ reader, size, typeCode, version, instance });
            }
            catch (TargetInvocationException e)
            {
                System.out.println(e.InnerException);
                throw e.InnerException;
            }
        
        }
        else
        {
            result = new UnknownRecord(reader, size, typeCode, version, instance);
        } 
        return result;
    }

    public Iterator<DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record> iterator() {
        Iterator<DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record> ret = null;
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


