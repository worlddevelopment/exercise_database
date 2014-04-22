//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:26 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.ChartPart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartFormatsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetContentSequence;

/**
* A container class storing information required by the chart mapping classes
*/
public class ChartContext   
{
    public enum ChartLocation
    {
        Embedded,
        Chartsheet
    }
    private ChartPart _chartPart;
    private ChartSheetContentSequence _chartSheetContentSequence;
    private DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext.ChartLocation _location = DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext.ChartLocation.Embedded;
    public ChartContext(ChartPart chartPart, ChartSheetContentSequence chartSheetContentSequence, DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext.ChartLocation location) throws Exception {
        this._chartPart = chartPart;
        this._chartSheetContentSequence = chartSheetContentSequence;
        this._location = location;
    }

    public ChartPart getChartPart() throws Exception {
        return this._chartPart;
    }

    public ChartSheetContentSequence getChartSheetContentSequence() throws Exception {
        return this._chartSheetContentSequence;
    }

    public DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext.ChartLocation getLocation() throws Exception {
        return this._location;
    }

    public ChartFormatsSequence getChartFormatsSequence() throws Exception {
        return this.getChartSheetContentSequence().ChartFormatsSequence;
    }

}


