//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:36 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.CRNData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CRN;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.XCT;

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
* This class contains several information about the XCT BIFF Record
*/
public class XCTData   
{
    private LinkedList<CRNData> crnDataList = new LinkedList<CRNData>();
    public LinkedList<CRNData> getCRNDataList() throws Exception {
        return this.crnDataList;
    }

    private UInt16 itab = new UInt16();
    public UInt16 getITab() throws Exception {
        return this.itab;
    }

    public XCTData(XCT xct) throws Exception {
        this.itab = xct.itab;
        this.crnDataList = new LinkedList<CRNData>();
    }

    public void addCRN(CRN crn) throws Exception {
        CRNData crndata = new CRNData(crn);
        this.crnDataList.AddLast(crndata);
    }

}


