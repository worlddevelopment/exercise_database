//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:00 AM
//

package docx2tex.Library;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Text.RegularExpressions.GroupCollection;
import CS2JNet.System.Text.RegularExpressions.Match;
import CS2JNet.System.Text.RegularExpressions.RegexOptions;
import docx2tex.Library.Data.CodeTable;
import docx2tex.Library.Data.MathMode;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class TeXing   
{
    private HashMap<String,String> _replacerPairs;
    private Pattern _refRegEx;
    public TeXing() throws Exception {
        initReplacesPairs();
    }

    private void initReplacesPairs() throws Exception {
        _refRegEx = Pattern.compile(" REF (?<Ref>.+?) ", 0 /* RegexOptions.Compiled */);
        _replacerPairs = new HashMap<String,String>();
        for (KeyValuePair ent : CodeTable.Instance.getNonMathTable().entrySet())
        {
            if ((ent.Value.MathMode & MathMode.No) == MathMode.No)
            {
                _replacerPairs.Add(ent.Key, ent.Value.TeX);
            }
             
            if ((ent.Value.MathMode & MathMode.Switch) == MathMode.Switch)
            {
                _replacerPairs.Add(ent.Key, String.format(StringSupport.CSFmtStrToJFmtStr("$"),ent.Value.TeX));
            }
             
        }
    }

    public String resolveBookmarkRef(String reference) throws Exception {
        try
        {
            Match match = Match.mk(_refRegEx, reference);
            return GroupCollection.mk(match).get("Ref").getValue();
        }
        catch (Exception __dummyCatchVar0)
        {
            return "";
        }
    
    }

    public String teXizeText(String original) throws Exception {
        String replaced = original;
        for (Object __dummyForeachVar1 : _replacerPairs.entrySet())
        {
            Entry<String,String> replacerPair = (Entry<String,String>)__dummyForeachVar1;
            replaced = replaced.replace(replacerPair.getKey(), replacerPair.getValue());
        }
        return replaced;
    }

    /**
    * TODO: put to codetable
    * 
    *  @param original 
    *  @return
    */
    public String verbatimizeText(String original) throws Exception {
        String newText = original.replace("’", "'").replace("“", "\"").replace("”", "\"");
        newText = newText.replace("…", "...");
        return newText;
    }

}


