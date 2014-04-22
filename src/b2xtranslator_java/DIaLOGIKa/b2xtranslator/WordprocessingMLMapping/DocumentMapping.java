//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:13 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.JavaSupport.Collections.Generic.LCC.CollectionSupport;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BookmarkFirst;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileShapeAddress;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FontFamilyName;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FormFieldData;
import DIaLOGIKa.b2xtranslator.DocFileFormat.NilPicfAndBinData;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.PictureDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SectionPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TablePropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TextMark;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.CharacterPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.CommentsMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DateMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.EndnotesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.Field;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.FooterMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.FootnotesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.FormFieldDataMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.HeaderMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.MainDocumentMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.OleObject;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.OleObjectMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ParagraphPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData.RevisionType;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TableCellPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TableInfo;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TablePropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TableRowPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.VMLPictureMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.VMLShapeMapping;
import java.util.Collections;

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
public abstract class DocumentMapping  extends AbstractOpenXmlMapping implements IMapping<WordDocument>
{
    protected WordDocument _doc;
    protected ConversionContext _ctx;
    protected ParagraphPropertyExceptions _lastValidPapx;
    protected SectionPropertyExceptions _lastValidSepx;
    protected int _skipRuns = 0;
    protected int _sectionNr = 0;
    protected int _footnoteNr = 0;
    protected int _endnoteNr = 0;
    protected int _commentNr = 0;
    protected boolean _writeInstrText = false;
    protected ContentPart _targetPart;
    private static class Symbol   
    {
        public String FontName;
        public String HexValue;
    }

    /**
    * Creates a new DocumentMapping that writes to the given XmlWriter
    * 
    *  @param ctx 
    *  @param targetPart
    */
    public DocumentMapping(ConversionContext ctx, ContentPart targetPart, XmlWriter writer) throws Exception {
        super(writer);
        _ctx = ctx;
        _targetPart = targetPart;
    }

    //Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo("en-US");
    /**
    * Creates a new DocumentMapping that creates a new XmLWriter on to the given ContentPart
    * 
    *  @param ctx 
    *  @param targetPart
    */
    public DocumentMapping(ConversionContext ctx, ContentPart targetPart) throws Exception {
        super(XmlWriter.Create(targetPart.getStream(), ctx.getWriterSettings()));
        _ctx = ctx;
        _targetPart = targetPart;
    }

    //Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo("en-US");
    public abstract void apply(WordDocument doc) throws Exception ;

    /**
    * Writes the table starts at the given cp value
    * 
    *  @param cp The cp at where the table begins
    *  @return The character pointer to the first character after this table
    */
    protected int writeTable(int initialCp, long nestingLevel) throws Exception {
        int cp = initialCp;
        int fc = _doc.PieceTable.FileCharacterPositions.get(cp);
        ParagraphPropertyExceptions papx = findValidPapx(fc);
        TableInfo tai = new TableInfo(papx);
        //build the table grid
        CSList<Short> grid = buildTableGrid(cp,nestingLevel);
        //find first row end
        int fcRowEnd = findRowEndFc(cp,nestingLevel);
        TablePropertyExceptions row1Tapx = new TablePropertyExceptions(findValidPapx(fcRowEnd),_doc.DataStream);
        //start table
        _writer.WriteStartElement("w", "tbl", OpenXmlNamespaces.WordprocessingML);
        //Convert it
        row1Tapx.Convert(new TablePropertiesMapping(_writer,_doc.Styles,grid));
        //convert all rows
        if (nestingLevel > 1)
        {
            while (tai.iTap == nestingLevel)
            {
                //It's an inner table
                //only convert the cells with the given nesting level
                cp = writeTableRow(cp,grid,nestingLevel);
                fc = _doc.PieceTable.FileCharacterPositions.get(cp);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
            }
        }
        else
        {
            while (tai.fInTable)
            {
                //It's a outer table (nesting level 1)
                //convert until the end of table is reached
                cp = writeTableRow(cp,grid,nestingLevel);
                fc = _doc.PieceTable.FileCharacterPositions.get(cp);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
            }
        } 
        //close w:tbl
        _writer.writeEndElement();
        return cp;
    }

    /**
    * Writes the table row that starts at the given cp value and ends at the next row end mark
    * 
    *  @param initialCp The cp at where the row begins
    *  @return The character pointer to the first character after this row
    */
    protected int writeTableRow(int initialCp, CSList<Short> grid, long nestingLevel) throws Exception {
        int cp = initialCp;
        int fc = _doc.PieceTable.FileCharacterPositions.get(cp);
        ParagraphPropertyExceptions papx = findValidPapx(fc);
        TableInfo tai = new TableInfo(papx);
        //start w:tr
        _writer.WriteStartElement("w", "tr", OpenXmlNamespaces.WordprocessingML);
        //convert the properties
        int fcRowEnd = findRowEndFc(cp,nestingLevel);
        ParagraphPropertyExceptions rowEndPapx = findValidPapx(fcRowEnd);
        TablePropertyExceptions tapx = new TablePropertyExceptions(rowEndPapx,_doc.DataStream);
        CSList<CharacterPropertyExceptions> chpxs = _doc.getCharacterPropertyExceptions(fcRowEnd,fcRowEnd + 1);
        if (tapx != null)
        {
            tapx.Convert(new TableRowPropertiesMapping(_writer,chpxs.get(0)));
        }
         
        int gridIndex = 0;
        int cellIndex = 0;
        if (nestingLevel > 1)
        {
            while (!(_doc.Text.get(cp) == TextMark.ParagraphEnd && tai.fInnerTtp) && tai.fInTable)
            {
                //It's an inner table.
                //Write until the first "inner trailer paragraph" is reached
                RefSupport<Integer> refVar___0 = new RefSupport<Integer>(gridIndex);
                cp = writeTableCell(cp,tapx,grid,refVar___0,cellIndex,nestingLevel);
                gridIndex = refVar___0.getValue();
                cellIndex++;
                //each cell has it's own PAPX
                fc = _doc.PieceTable.FileCharacterPositions.get(cp);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
            }
        }
        else
        {
            while (!(_doc.Text.get(cp) == TextMark.CellOrRowMark && tai.fTtp) && tai.fInTable)
            {
                //It's a outer table
                //Write until the first "row end trailer paragraph" is reached
                RefSupport<Integer> refVar___1 = new RefSupport<Integer>(gridIndex);
                cp = writeTableCell(cp,tapx,grid,refVar___1,cellIndex,nestingLevel);
                gridIndex = refVar___1.getValue();
                cellIndex++;
                //each cell has it's own PAPX
                fc = _doc.PieceTable.FileCharacterPositions.get(cp);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
            }
        } 
        //end w:tr
        _writer.writeEndElement();
        //skip the row end mark
        cp++;
        return cp;
    }

    /**
    * Writes the table cell that starts at the given cp value and ends at the next cell end mark
    * 
    *  @param initialCp The cp at where the cell begins
    *  @param tapx The TAPX that formats the row to which the cell belongs
    *  @param gridIndex The index of this cell in the grid
    *  @param gridIndex The grid
    *  @return The character pointer to the first character after this cell
    */
    protected int writeTableCell(int initialCp, TablePropertyExceptions tapx, CSList<Short> grid, RefSupport<Integer> gridIndex, int cellIndex, long nestingLevel) throws Exception {
        int cp = initialCp;
        //start w:tc
        _writer.WriteStartElement("w", "tc", OpenXmlNamespaces.WordprocessingML);
        //find cell end
        int cpCellEnd = findCellEndCp(initialCp,nestingLevel);
        //convert the properties
        TableCellPropertiesMapping mapping = new TableCellPropertiesMapping(_writer,grid,gridIndex.getValue(),cellIndex);
        if (tapx != null)
        {
            tapx.Convert(mapping);
        }
         
        gridIndex.setValue(gridIndex.getValue() + mapping.getGridSpan());
        while (cp < cpCellEnd)
        {
            //write the paragraphs of the cell
            //cp = writeParagraph(cp);
            int fc = _doc.PieceTable.FileCharacterPositions.get(cp);
            ParagraphPropertyExceptions papx = findValidPapx(fc);
            TableInfo tai = new TableInfo(papx);
            //cp = writeParagraph(cp);
            if (tai.iTap > nestingLevel)
            {
                //write the inner table if this is not a inner table (endless loop)
                cp = writeTable(cp,tai.iTap);
            }
            else
            {
                //after a inner table must be at least one paragraph
                //if (cp >= cpCellEnd)
                //{
                //    _writer.WriteStartElement("w", "p", OpenXmlNamespaces.WordprocessingML);
                //    _writer.WriteEndElement();
                //}
                //this PAPX is for a normal paragraph
                cp = writeParagraph(cp);
            } 
        }
        //end w:tc
        _writer.writeEndElement();
        return cp;
    }

    /**
    * Builds a list that contains the width of the several columns of the table.
    * 
    *  @param initialCp 
    *  @return
    */
    protected CSList<Short> buildTableGrid(int initialCp, long nestingLevel) throws Exception {
        ParagraphPropertyExceptions backup = _lastValidPapx;
        CSList<Short> boundaries = new CSList<Short>();
        CSList<Short> grid = new CSList<Short>();
        int cp = initialCp;
        int fc = _doc.PieceTable.FileCharacterPositions.get(cp);
        ParagraphPropertyExceptions papx = findValidPapx(fc);
        TableInfo tai = new TableInfo(papx);
        RefSupport<Integer> refVar___2 = new RefSupport<Integer>();
        int fcRowEnd = findRowEndFc(cp,refVar___2,nestingLevel);
        cp = refVar___2.getValue();
        while (tai.fInTable)
        {
            for (SinglePropertyModifier sprm : papx.grpprl)
            {
                //check all SPRMs of this TAPX
                //find the tDef SPRM
                if (sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmTDefTable)
                {
                    byte itcMac = sprm.Arguments[0];
                    for (int i = 0;i < itcMac;i++)
                    {
                        short boundary1 = System.BitConverter.ToInt16(sprm.Arguments, 1 + (i * 2));
                        if (!boundaries.contains(boundary1))
                            boundaries.add(boundary1);
                         
                        short boundary2 = System.BitConverter.ToInt16(sprm.Arguments, 1 + ((i + 1) * 2));
                        if (!boundaries.contains(boundary2))
                            boundaries.add(boundary2);
                         
                    }
                }
                 
            }
            //get the next papx
            papx = findValidPapx(fcRowEnd);
            tai = new TableInfo(papx);
            RefSupport<Integer> refVar___3 = new RefSupport<Integer>();
            fcRowEnd = findRowEndFc(cp,refVar___3,nestingLevel);
            cp = refVar___3.getValue();
        }
        //build the grid based on the boundaries
        Collections.sort(boundaries);
        for (int i = 0;i < boundaries.size() - 1;i++)
        {
            grid.add((short)(boundaries.get(i + 1) - boundaries.get(i)));
        }
        _lastValidPapx = backup;
        return grid;
    }

    /**
    * Finds the FC of the next row end mark.
    * 
    *  @param initialCp Some CP before the row end
    *  @param rowEndCp The CP of the next row end mark
    *  @return The FC of the next row end mark
    */
    protected int findRowEndFc(int initialCp, RefSupport<Integer> rowEndCp, long nestingLevel) throws Exception {
        int cp = initialCp;
        int fc = _doc.PieceTable.FileCharacterPositions.get(cp);
        ParagraphPropertyExceptions papx = findValidPapx(fc);
        TableInfo tai = new TableInfo(papx);
        if (nestingLevel > 1)
        {
            while (tai.fInnerTtp == false && tai.fInTable == true)
            {
                while (_doc.Text.get(cp) != TextMark.ParagraphEnd)
                {
                    //Its an inner table.
                    //Search the "inner table trailer paragraph"
                    cp++;
                }
                fc = _doc.PieceTable.FileCharacterPositions.get(cp);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
                cp++;
            }
        }
        else
        {
            while (tai.fTtp == false && tai.fInTable == true)
            {
                while (_doc.Text.get(cp) != TextMark.CellOrRowMark)
                {
                    //Its an outer table.
                    //Search the "table trailer paragraph"
                    cp++;
                }
                fc = _doc.PieceTable.FileCharacterPositions.get(cp);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
                cp++;
            }
        } 
        rowEndCp.setValue(cp);
        return fc;
    }

    /**
    * Finds the FC of the next row end mark.
    * 
    *  @param cp 
    *  @return
    */
    protected int findRowEndFc(int initialCp, long nestingLevel) throws Exception {
        int cp = initialCp;
        int fc = _doc.PieceTable.FileCharacterPositions.get(cp);
        ParagraphPropertyExceptions papx = findValidPapx(fc);
        TableInfo tai = new TableInfo(papx);
        if (nestingLevel > 1)
        {
            while (tai.fInnerTtp == false && tai.fInTable == true)
            {
                while (_doc.Text.get(cp) != TextMark.ParagraphEnd)
                {
                    //Its an inner table.
                    //Search the "inner table trailer paragraph"
                    cp++;
                }
                fc = _doc.PieceTable.FileCharacterPositions.get(cp);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
                cp++;
            }
        }
        else
        {
            while (tai.fTtp == false && tai.fInTable == true)
            {
                while (_doc.Text.get(cp) != TextMark.CellOrRowMark)
                {
                    //Its an outer table.
                    //Search the "table trailer paragraph"
                    cp++;
                }
                fc = _doc.PieceTable.FileCharacterPositions.get(cp);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
                cp++;
            }
        } 
        return fc;
    }

    protected int findCellEndCp(int initialCp, long nestingLevel) throws Exception {
        int cpCellEnd = initialCp;
        if (nestingLevel > 1)
        {
            int fc = _doc.PieceTable.FileCharacterPositions.get(initialCp);
            ParagraphPropertyExceptions papx = findValidPapx(fc);
            TableInfo tai = new TableInfo(papx);
            while (!tai.fInnerTableCell)
            {
                cpCellEnd++;
                fc = _doc.PieceTable.FileCharacterPositions.get(cpCellEnd);
                papx = findValidPapx(fc);
                tai = new TableInfo(papx);
            }
            cpCellEnd++;
        }
        else
        {
            while (_doc.Text.get(cpCellEnd) != TextMark.CellOrRowMark)
            {
                cpCellEnd++;
            }
            cpCellEnd++;
        } 
        return cpCellEnd;
    }

    /**
    * Writes a Paragraph that starts at the given cp and
    * ends at the next paragraph end mark or section end mark
    * 
    *  @param cp
    */
    protected int writeParagraph(int cp) throws Exception {
        //search the paragraph end
        int cpParaEnd = cp;
        while (_doc.Text.get(cpParaEnd) != TextMark.ParagraphEnd && _doc.Text.get(cpParaEnd) != TextMark.CellOrRowMark && !(_doc.Text.get(cpParaEnd) == TextMark.PageBreakOrSectionMark && isSectionEnd(cpParaEnd)))
        {
            cpParaEnd++;
        }
        if (_doc.Text.get(cpParaEnd) == TextMark.PageBreakOrSectionMark)
        {
            //there is a page break OR section mark,
            //write the section only if it's a section mark
            boolean sectionEnd = isSectionEnd(cpParaEnd);
            cpParaEnd++;
            return writeParagraph(cp,cpParaEnd,sectionEnd);
        }
        else
        {
            cpParaEnd++;
            return writeParagraph(cp,cpParaEnd,false);
        } 
    }

    /**
    * Writes a Paragraph that starts at the given cpStart and
    * ends at the given cpEnd
    * 
    *  @param cpStart 
    *  @param cpEnd 
    *  @param sectionEnd Set if this paragraph is the last paragraph of a section
    *  @return
    */
    protected int writeParagraph(int initialCp, int cpEnd, boolean sectionEnd) throws Exception {
        int cp = initialCp;
        int fc = _doc.PieceTable.FileCharacterPositions.get(cp);
        int fcEnd = _doc.PieceTable.FileCharacterPositions.get(cpEnd);
        ParagraphPropertyExceptions papx = findValidPapx(fc);
        //get all CHPX between these boundaries to determine the count of runs
        CSList<CharacterPropertyExceptions> chpxs = _doc.getCharacterPropertyExceptions(fc,fcEnd);
        CSList<Integer> chpxFcs = _doc.getFileCharacterPositions(fc,fcEnd);
        chpxFcs.add(fcEnd);
        //the last of these CHPX formats the paragraph end mark
        CharacterPropertyExceptions paraEndChpx = chpxs.get(chpxs.size() - 1);
        //start paragraph
        _writer.WriteStartElement("w", "p", OpenXmlNamespaces.WordprocessingML);
        //check for section properties
        if (sectionEnd)
        {
            //this is the last paragraph of this section
            //write properties with section properties
            if (papx != null)
            {
                papx.Convert(new ParagraphPropertiesMapping(_writer,_ctx,_doc,paraEndChpx,findValidSepx(cpEnd),_sectionNr));
            }
             
            _sectionNr++;
        }
        else
        {
            //write properties
            if (papx != null)
            {
                papx.Convert(new ParagraphPropertiesMapping(_writer,_ctx,_doc,paraEndChpx));
            }
             
        } 
        for (int i = 0;i < chpxs.size();i++)
        {
            //write a runs for each CHPX
            //get the FC range for this run
            int fcChpxStart = chpxFcs.get(i);
            int fcChpxEnd = chpxFcs.get(i + 1);
            //it's the first chpx and it starts before the paragraph
            if (i == 0 && fcChpxStart < fc)
            {
                //so use the FC of the paragraph
                fcChpxStart = fc;
            }
             
            //it's the last chpx and it exceeds the paragraph
            if (i == (chpxs.size() - 1) && fcChpxEnd > fcEnd)
            {
                //so use the FC of the paragraph
                fcChpxEnd = fcEnd;
            }
             
            //read the chars that are formatted via this CHPX
            CSList<Character> chpxChars = _doc.PieceTable.getChars(fcChpxStart,fcChpxEnd,_doc.WordDocumentStream);
            //search for bookmarks in the chars
            CSList<Integer> bookmarks = searchBookmarks(chpxChars,cp);
            //if there are bookmarks in this run, split the run into several runs
            if (bookmarks.size() > 0)
            {
                CSList<CSList<Character>> runs = splitCharList(chpxChars,bookmarks);
                for (int s = 0;s < runs.size();s++)
                {
                    if (_doc.BookmarkStartPlex.CharacterPositions.contains(cp) && _doc.BookmarkEndPlex.CharacterPositions.contains(cp))
                    {
                        for (int b = 0;b < _doc.BookmarkEndPlex.CharacterPositions.size();b++)
                        {
                            //there start and end bookmarks here
                            //so get all bookmarks that end here
                            if (_doc.BookmarkEndPlex.CharacterPositions.get(b) == cp)
                            {
                                //and check if the matching start bookmark also starts here
                                if (_doc.BookmarkStartPlex.CharacterPositions.get(b) == cp)
                                {
                                    //then write a start and a end
                                    if (_doc.BookmarkStartPlex.Elements.size() > b)
                                    {
                                        writeBookmarkStart((BookmarkFirst)_doc.BookmarkStartPlex.Elements.get(b));
                                        writeBookmarkEnd((BookmarkFirst)_doc.BookmarkStartPlex.Elements.get(b));
                                    }
                                     
                                }
                                else
                                {
                                    //write a end
                                    writeBookmarkEnd((BookmarkFirst)_doc.BookmarkStartPlex.Elements.get(b));
                                } 
                            }
                             
                        }
                        writeBookmarkStarts(cp);
                    }
                    else if (_doc.BookmarkStartPlex.CharacterPositions.contains(cp))
                    {
                        writeBookmarkStarts(cp);
                    }
                    else if (_doc.BookmarkEndPlex.CharacterPositions.contains(cp))
                    {
                        writeBookmarkEnds(cp);
                    }
                       
                    cp = writeRun(runs.get(s),chpxs.get(i),cp);
                }
            }
            else
            {
                cp = writeRun(chpxChars,chpxs.get(i),cp);
            } 
        }
        //end paragraph
        _writer.writeEndElement();
        return cpEnd++;
    }

    /**
    * Writes a run with the given characters and CHPX
    */
    protected int writeRun(CSList<Character> chars, CharacterPropertyExceptions chpx, int initialCp) throws Exception {
        int cp = initialCp;
        if (_skipRuns <= 0 && chars.size() > 0)
        {
            RevisionData rev = new RevisionData(chpx);
            if (rev.Type == RevisionType.Deleted)
            {
                //If it's a deleted run
                _writer.WriteStartElement("w", "del", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "author", OpenXmlNamespaces.WordprocessingML, "[b2x: could not retrieve author]");
                _writer.WriteAttributeString("w", "date", OpenXmlNamespaces.WordprocessingML, "[b2x: could not retrieve date]");
            }
            else if (rev.Type == RevisionType.Inserted)
            {
                //if it's a inserted run
                _writer.WriteStartElement("w", "ins", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "author", OpenXmlNamespaces.WordprocessingML, _doc.RevisionAuthorTable.Strings[rev.Isbt]);
                rev.Dttm.Convert(new DateMapping(_writer));
            }
              
            //start run
            _writer.WriteStartElement("w", "r", OpenXmlNamespaces.WordprocessingML);
            //append rsids
            if (rev.Rsid != 0)
            {
                String rsid = String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),rev.Rsid);
                _writer.WriteAttributeString("w", "rsidR", OpenXmlNamespaces.WordprocessingML, rsid);
                _ctx.addRsid(rsid);
            }
             
            if (rev.RsidDel != 0)
            {
                String rsidDel = String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),rev.RsidDel);
                _writer.WriteAttributeString("w", "rsidDel", OpenXmlNamespaces.WordprocessingML, rsidDel);
                _ctx.addRsid(rsidDel);
            }
             
            if (rev.RsidProp != 0)
            {
                String rsidProp = String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),rev.RsidProp);
                _writer.WriteAttributeString("w", "rsidRPr", OpenXmlNamespaces.WordprocessingML, rsidProp);
                _ctx.addRsid(rsidProp);
            }
             
            //convert properties
            chpx.Convert(new CharacterPropertiesMapping(_writer,_doc,rev,_lastValidPapx,false));
            if (rev.Type == RevisionType.Deleted)
                writeText(chars,cp,chpx,true);
            else
                writeText(chars,cp,chpx,false); 
            //end run
            _writer.writeEndElement();
            if (rev.Type == RevisionType.Deleted || rev.Type == RevisionType.Inserted)
            {
                _writer.writeEndElement();
            }
             
        }
        else
        {
            _skipRuns--;
        } 
        return cp + chars.size();
    }

    /**
    * Writes the given text to the document
    * 
    *  @param chars
    */
    protected void writeText(CSList<Character> chars, int initialCp, CharacterPropertyExceptions chpx, boolean writeDeletedText) throws Exception {
        int cp = initialCp;
        boolean fSpec = isSpecial(chpx);
        //detect text type
        String textType = "t";
        if (writeDeletedText)
            textType = "delText";
        else if (_writeInstrText)
            textType = "instrText";
          
        //open a new w:t element
        writeTextStart(textType);
        for (int i = 0;i < chars.size();i++)
        {
            //write text
            char c = chars.get(i);
            if (c == TextMark.Tab)
            {
                _writer.writeEndElement();
                _writer.WriteElementString("w", "tab", OpenXmlNamespaces.WordprocessingML, "");
                writeTextStart(textType);
            }
            else if (c == TextMark.HardLineBreak)
            {
                //close previous w:t ...
                _writer.writeEndElement();
                _writer.WriteElementString("w", "br", OpenXmlNamespaces.WordprocessingML, "");
                writeTextStart(textType);
            }
            else if (c == TextMark.ParagraphEnd)
            {
            }
            else //do nothing
            if (c == TextMark.PageBreakOrSectionMark)
            {
                //write page break, section breaks are written by writeParagraph() method
                if (!isSectionEnd(cp))
                {
                    //close previous w:t ...
                    _writer.writeEndElement();
                    _writer.WriteStartElement("w", "br", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "page");
                    _writer.writeEndElement();
                    writeTextStart(textType);
                }
                 
            }
            else if (c == TextMark.ColumnBreak)
            {
                //close previous w:t ...
                _writer.writeEndElement();
                _writer.WriteStartElement("w", "br", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "column");
                _writer.writeEndElement();
                writeTextStart(textType);
            }
            else if (c == TextMark.FieldBeginMark)
            {
                //close previous w:t ...
                _writer.writeEndElement();
                int cpFieldStart = initialCp + i;
                int cpFieldEnd = searchNextTextMark(_doc.Text,cpFieldStart,TextMark.FieldEndMark);
                Field f = new Field(_doc.Text.GetRange(cpFieldStart, cpFieldEnd - cpFieldStart + 1));
                if (f.FieldCode.startsWith(" FORM"))
                {
                    _writer.WriteStartElement("w", "fldChar", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "fldCharType", OpenXmlNamespaces.WordprocessingML, "begin");
                    int cpPic = searchNextTextMark(_doc.Text,cpFieldStart,TextMark.Picture);
                    if (cpPic < cpFieldEnd)
                    {
                        int fcPic = _doc.PieceTable.FileCharacterPositions.get(cpPic);
                        CharacterPropertyExceptions chpxPic = _doc.getCharacterPropertyExceptions(fcPic,fcPic + 1).get(0);
                        NilPicfAndBinData npbd = new NilPicfAndBinData(chpxPic,_doc.DataStream);
                        FormFieldData ffdata = new FormFieldData(npbd.binData);
                        ffdata.Convert(new FormFieldDataMapping(_writer));
                    }
                     
                    _writer.writeEndElement();
                }
                else if (f.FieldCode.startsWith(" EMBED") || f.FieldCode.startsWith(" LINK"))
                {
                    _writer.WriteStartElement("w", "object", OpenXmlNamespaces.WordprocessingML);
                    int cpPic = searchNextTextMark(_doc.Text,cpFieldStart,TextMark.Picture);
                    int cpFieldSep = searchNextTextMark(_doc.Text,cpFieldStart,TextMark.FieldSeperator);
                    if (cpPic < cpFieldEnd)
                    {
                        int fcPic = _doc.PieceTable.FileCharacterPositions.get(cpPic);
                        CharacterPropertyExceptions chpxPic = _doc.getCharacterPropertyExceptions(fcPic,fcPic + 1).get(0);
                        PictureDescriptor pic = new PictureDescriptor(chpxPic,_doc.DataStream);
                        //append the origin attributes
                        _writer.WriteAttributeString("w", "dxaOrig", OpenXmlNamespaces.WordprocessingML, String.valueOf((pic.dxaGoal + pic.dxaOrigin)));
                        _writer.WriteAttributeString("w", "dyaOrig", OpenXmlNamespaces.WordprocessingML, String.valueOf((pic.dyaGoal + pic.dyaOrigin)));
                        pic.Convert(new VMLPictureMapping(_writer,this._targetPart,true));
                        if (cpFieldSep < cpFieldEnd)
                        {
                            int fcFieldSep = _doc.PieceTable.FileCharacterPositions.get(cpFieldSep);
                            CharacterPropertyExceptions chpxSep = _doc.getCharacterPropertyExceptions(fcFieldSep,fcFieldSep + 1).get(0);
                            OleObject ole = new OleObject(chpxSep,_doc.Storage);
                            ole.Convert(new OleObjectMapping(_writer,_doc,_targetPart,pic));
                        }
                         
                    }
                     
                    _writer.writeEndElement();
                    _skipRuns = 4;
                }
                else
                {
                    _writer.WriteStartElement("w", "fldChar", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "fldCharType", OpenXmlNamespaces.WordprocessingML, "begin");
                    _writer.writeEndElement();
                }  
                _writeInstrText = true;
                writeTextStart("instrText");
            }
            else if (c == TextMark.FieldSeperator)
            {
                //close previous w:t ...
                _writer.writeEndElement();
                _writer.WriteStartElement("w", "fldChar", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "fldCharType", OpenXmlNamespaces.WordprocessingML, "separate");
                _writer.writeEndElement();
                writeTextStart(textType);
            }
            else if (c == TextMark.FieldEndMark)
            {
                //close previous w:t ...
                _writer.writeEndElement();
                _writer.WriteStartElement("w", "fldChar", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "fldCharType", OpenXmlNamespaces.WordprocessingML, "end");
                _writer.writeEndElement();
                _writeInstrText = false;
                writeTextStart("t");
            }
            else if (c == TextMark.Symbol && fSpec)
            {
                //close previous w:t ...
                _writer.writeEndElement();
                Symbol s = getSymbol(chpx);
                _writer.WriteStartElement("w", "sym", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "font", OpenXmlNamespaces.WordprocessingML, s.FontName);
                _writer.WriteAttributeString("w", "char", OpenXmlNamespaces.WordprocessingML, s.HexValue);
                _writer.writeEndElement();
                writeTextStart(textType);
            }
            else if (c == TextMark.DrawnObject && fSpec)
            {
                FileShapeAddress fspa = null;
                if (getClass() == MainDocumentMapping.class)
                {
                    fspa = (FileShapeAddress)_doc.OfficeDrawingPlex.getStruct(cp);
                }
                else if (getClass() == HeaderMapping.class || getClass() == FooterMapping.class)
                {
                    int headerCp = cp - _doc.FIB.ccpText - _doc.FIB.ccpFtn;
                    fspa = (FileShapeAddress)_doc.OfficeDrawingPlexHeader.getStruct(headerCp);
                }
                  
                if (fspa != null)
                {
                    ShapeContainer shape = _doc.OfficeArtContent.getShapeContainer(fspa.spid);
                    if (shape != null)
                    {
                        //close previous w:t ...
                        _writer.writeEndElement();
                        _writer.WriteStartElement("w", "pict", OpenXmlNamespaces.WordprocessingML);
                        shape.Convert(new VMLShapeMapping(_writer,_targetPart,fspa,null,_ctx));
                        _writer.writeEndElement();
                        writeTextStart(textType);
                    }
                     
                }
                 
            }
            else if (c == TextMark.Picture && fSpec)
            {
                PictureDescriptor pict = new PictureDescriptor(chpx,_doc.DataStream);
                if (pict.mfp.mm > 98 && pict.ShapeContainer != null)
                {
                    //close previous w:t ...
                    _writer.writeEndElement();
                    _writer.WriteStartElement("w", "pict", OpenXmlNamespaces.WordprocessingML);
                    if (isWordArtShape(pict.ShapeContainer))
                    {
                        // a PICT without a BSE can stand for a WordArt Shape
                        pict.ShapeContainer.Convert(new VMLShapeMapping(_writer,_targetPart,null,pict,_ctx));
                    }
                    else
                    {
                        // it's a normal picture
                        pict.Convert(new VMLPictureMapping(_writer,_targetPart,false));
                    } 
                    _writer.writeEndElement();
                    writeTextStart(textType);
                }
                 
            }
            else if (c == TextMark.AutoNumberedFootnoteReference && fSpec)
            {
                //close previous w:t ...
                _writer.writeEndElement();
                if (this.getClass() != FootnotesMapping.class && this.getClass() != EndnotesMapping.class)
                {
                    //it's in the document
                    if (this._doc.FootnoteReferencePlex.CharacterPositions.contains(cp))
                    {
                        _writer.WriteStartElement("w", "footnoteReference", OpenXmlNamespaces.WordprocessingML);
                        _writer.WriteAttributeString("w", "id", OpenXmlNamespaces.WordprocessingML, String.valueOf(_footnoteNr));
                        _writer.writeEndElement();
                        _footnoteNr++;
                    }
                    else if (this._doc.EndnoteReferencePlex.CharacterPositions.contains(cp))
                    {
                        _writer.WriteStartElement("w", "endnoteReference", OpenXmlNamespaces.WordprocessingML);
                        _writer.WriteAttributeString("w", "id", OpenXmlNamespaces.WordprocessingML, String.valueOf(_endnoteNr));
                        _writer.writeEndElement();
                        _endnoteNr++;
                    }
                      
                }
                else
                {
                    // it's not the document, write the short ref
                    if (this.getClass() != FootnotesMapping.class)
                    {
                        _writer.WriteElementString("w", "footnoteRef", OpenXmlNamespaces.WordprocessingML, "");
                    }
                     
                    if (this.getClass() != EndnotesMapping.class)
                    {
                        _writer.WriteElementString("w", "endnoteRef", OpenXmlNamespaces.WordprocessingML, "");
                    }
                     
                } 
                writeTextStart(textType);
            }
            else if (c == TextMark.AnnotationReference)
            {
                //close previous w:t ...
                _writer.writeEndElement();
                if (this.getClass() != CommentsMapping.class)
                {
                    _writer.WriteStartElement("w", "commentReference", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "id", OpenXmlNamespaces.WordprocessingML, String.valueOf(_commentNr));
                    _writer.writeEndElement();
                }
                else
                {
                    _writer.WriteElementString("w", "annotationRef", OpenXmlNamespaces.WordprocessingML, "");
                } 
                _commentNr++;
                writeTextStart(textType);
            }
            else if ((int)c > 31 && (int)c != 0xFFFF)
            {
                _writer.WriteChars(new char[]{ c }, 0, 1);
            }
                          
            cp++;
        }
        //close w:t
        _writer.writeEndElement();
    }

    protected void writeTextStart(String textType) throws Exception {
        _writer.WriteStartElement("w", textType, OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("xml", "space", "", "preserve");
    }

    /**
    * Writes a bookmark start element at the given position
    * 
    *  @param cp
    */
    protected void writeBookmarkStarts(int cp) throws Exception {
        if (_doc.BookmarkStartPlex.CharacterPositions.size() > 1)
        {
            for (int b = 0;b < _doc.BookmarkStartPlex.CharacterPositions.size();b++)
            {
                if (_doc.BookmarkStartPlex.CharacterPositions.get(b) == cp)
                {
                    if (_doc.BookmarkStartPlex.Elements.size() > b)
                    {
                        writeBookmarkStart((BookmarkFirst)_doc.BookmarkStartPlex.Elements.get(b));
                    }
                     
                }
                 
            }
        }
         
    }

    protected void writeBookmarkStart(BookmarkFirst bookmark) throws Exception {
        //write bookmark start
        _writer.WriteStartElement("w", "bookmarkStart", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "id", OpenXmlNamespaces.WordprocessingML, String.valueOf(bookmark.ibkl));
        _writer.WriteAttributeString("w", "name", OpenXmlNamespaces.WordprocessingML, _doc.BookmarkNames.Strings[bookmark.ibkl]);
        _writer.writeEndElement();
    }

    /**
    * Writes a bookmark end element at the given position
    * 
    *  @param cp
    */
    protected void writeBookmarkEnds(int cp) throws Exception {
        if (_doc.BookmarkEndPlex.CharacterPositions.size() > 1)
        {
            for (int b = 0;b < _doc.BookmarkEndPlex.CharacterPositions.size();b++)
            {
                //write all bookmark ends
                if (_doc.BookmarkEndPlex.CharacterPositions.get(b) == cp)
                {
                    writeBookmarkEnd((BookmarkFirst)_doc.BookmarkStartPlex.Elements.get(b));
                }
                 
            }
        }
         
    }

    protected void writeBookmarkEnd(BookmarkFirst bookmark) throws Exception {
        //write bookmark end
        _writer.WriteStartElement("w", "bookmarkEnd", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "id", OpenXmlNamespaces.WordprocessingML, String.valueOf(bookmark.ibkl));
        _writer.writeEndElement();
    }

    protected boolean isWordArtShape(ShapeContainer shape) throws Exception {
        boolean result = false;
        CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry> options = shape.extractOptions();
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry entry : options)
        {
            if (entry.pid == DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId.gtextUNICODE)
            {
                result = true;
                break;
            }
             
        }
        return result;
    }

    /**
    * Splits a list of characters into several lists
    * 
    *  @return
    */
    protected CSList<CSList<Character>> splitCharList(CSList<Character> chars, CSList<Integer> splitIndices) throws Exception {
        CSList<CSList<Character>> ret = new CSList<CSList<Character>>();
        int startIndex = 0;
        for (int i = 0;i < splitIndices.size();i++)
        {
            //add the parts
            int cch = splitIndices.get(i) - startIndex;
            if (cch > 0)
            {
                ret.Add(chars.GetRange(startIndex, cch));
            }
             
            startIndex += cch;
        }
        //add the last part
        ret.Add(chars.GetRange(startIndex, chars.size() - startIndex));
        return ret;
    }

    /**
    * Searches for bookmarks in the list of characters.
    * 
    *  @param chars 
    *  @return A List with all bookmarks indices in the given character list
    */
    protected CSList<Integer> searchBookmarks(CSList<Character> chars, int initialCp) throws Exception {
        CSList<Integer> ret = new CSList<Integer>();
        int cp = initialCp;
        for (int i = 0;i < chars.size();i++)
        {
            if (_doc.BookmarkStartPlex.CharacterPositions.contains(cp) || _doc.BookmarkEndPlex.CharacterPositions.contains(cp))
            {
                ret.add(i);
            }
             
            cp++;
        }
        return ret;
    }

    /**
    * Searches the given List for the next FieldEnd character.
    * 
    *  @param chars The List of chars
    *  @param initialCp The position where the search should start
    *  @param mark The TextMark
    *  @return The position of the next FieldEnd mark
    */
    protected int searchNextTextMark(CSList<Character> chars, int initialCp, char mark) throws Exception {
        int ret = initialCp;
        for (int i = initialCp;i < chars.size();i++)
        {
            if (chars.get(i) == mark)
            {
                ret = i;
                break;
            }
             
        }
        return ret;
    }

    /**
    * Checks if the PAPX is old
    * 
    *  @param chpx The PAPX
    *  @return
    */
    protected boolean isOld(ParagraphPropertyExceptions papx) throws Exception {
        boolean ret = false;
        for (SinglePropertyModifier sprm : papx.grpprl)
        {
            if (sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmPWall)
            {
                //sHasOldProps
                ret = true;
                break;
            }
             
        }
        return ret;
    }

    /**
    * Checks if the CHPX is special
    * 
    *  @param chpx The CHPX
    *  @return
    */
    protected boolean isSpecial(CharacterPropertyExceptions chpx) throws Exception {
        boolean ret = false;
        for (SinglePropertyModifier sprm : chpx.grpprl)
        {
            if (sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmCPicLocation || sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmCHsp)
            {
                //special picture
                ret = true;
                break;
            }
            else if (sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmCSymbol)
            {
                //special symbol
                ret = true;
                break;
            }
            else if (sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmCFSpec)
            {
                //special value
                ret = DIaLOGIKa.b2xtranslator.Tools.Utils.byteToBool(sprm.Arguments[0]);
                break;
            }
               
        }
        return ret;
    }

    /**
    * @param chpx 
    *  @return
    */
    private Symbol getSymbol(CharacterPropertyExceptions chpx) throws Exception {
        Symbol ret = null;
        for (SinglePropertyModifier sprm : chpx.grpprl)
        {
            if (sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmCSymbol)
            {
                //special symbol
                ret = new Symbol();
                short fontIndex = System.BitConverter.ToInt16(sprm.Arguments, 0);
                short code = System.BitConverter.ToInt16(sprm.Arguments, 2);
                FontFamilyName ffn = (FontFamilyName)_doc.FontTable.Data[fontIndex];
                ret.FontName = ffn.xszFtn;
                ret.HexValue = String.format(StringSupport.CSFmtStrToJFmtStr("{0:x4}"),code);
                break;
            }
             
        }
        return ret;
    }

    /**
    * Looks into the section table to find out if this CP is the end of a section
    * 
    *  @param cp 
    *  @return
    */
    protected boolean isSectionEnd(int cp) throws Exception {
        boolean result = false;
        //if cp is the last char of a section, the next section will start at cp +1
        int search = cp + 1;
        for (int i = 0;i < _doc.SectionPlex.CharacterPositions.size();i++)
        {
            if (_doc.SectionPlex.CharacterPositions.get(i) == search)
            {
                result = true;
                break;
            }
             
        }
        return result;
    }

    /**
    * Finds the PAPX that is valid for the given FC.
    * 
    *  @param fc 
    *  @return
    */
    protected ParagraphPropertyExceptions findValidPapx(int fc) throws Exception {
        ParagraphPropertyExceptions ret = null;
        if (_doc.AllPapx.containsKey(fc))
        {
            ret = _doc.AllPapx.get(fc);
            _lastValidPapx = ret;
        }
        else
        {
            ret = _lastValidPapx;
        } 
        return ret;
    }

    /**
    * Finds the SEPX that is valid for the given CP.
    * 
    *  @param cp 
    *  @return
    */
    protected SectionPropertyExceptions findValidSepx(int cp) throws Exception {
        SectionPropertyExceptions ret = null;
        try
        {
            ret = _doc.AllSepx.get(cp);
            _lastValidSepx = ret;
        }
        catch (KeyNotFoundException __dummyCatchVar0)
        {
            //there is no SEPX at this position,
            //so the previous SEPX is valid for this cp
            int lastKey = _doc.SectionPlex.CharacterPositions.get(1);
            for (int key : CollectionSupport.mk(_doc.AllSepx.keySet()))
            {
                if (cp > lastKey && cp < key)
                {
                    ret = _doc.AllSepx.get(lastKey);
                    break;
                }
                else
                {
                    lastKey = key;
                } 
            }
        }

        return ret;
    }

}


