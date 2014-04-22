//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:01 AM
//

package docx2tex.UI;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Collections.LCC.IEnumerable;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import docx2tex.Library.Config;
import docx2tex.Library.Data.Docx2TexConfig;
import docx2tex.Library.Data.Infra;
import docx2tex.Library.Data.InputEncInfo;
import docx2tex.Library.Data.LaTeXTags;
import docx2tex.Library.Data.StyleMap;
import docx2tex.Library.StaticConfigHelper;
import docx2tex.Library.StyleEnumerator;
import docx2tex.UI.ConfigurationClassEnum;
import docx2tex.UI.IContentClosable;


/**
* Interaction logic for ConfigEditor.xaml
*/
public class ConfigEditor  extends UserControl 
{

    private Docx2TexConfig _conf;
    private ConfigurationClassEnum _configurationClass = ConfigurationClassEnum.System;
    private String _documentFilePath;
    private IContentClosable _contentClosable;
    private CSList<InputEncInfo> getAllEncodings() throws Exception {
        return docx2tex.Library.Data.InputEnc.Instance.getInputEncs();
    }

    public IEnumerable<InputEncInfo> getAllEncodingsPlusEmpty() throws Exception {

        for (InputEncInfo enc : getAllEncodings())
        {
        }
    }

    public IEnumerable<String> getAllDocumentClassPlusEmpty() throws Exception {



    
    }

    public IEnumerable<String> getAllFontSizePlusEmpty() throws Exception {



    
    }

    public IEnumerable<String> getAllPaperSizePlusEmpty() throws Exception {






    
    }

    public ConfigEditor(ConfigurationClassEnum configClass, String documentFilePath, IContentClosable contentClosable) throws Exception {
        InitializeComponent();
        _configurationClass = configClass;
        _documentFilePath = documentFilePath;
        _contentClosable = contentClosable;
    }

    private void configEditor_Loaded(Object sender, RoutedEventArgs e) throws Exception {
        if (!DesignerProperties.GetIsInDesignMode(this))
        {
            initialize();
        }
         
    }

    private void btnSelectImgMgck_Click(Object sender, RoutedEventArgs e) throws Exception {
        selectImageMagick();
    }

    private void txtLineLength_PreviewTextInput(Object sender, TextCompositionEventArgs e) throws Exception {
        e.Handled = (new CSList<Character>(e.Text.ToCharArray())).Exists(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(c) => {
            return !char.IsDigit(c);
        }" */);
    }

    private void btnCleanPage_Click(Object sender, RoutedEventArgs e) throws Exception {
        if (confirmClick())
        {
            cleanPage(tcConfig.SelectedItem instanceof TabItem ? (TabItem)tcConfig.SelectedItem : (TabItem)null);
        }
         
    }

    private void btnCleanAll_Click(Object sender, RoutedEventArgs e) throws Exception {
        if (confirmClick())
        {
            cleanAllPages();
        }
         
    }

    private void btnRevertPage_Click(Object sender, RoutedEventArgs e) throws Exception {
        if (confirmClick())
        {
            revertPage(tcConfig.SelectedItem instanceof TabItem ? (TabItem)tcConfig.SelectedItem : (TabItem)null);
        }
         
    }

    private void btnRevertAll_Click(Object sender, RoutedEventArgs e) throws Exception {
        if (confirmClick())
        {
            revertAll();
        }
         
    }

    private void btnSave_Click(Object sender, RoutedEventArgs e) throws Exception {
        if (confirmClickSave())
        {
            Config.saveConfig(_conf);
        }
         
    }

    private void btnClose_Click(Object sender, RoutedEventArgs e) throws Exception {
        if (confirmClick())
        {
            _contentClosable.contentClose();
        }
         
    }

    private void btnStyleSelectWord2k7Doc_Click(Object sender, RoutedEventArgs e) throws Exception {
        styleSelectWord2K7Doc();
    }

    private void initialize() throws Exception {
        _conf = loadConfig();
        tcConfig.DataContext = _conf;
    }

    private void selectImageMagick() throws Exception {
        /* [UNSUPPORTED] 'var' as type is unsupported "var" */ ofd = new System.Windows.Forms.OpenFileDialog();
        try
        {
            {
                /* [UNSUPPORTED] 'var' as type is unsupported "var" */ fullName = txtSelectImgMgck.Text;
                String dir = "";
                String fileName = "";
                try
                {
                    dir = Path.GetDirectoryName(fullName);
                    if (StringSupport.isNullOrEmpty(dir))
                    {
                        dir = Path.GetPathRoot(fullName);
                    }
                     
                    fileName = Path.GetFileName(fullName);
                }
                catch (Exception __dummyCatchVar0)
                {
                    dir = "";
                    fileName = "";
                }

                ofd.InitialDirectory = dir;
                ofd.FileName = fileName;
                if (ofd.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                {
                    txtSelectImgMgck.Text = ofd.FileName;
                }
                 
            }
        }
        finally
        {
            if (ofd != null)
                Disposable.mkDisposable(ofd).dispose();
             
        }
    }

    private void cleanPage(TabItem currentTabPage) throws Exception {
        if (currentTabPage != null)
        {
            if (currentTabPage == tabInfra)
            {
                _conf.setInfra(new Infra());
            }
            else if (currentTabPage == tabLtXTags)
            {
                _conf.setLaTeXTags(new LaTeXTags());
            }
            else if (currentTabPage == tabStyleMap)
            {
                _conf.setStyleMap(new StyleMap());
            }
               
            reBind();
        }
         
    }

    private void cleanAllPages() throws Exception {
        _conf.cleanProperties();
        reBind();
    }

    private void revertPage(TabItem currentTabPage) throws Exception {
        Docx2TexConfig conf = loadConfig();
        if (currentTabPage != null)
        {
            if (currentTabPage == tabInfra)
            {
                _conf.setInfra(conf.getInfra());
            }
            else if (currentTabPage == tabLtXTags)
            {
                _conf.setLaTeXTags(conf.getLaTeXTags());
            }
            else if (currentTabPage == tabStyleMap)
            {
                _conf.setStyleMap(conf.getStyleMap());
            }
               
            reBind();
        }
         
    }

    private void revertAll() throws Exception {
        _conf = loadConfig();
        reBind();
    }

    private void reBind() throws Exception {
        tcConfig.DataContext = null;
        tcConfig.DataContext = _conf;
    }

    private Docx2TexConfig loadConfig() throws Exception {
        Docx2TexConfig config = null;
        switch(_configurationClass)
        {
            case System: 
                config = Config.loadSystemConfig();
                txtConfigLevel.Text = "System level configuration";
                break;
            case User: 
                config = Config.loadUserConfig();
                txtConfigLevel.Text = "User level configuration";
                break;
            case Document: 
                StaticConfigHelper.setDocxPath(_documentFilePath);
                config = Config.loadDocumentConfig();
                txtConfigLevel.Text = "Document level configuration";
                txtStyleSelectWord2k7Doc.Text = _documentFilePath;
                enumerateSyles();
                break;
        
        }
        txtConfigLevelInfo.Text = config.getConfigurationFilePath();
        return config;
    }

    private void txtLineLength_TextChanged(Object sender, TextChangedEventArgs e) throws Exception {
        if (String.IsNullOrEmpty((e.Source instanceof TextBox ? (TextBox)e.Source : (TextBox)null).Text))
        {
            _conf.getInfra().setLineLength(null);
        }
         
    }

    private boolean confirmClick() throws Exception {
        return MessageBox.Show("All changes will be lost. Are you sure?", "Confirmation", MessageBoxButton.YesNo) == MessageBoxResult.Yes;
    }

    private boolean confirmClickSave() throws Exception {
        return MessageBox.Show("All modifications will be persisted. Are you sure?", "Confirmation", MessageBoxButton.YesNo) == MessageBoxResult.Yes;
    }

    private void styleSelectWord2K7Doc() throws Exception {
        /* [UNSUPPORTED] 'var' as type is unsupported "var" */ ofd = new System.Windows.Forms.OpenFileDialog();
        try
        {
            {
                ofd.Multiselect = false;
                ofd.CheckFileExists = true;
                ofd.CheckPathExists = true;
                ofd.DereferenceLinks = true;
                ofd.Filter = "Word 2007 documents (*.docx;*.docm)|*.docx;*.docm";
                if (ofd.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                {
                    txtStyleSelectWord2k7Doc.Text = ofd.FileName;
                    enumerateSyles();
                }
                 
            }
        }
        finally
        {
            if (ofd != null)
                Disposable.mkDisposable(ofd).dispose();
             
        }
    }

    private void enumerateSyles() throws Exception {
        /* [UNSUPPORTED] 'var' as type is unsupported "var" */ styles = StyleEnumerator.Enumerate(txtStyleSelectWord2k7Doc.Text);
        lbStyles.Items.Clear();
        styles.ForEach(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(s) => {
            return lbStyles.Items.Add(s);
        }" */);
    }

    private void lbStyles_MouseDown(Object sender, MouseButtonEventArgs e) throws Exception {
        ListBox lb = e.Source instanceof ListBox ? (ListBox)e.Source : (ListBox)null;
        String data = GetObjectDataFromPoint(lb, e.GetPosition(lb));
        if (data != null)
        {
            DragDrop.DoDragDrop(lb, data, DragDropEffects.Copy);
        }
         
    }

    private String getObjectDataFromPoint(ListBox lb, Point point) throws Exception {
        UIElement element = lb.InputHitTest(point) instanceof UIElement ? (UIElement)lb.InputHitTest(point) : (UIElement)null;
        if (element != null)
        {
            Object data = DependencyProperty.UnsetValue;
            while (data == DependencyProperty.UnsetValue)
            {
                data = lb.ItemContainerGenerator.ItemFromContainer(element);
                if (data == DependencyProperty.UnsetValue)
                {
                    element = VisualTreeHelper.GetParent(element) instanceof UIElement ? (UIElement)VisualTreeHelper.GetParent(element) : (UIElement)null;
                }
                 
                if (element == lb)
                    return null;
                 
            }
            if (data != DependencyProperty.UnsetValue)
            {
                return data instanceof String ? (String)data : (String)null;
            }
             
        }
         
        return null;
    }

}


