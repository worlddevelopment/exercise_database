//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:46 AM
//

package DIaLOGIKa.b2xtranslator.OlePropertySet;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class ValueProperty   
{
    public enum PropertyType
    {
        Empty,
        Null,
        SignedInt16,
        SignedInt32,
        FloatingPoint32,
        FloatingPoint64,
        Currency,
        Date,
        CodePageString1,
        HResult,
        Boolean,
        Decimal,
        SignedByte,
        UnsignedByte,
        UnsignedInt16,
        UnsignedInt32,
        SignedInt64,
        UsignedInt64,
        NewSignedInt32,
        NewUnsignedInt32,
        CodePageString2,
        UnicodeString,
        FileTime,
        Blob,
        Stream,
        Storage,
        StreamObject,
        StorageObject,
        BlobObject,
        PropertyId,
        ClassId,
        VersionedStream,
        VectorOfSignedInt16,
        VectorOfSignedInt32,
        VectorOfFloatingPoint32,
        VectorOfFloatingPoint64,
        VectorOfCurrency,
        VectorOfDate,
        VectorOfCodePageString1,
        VectorOfHResult,
        VectorOfBoolean,
        VectorOfVariables,
        VectorOfSignedByte,
        VectorOfUnsignedByte,
        VectorOfUnsignedInt16,
        VectorOfUnsignedInt32,
        VectorOfSignedInt64,
        VectorOfUsignedInt64,
        VectorOfCodePageString2,
        VectorOfUnicodeString,
        VectorOfFileTime,
        VectorOfPropertyId,
        VectorOfClassId,
        ArrayOfSignedInt16,
        ArrayOfSignedInt32,
        ArrayOfFloatingPoint32,
        ArrayOfFloatingPoint64,
        ArrayOfCurrency,
        ArrayOfDate,
        ArrayOfCodePageString1,
        ArrayOfHResult,
        ArrayOfBoolean,
        ArrayOfVariables,
        ArrayOfDecimal,
        ArrayOfSignedByte,
        ArrayOfUnsignedByte,
        ArrayOfUnsignedInt16,
        ArrayOfUnsignedInt32,
        ArrayOfSignedInt4Byte,
        ArrayOfUnsignedInt4Byte
    }
    public PropertyType Type = PropertyType.Empty;
    public byte[] Data;
    public ValueProperty(VirtualStreamReader stream) throws Exception {
        //read type
        this.Type = (PropertyType)stream.readUInt16();
        //skip padding
        stream.readBytes(2);
        //read data
        if (Type == PropertyType.SignedInt16 || Type == PropertyType.UnsignedInt16)
        {
            // 2 bytes data
            this.Data = stream.readBytes(2);
        }
        else if (Type == PropertyType.SignedInt32 || Type == PropertyType.UnsignedInt32 || Type == PropertyType.FloatingPoint32 || Type == PropertyType.NewSignedInt32 || Type == PropertyType.NewUnsignedInt32 || Type == PropertyType.HResult || Type == PropertyType.Boolean)
        {
            // 4 bytes data
            this.Data = stream.readBytes(4);
        }
        else if (Type == PropertyType.FloatingPoint64 || Type == PropertyType.SignedInt64 || Type == PropertyType.UsignedInt64 || Type == PropertyType.Currency || Type == PropertyType.Date)
        {
            // 8 bytes data
            this.Data = stream.readBytes(8);
        }
        else if (Type == PropertyType.Decimal)
        {
            // 16 bytes data
            this.Data = stream.readBytes(16);
        }
        else
        {
            // not yet implemented
            this.Data = new byte[0];
        }    
    }

}


