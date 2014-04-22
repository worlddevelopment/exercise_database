//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;

import DIaLOGIKa.b2xtranslator.ZipUtils.ZipException;

/* 
 * Copyright (c) 2006, Clever Age
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Clever Age nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
* Thrown whenever an error occurs during the build.
*/
public class ZipEntryNotFoundException  extends ZipException 
{
    /**
    * Constructs an exception with no descriptive information.
    */
    public ZipEntryNotFoundException() throws Exception {
        super();
    }

    /**
    * Constructs an exception with a descriptive message.
    *  @param message The error message that explains the reason for the exception.
    */
    public ZipEntryNotFoundException(String message) throws Exception {
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
    public ZipEntryNotFoundException(String message, Exception innerException) throws Exception {
        super(message, innerException);
    }

    /**
    * Initializes a new instance of the exception class with serialized data.
    *  @param info The object that holds the serialized object data.
    *  @param context The contextual information about the source or destination.
    */
    public ZipEntryNotFoundException(SerializationInfo info, StreamingContext context) throws Exception {
        super(info, context);
    }

}


