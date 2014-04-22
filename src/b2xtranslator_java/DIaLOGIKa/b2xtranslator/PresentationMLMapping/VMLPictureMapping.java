//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:22 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BitmapBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry.BlipType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.MetafilePictBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.PictureFrameType;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.VmlPart;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.VMLShapeTypeMapping;
import DIaLOGIKa.b2xtranslator.Tools.EmuValue;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor.ByteOrder;
import java.io.InputStream;
import java.util.ArrayList;

public class VMLPictureMapping  extends AbstractOpenXmlMapping 
{
    ContentPart _targetPart;
    private ConversionContext _ctx;
    public VMLPictureMapping(VmlPart vmlPart, XmlWriterSettings xws) throws Exception {
        super(XmlWriter.Create(vmlPart.getStream(), xws));
        _targetPart = vmlPart;
    }

    //public void Apply(BlipStoreEntry bse, Shape shape, ShapeOptions options, Rectangle bounds, ConversionContext ctx, string spid, ref Point size)
    public void apply(CSList<ArrayList> VMLEntriesList, ConversionContext ctx) throws Exception {
        _ctx = ctx;
        BlipStoreEntry bse;
        _writer.WriteStartDocument();
        _writer.writeStartElement("xml");
        _writer.WriteStartElement("o", "shapelayout", OpenXmlNamespaces.Office);
        _writer.WriteAttributeString("v", "ext", OpenXmlNamespaces.VectorML, "edit");
        _writer.WriteStartElement("o", "idmap", OpenXmlNamespaces.Office);
        _writer.WriteAttributeString("v", "ext", OpenXmlNamespaces.VectorML, "edit");
        _writer.writeAttributeString("data", "1079");
        _writer.writeEndElement();
        //idmap
        _writer.writeEndElement();
        //shapelayout
        //v:shapetype
        PictureFrameType type = new PictureFrameType();
        type.Convert(new VMLShapeTypeMapping(_ctx,_writer));
        for (ArrayList VMLEntry : VMLEntriesList)
        {
            bse = (BlipStoreEntry)VMLEntry.get(0);
            DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions options = (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions)VMLEntry.get(2);
            Rectangle bounds = (Rectangle)VMLEntry.get(3);
            String spid = (String)VMLEntry.get(4);
            Point size = (Point)VMLEntry.get(5);
            RefSupport<Point> refVar___0 = new RefSupport<Point>(size);
            ImagePart imgPart = copyPicture(bse,refVar___0);
            size = refVar___0.getValue();
            if (imgPart != null)
            {
                //v:shape
                _writer.WriteStartElement("v", "shape", OpenXmlNamespaces.VectorML);
                _writer.writeAttributeString("id", spid);
                _writer.writeAttributeString("type", "#" + VMLShapeTypeMapping.generateTypeId(type));
                StringBuilder style = new StringBuilder();
                style.append("position:absolute;");
                style.append("left:" + String.valueOf(((new EmuValue(Utils.MasterCoordToEMU(bounds.Left))).toPoints())) + "pt;");
                style.append("top:" + String.valueOf(((new EmuValue(Utils.MasterCoordToEMU(bounds.Top))).toPoints())) + "pt;");
                style.append("width:").append((new EmuValue(Utils.MasterCoordToEMU(bounds.Width))).toPoints()).append("pt;");
                style.append("height:").append((new EmuValue(Utils.MasterCoordToEMU(bounds.Height))).toPoints()).append("pt;");
                _writer.writeAttributeString("style", style.toString());
                for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry entry : options.OptionsByID.values())
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
                    
                    }
                }
                //v:imageData
                _writer.WriteStartElement("v", "imagedata", OpenXmlNamespaces.VectorML);
                _writer.WriteAttributeString("o", "relid", OpenXmlNamespaces.Office, imgPart.getRelIdToString());
                _writer.WriteAttributeString("o", "title", OpenXmlNamespaces.Office, "");
                _writer.writeEndElement();
                //imagedata
                //close v:shape
                _writer.writeEndElement();
            }
             
        }
        _writer.writeEndElement();
        //xml
        _writer.WriteEndDocument();
        _writer.Flush();
    }

    /**
    * Copies the picture from the binary stream to the zip archive
    * and creates the relationships for the image.
    * 
    *  @param pict The PictureDescriptor
    *  @return The created ImagePart
    */
    protected ImagePart copyPicture(BlipStoreEntry bse, RefSupport<Point> size) throws Exception {
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
                case msoblipDIB: 
                case msoblipPICT: 
                case msoblipERROR: 
                case msoblipUNKNOWN: 
                case msoblipLastClient: 
                case msoblipFirstClient: 
                    break;
            
            }
            //throw new Exception("Cannot convert picture of type " + bse.btWin32);
            imgPart.setTargetDirectory("..\\media");
            InputStream outStream = imgPart.getStream();
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record mb = _ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
            //write the blip
            if (mb != null)
            {
                switch(bse.btWin32)
                {
                    case msoblipEMF: 
                    case msoblipWMF: 
                        //it's a meta image
                        MetafilePictBlip metaBlip = (MetafilePictBlip)mb;
                        size.setValue(metaBlip.m_ptSize);
                        //meta images can be compressed
                        byte[] decompressed = metaBlip.decrompress();
                        outStream.write(decompressed,0,decompressed.length);
                        break;
                    case msoblipJPEG: 
                    case msoblipCMYKJPEG: 
                    case msoblipPNG: 
                    case msoblipTIFF: 
                        //it's a bitmap image
                        BitmapBlip bitBlip = (BitmapBlip)mb;
                        outStream.write(bitBlip.m_pvBits,0,bitBlip.m_pvBits.length);
                        break;
                
                }
            }
             
        }
         
        return imgPart;
    }

}


