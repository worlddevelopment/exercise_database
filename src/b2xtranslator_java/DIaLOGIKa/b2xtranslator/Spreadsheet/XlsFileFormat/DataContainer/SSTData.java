//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SSTData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.StringFormatAssignment;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SST;

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
public class SSTData   implements IVisitable
{
    /**
    * Total and unique number of strings in this SST-Biffrecord
    */
    public long cstTotal;
    public long cstUnique;
    /**
    * Two lists to store the shared String Data
    */
    public CSList<String> StringList;
    public CSList<StringFormatAssignment> FormatList;
    /**
    * Ctor
    * 
    *  @param sst The SST BiffRecord
    */
    public SSTData(SST sst) throws Exception {
        this.copySSTData(sst);
    }

    /**
    * copies the different datasources from the SST BiffRecord
    * 
    *  @param sst The SST BiffRecord
    */
    public void copySSTData(SST sst) throws Exception {
        this.StringList = sst.StringList;
        this.FormatList = sst.FormatList;
        this.cstTotal = sst.cstTotal;
        this.cstUnique = sst.cstUnique;
    }

    public CSList<StringFormatAssignment> getFormatingRuns(int stringNumber) throws Exception {
        CSList<StringFormatAssignment> returnList = new CSList<StringFormatAssignment>();
        for (StringFormatAssignment item : this.FormatList)
        {
            if (item.StringNumber == stringNumber)
            {
                returnList.add(item);
            }
             
        }
        return returnList;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<SSTData>)mapping).apply(this);
    }

}


