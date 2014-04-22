//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;


public enum ErrorCode
{
    /**
    * List of possible error codes.No error.
    */
    Ok,
    /**
    * Unknown error.
    */
    Error,
    /**
    * Last entry in directory reached.
    */
    EndOfListOfFile,
    /**
    * Parameter error.
    */
    ParameterError,
    /**
    * Zip file is invalid.
    */
    BadZipFile,
    /**
    * Internal program error.
    */
    InternalError,
    /**
    * Crc values do not match.
    */
    CrcError
}

