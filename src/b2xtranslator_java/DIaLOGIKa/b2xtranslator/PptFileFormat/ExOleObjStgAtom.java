//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExOleObjStgAtom;

public class ExOleObjStgAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record implements IVisitable
{
    public uint len = 0;
    public long decompressedSize = 0;
    public byte[] data;
    public ExOleObjStgAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        if (instance == 0)
        {
            //uncompressed
            data = this.Reader.ReadBytes((int)size);
        }
        else
        {
            //compressed
            decompressedSize = this.Reader.ReadUInt32();
            len = size - 4;
            data = this.Reader.ReadBytes((int)len);
        } 
    }

    public byte[] decompressData() throws Exception {
        // create memory stream to the data
        MemoryStream msCompressed = new MemoryStream(data);
        // skip the first 2 bytes
        msCompressed.ReadByte();
        msCompressed.ReadByte();
        // decompress the bytes
        byte[] decompressedBytes = new byte[decompressedSize];
        DeflateStream deflateStream = new DeflateStream(msCompressed, CompressionMode.Decompress, true);
        deflateStream.Read(decompressedBytes, 0, decompressedBytes.length);
        return decompressedBytes;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ExOleObjStgAtom>)mapping).apply(this);
    }

}


