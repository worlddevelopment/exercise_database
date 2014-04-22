//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:59 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.AutoSummaryInfo;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DateAndTime;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DocumentProperties;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DocumentTypographyInfo;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DrawingObjectGrid;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.DocFileFormat.UnspportedFileVersionException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

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
public class DocumentProperties   implements IVisitable
{
    /**
    * True when facing pages should be printed
    */
    public boolean fFacingPages;
    /**
    * True when window control is in effect
    */
    public boolean fWindowControl;
    /**
    * true when doc is a main doc for Print Merge Helper
    */
    public boolean fPMHMainDoc;
    /**
    * Default line suppression storage:
    * 0 form letter line supression
    * 1 no line supression
    * (no longer used)
    */
    public short grfSuppression;
    /**
    * Footnote position code:
    * 0 print as endnotes
    * 1 print as bottom of page
    * 2 print immediately beneath text
    */
    public short Fpc;
    /**
    * No longer used
    */
    public short grpfIhdt;
    /**
    * Restart index for footnotes:
    * 0 don't restart note numbering
    * 1 restart for each section
    * 2 restart for each page
    */
    public short rncFtn;
    /**
    * Initial footnote number for document
    */
    public short nFtn;
    /**
    * When true, indicates that information in the hplcpad should
    * be refreshed since outline has been dirtied
    */
    public boolean fOutlineDirtySave;
    /**
    * When true, Word believes all pictures recorded in the
    * document were created on a Macintosh
    */
    public boolean fOnlyMacPics;
    /**
    * When true, Word believes all pictures recorded in the
    * document were created in Windows
    */
    public boolean fOnlyWinPics;
    /**
    * When true, document was created as a print
    * merge labels document
    */
    public boolean fLabelDoc;
    /**
    * When true, Word is allowed to hyphenate words that are capitalized
    */
    public boolean fHyphCapitals;
    /**
    * When true, Word will hyphenate newly typed
    * text as a background task
    */
    public boolean fAutoHyphen;
    /**
    * 
    */
    public boolean fFormNoFields;
    /**
    * When true, Word will merge styles from its template
    */
    public boolean fLinkStyles;
    /**
    * Whent true, Word will mark revisions as the document is edited
    */
    public boolean fRevMarking;
    /**
    * When true, always make backup when document saved
    */
    public boolean fBackup;
    /**
    * When true, the results of the last Word Count execution are still exactly correct
    */
    public boolean fExactWords;
    /**
    * When true, hidden documents contents are displayed
    */
    public boolean fPagHidden;
    /**
    * When true, field results are displayed, when false, field codes are displayed
    */
    public boolean fPagResults;
    /**
    * When true, annotations are locked for editing
    */
    public boolean fLockAtn;
    /**
    * When true, swap margins on left/right pages
    */
    public boolean fMirrorMargins;
    /**
    * When true, use TrueType fonts by default
    * (flag obeyed only when doc was created by WinWord 2.x)
    */
    public boolean fDflttrueType;
    /**
    * When true, file created with SUPPRESSTOPSPACING=YES in Win.ini
    * (flag obeyed only when doc was created by WinWord 2.x)
    */
    public boolean fPagSuppressTopSpacing;
    /**
    * When true, document is protected from edit operations
    */
    public boolean fProtEnabled;
    /**
    * When true, restrict selections to occur only within form fields
    */
    public boolean fDispFormFldSel;
    /**
    * When true, show revision markings on screen
    */
    public boolean fRMView;
    /**
    * When true, show revision markings when document is printed
    */
    public boolean fRMPrint;
    /**
    * When true, the current revision marking state is locked
    */
    public boolean fLockRev;
    /**
    * When true, document contains embedded TrueType fonts
    */
    public boolean fEmbedFonts;
    /**
    * Compatibility option: when true, don't add automatic tab
    * stops for hanging indent
    */
    public boolean fNoTabForInd;
    /**
    * Compatibility option: when true, don't add extra space
    * for raised or lowered characters
    */
    public boolean fNoSpaceRaiseLower;
    /**
    * Compatibility option: when true, suppress the paragraph
    * Space Before and Space After options after a page break
    */
    public boolean fSuppressSpbfAfterPageBreak;
    /**
    * Compatibility option: when true, wrap trailing spaces
    * at the end of a line to the next line
    */
    public boolean fWrapTrailSpaces;
    /**
    * Compatibility option: when true, print colors as black
    * on non-color printer
    */
    public boolean fMapPrintTextColor;
    /**
    * Compatibility option: when true, don't balance columns
    * for Continuous Section starts
    */
    public boolean fNoColumnBalance;
    /**
    * 
    */
    public boolean fConvMailMergeEsc;
    /**
    * Compatibility option: when true, suppress extra line
    * spacing at top of page
    */
    public boolean fSuppressTopSpacing;
    /**
    * Compatibility option: when true, combine table borders
    * like Word 5.x for the Macintosh
    */
    public boolean fOrigWordTableRules;
    /**
    * Compatibility option: when true, don't blank area
    * between metafile pictures
    */
    public boolean fTransparentMetafiles;
    /**
    * Compatibility option: when true, show hard page or
    * column breaks in frames
    */
    public boolean fShowBreaksInFrames;
    /**
    * Compatibility option: when true, swap left and right
    * pages on odd facing pages
    */
    public boolean fSwapBordersFacingPgs;
    /**
    * Default tab width
    */
    public UInt16 dxaTab = new UInt16();
    /**
    * Reserved
    */
    public UInt16 wSpare = new UInt16();
    /**
    * Width of hyphenation hot zone measured in twips
    */
    public UInt16 dxaHotZ = new UInt16();
    /**
    * Number of lines allowed to have consecutive hyphens
    */
    public UInt16 cConsecHypLim = new UInt16();
    /**
    * Reserved
    */
    public UInt16 wSpare2 = new UInt16();
    /**
    * Date and time document was created
    */
    public DateAndTime dttmCreated;
    /**
    * Date and time document was last revised
    */
    public DateAndTime dttmRevised;
    /**
    * Date and time document was last printed
    */
    public DateAndTime dttmLastPrint;
    /**
    * Number of times document has ben revised since its creation
    */
    public short nRevision;
    /**
    * Time document was last edited
    */
    public int tmEdited;
    /**
    * Count of words tallied by last Word Count execution
    */
    public int cWords;
    /**
    * Count of characters tallied by the last Word Count execution
    */
    public int cCh;
    /**
    * Count of pages tallied by the last Word Count execution
    */
    public short cPg;
    /**
    * Count of paragraphs tallied by the last Word Count execution
    */
    public int cParas;
    /**
    * Restart endnote number code:
    * 0 don't restart endnote numbering
    * 1 restart for each section
    * 2 restart for each page
    */
    public short rncEdn;
    /**
    * Beginning endnote number
    */
    public short nEdn;
    /**
    * Endnote position code:
    * 0 display endnotes at end of section
    * 3 display endnotes at the end of document
    */
    public short Epc;
    /**
    * Number format code for auto footnotes.
    * Use the Number Format Table.
    * Note: Only the first 16 values in the table can be used.
    */
    public short nfcFtnRef;
    /**
    * Number format code for auto endnotes.
    * Use the Number Format Table.
    * Note: Only the first 16 values in the table can be used.
    */
    public short nfcEdnRef;
    /**
    * Only print data inside of form fields
    */
    public boolean fPrintFormData;
    /**
    * Only save document data that is inside of a form field
    */
    public boolean fSaveFormData;
    /**
    * Shade form fields
    */
    public boolean fShadeFormData;
    /**
    * When true, include footnotes and endnotes in Word Count
    */
    public boolean fWCFtnEdn;
    /**
    * Count of lines tallied by last Word Count operation
    */
    public int cLines;
    /**
    * Count of words in footnotes and endnotes tallied by last
    * word count operation
    */
    public int cWordsFtnEdn;
    /**
    * Count of characters in footnotes and endnotes tallied by last
    * word count operation
    */
    public int cChFtnEdn;
    /**
    * Count of pages in footnotes and endnotes tallied by last
    * word count operation
    */
    public int cPgFtnEdn;
    /**
    * Count of paragraphs in footnotes and endnotes tallied by last
    * word count operation
    */
    public int cParasFtnEdn;
    /**
    * Count of lines in footnotes and endnotes tallied by last
    * word count operation
    */
    public int cLinesFtnEdn;
    /**
    * Document protection password key only valid if
    * fProtEnabled, fLockAtn or fLockRev is true
    */
    public int lKeyProtDoc;
    /**
    * Document view kind
    * 0 Normal view
    * 1 Outline view
    * 2 Page view
    */
    public short wvkSaved;
    /**
    * Zoom percentage
    */
    public short wScaleSaved;
    /**
    * Zoom type:
    * 0 None
    * 1 Full page
    * 2 Page width
    */
    public short zkSaved;
    /**
    * This is a vertical document
    * (Word 6 and 96 only)
    */
    public boolean fRotateFontW6;
    /**
    * Gutter position for this doc:
    * 0 Side
    * 1 Top
    */
    public boolean iGutterPos;
    /**
    * SUpress extra line spacing at top of page like Word 5.x for the Macintosh
    */
    public boolean fSuppressTopSpacingMac5;
    /**
    * Expand/Codense by whole number of points
    */
    public boolean fTruncDxaExpand;
    /**
    * Print body text before header/footer
    */
    public boolean fPrintBodyBeforeHdr;
    /**
    * Don't add leading (extra space) between rows of text
    */
    public boolean fNoLeading;
    /**
    * USer larger small caps like Word 5.x for the Macintosh
    */
    public boolean fMWSmallCaps;
    /**
    * Autoformat document type: 
    * 0 for normal
    * 1 for letter
    * 2 for email
    */
    public UInt16 adt = new UInt16();
    /**
    * 
    */
    public DocumentTypographyInfo doptypography;
    /**
    * 
    */
    public DrawingObjectGrid dogrid;
    /**
    * Which outline levels are showing in outline view:
    * 0 heading 1 only
    * 4 headings 1 through 5
    * 9 all levels showing
    */
    public short lvl;
    /**
    * Document has been completely grammar checked
    */
    public boolean fGramAllDone;
    /**
    * No grammar errors exist in document
    */
    public boolean fGramAllClean;
    /**
    * If you are doing font embedding, you should only embed
    * the characters in the font that are used in the document
    */
    public boolean fSubsetFonts;
    /**
    * Hide the version created for auto version
    */
    public boolean fHideLastVersion;
    /**
    * This file is based upon an HTML file
    */
    public boolean fHtmlDoc;
    /**
    * Snap table and page borders to page border
    */
    public boolean fSnapBorder;
    /**
    * Place header inside page border
    */
    public boolean fIncludeHeader;
    /**
    * Place footer inside page border
    */
    public boolean fIncludeFooter;
    /**
    * Are we in online view
    */
    public boolean fForcePageSizePag;
    /**
    * Are we auto-promoting fonts to >= hpsZoonFontPag?
    */
    public boolean fMinFontSizePag;
    /**
    * Versioning is turned on
    */
    public boolean fHaveVersions;
    /**
    * Auto versioning is enabled
    */
    public boolean fAutoVersion;
    /**
    * Auto summary info
    */
    public AutoSummaryInfo asumyi;
    /**
    * Count of characters with spaces
    */
    public int cChWS;
    /**
    * Count of characters with spaces in footnotes and endnotes
    */
    public int cChWSFtnEdn;
    /**
    * 
    */
    public int grfDocEvents;
    /**
    * Have we prompted for virus protection on this document?
    */
    public boolean fVirusPromted;
    /**
    * If prompted, load safely for this document?
    */
    public boolean fVirusLoadSafe;
    /**
    * Random session key to sign above bits for a Word session
    */
    public int KeyVirusSession30;
    /**
    * Count of double byte characters
    */
    public int cDBC;
    /**
    * Count of double byte characters in footnotes and endnotes
    */
    public int cDBCFtnEdn;
    /**
    * Minimum font size if fMinFontSizePag is true
    */
    public short hpsZoonFontPag;
    /**
    * Height of the window in online view during last repagination
    */
    public short dywDispPag;
    /**
    * Used internally by Word
    */
    public byte ilvlLastBulletMain;
    /**
    * Used internally by Word
    */
    public byte ilvlLastNumberMain;
    /**
    * Default paragraph style for click and type
    */
    public short istdClickTypePara;
    /**
    * When set to true, language of all text in doc has been auto-detected
    */
    public boolean fLADAllDone;
    /**
    * When set to true, envelope is visible.
    */
    public boolean fEnvelopeVis;
    /**
    * When set to true, doc may have a tentative list in it
    */
    public boolean fMaybeTentativeListInDoc;
    /**
    * When set to 1, doc may have fit text
    */
    public boolean fMaybeFitText;
    /**
    * When set to true, rely on CSS for formatting
    */
    public boolean fRelyOnCss_WebOpt;
    /**
    * When set to true, Rely on VML for displaying graphics in browsers
    */
    public boolean fRelyOnVML_WebOpt;
    /**
    * When set to 1, allow PNG as an output format for graphics
    */
    public boolean fAllowPNG_WebOpt;
    /**
    * Target monitor screen size
    */
    public short screenSize_WebOpt;
    /**
    * When set to 1, organize supporting files in a folder
    */
    public boolean fOrganizeInFolder_WebOpt;
    /**
    * Use long file names for supporting files
    */
    public boolean fUseLongFileNames_WebOpt;
    /**
    * Target monitor resolution in pixels per inch
    */
    public short iPixelsPerInch_WebOpt;
    /**
    * When set to 1, the web options have been filled in
    */
    public boolean fWebOptionsInit;
    /**
    * When set to 1, the document may have East Asian layouts
    */
    public boolean fMaybeFEL;
    /**
    * When set to 1, there may be character unit indents or line unit
    */
    public boolean fCharLineUnits;
    /**
    * When set to 1, there may be RTL Tables in this document
    */
    public boolean fMaybeRTLTables;
    /**
    * Compatibility option: when set to true, do not convert
    * backslash characters into yen signs
    */
    public boolean fLeaveBackslashAlone;
    /**
    * Compatibility option: when set to true, expand character
    * spaces on the line ending SHIFT+RETURN
    */
    public boolean fExpShRtn;
    /**
    * Compatibility option: when set to true, don‘t underline trailing spaces
    */
    public boolean fDntULTrlSpc;
    /**
    * Compatibility option: when set to true, don't balance SBCS and DBCS characters
    */
    public boolean fDntBlnSbDbWid;
    /**
    * Compatibility option: when set to true, add space for underlines.
    */
    public boolean fMakeSpaceForUL;
    /**
    * Compatibility option: suppress extra line spacing like WordPerfect
    */
    public boolean f2ptExtLeadingOnly;
    /**
    * Compatibility option: when set to true, truncate font height
    */
    public boolean fTruncFontHeight;
    /**
    * Compatibility option: when set to true, substitute fonts based on size.
    */
    public boolean fSubOnSize;
    /**
    * Compatibility option: when set to true, lines wrap like Word 6.0
    */
    public boolean fLineWrapLikeWord6;
    /**
    * Compatibility option: when set to true, use Word 6.0/95/97 border rules.
    */
    public boolean fWW6BorderRules;
    /**
    * Compatibility option: when set to true, don't center "exact line height" lines
    */
    public boolean fExactOnTop;
    /**
    * Compatibility option: when set to true, suppress extra line spacing at bottom of page
    */
    public boolean fExtraAfter;
    /**
    * Compatibility option: when set to true, set the width of a space like WordPerfect 5
    */
    public boolean fWPSpace;
    /**
    * Compatibility option: when set to true, do full justification like WordPerfect 6.x
    */
    public boolean fWPJust;
    /**
    * Compatibility option: when set to true, use printer metrics to lay out the document
    */
    public boolean fPrintMet;
    /**
    * Compatibility option: when set to true, lay AutoShapes like Word 97
    */
    public boolean fSpLayoutLikeWW8;
    /**
    * Compatibility option: when set to true, lay footnotes like Word 6.x/95/97.
    */
    public boolean fFtnLayoutLikeWW8;
    /**
    * Compatibility option: when set to true, don't use HTML paragraph auto spacing
    */
    public boolean fDontUseHTMLParagraphAutoSpacing;
    /**
    * Compatibility option: when set to true, don't adjust line height in tables
    */
    public boolean fDontAdjustLineHeightInTable;
    /**
    * Compatibility option: when set to 1, forget last tab alignment
    */
    public boolean fForgetLastTabAlign;
    /**
    * Compatibility option: when set to 1, use auto space like Word 95
    */
    public boolean fUseAutoSpaceForFullWidthAlpha;
    /**
    * Compatibility option: when set to 1, align table rows independently
    */
    public boolean fAlignTablesRowByRow;
    /**
    * Compatibility option: when set to 1, lay out tables with raw width
    */
    public boolean fLayoutRawTableWidth;
    /**
    * Compatibility option: when set to 1, allow table rows to lay out apart
    */
    public boolean fLayoutTableRowsApart;
    /**
    * Compatibility option: when set to 1, use Word 97 line breaking rules for East Asian text
    */
    public boolean fUserWord97LineBreakingRules;
    /**
    * Compatibility option: Do not break wrapped tables across pages.
    */
    public boolean fDontBreakWrappedTables;
    /**
    * Compatibility option: Do not snap text to grid while in a table with inline objects.
    */
    public boolean fDontSnapToGridInCell;
    /**
    * Compatibility option: Select the entire field with the first or last character
    */
    public boolean fDontAllowFieldEndSelect;
    /**
    * Compatibility option: Apply breaking rules
    */
    public boolean fApplyBreakingRules;
    /**
    * Compatibility option: Do not allow hanging punctuation with character grid
    */
    public boolean fDontWrapTextWithPunct;
    /**
    * Compatibility option: Do not use Asian break rules for line breaks with character grid.
    */
    public boolean fDontUseAsianBreakRules;
    /**
    * Compatibility option: Use the Word 2002 table style rules. 
    * Word 2002 places the top border of a column under the heading row,
    * rather than above it as Word 2003 does. 
    * Word 2003 applies the top border of a column in a more intuitive place when
    * there is a header row in the table. This new behavior also fixes an issue with
    * shading not displaying correctly for cells using conditional formatting.
    */
    public boolean fUseWord2002TableStyleRules;
    /**
    * Compatibility option:
    * Allow tables set to ―autofit to contents‖ to extend into the margins when in Print Layout.
    * Word 2003 does not allow this by default.
    */
    public boolean fGrowAutofit;
    /**
    * HTML I/O compatibility level
    */
    public UInt16 verCompatPreW10 = new UInt16();
    /**
    * Page view option
    */
    public boolean fNoMargPgvwSaved;
    /**
    * Page view option
    */
    public boolean fNoMargPgvWPag;
    /**
    * Web View option
    */
    public boolean fWebViewPag;
    /**
    * 
    */
    public boolean fSeeDrawingsPag;
    /**
    * this doc was produced by the document BulletProofer
    */
    public boolean fBulletProofed;
    /**
    * this doc was doctored by the Document Corrupter
    */
    public boolean fCorrupted;
    /**
    * Save option: Embed linguistic in the doc
    */
    public boolean fSaveUim;
    /**
    * Save option: Remove personal information on save
    */
    public boolean fFilterPrivacy;
    /**
    * we are under FReplace (and not just FReplaceRM)
    */
    public boolean fInFReplaceNoRM;
    /**
    * The user has seen the repairs made to the document
    */
    public boolean fSeenRepairs;
    /**
    * XML: The document has XML
    */
    public boolean fHasXML;
    /**
    * 
    */
    public boolean fSeeScriptAnchorsPag;
    /**
    * XML option: Validate XML on save
    */
    public boolean fValidateXML;
    /**
    * XML option: Save the document even if the XML is invalid
    */
    public boolean fSaveIfInvalidXML;
    /**
    * XML option: Show any errors in the XML
    */
    public boolean fShowXMLErrors;
    /**
    * we imported an XML file that had no namespace, so we have elements with no namespace and no schema
    */
    public boolean fAlwaysMergeEmptyNamespace;
    /**
    * 
    */
    public int cpMaxListCacheMainDoc;
    /**
    * Do not embed system fonts in this document
    */
    public boolean fDoNotEmbedSystemFont;
    /**
    * 
    */
    public boolean fWordCompact;
    /**
    * 
    */
    public boolean fLiveRecover;
    /**
    * Embed smart tags in the document
    */
    public boolean fEmbedFactoids;
    /**
    * Save smart tags as XML properties
    */
    public boolean fFactoidXML;
    /**
    * Done processing smart tags
    */
    public boolean fFactoidAllDone;
    /**
    * Print option: Book fold
    */
    public boolean fFolioPrint;
    /**
    * Print option: Reverse book fold
    */
    public boolean fReverseFolio;
    /**
    * 
    */
    public short iTextLineEnding;
    /**
    * Do not keep track of formatting
    */
    public boolean fHideFcc;
    /**
    * Track changes: show markup
    */
    public boolean fAcetateShowMarkup;
    /**
    * Track changes: show annotations
    */
    public boolean fAcetateShowAtn;
    /**
    * Track changes: show insertions and deletions
    */
    public boolean fAcetateShowInsDel;
    /**
    * Track changes: show formatting
    */
    public boolean fAcetateShowProps;
    /**
    * Default table style for the document
    */
    public UInt16 istdTableDflt = new UInt16();
    /**
    * Internal: Version compatibility for save
    */
    public UInt16 verCompat = new UInt16();
    /**
    * Internal: filter state for the Styles and Formatting Pane.
    */
    public UInt16 grfFmtFilter = new UInt16();
    /**
    * Book fold printing: sheets per booklet
    */
    public short iFolioPages;
    /**
    * 
    */
    public UInt16 cpgText = new UInt16();
    /**
    * Revision mark CP info
    */
    public int cpMinRMText;
    /**
    * Revision mark CP info
    */
    public int cpMinRMFtn;
    /**
    * Revision mark CP info
    */
    public int cpMinRMHdd;
    /**
    * Revision mark CP info
    */
    public int cpMinRMAtn;
    /**
    * Revision mark CP info
    */
    public int cpMinRMEdn;
    /**
    * Revision mark CP info
    */
    public int cpMinRMTxbx;
    /**
    * Revision mark CP info
    */
    public int cpMinRMHdrTxbx;
    /**
    * 
    */
    public int rsidRoot;
    /**
    * Document Protection: Treat lock for annotations as Read Only
    */
    public boolean fTreatLockAtnAsReadOnly;
    /**
    * Document Protection: Style lockdown is turned on
    */
    public boolean fStyleLock;
    /**
    * Document Protection: Allow AutoFormat to override style lockdown
    */
    public boolean fAutoFmtOverride;
    /**
    * XML Option: Remove Word XML when saving; save only non-Word XML data.
    */
    public boolean fRemoveWordML;
    /**
    * XML Option: Apply custom transform on Save
    */
    public boolean fApplyCustomXForm;
    /**
    * Document Protection: Style lockdown is enforced
    */
    public boolean fStyeLockEnforced;
    /**
    * Document Protection: Simulate locked for annotations in
    * older version when a document has style protection
    */
    public boolean fFakeLockAtn;
    /**
    * XML Option: Ignore mixed content
    */
    public boolean fIgnoreMixedContent;
    /**
    * XML Option: Show placeholder text for all empty XML elements
    */
    public boolean fShowPlaceholderText;
    /**
    * 
    */
    public long grf;
    /**
    * Reading mode: ink lock down
    */
    public boolean fReadingModeInkLockDown;
    /**
    * Track changes: Show ink annotations
    */
    public boolean fAcetateShowInkAtn;
    /**
    * Filter date and time
    */
    public boolean fFilterDttm;
    /**
    * Enforce document protection
    */
    public boolean fEnforceDocProt;
    /**
    * Doc protection level:
    * 0 Protect for track changes
    * 1 Comment protection
    * 2 Form protection
    * 3 Read Only
    */
    public UInt16 iDocProtCur = new UInt16();
    /**
    * 
    */
    public boolean fDispBkSpSaved;
    /**
    * Reading Layout page size lockdown
    */
    public short dxaPageLock;
    /**
    * Reading Layout page size lockdown
    */
    public short dyaPageLock;
    /**
    * Reading Layout font lockdown
    */
    public int pctFontLock;
    /**
    * 
    */
    public byte grfitbid;
    /**
    * Number of LFOs when CleanupLists last attempted cleaning
    */
    public UInt16 ilfoMacAtCleanup = new UInt16();
    /**
    * Parses the bytes to retrieve a DocumentProperties
    * 
    *  @param bytes The bytes
    */
    public DocumentProperties(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
        setDefaultCompatibilityOptions(fib.nFib);
        byte[] bytes = new byte[fib.lcbDop];
        tableStream.Read(bytes, 0, (int)fib.lcbDop, fib.fcDop);
        try
        {
            if (bytes.length > 0)
            {
                BitArray bits = new BitArray();
                //split byte 0 and 1 into bits
                bits = new BitArray(new byte[]{ bytes[0], bytes[1] });
                this.fFacingPages = bits[0];
                this.fWindowControl = bits[1];
                this.fPMHMainDoc = bits[2];
                this.grfSuppression = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,3,2));
                this.Fpc = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,5,2));
                //split byte 2 and 3 into bits
                bits = new BitArray(new byte[]{ bytes[2], bytes[3] });
                this.rncFtn = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,0,2));
                this.nFtn = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,2,14));
                //split byte 4 and 5 into bits
                bits = new BitArray(new byte[]{ bytes[4], bytes[5] });
                this.fOutlineDirtySave = bits[0];
                this.fOnlyMacPics = bits[8];
                this.fOnlyWinPics = bits[9];
                this.fLabelDoc = bits[10];
                this.fHyphCapitals = bits[11];
                this.fAutoHyphen = bits[12];
                this.fFormNoFields = bits[13];
                this.fLinkStyles = bits[14];
                this.fRevMarking = bits[15];
                //split byte 6 and 7 into bits
                bits = new BitArray(new byte[]{ bytes[6], bytes[7] });
                this.fBackup = bits[0];
                this.fExactWords = bits[1];
                this.fPagHidden = bits[2];
                this.fPagResults = bits[3];
                this.fLockAtn = bits[4];
                this.fMirrorMargins = bits[5];
                //bit 6 is reserved
                this.fDflttrueType = bits[7];
                this.fProtEnabled = bits[8];
                this.fDispFormFldSel = bits[9];
                this.fRMView = bits[10];
                this.fRMPrint = bits[11];
                //bit 12 and 13 are reserved
                this.fLockRev = bits[14];
                this.fEmbedFonts = bits[15];
                //split byte 8 and 9 into bits
                bits = new BitArray(new byte[]{ bytes[8], bytes[9] });
                this.fNoTabForInd = bits[0];
                this.fNoSpaceRaiseLower = bits[1];
                this.fSuppressSpbfAfterPageBreak = bits[2];
                this.fWrapTrailSpaces = bits[3];
                this.fMapPrintTextColor = bits[4];
                this.fNoColumnBalance = bits[5];
                this.fConvMailMergeEsc = bits[6];
                this.fSuppressTopSpacing = bits[7];
                this.fOrigWordTableRules = bits[8];
                this.fTransparentMetafiles = bits[9];
                this.fShowBreaksInFrames = bits[10];
                this.fSwapBordersFacingPgs = bits[11];
                this.dxaTab = System.BitConverter.ToUInt16(bytes, 10);
                this.dxaHotZ = System.BitConverter.ToUInt16(bytes, 14);
                this.cConsecHypLim = System.BitConverter.ToUInt16(bytes, 16);
                byte[] createdbytes = new byte[4];
                Array.Copy(bytes, 20, createdbytes, 0, createdbytes.length);
                this.dttmCreated = new DateAndTime(createdbytes);
                byte[] revisedbytes = new byte[4];
                Array.Copy(bytes, 24, revisedbytes, 0, revisedbytes.length);
                this.dttmRevised = new DateAndTime(revisedbytes);
                byte[] printbytes = new byte[4];
                Array.Copy(bytes, 28, printbytes, 0, printbytes.length);
                this.dttmLastPrint = new DateAndTime(printbytes);
                this.nRevision = System.BitConverter.ToInt16(bytes, 32);
                this.tmEdited = System.BitConverter.ToInt32(bytes, 34);
                this.cWords = System.BitConverter.ToInt32(bytes, 38);
                this.cCh = System.BitConverter.ToInt32(bytes, 42);
                this.cPg = System.BitConverter.ToInt16(bytes, 46);
                this.cParas = System.BitConverter.ToInt32(bytes, 48);
                //split byte 52 and 53 into bits
                bits = new BitArray(new byte[]{ bytes[52], bytes[53] });
                this.rncEdn = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,0,2));
                this.nEdn = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,2,14));
                //split byte 54 and 55 into bits
                bits = new BitArray(new byte[]{ bytes[54], bytes[55] });
                this.Epc = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,0,2));
                this.nfcFtnRef = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,2,4));
                this.nfcEdnRef = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,6,4));
                this.fPrintFormData = bits[10];
                this.fSaveFormData = bits[11];
                this.fShadeFormData = bits[12];
                //bits 13 and 14 are reserved
                this.fWCFtnEdn = bits[15];
                this.cLines = System.BitConverter.ToInt32(bytes, 56);
                this.cWordsFtnEdn = System.BitConverter.ToInt32(bytes, 60);
                this.cChFtnEdn = System.BitConverter.ToInt32(bytes, 64);
                this.cPgFtnEdn = System.BitConverter.ToInt16(bytes, 68);
                this.cParasFtnEdn = System.BitConverter.ToInt32(bytes, 70);
                this.cLinesFtnEdn = System.BitConverter.ToInt32(bytes, 74);
                this.lKeyProtDoc = System.BitConverter.ToInt32(bytes, 78);
                //split byte 82 and 83 into bits
                bits = new BitArray(new byte[]{ bytes[82], bytes[83] });
                this.wvkSaved = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,0,3));
                this.wScaleSaved = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,3,9));
                this.zkSaved = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,12,2));
                this.fRotateFontW6 = bits[14];
                this.iGutterPos = bits[15];
                //compatibility options section
                if (bytes.length > 84)
                {
                    //split byte 84,85,86,87 into bits
                    bits = new BitArray(new byte[]{ bytes[84], bytes[85], bytes[86], bytes[87] });
                    this.fNoTabForInd = bits[0];
                    this.fNoSpaceRaiseLower = bits[1];
                    this.fSuppressSpbfAfterPageBreak = bits[2];
                    this.fWrapTrailSpaces = bits[3];
                    this.fMapPrintTextColor = bits[4];
                    this.fNoColumnBalance = bits[5];
                    this.fConvMailMergeEsc = bits[6];
                    this.fSuppressTopSpacing = bits[7];
                    this.fOrigWordTableRules = bits[8];
                    this.fTransparentMetafiles = bits[9];
                    this.fShowBreaksInFrames = bits[10];
                    this.fSwapBordersFacingPgs = bits[11];
                    //bits 12,13,14,15 are reserved
                    this.fSuppressTopSpacingMac5 = bits[16];
                    this.fTruncDxaExpand = bits[17];
                    this.fPrintBodyBeforeHdr = bits[18];
                    this.fNoLeading = bits[19];
                    //bits 20 is reserved
                    this.fMWSmallCaps = bits[21];
                    //bits 22-31 are reserved
                    if (bytes.length > 88)
                    {
                        this.adt = (UInt16)System.BitConverter.ToInt16(bytes, 88);
                        byte[] doptypoBytes = new byte[310];
                        Array.Copy(bytes, 90, doptypoBytes, 0, doptypoBytes.length);
                        this.doptypography = new DocumentTypographyInfo(doptypoBytes);
                        byte[] dogridBytes = new byte[10];
                        Array.Copy(bytes, 400, dogridBytes, 0, dogridBytes.length);
                        this.dogrid = new DrawingObjectGrid(dogridBytes);
                        //split byte 410 and 411 into bits
                        bits = new BitArray(new byte[]{ bytes[410], bytes[411] });
                        //bit 0 is reserved
                        this.lvl = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,1,4));
                        this.fGramAllDone = bits[5];
                        this.fGramAllClean = bits[6];
                        this.fSubsetFonts = bits[7];
                        this.fHideLastVersion = bits[8];
                        this.fHtmlDoc = bits[9];
                        //bit 10 is reserved
                        this.fSnapBorder = bits[11];
                        this.fIncludeHeader = bits[12];
                        this.fIncludeFooter = bits[13];
                        this.fForcePageSizePag = bits[14];
                        this.fMinFontSizePag = bits[15];
                        //split byte 412 and 413 into bits
                        bits = new BitArray(new byte[]{ bytes[412], bytes[413] });
                        this.fHaveVersions = bits[0];
                        this.fAutoVersion = bits[1];
                        //other bits are reserved
                        byte[] asumybits = new byte[12];
                        Array.Copy(bytes, 414, asumybits, 0, asumybits.length);
                        this.asumyi = new AutoSummaryInfo(asumybits);
                        this.cChWS = System.BitConverter.ToInt32(bytes, 426);
                        this.cChWSFtnEdn = System.BitConverter.ToInt32(bytes, 430);
                        this.grfDocEvents = System.BitConverter.ToInt32(bytes, 434);
                        //split bytes 438-441 in bits
                        bits = new BitArray(new byte[]{ bytes[438], bytes[439], bytes[440], bytes[441] });
                        this.fVirusPromted = bits[0];
                        this.fVirusLoadSafe = bits[1];
                        this.KeyVirusSession30 = (int)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,2,30));
                        this.cDBC = System.BitConverter.ToInt32(bytes, 480);
                        this.cDBCFtnEdn = System.BitConverter.ToInt32(bytes, 484);
                        this.nfcEdnRef = System.BitConverter.ToInt16(bytes, 492);
                        this.nfcFtnRef = System.BitConverter.ToInt16(bytes, 494);
                        this.hpsZoonFontPag = System.BitConverter.ToInt16(bytes, 496);
                        this.dywDispPag = System.BitConverter.ToInt16(bytes, 498);
                        //WORD 2000, 2002, 2003 PART
                        if (bytes.length > 500)
                        {
                            this.ilvlLastBulletMain = bytes[500];
                            this.ilvlLastNumberMain = bytes[501];
                            this.istdClickTypePara = System.BitConverter.ToInt16(bytes, 502);
                            //split byte 504 and 505 into bits
                            bits = new BitArray(new byte[]{ bytes[504], bytes[505] });
                            this.fLADAllDone = bits[0];
                            this.fEnvelopeVis = bits[1];
                            this.fMaybeTentativeListInDoc = bits[2];
                            this.fMaybeFitText = bits[3];
                            this.fRelyOnCss_WebOpt = bits[9];
                            this.fRelyOnVML_WebOpt = bits[10];
                            this.fAllowPNG_WebOpt = bits[11];
                            this.screenSize_WebOpt = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,12,4));
                            //split byte 506 and 507 into bits
                            bits = new BitArray(new byte[]{ bytes[506], bytes[507] });
                            this.fOrganizeInFolder_WebOpt = bits[0];
                            this.fUseLongFileNames_WebOpt = bits[1];
                            this.iPixelsPerInch_WebOpt = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,2,10));
                            this.fWebOptionsInit = bits[12];
                            this.fMaybeFEL = bits[13];
                            this.fCharLineUnits = bits[14];
                            this.fMaybeRTLTables = bits[15];
                            //split bytes 508,509,510,511 into bits
                            bits = new BitArray(new byte[]{ bytes[508], bytes[509], bytes[510], bytes[511] });
                            this.fNoTabForInd = bits[0];
                            this.fNoSpaceRaiseLower = bits[1];
                            this.fSuppressSpbfAfterPageBreak = bits[2];
                            this.fWrapTrailSpaces = bits[3];
                            this.fMapPrintTextColor = bits[4];
                            this.fNoColumnBalance = bits[5];
                            this.fConvMailMergeEsc = bits[6];
                            this.fSuppressTopSpacing = bits[7];
                            this.fOrigWordTableRules = bits[8];
                            this.fTransparentMetafiles = bits[9];
                            this.fShowBreaksInFrames = bits[10];
                            this.fSwapBordersFacingPgs = bits[11];
                            this.fLeaveBackslashAlone = bits[12];
                            this.fExpShRtn = bits[13];
                            this.fDntULTrlSpc = bits[14];
                            this.fDntBlnSbDbWid = bits[15];
                            this.fSuppressTopSpacingMac5 = bits[16];
                            this.fTruncDxaExpand = bits[17];
                            this.fPrintBodyBeforeHdr = bits[18];
                            this.fNoLeading = bits[19];
                            this.fMakeSpaceForUL = bits[20];
                            this.fMWSmallCaps = bits[21];
                            this.f2ptExtLeadingOnly = bits[22];
                            this.fTruncFontHeight = bits[23];
                            this.fSubOnSize = bits[24];
                            this.fLineWrapLikeWord6 = bits[25];
                            this.fWW6BorderRules = bits[26];
                            this.fExactOnTop = bits[27];
                            this.fExtraAfter = bits[28];
                            this.fWPSpace = bits[29];
                            this.fWPJust = bits[30];
                            this.fPrintMet = bits[31];
                            //split bytes 512,513,514,515 into bits
                            bits = new BitArray(new byte[]{ bytes[512], bytes[513], bytes[514], bytes[515] });
                            this.fSpLayoutLikeWW8 = bits[0];
                            this.fFtnLayoutLikeWW8 = bits[1];
                            this.fDontUseHTMLParagraphAutoSpacing = bits[2];
                            this.fDontAdjustLineHeightInTable = bits[3];
                            this.fForgetLastTabAlign = bits[4];
                            this.fUseAutoSpaceForFullWidthAlpha = bits[5];
                            this.fAlignTablesRowByRow = bits[6];
                            this.fLayoutRawTableWidth = bits[7];
                            this.fLayoutTableRowsApart = bits[8];
                            this.fUserWord97LineBreakingRules = bits[9];
                            this.fDontBreakWrappedTables = bits[10];
                            this.fDontSnapToGridInCell = bits[11];
                            this.fDontAllowFieldEndSelect = bits[12];
                            this.fApplyBreakingRules = bits[13];
                            this.fDontWrapTextWithPunct = bits[14];
                            this.fDontUseAsianBreakRules = bits[15];
                            this.fUseWord2002TableStyleRules = bits[16];
                            this.fGrowAutofit = bits[17];
                            //bits 18-31 are unused
                            //bytes 516-539 are unused
                            //split bytes 540,541,542,543 into bits
                            bits = new BitArray(new byte[]{ bytes[540], bytes[541], bytes[542], bytes[543] });
                            this.verCompatPreW10 = (UInt16)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,0,16));
                            this.fNoMargPgvwSaved = bits[16];
                            this.fNoMargPgvWPag = bits[17];
                            this.fWebViewPag = bits[18];
                            this.fSeeDrawingsPag = bits[19];
                            this.fBulletProofed = bits[20];
                            this.fCorrupted = bits[21];
                            this.fSaveUim = bits[22];
                            this.fFilterPrivacy = bits[23];
                            this.fInFReplaceNoRM = bits[24];
                            this.fSeenRepairs = bits[25];
                            this.fHasXML = bits[26];
                            this.fSeeScriptAnchorsPag = bits[27];
                            this.fValidateXML = bits[28];
                            this.fSaveIfInvalidXML = bits[29];
                            this.fShowXMLErrors = bits[30];
                            this.fAlwaysMergeEmptyNamespace = bits[31];
                            this.cpMaxListCacheMainDoc = System.BitConverter.ToInt32(bytes, 544);
                            //split bytes 548,549 into bits
                            bits = new BitArray(new byte[]{ bytes[548], bytes[549] });
                            this.fDoNotEmbedSystemFont = bits[0];
                            this.fWordCompact = bits[1];
                            this.fLiveRecover = bits[2];
                            this.fEmbedFactoids = bits[3];
                            this.fFactoidXML = bits[4];
                            this.fFactoidAllDone = bits[5];
                            this.fFolioPrint = bits[6];
                            this.fReverseFolio = bits[7];
                            this.iTextLineEnding = (short)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,8,3));
                            this.fHideFcc = bits[11];
                            this.fAcetateShowMarkup = bits[12];
                            this.fAcetateShowAtn = bits[13];
                            this.fAcetateShowInsDel = bits[14];
                            this.fAcetateShowProps = bits[15];
                            this.istdTableDflt = System.BitConverter.ToUInt16(bytes, 550);
                            this.verCompat = System.BitConverter.ToUInt16(bytes, 552);
                            this.grfFmtFilter = System.BitConverter.ToUInt16(bytes, 554);
                            this.iFolioPages = System.BitConverter.ToInt16(bytes, 556);
                            this.cpgText = System.BitConverter.ToUInt16(bytes, 558);
                            this.cpMinRMText = System.BitConverter.ToInt32(bytes, 560);
                            this.cpMinRMFtn = System.BitConverter.ToInt32(bytes, 564);
                            this.cpMinRMHdd = System.BitConverter.ToInt32(bytes, 568);
                            this.cpMinRMAtn = System.BitConverter.ToInt32(bytes, 572);
                            this.cpMinRMEdn = System.BitConverter.ToInt32(bytes, 576);
                            this.cpMinRMTxbx = System.BitConverter.ToInt32(bytes, 580);
                            this.cpMinRMHdrTxbx = System.BitConverter.ToInt32(bytes, 584);
                            this.rsidRoot = System.BitConverter.ToInt32(bytes, 588);
                            if (bytes.length == 610)
                            {
                                //split bytes 592,593,594,595 into bits
                                bits = new BitArray(new byte[]{ bytes[592], bytes[593], bytes[594], bytes[595] });
                                this.fTreatLockAtnAsReadOnly = bits[0];
                                this.fStyleLock = bits[1];
                                this.fAutoFmtOverride = bits[2];
                                this.fRemoveWordML = bits[3];
                                this.fApplyCustomXForm = bits[4];
                                this.fStyeLockEnforced = bits[5];
                                this.fFakeLockAtn = bits[6];
                                this.fIgnoreMixedContent = bits[7];
                                this.fShowPlaceholderText = bits[8];
                                this.grf = DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,9,23));
                                //split bytes 596 and 597 into bits
                                bits = new BitArray(new byte[]{ bytes[596], bytes[597] });
                                this.fReadingModeInkLockDown = bits[0];
                                this.fAcetateShowInkAtn = bits[1];
                                this.fFilterDttm = bits[2];
                                this.fEnforceDocProt = bits[3];
                                this.iDocProtCur = (UInt16)DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayToUInt32(DIaLOGIKa.b2xtranslator.Tools.Utils.bitArrayCopy(bits,4,3));
                                this.fDispBkSpSaved = bits[7];
                                this.dxaPageLock = System.BitConverter.ToInt16(bytes, 598);
                                this.dyaPageLock = System.BitConverter.ToInt16(bytes, 600);
                                this.pctFontLock = System.BitConverter.ToInt32(bytes, 602);
                                this.grfitbid = bytes[606];
                                //byte 607 is unused
                                this.ilfoMacAtCleanup = System.BitConverter.ToUInt16(bytes, 608);
                            }
                             
                        }
                         
                    }
                     
                }
                 
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            //this DOP was probably not written by Word
            TraceLogger.warning("Unexpected length of DOP ({0} bytes) in input file.",(int)fib.lcbDop);
        }
    
    }

    private void setDefaultCompatibilityOptions(DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion nFib) throws Exception {
        if (nFib == DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib1997)
        {
            //Word 97 default settings
            this.fAlignTablesRowByRow = true;
            this.fLayoutTableRowsApart = true;
            this.fGrowAutofit = true;
            this.fDontWrapTextWithPunct = true;
            //ToDo: Don't autofit tables next to wrapped objects
            //ToDo: Don't break constrained tables forced onto the page
            this.fDontBreakWrappedTables = true;
            this.fDontSnapToGridInCell = true;
            this.fDontUseAsianBreakRules = true;
            this.fNoTabForInd = true;
            this.fDontUseHTMLParagraphAutoSpacing = true;
            this.fForgetLastTabAlign = true;
            this.fSpLayoutLikeWW8 = true;
            this.fFtnLayoutLikeWW8 = true;
            this.fLayoutRawTableWidth = true;
            this.fDontAllowFieldEndSelect = true;
            //ToDo: underline characters in numbered lists
            this.fUseWord2002TableStyleRules = true;
            this.fUserWord97LineBreakingRules = true;
        }
        else if (nFib == DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib2000)
        {
            //Word 2000 default settings
            this.fGrowAutofit = true;
            this.fDontWrapTextWithPunct = true;
            //ToDo: Don't autofit tables next to wrapped objects
            this.fDontBreakWrappedTables = true;
            this.fDontSnapToGridInCell = true;
            this.fDontUseAsianBreakRules = true;
            this.fNoTabForInd = true;
            this.fDontAllowFieldEndSelect = true;
            //ToDo: underline characters in numbered lists
            this.fUseWord2002TableStyleRules = true;
        }
        else if (nFib == DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib2002)
        {
            //Word 2002 (XP)
            this.fGrowAutofit = true;
            //ToDo: Don't autofit tables next to wrapped objects
            this.fDontBreakWrappedTables = true;
            this.fNoTabForInd = true;
            this.fUseWord2002TableStyleRules = true;
        }
        else if (nFib == DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib2003)
        {
            //Word 2003
            //ToDo: Don't autofit tables next to wrapped objects
            this.fDontBreakWrappedTables = true;
            this.fNoTabForInd = true;
        }
        else if (nFib < DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib1997)
        {
            throw new UnspportedFileVersionException();
        }
             
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<DocumentProperties>)mapping).apply(this);
    }

}


