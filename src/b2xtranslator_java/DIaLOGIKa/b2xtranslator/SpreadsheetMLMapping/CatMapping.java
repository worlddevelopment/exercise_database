//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:26 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AiSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AbstractCellContent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI.BraiId;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI.DataSource;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Label;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SIIndex;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SIIndex.SeriesDataType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesDataSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesFormatSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesGroup;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.FormulaInfixMapping;

public class CatMapping  extends AbstractChartMapping implements IMapping<SeriesFormatSequence>
{
    String _parentElement;
    public CatMapping(ExcelContext workbookContext, ChartContext chartContext, String parentElement) throws Exception {
        super(workbookContext, chartContext);
        this._parentElement = parentElement;
    }

    public void apply(SeriesFormatSequence seriesFormatSequence) throws Exception {
        for (AiSequence aiSequence : seriesFormatSequence.AiSequences)
        {
            // find BRAI record for categories
            if (aiSequence.BRAI.braiId == BraiId.SeriesCategory)
            {
                BRAI brai = aiSequence.BRAI;
                // don't create a c:cat node for automatically generated category axis data!
                if (brai.rt != DataSource.Automatic)
                {
                    // c:cat (or c:xVal for scatter and bubble charts)
                    _writer.WriteStartElement(Chart.Prefix, this._parentElement, Chart.Ns);
                    {
                        switch(brai.rt)
                        {
                            case Literal: 
                                // c:strLit
                                _writer.WriteStartElement(Chart.Prefix, Chart.ElStrLit, Chart.Ns);
                                {
                                    convertStringPoints(seriesFormatSequence);
                                }
                                _writer.writeEndElement();
                                break;
                            case Reference: 
                                // c:strRef
                                _writer.WriteStartElement(Chart.Prefix, Chart.ElStrRef, Chart.Ns);
                                {
                                    // c:f
                                    String formula = FormulaInfixMapping.mapFormula(brai.formula.formula,this.getWorkbookContext());
                                    _writer.WriteElementString(Chart.Prefix, Chart.ElF, Chart.Ns, formula);
                                    // c:strCache
                                    _writer.WriteStartElement(Chart.Prefix, Chart.ElStrCache, Chart.Ns);
                                    {
                                        convertStringPoints(seriesFormatSequence);
                                    }
                                    _writer.writeEndElement();
                                }
                                _writer.writeEndElement();
                                break;
                        
                        }
                    }
                    _writer.writeEndElement();
                }
                 
                break;
            }
             
        }
    }

    // c:cat
    private void convertStringPoints(SeriesFormatSequence seriesFormatSequence) throws Exception {
        // find series data
        SeriesDataSequence seriesDataSequence = this.getChartContext().getChartSheetContentSequence().SeriesDataSequence;
        for (SeriesGroup seriesGroup : seriesDataSequence.SeriesGroups)
        {
            if (seriesGroup.SIIndex.numIndex == SeriesDataType.CategoryLabels)
            {
                AbstractCellContent[][] dataMatrix = seriesDataSequence.DataMatrix[(UInt16)seriesGroup.SIIndex.numIndex - 1];
                // TODO: c:formatCode
                long ptCount = 0;
                for (long i = 0;i < dataMatrix.GetLength(1);i++)
                {
                    if (dataMatrix[seriesFormatSequence.order, i] != null)
                    {
                        ptCount++;
                    }
                     
                }
                // c:ptCount
                writeValueElement(Chart.ElPtCount,String.valueOf(ptCount));
                long idx = 0;
                for (long i = 0;i < dataMatrix.GetLength(1);i++)
                {
                    AbstractCellContent cellContent = dataMatrix[seriesFormatSequence.order, i];
                    if (cellContent != null)
                    {
                        if (cellContent instanceof Label)
                        {
                            Label lblInCell = (Label)cellContent;
                            // c:pt
                            _writer.WriteStartElement(Chart.Prefix, Chart.ElPt, Chart.Ns);
                            _writer.writeAttributeString(Chart.AttrIdx, String.valueOf(idx));
                            // c:v
                            _writer.WriteElementString(Chart.Prefix, Chart.ElV, Chart.Ns, lblInCell.st.getValue());
                            _writer.writeEndElement();
                        }
                         
                    }
                     
                    // c:pt
                    idx++;
                }
                break;
            }
             
        }
    }

}


