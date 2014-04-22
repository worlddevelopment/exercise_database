//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:56 AM
//

package UnitTests;

import UnitTests.Program;

public class Program   
{
    public static void main(String[] args) throws Exception {
        Program.Main(args);
    }

    public static void Main(String[] args) throws Exception {
        //DocFileFormatTests test = new DocFileFormatTests();
        //test.SetUp();
        //test.TestProperties();
        //test.TearDown();
        DocObjectModelTests test2 = new DocObjectModelTests();
        test2.TestParseability();
    }

}


