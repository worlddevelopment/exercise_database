//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:22 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;

public class VMLShapeTypeMapping  extends AbstractOpenXmlMapping implements IMapping<ShapeType>
{
    public VMLShapeTypeMapping(ConversionContext ctx, XmlWriter writer) throws Exception {
        super(writer);
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
            _writer.WriteAttributeString("o", "preferrelative", OpenXmlNamespaces.Office, "t");
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
            _writer.WriteStartElement("v", "stroke", OpenXmlNamespaces.VectorML);
            _writer.writeAttributeString("joinstyle", shapeType.Joins.toString());
            _writer.writeEndElement();
            //stroke
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
                //f
                _writer.writeEndElement();
            }
             
            //formulas
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
            _writer.WriteAttributeString("o", "extrusionok", OpenXmlNamespaces.Office, "f");
            _writer.writeEndElement();
            //path
            //Lock
            _writer.WriteStartElement("o", "lock", OpenXmlNamespaces.Office);
            _writer.WriteAttributeString("v", "ext", OpenXmlNamespaces.VectorML, "edit");
            _writer.writeAttributeString("aspectratio", "f");
            _writer.writeEndElement();
            //lock
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
                //v
                _writer.writeEndElement();
            }
             
            //handles
            _writer.writeEndElement();
        }
         
    }

    //shapetype
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


