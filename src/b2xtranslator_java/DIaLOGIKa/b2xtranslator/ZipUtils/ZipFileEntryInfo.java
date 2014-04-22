//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;

import DIaLOGIKa.b2xtranslator.ZipUtils.ZipDateTimeInfo;

public class ZipFileEntryInfo   
{
    public ZipFileEntryInfo() {
    }

    public ZipDateTimeInfo DateTime = new ZipDateTimeInfo();
    public UIntPtr DosDate = new UIntPtr();
    public UIntPtr InternalFileAttributes = new UIntPtr();
    // 2 bytes
    public UIntPtr ExternalFileAttributes = new UIntPtr();
}


