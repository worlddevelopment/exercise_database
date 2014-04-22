//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:57 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DateAndTime;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Global;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PictureBulletInformation;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ShadingDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StyleSheet;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;

public class CharacterProperties   
{
    public boolean fBold;
    public boolean fItalic;
    public boolean fRMarkDel;
    public boolean fOutline;
    public boolean fFldVanish;
    public boolean fSmallCaps;
    public boolean fCaps;
    public boolean fVanish;
    public boolean fRMark;
    public boolean fSpec;
    public boolean fStrike;
    public boolean fObj;
    public boolean fShadow;
    public boolean fLowerCase;
    public boolean fData;
    public boolean fOle2;
    public boolean fEmboss;
    public boolean fImprint;
    public boolean fDStrike;
    public boolean fBoldBi;
    public boolean fComplexScripts;
    public boolean fItalicBi;
    public boolean fBiDi;
    public boolean fIcoBi;
    public boolean fNonGlyph;
    public boolean fBoldOther;
    public boolean fItalicOther;
    public boolean fNoProof;
    public boolean fWebHidden;
    public boolean fFitText;
    public boolean fCalc;
    public boolean fFmtLineProp;
    public UInt16 hps = new UInt16();
    //ftc;
    //ftcAsci;
    //ftcFE;
    //ftcOther;
    //ftcBi;
    public int dxaSpace;
    public RGBColor cv;
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier ico = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier.auto;
    public UInt16 pctCharWidth = new UInt16();
    public short lid;
    public short lidDefault;
    public short lidFE;
    public short lidBi;
    public byte kcd;
    public boolean fUndetermine;
    public byte iss;
    public boolean fSpecSymbol;
    public byte idct;
    public byte idctHint;
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.UnderlineCode kul = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.UnderlineCode.none;
    public byte hres;
    public byte chHres;
    public UInt16 hpsKern = new UInt16();
    public UInt16 hpsPos = new UInt16();
    public RGBColor cvUl;
    public ShadingDescriptor shd;
    public BorderCode brc;
    public short ibstRMark;
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TextAnimation sfxtText = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TextAnimation.none;
    public boolean fDblBdr;
    public boolean fBorderWS;
    public UInt16 ufel = new UInt16();
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.FarEastLayout itypFELayout = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.FarEastLayout.none;
    public boolean fTNY;
    public boolean fWarichu;
    public boolean fKumimoji;
    public boolean fRuby;
    public boolean fLSFitText;
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.WarichuBracket iWarichuBracket = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.WarichuBracket.none;
    public boolean fWarichuNoOpenBracket;
    public boolean fTNYCompress;
    public boolean fTNYFetchTxm;
    public boolean fCellFitText;
    public UInt16 hpsAsci = new UInt16();
    public UInt16 hpsFE = new UInt16();
    public UInt16 hpsBi = new UInt16();
    //ftcSym;
    public char xchSym;
    public boolean fNumRunBi;
    public boolean fSysVanish;
    public boolean fDiacRunBi;
    public boolean fBoldPresent;
    public boolean fItalicPresent;
    public int fcPic;
    public int fcObj;
    public long lTagObj;
    public int fcData;
    public boolean fDirty;
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.HyphenationRule hresOld = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.HyphenationRule.none;
    public long chHresOld;
    public int dxpKashida;
    public int dxpSpace;
    public short ibstRMarkDel;
    public DateAndTime dttmRMark;
    public DateAndTime dttmRMarkDel;
    public UInt16 istd = new UInt16();
    public UInt16 idslRMReason = new UInt16();
    public UInt16 idslRMReasonDel = new UInt16();
    public UInt16 cpg = new UInt16();
    public UInt16 iatrUndetType = new UInt16();
    public boolean fUlGap;
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier icoHighlight = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier.auto;
    public boolean fHighlight;
    public boolean fScriptAnchor;
    public boolean fFixedObj;
    public boolean fNavHighlight;
    public boolean fChsDiff;
    public boolean fMacChs;
    public boolean fFtcAsciSym;
    public boolean fFtcReq;
    public boolean fLangApplied;
    public boolean fSpareLangApplied;
    public boolean fForcedCvAuto;
    public boolean fPropRMark;
    public short ibstPropRMark;
    public DateAndTime dttmPropRMark;
    public boolean fAnmPropRMark;
    public boolean fConflictOrig;
    public boolean fConflictOtherDel;
    public UInt16 wConflict = new UInt16();
    public short ibstConflict;
    public DateAndTime dttmConflict;
    public boolean fDispFldRMark;
    public short ibstDispFldRMark;
    public DateAndTime dttmDispFldRMark;
    public String xstDispFldRMark;
    public boolean fcObjp;
    public int dxaFitText;
    public int lFitTextID;
    public byte lbrCRJ;
    public int rsidProp;
    public int rsidText;
    public int rsidRMDel;
    public boolean fSpecVanish;
    public boolean fHasOldProps;
    public PictureBulletInformation pbi;
    public int hplcnf;
    public byte ffm;
    public boolean fSdtVanish;
    /**
    * Creates a CHP with default properties
    */
    public CharacterProperties() throws Exception {
        setDefaultValues();
    }

    /**
    * Builds a CHP based on a CHPX
    * 
    *  @param styles The stylesheet
    *  @param chpx The CHPX
    */
    public CharacterProperties(StyleSheet styleSheet, CharacterPropertyExceptions chpx, ParagraphPropertyExceptions parentPapx) throws Exception {
        setDefaultValues();
        //get all CHPX in the hierarchy
        CSList<CharacterPropertyExceptions> chpxHierarchy = new CSList<CharacterPropertyExceptions>();
        chpxHierarchy.add(chpx);
        //add parent character styles
        buildHierarchy(chpxHierarchy,styleSheet,(UInt16)getIsdt(chpx));
        //add parent paragraph styles
        buildHierarchy(chpxHierarchy,styleSheet,parentPapx.istd);
        chpxHierarchy.Reverse();
        for (CharacterPropertyExceptions c : chpxHierarchy)
        {
            //apply the CHPX hierarchy to this CHP
            applyChpx(c);
        }
    }

    private void applyChpx(PropertyExceptions chpx) throws Exception {
        for (SinglePropertyModifier sprm : chpx.grpprl)
        {
            switch(sprm.OpCode)
            {
                case sprmCIstd: 
                    //style id
                    this.istd = System.BitConverter.ToUInt16(sprm.Arguments, 0);
                    break;
                case sprmCHps: 
                    //font size
                    this.hps = sprm.Arguments[0];
                    break;
                case sprmCFBold: 
                    //bold
                    this.fBold = handleToogleValue(this.fBold,sprm.Arguments[0]);
                    break;
                case sprmCFItalic: 
                    //italic
                    this.fItalic = handleToogleValue(this.fItalic,sprm.Arguments[0]);
                    break;
            
            }
        }
    }

    private void buildHierarchy(CSList<CharacterPropertyExceptions> hierarchy, StyleSheet styleSheet, UInt16 istdStart) throws Exception {
        int istd = (int)istdStart;
        boolean goOn = true;
        while (goOn)
        {
            try
            {
                CharacterPropertyExceptions baseChpx = styleSheet.Styles.get(istd).chpx;
                if (baseChpx != null)
                {
                    hierarchy.add(baseChpx);
                    istd = (int)styleSheet.Styles.get(istd).istdBase;
                }
                else
                {
                    goOn = false;
                } 
            }
            catch (Exception __dummyCatchVar0)
            {
                goOn = false;
            }
        
        }
    }

    private boolean handleToogleValue(boolean currentValue, byte toggle) throws Exception {
        if (toggle == 1)
            return true;
        else if (toggle == 129)
            //invert the current value
            if (currentValue)
                return false;
            else
                return true; 
        else if (toggle == 128)
            return currentValue;
        else
            return false;   
    }

    //use the current value
    private void setDefaultValues() throws Exception {
        this.hps = 20;
        this.fcPic = -1;
        this.istd = 10;
        this.lidDefault = 0x0400;
        this.lidFE = 0x0400;
    }

    private int getIsdt(CharacterPropertyExceptions chpx) throws Exception {
        int ret = 10;
        for (SinglePropertyModifier sprm : chpx.grpprl)
        {
            //default value for istd
            if (sprm.OpCode == OperationCode.sprmCIstd)
            {
                ret = (int)System.BitConverter.ToInt16(sprm.Arguments, 0);
                break;
            }
             
        }
        return ret;
    }

}


