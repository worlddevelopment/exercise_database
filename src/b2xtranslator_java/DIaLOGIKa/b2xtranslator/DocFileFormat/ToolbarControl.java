//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:08 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ToolbarControlBitmap;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class ToolbarControl  extends ByteStructure 
{
    public enum ToolbarControlType
    {
        __dummyEnum__0,
        Button,
        Edit,
        Dropdown,
        ComboBox,
        __dummyEnum__1,
        SplitDropDown,
        OCXDropDown,
        __dummyEnum__2,
        GraphicDropDown,
        Popup,
        __dummyEnum__3,
        ButtonPopup,
        SplitButtonPopup,
        SplitButtonMRUPopup,
        Label,
        ExpandingGrid,
        __dummyEnum__4,
        Grid,
        Gauge,
        GraphicCombo,
        Pane,
        ActiveX
    }
    /**
    * Signed integer that specifies the toolbar control signature number.
    * MUST be 0x03.
    */
    public byte bSignature;
    /**
    * Signed integer that specifies the toolbar control version number. 
    * MUST be 0x01.
    */
    public byte bVersion;
    public boolean fHidden;
    public boolean fBeginGroup;
    public boolean fOwnLine;
    public boolean fNoCustomize;
    public boolean fSaveDxy;
    public boolean fBeginLine;
    /**
    * 
    */
    public ToolbarControlType tct = ToolbarControlType.Button;
    /**
    * Unsigned integer that specifies the toolbar control identifier for this toolbar control.
    * MUST be 0x0001 when the toolbar control is a custom toolbar control or MUST be equal
    * to one of the values listed in [MS-CTDOC] section 2.2 or in [MS-CTXLS] section 2.2
    * when the toolbar control is not a custom toolbar control.
    */
    public short tcid;
    /**
    * Structure of type TBCSFlags that specifies toolbar control flags.
    */
    public int tbct;
    /**
    * Unsigned integer that specifies the toolbar control priority for dropping and wrapping purposes. 
    * Value MUST be in the range 0x00 to 0x07. 
    * If the value equals 0x00, it is considered the default state. 
    * If it equals 0x01 the toolbar control will never be dropped from the toolbar and will be wrapped when needed. 
    * Otherwise the higher the number the sooner the toolbar control will be dropped.
    */
    public byte bPriority;
    /**
    * Unsigned integer that specifies the width, in pixels, of the toolbar control. 
    * MUST only exist if bFlagsTCR.fSaveDxy equals 1.
    */
    public UInt16 width = new UInt16();
    /**
    * Unsigned integer that specifies the height, in pixels, of the toolbar control. 
    * MUST only exist if bFlagsTCR.fSaveDxy equals 1.
    */
    public UInt16 height = new UInt16();
    /**
    * Structure of type Cid that specifies the command identifier for this toolbar control.
    * MUST only exist if tbch.tcid is not equal to 0x0001 and is not equal to 0x1051. 
    * Toolbar controls MUST only have Cid structures that have Cmt values equal to 0x0001 or 0x0003.
    */
    public byte[] cid;
    public boolean fSaveText;
    public boolean fSaveMiscUIStrings;
    public boolean fSaveMiscCustom;
    public boolean fDisabled;
    /**
    * specifies the custom label of the toolbar control. 
    * MUST exist if bFlags.fSaveText equals 1. 
    * MUST NOT exist if bFlags.fSaveText equals 0.
    */
    public String customText;
    /**
    * specifies a description of this toolbar control. 
    * MUST exist if bFlags.fSaveMiscUIStrings equals 1.  
    * MUST NOT exist if bflags.fSaveMiscUIString equals 0.
    */
    public String descriptionText;
    /**
    * SHOULD specify the ToolTip of this toolbar control. 
    * MUST exist if bFlags.fSaveMiscUIStrings equals 1. 
    * MUST NOT exist if bFlags.fSaveMiscUIStrings equals 0.
    */
    public String tooltip;
    /**
    * specifies the full path to the help file used to provide the help topic of the toolbar control. 
    * For this field to be used idHelpContext MUST be set.
    */
    public String helpFile;
    /**
    * specifies the help context id number for the help topic of the toolbar control. 
    * A help context id is a numeric identifier associated to a specific help topic. 
    * For this field to be used wstrHelpFile MUST be set.
    */
    public int idHelpContext;
    /**
    * Specifies a custom string used to store arbitrary information about the toolbar control.
    */
    public String tag;
    /**
    * Specifies the name of the macro associated to this toolbar control.
    */
    public String onAction;
    /**
    * Apecifies a custom string used to store arbitrary information about the toolbar control.
    */
    public String param;
    /**
    * Signed integer that specifies how the toolbar control will be used during OLE merging. 
    * The value MUST be in the following table:
    * 0xFF: A correct value was not found for this toolbar control. A value of 0x0001 will be used when the value of this field is requested.
    * 0x00: Neither. Toolbar control is not applicable when the application in either OLE host mode or OLE server mode.
    * 0x01: Server. Toolbar control is applicable when the application is in OLE server mode. (Default value used by custom toolbar controls)
    * 0x02: Host. Toolbar control is applicable when the application is in OLE host mode.
    * 0x03: Both. Toolbar control is applicable when the application is in OLE server mode and OLE host mode.
    */
    public byte tbcu;
    /**
    * Signed integer that specifies how the toolbar control will be used during OLE menu merging. 
    * This field is only used by toolbar controls of type Popup. 
    * The Value MUST be in the following table:
    * 0xFF: None. Toolbar control will not be placed in any OLE menu group.
    * 0x00: File. Toolbar control will be placed in the File OLE menu group.
    * 0x01: Edit. Toolbar control will be placed in the Edit OLE menu group.
    * 0x02: Container. Toolbar control will be placed in the Container OLE menu group.
    * 0x03: Object. Toolbar control will be placed in the Object OLE menu group.
    * 0x04: Window. Toolbar control will be placed in the Window OLE menu group.
    * 0x05: Help. Toolbar control will be placed in the Help OLE menu group.
    */
    public byte tbmg;
    public ToolbarControl(VirtualStreamReader reader) throws Exception {
        super(reader, ByteStructure.VARIABLE_LENGTH);
        //HEADER START
        this.bSignature = reader.readByte();
        this.bVersion = reader.readByte();
        int bFlagsTCR = (int)reader.readByte();
        this.fHidden = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlagsTCR,0x01);
        this.fBeginGroup = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlagsTCR,0x02);
        this.fOwnLine = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlagsTCR,0x04);
        this.fNoCustomize = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlagsTCR,0x08);
        this.fSaveDxy = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlagsTCR,0x10);
        this.fBeginLine = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlagsTCR,0x40);
        this.tct = (ToolbarControlType)reader.readByte();
        this.tcid = reader.readInt16();
        this.tbct = reader.readInt32();
        this.bPriority = reader.readByte();
        if (this.fSaveDxy)
        {
            this.width = reader.readUInt16();
            this.height = reader.readUInt16();
        }
         
        //HEADER END
        //cid
        if (this.tcid != 0x01 && this.tcid != 0x1051)
        {
            this.cid = reader.readBytes(4);
        }
         
        //DATA START
        if (this.tct != ToolbarControlType.ActiveX)
        {
            //general control info
            byte flags = reader.readByte();
            this.fSaveText = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flags,0x01);
            this.fSaveMiscUIStrings = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flags,0x02);
            this.fSaveMiscCustom = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flags,0x04);
            this.fDisabled = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flags,0x04);
            if (this.fSaveText)
            {
                this.customText = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
            }
             
            if (this.fSaveMiscUIStrings)
            {
                this.descriptionText = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                this.tooltip = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
            }
             
            if (this.fSaveMiscCustom)
            {
                this.helpFile = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                this.idHelpContext = reader.readInt32();
                this.tag = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                this.onAction = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                this.param = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                this.tbcu = reader.readByte();
                this.tbmg = reader.readByte();
            }
             
            //control specific info
            switch(this.tct)
            {
                case Button: 
                case ExpandingGrid: 
                    //TBCB Specific
                    int bFlags = (int)reader.readByte();
                    int state = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt(bFlags,0x03);
                    boolean fAccelerator = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlags,0x04);
                    boolean fCustomBitmap = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlags,0x08);
                    boolean fCustomBtnFace = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlags,0x10);
                    boolean fHyperlinkType = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bFlags,0x20);
                    if (fCustomBitmap)
                    {
                        ToolbarControlBitmap icon = new ToolbarControlBitmap(reader);
                        ToolbarControlBitmap iconMask = new ToolbarControlBitmap(reader);
                    }
                     
                    if (fCustomBtnFace)
                    {
                        UInt16 iBtnFace = reader.readUInt16();
                    }
                     
                    if (fAccelerator)
                    {
                        String wstrAcc = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                    }
                     
                    break;
                case Popup: 
                case ButtonPopup: 
                case SplitButtonPopup: 
                case SplitButtonMRUPopup: 
                    //TBC Menu Specific
                    int tbid = reader.readInt32();
                    String name = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                    break;
                case Edit: 
                case ComboBox: 
                case GraphicCombo: 
                case Dropdown: 
                case SplitDropDown: 
                case OCXDropDown: 
                case GraphicDropDown: 
                    //TBC Combo Dropdown Specific
                    if (this.tcid == 1)
                    {
                        short cwstrItems = reader.readInt16();
                        String wstrList = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                        short cwstrMRU = reader.readInt16();
                        short iSel = reader.readInt16();
                        short cLines = reader.readInt16();
                        short dxWidth = reader.readInt16();
                        String wstrEdit = DIaLOGIKa.b2xtranslator.Tools.Utils.readWString(reader.getBaseStream());
                    }
                     
                    break;
                default: 
                    break;
            
            }
        }
         
    }

}


//no control Specific Info