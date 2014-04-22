//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.DrawingsPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.WorkbookPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetMLContentTypes;

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
public class ChartsheetPart  extends OpenXmlPart 
{
    private DrawingsPart _drawingsPart = null;
    public ChartsheetPart(WorkbookPart parent, int partIndex) throws Exception {
        super(parent, partIndex);
    }

    public String getContentType() throws Exception {
        return SpreadsheetMLContentTypes.Chartsheet;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.Chartsheet;
    }

    public String getTargetName() throws Exception {
        return "sheet" + String.valueOf(this.getPartIndex());
    }

    public String getTargetDirectory() throws Exception {
        return "chartsheets";
    }

    public DrawingsPart getDrawingsPart() throws Exception {
        if (this._drawingsPart == null)
        {
            this._drawingsPart = this.AddPart(new DrawingsPart(this,++((WorkbookPart)this.getParent()).getDrawingsNumber()));
        }
         
        return this._drawingsPart;
    }

}


//this._drawingsPart = ((WorkbookPart)this.Parent).AddDrawingsPart();