//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:01 AM
//

package docx2tex.UI;

import docx2tex.UI.FromToElementCollection;

public class RecentConversionSection  extends ConfigurationSection 
{
    static {
        try
        {
            _propFromTos = new ConfigurationProperty("", FromToElementCollection.class, null, ConfigurationPropertyOptions.IsRequired | ConfigurationPropertyOptions.IsDefaultCollection);
            _properties = new ConfigurationPropertyCollection();
            _properties.Add(_propFromTos);
        }
        catch (Exception __dummyStaticConstructorCatchVar0)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar0);
        }
    
    }

    private static ConfigurationPropertyCollection _properties = new ConfigurationPropertyCollection();
    private static ConfigurationProperty _propFromTos = new ConfigurationProperty();
    public FromToElementCollection getFromTos() throws Exception {
        return (FromToElementCollection)super[_propFromTos];
    }

    protected ConfigurationPropertyCollection getProperties() throws Exception {
        return _properties;
    }

}


