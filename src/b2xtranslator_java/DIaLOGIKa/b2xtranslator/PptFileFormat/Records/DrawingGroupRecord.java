//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.OfficeRecordAttribute;

public class DrawingGroupRecord  extends DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record 
{
    public static class FileIdCluster   
    {
        public long DrawingGroupId;
        public long CSpIdCur;
        public FileIdCluster(BinaryReader reader) throws Exception {
            this.DrawingGroupId = reader.ReadUInt32();
            this.CSpIdCur = reader.ReadUInt32();
        }

        public String toString(uint depth) throws Exception {
            StringBuilder result = new StringBuilder();
            result.append(IndentationForDepth(depth));
            result.append(String.format(StringSupport.CSFmtStrToJFmtStr("FileIdCluster: DrawingGroupId = {0}, CSpIdCur = {1}"),this.DrawingGroupId,this.CSpIdCur));
            return result.toString();
        }
    
    }

    public long MaxShapeId;
    // Maximum shape ID
    public long IdClustersCount;
    // Number of FileIdClusters
    public long ShapesSavedCount;
    // Total number of shapes saved
    public long DrawingsSavedCount;
    // Total number of drawings saved
    public CSList<FileIdCluster> Clusters = new CSList<FileIdCluster>();
    public DrawingGroupRecord(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.MaxShapeId = this.Reader.ReadUInt32();
        this.IdClustersCount = this.Reader.ReadUInt32() - 1;
        // Office saves the actual value + 1 -- flgr
        this.ShapesSavedCount = this.Reader.ReadUInt32();
        this.DrawingsSavedCount = this.Reader.ReadUInt32();
        for (int i = 0;i < this.IdClustersCount;i++)
        {
            Clusters.add(new FileIdCluster(this.Reader));
        }
    }

    public String toString(uint depth) throws Exception {
        StringBuilder result = new StringBuilder();
        result.append((super.toString(depth)) + System.getProperty("line.separator"));
        result.append(indentationForDepth(depth + 1));
        result.append(String.format(StringSupport.CSFmtStrToJFmtStr("MaxShapeId = {0}, IdClustersCount = {1}"),this.MaxShapeId,this.IdClustersCount));
        result.AppendLine();
        result.append(indentationForDepth(depth + 1));
        result.append(String.format(StringSupport.CSFmtStrToJFmtStr("ShapesSavedCount = {0}, DrawingsSavedCount = {1}"),this.ShapesSavedCount,this.DrawingsSavedCount));
        depth++;
        if (this.Clusters.size() > 0)
        {
            result.AppendLine();
            result.append(indentationForDepth(depth));
            result.append("Clusters:");
        }
         
        for (FileIdCluster cluster : this.Clusters)
        {
            result.AppendLine();
            result.append(cluster.toString(depth + 1));
        }
        return result.toString();
    }

}


