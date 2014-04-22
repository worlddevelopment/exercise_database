//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import java.lang.reflect.Field;

import docx2tex.Library.Data.Docx2TexAutoConfig;
import docx2tex.Library.Data.Docx2TexAutoConfigAttribute;

/**
* Automatic configuration mapper class
*/
abstract public class Docx2TexAutoConfig   
{
    protected Docx2TexAutoConfig() throws Exception {
    }

    /**
    * Constructore that does the job
    * 
    *  @param system 
    *  @param user 
    *  @param document
    */
    protected Docx2TexAutoConfig(Docx2TexAutoConfig system, Docx2TexAutoConfig user, Docx2TexAutoConfig document) throws Exception {
        setAutoConfigProperties(system,user,document);
    }

    /**
    * Property mapper
    * The same properties are read from the system level, the user level, and the document level configuration.
    * User level properties can override system level properties while document level properties are specific
    * only for a single document
    * 
    *  @param system 
    *  @param user 
    *  @param document
    */
    private void setAutoConfigProperties(Docx2TexAutoConfig system, Docx2TexAutoConfig user, Docx2TexAutoConfig document) throws Exception {
        // all public instance properties
        Field[] properties = this.getClass().getFields();//GetProperties(BindingFlags.getInstance() | BindingFlags.getPublic());
        for (Field prop : properties)
        {
            // properties that has the Docx2TexAutoConfigAttribute attribute
            if (prop.getGetCustomAttributes(Docx2TexAutoConfigAttribute.class, false).Length > 0)
            {
                Object systemPropVal = prop.GetValue(system, null);
                Object userPropVal = user != null ? prop.GetValue(user, null) : null;
                Object documentPropVal = document != null ? prop.GetValue(document, null) : null;
                // if document level property is set, then it wins. If not then try user level.
                // If no user level property found then user the system level property.
                Object resultVal = documentPropVal != null ? documentPropVal : (userPropVal != null ? userPropVal : systemPropVal);
                prop.SetValue(this, resultVal, null);
            }
             
        }
    }

}


