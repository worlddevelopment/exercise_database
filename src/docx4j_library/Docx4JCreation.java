package docx4j_library;


//http://blog.iprofs.nl/2012/09/06/creating-word-documents-with-docx4j/
public class Docx4JCreation {

	public static addParagraphOfText() {
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
		wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");
		wordMLPackage.save(new java.io.File("src/main/files/HelloWord1.docx"));
	
	
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Hello Word!");
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",
		"This is a subtitle!");
		wordMLPackage.save(new java.io.File("src/main/files/HelloWord2.docx"));
		
		
	}
	

	public static class AddingATable {
	    private static WordprocessingMLPackage  wordMLPackage;
	    private static ObjectFactory factory;
	 
	    public static void main (String[] args) throws Docx4JException {
	    wordMLPackage = WordprocessingMLPackage.createPackage();
	    factory = Context.getWmlObjectFactory();
	 
	    Tbl table = factory.createTbl();
	    Tr tableRow = factory.createTr();
	 
	    addTableCell(tableRow, "Field 1");
	    addTableCell(tableRow, "Field 2");
	 
	    table.getContent().add(tableRow);
	 
	    wordMLPackage.getMainDocumentPart().addObject(table);
	 
	    wordMLPackage.save(new java.io.File("src/main/files/HelloWord4.docx"));
	    }
	 
	    private static void addTableCell(Tr tableRow, String content) {
	    Tc tableCell = factory.createTc();
	    tableCell.getContent().add(
	        wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
	    tableRow.getContent().add(tableCell);
	    }
	}
	
	
	
	
	
	
	//TABLE WITH BORDERS
	public class TableWithBorders {
	
		public static void main (String[] args) throws Docx4JException {
	        wordMLPackage = WordprocessingMLPackage.createPackage();
	        factory = Context.getWmlObjectFactory();
	 
	        Tbl table = createTableWithContent();
	 
	        addBorders(table);
	 
	        wordMLPackage.getMainDocumentPart().addObject(table);
	        wordMLPackage.save(new java.io.File(
	            "src/main/files/HelloWord5.docx") );
	    }
	 
	    private static void addBorders(Tbl table) {
	        table.setTblPr(new TblPr());
	        CTBorder border = new CTBorder();
	        border.setColor("auto");
	        tborder.setSz(new BigInteger("4"));
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
	 
	    private static Tbl createTableWithContent() {
	        Tbl table = factory.createTbl();
	        Tr tableRow = factory.createTr();
	 
	        addTableCell(tableRow, "Field 1");
	        addTableCell(tableRow, "Field 2");
	 
	        table.getContent().add(tableRow);
	        return table;
	    }
	 
	    private static void addTableCell(Tr tableRow, String content) {
	        Tc tableCell = factory.createTc();
	        tableCell.getContent().add(
	        wordMLPackage.getMainDocumentPart().
	            createParagraphOfText(content));
	        tableRow.getContent().add(tableCell);
	    }
	
	}
	
    public class TableWithStyledContent {
        private static WordprocessingMLPackage  wordMLPackage;
        private static ObjectFactory factory;
     
        /**
         *  Once again, we create a table. We add three cells to it. One in the same
         *  way we did before, and two cells with styling. To this new method we pass
         *  on the table row, the content of the cell, whether it is bold or not,
         *  and the font size. Mind you, the font size has to be twice as big as you
         *  would want it to show up in Word, as the Office Open specification has
         *  defined this property to be in half-point size.
         */
        public static void main (String[] args) throws Docx4JException {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
     
            Tbl table = factory.createTbl();
            Tr tableRow = factory.createTr();
     
            addRegularTableCell(tableRow, "Normal text");
            addStyledTableCell(tableRow, "Bold text", true, null);
            addStyledTableCell(tableRow, "Bold large text", true, "40");
     
            table.getContent().add(tableRow);
            addBorders(table);
     
            wordMLPackage.getMainDocumentPart().addObject(table);
            wordMLPackage.save(new java.io.File(
                "src/main/files/HelloWord6.docx") );
        }
     
        /**
         *  In this method we create a table cell,add the styling and add the cell to
         *  the table row.
         */
        private static void addStyledTableCell(Tr tableRow, String content,
                            boolean bold, String fontSize) {
            Tc tableCell = factory.createTc();
            addStyling(tableCell, content, bold, fontSize);
            tableRow.getContent().add(tableCell);
        }
     
        /**
         *  This is where we add the actual styling information. In order to do this
         *  we first create a paragraph. Then we create a text with the content of
         *  the cell as the value. Thirdly, we create a so-called run, which is a
         *  container for one or more pieces of text having the same set of
         *  properties, and add the text to it. We then add the run to the content
         *  of the paragraph.
         *  So far what we've done still doesn't add any styling. To accomplish that,
         *  we'll create run properties and add the styling to it. These run
         *  properties are then added to the run. Finally the paragraph is added
         *  to the content of the table cell.
         */
        private static void addStyling(Tc tableCell, String content,
                        boolean bold, String fontSize) {
            P paragraph = factory.createP();
     
            Text text = factory.createText();
            text.setValue(content);
     
            R run = factory.createR();
            run.getContent().add(text);
     
            paragraph.getContent().add(run);
     
            RPr runProperties = factory.createRPr();
            if (bold) {
                addBoldStyle(runProperties);
            }
     
            if (fontSize != null && !fontSize.isEmpty()) {
                setFontSize(runProperties, fontSize);
            }
     
            run.setRPr(runProperties);
     
            tableCell.getContent().add(paragraph);
        }
     
        /**
         *  In this method we're going to add the font size information to the run
         *  properties. First we'll create a half-point measurement. Then we'll
         *  set the fontSize as the value of this measurement. Finally we'll set
         *  the non-complex and complex script font sizes, sz and szCs respectively.
         */
        private static void setFontSize(RPr runProperties, String fontSize) {
            HpsMeasure size = new HpsMeasure();
            size.setVal(new BigInteger(fontSize));
            runProperties.setSz(size);
            runProperties.setSzCs(size);
        }
     
        /**
         *  In this method we'll add the bold property to the run properties.
         *  BooleanDefaultTrue is the Docx4j object for the b property.
         *  Technically we wouldn't have to set the value to true, as this is
         *  the default.
         */
        private static void addBoldStyle(RPr runProperties) {
            BooleanDefaultTrue b = new BooleanDefaultTrue();
            b.setVal(true);
            runProperties.setB(b);
        }
     
        /**
         *  In this method, we once again create a regular cell, as in the previous
         *  examples.
         */
        private static void addRegularTableCell(Tr tableRow, String content) {
            Tc tableCell = factory.createTc();
            tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(
                    content));
            tableRow.getContent().add(tableCell);
        }
     
        /**
         *  In this method we'll add the borders to the table.
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
    
	    
    
    //VERTICALLY MANAGING TABLES
    public class TableWithMergedCells {
        private static WordprocessingMLPackage  wordMLPackage;
        private static ObjectFactory factory;
     
        /**
         *  We create a table with borders and add four rows with content to it,
         *  and then we add the table to the document and save it.
         */
        public static void main (String[] args) throws Docx4JException {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
     
            Tbl table = factory.createTbl();
            addBorders(table);
     
            addTableRowWithMergedCells("Heading 1", "Heading 1.1",
                "Field 1", table);
            addTableRowWithMergedCells(null, "Heading 1.2", "Field 2", table);
     
            addTableRowWithMergedCells("Heading 2", "Heading 2.1",
                "Field 3", table);
            addTableRowWithMergedCells(null, "Heading 2.2", "Field 4", table);
     
            wordMLPackage.getMainDocumentPart().addObject(table);
            wordMLPackage.save(new java.io.File(
                "src/main/files/HelloWord9.docx") );
        }
     
        /**
         *  In this method we create a row, add the merged column to it, and then
         *  add two regular cells to it. Then we add the row to the table.
         */
        private static void addTableRowWithMergedCells(String mergedContent,
                String field1Content, String field2Content, Tbl table) {
            Tr tableRow1 = factory.createTr();
     
            addMergedColumn(tableRow1, mergedContent);
     
            addTableCell(tableRow1, field1Content);
            addTableCell(tableRow1, field2Content);
     
            table.getContent().add(tableRow1);
        }
     
        /**
         *  In this method we add a column cell that is merged with cells in other
         *  rows. If the given content is null, we pass on empty content and a merge
         *  value of null.
         */
        private static void addMergedColumn(Tr row, String content) {
            if (content == null) {
                addMergedCell(row, "", null);
            } else {
                addMergedCell(row, content, "restart");
            }
        }
     
        /**
         *  We create a table cell and then a table cell properties object.
         *  We also create a vertical merge object. If the merge value is not null,
         *  we set it on the object. Then we add the merge object to the table cell
         *  properties and add the properties to the table cell. Finally we set the
         *  content in the table cell and add the cell to the row.
         *
         *  If the merge value is 'restart', a new row is started. If it is null, we
         *  continue with the previous row, thus merging the cells.
         */
        private static void addMergedCell(Tr row, String content, String vMergeVal) {
            Tc tableCell = factory.createTc();
            TcPr tableCellProperties = new TcPr();
     
            VMerge merge = new VMerge();
            if(vMergeVal != null){
                merge.setVal(vMergeVal);
            }
            tableCellProperties.setVMerge(merge);
     
            tableCell.setTcPr(tableCellProperties);
            if(content != null) {
                    tableCell.getContent().add(
                    wordMLPackage.getMainDocumentPart().
                        createParagraphOfText(content));
            }
     
            row.getContent().add(tableCell);
        }
     
        /**
         * In this method we add a table cell to the given row with the given
         *  paragraph as content.
         */
        private static void addTableCell(Tr tr, String content) {
            Tc tc1 = factory.createTc();
            tc1.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(
                    content));
            tr.getContent().add(tc1);
        }
     
        /**
         *  In this method we'll add the borders to the table.
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
    
    
    
    //SETTING A CERTAIN COLUMN WIDTH
    public class SettingColumnWidthForTable {
        private static WordprocessingMLPackage  wordMLPackage;
        private static ObjectFactory factory;
     
        /**
         *  We create a table with borders and then add a row to it. Then we add
         *  two cells with content and the given width to it.
         */
        public static void main (String[] args) throws Docx4JException {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
     
            Tbl table = factory.createTbl();
            addBorders(table);
     
            Tr tr = factory.createTr();
     
            addTableCellWithWidth(tr, "Field 1", 2500);
            addTableCellWithWidth(tr, "Field 2", 0);
     
            table.getContent().add(tr);
     
            wordMLPackage.getMainDocumentPart().addObject(table);
            wordMLPackage.save(new java.io.File(
                "src/main/files/HelloWord13.docx") );
        }
     
        /**
         *  In this method we create a cell and add the given content to it.
         *  If the given width is greater than 0, we set the width on the cell.
         *  Finally, we add the cell to the row.
         */
        private static void addTableCellWithWidth(Tr row, String content, int width){
            Tc tableCell = factory.createTc();
            tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(
                    content));
     
            if (width > 0) {
                setCellWidth(tableCell, width);
            }
            row.getContent().add(tableCell);
        }
     
        /**
         *  In this method we create a table cell properties object and a table width
         *  object. We set the given width on the width object and then add it to
         *  the properties object. Finally we set the properties on the table cell.
         */
        private static void setCellWidth(Tc tableCell, int width) {
            TcPr tableCellProperties = new TcPr();
            TblWidth tableWidth = new TblWidth();
            tableWidth.setW(BigInteger.valueOf(width));
            tableCellProperties.setTcW(tableWidth);
            tableCell.setTcPr(tableCellProperties);
        }
     
        /**
         *  In this method we'll add the borders to the table.
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
	    
}
