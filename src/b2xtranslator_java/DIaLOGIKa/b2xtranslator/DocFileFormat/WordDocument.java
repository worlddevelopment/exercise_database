//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:09 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Reflection.Assembly;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.AnnotationOwnerList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.AnnotationReferenceDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.AnnotationReferenceExtraTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BookmarkFirst;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BreakDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteParseException;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CommandTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DocumentProperties;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileShapeAddress;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FontFamilyName;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPageCHPX;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormattedDiskPagePAPX;
import DIaLOGIKa.b2xtranslator.DocFileFormat.HeaderAndFooterTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListFormatOverrideTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ListTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.OfficeArtContent;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PieceTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Plex;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SectionDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SectionPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StringTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StyleSheet;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.StreamNotFoundException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import java.util.HashMap;

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
public class WordDocument   implements IVisitable
{
    static {
        try
        {
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.UpdateTypeToRecordClassMapping(null /* getExecutingAssembly() */, WordDocument.class.Namespace);
        }
        catch (Exception __dummyStaticConstructorCatchVar0)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar0);
        }
    
    }

    /**
    * A dictionary that contains all SEPX of the document.
    * The key is the CP at which sections ends.
    * The value is the SEPX that formats the section.
    */
    public HashMap<Integer,SectionPropertyExceptions> AllSepx;
    /**
    * A dictionary that contains all PAPX of the document.
    * The key is the FC at which the paragraph starts.
    * The value is the PAPX that formats the paragraph.
    */
    public HashMap<Integer,ParagraphPropertyExceptions> AllPapx;
    /**
    * 
    */
    public PieceTable PieceTable;
    public CommandTable CommandTable;
    /**
    * A Plex containing all section descriptors
    */
    public Plex<SectionDescriptor> SectionPlex;
    /**
    * Contains the names of all author who revised something in the document
    */
    public StringTable RevisionAuthorTable;
    /**
    * The stream "WordDocument"
    */
    public VirtualStream WordDocumentStream;
    /**
    * The stream "0Table" or "1Table"
    */
    public VirtualStream TableStream;
    /**
    * The stream called "Data"
    */
    public VirtualStream DataStream;
    /**
    * The StructuredStorageFile itself
    */
    public StructuredStorageReader Storage;
    /**
    * The file information block of the word document
    */
    public FileInformationBlock FIB;
    /**
    * All text of the Word document
    */
    public CSList<Character> Text;
    /**
    * The style sheet of the document
    */
    public StyleSheet Styles;
    /**
    * A list of all font names, used in the doucument
    */
    public StringTable FontTable;
    public StringTable BookmarkNames;
    public StringTable AutoTextNames;
    //public StringTable ProtectionUsers;
    /**
    * A plex with all ATRDPre10 structs
    */
    public Plex<AnnotationReferenceDescriptor> AnnotationsReferencePlex;
    public AnnotationOwnerList AnnotationOwners;
    /**
    * An array with all ATRDPost10 structs
    */
    public AnnotationReferenceExtraTable AnnotationReferenceExtraTable;
    /**
    * A list that contains all formatting information of
    * the lists and numberings in the document.
    */
    public ListTable ListTable;
    /**
    * The drawing object table ....
    */
    public OfficeArtContent OfficeArtContent;
    /**
    * 
    */
    public Plex<FileShapeAddress> OfficeDrawingPlex;
    /**
    * 
    */
    public Plex<FileShapeAddress> OfficeDrawingPlexHeader;
    /**
    * Each character position specifies the beginning of a range of text
    * that constitutes the contents of an AutoText item.
    */
    public Plex<Exception> AutoTextPlex;
    public Plex<Short> EndnoteReferencePlex;
    public Plex<Short> FootnoteReferencePlex;
    /**
    * Describes the breaks inside the textbox subdocument
    */
    public Plex<BreakDescriptor> TextboxBreakPlex;
    /**
    * Describes the breaks inside the header textbox subdocument
    */
    public Plex<BreakDescriptor> TextboxBreakPlexHeader;
    public Plex<BookmarkFirst> BookmarkStartPlex;
    public Plex<Exception> BookmarkEndPlex;
    /**
    * The DocumentProperties of the word document
    */
    public DocumentProperties DocumentProperties;
    /**
    * A list that contains all overriding formatting information
    * of the lists and numberings in the document.
    */
    public ListFormatOverrideTable ListFormatOverrideTable;
    /**
    * A list of all FKPs that contain PAPX
    */
    public CSList<FormattedDiskPagePAPX> AllPapxFkps;
    /**
    * A list of all FKPs that contain CHPX
    */
    public CSList<FormattedDiskPageCHPX> AllChpxFkps;
    /**
    * A table that contains the positions of the headers and footer in the text.
    */
    public HeaderAndFooterTable HeaderAndFooterTable;
    public WordDocument Glossary;
    public WordDocument(StructuredStorageReader reader) throws Exception {
        parse(reader,0);
    }

    public WordDocument(StructuredStorageReader reader, int fibFC) throws Exception {
        parse(reader,fibFC);
    }

    private void parse(StructuredStorageReader reader, int fibFC) throws Exception {
        this.Storage = reader;
        this.WordDocumentStream = reader.getStream("WordDocument");
        //parse FIB
        this.WordDocumentStream.Seek(fibFC, System.IO.SeekOrigin.Begin);
        this.FIB = new FileInformationBlock(new VirtualStreamReader(this.WordDocumentStream));
        //check the file version
        if (((Enum)FIB.nFib).ordinal() != 0)
        {
            if (this.FIB.nFib < DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib1997)
            {
                throw new ByteParseException("Could not parse the file because it was created by an unsupported application (Word 95 or older).");
            }
             
        }
        else
        {
            if (this.FIB.nFibNew < DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock.FibVersion.Fib1997)
            {
                throw new ByteParseException("Could not parse the file because it was created by an unsupported application (Word 95 or older).");
            }
             
        } 
        //get the streams
        if (this.FIB.fWhichTblStm)
        {
            this.TableStream = reader.getStream("1Table");
        }
        else
        {
            this.TableStream = reader.getStream("0Table");
        } 
        try
        {
            this.DataStream = reader.getStream("Data");
        }
        catch (StreamNotFoundException __dummyCatchVar0)
        {
            this.DataStream = null;
        }

        //Read all needed STTBs
        this.RevisionAuthorTable = new StringTable(String.class,this.TableStream,this.FIB.fcSttbfRMark,this.FIB.lcbSttbfRMark);
        this.FontTable = new StringTable(FontFamilyName.class,this.TableStream,this.FIB.fcSttbfFfn,this.FIB.lcbSttbfFfn);
        this.BookmarkNames = new StringTable(String.class,this.TableStream,this.FIB.fcSttbfBkmk,this.FIB.lcbSttbfBkmk);
        this.AutoTextNames = new StringTable(String.class,this.TableStream,this.FIB.fcSttbfGlsy,this.FIB.lcbSttbfGlsy);
        //this.ProtectionUsers = new StringTable(typeof(String), this.TableStream, this.FIB.fcSttbProtUser, this.FIB.lcbSttbProtUser);
        //Read all needed PLCFs
        this.AnnotationsReferencePlex = new Plex<AnnotationReferenceDescriptor>(30,this.TableStream,this.FIB.fcPlcfandRef,this.FIB.lcbPlcfandRef);
        this.TextboxBreakPlex = new Plex<BreakDescriptor>(6,this.TableStream,this.FIB.fcPlcfTxbxBkd,this.FIB.lcbPlcfTxbxBkd);
        this.TextboxBreakPlexHeader = new Plex<BreakDescriptor>(6,this.TableStream,this.FIB.fcPlcfTxbxHdrBkd,this.FIB.lcbPlcfTxbxHdrBkd);
        this.OfficeDrawingPlex = new Plex<FileShapeAddress>(26,this.TableStream,this.FIB.fcPlcSpaMom,this.FIB.lcbPlcSpaMom);
        this.OfficeDrawingPlexHeader = new Plex<FileShapeAddress>(26,this.TableStream,this.FIB.fcPlcSpaHdr,this.FIB.lcbPlcSpaHdr);
        this.SectionPlex = new Plex<SectionDescriptor>(12,this.TableStream,this.FIB.fcPlcfSed,this.FIB.lcbPlcfSed);
        this.BookmarkStartPlex = new Plex<BookmarkFirst>(4,this.TableStream,this.FIB.fcPlcfBkf,this.FIB.lcbPlcfBkf);
        this.EndnoteReferencePlex = new Plex<Short>(2,this.TableStream,this.FIB.fcPlcfendRef,this.FIB.lcbPlcfendRef);
        this.FootnoteReferencePlex = new Plex<Short>(2,this.TableStream,this.FIB.fcPlcffndRef,this.FIB.lcbPlcffndRef);
        // PLCFs without types
        this.BookmarkEndPlex = new Plex<Exception>(0,this.TableStream,this.FIB.fcPlcfBkl,this.FIB.lcbPlcfBkl);
        this.AutoTextPlex = new Plex<Exception>(0,this.TableStream,this.FIB.fcPlcfGlsy,this.FIB.lcbPlcfGlsy);
        //read the FKPs
        this.AllPapxFkps = FormattedDiskPagePAPX.getAllPAPXFKPs(this.FIB,this.WordDocumentStream,this.TableStream,this.DataStream);
        this.AllChpxFkps = FormattedDiskPageCHPX.getAllCHPXFKPs(this.FIB,this.WordDocumentStream,this.TableStream);
        //read custom tables
        this.DocumentProperties = new DocumentProperties(this.FIB,this.TableStream);
        this.Styles = new StyleSheet(this.FIB,this.TableStream,this.DataStream);
        this.ListTable = new ListTable(this.FIB,this.TableStream);
        this.ListFormatOverrideTable = new ListFormatOverrideTable(this.FIB,this.TableStream);
        this.OfficeArtContent = new OfficeArtContent(this.FIB,this.TableStream);
        this.HeaderAndFooterTable = new HeaderAndFooterTable(this);
        this.AnnotationReferenceExtraTable = new AnnotationReferenceExtraTable(this.FIB,this.TableStream);
        this.CommandTable = new CommandTable(this.FIB,this.TableStream);
        this.AnnotationOwners = new AnnotationOwnerList(this.FIB,this.TableStream);
        //parse the piece table and construct a list that contains all chars
        this.PieceTable = new PieceTable(this.FIB,this.TableStream);
        this.Text = this.PieceTable.getAllChars(this.WordDocumentStream);
        //build a dictionaries of all PAPX
        this.AllPapx = new HashMap<Integer,ParagraphPropertyExceptions>();
        for (int i = 0;i < this.AllPapxFkps.size();i++)
        {
            for (int j = 0;j < this.AllPapxFkps.get(i).grppapx.length;j++)
            {
                this.AllPapx.put(this.AllPapxFkps.get(i).rgfc[j], this.AllPapxFkps.get(i).grppapx[j]);
            }
        }
        //build a dictionary of all SEPX
        this.AllSepx = new HashMap<Integer,SectionPropertyExceptions>();
        for (int i = 0;i < this.SectionPlex.Elements.size();i++)
        {
            //Read the SED
            SectionDescriptor sed = (SectionDescriptor)this.SectionPlex.Elements.get(i);
            int cp = this.SectionPlex.CharacterPositions.get(i + 1);
            //Get the SEPX
            VirtualStreamReader wordReader = new VirtualStreamReader(this.WordDocumentStream);
            this.WordDocumentStream.Seek(sed.fcSepx, System.IO.SeekOrigin.Begin);
            short cbSepx = wordReader.readInt16();
            SectionPropertyExceptions sepx = new SectionPropertyExceptions(wordReader.ReadBytes(cbSepx - 2));
            this.AllSepx.put(cp, sepx);
        }
        //read the Glossary
        if (this.FIB.pnNext > 0)
        {
            this.Glossary = new WordDocument(this.Storage,(int)(this.FIB.pnNext * 512));
        }
         
    }

    /**
    * Returns a list of all CHPX which are valid for the given FCs.
    * 
    *  @param fcMin The lower boundary
    *  @param fcMax The upper boundary
    *  @return The FCs
    */
    public CSList<Integer> getFileCharacterPositions(int fcMin, int fcMax) throws Exception {
        CSList<Integer> list = new CSList<Integer>();
        for (int i = 0;i < this.AllChpxFkps.size();i++)
        {
            FormattedDiskPageCHPX fkp = this.AllChpxFkps.get(i);
            //if the last fc of this fkp is smaller the fcMin
            //this fkp is before the requested range
            if (fkp.rgfc[fkp.rgfc.length - 1] < fcMin)
            {
                continue;
            }
             
            //if the first fc of this fkp is larger the Max
            //this fkp is beyond the requested range
            if (fkp.rgfc[0] > fcMax)
            {
                break;
            }
             
            //don't add the duplicated values of the FKP boundaries (Length-1)
            int max = fkp.rgfc.length - 1;
            //last fkp?
            //use full table
            if (i == (this.AllChpxFkps.size() - 1))
            {
                max = fkp.rgfc.length;
            }
             
            for (int j = 0;j < max;j++)
            {
                if (fkp.rgfc[j] < fcMin && fkp.rgfc[j + 1] > fcMin)
                {
                    //this chpx starts before fcMin
                    list.add(fkp.rgfc[j]);
                }
                else if (fkp.rgfc[j] >= fcMin && fkp.rgfc[j] < fcMax)
                {
                    //this chpx is in the range
                    list.add(fkp.rgfc[j]);
                }
                  
            }
        }
        return list;
    }

    /**
    * Returnes a list of all CharacterPropertyExceptions which correspond to text
    * between the given boundaries.
    * 
    *  @param fcMin The lower boundary
    *  @param fcMax The upper boundary
    *  @return The FCs
    */
    public CSList<CharacterPropertyExceptions> getCharacterPropertyExceptions(int fcMin, int fcMax) throws Exception {
        CSList<CharacterPropertyExceptions> list = new CSList<CharacterPropertyExceptions>();
        for (FormattedDiskPageCHPX fkp : this.AllChpxFkps)
        {
            for (int j = 0;j < fkp.grpchpx.length;j++)
            {
                //get the CHPX
                if (fkp.rgfc[j] < fcMin && fkp.rgfc[j + 1] > fcMin)
                {
                    //this chpx starts before fcMin
                    list.add(fkp.grpchpx[j]);
                }
                else if (fkp.rgfc[j] >= fcMin && fkp.rgfc[j] < fcMax)
                {
                    //this chpx is in the range
                    list.add(fkp.grpchpx[j]);
                }
                  
            }
        }
        return list;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<WordDocument>)mapping).apply(this);
    }

}


