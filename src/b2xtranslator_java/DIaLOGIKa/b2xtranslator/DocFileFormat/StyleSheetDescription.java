//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:08 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TablePropertyExceptions;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;

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
public class StyleSheetDescription   
{
    public enum StyleKind
    {
        __dummyEnum__0,
        paragraph,
        character,
        table,
        list
    }
    public enum StyleIdentifier
    {
        Normal,
        Heading1,
        Heading2,
        Heading3,
        Heading4,
        Heading5,
        Heading6,
        Heading7,
        Heading8,
        Heading9,
        Index1,
        Index2,
        Index3,
        Index4,
        Index5,
        Index6,
        Index7,
        Index8,
        Index9,
        TOC1,
        TOC2,
        TOC3,
        TOC4,
        TOC5,
        TOC6,
        TOC7,
        TOC8,
        TOC9,
        NormalIndent,
        FootnoteText,
        AnnotationText,
        Header,
        Footer,
        IndexHeading,
        Caption,
        ToCaption,
        EnvelopeAddress,
        EnvelopeReturn,
        FootnoteReference,
        AnnotationReference,
        LineNumber,
        PageNumber,
        EndnoteReference,
        EndnoteText,
        TableOfAuthoring,
        Macro,
        TOAHeading,
        List,
        ListBullet,
        ListNumber,
        List2,
        List3,
        List4,
        List5,
        ListBullet2,
        ListBullet3,
        ListBullet4,
        ListBullet5,
        ListNumber2,
        ListNumber3,
        ListNumber4,
        ListNumber5,
        Title,
        Closing,
        Signature,
        NormalCharacter,
        BodyText,
        BodyTextIndent,
        ListContinue,
        ListContinue2,
        ListContinue3,
        ListContinue4,
        ListContinue5,
        MessageHeader,
        Subtitle,
        Salutation,
        Date,
        BodyText1I,
        BodyText1I2,
        NoteHeading,
        BodyText2,
        BodyText3,
        BodyTextIndent2,
        BodyTextIndent3,
        BlockQuote,
        Hyperlink,
        FollowedHyperlink,
        Strong,
        Emphasis,
        NavPane,
        PlainText,
        AutoSignature,
        FormTop,
        FormBottom,
        HtmlNormal,
        HtmlAcronym,
        HtmlAddress,
        HtmlCite,
        HtmlCode,
        HtmlDfn,
        HtmlKbd,
        HtmlPre,
        htmlSamp,
        HtmlTt,
        HtmlVar,
        TableNormal,
        AnnotationSubject,
        NormalList,
        OutlineList1,
        OutlineList2,
        OutlineList3,
        TableSimple,
        TableSimple2,
        TableSimple3,
        TableClassic1,
        TableClassic2,
        TableClassic3,
        TableClassic4,
        TableColorful1,
        TableColorful2,
        TableColorful3,
        TableColumns1,
        TableColumns2,
        TableColumns3,
        TableColumns4,
        TableColumns5,
        TableGrid1,
        TableGrid2,
        TableGrid3,
        TableGrid4,
        TableGrid5,
        TableGrid6,
        TableGrid7,
        TableGrid8,
        TableList1,
        TableList2,
        TableList3,
        TableList4,
        TableList5,
        TableList6,
        TableList7,
        TableList8,
        Table3DFx1,
        Table3DFx2,
        Table3DFx3,
        TableContemporary,
        TableElegant,
        TableProfessional,
        TableSubtle1,
        tableSubtle2,
        TableWeb1,
        TableWeb2,
        TableWeb3,
        Acetate,
        TableGrid,
        TableTheme,
        Max,
        User,
        Null
    }
    /**
    * The name of the style
    */
    public String xstzName;
    /**
    * Invariant style identifier
    */
    public StyleIdentifier sti = StyleIdentifier.Normal;
    /**
    * spare field for any temporary use, always reset back to zero!
    */
    public boolean fScratch;
    /**
    * PHEs of all text with this style are wrong
    */
    public boolean fInvalHeight;
    /**
    * UPEs have been generated
    */
    public boolean fHasUpe;
    /**
    * std has been mass-copied; if unused at save time,
    * style should be deleted
    */
    public boolean fMassCopy;
    /**
    * style kind
    */
    public StyleKind stk = StyleKind.paragraph;
    /**
    * base style
    */
    public long istdBase;
    /**
    * number of UPXs (and UPEs)
    */
    public UInt16 cupx = new UInt16();
    /**
    * next style
    */
    public long istdNext;
    /**
    * offset to end of upx's, start of upe's
    */
    public UInt16 bchUpe = new UInt16();
    /**
    * auto redefine style when appropriate
    */
    public boolean fAutoRedef;
    /**
    * hidden from UI?
    */
    public boolean fHidden;
    /**
    * style already has valid sprmCRgLidX_80 in it
    */
    public boolean f97LidsSet;
    /**
    * if f97LidsSet, says whether we copied the lid from sprmCRgLidX
    * into sprmCRgLidX_80 or whether we gotrid of sprmCRgLidX_80
    */
    public boolean fCopyLang;
    /**
    * HTML Threading compose style
    */
    public boolean fPersonalCompose;
    /**
    * HTML Threading reply style
    */
    public boolean fPersonalReply;
    /**
    * HTML Threading - another user's personal style
    */
    public boolean fPersonal;
    /**
    * Do not export this style to HTML/CSS
    */
    public boolean fNoHtmlExport;
    /**
    * Do not show this style in long style lists
    */
    public boolean fSemiHidden;
    /**
    * Locked style?
    */
    public boolean fLocked;
    /**
    * Style is used by a word feature, e.g. footnote
    */
    public boolean fInternalUse;
    /**
    * Is this style linked to another?
    */
    public long istdLink;
    /**
    * Style has RevMarking history
    */
    public boolean fHasOriginalStyle;
    /**
    * marks during merge which doc's style changed
    */
    public long rsid;
    /**
    * used temporarily during html export
    */
    public long iftcHtml;
    /**
    * A StyleSheetDescription can have a PAPX. 
    * If the style doesn't modify paragraph properties, papx is null.
    */
    public ParagraphPropertyExceptions papx;
    /**
    * A StyleSheetDescription can have a CHPX. 
    * If the style doesn't modify character properties, chpx is null.
    */
    public CharacterPropertyExceptions chpx;
    /**
    * A StyleSheetDescription can have a TAPX. 
    * If the style doesn't modify table properties, tapx is null.
    */
    public TablePropertyExceptions tapx;
    /**
    * Creates an empty STD object
    */
    public StyleSheetDescription() throws Exception {
    }

    /**
    * Parses the bytes to retrieve a StyleSheetDescription
    * 
    *  @param bytes The bytes
    *  @param cbStdBase 
    *  @param dataStream The "Data" stream (optional, can be null)
    */
    public StyleSheetDescription(byte[] bytes, int cbStdBase, VirtualStream dataStream) throws Exception {
        BitArray bits = new BitArray(bytes);
        //parsing the base (fix part)
        if (cbStdBase >= 2)
        {
            //sti
            BitArray stiBits = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,0,12);
            this.sti = (StyleIdentifier)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(stiBits);
            //flags
            this.fScratch = bits[12];
            this.fInvalHeight = bits[13];
            this.fHasUpe = bits[14];
            this.fMassCopy = bits[15];
        }
         
        if (cbStdBase >= 4)
        {
            //stk
            BitArray stkBits = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,16,4);
            this.stk = (StyleKind)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(stkBits);
            //istdBase
            BitArray istdBits = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,20,12);
            this.istdBase = (long)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(istdBits);
        }
         
        if (cbStdBase >= 6)
        {
            //cupx
            BitArray cupxBits = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,32,4);
            this.cupx = (UInt16)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(cupxBits);
            //istdNext
            BitArray istdNextBits = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,36,12);
            this.istdNext = (long)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(istdNextBits);
        }
         
        if (cbStdBase >= 8)
        {
            //bchUpe
            BitArray bchBits = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,48,16);
            this.bchUpe = (UInt16)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(bchBits);
        }
         
        if (cbStdBase >= 10)
        {
            //flags
            this.fAutoRedef = bits[64];
            this.fHidden = bits[65];
            this.f97LidsSet = bits[66];
            this.fCopyLang = bits[67];
            this.fPersonalCompose = bits[68];
            this.fPersonalReply = bits[69];
            this.fPersonal = bits[70];
            this.fNoHtmlExport = bits[71];
            this.fSemiHidden = bits[72];
            this.fLocked = bits[73];
            this.fInternalUse = bits[74];
        }
         
        if (cbStdBase >= 12)
        {
            //istdLink
            BitArray istdLinkBits = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,80,12);
            this.istdLink = (long)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(istdLinkBits);
            //fHasOriginalStyle
            this.fHasOriginalStyle = bits[92];
        }
         
        if (cbStdBase >= 16)
        {
            //rsid
            BitArray rsidBits = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,96,32);
            this.rsid = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(rsidBits);
        }
         
        //parsing the variable part
        //xstz
        byte characterCount = bytes[cbStdBase];
        //characters are zero-terminated, so 1 char has 2 bytes:
        byte[] name = new byte[characterCount * 2];
        Array.Copy(bytes, cbStdBase + 2, name, 0, name.length);
        //remove zero-termination
        this.xstzName = Encoding.Unicode.GetString(name);
        //parse the UPX structs
        int upxOffset = cbStdBase + 1 + (characterCount * 2) + 2;
        for (int i = 0;i < this.cupx;i++)
        {
            //find the next even byte border
            if (upxOffset % 2 != 0)
            {
                upxOffset++;
            }
             
            //get the count of bytes for UPX
            UInt16 cbUPX = System.BitConverter.ToUInt16(bytes, upxOffset);
            if (cbUPX > 0)
            {
                //copy the bytes
                byte[] upxBytes = new byte[cbUPX];
                Array.Copy(bytes, upxOffset + 2, upxBytes, 0, upxBytes.length);
                if (this.stk == StyleKind.table)
                {
                    //first upx is TAPX; second PAPX, third CHPX
                    switch(i)
                    {
                        case 0: 
                            this.tapx = new TablePropertyExceptions(upxBytes);
                            break;
                        case 1: 
                            this.papx = new ParagraphPropertyExceptions(upxBytes,dataStream);
                            break;
                        case 2: 
                            this.chpx = new CharacterPropertyExceptions(upxBytes);
                            break;
                    
                    }
                }
                else if (this.stk == StyleKind.paragraph)
                {
                    //first upx is PAPX, second CHPX
                    switch(i)
                    {
                        case 0: 
                            this.papx = new ParagraphPropertyExceptions(upxBytes,dataStream);
                            break;
                        case 1: 
                            this.chpx = new CharacterPropertyExceptions(upxBytes);
                            break;
                    
                    }
                }
                else if (this.stk == StyleKind.list)
                {
                    //list styles have only one PAPX
                    switch(i)
                    {
                        case 0: 
                            this.papx = new ParagraphPropertyExceptions(upxBytes,dataStream);
                            break;
                    
                    }
                }
                else if (this.stk == StyleKind.character)
                {
                    //character styles have only one CHPX
                    switch(i)
                    {
                        case 0: 
                            this.chpx = new CharacterPropertyExceptions(upxBytes);
                            break;
                    
                    }
                }
                    
            }
             
            //increase the offset for the next run
            upxOffset += (2 + cbUPX);
        }
    }

}


