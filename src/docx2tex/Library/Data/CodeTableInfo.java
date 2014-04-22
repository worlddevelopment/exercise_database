//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.MathMode;

public class CodeTableInfo   
{
    public CodeTableInfo(String word, String tex, MathMode mm) throws Exception {
        setWord(word);
        setTeX(tex);
        setMathMode(mm);
    }

    private String __Word;
    public String getWord() {
        return __Word;
    }

    public void setWord(String value) {
        __Word = value;
    }

    private String __TeX;
    public String getTeX() {
        return __TeX;
    }

    public void setTeX(String value) {
        __TeX = value;
    }

    private MathMode __MathMode = MathMode.No;
    public MathMode getMathMode() {
        return __MathMode;
    }

    public void setMathMode(MathMode value) {
        __MathMode = value;
    }

}


