//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:50 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.DrawingsPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.ChartsheetPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.ExternalLinkPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.SharedStringPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.StylesPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.WorksheetPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.VbaProjectPart;

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
public class WorkbookPart  extends OpenXmlPart 
{
    private int _worksheetNumber;
    private int _chartsheetNumber;
    private int _drawingsNumber;
    private int _externalLinkNumber;
    protected WorksheetPart _workSheetPart;
    protected SharedStringPart _sharedStringPart;
    protected ExternalLinkPart _externalLinkPart;
    protected VbaProjectPart _vbaProjectPart;
    protected StylesPart _stylesPart;
    private String _type;
    public WorkbookPart(OpenXmlPartContainer parent, String contentType) throws Exception {
        super(parent, 0);
        this._worksheetNumber = 1;
        this._chartsheetNumber = 1;
        this._externalLinkNumber = 1;
        this._type = contentType;
    }

    public String getContentType() throws Exception {
        return this._type;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.OfficeDocument;
    }

    /**
    * returns the newly added worksheet part from the new excel document
    * 
    *  @return
    */
    public WorksheetPart addWorksheetPart() throws Exception {
        this._workSheetPart = new WorksheetPart(this,this._worksheetNumber);
        this._worksheetNumber++;
        return this.AddPart(this._workSheetPart);
    }

    public ChartsheetPart addChartsheetPart() throws Exception {
        return this.AddPart(new ChartsheetPart(this, this._chartsheetNumber++));
    }

    public DrawingsPart addDrawingsPart() throws Exception {
        return this.AddPart(new DrawingsPart(this, this._drawingsNumber++));
    }

    /**
    * returns the vba project part that contains the binary macro data
    */
    public VbaProjectPart getVbaProjectPart() throws Exception {
        if (_vbaProjectPart == null)
        {
            _vbaProjectPart = this.AddPart(new VbaProjectPart(this));
        }
         
        return _vbaProjectPart;
    }

    /**
    * return the latest created worksheetpart
    * 
    *  @return
    */
    public WorksheetPart getWorksheetPart() throws Exception {
        return this._workSheetPart;
    }

    /**
    * returns the worksheet part from the new excel document
    * 
    *  @return
    */
    public ExternalLinkPart addExternalLinkPart() throws Exception {
        this._externalLinkPart = new ExternalLinkPart(this,this._externalLinkNumber);
        this._externalLinkNumber++;
        return this.AddPart(this._externalLinkPart);
    }

    /**
    * return the latest created worksheetpart
    * 
    *  @return
    */
    public ExternalLinkPart getExternalLinkPart() throws Exception {
        return this._externalLinkPart;
    }

    public String getTargetName() throws Exception {
        return "workbook";
    }

    public String getTargetDirectory() throws Exception {
        return "xl";
    }

    /**
    * returns the sharedstringtable part from the new excel document
    * 
    *  @return
    */
    public SharedStringPart addSharedStringPart() throws Exception {
        this._sharedStringPart = new SharedStringPart(this);
        return this.AddPart(this._sharedStringPart);
    }

    /**
    * returns the sharedstringtable part from the new excel document
    * 
    *  @return
    */
    public StylesPart addStylesPart() throws Exception {
        this._stylesPart = new StylesPart(this);
        return this.AddPart(this._stylesPart);
    }

    public int getDrawingsNumber() throws Exception {
        return this._drawingsNumber;
    }

    public void setDrawingsNumber(int value) throws Exception {
        this._drawingsNumber = value;
    }

}


