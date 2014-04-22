//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:03 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.IO.PathSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.LCC.IDisposable;
import CS2JNet.System.Text.EncodingSupport;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.PresentationDocument;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PowerpointDocument;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.PresentationPartMapping;

public class Converter   
{
    public static DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType detectOutputType(PowerpointDocument ppt) throws Exception {
        DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType returnType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Document;
        try
        {
            //ToDo: Find better way to detect macro type
            if (ppt.VbaProject != null)
            {
                returnType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.MacroEnabledDocument;
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return returnType;
    }

    public static String getConformFilename(String choosenFilename, DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType outType) throws Exception {
        String outExt = ".pptx";
        switch(outType)
        {
            case Document: 
                outExt = ".pptx";
                break;
            case MacroEnabledDocument: 
                outExt = ".pptm";
                break;
            case MacroEnabledTemplate: 
                outExt = ".potm";
                break;
            case Template: 
                outExt = ".potx";
                break;
            default: 
                outExt = ".pptx";
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

    public static void convert(PowerpointDocument ppt, PresentationDocument pptx) throws Exception {
        IDisposable __newVar0 = pptx;
        try
        {
            {
                // Setup the writer
                XmlWriterSettings xws = new XmlWriterSettings();
                xws.OmitXmlDeclaration = false;
                xws.CloseOutput = true;
                xws.Encoding = EncodingSupport.GetEncoder("UTF-8");
                xws.ConformanceLevel = ConformanceLevel.Document;
                // Setup the context
                ConversionContext context = new ConversionContext(ppt);
                context.setWriterSettings(xws);
                context.setPptx(pptx);
                // Write presentation.xml
                ppt.Convert(new PresentationPartMapping(context));
            }
        }
        finally
        {
            if (__newVar0 != null)
                Disposable.mkDisposable(__newVar0).dispose();
             
        }
    }

}


//AppMapping app = new AppMapping(pptx.AddAppPropertiesPart(), xws);
//app.Apply(null);
//CoreMapping core = new CoreMapping(pptx.AddCoreFilePropertiesPart(), xws);
//core.Apply(null);