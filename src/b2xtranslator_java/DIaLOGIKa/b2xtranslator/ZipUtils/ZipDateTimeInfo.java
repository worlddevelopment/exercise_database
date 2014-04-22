//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;

import DIaLOGIKa.b2xtranslator.ZipUtils.ZipDateTimeInfo;
import java.util.Date;

// 4 bytes
/**
* Custom ZipLib date time structure.
*/
public class ZipDateTimeInfo   
{
    public ZipDateTimeInfo() {
    }

    /**
    * Seconds after the minute - [0,59]
    */
    public long Seconds;
    /**
    * Minutes after the hour - [0,59]
    */
    public long Minutes;
    /**
    * Hours since midnight - [0,23]
    */
    public long Hours;
    /**
    * Day of the month - [1,31]
    */
    public long Day;
    /**
    * Months since January - [0,11]
    */
    public long Month;
    /**
    * Years - [1980..2044]
    */
    public long Year;
    // implicit conversion from DateTime to ZipDateTimeInfo
    public static ZipDateTimeInfo __cast(Date date) throws Exception {
        ZipDateTimeInfo d = new ZipDateTimeInfo();
        d.Seconds = (uint)date.Second;
        d.Minutes = (uint)date.Minute;
        d.Hours = (uint)date.Hour;
        d.Day = (uint)date.Day;
        d.Month = (uint)date.Month - 1;
        d.Year = (uint)date.Year;
        return d;
    }

}


