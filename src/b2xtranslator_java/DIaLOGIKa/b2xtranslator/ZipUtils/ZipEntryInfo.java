//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;

import DIaLOGIKa.b2xtranslator.ZipUtils.ZipDateTimeInfo;

/**
* Information stored in zip file directory about an entry.
*/
public class ZipEntryInfo   
{
    public ZipEntryInfo() {
    }

    // <summary>Version made by (2 bytes).</summary>
    public UIntPtr Version = new UIntPtr();
    /**
    * Version needed to extract (2 bytes).
    */
    public UIntPtr VersionNeeded = new UIntPtr();
    /**
    * General purpose bit flag (2 bytes).
    */
    public UIntPtr Flag = new UIntPtr();
    /**
    * Compression method (2 bytes).
    */
    public UIntPtr CompressionMethod = new UIntPtr();
    /**
    * Last mod file date in Dos fmt (4 bytes).
    */
    public UIntPtr DosDate = new UIntPtr();
    /**
    * Crc-32 (4 bytes).
    */
    public UIntPtr Crc = new UIntPtr();
    /**
    * Compressed size (4 bytes).
    */
    public UIntPtr CompressedSize = new UIntPtr();
    /**
    * Uncompressed size (4 bytes).
    */
    public UIntPtr UncompressedSize = new UIntPtr();
    /**
    * Filename length (2 bytes).
    */
    public UIntPtr FileNameLength = new UIntPtr();
    /**
    * Extra field length (2 bytes).
    */
    public UIntPtr ExtraFieldLength = new UIntPtr();
    /**
    * File comment length (2 bytes).
    */
    public UIntPtr CommentLength = new UIntPtr();
    /**
    * Disk number start (2 bytes).
    */
    public UIntPtr DiskStartNumber = new UIntPtr();
    /**
    * Internal file attributes (2 bytes).
    */
    public UIntPtr InternalFileAttributes = new UIntPtr();
    /**
    * External file attributes (4 bytes).
    */
    public UIntPtr ExternalFileAttributes = new UIntPtr();
    /**
    * File modification date of entry.
    */
    public ZipDateTimeInfo DateTime = new ZipDateTimeInfo();
}


