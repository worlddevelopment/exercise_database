//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.AbstractCellData;

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
* Abstract class which stores some data
*/
public abstract class AbstractCellData   implements IComparable
{
    /**
    * Attributes ///
    * 
    * Row of the Object
    */
    private int row;
    /**
    * Getter Setter from Row
    */
    public int getRow() throws Exception {
        return row;
    }

    public void setRow(int value) throws Exception {
        row = value;
    }

    /**
    * The column of the object
    */
    private int col;
    /**
    * Getter Setter from col
    */
    public int getCol() throws Exception {
        return col;
    }

    public void setCol(int value) throws Exception {
        col = value;
    }

    /**
    * TemplateID from this object
    * References to a template field
    */
    private int templateID;
    /**
    * Getter setter from the templateID attribute
    */
    public int getTemplateID() throws Exception {
        return templateID;
    }

    public void setTemplateID(int value) throws Exception {
        templateID = value;
    }

    /**
    * Constructors ///
    * 
    * Ctor
    */
    public AbstractCellData() throws Exception {
        this(0, 0, 0);
    }

    /**
    * ctor
    * 
    *  @param row Rownumber of the object
    *  @param col Colnumber of the object
    *  @param templateID ID of the objectstyletemplate
    */
    public AbstractCellData(int row, int col, int templateID) throws Exception {
        this.row = row;
        this.col = col;
        this.templateID = templateID;
    }

    /**
    * Abstract Methods ///
    * 
    * Returns a String from the stored Value
    * 
    *  @return
    */
    public abstract String getValue() throws Exception ;

    /**
    * Sets the value
    * 
    *  @param obj
    */
    public abstract void setValue(Object obj) throws Exception ;

    /**
    * Implements the compareble interface
    * 
    *  @param obj 
    *  @return
    */
    int iComparable___CompareTo(Object obj) throws Exception {
        AbstractCellData cell = (AbstractCellData)obj;
        if (this.col > cell.col)
            return (1);
         
        if (this.col < cell.col)
            return (-1);
        else
            return (0); 
    }

}


