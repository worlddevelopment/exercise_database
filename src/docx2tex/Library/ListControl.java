//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;

import docx2tex.Library.ListTypeEnum;
import docx2tex.Library.NumberedCounterTypeEnum;

public class ListControl   
{
    public ListControl() {
    }

    public ListControl(ListTypeEnum listType, NumberedCounterTypeEnum numberedCounterType, String numbering) throws Exception {
        this.ListType = listType;
        this.NumberedCounterType = numberedCounterType;
        this.Numbering = numbering;
    }

    public ListTypeEnum ListType = ListTypeEnum.None;
    public NumberedCounterTypeEnum NumberedCounterType = NumberedCounterTypeEnum.None;
    public String Numbering;
}


