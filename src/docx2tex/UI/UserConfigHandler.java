//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:01 AM
//

package docx2tex.UI;

import CS2JNet.System.Collections.LCC.CSList;
import docx2tex.UI.FromToElement;
import docx2tex.UI.RecentConversionSection;

public class UserConfigHandler   
{
    private static Configuration _config = new Configuration();
    private static RecentConversionSection _recentConvs;
    public static void loadConfiguration() throws Exception {
        _config = NONE.OpenExeConfiguration(ConfigurationUserLevel.PerUserRoamingAndLocal);
        _recentConvs = _config.GetSection("recentConversions") instanceof RecentConversionSection ? (RecentConversionSection)_config.GetSection("recentConversions") : (RecentConversionSection)null;
        if (_recentConvs == null)
        {
            _recentConvs = new RecentConversionSection();
            //trick:
            _recentConvs.SectionInformation.AllowExeDefinition = ConfigurationAllowExeDefinition.MachineToLocalUser;
            _config.Sections.Add("recentConversions", _recentConvs);
        }
         
    }

    public static CSList<FromToElement> getRecentConversions() throws Exception {
        CSList<FromToElement> ftes = new CSList<FromToElement>();
        for (Object __dummyForeachVar0 : _recentConvs.getFromTos())
        {
            FromToElement fte = (FromToElement)__dummyForeachVar0;
            ftes.add(fte);
        }
        return ftes;
    }

    public static void updateRecentConversion(CSList<FromToElement> ftes) throws Exception {
        while (_recentConvs.getFromTos().Count > 0)
        {
            _recentConvs.getFromTos().removeAt(0);
        }
        for (int i = 0;i < ftes.size() && i < 10;i++)
        {
            FromToElement fte = ftes.get(i);
            fte.setOrder(i);
            _recentConvs.getFromTos().add(fte);
        }
    }

    public static void saveConfiguration() throws Exception {
        _config.Save();
    }

}


