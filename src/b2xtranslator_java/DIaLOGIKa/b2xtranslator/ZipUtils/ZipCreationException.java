//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;

import DIaLOGIKa.b2xtranslator.ZipUtils.ZipException;

public class ZipCreationException  extends ZipException 
{
    /**
    * Constructs an exception with no descriptive information.
    */
    public ZipCreationException() throws Exception {
        super();
    }

    /**
    * Constructs an exception with a descriptive message.
    *  @param message The error message that explains the reason for the exception.
    */
    public ZipCreationException(String message) throws Exception {
        super(message);
    }

    /**
    * Constructs an exception with a descriptive message and a reference to the instance of the 
    *  {@code Exception}
    *  that is the root cause of the this exception.
    *  @param message The error message that explains the reason for the exception.
    *  @param innerException An instance of 
    *  {@code Exception}
    *  that is the cause of the current Exception. If 
    *  {@code innerException}
    *  is non-null, then the current Exception is raised in a catch block handling 
    *  {@code }
    * .
    */
    public ZipCreationException(String message, Exception innerException) throws Exception {
        super(message, innerException);
    }

    /**
    * Initializes a new instance of the exception class with serialized data.
    *  @param info The object that holds the serialized object data.
    *  @param context The contextual information about the source or destination.
    */
    public ZipCreationException(SerializationInfo info, StreamingContext context) throws Exception {
        super(info, context);
    }

}


