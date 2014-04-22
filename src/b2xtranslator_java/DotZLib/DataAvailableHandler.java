//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DotZLib;

import CS2JNet.JavaSupport.util.ListSupport;
import DotZLib.DataAvailableHandler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface DataAvailableHandler   
{
    void invoke(byte[] data, int startIndex, int count) throws Exception ;

    List<DataAvailableHandler> getInvocationList() throws Exception ;

}


