//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:24 AM
//

package DIaLOGIKa.b2xtranslator.doc2x;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.doc2x.Program;
import java.util.Map;


/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
    private String creationValue = "doc2xInstaller";
    public CustomInstaller() throws Exception {
        initializeComponent();
    }

    public void install(Map savedState) throws Exception {
        super.Install(savedState);
        if (StringSupport.equals(Context.Parameters["createcontextmenudoc"], "1"))
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


