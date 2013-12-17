/*
 *  Copyright 2007-2008, Plutext Pty Ltd.
 *
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.

    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 */

package docx4j_library;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.samples.AbstractSample;

/**
 * This sample uses XSLT (and Xalan) to
 * produce HTML output.  (There is also
 * HtmlExporterNonXSLT for environments where
 * that is not desirable eg Android).
 *
 * If the source docx contained a WMF, that
 * will get converted to inline SVG.  In order
 * to see the SVG in your browser, you'll need
 * to rename the file to .xml or serve
 * it with MIME type application/xhtml+xml
 *
 */
public class ConvertOutHtml extends AbstractSample {

	// Config for non-command line version
	static {
	
    	inputfilepath = System.getProperty("user.dir") + "/sample-docs/word/sample-docxv2.docx";
		
		save = true;
	}

	static boolean save;

    public static void main(String[] args)
            throws Exception {
    	
		try {
			getInputFilePath(args);
		} catch (IllegalArgumentException e) {
		}
		
		
		process();
		
		
    }
		
    /**
     * This method may be called by the main function OR by another part of the programme.
     * @throws Exception 
     */
    public static void process() throws Exception {
    	process(inputfilepath);
    }
    /**
     * Here the inputfilepath is obligatory. This also enforces that we don't have to check
     * for the inputfilepath being null.
     * Storing to a freely chosen OUT_FontConverted.xhtml+xml makes is less optimal for
     * production usage if we want to keep individual pdfs for each file. Otherwise a
     * standard export would only work if there are no semaphore issues, i.e. not many
     * processes writing to this one identical file.
     * 
     * @author Jason Harrop, J. R.I.B.-Wein
     * @param inputfilepath
     * @throws Exception
     */
    public static void process(String inputfilepath) throws Exception {
    	process(inputfilepath, inputfilepath + ".html", save);
    }
    public static void process(String inputfilepath, boolean save) throws Exception {
    	process(inputfilepath, inputfilepath + ".html", save);
    }
    public static void process(String inputfilepath, String outputfilepath) throws Exception {
    	//if an output path is specified saving is implied
    	process(inputfilepath, outputfilepath, true);
    }
    public static void process(String inputfilepath, String outputfilepath, boolean save) throws Exception {
    	AbstractSample.inputfilepath = inputfilepath;
    	
		// Document loading (required)
		WordprocessingMLPackage wordMLPackage;
		if (inputfilepath == null) {
			// Create a docx
			System.out.println("No input path passed, creating dummy document");
			 wordMLPackage = WordprocessingMLPackage.createPackage();
			//TODO fix line below (SampleDocument not exists)
			//-- ONLY RELEVANT FOR standalone use, that is using main() above.
			//SampleDocument.createContent(wordMLPackage.getMainDocumentPart());	
		} else {
			System.out.println("Loading file from " + inputfilepath);
			wordMLPackage = Docx4J.load(new java.io.File(inputfilepath));
		}

		// HTML exporter setup (required)
		// .. the HTMLSettings object
    	HTMLSettings htmlSettings = Docx4J.createHTMLSettings();

    	htmlSettings.setImageDirPath(inputfilepath + "_files");
    	htmlSettings.setImageTargetUri(inputfilepath.substring(inputfilepath.lastIndexOf("/")+1)
    			+ "_files");
    	htmlSettings.setWmlPackage(wordMLPackage);
    	
    	//Other settings (optional)
//    	htmlSettings.setUserBodyTop("<H1>TOP!</H1>");
//    	htmlSettings.setUserBodyTail("<H1>TAIL!</H1>");
		
		// Sample sdt tag handler (tag handlers insert specific
		// html depending on the contents of an sdt's tag).
		// This will only have an effect if the sdt tag contains
		// the string @class=XXX
//			SdtWriter.registerTagHandler("@class", new TagClass() );
		
//		SdtWriter.registerTagHandler(Containerization.TAG_BORDERS, new TagSingleBox() );
//		SdtWriter.registerTagHandler(Containerization.TAG_SHADING, new TagSingleBox() );
		
		// output to an OutputStream.		
		OutputStream os; 
		if (save) {
			os = new FileOutputStream(outputfilepath);
		} else {
			os = new ByteArrayOutputStream();
		}

		//Don't care what type of exporter you use
		Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_NONE);
		//Prefer the exporter, that uses a xsl transformation
		//Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
		//Prefer the exporter, that doesn't use a xsl transformation (= uses a visitor)
		//Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_NONXSL);

		if (save) {
			System.out.println("Saved to: " + outputfilepath);
		} else {
			System.out.println( ((ByteArrayOutputStream)os).toString() );
		}

    }
}