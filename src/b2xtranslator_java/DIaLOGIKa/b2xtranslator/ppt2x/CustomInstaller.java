//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:24 AM
//

package DIaLOGIKa.b2xtranslator.ppt2x;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.ppt2x.Program;
import java.util.Map;

public class CustomInstaller  extends Installer 
{

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        components = new System.ComponentModel.Container();
    }

    private String creationMark = "CreatedBy";
    private String creationValue = "ppt2xInstaller";
    public CustomInstaller() throws Exception {
        initializeComponent();
    }

    public void install(Map savedState) throws Exception {
        super.Install(savedState);
        if (StringSupport.equals(Context.Parameters["createcontextmenuppt"], "1"))
        {
            addContextMenuEntry();
        }
         
    }

    public void uninstall(Map savedState) throws Exception {
        super.Uninstall(savedState);
        removeContextMenuEntry();
    }

    /**
    * Creates the context menu entry
    */
    private void addContextMenuEntry() throws Exception {
        RegistryKey shellKey = getShellKey(Program.ContextMenuInputExtension);
        if (shellKey != null)
        {
            // create the context menu entry
            RegistryKey entryKey = shellKey.CreateSubKey(Program.ContextMenuText);
            // set the installer's mark
            entryKey.SetValue(creationMark, creationValue);
            // create command subkey
            RegistryKey convertCommand = entryKey.CreateSubKey("Command");
            // set the ppt path as value
            convertCommand.SetValue("", String.format(StringSupport.CSFmtStrToJFmtStr("\"{0}\" \"%1\""),Context.Parameters["assemblypath"]));
        }
         
    }

    /**
    * Removes the context menu entry if it is present
    * and if it was created by the installer.
    */
    private void removeContextMenuEntry() throws Exception {
        try
        {
            RegistryKey shellKey = getShellKey(Program.ContextMenuInputExtension);
            if (shellKey != null)
            {
                // if the entry is present and was created by the installer
                RegistryKey entryKey = shellKey.OpenSubKey(Program.ContextMenuText);
                if (entryKey != null)
                {
                    Object mark = entryKey.GetValue(creationMark);
                    if (mark != null && StringSupport.equals((String)mark, creationValue))
                    {
                        // remove the entry
                        shellKey.DeleteSubKeyTree(Program.ContextMenuText);
                    }
                     
                }
                 
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    /**
    * Returns the shell key that is related to the given extension
    * 
    *  @param triggerExtension 
    *  @return
    */
    private RegistryKey getShellKey(String triggerExtension) throws Exception {
        RegistryKey result = null;
        try
        {
            String defaultApp = (String)Registry.ClassesRoot.OpenSubKey(triggerExtension).GetValue("");
            result = Registry.ClassesRoot.CreateSubKey(defaultApp).CreateSubKey("shell");
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return result;
    }

}


