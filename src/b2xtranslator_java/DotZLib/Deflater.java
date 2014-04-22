//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.ArgumentException;
import DotZLib.CodecBase;
import DotZLib.CompressLevel;
import DotZLib.FlushTypes;
import DotZLib.Info;
import DotZLib.ZLibException;
import DotZLib.ZStream;

//
// � Copyright Henrik Ravn 2004
//
// Use, modification and distribution are subject to the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
//
/**
* Implements a data compressor, using the deflate algorithm in the ZLib dll
*/
public final class Deflater  extends CodecBase 
{
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int deflateInit_(RefSupport<ZStream> sz, int level, String vs, int size) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int deflate(RefSupport<ZStream> sz, int flush) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int deflateReset(RefSupport<ZStream> sz) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ Integer deflateEnd(RefSupport<ZStream> sz) throws Exception ;

    /**
    * Constructs an new instance of the 
    *  {@code Deflater}
    * 
    *  @param level The compression level to use for this 
    *  {@code Deflater}
    */
    public Deflater(CompressLevel level) throws Exception {
        super();
        RefSupport<ZStream> refVar___0 = new RefSupport<ZStream>(_ztream);
        int retval = deflateInit_(refVar___0, ((Enum)level).ordinal(), Info.getVersion(), Marshal.SizeOf(_ztream));
        _ztream = refVar___0.getValue();
        if (retval != 0)
            throw new ZLibException(retval,"Could not initialize deflater");
         
        resetOutput();
    }

    /**
    * Adds more data to the codec to be processed.
    * 
    *  @param data Byte array containing the data to be added to the codec
    *  @param offset The index of the first byte to add from 
    *  {@code data}
    * 
    *  @param count The number of bytes to addAdding data may, or may not, raise the 
    *  {@code DataAvailable}
    *  event
    */
    public void add(byte[] data, int offset, int count) throws Exception {
        if (data == null)
            throw new ArgumentNullException();
         
        if (offset < 0 || count < 0)
            throw new ArgumentOutOfRangeException();
         
        if ((offset + count) > data.length)
            throw new ArgumentException();
         
        int total = count;
        int inputIndex = offset;
        int err = 0;
        while (err >= 0 && inputIndex < total)
        {
            copyInput(data,inputIndex,Math.min(total - inputIndex,kBufferSize));
            while (err >= 0 && _ztream.avail_in > 0)
            {
                RefSupport<ZStream> refVar___1 = new RefSupport<ZStream>(_ztream);
                err = deflate(refVar___1,((Enum)FlushTypes.None).ordinal());
                _ztream = refVar___1.getValue();
                if (err == 0)
                    while (_ztream.avail_out == 0)
                    {
                        onDataAvailable();
                        RefSupport<ZStream> refVar___2 = new RefSupport<ZStream>(_ztream);
                        err = deflate(refVar___2,((Enum)FlushTypes.None).ordinal());
                        _ztream = refVar___2.getValue();
                    }
                 
                inputIndex += (int)_ztream.total_in;
            }
        }
        setChecksum(_ztream.adler);
    }

    /**
    * Finishes up any pending data that needs to be processed and handled.
    */
    public void finish() throws Exception {
        int err;
        do
        {
            RefSupport<ZStream> refVar___3 = new RefSupport<ZStream>(_ztream);
            err = deflate(refVar___3,((Enum)FlushTypes.Finish).ordinal());
            _ztream = refVar___3.getValue();
            onDataAvailable();
        }
        while (err == 0);
        setChecksum(_ztream.adler);
        RefSupport<ZStream> refVar___4 = new RefSupport<ZStream>(_ztream);
        deflateReset(refVar___4);
        _ztream = refVar___4.getValue();
        resetOutput();
    }

    /**
    * Closes the internal zlib deflate stream
    */
    protected void cleanUp() throws Exception {
        RefSupport<ZStream> refVar___5 = new RefSupport<ZStream>(_ztream);
        deflateEnd(refVar___5);
        _ztream = refVar___5.getValue();
    }

}


