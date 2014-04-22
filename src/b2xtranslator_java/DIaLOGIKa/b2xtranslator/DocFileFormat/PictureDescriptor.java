//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:05 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PictureDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class PictureDescriptor   implements IVisitable
{
    public enum PictureType
    {
        jpg,
        png,
        wmf
    }
    public static class MetafilePicture   
    {
        public MetafilePicture() {
        }

        /**
        * Specifies the mapping mode in which the picture is drawn.
        */
        public short mm;
        /**
        * Specifies the size of the metafile picture for all modes except the MM_ISOTROPIC and MM_ANISOTROPIC modes.
        * (For more information about these modes, see the yExt member.) 
        * The x-extent specifies the width of the rectangle within which the picture is drawn.
        * The coordinates are in units that correspond to the mapping mode.
        */
        public short xExt;
        /**
        * Specifies the size of the metafile picture for all modes except the MM_ISOTROPIC and MM_ANISOTROPIC modes.
        * The y-extent specifies the height of the rectangle within which the picture is drawn.
        * The coordinates are in units that correspond to the mapping mode. 
        * For MM_ISOTROPIC and MM_ANISOTROPIC modes, which can be scaled, the xExt and yExt members
        * contain an optional suggested size in MM_HIMETRIC units.
        * For MM_ANISOTROPIC pictures, xExt and yExt can be zero when no suggested size is supplied.
        * For MM_ISOTROPIC pictures, an aspect ratio must be supplied even when no suggested size is given.
        * (If a suggested size is given, the aspect ratio is implied by the size.)
        * To give an aspect ratio without implying a suggested size, set xExt and yExt to negative values
        * whose ratio is the appropriate aspect ratio.
        * The magnitude of the negative xExt and yExt values is ignored; only the ratio is used.
        */
        public short yExt;
        /**
        * Handle to a memory metafile.
        */
        public short hMf;
    }

    /**
    * Rectangle for window origin and extents when metafile is stored (ignored if 0).
    */
    public byte[] rcWinMf;
    /**
    * Horizontal measurement in twips of the rectangle the picture should be imaged within.
    */
    public short dxaGoal;
    /**
    * Vertical measurement in twips of the rectangle the picture should be imaged within.
    */
    public short dyaGoal;
    /**
    * Horizontal scaling factor supplied by user expressed in .001% units
    */
    public UInt16 mx = new UInt16();
    /**
    * Vertical scaling factor supplied by user expressed in .001% units
    */
    public UInt16 my = new UInt16();
    /**
    * The type of the picture
    */
    public PictureType Type = PictureType.jpg;
    /**
    * The name of the picture
    */
    public String Name;
    /**
    * The data of the windows metafile picture (WMF)
    */
    public MetafilePicture mfp = new MetafilePicture();
    /**
    * The amount the picture has been cropped on the left in twips
    */
    public short dxaCropLeft;
    /**
    * The amount the picture has been cropped on the top in twips
    */
    public short dyaCropTop;
    /**
    * The amount the picture has been cropped on the right in twips
    */
    public short dxaCropRight;
    /**
    * The amount the picture has been cropped on the bottom in twips
    */
    public short dyaCropBottom;
    /**
    * Border above picture
    */
    public BorderCode brcTop;
    /**
    * Border to the left of the picture
    */
    public BorderCode brcLeft;
    /**
    * Border below picture
    */
    public BorderCode brcBottom;
    /**
    * Border to the right of the picture
    */
    public BorderCode brcRight;
    /**
    * Horizontal offset of hand annotation origin
    */
    public short dxaOrigin;
    /**
    * vertical offset of hand annotation origin
    */
    public short dyaOrigin;
    /**
    * unused
    */
    public short cProps;
    public ShapeContainer ShapeContainer;
    public BlipStoreEntry BlipStoreEntry;
    /**
    * Parses the CHPX for a fcPic an loads the PictureDescriptor at this offset
    * 
    *  @param chpx The CHPX that holds a SPRM for fcPic
    */
    public PictureDescriptor(CharacterPropertyExceptions chpx, VirtualStream stream) throws Exception {
        //Get start and length of the PICT
        int fc = getFcPic(chpx);
        if (fc >= 0)
        {
            parse(stream,fc);
        }
         
    }

    private void parse(VirtualStream stream, int fc) throws Exception {
        stream.Seek(fc, System.IO.SeekOrigin.Begin);
        VirtualStreamReader reader = new VirtualStreamReader(stream);
        int lcb = reader.readInt32();
        if (lcb > 0)
        {
            UInt16 cbHeader = reader.readUInt16();
            this.mfp = new MetafilePicture();
            this.mfp.mm = reader.readInt16();
            this.mfp.xExt = reader.readInt16();
            this.mfp.yExt = reader.readInt16();
            this.mfp.hMf = reader.readInt16();
            if (this.mfp.mm > 98)
            {
                this.rcWinMf = reader.readBytes(14);
                //dimensions
                this.dxaGoal = reader.readInt16();
                this.dyaGoal = reader.readInt16();
                this.mx = reader.readUInt16();
                this.my = reader.readUInt16();
                //cropping
                this.dxaCropLeft = reader.readInt16();
                this.dyaCropTop = reader.readInt16();
                this.dxaCropRight = reader.readInt16();
                this.dyaCropBottom = reader.readInt16();
                short brcl = reader.readInt16();
                //borders
                this.brcTop = new BorderCode(reader.readBytes(4));
                this.brcLeft = new BorderCode(reader.readBytes(4));
                this.brcBottom = new BorderCode(reader.readBytes(4));
                this.brcRight = new BorderCode(reader.readBytes(4));
                this.dxaOrigin = reader.readInt16();
                this.dyaOrigin = reader.readInt16();
                this.cProps = reader.readInt16();
                //Parse the OfficeDrawing Stuff
                DIaLOGIKa.b2xtranslator.OfficeDrawing.Record r = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(reader);
                if (r instanceof ShapeContainer)
                {
                    this.ShapeContainer = (ShapeContainer)r;
                    long pos = Unsupported.throwUnsupported("reader.getBaseStream().Position");
                    if (pos < (fc + lcb))
                    {
                        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(reader);
                        if (rec.getClass() == BlipStoreEntry.class)
                        {
                            this.BlipStoreEntry = (BlipStoreEntry)rec;
                        }
                         
                    }
                     
                }
                 
            }
             
        }
         
    }

    /**
    * Returns the fcPic into the "data" stream, where the PIC begins.
    * Returns -1 if the CHPX has no fcPic.
    * 
    *  @param chpx The CHPX
    *  @return
    */
    public static int getFcPic(CharacterPropertyExceptions chpx) throws Exception {
        int ret = -1;
        for (SinglePropertyModifier sprm : chpx.grpprl)
        {
            switch(sprm.OpCode)
            {
                case sprmCPicLocation: 
                    ret = System.BitConverter.ToInt32(sprm.Arguments, 0);
                    break;
                case sprmCHsp: 
                    ret = System.BitConverter.ToInt32(sprm.Arguments, 0);
                    break;
                case sprmCFData: 
                    break;
            
            }
        }
        return ret;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<PictureDescriptor>)mapping).apply(this);
    }

}


