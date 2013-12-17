
package docx4j_library;


//http://blog.iprofs.nl/2012/10/22/adding-images-and-layout-to-your-docx4j-generated-word-documents-part-1/





public class AddingAnInlineImage {
    /**
     *  As is usual, we create a package to contain the document.
     *  Then we create a file that contains the image we want to add to the document.
     *  In order to be able to do something with this image, we'll have to convert
     *  it to an array of bytes. Finally we add the image to the package
     *  and save the package.
     */
    public static void main (String[] args) throws Exception {
        WordprocessingMLPackage  wordMLPackage =
            WordprocessingMLPackage.createPackage();
 
        File file = new File("src/main/resources/iProfsLogo.png");
        byte[] bytes = convertImageToByteArray(file);
        addImageToPackage(wordMLPackage, bytes);
 
        wordMLPackage.save(new java.io.File("src/main/files/HelloWord7.docx"));
    }
 
    /**
     *  Docx4j contains a utility method to create an image part from an array of
     *  bytes and then adds it to the given package. In order to be able to add this
     *  image to a paragraph, we have to convert it into an inline object. For this
     *  there is also a method, which takes a filename hint, an alt-text, two ids
     *  and an indication on whether it should be embedded or linked to.
     *  One id is for the drawing object non-visual properties of the document, and
     *  the second id is for the non visual drawing properties of the picture itself.
     *  Finally we add this inline object to the paragraph and the paragraph to the
     *  main document of the package.
     *
     *  @param wordMLPackage The package we want to add the image to
     *  @param bytes         The bytes of the image
     *  @throws Exception    Sadly the createImageInline method throws an Exception
     *                       (and not a more specific exception type)
     */
    private static void addImageToPackage(WordprocessingMLPackage wordMLPackage,
                            byte[] bytes) throws Exception {
        BinaryPartAbstractImage imagePart =
            BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
 
        int docPrId = 1;
        int cNvPrId = 2;
            Inline inline = imagePart.createImageInline("Filename hint",
                "Alternative text", docPrId, cNvPrId, false);
 
        P paragraph = addInlineImageToParagraph(inline);
 
        wordMLPackage.getMainDocumentPart().addObject(paragraph);
    }
 
    /**
     *  We create an object factory and use it to create a paragraph and a run.
     *  Then we add the run to the paragraph. Next we create a drawing and
     *  add it to the run. Finally we add the inline object to the drawing and
     *  return the paragraph.
     *
     * @param   inline The inline object containing the image.
     * @return  the paragraph containing the image
     */
    private static P addInlineImageToParagraph(Inline inline) {
        // Now add the in-line image to a paragraph
        ObjectFactory factory = new ObjectFactory();
        P paragraph = factory.createP();
        R run = factory.createR();
        paragraph.getContent().add(run);
        Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return paragraph;
    }
 
    /**
     * Convert the image from the file into an array of bytes.
     *
     * @param file  the image file to be converted
     * @return      the byte array containing the bytes from the image
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static byte[] convertImageToByteArray(File file)
            throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(file );
        long length = file.length();
        // You cannot create an array using a long, it needs to be an int.
        if (length > Integer.MAX_VALUE) {
            System.out.println("File too large!!");
        }
        byte[] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read
        if (offset < bytes.length) {
            System.out.println("Could not completely read file "
                        +file.getName());
        }
        is.close();
        return bytes;
    }
    
    
    
    
    public class AddingAnInlineImageToTable {
        private static WordprocessingMLPackage  wordMLPackage;
        private static ObjectFactory factory;
     
        /**
         *  First we create the package and the object factory, so we can use them
         *  everywhere in the class. Then we create a table and add borders to it.
         *  Next we create a table row and add a first field with some text.
         *  For the second field, we use the same image file as before and create a
         *  paragraph with an image, that we add to it. Finally we add the row to the
         *  table, and the table to the package, and save the package.
         */
        public static void main (String[] args) throws Exception {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
     
            Tbl table = factory.createTbl();
            addBorders(table);
     
            Tr tr = factory.createTr();
     
            P paragraphOfText = wordMLPackage.getMainDocumentPart()
                            .createParagraphOfText("Field 1");
            addTableCell(tr, paragraphOfText);
     
            File file = new File("src/main/resources/iProfsLogo.png");
            P paragraphWithImage = addInlineImageToParagraph(createInlineImage(file));
            addTableCell(tr, paragraphWithImage);
     
            table.getContent().add(tr);
     
            wordMLPackage.getMainDocumentPart().addObject(table);
            wordMLPackage.save(new java.io.File("src/main/files/HelloWord8.docx"));
        }
     
        /**
         * Adds a table cell to the given row with the given paragraph as content.
         *
         * @param tr
         * @param paragraph
         */
        private static void addTableCell(Tr tr, P paragraph) {
            Tc tc1 = factory.createTc();
            tc1.getContent().add(paragraph);
            tr.getContent().add(tc1);
        }
     
        /**
         *  Adds the in-line image to a new paragraph and then returns the paragraph.
         *  Thism method has not changed from the previous example.
         *
         * @param inline
         * @return
         */
        private static P addInlineImageToParagraph(Inline inline) {
            // Now add the in-line image to a paragraph
            ObjectFactory factory = new ObjectFactory();
            P paragraph = factory.createP();
            R run = factory.createR();
            paragraph.getContent().add(run);
            Drawing drawing = factory.createDrawing();
            run.getContent().add(drawing);
            drawing.getAnchorOrInline().add(inline);
            return paragraph;
        }
     
        /**
         * Creates an in-line image of the given file.
         * As in the previous example, we convert the file to a byte array, and then
         * create an inline image object of it.
         *
         * @param file
         * @return
         * @throws Exception
         */
        private static Inline createInlineImage(File file) throws Exception {
            byte[] bytes = convertImageToByteArray(file);
     
            BinaryPartAbstractImage imagePart =
                BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
     
            int docPrId = 1;
            int cNvPrId = 2;
     
            return imagePart.createImageInline("Filename hint",
                    "Alternative text", docPrId, cNvPrId, false);
        }
     
        /**
         * Convert the image from the file into an array of bytes.
         *
         * @param file
         * @return
         * @throws FileNotFoundException
         * @throws IOException
         */
        private static byte[] convertImageToByteArray(File file)
                throws FileNotFoundException, IOException {
            InputStream is = new FileInputStream(file );
            long length = file.length();
            // You cannot create an array using a long, it needs to be an int.
            if (length > Integer.MAX_VALUE) {
                System.out.println("File too large!!");
            }
            byte[] bytes = new byte[(int)length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
            // Ensure all the bytes have been read
            if (offset < bytes.length) {
                System.out.println("Could not completely read file "+file.getName());
            }
            is.close();
            return bytes;
        }
     
        /**
         * Adds simple black borders to the table
         *
         * @param table
         */
        private static void addBorders(Tbl table) {
            table.setTblPr(new TblPr());
            CTBorder border = new CTBorder();
            border.setColor("auto");
            border.setSz(new BigInteger("4"));
            border.setSpace(new BigInteger("0"));
            border.setVal(STBorder.SINGLE);
     
            TblBorders borders = new TblBorders();
            borders.setBottom(border);
            borders.setLeft(border);
            borders.setRight(border);
            borders.setTop(border);
            borders.setInsideH(border);
            borders.setInsideV(border);
            table.getTblPr().setTblBorders(borders);
        }
    }
    
    
    
    public class AddingAPageBreak {
        private static ObjectFactory factory;
        private static WordprocessingMLPackage  wordMLPackage;
     
        public static void main (String[] args) throws Docx4JException {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
     
            wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");
     
            addPageBreak();
     
            wordMLPackage.getMainDocumentPart().addParagraphOfText("This is page 2!");
            wordMLPackage.save(new java.io.File("src/main/files/HelloWord11.docx") );
        }
     
        /**
         * Adds a page break to the document.
         */
        private static void addPageBreak() {
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
     
            Br breakObj = new Br();
            breakObj.setType(STBrType.PAGE);
     
            P paragraph = factory.createP();
            paragraph.getContent().add(breakObj);
            documentPart.getJaxbElement().getBody().getContent().add(paragraph);
        }
    }
    
    
    
    //YES IF QUESTIONED "WANT TO UPDATE THESE FIELDS?" OR BEST TRIAL AND ERROR AS THERE ARE ONLY 2 POSSIBILIES
    
    public class AddingTableOfContent {
        private static ObjectFactory factory;
     
        /**
         *  First we create the factory and the package and extract the document part
         *  from the package. Then we add the table of content, followed by some
         *  paragraphs with assorted heading styles. Finally we save the package.
         */
        public static void main(String[] args) throws Docx4JException {
            factory = Context.getWmlObjectFactory();
            WordprocessingMLPackage wordMLPackage =
                WordprocessingMLPackage.createPackage();
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
     
            addTableOfContent(documentPart);
     
            documentPart.addStyledParagraphOfText("Heading1", "Hello 1");
            documentPart.addStyledParagraphOfText("Heading2", "Hello 2");
            documentPart.addStyledParagraphOfText("Heading3", "Hello 3");
            documentPart.addStyledParagraphOfText("Heading1", "Hello 1");
     
            wordMLPackage.save(new File("src/main/files/HelloWord10.docx"));
        }
     
        /**
         *  Adds the table of content to the document.
         *
         *  First we create a paragraph. Then we add the indicator to mark the start of
         *  the field. Then we add the content of the field (with the actual table of
         *  content), followed by the indicator to mark the end of the field. Finally
         *  we add the paragraph to the JAXB elements of the given document part.
         *
         *  @param documentPart
         */
        private static void addTableOfContent(MainDocumentPart documentPart) {
            P paragraph = factory.createP();
     
            addFieldBegin(paragraph);
            addTableOfContentField(paragraph);
            addFieldEnd(paragraph);
     
            documentPart.getJaxbElement().getBody().getContent().add(paragraph);
        }
     
        /**
         *  Adds the field that Word uses to create a table of content to the paragraph.
         *
         *  First we create a run and a text. Then we indicate that all spaces in the
         *  text are to be preserved and set the value to that of the TOC field.
         *  This field definition takes some arguments. The exact definition can be
         *  found in §17.16.5.58 of the Office Open XML standard. In this case we
         *  specify that we want to include all paragrapsh formatted with headings of
         *  levels 1-3 (\0 “1-3”). We also specify that we want all entries to be
         *  hyperlinks (\h), that we want to hide tab leader and page numbers in Web
         *  layout view (\z), and that we want to use the applied paragraph outline
         *  level (\u).
         *  Finally we take the text and use it to create a JAXB element containing text
         *  and add this to the run, which we then add to the given paragraph.
         *
         *  @param paragraph
         */
        private static void addTableOfContentField(P paragraph) {
            R run = factory.createR();
            Text txt = new Text();
            txt.setSpace("preserve");
            txt.setValue("TOC \\o \"1-3\" \\h \\z \\u");
            run.getContent().add(factory.createRInstrText(txt));
            paragraph.getContent().add(run);
        }
     
        /**
         *  Every fields needs to be delimited by complex field characters. This method
         *  adds the delimiter that precedes the actual field to the given paragraph.
         *
         *  Once again, we start by creating a run. Then we create a field character to
         *  mark the beginning of the field and mark the field as dirty as we want the
         *  content to be updated after the entire document is generated.
         *  Finally we convert the field character to a JAXB element and add it to
         *  the run, and add the run to the paragraph.
         *
         *  @param paragraph
         */
        private static void addFieldBegin(P paragraph) {
            R run = factory.createR();
            FldChar fldchar = factory.createFldChar();
            fldchar.setFldCharType(STFldCharType.BEGIN);
            fldchar.setDirty(true);
            run.getContent().add(getWrappedFldChar(fldchar));
            paragraph.getContent().add(run);
        }
     
        /**
         *  Every fields needs to be delimited by complex field characters. This method
         *  adds the delimiter that follows the actual field to the given paragraph.
         *
         *  As before, we start by creating a run. Then we create a field character to
         *  mark the end of the field. Finally we convert the field character to a
         *  JAXB element and add it to the run, and add the run to the paragraph.
         *
         *  @param paragraph
         */
        private static void addFieldEnd(P paragraph) {
            R run = factory.createR();
            FldChar fldcharend = factory.createFldChar();
            fldcharend.setFldCharType(STFldCharType.END);
            run.getContent().add(getWrappedFldChar(fldcharend));
            paragraph.getContent().add(run);
        }
     
        /**
         *  Convenience method that creates the JAXBElement to contain the given complex
         *  field character.
         *
         *  @param fldchar
         *  @return
         */
        public static JAXBElement getWrappedFldChar(FldChar fldchar) {
            return new JAXBElement(new QName(Namespaces.NS_WORD12, "fldChar"),
                                 FldChar.class, fldchar);
        }
    }