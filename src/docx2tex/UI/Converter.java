//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:01 AM
//

package docx2tex.UI;

import CS2JNet.System.DateTimeSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import CS2JNet.System.TimeSpan;
import docx2tex.Library.Docx2TexWorker;
import docx2tex.Library.IStatusInformation;
import docx2tex.Library.StaticConfigHelper;
import docx2tex.UI.FromToElement;
import docx2tex.UI.IRecentConversion;
import docx2tex.UI.UserConfigHandler;
import java.util.Calendar;
import java.util.Date;


/**
* Interaction logic for Converter.xaml
*/
public class Converter  extends UserControl 
{

    IRecentConversion _contentClosable;
    public Converter(IRecentConversion contentClosable) throws Exception {
        _contentClosable = contentClosable;
        InitializeComponent();
    }

    private void btnSelectWord2k7Doc_Click(Object sender, RoutedEventArgs e) throws Exception {
        selectWord2K7Doc();
    }

    private void btnSelectLaTeXDoc_Click(Object sender, RoutedEventArgs e) throws Exception {
        selectLaTeXDoc();
    }

    private void btnStartConversion_Click(Object sender, RoutedEventArgs e) throws Exception {
        startConversion();
    }

    private void btnClose_Click(Object sender, RoutedEventArgs e) throws Exception {
        _contentClosable.contentClose();
    }

    public void setParameters(String docxPath, String texPath) throws Exception {
        txtSelectWord2k7Doc.Text = docxPath;
        txtSelectLaTeXDoc.Text = texPath;
    }

    private void selectWord2K7Doc() throws Exception {
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
                    txtSelectWord2k7Doc.Text = ofd.FileName;
                }
                 
            }
        }
        finally
        {
            if (ofd != null)
                Disposable.mkDisposable(ofd).dispose();
             
        }
    }

    private void selectLaTeXDoc() throws Exception {
        /* [UNSUPPORTED] 'var' as type is unsupported "var" */ ofd = new System.Windows.Forms.OpenFileDialog();
        try
        {
            {
                ofd.Multiselect = false;
                ofd.CheckPathExists = true;
                ofd.CheckFileExists = false;
                ofd.DereferenceLinks = true;
                ofd.Filter = "LaTeX documents (*.tex;*.ltx)|*.tex;*.ltx";
                if (ofd.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                {
                    txtSelectLaTeXDoc.Text = ofd.FileName;
                }
                 
            }
        }
        finally
        {
            if (ofd != null)
                Disposable.mkDisposable(ofd).dispose();
             
        }
    }

    private void startConversion() throws Exception {
        List<FromToElement> recentConversions = UserConfigHandler.getRecentConversions();
        lblError.Content = "";
        String docxPath = txtSelectWord2k7Doc.Text;
        String texPath = txtSelectLaTeXDoc.Text;
        TextBoxOutput statusInfo = new TextBoxOutput(txtScreen, scrLog);
        try
        {
            statusInfo.writeLine("");
            StaticConfigHelper.setDocxPath(docxPath);
            Docx2TexWorker docx2TexWorker = new Docx2TexWorker();
            btnStartConversion.IsEnabled = false;
            statusInfo.writeLine("Source: " + docxPath);
            statusInfo.writeLine("Destination: " + texPath);
            statusInfo.writeLine("");
            if (docx2TexWorker.process(docxPath,texPath,statusInfo))
            {
                recentConversions.RemoveAll(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(rce) => {
                    return StringSupport.equals(rce.From, docxPath) && StringSupport.equals(rce.To, texPath);
                }" */);
                recentConversions.add(0, new FromToElement());
                UserConfigHandler.updateRecentConversion(recentConversions);
                _contentClosable.buildRecentConversionMenus();
            }
             
        }
        catch (Exception ex)
        {
            lblError.Content = ex.getMessage().replace(System.getProperty("line.separator"), "");
            statusInfo.write(ex.toString());
        }
        finally
        {
            statusInfo.writeLine("");
            btnStartConversion.IsEnabled = true;
        }
        statusInfo.flush();
    }

    private static class TextBoxOutput   implements IStatusInformation
    {
        private TextBox _txtScreen = new TextBox();
        private ScrollViewer _scrollViewer = new ScrollViewer();
        private boolean _lastCR;
        private Date _lastRefresh;
        public TextBoxOutput(TextBox txtScreen, ScrollViewer scrollViewer) throws Exception {
            _txtScreen = txtScreen;
            _scrollViewer = scrollViewer;
            _lastCR = false;
            _lastRefresh = Calendar.getInstance().getTime().AddHours(-1.0);
        }

        public void write(String data) throws Exception {
            resolveLastCR();
            _txtScreen.AppendText(data);
            ensureText();
        }

        public void writeCR(String data) throws Exception {
            resolveLastCR();
            _txtScreen.AppendText(data);
            _lastCR = true;
            ensureText();
        }

        public void writeLine(String data) throws Exception {
            resolveLastCR();
            _txtScreen.AppendText(data + System.getProperty("line.separator"));
            ensureText();
        }

        void resolveLastCR() throws Exception {
            if (_lastCR)
            {
                String data = _txtScreen.Text;
                int lastNL = data.lastIndexOf(System.getProperty("line.separator"));
                if (lastNL >= 0)
                {
                    _txtScreen.Text = data.substring(0, (0) + (lastNL + 2));
                }
                 
            }
             
            //                    string []lines = _txtScreen.Text.Split(new string[] { Environment.NewLine }, StringSplitOptions.None);
            //                    _txtScreen.Text = string.Join(Environment.NewLine, lines, 0, lines.Length - 1) + Environment.NewLine;
            _lastCR = false;
        }

        private void ensureText() throws Exception {
            Date lastRefresh = Calendar.getInstance().getTime();
            // for security reasons refresh screen only in every 200 milliseconds
            if (DateTimeSupport.lessthan(new TimeSpan(0, 0, 0, 0, 200), lastRefresh - _lastRefresh))
            {
                _scrollViewer.ScrollToBottom();
                Application.DoEvents();
                _lastRefresh = lastRefresh;
            }
             
        }

        public void flush() throws Exception {
            _lastRefresh = _lastRefresh.AddHours(-1.0);
            ensureText();
        }
    
    }

}


