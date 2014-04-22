package docx4j_library;

import java.io.File;
import java.util.List;

import org.docx4j.wml.FooterReference;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Text;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;

//http://blog.iprofs.nl/2012/11/19/adding-layout-to-your-docx4j-generated-word-documents-part-2/
public class AddingAFooter {
    private static WordprocessingMLPackage wordMLPackage;
    private static ObjectFactory factory;
 
    /**
     *  First we create the package and the factory. Then we create the footer part,
     *  which returns a relationship. This relationship is then used to create
     *  a reference. Finally we add some text to the document and save it.
     */
    public static void main (String[] args) throws Docx4JException {
        wordMLPackage = WordprocessingMLPackage.createPackage();
        factory = Context.getWmlObjectFactory();
 
        Relationship relationship = createFooterPart();
        createFooterReference(relationship);
 
        wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");
 
        wordMLPackage.save(new File("src/main/files/HelloWord14.docx") );
    }
 
    /**
     *  This method creates a footer part and set the package on it. Then we add some
     *  text and add the footer part to the package. Finally we return the
     *  corresponding relationship.
     *
     *  @return
     *  @throws InvalidFormatException
     */
    private static Relationship createFooterPart() throws InvalidFormatException {
        FooterPart footerPart = new FooterPart();
        footerPart.setPackage(wordMLPackage);
 
        footerPart.setJaxbElement(createFooter("Text"));
 
        return wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);
    }
 
    /**
     *  First we create a footer, a paragraph, a run and a text. We add the given
     *  given content to the text and add that to the run. The run is then added to
     *  the paragraph, which is in turn added to the footer. Finally we return the
     *  footer.
     *
     *  @param content
     *  @return
     */
    private static Ftr createFooter(String content) {
        Ftr footer = factory.createFtr();
        P paragraph = factory.createP();
        R run = factory.createR();
        Text text = new Text();
        text.setValue(content);
        run.getContent().add(text);
        paragraph.getContent().add(run);
        footer.getContent().add(paragraph);
        return footer;
    }
 
    /**
     *  First we retrieve the document sections from the package. As we want to add
     *  a footer, we get the last section and take the section properties from it.
     *  The section is always present, but it might not have properties, so we check
     *  if they exist to see if we should create them. If they need to be created,
     *  we do and add them to the main document part and the section.
     *  Then we create a reference to the footer, give it the id of the relationship,
     *  set the type to header/footer reference and add it to the collection of
     *  references to headers and footers in the section properties.
     *
     * @param relationship
     */
    private static void createFooterReference(Relationship relationship) {
        List<SectionWrapper> sections =
            wordMLPackage.getDocumentModel().getSections();
 
        SectPr sectionProperties = sections.get(sections.size() - 1).getSectPr();
        // There is always a section wrapper, but it might not contain a sectPr
        if (sectionProperties==null ) {
            sectionProperties = factory.createSectPr();
            wordMLPackage.getMainDocumentPart().addObject(sectionProperties);
            sections.get(sections.size() - 1).setSectPr(sectionProperties);
        }
 
        FooterReference footerReference = factory.createFooterReference();
        footerReference.setId(relationship.getId());
        footerReference.setType(HdrFtrRef.DEFAULT);
        sectionProperties.getEGHdrFtrReferences().add(footerReference);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public class AddingPageNrToFooter {
        private static WordprocessingMLPackage wordMLPackage;
        private static ObjectFactory factory;
     
        /**
         *  First we create the package and the factory. Then we create the footer.
         *  Finally we add two pages with text to the document and save it.
         */
        public static void main (String[] args) throws Exception {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
     
            Relationship relationship = createFooterPart();
            createFooterReference(relationship);
     
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
     
            documentPart.addParagraphOfText("Hello World!");
     
            addPageBreak(documentPart);
     
            documentPart.addParagraphOfText("This is page 2!");
            wordMLPackage.save(new File("src/main/files/HelloWord15.docx") );
        }
     
        /**
         *  As in the previous example, this method creates a footer part and adds it to
         *  the main document and then returns the corresponding relationship.
         *
         *  @return
         *  @throws InvalidFormatException
         */
        private static Relationship createFooterPart() throws InvalidFormatException {
            FooterPart footerPart = new FooterPart();
            footerPart.setPackage(wordMLPackage);
     
            footerPart.setJaxbElement(createFooterWithPageNr());
     
            return wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);
        }
     
        /**
         *  As in the previous example, we create a footer and a paragraph object. But
         *  this time, instead of adding text to a run, we add a field. And just as with
         *  the table of content, we have to add a begin and end character around the
         *  actual field with the page number. Finally we add the paragraph to the
         *  content of the footer and then return it.
         *
         * @return
         */
        public static Ftr createFooterWithPageNr() {
            Ftr ftr = factory.createFtr();
            P paragraph = factory.createP();
     
            addFieldBegin(paragraph);
            addPageNumberField(paragraph);
            addFieldEnd(paragraph);
     
            ftr.getContent().add(paragraph);
            return ftr;
        }
     
        /**
         *  Creating the page number field is nearly the same as creating the field in
         *  the TOC example. The only difference is in the value. We use the PAGE
         *  command, which prints the number of the current page, together with the
         *  MERGEFORMAT switch, which indicates that the current formatting should be
         *  preserved when the field is updated.
         *
         * @param paragraph
         */
        private static void addPageNumberField(P paragraph) {
            R run = factory.createR();
            Text txt = new Text();
            txt.setSpace("preserve");
            txt.setValue(" PAGE   \\* MERGEFORMAT ");
            run.getContent().add(factory.createRInstrText(txt));
            paragraph.getContent().add(run);
        }
     
        /**
         * Every fields needs to be delimited by complex field characters. This method
         * adds the delimiter that precedes the actual field to the given paragraph.
         * @param paragraph
         */
        private static void addFieldBegin(P paragraph) {
            R run = factory.createR();
            FldChar fldchar = factory.createFldChar();
            fldchar.setFldCharType(STFldCharType.BEGIN);
            run.getContent().add(fldchar);
            paragraph.getContent().add(run);
        }
     
        /**
         * Every fields needs to be delimited by complex field characters. This method
         * adds the delimiter that follows the actual field to the given paragraph.
         * @param paragraph
         */
        private static void addFieldEnd(P paragraph) {
            FldChar fldcharend = factory.createFldChar();
            fldcharend.setFldCharType(STFldCharType.END);
            R run3 = factory.createR();
            run3.getContent().add(fldcharend);
            paragraph.getContent().add(run3);
        }
     
        /**
         * This method fetches the document final section properties, and adds a newly
         * created footer reference to them.
         *
         * @param relationship
         */
        public static void createFooterReference(Relationship relationship){
     
            List<SectionWrapper> sections =
                wordMLPackage.getDocumentModel().getSections();
     
            SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
            // There is always a section wrapper, but it might not contain a sectPr
            if (sectPr==null ) {
                sectPr = factory.createSectPr();
                wordMLPackage.getMainDocumentPart().addObject(sectPr);
                sections.get(sections.size() - 1).setSectPr(sectPr);
            }
     
            FooterReference footerReference = factory.createFooterReference();
            footerReference.setId(relationship.getId());
            footerReference.setType(HdrFtrRef.DEFAULT);
            sectPr.getEGHdrFtrReferences().add(footerReference);
        }
     
        /**
         * Adds a page break to the document.
         *
         * @param documentPart
         */
        private static void addPageBreak(MainDocumentPart documentPart) {
            Br breakObj = new Br();
            breakObj.setType(STBrType.PAGE);
     
            P paragraph = factory.createP();
            paragraph.getContent().add(breakObj);
            documentPart.getJaxbElement().getBody().getContent().add(paragraph);
        }
    }
    
    
}