//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import docx2tex.Library.Config;
import docx2tex.Library.IStatusInformation;
import docx2tex.Library.NewLineRun;
import docx2tex.Library.NullRun;
import docx2tex.Library.Run;
import docx2tex.Library.StyleEndRun;
import docx2tex.Library.StyleEnum;
import docx2tex.Library.StyleKillerRun;
import docx2tex.Library.StyleRun;
import docx2tex.Library.StyleStartRun;
import docx2tex.Library.Styling;
import docx2tex.Library.TagEndRun;
import docx2tex.Library.TagEnum;
import docx2tex.Library.Taging;
import docx2tex.Library.TagRun;
import docx2tex.Library.TagStartRun;
import docx2tex.Library.TextRun;
import docx2tex.Library.VerbatimRun;

public class Store   
{
    public static final int LINELENGTH = 72;
    private Styling _stylingFn;
    private Taging _tagingFn;
    private CSList<Run> _runs;
    private IStatusInformation _statusInfo;
    static {
        try
        {
            if (Config.Instance.getInfra().getLineLength().hasValue())
            {
                LINELENGTH = Config.Instance.getInfra().getLineLength().Value;
            }
             
        }
        catch (Exception __dummyStaticConstructorCatchVar0)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar0);
        }
    
    }

    public Store(Styling stylingFn, Taging tagingFn, IStatusInformation statusInfo) throws Exception {
        _stylingFn = stylingFn;
        _tagingFn = tagingFn;
        _runs = new CSList<Run>();
        _statusInfo = statusInfo;
    }

    public void addText(String text) throws Exception {
        _runs.add(new TextRun(text));
    }

    public void addVerbatim(String text) throws Exception {
        _runs.add(new VerbatimRun(text));
    }

    public void addNL() throws Exception {
        _runs.add(new NewLineRun());
    }

    public void addTextNL(String text) throws Exception {
        addText(text);
        addNL();
    }

    public void addStartStyle(StyleEnum styleEnum) throws Exception {
        _runs.add(new StyleStartRun(styleEnum,_stylingFn));
    }

    public void addEndStyle(StyleEnum styleEnum) throws Exception {
        _runs.add(new StyleEndRun(styleEnum,_stylingFn));
    }

    public void addStyleKiller() throws Exception {
        _runs.add(new StyleKillerRun());
    }

    public void addStartTag(TagEnum tagEnum) throws Exception {
        _runs.add(new TagStartRun(tagEnum,_tagingFn));
    }

    public void addEndTag(TagEnum tagEnum) throws Exception {
        _runs.add(new TagEndRun(tagEnum,_tagingFn));
    }

    public String convertToString() throws Exception {
        List<Run> originalRuns = _runs;
        CSList<Run> simplifiedRuns;
        _statusInfo.writeLine("Removing unusable styles...");
        // run style killer
        // delete styles that suround some float
        originalRuns = runStyleKillers(originalRuns);
        _statusInfo.writeLine("Removing empty styles...");
        // kill style pairs that do not have content while there are any
        RefSupport<CSList<Run>> refVar___0 = new RefSupport<CSList<Run>>();
        while (killEmptyStyles(refVar___0,originalRuns))
        {
            originalRuns = simplifiedRuns;
        }
        simplifiedRuns = refVar___0.getValue();
        _statusInfo.writeLine("Compacting runs...");
        // simplify the runs as long as they can be simplified
        RefSupport<CSList<Run>> refVar___1 = new RefSupport<CSList<Run>>();
        while (simplify(refVar___1,originalRuns))
        {
            originalRuns = simplifiedRuns;
        }
        simplifiedRuns = refVar___1.getValue();
        _statusInfo.writeLine("Merging text runs...");
        // merge textrun siblings
        mergeTextRuns(simplifiedRuns);
        _statusInfo.writeLine("Correcting line lengths...");
        return compileOutputText(simplifiedRuns);
    }

    // split the line lengths
    private CSList<Run> runStyleKillers(CSList<Run> originalRuns) throws Exception {
        CSList<Run> simplifiedRuns = new CSList<Run>(originalRuns);
        int cntActiveStyles = 0;
        for (int i = 0;i < simplifiedRuns.size();i++)
        {
            Run run = simplifiedRuns.get(i);
            if (run instanceof StyleStartRun)
            {
                cntActiveStyles++;
            }
            else if (run instanceof StyleEndRun)
            {
                cntActiveStyles--;
            }
              
            if (run instanceof StyleKillerRun && cntActiveStyles > 0)
            {
                int cnt = cntActiveStyles;
                int effectiveCnt = 0;
                int j = i - 1;
                while (cnt > 0 && j > 0)
                {
                    if (simplifiedRuns.get(j) instanceof StyleStartRun)
                    {
                        effectiveCnt++;
                        if (effectiveCnt > 0)
                        {
                            simplifiedRuns.add(j, new NullRun());
                            //simplifiedRuns.RemoveAt(j);
                            effectiveCnt--;
                            cnt--;
                        }
                         
                    }
                    else //i--;
                    if (simplifiedRuns.get(j) instanceof StyleEndRun)
                    {
                        effectiveCnt--;
                    }
                      
                    j--;
                }
                cnt = cntActiveStyles;
                effectiveCnt = 0;
                j = i + 1;
                while (cnt > 0 && j < simplifiedRuns.size())
                {
                    if (simplifiedRuns.get(j) instanceof StyleEndRun)
                    {
                        effectiveCnt++;
                        if (effectiveCnt > 0)
                        {
                            simplifiedRuns.add(j, new NullRun());
                            //simplifiedRuns.RemoveAt(j);
                            effectiveCnt--;
                            cnt--;
                        }
                         
                    }
                    else //j--;
                    if (simplifiedRuns.get(j) instanceof StyleStartRun)
                    {
                        effectiveCnt--;
                    }
                      
                    j++;
                }
            }
             
        }
        return simplifiedRuns;
    }

    private boolean killEmptyStyles(RefSupport<CSList<Run>> simplifiedRuns, CSList<Run> originalRuns) throws Exception {
        boolean didKill = false;
        simplifiedRuns.setValue(new CSList<Run>(originalRuns));
        simplifiedRuns.getValue().RemoveAll(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(r) => {
            return (r instanceof TextRun) && StringSupport.isNullOrEmpty((r instanceof TextRun ? (TextRun)r : (TextRun)null).getText());
        }" */);
        for (int i = 0;i < simplifiedRuns.getValue().size() - 1;i++)
        {
            Run run1 = simplifiedRuns.getValue().get(i);
            Run run2 = simplifiedRuns.getValue().get(i + 1);
            if (run1 instanceof StyleStartRun && run2 instanceof StyleEndRun && (run1 instanceof StyleRun ? (StyleRun)run1 : (StyleRun)null).getStyle() == (run2 instanceof StyleRun ? (StyleRun)run2 : (StyleRun)null).getStyle())
            {
                simplifiedRuns.getValue().add(i, new NullRun());
                simplifiedRuns.getValue().add(i + 1, new NullRun());
                //simplifiedRuns.RemoveAt(i); //ith
                //simplifiedRuns.RemoveAt(i); // i+1th
                i++;
                didKill = true;
            }
             
        }
        return didKill;
    }

    private boolean simplify(RefSupport<CSList<Run>> simplifiedRuns, CSList<Run> originalRuns) throws Exception {
        simplifiedRuns.setValue(new CSList<Run>());
        boolean didSimplify = false;
        StyleEndRun lastStyleEndRun = null;
        /* [UNSUPPORTED] 'var' as type is unsupported "var" */ runEnum = originalRuns.GetEnumerator();
        while (runEnum.MoveNext())
        {
            Run run = runEnum.Current;
            // if newline or text
            if (run instanceof NewLineRun || run instanceof TextRun || run instanceof VerbatimRun)
            {
                // if a style ending run found then flush it
                if (lastStyleEndRun != null)
                {
                    simplifiedRuns.getValue().add(lastStyleEndRun);
                    lastStyleEndRun = null;
                }
                 
                // add run
                simplifiedRuns.getValue().add(run);
            }
             
            // if tag
            if (run instanceof TagRun)
            {
                // if a style ending run found then flush it
                if (lastStyleEndRun != null)
                {
                    simplifiedRuns.getValue().add(lastStyleEndRun);
                    lastStyleEndRun = null;
                }
                 
                // add run
                simplifiedRuns.getValue().add(run);
            }
            else if (run instanceof StyleStartRun)
            {
                // style start run
                // if a style ending run found then process it
                if (lastStyleEndRun != null)
                {
                    // if the style of the end is not the same and the start
                    if (((StyleStartRun)run).getStyle() != lastStyleEndRun.getStyle())
                    {
                        // flush style end
                        simplifiedRuns.getValue().add(lastStyleEndRun);
                        // add start run
                        simplifiedRuns.getValue().add(run);
                    }
                    else
                    {
                        didSimplify = true;
                    } 
                    lastStyleEndRun = null;
                }
                else
                {
                    // no style ending run found
                    // add run
                    simplifiedRuns.getValue().add(run);
                } 
            }
            else if (run instanceof StyleEndRun)
            {
                // if an other style end run found
                if (lastStyleEndRun != null)
                {
                    // flush it
                    simplifiedRuns.getValue().add(lastStyleEndRun);
                    lastStyleEndRun = null;
                }
                 
                // save the style end run
                lastStyleEndRun = (StyleEndRun)run;
            }
               
        }
        // if an style end run found
        if (lastStyleEndRun != null)
        {
            // flush it
            simplifiedRuns.getValue().add(lastStyleEndRun);
        }
         
        return didSimplify;
    }

    private void mergeTextRuns(CSList<Run> simplifiedRuns) throws Exception {
        for (int i = 0;i < simplifiedRuns.size();i++)
        {
            if (simplifiedRuns.get(i) instanceof TextRun)
            {
                String finalText = (simplifiedRuns.get(i) instanceof TextRun ? (TextRun)simplifiedRuns.get(i) : (TextRun)null).getText();
                boolean found = false;
                int j = i + 1;
                while (j < simplifiedRuns.size() && simplifiedRuns.get(j) instanceof TextRun)
                {
                    finalText += (simplifiedRuns.get(j) instanceof TextRun ? (TextRun)simplifiedRuns.get(j) : (TextRun)null).getText();
                    simplifiedRuns.add(j, new NullRun());
                    found = true;
                    j++;
                }
                if (found)
                {
                    simplifiedRuns.add(i, new TextRun(finalText));
                }
                 
            }
             
        }
    }

    private String compileOutputText(CSList<Run> simplifiedRuns) throws Exception {
        StringBuilder sb = new StringBuilder();
        int lastLineLength = 0;
        for (Run r : simplifiedRuns)
        {
            if (r instanceof VerbatimRun)
            {
                sb.append(r.getTeXText());
            }
            else if (r instanceof TextRun || r instanceof StyleStartRun || r instanceof StyleEndRun || r instanceof TagRun)
            {
                String parts = r.getTeXText();
                lastLineLength = addRun(sb,r.getTeXText(),lastLineLength);
            }
            else if (r instanceof NewLineRun)
            {
                sb.append(r.getTeXText());
                lastLineLength = 0;
            }
               
        }
        return sb.toString();
    }

    private int addRun(StringBuilder sb, String parts, int lastLineLength) throws Exception {
        StringBuilder broken = new StringBuilder();
        for (String part : StringSupport.Split(parts, ' '))
        {
            if (lastLineLength + part.length() > LINELENGTH)
            {
                broken.append(System.getProperty("line.separator"));
                lastLineLength = 0;
            }
             
            if (!StringSupport.isNullOrEmpty(part))
            {
                broken.append(String.format(StringSupport.CSFmtStrToJFmtStr("{0} "),part));
                lastLineLength += part.length() + 1;
            }
             
        }
        String res = broken.toString();
        if (!parts.endsWith(" "))
        {
            res = StringSupport.TrimEnd(res, null);
        }
         
        if (parts.startsWith(" ") && !res.startsWith(" "))
        {
            res = " " + res;
        }
         
        sb.append(res);
        return lastLineLength;
    }

}


