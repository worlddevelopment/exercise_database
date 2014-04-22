//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:27 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlDocument;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.LdSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pos;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pos.PositionMode;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ShapePropertiesMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.TextBodyMapping;

public class LegendMapping  extends AbstractChartMapping implements IMapping<LdSequence>
{
    public LegendMapping(ExcelContext workbookContext, ChartContext chartContext) throws Exception {
        super(workbookContext, chartContext);
    }

    public void apply(LdSequence ldSequence) throws Exception {
        // c:legend
        _writer.WriteStartElement(Chart.Prefix, "legend", Chart.Ns);
        {
            // c:legendPos
            if (ldSequence.CrtLayout12 != null)
            {
                // created by Excel 2007
                writeValueElement("legendPos",mapLegendPos(ldSequence.CrtLayout12.autolayouttype));
            }
            else
            {
                // older Excel Versions
                writeValueElement("legendPos",mapLegendPos(ldSequence.Pos));
            } 
            // c:legendEntry
            // c:layout
            // c:overlay
            // c:spPr
            if (ldSequence.FrameSequence != null)
            {
                ldSequence.FrameSequence.Convert(new ShapePropertiesMapping(this.getWorkbookContext(),this.getChartContext()));
            }
             
            // c:txPr
            if (ldSequence.TextPropsSequence != null)
            {
                // created by Excel 2007
                XmlDocument xmlTextProps = new XmlDocument();
                if (ldSequence.TextPropsSequence.TextPropsStream != null)
                {
                    if (ldSequence.TextPropsSequence.TextPropsStream.rgb != null && !StringSupport.equals(ldSequence.TextPropsSequence.TextPropsStream.rgb, ""))
                    {
                        xmlTextProps.loadXml(ldSequence.TextPropsSequence.TextPropsStream.rgb);
                    }
                     
                }
                else if (ldSequence.TextPropsSequence.RichTextStream != null)
                {
                    if (ldSequence.TextPropsSequence.RichTextStream.rgb != null && !StringSupport.equals(ldSequence.TextPropsSequence.RichTextStream.rgb, ""))
                    {
                        xmlTextProps.loadXml(ldSequence.TextPropsSequence.RichTextStream.rgb);
                    }
                     
                }
                  
                // NOTE: Don't use WriteTo on the document root because it might try to
                // add an XML declaration to the writer (BANG!).
                // Use it on the top-most element node instead.
                //
                if (xmlTextProps.getDocumentElement() != null)
                {
                    xmlTextProps.getDocumentElement().WriteTo(_writer);
                }
                 
            }
            else
            {
                // TODO: older Excel Versions
                ldSequence.AttachedLabelSequence.Convert(new TextBodyMapping(this.getWorkbookContext(),this.getChartContext()));
            } 
        }
        _writer.writeEndElement();
    }

    // c:legend
    private String mapLegendPos(Pos pos) throws Exception {
        String result = "r";
        if (pos.mdTopLt == PositionMode.MDCHART && pos.mdBotRt == PositionMode.MDPARENT)
        {
            // use x1 and y1 values
            if (pos.x1 < 50)
            {
                // positioned less than 50 from the left border
                result = "l";
            }
            else
            {
                if (pos.y1 < 50)
                {
                    // positioned less than 50 from the top border
                    result = "t";
                }
                else
                {
                    if (pos.y1 > pos.x1)
                    {
                        // positioned at the bottom border
                        result = "b";
                    }
                     
                } 
            } 
        }
         
        return result;
    }

    private String mapLegendPos(DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12.AutoLayoutType autoLayoutType) throws Exception {
        switch(autoLayoutType)
        {
            case Bottom: 
                return "b";
            case TopRightCorner: 
                return "tr";
            case Top: 
                return "t";
            case Right: 
                return "r";
            case Left: 
                return "l";
            default: 
                return "r";
        
        }
    }

}


