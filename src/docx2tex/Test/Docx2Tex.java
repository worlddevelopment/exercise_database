//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:00 AM
//

package docx2tex.Test;

import CS2JNet.JavaSupport.io.FilterOnlyFiles;
import CS2JNet.System.IO.DirectorySupport;
import java.io.File;

/**
* Summary description for Docx2Tex
*/
public class Docx2Tex   
{
    public Docx2Tex() throws Exception {
    }

    //
    // TODO: Add constructor logic here
    //
    private TestContext testContextInstance = new TestContext();
    /**
    * Gets or sets the test context which provides
    * information about and functionality for the current test run.
    */
    public TestContext getTestContext() throws Exception {
        return testContextInstance;
    }

    public void setTestContext(TestContext value) throws Exception {
        testContextInstance = value;
    }

    //
    // You can use the following additional attributes as you write your tests:
    //
    // Use ClassInitialize to run code before running the first test in the class
    // [ClassInitialize()]
    // public static void MyClassInitialize(TestContext testContext) { }
    //
    // Use ClassCleanup to run code after all tests in a class have run
    // [ClassCleanup()]
    // public static void MyClassCleanup() { }
    //
    // Use TestInitialize to run code before running each test
    // [TestInitialize()]
    // public void MyTestInitialize() { }
    //
    // Use TestCleanup to run code after each test has run
    // [TestCleanup()]
    // public void MyTestCleanup() { }
    //
    public void preparedDocTest() throws Exception {
        do("test","example");
    }

    public void preparedCSCSTest() throws Exception {
        do("testcscs","regression-test-final2");
    }

    public void preparedTUGTest() throws Exception {
        do("testtug","docx2tex");
    }

    public void preparedMACSTest() throws Exception {
        do("testmacs","macs_secdistr_draft");
    }

    public void preparedEquationsest() throws Exception {
        do("testeq","eqtest");
    }

    private void do(String dir, String fileName) throws Exception {
        String solutionDir = (new File(testContextInstance.TestDir)).Parent.Parent.FullName;
        String prog = (new File(solutionDir, "out\\docx2tex.exe")).toString();
        String testProjDir = (new File(solutionDir, "docx2tex.Test")).toString();
        String baseInputDir = (new File(testProjDir, "Input")).toString();
        String baseOutputDir = (new File(testProjDir, "Output")).toString();
        String baseExpectedDir = (new File(testProjDir, "Expected")).toString();
        String inputDir = (new File(baseInputDir, dir)).toString();
        String outputDir = (new File(baseOutputDir, dir)).toString();
        String expectedDir = (new File(baseExpectedDir, dir)).toString();
        String outputMediaDir = (new File(outputDir, "media")).toString();
        String expectedMediaDir = (new File(expectedDir, "media")).toString();
        String inputFile = (new File(inputDir, fileName + ".docx")).toString();
        String outputFile = (new File(outputDir, fileName + ".tex")).toString();
        String expectedFile = (new File(expectedDir, fileName + ".tex")).toString();
        if ((new File(outputMediaDir)).exists())
        {
            DirectorySupport.delete(outputMediaDir, true);
        }
         
        if ((new File(outputFile)).exists())
        {
            (new File(outputFile)).delete();
        }
         
        ProcessStartInfo psi = new ProcessStartInfo();
        psi.FileName = prog;
        psi.Arguments = inputFile + " " + outputFile;
        Process proc = Process.Start(psi);
        proc.WaitForExit();
        Assert.AreEqual(File.ReadAllText(expectedFile), File.ReadAllText(outputFile));
        File[] outFiles = new File(outputMediaDir).listFiles(new FilterOnlyFiles());
        File[] expFiles = new File(expectedMediaDir).listFiles(new FilterOnlyFiles());
        Assert.AreEqual(expFiles.length, outFiles.length);
    }

}


