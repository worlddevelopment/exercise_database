//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlDocument;
import CS2JNet.System.Xml.XmlNode;
import docx2tex.Library.ListControl;
import docx2tex.Library.ListInfo;
import docx2tex.Library.ListTypeEnum;
import docx2tex.Library.NumberedCounterTypeEnum;
import java.io.InputStream;
import java.util.HashMap;

public class Numbering   
{
    private ZipPackagePart _numberingPart = new ZipPackagePart();
    private XmlDocument _numberingDoc;
    private XmlNamespaceManager _xmlnsMgr = new XmlNamespaceManager();
    private CSList<ListInfo> _listStyle;
    private HashMap<Long,String> _visitedFirstLevelNumberings;
    public Numbering(ZipPackagePart numberingPart) throws Exception {
        _numberingPart = numberingPart;
        // if exist then load it
        if (numberingPart != null)
        {
            InputStream numberingStream = _numberingPart.GetStream();
            NameTable nt = new NameTable();
            _xmlnsMgr = new XmlNamespaceManager(nt);
            _xmlnsMgr.AddNamespace("w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
            _numberingDoc = new XmlDocument(nt);
            _numberingDoc.load(numberingStream);
        }
         
        _listStyle = new CSList<ListInfo>();
        _visitedFirstLevelNumberings = new HashMap<Long,String>();
    }

    public ListControl processBeforeListItem(int currentNumId, int currentLevel, ListTypeEnum currentType, Integer previousNumId, Integer previousLevel, Integer nextNumId, Integer nextLevel) throws Exception {
        // this is the first list element
        if (!previousNumId != null || !previousLevel != null)
        {
            ListInfo listInfo = new ListInfo(currentNumId,currentLevel,currentType);
            _listStyle.add(listInfo);
            String suffix = "";
            if (currentType == ListTypeEnum.Numbered && currentLevel == 0)
            {
                if (_visitedFirstLevelNumberings.containsKey(listInfo.getHashCode()))
                {
                    return new ListControl(ListTypeEnum.Numbered,NumberedCounterTypeEnum.LoadCounter,uniqueString(listInfo.NumId));
                }
                else
                {
                    _visitedFirstLevelNumberings.put(listInfo.getHashCode(), "");
                    return new ListControl(ListTypeEnum.Numbered,NumberedCounterTypeEnum.NewCounter,uniqueString(listInfo.NumId));
                } 
            }
             
            return new ListControl(currentType,NumberedCounterTypeEnum.None,null);
        }
        else
        {
            //this is not the first list element
            int listTopNumId = _listStyle.get(_listStyle.size() - 1).NumId;
            int listTopLevel = _listStyle.get(_listStyle.size() - 1).Level;
            // the same list continues
            if (listTopNumId == currentNumId)
            {
                // the same list continues with the same level
                if (listTopLevel == currentLevel)
                {
                }
                else
                {
                    //nothing to do
                    // the same list continues but with different level
                    // a new level started
                    if (currentLevel > listTopLevel)
                    {
                        _listStyle.add(new ListInfo(currentNumId,currentLevel,currentType));
                        return new ListControl(currentType,NumberedCounterTypeEnum.None,null);
                    }
                    else
                    {
                    } 
                } 
            }
            else
            {
                // the previous level ended
                //nothing to do
                // there was an other list before this
                int indexOfPrevious = findListElement(previousNumId,previousLevel);
                //a new list started because the previous cannot find in the list
                if (indexOfPrevious != -1)
                {
                    _listStyle.add(new ListInfo(currentNumId,currentLevel,currentType));
                    return new ListControl(currentType,NumberedCounterTypeEnum.None,null);
                }
                else
                {
                } 
            } 
        } 
        return new ListControl(ListTypeEnum.None,NumberedCounterTypeEnum.None,null);
    }

    // a previously broken list found
    // nothing to do
    public CSList<ListControl> processAfterListItem(int currentNumId, int currentLevel, ListTypeEnum currentType, Integer previousNumId, Integer previousLevel, Integer nextNumId, Integer nextLevel) throws Exception {
        //if this is the last list element
        if (!nextNumId != null || !nextLevel != null)
        {
            ListControl suffix = new ListControl(ListTypeEnum.None,NumberedCounterTypeEnum.None,null);
            if (_listStyle.size() > 0)
            {
                ListInfo listInfo = _listStyle.get(0);
                if (listInfo.Style == ListTypeEnum.Numbered && listInfo.Level == 0)
                {
                    if (_visitedFirstLevelNumberings.containsKey(listInfo.getHashCode()))
                    {
                        suffix.NumberedCounterType = NumberedCounterTypeEnum.SaveCounter;
                        suffix.Numbering = uniqueString(listInfo.NumId);
                    }
                     
                }
                 
            }
             
            CSList<ListControl> ends = getReverseListTilIndex(0);
            if (ends.size() > 0)
            {
                ListControl first = ends.get(0);
                first.NumberedCounterType = suffix.NumberedCounterType;
                first.Numbering = suffix.Numbering;
                ends.remove((int));
                ends.add(0, first);
            }
             
            _listStyle.clear();
            return ends;
        }
        else
        {
            //a list
            // the same list continues
            if (currentNumId == nextNumId && currentLevel == nextLevel)
            {
            }
            else
            {
                //nothing to do
                // other list encountered
                int indexOfNext = findListElement(nextNumId,nextLevel);
                //if the next list element cannot find, then a unknown new list will start
                if (indexOfNext == -1)
                {
                }
                else
                {
                    //nothing to do
                    // else end of list
                    //remove listStyles and sign ends
                    CSList<ListControl> ends = getReverseListTilIndex(indexOfNext + 1);
                    _listStyle.RemoveRange(indexOfNext + 1, _listStyle.size() - indexOfNext - 1);
                    return ends;
                } 
            } 
        } 
        return new CSList<ListControl>();
    }

    private String uniqueString(int num) throws Exception {
        if (num == 0)
            return "";
         
        String val = String.valueOf((num - 1));
        String ret = "";
        for (char c : val.toCharArray())
        {
            ret += Convert.ToChar(Convert.ToInt32(c) + Convert.ToInt32('A') - Convert.ToInt32('0'));
        }
        return ret;
    }

    private CSList<ListControl> getReverseListTilIndex(int index) throws Exception {
        CSList<ListControl> revList = new CSList<ListControl>();
        if (_listStyle.size() == 0)
            return revList;
         
        int current = _listStyle.size() - 1;
        while (current >= index)
        {
            revList.add(new ListControl(_listStyle.get(current).Style,NumberedCounterTypeEnum.None,null));
            current--;
        }
        return revList;
    }

    private int findListElement(int numId, int level) throws Exception {
        if (_listStyle.size() == 0)
            return -1;
         
        int foundIndex = _listStyle.size() - 1;
        while (foundIndex >= 0)
        {
            if (_listStyle.get(foundIndex).NumId == numId && _listStyle.get(foundIndex).Level == level)
            {
                break;
            }
             
            foundIndex--;
        }
        return foundIndex;
    }

    public ListTypeEnum getNumberingStyle(Integer numbering, Integer level) throws Exception {
        if (numbering != null && level != null)
        {
            XmlNode node = null;
            node = _numberingDoc.getDocumentElement().SelectSingleNode(String.format(StringSupport.CSFmtStrToJFmtStr("/w:numbering/w:num[@w:numId='{0}']/w:abstractNumId"),numbering), _xmlnsMgr);
            // there are some cases when w:numId is bad in document.xml and references to an unknown numId in numbering.xml
            if (node == null)
            {
                return ListTypeEnum.None;
            }
             
            int abstractNumbering = Integer.valueOf(node.getAttributes().get("w:val").getValue());
            XmlNode absNode = _numberingDoc.getDocumentElement().SelectSingleNode(String.format(StringSupport.CSFmtStrToJFmtStr("/w:numbering/w:abstractNum[@w:abstractNumId='{0}']/w:lvl[@w:ilvl='{1}']/w:numFmt"),abstractNumbering,level), _xmlnsMgr);
            if (!StringSupport.equals(absNode.getAttributes().get("w:val").getValue(), "bullet"))
            {
                return ListTypeEnum.Numbered;
            }
             
            return ListTypeEnum.Bulleted;
        }
         
        return ListTypeEnum.None;
    }

}


