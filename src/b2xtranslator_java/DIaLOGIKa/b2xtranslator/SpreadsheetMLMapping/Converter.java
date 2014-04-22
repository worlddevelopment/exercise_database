//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:28 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.IO.PathSupport;
import CS2JNet.System.Text.EncodingSupport;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.SpreadsheetDocument;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SupBookData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.XlsDocument;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExternalLinkMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.MacroBinaryMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.SSTMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.StylesMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.WorkbookMapping;

public class Converter   
{
    public static DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType detectOutputType(XlsDocument xls) throws Exception {
        DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType returnType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Document;
        try
        {
            //ToDo: Find better way to detect macro type
            if (xls.Storage.getFullNameOfAllEntries().Contains("\\_VBA_PROJECT_CUR"))
            {
                if (xls.WorkBookData.getTemplate())
                {
                    returnType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.MacroEnabledTemplate;
                }
                else
                {
                    returnType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.MacroEnabledDocument;
                } 
            }
            else
            {
                if (xls.WorkBookData.getTemplate())
                {
                    returnType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Template;
                }
                else
                {
                    returnType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Document;
                } 
            } 
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return returnType;
    }

    public static String getConformFilename(String choosenFilename, DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType outType) throws Exception {
        String outExt = ".xlsx";
        switch(outType)
        {
            case Document: 
                outExt = ".xlsx";
                break;
            case MacroEnabledDocument: 
                outExt = ".xlsm";
                break;
            case MacroEnabledTemplate: 
                outExt = ".xltm";
                break;
            case Template: 
                outExt = ".xltx";
                break;
            default: 
                outExt = ".xlsx";
                break;
        
        }
        String inExt = PathSupport.getExtension(choosenFilename);
        if (inExt != null)
        {
            return choosenFilename.replace(inExt, outExt);
        }
        else
        {
            return choosenFilename + outExt;
        } 
    }

    public static void convert(XlsDocument xls, SpreadsheetDocument spreadsheetDocument) throws Exception {
        //Setup the writer
        XmlWriterSettings xws = new XmlWriterSettings();
        xws.CloseOutput = true;
        xws.Encoding = EncodingSupport.GetEncoder("UTF-8");
        xws.ConformanceLevel = ConformanceLevel.Document;
        ExcelContext xlsContext = new ExcelContext(xls,xws);
        xlsContext.setSpreadDoc(spreadsheetDocument);
        // convert the shared string table
        if (xls.WorkBookData.getSstData() != null)
        {
            xls.WorkBookData.getSstData().Convert(new SSTMapping(xlsContext));
        }
         
        // create the styles.xml
        if (xls.WorkBookData.styleData != null)
        {
            xls.WorkBookData.styleData.Convert(new StylesMapping(xlsContext));
        }
         
        int sbdnumber = 1;
        for (Object __dummyForeachVar0 : xls.WorkBookData.supBookDataList)
        {
            SupBookData sbd = (SupBookData)__dummyForeachVar0;
            if (!sbd.getSelfRef())
            {
                sbd.Number = sbdnumber;
                sbdnumber++;
                sbd.Convert(new ExternalLinkMapping(xlsContext));
            }
             
        }
        xls.WorkBookData.Convert(new WorkbookMapping(xlsContext,spreadsheetDocument.getWorkbookPart()));
        // convert the macros
        if (spreadsheetDocument.getDocumentType() == DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.MacroEnabledDocument || spreadsheetDocument.getDocumentType() == DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.MacroEnabledTemplate)
        {
            xls.Convert(new MacroBinaryMapping(xlsContext));
        }
         
    }

}


