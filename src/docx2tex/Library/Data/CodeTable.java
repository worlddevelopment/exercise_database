//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import CS2JNet.System.EnumSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.Xml.org.w3c.dom.Document;
import CS2JNet.System.Xml.org.w3c.dom.Node;
import docx2tex.Library.Data.CodeTable;
import docx2tex.Library.Data.CodeTableInfo;
import docx2tex.Library.Data.MathMode;
import java.io.InputStream;
import java.util.HashMap;

/**
* Codetable for math and other LaTeX symbols
*/
public class CodeTable   
{
    /**
    * This is for math symbols
    */
    private HashMap<String,CodeTableInfo> __MathOnlyTable;
    public HashMap<String,CodeTableInfo> getMathOnlyTable() {
        return __MathOnlyTable;
    }

    public void setMathOnlyTable(HashMap<String,CodeTableInfo> value) {
        __MathOnlyTable = value;
    }

    /**
    * This is for non-math symbols and single symbols that switch to math
    */
    private HashMap<String,CodeTableInfo> __NonMathTable;
    public HashMap<String,CodeTableInfo> getNonMathTable() {
        return __NonMathTable;
    }

    public void setNonMathTable(HashMap<String,CodeTableInfo> value) {
        __NonMathTable = value;
    }

    private CodeTable() throws Exception {
        setMathOnlyTable(new HashMap<String,CodeTableInfo>());
        setNonMathTable(new HashMap<String,CodeTableInfo>());
        Assembly ass = null /* getExecutingAssembly() */;
        // load embedded resource
        InputStream stream = ass.GetManifestResourceStream("docx2tex.Library.Data.CodeTable.xml");
        try
        {
            {
                org.w3c.dom.Document doc = new org.w3c.dom.Document();
                doc.load(stream);
                for (Object __dummyForeachVar0 : doc.selectNodes("/Codes/Code"))
                {
                    // fill dictionary
                    org.w3c.dom.Node xmlNode = (org.w3c.dom.Node)__dummyForeachVar0;
                    String word = xmlNode.getAttributes().get("Word").getValue();
                    String tex = xmlNode.getAttributes().get("TeX").getValue();
                    String mathModeStr = xmlNode.getAttributes().get("MathMode").getValue();
                    MathMode mathMode = (MathMode)EnumSupport.Parse((Class<? extends Enum>)MathMode.class, mathModeStr);
                    if ((mathMode & MathMode.Yes) == MathMode.Yes || (mathMode & MathMode.Switch) == MathMode.Switch)
                    {
                        getMathOnlyTable().put(word, new CodeTableInfo(word,tex,mathMode));
                    }
                     
                    if ((mathMode & MathMode.No) == MathMode.No || (mathMode & MathMode.Switch) == MathMode.Switch)
                    {
                        getNonMathTable().put(word, new CodeTableInfo(word,tex,mathMode));
                    }
                     
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
    public static final CodeTable Instance = new CodeTable();
}


