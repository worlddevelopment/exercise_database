//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlDocument;
import CS2JNet.System.Xml.XmlNode;
import docx2tex.Library.Config;
import docx2tex.Library.Data.InputEnc;
import docx2tex.Library.Data.InputEncInfo;
import java.io.InputStream;

/**
* Input encodings
*/
public class InputEnc   
{
    /**
    * This is for all encodings
    */
    private CSList<InputEncInfo> __InputEncs;
    public CSList<InputEncInfo> getInputEncs() {
        return __InputEncs;
    }

    public void setInputEncs(CSList<InputEncInfo> value) {
        __InputEncs = value;
    }

    /**
    * This is the current encoding
    */
    public InputEncInfo getCurrentEncoding() throws Exception {
        String enc = Config.Instance.getInfra().getInputEnc();
        return getInputEncs().Find(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(e) => {
            return StringSupport.equals(e.InputEncoding, enc);
        }" */);
    }

    //if not found or enc is null then return null else the correct object
    private InputEnc() throws Exception {
        setInputEncs(new CSList<InputEncInfo>());
        Assembly ass = null /* getExecutingAssembly() */;
        // load embedded resource
        InputStream stream = ass.GetManifestResourceStream("docx2tex.Library.Data.InputEncs.xml");
        try
        {
            {
                XmlDocument doc = new XmlDocument();
                doc.load(stream);
                for (Object __dummyForeachVar0 : doc.selectNodes("/InputEncs/InputEnc"))
                {
                    // fill dictionary
                    XmlNode xmlNode = (XmlNode)__dummyForeachVar0;
                    String inputenc = xmlNode.getAttributes().get("inputenc").getValue();
                    String enc = xmlNode.getAttributes().get("encoding").getValue();
                    String desc = xmlNode.getAttributes().get("description").getValue();
                    getInputEncs().add(new InputEncInfo(inputenc,enc,desc));
                }
            }
        }
        finally
        {
            if (stream != null)
                Disposable.mkDisposable(stream).dispose();
             
        }
    }

    /**
    * Singleton instance
    */
    public static final InputEnc Instance = new InputEnc();
}


