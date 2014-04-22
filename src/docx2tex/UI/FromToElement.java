//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:01 AM
//

package docx2tex.UI;


public class FromToElement  extends ConfigurationElement 
{
    static {
        try
        {
            _orderName = new ConfigurationProperty("order", int.class, null, ConfigurationPropertyOptions.IsRequired);
            _fromName = new ConfigurationProperty("from", String.class, null, ConfigurationPropertyOptions.IsRequired);
            _toName = new ConfigurationProperty("to", String.class, null, ConfigurationPropertyOptions.IsRequired);
            _properties = new ConfigurationPropertyCollection();
            _properties.Add(_orderName);
            _properties.Add(_fromName);
            _properties.Add(_toName);
        }
        catch (Exception __dummyStaticConstructorCatchVar0)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar0);
        }
    
    }

    private static ConfigurationProperty _orderName = new ConfigurationProperty();
    private static ConfigurationProperty _fromName = new ConfigurationProperty();
    private static ConfigurationProperty _toName = new ConfigurationProperty();
    private static ConfigurationPropertyCollection _properties = new ConfigurationPropertyCollection();
    public int getOrder() throws Exception {
        return (int)super[_orderName];
    }

    public void setOrder(int value) throws Exception {
        super[_orderName] = value;
    }

    public String getFrom() throws Exception {
        return (String)super[_fromName];
    }

    public void setFrom(String value) throws Exception {
        super[_fromName] = value;
    }

    public String getTo() throws Exception {
        return (String)super[_toName];
    }

    public void setTo(String value) throws Exception {
        super[_toName] = value;
    }

    protected ConfigurationPropertyCollection getProperties() throws Exception {
        return _properties;
    }

}


