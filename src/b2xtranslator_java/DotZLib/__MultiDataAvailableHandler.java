//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DotZLib;

import CS2JNet.JavaSupport.util.ListSupport;
import DotZLib.__MultiDataAvailableHandler;
import DotZLib.DataAvailableHandler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* Represents the method that will be called from a codec when new data
* are available.
* 
*  {@code data}
* 
*  {@code startIndex}
* 
*  {@code count}
* On return from this method, the data may be overwritten, so grab it while you can.
* You cannot assume that startIndex will be zero.
*/
public class __MultiDataAvailableHandler   implements DataAvailableHandler
{
    public void invoke(byte[] data, int startIndex, int count) throws Exception {
        IList<DataAvailableHandler> copy = new IList<DataAvailableHandler>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<DataAvailableHandler>(members);
        }
        for (Object __dummyForeachVar0 : copy)
        {
            DataAvailableHandler d = (DataAvailableHandler)__dummyForeachVar0;
            d.invoke(data, startIndex, count);
        }
    }

    private List<DataAvailableHandler> _invocationList = new ArrayList<DataAvailableHandler>();
    public static DataAvailableHandler combine(DataAvailableHandler a, DataAvailableHandler b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiDataAvailableHandler ret = new __MultiDataAvailableHandler();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static DataAvailableHandler remove(DataAvailableHandler a, DataAvailableHandler b) throws Exception {
        if (a == null || b == null)
            return a;
         
        List<DataAvailableHandler> aInvList = a.getInvocationList();
        List<DataAvailableHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            __MultiDataAvailableHandler ret = new __MultiDataAvailableHandler();
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    public List<DataAvailableHandler> getInvocationList() throws Exception {
        return _invocationList;
    }

}


