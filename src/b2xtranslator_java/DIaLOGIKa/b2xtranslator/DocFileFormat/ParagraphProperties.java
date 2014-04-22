//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:05 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.AutoNumberedListDataDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DateAndTime;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DropCapSpecifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPagePAPX;
import DIaLOGIKa.b2xtranslator.DocFileFormat.LineSpacingDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.NumberRevisionMarkData;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphHeight;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphProperties;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ShadingDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TabDescriptor;

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
public class ParagraphProperties   implements IVisitable
{
    public enum JustificationCode
    {
        left,
        center,
        right,
        both,
        distribute,
        mediumKashida,
        numTab,
        highKashida,
        lowKashida,
        thaiDistribute
    }
    /**
    * Index the style descriptor. This is an index to an STD in the STSH structure
    */
    public UInt16 istd = new UInt16();
    /**
    * When 1, paragraph is a side by side paragraph
    */
    public boolean fSideBySide;
    /**
    * Keep entire paragraph on one page if possible
    */
    public boolean fKeep;
    /**
    * Keep paragraph on same page with next paragraph if possible
    */
    public boolean fKeepFollow;
    /**
    * Start this paragraph on new page
    */
    public boolean fPageBreakBefore;
    /**
    * Border line style
    * 0 single
    * 1 thick
    * 2 double
    * 3 shadow
    */
    public byte brcl;
    /**
    * Rectangle boder codes
    * 0 none
    * 1 border above
    * 2 border below
    * 15 box around
    * 16 bar to left of paragraph
    */
    public byte brcp;
    /**
    * When non-zero, list level for this paragraph
    */
    public byte ilvl;
    /**
    * When non-zero, (1-based) index intothe pllfo identifiying the
    * list to which the paragraph belongs
    */
    public byte ilfo;
    /**
    * No line numbering for this paragraph.
    */
    public boolean fNoLynn;
    /**
    * Space before paragraph
    */
    public long dyaBefore;
    /**
    * Space after paragraph
    */
    public long dyaAfter;
    /**
    * Paragraph is in table (archaic)
    */
    public boolean fInTableW97;
    /**
    * Table trailer paragraph (last paragraph in table row)
    */
    public boolean fTtp;
    /**
    * When positive, is the horizontal distance from the
    * reference frame specified bypap.pcHorz.
    * 0 means paragraph is positioned at the left with respect to the
    * reference frame specified by pcHorz.
    * Certain negative values have special meaning:
    * -4 paragraph centered horizontal within reference frame
    * -8 paragraph adjusted right within reference frame
    * -12 paragraph placed immediately inside of reference frame
    * -16 paragraph placed immediately outside of reference frame
    */
    public int dxaAbs;
    /**
    * When positive, is the vertical distance from the reference
    * frame specified by pcVert.
    * 0 means paragraph's y-position is unconstrained.
    * Certain negative values have special meaning:
    * -4 paragraph is placed at top of reference frame
    * -8 paragraph is centered vertically within reference frame
    * -12 paragraph is placed at bottom of reference frame
    */
    public int dyaAbs;
    /**
    * When not 0, paragraph is constrained to be dxaWidth wide,
    * independent of current margin or column settings.
    */
    public int dxaWidth;
    /**
    * 
    */
    public boolean fBrLnAbove;
    /**
    * 
    */
    public boolean fBrLnBelow;
    /**
    * Vertical position code. Specifies coodrinate frame to use when
    * paragraphs are absolutely positioned.
    * 0 vertical position coordinates are relative to margin
    * 1 coordinates are relative to page
    * 2 coordinates are relative to text. This means: relative to where
    * the nex non-APO text would have been placed if this APO did not exist
    */
    public byte pcVert;
    /**
    * Horizontal position code. Specifies coodrinate frame to use when
    * paragraphs are absolutely positioned.
    * 0 horizontal position coordinates are relative to column
    * 1 coordinates are relative to column
    * 2 coordinates are relative to page
    */
    public byte pcHorz;
    /**
    * Wrap code for absolute objects
    */
    public byte wr;
    /**
    * When fasle, text in paragraph may be auto hyphenated
    */
    public boolean fNoAutoHyph;
    /**
    * Height when 0 == Auto
    */
    public UInt16 wHeightAbs = new UInt16();
    /**
    * Height of abs obj; 0 = Auto
    */
    public long dyaHeight;
    /**
    * Minimum height is exact or auto:
    * false = exact
    * true = at least
    */
    public boolean fMinHeight;
    /**
    * Vertical distance between text and absolutely positioned object
    */
    public int dyaFromText;
    /**
    * Horizontal distance between text and absolutely positioned object
    */
    public int dxaFromText;
    /**
    * Anchor of an absolutely positioned frame is locked
    */
    public boolean fLocked;
    /**
    * When true, Word will prevent windowed lines in this paragraph
    * from beeing placed at the begging of a page
    */
    public boolean fWindowControl;
    /**
    * When true, appl Kinsoku rules when performing line wrapping
    */
    public boolean fKinsoku;
    /**
    * When true, perform word wrap
    */
    public boolean fWordWrap;
    /**
    * When true, apply overflow punctation rules when performing line wrapping
    */
    public boolean fOverflowPunct;
    /**
    * When true, perform top line punctation
    */
    public boolean fTopLinePunct;
    /**
    * When true, auto space East Asian and alphabetic characters
    */
    public boolean fAutoSpaceDE;
    /**
    * When true, auto space East Asian and numeric characters
    */
    public boolean fAutoSpaceDN;
    /**
    * Font alignment
    * 0 Hanging
    * 1 Centered
    * 2 Roman
    * 3 Variable
    * 4 Auto
    */
    public UInt16 wAlignFont = new UInt16();
    /**
    * Used intenally by Word
    */
    public boolean fVertical;
    /**
    * Used intenally by Word
    */
    public boolean fBackward;
    /**
    * Used intenally by Word
    */
    public boolean fRotateFont;
    /**
    * Reserved
    */
    public short empty;
    /**
    * Outline level
    */
    public byte lvl;
    /**
    * 
    */
    public boolean fBiDi;
    /**
    * Paragraph number is inserted (only valid if numrm.fNumRm is o)
    */
    public boolean fNumRMins;
    /**
    * Used Internally
    */
    public boolean fCrLf;
    /**
    * use page Setup Line Pitch
    */
    public boolean fUsePgsuSettings;
    /**
    * Adjust right margin
    */
    public boolean AdjustRight;
    /**
    * Table nesting level
    */
    public int itap;
    /**
    * When true, he end of paragraph mark is really an end of
    * cell mark for a nested table cell
    */
    public boolean fInnerTableCell;
    /**
    * Ensure the Table Cell char doesn't show up as zero height
    */
    public boolean fOpenTch;
    /**
    * Right indent in character units
    */
    public short dxcRight;
    /**
    * Left indent in character units
    */
    public short dxcLeft;
    /**
    * First Lline indent in character units
    */
    public short dxcLeft1;
    /**
    * Vertical spacing before paragraph in character units
    */
    public short dylBefore;
    /**
    * Vertical spacing after paragraph in character units
    */
    public short dylAfter;
    /**
    * Vertical spacing before is automatic
    */
    public boolean fDyaBeforeAuto;
    /**
    * Vertical spacing after is automatic
    */
    public boolean fDyaAfterAuto;
    /**
    * Word 97: indent from right margin
    * Word 2000: indent from right margin (signed)
    * for left-to-right text; from left margin for right-to-left text.
    */
    public int dxaRight;
    /**
    * Word 97: indent from left margin
    * Word 2000: indent from left margin (signed)
    * for left-to-right text; from right margin for right-to-left text.
    */
    public int dxaLeft;
    /**
    * First line indent; signed number relative to dxaLeft
    */
    public int dxaLeft1;
    /**
    * Justification code
    * Justification in Word 2000 and above is relative to text direction
    */
    public JustificationCode jc = JustificationCode.left;
    /**
    * When true, properties have been changed with revision
    */
    public boolean fPropRMark;
    /**
    * Used internally by Word
    */
    public boolean fCharLineUnits;
    /**
    * Used internally by Word
    */
    public boolean fFrpTap;
    /**
    * Used internally by Word
    */
    public int dxaFromTextRight;
    /**
    * Used internally by Word
    */
    public int dyaFromTextBottom;
    /**
    * Used internally by Word
    */
    public int lfrp;
    /// <summary>
    /// Number of tabs stops defined for paragraph.
    /// Must be &gt:= 0 and &lt;=64
    /// </summary>
    public short itbdMac;
    /**
    * Array of positions of itbdMa´c tab stops
    */
    public CSList<Short> rgdxaTab;
    /**
    * When true, absolutely positioned paragraph cannot
    * overlap with another paragraph
    */
    public boolean fNoAllowOverlap;
    /**
    * HTML DIV ID for this paragraph
    */
    public long ipgp;
    /**
    * 
    */
    public long rsid;
    /**
    * Paragraph list style
    */
    public UInt16 istdList = new UInt16();
    /**
    * Used for paragraph property revision marking.
    * The pap at the time fHasOldProps is true, the is the old pap.
    */
    public boolean fHasOldProps;
    /**
    * Specification for border above paragraph
    */
    public BorderCode brcTop;
    /**
    * Specification for border to the left of paragraph
    */
    public BorderCode brcLeft;
    /**
    * Specification for border below paragraph
    */
    public BorderCode brcBottom;
    /**
    * Specification for border to the right of paragraph
    */
    public BorderCode brcRight;
    /**
    * Specification of border to place between conforming paragraphs.
    * Two paragraphs conform when both have borders, their brcLeft and brcRight
    * matches, their widths are the same, they both belong to tables or
    * both do not, and have the same absolute positioning props.
    */
    public BorderCode brcBetween;
    /**
    * Specification of border to place on outside of text when
    * facing pages are to be displayed
    */
    public BorderCode brcBar;
    /**
    * Line spacing descriptor for the paragraph
    */
    public LineSpacingDescriptor lspd;
    /**
    * Drop cap specifier
    */
    public DropCapSpecifier dcs;
    /**
    * Paragraph shading
    */
    public ShadingDescriptor shd;
    /**
    * Word 6.0 paragraph numbering
    */
    public AutoNumberedListDataDescriptor anld;
    /**
    * Height of current paragraph
    */
    public ParagraphHeight phe;
    /**
    * Date at which prperties of this were changed for
    * this run of text by the author.
    * (Only recored when revision marking is on)
    */
    public DateAndTime dttmPropMark;
    /**
    * Array of itbdMac tab descriptors
    */
    public CSList<TabDescriptor> rgtbd;
    /**
    * Paragraph numbering revision mark data
    */
    public NumberRevisionMarkData numrm;
    /**
    * Adjust right margin
    */
    public boolean fAdjustRight;
    /**
    * Ignore the space before/after properties between paragraphs of the same style
    */
    public boolean fContextualSpacing;
    /**
    * Creates a new ParagraphProperties with default values.
    */
    public ParagraphProperties() throws Exception {
        setDefaultValue();
    }

    /**
    * Creates a ParagraphProperties of the paragraph that
    * starts at the given file character position
    * 
    *  @param fc The file character position
    *  @param fib The fib
    *  @param wordDocumentStream The WordDocument stream
    *  @param tableStream The 0TableStream
    */
    public ParagraphProperties(int fc, CSList<FormattedDiskPagePAPX> papxFkps) throws Exception {
        setDefaultValue();
        for (FormattedDiskPagePAPX fkp : papxFkps)
        {
            for (int i = 0;i < fkp.grppapx.length;i++)
            {
                //get all FKPs
                //ok, that's me?
                if (fkp.rgfc[i] == fc)
                {
                    //modify me
                    this.istd = fkp.grppapx[i].istd;
                    for (SinglePropertyModifier sprm : fkp.grppapx[i].grpprl)
                    {
                        this.modify(sprm);
                    }
                    break;
                }
                 
            }
        }
    }

    /**
    * Modifies the ParagraphProperties object with a SinglePropertyModifier
    * 
    *  @param sprm The SinglePropertyModifier
    */
    public void modify(SinglePropertyModifier sprm) throws Exception {
    }

    private void modifyTabs(byte[] args) throws Exception {
        int pos = 0;
    }

    //todo
    private void setDefaultValue() throws Exception {
        //The standard PAP is all zero ...
        this.AdjustRight = false;
        this.brcl = 0;
        this.brcp = 0;
        this.dxaAbs = 0;
        this.dxaFromText = 0;
        this.dxaFromTextRight = 0;
        this.dxaLeft = 0;
        this.dxaLeft1 = 0;
        this.dxaRight = 0;
        this.dxcLeft = 0;
        this.dxcLeft1 = 0;
        this.dxcRight = 0;
        this.dxaWidth = 0;
        this.dyaAbs = 0;
        this.dyaAfter = 0;
        this.dyaBefore = 0;
        this.dyaFromText = 0;
        this.dyaFromTextBottom = 0;
        this.dyaHeight = 0;
        this.dylAfter = 0;
        this.dylBefore = 0;
        this.empty = 0;
        this.fAutoSpaceDE = false;
        this.fAutoSpaceDN = false;
        this.fBackward = false;
        this.fBrLnAbove = false;
        this.fBrLnBelow = false;
        this.fCharLineUnits = false;
        this.fCrLf = false;
        this.fDyaAfterAuto = false;
        this.fDyaBeforeAuto = false;
        this.fFrpTap = false;
        this.fHasOldProps = false;
        this.fInnerTableCell = false;
        this.fInTableW97 = false;
        this.fKeep = false;
        this.fKeepFollow = false;
        this.fKinsoku = false;
        this.fLocked = false;
        this.fMinHeight = false;
        this.fNoAllowOverlap = false;
        this.fNoAutoHyph = false;
        this.fNoLynn = false;
        this.fNumRMins = false;
        this.fOpenTch = false;
        this.fOverflowPunct = false;
        this.fPageBreakBefore = false;
        this.fPropRMark = false;
        this.fRotateFont = false;
        this.fSideBySide = false;
        this.fTopLinePunct = false;
        this.fTtp = false;
        this.fUsePgsuSettings = false;
        this.fVertical = false;
        this.fWordWrap = false;
        this.ilfo = 0;
        this.ilvl = 0;
        this.ipgp = 0;
        this.istd = 0;
        this.istdList = 0;
        this.itap = 0;
        this.itbdMac = 0;
        this.jc = JustificationCode.left;
        this.lfrp = 0;
        this.pcHorz = 0;
        this.pcVert = 0;
        this.wAlignFont = 0;
        this.wHeightAbs = 0;
        this.wr = 0;
        this.anld = new AutoNumberedListDataDescriptor();
        this.brcBar = null;
        this.brcBetween = null;
        this.brcBottom = null;
        this.brcLeft = null;
        this.brcRight = null;
        this.brcTop = null;
        this.dcs = new DropCapSpecifier();
        this.dttmPropMark = new DateAndTime();
        this.numrm = new NumberRevisionMarkData();
        this.phe = new ParagraphHeight();
        this.rgdxaTab = null;
        this.rgtbd = null;
        this.shd = null;
        //except ...
        this.fWindowControl = true;
        this.lvl = 9;
        LineSpacingDescriptor desc = new LineSpacingDescriptor();
        desc.fMultLinespace = true;
        desc.dyaLine = 240;
        this.lspd = desc;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ParagraphProperties>)mapping).apply(this);
    }

}


