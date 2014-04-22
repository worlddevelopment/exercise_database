//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:00 AM
//

package docx2tex.Library;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.IO.FileAccess;
import CS2JNet.System.IO.FileMode;
import CS2JNet.System.Xml.XmlDocument;
import CS2JNet.System.Xml.XmlNode;
import java.net.URI;
import java.util.Collections;

/**
* Style enumerator helper class
*/
public class StyleEnumerator   
{
    /**
    * Enumerate all styles in a document
    * 
    *  @param path 
    *  @return
    */
    public static CSList<String> enumerate(String path) throws Exception {
        List<String> styles = new CSList<String>();
        try
        {
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ pkg = Package.Open(path, FileMode.Open, FileAccess.Read, FileShare.Read);
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ stylesPart = (ZipPackagePart)pkg.GetPart(new URI("/word/styles.xml", UriKind.Relative));
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ stylesStream = stylesPart.GetStream();
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ nt = new NameTable();
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ nsmgr = new XmlNamespaceManager(nt);
            nsmgr.AddNamespace("w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
            XmlDocument stylesDoc = new XmlDocument(nt);
            stylesDoc.Load(stylesStream);
            for (Object __dummyForeachVar0 : stylesDoc.getDocumentElement().SelectNodes("/w:styles/w:style", nsmgr))
            {
                XmlNode xmlNode = (XmlNode)__dummyForeachVar0;
                styles.add(xmlNode.getAttributes().get("w:styleId").getValue().toLowerCase());
            }
            pkg.Close();
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        Collections.sort(styles);
        return styles;
    }

}


