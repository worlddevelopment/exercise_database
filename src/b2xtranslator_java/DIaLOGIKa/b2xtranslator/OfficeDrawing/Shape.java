//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:33 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeType;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class Shape  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public int spid;
    /**
    * This shape is a group shape
    */
    public boolean fGroup;
    /**
    * Not a top-level shape
    */
    public boolean fChild;
    /**
    * This is the topmost group shape.
    * Exactly one of these per drawing.
    */
    public boolean fPatriarch;
    /**
    * The shape has been deleted
    */
    public boolean fDeleted;
    /**
    * The shape is an OLE object
    */
    public boolean fOleShape;
    /**
    * Shape has a hspMaster property
    */
    public boolean fHaveMaster;
    /**
    * Shape is flipped horizontally
    */
    public boolean fFlipH;
    /**
    * Shape is flipped vertically
    */
    public boolean fFlipV;
    /**
    * Connector type of shape
    */
    public boolean fConnector;
    /**
    * Shape has an anchor of some kind
    */
    public boolean fHaveAnchor;
    /**
    * Background shape
    */
    public boolean fBackground;
    /**
    * Shape has a shape type property
    */
    public boolean fHaveSpt;
    /**
    * The shape type of the shape
    */
    public ShapeType ShapeType;
    public Shape(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.spid = this.Reader.ReadInt32();
        long flag = this.Reader.ReadUInt32();
        this.fGroup = Utils.BitmaskToBool(flag, 0x1);
        this.fChild = Utils.BitmaskToBool(flag, 0x2);
        this.fPatriarch = Utils.BitmaskToBool(flag, 0x4);
        this.fDeleted = Utils.BitmaskToBool(flag, 0x8);
        this.fOleShape = Utils.BitmaskToBool(flag, 0x10);
        this.fHaveMaster = Utils.BitmaskToBool(flag, 0x20);
        this.fFlipH = Utils.BitmaskToBool(flag, 0x40);
        this.fFlipV = Utils.BitmaskToBool(flag, 0x80);
        this.fConnector = Utils.BitmaskToBool(flag, 0x100);
        this.fHaveAnchor = Utils.BitmaskToBool(flag, 0x200);
        this.fBackground = Utils.BitmaskToBool(flag, 0x400);
        this.fHaveSpt = Utils.BitmaskToBool(flag, 0x800);
        this.ShapeType = ShapeType.getShapeType(this.Instance);
    }

    public String toString(uint depth) throws Exception {
        return String.Format("{0}\n{1}Id = {2}, isGroup = {3}, isChild = {4}, isPatriarch = {5}", super.toString(depth), indentationForDepth(depth + 1), this.spid, this.fGroup, this.fChild, this.fPatriarch);
    }

}


