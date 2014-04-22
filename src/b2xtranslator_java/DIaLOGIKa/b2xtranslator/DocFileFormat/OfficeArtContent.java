//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:04 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DrawingContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.DrawingGroup;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.GroupContainer;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shape;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeContainer;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class OfficeArtContent   
{
    public enum DrawingType
    {
        MainDocument,
        Header
    }
    public static class OfficeArtWordDrawing   
    {
        public OfficeArtWordDrawing() {
        }

        public DrawingType dgglbl = DrawingType.MainDocument;
        public DrawingContainer container;
    }

    public DrawingGroup DrawingGroupData;
    public CSList<OfficeArtWordDrawing> Drawings;
    public OfficeArtContent(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
        VirtualStreamReader reader = new VirtualStreamReader(tableStream);
        tableStream.Seek(fib.fcDggInfo, System.IO.SeekOrigin.Begin);
        if (fib.lcbDggInfo > 0)
        {
            int maxPosition = (int)(fib.fcDggInfo + fib.lcbDggInfo);
            //read the DrawingGroupData
            this.DrawingGroupData = (DrawingGroup)DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(reader);
            //read the Drawings
            this.Drawings = new CSList<OfficeArtWordDrawing>();
            while (Unsupported.throwUnsupported("reader.getBaseStream().Position") < maxPosition)
            {
                OfficeArtWordDrawing drawing = new OfficeArtWordDrawing();
                drawing.dgglbl = (DrawingType)reader.readByte();
                drawing.container = (DrawingContainer)DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(reader);
                for (int i = 0;i < drawing.container.Children.size();i++)
                {
                    DIaLOGIKa.b2xtranslator.OfficeDrawing.Record groupChild = drawing.container.Children.get(i);
                    if (groupChild.TypeCode == 0xF003)
                    {
                        // the child is a subgroup
                        GroupContainer group = (GroupContainer)drawing.container.Children.get(i);
                        group.Index = i;
                        drawing.container.Children.add(i, group);
                    }
                    else if (groupChild.TypeCode == 0xF004)
                    {
                        // the child is a shape
                        ShapeContainer shape = (ShapeContainer)drawing.container.Children.get(i);
                        shape.Index = i;
                        drawing.container.Children.add(i, shape);
                    }
                      
                }
                this.Drawings.add(drawing);
            }
        }
         
    }

    /**
    * Searches the matching shape
    * 
    *  @param spid The shape ID
    *  @return The ShapeContainer
    */
    public ShapeContainer getShapeContainer(int spid) throws Exception {
        ShapeContainer ret = null;
        for (OfficeArtWordDrawing drawing : this.Drawings)
        {
            GroupContainer group = (GroupContainer)drawing.container.firstChildWithType();
            if (group != null)
            {
                for (int i = 1;i < group.Children.size();i++)
                {
                    DIaLOGIKa.b2xtranslator.OfficeDrawing.Record groupChild = group.Children.get(i);
                    if (groupChild.TypeCode == 0xF003)
                    {
                        //It's a group of shapes
                        GroupContainer subgroup = (GroupContainer)groupChild;
                        //the referenced shape must be the first shape in the group
                        ShapeContainer container = (ShapeContainer)subgroup.Children.get(0);
                        Shape shape = (Shape)container.Children.get(1);
                        if (shape.spid == spid)
                        {
                            ret = container;
                            break;
                        }
                         
                    }
                    else if (groupChild.TypeCode == 0xF004)
                    {
                        //It's a singe shape
                        ShapeContainer container = (ShapeContainer)groupChild;
                        Shape shape = (Shape)container.Children.get(0);
                        if (shape.spid == spid)
                        {
                            ret = container;
                            break;
                        }
                         
                    }
                      
                }
            }
            else
            {
                continue;
            } 
            if (ret != null)
            {
                break;
            }
             
        }
        return ret;
    }

}


