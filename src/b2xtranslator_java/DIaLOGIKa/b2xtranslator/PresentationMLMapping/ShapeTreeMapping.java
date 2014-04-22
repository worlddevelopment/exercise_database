//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:14 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Collections.LCC.IEnumerator;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BitmapBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.BlipStoreEntry.BlipType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ChildAnchor;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientAnchor;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientData;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientTextbox;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DrawingContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DrawingGroup;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.FConnectorRule;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.FillStyleBooleanProperties;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GeometryTextBooleanProperties;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GroupContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GroupShapeRecord;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.LineStyleBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.MetafilePictBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.PathParser;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.PathSegment;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.PathSegment.SegmentType;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShadowStyleBooleanProperties;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shape;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.PropertyId;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.SolverContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.TextBooleanProperties;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ThreeDObjectProperties;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ThreeDStyleProperties;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.VmlPart;
import DIaLOGIKa.b2xtranslator.PptFileFormat.AnimationInfoContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CStringAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExObjRefAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExOleEmbedContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Handout;
import DIaLOGIKa.b2xtranslator.PptFileFormat.MasterTextPropAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.MasterTextPropRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Note;
import DIaLOGIKa.b2xtranslator.PptFileFormat.OEPlaceHolderAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.OutlineTextRefAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PlaceholderEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PPDrawing;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PPDrawingGroup;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgBinaryTag;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgBinaryTagDataBlob;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideListWithText;
import DIaLOGIKa.b2xtranslator.PptFileFormat.StyleTextProp9Atom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextHeaderAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextSpecialInfoAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextStyleAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextType;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.CharacterRunPropsMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.FillMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.PresentationMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ShadowMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.TextMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.VMLPictureMapping;
import DIaLOGIKa.b2xtranslator.Tools.FixedPointNumber;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

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
public class ShapeTreeMapping  extends AbstractOpenXmlMapping implements IMapping<PPDrawing>
{
    protected int _idCnt;
    protected ConversionContext _ctx;
    protected String _footertext;
    protected String _headertext;
    protected String _datetext;
    public PresentationMapping<RegularContainer> parentSlideMapping = null;
    public HashMap<AnimationInfoContainer,Integer> animinfos = new HashMap<AnimationInfoContainer,Integer>();
    public StyleTextProp9Atom ShapeStyleTextProp9Atom = null;
    private SortedDictionary<Integer, Integer> ColumnWidthsByYPos = new SortedDictionary<Integer, Integer>();
    private CSList<Integer> RowHeights;
    //private List<ShapeContainer> Lines;
    private SortedList<Integer, SortedList<Integer, ShapeContainer>> verticallinelist = new SortedList<Integer, SortedList<Integer, ShapeContainer>>();
    private SortedList<Integer, SortedList<Integer, ShapeContainer>> horizontallinelist = new SortedList<Integer, SortedList<Integer, ShapeContainer>>();
    public ShapeTreeMapping(ConversionContext ctx, XmlWriter writer) throws Exception {
        super(writer);
        _ctx = ctx;
    }

    public void dynamicApply(DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record) throws Exception {
        // Call Apply(record) with dynamic dispatch (selection based on run-time type of record)
        MethodInfo method = this.getClass().GetMethod("Apply", new Class[]{ record.getClass() });
        try
        {
            //TraceLogger.DebugInternal(method.ToString());
            method.Invoke(this, new Object[]{ record });
        }
        catch (TargetInvocationException e)
        {
            TraceLogger.debugInternal(e.InnerException.toString());
            throw e.InnerException;
        }
    
    }

    public void apply(PPDrawing drawing) throws Exception {
        apply((RegularContainer)drawing);
        writeVML();
    }

    public void apply(DrawingContainer drawingContainer) throws Exception {
        GroupContainer group = drawingContainer.firstChildWithType();
        IEnumerator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> iter = group.Children.GetEnumerator();
        iter.moveNext();
        ShapeContainer header = iter.getCurrent() instanceof ShapeContainer ? (ShapeContainer)iter.getCurrent() : (ShapeContainer)null;
        writeGroupShapeProperties(header);
        while (iter.moveNext())
        dynamicApply(iter.getCurrent());
    }

    //used to give each group a unique identifyer
    private int groupcounter = -10;
    public void apply(GroupContainer group) throws Exception {
        GroupShapeRecord gsr = group.firstChildWithType().<GroupShapeRecord>FirstChildWithType();
        ClientAnchor anchor = group.firstChildWithType().<ClientAnchor>FirstChildWithType();
        ChildAnchor chanchor = group.firstChildWithType().<ChildAnchor>FirstChildWithType();
        for (Object __dummyForeachVar0 : group.firstChildWithType().<ShapeOptions>AllChildrenWithType())
        {
            ShapeOptions ops = (ShapeOptions)__dummyForeachVar0;
            if (ops.OptionsByID.containsKey(PropertyId.tableProperties))
            {
                uint TABLEFLAGS = ops.OptionsByID.get(PropertyId.tableProperties).op;
                if (DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(TABLEFLAGS, 0x1))
                {
                    //this group is a table
                    applyTable(group,TABLEFLAGS);
                    return ;
                }
                 
            }
             
        }
        _writer.WriteStartElement("p", "grpSp", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "nvGrpSpPr", OpenXmlNamespaces.PresentationML);
        writeCNvPr(--groupcounter,"");
        _writer.WriteElementString("p", "cNvGrpSpPr", OpenXmlNamespaces.PresentationML, "");
        _writer.WriteElementString("p", "nvPr", OpenXmlNamespaces.PresentationML, "");
        _writer.writeEndElement();
        //nvGrpSpPr
        _writer.WriteStartElement("p", "grpSpPr", OpenXmlNamespaces.PresentationML);
        if (anchor != null && anchor.getRight() >= anchor.getLeft() && anchor.getBottom() >= anchor.getTop())
        {
            _writer.WriteStartElement("a", "xfrm", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("x", String.valueOf(Utils.masterCoordToEMU(anchor.getLeft())));
            _writer.writeAttributeString("y", String.valueOf(Utils.masterCoordToEMU(anchor.getTop())));
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("cx", String.valueOf(Utils.masterCoordToEMU(anchor.getRight() - anchor.getLeft())));
            _writer.writeAttributeString("cy", String.valueOf(Utils.masterCoordToEMU(anchor.getBottom() - anchor.getTop())));
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "chOff", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("x", gsr.rcgBounds.Left.toString());
            _writer.writeAttributeString("y", gsr.rcgBounds.Top.toString());
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "chExt", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("cx", (gsr.rcgBounds.Right - gsr.rcgBounds.Left).toString());
            _writer.writeAttributeString("cy", (gsr.rcgBounds.Bottom - gsr.rcgBounds.Top).toString());
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        else if (chanchor != null && chanchor.Right >= chanchor.Left && chanchor.Bottom >= chanchor.Top)
        {
            _writer.WriteStartElement("a", "xfrm", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("x", String.valueOf((chanchor.Left)));
            _writer.writeAttributeString("y", String.valueOf((chanchor.Top)));
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("cx", String.valueOf((chanchor.Right - chanchor.Left)));
            _writer.writeAttributeString("cy", String.valueOf((chanchor.Bottom - chanchor.Top)));
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "chOff", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("x", gsr.rcgBounds.Left.toString());
            _writer.writeAttributeString("y", gsr.rcgBounds.Top.toString());
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "chExt", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("cx", (gsr.rcgBounds.Right - gsr.rcgBounds.Left).toString());
            _writer.writeAttributeString("cy", (gsr.rcgBounds.Bottom - gsr.rcgBounds.Top).toString());
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
          
        _writer.writeEndElement();
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record : group.Children)
        {
            //grpSpPr
            dynamicApply(record);
        }
        _writer.writeEndElement();
    }

    //grpSp
    private int getGridSpanCount(ChildAnchor anch, int col) throws Exception {
        int count = 0;
        int availableWidth = anch.rcgBounds.Width;
        int currentLeft = anch.Left;
        while (availableWidth > 0)
        {
            availableWidth -= ColumnWidthsByYPos[currentLeft];
            currentLeft += ColumnWidthsByYPos[currentLeft];
            count++;
        }
        return count;
    }

    private int getRowSpanCount(ChildAnchor anch, int row) throws Exception {
        try
        {
            int count = 0;
            int availableHeight = anch.rcgBounds.Height;
            while (availableHeight > 0 && RowHeights.size() > row + count)
            {
                availableHeight -= RowHeights.get(row + count);
                if (availableHeight >= 0)
                    count++;
                else
                    return 0; 
            }
            return count;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
    
    }

    public void applyTable(GroupContainer group, uint TABLEFLAGS) throws Exception {
        GroupShapeRecord gsr = group.firstChildWithType().<GroupShapeRecord>FirstChildWithType();
        ClientAnchor anchor = group.firstChildWithType().<ClientAnchor>FirstChildWithType();
        RowHeights = new CSList<Integer>();
        for (Object __dummyForeachVar2 : group.firstChildWithType().<ShapeOptions>AllChildrenWithType())
        {
            ShapeOptions ops = (ShapeOptions)__dummyForeachVar2;
            if (ops.OptionsByID.containsKey(PropertyId.tableRowProperties))
            {
                uint TableRowPropertiesCount = ops.OptionsByID.get(PropertyId.tableRowProperties).op;
                byte[] data = ops.OptionsByID.get(PropertyId.tableRowProperties).opComplex;
                UInt16 nElems = BitConverter.ToUInt16(data, 0);
                UInt16 nElemsAlloc = BitConverter.ToUInt16(data, 2);
                UInt16 cbElem = BitConverter.ToUInt16(data, 4);
                for (int i = 0;i < nElems;i++)
                {
                    int height = BitConverter.ToInt32(data, 6 + i * cbElem);
                    //this is a workaround for a bug
                    //it should be analysed when to use 0 height
                    if (height > 53)
                    {
                        RowHeights.add(height);
                    }
                    else
                    {
                        RowHeights.add(0);
                    } 
                }
            }
             
        }
        CSList<ShapeContainer> Cells = new CSList<ShapeContainer>();
        //Lines = new List<ShapeContainer>();
        HashMap<String,ShapeContainer> LinesByPosition = new HashMap<String,ShapeContainer>();
        SortedList<Integer, SortedList<Integer, ShapeContainer>> tablelist = new SortedList<Integer, SortedList<Integer, ShapeContainer>>();
        verticallinelist = new SortedList<Integer, SortedList<Integer, ShapeContainer>>();
        horizontallinelist = new SortedList<Integer, SortedList<Integer, ShapeContainer>>();
        for (Object __dummyForeachVar4 : group.allChildrenWithType())
        {
            ShapeContainer scontainer = (ShapeContainer)__dummyForeachVar4;
            ChildAnchor anch = scontainer.firstChildWithType();
            for (Object __dummyForeachVar3 : scontainer.allChildrenWithType())
            {
                Shape shape = (Shape)__dummyForeachVar3;
                if (StringSupport.equals(Utils.getPrstForShape(shape.Instance), "rect"))
                {
                    if (!tablelist.ContainsKey(anch.Top))
                        tablelist.Add(anch.Top, new SortedList<Integer, ShapeContainer>());
                     
                    tablelist[anch.Top].Add(anch.Left, scontainer);
                }
                else if (StringSupport.equals(Utils.getPrstForShape(shape.Instance), "line"))
                {
                    if (anch.Top == anch.Bottom)
                    {
                        //horizontal
                        if (!horizontallinelist.ContainsKey(anch.Top))
                            horizontallinelist.Add(anch.Top, new SortedList<Integer, ShapeContainer>());
                         
                        horizontallinelist[anch.Top].Add(anch.Left, scontainer);
                    }
                    else
                    {
                        //vertical
                        if (!verticallinelist.ContainsKey(anch.Top))
                            verticallinelist.Add(anch.Top, new SortedList<Integer, ShapeContainer>());
                         
                        verticallinelist[anch.Top].Add(anch.Left, scontainer);
                    } 
                }
                else
                {
                    //Lines.Add(scontainer);
                    String s = Utils.getPrstForShape(shape.Instance);
                }  
            }
        }
        ColumnWidthsByYPos = new SortedDictionary<Integer, Integer>();
        HashMap<Integer,Integer> ColumnIndices = new HashMap<Integer,Integer>();
        for (Object __dummyForeachVar6 : tablelist.Values)
        {
            //this list will contain all column limits
            SortedList<Integer, ShapeContainer> rowlist = (SortedList<Integer, ShapeContainer>)__dummyForeachVar6;
            for (Object __dummyForeachVar5 : rowlist.Keys)
            {
                //rowlist contains all cells in a row
                int y = (Integer)__dummyForeachVar5;
                int w = rowlist[y].<ChildAnchor>FirstChildWithType().rcgBounds.Width;
                if (!ColumnWidthsByYPos.ContainsKey(y))
                {
                    ColumnWidthsByYPos.Add(y, w);
                }
                else
                {
                    if (w < ColumnWidthsByYPos[y])
                        ColumnWidthsByYPos[y] = w;
                     
                } 
                Cells.Add(rowlist[y]);
            }
        }
        int counter = 0;
        for (Object __dummyForeachVar7 : ColumnWidthsByYPos.Keys)
        {
            int y = (Integer)__dummyForeachVar7;
            ColumnIndices.put(y, counter++);
        }
        //the table contains all cells at their correct position
        ShapeContainer[][] table = new ShapeContainer[RowHeights.size(), ColumnWidthsByYPos.Count];
        int c;
        int r = 0;
        for (Object __dummyForeachVar9 : tablelist.Values)
        {
            SortedList<Integer, ShapeContainer> rowlst = (SortedList<Integer, ShapeContainer>)__dummyForeachVar9;
            for (Object __dummyForeachVar8 : rowlst.Keys)
            {
                int y = (Integer)__dummyForeachVar8;
                c = ColumnIndices.get(y);
                table[r, c] = rowlst[y];
            }
            r++;
        }
        _writer.WriteStartElement("p", "graphicFrame", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "nvGraphicFramePr", OpenXmlNamespaces.PresentationML);
        writeCNvPr(--groupcounter,"");
        _writer.WriteStartElement("p", "cNvGraphicFramePr", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("a", "graphicFrameLocks", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("noGrp", "1");
        _writer.writeEndElement();
        //graphicFrameLocks
        _writer.writeEndElement();
        //cNvGraphicFramePr
        _writer.WriteStartElement("p", "nvPr", OpenXmlNamespaces.PresentationML);
        if (DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(TABLEFLAGS, 0x1 << 1))
        {
            OEPlaceHolderAtom placeholder = null;
            int exObjIdRef = -1;
            RefSupport<OEPlaceHolderAtom> refVar___0 = new RefSupport<OEPlaceHolderAtom>(placeholder);
            RefSupport<Integer> refVar___1 = new RefSupport<Integer>(exObjIdRef);
            CheckClientData(group.firstChildWithType().<ClientData>FirstChildWithType(), refVar___0, refVar___1);
            placeholder = refVar___0.getValue();
            exObjIdRef = refVar___1.getValue();
            if (placeholder != null)
            {
                _writer.WriteStartElement("p", "ph", OpenXmlNamespaces.PresentationML);
                if (!placeholder.isObjectPlaceholder())
                {
                    String typeValue = Utils.placeholderIdToXMLValue(placeholder.PlacementId);
                    _writer.writeAttributeString("type", typeValue);
                }
                 
                byte __dummyScrutVar0 = placeholder.PlaceholderSize;
                if (__dummyScrutVar0.equals(1))
                {
                    _writer.writeAttributeString("sz", "half");
                }
                else if (__dummyScrutVar0.equals(2))
                {
                    _writer.writeAttributeString("sz", "quarter");
                }
                  
                if (placeholder.Position != -1)
                {
                    _writer.writeAttributeString("idx", String.valueOf(placeholder.Position));
                }
                else
                {
                    try
                    {
                        Slide master = _ctx.getPpt().FindMasterRecordById(group.firstAncestorWithType().<SlideAtom>FirstChildWithType().MasterId);
                        for (Object __dummyForeachVar10 : master.firstChildWithType().<DrawingContainer>FirstChildWithType().<GroupContainer>FirstChildWithType().<ShapeContainer>AllChildrenWithType())
                        {
                            ShapeContainer cont = (ShapeContainer)__dummyForeachVar10;
                            Shape s = cont.firstChildWithType();
                            ClientData d = cont.firstChildWithType();
                            if (d != null)
                            {
                                System.IO.MemoryStream ms = new System.IO.MemoryStream(d.bytes);
                                DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                                if (rec instanceof OEPlaceHolderAtom)
                                {
                                    OEPlaceHolderAtom placeholder2 = (OEPlaceHolderAtom)rec;
                                    if (placeholder2.PlacementId == PlaceholderEnum.MasterBody && (placeholder.PlacementId == PlaceholderEnum.Body || placeholder.PlacementId == PlaceholderEnum.Object))
                                    {
                                        if (placeholder2.Position != -1)
                                        {
                                            _writer.writeAttributeString("idx", String.valueOf(placeholder2.Position));
                                        }
                                         
                                    }
                                     
                                }
                                 
                            }
                             
                        }
                    }
                    catch (Exception __dummyCatchVar1)
                    {
                    }
                
                } 
                //ignore
                _writer.writeEndElement();
            }
             
        }
         
        _writer.writeEndElement();
        //nvPr
        _writer.writeEndElement();
        //nvGraphicFramePr
        if (anchor != null && anchor.getRight() >= anchor.getLeft() && anchor.getBottom() >= anchor.getTop())
        {
            _writer.WriteStartElement("p", "xfrm", OpenXmlNamespaces.PresentationML);
            _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("x", String.valueOf(Utils.masterCoordToEMU(anchor.getLeft())));
            _writer.writeAttributeString("y", String.valueOf(Utils.masterCoordToEMU(anchor.getTop())));
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("cx", String.valueOf(Utils.masterCoordToEMU(anchor.getRight() - anchor.getLeft())));
            _writer.writeAttributeString("cy", String.valueOf(Utils.masterCoordToEMU(anchor.getBottom() - anchor.getTop())));
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
         
        _writer.WriteStartElement("a", "graphic", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "graphicData", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("uri", "http://schemas.openxmlformats.org/drawingml/2006/table");
        _writer.WriteStartElement("a", "tbl", OpenXmlNamespaces.DrawingML);
        _writer.WriteElementString("a", "tblPr", OpenXmlNamespaces.DrawingML, "");
        _writer.WriteStartElement("a", "tblGrid", OpenXmlNamespaces.DrawingML);
        for (Object __dummyForeachVar11 : ColumnWidthsByYPos.Keys)
        {
            int y = (Integer)__dummyForeachVar11;
            _writer.WriteStartElement("a", "gridCol", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("w", Utils.MasterCoordToEMU(ColumnWidthsByYPos[y]).toString());
            _writer.writeEndElement();
        }
        //gridCol
        _writer.writeEndElement();
        for (int row = 0;row < RowHeights.size();row++)
        {
            //tblGrid
            _writer.WriteStartElement("a", "tr", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("h", String.valueOf(Utils.masterCoordToEMU(RowHeights.get(row))));
            for (int col = 0;col < ColumnWidthsByYPos.Count;col++)
            {
                _writer.WriteStartElement("a", "tc", OpenXmlNamespaces.DrawingML);
                if (table[row, col] != null)
                {
                    ShapeContainer container = table[row, col];
                    ChildAnchor anch = container.firstChildWithType();
                    int colWidth = ColumnWidthsByYPos[anch.Left];
                    if (anch.rcgBounds.Height > RowHeights.get(row) && getRowSpanCount(anch,row) > 1)
                    {
                        if (table[row + 1, col] == null)
                        {
                            _writer.writeAttributeString("rowSpan", String.valueOf(getRowSpanCount(anch,row)));
                        }
                         
                    }
                     
                    if (anch.rcgBounds.Width > colWidth)
                    {
                        _writer.writeAttributeString("gridSpan", String.valueOf(getGridSpanCount(anch,col)));
                    }
                     
                    for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record : container.Children)
                    {
                        if (record instanceof ClientTextbox)
                        {
                            so = container.firstChildWithType();
                            apply((ClientTextbox)record,true);
                        }
                         
                    }
                    _writer.WriteStartElement("a", "tcPr", OpenXmlNamespaces.DrawingML);
                    if (so.OptionsByID.containsKey(PropertyId.dxTextLeft))
                    {
                        _writer.writeAttributeString("marL", String.valueOf(so.OptionsByID.get(PropertyId.dxTextLeft).op));
                    }
                     
                    if (so.OptionsByID.containsKey(PropertyId.dyTextTop))
                    {
                        _writer.writeAttributeString("marT", String.valueOf(so.OptionsByID.get(PropertyId.dyTextTop).op));
                    }
                     
                    if (so.OptionsByID.containsKey(PropertyId.dxTextRight))
                    {
                        _writer.writeAttributeString("marR", String.valueOf(so.OptionsByID.get(PropertyId.dxTextRight).op));
                    }
                     
                    if (so.OptionsByID.containsKey(PropertyId.dyTextBottom))
                    {
                        _writer.writeAttributeString("marB", String.valueOf(so.OptionsByID.get(PropertyId.dyTextBottom).op));
                    }
                     
                    if (so.OptionsByID.containsKey(PropertyId.anchorText))
                    {
                        long __dummyScrutVar1 = so.OptionsByID.get(PropertyId.anchorText).op;
                        if (__dummyScrutVar1.equals(0))
                        {
                            //Top
                            _writer.writeAttributeString("anchor", "t");
                        }
                        else if (__dummyScrutVar1.equals(1))
                        {
                            //Middle
                            _writer.writeAttributeString("anchor", "ctr");
                        }
                        else if (__dummyScrutVar1.equals(2))
                        {
                            //Bottom
                            _writer.writeAttributeString("anchor", "b");
                        }
                        else if (__dummyScrutVar1.equals(3))
                        {
                            //TopCentered
                            _writer.writeAttributeString("anchor", "t");
                            _writer.writeAttributeString("anchorCtr", "1");
                        }
                        else if (__dummyScrutVar1.equals(4))
                        {
                            //MiddleCentered
                            _writer.writeAttributeString("anchor", "ctr");
                            _writer.writeAttributeString("anchorCtr", "1");
                        }
                        else if (__dummyScrutVar1.equals(5))
                        {
                            //BottomCentered
                            _writer.writeAttributeString("anchor", "b");
                            _writer.writeAttributeString("anchorCtr", "1");
                        }
                              
                    }
                     
                    _writer.writeAttributeString("horzOverflow", "overflow");
                    writeTableLineProperties(row,col,table);
                    if (so.OptionsByID.containsKey(PropertyId.FillStyleBooleanProperties))
                    {
                        FillStyleBooleanProperties p = new FillStyleBooleanProperties(so.OptionsByID.get(PropertyId.FillStyleBooleanProperties).op);
                        if (p.fUsefFilled & p.fFilled)
                        {
                            //  so.OptionsByID.ContainsKey(ShapeOptions.PropertyId.fillType))
                            new FillMapping(_ctx,_writer,parentSlideMapping).apply(so);
                        }
                         
                    }
                     
                    _writer.writeEndElement();
                }
                else
                {
                    //tcPr
                    if (col > 0 && table[row, col - 1] != null)
                    {
                        ShapeContainer previouscontainer = table[row, col - 1];
                        ChildAnchor anch = previouscontainer.firstChildWithType();
                        if (anch.rcgBounds.Height > RowHeights.get(row) && getRowSpanCount(anch,row) > 1)
                        {
                            _writer.writeAttributeString("rowSpan", String.valueOf(getRowSpanCount(anch,row)));
                        }
                         
                        //if (anch.rcgBounds.Height > RowHeights[row])
                        //{
                        //    _writer.WriteAttributeString("rowSpan", "2");
                        //}
                        if (anch.rcgBounds.Width > ColumnWidthsByYPos[anch.Left])
                        {
                            _writer.writeAttributeString("hMerge", "1");
                        }
                         
                    }
                    else if (row > 0 && col > 0 && table[row - 1, col - 1] != null)
                    {
                        //this checks the cell above on the left
                        ShapeContainer previouscontainer = table[row - 1, col - 1];
                        ChildAnchor anch = previouscontainer.firstChildWithType();
                        if (anch.rcgBounds.Width > ColumnWidthsByYPos[anch.Left])
                        {
                            _writer.writeAttributeString("hMerge", "1");
                        }
                         
                    }
                      
                    if (row > 0 && table[row - 1, col] != null)
                    {
                        //this checks the cell on the left
                        ShapeContainer previouscontainer = table[row - 1, col];
                        ChildAnchor anch = previouscontainer.firstChildWithType();
                        int colWidth = ColumnWidthsByYPos[anch.Left];
                        if (anch.rcgBounds.Width > colWidth)
                        {
                            _writer.writeAttributeString("gridSpan", "2");
                        }
                         
                        if (anch.rcgBounds.Height > RowHeights.get(row - 1))
                        {
                            _writer.writeAttributeString("vMerge", "1");
                        }
                         
                    }
                    else if (row > 0 && col > 0 && table[row - 1, col - 1] != null)
                    {
                        //this checks the cell above on the left
                        ShapeContainer previouscontainer = table[row - 1, col - 1];
                        ChildAnchor anch = previouscontainer.firstChildWithType();
                        if (anch.rcgBounds.Height > RowHeights.get(row - 1))
                        {
                            _writer.writeAttributeString("vMerge", "1");
                        }
                         
                    }
                      
                    //insert dummy tc content
                    _writer.WriteStartElement("a", "txBody", OpenXmlNamespaces.DrawingML);
                    _writer.WriteElementString("a", "bodyPr", OpenXmlNamespaces.DrawingML, "");
                    _writer.WriteElementString("a", "lstStyle", OpenXmlNamespaces.DrawingML, "");
                    _writer.WriteElementString("a", "p", OpenXmlNamespaces.DrawingML, "");
                    _writer.writeEndElement();
                    //txBody
                    _writer.WriteElementString("a", "tcPr", OpenXmlNamespaces.DrawingML, "");
                } 
                _writer.writeEndElement();
            }
            //tc
            //cellPointer++;
            _writer.writeEndElement();
        }
        //tr
        _writer.writeEndElement();
        //tbl
        _writer.writeEndElement();
        //graphicData
        _writer.writeEndElement();
        //graphic
        _writer.writeEndElement();
    }

    //graphicFrame
    private void writeTableLineProperties(int row, int col, ShapeContainer[][] table) throws Exception {
        ShapeContainer container = table[row, col];
        ChildAnchor anch = container.firstChildWithType();
        ShapeOptions so = container.firstChildWithType();
        ShapeContainer leftLine = null;
        ShapeContainer rightLine = null;
        ShapeContainer topLine = null;
        ShapeContainer bottomLine = null;
        for (Object __dummyForeachVar15 : horizontallinelist.Keys)
        {
            int linetop = (Integer)__dummyForeachVar15;
            SortedList<Integer, ShapeContainer> lst = horizontallinelist[linetop];
            if (linetop == anch.Top)
                for (Object __dummyForeachVar13 : lst.Keys)
                {
                    int lineleft = (Integer)__dummyForeachVar13;
                    ShapeContainer line = lst[lineleft];
                    int w = line.firstChildWithType().rcgBounds.Width;
                    if (lineleft <= anch.Left && lineleft + w >= anch.Right)
                        topLine = line;
                     
                }
             
            if (linetop == anch.Bottom)
                for (Object __dummyForeachVar14 : lst.Keys)
                {
                    int lineleft = (Integer)__dummyForeachVar14;
                    ShapeContainer line = lst[lineleft];
                    int w = line.firstChildWithType().rcgBounds.Width;
                    if (lineleft <= anch.Left && lineleft + w >= anch.Right)
                        bottomLine = line;
                     
                }
             
        }
        for (Object __dummyForeachVar17 : verticallinelist.Keys)
        {
            int linetop = (Integer)__dummyForeachVar17;
            SortedList<Integer, ShapeContainer> lst = verticallinelist[linetop];
            for (Object __dummyForeachVar16 : lst.Keys)
            {
                int lineleft = (Integer)__dummyForeachVar16;
                ShapeContainer line = lst[lineleft];
                int h = line.firstChildWithType().rcgBounds.Height;
                if (lineleft == anch.Left)
                    //if (linetop == anch.Top) leftLine = line;
                    if (linetop <= anch.Top && linetop + h >= anch.Bottom)
                        leftLine = line;
                     
                 
                if (lineleft == anch.Right)
                    //if (linetop == anch.Top) rightLine = line;
                    if (linetop <= anch.Top && linetop + h >= anch.Bottom)
                        rightLine = line;
                     
                 
            }
        }
        int rows = table.GetLength(0);
        int columns = table.GetLength(1);
        /**
        * /check cell position
        */
        //bool isLeft = (col == 0);
        //bool isRight = (col == columns - 1);
        //bool isTop = (row == 0);
        //bool isBottom = (row == rows - 1);
        int span;
        int colWidth = ColumnWidthsByYPos[anch.Left];
        if (anch.rcgBounds.Height > RowHeights.get(row))
        {
            //recheck isBottom
            span = getRowSpanCount(anch,row);
        }
         
        //isBottom = (row + span - 1 == rows - 1);
        if (anch.rcgBounds.Width > colWidth)
        {
            //recheck isRight
            span = getGridSpanCount(anch,col);
        }
         
        //isRight = (col + span - 1 == columns - 1);
        _writer.WriteStartElement("a", "lnL", OpenXmlNamespaces.DrawingML);
        writeLineProperties(leftLine,so);
        //if (isLeft) WriteLineProperties(outerLine, so); else WriteLineProperties(innerLine, null);
        _writer.writeEndElement();
        //lnL
        _writer.WriteStartElement("a", "lnR", OpenXmlNamespaces.DrawingML);
        writeLineProperties(rightLine,so);
        //if (isRight) WriteLineProperties(outerLine, so); else WriteLineProperties(innerLine, null);
        _writer.writeEndElement();
        //lnR
        _writer.WriteStartElement("a", "lnT", OpenXmlNamespaces.DrawingML);
        writeLineProperties(topLine,so);
        //if (isTop) WriteLineProperties(outerLine, so); else WriteLineProperties(innerLine, null);
        _writer.writeEndElement();
        //lnT
        _writer.WriteStartElement("a", "lnB", OpenXmlNamespaces.DrawingML);
        writeLineProperties(bottomLine,so);
        //if (isBottom) WriteLineProperties(outerLine, so); else WriteLineProperties(innerLine, null);
        _writer.writeEndElement();
        //lnB
        _writer.WriteStartElement("a", "lnTlToBr", OpenXmlNamespaces.DrawingML);
        _writer.WriteElementString("a", "noFill", OpenXmlNamespaces.DrawingML, "");
        _writer.writeEndElement();
        //lnTlToBr
        _writer.WriteStartElement("a", "lnBlToTr", OpenXmlNamespaces.DrawingML);
        _writer.WriteElementString("a", "noFill", OpenXmlNamespaces.DrawingML, "");
        _writer.writeEndElement();
    }

    //lnBlToTr
    private void writeLineProperties(ShapeOptions soline) throws Exception {
        LineStyleBooleans fsb = new LineStyleBooleans(soline.OptionsByID.get(PropertyId.lineStyleBooleans).op);
        if (fsb.fUsefLine && fsb.fLine)
        {
            if (soline.OptionsByID.containsKey(PropertyId.lineColor))
            {
                _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
                String SchemeType = "";
                RefSupport<String> refVar___2 = new RefSupport<String>(SchemeType);
                String colorVal = Utils.getRGBColorFromOfficeArtCOLORREF(soline.OptionsByID.get(PropertyId.lineColor).op, soline.firstAncestorWithType(), so, refVar___2);
                SchemeType = refVar___2.getValue();
                if (SchemeType.length() == 0)
                {
                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", colorVal);
                }
                else
                {
                    _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", SchemeType);
                } 
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
            else
            {
                _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", "000000");
                _writer.writeEndElement();
                _writer.writeEndElement();
            } 
        }
         
    }

    private void writeLineProperties(ShapeContainer lineCont, ShapeOptions soframe) throws Exception {
        if (lineCont == null)
        {
            _writer.WriteElementString("a", "noFill", OpenXmlNamespaces.DrawingML, "");
            return ;
        }
         
        for (Object __dummyForeachVar18 : lineCont.allChildrenWithType())
        {
            ShapeOptions soline = (ShapeOptions)__dummyForeachVar18;
            if (soline.OptionsByID.containsKey(PropertyId.lineWidth))
            {
                uint w = soline.OptionsByID.get(PropertyId.lineWidth).op;
                _writer.writeAttributeString("w", String.valueOf(w));
            }
             
            if (soline.OptionsByID.containsKey(PropertyId.lineEndCapStyle))
            {
                //switch (soline.OptionsByID[ShapeOptions.PropertyId.lineEndCapStyle].op)
                //{
                //    case 0: //round
                //        _writer.WriteAttributeString("cap", "rnd");
                //        break;
                //    case 1: //square
                //        _writer.WriteAttributeString("cap", "sq");
                //        break;
                //    case 2: //flat
                _writer.writeAttributeString("cap", "flat");
            }
             
            //break;
            //}
            _writer.writeAttributeString("cmpd", "sng");
            _writer.writeAttributeString("algn", "ctr");
            if (soline.OptionsByID.containsKey(PropertyId.lineStyleBooleans))
            {
                writeLineProperties(soline);
            }
            else //LineStyleBooleans fsb = new LineStyleBooleans(soline.OptionsByID[ShapeOptions.PropertyId.lineStyleBooleans].op);
            //if (fsb.fUsefLine && fsb.fLine)
            //{
            //    if (soline.OptionsByID.ContainsKey(ShapeOptions.PropertyId.lineColor))
            //    {
            //        _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
            //        string SchemeType = "";
            //        string colorVal = Utils.getRGBColorFromOfficeArtCOLORREF(soline.OptionsByID[ShapeOptions.PropertyId.lineColor].op, lineCont.FirstAncestorWithType<Slide>(), so, ref SchemeType);
            //        if (SchemeType.Length == 0)
            //        {
            //            _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            //            _writer.WriteAttributeString("val", colorVal);
            //        }
            //        else
            //        {
            //            _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
            //            _writer.WriteAttributeString("val", SchemeType);
            //        }
            //        _writer.WriteEndElement();
            //        _writer.WriteEndElement();
            //    }
            //    else
            //    {
            //        _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
            //        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            //        _writer.WriteAttributeString("val", "000000");
            //        _writer.WriteEndElement();
            //        _writer.WriteEndElement();
            //    }
            //}
            if (soframe != null && soframe.OptionsByID.containsKey(PropertyId.lineStyleBooleans))
            {
                writeLineProperties(soframe);
            }
              
            //LineStyleBooleans fsb = new LineStyleBooleans(soframe.OptionsByID[ShapeOptions.PropertyId.lineStyleBooleans].op);
            //if (fsb.fUsefLine && fsb.fLine)
            //{
            //     if (soframe.OptionsByID.ContainsKey(ShapeOptions.PropertyId.lineColor))
            //    {
            //        _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
            //        string SchemeType = "";
            //        string colorVal = Utils.getRGBColorFromOfficeArtCOLORREF(soframe.OptionsByID[ShapeOptions.PropertyId.lineColor].op, lineCont.FirstAncestorWithType<Slide>(), soframe, ref SchemeType);
            //        if (SchemeType.Length == 0)
            //        {
            //            _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            //            _writer.WriteAttributeString("val", colorVal);
            //        }
            //        else
            //        {
            //            _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
            //            _writer.WriteAttributeString("val", SchemeType);
            //        }
            //        _writer.WriteEndElement();
            //        _writer.WriteEndElement();
            //    }
            //}
            if (soline.OptionsByID.containsKey(PropertyId.lineDashing))
            {
                _writer.WriteStartElement("a", "prstDash", OpenXmlNamespaces.DrawingML);
                switch((DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineDashing)soline.OptionsByID.get(PropertyId.lineDashing).op)
                {
                    case Solid: 
                        _writer.writeAttributeString("val", "solid");
                        break;
                    case DashSys: 
                        _writer.writeAttributeString("val", "sysDash");
                        break;
                    case DotSys: 
                        _writer.writeAttributeString("val", "sysDot");
                        break;
                    case DashDotSys: 
                        _writer.writeAttributeString("val", "sysDashDot");
                        break;
                    case DashDotDotSys: 
                        _writer.writeAttributeString("val", "sysDashDotDot");
                        break;
                    case DotGEL: 
                        _writer.writeAttributeString("val", "dot");
                        break;
                    case DashGEL: 
                        _writer.writeAttributeString("val", "dash");
                        break;
                    case LongDashGEL: 
                        _writer.writeAttributeString("val", "lgDash");
                        break;
                    case DashDotGEL: 
                        _writer.writeAttributeString("val", "dashDot");
                        break;
                    case LongDashDotGEL: 
                        _writer.writeAttributeString("val", "lgDashDot");
                        break;
                    case LongDashDotDotGEL: 
                        _writer.writeAttributeString("val", "lgDashDotDot");
                        break;
                
                }
                _writer.writeEndElement();
            }
            else if (soframe != null && soframe.OptionsByID.containsKey(PropertyId.lineDashing))
            {
                _writer.WriteStartElement("a", "prstDash", OpenXmlNamespaces.DrawingML);
                switch((DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineDashing)soframe.OptionsByID.get(PropertyId.lineDashing).op)
                {
                    case Solid: 
                        _writer.writeAttributeString("val", "solid");
                        break;
                    case DashSys: 
                        _writer.writeAttributeString("val", "sysDash");
                        break;
                    case DotSys: 
                        _writer.writeAttributeString("val", "sysDot");
                        break;
                    case DashDotSys: 
                        _writer.writeAttributeString("val", "sysDashDot");
                        break;
                    case DashDotDotSys: 
                        _writer.writeAttributeString("val", "sysDashDotDot");
                        break;
                    case DotGEL: 
                        _writer.writeAttributeString("val", "dot");
                        break;
                    case DashGEL: 
                        _writer.writeAttributeString("val", "dash");
                        break;
                    case LongDashGEL: 
                        _writer.writeAttributeString("val", "lgDash");
                        break;
                    case DashDotGEL: 
                        _writer.writeAttributeString("val", "dashDot");
                        break;
                    case LongDashDotGEL: 
                        _writer.writeAttributeString("val", "lgDashDot");
                        break;
                    case LongDashDotDotGEL: 
                        _writer.writeAttributeString("val", "lgDashDotDot");
                        break;
                
                }
                _writer.writeEndElement();
            }
              
            _writer.WriteElementString("a", "round", OpenXmlNamespaces.DrawingML, "");
            if (soline.OptionsByID.containsKey(PropertyId.lineStartArrowhead))
            {
                DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd val = (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd)soline.OptionsByID.get(PropertyId.lineStartArrowhead).op;
                if (val != DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd.NoEnd)
                {
                    _writer.WriteStartElement("a", "headEnd", OpenXmlNamespaces.DrawingML);
                    switch(val)
                    {
                        case ArrowEnd: 
                            _writer.writeAttributeString("type", "triangle");
                            break;
                        case ArrowStealthEnd: 
                            _writer.writeAttributeString("type", "stealth");
                            break;
                        case ArrowDiamondEnd: 
                            _writer.writeAttributeString("type", "diamond");
                            break;
                        case ArrowOvalEnd: 
                            _writer.writeAttributeString("type", "oval");
                            break;
                        case ArrowOpenEnd: 
                            _writer.writeAttributeString("type", "arrow");
                            break;
                        case ArrowChevronEnd: 
                        case ArrowDoubleChevronEnd: 
                            //this should be ignored
                            _writer.writeAttributeString("type", "triangle");
                            break;
                    
                    }
                    _writer.writeAttributeString("w", "med");
                    _writer.writeAttributeString("len", "med");
                    _writer.writeEndElement();
                }
                 
            }
            else
            {
                //headEnd
                _writer.WriteStartElement("a", "headEnd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("type", "none");
                _writer.writeAttributeString("w", "med");
                _writer.writeAttributeString("len", "med");
                _writer.writeEndElement();
            } 
            //headEnd
            if (soline.OptionsByID.containsKey(PropertyId.lineEndArrowhead))
            {
                DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd val = (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd)soline.OptionsByID.get(PropertyId.lineEndArrowhead).op;
                if (val != DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd.NoEnd)
                {
                    _writer.WriteStartElement("a", "tailEnd", OpenXmlNamespaces.DrawingML);
                    switch(val)
                    {
                        case ArrowEnd: 
                            _writer.writeAttributeString("type", "triangle");
                            break;
                        case ArrowStealthEnd: 
                            _writer.writeAttributeString("type", "stealth");
                            break;
                        case ArrowDiamondEnd: 
                            _writer.writeAttributeString("type", "diamond");
                            break;
                        case ArrowOvalEnd: 
                            _writer.writeAttributeString("type", "oval");
                            break;
                        case ArrowOpenEnd: 
                            _writer.writeAttributeString("type", "arrow");
                            break;
                        case ArrowChevronEnd: 
                        case ArrowDoubleChevronEnd: 
                            //this should be ignored
                            _writer.writeAttributeString("type", "triangle");
                            break;
                    
                    }
                    _writer.writeAttributeString("w", "med");
                    _writer.writeAttributeString("len", "med");
                    _writer.writeEndElement();
                }
                 
            }
            else
            {
                //tailnd
                _writer.WriteStartElement("a", "tailEnd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("type", "none");
                _writer.writeAttributeString("w", "med");
                _writer.writeAttributeString("len", "med");
                _writer.writeEndElement();
            } 
            break;
        }
    }

    //tailEnd
    private ShapeOptions so;
    public void apply(ShapeContainer container) throws Exception {
        apply(container,"","","");
    }

    public void apply(ShapeContainer container, String footertext, String headertext, String datetext) throws Exception {
        _footertext = footertext;
        _headertext = headertext;
        _datetext = datetext;
        ClientData clientData = container.firstChildWithType();
        RegularContainer slide = container.firstAncestorWithType();
        if (slide == null)
            slide = container.firstAncestorWithType();
         
        if (slide == null)
            slide = container.firstAncestorWithType();
         
        boolean continueShape = true;
        Shape sh = container.firstChildWithType();
        so = container.firstChildWithType();
        //if (clientData == null)
        if (so != null)
        {
            if (so.OptionsByID.containsKey(PropertyId.Pib))
            {
                if (sh.fOleShape)
                {
                    OEPlaceHolderAtom placeholder = null;
                    int exObjIdRef = -1;
                    RefSupport<OEPlaceHolderAtom> refVar___3 = new RefSupport<OEPlaceHolderAtom>(placeholder);
                    RefSupport<Integer> refVar___4 = new RefSupport<Integer>(exObjIdRef);
                    CheckClientData(container.firstChildWithType(), refVar___3, refVar___4);
                    placeholder = refVar___3.getValue();
                    exObjIdRef = refVar___4.getValue();
                    ExOleEmbedContainer oleContainer = this._ctx.getPpt().OleObjects.get(exObjIdRef);
                    if (oleContainer.firstChildWithType() != null)
                        if (StringSupport.equals(oleContainer.firstChildWithType().Text, "Chart"))
                        {
                            writeOle(container,oleContainer);
                            continueShape = false;
                        }
                         
                     
                }
                 
                if (continueShape)
                {
                    writePic(container);
                    continueShape = false;
                }
                 
            }
             
        }
        else
        {
            so = new ShapeOptions();
        } 
        ShapeOptions sndSo = null;
        if (container.allChildrenWithType().size() > 1)
        {
            sndSo = ((RegularContainer)sh.getParentRecord()).allChildrenWithType().get(1);
        }
         
        //if (sndSo.OptionsByID.ContainsKey(ShapeOptions.PropertyId.metroBlob))
        //{
        //    ZipUtils.ZipReader reader = null;
        //    try
        //    {
        //        ShapeOptions.OptionEntry metroBlob = sndSo.OptionsByID[ShapeOptions.PropertyId.metroBlob];
        //        byte[] code = metroBlob.opComplex;
        //        string path = System.IO.Path.GetTempFileName();
        //        System.IO.FileStream fs = new System.IO.FileStream(path, System.IO.FileMode.Create);
        //        fs.Write(code, 0, code.Length);
        //        fs.Close();
        //        reader = ZipUtils.ZipFactory.OpenArchive(path);
        //        System.IO.StreamReader mems = new System.IO.StreamReader(reader.GetEntry("drs/shapexml.xml"));
        //        string xml = mems.ReadToEnd();
        //        xml = Tools.Utils.replaceOutdatedNamespaces(xml);
        //        xml = xml.Substring(xml.IndexOf("<p:sp")); //remove xml declaration
        //        _writer.WriteRaw(xml);
        //        continueShape = false;
        //        reader.Close();
        //    }
        //    catch (Exception e)
        //    {
        //        continueShape = true;
        //        if (reader != null) reader.Close();
        //    }
        //}
        if (continueShape)
        {
            if (sh.fConnector)
            {
                String idStart = "";
                String idEnd = "";
                String idxStart = "0";
                String idxEnd = "0";
                for (Object __dummyForeachVar19 : container.firstAncestorWithType().<SolverContainer>FirstChildWithType().<FConnectorRule>AllChildrenWithType())
                {
                    FConnectorRule rule = (FConnectorRule)__dummyForeachVar19;
                    if (rule.spidC == sh.spid)
                    {
                        //spidC marks the connector shape
                        if (!spidToId.containsKey((int)rule.spidA))
                        {
                            spidToId.put((int)rule.spidA, ++_idCnt);
                        }
                         
                        if (!spidToId.containsKey((int)rule.spidB))
                        {
                            spidToId.put((int)rule.spidB, ++_idCnt);
                        }
                         
                        idStart = String.valueOf(spidToId.get((int)rule.spidA));
                        //spidA marks the start shape
                        idEnd = String.valueOf(spidToId.get((int)rule.spidB));
                        //spidB marks the end shape
                        idxStart = String.valueOf(rule.cptiA);
                        idxEnd = String.valueOf(rule.cptiB);
                    }
                     
                }
                _writer.WriteStartElement("p", "cxnSp", OpenXmlNamespaces.PresentationML);
                _writer.WriteStartElement("p", "nvCxnSpPr", OpenXmlNamespaces.PresentationML);
                writeCNvPr(sh.spid,"");
                _writer.WriteStartElement("p", "cNvCxnSpPr", OpenXmlNamespaces.PresentationML);
                _writer.WriteStartElement("a", "stCxn", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("id", idStart);
                _writer.writeAttributeString("idx", idxStart);
                _writer.writeEndElement();
                //stCxn
                _writer.WriteStartElement("a", "endCxn", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("id", idEnd);
                _writer.writeAttributeString("idx", idxEnd);
                _writer.writeEndElement();
                //endCxn
                _writer.writeEndElement();
            }
            else
            {
                //cNvCxnSpPr
                _writer.WriteStartElement("p", "sp", OpenXmlNamespaces.PresentationML);
                _writer.WriteStartElement("p", "nvSpPr", OpenXmlNamespaces.PresentationML);
                writeCNvPr(sh.spid,"");
                _writer.WriteElementString("p", "cNvSpPr", OpenXmlNamespaces.PresentationML, "");
            } 
            _writer.WriteStartElement("p", "nvPr", OpenXmlNamespaces.PresentationML);
            OEPlaceHolderAtom placeholder = null;
            int exObjIdRef = 0;
            RefSupport<OEPlaceHolderAtom> refVar___5 = new RefSupport<OEPlaceHolderAtom>(placeholder);
            RefSupport<Integer> refVar___6 = new RefSupport<Integer>(exObjIdRef);
            checkClientData(clientData,refVar___5,refVar___6);
            placeholder = refVar___5.getValue();
            exObjIdRef = refVar___6.getValue();
            _writer.writeEndElement();
            _writer.writeEndElement();
            // Visible shape properties
            _writer.WriteStartElement("p", "spPr", OpenXmlNamespaces.PresentationML);
            ClientAnchor anchor = container.firstChildWithType();
            ChildAnchor chAnchor = container.firstChildWithType();
            boolean swapHeightWidth = false;
            double dc = 0;
            if (anchor != null && anchor.getRight() >= anchor.getLeft() && anchor.getBottom() >= anchor.getTop())
            {
                _writer.WriteStartElement("a", "xfrm", OpenXmlNamespaces.DrawingML);
                if (sh.fFlipH)
                    _writer.writeAttributeString("flipH", "1");
                 
                if (sh.fFlipV)
                    _writer.writeAttributeString("flipV", "1");
                 
                if (so.OptionsByID.containsKey(PropertyId.rotation))
                {
                    byte[] bytes = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.rotation).op);
                    int integral = BitConverter.ToInt16(bytes, 2);
                    uint fractional = BitConverter.ToUInt16(bytes, 0);
                    double result = integral + ((double)fractional / (double)65536);
                    double w = anchor.getBottom() - anchor.getTop();
                    double h = anchor.getRight() - anchor.getLeft();
                    dc = (w - h) / 2;
                    if (Math.Abs(result) > 45 && Math.Abs(result) < 135)
                        swapHeightWidth = true;
                     
                    if (Math.Abs(result) > 225 && Math.Abs(result) < 315)
                        swapHeightWidth = true;
                     
                    if (result < 0 && sh.fFlipH == false)
                        result = result * -1;
                     
                    String rotation = String.valueOf(Math.floor(result * 60000));
                    if (result != 0)
                    {
                        _writer.writeAttributeString("rot", rotation);
                    }
                     
                }
                 
                if (container.firstAncestorWithType().<GroupContainer>FirstAncestorWithType() == null)
                {
                    _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
                    if (swapHeightWidth)
                    {
                        _writer.writeAttributeString("x", String.valueOf(Utils.masterCoordToEMU(anchor.getLeft() - ((int)(dc)))));
                        _writer.writeAttributeString("y", String.valueOf(Utils.masterCoordToEMU(anchor.getTop() + ((int)(dc)))));
                    }
                    else
                    {
                        _writer.writeAttributeString("x", String.valueOf(Utils.masterCoordToEMU(anchor.getLeft())));
                        _writer.writeAttributeString("y", String.valueOf(Utils.masterCoordToEMU(anchor.getTop())));
                    } 
                    _writer.writeEndElement();
                    _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
                    if (swapHeightWidth)
                    {
                        _writer.writeAttributeString("cx", String.valueOf(Utils.masterCoordToEMU(anchor.getBottom() - anchor.getTop())));
                        _writer.writeAttributeString("cy", String.valueOf(Utils.masterCoordToEMU(anchor.getRight() - anchor.getLeft())));
                    }
                    else
                    {
                        _writer.writeAttributeString("cx", String.valueOf(Utils.masterCoordToEMU(anchor.getRight() - anchor.getLeft())));
                        _writer.writeAttributeString("cy", String.valueOf(Utils.masterCoordToEMU(anchor.getBottom() - anchor.getTop())));
                    } 
                    _writer.writeEndElement();
                }
                else
                {
                    ClientAnchor clanchor = container.firstChildWithType();
                    if (clanchor == null)
                    {
                        _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("x", String.valueOf(Utils.masterCoordToEMU(anchor.getLeft())));
                        _writer.writeAttributeString("y", String.valueOf(Utils.masterCoordToEMU(anchor.getTop())));
                        _writer.writeEndElement();
                        _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
                        if (swapHeightWidth)
                        {
                            _writer.writeAttributeString("cx", String.valueOf(Utils.masterCoordToEMU(anchor.getBottom() - anchor.getTop())));
                            _writer.writeAttributeString("cy", String.valueOf(Utils.masterCoordToEMU(anchor.getRight() - anchor.getLeft())));
                        }
                        else
                        {
                            _writer.writeAttributeString("cx", String.valueOf(Utils.masterCoordToEMU(anchor.getRight() - anchor.getLeft())));
                            _writer.writeAttributeString("cy", String.valueOf(Utils.masterCoordToEMU(anchor.getBottom() - anchor.getTop())));
                        } 
                        _writer.writeEndElement();
                    }
                    else
                    {
                        _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("x", String.valueOf((anchor.getLeft())));
                        _writer.writeAttributeString("y", String.valueOf((anchor.getTop())));
                        _writer.writeEndElement();
                        _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
                        if (swapHeightWidth)
                        {
                            _writer.writeAttributeString("cx", String.valueOf((anchor.getBottom() - anchor.getTop())));
                            _writer.writeAttributeString("cy", String.valueOf((anchor.getRight() - anchor.getLeft())));
                        }
                        else
                        {
                            _writer.writeAttributeString("cx", String.valueOf((anchor.getRight() - anchor.getLeft())));
                            _writer.writeAttributeString("cy", String.valueOf((anchor.getBottom() - anchor.getTop())));
                        } 
                        _writer.writeEndElement();
                    } 
                } 
                _writer.writeEndElement();
            }
            else if (chAnchor != null && chAnchor.Right >= chAnchor.Left && chAnchor.Bottom >= chAnchor.Top)
            {
                ClientAnchor groupAnchor = container.firstAncestorWithType().<ShapeContainer>FirstChildWithType().<ClientAnchor>FirstChildWithType();
                Rectangle rec = container.firstAncestorWithType().<ShapeContainer>FirstChildWithType().<GroupShapeRecord>FirstChildWithType().rcgBounds;
                _writer.WriteStartElement("a", "xfrm", OpenXmlNamespaces.DrawingML);
                if (so.OptionsByID.containsKey(PropertyId.rotation))
                {
                    byte[] bytes = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.rotation).op);
                    int integral = BitConverter.ToInt16(bytes, 2);
                    uint fractional = BitConverter.ToUInt16(bytes, 0);
                    double result = integral + ((double)fractional / (double)65536);
                    if (result < 0 && sh.fFlipH == false)
                        result = result * -1;
                     
                    double w = chAnchor.Bottom - chAnchor.Top;
                    double h = chAnchor.Right - chAnchor.Left;
                    dc = (w - h) / 2;
                    if (Math.Abs(result) > 45 && Math.Abs(result) < 135)
                        swapHeightWidth = true;
                     
                    if (Math.Abs(result) > 225 && Math.Abs(result) < 315)
                        swapHeightWidth = true;
                     
                    String rotation = String.valueOf(Math.floor(result * 60000));
                    _writer.writeAttributeString("rot", rotation);
                }
                 
                if (sh.fFlipH)
                    _writer.writeAttributeString("flipH", "1");
                 
                if (sh.fFlipV)
                    _writer.writeAttributeString("flipV", "1");
                 
                _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("x", String.valueOf((chAnchor.Left)));
                _writer.writeAttributeString("y", String.valueOf((chAnchor.Top)));
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("cx", String.valueOf((chAnchor.Right - chAnchor.Left)));
                _writer.writeAttributeString("cy", String.valueOf((chAnchor.Bottom - chAnchor.Top)));
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
              
            if (sh.Instance != 0 & !so.OptionsByID.containsKey(PropertyId.pSegmentInfo))
            {
                //this means a predefined shape
                writeprstGeom(sh);
            }
            else
            {
                //this means a custom shape
                writecustGeom(sh);
            } 
            writeShapeProperties(sh,placeholder != null,slide);
            _writer.writeEndElement();
            boolean TextBoxFound = false;
            for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record : container.Children)
            {
                // Descend into unsupported records
                dynamicApply(record);
                if (record instanceof ClientTextbox)
                    TextBoxFound = true;
                 
            }
            if (!TextBoxFound & !sh.fConnector)
            {
                _writer.WriteStartElement("p", "txBody", OpenXmlNamespaces.PresentationML);
                writeBodyPr(container,false,false);
                _writer.WriteElementString("a", "lstStyle", OpenXmlNamespaces.DrawingML, "");
                _writer.WriteStartElement("a", "p", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "pPr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("algn", "ctr");
                _writer.writeAttributeString("fontAlgn", "auto");
                _writer.writeEndElement();
                //check if there is text in so
                if (so.OptionsByID.containsKey(PropertyId.gtextUNICODE))
                {
                    byte[] bytes = so.OptionsByID.get(PropertyId.gtextUNICODE).opComplex;
                    String sText = Encoding.Unicode.GetString(bytes);
                    if (sText.contains("\0"))
                        sText = sText.substring(0, (0) + (sText.indexOf("\0")));
                     
                    _writer.WriteStartElement("a", "r", OpenXmlNamespaces.DrawingML);
                    if (so.OptionsByID.containsKey(PropertyId.gtextFont))
                    {
                        bytes = so.OptionsByID.get(PropertyId.gtextFont).opComplex;
                        String sFont = Encoding.Unicode.GetString(bytes);
                        if (sFont.contains("\0"))
                            sFont = sFont.substring(0, (0) + (sFont.indexOf("\0")));
                         
                        _writer.WriteStartElement("a", "rPr", OpenXmlNamespaces.DrawingML);
                        if (so.OptionsByID.containsKey(PropertyId.gtextSize))
                        {
                            _writer.writeAttributeString("sz", String.valueOf((so.OptionsByID.get(PropertyId.gtextSize).op / 0x100)));
                        }
                        else
                        {
                            _writer.writeAttributeString("sz", "3600");
                        } 
                        if (so.OptionsByID.containsKey(PropertyId.GeometryTextBooleanProperties))
                        {
                            GeometryTextBooleanProperties gb = new GeometryTextBooleanProperties(so.OptionsByID.get(PropertyId.GeometryTextBooleanProperties).op);
                            if (gb.fUsegtextFKern & gb.gtextFKern)
                            {
                                _writer.writeAttributeString("kern", "10");
                            }
                             
                            if (gb.fUsegtextFItalic & gb.gtextFItalic)
                            {
                                _writer.writeAttributeString("i", "1");
                            }
                             
                        }
                         
                        if (so.OptionsByID.containsKey(PropertyId.lineStyleBooleans))
                        {
                            LineStyleBooleans lb = new LineStyleBooleans(so.OptionsByID.get(PropertyId.lineStyleBooleans).op);
                            if (lb.fUsefLine & lb.fLine)
                            {
                                _writer.WriteStartElement("a", "ln", OpenXmlNamespaces.DrawingML);
                                if (so.OptionsByID.containsKey(PropertyId.lineWidth))
                                {
                                    uint w = so.OptionsByID.get(PropertyId.lineWidth).op;
                                    _writer.writeAttributeString("w", String.valueOf(w));
                                }
                                 
                                String color = "000000";
                                if (so.OptionsByID.containsKey(PropertyId.lineColor))
                                {
                                    color = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.lineColor).op,slide,so);
                                }
                                 
                                boolean ignoreColor = false;
                                if (so.OptionsByID.containsKey(PropertyId.ThreeDObjectBooleanProperties))
                                {
                                    ThreeDObjectProperties tdo = new ThreeDObjectProperties(so.OptionsByID.get(PropertyId.ThreeDObjectBooleanProperties).op);
                                    if (tdo.fc3D && tdo.fUsefc3D)
                                    {
                                        ignoreColor = true;
                                    }
                                     
                                }
                                 
                                if (!ignoreColor)
                                {
                                    _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
                                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                                    _writer.writeAttributeString("val", color);
                                    _writer.writeEndElement();
                                    _writer.writeEndElement();
                                }
                                 
                                _writer.writeEndElement();
                            }
                             
                        }
                         
                        if (so.OptionsByID.containsKey(PropertyId.FillStyleBooleanProperties))
                        {
                            FillStyleBooleanProperties p = new FillStyleBooleanProperties(so.OptionsByID.get(PropertyId.FillStyleBooleanProperties).op);
                            if (p.fUsefFilled & p.fFilled)
                            {
                                new FillMapping(_ctx,_writer,parentSlideMapping).apply(so);
                            }
                             
                        }
                         
                        //shadow
                        if (so.OptionsByID.containsKey(PropertyId.ShadowStyleBooleanProperties))
                        {
                            ShadowStyleBooleanProperties sp = new ShadowStyleBooleanProperties(so.OptionsByID.get(PropertyId.ShadowStyleBooleanProperties).op);
                            if (sp.fUsefShadow & sp.fShadow)
                            {
                                new ShadowMapping(_ctx,_writer).apply(so);
                            }
                             
                        }
                         
                        _writer.WriteStartElement("a", "latin", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("typeface", sFont);
                        _writer.writeEndElement();
                        _writer.writeEndElement();
                    }
                     
                    _writer.WriteElementString("a", "t", OpenXmlNamespaces.DrawingML, sText);
                    _writer.writeEndElement();
                }
                 
                _writer.WriteElementString("a", "endParaRPr", OpenXmlNamespaces.DrawingML, "");
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
             
            _writer.writeEndElement();
        }
         
    }

    private void writeShapeProperties(Shape sh, boolean isPlaceholder, RegularContainer slide) throws Exception {
        ShapeContainer container = (ShapeContainer)sh.getParentRecord();
        if (so.OptionsByID.containsKey(PropertyId.FillStyleBooleanProperties))
        {
            FillStyleBooleanProperties p = new FillStyleBooleanProperties(so.OptionsByID.get(PropertyId.FillStyleBooleanProperties).op);
            if (p.fUsefFilled & p.fFilled)
            {
                //  so.OptionsByID.ContainsKey(ShapeOptions.PropertyId.fillType))
                new FillMapping(_ctx,_writer,parentSlideMapping).apply(so);
            }
             
        }
        else if (so.OptionsByID.containsKey(PropertyId.fillColor))
        {
            if (sh.Instance != 0xca & isPlaceholder == false)
            {
                String colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.fillColor).op,slide,so);
                _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", colorval);
                if (so.OptionsByID.containsKey(PropertyId.fillOpacity) && so.OptionsByID.get(PropertyId.fillOpacity).op != 65536)
                {
                    _writer.WriteStartElement("a", "alpha", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", String.valueOf(Math.round(((double)so.OptionsByID.get(PropertyId.fillOpacity).op / 65536 * 100000))));
                    //we need the percentage of the opacity (65536 means 100%)
                    _writer.writeEndElement();
                }
                 
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
             
        }
          
        _writer.WriteStartElement("a", "ln", OpenXmlNamespaces.DrawingML);
        if (so.OptionsByID.containsKey(PropertyId.lineWidth))
        {
            _writer.writeAttributeString("w", String.valueOf(so.OptionsByID.get(PropertyId.lineWidth).op));
        }
         
        if (so.OptionsByID.containsKey(PropertyId.lineEndCapStyle))
        {
            long __dummyScrutVar6 = so.OptionsByID.get(PropertyId.lineEndCapStyle).op;
            if (__dummyScrutVar6.equals(0))
            {
                //round
                _writer.writeAttributeString("cap", "rnd");
            }
            else if (__dummyScrutVar6.equals(1))
            {
                //square
                _writer.writeAttributeString("cap", "sq");
            }
            else if (__dummyScrutVar6.equals(2))
            {
                //flat
                _writer.writeAttributeString("cap", "flat");
            }
               
        }
         
        if (so.OptionsByID.containsKey(PropertyId.lineType))
        {
            long __dummyScrutVar7 = so.OptionsByID.get(PropertyId.lineType).op;
            if (__dummyScrutVar7.equals(0))
            {
                //solid
                if (so.OptionsByID.containsKey(PropertyId.lineStyleBooleans))
                {
                    LineStyleBooleans lineStyle = new LineStyleBooleans(so.OptionsByID.get(PropertyId.lineStyleBooleans).op);
                    if (lineStyle.fLine)
                    {
                        String colorval = "000000";
                        if (so.OptionsByID.containsKey(PropertyId.lineColor))
                            colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.lineColor).op,slide,so);
                         
                        _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
                        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("val", colorval);
                        _writer.writeEndElement();
                        _writer.writeEndElement();
                    }
                     
                }
                 
            }
            else if (__dummyScrutVar7.equals(1))
            {
                //pattern
                uint blipIndex = so.OptionsByID.get(PropertyId.lineFillBlip).op;
                DrawingGroup gr = (DrawingGroup)this._ctx.getPpt().DocumentRecord.firstChildWithType().Children[0];
                BlipStoreEntry bse = (BlipStoreEntry)gr.firstChildWithType().Children[(int)blipIndex - 1];
                BitmapBlip b = (BitmapBlip)_ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
                _writer.WriteStartElement("a", "pattFill", OpenXmlNamespaces.DrawingML);
                _writer.WriteAttributeString("prst", Utils.getPrstForPatternCode(b.m_bTag));
                //Utils.getPrstForPattern(blipNamePattern));
                _writer.WriteStartElement("a", "fgClr", OpenXmlNamespaces.DrawingML);
                if (so.OptionsByID.containsKey(PropertyId.lineColor))
                {
                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                    _writer.WriteAttributeString("val", Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.lineColor).op, container.firstAncestorWithType(), so));
                    _writer.writeEndElement();
                }
                else
                {
                    _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", "tx1");
                    _writer.writeEndElement();
                } 
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "bgClr", OpenXmlNamespaces.DrawingML);
                if (so.OptionsByID.containsKey(PropertyId.lineBackColor))
                {
                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                    _writer.WriteAttributeString("val", Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.lineBackColor).op, container.firstAncestorWithType(), so));
                    _writer.writeEndElement();
                }
                else
                {
                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", "FFFFFF");
                    _writer.writeEndElement();
                } 
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
            else if (__dummyScrutVar7.equals(2))
            {
            }
            else
            {
            }   
        }
        else
        {
            //texture
            if (so.OptionsByID.containsKey(PropertyId.lineStyleBooleans))
            {
                LineStyleBooleans lineStyle = new LineStyleBooleans(so.OptionsByID.get(PropertyId.lineStyleBooleans).op);
                if (lineStyle.fLine)
                {
                    String colorval = "000000";
                    if (so.OptionsByID.containsKey(PropertyId.lineColor))
                        colorval = Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.lineColor).op,slide,so);
                     
                    _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", colorval);
                    _writer.writeEndElement();
                    _writer.writeEndElement();
                }
                 
            }
             
        } 
        if (so.OptionsByID.containsKey(PropertyId.lineDashing))
        {
            _writer.WriteStartElement("a", "prstDash", OpenXmlNamespaces.DrawingML);
            switch((DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineDashing)so.OptionsByID.get(PropertyId.lineDashing).op)
            {
                case Solid: 
                    _writer.writeAttributeString("val", "solid");
                    break;
                case DashSys: 
                    _writer.writeAttributeString("val", "sysDash");
                    break;
                case DotSys: 
                    _writer.writeAttributeString("val", "sysDot");
                    break;
                case DashDotSys: 
                    _writer.writeAttributeString("val", "sysDashDot");
                    break;
                case DashDotDotSys: 
                    _writer.writeAttributeString("val", "sysDashDotDot");
                    break;
                case DotGEL: 
                    _writer.writeAttributeString("val", "dot");
                    break;
                case DashGEL: 
                    _writer.writeAttributeString("val", "dash");
                    break;
                case LongDashGEL: 
                    _writer.writeAttributeString("val", "lgDash");
                    break;
                case DashDotGEL: 
                    _writer.writeAttributeString("val", "dashDot");
                    break;
                case LongDashDotGEL: 
                    _writer.writeAttributeString("val", "lgDashDot");
                    break;
                case LongDashDotDotGEL: 
                    _writer.writeAttributeString("val", "lgDashDotDot");
                    break;
            
            }
            _writer.writeEndElement();
        }
         
        if (so.OptionsByID.containsKey(PropertyId.lineStartArrowhead))
        {
            DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd val = (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd)so.OptionsByID.get(PropertyId.lineStartArrowhead).op;
            if (val != DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd.NoEnd)
            {
                _writer.WriteStartElement("a", "headEnd", OpenXmlNamespaces.DrawingML);
                switch(val)
                {
                    case ArrowEnd: 
                        _writer.writeAttributeString("type", "triangle");
                        break;
                    case ArrowStealthEnd: 
                        _writer.writeAttributeString("type", "stealth");
                        break;
                    case ArrowDiamondEnd: 
                        _writer.writeAttributeString("type", "diamond");
                        break;
                    case ArrowOvalEnd: 
                        _writer.writeAttributeString("type", "oval");
                        break;
                    case ArrowOpenEnd: 
                        _writer.writeAttributeString("type", "arrow");
                        break;
                    case ArrowChevronEnd: 
                    case ArrowDoubleChevronEnd: 
                        //this should be ignored
                        _writer.writeAttributeString("type", "triangle");
                        break;
                
                }
                _writer.writeAttributeString("w", "med");
                _writer.writeAttributeString("len", "med");
                _writer.writeEndElement();
            }
             
        }
         
        //headEnd
        if (so.OptionsByID.containsKey(PropertyId.lineEndArrowhead))
        {
            DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd val = (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd)so.OptionsByID.get(PropertyId.lineEndArrowhead).op;
            if (val != DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.LineEnd.NoEnd)
            {
                _writer.WriteStartElement("a", "tailEnd", OpenXmlNamespaces.DrawingML);
                switch(val)
                {
                    case ArrowEnd: 
                        _writer.writeAttributeString("type", "triangle");
                        break;
                    case ArrowStealthEnd: 
                        _writer.writeAttributeString("type", "stealth");
                        break;
                    case ArrowDiamondEnd: 
                        _writer.writeAttributeString("type", "diamond");
                        break;
                    case ArrowOvalEnd: 
                        _writer.writeAttributeString("type", "oval");
                        break;
                    case ArrowOpenEnd: 
                        _writer.writeAttributeString("type", "arrow");
                        break;
                    case ArrowChevronEnd: 
                    case ArrowDoubleChevronEnd: 
                        //this should be ignored
                        _writer.writeAttributeString("type", "triangle");
                        break;
                
                }
                _writer.writeAttributeString("w", "med");
                _writer.writeAttributeString("len", "med");
                _writer.writeEndElement();
            }
             
        }
         
        //tailnd
        if (!so.OptionsByID.containsKey(PropertyId.lineEndCapStyle))
        {
        }
         
        //    _writer.WriteStartElement("a", "miter", OpenXmlNamespaces.DrawingML);
        //    _writer.WriteAttributeString("lim", "800000");
        //    _writer.WriteEndElement();
        _writer.writeEndElement();
        //ln
        //shadow
        if (so.OptionsByID.containsKey(PropertyId.ShadowStyleBooleanProperties))
        {
            ShadowStyleBooleanProperties sp = new ShadowStyleBooleanProperties(so.OptionsByID.get(PropertyId.ShadowStyleBooleanProperties).op);
            if (sp.fUsefShadow & sp.fShadow)
            {
                new ShadowMapping(_ctx,_writer).apply(so);
            }
             
        }
         
    }

    public Point scanEMFPictureForSize(BlipStoreEntry bse) throws Exception {
        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record mb = _ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
        Point size = new Point();
        //write the blip
        if (mb != null)
        {
            switch(bse.btWin32)
            {
                case msoblipEMF: 
                case msoblipWMF: 
                    //it's a meta image
                    MetafilePictBlip metaBlip = (MetafilePictBlip)mb;
                    size = metaBlip.m_ptSize;
                    //meta images can be compressed
                    byte[] decompressed = metaBlip.decrompress();
                    break;
            
            }
        }
         
        return size;
    }

    private void writeOle(ShapeContainer container, ExOleEmbedContainer oleContainer) throws Exception {
        Shape sh = container.firstChildWithType();
        ShapeOptions so = container.firstChildWithType();
        _writer.WriteStartElement("p", "graphicFrame", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "nvGraphicFramePr", OpenXmlNamespaces.PresentationML);
        String id = writeCNvPr(--groupcounter,"");
        _writer.WriteStartElement("p", "cNvGraphicFramePr", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("a", "graphicFrameLocks", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("noChangeAspect", "1");
        _writer.writeEndElement();
        //graphicFrameLocks
        _writer.writeEndElement();
        //cNvGraphicFramePr
        //_writer.WriteElementString("p", "nvPr", OpenXmlNamespaces.PresentationML, "");
        _writer.WriteStartElement("p", "nvPr", OpenXmlNamespaces.PresentationML);
        OEPlaceHolderAtom placeholder = null;
        int exObjIdRef = 0;
        RefSupport<OEPlaceHolderAtom> refVar___7 = new RefSupport<OEPlaceHolderAtom>(placeholder);
        RefSupport<Integer> refVar___8 = new RefSupport<Integer>(exObjIdRef);
        CheckClientData(container.firstChildWithType(), refVar___7, refVar___8);
        placeholder = refVar___7.getValue();
        exObjIdRef = refVar___8.getValue();
        _writer.writeEndElement();
        _writer.writeEndElement();
        //nvGraphicFramePr
        Rectangle anchor = new Rectangle();
        ClientAnchor clanchor = container.firstChildWithType();
        if (clanchor == null)
        {
            ChildAnchor chanchor = container.firstChildWithType();
            anchor = new Rectangle(chanchor.Left, chanchor.Top, chanchor.rcgBounds.Width, chanchor.rcgBounds.Height);
            if (anchor != null && anchor.Right >= anchor.Left && anchor.Bottom >= anchor.Top)
            {
                _writer.WriteStartElement("p", "xfrm", OpenXmlNamespaces.PresentationML);
                _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("x", anchor.Left.toString());
                _writer.writeAttributeString("y", anchor.Top.toString());
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("cx", (anchor.Right - anchor.Left).toString());
                _writer.writeAttributeString("cy", (anchor.Bottom - anchor.Top).toString());
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
             
        }
        else
        {
            anchor = new Rectangle(clanchor.getLeft(), clanchor.getTop(), clanchor.getRight() - clanchor.getLeft(), clanchor.getBottom() - clanchor.getTop());
            if (anchor != null && anchor.Right >= anchor.Left && anchor.Bottom >= anchor.Top)
            {
                _writer.WriteStartElement("p", "xfrm", OpenXmlNamespaces.PresentationML);
                _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("x", Utils.MasterCoordToEMU(anchor.Left).toString());
                _writer.writeAttributeString("y", Utils.MasterCoordToEMU(anchor.Top).toString());
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("cx", Utils.MasterCoordToEMU(anchor.Right - anchor.Left).toString());
                _writer.writeAttributeString("cy", Utils.MasterCoordToEMU(anchor.Bottom - anchor.Top).toString());
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
             
        } 
        _writer.WriteStartElement("a", "graphic", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "graphicData", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("uri", "http://schemas.openxmlformats.org/presentationml/2006/ole");
        EmbeddedObjectPart embPart = null;
        embPart = parentSlideMapping.targetPart.addEmbeddedObjectPart(DIaLOGIKa.b2xtranslator.OpenXmlLib.EmbeddedObjectPart.ObjectType.Other);
        embPart.setTargetDirectory("..\\embeddings");
        InputStream outStream = embPart.getStream();
        if (oleContainer.stgAtom.Instance == 1)
        {
            outStream.write(oleContainer.stgAtom.decompressData(),0,(int)oleContainer.stgAtom.decompressedSize);
        }
        else
        {
            outStream.write(oleContainer.stgAtom.data,0,oleContainer.stgAtom.data.length);
        } 
        String rId = embPart.getRelIdToString();
        String spid = "_x0000_s" + String.valueOf(sh.spid);
        //+ id;
        String name = oleContainer.allChildrenWithType().get(0).Text;
        String progId = oleContainer.allChildrenWithType().get(1).Text;
        DrawingGroup gr = (DrawingGroup)this._ctx.getPpt().DocumentRecord.firstChildWithType().Children[0];
        BlipStoreEntry bse = (BlipStoreEntry)gr.firstChildWithType().Children[(int)so.OptionsByID.get(PropertyId.Pib).op - 1];
        //VmlPart vmlPart = null;
        //vmlPart = parentSlideMapping.targetPart.AddVmlPart();
        //vmlPart.TargetDirectory = "..\\drawings";
        //System.IO.Stream vmlStream = vmlPart.GetStream();
        GroupShapeRecord gsr = container.firstAncestorWithType().<ShapeContainer>FirstChildWithType().<GroupShapeRecord>FirstChildWithType();
        ClientAnchor anch = container.firstChildWithType();
        if (anch == null)
            anch = container.firstAncestorWithType().<ShapeContainer>FirstChildWithType().<ClientAnchor>FirstChildWithType();
         
        ChildAnchor chanch = container.firstChildWithType();
        Rectangle rec = new Rectangle();
        if (chanch != null)
        {
            rec = new Rectangle(chanch.Left, chanch.Top, chanch.Right - anch.getLeft(), chanch.Bottom - anch.getTop());
        }
        else if (anch != null)
        {
            rec = new Rectangle(anch.getLeft(), anch.getTop(), anch.getRight() - anch.getLeft(), anch.getBottom() - anch.getTop());
        }
        else
        {
            rec = new Rectangle(chanch.Left, chanch.Top, chanch.Right - chanch.Left, chanch.Bottom - chanch.Top);
        }  
        //VMLPictureMapping vm = new VMLPictureMapping(vmlPart, _ctx.WriterSettings);
        Point size = new Point();
        //vm.Apply(bse, sh, so, rec, _ctx, spid, ref size);
        RefSupport<Point> refVar___9 = new RefSupport<Point>(size);
        addVMLEntry(bse,sh,so,rec,spid,refVar___9);
        size = refVar___9.getValue();
        _writer.WriteStartElement("p", "oleObj", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("spid", spid);
        _writer.writeAttributeString("name", name);
        _writer.WriteAttributeString("id", OpenXmlNamespaces.Relationships, rId);
        _writer.writeAttributeString("imgW", size.X.toString());
        _writer.writeAttributeString("imgH", size.Y.toString());
        _writer.writeAttributeString("progId", progId);
        _writer.WriteStartElement("p", "embed", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("followColorScheme", "full");
        _writer.writeEndElement();
        //embed
        _writer.writeEndElement();
        //oleObj
        _writer.writeEndElement();
        //graphicData
        _writer.writeEndElement();
        //graphic
        _writer.writeEndElement();
    }

    //graphicFrame
    private CSList<ArrayList> VMLEntries = new CSList<ArrayList>();
    private void addVMLEntry(BlipStoreEntry bse, Shape shape, ShapeOptions options, Rectangle bounds, String spid, RefSupport<Point> size) throws Exception {
        size.setValue(scanEMFPictureForSize(bse));
        ArrayList newVMLEntries = new ArrayList();
        newVMLEntries.add(bse);
        newVMLEntries.add(shape);
        newVMLEntries.add(options);
        newVMLEntries.add(bounds);
        newVMLEntries.add(spid);
        newVMLEntries.add(size.getValue());
        VMLEntries.add(newVMLEntries);
    }

    private void writeVML() throws Exception {
        if (VMLEntries.size() > 0)
        {
            VmlPart vmlPart = null;
            vmlPart = parentSlideMapping.targetPart.addVmlPart();
            vmlPart.setTargetDirectory("..\\drawings");
            InputStream vmlStream = vmlPart.getStream();
            VMLPictureMapping vm = new VMLPictureMapping(vmlPart,_ctx.getWriterSettings());
            Point size = new Point();
            vm.apply(VMLEntries,_ctx);
        }
         
    }

    //vm.Apply(bse, sh, so, rec, _ctx, spid, ref size);
    private void writePic(ShapeContainer container) throws Exception {
        Shape sh = container.firstChildWithType();
        ShapeOptions so = container.firstChildWithType();
        uint indexOfPicture = 0;
        //TODO: read these infos from so
        ++_ctx.lastImageID;
        int id = _ctx.lastImageID;
        String name = "";
        String descr = "";
        String rId = "";
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry en : so.Options)
        {
            switch(en.pid)
            {
                case Pib: 
                    indexOfPicture = en.op - 1;
                    break;
                case pibName: 
                    break;
                case pibPrintName: 
                    //    name = Encoding.Unicode.GetString(en.opComplex);
                    //    name = name.Substring(0, name.Length - 1).Replace("\0","");
                    id = (int)en.op;
                    break;
            
            }
        }
        DrawingGroup gr = (DrawingGroup)this._ctx.getPpt().DocumentRecord.firstChildWithType().Children[0];
        BlipStoreEntry bse = (BlipStoreEntry)gr.firstChildWithType().Children[(int)indexOfPicture];
        //if (this.parentSlideMapping is MasterMapping) return;
        if (this._ctx.AddedImages.ContainsKey(bse.foDelay))
        {
            rId = this._ctx.AddedImages.get(bse.foDelay);
        }
        else
        {
            if (!_ctx.getPpt().PicturesContainer._pictures.ContainsKey(bse.foDelay))
            {
                return ;
            }
             
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record recBlip = _ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
            if (recBlip instanceof BitmapBlip)
            {
                BitmapBlip b = (BitmapBlip)_ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
                ImagePart imgPart = null;
                imgPart = parentSlideMapping.targetPart.addImagePart(getImageType(b.TypeCode));
                imgPart.setTargetDirectory("..\\media");
                InputStream outStream = imgPart.getStream();
                outStream.write(b.m_pvBits,0,b.m_pvBits.length);
                rId = imgPart.getRelIdToString();
            }
            else if (recBlip instanceof MetafilePictBlip)
            {
                MetafilePictBlip mb = (MetafilePictBlip)_ctx.getPpt().PicturesContainer._pictures.get(bse.foDelay);
                ImagePart imgPart = null;
                imgPart = parentSlideMapping.targetPart.addImagePart(getImageType(mb.TypeCode));
                imgPart.setTargetDirectory("..\\media");
                InputStream outStream = imgPart.getStream();
                byte[] decompressed = mb.decrompress();
                outStream.write(decompressed,0,decompressed.length);
                //outStream.Write(mb.m_pvBits, 0, mb.m_pvBits.Length);
                rId = imgPart.getRelIdToString();
            }
              
        } 
        //this._ctx.AddedImages.Add(bse.foDelay, rId);
        _writer.WriteStartElement("p", "pic", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "nvPicPr", OpenXmlNamespaces.PresentationML);
        if (!spidToId.containsKey(sh.spid))
        {
            spidToId.put(sh.spid, id);
        }
         
        _writer.WriteStartElement("p", "cNvPr", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf(spidToId.get(sh.spid)));
        //_writer.WriteStartElement("p", "cNvPr", OpenXmlNamespaces.PresentationML);
        //_writer.WriteAttributeString("id", id);
        _writer.writeAttributeString("name", name);
        _writer.writeAttributeString("descr", descr);
        _writer.writeEndElement();
        //p:cNvPr
        _writer.WriteStartElement("p", "cNvPicPr", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("a", "picLocks", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("noChangeAspect", "1");
        _writer.writeAttributeString("noChangeArrowheads", "1");
        _writer.writeEndElement();
        //a:picLocks
        _writer.writeEndElement();
        //p:cNvPicPr
        _writer.WriteElementString("p", "nvPr", OpenXmlNamespaces.PresentationML, "");
        _writer.writeEndElement();
        //p:nvPicPr
        _writer.WriteStartElement("p", "blipFill", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("a", "blip", OpenXmlNamespaces.DrawingML);
        _writer.WriteAttributeString("embed", OpenXmlNamespaces.Relationships, rId);
        if (so.OptionsByID.containsKey(PropertyId.pictureBrightness) | so.OptionsByID.containsKey(PropertyId.pictureContrast))
        {
            _writer.WriteStartElement("a", "lum", OpenXmlNamespaces.DrawingML);
            if (so.OptionsByID.containsKey(PropertyId.pictureBrightness))
            {
                long b = so.OptionsByID.get(PropertyId.pictureBrightness).op;
                if (b == 0xFFF8000)
                    b = 0;
                 
                double b1 = 0;
                if (((int)b) < 0)
                {
                    int b2 = (int)b;
                    b1 = (double)b2 / 0x8000;
                }
                else
                {
                    b1 = (double)b / 0x8000;
                } 
                b1 = b1 * 100000;
                b1 = Math.floor(b1);
                _writer.writeAttributeString("bright", String.valueOf(b1));
            }
             
            //if (so.OptionsByID.ContainsKey(ShapeOptions.PropertyId.pictureContrast))
            //{
            //    Int32 b = (int)so.OptionsByID[ShapeOptions.PropertyId.pictureContrast].op;
            //    Decimal b2 = (Decimal)b  / 0x10000; //This comes from analysing, no hint in spec found
            //    b2 = b2 * 100000;
            //    b2 = Math.Floor(b2);
            //    if (b == 0x7FFFFFFF) b2 = 100000;
            //    if (b2 > 100000) b2 = 100000;
            //    _writer.WriteAttributeString("contrast", b2.ToString());
            //}
            _writer.writeEndElement();
        }
         
        _writer.writeEndElement();
        //a:blip
        _writer.WriteStartElement("a", "srcRect", OpenXmlNamespaces.DrawingML);
        if (so.OptionsByID.containsKey(PropertyId.cropFromLeft))
        {
            _writer.writeAttributeString("l", String.valueOf(Math.floor((double)(int)so.OptionsByID.get(PropertyId.cropFromLeft).op / 65536 * 100000)));
        }
         
        if (so.OptionsByID.containsKey(PropertyId.cropFromTop))
        {
            _writer.writeAttributeString("t", String.valueOf(Math.floor((double)(int)so.OptionsByID.get(PropertyId.cropFromTop).op / 65536 * 100000)));
        }
         
        if (so.OptionsByID.containsKey(PropertyId.cropFromRight))
        {
            _writer.writeAttributeString("r", String.valueOf(Math.floor((double)(int)so.OptionsByID.get(PropertyId.cropFromRight).op / 65536 * 100000)));
        }
         
        if (so.OptionsByID.containsKey(PropertyId.cropFromBottom))
        {
            _writer.writeAttributeString("b", String.valueOf(Math.floor((double)(int)so.OptionsByID.get(PropertyId.cropFromBottom).op / 65536 * 100000)));
        }
         
        _writer.writeEndElement();
        _writer.WriteStartElement("a", "stretch", OpenXmlNamespaces.DrawingML);
        _writer.WriteElementString("a", "fillRect", OpenXmlNamespaces.DrawingML, "");
        _writer.writeEndElement();
        //a:stretch
        _writer.writeEndElement();
        //p:blipFill
        // Visible shape properties
        _writer.WriteStartElement("p", "spPr", OpenXmlNamespaces.PresentationML);
        ClientAnchor anchor = container.firstChildWithType();
        if (anchor != null && anchor.getRight() >= anchor.getLeft() && anchor.getBottom() >= anchor.getTop())
        {
            _writer.WriteStartElement("a", "xfrm", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("x", String.valueOf(Utils.masterCoordToEMU(anchor.getLeft())));
            _writer.writeAttributeString("y", String.valueOf(Utils.masterCoordToEMU(anchor.getTop())));
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("cx", String.valueOf(Utils.masterCoordToEMU(anchor.getRight() - anchor.getLeft())));
            _writer.writeAttributeString("cy", String.valueOf(Utils.masterCoordToEMU(anchor.getBottom() - anchor.getTop())));
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        else
        {
            ChildAnchor chanchor = container.firstChildWithType();
            _writer.WriteStartElement("a", "xfrm", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("x", String.valueOf((chanchor.Left)));
            _writer.writeAttributeString("y", String.valueOf((chanchor.Top)));
            _writer.writeEndElement();
            _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("cx", String.valueOf((chanchor.Right - chanchor.Left)));
            _writer.writeAttributeString("cy", String.valueOf((chanchor.Bottom - chanchor.Top)));
            _writer.writeEndElement();
            _writer.writeEndElement();
        } 
        _writer.WriteStartElement("a", "prstGeom", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("prst", "rect");
        _writer.WriteElementString("a", "avLst", OpenXmlNamespaces.DrawingML, "");
        _writer.writeEndElement();
        //a:prstGeom
        _writer.WriteElementString("a", "noFill", OpenXmlNamespaces.DrawingML, "");
        //line
        if (so.OptionsByID.containsKey(PropertyId.lineStyleBooleans))
        {
            _writer.WriteStartElement("a", "ln", OpenXmlNamespaces.DrawingML);
            if (so.OptionsByID.containsKey(PropertyId.lineWidth))
            {
                _writer.writeAttributeString("w", String.valueOf(so.OptionsByID.get(PropertyId.lineWidth).op));
            }
             
            writeLineProperties(so);
            _writer.writeEndElement();
        }
         
        //shadow
        if (so.OptionsByID.containsKey(PropertyId.ShadowStyleBooleanProperties))
        {
            ShadowStyleBooleanProperties p = new ShadowStyleBooleanProperties(so.OptionsByID.get(PropertyId.ShadowStyleBooleanProperties).op);
            if (p.fUsefShadow & p.fShadow)
            {
                new ShadowMapping(_ctx,_writer).apply(so);
            }
             
        }
         
        _writer.writeEndElement();
        //p:spPr
        _writer.writeEndElement();
    }

    //p:pic
    public void writeBodyPr(DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec, boolean cancelAttributes, boolean no3D) throws Exception {
        _writer.WriteStartElement("a", "bodyPr", OpenXmlNamespaces.DrawingML);
        //bool cancelAttributes = false;
        if (rec instanceof ShapeContainer)
        {
            ShapeContainer container = (ShapeContainer)rec;
            Instance __dummyScrutVar13 = container.firstChildWithType().Instance;
            if (__dummyScrutVar13.equals(0x88))
            {
                // WordArt 1, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 18, 19, 25, 29, 30
                if (so.OptionsByID.containsKey(PropertyId.GeometryTextBooleanProperties))
                {
                    GeometryTextBooleanProperties gbp = new GeometryTextBooleanProperties(so.OptionsByID.get(PropertyId.GeometryTextBooleanProperties).op);
                    if (gbp.fUsegtextFVertical && gbp.gtextFVertical)
                    {
                        _writer.writeAttributeString("vert", "wordArtVert");
                    }
                     
                }
                 
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textPlain");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 50000");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0x8A))
            {
                // WordArt 20
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textTriangle");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 50000");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0x90))
            {
                // WordArt 3
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textArchUp");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 10800000");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0x98))
            {
                // WordArt 23
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textCurveUp");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 40356");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0x9A))
            {
                // WordArt 26, 28
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textCascadeUp");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 44444");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0x9C))
            {
                // WordArt 17
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textWave1");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj1");
                _writer.writeAttributeString("fmla", "val 13005");
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj2");
                _writer.writeAttributeString("fmla", "val 0");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0x9E))
            {
                //WordArt 22
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textDoubleWave1");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj1");
                _writer.writeAttributeString("fmla", "val 6500");
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj2");
                _writer.writeAttributeString("fmla", "val 0");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0x9F))
            {
                // WordArt 24
                if (so.OptionsByID.containsKey(PropertyId.GeometryTextBooleanProperties))
                {
                    GeometryTextBooleanProperties gbp = new GeometryTextBooleanProperties(so.OptionsByID.get(PropertyId.GeometryTextBooleanProperties).op);
                    if (gbp.fUsegtextFVertical && gbp.gtextFVertical)
                    {
                        _writer.writeAttributeString("vert", "wordArtVert");
                    }
                     
                }
                 
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textWave4");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj1");
                _writer.writeAttributeString("fmla", "val 13005");
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj2");
                _writer.writeAttributeString("fmla", "val 0");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0xA1))
            {
                // WordArt 4
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textDeflate");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 26227");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0xA3))
            {
                // WordArt 27
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textDeflateBottom");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 76472");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0xAA))
            {
                //WordArt 21
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textFadeUp");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 9991");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0xAC))
            {
                // WordArt 2, 14
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textSlantUp");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 55556");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else if (__dummyScrutVar13.equals(0xAF))
            {
                // WordArt 5
                _writer.writeAttributeString("wrap", "none");
                _writer.writeAttributeString("fromWordArt", "1");
                _writer.WriteStartElement("a", "prstTxWarp", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", "textCanDown");
                _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "adj");
                _writer.writeAttributeString("fmla", "val 33333");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                cancelAttributes = true;
            }
            else
            {
            }             
        }
         
        if (!cancelAttributes)
        {
            if (so.OptionsByID.containsKey(PropertyId.dxTextLeft))
            {
                _writer.writeAttributeString("lIns", String.valueOf(so.OptionsByID.get(PropertyId.dxTextLeft).op));
            }
             
            if (so.OptionsByID.containsKey(PropertyId.dyTextTop))
            {
                _writer.writeAttributeString("tIns", String.valueOf(so.OptionsByID.get(PropertyId.dyTextTop).op));
            }
             
            if (so.OptionsByID.containsKey(PropertyId.dxTextRight))
            {
                _writer.writeAttributeString("rIns", String.valueOf(so.OptionsByID.get(PropertyId.dxTextRight).op));
            }
             
            if (so.OptionsByID.containsKey(PropertyId.dyTextBottom))
            {
                _writer.writeAttributeString("bIns", String.valueOf(so.OptionsByID.get(PropertyId.dyTextBottom).op));
            }
             
            if (so.OptionsByID.containsKey(PropertyId.WrapText))
            {
                long __dummyScrutVar14 = so.OptionsByID.get(PropertyId.WrapText).op;
                if (__dummyScrutVar14.equals(0))
                {
                    //square
                    _writer.writeAttributeString("wrap", "square");
                }
                else if (__dummyScrutVar14.equals(1))
                {
                }
                else //by points
                //TODO
                if (__dummyScrutVar14.equals(2))
                {
                    //none
                    _writer.writeAttributeString("wrap", "none");
                }
                else
                {
                }   
            }
             
            //top bottom
            //through
            //TODO
            String s = "";
            for (DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry en : so.Options)
            {
                switch(en.pid)
                {
                    case anchorText: 
                        long __dummyScrutVar16 = en.op;
                        if (__dummyScrutVar16.equals(0))
                        {
                            //Top
                            _writer.writeAttributeString("anchor", "t");
                        }
                        else if (__dummyScrutVar16.equals(1))
                        {
                            //Middle
                            _writer.writeAttributeString("anchor", "ctr");
                        }
                        else if (__dummyScrutVar16.equals(2))
                        {
                            //Bottom
                            _writer.writeAttributeString("anchor", "b");
                        }
                        else if (__dummyScrutVar16.equals(3))
                        {
                            //TopCentered
                            _writer.writeAttributeString("anchor", "t");
                            _writer.writeAttributeString("anchorCtr", "1");
                        }
                        else if (__dummyScrutVar16.equals(4))
                        {
                            //MiddleCentered
                            _writer.writeAttributeString("anchor", "ctr");
                            _writer.writeAttributeString("anchorCtr", "1");
                        }
                        else if (__dummyScrutVar16.equals(5))
                        {
                            //BottomCentered
                            _writer.writeAttributeString("anchor", "b");
                            _writer.writeAttributeString("anchorCtr", "1");
                        }
                        else //TopBaseline
                        //BottomBaseline
                        //TopCenteredBaseline
                        if (__dummyScrutVar16.equals(6) || __dummyScrutVar16.equals(7) || __dummyScrutVar16.equals(8) || __dummyScrutVar16.equals(9))
                        {
                        }
                               
                        break;
                    default: 
                        //BottomCenteredBaseline
                        //TODO
                        s += en.pid.toString() + " ";
                        break;
                
                }
            }
        }
         
        RegularContainer slide = so.firstAncestorWithType();
        if (slide == null)
            slide = so.firstAncestorWithType();
         
        if (slide == null)
            slide = so.firstAncestorWithType();
         
        if (!no3D && so.OptionsByID.containsKey(PropertyId.ThreeDObjectBooleanProperties))
        {
            ThreeDObjectProperties tdo = new ThreeDObjectProperties(so.OptionsByID.get(PropertyId.ThreeDObjectBooleanProperties).op);
            if (tdo.fc3D && tdo.fUsefc3D)
            {
                ThreeDStyleProperties tds = null;
                if (so.OptionsByID.containsKey(PropertyId.ThreeDStyleBooleanProperties))
                {
                    tds = new ThreeDStyleProperties(so.OptionsByID.get(PropertyId.ThreeDStyleBooleanProperties).op);
                }
                 
                double x = -1;
                double y = -1;
                double ox = -1;
                double oy = -1;
                if (so.OptionsByID.containsKey(PropertyId.c3DOriginX))
                {
                    byte[] data = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.c3DOriginX).op);
                    ox = (new FixedPointNumber(BitConverter.ToUInt16(data, 0), BitConverter.ToUInt16(data, 2))).getValue();
                }
                 
                if (so.OptionsByID.containsKey(PropertyId.c3DOriginY))
                {
                    byte[] data = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.c3DOriginY).op);
                    oy = (new FixedPointNumber(BitConverter.ToUInt16(data, 0), BitConverter.ToUInt16(data, 2))).getValue();
                }
                 
                if (so.OptionsByID.containsKey(PropertyId.c3DKeyX))
                {
                    byte[] data = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.c3DKeyX).op);
                    x = (new FixedPointNumber(BitConverter.ToUInt16(data, 0), BitConverter.ToUInt16(data, 2))).getValue();
                }
                 
                if (so.OptionsByID.containsKey(PropertyId.c3DKeyY))
                {
                    byte[] data = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.c3DKeyY).op);
                    y = (new FixedPointNumber(BitConverter.ToUInt16(data, 0), BitConverter.ToUInt16(data, 2))).getValue();
                }
                 
                String prst = "legacyObliqueRight";
                if (ox == -1 && oy == 0)
                {
                    prst = "legacyObliqueRight";
                }
                else if ((((int)(ox))) == 32768 && oy == -1)
                {
                    prst = "legacyPerspectiveTopLeft";
                }
                else if (ox == 0 && oy == 0)
                {
                    prst = "legacyPerspectiveFront";
                }
                else if (ox == -1 && (((int)(oy))) == 32768)
                {
                    prst = "legacyPerspectiveBottomRight";
                }
                    
                String dir = "t";
                if ((((int)(x))) == 15536)
                {
                    dir = "t";
                }
                else if ((((int)(y))) == 15536)
                {
                    dir = "r";
                }
                else if (x == -1)
                {
                    dir = "b";
                }
                else if (x == 0 && y == 50000)
                {
                    dir = "l";
                }
                    
                String rig = "legacyHarsh3";
                if (ox == 0 && oy == 0 && (((int)(x))) == 15536 && (((int)(y))) == 15536)
                {
                    rig = "legacyNormal2";
                }
                else if (((((int)(ox))) == 32768) || (ox == 0 && oy == 0))
                {
                    rig = "legacyNormal3";
                }
                  
                _writer.WriteStartElement("a", "scene3d", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "camera", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", prst);
                if (tds != null)
                    if (tds.fc3DConstrainRotation && tds.fUsefc3DConstrainRotation)
                    {
                        double xra = 0;
                        double yra = 0;
                        if (so.OptionsByID.containsKey(PropertyId.c3DXRotationAngle))
                        {
                            byte[] data = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.c3DXRotationAngle).op);
                            int integral = BitConverter.ToInt16(data, 2);
                            uint fractional = BitConverter.ToUInt16(data, 0);
                            xra = -1 * (integral + ((double)fractional / (double)65536));
                            if (xra < 0)
                                xra += 360;
                             
                        }
                         
                        if (so.OptionsByID.containsKey(PropertyId.c3DYRotationAngle))
                        {
                            byte[] data = BitConverter.GetBytes(so.OptionsByID.get(PropertyId.c3DYRotationAngle).op);
                            int integral = BitConverter.ToInt16(data, 2);
                            uint fractional = BitConverter.ToUInt16(data, 0);
                            yra = integral + ((double)fractional / (double)65536);
                            if (yra < 0)
                                yra += 360;
                             
                        }
                         
                        if (xra != 0 || yra != 0)
                        {
                            //rot
                            _writer.WriteStartElement("a", "rot", OpenXmlNamespaces.DrawingML);
                            //@lat
                            _writer.writeAttributeString("lat", String.valueOf(Math.floor(xra * 60000)));
                            //@lon
                            _writer.writeAttributeString("lon", String.valueOf(Math.floor(yra * 60000)));
                            //@rev
                            _writer.writeAttributeString("rev", "0");
                            _writer.writeEndElement();
                        }
                         
                    }
                     
                 
                //rot
                _writer.writeEndElement();
                //camera
                _writer.WriteStartElement("a", "lightRig", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("rig", rig);
                _writer.writeAttributeString("dir", dir);
                _writer.writeEndElement();
                //lightRig
                _writer.writeEndElement();
                //scene3d
                _writer.WriteStartElement("a", "sp3d", OpenXmlNamespaces.DrawingML);
                String extrusionH = "457200";
                if (so.OptionsByID.containsKey(PropertyId.c3DExtrudeBackward))
                {
                    //the -27000 comes from analysing PP 2003
                    uint v = so.OptionsByID.get(PropertyId.c3DExtrudeBackward).op - 27000;
                    extrusionH = String.valueOf(v);
                }
                 
                _writer.writeAttributeString("extrusionH", extrusionH);
                _writer.writeAttributeString("prstMaterial", "legacyMatte");
                //if (tdo.fc3UseExtrusionColor && tdo.fUsefc3DUseExtrusionColor)
                if (so.OptionsByID.containsKey(PropertyId.c3DExtrusionColor))
                {
                    _writer.WriteStartElement("a", "extrusionClr", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("val", Utils.getRGBColorFromOfficeArtCOLORREF(so.OptionsByID.get(PropertyId.c3DExtrusionColor).op,slide,so));
                    _writer.writeEndElement();
                    //srgbClr
                    _writer.writeEndElement();
                }
                 
                //extrusionClr
                _writer.writeEndElement();
            }
             
        }
         
        //sp3d
        if (so.OptionsByID.containsKey(PropertyId.TextBooleanProperties))
        {
            TextBooleanProperties props = new TextBooleanProperties(so.OptionsByID.get(PropertyId.TextBooleanProperties).op);
            if (props.fFitShapeToText && props.fUsefFitShapeToText)
                _writer.WriteElementString("a", "spAutoFit", OpenXmlNamespaces.DrawingML, "");
             
        }
         
        _writer.writeEndElement();
    }

    private void checkClientData(ClientData clientData, RefSupport<OEPlaceHolderAtom> placeholder, RefSupport<Integer> exObjIdRef) throws Exception {
        Boolean output = exObjIdRef.getValue() > -1;
        ShapeStyleTextProp9Atom = null;
        Boolean phWritten = false;
        if (clientData != null)
        {
            System.IO.MemoryStream ms = new System.IO.MemoryStream(clientData.bytes);
            if (ms.Length > 0)
            {
                DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                if (rec.TypeCode == 4116 && output)
                {
                    AnimationInfoContainer animinfo = (AnimationInfoContainer)rec;
                    animinfos.put(animinfo, _idCnt);
                    if (ms.Position < ms.Length)
                    {
                        rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                        rec.SiblingIdx = 1;
                    }
                     
                }
                 
                while (true)
                {
                    long __dummyScrutVar17 = rec.TypeCode;
                    if (__dummyScrutVar17.equals(3009))
                    {
                        exObjIdRef.setValue(((ExObjRefAtom)rec).exObjIdRef);
                    }
                    else if (__dummyScrutVar17.equals(3011))
                    {
                        placeholder.setValue((OEPlaceHolderAtom)rec);
                        if (placeholder.getValue() != null && output)
                        {
                            _writer.WriteStartElement("p", "ph", OpenXmlNamespaces.PresentationML);
                            if (!placeholder.getValue().isObjectPlaceholder())
                            {
                                String typeValue = Utils.placeholderIdToXMLValue(placeholder.getValue().PlacementId);
                                _writer.writeAttributeString("type", typeValue);
                            }
                             
                            byte __dummyScrutVar18 = placeholder.getValue().PlaceholderSize;
                            if (__dummyScrutVar18.equals(1))
                            {
                                _writer.writeAttributeString("sz", "half");
                            }
                            else if (__dummyScrutVar18.equals(2))
                            {
                                _writer.writeAttributeString("sz", "quarter");
                            }
                              
                            if (placeholder.getValue().Position != -1)
                            {
                                _writer.writeAttributeString("idx", String.valueOf(placeholder.getValue().Position));
                            }
                            else
                            {
                                try
                                {
                                    Slide master = _ctx.getPpt().FindMasterRecordById(clientData.firstAncestorWithType().<SlideAtom>FirstChildWithType().MasterId);
                                    for (Object __dummyForeachVar23 : master.firstChildWithType().<DrawingContainer>FirstChildWithType().<GroupContainer>FirstChildWithType().<ShapeContainer>AllChildrenWithType())
                                    {
                                        ShapeContainer cont = (ShapeContainer)__dummyForeachVar23;
                                        Shape s = cont.firstChildWithType();
                                        ClientData d = cont.firstChildWithType();
                                        if (d != null)
                                        {
                                            ms = new System.IO.MemoryStream(d.bytes);
                                            rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                                            if (rec instanceof OEPlaceHolderAtom)
                                            {
                                                OEPlaceHolderAtom placeholder2 = (OEPlaceHolderAtom)rec;
                                                if (placeholder2.PlacementId == PlaceholderEnum.MasterBody && (placeholder.getValue().PlacementId == PlaceholderEnum.Body || placeholder.getValue().PlacementId == PlaceholderEnum.Object))
                                                {
                                                    if (placeholder2.Position != -1)
                                                    {
                                                        _writer.writeAttributeString("idx", String.valueOf(placeholder2.Position));
                                                    }
                                                     
                                                }
                                                 
                                            }
                                             
                                        }
                                         
                                    }
                                }
                                catch (Exception __dummyCatchVar2)
                                {
                                }
                            
                            } 
                            //ignore
                            _writer.writeEndElement();
                            phWritten = true;
                        }
                         
                    }
                    else if (__dummyScrutVar17.equals(4116))
                    {
                    }
                    else if (__dummyScrutVar17.equals(5000))
                    {
                        RegularContainer con = (RegularContainer)rec;
                        for (Object __dummyForeachVar24 : con.allChildrenWithType())
                        {
                            ProgBinaryTag t = (ProgBinaryTag)__dummyForeachVar24;
                            CStringAtom c = t.firstChildWithType();
                            ProgBinaryTagDataBlob b = t.firstChildWithType();
                            StyleTextProp9Atom p = b.firstChildWithType();
                            ShapeStyleTextProp9Atom = p;
                        }
                    }
                    else
                    {
                    }    
                    if (ms.Position < ms.Length)
                    {
                        rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                    }
                    else
                    {
                        break;
                    } 
                }
            }
             
            RegularContainer container = (RegularContainer)(clientData.getParentRecord());
            for (Object __dummyForeachVar25 : container.allChildrenWithType())
            {
                ClientTextbox b = (ClientTextbox)__dummyForeachVar25;
                ms = new System.IO.MemoryStream(b.Bytes);
                DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec;
                while (ms.Position < ms.Length)
                {
                    rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
                    long __dummyScrutVar19 = rec.TypeCode;
                    //TextCharsAtom
                    //TextRunStyleAtom
                    //TextRulerAtom
                    //TextBytesAtom
                    //TextSpecialInfoAtom
                    if (__dummyScrutVar19.equals(0xfa0) || __dummyScrutVar19.equals(0xfa1) || __dummyScrutVar19.equals(0xfa6) || __dummyScrutVar19.equals(0xfa8) || __dummyScrutVar19.equals(0xfaa) || __dummyScrutVar19.equals(0xfa2))
                    {
                    }
                    else //MasterTextPropAtom
                    if (__dummyScrutVar19.equals(0xfd8))
                    {
                    }
                    else //SlideNumberMCAtom
                    if (__dummyScrutVar19.equals(0xff7))
                    {
                        //DateTimeMCAtom
                        if (!phWritten && output)
                        {
                            _writer.WriteStartElement("p", "ph", OpenXmlNamespaces.PresentationML);
                            _writer.writeAttributeString("type", "dt");
                            _writer.writeEndElement();
                        }
                         
                    }
                    else if (__dummyScrutVar19.equals(0xff9))
                    {
                    }
                    else //HeaderMCAtom
                    if (__dummyScrutVar19.equals(0xffa))
                    {
                    }
                    else //FooterMCAtom
                    if (__dummyScrutVar19.equals(0xff8))
                    {
                    }
                    else
                    {
                    }      
                }
            }
        }
         
    }

    //GenericDateMCAtom
    public void apply(ClientTextbox textbox) throws Exception {
        apply(textbox,false);
    }

    public void apply(ClientTextbox textbox, boolean insideTable) throws Exception {
        if (insideTable)
        {
            _writer.WriteStartElement("a", "txBody", OpenXmlNamespaces.DrawingML);
        }
        else
        {
            _writer.WriteStartElement("p", "txBody", OpenXmlNamespaces.PresentationML);
        } 
        writeBodyPr(textbox,insideTable,true);
        _writer.WriteStartElement("a", "lstStyle", OpenXmlNamespaces.DrawingML);
        boolean lvlRprWritten = false;
        System.IO.MemoryStream ms = new System.IO.MemoryStream(textbox.Bytes);
        TextHeaderAtom thAtom = null;
        TextStyleAtom style = null;
        CSList<Integer> lst = new CSList<Integer>();
        String lang = "en-US";
        while (ms.Position < ms.Length)
        {
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.ReadRecord(ms);
            long __dummyScrutVar20 = rec.TypeCode;
            if (__dummyScrutVar20.equals(0xf9e))
            {
                //OutlineTextRefAtom
                OutlineTextRefAtom otrAtom = (OutlineTextRefAtom)rec;
                SlideListWithText slideListWithText = _ctx.getPpt().DocumentRecord.RegularSlideListWithText;
                CSList<TextHeaderAtom> thAtoms = slideListWithText.SlideToPlaceholderTextHeaders.get(textbox.firstAncestorWithType().PersistAtom);
                thAtom = thAtoms.get(otrAtom.Index);
                //if (thAtom.TextAtom != null) text = thAtom.TextAtom.Text;
                if (thAtom.TextStyleAtom != null)
                    style = thAtom.TextStyleAtom;
                 
            }
            else if (__dummyScrutVar20.equals(0xf9f))
            {
                //TextHeaderAtom
                thAtom = (TextHeaderAtom)rec;
            }
            else if (__dummyScrutVar20.equals(0xfa0))
            {
                //TextCharsAtom
                thAtom.TextAtom = (TextAtom)rec;
            }
            else if (__dummyScrutVar20.equals(0xfa1))
            {
                //StyleTextPropAtom
                style = (TextStyleAtom)rec;
                style.setTextHeaderAtom(thAtom);
            }
            else if (__dummyScrutVar20.equals(0xfa2))
            {
                //MasterTextPropAtom
                MasterTextPropAtom m = (MasterTextPropAtom)rec;
                for (MasterTextPropRun r : m.MasterTextPropRuns)
                {
                    if (!lst.Contains(r.indentLevel))
                    {
                        _writer.WriteStartElement("a", "lvl" + (r.indentLevel + 1) + "pPr", OpenXmlNamespaces.DrawingML);
                        if (thAtom.TextType == TextType.CenterTitle || thAtom.TextType == TextType.CenterBody)
                        {
                            _writer.writeAttributeString("algn", "ctr");
                        }
                         
                        //_writer.WriteElementString("a", "buNone", OpenXmlNamespaces.DrawingML, "");
                        _writer.writeEndElement();
                        lst.Add(r.indentLevel);
                    }
                     
                }
            }
            else if (__dummyScrutVar20.equals(0xfa8))
            {
                //TextBytesAtom
                //text = ((TextBytesAtom)rec).Text;
                thAtom.TextAtom = (TextAtom)rec;
            }
            else if (__dummyScrutVar20.equals(0xfaa))
            {
                //TextSpecialInfoAtom
                TextSpecialInfoAtom sia = (TextSpecialInfoAtom)rec;
                if (sia.Runs.size() > 0)
                {
                    if (sia.Runs.get(0).si.lang)
                    {
                        UInt16 __dummyScrutVar21 = sia.Runs.get(0).si.lid;
                        if (__dummyScrutVar21.equals(0x0))
                        {
                        }
                        else // no language
                        if (__dummyScrutVar21.equals(0x13))
                        {
                        }
                        else //Any Dutch language is preferred over non-Dutch languages when proofing the text
                        if (__dummyScrutVar21.equals(0x400))
                        {
                        }
                        else
                        {
                            try
                            {
                                //no proofing
                                lang = Locale.GetCultureInfo(sia.Runs.get(0).si.lid).IetfLanguageTag;
                            }
                            catch (Exception __dummyCatchVar3)
                            {
                            }
                        
                        }   
                    }
                     
                }
                 
            }
            else //ignore
            //SlideNumberMCAtom
            //HeaderMCAtom
            //FooterMCAtom
            if (__dummyScrutVar20.equals(0xfd8) || __dummyScrutVar20.equals(0xff9) || __dummyScrutVar20.equals(0xffa) || __dummyScrutVar20.equals(0xff8))
            {
                //GenericDateMCAtom
                if (!lvlRprWritten)
                    for (ParagraphRun r : style.PRuns)
                    {
                        _writer.WriteStartElement("a", "lvl" + (r.IndentLevel + 1) + "pPr", OpenXmlNamespaces.DrawingML);
                        if (r.getAlignmentPresent())
                        {
                            Int16? __dummyScrutVar22 = r.Alignment;
                            if (__dummyScrutVar22.equals(0x0000))
                            {
                                //Left
                                _writer.writeAttributeString("algn", "l");
                            }
                            else if (__dummyScrutVar22.equals(0x0001))
                            {
                                //Center
                                _writer.writeAttributeString("algn", "ctr");
                            }
                            else if (__dummyScrutVar22.equals(0x0002))
                            {
                                //Right
                                _writer.writeAttributeString("algn", "r");
                            }
                            else if (__dummyScrutVar22.equals(0x0003))
                            {
                                //Justify
                                _writer.writeAttributeString("algn", "just");
                            }
                            else if (__dummyScrutVar22.equals(0x0004))
                            {
                                //Distributed
                                _writer.writeAttributeString("algn", "dist");
                            }
                            else if (__dummyScrutVar22.equals(0x0005))
                            {
                                //ThaiDistributed
                                _writer.writeAttributeString("algn", "thaiDist");
                            }
                            else if (__dummyScrutVar22.equals(0x0006))
                            {
                                //JustifyLow
                                _writer.writeAttributeString("algn", "justLow");
                            }
                                   
                        }
                         
                        String lastColor = "";
                        String lastSize = "";
                        String lastTypeface = "";
                        RegularContainer slide = textbox.firstAncestorWithType();
                        if (slide == null)
                            slide = textbox.firstAncestorWithType();
                         
                        if (slide == null)
                            slide = textbox.firstAncestorWithType();
                         
                        RefSupport<String> refVar___10 = new RefSupport<String>(lastColor);
                        RefSupport<String> refVar___11 = new RefSupport<String>(lastSize);
                        RefSupport<String> refVar___12 = new RefSupport<String>(lastTypeface);
                        new CharacterRunPropsMapping(_ctx,_writer).Apply(style.CRuns.get(0), "defRPr", slide, refVar___10, refVar___11, refVar___12, lang, null, r.IndentLevel);
                        lastColor = refVar___10.getValue();
                        lastSize = refVar___11.getValue();
                        lastTypeface = refVar___12.getValue();
                        _writer.writeEndElement();
                        lvlRprWritten = true;
                    }
                 
            }
            else
            {
            }        
        }
        _writer.writeEndElement();
        new TextMapping(_ctx,_writer).apply(this,textbox,_footertext,_headertext,_datetext);
        _writer.writeEndElement();
    }

    public static DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType getImageType(uint TypeCode) throws Exception {
        long __dummyScrutVar23 = TypeCode;
        if (__dummyScrutVar23.equals(0xF01A))
        {
            return DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Emf;
        }
        else if (__dummyScrutVar23.equals(0xF01B))
        {
            return DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Wmf;
        }
        else if (__dummyScrutVar23.equals(0xF01D))
        {
            return DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Jpeg;
        }
        else if (__dummyScrutVar23.equals(0xF01E))
        {
            return DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Png;
        }
        else if (__dummyScrutVar23.equals(0xF01F))
        {
            return DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Bmp;
        }
        else //DIP
        if (__dummyScrutVar23.equals(0xF020))
        {
            return DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Tiff;
        }
        else
        {
            return DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Png;
        }      
    }

    public void apply(RegularContainer container) throws Exception {
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record : container.Children)
        {
            // Descend into container records by default
            dynamicApply(record);
        }
    }

    public void apply(DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record) throws Exception {
    }

    // Ignore unsupported records
    //TraceLogger.DebugInternal("Unsupported record: {0}", record);
    private void writeGroupShapeProperties(ShapeContainer header) throws Exception {
        GroupShapeRecord groupShape = header.firstChildWithType();
        // Write non-visible Group Shape properties
        _writer.WriteStartElement("p", "nvGrpSpPr", OpenXmlNamespaces.PresentationML);
        // Non-visible Canvas Properties
        writeCNvPr(-1,"");
        _writer.WriteElementString("p", "cNvGrpSpPr", OpenXmlNamespaces.PresentationML, "");
        _writer.WriteElementString("p", "nvPr", OpenXmlNamespaces.PresentationML, "");
        _writer.writeEndElement();
        // Write visible Group Shape properties
        _writer.WriteStartElement("p", "grpSpPr", OpenXmlNamespaces.PresentationML);
        writeXFrm(_writer,new Rectangle());
        // groupShape.rcgBounds
        _writer.writeEndElement();
    }

    private void writecustGeom(Shape sh) throws Exception {
        if (!so.OptionsByID.containsKey(PropertyId.pVertices) | !so.OptionsByID.containsKey(PropertyId.pSegmentInfo))
        {
            _writer.WriteStartElement("a", "prstGeom", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("prst", "rect");
            _writer.WriteElementString("a", "avLst", OpenXmlNamespaces.DrawingML, "");
            _writer.writeEndElement();
            return ;
        }
         
        //prstGeom
        _writer.WriteStartElement("a", "custGeom", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "cxnLst", OpenXmlNamespaces.DrawingML);
        DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry pVertices = so.OptionsByID.get(PropertyId.pVertices);
        uint shapepath = 1;
        if (so.OptionsByID.containsKey(PropertyId.shapePath))
        {
            DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry ShapePath = so.OptionsByID.get(PropertyId.shapePath);
            shapepath = ShapePath.op;
        }
         
        DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry SegementInfo = so.OptionsByID.get(PropertyId.pSegmentInfo);
        PathParser pp = new PathParser(SegementInfo.opComplex,pVertices.opComplex);
        for (Point point : pp.getValues())
        {
            _writer.WriteStartElement("a", "cxn", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("ang", "0");
            _writer.WriteStartElement("a", "pos", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("x", point.X.toString());
            _writer.writeAttributeString("y", point.Y.toString());
            _writer.writeEndElement();
            //pos
            _writer.writeEndElement();
        }
        //cxn
        _writer.writeEndElement();
        //cxnLst
        _writer.WriteStartElement("a", "rect", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("l", "0");
        _writer.writeAttributeString("t", "0");
        _writer.writeAttributeString("r", "r");
        _writer.writeAttributeString("b", "b");
        _writer.writeEndElement();
        //rect
        _writer.WriteStartElement("a", "pathLst", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "path", OpenXmlNamespaces.DrawingML);
        //compute width and height:
        int minX = 1000;
        int minY = 1000;
        int maxX = -1000;
        int maxY = -1000;
        for (Point p : pp.getValues())
        {
            if ((p.X) < minX)
                minX = p.X;
             
            if ((p.X) > maxX)
                maxX = p.X;
             
            if ((p.Y) < minY)
                minY = p.Y;
             
            if ((p.Y) > maxY)
                maxY = p.Y;
             
        }
        _writer.writeAttributeString("w", String.valueOf((maxX - minX)));
        _writer.writeAttributeString("h", String.valueOf((maxY - minY)));
        int valuePointer = 0;
        long __dummyScrutVar24 = shapepath;
        //lines
        if (__dummyScrutVar24.equals(0) || __dummyScrutVar24.equals(1))
        {
            while (valuePointer < pp.getValues().size())
            {
                //lines closed
                if (valuePointer == 0)
                {
                    _writer.WriteStartElement("a", "moveTo", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "pt", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("x", pp.getValues().get(valuePointer).X.toString());
                    _writer.writeAttributeString("y", pp.getValues().get(valuePointer).Y.toString());
                    _writer.writeEndElement();
                    //pr
                    _writer.writeEndElement();
                    //moveTo
                    valuePointer += 1;
                }
                else
                {
                    _writer.WriteStartElement("a", "lnTo", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "pt", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("x", pp.getValues().get(valuePointer).X.toString());
                    _writer.writeAttributeString("y", pp.getValues().get(valuePointer).Y.toString());
                    _writer.writeEndElement();
                    //pt
                    _writer.writeEndElement();
                    //lnTo
                    valuePointer += 1;
                } 
            }
        }
        else if (__dummyScrutVar24.equals(2))
        {
        }
        else //curves
        if (__dummyScrutVar24.equals(3))
        {
        }
        else //curves closed
        if (__dummyScrutVar24.equals(4))
        {
            for (PathSegment seg : pp.getSegments())
            {
                //complex
                if (valuePointer >= pp.getValues().size())
                    break;
                 
                switch(seg.getType())
                {
                    case msopathLineTo: 
                        _writer.WriteStartElement("a", "lnTo", OpenXmlNamespaces.DrawingML);
                        _writer.WriteStartElement("a", "pt", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("x", pp.getValues().get(valuePointer).X.toString());
                        _writer.writeAttributeString("y", pp.getValues().get(valuePointer).Y.toString());
                        _writer.writeEndElement();
                        //pt
                        _writer.writeEndElement();
                        //lnTo
                        valuePointer += 1;
                        break;
                    case msopathCurveTo: 
                        _writer.WriteStartElement("a", "cubicBezTo", OpenXmlNamespaces.DrawingML);
                        _writer.WriteStartElement("a", "pt", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("x", pp.getValues().get(valuePointer).X.toString());
                        _writer.writeAttributeString("y", pp.getValues().get(valuePointer).Y.toString());
                        _writer.writeEndElement();
                        //pt
                        valuePointer += 1;
                        _writer.WriteStartElement("a", "pt", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("x", pp.getValues().get(valuePointer).X.toString());
                        _writer.writeAttributeString("y", pp.getValues().get(valuePointer).Y.toString());
                        _writer.writeEndElement();
                        //pt
                        valuePointer += 1;
                        _writer.WriteStartElement("a", "pt", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("x", pp.getValues().get(valuePointer).X.toString());
                        _writer.writeAttributeString("y", pp.getValues().get(valuePointer).Y.toString());
                        _writer.writeEndElement();
                        //pt
                        valuePointer += 1;
                        _writer.writeEndElement();
                        break;
                    case msopathMoveTo: 
                        //cubicBezTo
                        _writer.WriteStartElement("a", "moveTo", OpenXmlNamespaces.DrawingML);
                        _writer.WriteStartElement("a", "pt", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("x", pp.getValues().get(valuePointer).X.toString());
                        _writer.writeAttributeString("y", pp.getValues().get(valuePointer).Y.toString());
                        _writer.writeEndElement();
                        //pr
                        _writer.writeEndElement();
                        //moveTo
                        valuePointer += 1;
                        break;
                    case msopathClose: 
                        _writer.WriteElementString("a", "close", OpenXmlNamespaces.DrawingML, "");
                        break;
                    default: 
                        break;
                
                }
            }
        }
            
        _writer.writeEndElement();
        //path
        _writer.writeEndElement();
        //pathLst
        _writer.writeEndElement();
    }

    //custGeom
    private void writeprstGeom(Shape shape) throws Exception {
        if (shape != null)
        {
            String prst = Utils.getPrstForShape(shape.Instance);
            if (prst.length() > 0)
            {
                _writer.WriteStartElement("a", "prstGeom", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("prst", prst);
                if (StringSupport.equals(prst, "roundRect") & so.OptionsByID.containsKey(PropertyId.adjustValue))
                {
                    //TODO: implement for all shapes
                    _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("name", "adj");
                    _writer.writeAttributeString("fmla", "val " + Math.Floor(so.OptionsByID.get(PropertyId.adjustValue).op * 4.63).toString());
                    //TODO: find out where this 4.63 comes from (value found by analysing behaviour of Powerpoint 2003)
                    _writer.writeEndElement();
                    _writer.writeEndElement();
                }
                else if (StringSupport.equals(prst, "wedgeRectCallout") & so.OptionsByID.containsKey(PropertyId.adjustValue))
                {
                    //the following computations are based on experiments using Powerpoint 2003 and are not part of the spec
                    double val = (double)(int)so.OptionsByID.get(PropertyId.adjustValue).op;
                    double percent = val / 21600 * 100;
                    int newVal = 0;
                    if (percent >= 50)
                    {
                        newVal = (int)(percent - 50) * 1000;
                    }
                    else
                    {
                        newVal = ((int)((50 - percent))) * -1000;
                    } 
                    _writer.WriteStartElement("a", "avLst", OpenXmlNamespaces.DrawingML);
                    _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                    _writer.writeAttributeString("name", "adj1");
                    _writer.writeAttributeString("fmla", "val " + String.valueOf(newVal));
                    _writer.writeEndElement();
                    if (so.OptionsByID.containsKey(PropertyId.adjust2Value))
                    {
                        val = (double)(int)so.OptionsByID.get(PropertyId.adjust2Value).op;
                        percent = val / 21600 * 100;
                        newVal = 0;
                        if (percent >= 50)
                        {
                            newVal = (int)(percent - 50) * 1000;
                        }
                        else
                        {
                            newVal = ((int)((50 - percent))) * -1000;
                        } 
                        _writer.WriteStartElement("a", "gd", OpenXmlNamespaces.DrawingML);
                        _writer.writeAttributeString("name", "adj2");
                        _writer.writeAttributeString("fmla", "val " + String.valueOf(newVal));
                        _writer.writeEndElement();
                    }
                     
                    _writer.writeEndElement();
                }
                else
                {
                    _writer.WriteElementString("a", "avLst", OpenXmlNamespaces.DrawingML, "");
                }  
                _writer.writeEndElement();
            }
             
        }
         
    }

    //prstGeom
    public HashMap<Integer,Integer> spidToId = new HashMap<Integer,Integer>();
    private String writeCNvPr(int spid, String name) throws Exception {
        String id = "";
        if (!spidToId.containsKey(spid))
        {
            spidToId.put(spid, ++_idCnt);
        }
         
        _writer.WriteStartElement("p", "cNvPr", OpenXmlNamespaces.PresentationML);
        id = String.valueOf(spidToId.get(spid));
        _writer.writeAttributeString("id", id);
        _writer.writeAttributeString("name", name);
        _writer.writeEndElement();
        return id;
    }

    private void writeXFrm(XmlWriter _writer, Rectangle rect) throws Exception {
        _writer.WriteStartElement("a", "xfrm", OpenXmlNamespaces.DrawingML);
        // TODO: Coordinate conversion?
        _writer.WriteStartElement("a", "off", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("x", rect.X.toString());
        _writer.writeAttributeString("y", rect.Y.toString());
        _writer.writeEndElement();
        // TODO: Coordinate conversion?
        _writer.WriteStartElement("a", "ext", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("cx", rect.Width.toString());
        _writer.writeAttributeString("cy", rect.Height.toString());
        _writer.writeEndElement();
        // TODO: Where do we get this from?
        _writer.WriteStartElement("a", "chOff", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("x", "0");
        _writer.writeAttributeString("y", "0");
        _writer.writeEndElement();
        _writer.WriteStartElement("a", "chExt", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("cx", rect.Width.toString());
        _writer.writeAttributeString("cy", rect.Height.toString());
        _writer.writeEndElement();
        _writer.writeEndElement();
    }

}


