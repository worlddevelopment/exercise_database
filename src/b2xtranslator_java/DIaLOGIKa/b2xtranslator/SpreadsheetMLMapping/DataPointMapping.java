//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:26 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SsSequence;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ShapePropertiesMapping;

public class DataPointMapping  extends AbstractChartMapping implements IMapping<SsSequence>
{
    int _index;
    public DataPointMapping(ExcelContext workbookContext, ChartContext chartContext, int index) throws Exception {
        super(workbookContext, chartContext);
        _index = index;
    }

    public void apply(SsSequence ssSequence) throws Exception {
        // c:dPt
        _writer.WriteStartElement(Chart.Prefix, "dPt", Chart.Ns);
        {
            // c:bubble3D
            // TODO
            // c:explosion
            // TODO
            // c:idx
            writeValueElement("idx",String.valueOf(_index));
            // c:invertIfNegative
            // TODO
            // c:marker
            // TODO
            // c:pictureOptions
            // TODO
            // c:spPr
            ssSequence.Convert(new ShapePropertiesMapping(this.getWorkbookContext(),this.getChartContext()));
        }
        _writer.writeEndElement();
    }

}


