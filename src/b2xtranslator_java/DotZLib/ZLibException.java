//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* The exception that is thrown when an error occurs on the zlib dll
*/
public class ZLibException  extends RuntimeException 
{
    /**
    * Initializes a new instance of the 
    *  {@link #ZLibException}
    *  class with a specified
    * error message and error code
    * 
    *  @param errorCode The zlib error code that caused the exception
    *  @param msg A message that (hopefully) describes the error
    */
    public ZLibException(int errorCode, String msg) throws Exception {
        super(String.format(StringSupport.CSFmtStrToJFmtStr("ZLib error {0} {1}"),errorCode,msg));
    }

    /**
    * Initializes a new instance of the 
    *  {@link #ZLibException}
    *  class with a specified
    * error code
    * 
    *  @param errorCode The zlib error code that caused the exception
    */
    public ZLibException(int errorCode) throws Exception {
        super(String.format(StringSupport.CSFmtStrToJFmtStr("ZLib error {0}"),errorCode));
    }

}


