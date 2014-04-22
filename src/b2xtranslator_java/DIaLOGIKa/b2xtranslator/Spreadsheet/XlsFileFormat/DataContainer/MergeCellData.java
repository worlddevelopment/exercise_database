//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;

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
* This class is used  to store the data from a mergecell biffrecord
*/
public class MergeCellData   
{
    /**
    * First row from the merge cell
    */
    public UInt16 rwFirst = new UInt16();
    /**
    * Last row from the merge cell
    */
    public UInt16 rwLast = new UInt16();
    /**
    * First column of the merge cell
    */
    public UInt16 colFirst = new UInt16();
    /**
    * Last colum of the merge cell
    */
    public UInt16 colLast = new UInt16();
    /**
    * Ctor
    */
    public MergeCellData() throws Exception {
        this(0, 0, 0, 0);
    }

    /**
    * Ctor
    * 
    *  @param rwFirst First row
    *  @param rwLast Last row
    *  @param colFirst First column
    *  @param colLast Last column
    */
    public MergeCellData(UInt16 rwFirst, UInt16 rwLast, UInt16 colFirst, UInt16 colLast) throws Exception {
        this.rwFirst = rwFirst;
        this.rwLast = rwLast;
        this.colFirst = colFirst;
        this.colLast = colLast;
    }

    /**
    * Converts the classattributes to a string which can be used in the new open xml standard
    * This would be:
    * mergeCell ref="B3:C3"
    * ref is  the from the first cell to the last cell
    * 
    *  @return
    */
    public String getOXMLFormatedData() throws Exception {
        String returnvalue = "";
        returnvalue += ExcelHelperClass.intToABCString(this.colFirst, (this.rwFirst + 1).toString());
        returnvalue += ":";
        returnvalue += ExcelHelperClass.intToABCString(this.colLast, (this.rwLast + 1).toString());
        return returnvalue;
    }

}


