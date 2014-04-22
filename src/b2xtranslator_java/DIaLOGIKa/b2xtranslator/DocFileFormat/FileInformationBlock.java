//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:01 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

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
public class FileInformationBlock   
{
    public enum FibVersion
    {
        __dummyEnum__0,
        __dummyEnum__1,
        __dummyEnum__2,
        __dummyEnum__3,
        __dummyEnum__4,
        __dummyEnum__5,
        __dummyEnum__6,
        __dummyEnum__7,
        __dummyEnum__8,
        __dummyEnum__9,
        __dummyEnum__10,
        __dummyEnum__11,
        __dummyEnum__12,
        __dummyEnum__13,
        __dummyEnum__14,
        __dummyEnum__15,
        __dummyEnum__16,
        __dummyEnum__17,
        __dummyEnum__18,
        __dummyEnum__19,
        __dummyEnum__20,
        __dummyEnum__21,
        __dummyEnum__22,
        __dummyEnum__23,
        __dummyEnum__24,
        __dummyEnum__25,
        __dummyEnum__26,
        __dummyEnum__27,
        __dummyEnum__28,
        __dummyEnum__29,
        __dummyEnum__30,
        __dummyEnum__31,
        __dummyEnum__32,
        __dummyEnum__33,
        __dummyEnum__34,
        __dummyEnum__35,
        __dummyEnum__36,
        __dummyEnum__37,
        __dummyEnum__38,
        __dummyEnum__39,
        __dummyEnum__40,
        __dummyEnum__41,
        __dummyEnum__42,
        __dummyEnum__43,
        __dummyEnum__44,
        __dummyEnum__45,
        __dummyEnum__46,
        __dummyEnum__47,
        __dummyEnum__48,
        __dummyEnum__49,
        __dummyEnum__50,
        __dummyEnum__51,
        __dummyEnum__52,
        __dummyEnum__53,
        __dummyEnum__54,
        __dummyEnum__55,
        __dummyEnum__56,
        __dummyEnum__57,
        __dummyEnum__58,
        __dummyEnum__59,
        __dummyEnum__60,
        __dummyEnum__61,
        __dummyEnum__62,
        __dummyEnum__63,
        __dummyEnum__64,
        __dummyEnum__65,
        __dummyEnum__66,
        __dummyEnum__67,
        __dummyEnum__68,
        __dummyEnum__69,
        __dummyEnum__70,
        __dummyEnum__71,
        __dummyEnum__72,
        __dummyEnum__73,
        __dummyEnum__74,
        __dummyEnum__75,
        __dummyEnum__76,
        __dummyEnum__77,
        __dummyEnum__78,
        __dummyEnum__79,
        __dummyEnum__80,
        __dummyEnum__81,
        __dummyEnum__82,
        __dummyEnum__83,
        __dummyEnum__84,
        __dummyEnum__85,
        __dummyEnum__86,
        __dummyEnum__87,
        __dummyEnum__88,
        __dummyEnum__89,
        __dummyEnum__90,
        __dummyEnum__91,
        __dummyEnum__92,
        __dummyEnum__93,
        __dummyEnum__94,
        __dummyEnum__95,
        __dummyEnum__96,
        __dummyEnum__97,
        __dummyEnum__98,
        __dummyEnum__99,
        __dummyEnum__100,
        __dummyEnum__101,
        __dummyEnum__102,
        __dummyEnum__103,
        __dummyEnum__104,
        __dummyEnum__105,
        __dummyEnum__106,
        __dummyEnum__107,
        __dummyEnum__108,
        __dummyEnum__109,
        __dummyEnum__110,
        __dummyEnum__111,
        __dummyEnum__112,
        __dummyEnum__113,
        __dummyEnum__114,
        __dummyEnum__115,
        __dummyEnum__116,
        __dummyEnum__117,
        __dummyEnum__118,
        __dummyEnum__119,
        __dummyEnum__120,
        __dummyEnum__121,
        __dummyEnum__122,
        __dummyEnum__123,
        __dummyEnum__124,
        __dummyEnum__125,
        __dummyEnum__126,
        __dummyEnum__127,
        __dummyEnum__128,
        __dummyEnum__129,
        __dummyEnum__130,
        __dummyEnum__131,
        __dummyEnum__132,
        __dummyEnum__133,
        __dummyEnum__134,
        __dummyEnum__135,
        __dummyEnum__136,
        __dummyEnum__137,
        __dummyEnum__138,
        __dummyEnum__139,
        __dummyEnum__140,
        __dummyEnum__141,
        __dummyEnum__142,
        __dummyEnum__143,
        __dummyEnum__144,
        __dummyEnum__145,
        __dummyEnum__146,
        __dummyEnum__147,
        __dummyEnum__148,
        __dummyEnum__149,
        __dummyEnum__150,
        __dummyEnum__151,
        __dummyEnum__152,
        __dummyEnum__153,
        __dummyEnum__154,
        __dummyEnum__155,
        __dummyEnum__156,
        __dummyEnum__157,
        __dummyEnum__158,
        __dummyEnum__159,
        __dummyEnum__160,
        __dummyEnum__161,
        __dummyEnum__162,
        __dummyEnum__163,
        __dummyEnum__164,
        __dummyEnum__165,
        __dummyEnum__166,
        __dummyEnum__167,
        __dummyEnum__168,
        __dummyEnum__169,
        __dummyEnum__170,
        __dummyEnum__171,
        __dummyEnum__172,
        __dummyEnum__173,
        __dummyEnum__174,
        __dummyEnum__175,
        __dummyEnum__176,
        __dummyEnum__177,
        __dummyEnum__178,
        __dummyEnum__179,
        __dummyEnum__180,
        __dummyEnum__181,
        __dummyEnum__182,
        __dummyEnum__183,
        __dummyEnum__184,
        __dummyEnum__185,
        __dummyEnum__186,
        __dummyEnum__187,
        __dummyEnum__188,
        __dummyEnum__189,
        __dummyEnum__190,
        __dummyEnum__191,
        __dummyEnum__192,
        Fib1997,
        __dummyEnum__193,
        __dummyEnum__194,
        __dummyEnum__195,
        __dummyEnum__196,
        __dummyEnum__197,
        __dummyEnum__198,
        __dummyEnum__199,
        __dummyEnum__200,
        __dummyEnum__201,
        __dummyEnum__202,
        __dummyEnum__203,
        __dummyEnum__204,
        __dummyEnum__205,
        __dummyEnum__206,
        __dummyEnum__207,
        __dummyEnum__208,
        __dummyEnum__209,
        __dummyEnum__210,
        __dummyEnum__211,
        __dummyEnum__212,
        __dummyEnum__213,
        __dummyEnum__214,
        __dummyEnum__215,
        Fib2000,
        __dummyEnum__216,
        __dummyEnum__217,
        __dummyEnum__218,
        __dummyEnum__219,
        __dummyEnum__220,
        __dummyEnum__221,
        __dummyEnum__222,
        __dummyEnum__223,
        __dummyEnum__224,
        __dummyEnum__225,
        __dummyEnum__226,
        __dummyEnum__227,
        __dummyEnum__228,
        __dummyEnum__229,
        __dummyEnum__230,
        __dummyEnum__231,
        __dummyEnum__232,
        __dummyEnum__233,
        __dummyEnum__234,
        __dummyEnum__235,
        __dummyEnum__236,
        __dummyEnum__237,
        __dummyEnum__238,
        __dummyEnum__239,
        __dummyEnum__240,
        __dummyEnum__241,
        __dummyEnum__242,
        __dummyEnum__243,
        __dummyEnum__244,
        __dummyEnum__245,
        __dummyEnum__246,
        __dummyEnum__247,
        __dummyEnum__248,
        __dummyEnum__249,
        __dummyEnum__250,
        __dummyEnum__251,
        __dummyEnum__252,
        __dummyEnum__253,
        __dummyEnum__254,
        Fib2002,
        __dummyEnum__255,
        __dummyEnum__256,
        __dummyEnum__257,
        __dummyEnum__258,
        __dummyEnum__259,
        __dummyEnum__260,
        __dummyEnum__261,
        __dummyEnum__262,
        __dummyEnum__263,
        __dummyEnum__264,
        Fib2003,
        __dummyEnum__265,
        __dummyEnum__266,
        __dummyEnum__267,
        __dummyEnum__268,
        __dummyEnum__269,
        Fib2007
    }
    public UInt16 wIdent = new UInt16();
    public DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion nFib = DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib1997;
    public UInt16 lid = new UInt16();
    public short pnNext;
    public boolean fDot;
    public boolean fGlsy;
    public boolean fComplex;
    public boolean fHasPic;
    public UInt16 cQuickSaves = new UInt16();
    public boolean fEncrypted;
    public boolean fWhichTblStm;
    public boolean fReadOnlyRecommended;
    public boolean fWriteReservation;
    public boolean fExtChar;
    public boolean fLoadOverwrite;
    public boolean fFarEast;
    public boolean fCrypto;
    public UInt16 nFibBack = new UInt16();
    public int lKey;
    public byte envr;
    public boolean fMac;
    public boolean fEmptySpecial;
    public boolean fLoadOverridePage;
    public boolean fFutureSavedUndo;
    public boolean fWord97Saved;
    public int fcMin;
    public int fcMac;
    public short lidFE;
    public int cbMac;
    public int ccpText;
    public int ccpFtn;
    public int ccpHdr;
    public int ccpAtn;
    public int ccpEdn;
    public int ccpTxbx;
    public int ccpHdrTxbx;
    public long fcStshfOrig;
    public long lcbStshfOrig;
    public long fcStshf;
    public long lcbStshf;
    public long fcPlcffndRef;
    public long lcbPlcffndRef;
    public long fcPlcffndTxt;
    public long lcbPlcffndTxt;
    public long fcPlcfandRef;
    public long lcbPlcfandRef;
    public long fcPlcfandTxt;
    public long lcbPlcfandTxt;
    public long fcPlcfSed;
    public long lcbPlcfSed;
    public long fcPlcPad;
    public long lcbPlcPad;
    public long fcPlcfPhe;
    public long lcbPlcfPhe;
    public long fcSttbfGlsy;
    public long lcbSttbfGlsy;
    public long fcPlcfGlsy;
    public long lcbPlcfGlsy;
    public long fcPlcfHdd;
    public long lcbPlcfHdd;
    public long fcPlcfBteChpx;
    public long lcbPlcfBteChpx;
    public long fcPlcfBtePapx;
    public long lcbPlcfBtePapx;
    public long fcPlcfSea;
    public long lcbPlcfSea;
    public long fcSttbfFfn;
    public long lcbSttbfFfn;
    public long fcPlcfFldMom;
    public long lcbPlcfFldMom;
    public long fcPlcfFldHdr;
    public long lcbPlcfFldHdr;
    public long fcPlcfFldFtn;
    public long lcbPlcfFldFtn;
    public long fcPlcfFldAtn;
    public long lcbPlcfFldAtn;
    public long fcPlcfFldMcr;
    public long lcbPlcfFldMcr;
    public long fcSttbfBkmk;
    public long lcbSttbfBkmk;
    public long fcPlcfBkf;
    public long lcbPlcfBkf;
    public long fcPlcfBkl;
    public long lcbPlcfBkl;
    public long fcCmds;
    public long lcbCmds;
    public long fcSttbfMcr;
    public long lcbSttbfMcr;
    public long fcPrDrvr;
    public long lcbPrDrvr;
    public long fcPrEnvPort;
    public long lcbPrEnvPort;
    public long fcPrEnvLand;
    public long lcbPrEnvLand;
    public long fcWss;
    public long lcbWss;
    public long fcDop;
    public long lcbDop;
    public long fcSttbfAssoc;
    public long lcbSttbfAssoc;
    public long fcClx;
    public long lcbClx;
    public long fcPlcfPgdFtn;
    public long lcbPlcfPgdFtn;
    public long fcAutosaveSource;
    public long lcbAutosaveSource;
    public long fcGrpXstAtnOwners;
    public long lcbGrpXstAtnOwners;
    public long fcSttbfAtnBkmk;
    public long lcbSttbfAtnBkmk;
    public long fcPlcSpaMom;
    public long lcbPlcSpaMom;
    public long fcPlcSpaHdr;
    public long lcbPlcSpaHdr;
    public long fcPlcfAtnBkf;
    public long lcbPlcfAtnBkf;
    public long fcPlcfAtnBkl;
    public long lcbPlcfAtnBkl;
    public long fcPms;
    public long lcbPms;
    public long fcFormFldSttbs;
    public long lcbFormFldSttbs;
    public long fcPlcfendRef;
    public long lcbPlcfendRef;
    public long fcPlcfendTxt;
    public long lcbPlcfendTxt;
    public long fcPlcfFldEdn;
    public long lcbPlcfFldEdn;
    public long fcDggInfo;
    public long lcbDggInfo;
    public long fcSttbfRMark;
    public long lcbSttbfRMark;
    public long fcSttbfCaption;
    public long lcbSttbfCaption;
    public long fcSttbfAutoCaption;
    public long lcbSttbfAutoCaption;
    public long fcPlcfWkb;
    public long lcbPlcfWkb;
    public long fcPlcfSpl;
    public long lcbPlcfSpl;
    public long fcPlcftxbxTxt;
    public long lcbPlcftxbxTxt;
    public long fcPlcfFldTxbx;
    public long lcbPlcfFldTxbx;
    public long fcPlcfHdrtxbxTxt;
    public long lcbPlcfHdrtxbxTxt;
    public long fcPlcffldHdrTxbx;
    public long lcbPlcffldHdrTxbx;
    public long fcStwUser;
    public long lcbStwUser;
    public long fcSttbTtmbd;
    public long lcbSttbTtmbd;
    public long fcCookieData;
    public long lcbCookieData;
    public long fcPgdMotherOldOld;
    public long lcbPgdMotherOldOld;
    public long fcBkdMotherOldOld;
    public long lcbBkdMotherOldOld;
    public long fcPgdFtnOldOld;
    public long lcbPgdFtnOldOld;
    public long fcBkdFtnOldOld;
    public long lcbBkdFtnOldOld;
    public long fcPgdEdnOldOld;
    public long lcbPgdEdnOldOld;
    public long fcBkdEdnOldOld;
    public long lcbBkdEdnOldOld;
    public long fcSttbfIntlFld;
    public long lcbSttbfIntlFld;
    public long fcRouteSlip;
    public long lcbRouteSlip;
    public long fcSttbSavedBy;
    public long lcbSttbSavedBy;
    public long fcSttbFnm;
    public long lcbSttbFnm;
    public long fcPlfLst;
    public long lcbPlfLst;
    public long fcPlfLfo;
    public long lcbPlfLfo;
    public long fcPlcfTxbxBkd;
    public long lcbPlcfTxbxBkd;
    public long fcPlcfTxbxHdrBkd;
    public long lcbPlcfTxbxHdrBkd;
    public long fcDocUndoWord9;
    public long lcbDocUndoWord9;
    public long fcRgbUse;
    public long lcbRgbUse;
    public long fcUsp;
    public long lcbUsp;
    public long fcUskf;
    public long lcbUskf;
    public long fcPlcupcRgbUse;
    public long lcbPlcupcRgbUse;
    public long fcPlcupcUsp;
    public long lcbPlcupcUsp;
    public long fcSttbGlsyStyle;
    public long lcbSttbGlsyStyle;
    public long fcPlgosl;
    public long lcbPlgosl;
    public long fcPlcocx;
    public long lcbPlcocx;
    public long fcPlcfBteLvc;
    public long lcbPlcfBteLvc;
    public long dwLowDateTime;
    public long dwHighDateTime;
    public long fcPlcfLvcPre10;
    public long lcbPlcfLvcPre10;
    public long fcPlcfAsumy;
    public long lcbPlcfAsumy;
    public long fcPlcfGram;
    public long lcbPlcfGram;
    public long fcSttbListNames;
    public long lcbSttbListNames;
    public long fcSttbfUssr;
    public long lcbSttbfUssr;
    public long fcPlcfTch;
    public long lcbPlcfTch;
    public long fcRmdThreading;
    public long lcbRmdThreading;
    public long fcMid;
    public long lcbMid;
    public long fcSttbRgtplc;
    public long lcbSttbRgtplc;
    public long fcMsoEnvelope;
    public long lcbMsoEnvelope;
    public long fcPlcfLad;
    public long lcbPlcfLad;
    public long fcRgDofr;
    public long lcbRgDofr;
    public long fcPlcosl;
    public long lcbPlcosl;
    public long fcPlcfCookieOld;
    public long lcbPlcfCookieOld;
    public long fcPgdMotherOld;
    public long lcbPgdMotherOld;
    public long fcBkdMotherOld;
    public long lcbBkdMotherOld;
    public long fcPgdFtnOld;
    public long lcbPgdFtnOld;
    public long fcBkdFtnOld;
    public long lcbBkdFtnOld;
    public long fcPgdEdnOld;
    public long lcbPgdEdnOld;
    public long fcBkdEdnOld;
    public long lcbBkdEdnOld;
    public long fcPlcfPgp;
    public long lcbPlcfPgp;
    public long fcPlcfuim;
    public long lcbPlcfuim;
    public long fcPlfguidUim;
    public long lcbPlfguidUim;
    public long fcAtrdExtra;
    public long lcbAtrdExtra;
    public long fcPlrsid;
    public long lcbPlrsid;
    public long fcSttbfBkmkFactoid;
    public long lcbSttbfBkmkFactoid;
    public long fcPlcfBkfFactoid;
    public long lcbPlcfBkfFactoid;
    public long fcPlcfcookie;
    public long lcbPlcfcookie;
    public long fcPlcfBklFactoid;
    public long lcbPlcfBklFactoid;
    public long fcFactoidData;
    public long lcbFactoidData;
    public long fcDocUndo;
    public long lcbDocUndo;
    public long fcSttbfBkmkFcc;
    public long lcbSttbfBkmkFcc;
    public long fcPlcfBkfFcc;
    public long lcbPlcfBkfFcc;
    public long fcPlcfBklFcc;
    public long lcbPlcfBklFcc;
    public long fcSttbfbkmkBPRepairs;
    public long lcbSttbfbkmkBPRepairs;
    public long fcPlcfbkfBPRepairs;
    public long lcbPlcfbkfBPRepairs;
    public long fcPlcfbklBPRepairs;
    public long lcbPlcfbklBPRepairs;
    public long fcPmsNew;
    public long lcbPmsNew;
    public long fcODSO;
    public long lcbODSO;
    public long fcPlcfpmiOldXP;
    public long lcbPlcfpmiOldXP;
    public long fcPlcfpmiNewXP;
    public long lcbPlcfpmiNewXP;
    public long fcPlcfpmiMixedXP;
    public long lcbPlcfpmiMixedXP;
    public long fcPlcffactoid;
    public long lcbPlcffactoid;
    public long fcPlcflvcOldXP;
    public long lcbPlcflvcOldXP;
    public long fcPlcflvcNewXP;
    public long lcbPlcflvcNewXP;
    public long fcPlcflvcMixedXP;
    public long lcbPlcflvcMixedXP;
    public long fcHplxsdr;
    public long lcbHplxsdr;
    public long fcSttbfBkmkSdt;
    public long lcbSttbfBkmkSdt;
    public long fcPlcfBkfSdt;
    public long lcbPlcfBkfSdt;
    public long fcPlcfBklSdt;
    public long lcbPlcfBklSdt;
    public long fcCustomXForm;
    public long lcbCustomXForm;
    public long fcSttbfBkmkProt;
    public long lcbSttbfBkmkProt;
    public long fcPlcfBkfProt;
    public long lcbPlcfBkfProt;
    public long fcPlcfBklProt;
    public long lcbPlcfBklProt;
    public long fcSttbProtUser;
    public long lcbSttbProtUser;
    public long fcPlcfpmiOld;
    public long lcbPlcfpmiOld;
    public long fcPlcfpmiOldInline;
    public long lcbPlcfpmiOldInline;
    public long fcPlcfpmiNew;
    public long lcbPlcfpmiNew;
    public long fcPlcfpmiNewInline;
    public long lcbPlcfpmiNewInline;
    public long fcPlcflvcOld;
    public long lcbPlcflvcOld;
    public long fcPlcflvcOldInline;
    public long lcbPlcflvcOldInline;
    public long fcPlcflvcNew;
    public long lcbPlcflvcNew;
    public long fcPlcflvcNewInline;
    public long lcbPlcflvcNewInline;
    public long fcPgdMother;
    public long lcbPgdMother;
    public long fcBkdMother;
    public long lcbBkdMother;
    public long fcAfdMother;
    public long lcbAfdMother;
    public long fcPgdFtn;
    public long lcbPgdFtn;
    public long fcBkdFtn;
    public long lcbBkdFtn;
    public long fcAfdFtn;
    public long lcbAfdFtn;
    public long fcPgdEdn;
    public long lcbPgdEdn;
    public long fcBkdEdn;
    public long lcbBkdEdn;
    public long fcAfdEdn;
    public long lcbAfdEdn;
    public long fcAfd;
    public long lcbAfd;
    public long fcPlcfmthd;
    public long lcbPlcfmthd;
    public long fcSttbfBkmkMoveFrom;
    public long lcbSttbfBkmkMoveFrom;
    public long fcPlcfBkfMoveFrom;
    public long lcbPlcfBkfMoveFrom;
    public long fcPlcfBklMoveFrom;
    public long lcbPlcfBklMoveFrom;
    public long fcSttbfBkmkMoveTo;
    public long lcbSttbfBkmkMoveTo;
    public long fcPlcfBkfMoveTo;
    public long lcbPlcfBkfMoveTo;
    public long fcPlcfBklMoveTo;
    public long lcbPlcfBklMoveTo;
    public long fcSttbfBkmkArto;
    public long lcbSttbfBkmkArto;
    public long fcPlcfBkfArto;
    public long lcbPlcfBkfArto;
    public long fcPlcfBklArto;
    public long lcbPlcfBklArto;
    public long fcArtoData;
    public long lcbArtoData;
    public long fcOssTheme;
    public long lcbOssTheme;
    public long fcColorSchemeMapping;
    public long lcbColorSchemeMapping;
    public DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion nFibNew = DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib1997;
    public UInt16 cQuickSavesNew = new UInt16();
    public UInt16 csw = new UInt16();
    public UInt16 cslw = new UInt16();
    public UInt16 cbRgFcLcb = new UInt16();
    public UInt16 cswNew = new UInt16();
    //*****************************************************************************************
    //                                                                              CONSTRUCTOR
    //*****************************************************************************************
    public FileInformationBlock(VirtualStreamReader reader) throws Exception {
        UInt16 flag16 = 0;
        byte flag8 = 0;
        //read the FIB base
        this.wIdent = reader.readUInt16();
        this.nFib = (DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion)reader.readUInt16();
        reader.readBytes(2);
        this.lid = reader.readUInt16();
        this.pnNext = reader.readInt16();
        flag16 = reader.readUInt16();
        this.fDot = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x0001);
        this.fGlsy = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x0002);
        this.fComplex = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x0002);
        this.fHasPic = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x0008);
        this.cQuickSaves = (UInt16)(((int)flag16 & 0x00F0) >> 4);
        this.fEncrypted = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x0100);
        this.fWhichTblStm = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x0200);
        this.fReadOnlyRecommended = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x0400);
        this.fWriteReservation = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x0800);
        this.fExtChar = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x1000);
        this.fLoadOverwrite = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x2000);
        this.fFarEast = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x4000);
        this.fCrypto = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag16,0x8000);
        this.nFibBack = reader.readUInt16();
        this.lKey = reader.readInt32();
        this.envr = reader.readByte();
        flag8 = reader.readByte();
        this.fMac = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag8,0x01);
        this.fEmptySpecial = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag8,0x02);
        this.fLoadOverridePage = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag8,0x04);
        this.fFutureSavedUndo = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag8,0x08);
        this.fWord97Saved = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flag8,0x10);
        reader.readBytes(4);
        this.fcMin = reader.readInt32();
        this.fcMac = reader.readInt32();
        this.csw = reader.readUInt16();
        //read the RgW97
        reader.readBytes(26);
        this.lidFE = reader.readInt16();
        this.cslw = reader.readUInt16();
        //read the RgLW97
        this.cbMac = reader.readInt32();
        reader.readBytes(8);
        this.ccpText = reader.readInt32();
        this.ccpFtn = reader.readInt32();
        this.ccpHdr = reader.readInt32();
        reader.readBytes(4);
        this.ccpAtn = reader.readInt32();
        this.ccpEdn = reader.readInt32();
        this.ccpTxbx = reader.readInt32();
        this.ccpHdrTxbx = reader.readInt32();
        reader.readBytes(44);
        this.cbRgFcLcb = reader.readUInt16();
        if (this.nFib >= DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib1997)
        {
            //Read the FibRgFcLcb97
            this.fcStshfOrig = reader.readUInt32();
            this.lcbStshfOrig = reader.readUInt32();
            this.fcStshf = reader.readUInt32();
            this.lcbStshf = reader.readUInt32();
            this.fcPlcffndRef = reader.readUInt32();
            this.lcbPlcffndRef = reader.readUInt32();
            this.fcPlcffndTxt = reader.readUInt32();
            this.lcbPlcffndTxt = reader.readUInt32();
            this.fcPlcfandRef = reader.readUInt32();
            this.lcbPlcfandRef = reader.readUInt32();
            this.fcPlcfandTxt = reader.readUInt32();
            this.lcbPlcfandTxt = reader.readUInt32();
            this.fcPlcfSed = reader.readUInt32();
            this.lcbPlcfSed = reader.readUInt32();
            this.fcPlcPad = reader.readUInt32();
            this.lcbPlcPad = reader.readUInt32();
            this.fcPlcfPhe = reader.readUInt32();
            this.lcbPlcfPhe = reader.readUInt32();
            this.fcSttbfGlsy = reader.readUInt32();
            this.lcbSttbfGlsy = reader.readUInt32();
            this.fcPlcfGlsy = reader.readUInt32();
            this.lcbPlcfGlsy = reader.readUInt32();
            this.fcPlcfHdd = reader.readUInt32();
            this.lcbPlcfHdd = reader.readUInt32();
            this.fcPlcfBteChpx = reader.readUInt32();
            this.lcbPlcfBteChpx = reader.readUInt32();
            this.fcPlcfBtePapx = reader.readUInt32();
            this.lcbPlcfBtePapx = reader.readUInt32();
            this.fcPlcfSea = reader.readUInt32();
            this.lcbPlcfSea = reader.readUInt32();
            this.fcSttbfFfn = reader.readUInt32();
            this.lcbSttbfFfn = reader.readUInt32();
            this.fcPlcfFldMom = reader.readUInt32();
            this.lcbPlcfFldMom = reader.readUInt32();
            this.fcPlcfFldHdr = reader.readUInt32();
            this.lcbPlcfFldHdr = reader.readUInt32();
            this.fcPlcfFldFtn = reader.readUInt32();
            this.lcbPlcfFldFtn = reader.readUInt32();
            this.fcPlcfFldAtn = reader.readUInt32();
            this.lcbPlcfFldAtn = reader.readUInt32();
            this.fcPlcfFldMcr = reader.readUInt32();
            this.lcbPlcfFldMcr = reader.readUInt32();
            this.fcSttbfBkmk = reader.readUInt32();
            this.lcbSttbfBkmk = reader.readUInt32();
            this.fcPlcfBkf = reader.readUInt32();
            this.lcbPlcfBkf = reader.readUInt32();
            this.fcPlcfBkl = reader.readUInt32();
            this.lcbPlcfBkl = reader.readUInt32();
            this.fcCmds = reader.readUInt32();
            this.lcbCmds = reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            this.fcSttbfMcr = reader.readUInt32();
            this.lcbSttbfMcr = reader.readUInt32();
            this.fcPrDrvr = reader.readUInt32();
            this.lcbPrDrvr = reader.readUInt32();
            this.fcPrEnvPort = reader.readUInt32();
            this.lcbPrEnvPort = reader.readUInt32();
            this.fcPrEnvLand = reader.readUInt32();
            this.lcbPrEnvLand = reader.readUInt32();
            this.fcWss = reader.readUInt32();
            this.lcbWss = reader.readUInt32();
            this.fcDop = reader.readUInt32();
            this.lcbDop = reader.readUInt32();
            this.fcSttbfAssoc = reader.readUInt32();
            this.lcbSttbfAssoc = reader.readUInt32();
            this.fcClx = reader.readUInt32();
            this.lcbClx = reader.readUInt32();
            this.fcPlcfPgdFtn = reader.readUInt32();
            this.lcbPlcfPgdFtn = reader.readUInt32();
            this.fcAutosaveSource = reader.readUInt32();
            this.lcbAutosaveSource = reader.readUInt32();
            this.fcGrpXstAtnOwners = reader.readUInt32();
            this.lcbGrpXstAtnOwners = reader.readUInt32();
            this.fcSttbfAtnBkmk = reader.readUInt32();
            this.lcbSttbfAtnBkmk = reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            this.fcPlcSpaMom = reader.readUInt32();
            this.lcbPlcSpaMom = reader.readUInt32();
            this.fcPlcSpaHdr = reader.readUInt32();
            this.lcbPlcSpaHdr = reader.readUInt32();
            this.fcPlcfAtnBkf = reader.readUInt32();
            this.lcbPlcfAtnBkf = reader.readUInt32();
            this.fcPlcfAtnBkl = reader.readUInt32();
            this.lcbPlcfAtnBkl = reader.readUInt32();
            this.fcPms = reader.readUInt32();
            this.lcbPms = reader.readUInt32();
            this.fcFormFldSttbs = reader.readUInt32();
            this.lcbFormFldSttbs = reader.readUInt32();
            this.fcPlcfendRef = reader.readUInt32();
            this.lcbPlcfendRef = reader.readUInt32();
            this.fcPlcfendTxt = reader.readUInt32();
            this.lcbPlcfendTxt = reader.readUInt32();
            this.fcPlcfFldEdn = reader.readUInt32();
            this.lcbPlcfFldEdn = reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            this.fcDggInfo = reader.readUInt32();
            this.lcbDggInfo = reader.readUInt32();
            this.fcSttbfRMark = reader.readUInt32();
            this.lcbSttbfRMark = reader.readUInt32();
            this.fcSttbfCaption = reader.readUInt32();
            this.lcbSttbfCaption = reader.readUInt32();
            this.fcSttbfAutoCaption = reader.readUInt32();
            this.lcbSttbfAutoCaption = reader.readUInt32();
            this.fcPlcfWkb = reader.readUInt32();
            this.lcbPlcfWkb = reader.readUInt32();
            this.fcPlcfSpl = reader.readUInt32();
            this.lcbPlcfSpl = reader.readUInt32();
            this.fcPlcftxbxTxt = reader.readUInt32();
            this.lcbPlcftxbxTxt = reader.readUInt32();
            this.fcPlcfFldTxbx = reader.readUInt32();
            this.lcbPlcfFldTxbx = reader.readUInt32();
            this.fcPlcfHdrtxbxTxt = reader.readUInt32();
            this.lcbPlcfHdrtxbxTxt = reader.readUInt32();
            this.fcPlcffldHdrTxbx = reader.readUInt32();
            this.lcbPlcffldHdrTxbx = reader.readUInt32();
            this.fcStwUser = reader.readUInt32();
            this.lcbStwUser = reader.readUInt32();
            this.fcSttbTtmbd = reader.readUInt32();
            this.lcbSttbTtmbd = reader.readUInt32();
            this.fcCookieData = reader.readUInt32();
            this.lcbCookieData = reader.readUInt32();
            this.fcPgdMotherOldOld = reader.readUInt32();
            this.lcbPgdMotherOldOld = reader.readUInt32();
            this.fcBkdMotherOldOld = reader.readUInt32();
            this.lcbBkdMotherOldOld = reader.readUInt32();
            this.fcPgdFtnOldOld = reader.readUInt32();
            this.lcbPgdFtnOldOld = reader.readUInt32();
            this.fcBkdFtnOldOld = reader.readUInt32();
            this.lcbBkdFtnOldOld = reader.readUInt32();
            this.fcPgdEdnOldOld = reader.readUInt32();
            this.lcbPgdEdnOldOld = reader.readUInt32();
            this.fcBkdEdnOldOld = reader.readUInt32();
            this.lcbBkdEdnOldOld = reader.readUInt32();
            this.fcSttbfIntlFld = reader.readUInt32();
            this.lcbSttbfIntlFld = reader.readUInt32();
            this.fcRouteSlip = reader.readUInt32();
            this.lcbRouteSlip = reader.readUInt32();
            this.fcSttbSavedBy = reader.readUInt32();
            this.lcbSttbSavedBy = reader.readUInt32();
            this.fcSttbFnm = reader.readUInt32();
            this.lcbSttbFnm = reader.readUInt32();
            this.fcPlfLst = reader.readUInt32();
            this.lcbPlfLst = reader.readUInt32();
            this.fcPlfLfo = reader.readUInt32();
            this.lcbPlfLfo = reader.readUInt32();
            this.fcPlcfTxbxBkd = reader.readUInt32();
            this.lcbPlcfTxbxBkd = reader.readUInt32();
            this.fcPlcfTxbxHdrBkd = reader.readUInt32();
            this.lcbPlcfTxbxHdrBkd = reader.readUInt32();
            this.fcDocUndoWord9 = reader.readUInt32();
            this.lcbDocUndoWord9 = reader.readUInt32();
            this.fcRgbUse = reader.readUInt32();
            this.lcbRgbUse = reader.readUInt32();
            this.fcUsp = reader.readUInt32();
            this.lcbUsp = reader.readUInt32();
            this.fcUskf = reader.readUInt32();
            this.lcbUskf = reader.readUInt32();
            this.fcPlcupcRgbUse = reader.readUInt32();
            this.lcbPlcupcRgbUse = reader.readUInt32();
            this.fcPlcupcUsp = reader.readUInt32();
            this.lcbPlcupcUsp = reader.readUInt32();
            this.fcSttbGlsyStyle = reader.readUInt32();
            this.lcbSttbGlsyStyle = reader.readUInt32();
            this.fcPlgosl = reader.readUInt32();
            this.lcbPlgosl = reader.readUInt32();
            this.fcPlcocx = reader.readUInt32();
            this.lcbPlcocx = reader.readUInt32();
            this.fcPlcfBteLvc = reader.readUInt32();
            this.lcbPlcfBteLvc = reader.readUInt32();
            this.dwLowDateTime = reader.readUInt32();
            this.dwHighDateTime = reader.readUInt32();
            this.fcPlcfLvcPre10 = reader.readUInt32();
            this.lcbPlcfLvcPre10 = reader.readUInt32();
            this.fcPlcfAsumy = reader.readUInt32();
            this.lcbPlcfAsumy = reader.readUInt32();
            this.fcPlcfGram = reader.readUInt32();
            this.lcbPlcfGram = reader.readUInt32();
            this.fcSttbListNames = reader.readUInt32();
            this.lcbSttbListNames = reader.readUInt32();
            this.fcSttbfUssr = reader.readUInt32();
            this.lcbSttbfUssr = reader.readUInt32();
        }
         
        if (this.nFib >= DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib2000)
        {
            //Read also the FibRgFcLcb2000
            this.fcPlcfTch = reader.readUInt32();
            this.lcbPlcfTch = reader.readUInt32();
            this.fcRmdThreading = reader.readUInt32();
            this.lcbRmdThreading = reader.readUInt32();
            this.fcMid = reader.readUInt32();
            this.lcbMid = reader.readUInt32();
            this.fcSttbRgtplc = reader.readUInt32();
            this.lcbSttbRgtplc = reader.readUInt32();
            this.fcMsoEnvelope = reader.readUInt32();
            this.lcbMsoEnvelope = reader.readUInt32();
            this.fcPlcfLad = reader.readUInt32();
            this.lcbPlcfLad = reader.readUInt32();
            this.fcRgDofr = reader.readUInt32();
            this.lcbRgDofr = reader.readUInt32();
            this.fcPlcosl = reader.readUInt32();
            this.lcbPlcosl = reader.readUInt32();
            this.fcPlcfCookieOld = reader.readUInt32();
            this.lcbPlcfCookieOld = reader.readUInt32();
            this.fcPgdMotherOld = reader.readUInt32();
            this.lcbPgdMotherOld = reader.readUInt32();
            this.fcBkdMotherOld = reader.readUInt32();
            this.lcbBkdMotherOld = reader.readUInt32();
            this.fcPgdFtnOld = reader.readUInt32();
            this.lcbPgdFtnOld = reader.readUInt32();
            this.fcBkdFtnOld = reader.readUInt32();
            this.lcbBkdFtnOld = reader.readUInt32();
            this.fcPgdEdnOld = reader.readUInt32();
            this.lcbPgdEdnOld = reader.readUInt32();
            this.fcBkdEdnOld = reader.readUInt32();
            this.lcbBkdEdnOld = reader.readUInt32();
        }
         
        if (this.nFib >= DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib2002)
        {
            //Read also the fibRgFcLcb2002
            reader.readUInt32();
            reader.readUInt32();
            this.fcPlcfPgp = reader.readUInt32();
            this.lcbPlcfPgp = reader.readUInt32();
            this.fcPlcfuim = reader.readUInt32();
            this.lcbPlcfuim = reader.readUInt32();
            this.fcPlfguidUim = reader.readUInt32();
            this.lcbPlfguidUim = reader.readUInt32();
            this.fcAtrdExtra = reader.readUInt32();
            this.lcbAtrdExtra = reader.readUInt32();
            this.fcPlrsid = reader.readUInt32();
            this.lcbPlrsid = reader.readUInt32();
            this.fcSttbfBkmkFactoid = reader.readUInt32();
            this.lcbSttbfBkmkFactoid = reader.readUInt32();
            this.fcPlcfBkfFactoid = reader.readUInt32();
            this.lcbPlcfBkfFactoid = reader.readUInt32();
            this.fcPlcfcookie = reader.readUInt32();
            this.lcbPlcfcookie = reader.readUInt32();
            this.fcPlcfBklFactoid = reader.readUInt32();
            this.lcbPlcfBklFactoid = reader.readUInt32();
            this.fcFactoidData = reader.readUInt32();
            this.lcbFactoidData = reader.readUInt32();
            this.fcDocUndo = reader.readUInt32();
            this.lcbDocUndo = reader.readUInt32();
            this.fcSttbfBkmkFcc = reader.readUInt32();
            this.lcbSttbfBkmkFcc = reader.readUInt32();
            this.fcPlcfBkfFcc = reader.readUInt32();
            this.lcbPlcfBkfFcc = reader.readUInt32();
            this.fcPlcfBklFcc = reader.readUInt32();
            this.lcbPlcfBklFcc = reader.readUInt32();
            this.fcSttbfbkmkBPRepairs = reader.readUInt32();
            this.lcbSttbfbkmkBPRepairs = reader.readUInt32();
            this.fcPlcfbkfBPRepairs = reader.readUInt32();
            this.lcbPlcfbkfBPRepairs = reader.readUInt32();
            this.fcPlcfbklBPRepairs = reader.readUInt32();
            this.lcbPlcfbklBPRepairs = reader.readUInt32();
            this.fcPmsNew = reader.readUInt32();
            this.lcbPmsNew = reader.readUInt32();
            this.fcODSO = reader.readUInt32();
            this.lcbODSO = reader.readUInt32();
            this.fcPlcfpmiOldXP = reader.readUInt32();
            this.lcbPlcfpmiOldXP = reader.readUInt32();
            this.fcPlcfpmiNewXP = reader.readUInt32();
            this.lcbPlcfpmiNewXP = reader.readUInt32();
            this.fcPlcfpmiMixedXP = reader.readUInt32();
            this.lcbPlcfpmiMixedXP = reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            this.fcPlcffactoid = reader.readUInt32();
            this.lcbPlcffactoid = reader.readUInt32();
            this.fcPlcflvcOldXP = reader.readUInt32();
            this.lcbPlcflvcOldXP = reader.readUInt32();
            this.fcPlcflvcNewXP = reader.readUInt32();
            this.lcbPlcflvcNewXP = reader.readUInt32();
            this.fcPlcflvcMixedXP = reader.readUInt32();
            this.lcbPlcflvcMixedXP = reader.readUInt32();
        }
         
        if (this.nFib >= DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib2003)
        {
            //Read also the fibRgFcLcb2003
            this.fcHplxsdr = reader.readUInt32();
            this.lcbHplxsdr = reader.readUInt32();
            this.fcSttbfBkmkSdt = reader.readUInt32();
            this.lcbSttbfBkmkSdt = reader.readUInt32();
            this.fcPlcfBkfSdt = reader.readUInt32();
            this.lcbPlcfBkfSdt = reader.readUInt32();
            this.fcPlcfBklSdt = reader.readUInt32();
            this.lcbPlcfBklSdt = reader.readUInt32();
            this.fcCustomXForm = reader.readUInt32();
            this.lcbCustomXForm = reader.readUInt32();
            this.fcSttbfBkmkProt = reader.readUInt32();
            this.lcbSttbfBkmkProt = reader.readUInt32();
            this.fcPlcfBkfProt = reader.readUInt32();
            this.lcbPlcfBkfProt = reader.readUInt32();
            this.fcPlcfBklProt = reader.readUInt32();
            this.lcbPlcfBklProt = reader.readUInt32();
            this.fcSttbProtUser = reader.readUInt32();
            this.lcbSttbProtUser = reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            this.fcPlcfpmiOld = reader.readUInt32();
            this.lcbPlcfpmiOld = reader.readUInt32();
            this.fcPlcfpmiOldInline = reader.readUInt32();
            this.lcbPlcfpmiOldInline = reader.readUInt32();
            this.fcPlcfpmiNew = reader.readUInt32();
            this.lcbPlcfpmiNew = reader.readUInt32();
            this.fcPlcfpmiNewInline = reader.readUInt32();
            this.lcbPlcfpmiNewInline = reader.readUInt32();
            this.fcPlcflvcOld = reader.readUInt32();
            this.lcbPlcflvcOld = reader.readUInt32();
            this.fcPlcflvcOldInline = reader.readUInt32();
            this.lcbPlcflvcOldInline = reader.readUInt32();
            this.fcPlcflvcNew = reader.readUInt32();
            this.lcbPlcflvcNew = reader.readUInt32();
            this.fcPlcflvcNewInline = reader.readUInt32();
            this.lcbPlcflvcNewInline = reader.readUInt32();
            this.fcPgdMother = reader.readUInt32();
            this.lcbPgdMother = reader.readUInt32();
            this.fcBkdMother = reader.readUInt32();
            this.lcbBkdMother = reader.readUInt32();
            this.fcAfdMother = reader.readUInt32();
            this.lcbAfdMother = reader.readUInt32();
            this.fcPgdFtn = reader.readUInt32();
            this.lcbPgdFtn = reader.readUInt32();
            this.fcBkdFtn = reader.readUInt32();
            this.lcbBkdFtn = reader.readUInt32();
            this.fcAfdFtn = reader.readUInt32();
            this.lcbAfdFtn = reader.readUInt32();
            this.fcPgdEdn = reader.readUInt32();
            this.lcbPgdEdn = reader.readUInt32();
            this.fcBkdEdn = reader.readUInt32();
            this.lcbBkdEdn = reader.readUInt32();
            this.fcAfdEdn = reader.readUInt32();
            this.lcbAfdEdn = reader.readUInt32();
            this.fcAfd = reader.readUInt32();
            this.lcbAfd = reader.readUInt32();
        }
         
        if (this.nFib >= DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib2007)
        {
            //Read also the fibRgFcLcb2007
            this.fcPlcfmthd = reader.readUInt32();
            this.lcbPlcfmthd = reader.readUInt32();
            this.fcSttbfBkmkMoveFrom = reader.readUInt32();
            this.lcbSttbfBkmkMoveFrom = reader.readUInt32();
            this.fcPlcfBkfMoveFrom = reader.readUInt32();
            this.lcbPlcfBkfMoveFrom = reader.readUInt32();
            this.fcPlcfBklMoveFrom = reader.readUInt32();
            this.lcbPlcfBklMoveFrom = reader.readUInt32();
            this.fcSttbfBkmkMoveTo = reader.readUInt32();
            this.lcbSttbfBkmkMoveTo = reader.readUInt32();
            this.fcPlcfBkfMoveTo = reader.readUInt32();
            this.lcbPlcfBkfMoveTo = reader.readUInt32();
            this.fcPlcfBklMoveTo = reader.readUInt32();
            this.lcbPlcfBklMoveTo = reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            this.fcSttbfBkmkArto = reader.readUInt32();
            this.lcbSttbfBkmkArto = reader.readUInt32();
            this.fcPlcfBkfArto = reader.readUInt32();
            this.lcbPlcfBkfArto = reader.readUInt32();
            this.fcPlcfBklArto = reader.readUInt32();
            this.lcbPlcfBklArto = reader.readUInt32();
            this.fcArtoData = reader.readUInt32();
            this.lcbArtoData = reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            reader.readUInt32();
            this.fcOssTheme = reader.readUInt32();
            this.lcbOssTheme = reader.readUInt32();
            this.fcColorSchemeMapping = reader.readUInt32();
            this.lcbColorSchemeMapping = reader.readUInt32();
        }
         
        this.cswNew = reader.readUInt16();
        if (this.cswNew != 0)
        {
            //Read the FibRgCswNew
            this.nFibNew = (DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion)reader.readUInt16();
            this.cQuickSavesNew = reader.readUInt16();
        }
         
    }

}


