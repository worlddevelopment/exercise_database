//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:56 AM
//

package UnitTests;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.IO.FileMode;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.StringSupport;
import CS2JNet.System.TypeSupport;
import CS2JNet.System.Xml.XmlDocument;
import CS2JNet.System.Xml.XmlNode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.AnnotationReferenceDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import java.io.File;
import java.util.Date;
import java.util.Random;

public class DocFileFormatTests   
{
    private Application word2007 = null;
    private CSList<File> files;
    private Object confirmConversions = Class.Missing;
    private Object readOnly = true;
    private Object addToRecentFiles = Class.Missing;
    private Object passwordDocument = Class.Missing;
    private Object passwordTemplate = Class.Missing;
    private Object revert = Class.Missing;
    private Object writePasswordDocument = Class.Missing;
    private Object writePasswordTemplate = Class.Missing;
    private Object format = Class.Missing;
    private Object encoding = Class.Missing;
    private Object visible = Class.Missing;
    private Object openConflictDocument = Class.Missing;
    private Object openAndRepair = Class.Missing;
    private Object documentDirection = Class.Missing;
    private Object noEncodingDialog = Class.Missing;
    private Object saveChanges = false;
    private Object originalFormat = false;
    private Object routeDocument = false;
    public void setUp() throws Exception {
        //read the config
        FileStreamSupport fs = new FileStreamSupport("Config.xml", FileMode.Open);
        XmlDocument config = new XmlDocument();
        config.load(fs);
        fs.close();
        //read the inputfiles
        this.files = new CSList<File>();
        for (Object __dummyForeachVar0 : config.selectNodes("input-files/file"))
        {
            XmlNode fileNode = (XmlNode)__dummyForeachVar0;
            this.files.add(new File(fileNode.getAttributes().get("path").getValue()));
        }
        //start the application
        this.word2007 = new Application();
    }

    public void tearDown() throws Exception {
        RefSupport<Object> refVar___0 = new RefSupport<Object>(saveChanges);
        RefSupport<Object> refVar___1 = new RefSupport<Object>(originalFormat);
        RefSupport<Object> refVar___2 = new RefSupport<Object>(routeDocument);
        this.word2007.Quit(refVar___0, refVar___1, refVar___2);
        saveChanges = refVar___0.getValue();
        originalFormat = refVar___1.getValue();
        routeDocument = refVar___2.getValue();
    }

    /**
    * Tests if the inputfile is parsable
    */
    public void testParseability() throws Exception {
        for (File inputFile : this.files)
        {
            try
            {
                StructuredStorageReader reader = new StructuredStorageReader(inputFile.FullName);
                WordDocument doc = new WordDocument(reader);
                System.out.println("PASSED TestParseability " + inputFile.FullName);
            }
            catch (Exception e)
            {
                throw new AssertionException(e.getMessage() + inputFile.FullName, e);
            }
        
        }
    }

    public void testProperties() throws Exception {
        for (File inputFile : this.files)
        {
            Document omDoc = loadDocument(inputFile.FullName);
            WordDocument dffDoc = new WordDocument(new StructuredStorageReader(inputFile.FullName));
            String dffRevisionNumber = String.valueOf(dffDoc.DocumentProperties.nRevision);
            String omRevisionNumber = (String)getDocumentProperty(omDoc,"Revision number");
            Object omCreationDate = (Date)getDocumentProperty(omDoc,"Creation date");
            Date dffCreationDate = dffDoc.DocumentProperties.dttmCreated.toDateTime();
            Object omLastPrintedDate = getDocumentProperty(omDoc,"Last print date");
            Date dffLastPrintedDate = dffDoc.DocumentProperties.dttmLastPrint.toDateTime();
            RefSupport<Object> refVar___3 = new RefSupport<Object>(saveChanges);
            RefSupport<Object> refVar___4 = new RefSupport<Object>(originalFormat);
            RefSupport<Object> refVar___5 = new RefSupport<Object>(routeDocument);
            omDoc.Close(refVar___3, refVar___4, refVar___5);
            saveChanges = refVar___3.getValue();
            originalFormat = refVar___4.getValue();
            routeDocument = refVar___5.getValue();
            try
            {
                Assert.AreEqual(omRevisionNumber, dffRevisionNumber);
                if (omCreationDate != null && ((Date)omCreationDate).Year != 1601)
                {
                    Assert.AreEqual((Date)omCreationDate, dffCreationDate);
                }
                 
                if (omLastPrintedDate != null && ((Date)omLastPrintedDate).Year != 1601)
                {
                    Assert.AreEqual((Date)omLastPrintedDate, dffLastPrintedDate);
                }
                 
                System.out.println("PASSED TestProperties " + inputFile.FullName);
            }
            catch (AssertionException e)
            {
                throw new AssertionException(e.Message + inputFile.FullName, e);
            }
        
        }
    }

    /**
    * 
    */
    public void testCharacters() throws Exception {
        for (File inputFile : this.files)
        {
            Document omDoc = loadDocument(inputFile.FullName);
            WordDocument dffDoc = new WordDocument(new StructuredStorageReader(inputFile.FullName));
            omDoc.Fields.ToggleShowCodes();
            StringBuilder omText = new StringBuilder();
            char[] omMainText = omDoc.StoryRanges[WdStoryType.wdMainTextStory].Text.ToCharArray();
            for (char c : omMainText)
            {
                if ((int)c > 0x20)
                {
                    omText.append(c);
                }
                 
            }
            StringBuilder dffText = new StringBuilder();
            CSList<Character> dffMainText = dffDoc.Text.GetRange(0, dffDoc.FIB.ccpText);
            for (char c : dffMainText)
            {
                if ((int)c > 0x20)
                {
                    dffText.append(c);
                }
                 
            }
            try
            {
                Assert.AreEqual(omText.toString(), dffText.toString());
                System.out.println("PASSED TestCharacters " + inputFile.FullName);
            }
            catch (AssertionException e)
            {
                throw new AssertionException(e.Message + inputFile.FullName, e);
            }
        
        }
    }

    /**
    * Tests the count of bookmarks in the documents.
    * Also tests the start and the end position a randomly selected bookmark.
    */
    public void testBookmarks() throws Exception {
        for (File inputFile : this.files)
        {
            Document omDoc = loadDocument(inputFile.FullName);
            WordDocument dffDoc = new WordDocument(new StructuredStorageReader(inputFile.FullName));
            omDoc.Bookmarks.ShowHidden = true;
            int omBookmarkCount = omDoc.Bookmarks.Count;
            int dffBookmarkCount = dffDoc.BookmarkNames.Strings.size();
            int omBookmarkStart = 0;
            int dffBookmarkStart = 0;
            int omBookmarkEnd = 0;
            int dffBookmarkEnd = 0;
            if (omBookmarkCount > 0 && dffBookmarkCount > 0)
            {
                //generate a randomly selected bookmark
                Random rand = new Random();
                Object omIndex = rand.Next(0, dffBookmarkCount);
                //get the index's bookmark
                RefSupport<Object> refVar___6 = new RefSupport<Object>(omIndex);
                Bookmark omBookmark = omDoc.Bookmarks.get_Item(refVar___6);
                omIndex = refVar___6.getValue();
                omBookmarkStart = omBookmark.Start;
                omBookmarkEnd = omBookmark.End;
                //get the bookmark with the same name from DFF
                int dffIndex = 0;
                for (int i = 0;i < dffDoc.BookmarkNames.Strings.size();i++)
                {
                    if (StringSupport.equals(dffDoc.BookmarkNames.Strings.get(i), omBookmark.Name))
                    {
                        dffIndex = i;
                        break;
                    }
                     
                }
                dffBookmarkStart = dffDoc.BookmarkStartPlex.CharacterPositions.get(dffIndex);
                dffBookmarkEnd = dffDoc.BookmarkEndPlex.CharacterPositions.get(dffIndex);
            }
             
            RefSupport<Object> refVar___7 = new RefSupport<Object>(saveChanges);
            RefSupport<Object> refVar___8 = new RefSupport<Object>(originalFormat);
            RefSupport<Object> refVar___9 = new RefSupport<Object>(routeDocument);
            omDoc.Close(refVar___7, refVar___8, refVar___9);
            saveChanges = refVar___7.getValue();
            originalFormat = refVar___8.getValue();
            routeDocument = refVar___9.getValue();
            try
            {
                //compare bookmark count
                Assert.AreEqual(omBookmarkCount, dffBookmarkCount);
                //compare bookmark start
                Assert.AreEqual(omBookmarkStart, dffBookmarkStart);
                //compare bookmark end
                Assert.AreEqual(omBookmarkEnd, dffBookmarkEnd);
                System.out.println("PASSED TestBookmarks " + inputFile.FullName);
            }
            catch (AssertionException e)
            {
                throw new AssertionException(e.Message + inputFile.FullName, e);
            }
        
        }
    }

    /**
    * Tests the count of of comments in the documents.
    * Also compares the author of the first comment.
    */
    public void testComments() throws Exception {
        for (File inputFile : this.files)
        {
            Document omDoc = loadDocument(inputFile.FullName);
            WordDocument dffDoc = new WordDocument(new StructuredStorageReader(inputFile.FullName));
            int dffCommentCount = dffDoc.AnnotationsReferencePlex.Elements.size();
            int omCommentCount = omDoc.Comments.Count;
            String omFirstCommentInitial = "";
            String omFirstCommentAuthor = "";
            String dffFirstCommentInitial = "";
            String dffFirstCommentAuthor = "";
            if (dffCommentCount > 0 && omCommentCount > 0)
            {
                Comment omFirstComment = omDoc.Comments[1];
                AnnotationReferenceDescriptor dffFirstComment = (AnnotationReferenceDescriptor)dffDoc.AnnotationsReferencePlex.Elements.get(0);
                omFirstCommentInitial = omFirstComment.Initial;
                omFirstCommentAuthor = omFirstComment.Author;
                dffFirstCommentInitial = dffFirstComment.UserInitials;
                dffFirstCommentAuthor = dffDoc.AnnotationOwners[dffFirstComment.AuthorIndex];
            }
             
            RefSupport<Object> refVar___10 = new RefSupport<Object>(saveChanges);
            RefSupport<Object> refVar___11 = new RefSupport<Object>(originalFormat);
            RefSupport<Object> refVar___12 = new RefSupport<Object>(routeDocument);
            omDoc.Close(refVar___10, refVar___11, refVar___12);
            saveChanges = refVar___10.getValue();
            originalFormat = refVar___11.getValue();
            routeDocument = refVar___12.getValue();
            try
            {
                //compare comment count
                Assert.AreEqual(omCommentCount, dffCommentCount);
                //compare initials
                Assert.AreEqual(omFirstCommentInitial, dffFirstCommentInitial);
                //compate the author names
                Assert.AreEqual(omFirstCommentAuthor, dffFirstCommentAuthor);
                System.out.println("PASSED TestComments " + inputFile.FullName);
            }
            catch (AssertionException e)
            {
                throw new AssertionException(e.Message + inputFile.FullName, e);
            }
        
        }
    }

    private Document loadDocument(Object filename) throws Exception {
        RefSupport<Object> refVar___13 = new RefSupport<Object>(filename);
        RefSupport<Object> refVar___14 = new RefSupport<Object>(confirmConversions);
        RefSupport<Object> refVar___15 = new RefSupport<Object>(readOnly);
        RefSupport<Object> refVar___16 = new RefSupport<Object>(addToRecentFiles);
        RefSupport<Object> refVar___17 = new RefSupport<Object>(passwordDocument);
        RefSupport<Object> refVar___18 = new RefSupport<Object>(passwordTemplate);
        RefSupport<Object> refVar___19 = new RefSupport<Object>(revert);
        RefSupport<Object> refVar___20 = new RefSupport<Object>(writePasswordDocument);
        RefSupport<Object> refVar___21 = new RefSupport<Object>(writePasswordTemplate);
        RefSupport<Object> refVar___22 = new RefSupport<Object>(format);
        RefSupport<Object> refVar___23 = new RefSupport<Object>(encoding);
        RefSupport<Object> refVar___24 = new RefSupport<Object>(visible);
        RefSupport<Object> refVar___25 = new RefSupport<Object>(openConflictDocument);
        RefSupport<Object> refVar___26 = new RefSupport<Object>(openAndRepair);
        RefSupport<Object> refVar___27 = new RefSupport<Object>(documentDirection);
        RefSupport<Object> refVar___28 = new RefSupport<Object>(noEncodingDialog);
        resVar___0 = this.word2007.Documents.Open(refVar___13, refVar___14, refVar___15, refVar___16, refVar___17, refVar___18, refVar___19, refVar___20, refVar___21, refVar___22, refVar___23, refVar___24, refVar___25, refVar___26, refVar___27, refVar___28);
        filename = refVar___13.getValue();
        confirmConversions = refVar___14.getValue();
        readOnly = refVar___15.getValue();
        addToRecentFiles = refVar___16.getValue();
        passwordDocument = refVar___17.getValue();
        passwordTemplate = refVar___18.getValue();
        revert = refVar___19.getValue();
        writePasswordDocument = refVar___20.getValue();
        writePasswordTemplate = refVar___21.getValue();
        format = refVar___22.getValue();
        encoding = refVar___23.getValue();
        visible = refVar___24.getValue();
        openConflictDocument = refVar___25.getValue();
        openAndRepair = refVar___26.getValue();
        documentDirection = refVar___27.getValue();
        noEncodingDialog = refVar___28.getValue();
        return resVar___0;
    }

    private Object getDocumentProperty(Document document, String propertyName) throws Exception {
        Object propertyValue = null;
        try
        {
            Object builtInProperties = document.BuiltInDocumentProperties;
            Class builtInPropertiesType = builtInProperties.getClass();
            Object property = TypeSupport.InvokeMember(builtInPropertiesType, "Item", builtInProperties, new Object[]{ propertyName });
            Class propertyType = property.getClass();
            propertyValue = TypeSupport.InvokeMember(propertyType, "Value", property, new Object[]{  });
        }
        catch (TargetInvocationException __dummyCatchVar0)
        {
        }

        return propertyValue;
    }

}


