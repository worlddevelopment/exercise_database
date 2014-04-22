//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:24 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType.JoinStyle;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;

public class VMLShapeTypeMapping  extends PropertiesMapping implements IMapping<ShapeType>
{
    private XmlElement _lock = null;
    public VMLShapeTypeMapping(XmlWriter writer) throws Exception {
        super(writer);
        _lock = _nodeFactory.createElement("o", "lock", OpenXmlNamespaces.Office);
        appendValueAttribute(_lock,"v","ext","edit",OpenXmlNamespaces.VectorML);
    }

    public void apply(ShapeType shapeType) throws Exception {
        if (!(shapeType instanceof DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.OvalType))
        {
            _writer.WriteStartElement("v", "shapetype", OpenXmlNamespaces.VectorML);
            //id
            _writer.writeAttributeString("id", generateTypeId(shapeType));
            //coordinate system
            _writer.writeAttributeString("coordsize", "21600,21600");
            //shape type code
            _writer.WriteAttributeString("o", "spt", OpenXmlNamespaces.Office, String.valueOf(shapeType.getTypeCode()));
            //adj
            if (shapeType.AdjustmentValues != null)
            {
                _writer.writeAttributeString("adj", shapeType.AdjustmentValues);
            }
             
            //The path
            if (shapeType.Path != null)
            {
                _writer.writeAttributeString("path", shapeType.Path);
            }
             
            // always write this attribute
            // if this causes regression bugs, remove it.
            // this was inserted due to a bug in Word 2007 (sf.net item: 2256373)
            if (shapeType.PreferRelative)
            {
                _writer.WriteAttributeString("o", "preferrelative", OpenXmlNamespaces.Office, "t");
            }
             
            //Default fill / stroke
            if (shapeType.Filled == false)
            {
                _writer.writeAttributeString("filled", "f");
            }
             
            if (shapeType.Stroked == false)
            {
                _writer.writeAttributeString("stroked", "f");
            }
             
            //stroke
            if (shapeType.Joins != JoinStyle.none)
            {
                _writer.WriteStartElement("v", "stroke", OpenXmlNamespaces.VectorML);
                _writer.writeAttributeString("joinstyle", shapeType.Joins.toString());
                _writer.writeEndElement();
            }
             
            //Formulas
            if (shapeType.Formulas != null && shapeType.Formulas.size() > 0)
            {
                _writer.WriteStartElement("v", "formulas", OpenXmlNamespaces.VectorML);
                for (String formula : shapeType.Formulas)
                {
                    _writer.WriteStartElement("v", "f", OpenXmlNamespaces.VectorML);
                    _writer.writeAttributeString("eqn", formula);
                    _writer.writeEndElement();
                }
                _writer.writeEndElement();
            }
             
            //Path
            _writer.WriteStartElement("v", "path", OpenXmlNamespaces.VectorML);
            if (shapeType.ShapeConcentricFill)
            {
                _writer.writeAttributeString("gradientshapeok", "t");
            }
             
            if (shapeType.Limo != null)
            {
                _writer.writeAttributeString("limo", shapeType.Limo);
            }
             
            if (shapeType.TextPath)
            {
                _writer.writeAttributeString("textpathok", "t");
            }
             
            if (shapeType.ConnectorLocations != null)
            {
                _writer.WriteAttributeString("o", "connecttype", OpenXmlNamespaces.Office, "custom");
                _writer.WriteAttributeString("o", "connectlocs", OpenXmlNamespaces.Office, shapeType.ConnectorLocations);
            }
            else if (shapeType.ConnectorType != null)
            {
                _writer.WriteAttributeString("o", "connecttype", OpenXmlNamespaces.Office, shapeType.ConnectorType);
            }
              
            if (shapeType.TextboxRectangle != null)
            {
                _writer.writeAttributeString("textboxrect", shapeType.TextboxRectangle);
            }
             
            if (shapeType.ConnectorAngles != null)
            {
                _writer.WriteAttributeString("o", "connectangles", OpenXmlNamespaces.Office, shapeType.ConnectorAngles);
            }
             
            // always write this attribute
            // if this causes regression bugs, remove it.
            // this was inserted due to a bug in Word 2007 (sf.net item: 2256373)
            if (shapeType.ExtrusionOk == false)
            {
                _writer.WriteAttributeString("o", "extrusionok", OpenXmlNamespaces.Office, "f");
            }
             
            _writer.writeEndElement();
            //Lock
            if (shapeType.Lock.fUsefLockAspectRatio && shapeType.Lock.fLockAspectRatio)
            {
                appendValueAttribute(_lock,null,"aspectratio","t",null);
            }
             
            if (shapeType.Lock.fUsefLockText && shapeType.Lock.fLockText)
            {
                appendValueAttribute(_lock,"v","ext","edit",OpenXmlNamespaces.VectorML);
                appendValueAttribute(_lock,null,"text","t",null);
            }
             
            if (shapeType.LockShapeType)
            {
                appendValueAttribute(_lock,null,"shapetype","t",null);
            }
             
            if (_lock.getAttributes().size() > 1)
            {
                _lock.WriteTo(_writer);
            }
             
            // Textpath
            if (shapeType.TextPath)
            {
                _writer.WriteStartElement("v", "textpath", OpenXmlNamespaces.VectorML);
                _writer.writeAttributeString("on", "t");
                StringBuilder textPathStyle = new StringBuilder();
                if (shapeType.TextKerning)
                {
                    textPathStyle.append("v-text-kern:t;");
                }
                 
                if (textPathStyle.capacity() > 0)
                {
                    _writer.writeAttributeString("style", textPathStyle.toString());
                }
                 
                _writer.writeAttributeString("fitpath", "t");
                _writer.writeEndElement();
            }
             
            //Handles
            if (shapeType.Handles != null && shapeType.Handles.size() > 0)
            {
                _writer.WriteStartElement("v", "handles", OpenXmlNamespaces.VectorML);
                for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType.Handle handle : shapeType.Handles)
                {
                    _writer.WriteStartElement("v", "h", OpenXmlNamespaces.VectorML);
                    if (handle.position != null)
                        _writer.writeAttributeString("position", handle.position);
                     
                    if (handle.switchHandle != null)
                        _writer.writeAttributeString("switch", handle.switchHandle);
                     
                    if (handle.xrange != null)
                        _writer.writeAttributeString("xrange", handle.xrange);
                     
                    if (handle.yrange != null)
                        _writer.writeAttributeString("yrange", handle.yrange);
                     
                    if (handle.polar != null)
                        _writer.writeAttributeString("polar", handle.polar);
                     
                    if (handle.radiusrange != null)
                        _writer.writeAttributeString("radiusrange", handle.radiusrange);
                     
                    _writer.writeEndElement();
                }
                _writer.writeEndElement();
            }
             
            _writer.writeEndElement();
        }
         
        _writer.Flush();
    }

    /**
    * Returns the id of the referenced type
    */
    public static String generateTypeId(ShapeType shapeType) throws Exception {
        StringBuilder type = new StringBuilder();
        type.append("_x0000_t");
        type.append(shapeType.getTypeCode());
        return type.toString();
    }

}


