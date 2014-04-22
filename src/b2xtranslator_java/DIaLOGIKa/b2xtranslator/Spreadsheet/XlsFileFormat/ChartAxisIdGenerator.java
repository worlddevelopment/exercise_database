//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartAxisIdGenerator;

/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
/**
* An internal helper class for generating unique axis ids to be used in OpenXML
*/
public class ChartAxisIdGenerator   
{
    private int _id;
    /**
    * A list containing all axis ids belonging to a chart group.
    */
    private CSList<Integer> _idList = new CSList<Integer>();
    /**
    * This class is a singleton
    */
    private static ChartAxisIdGenerator _instance;
    private ChartAxisIdGenerator() throws Exception {
    }

    public static ChartAxisIdGenerator getInstance() throws Exception {
        if (_instance == null)
        {
            _instance = new ChartAxisIdGenerator();
        }
         
        return _instance;
    }

    public void startNewChartsheetSubstream() throws Exception {
        _id = 0;
        _idList.clear();
    }

    public void startNewAxisGroup() throws Exception {
        _idList.clear();
    }

    public int generateId() throws Exception {
        int newId = _id++;
        _idList.add(newId);
        return newId;
    }

    public int[] getAxisIds() throws Exception {
        int[] retVal = new int[_idList.size()];
        _idList.CopyTo(retVal);
        return retVal;
    }

}


