//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.AbstractCellData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.AbstractPtg;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
* This class stores the data about cell with a reference to a value in the
* SharedStringTable
*/
public class FormulaCell  extends AbstractCellData 
{
    /**
    * String which stores the index to the sharedstringtable
    */
    private String valueString;
    /**
    * 
    */
    private Stack<AbstractPtg> ptgStack = new Stack<AbstractPtg>();
    public Stack<AbstractPtg> getPtgStack() throws Exception {
        return this.ptgStack;
    }

    public boolean usesArrayRecord = false;
    public boolean isSharedFormula = false;
    public boolean alwaysCalculated = false;
    /**
    * This method is used to get the Value from this cell
    * 
    *  @return
    */
    public String getValue() throws Exception {
        return this.valueString;
    }

    /**
    * This method is used to set the value of the cell
    * 
    *  @param obj
    */
    public void setValue(Object obj) throws Exception {
        if (obj instanceof Stack<AbstractPtg>)
        {
            this.ptgStack = (Stack<AbstractPtg>)obj;
        }
         
    }

    public Object calculatedValue;
}


