//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:01 AM
//

package docx2tex.UI;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import docx2tex.UI.ConfigEditor;
import docx2tex.UI.ConfigurationClassEnum;
import docx2tex.UI.Converter;
import docx2tex.UI.FromToElement;
import docx2tex.UI.IContentClosable;
import docx2tex.UI.IRecentConversion;
import docx2tex.UI.UserConfigHandler;
import java.io.File;


/**
* Interaction logic for MainForm.xaml
*/
public class MainForm  extends Window implements IContentClosable, IRecentConversion
{

    public MainForm() throws Exception {
        InitializeComponent();
        UserConfigHandler.loadConfiguration();
        buildRecentConversionMenus();
    }

    private void mnuExit_Click(Object sender, RoutedEventArgs e) throws Exception {
        Close();
    }

    private void window_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        UserConfigHandler.saveConfiguration();
    }

    private void mnuConfDocument_Click(Object sender, RoutedEventArgs e) throws Exception {
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
                    PutConfigEditor(ConfigurationClassEnum.Document, ofd.FileName);
                }
                 
            }
        }
        finally
        {
            if (ofd != null)
                Disposable.mkDisposable(ofd).dispose();
             
        }
    }

    private void mnuConfUser_Click(Object sender, RoutedEventArgs e) throws Exception {
        putConfigEditor(ConfigurationClassEnum.User);
    }

    private void mnuConfSystem_Click(Object sender, RoutedEventArgs e) throws Exception {
        if (MessageBox.Show("Do you really want to edit the System level configuration?", "Confirmation", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
        {
            putConfigEditor(ConfigurationClassEnum.System);
        }
         
    }

    private void mnuNewConversion_Click(Object sender, RoutedEventArgs e) throws Exception {
        putConverter();
    }

    private void menuItemRecentConversion_Click(Object sender, RoutedEventArgs e) throws Exception {
        contentClose();
        Converter converter = putConverter();
        FromToElement fte = (e.Source instanceof MenuItem ? (MenuItem)e.Source : (MenuItem)null).Tag instanceof FromToElement ? (FromToElement)(e.Source instanceof MenuItem ? (MenuItem)e.Source : (MenuItem)null).Tag : (FromToElement)null;
        converter.setParameters(fte.getFrom(),fte.getTo());
    }

    private void putConfigEditor(ConfigurationClassEnum confClassLevel) throws Exception {
        putConfigEditor(confClassLevel,null);
    }

    private void putConfigEditor(ConfigurationClassEnum confClassLevel, String documentFilePath) throws Exception {
        grdCenter.Children.Clear();
        ConfigEditor configEditor = new ConfigEditor(confClassLevel,documentFilePath,this);
        configEditor.Opacity = 0.9;
        Grid.SetColumn(configEditor, 0);
        Grid.SetRow(configEditor, 0);
        grdCenter.Children.Add(configEditor);
    }

    private Converter putConverter() throws Exception {
        grdCenter.Children.Clear();
        Converter converter = new Converter(this);
        converter.Opacity = 0.9;
        Grid.SetColumn(converter, 0);
        Grid.SetRow(converter, 0);
        grdCenter.Children.Add(converter);
        return converter;
    }

    public void buildRecentConversionMenus() throws Exception {
        List<FromToElement> recentConversions = UserConfigHandler.getRecentConversions();
        for (int i = mnuConversion.Items.Count - 1;i >= 2;i--)
        {
            mnuConversion.Items.RemoveAt(i);
        }
        for (FromToElement fte : recentConversions)
        {
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ menuItem = new MenuItem();
            menuItem.Click += new RoutedEventHandler(menuItemRecentConversion_Click);
            mnuConversion.Items.Add(menuItem);
        }
    }

    public void contentClose() throws Exception {
        grdCenter.Children.Clear();
    }

}


