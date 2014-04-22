//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:00 AM
//

package docx2tex.UI;

import CS2JNet.System.StringSupport;
import java.util.Locale;

public class NullStringBindingConverter  extends IValueConverter 
{
    public Object convert(Object value, Class targetType, Object parameter, Locale culture) throws Exception {
        if (value != null && value instanceof String && StringSupport.equals((String)value, ""))
        {
            return null;
        }
         
        return value;
    }

    public Object convertBack(Object value, Class targetType, Object parameter, Locale culture) throws Exception {
        if (value != null && value instanceof String && StringSupport.equals((String)value, ""))
        {
            return null;
        }
         
        return value;
    }

}


