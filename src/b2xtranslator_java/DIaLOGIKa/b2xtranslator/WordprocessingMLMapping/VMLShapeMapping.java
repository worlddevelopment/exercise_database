//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:23 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Text.StringBuilderSupport;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileShapeAddress;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Global;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PictureDescriptor;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BitmapBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry.BlipType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ChildAnchor;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientAnchor;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientTextbox;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DiagramBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.FillStyleBooleanProperties;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GeometryBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GeometryTextBooleanProperties;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GroupContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GroupShapeBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GroupShapeRecord;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.LineStyleBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.MetafilePictBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.PathParser;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.PathSegment;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.PathSegment.SegmentType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shape;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.LineType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.RectangleType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.RoundedRectangleType;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.Tools.EmuValue;
import DIaLOGIKa.b2xtranslator.Tools.FixedPointNumber;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor.ByteOrder;
import DIaLOGIKa.b2xtranslator.Tools.TwipsValue;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TextboxMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.VMLShapeMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.VMLShapeTypeMapping;
import java.io.InputStream;
import java.util.Locale;

public class VMLShapeMapping  extends PropertiesMapping implements IMapping<ShapeContainer>
{
    private BlipStoreContainer _blipStore = null;
    private ConversionContext _ctx;
    private FileShapeAddress _fspa;
    private PictureDescriptor _pict;
    private ContentPart _targetPart;
    private XmlElement _fill, _stroke, _shadow, _imagedata, _3dstyle, _textpath;
    private CSList<Byte> pSegmentInfo = new CSList<Byte>();
    private CSList<Byte> pVertices = new CSList<Byte>();
    private StringBuilder _textPathStyle;
    public VMLShapeMapping(XmlWriter writer, ContentPart targetPart, FileShapeAddress fspa, PictureDescriptor pict, ConversionContext ctx) throws Exception {
        super(writer);
        _ctx = ctx;
        _fspa = fspa;
        _pict = pict;
        _targetPart = targetPart;
        _imagedata = _nodeFactory.createElement("v", "imagedata", OpenXmlNamespaces.VectorML);
        _fill = _nodeFactory.createElement("v", "fill", OpenXmlNamespaces.VectorML);
        _stroke = _nodeFactory.createElement("v", "stroke", OpenXmlNamespaces.VectorML);
        _shadow = _nodeFactory.createElement("v", "shadow", OpenXmlNamespaces.VectorML);
        _3dstyle = _nodeFactory.createElement("o", "extrusion", OpenXmlNamespaces.Office);
        _textpath = _nodeFactory.createElement("v", "textpath", OpenXmlNamespaces.VectorML);
        _textPathStyle = new StringBuilder();
        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record recBs = _ctx.getDoc().OfficeArtContent.DrawingGroupData.firstChildWithType();
        if (recBs != null)
        {
            _blipStore = (BlipStoreContainer)recBs;
        }
         
    }

    public void apply(ShapeContainer container) throws Exception {
        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record firstRecord = container.Children.get(0);
        if (firstRecord.getClass() == Shape.class)
        {
            //It's a single shape
            convertShape(container);
        }
        else if (firstRecord.getClass() == GroupShapeRecord.class)
        {
            //Its a group of shapes
            convertGroup((GroupContainer)container.getParentRecord());
        }
          
        _writer.Flush();
    }

    /**
    * Converts a group of shapes
    * 
    *  @param container
    */
    private void convertGroup(GroupContainer container) throws Exception {
        ShapeContainer groupShape = (ShapeContainer)container.Children.get(0);
        GroupShapeRecord gsr = (GroupShapeRecord)groupShape.Children.get(0);
        Shape shape = (Shape)groupShape.Children.get(1);
        CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry> options = groupShape.extractOptions();
        ChildAnchor anchor = groupShape.firstChildWithType();
        _writer.WriteStartElement("v", "group", OpenXmlNamespaces.VectorML);
        _writer.writeAttributeString("id", getShapeId(shape));
        _writer.writeAttributeString("style", buildStyle(shape,anchor,options,container.Index).toString());
        _writer.WriteAttributeString("coordorigin", gsr.rcgBounds.Left + "," + gsr.rcgBounds.Top);
        _writer.WriteAttributeString("coordsize", gsr.rcgBounds.Width + "," + gsr.rcgBounds.Height);
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry entry : options)
        {
            //write wrap coords
            switch(entry.pid)
            {
                case pWrapPolygonVertices: 
                    _writer.writeAttributeString("wrapcoords", getWrapCoords(entry));
                    break;
            
            }
        }
        for (int i = 1;i < container.Children.size();i++)
        {
            //convert the shapes/groups in the group
            if (container.Children.get(i).getClass() == ShapeContainer.class)
            {
                ShapeContainer childShape = (ShapeContainer)container.Children.get(i);
                childShape.Convert(new VMLShapeMapping(_writer,_targetPart,_fspa,null,_ctx));
            }
            else if (container.Children.get(i).getClass() == GroupContainer.class)
            {
                GroupContainer childGroup = (GroupContainer)container.Children.get(i);
                _fspa = null;
                convertGroup(childGroup);
            }
              
        }
        //write wrap
        if (_fspa != null)
        {
            String wrap = getWrapType(_fspa);
            if (!StringSupport.equals(wrap, "through"))
            {
                _writer.WriteStartElement("w10", "wrap", OpenXmlNamespaces.OfficeWord);
                _writer.writeAttributeString("type", wrap);
                _writer.writeEndElement();
            }
             
        }
         
        _writer.writeEndElement();
    }

    /**
    * Converts a single shape
    * 
    *  @param container
    */
    private void convertShape(ShapeContainer container) throws Exception {
        Shape shape = (Shape)container.Children.get(0);
        CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry> options = container.extractOptions();
        ChildAnchor anchor = container.firstChildWithType();
        ClientAnchor clientAnchor = container.firstChildWithType();
        writeStartShapeElement(shape);
        _writer.writeAttributeString("id", getShapeId(shape));
        if (shape.ShapeType != null)
        {
            _writer.writeAttributeString("type", "#" + VMLShapeTypeMapping.GenerateTypeId(shape.ShapeType));
        }
         
        _writer.writeAttributeString("style", buildStyle(shape,anchor,options,container.Index).toString());
        if (shape.ShapeType instanceof LineType)
        {
            //append "from" and  "to" attributes
            _writer.writeAttributeString("from", getCoordinateFrom(anchor));
            _writer.writeAttributeString("to", getCoordinateTo(anchor));
        }
         
        //temporary variables
        EmuValue shadowOffsetX = null;
        EmuValue shadowOffsetY = null;
        EmuValue secondShadowOffsetX = null;
        EmuValue secondShadowOffsetY = null;
        double shadowOriginX = 0;
        double shadowOriginY = 0;
        EmuValue viewPointX = null;
        EmuValue viewPointY = null;
        EmuValue viewPointZ = null;
        Double viewPointOriginX = null;
        Double viewPointOriginY = null;
        String[] adjValues = new String[8];
        int numberAdjValues = 0;
        uint xCoord = 0;
        uint yCoord = 0;
        boolean stroked = true;
        boolean filled = true;
        boolean hasTextbox = false;
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry entry : options)
        {
            switch(entry.pid)
            {
                case geometryBooleans: 
                    //BOOLEANS
                    GeometryBooleans geometryBooleans = new GeometryBooleans(entry.op);
                    if (geometryBooleans.fUsefLineOK && geometryBooleans.fLineOK == false)
                    {
                        stroked = false;
                    }
                     
                    if (!(geometryBooleans.fUsefFillOK && geometryBooleans.fFillOK))
                    {
                        filled = false;
                    }
                     
                    break;
                case FillStyleBooleanProperties: 
                    FillStyleBooleanProperties fillBooleans = new FillStyleBooleanProperties(entry.op);
                    if (fillBooleans.fUsefFilled && fillBooleans.fFilled == false)
                    {
                        filled = false;
                    }
                     
                    break;
                case lineStyleBooleans: 
                    LineStyleBooleans lineBooleans = new LineStyleBooleans(entry.op);
                    if (lineBooleans.fUsefLine && lineBooleans.fLine == false)
                    {
                        stroked = false;
                    }
                     
                    break;
                case protectionBooleans: 
                    ProtectionBooleans protBools = new ProtectionBooleans(entry.op);
                    break;
                case diagramBooleans: 
                    DiagramBooleans diaBools = new DiagramBooleans(entry.op);
                    break;
                case adjustValue: 
                    // GEOMETRY
                    adjValues[0] = (String.valueOf(((int)entry.op)));
                    numberAdjValues++;
                    break;
                case adjust2Value: 
                    adjValues[1] = (String.valueOf(((int)entry.op)));
                    numberAdjValues++;
                    break;
                case adjust3Value: 
                    adjValues[2] = (String.valueOf(((int)entry.op)));
                    numberAdjValues++;
                    break;
                case adjust4Value: 
                    adjValues[3] = (String.valueOf(((int)entry.op)));
                    numberAdjValues++;
                    break;
                case adjust5Value: 
                    adjValues[4] = (String.valueOf(((int)entry.op)));
                    numberAdjValues++;
                    break;
                case adjust6Value: 
                    adjValues[5] = (String.valueOf(((int)entry.op)));
                    numberAdjValues++;
                    break;
                case adjust7Value: 
                    adjValues[6] = (String.valueOf(((int)entry.op)));
                    numberAdjValues++;
                    break;
                case adjust8Value: 
                    adjValues[7] = (String.valueOf(((int)entry.op)));
                    numberAdjValues++;
                    break;
                case pWrapPolygonVertices: 
                    _writer.writeAttributeString("wrapcoords", getWrapCoords(entry));
                    break;
                case geoRight: 
                    xCoord = entry.op;
                    break;
                case geoBottom: 
                    yCoord = entry.op;
                    break;
                case lineColor: 
                    // OUTLINE
                    RGBColor lineColor = new RGBColor((int)entry.op,ByteOrder.RedFirst);
                    _writer.writeAttributeString("strokecolor", "#" + lineColor.SixDigitHexCode);
                    break;
                case lineWidth: 
                    EmuValue lineWidth = new EmuValue((int)entry.op);
                    _writer.writeAttributeString("strokeweight", lineWidth.toString());
                    break;
                case lineDashing: 
                    DIaLOGIKa.b2xtranslator.DocFileFormat.Global.DashStyle dash = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.DashStyle)entry.op;
                    appendValueAttribute(_stroke,null,"dashstyle",dash.toString(),null);
                    break;
                case lineStyle: 
                    appendValueAttribute(_stroke,null,"linestyle",getLineStyle(entry.op),null);
                    break;
                case lineEndArrowhead: 
                    appendValueAttribute(_stroke,null,"endarrow",getArrowStyle(entry.op),null);
                    break;
                case lineEndArrowLength: 
                    appendValueAttribute(_stroke,null,"endarrowlength",getArrowLength(entry.op),null);
                    break;
                case lineEndArrowWidth: 
                    appendValueAttribute(_stroke,null,"endarrowwidth",getArrowWidth(entry.op),null);
                    break;
                case lineStartArrowhead: 
                    appendValueAttribute(_stroke,null,"startarrow",getArrowStyle(entry.op),null);
                    break;
                case lineStartArrowLength: 
                    appendValueAttribute(_stroke,null,"startarrowlength",getArrowLength(entry.op),null);
                    break;
                case lineStartArrowWidth: 
                    appendValueAttribute(_stroke,null,"startarrowwidth",getArrowWidth(entry.op),null);
                    break;
                case fillColor: 
                    // FILL
                    RGBColor fillColor = new RGBColor((int)entry.op,ByteOrder.RedFirst);
                    _writer.writeAttributeString("fillcolor", "#" + fillColor.SixDigitHexCode);
                    break;
                case fillBackColor: 
                    RGBColor fillBackColor = new RGBColor((int)entry.op,ByteOrder.RedFirst);
                    appendValueAttribute(_fill,null,"color2","#" + fillBackColor.SixDigitHexCode,null);
                    break;
                case fillAngle: 
                    FixedPointNumber fllAngl = new FixedPointNumber(entry.op);
                    appendValueAttribute(_fill,null,"angle",String.valueOf(fllAngl.toAngle()),null);
                    break;
                case fillShadeType: 
                    appendValueAttribute(_fill,null,"method",getFillMethod(entry.op),null);
                    break;
                case fillShadeColors: 
                    appendValueAttribute(_fill,null,"colors",getFillColorString(entry.opComplex),null);
                    break;
                case fillFocus: 
                    appendValueAttribute(_fill, null, "focus", entry.op + "%", null);
                    break;
                case fillType: 
                    appendValueAttribute(_fill,null,"type",getFillType(entry.op),null);
                    break;
                case fillBlip: 
                    ImagePart fillBlipPart = null;
                    if (_pict != null && _pict.BlipStoreEntry != null)
                    {
                    }
                    else
                    {
                        // Word Art Texture
                        //fillBlipPart = copyPicture(_pict.BlipStoreEntry);
                        BlipStoreEntry fillBlip = (BlipStoreEntry)_blipStore.Children.get((int)entry.op - 1);
                        fillBlipPart = copyPicture(fillBlip);
                    } 
                    if (fillBlipPart != null)
                    {
                        appendValueAttribute(_fill,"r","id",fillBlipPart.getRelIdToString(),OpenXmlNamespaces.Relationships);
                        appendValueAttribute(_imagedata,"o","title","",OpenXmlNamespaces.Office);
                    }
                     
                    break;
                case fillOpacity: 
                    appendValueAttribute(_fill, null, "opacity", entry.op + "f", null);
                    break;
                case shadowType: 
                    // SHADOW
                    appendValueAttribute(_shadow,null,"type",getShadowType(entry.op),null);
                    break;
                case shadowColor: 
                    RGBColor shadowColor = new RGBColor((int)entry.op,ByteOrder.RedFirst);
                    appendValueAttribute(_shadow,null,"color","#" + shadowColor.SixDigitHexCode,null);
                    break;
                case shadowOffsetX: 
                    shadowOffsetX = new EmuValue((int)entry.op);
                    break;
                case shadowSecondOffsetX: 
                    secondShadowOffsetX = new EmuValue((int)entry.op);
                    break;
                case shadowOffsetY: 
                    shadowOffsetY = new EmuValue((int)entry.op);
                    break;
                case shadowSecondOffsetY: 
                    secondShadowOffsetY = new EmuValue((int)entry.op);
                    break;
                case shadowOriginX: 
                    shadowOriginX = entry.op / Math.pow(2,16);
                    break;
                case shadowOriginY: 
                    shadowOriginY = entry.op / Math.pow(2,16);
                    break;
                case shadowOpacity: 
                    double shadowOpa = (entry.op / Math.pow(2,16));
                    appendValueAttribute(_shadow, null, "opacity", String.Format(Locale.CreateSpecificCulture("EN"), "{0:0.00}", shadowOpa), null);
                    break;
                case Pib: 
                    // PICTURE
                    int index = (int)entry.op - 1;
                    BlipStoreEntry bse = (BlipStoreEntry)_blipStore.Children.get(index);
                    ImagePart part = copyPicture(bse);
                    if (part != null)
                    {
                        appendValueAttribute(_imagedata,"r","id",part.getRelIdToString(),OpenXmlNamespaces.Relationships);
                    }
                     
                    break;
                case pibName: 
                    String name = Encoding.Unicode.GetString(entry.opComplex);
                    name = DIaLOGIKa.b2xtranslator.Tools.Utils.getWritableString(name.substring(0, (0) + (name.length() - 1)));
                    appendValueAttribute(_imagedata,"o","title",name,OpenXmlNamespaces.Office);
                    break;
                case f3D: 
                case ThreeDStyleBooleanProperties: 
                case ThreeDObjectBooleanProperties: 
                    break;
                case c3DExtrudeBackward: 
                    // 3D STYLE
                    EmuValue backwardValue = new EmuValue((int)entry.op);
                    appendValueAttribute(_3dstyle,"backdepth",String.valueOf(backwardValue.toPoints()));
                    break;
                case c3DSkewAngle: 
                    FixedPointNumber skewAngle = new FixedPointNumber(entry.op);
                    appendValueAttribute(_3dstyle,"","skewangle",String.valueOf(skewAngle.toAngle()),"");
                    break;
                case c3DXViewpoint: 
                    viewPointX = new EmuValue(new FixedPointNumber(entry.op).Integral);
                    break;
                case c3DYViewpoint: 
                    viewPointY = new EmuValue(new FixedPointNumber(entry.op).Integral);
                    break;
                case c3DZViewpoint: 
                    viewPointZ = new EmuValue(new FixedPointNumber(entry.op).Integral);
                    break;
                case c3DOriginX: 
                    FixedPointNumber dOriginX = new FixedPointNumber(entry.op);
                    viewPointOriginX = dOriginX.Integral / 65536.0;
                    break;
                case c3DOriginY: 
                    FixedPointNumber dOriginY = new FixedPointNumber(entry.op);
                    break;
                case lTxid: 
                    // TEXTBOX
                    hasTextbox = true;
                    break;
                case gtextUNICODE: 
                    // TEXT PATH (Word Art)
                    String text = Encoding.Unicode.GetString(entry.opComplex);
                    text = text.replace("\n", "");
                    text = text.replace("\0", "");
                    appendValueAttribute(_textpath,"","string",text,"");
                    break;
                case gtextFont: 
                    String font = Encoding.Unicode.GetString(entry.opComplex);
                    font = font.replace("\0", "");
                    appendStyleProperty(_textPathStyle,"font-family","\"" + font + "\"");
                    break;
                case GeometryTextBooleanProperties: 
                    GeometryTextBooleanProperties props = new GeometryTextBooleanProperties(entry.op);
                    if (props.fUsegtextFBestFit && props.gtextFBestFit)
                    {
                        appendValueAttribute(_textpath,"","fitshape","t","");
                    }
                     
                    if (props.fUsegtextFShrinkFit && props.gtextFShrinkFit)
                    {
                        appendValueAttribute(_textpath,"","trim","t","");
                    }
                     
                    if (props.fUsegtextFVertical && props.gtextFVertical)
                    {
                        appendStyleProperty(_textPathStyle,"v-rotate-letters","t");
                    }
                     
                    //_twistDimension = true;
                    if (props.fUsegtextFKern && props.gtextFKern)
                    {
                        appendStyleProperty(_textPathStyle,"v-text-kern","t");
                    }
                     
                    if (props.fUsegtextFItalic && props.gtextFItalic)
                    {
                        appendStyleProperty(_textPathStyle,"font-style","italic");
                    }
                     
                    if (props.fUsegtextFBold && props.gtextFBold)
                    {
                        appendStyleProperty(_textPathStyle,"font-weight","bold");
                    }
                     
                    break;
                case shapePath: 
                    // PATH
                    String path = parsePath(options);
                    if (!StringSupport.isNullOrEmpty(path))
                    {
                        _writer.writeAttributeString("path", path);
                    }
                     
                    break;
            
            }
        }
        if (!filled)
        {
            _writer.writeAttributeString("filled", "f");
        }
         
        if (!stroked)
        {
            _writer.writeAttributeString("stroked", "f");
        }
         
        if (xCoord > 0 && yCoord > 0)
        {
            _writer.WriteAttributeString("coordsize", xCoord + "," + yCoord);
        }
         
        //write adj values
        if (numberAdjValues != 0)
        {
            String adjString = adjValues[0];
            for (int i = 1;i < 8;i++)
            {
                adjString += "," + adjValues[i];
            }
            _writer.writeAttributeString("adj", adjString);
        }
         
        //string.Format("{0:x4}", adjValues);
        //build shadow offsets
        StringBuilder offset = new StringBuilder();
        if (shadowOffsetX != null)
        {
            offset.append(shadowOffsetX.toPoints());
            offset.append("pt");
        }
         
        if (shadowOffsetY != null)
        {
            offset.append(",");
            offset.append(shadowOffsetY.toPoints());
            offset.append("pt");
        }
         
        if (offset.length() > 0)
        {
            appendValueAttribute(_shadow,null,"offset",offset.toString(),null);
        }
         
        StringBuilder offset2 = new StringBuilder();
        if (secondShadowOffsetX != null)
        {
            offset2.append(secondShadowOffsetX.toPoints());
            offset2.append("pt");
        }
         
        if (secondShadowOffsetY != null)
        {
            offset2.append(",");
            offset2.append(secondShadowOffsetY.toPoints());
            offset2.append("pt");
        }
         
        if (offset2.length() > 0)
        {
            appendValueAttribute(_shadow,null,"offset2",offset2.toString(),null);
        }
         
        //build shadow origin
        if (shadowOriginX != 0 && shadowOriginY != 0)
        {
            appendValueAttribute(_shadow, null, "origin", shadowOriginX + "," + shadowOriginY, null);
        }
         
        //write shadow
        if (_shadow.getAttributes().size() > 0)
        {
            appendValueAttribute(_shadow,null,"on","t",null);
            _shadow.WriteTo(_writer);
        }
         
        //write 3d style
        if (_3dstyle.getAttributes().size() > 0)
        {
            appendValueAttribute(_3dstyle,"v","ext","view",OpenXmlNamespaces.VectorML);
            appendValueAttribute(_3dstyle,null,"on","t",null);
            //write the viewpoint
            if (viewPointX != null || viewPointY != null || viewPointZ != null)
            {
                StringBuilder viewPoint = new StringBuilder();
                if (viewPointX != null)
                {
                    viewPoint.append(viewPointX.Value);
                }
                 
                if (viewPointY != null)
                {
                    viewPoint.append(",");
                    viewPoint.append(viewPointY.Value);
                }
                 
                if (viewPointZ != null)
                {
                    viewPoint.append(",");
                    viewPoint.append(viewPointZ.Value);
                }
                 
                appendValueAttribute(_3dstyle,null,"viewpoint",viewPoint.toString(),null);
            }
             
            // write the viewpointorigin
            if (viewPointOriginX != null || viewPointOriginY != null)
            {
                StringBuilder viewPointOrigin = new StringBuilder();
                if (viewPointOriginX != null)
                {
                    viewPointOrigin.append(String.Format(Locale.CreateSpecificCulture("EN"), "{0:0.00}", viewPointOriginX));
                }
                 
                if (viewPointOriginY != null)
                {
                    viewPointOrigin.append(",");
                    viewPointOrigin.append(String.Format(Locale.CreateSpecificCulture("EN"), "{0:0.00}", viewPointOriginY));
                }
                 
                appendValueAttribute(_3dstyle,null,"viewpointorigin",viewPointOrigin.toString(),null);
            }
             
            _3dstyle.WriteTo(_writer);
        }
         
        //write wrap
        if (_fspa != null)
        {
            String wrap = getWrapType(_fspa);
            if (!StringSupport.equals(wrap, "through"))
            {
                _writer.WriteStartElement("w10", "wrap", OpenXmlNamespaces.OfficeWord);
                _writer.writeAttributeString("type", wrap);
                _writer.writeEndElement();
            }
             
        }
         
        //write stroke
        if (_stroke.getAttributes().size() > 0)
        {
            _stroke.WriteTo(_writer);
        }
         
        //write fill
        if (_fill.getAttributes().size() > 0)
        {
            _fill.WriteTo(_writer);
        }
         
        // text path
        if (_textpath.getAttributes().size() > 0)
        {
            appendValueAttribute(_textpath,"","style",_textPathStyle.toString(),"");
            _textpath.WriteTo(_writer);
        }
         
        //write imagedata
        if (_imagedata.getAttributes().size() > 0)
        {
            _imagedata.WriteTo(_writer);
        }
         
        //write the textbox
        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record recTextbox = container.firstChildWithType();
        if (recTextbox != null)
        {
            //Word text box
            //Word appends a ClientTextbox record to the container.
            //This record stores the index of the textbox.
            ClientTextbox box = (ClientTextbox)recTextbox;
            short textboxIndex = System.BitConverter.ToInt16(box.Bytes, 2);
            textboxIndex--;
            _ctx.getDoc().Convert(new TextboxMapping(_ctx, textboxIndex, _targetPart, _writer));
        }
        else if (hasTextbox)
        {
            //Open Office textbox
            //Open Office doesn't append a ClientTextbox record to the container.
            //We don't know how Word gets the relation to the text, but we assume that the first textbox in the document
            //get the index 0, the second textbox gets the index 1 (and so on).
            _ctx.getDoc().Convert(new TextboxMapping(_ctx,_targetPart,_writer));
        }
          
        //write the shape
        _writer.writeEndElement();
        _writer.Flush();
    }

    private String getFillColorString(byte[] p) throws Exception {
        StringBuilder result = new StringBuilder();
        // parse the IMsoArray
        UInt16 nElems = System.BitConverter.ToUInt16(p, 0);
        UInt16 nElemsAlloc = System.BitConverter.ToUInt16(p, 2);
        UInt16 cbElem = System.BitConverter.ToUInt16(p, 4);
        for (int i = 0;i < nElems;i++)
        {
            int pos = 6 + i * cbElem;
            RGBColor color = new RGBColor(System.BitConverter.ToInt32(p, pos), ByteOrder.RedFirst);
            int colorPos = System.BitConverter.ToInt32(p, pos + 4);
            result.append(colorPos);
            result.append("f #");
            result.append(color.SixDigitHexCode);
            result.append(";");
        }
        return result.toString();
    }

    private String parsePath(CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry> options) throws Exception {
        String path = "";
        byte[] pVertices = null;
        byte[] pSegmentInfo = null;
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry e : options)
        {
            if (e.pid == DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.pVertices)
            {
                pVertices = e.opComplex;
            }
            else if (e.pid == DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.pSegmentInfo)
            {
                pSegmentInfo = e.opComplex;
            }
              
        }
        if (pSegmentInfo != null && pVertices != null)
        {
            PathParser parser = new PathParser(pSegmentInfo,pVertices);
            path = buildVmlPath(parser);
        }
         
        return path;
    }

    private String buildVmlPath(PathParser parser) throws Exception {
        // build the VML Path
        StringBuilder VmlPath = new StringBuilder();
        int valuePointer = 0;
        for (PathSegment seg : parser.getSegments())
        {
            try
            {
                switch(seg.getType())
                {
                    case msopathLineTo: 
                        VmlPath.append("l");
                        VmlPath.append(parser.getValues().get(valuePointer).X);
                        VmlPath.append(",");
                        VmlPath.append(parser.getValues().get(valuePointer).Y);
                        valuePointer += 1;
                        break;
                    case msopathCurveTo: 
                        VmlPath.append("c");
                        VmlPath.append(parser.getValues().get(valuePointer).X);
                        VmlPath.append(",");
                        VmlPath.append(parser.getValues().get(valuePointer).Y);
                        VmlPath.append(",");
                        VmlPath.append(parser.getValues().get(valuePointer + 1).X);
                        VmlPath.append(",");
                        VmlPath.append(parser.getValues().get(valuePointer + 1).Y);
                        VmlPath.append(",");
                        VmlPath.append(parser.getValues().get(valuePointer + 2).X);
                        VmlPath.append(",");
                        VmlPath.append(parser.getValues().get(valuePointer + 2).Y);
                        valuePointer += 3;
                        break;
                    case msopathMoveTo: 
                        VmlPath.append("m");
                        VmlPath.append(parser.getValues().get(valuePointer).X);
                        VmlPath.append(",");
                        VmlPath.append(parser.getValues().get(valuePointer).Y);
                        valuePointer += 1;
                        break;
                    case msopathClose: 
                        VmlPath.append("x");
                        break;
                    case msopathEnd: 
                        VmlPath.append("e");
                        break;
                    case msopathEscape: 
                    case msopathClientEscape: 
                    case msopathInvalid: 
                        break;
                
                }
            }
            catch (IndexOutOfRangeException ex)
            {
                break;
            }
        
        }
        //ignore escape segments and invalid segments
        // Sometimes there are more Segments than available Values.
        // Accordingly to the spec this should never happen :)
        // end the path
        if (VmlPath[VmlPath.length() - 1] != 'e')
        {
            VmlPath.append("e");
        }
         
        return VmlPath.toString();
    }

    private String getCoordinateFrom(ChildAnchor anchor) throws Exception {
        StringBuilder from = new StringBuilder();
        if (_fspa != null)
        {
            TwipsValue left = new TwipsValue(_fspa.xaLeft);
            TwipsValue top = new TwipsValue(_fspa.yaTop);
            from.append(left.toPoints().ToString(Locale.GetCultureInfo("en-US")));
            from.append("pt,");
            from.append(top.toPoints().ToString(Locale.GetCultureInfo("en-US")));
            from.append("pt");
        }
        else
        {
            from.append(anchor.rcgBounds.Left);
            from.append("pt,");
            from.append(anchor.rcgBounds.Top);
            from.append("pt");
        } 
        return from.toString();
    }

    private String getCoordinateTo(ChildAnchor anchor) throws Exception {
        StringBuilder from = new StringBuilder();
        if (_fspa != null)
        {
            TwipsValue right = new TwipsValue(_fspa.xaRight);
            TwipsValue bottom = new TwipsValue(_fspa.yaBottom);
            from.append(right.toPoints().ToString(Locale.GetCultureInfo("en-US")));
            from.append("pt,");
            from.append(bottom.toPoints().ToString(Locale.GetCultureInfo("en-US")));
            from.append("pt");
        }
        else
        {
            from.append(anchor.rcgBounds.Right);
            from.append("pt,");
            from.append(anchor.rcgBounds.Bottom);
            from.append("pt");
        } 
        return from.toString();
    }

    private StringBuilder buildStyle(Shape shape, ChildAnchor anchor, CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry> options, int zIndex) throws Exception {
        StringBuilder style = new StringBuilder();
        // Check if some properties are set that cause the dimensions to be twisted
        boolean twistDimensions = false;
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry entry : options)
        {
            if (entry.pid == DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.GeometryTextBooleanProperties)
            {
                GeometryTextBooleanProperties props = new GeometryTextBooleanProperties(entry.op);
                if (props.fUsegtextFVertical && props.gtextFVertical)
                {
                    twistDimensions = true;
                }
                 
            }
             
        }
        //don't append the dimension info to lines,
        // because they have "from" and "to" attributes to decline the dimension
        if (!(shape.ShapeType instanceof LineType))
        {
            if (_fspa != null)
            {
                //this shape is placed directly in the document,
                //so use the FSPA to build the style
                appendDimensionToStyle(style,_fspa,twistDimensions);
            }
            else if (anchor != null)
            {
                //the style is part of a group,
                //so use the anchor
                appendDimensionToStyle(style,anchor,twistDimensions);
            }
            else if (_pict != null)
            {
                // it is some kind of PICT shape (e.g. WordArt)
                appendDimensionToStyle(style,_pict,twistDimensions);
            }
               
        }
         
        if (shape.fFlipH)
        {
            appendStyleProperty(style,"flip","x");
        }
         
        if (shape.fFlipV)
        {
            appendStyleProperty(style,"flip","y");
        }
         
        appendOptionsToStyle(style,options);
        appendStyleProperty(style,"z-index",String.valueOf(zIndex));
        return style;
    }

    private void writeStartShapeElement(Shape shape) throws Exception {
        if (shape.ShapeType instanceof DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.OvalType)
        {
            //OVAL
            _writer.WriteStartElement("v", "oval", OpenXmlNamespaces.VectorML);
        }
        else if (shape.ShapeType instanceof RoundedRectangleType)
        {
            //ROUNDED RECT
            _writer.WriteStartElement("v", "roundrect", OpenXmlNamespaces.VectorML);
        }
        else if (shape.ShapeType instanceof RectangleType)
        {
            //RECT
            _writer.WriteStartElement("v", "rect", OpenXmlNamespaces.VectorML);
        }
        else if (shape.ShapeType instanceof LineType)
        {
            //LINE
            _writer.WriteStartElement("v", "line", OpenXmlNamespaces.VectorML);
        }
        else
        {
            //SHAPE
            if (shape.ShapeType != null)
            {
                shape.ShapeType.Convert(new VMLShapeTypeMapping(_writer));
            }
             
            _writer.WriteStartElement("v", "shape", OpenXmlNamespaces.VectorML);
        }    
    }

    /**
    * Returns the OpenXML fill type of a fill effect
    */
    private String getFillType(uint p) throws Exception {
        long __dummyScrutVar3 = p;
        if (__dummyScrutVar3.equals(0))
        {
            return "solid";
        }
        else if (__dummyScrutVar3.equals(1))
        {
            return "tile";
        }
        else if (__dummyScrutVar3.equals(2))
        {
            return "pattern";
        }
        else if (__dummyScrutVar3.equals(3))
        {
            return "frame";
        }
        else if (__dummyScrutVar3.equals(4))
        {
            return "gradient";
        }
        else if (__dummyScrutVar3.equals(5))
        {
            return "gradientRadial";
        }
        else if (__dummyScrutVar3.equals(6))
        {
            return "gradientRadial";
        }
        else if (__dummyScrutVar3.equals(7))
        {
            return "gradient";
        }
        else if (__dummyScrutVar3.equals(9))
        {
            return "solid";
        }
        else
        {
            return "solid";
        }         
    }

    private String getShadowType(uint p) throws Exception {
        long __dummyScrutVar4 = p;
        if (__dummyScrutVar4.equals(0))
        {
            return "single";
        }
        else if (__dummyScrutVar4.equals(1))
        {
            return "double";
        }
        else if (__dummyScrutVar4.equals(2))
        {
            return "perspective";
        }
        else if (__dummyScrutVar4.equals(3))
        {
            return "shaperelative";
        }
        else if (__dummyScrutVar4.equals(4))
        {
            return "drawingrelative";
        }
        else if (__dummyScrutVar4.equals(5))
        {
            return "emboss";
        }
        else
        {
            return "single";
        }      
    }

    private String getLineStyle(uint p) throws Exception {
        long __dummyScrutVar5 = p;
        if (__dummyScrutVar5.equals(0))
        {
            return "single";
        }
        else if (__dummyScrutVar5.equals(1))
        {
            return "thinThin";
        }
        else if (__dummyScrutVar5.equals(2))
        {
            return "thinThick";
        }
        else if (__dummyScrutVar5.equals(3))
        {
            return "thickThin";
        }
        else if (__dummyScrutVar5.equals(4))
        {
            return "thickBetweenThin";
        }
        else
        {
            return "single";
        }     
    }

    private String getFillMethod(uint p) throws Exception {
        short val = (short)((p & 0xFFFF0000) >> 28);
        short __dummyScrutVar6 = val;
        if (__dummyScrutVar6.equals(0))
        {
            return "none";
        }
        else if (__dummyScrutVar6.equals(1))
        {
            return "any";
        }
        else if (__dummyScrutVar6.equals(2))
        {
            return "linear";
        }
        else if (__dummyScrutVar6.equals(4))
        {
            return "linear sigma";
        }
        else
        {
            return "any";
        }    
    }

    /**
    * Returns the OpenXML wrap type of the shape
    * 
    *  @param fspa 
    *  @return
    */
    private String getWrapType(FileShapeAddress fspa) throws Exception {
        // spec values
        // 0 = like 2 but doesn't equire absolute object
        // 1 = no text next to shape
        // 2 = wrap around absolute object
        // 3 = wrap as if no object present
        // 4 = wrap tightly areound object
        // 5 = wrap tightly but allow holes
        UInt16 __dummyScrutVar7 = fspa.wr;
        if (__dummyScrutVar7.equals(0) || __dummyScrutVar7.equals(2))
        {
            return "square";
        }
        else if (__dummyScrutVar7.equals(1))
        {
            return "topAndBottom";
        }
        else if (__dummyScrutVar7.equals(3))
        {
            return "through";
        }
        else if (__dummyScrutVar7.equals(4) || __dummyScrutVar7.equals(5))
        {
            return "tight";
        }
        else
        {
            return "none";
        }    
    }

    private String getArrowWidth(uint op) throws Exception {
        long __dummyScrutVar8 = op;
        //msolineNarrowArrow
        if (__dummyScrutVar8.equals(1))
        {
            return "medium";
        }
        else //msolineMediumWidthArrow
        if (__dummyScrutVar8.equals(2))
        {
            return "wide";
        }
        else
        {
            return "narrow";
        }  
    }

    //msolineWideArrow
    private String getArrowLength(uint op) throws Exception {
        long __dummyScrutVar9 = op;
        //msolineShortArrow
        if (__dummyScrutVar9.equals(1))
        {
            return "medium";
        }
        else //msolineMediumLengthArrow
        if (__dummyScrutVar9.equals(2))
        {
            return "long";
        }
        else
        {
            return "short";
        }  
    }

    //msolineLongArrow
    private String getArrowStyle(uint op) throws Exception {
        long __dummyScrutVar10 = op;
        //msolineNoEnd
        if (__dummyScrutVar10.equals(1))
        {
            return "block";
        }
        else //msolineArrowEnd
        if (__dummyScrutVar10.equals(2))
        {
            return "classic";
        }
        else //msolineArrowStealthEnd
        if (__dummyScrutVar10.equals(3))
        {
            return "diamond";
        }
        else //msolineArrowDiamondEnd
        if (__dummyScrutVar10.equals(4))
        {
            return "oval";
        }
        else //msolineArrowOvalEnd
        if (__dummyScrutVar10.equals(5))
        {
            return "open";
        }
        else
        {
            return "none";
        }     
    }

    //msolineArrowOpenEnd
    /**
    * Build the VML wrapcoords string for a given pWrapPolygonVertices
    * 
    *  @param pWrapPolygonVertices 
    *  @return
    */
    private String getWrapCoords(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry pWrapPolygonVertices) throws Exception {
        BinaryReader r = new BinaryReader(new MemoryStream(pWrapPolygonVertices.opComplex));
        CSList<Integer> pVertices = new CSList<Integer>();
        //skip first 6 bytes (header???)
        r.ReadBytes(6);
        while (r.BaseStream.Position < r.BaseStream.Length)
        {
            //read the Int32 coordinates
            pVertices.Add(r.ReadInt32());
        }
        //build the string
        StringBuilder coords = new StringBuilder();
        for (int coord : pVertices)
        {
            coords.append(coord);
            coords.append(" ");
        }
        return StringSupport.Trim(coords.toString());
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
        //throw new MappingException("Cannot convert picture of type " + bse.btWin32);
        if (imgPart != null)
        {
            InputStream outStream = imgPart.getStream();
            _ctx.getDoc().WordDocumentStream.Seek((long)bse.foDelay, SeekOrigin.Begin);
            BinaryReader reader = new BinaryReader(_ctx.getDoc().WordDocumentStream);
            switch(bse.btWin32)
            {
                case msoblipEMF: 
                case msoblipWMF: 
                    //it's a meta image
                    MetafilePictBlip metaBlip = (MetafilePictBlip)DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(reader);
                    //meta images can be compressed
                    byte[] decompressed = metaBlip.decrompress();
                    outStream.write(decompressed,0,decompressed.length);
                    break;
                case msoblipJPEG: 
                case msoblipCMYKJPEG: 
                case msoblipPNG: 
                case msoblipTIFF: 
                    //it's a bitmap image
                    BitmapBlip bitBlip = (BitmapBlip)DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(reader);
                    outStream.write(bitBlip.m_pvBits,0,bitBlip.m_pvBits.length);
                    break;
            
            }
        }
         
        return imgPart;
    }

    //*******************************************************************
    //                                                     STATIC METHODS
    //*******************************************************************
    private static void appendDimensionToStyle(StringBuilder style, PictureDescriptor pict, boolean twistDimensions) throws Exception {
        double xScaling = pict.mx / 1000.0;
        double yScaling = pict.my / 1000.0;
        TwipsValue width = new TwipsValue(pict.dxaGoal * xScaling);
        TwipsValue height = new TwipsValue(pict.dyaGoal * yScaling);
        if (twistDimensions)
        {
            width = new TwipsValue(pict.dyaGoal * yScaling);
            height = new TwipsValue(pict.dxaGoal * xScaling);
        }
         
        String widthString = Convert.ToString(width.toPoints(), Locale.GetCultureInfo("en-US"));
        String heightString = Convert.ToString(height.toPoints(), Locale.GetCultureInfo("en-US"));
        style.append("width:").append(widthString).append("pt;");
        style.append("height:").append(heightString).append("pt;");
    }

    public static void appendDimensionToStyle(StringBuilder style, FileShapeAddress fspa, boolean twistDimensions) throws Exception {
        //append size and position ...
        appendStyleProperty(style,"position","absolute");
        TwipsValue left = new TwipsValue(fspa.xaLeft);
        TwipsValue top = new TwipsValue(fspa.yaTop);
        TwipsValue width = new TwipsValue(fspa.xaRight - fspa.xaLeft);
        TwipsValue height = new TwipsValue(fspa.yaBottom - fspa.yaTop);
        if (twistDimensions)
        {
            width = new TwipsValue(fspa.yaBottom - fspa.yaTop);
            height = new TwipsValue(fspa.xaRight - fspa.xaLeft);
        }
         
        appendStyleProperty(style, "margin-left", Convert.ToString(left.toPoints(), Locale.GetCultureInfo("en-US")) + "pt");
        appendStyleProperty(style, "margin-top", Convert.ToString(top.toPoints(), Locale.GetCultureInfo("en-US")) + "pt");
        appendStyleProperty(style, "width", Convert.ToString(width.toPoints(), Locale.GetCultureInfo("en-US")) + "pt");
        appendStyleProperty(style, "height", Convert.ToString(height.toPoints(), Locale.GetCultureInfo("en-US")) + "pt");
    }

    public static void appendDimensionToStyle(StringBuilder style, ChildAnchor anchor, boolean twistDimensions) throws Exception {
        //append size and position ...
        appendStyleProperty(style,"position","absolute");
        appendStyleProperty(style,"left",anchor.rcgBounds.Left.toString());
        appendStyleProperty(style,"top",anchor.rcgBounds.Top.toString());
        if (twistDimensions)
        {
            appendStyleProperty(style,"width",anchor.rcgBounds.Height.toString());
            appendStyleProperty(style,"height",anchor.rcgBounds.Width.toString());
        }
        else
        {
            appendStyleProperty(style,"width",anchor.rcgBounds.Width.toString());
            appendStyleProperty(style,"height",anchor.rcgBounds.Height.toString());
        } 
    }

    public static void appendOptionsToStyle(StringBuilder style, CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry> options) throws Exception {
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry entry : options)
        {
            switch(entry.pid)
            {
                case posh: 
                    //POSITIONING
                    appendStyleProperty(style,"mso-position-horizontal",mapHorizontalPosition((DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PositionHorizontal)entry.op));
                    break;
                case posrelh: 
                    appendStyleProperty(style,"mso-position-horizontal-relative",mapHorizontalPositionRelative((DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PositionHorizontalRelative)entry.op));
                    break;
                case posv: 
                    appendStyleProperty(style,"mso-position-vertical",mapVerticalPosition((DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PositionVertical)entry.op));
                    break;
                case posrelv: 
                    appendStyleProperty(style,"mso-position-vertical-relative",mapVerticalPositionRelative((DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PositionVerticalRelative)entry.op));
                    break;
                case groupShapeBooleans: 
                    //BOOLEANS
                    GroupShapeBooleans groupShapeBoolean = new GroupShapeBooleans(entry.op);
                    if (groupShapeBoolean.fUsefBehindDocument && groupShapeBoolean.fBehindDocument)
                    {
                        //The shape is behind the text, so the z-index must be negative.
                        appendStyleProperty(style,"z-index","-1");
                    }
                     
                    break;
                case rotation: 
                    // GEOMETRY
                    appendStyleProperty(style,"rotation",String.valueOf((entry.op / Math.pow(2,16))));
                    break;
                case anchorText: 
                    //TEXTBOX
                    appendStyleProperty(style,"v-text-anchor",getTextboxAnchor(entry.op));
                    break;
                case dxWrapDistLeft: 
                    //WRAP DISTANCE
                    appendStyleProperty(style, "mso-wrap-distance-left", new EmuValue((int)entry.op).toPoints() + "pt");
                    break;
                case dxWrapDistRight: 
                    appendStyleProperty(style, "mso-wrap-distance-right", new EmuValue((int)entry.op).toPoints() + "pt");
                    break;
                case dyWrapDistBottom: 
                    appendStyleProperty(style, "mso-wrap-distance-bottom", new EmuValue((int)entry.op).toPoints() + "pt");
                    break;
                case dyWrapDistTop: 
                    appendStyleProperty(style, "mso-wrap-distance-top", new EmuValue((int)entry.op).toPoints() + "pt");
                    break;
            
            }
        }
    }

    private static void appendStyleProperty(StringBuilder b, String propName, String propValue) throws Exception {
        b.append(propName);
        b.append(":");
        b.append(propValue);
        b.append(";");
    }

    private static String getTextboxAnchor(uint anchor) throws Exception {
        long __dummyScrutVar14 = anchor;
        if (__dummyScrutVar14.equals(0))
        {
            return "top";
        }
        else //msoanchorTop
        if (__dummyScrutVar14.equals(1))
        {
            return "middle";
        }
        else //msoanchorMiddle
        if (__dummyScrutVar14.equals(2))
        {
            return "bottom";
        }
        else //msoanchorBottom
        if (__dummyScrutVar14.equals(3))
        {
            return "top-center";
        }
        else //msoanchorTopCentered
        if (__dummyScrutVar14.equals(4))
        {
            return "middle-center";
        }
        else //msoanchorMiddleCentered
        if (__dummyScrutVar14.equals(5))
        {
            return "bottom-center";
        }
        else //msoanchorBottomCentered
        if (__dummyScrutVar14.equals(6))
        {
            return "top-baseline";
        }
        else //msoanchorTopBaseline
        if (__dummyScrutVar14.equals(7))
        {
            return "bottom-baseline";
        }
        else //msoanchorBottomBaseline
        if (__dummyScrutVar14.equals(8))
        {
            return "top-center-baseline";
        }
        else //msoanchorTopCenteredBaseline
        if (__dummyScrutVar14.equals(9))
        {
            return "bottom-center-baseline";
        }
        else
        {
            return "top";
        }          
    }

    //msoanchorBottomCenteredBaseline
    private static String mapVerticalPosition(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PositionVertical vPos) throws Exception {
        switch(vPos)
        {
            case msopvAbs: 
                return "absolute";
            case msopvTop: 
                return "top";
            case msopvCenter: 
                return "center";
            case msopvBottom: 
                return "bottom";
            case msopvInside: 
                return "inside";
            case msopvOutside: 
                return "outside";
            default: 
                return "absolute";
        
        }
    }

    private static String mapVerticalPositionRelative(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PositionVerticalRelative vRel) throws Exception {
        switch(vRel)
        {
            case msoprvMargin: 
                return "margin";
            case msoprvPage: 
                return "page";
            case msoprvText: 
                return "text";
            case msoprvLine: 
                return "line";
            default: 
                return "margin";
        
        }
    }

    private static String mapHorizontalPosition(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PositionHorizontal hPos) throws Exception {
        switch(hPos)
        {
            case msophAbs: 
                return "absolute";
            case msophLeft: 
                return "left";
            case msophCenter: 
                return "center";
            case msophRight: 
                return "right";
            case msophInside: 
                return "inside";
            case msophOutside: 
                return "outside";
            default: 
                return "absolute";
        
        }
    }

    private static String mapHorizontalPositionRelative(DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PositionHorizontalRelative hRel) throws Exception {
        switch(hRel)
        {
            case msoprhMargin: 
                return "margin";
            case msoprhPage: 
                return "page";
            case msoprhText: 
                return "text";
            case msoprhChar: 
                return "char";
            default: 
                return "margin";
        
        }
    }

    /**
    * Generates a string id for the given shape
    * 
    *  @param shape 
    *  @return
    */
    private static String getShapeId(Shape shape) throws Exception {
        StringBuilder id = new StringBuilder();
        id.append("_x0000_s");
        id.append(shape.spid);
        return id.toString();
    }

}


