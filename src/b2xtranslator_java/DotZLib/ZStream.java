//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// internal mapping of the zlib zstream structure for marshalling
public class ZStream   
{
    public ZStream() {
    }

    public IntPtr next_in = new IntPtr();
    public uint avail_in;
    public uint total_in;
    public IntPtr next_out = new IntPtr();
    public uint avail_out;
    public uint total_out;
    String msg;
    uint state;
    uint zalloc;
    uint zfree;
    uint opaque;
    int data_type;
    public uint adler;
    uint reserved;
}


