//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import CS2JNet.System.LCC.Disposable;

public class InputEncInfo   
{
    public InputEncInfo(String inputenc, String enc, String desc) throws Exception {
        setInputEncoding(inputenc);
        setDotNetEncoding(enc);
        setDescription(desc);
    }

    private String __InputEncoding;
    public String getInputEncoding() {
        return __InputEncoding;
    }

    public void setInputEncoding(String value) {
        __InputEncoding = value;
    }

    private String __DotNetEncoding;
    public String getDotNetEncoding() {
        return __DotNetEncoding;
    }

    public void setDotNetEncoding(String value) {
        __DotNetEncoding = value;
    }

    private String __Description;
    public String getDescription() {
        return __Description;
    }

    public void setDescription(String value) {
        __Description = value;
    }

}


