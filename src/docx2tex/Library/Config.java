//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library;

import CS2JNet.System.IO.StreamReader;
import CS2JNet.System.LCC.Disposable;
import docx2tex.Library.Config;
import docx2tex.Library.Data.Docx2TexConfig;
import docx2tex.Library.Data.Infra;
import docx2tex.Library.Data.LaTeXTags;
import docx2tex.Library.Data.StyleMap;
import docx2tex.Library.StaticConfigHelper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
* Global system configuration
*/
final public class Config   
{
    public Config() throws Exception {
        // read system config
        Docx2TexConfig systemConfig = loadSystemConfig();
        // read user config
        Docx2TexConfig userConfig = loadUserConfig();
        // read document config
        Docx2TexConfig documentConfig = loadDocumentConfig();
        // fill system data structures
        setLaTeXTags(new LaTeXTags(systemConfig.getLaTeXTags(),userConfig != null ? userConfig.getLaTeXTags() : null,documentConfig != null ? documentConfig.getLaTeXTags() : null));
        setInfra(new Infra(systemConfig.getInfra(),userConfig != null ? userConfig.getInfra() : null,documentConfig != null ? documentConfig.getInfra() : null));
        setStyleMap(new StyleMap(systemConfig.getStyleMap(),userConfig != null ? userConfig.getStyleMap() : null,documentConfig != null ? documentConfig.getStyleMap() : null));
    }

    public static Docx2TexConfig loadSystemConfig() throws Exception {
        String mainModuleDirPath = Path.GetDirectoryName(Process.GetCurrentProcess().MainModule.FileName);
        return getConfig(mainModuleDirPath,"docx2tex.SystemConfig");
    }

    public static Docx2TexConfig loadUserConfig() throws Exception {
        String addDataPath = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData);
        String userConfigPath = (new File(addDataPath, "docx2tex")).toString();
        return getConfig(userConfigPath,"docx2tex.UserConfig");
    }

    public static Docx2TexConfig loadDocumentConfig() throws Exception {
        if (StaticConfigHelper.getDocxPath() != null)
        {
            String docxDirPath = (new File(StaticConfigHelper.getDocxPath())).getParent();
            String configFileName = Path.ChangeExtension((new File(StaticConfigHelper.getDocxPath())).getName(), ".docx2texConfig");
            return getConfig(docxDirPath,configFileName);
        }
         
        return null;
    }

    public static void saveConfig(Docx2TexConfig docx2TexConfig) throws Exception {
        String configFilePath = docx2TexConfig.getConfigurationFilePath();
        (new File((new File(configFilePath)).getParent())).mkdirs();
        // config serializer
        XmlSerializer docx2texConfigSerialier = new XmlSerializer(Docx2TexConfig.class);
        PrintWriter systemConfigWriter = new PrintWriter(new FileWriter(configFilePath), true);
        try
        {
            {
                docx2texConfigSerialier.Serialize(systemConfigWriter, docx2TexConfig);
            }
        }
        finally
        {
            if (systemConfigWriter != null)
                Disposable.mkDisposable(systemConfigWriter).dispose();
             
        }
    }

    private static Docx2TexConfig getConfig(String moduleDir, String fileName) throws Exception {
        String configPath = (new File(moduleDir, fileName)).toString();
        Docx2TexConfig config = null;
        if ((new File(configPath)).exists())
        {
            // config serializer
            XmlSerializer docx2texConfigSerialier = new XmlSerializer(Docx2TexConfig.class);
            BufferedReader systemConfigReader = new BufferedReader(StreamReader.make(new BufferedInputStream(new FileInputStream(configPath))));
            try
            {
                {
                    config = (Docx2TexConfig)docx2texConfigSerialier.Deserialize(systemConfigReader);
                    config.setConfigurationFilePath(configPath);
                }
            }
            finally
            {
                if (systemConfigReader != null)
                    Disposable.mkDisposable(systemConfigReader).dispose();
                 
            }
        }
         
        if (config == null)
        {
            config = new Docx2TexConfig();
            config.setConfigurationFilePath(configPath);
        }
         
        return config;
    }

    /**
    * Singleton instance (not readonly because it has to be overwritten some times)
    */
    public static Config Instance = new Config();
    /**
    * running infrastructure
    */
    private Infra __Infra;
    public Infra getInfra() {
        return __Infra;
    }

    public void setInfra(Infra value) {
        __Infra = value;
    }

    /**
    * Tags
    */
    private LaTeXTags __LaTeXTags;
    public LaTeXTags getLaTeXTags() {
        return __LaTeXTags;
    }

    public void setLaTeXTags(LaTeXTags value) {
        __LaTeXTags = value;
    }

    /**
    * Style mappings
    */
    private StyleMap __StyleMap;
    public StyleMap getStyleMap() {
        return __StyleMap;
    }

    public void setStyleMap(StyleMap value) {
        __StyleMap = value;
    }

}


