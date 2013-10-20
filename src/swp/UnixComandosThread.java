package swp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.text.StrBuilder;

import aufgaben_db.Global;
public class UnixComandosThread extends Thread /* implements Runnable */ {
	PrintWriter out;
	
	String inFilelink;
	private String inFileEnding;
	String outdir;
	private String htmlFilePath; 
	
	private String filePathWithoutEnding;
	private String filePathWithoutEndingAndNotMarkedAsOriginal;
	//private List<String> commandList; 
	private String[] commandList = new String[10];
	
	public UnixComandosThread(String inFilelink,
			String outDir) throws IOException {
		this(null, inFilelink, outDir);
	}
	public UnixComandosThread(HttpServletResponse response, String inFilelink,
			String outDir) throws IOException {
		
		out = response.getWriter();
		this.inFilelink = inFilelink;
		this.outdir = outDir;
		
		this.buildFilenameWithoutAnyExtension();
		this.generateCommandList();		//Unser Konvertier-"Fahrplan".
		
		//using p.start() now instead for starting a real Thread.
		d_o();
		//p.start();
		
	}


	@Override
	public void run() {
		d_o();
	}
	
	/**
	 * The main action, executing all commandos of the command list. 
	 * @param commandList
	 */
	public void d_o() {
		d_o(this.commandList);
	}
	public void d_o(String[] /*List<String>*/ commandList) {

    	//Probiere dein Glueck.
    	for (String cmd : commandList) {
    		//Is the command slot used, i.e. contains a command? 
    		if (cmd == null || cmd.equals("")) {
    			continue;
    		}
    		//Geht auch nur ein Kommando schief, duerfte es das fuer uns gewesen sein.
	        try {
	        	Runtime run = Runtime.getRuntime();
	        	//out.print("Hallo");
	        	Process pr = run.exec(cmd);
	        	pr.waitFor();
	        	System.out.println(
	        		Global.addMessage("current command: '" + cmd + "'", "info")
	        	);
	        	//why that?
	        	sleep(5000);//originally 5 seconds instead of 1 second
	        	
	        }
	        catch(InterruptedException e) {
	        	e.printStackTrace();
	        	System.out.println(e.getMessage());
	        }
	        catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
    	}
   	

    }
    
    
    //======= HELPER
    private void buildFilenameWithoutAnyExtension() {
    	//Build name of pdf file.
        StrBuilder str_b = new StrBuilder(this.inFilelink);
        String rev_str = str_b.reverse().toString();
        int firstOccurrenceOfPoint = rev_str.indexOf("."); 
        String without_ext_rev = rev_str.substring(firstOccurrenceOfPoint + 1);
        
        String inFileEndingRev = rev_str.substring(0, firstOccurrenceOfPoint);
        this.inFileEnding = new StrBuilder(inFileEndingRev).reverse().toString();
        
        this.filePathWithoutEnding = new StrBuilder(without_ext_rev).reverse().toString();
    	this.filePathWithoutEndingAndNotMarkedAsOriginal = this.filePathWithoutEnding.replaceAll("/[.]original(e)?$/i", "");
        
    }
    
    
    /**
     * generateCommandList
     * 
     * contains:
     * A "Fahrplan" how to get this complicated conversion problem aside.
     */
    private void generateCommandList() {
    	/**/
    	//this.commandList// = new ArrayList<String>();
    	
    	
    	/*fill in the commands - alle absolut erfolgskritisch, all are critical*/
    	//TODO - handle and give feedback if something goes wrong while executing
    	int cmd_i = -1;
    	
    	//-----------------perhaps cycle over this bloc to get other previews also?--//
    	//PDF
    	String cmd = "libreoffice --headless -convert-to pdf " + this.inFilelink + " -outdir "
    				+ this.outdir;
    	commandList[++cmd_i] = cmd;//.add(cmd);

        String hypo_pdf_file = this.filePathWithoutEnding.concat(".pdf");
        String hypo_jpg_file = Global.getImageLinkFromFile(inFilelink);//this.filePathWithoutEnding.concat(".jpg");
        System.out.println(hypo_pdf_file);
        //rename file to no longer contain the ".original" before the ending

        //IMAGE - I figured out we use ImageMagick for conversion.
        cmd = "convert -density 150 -quality 200 -resize 800x " + hypo_pdf_file + "[0] " + hypo_jpg_file;
        commandList[++cmd_i] = cmd;//.add(cmd);
    	//-----------------END-OF-BLOC-PDF-TO-IMAGE----------------------------------//
    	

    	
    	
    	
    	
    	//WHATEVER --> TEX
    	String tex_file = this.filePathWithoutEndingAndNotMarkedAsOriginal.concat(".tex");
    	boolean weHaveTex = false; 
    	if (inFileEnding.equals("docx")) {
	    	//DOCX -> TEX
	    	//docx2tex using mono framework equivalent to .NET runtime environment on windows
	    	cmd = "mono docx2tex.exe " + this.inFilelink + " " + tex_file;
//TODO	    	commandList[++cmd_i] = cmd;//.add(cmd);
	    	weHaveTex = true;
	    	
    	}
//    	else if (inFileEnding.equals("ods")) {
//    		//OPEN XML -> TEX
//	    	cmd = "libreoffice --headless -convert-to tex" + this.inFilelink + " -outdir "
//	    			+ this.outdir;
////TODO	    	commandList[++cmd_i] = cmd;//.add(cmd);
//	    	weHaveTex = true;
//	    	
//    	}
//    	
//    	
//    	
//    	//we need to have a tex representation at this point for formula handling in html
//    	//TEX -> HTML
//    	//this is customised very strongly - formulas should be latex-conform and js-rendered
//    	String htmlFile = this.filePathWithoutEndingAndNotMarkedAsOriginal.concat(".html");
//    	//the html should have all css as embedded style inside of itself (in one file!)
//    	if (!weHaveTex) {
//    		System.out.println(
//    			Global.addMessage("We don't have a TeX representation of the original document.", "warning")
//    		);
//    		//generate html directly via libreoffice
//    		//OPEN XML -> HTML
//	    	cmd = "libreoffice --headless -convert-to xhtml" + this.inFilelink + " -outdir "
//	    			+ this.outdir;
////TODO	    	commandList[++cmd_i] = cmd;//.add(cmd);
//	    	System.out.print(
//	    	    Global.addMessage("Therefore trying to convert from Open XML or whatever(?) to HTML directly using LibreOffice.", "info")
//	    	);
//	    	
//    	}
//    	else {
//    		//TODO
//    		//How to generate to the original highly similar HTML out of TeX?
//    		//Manually tweak the formula conversion if necessary.
//    		System.out.println(
//    			Global.addMessage("TODO - Generate HTML out of TeX. OR DOCX2HTML.", "warning")
//			);
//	    	
//    	}
//    	this.htmlFilePath = htmlFile;
    	
    	
    	
    	//TODO invoke another class which takes the html file location as argument of
    	//the constructor and which performs the construction of the html to exercise 
    	//interactive extraction of the html content - hopefully maintaining style and
    	//formulas. (mathematical not chemical formulas so far;)
    }
    
    
  
  }
  