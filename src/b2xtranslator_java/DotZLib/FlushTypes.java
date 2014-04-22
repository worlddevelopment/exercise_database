//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum FlushTypes
{
    //
    // � Copyright Henrik Ravn 2004
    //
    // Use, modification and distribution are subject to the Boost Software License, Version 1.0.
    // (See accompanying file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
    //
    /**
    * Defines constants for the various flush types used with zlib
    */
    None,
    Partial,
    Sync,
    Full,
    Finish,
    Block
}

