//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:21 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PictureDescriptor;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BitmapBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry.BlipType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.MetafilePictBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shape;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.PictureFrameType;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor.ByteOrder;
import DIaLOGIKa.b2xtranslator.Tools.TwipsValue;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.VMLShapeTypeMapping;
import java.io.InputStream;
import java.util.Locale;

public class VMLPictureMapping  extends PropertiesMapping implements IMapping<PictureDescriptor>
{
    ContentPart _targetPart;
    boolean _olePreview;
    private XmlElement _imageData = null;
    public VMLPictureMapping(XmlWriter writer, ContentPart targetPart, boolean olePreview) throws Exception {
        super(writer);
        _targetPart = targetPart;
        _olePreview = olePreview;
        _imageData = _nodeFactory.createElement("v", "imageData", OpenXmlNamespaces.VectorML);
    }

    public void apply(PictureDescriptor pict) throws Exception {
        ImagePart imgPart = copyPicture(pict.BlipStoreEntry);
        if (imgPart != null)
        {
            Shape shape = (Shape)pict.ShapeContainer.Children.get(0);
            CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry> options = pict.ShapeContainer.extractOptions();
            //v:shapetype
            PictureFrameType type = new PictureFrameType();
            type.Convert(new VMLShapeTypeMapping(_writer));
            //v:shape
            _writer.WriteStartElement("v", "shape", OpenXmlNamespaces.VectorML);
            _writer.writeAttributeString("type", "#" + VMLShapeTypeMapping.generateTypeId(type));
            StringBuilder style = new StringBuilder();
            double xScaling = pict.mx / 1000.0;
            double yScaling = pict.my / 1000.0;
            TwipsValue width = new TwipsValue(pict.dxaGoal * xScaling);
            TwipsValue height = new TwipsValue(pict.dyaGoal * yScaling);
            String widthString = Convert.ToString(width.toPoints(), Locale.GetCultureInfo("en-US"));
            String heightString = Convert.ToString(height.toPoints(), Locale.GetCultureInfo("en-US"));
            style.append("width:").append(widthString).append("pt;");
            style.append("height:").append(heightString).append("pt;");
            _writer.writeAttributeString("style", style.toString());
            _writer.writeAttributeString("id", pict.ShapeContainer.hashCode().toString());
            if (_olePreview)
            {
                _writer.WriteAttributeString("o", "ole", OpenXmlNamespaces.Office, "");
            }
             
            for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry entry : options)
            {
                switch(entry.pid)
                {
                    case borderBottomColor: 
                        //BORDERS
                        RGBColor bottomColor = new RGBColor((int)entry.op,ByteOrder.RedFirst);
                        _writer.WriteAttributeString("o", "borderbottomcolor", OpenXmlNamespaces.Office, "#" + bottomColor.SixDigitHexCode);
                        break;
                    case borderLeftColor: 
                        RGBColor leftColor = new RGBColor((int)entry.op,ByteOrder.RedFirst);
                        _writer.WriteAttributeString("o", "borderleftcolor", OpenXmlNamespaces.Office, "#" + leftColor.SixDigitHexCode);
                        break;
                    case borderRightColor: 
                        RGBColor rightColor = new RGBColor((int)entry.op,ByteOrder.RedFirst);
                        _writer.WriteAttributeString("o", "borderrightcolor", OpenXmlNamespaces.Office, "#" + rightColor.SixDigitHexCode);
                        break;
                    case borderTopColor: 
                        RGBColor topColor = new RGBColor((int)entry.op,ByteOrder.RedFirst);
                        _writer.WriteAttributeString("o", "bordertopcolor", OpenXmlNamespaces.Office, "#" + topColor.SixDigitHexCode);
                        break;
                    case cropFromBottom: 
                        //CROPPING
                        //cast to signed integer
                        int cropBottom = (int)entry.op;
                        appendValueAttribute(_imageData, null, "cropbottom", cropBottom + "f", null);
                        break;
                    case cropFromLeft: 
                        //cast to signed integer
                        int cropLeft = (int)entry.op;
                        appendValueAttribute(_imageData, null, "cropleft", cropLeft + "f", null);
                        break;
                    case cropFromRight: 
                        //cast to signed integer
                        int cropRight = (int)entry.op;
                        appendValueAttribute(_imageData, null, "cropright", cropRight + "f", null);
                        break;
                    case cropFromTop: 
                        //cast to signed integer
                        int cropTop = (int)entry.op;
                        appendValueAttribute(_imageData, null, "croptop", cropTop + "f", null);
                        break;
                
                }
            }
            //v:imageData
            appendValueAttribute(_imageData,"r","id",imgPart.getRelIdToString(),OpenXmlNamespaces.Relationships);
            appendValueAttribute(_imageData,"o","title","",OpenXmlNamespaces.Office);
            _imageData.WriteTo(_writer);
            //borders
            writePictureBorder("bordertop",pict.brcTop);
            writePictureBorder("borderleft",pict.brcLeft);
            writePictureBorder("borderbottom",pict.brcBottom);
            writePictureBorder("borderright",pict.brcRight);
            //close v:shape
            _writer.writeEndElement();
        }
         
    }

    /**
    * Writes a border element
    * 
    *  @param name The name of the element
    *  @param brc The BorderCode object
    */
    private void writePictureBorder(String name, BorderCode brc) throws Exception {
        _writer.WriteStartElement("w10", name, OpenXmlNamespaces.OfficeWord);
        _writer.writeAttributeString("type", getBorderType(brc.brcType));
        _writer.writeAttributeString("width", String.valueOf(brc.dptLineWidth));
        _writer.writeEndElement();
    }

    /**
    * Copies the picture from the binary stream to the zip archive
    * and creates the relationships for the image.
    * 
    *  @param pict The PictureDescriptor
    *  @return The created ImagePart
    */
    protected ImagePart copyPicture(BlipStoreEntry bse) throws Exception {
        //create the image part
        ImagePart imgPart = null;
        if (bse != null)
        {
            switch(bse.btWin32)
            {
                case msoblipEMF: 
                    imgPart = _targetPart.addImagePart(DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Emf);
                    break;
                case msoblipWMF: 
                    imgPart = _targetPart.addImagePart(DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Wmf);
                    break;
                case msoblipJPEG: 
                case msoblipCMYKJPEG: 
                    imgPart = _targetPart.addImagePart(DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Jpeg);
                    break;
                case msoblipPNG: 
                    imgPart = _targetPart.addImagePart(DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Png);
                    break;
                case msoblipTIFF: 
                    imgPart = _targetPart.addImagePart(DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Tiff);
                    break;
                case msoblipPICT: 
                case msoblipERROR: 
                case msoblipUNKNOWN: 
                case msoblipLastClient: 
                case msoblipFirstClient: 
                case msoblipDIB: 
                    break;
            
            }
            //throw new MappingException("Cannot convert picture of type " + bse.btWin32);
            if (imgPart != null)
            {
                InputStream outStream = imgPart.getStream();
                //write the blip
                if (bse.Blip != null)
                {
                    switch(bse.btWin32)
                    {
                        case msoblipEMF: 
                        case msoblipWMF: 
                            //it's a meta image
                            MetafilePictBlip metaBlip = (MetafilePictBlip)bse.Blip;
                            //meta images can be compressed
                            byte[] decompressed = metaBlip.decrompress();
                            outStream.write(decompressed,0,decompressed.length);
                            break;
                        case msoblipJPEG: 
                        case msoblipCMYKJPEG: 
                        case msoblipPNG: 
                        case msoblipTIFF: 
                            //it's a bitmap image
                            BitmapBlip bitBlip = (BitmapBlip)bse.Blip;
                            outStream.write(bitBlip.m_pvBits,0,bitBlip.m_pvBits.length);
                            break;
                    
                    }
                }
                 
            }
             
        }
         
        return imgPart;
    }

}


