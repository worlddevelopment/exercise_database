//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library;

import CS2JNet.System.IO.FileAccess;
import CS2JNet.System.IO.FileMode;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.LCC.Disposable;
import docx2tex.Library.Engine;
import docx2tex.Library.Imaging;
import docx2tex.Library.IStatusInformation;
import docx2tex.Library.Numbering;
import java.io.File;
import java.io.InputStream;
import java.net.URI;

public class Docx2TexWorker   
{
    public boolean process(String inputDocxPath, String outputTexPath, IStatusInformation statusInfo) throws Exception {
        String documentPath = (new File(outputTexPath)).getParent();
        if (documentPath == null)
        {
            documentPath = Path.GetPathRoot(outputTexPath);
        }
         
        ensureMediaPath(documentPath);
        statusInfo.writeCR("Opening document...");
        Package pkg = null;
        try
        {
            pkg = Package.Open(inputDocxPath, FileMode.Open, FileAccess.Read, FileShare.Read);
        }
        catch (Exception ex)
        {
            // this happens mostly when the user leaves the Word file open
            statusInfo.writeLine(ex.getMessage());
            return false;
        }

        ZipPackagePart documentPart = (ZipPackagePart)pkg.GetPart(new URI("/word/document.xml", UriKind.Relative));
        //numbering part may not exist for simple documents
        ZipPackagePart numberingPart = null;
        if (pkg.PartExists(new URI("/word/numbering.xml", UriKind.Relative)))
        {
            numberingPart = (ZipPackagePart)pkg.GetPart(new URI("/word/numbering.xml", UriKind.Relative));
        }
         
        Numbering numbering = new Numbering(numberingPart);
        Imaging imaging = new Imaging(documentPart,inputDocxPath,outputTexPath);
        InputStream documentXmlStream = documentPart.GetStream();
        try
        {
            {
                Engine engine = new Engine(documentXmlStream,numbering,imaging,statusInfo);
                statusInfo.writeLine("Document opened.        ");
                String outputString = engine.process();
                String latexSource = replaceSomeCharacters(outputString);
                Encoding encoding = Encoding.Default;
                InputEncInfo enc = docx2tex.Library.Data.InputEnc.Instance.getCurrentEncoding();
                if (enc != null)
                {
                    encoding = Encoding.GetEncoding(enc.getDotNetEncoding());
                }
                 
                byte[] data = encoding.getBytes(latexSource);
                FileStreamSupport fs = new FileStreamSupport(outputTexPath, FileMode.Create, FileAccess.Write);
                try
                {
                    {
                        BinaryWriter outputTexStream = new BinaryWriter(fs);
                        try
                        {
                            {
                                outputTexStream.Write(data);
                            }
                        }
                        finally
                        {
                            if (outputTexStream != null)
                                Disposable.mkDisposable(outputTexStream).dispose();
                             
                        }
                    }
                }
                finally
                {
                    if (fs != null)
                        Disposable.mkDisposable(fs).dispose();
                     
                }
            }
        }
        finally
        {
            if (documentXmlStream != null)
                Disposable.mkDisposable(documentXmlStream).dispose();
             
        }
        pkg.Close();
        return true;
    }

    private static String replaceSomeCharacters(String latexSource) throws Exception {
        latexSource = latexSource.replace("!!!DOLLARSIGN!!!", "\\$");
        return latexSource;
    }

    private static void ensureMediaPath(String documentPath) throws Exception {
        String mediaPath = (new File(documentPath, "media")).toString();
        if (!(new File(mediaPath)).exists())
        {
            (new File(mediaPath)).mkdirs();
        }
         
    }

}


