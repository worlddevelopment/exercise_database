//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:11 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlAttribute;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DateAndTime;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;

public class DateMapping  extends AbstractOpenXmlMapping implements IMapping<DateAndTime>
{
    XmlElement _parent;
    /**
    * Writes a date attribute to the given writer
    * 
    *  @param writer
    */
    public DateMapping(XmlWriter writer) throws Exception {
        super(writer);
    }

    /**
    * Appends a date attribute to the given Element
    * 
    *  @param parent
    */
    public DateMapping(XmlElement parent) throws Exception {
        super(null);
        _parent = parent;
        _nodeFactory = parent.getOwnerDocument();
    }

    public void apply(DateAndTime dttm) throws Exception {
        StringBuilder date = new StringBuilder();
        date.append(String.format(StringSupport.CSFmtStrToJFmtStr("{0:0000}"),dttm.yr));
        date.append("-");
        date.append(String.format(StringSupport.CSFmtStrToJFmtStr("{0:00}"),dttm.mon));
        date.append("-");
        date.append(String.format(StringSupport.CSFmtStrToJFmtStr("{0:00}"),dttm.dom));
        date.append("T");
        date.append(String.format(StringSupport.CSFmtStrToJFmtStr("{0:00}"),dttm.hr));
        date.append(":");
        date.append(String.format(StringSupport.CSFmtStrToJFmtStr("{0:00}"),dttm.mint));
        date.append(":00Z");
        XmlAttribute xml = _nodeFactory.createAttribute("w","date",OpenXmlNamespaces.WordprocessingML);
        xml.setValue(date.toString());
        //append or write
        if (_writer != null)
        {
            xml.WriteTo(_writer);
        }
        else if (_parent != null)
        {
            _parent.getAttributes().add(xml);
        }
          
    }

}


