//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:01 AM
//

package docx2tex.Library;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlNode;
import CS2JNet.System.Xml.XmlNodeList;
import docx2tex.Library.Config;
import java.util.HashMap;
import docx2tex.Library.Data.CodeTable;
import docx2tex.Library.TagEnum;
import CS2JNet.System.Collections.LCC.CSList;
import docx2tex.Library.ListControl;
import docx2tex.Library.ListTypeEnum;
import docx2tex.Library.NumberedCounterTypeEnum;
import docx2tex.Library.InstrTextTypeEnum;
import docx2tex.Library.RunInfo;
import docx2tex.Library.StyleEnum;
import CS2JNet.System.Xml.XmlDocument;
import docx2tex.Library.Imaging;
import docx2tex.Library.IStatusInformation;
import docx2tex.Library.Numbering;
import docx2tex.Library.Store;
import docx2tex.Library.Styling;
import docx2tex.Library.Taging;
import docx2tex.Library.TeXing;
import java.io.InputStream;

public class Engine   
{

    private HashMap<String,XmlNode> _bookmarkNodeCache;
    /**
    * Cache the bookmark node to each bookmark name
    */
    private void cacheBookmarks() throws Exception {
        _bookmarkNodeCache = new HashMap<String,XmlNode>();
        for (Object __dummyForeachVar0 : getNodes(_doc,"//w:p/w:bookmarkStart"))
        {
            XmlNode bookmarkNode = (XmlNode)__dummyForeachVar0;
            String bookmarkName = getString(bookmarkNode,"@w:name");
            if (bookmarkName != null)
            {
                _bookmarkNodeCache.put(bookmarkName, bookmarkNode);
            }
             
        }
    }

    /**
    * Add reference fields
    * 
    *  @param currentBookmarkName
    */
    private void processReference(String currentBookmarkName) throws Exception {
        String bookmarkRefName = _texingFn.resolveBookmarkRef(currentBookmarkName);
        // if no such bookmark found
        if (!_bookmarkNodeCache.containsKey(bookmarkRefName))
        {
            return ;
        }
         
        XmlNode bookmarkRefNode = _bookmarkNodeCache.get(bookmarkRefName);
        //GetNode(_doc, string.Format("//w:p/w:bookmarkStart[@w:name='{0}']", bookmarkRefName));
        String refStyle = getLowerString(bookmarkRefNode,"./preceding-sibling::*[1]/w:pStyle/@w:val");
        String seq = getString(bookmarkRefNode.getParentNode(),"./w:fldSimple[starts-with(@w:instr, ' SEQ ')]/@w:instr");
        // do for sections
        if (StringSupport.equals(refStyle, RESOLVED_SECTION) || StringSupport.equals(refStyle, RESOLVED_SUBSECTION) || StringSupport.equals(refStyle, RESOLVED_SUBSUBSECTION))
        {
            if (Config.Instance.getLaTeXTags().getPutSectionReferences().Value)
            {
                _tex.addText("\\ref{section:" + bookmarkRefName + "}.");
            }
             
        }
        else if (!StringSupport.isNullOrEmpty(seq))
        {
            // do for tables, listings, figures
            if (seq.contains("SEQ Table"))
            {
                if (Config.Instance.getLaTeXTags().getPutTableReferences().Value)
                {
                    _tex.addText("\\ref{table:" + bookmarkRefName + "}.");
                }
                 
            }
            else if (seq.contains("SEQ Listing"))
            {
                if (Config.Instance.getLaTeXTags().getPutListingReferences().Value)
                {
                    _tex.addText("\\ref{listing:" + bookmarkRefName + "}.");
                }
                 
            }
            else if (seq.contains("SEQ Fig"))
            {
                if (Config.Instance.getLaTeXTags().getPutFigureReferences().Value)
                {
                    _tex.addText("\\ref{figure:" + bookmarkRefName + "}.");
                }
                 
            }
            else
            {
                _tex.addText("!!!Unresolved reference!!!");
            }   
        }
        else
        {
            _tex.addText("!!!Unresolved reference!!!");
        }  
    }

    private void listingCaptionRun(XmlNode paraNode) throws Exception {
        if (!StringSupport.isNullOrEmpty(getString(paraNode,"./w:fldSimple[starts-with(@w:instr, ' SEQ Listing ')]/@w:instr")))
        {
            _tex.addTextNL("\\begin{figure}[h]");
            _tex.addText("\\caption{");
            if (Config.Instance.getLaTeXTags().getPutListingReferences().Value)
            {
                _tex.addText("\\label{listing:" + getString(paraNode,"./w:bookmarkStart/@w:name") + "}");
            }
             
            captionText(paraNode);
            _tex.addTextNL("}");
            _tex.addTextNL("\\end{figure}");
        }
         
    }

    private void captionText(XmlNode captionP) throws Exception {
        XmlNodeList captionNodes = null;
        // if bookmarkstart found then the bookmark text is after the last bookmarkEnd node
        if (!StringSupport.isNullOrEmpty(getString(captionP,"./w:bookmarkStart/@w:name")))
        {
            captionNodes = getNodes(captionP,"./w:bookmarkEnd[last()]/following-sibling::*");
        }
        else
        {
            captionNodes = getNodes(captionP,"./w:r");
        } 
        for (Object __dummyForeachVar1 : captionNodes)
        {
            XmlNode captNode = (XmlNode)__dummyForeachVar1;
            _tex.addText(getString(captNode,"./w:t"));
        }
    }

    /**
    * Resolve image caption
    * 
    *  @param captionP
    */
    private void imageCaption(XmlNode captionP) throws Exception {
        if (!StringSupport.isNullOrEmpty(getString(captionP,"./w:fldSimple[starts-with(@w:instr, ' SEQ Figure ')]/@w:instr")))
        {
            _tex.addText("\\caption{");
            if (Config.Instance.getLaTeXTags().getPutFigureReferences().Value)
            {
                String refName = getString(captionP,"./w:bookmarkStart/@w:name");
                if (!StringSupport.isNullOrEmpty(refName))
                {
                    _tex.addText("\\label{figure:" + refName + "}");
                }
                 
            }
             
            captionText(captionP);
            _tex.addTextNL("}");
        }
         
    }

    private Integer getInt(XmlNode srcNode, String query) throws Exception {
        if (srcNode == null)
            return null;
         
        XmlNode node = srcNode.SelectSingleNode(query, _nsmgr);
        if (node == null)
            return null;
         
        return int.Parse(node.Value);
    }

    private String getString(XmlNode srcNode, String query) throws Exception {
        if (srcNode == null)
            return null;
         
        XmlNode node = srcNode.SelectSingleNode(query, _nsmgr);
        if (node == null)
            return null;
         
        return node.getInnerText();
    }

    private String getLowerString(XmlNode srcNode, String query) throws Exception {
        String ret = getString(srcNode,query);
        if (ret == null)
            return null;
         
        return ret.toLowerCase();
    }

    private int countNodes(XmlNode srcNode, String query) throws Exception {
        if (srcNode == null)
            return 0;
         
        return srcNode.SelectNodes(query, _nsmgr).Count;
    }

    private XmlNodeList getNodes(XmlNode srcNode, String query) throws Exception {
        if (srcNode == null)
            return null;
         
        return srcNode.SelectNodes(query, _nsmgr);
    }

    private XmlNode getNode(XmlNode srcNode, String query) throws Exception {
        if (srcNode == null)
            return null;
         
        return srcNode.SelectSingleNode(query, _nsmgr);
    }

    HashMap<String,String> _mathTable;
    private void initMathTables() throws Exception {
        _mathTable = new HashMap<String,String>();
        for (KeyValuePair ent : CodeTable.Instance.getMathOnlyTable().entrySet())
        {
            _mathTable.Add(ent.Key, ent.Value.TeX);
        }
        _mathTable.put("&", "");
    }

    // no alignment
    private void processMath(XmlNode mathNode) throws Exception {
        // begin math
        _tex.addStartTag(TagEnum.Math);
        processMathNodes(getNodes(mathNode,"./*"));
        // end math
        _tex.addEndTag(TagEnum.Math);
    }

    private void processMathNodes(XmlNodeList xmlNodeList) throws Exception {
        for (Object __dummyForeachVar5 : xmlNodeList)
        {
            XmlNode node = (XmlNode)__dummyForeachVar5;
            // standard text
            String __dummyScrutVar0 = node.getName();
            if (__dummyScrutVar0.equals("m:r"))
            {
                {
                    String str = getString(node,"./m:t");
                    // process as a function or standard text
                    String data = "";
                    String __dummyScrutVar1 = str;
                    // functions
                    if (__dummyScrutVar1.equals("cos"))
                    {
                        data = "\\cos ";
                    }
                    else if (__dummyScrutVar1.equals("sin"))
                    {
                        data = "\\sin ";
                    }
                    else if (__dummyScrutVar1.equals("tan"))
                    {
                        data = "\\tan ";
                    }
                    else if (__dummyScrutVar1.equals("csc"))
                    {
                        data = "\\csc ";
                    }
                    else if (__dummyScrutVar1.equals("sec"))
                    {
                        data = "\\sec ";
                    }
                    else if (__dummyScrutVar1.equals("cot"))
                    {
                        data = "\\cot ";
                    }
                    else if (__dummyScrutVar1.equals("sinh"))
                    {
                        data = "\\sinh ";
                    }
                    else if (__dummyScrutVar1.equals("cosh"))
                    {
                        data = "\\cosh ";
                    }
                    else if (__dummyScrutVar1.equals("tanh"))
                    {
                        data = "\\tanh ";
                    }
                    else if (__dummyScrutVar1.equals("csch"))
                    {
                        data = "csch ";
                    }
                    else if (__dummyScrutVar1.equals("sech"))
                    {
                        data = "sech ";
                    }
                    else if (__dummyScrutVar1.equals("coth"))
                    {
                        data = "\\coth ";
                    }
                    else if (__dummyScrutVar1.equals("lim"))
                    {
                        data = "\\lim ";
                    }
                    else if (__dummyScrutVar1.equals("min"))
                    {
                        data = "\\min ";
                    }
                    else if (__dummyScrutVar1.equals("max"))
                    {
                        data = "\\max ";
                    }
                    else if (__dummyScrutVar1.equals("log"))
                    {
                        data = "\\log ";
                    }
                    else if (__dummyScrutVar1.equals("ln"))
                    {
                        data = "\\ln ";
                    }
                    else
                    {
                        {
                            // sometimes the text is missing, should check
                            if (str != null)
                            {
                                for (Char c : str.toCharArray())
                                {
                                    //standard text
                                    String cs = String.valueOf(c);
                                    // special characters
                                    if (_mathTable.containsKey(cs))
                                    {
                                        data += _mathTable.get(cs);
                                    }
                                    else
                                    {
                                        // normal characters
                                        data += cs;
                                    } 
                                }
                            }
                             
                        }
                    }                 
                    _tex.addText(data);
                }
            }
            else // ( xxx )
            if (__dummyScrutVar0.equals("m:d"))
            {
                {
                    String begChar = getString(node,"./m:dPr/m:begChr/@m:val");
                    String endChar = getString(node,"./m:dPr/m:endChr/@m:val");
                    //hack
                    //if no begin and end char specified and a nobar fraction comes (a binomial)
                    //then no ()-s should be put
                    if (StringSupport.isNullOrEmpty(begChar) && StringSupport.isNullOrEmpty(endChar) && countNodes(node,"./m:e/m:f/m:fPr/m:type[@m:val='noBar']") == 1)
                    {
                        processMathNodes(getNodes(node,"./m:e/*"));
                        break;
                    }
                     
                    boolean isEqArr = countNodes(node,"./m:e/m:eqArr") > 0;
                    String b = "";
                    String __dummyScrutVar2 = begChar;
                    if (__dummyScrutVar2.equals("{"))
                    {
                        if (!isEqArr)
                        {
                            b = "\\lbrace ";
                        }
                        else
                        {
                            b = "\\bigg\\{ ";
                        } 
                    }
                    else if (__dummyScrutVar2.equals("["))
                    {
                        b = "\\lbrack ";
                    }
                    else if (__dummyScrutVar2.equals("〈"))
                    {
                        b = "\\langle ";
                    }
                    else if (__dummyScrutVar2.equals("⌊"))
                    {
                        b = "\\lfloor ";
                    }
                    else if (__dummyScrutVar2.equals("⌈"))
                    {
                        b = "\\lceil ";
                    }
                    else if (__dummyScrutVar2.equals("|"))
                    {
                        b = "\\vert ";
                    }
                    else if (__dummyScrutVar2.equals("‖"))
                    {
                        b = "\\Vert ";
                    }
                    else if (__dummyScrutVar2.equals("]"))
                    {
                        b = "\\rbrack ";
                    }
                    else if (__dummyScrutVar2.equals("("))
                    {
                        b = "(";
                    }
                    else
                    {
                        b = "(";
                    }         
                    if (!StringSupport.isNullOrEmpty(b))
                    {
                        _tex.addText(b);
                    }
                     
                    processMathNodes(getNodes(node,"./m:e/*"));
                    String e = "";
                    String __dummyScrutVar3 = endChar;
                    if (__dummyScrutVar3.equals("}"))
                    {
                        e = "\\rbrace ";
                    }
                    else if (__dummyScrutVar3.equals("]"))
                    {
                        e = "\\rbrack ";
                    }
                    else if (__dummyScrutVar3.equals("〉"))
                    {
                        e = "\\rangle ";
                    }
                    else if (__dummyScrutVar3.equals("⌋"))
                    {
                        e = "\\rfloor ";
                    }
                    else if (__dummyScrutVar3.equals("⌉"))
                    {
                        e = "\\rceil ";
                    }
                    else if (__dummyScrutVar3.equals("|"))
                    {
                        e = "\\vert ";
                    }
                    else if (__dummyScrutVar3.equals("‖"))
                    {
                        e = "\\Vert ";
                    }
                    else if (__dummyScrutVar3.equals("["))
                    {
                        e = "\\lbrack ";
                    }
                    else if (__dummyScrutVar3.equals(")"))
                    {
                        e = ")";
                    }
                    else
                    {
                        if (StringSupport.isNullOrEmpty(begChar) || StringSupport.equals(begChar, "("))
                        {
                            _tex.addText(")");
                        }
                         
                    }         
                    if (!StringSupport.isNullOrEmpty(e))
                    {
                        _tex.addText(e);
                    }
                     
                }
            }
            else // box
            if (__dummyScrutVar0.equals("m:box"))
            {
                {
                    processMathNodes(getNodes(node,"./m:e/*"));
                }
            }
            else // frac or binom
            if (__dummyScrutVar0.equals("m:f"))
            {
                {
                    if (countNodes(node,"./m:fPr/m:type[@m:val='noBar']") == 1)
                    {
                        _tex.addText("\\binom{");
                    }
                    else
                    {
                        _tex.addText("\\frac{");
                    } 
                    //TODO: frac styles: a/b
                    //numerator
                    processMathNodes(getNodes(node,"./m:num/*"));
                    _tex.addText("}{");
                    //denominator
                    processMathNodes(getNodes(node,"./m:den/*"));
                    _tex.addText("}");
                }
            }
            else // roots
            if (__dummyScrutVar0.equals("m:rad"))
            {
                {
                    _tex.addText("\\sqrt");
                    // if has child nodes
                    if (countNodes(node,"./m:deg/*") > 0)
                    {
                        _tex.addText("[");
                        processMathNodes(getNodes(node,"./m:deg/*"));
                        _tex.addText("]");
                    }
                     
                    // under deg
                    _tex.addText("{");
                    processMathNodes(getNodes(node,"./m:e/*"));
                    _tex.addText("}");
                }
            }
            else // big operators
            if (__dummyScrutVar0.equals("m:nary"))
            {
                {
                    XmlNode naryPr = getNode(node,"./m:naryPr");
                    String op = "\\int";
                    if (countNodes(naryPr,"./m:chr") == 1)
                    {
                        String chr = getString(naryPr,"./m:chr/@m:val");
                        String __dummyScrutVar4 = chr;
                        // different kinds of big operators
                        if (__dummyScrutVar4.equals("∑"))
                        {
                            op = "\\sum";
                        }
                        else if (__dummyScrutVar4.equals("∏"))
                        {
                            op = "\\prod";
                        }
                        else if (__dummyScrutVar4.equals("∐"))
                        {
                            op = "\\coprod";
                        }
                        else if (__dummyScrutVar4.equals("⋃"))
                        {
                            op = "\\bigcup";
                        }
                        else if (__dummyScrutVar4.equals("⋂"))
                        {
                            op = "\\bigcap";
                        }
                        else if (__dummyScrutVar4.equals("⋁"))
                        {
                            op = "\\bigvee";
                        }
                        else if (__dummyScrutVar4.equals("⋀"))
                        {
                            op = "\\bigwedge";
                        }
                        else if (__dummyScrutVar4.equals("∬"))
                        {
                            op = "\\iint";
                        }
                        else if (__dummyScrutVar4.equals("∭"))
                        {
                            op = "\\iiint";
                        }
                        else if (__dummyScrutVar4.equals("∮"))
                        {
                            op = "\\oint";
                        }
                        else if (__dummyScrutVar4.equals("∯"))
                        {
                            op = "\\oint\\oint";
                        }
                        else //TODO
                        if (__dummyScrutVar4.equals("∰"))
                        {
                            op = "\\oint\\oint\\oint";
                        }
                        else
                        {
                            //TODO
                            op = "";
                        }            
                    }
                     
                    _tex.addText(op);
                    //TODO: alignment of scripts
                    //subscript
                    if (countNodes(node,"./m:sub/*") > 0)
                    {
                        _tex.addText("_{");
                        processMathNodes(getNodes(node,"./m:sub/*"));
                        _tex.addText("}");
                    }
                     
                    //superscript
                    if (countNodes(node,"./m:sup/*") > 0)
                    {
                        _tex.addText("^{");
                        processMathNodes(getNodes(node,"./m:sup/*"));
                        _tex.addText("}");
                    }
                     
                    // main data
                    _tex.addText("{");
                    processMathNodes(getNodes(node,"./m:e/*"));
                    _tex.addText("}");
                }
            }
            else //superscript
            if (__dummyScrutVar0.equals("m:sSup"))
            {
                {
                    // base
                    processMathNodes(getNodes(node,"./m:e/*"));
                    // sup
                    _tex.addText("^{");
                    processMathNodes(getNodes(node,"./m:sup/*"));
                    _tex.addText("}");
                }
            }
            else //subscript
            if (__dummyScrutVar0.equals("m:sSub"))
            {
                {
                    // base
                    processMathNodes(getNodes(node,"./m:e/*"));
                    // sub
                    _tex.addText("_{");
                    processMathNodes(getNodes(node,"./m:sub/*"));
                    _tex.addText("}");
                }
            }
            else //sub+superscript
            if (__dummyScrutVar0.equals("m:sSubSup"))
            {
                {
                    // base
                    processMathNodes(getNodes(node,"./m:e/*"));
                    // sub
                    _tex.addText("_{");
                    processMathNodes(getNodes(node,"./m:sub/*"));
                    _tex.addText("}");
                    // sup
                    _tex.addText("^{");
                    processMathNodes(getNodes(node,"./m:sup/*"));
                    _tex.addText("}");
                }
            }
            else //sub+superscript
            if (__dummyScrutVar0.equals("m:sPre"))
            {
                {
                    //TODO: nicer
                    // sub
                    _tex.addText("{}_{");
                    processMathNodes(getNodes(node,"./m:sub/*"));
                    _tex.addText("}");
                    // sup
                    _tex.addText("{}^{");
                    processMathNodes(getNodes(node,"./m:sup/*"));
                    _tex.addText("}");
                    // base
                    processMathNodes(getNodes(node,"./m:e/*"));
                }
            }
            else // equation arrays
            if (__dummyScrutVar0.equals("m:eqArr"))
            {
                {
                    _tex.addNL();
                    _tex.addTextNL("\\begin{gathered}");
                    for (Object __dummyForeachVar2 : getNodes(node,"./m:e"))
                    {
                        XmlNode eq = (XmlNode)__dummyForeachVar2;
                        processMathNodes(getNodes(eq,"./*"));
                        _tex.addTextNL(" \\\\");
                    }
                    _tex.addTextNL("\\end{gathered}");
                }
            }
            else // functions
            if (__dummyScrutVar0.equals("m:func"))
            {
                {
                    //function
                    processMathNodes(getNodes(node,"./m:fName/*"));
                    //data
                    processMathNodes(getNodes(node,"./m:e/*"));
                }
            }
            else // bar
            if (__dummyScrutVar0.equals("m:bar"))
            {
                {
                    if (countNodes(node,"./m:barPr/m:pos[@m:val='top']") == 1)
                    {
                        _tex.addText("\\overline{");
                    }
                    else
                    {
                        _tex.addText("\\underline{");
                    } 
                    //base
                    processMathNodes(getNodes(node,"./m:e/*"));
                    _tex.addText("}");
                }
            }
            else // accents
            if (__dummyScrutVar0.equals("m:acc"))
            {
                {
                    String acc = getString(node,"./m:accPr/m:chr/@m:val");
                    String lacc = "";
                    String __dummyScrutVar5 = acc;
                    if (__dummyScrutVar5.equals("̇"))
                    {
                        lacc = "\\dot{";
                    }
                    else if (__dummyScrutVar5.equals("̈"))
                    {
                        lacc = "\\ddot{";
                    }
                    else if (__dummyScrutVar5.equals("⃛"))
                    {
                        lacc = "\\dddot{";
                    }
                    else if (__dummyScrutVar5.equals("̌"))
                    {
                        lacc = "\\check{";
                    }
                    else if (__dummyScrutVar5.equals("̆"))
                    {
                        lacc = "\\breve{";
                    }
                    else if (__dummyScrutVar5.equals("̅"))
                    {
                        lacc = "\\bar{";
                    }
                    else if (__dummyScrutVar5.equals("̿"))
                    {
                        lacc = "{";
                    }
                    else //TODO
                    if (__dummyScrutVar5.equals("⃖"))
                    {
                        lacc = "\\overleftarrow{";
                    }
                    else if (__dummyScrutVar5.equals("⃗"))
                    {
                        lacc = "\\overrightarrow{";
                    }
                    else if (__dummyScrutVar5.equals("⃡"))
                    {
                        lacc = "\\overleftrightarrow{";
                    }
                    else if (__dummyScrutVar5.equals("⃐"))
                    {
                        lacc = "{";
                    }
                    else //TODO
                    if (__dummyScrutVar5.equals("⃑"))
                    {
                        lacc = "{";
                    }
                    else
                    {
                        //TODO
                        lacc = "{";
                    }            
                    _tex.addText(lacc);
                    //base
                    processMathNodes(getNodes(node,"./m:e/*"));
                    _tex.addText("}");
                }
            }
            else // groups with brace
            if (__dummyScrutVar0.equals("m:groupChr"))
            {
                {
                    String chr = getString(node,"./m:groupChrPr/m:chr/@m:val");
                    // over the math
                    if (countNodes(node,"./m:groupChrPr/m:pos[@m:val='top']") == 1)
                    {
                        String put = "";
                        String __dummyScrutVar6 = chr;
                        if (__dummyScrutVar6.equals("\u23DE"))
                        {
                            put = "\\overbrace{";
                        }
                        else //overbrace unicode
                        if (__dummyScrutVar6.equals("→"))
                        {
                            put = "\\overrightarrow{";
                        }
                        else if (__dummyScrutVar6.equals("←"))
                        {
                            put = "\\overleftarrow{";
                        }
                        else if (__dummyScrutVar6.equals("↔"))
                        {
                            put = "\\overleftrightarrow{";
                        }
                        else if (__dummyScrutVar6.equals("⇒"))
                        {
                            put = "!!!DBL!!!\\overrightarrow{";
                        }
                        else if (__dummyScrutVar6.equals("⇐"))
                        {
                            put = "!!!DBL!!!\\overleftarrow{";
                        }
                        else if (__dummyScrutVar6.equals("⇔"))
                        {
                            put = "!!!DBL!!!\\overleftrightarrow{";
                        }
                        else
                        {
                            put = "{";
                        }       
                        _tex.addText(put);
                    }
                    else
                    {
                        // under the math
                        String put = "";
                        String __dummyScrutVar7 = chr;
                        if (__dummyScrutVar7.equals("→"))
                        {
                            put = "\\underrightarrow{";
                        }
                        else if (__dummyScrutVar7.equals("←"))
                        {
                            put = "\\underleftarrow{";
                        }
                        else if (__dummyScrutVar7.equals("↔"))
                        {
                            put = "\\underleftrightarrow{";
                        }
                        else if (__dummyScrutVar7.equals("⇒"))
                        {
                            put = "!!!DBL!!!\\underrightarrow{";
                        }
                        else if (__dummyScrutVar7.equals("⇐"))
                        {
                            put = "!!!DBL!!!\\underleftarrow{";
                        }
                        else if (__dummyScrutVar7.equals("⇔"))
                        {
                            put = "!!!DBL!!!\\underleftrightarrow{";
                        }
                        else
                        {
                            put = "\\underbrace{";
                        }      
                        _tex.addText(put);
                    } 
                    //base
                    processMathNodes(getNodes(node,"./m:e/*"));
                    _tex.addText("}");
                }
            }
            else // overbrace with data
            if (__dummyScrutVar0.equals("m:limUpp"))
            {
                {
                    //this contains a groupChr
                    processMathNodes(getNodes(node,"./m:e/*"));
                    _tex.addText("^{");
                    processMathNodes(getNodes(node,"./m:lim/*"));
                    _tex.addText("}");
                }
            }
            else // underbrace with data
            if (__dummyScrutVar0.equals("m:limLow"))
            {
                {
                    //this contains a groupChr
                    processMathNodes(getNodes(node,"./m:e/*"));
                    _tex.addText("_{");
                    processMathNodes(getNodes(node,"./m:lim/*"));
                    _tex.addText("}");
                }
            }
            else // border
            if (__dummyScrutVar0.equals("m:borderBox"))
            {
                {
                    //TODO: BOX
                    //base
                    processMathNodes(getNodes(node,"./m:e/*"));
                }
            }
            else // matrix
            if (__dummyScrutVar0.equals("m:m"))
            {
                {
                    _tex.addTextNL("\\begin{matrix}");
                    for (Object __dummyForeachVar4 : getNodes(node,"./m:mr"))
                    {
                        XmlNode mr = (XmlNode)__dummyForeachVar4;
                        for (Object __dummyForeachVar3 : getNodes(mr,"./m:e"))
                        {
                            XmlNode e = (XmlNode)__dummyForeachVar3;
                            processMathNodes(e.getChildNodes());
                            _tex.addText(" & ");
                        }
                        _tex.addTextNL("\\\\");
                    }
                    _tex.addTextNL("\\end{matrix}");
                }
            }
            else
            {
            }                   
        }
    }

    /**
    * Add an image
    * 
    *  @param xmlNode
    */
    private void processDrawing(XmlNode xmlNode, boolean inTable) throws Exception {
        if (!Config.Instance.getLaTeXTags().getProcessFigures().Value)
            return ;
         
        XmlNode blipNode = getNode(xmlNode,"./wp:inline/a:graphic/a:graphicData/pic:pic/pic:blipFill/a:blip");
        if (blipNode != null)
        {
            // kill any surrounding styles when simplifying
            _tex.addStyleKiller();
            // if we are in a table then no \begin{figure} allowed
            if (!inTable)
            {
                // put as figure
                _tex.addStartTag(TagEnum.Figure);
                _tex.addTextNL("[" + Config.Instance.getLaTeXTags().getFigurePlacement() + "]");
                if (Config.Instance.getLaTeXTags().getCenterFigures().Value)
                {
                    _tex.addTextNL("\\centering");
                }
                 
            }
             
            // apply width and height
            XmlNode extentNode = getNode(blipNode.getParentNode().getParentNode().getParentNode().getParentNode().getParentNode(),"./wp:extent");
            String widthHeightStr = _imagingFn.getWidthAndHeightFromStyle(getInt(extentNode,"@cx"),getInt(extentNode,"@cy"));
            _tex.addText("\\includegraphics[" + widthHeightStr + "]{");
            // convert and resolve new image path
            _tex.addTextNL(_imagingFn.resolveImage(getString(blipNode,"@r:embed"),_statusInfo) + "}");
            XmlNode captionP = blipNode.getParentNode().getParentNode().getParentNode().getParentNode().getParentNode().getParentNode().getParentNode().getParentNode().getNextSibling();
            // add caption
            imageCaption(captionP);
            // if we are in a table then no \end{figure} allowed
            if (!inTable)
            {
                _tex.addEndTag(TagEnum.Figure);
                _tex.addNL();
            }
             
        }
         
    }

    /**
    * Add an image
    * 
    *  @param xmlNode
    */
    private void processObject(XmlNode xmlNode, boolean inTable) throws Exception {
        if (!Config.Instance.getLaTeXTags().getProcessFigures().Value)
            return ;
         
        XmlNode imageData = getNode(xmlNode,"./v:shape/v:imagedata");
        if (imageData != null)
        {
            // kill any surrounding styles when simplifying
            _tex.addStyleKiller();
            // if we are in a table then no \begin{figure} allowed
            if (!inTable)
            {
                // put as figure
                _tex.addStartTag(TagEnum.Figure);
                _tex.addTextNL("[" + Config.Instance.getLaTeXTags().getFigurePlacement() + "]");
                if (Config.Instance.getLaTeXTags().getCenterFigures().Value)
                {
                    _tex.addTextNL("\\centering");
                }
                 
            }
             
            // apply width and height
            String widthHeightStr = _imagingFn.getWidthAndHeightFromStyle(getString(imageData.getParentNode(),"@style"));
            _tex.addText("\\includegraphics[" + widthHeightStr + "]{");
            // convert and resolve new image path
            _tex.addTextNL(_imagingFn.resolveImage(getString(imageData,"@r:id"),_statusInfo) + "}");
            XmlNode captionP = imageData.getParentNode().getParentNode().getParentNode().getParentNode().getNextSibling();
            // add caption
            imageCaption(captionP);
            // if we are in a table then no \end{figure} allowed
            if (!inTable)
            {
                _tex.addEndTag(TagEnum.Figure);
                _tex.addNL();
            }
             
        }
         
    }

    /**
    * Process picture-like objects
    * 1. textboxes
    * 
    *  @param xmlNode
    */
    private void processPict(XmlNode xmlNode) throws Exception {
        for (Object __dummyForeachVar0 : getNodes(xmlNode,".//v:shape/v:textbox/w:txbxContent"))
        {
            // loop through all textbox contents and process them as normal content
            // if or if not grouped
            XmlNode txbxs = (XmlNode)__dummyForeachVar0;
            bulkMainProcessor(txbxs,false,true);
        }
    }

    /**
    * process a set of paragraphs
    * 
    *  @param par
    */
    private void bulkMainProcessor(XmlNode par, boolean inTable, boolean drawNewLine) throws Exception {
        for (Object __dummyForeachVar1 : getNodes(par,"./*"))
        {
            XmlNode paragraphNode = (XmlNode)__dummyForeachVar1;
            mainProcessor(paragraphNode,inTable,drawNewLine);
        }
    }

    /**
    * Process a paragraph
    * 
    *  @param paragraphNode 
    *  @param prevNode 
    *  @param nextNode
    */
    private void processParagraph(XmlNode paragraphNode, XmlNode prevNode, XmlNode nextNode, boolean inTable, boolean drawNewLine) throws Exception {
        // list settings of the current paragraph
        Integer currentNumId = getInt(paragraphNode,"./w:pPr/w:numPr/w:numId/@w:val");
        Integer currentLevel = getInt(paragraphNode,"./w:pPr/w:numPr/w:ilvl/@w:val");
        boolean isList = currentNumId != null && currentLevel != null;
        ListTypeEnum currentType = _numberingFn.GetNumberingStyle(currentNumId, currentLevel);
        Integer prevNumId = null;
        Integer prevLevel = null;
        ListTypeEnum prevType = ListTypeEnum.None;
        Integer nextNumId = null;
        Integer nextLevel = null;
        ListTypeEnum nextType = ListTypeEnum.None;
        // process list data if we are in a list
        if (isList)
        {
            // list settings of the previous paragraph
            prevNumId = getInt(prevNode,"./w:pPr/w:numPr/w:numId/@w:val");
            prevLevel = getInt(prevNode,"./w:pPr/w:numPr/w:ilvl/@w:val");
            prevType = _numberingFn.GetNumberingStyle(prevNumId, prevLevel);
            // list settings of the next paragraph
            nextNumId = getInt(nextNode,"./w:pPr/w:numPr/w:numId/@w:val");
            nextLevel = getInt(nextNode,"./w:pPr/w:numPr/w:ilvl/@w:val");
            nextType = _numberingFn.GetNumberingStyle(nextNumId, nextLevel);
        }
         
        // if it is a list
        if (isList)
        {
            ListControl listBegin = _numberingFn.ProcessBeforeListItem(currentNumId, currentLevel, currentType, prevNumId, prevLevel, nextNumId, nextLevel);
            // some numbered
            if (listBegin.ListType == ListTypeEnum.Numbered)
            {
                switch(listBegin.NumberedCounterType)
                {
                    case None: 
                        // simple numbered begins
                        _tex.addStartTag(TagEnum.Enumerate);
                        _tex.addNL();
                        break;
                    case NewCounter: 
                        // a new numbered begins
                        if (Config.Instance.getLaTeXTags().getAllowContinuousLists().Value)
                        {
                            _tex.addTextNL("\\newcounter{numberedCnt" + listBegin.Numbering + "}");
                        }
                         
                        _tex.addStartTag(TagEnum.Enumerate);
                        _tex.addNL();
                        break;
                    case LoadCounter: 
                        // a numbered loaded
                        _tex.addStartTag(TagEnum.Enumerate);
                        _tex.addNL();
                        if (Config.Instance.getLaTeXTags().getAllowContinuousLists().Value)
                        {
                            _tex.addTextNL("\\setcounter{enumi}{\\thenumberedCnt" + listBegin.Numbering + "}");
                        }
                         
                        break;
                
                }
            }
            else if (listBegin.ListType == ListTypeEnum.Bulleted)
            {
                // bulleted list begins
                _tex.addStartTag(TagEnum.Itemize);
                _tex.addNL();
            }
              
            //list item
            _tex.addText("\\item ");
        }
         
        // this will process the real content of the paragraph
        processParagraphContent(paragraphNode,prevNode,nextNode,drawNewLine & true,inTable | false,isList);
        // in case of list
        if (isList)
        {
            CSList<ListControl> listEnd = _numberingFn.ProcessAfterListItem(currentNumId, currentLevel, currentType, prevNumId, prevLevel, nextNumId, nextLevel);
            for (ListControl token : listEnd)
            {
                // rollback the ended lists
                // if a numbered list found
                if (token.ListType == ListTypeEnum.Numbered)
                {
                    // save counter of next use
                    if (token.NumberedCounterType == NumberedCounterTypeEnum.SaveCounter)
                    {
                        if (Config.Instance.getLaTeXTags().getAllowContinuousLists().Value)
                        {
                            _tex.addTextNL("\\setcounter{numberedCnt" + token.Numbering + "}{\\theenumi}");
                        }
                         
                    }
                     
                    _tex.addEndTag(TagEnum.Enumerate);
                    _tex.addNL();
                }
                else if (token.ListType == ListTypeEnum.Bulleted)
                {
                    // bulleted ended
                    _tex.addEndTag(TagEnum.Itemize);
                    _tex.addNL();
                }
                  
            }
        }
         
    }

    private String RESOLVED_SECTION = "";
    private String RESOLVED_SUBSECTION = "";
    private String RESOLVED_SUBSUBSECTION = "";
    private String RESOLVED_VERBATIM = "";
    private void cacheResolvedStyles() throws Exception {
        RESOLVED_SECTION = _stylingFn.resolveParaStyle("section");
        RESOLVED_SUBSECTION = _stylingFn.resolveParaStyle("subsection");
        RESOLVED_SUBSUBSECTION = _stylingFn.resolveParaStyle("subsubsection");
        RESOLVED_VERBATIM = _stylingFn.resolveParaStyle("verbatim");
    }

    /**
    * Process the paragraph's real content
    * 
    *  @param paraNode 
    *  @param prevNode 
    *  @param nextNode 
    *  @param drawNewLine 
    *  @param inTable 
    *  @param isList
    */
    private void processParagraphContent(XmlNode paraNode, XmlNode prevNode, XmlNode nextNode, boolean drawNewLine, boolean inTable, boolean isList) throws Exception {
        String paraStyle = getLowerString(paraNode,"./w:pPr/w:pStyle/@w:val");
        // if a heading found then process it
        if (StringSupport.equals(paraStyle, RESOLVED_SECTION) || StringSupport.equals(paraStyle, RESOLVED_SUBSECTION) || StringSupport.equals(paraStyle, RESOLVED_SUBSUBSECTION))
        {
            // put sections
            if (StringSupport.equals(paraStyle, RESOLVED_SECTION))
            {
                _tex.addText(Config.Instance.getLaTeXTags().getSection() + "{");
            }
            else if (StringSupport.equals(paraStyle, RESOLVED_SUBSECTION))
            {
                _tex.addText(Config.Instance.getLaTeXTags().getSubSection() + "{");
            }
            else if (StringSupport.equals(paraStyle, RESOLVED_SUBSUBSECTION))
            {
                _tex.addText(Config.Instance.getLaTeXTags().getSubSubSection() + "{");
            }
               
            // put text
            paragraphRuns(paraNode,false,false);
            _tex.addText("}");
            if (Config.Instance.getLaTeXTags().getPutSectionReferences().Value)
            {
                // put the reference name
                if (countNodes(paraNode,"w:bookmarkStart") > 0)
                {
                    _tex.addText("\\label{section:" + getString(paraNode,"./w:bookmarkStart/@w:name") + "}");
                }
                 
            }
             
            _tex.addNL();
        }
        else if (StringSupport.equals(paraStyle, RESOLVED_VERBATIM))
        {
            // if verbatim node found
            String prevParaStyle = getLowerString(prevNode,"./w:pPr/w:pStyle/@w:val");
            String nextParaStyle = getLowerString(nextNode,"./w:pPr/w:pStyle/@w:val");
            // the previous was also verbatim
            boolean wasVerbatim = StringSupport.equals(prevParaStyle, RESOLVED_VERBATIM);
            // the next will be also verbatim
            boolean willVerbatim = StringSupport.equals(nextParaStyle, RESOLVED_VERBATIM);
            // the first verbatim is begining
            if (!wasVerbatim)
            {
                _tex.addStartTag(TagEnum.Verbatim);
            }
             
            _tex.addNL();
            // content
            paragraphRuns(paraNode,false,true);
            // the last verbatim ends
            if (!willVerbatim)
            {
                _tex.addNL();
                _tex.addEndTag(TagEnum.Verbatim);
                _tex.addNL();
            }
             
        }
        else if (countNodes(paraNode,"./w:fldSimple[starts-with(@w:instr, \' SEQ \')]") > 0)
        {
            // a caption text here
            listingCaptionRun(paraNode);
            if (drawNewLine)
            {
                _tex.addNL();
            }
             
        }
        else
        {
            // draw NORMAL paragraph runs
            paragraphRuns(paraNode,inTable,false);
            if (drawNewLine)
            {
                _tex.addNL();
                if (!isList)
                {
                    _tex.addNL();
                }
                 
            }
             
        }   
    }

    /**
    * Process normal paragraph runs
    * 
    *  @param paraNode 
    *  @param inTable 
    *  @param inVerbatim
    */
    private void paragraphRuns(XmlNode paraNode, boolean inTable, boolean inVerbatim) throws Exception {
        // apply paragraph level styling for standard paragraphs
        if (!inTable && !inVerbatim)
        {
            textParaStyleStart(getNode(paraNode,"./w:pPr"));
        }
         
        RunInfo lastRunInfo = new RunInfo();
        for (Object __dummyForeachVar1 : getNodes(paraNode,"./w:r|./m:oMathPara|./m:oMath|./w:smartTag|./w:hyperlink"))
        {
            //string lastCFC = "!!!NONE!!!";
            // process all runs
            XmlNode run = (XmlNode)__dummyForeachVar1;
            // normal runs
            if (StringSupport.equals(run.getName(), "w:r"))
            {
                lastRunInfo = processSingleRun(inVerbatim,lastRunInfo,run,inTable);
            }
            else if (StringSupport.equals(run.getName(), "w:smartTag") || StringSupport.equals(run.getName(), "w:hyperlink"))
            {
                for (Object __dummyForeachVar0 : getNodes(run,".//w:r"))
                {
                    // when runs are under smartTags or hyperlinks
                    XmlNode stRun = (XmlNode)__dummyForeachVar0;
                    lastRunInfo = processSingleRun(inVerbatim,lastRunInfo,stRun,inTable);
                }
            }
            else // math paragraph
            if (StringSupport.equals(run.getName(), "m:oMathPara"))
            {
                //math content
                processMath(getNode(run,"./m:oMath"));
                _tex.addTextNL(Config.Instance.getLaTeXTags().getBreaks().getLine());
            }
            else // math content
            if (StringSupport.equals(run.getName(), "m:oMath"))
            {
                processMath(run);
            }
                
        }
        // apply style end for standard paragraphs
        if (!inTable && !inVerbatim)
        {
            textParaStyleEnd(getNode(paraNode,"./w:pPr"));
        }
         
    }

    private RunInfo processSingleRun(boolean inVerbatim, RunInfo prevRunInfo, XmlNode run, boolean inTable) throws Exception {
        RunInfo currentRunInfo = new RunInfo(prevRunInfo);
        // if it is not verbatim then process breaks and styles
        if (!inVerbatim)
        {
            if (countNodes(run,"./w:br") > 0)
            {
                // page break
                if (StringSupport.equals(getString(run,"./w:br/@w:type"), "page"))
                {
                    _tex.addTextNL(Config.Instance.getLaTeXTags().getBreaks().getPage());
                }
                else
                {
                    // line break
                    _tex.addTextNL(Config.Instance.getLaTeXTags().getBreaks().getLine());
                } 
            }
             
            // tab
            if (countNodes(run,"./w:tab") > 0)
            {
                _tex.addText("\\ \\ \\ \\ ");
            }
             
            // apply run level style
            textRunStyleStart(getNode(run,"./w:rPr"));
        }
        else
        {
            // for verbatims put a simple newline
            if (countNodes(run,"./w:br") > 0)
            {
                _tex.addNL();
            }
             
        } 
        String tmpFld = getString(run,"./w:fldChar[@w:fldCharType=\'begin\' or @w:fldCharType=\'separate\' or @w:fldCharType=\'end\']/@w:fldCharType");
        // store the last crossref field command (begin or separator or end)
        if (!StringSupport.isNullOrEmpty(tmpFld))
        {
            currentRunInfo.setCFC(tmpFld);
        }
         
        /**
        * / query the name of the bookmark
        */
        String currentBookmarkName = getString(run,"./w:instrText");
        // if set
        if (!StringSupport.isNullOrEmpty(currentBookmarkName))
        {
            // it is a crossref
            if (currentBookmarkName.startsWith(" REF "))
            {
                currentRunInfo.setInstrTextType(InstrTextTypeEnum.Reference);
            }
            else
            {
                // it is not a cross ref
                currentRunInfo.setInstrTextType(InstrTextTypeEnum.OtherField);
            } 
        }
         
        // if end then no instrtext
        if (StringSupport.equals(currentRunInfo.getCFC(), "end"))
        {
            // no field found
            currentRunInfo.setInstrTextType(InstrTextTypeEnum.None);
        }
         
        // if we are in the exact bookmark reference in the begin part of the CFC then this is a reference
        if (StringSupport.equals(currentRunInfo.getCFC(), "begin") && currentRunInfo.getInstrTextType() == InstrTextTypeEnum.Reference && !StringSupport.isNullOrEmpty(currentBookmarkName))
        {
            processReference(currentBookmarkName);
        }
        else // else standard text or object
        // no CFC at all
        // if we are after a separate cfc an not reference field was here then we are interested in the text
        // or if we are after the fields
        if ((currentRunInfo.getCFC() == null) || (StringSupport.equals(currentRunInfo.getCFC(), "separate") && (currentRunInfo.getInstrTextType() == InstrTextTypeEnum.None || currentRunInfo.getInstrTextType() == InstrTextTypeEnum.OtherField)) || (StringSupport.equals(currentRunInfo.getCFC(), "end")))
        {
            String text = getString(run,"./w:t");
            // if standard text
            if (!StringSupport.isNullOrEmpty(text))
            {
                textRun(text,inVerbatim);
            }
            else if (getNode(run,"./w:drawing") != null)
            {
                // image
                processDrawing(getNode(run,"./w:drawing"),inTable);
            }
            else if (getNode(run,"./w:object") != null)
            {
                // image
                processObject(getNode(run,"./w:object"),inTable);
            }
            else if (getNode(run,"./w:pict") != null)
            {
                // textbox
                processPict(getNode(run,"./w:pict"));
            }
                
        }
          
        // apply styles if not verbatim
        if (!inVerbatim)
        {
            textRunStyleEnd(getNode(run,"./w:rPr"));
        }
         
        return currentRunInfo;
    }

    /**
    * Process text run
    * 
    *  @param t 
    *  @param inVerbatim
    */
    private void textRun(String t, boolean inVerbatim) throws Exception {
        if (t == null)
            return ;
         
        // normal
        if (!inVerbatim)
        {
            _tex.addText(_texingFn.teXizeText(t));
        }
        else
        {
            // verbatim
            _tex.addVerbatim(_texingFn.verbatimizeText(t));
        } 
    }

    /**
    * Text level styling starts
    * 
    *  @param xmlNode
    */
    private void textRunStyleStart(XmlNode xmlNode) throws Exception {
        if (xmlNode == null)
            return ;
         
        for (Object __dummyForeachVar0 : getNodes(xmlNode,"./*"))
        {
            XmlNode sty = (XmlNode)__dummyForeachVar0;
            String __dummyScrutVar0 = sty.getName();
            if (__dummyScrutVar0.equals("w:i"))
            {
                _tex.addStartStyle(StyleEnum.TextIt);
            }
            else if (__dummyScrutVar0.equals("w:b"))
            {
                _tex.addStartStyle(StyleEnum.TextBf);
            }
            else if (__dummyScrutVar0.equals("w:u"))
            {
                _tex.addStartStyle(StyleEnum.Underline);
            }
            else if (__dummyScrutVar0.equals("w:strike"))
            {
                _tex.addStartStyle(StyleEnum.Sout);
            }
            else if (__dummyScrutVar0.equals("w:dstrike"))
            {
                _tex.addStartStyle(StyleEnum.Xout);
            }
            else if (__dummyScrutVar0.equals("w:smallCaps"))
            {
                _tex.addStartStyle(StyleEnum.TextSc);
            }
            else if (__dummyScrutVar0.equals("w:caps"))
            {
                _tex.addStartStyle(StyleEnum.TextC);
            }
                   
        }
        for (Object __dummyForeachVar1 : getNodes(xmlNode,"./*"))
        {
            XmlNode sty = (XmlNode)__dummyForeachVar1;
            if (StringSupport.equals(sty.getName(), "w:vertAlign") && StringSupport.equals(getString(sty,"./@w:val"), "superscript"))
            {
                _tex.addStartStyle(StyleEnum.SuperScript);
            }
            else if (StringSupport.equals(sty.getName(), "w:vertAlign") && StringSupport.equals(getString(sty,"./@w:val"), "subscript"))
            {
                _tex.addStartStyle(StyleEnum.SubScript);
            }
              
        }
    }

    /**
    * Text level styling ends
    * 
    *  @param xmlNode
    */
    private void textRunStyleEnd(XmlNode xmlNode) throws Exception {
        if (xmlNode == null)
            return ;
         
        for (Object __dummyForeachVar2 : getNodes(xmlNode,"./*"))
        {
            XmlNode sty = (XmlNode)__dummyForeachVar2;
            if (StringSupport.equals(sty.getName(), "w:vertAlign") && StringSupport.equals(getString(sty,"./@w:val"), "superscript"))
            {
                _tex.addEndStyle(StyleEnum.SuperScript);
            }
            else if (StringSupport.equals(sty.getName(), "w:vertAlign") && StringSupport.equals(getString(sty,"./@w:val"), "subscript"))
            {
                _tex.addEndStyle(StyleEnum.SubScript);
            }
              
        }
        // ensure that the styles are closed in the reverse order as they are inserted
        // this is important when different style have different ending characters or
        // when the styles are paired
        CSList<XmlNode> endNodes = new CSList<XmlNode>();
        for (Object __dummyForeachVar3 : getNodes(xmlNode,"./*"))
        {
            XmlNode sty = (XmlNode)__dummyForeachVar3;
            endNodes.add(sty);
        }
        endNodes.Reverse();
        for (XmlNode sty : endNodes)
        {
            String __dummyScrutVar1 = sty.getName();
            if (__dummyScrutVar1.equals("w:i"))
            {
                _tex.addEndStyle(StyleEnum.TextIt);
            }
            else if (__dummyScrutVar1.equals("w:b"))
            {
                _tex.addEndStyle(StyleEnum.TextBf);
            }
            else if (__dummyScrutVar1.equals("w:u"))
            {
                _tex.addEndStyle(StyleEnum.Underline);
            }
            else if (__dummyScrutVar1.equals("w:strike"))
            {
                _tex.addEndStyle(StyleEnum.Sout);
            }
            else if (__dummyScrutVar1.equals("w:dstrike"))
            {
                _tex.addEndStyle(StyleEnum.Xout);
            }
            else if (__dummyScrutVar1.equals("w:smallCaps"))
            {
                _tex.addEndStyle(StyleEnum.TextSc);
            }
            else if (__dummyScrutVar1.equals("w:caps"))
            {
                _tex.addEndStyle(StyleEnum.TextC);
            }
                   
        }
    }

    /**
    * Paragraph level styling starts
    * 
    *  @param xmlNode
    */
    private void textParaStyleStart(XmlNode xmlNode) throws Exception {
        if (xmlNode == null)
            return ;
         
        for (Object __dummyForeachVar5 : getNodes(xmlNode,"./*"))
        {
            XmlNode chld = (XmlNode)__dummyForeachVar5;
            if (StringSupport.equals(chld.getName(), "w:jc"))
            {
                String align = getString(chld,"@w:val");
                if (StringSupport.equals(align, "right"))
                {
                    _tex.addStartStyle(StyleEnum.ParaFlushRight);
                }
                else if (StringSupport.equals(align, "center"))
                {
                    _tex.addStartStyle(StyleEnum.ParaCenter);
                }
                  
            }
             
        }
    }

    /**
    * Paragraph level styling ends
    * 
    *  @param xmlNode
    */
    private void textParaStyleEnd(XmlNode xmlNode) throws Exception {
        if (xmlNode == null)
            return ;
         
        CSList<XmlNode> endNodes = new CSList<XmlNode>();
        for (Object __dummyForeachVar6 : getNodes(xmlNode,"./*"))
        {
            XmlNode sty = (XmlNode)__dummyForeachVar6;
            endNodes.add(sty);
        }
        endNodes.Reverse();
        for (XmlNode chld : endNodes)
        {
            if (StringSupport.equals(chld.getName(), "w:jc"))
            {
                String align = getString(chld,"@w:val");
                if (StringSupport.equals(align, "right"))
                {
                    _tex.addEndStyle(StyleEnum.ParaFlushRight);
                }
                else if (StringSupport.equals(align, "center"))
                {
                    _tex.addEndStyle(StyleEnum.ParaCenter);
                }
                  
            }
             
        }
    }

    /**
    * Process table
    * 
    *  @param tblNode
    */
    private void processTable(XmlNode tblNode) throws Exception {
        int numberOfColumns = countNodes(tblNode,"./w:tblGrid/w:gridCol");
        _tex.addStartTag(TagEnum.Table);
        _tex.addTextNL("[" + Config.Instance.getLaTeXTags().getTablePlacement() + "]");
        if (Config.Instance.getLaTeXTags().getCenterTables().Value)
        {
            _tex.addTextNL("\\centering");
        }
         
        _tex.addText("\\begin{tabular}{|");
        for (int i = 0;i < numberOfColumns;i++)
        {
            _tex.addText("l|");
        }
        _tex.addTextNL("}");
        _tex.addTextNL("\\hline");
        for (Object __dummyForeachVar1 : getNodes(tblNode,"./w:tr"))
        {
            //rows
            XmlNode tr = (XmlNode)__dummyForeachVar1;
            for (Object __dummyForeachVar0 : getNodes(tr,"./w:tc"))
            {
                //columns
                XmlNode tc = (XmlNode)__dummyForeachVar0;
                bulkMainProcessor(tc,true,false);
                if (tc.getNextSibling() != null)
                {
                    _tex.addText(" & ");
                }
                 
            }
            _tex.addTextNL(" \\\\");
            _tex.addTextNL("\\hline");
        }
        _tex.addTextNL("\\end{tabular}");
        XmlNode captionP = tblNode.getNextSibling();
        if (!StringSupport.isNullOrEmpty(getString(captionP,"./w:fldSimple[starts-with(@w:instr, ' SEQ Table ')]/@w:instr")))
        {
            _tex.addText("\\caption{");
            if (Config.Instance.getLaTeXTags().getPutTableReferences().HasValue)
            {
                _tex.addText("\\label{table:" + getString(captionP,"./w:bookmarkStart/@w:name") + "}");
            }
             
            captionText(captionP);
            _tex.addTextNL("}");
        }
         
        _tex.addEndTag(TagEnum.Table);
        _tex.addNL();
    }

    private XmlDocument _doc;
    private Store _tex;
    private XmlNamespaceManager _nsmgr = new XmlNamespaceManager();
    private Numbering _numberingFn;
    private Styling _stylingFn;
    private Taging _tagingFn;
    private Imaging _imagingFn;
    private TeXing _texingFn;
    private IStatusInformation _statusInfo;
    /**
    * Setup helpers and namespaces
    * 
    *  @param documentXmlStream 
    *  @param dotnetFn
    */
    public Engine(InputStream documentXmlStream, Numbering numberingFn, Imaging imagingFn, IStatusInformation statusInfo) throws Exception {
        _statusInfo = statusInfo;
        _doc = new XmlDocument();
        _doc.load(documentXmlStream);
        _texingFn = new TeXing();
        _stylingFn = new Styling();
        Taging tagingFn = new Taging();
        _tex = new Store(_stylingFn,tagingFn,statusInfo);
        _nsmgr = new XmlNamespaceManager(_doc.NameTable);
        _nsmgr.AddNamespace("w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        _nsmgr.AddNamespace("wp", "http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing");
        _nsmgr.AddNamespace("a", "http://schemas.openxmlformats.org/drawingml/2006/main");
        _nsmgr.AddNamespace("pic", "http://schemas.openxmlformats.org/drawingml/2006/picture");
        _nsmgr.AddNamespace("r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships");
        _nsmgr.AddNamespace("m", "http://schemas.openxmlformats.org/officeDocument/2006/math");
        _nsmgr.AddNamespace("v", "urn:schemas-microsoft-com:vml");
        _numberingFn = numberingFn;
        _imagingFn = imagingFn;
        initMathTables();
        cacheResolvedStyles();
        cacheBookmarks();
    }

    /**
    * Entry method
    * 
    *  @return
    */
    public String process() throws Exception {
        header();
        int cntNodes = countNodes(_doc,"/w:document/w:body/*");
        int cnt = 0;
        for (Object __dummyForeachVar0 : getNodes(_doc,"/w:document/w:body/*"))
        {
            XmlNode paragraphNode = (XmlNode)__dummyForeachVar0;
            mainProcessor(paragraphNode,false,true);
            cnt++;
            _statusInfo.writeCR(String.format(StringSupport.CSFmtStrToJFmtStr("Processed: {0} percent"),cnt * 100 / cntNodes));
        }
        footer();
        _statusInfo.writeLine("Temporary data structure generated.");
        String tex = _tex.convertToString();
        _statusInfo.writeLine("done.");
        return tex;
    }

    /**
    * Processes a paragraph or a table
    * 
    *  @param paragraphNode
    */
    private void mainProcessor(XmlNode paragraphNode, boolean inTable, boolean drawNewLine) throws Exception {
        if (StringSupport.equals(paragraphNode.getName(), "w:p"))
        {
            ProcessParagraph(paragraphNode, paragraphNode.PreviousSibling, paragraphNode.getNextSibling(), inTable, drawNewLine);
        }
        else if (StringSupport.equals(paragraphNode.getName(), "w:tbl"))
        {
            processTable(paragraphNode);
        }
          
    }

    /**
    * Header
    */
    private void header() throws Exception {
        // the specified document class or article if not specified
        String docClass = Config.Instance.getInfra().getDocumentClass() != null ? Config.Instance.getInfra().getDocumentClass() : "article";
        // properties
        List<String> propsList = new CSList<String>();
        if (!StringSupport.isNullOrEmpty(Config.Instance.getInfra().getFontSize()))
        {
            propsList.add(Config.Instance.getInfra().getFontSize());
        }
         
        if (!StringSupport.isNullOrEmpty(Config.Instance.getInfra().getPaperSize()))
        {
            propsList.add(Config.Instance.getInfra().getPaperSize());
        }
         
        if (Config.Instance.getInfra().getLandscape() == true)
        {
            propsList.add("landscape");
        }
         
        propsList.RemoveAll(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(p) => {
            return String.IsNullOrEmpty(p);
        }" */);
        String props = StringSupport.join(((String[]) propsList.toArray()), ",");
        _tex.addTextNL("\\documentclass[" + props + "]{" + docClass + "}");
        InputEncInfo enc = docx2tex.Library.Data.InputEnc.Instance.getCurrentEncoding();
        if (enc != null)
        {
            _tex.addTextNL("\\usepackage[" + enc.getInputEncoding() + "]{inputenc}");
        }
         
        _tex.addTextNL("\\usepackage{graphicx}");
        _tex.addTextNL("\\usepackage{ulem}");
        _tex.addTextNL("\\usepackage{amsmath}");
        _tex.addTextNL("\\begin{document}");
    }

    /**
    * Footer
    */
    private void footer() throws Exception {
        _tex.addTextNL("\\end{document}");
    }

}


