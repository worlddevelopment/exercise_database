//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.AbstractFat;
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
* Represents the minifat of a structured storage.
* Author: math
*/
public class MiniFat  extends AbstractFat 
{
    // Start sector of the minifat.
    long _miniFatStart = SectorId.FREESECT;
    public long getMiniFatStart() throws Exception {
        return _miniFatStart;
    }

    // Number of sectors in the mini fat.
    long _numMiniFatSectors = 0x0;
    public long getNumMiniFatSectors() throws Exception {
        return _numMiniFatSectors;
    }

    /**
    * Constructor.
    * 
    *  @param context the current context
    */
    public MiniFat(StructuredStorageContext context) throws Exception {
        super(context);
    }

    /**
    * Writes minifat chain to fat and writes the minifat data to the output stream of the current context.
    */
    public void write() throws Exception {
        _numMiniFatSectors = (long)Math.Ceiling((double)(_entries.size() * 4) / (double)_context.getHeader().getSectorSize());
        _miniFatStart = _context.getFat().writeChain(_numMiniFatSectors);
        _context.getTempOutputStream().writeSectors(((byte[]) _context.getInternalBitConverter().getBytes(_entries).toArray()),_context.getHeader().getSectorSize(),SectorId.FREESECT);
    }

}


