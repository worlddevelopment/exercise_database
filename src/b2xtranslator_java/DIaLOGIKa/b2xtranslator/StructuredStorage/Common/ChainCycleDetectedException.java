//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class ChainCycleDetectedException  extends Exception 
{
    public ChainCycleDetectedException(String chain) throws Exception {
        super(chain + " contains a cycle.");
    }

    protected ChainCycleDetectedException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


