//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:53 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.BorderData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.BorderPartData;

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
public class BorderData   
{
    public BorderPartData top;
    public BorderPartData bottom;
    public BorderPartData left;
    public BorderPartData right;
    public BorderPartData diagonal;
    public ushort diagonalValue = new ushort();
    public BorderData() throws Exception {
        this.diagonalValue = 0;
    }

    /**
    * Equals Method
    * 
    *  @param obj 
    *  @return
    */
    public boolean equals(Object obj) {
        try
        {
            // If parameter is null return false.
            if (obj == null)
            {
                return false;
            }
             
            // If parameter cannot be cast to FillDataList return false.
            BorderData bd = obj instanceof BorderData ? (BorderData)obj : (BorderData)null;
            if ((Object)bd == null)
            {
                return false;
            }
             
            if (this.top.equals(bd.top) && this.bottom.equals(bd.bottom) && this.left.equals(bd.left) && this.right.equals(bd.right) && this.diagonal.equals(bd.diagonal) && this.diagonalValue == bd.diagonalValue)
            {
                return true;
            }
            else
            {
                return false;
            } 
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    /**
    * Equals Method
    * 
    *  @param fd 
    *  @return
    */
    public boolean equals(BorderData bd) {
        try
        {
            // If parameter is null return false:
            if ((Object)bd == null)
            {
                return false;
            }
             
            if (this.top.equals(bd.top) && this.bottom.equals(bd.bottom) && this.left.equals(bd.left) && this.right.equals(bd.right) && this.diagonal.equals(bd.diagonal) && this.diagonalValue == bd.diagonalValue)
            {
                return true;
            }
            else
            {
                return false;
            } 
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

}


