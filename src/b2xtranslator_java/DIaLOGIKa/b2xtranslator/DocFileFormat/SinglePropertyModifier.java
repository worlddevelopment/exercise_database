//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:07 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
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
public class SinglePropertyModifier   implements IVisitable
{
    public enum OperationCode
    {
        //Paragraph SPRMs
        sprmPIstd,
        sprmPIstdPermute,
        sprmPIncLvl,
        sprmPJc,
        sprmPJc80,
        sprmPFSideBySide,
        sprmPFKeep,
        sprmPFKeepFollow,
        sprmPFPageBreakBefore,
        sprmPBrcl,
        sprmPBrcp,
        sprmPIlvl,
        sprmPIlfo,
        sprmPFNoLineNumb,
        sprmPChgTabsPapx,
        sprmPDxaLeft,
        sprmPDxaLeft80,
        sprmPDxaLeft1,
        sprmPDxaLeft180,
        sprmPDxaRight,
        sprmPDxaRight80,
        sprmPDxcLeft,
        sprmPDxcLeft1,
        sprmPDxcRight,
        sprmPNest,
        sprmPNest80,
        sprmPDyaLine,
        sprmPDyaBefore,
        sprmPDyaAfter,
        sprmPFDyaAfterAuto,
        sprmPFDyaBeforeAuto,
        sprmPDylAfter,
        sprmPDylBefore,
        sprmPChgTabs,
        sprmPFInTable,
        sprmPFTtp,
        sprmPDxaAbs,
        sprmPDyaAbs,
        sprmPDxaWidth,
        sprmPPc,
        sprmPBrcTop10,
        sprmPBrcLeft10,
        sprmPBrcBottom10,
        sprmPBrcRight10,
        sprmPBrcBetween10,
        sprmPBrcBar10,
        sprmPDxaFromText10,
        sprmPWr,
        sprmPBrcBar,
        sprmPBrcBar70,
        sprmPBrcBar80,
        sprmPBrcBetween,
        sprmPBrcBetween70,
        sprmPBrcBetween80,
        sprmPBrcBottom,
        sprmPBrcBottom70,
        sprmPBrcBottom80,
        sprmPBrcLeft,
        sprmPBrcLeft70,
        sprmPBrcLeft80,
        sprmPBrcRight,
        sprmPBrcRight70,
        sprmPBrcRight80,
        sprmPBrcTop,
        sprmPBrcTop70,
        sprmPBrcTop80,
        sprmPFNoAutoHyph,
        sprmPWHeightAbs,
        sprmPDcs,
        sprmPShd80,
        sprmPShd,
        sprmPDyaFromText,
        sprmPDxaFromText,
        sprmPFLocked,
        sprmPFWidowControl,
        sprmPRuler,
        sprmPFKinsoku,
        sprmPFWordWrap,
        sprmPFOverflowPunct,
        sprmPFTopLinePunct,
        sprmPFAutoSpaceDE,
        sprmPFAutoSpaceDN,
        sprmPWAlignFont,
        sprmPFrameTextFlow,
        sprmPISnapBaseLine,
        sprmPAnld80,
        sprmPAnldCv,
        sprmPPropRMark,
        sprmPOutLvl,
        sprmPFBiDi,
        sprmPFNumRMIns,
        sprmPNumRM,
        sprmPHugePapx,
        sprmPFUsePgsuSettings,
        sprmPFAdjustRight,
        sprmPDtap,
        sprmPFInnerTableCell,
        sprmPFInnerTtp,
        sprmPFNoAllowOverlap,
        sprmPItap,
        sprmPWall,
        sprmPIpgp,
        sprmPCnf,
        sprmPRsid,
        sprmPIstdList,
        sprmPIstdListPermute,
        sprmPDyaBeforeNotCp0,
        sprmPTableProps,
        sprmPTIstdInfo,
        sprmPFContextualSpacing,
        sprmPRpf,
        sprmPPropRMark90,
        //Character SPRMs
        sprmCFRMarkDel,
        sprmCFRMark,
        sprmCFFldVanish,
        sprmCFSdtVanish,
        sprmCPicLocation,
        sprmCIbstRMark,
        sprmCDttmRMark,
        sprmCFData,
        sprmCIdslRMark,
        sprmCChs,
        sprmCSymbol,
        sprmCFOle2,
        sprmCIdCharType,
        sprmCHighlight,
        sprmCObjLocation,
        sprmCObjpLocation,
        sprmCFFtcAsciSymb,
        sprmCIstd,
        sprmCIstdPermute,
        sprmCDefault,
        sprmCPlain,
        sprmCKcd,
        sprmCFBold,
        sprmCFItalic,
        sprmCFStrike,
        sprmCFOutline,
        sprmCFShadow,
        sprmCFSmallCaps,
        sprmCFCaps,
        sprmCFVanish,
        sprmCFtcDefault,
        sprmCKul,
        sprmCSizePos,
        sprmCDxaSpace,
        sprmCLid,
        sprmCIco,
        sprmCHps,
        sprmCHpsInc,
        sprmCHpsPos,
        sprmCHpsPosAdj,
        sprmCMajority,
        sprmCIss,
        sprmCHpsNew50,
        sprmCHpsInc1,
        sprmCHpsKern,
        sprmCMajority50,
        sprmCHpsMul,
        sprmCHresi,
        sprmCRgFtc0,
        sprmCRgFtc1,
        sprmCRgFtc2,
        sprmCCharScale,
        sprmCFDStrike,
        sprmCFImprint,
        sprmCFSpec,
        sprmCFObj,
        sprmCPropRMark1,
        sprmCFEmboss,
        sprmCSfxText,
        sprmCFBiDi,
        sprmCFDiacColor,
        sprmCFBoldBi,
        sprmCFItalicBi,
        sprmCFtcBi,
        sprmCLidBi,
        sprmCIcoBi,
        sprmCHpsBi,
        sprmCDispFldRMark,
        sprmCIbstRMarkDel,
        sprmCDttmRMarkDel,
        SprmCBrc80,
        sprmCBrc,
        sprmCShd80,
        sprmCShd,
        sprmCIdslRMarkDel,
        sprmCFUsePgsuSettings,
        sprmCCpg,
        sprmCRgLid0_80,
        sprmCRgLid0,
        sprmCRgLid1_80,
        sprmCRgLid1,
        sprmCIdctHint,
        sprmCCv,
        sprmCCvPermute,
        sprmCCvUl,
        sprmCFBoldPresent,
        sprmCFELayout,
        sprmCFItalicPresent,
        sprmCFitText,
        sprmCFLangApplied,
        sprmCFNoProof,
        sprmCFWebHidden,
        sprmCHsp,
        sprmCLbcCRJ,
        sprmCNewIbstRM,
        sprmCTransNoProof0,
        sprmCTransNoProof1,
        sprmCFRMMove,
        sprmCRsidProp,
        sprmCRsidText,
        sprmCRsidRMDel,
        sprmCFSpecVanish,
        sprmCFComplexScripts,
        sprmCWall,
        sprmCPbi,
        sprmCCnf,
        sprmCNeedFontFixup,
        sprmCPbiIBullet,
        sprmCPbiGrf,
        sprmCPropRMark2,
        //Picture SPRMs
        sprmPicBrcl,
        sprmPicScale,
        sprmPicBrcTop80,
        sprmPicBrcBottom,
        sprmPicBrcBottom70,
        sprmPicBrcLeft80,
        sprmPicBrcLeft,
        sprmPicBrcLeft70,
        sprmPicBrcBottom80,
        sprmPicBrcRight,
        sprmPicBrcRight70,
        sprmPicBrcRight80,
        sprmPicBrcTop,
        sprmPicBrcTop70,
        sprmPicSpare4,
        sprmCFOle2WasHere,
        //Section SPRMs
        sprmScnsPgn,
        sprmSiHeadingPgn,
        sprmSOlstAnm,
        sprmSOlstAnm80,
        sprmSOlstCv,
        sprmSDxaColWidth,
        sprmSDxaColSpacing,
        sprmSFEvenlySpaced,
        sprmSFProtected,
        sprmSDmBinFirst,
        sprmSDmBinOther,
        sprmSBkc,
        sprmSFTitlePage,
        sprmSCcolumns,
        sprmSDxaColumns,
        sprmSFAutoPgn,
        sprmSNfcPgn,
        sprmSDyaPgn,
        sprmSDxaPgn,
        sprmSFPgnRestart,
        sprmSFEndnote,
        sprmSLnc,
        sprmSGprfIhdt,
        sprmSNLnnMod,
        sprmSDxaLnn,
        sprmSDyaHdrTop,
        sprmSDyaHdrBottom,
        sprmSLBetween,
        sprmSVjc,
        sprmSLnnMin,
        sprmSPgnStart,
        sprmSBOrientation,
        sprmSXaPage,
        sprmSYaPage,
        sprmSDxaLeft,
        sprmSDxaRight,
        sprmSDyaTop,
        sprmSDyaBottom,
        sprmSDzaGutter,
        sprmSDmPaperReq,
        sprmSPropRMark1,
        sprmSFBiDi,
        sprmSFFacingCol,
        sprmSFRTLGutter,
        sprmSBrcTop80,
        sprmSBrcTop,
        sprmSBrcLeft80,
        sprmSBrcLeft,
        sprmSBrcBottom80,
        sprmSBrcBottom,
        sprmSBrcRight80,
        sprmSBrcRight,
        sprmSPgbProp,
        sprmSDxtCharSpace,
        sprmSDyaLinePitch,
        sprmSClm,
        sprmSTextFlow,
        sprmSWall,
        sprmSRsid,
        sprmSFpc,
        sprmSRncFtn,
        sprmSEpc,
        sprmSRncEdn,
        sprmSNFtn,
        sprmSNfcFtnRef,
        sprmSNEdn,
        sprmSNfcEdnRef,
        sprmSPropRMark2,
        //Table SPRMs
        sprmTDefTable,
        sprmTDefTable10,
        sprmTDefTableShd97,
        sprmTDefTableShd,
        sprmTDefTableShd2nd,
        sprmTDefTableShd3rd,
        sprmTDelete,
        sprmTDiagLine,
        sprmTDiagLine80,
        sprmTDxaCol,
        sprmTDxaGapHalf,
        sprmTDxaLeft,
        sprmTDyaRowHeight,
        sprmTFBiDi80,
        sprmTFCantSplit,
        sprmTHTMLProps,
        sprmTInsert,
        sprmTJc,
        sprmTMerge,
        sprmTSetBrc80,
        sprmTSetBrc10,
        sprmTSetBrc,
        sprmTSetShd80,
        sprmTSetShdOdd80,
        sprmTSetShd,
        sprmTSetShdOdd,
        sprmTSetShdTable,
        sprmTSplit,
        sprmTTableBorders,
        sprmTTableBorders80,
        sprmTTableHeader,
        sprmTTextFlow,
        sprmTTlp,
        sprmTVertAlign,
        sprmTVertMerge,
        sprmTFCellNoWrap,
        sprmTFitText,
        sprmTFKeepFollow,
        sprmTFNeverBeenAutofit,
        sprmTFNoAllowOverlap,
        sprmTPc,
        sprmTBrcBottomCv,
        sprmTBrcLeftCv,
        sprmTBrcRightCv,
        sprmTBrcTopCv,
        sprmTCellBrcType,
        sprmTCellPadding,
        sprmTCellPaddingDefault,
        sprmTCellPaddingOuter,
        sprmTCellSpacing,
        sprmTCellSpacingDefault,
        sprmTCellSpacingOuter,
        sprmTCellWidth,
        sprmTDxaAbs,
        sprmTDxaFromText,
        sprmTDxaFromTextRight,
        sprmTDyaAbs,
        sprmTDyaFromText,
        sprmTDyaFromTextBottom,
        sprmTFAutofit,
        sprmTTableWidth,
        sprmTWidthAfter,
        sprmTWidthBefore,
        sprmTWidthIndent,
        sprmTIstd,
        sprmTSetShdRaw,
        sprmTSetShdOddRaw,
        sprmTIstdPermute,
        sprmTCellPaddingStyle,
        sprmTFCantSplit90,
        sprmTPropRMark,
        sprmTWall,
        sprmTIpgp,
        sprmTCnf,
        sprmTSetShdTableDef,
        sprmTDiagLine2nd,
        sprmTDiagLine3rd,
        sprmTDiagLine4th,
        sprmTDiagLine5th,
        sprmTDefTableShdRaw,
        sprmTDefTableShdRaw2nd,
        sprmTDefTableShdRaw3rd,
        sprmTSetShdRowFirst,
        sprmTSetShdRowLast,
        sprmTSetShdColFirst,
        sprmTSetShdColLast,
        sprmTSetShdBand1,
        sprmTSetShdBand2,
        sprmTRsid,
        sprmTCellWidthStyle,
        sprmTCellPaddingStyleBad,
        sprmTCellVertAlignStyle,
        sprmTCellNoWrapStyle,
        sprmTCellFitTextStyle,
        sprmTCellBrcTopStyle,
        sprmTCellBrcBottomStyle,
        sprmTCellBrcLeftStyle,
        sprmTCellBrcRightStyle,
        sprmTCellBrcInsideHStyle,
        sprmTCellBrcInsideVStyle,
        sprmTCellBrcTL2BRStyle,
        sprmTCellBrcTR2BLStyle,
        sprmTCellShdStyle,
        sprmTCHorzBands,
        sprmTCVertBands,
        sprmTJcRow,
        sprmTTableBrcTop,
        sprmTTableBrcLeft,
        sprmTTableBrcBottom,
        sprmTTableBrcRight,
        sprmTTableBrcInsideH,
        sprmTTableBrcInsideV,
        sprmTFBiDi,
        sprmTFBiDi90
    }
    public enum SprmType
    {
        /**
        * Identifies the type of a SPRM
        */
        __dummyEnum__0,
        PAP,
        CHP,
        PIC,
        SEP,
        TAP
    }
    /**
    * The operation code identifies the property of the
    * PAP/CHP/PIC/SEP/TAP which sould be modified
    */
    public DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode OpCode = DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmPIstd;
    /**
    * This SPRM requires special handling
    */
    public boolean fSpec;
    /**
    * The type of the SPRM
    */
    public SprmType Type = SprmType.PAP;
    /**
    * The arguments which is applied to the property
    */
    public byte[] Arguments;
    /**
    * parses the byte to retrieve a SPRM
    * 
    *  @param bytes The bytes
    */
    public SinglePropertyModifier(byte[] bytes) throws Exception {
        //first 2 bytes are the operation code ...
        this.OpCode = (DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode)System.BitConverter.ToUInt16(bytes, 0);
        //... whereof bit 9 is fSpec ...
        long j = (long)this.OpCode << 22;
        j = j >> 31;
        if (j == 1)
            this.fSpec = true;
        else
            this.fSpec = false; 
        //... and bits 10,11,12 are the type ...
        long i = (long)this.OpCode << 19;
        i = i >> 29;
        this.Type = (SprmType)i;
        //... and last 3 bits are the spra
        byte spra = (byte)(((Enum)this.OpCode).ordinal() >> 13);
        byte opSize = getOperandSize(spra);
        if (opSize == 255)
        {
            switch(OpCode)
            {
                case sprmTDefTable: 
                case sprmTDefTable10: 
                    //the variable length stand in the bytes 2 and 3
                    short opSizeTable = System.BitConverter.ToInt16(bytes, 2);
                    //and the arguments start at the byte after that (byte3)
                    this.Arguments = new byte[opSizeTable - 1];
                    //Arguments start at byte 4
                    Array.Copy(bytes, 4, this.Arguments, 0, Arguments.length);
                    break;
                case sprmPChgTabs: 
                    this.Arguments = new byte[bytes[2]];
                    Array.Copy(bytes, 3, this.Arguments, 0, Arguments.length);
                    break;
                default: 
                    //the variable length stand in the byte after the opcode (byte2)
                    opSize = bytes[2];
                    //and the arguments start at the byte after that (byte3)
                    this.Arguments = new byte[opSize];
                    Array.Copy(bytes, 3, this.Arguments, 0, Arguments.length);
                    break;
            
            }
        }
        else
        {
            this.Arguments = new byte[opSize];
            Array.Copy(bytes, 2, this.Arguments, 0, Arguments.length);
        } 
    }

    /**
    * Get be used to get the size of the sprm's operand.
    * Returns 0 if the Operation failed and 255 if the size is variable
    * 
    *  @param spra the 3 bits for spra (as byte)
    *  @return the size (as byte)
    */
    public static byte getOperandSize(byte spra) throws Exception {
        byte __dummyScrutVar1 = spra;
        if (__dummyScrutVar1.equals(0))
        {
            return 1;
        }
        else if (__dummyScrutVar1.equals(1))
        {
            return 1;
        }
        else if (__dummyScrutVar1.equals(2))
        {
            return 2;
        }
        else if (__dummyScrutVar1.equals(3))
        {
            return 4;
        }
        else if (__dummyScrutVar1.equals(4))
        {
            return 2;
        }
        else if (__dummyScrutVar1.equals(5))
        {
            return 2;
        }
        else if (__dummyScrutVar1.equals(6))
        {
            return 255;
        }
        else if (__dummyScrutVar1.equals(7))
        {
            return 3;
        }
        else
        {
            return 0;
        }        
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<SinglePropertyModifier>)mapping).apply(this);
    }

}


