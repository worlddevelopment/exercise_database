//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;

import CS2JNet.System.IO.FileMode;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Text.RegularExpressions.GroupCollection;
import CS2JNet.System.Text.RegularExpressions.Match;
import CS2JNet.System.Text.RegularExpressions.RegexOptions;
import docx2tex.Library.Config;
import docx2tex.Library.IStatusInformation;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.regex.Pattern;

public class Imaging   
{
    private ZipPackagePart _docRelPart = new ZipPackagePart();
    private String _documentDirectory;
    private String _latexDirectory;
    private String _inputDocxPath;
    public Imaging(ZipPackagePart docRelPart, String inputDocxPath, String outputLatexPath) throws Exception {
        _docRelPart = docRelPart;
        _inputDocxPath = inputDocxPath;
        _documentDirectory = (new File(inputDocxPath)).getParent();
        _latexDirectory = (new File(outputLatexPath)).getParent();
    }

    public String resolveImage(String imageId, IStatusInformation statusInfo) throws Exception {
        PackageRelationship rs = _docRelPart.GetRelationship(imageId);
        String imageUrl = rs.TargetUri.OriginalString;
        String orginalImagePath = (new File(_latexDirectory, imageUrl)).toString();
        String newImagePath = (new File(_latexDirectory, imageUrl)).toString();
        ZipPackagePart imagePackagePart = (ZipPackagePart)rs.Package.GetPart(new URI("/word/" + imageUrl, UriKind.Relative));
        InputStream contentStream = imagePackagePart.GetStream();
        try
        {
            {
                byte[] content = new byte[contentStream.Length];
                contentStream.read(content,0,(int)contentStream.Length);
                FileStreamSupport fs = new FileStreamSupport(orginalImagePath, FileMode.Create);
                try
                {
                    {
                        BinaryWriter bwImage = new BinaryWriter(fs);
                        try
                        {
                            {
                                bwImage.Write(content, 0, (int)contentStream.Length);
                            }
                        }
                        finally
                        {
                            if (bwImage != null)
                                Disposable.mkDisposable(bwImage).dispose();
                             
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
            if (contentStream != null)
                Disposable.mkDisposable(contentStream).dispose();
             
        }
        convertImageToEPS(orginalImagePath,newImagePath,statusInfo);
        return Path.ChangeExtension(imageUrl, "eps");
            ;
    }

    public String getWidthAndHeightFromStyle(String style) throws Exception {
        try
        {
            Pattern styleRegEx = Pattern.compile("width:(?<Width>.+?);height:(?<Height>.+?)(;|$)", 0 /* RegexOptions.Compiled */);
            Match match = Match.mk(styleRegEx, style);
            if (match.getSuccess())
            {
                return String.format(StringSupport.CSFmtStrToJFmtStr("width={0},height={1}"),GroupCollection.mk(match).get("Width").getValue(),GroupCollection.mk(match).get("Height").getValue());
            }
             
            return "";
        }
        catch (Exception __dummyCatchVar0)
        {
            return "";
        }
    
    }

    public String getWidthAndHeightFromStyle(Integer cx, Integer cy) throws Exception {
        try
        {
            if (cx != null && cy != null)
            {
                String width = String.format(StringSupport.CSFmtStrToJFmtStr("{0:f2}"),(float)cx / 360100).replace(',', '.');
                String height = String.format(StringSupport.CSFmtStrToJFmtStr("{0:f2}"),(float)cy / 360100).replace(',', '.');
                return String.format(StringSupport.CSFmtStrToJFmtStr("width={0}cm,height={1}cm"),width,height);
            }
            else
            {
                return "";
            } 
        }
        catch (Exception __dummyCatchVar1)
        {
            return "";
        }
    
    }

    private static void convertImageToEPS(String orginalImagePath, String newImagePath, IStatusInformation statusInfo) throws Exception {
        String epsImagePath = Path.ChangeExtension(newImagePath, "eps");
        String imageMagickPath = Config.Instance.getInfra().getImageMagickPath();
        if (StringSupport.isNullOrEmpty(imageMagickPath))
        {
            statusInfo.writeLine("ERROR: Unable to read configuration setting of ImageMagick's path");
            return ;
        }
         
        try
        {
            Process proc = Process.Start(imageMagickPath, String.format(StringSupport.CSFmtStrToJFmtStr("\"{0}\" \"{1}\""),orginalImagePath,epsImagePath));
            proc.WaitForExit(60 * 1000);
        }
        catch (Exception __dummyCatchVar2)
        {
            // wait one minute
            statusInfo.writeLine("ERROR: Unable to start ImageMagicK");
        }
    
    }

}


