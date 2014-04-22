//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:01 AM
//

package docx2tex.UI;

import docx2tex.UI.FromToElement;

public class FromToElementCollection  extends ConfigurationElementCollection 
{
    public ConfigurationElementCollectionType getCollectionType() throws Exception {
        return ConfigurationElementCollectionType.BasicMap;
    }

    protected String getElementName() throws Exception {
        return "fromto";
    }

    protected ConfigurationPropertyCollection getProperties() throws Exception {
        return new ConfigurationPropertyCollection();
    }

    protected ConfigurationElement createNewElement() throws Exception {
        return new FromToElement();
    }

    protected Object getElementKey(ConfigurationElement element) throws Exception {
        return String.valueOf((element instanceof FromToElement ? (FromToElement)element : (FromToElement)null).getOrder());
    }

    public FromToElement get___idx(int index) throws Exception {
        return (FromToElement)super.BaseGet(index);
    }

    public void set___idx(int index, FromToElement value) throws Exception {
        if (super.BaseGet(index) != null)
        {
            super.BaseRemoveAt(index);
        }
         
        super.BaseAdd(index, value);
    }

    public FromToElement get___idx(String key) throws Exception {
        return (FromToElement)super.BaseGet(key);
    }

    public void add(FromToElement item) throws Exception {
        super.BaseAdd(item);
    }

    public void remove(FromToElement item) throws Exception {
        super.BaseRemove(item);
    }

    public void removeAt(int index) throws Exception {
        super.BaseRemoveAt(index);
    }

}


