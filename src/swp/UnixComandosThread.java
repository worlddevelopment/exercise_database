package swp;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


import aufgaben_db.Global;
public class UnixComandosThread extends Thread /* implements Runnable */ {
	
	String inFilelink;
	String outdir;
	
	//private List<String> commandList; 
	private String[] commandList = new String[10];

	
	
	public UnixComandosThread(String inFilelink) throws IOException {
		this(null, inFilelink, new File(inFilelink).getAbsoluteFile().getParent());
	}
	public UnixComandosThread(String inFilelink,
			String outDir) throws IOException {
		this(null, inFilelink, outDir);
	}
	public UnixComandosThread(HttpServletResponse response, String inFilelink,
			String outDir) throws IOException {
		
		this.inFilelink = inFilelink;
		this.outdir = outDir;
		
		this.generateCommandList();		//Unser Konvertier-"Fahrplan".
		
		//using p.start() now instead for starting a real Thread.
		//d_o();
		//p.start();
		
	}


	@Override
	public void run() {
		d_o();
	}
	public void start(String[] commandList) {
		this.commandList = commandList;
		this.start();
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
	        		//Global.addMessage(
	        			"current command: '" + cmd + "'"
	        			//, "info"
        			//)
	        	);
	        	Global.addMessage("[ready] UNIX command executed.", "success");
	        	//why that?
	        	sleep(100);//originally 5 seconds instead
	        	
	        }
	        catch(InterruptedException e) {
	        	e.printStackTrace();
	        	System.out.print(
	        			Global.addMessage("Command execution has been interrupted.\r\n"
	        					+ e.getMessage(), "danger")
    			);
	        }
	        catch (IOException e) {
				e.printStackTrace();
				System.out.print(
						Global.addMessage(e.getMessage(), "danger")
				);
			}
    	}
   	

    }
    

	
	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @param target filetype being converted to (<em>no guarantee that this will work</em>). 
	 * @return A reusable command.
	 */
	public static String getCommand_usingLibreOffice(String source_filelink, String target_filetype) {
		return getCommand_usingLibreOffice(source_filelink, target_filetype, Global.extractPathTo(source_filelink));
	}
	public static String getCommand_usingLibreOffice(String source_filelink, String convert_to, String target_directory) {
		//Arbitrary 2 Arbitrary experimental using Libre Office in headless command line mode.
		return "libreoffice --headless -convert-to " + convert_to + " " + source_filelink + " -outdir " + target_directory;
		
	}	
	
	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @return A reusable command.
	 */
	public static String getCommand_2odt_usingLibreOffice(String source_filelink) {
		return getCommand_2odt_usingLibreOffice(source_filelink, Global.extractPathTo(source_filelink));
	}
	public static String getCommand_2odt_usingLibreOffice(String source_filelink, String target_directory) {
		//Arbitrary 2 ODT using Libre Office in headless command line mode.
		return "libreoffice --headless -convert-to odt " + source_filelink + " -outdir " + target_directory;
		
	}	
	
	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @return A reusable command.
	 */
	public static String getCommand_2rtf_usingLibreOffice(String source_filelink) {
		return getCommand_2rtf_usingLibreOffice(source_filelink, Global.extractPathTo(source_filelink));
	}
	public static String getCommand_2rtf_usingLibreOffice(String source_filelink, String target_directory) {
		//Arbitrary 2 RTF using Libre Office in headless command line mode.
		return "libreoffice --headless -convert-to rtf " + source_filelink + " -outdir " + target_directory;
		
	}
	
	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @return A reusable command.
	 */
	public static String getCommand_2pdf_usingLibreOffice(String source_filelink) {
		return getCommand_2pdf_usingLibreOffice(source_filelink, Global.extractPathTo(source_filelink));
	}
	public static String getCommand_2pdf_usingLibreOffice(String source_filelink, String target_directory) {
		//Arbitrary 2 PDF using Libre Office in headless command line mode.
		return "libreoffice --headless -convert-to pdf " + source_filelink + " -outdir " + target_directory;
		
	}
	
	/**
	 * @return A reusable command.
	 */
	public static String getCommand_pdf2image_usingImageMagick(String source_filelink, String target_imagelink) {
		return getCommand_pdf2image_usingImageMagick(source_filelink, target_imagelink, "");//no further options by default
	}
	public static String getCommand_pdf2image_usingImageMagick(String source_filelink, String target_imagelink
			, String options) {
		//IMAGE - I figured out we use ImageMagick for conversion.//+repage for getting only one image, not a
		//sequence on crop and in the purpose is to drop the canvas and original size information! ->hence resizes
		//canvas automatically to the new image.
		int density = Global.ImageMagick_density * Global.ImageMagick_qualityScale;
		return "convert -density " + density + " -quality 200 -alpha off -background '#FFFF' " + options + " " + source_filelink + "[0] "
				+ " -resize 800x +repage " /*+ " -layers flatten "*/ + target_imagelink;
		//quality of 150 was enough without crop! -flatten did not help.
	}
	
	/**
	 * @return A reusable command.
	 */
	public static String getCommand_docx2tex(String source_docx, String target_tex) {
		//docx2tex using mono framework equivalent to .NET runtime environment on windows.
    	return /*Global.root +*/ "mono " + Global.root + "docx2tex/docx2tex.exe " + source_docx + " " + target_tex;
		
	}
	
	
	/**
	 * @return A reusable command.
	 */
	public static String getCommand_tex2pdf(String source_tex) {
		return getCommand_pdfLaTeX(source_tex, Global.extractPathTo(source_tex));
	}
	public static String getCommand_tex2pdf(String source_tex, String target_pdf) {
		return getCommand_pdfLaTeX(source_tex, Global.extractPathTo(source_tex));
	}
	public static String getCommand_pdfLaTeX(String source_tex) {
		return getCommand_pdfLaTeX(source_tex, Global.extractPathTo(source_tex));
	}
	public static String getCommand_pdfLaTeX(String source_tex, String target_pdf) {
		//using a portable pdfLaTeX postscript ghostscript program
    	return Global.root + "pdflatex/pdflatex " + source_tex + " " + target_pdf;
		
	}
	
	/**
	 * @return A reusable command.
	 */
	public static String getCommand_tex2txt(String source_tex, String target_txt) {
		return getCommand_detex(source_tex, target_txt);
	}
	public static String getCommand_tex2txt(String source_tex) {
		return getCommand_detex(source_tex, Global.replaceEnding(source_tex, "txt"));
	}
	public static String getCommand_detex(String source_tex) {
		return getCommand_detex(source_tex, Global.replaceEnding(source_tex, "txt"));
	}
	public static String getCommand_detex(String source_tex, String target_txt) {
		/* /path/to/detex    path/to/file.tex      >       path/to/file.txt    */
		return Global.root + "opendetex/detex -n " + source_tex + " > " + target_txt;
		
	}
	
	
	
	
	
    
    //======= HELPER
    
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
    	String cmd = getCommand_2pdf_usingLibreOffice(this.inFilelink, this.outdir);
    	commandList[++cmd_i] = cmd;//.add(cmd);

        String hypo_pdf_file = Global.replaceEnding(inFilelink, "pdf");
        String hypo_jpg_file = Global.getImageLinkFromFile(inFilelink);//this.filePathWithoutEnding.concat(".jpg");
        System.out.println(hypo_pdf_file);

        //IMAGE - I figured out we use ImageMagick for conversion.
        cmd = getCommand_pdf2image_usingImageMagick(hypo_pdf_file, hypo_jpg_file);
        commandList[++cmd_i] = cmd;//.add(cmd);
    	//-----------------END-OF-BLOC-PDF-TO-IMAGE----------------------------------//
    	

    	
    	
    	
    	
    	//WHATEVER --> TEX
    	String tex_filelink = Global.replaceEnding(this.inFilelink, "tex");
    	boolean weHaveTex = false; 
    	if (Global.extractEnding(inFilelink).equals("docx")) {
	    	//DOCX -> TEX
	    	//docx2tex using mono framework equivalent to .NET runtime environment on windows
	    	cmd = getCommand_docx2tex(this.inFilelink, tex_filelink);
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
  