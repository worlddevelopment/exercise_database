//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:11 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import DIaLOGIKa.b2xtranslator.DocFileFormat.AnnotationReferenceDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.AnnotationReferenceDescriptorExtra;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DateMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DocumentMapping;

public class CommentsMapping  extends DocumentMapping 
{
    public CommentsMapping(ConversionContext ctx) throws Exception {
        super(ctx, ctx.getDocx().getMainDocumentPart().getCommentsPart());
        _ctx = ctx;
    }

    public void apply(WordDocument doc) throws Exception {
        _doc = doc;
        int index = 0;
        _writer.WriteStartElement("w", "comments", OpenXmlNamespaces.WordprocessingML);
        int cp = doc.FIB.ccpText + doc.FIB.ccpFtn + doc.FIB.ccpHdr;
        for (int i = 0;i < doc.AnnotationsReferencePlex.Elements.size();i++)
        {
            _writer.WriteStartElement("w", "comment", OpenXmlNamespaces.WordprocessingML);
            AnnotationReferenceDescriptor atrdPre10 = (AnnotationReferenceDescriptor)doc.AnnotationsReferencePlex.Elements.get(index);
            _writer.WriteAttributeString("w", "id", OpenXmlNamespaces.WordprocessingML, String.valueOf(index));
            _writer.WriteAttributeString("w", "author", OpenXmlNamespaces.WordprocessingML, doc.AnnotationOwners[atrdPre10.AuthorIndex]);
            _writer.WriteAttributeString("w", "initials", OpenXmlNamespaces.WordprocessingML, atrdPre10.UserInitials);
            //ATRDpost10 is optional and not saved in all files
            if (doc.AnnotationReferenceExtraTable != null && doc.AnnotationReferenceExtraTable.size() > index)
            {
                AnnotationReferenceDescriptorExtra atrdPost10 = doc.AnnotationReferenceExtraTable.get(index);
                atrdPost10.Date.Convert(new DateMapping(_writer));
            }
             
            cp = writeParagraph(cp);
            _writer.writeEndElement();
            index++;
        }
        _writer.writeEndElement();
        _writer.Flush();
    }

}


