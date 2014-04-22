//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageContext;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
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
* Abstract class of a Fat in a compound file
* Author: math
*/
abstract public class AbstractFat   
{
    protected CSList<Long> _entries = new CSList<Long>();
    protected long _currentEntry = 0;
    protected StructuredStorageContext _context;
    /**
    * Constructor
    * 
    *  @param context the current context
    */
    protected AbstractFat(StructuredStorageContext context) throws Exception {
        _context = context;
    }

    /**
    * Write a chain to the fat.
    * 
    *  @param entryCount number of entries in the chain
    *  @return
    */
    public long writeChain(long entryCount) throws Exception {
        if (entryCount == 0)
        {
            return SectorId.FREESECT;
        }
         
        long startSector = _currentEntry;
        for (int i = 0;i < entryCount - 1;i++)
        {
            _currentEntry++;
            _entries.add(_currentEntry);
        }
        _currentEntry++;
        _entries.add(SectorId.ENDOFCHAIN);
        return startSector;
    }

    abstract public void write() throws Exception ;

}


