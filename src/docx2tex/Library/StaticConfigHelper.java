//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:56 AM
//

package docx2tex.Library;

import CS2JNet.System.LCC.Disposable;
import docx2tex.Library.Config;

/**
* Store data that has to be available when the singleton instance of Config is constructed
*/
final public class StaticConfigHelper   
{
    private static String _docxPath;
    public static String getDocxPath() throws Exception {
        return _docxPath;
    }

    public static void setDocxPath(String value) throws Exception {
        _docxPath = value;
        Config.Instance = new Config();
    }

}


