//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:11 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.WordprocessingDocument;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.CommandTableMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.CommentsMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.EndnotesMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.FontTableMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.FootnotesMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.GlossaryMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.MacroBinaryMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.MacroDataMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.MainDocumentMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.NumberingMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.SettingsMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.StyleSheetMapping;

public class Converter   
{
    public static OpenXmlPackage.DocumentType detectOutputType(WordDocument doc) throws Exception {
    	OpenXmlPackage.DocumentType returnType = OpenXmlPackage.DocumentType.Document;
        //detect the document type
        if (doc.FIB.fDot)
        {
            //template
            if (doc.CommandTable.MacroDatas != null && doc.CommandTable.MacroDatas.size() > 0)
            {
                //macro enabled template
                returnType = OpenXmlPackage.DocumentType.MacroEnabledTemplate;
            }
            else
            {
                //without macros
                returnType = OpenXmlPackage.DocumentType.Template;
            } 
        }
        else
        {
            //no template
            if (doc.CommandTable.MacroDatas != null && doc.CommandTable.MacroDatas.size() > 0)
            {
                //macro enabled document
                returnType = OpenXmlPackage.DocumentType.MacroEnabledDocument;
            }
            else
            {
                returnType = OpenXmlPackage.DocumentType.Document;
            } 
        } 
        return returnType;
    }

    public static String getConformFilename(String choosenFilename, OpenXmlPackage.DocumentType outType) throws Exception {
        String outExt = ".docx";
        switch(outType)
        {
            case Document: 
                outExt = ".docx";
                break;
            case MacroEnabledDocument: 
                outExt = ".docm";
                break;
            case MacroEnabledTemplate: 
                outExt = ".dotm";
                break;
            case Template: 
                outExt = ".dotx";
                break;
            default: 
                outExt = ".docx";
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

    public static void convert(WordDocument doc, WordprocessingDocument docx) throws Exception {
        ConversionContext context = new ConversionContext(doc);
        IDisposable __newVar0 = docx;
        try
        {
            {
                //Setup the writer
                XmlWriterSettings xws = new XmlWriterSettings();
                xws.OmitXmlDeclaration = false;
                xws.CloseOutput = true;
                xws.Encoding = EncodingSupport.GetEncoder("UTF-8");
                xws.ConformanceLevel = ConformanceLevel.Document;
                //Setup the context
                context.setWriterSettings(xws);
                context.setDocx(docx);
                //convert the macros
                if (docx.getDocumentType() == OpenXmlPackage.DocumentType.MacroEnabledDocument || docx.getDocumentType() == OpenXmlPackage.DocumentType.MacroEnabledTemplate)
                {
                    doc.Convert(new MacroBinaryMapping(context));
                    doc.Convert(new MacroDataMapping(context));
                }
                 
                //convert the command table
                doc.CommandTable.Convert(new CommandTableMapping(context));
                //Write styles.xml
                doc.Styles.Convert(new StyleSheetMapping(context,doc,docx.getMainDocumentPart().getStyleDefinitionsPart()));
                //Write numbering.xml
                doc.ListTable.Convert(new NumberingMapping(context,doc));
                //Write fontTable.xml
                doc.FontTable.Convert(new FontTableMapping(context,docx.getMainDocumentPart().getFontTablePart()));
                //write document.xml and the header and footers
                doc.Convert(new MainDocumentMapping(context,context.getDocx().getMainDocumentPart()));
                //write the footnotes
                doc.Convert(new FootnotesMapping(context));
                //write the endnotes
                doc.Convert(new EndnotesMapping(context));
                //write the comments
                doc.Convert(new CommentsMapping(context));
                //write settings.xml at last because of the rsid list
                doc.DocumentProperties.Convert(new SettingsMapping(context, docx.getMainDocumentPart().getSettingsPart()));
                //convert the glossary subdocument
                if (doc.Glossary != null)
                {
                    doc.Glossary.Convert(new GlossaryMapping(context,context.getDocx().getMainDocumentPart().getGlossaryPart()));
                    doc.Glossary.FontTable.Convert(new FontTableMapping(context,docx.getMainDocumentPart().getGlossaryPart().getFontTablePart()));
                    //doc.Glossary.Styles.Convert(new StyleSheetMapping(context, doc.Glossary, docx.MainDocumentPart.GlossaryPart.StyleDefinitionsPart));
                    //write settings.xml at last because of the rsid list
                    doc.Glossary.DocumentProperties.Convert(new SettingsMapping(context, docx.getMainDocumentPart().getGlossaryPart().getSettingsPart()));
                }
                 
            }
        }
        finally
        {
            if (__newVar0 != null)
                Disposable.mkDisposable(__newVar0).dispose();
             
        }
    }

}


