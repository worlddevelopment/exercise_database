//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:58 AM
//

package docx2tex.Library;

import docx2tex.Library.InstrTextTypeEnum;
import docx2tex.Library.RunInfo;

/**
* Information about a run
*/
public class RunInfo   
{
    public RunInfo() throws Exception {
        setCFC(null);
        setInstrTextType(InstrTextTypeEnum.None);
    }

    public RunInfo(RunInfo other) throws Exception {
        setCFC(other.getCFC());
        setInstrTextType(other.getInstrTextType());
    }

    private String __CFC;
    public String getCFC() {
        return __CFC;
    }

    public void setCFC(String value) {
        __CFC = value;
    }

    private InstrTextTypeEnum __InstrTextType = InstrTextTypeEnum.None;
    public InstrTextTypeEnum getInstrTextType() {
        return __InstrTextType;
    }

    public void setInstrTextType(InstrTextTypeEnum value) {
        __InstrTextType = value;
    }

}


