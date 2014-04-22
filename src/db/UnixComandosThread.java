package db;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.fop.pdf.PDFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import command.Command;
import converter.Converter;
import converter.PDFToImage;
import fr.opensagres.xdocreport.converter.Options;

import aufgaben_db.Global;

public class UnixComandosThread extends Thread /* implements Runnable */ {
	
	/**
	 * Note: This is far from optimal as the input output parameters may differ severly inbetween
	 *       each command in this UnixCommandsThread's command list.
	 *       Nevertheless there is no class for a Command as it's impossible to cover all possible
	 *       Commands dynamically. And it'd be a huge workload to maintain as the parameter order
	 *       plays a crucial role for many commands. So I discourage creating such classes.
	 *       
	 *       Instead simply parse the generated commands for retrieving input and output parameters.
	 *       Overwriting may be useful for switching between several alternative same-purpose commands
	 *       or if you want to mix a direct JAVA call in a certain order with UNIX commands.
	 *       
	 *       This "overriding" of a UNIX command what is used for calling the JAVA JODConverter methods
	 *       in static Global class.
	 *       
	 *       So these parameters here are not really representative and are NOT VALID FOR ALL commands
	 *       of the command list. Keep this in mind! => Better extract the input and target filelinks
	 *       from the command in the list! 
	 * */
	
	//private List<String> commandList; 
	private Command[] commandList = new Command[10];/*<-- Assumption that we only have x commands in a 
														  certain order/row at max. */

	
/*  Now no longer commented out because it's now no longer required to explicitely specify a 
	conversion target (path) in all cases as we extract it dynamically from each command string.
	The reason is that the parameters can not be valid for all commands anyway. So treat them carefully
	and rather extract those parameters from a command string directly.
	*/
	public UnixComandosThread(String inFilelink) throws IOException {
		this(null, inFilelink, inFilelink);
	}
	/**
	 * 
	 * @param inFilelink Source filelink for default command list. 
	 * @param targetFilelinkOrDir Default target directory or filelink. Will be handled for resolving of what kind it is.  
	 * @throws IOException
	 */
	public UnixComandosThread(String inFilelink,
			String targetFilelinkOrDir) throws IOException {
		this(null, inFilelink, targetFilelinkOrDir);
	}
	public UnixComandosThread(HttpServletResponse response, String inFilelink,
			String targetFilelinkOrDir) throws IOException {

		if (inFilelink == null || inFilelink.isEmpty() || targetFilelinkOrDir == null || targetFilelinkOrDir.isEmpty()) {
			System.out.println("Target of Source filelink were null: Source: " + inFilelink + " Target:" + targetFilelinkOrDir 
					+ "\r\nSo this instance cant't be used with d_o() or run() without commandList parameter.");
		}
		else {
			this.generateDefaultCommandList(inFilelink, targetFilelinkOrDir);//Unser Konvertier-"Fahrplan".
		}
		
		//using p.start() now instead for starting a real Thread.
		//d_o();
		//p.start();
		
	}


	@Override
	public void run() {
		d_o();
	}
	public void start(Command[] commandList) {
		this.commandList = commandList;
		this.start();
	}
	/**
	 * Better not use this method if you wish to extract the source filelink or target file or directory
	 * later on. If you know how to extract this generally, then why not add it in 
	 * Command.constructInstance method directly?
	 * @param commandList 
	 */
	public void start(String[] commandList) {
		this.commandList = new Command[commandList.length];
		int i = -1;
		while (++i < commandList.length) {
			this.commandList[i] = Command.constructInstance(commandList[i]);
 		}
		start();
	}
	
	
	public void d_o(/*String inFilelink, String targetFilelinkOrDir*/) {
		if (this.commandList == null) {
//			this.generateDefaultCommandList(inFilelink, targetFilelinkOrDir);
			System.out.println("CommandList was null.\r\nSo this instance cant't be used with d_o() or run() without commandList parameter.");
		}
		else {
			d_o(this.commandList);
		}
	}
	/**
	 * The main action, executing all commandos of the command list. 
	 * @param commandList - optional, if not specified, then the default command list will be used.
	 */
	public void d_o(Command[] /*List<String>*/ commandList) {

    	//Probiere dein Glueck.
    	for (Command command : commandList) {
    		
    		if (command == null) {
    			continue;
    		}
    		
    		String cmd = command.toString();
    		//Is the command slot used, i.e. contains a command? 
    		if (cmd == null || cmd.equals("")) {
    			continue;
    		}
    		
    		
    		//------- The engine -------------------------------------------//
    		//NOTE: The following allows for overriding a UnixCommand with a direct Java function call.
    		String sourceFilelink = command.getSourceFilelink();
    		String targetFilelink = command.getTargetFilelink();/*<-- unless getTargetFilelinkOrDir this makes 
    		sure it is a filelink, so even if it had been a directory at the beginning, it now will be a 
    		filelink as the source filelink will be extracted and appended to the target directory. 
    		*/ 
    		
    		/*NOTE: It could be generalised and all could be diverted to Global.convertFile. After that it's
    		 		checked if the goal was achieved already. If the target file not exists, we go the Thread UNIX command route. 
	 		*/
    		
    		//Is this a valid office (probably conversion) command?
    		if (!Global.forceAllCommandsIntoOwnThread
    				&& sourceFilelink != null /*&& Global.isExactlyOnePath(sourceFilelink)*/
    				&& targetFilelink != null /*&& Global.isExactlyOnePath(sourceFilelink)*/
    				) {
    			System.out.println("[Java-only] Trying to convert " + sourceFilelink + " to " + targetFilelink);
    			
    			// For deciding if we have to fallback to the thread conversion method. 
    			boolean isConverted = false;
    			
    			boolean fallback = false;    			
    			//OVERRIDE CONVERSION TO IMAGE:
    			if (cmd.contains("imagemagick") 
    					|| cmd.contains("convert")
    					&& cmd.contains("pdf") //<-from
    					&&                     //to:
    					(cmd.contains("jpg") || cmd.contains("JPG") 
    							|| cmd.contains("png") || cmd.contains("PNG"))
    							) {
    				
    				String to = "jpg";
    				to = Global.extractEnding(targetFilelink);
    				if (cmd.contains("convert") && (to == null || to.contains("ENDING WAS NULL"))) {
    					String[] cmdParts = cmd.split("convert-to");
    					to = cmdParts[1].trim().split(" ")[0];
    				}
    				Options options = Options.getFrom("pdf");
    				options.to(to);
    				options.setProperty("convertFirstPageOnly", 1);//<--custom property see PDFToImage class.
    				System.out.println("[Java-only] Trying to convert from PDF to image format: " + to);
    				try {
						Converter.pdf2image(sourceFilelink, targetFilelink);
					} catch (IOException e) {
						e.printStackTrace();
					}//, options);
    				
    				
    			}
    			//OVERRIDE CONVERSION TO PDF :
    			else {
    				fallback = false;
    				if (//cmd.contains("imagemagick") 
    					cmd.contains("convert-to pdf") || cmd.contains("convert-to  pdf")
    					|| cmd.contains("convert-to PDF") || cmd.contains("convert-to  PDF")) {
    				
    				
    					System.out.println("[Java-only] Trying to convert to PDF.");
	    				try {
	    					//Attention: As we might just have come from convertFile, this could turn into an infinite loop: => set Java only via boolean parameter.
							Global.convertFile(sourceFilelink, Global.extractEnding(targetFilelink), targetFilelink, true); //<-- boolean isJavaOnlyDesired
							//fallback = false;
						} catch (Exception e) {
							e.printStackTrace();
							fallback = true;
						}
	    				
	    				
	    			}
	    			//OVERRIDE CONVERSION USING OPEN-/LIBREOFFICE INSTANCE:
	    			if (fallback 
	    					&& (
	    							cmd.contains("libreoffice ") || cmd.contains("soffice ")
		    						|| cmd.contains("openoffice ")/*spaces because commands always are succeded by space.*/
		    						//optional check:
		    						|| cmd.contains("convert-to pdf") || cmd.contains("convert-to  pdf")
		    						)
	    						) {
	    			
		    			/*=> Use JODConverter.
		    			     Handles open/libre office instance start automatically and 
		    			     keeps it running for reusing the same instance later.
		    			     => Tremendously less overhead time! 
				     	 */
	    				System.out.println("[JODConverter] Trying to convert using ProcessManager (troublesome if multiple threads access at the same time).");
	    				try {
	    					Global.convert(new File(/*Global.root + */sourceFilelink), new File(targetFilelink));
	    					isConverted = Global.officeManager.isRunning(); /*<-- TODO If the converter above encounters problems or
		    			the target file was somehow not created, fall back to the other conversion procedure.
	    					 */
	    				}
	    				catch(Exception e) {
	    					e.printStackTrace();
	    					System.out.println("JODConverter failed. Fallback too LibreOffice Thread.");
	    				}
	    			}
    			}
    			
    			//isConverted?
    			boolean exists = new File(targetFilelink).exists();
    			System.out.println("Checking if target filelink now already was created, i.e. exists. " + exists);
    			if (exists) {
    				System.out.println(Global.addMessage("Target file generated successfully (or already existed): " + targetFilelink + " (could detect its existence)", "success"));
    				printCommandMessage(command, " had not to be executed");
    				continue; //this command has hopefully been executed by the jodconverter directly.
    			}
    			//else => fallback to UnixComandosThread method ...
    			/*TODO check if JODConverter not only dispatches a thread and we are better off
	    			  if we insert a delay to ensure that following commands have what they expect 
	    			  (they could depend on a previous conversion e.g.).
    			 */
    		}
    		//else /*either all commands forced into own thread - or source and target link invalid.*/
    		
    		// THE (UNIX ONLY) MULTIPLE THREADS CONVERSION: (reliable but lengthy and not cross-platform)
    		//Geht auch nur ein Kommando schief, duerfte es das fuer uns gewesen sein (bauen aufeinander auf).
	        try {
	        	Runtime run = Runtime.getRuntime();
	        	//out.print("Hallo");
	        	Process pr = run.exec(cmd);
	        	pr.waitFor();
	        	printCommandMessage(command);
//	        	//why that?
//	        	sleep(100);//originally 5 seconds instead
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

	public static void printCommandMessage(Command command) {
		printCommandMessage(command, " executed");
	}
	public static void printCommandMessage(Command command, String additionally) {
		if (additionally == null) {
			additionally = " (no message given)";// had not to be executed";
		}
		System.out.println(
    		//Global.addMessage(
    			"current command: '" + command + "'"
    			//, "info"
			//)
    	);
    	
    	Global.addMessage("[ready] UNIX command " + additionally + "."/* + command*/, "success");
	}

	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @param target filetype being converted to (<em>no guarantee that this will work</em>). 
	 * @return A reusable command.
	 */
	public static Command getCommand_usingLibreOffice(String source_filelink, String target_filetype) {
		return getCommand_usingLibreOffice(source_filelink, target_filetype, Global.extractPathTo(source_filelink));
	}
	public static Command getCommand_usingLibreOffice(String source_filelink, String convert_to, String target_directory) {
		//Arbitrary 2 Arbitrary experimental using Libre Office in headless command line mode.
		return new Command(Command.getOfficeCommand() + " -convert-to " + convert_to + " ", source_filelink, " -outdir ", target_directory);
		
	}	
	
	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @return A reusable command.
	 */
	public static Command getCommand_2odt_usingLibreOffice(String source_filelink) {
		return getCommand_2odt_usingLibreOffice(source_filelink, Global.extractPathTo(source_filelink));
	}
	public static Command getCommand_2odt_usingLibreOffice(String source_filelink, String target_directory) {
		//Arbitrary 2 ODT using Libre Office in headless command line mode.
		return new Command(Command.getOfficeCommand() + "  -convert-to odt ", source_filelink, " -outdir ", target_directory);
	}	
	
	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @return A reusable command.
	 */
	public static Command getCommand_2rtf_usingLibreOffice(String source_filelink) {
		return getCommand_2rtf_usingLibreOffice(source_filelink, Global.extractPathTo(source_filelink));
	}
	public static Command getCommand_2rtf_usingLibreOffice(String source_filelink, String target_directory) {
		//Arbitrary 2 RTF using Libre Office in headless command line mode.
		return new Command(Command.getOfficeCommand() + "  -convert-to rtf ", source_filelink, " -outdir ", target_directory);
		
	}

	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @return A reusable command.
	 */
	public static Command getCommand_2docx_usingLibreOffice(String source_filelink) {
		return getCommand_2docx_usingLibreOffice(source_filelink, Global.extractPathTo(source_filelink));
	}
	public static Command getCommand_2docx_usingLibreOffice(String source_filelink, String target_directory) {
		//Arbitrary 2 RTF using Libre Office in headless command line mode.
		return new Command(Command.getOfficeCommand() + "  -convert-to docx ", source_filelink, " -outdir ", target_directory);
		
	}

	
	
	/**
	 * @param source_filelink -- The output directory is extracted from the input filelink.
	 * @return A reusable command.
	 */
	public static Command getCommand_2pdf_usingLibreOffice(String source_filelink) {
		return getCommand_2pdf_usingLibreOffice(source_filelink, Global.extractPathTo(source_filelink));
	}
	public static Command getCommand_2pdf_usingLibreOffice(String source_filelink, String target_directory) {
		//Arbitrary 2 PDF using Libre Office in headless command line mode.
		return new Command(Command.getOfficeCommand() + "  -convert-to pdf ", source_filelink, " -outdir ", target_directory);
		
	}
	
	/**
	 * @return A reusable command.
	 */
	public static Command getCommand_pdf2image_usingImageMagick(String source_filelink, String target_imagelink) {
		return getCommand_pdf2image_usingImageMagick(source_filelink, target_imagelink, "");//no further options by default
	}
	public static Command getCommand_pdf2image_usingImageMagick(String source_filelink, String target_imagelink
			, String options) {
		//IMAGE - I figured out we use ImageMagick for conversion.//+repage for getting only one image, not a
		//sequence on crop and in the purpose is to drop the canvas and original size information! ->hence resizes
		//canvas automatically to the new image.
		int density = Global.ImageMagick_density * Global.ImageMagick_qualityScale;
		return new Command(
				"convert -density " + density + " -quality 200 -alpha off -background '#FFFF' " + options + " "
				, source_filelink + "[0] "
				, " -resize 800x +repage " /*+ " -layers flatten "*/
				, target_imagelink
		);
		//quality of 150 was enough without crop! -flatten did not help.
	}
	
	/**
	 * @return A reusable command.
	 */
	public static Command getCommand_docx2tex(String source_docx, String target_tex) {
		//docx2tex using mono framework equivalent to .NET runtime environment on windows.
    	return new Command(/*Global.root +*/ "mono " + Global.root + "docx2tex/docx2tex.exe ", source_docx, " ", target_tex);
		
	}
	
	
	/**
	 * @return A reusable command.
	 */
	public static Command getCommand_tex2pdf(String source_tex) {
		return getCommand_pdfLaTeX(source_tex, Global.extractPathTo(source_tex));
	}
	public static Command getCommand_tex2pdf(String source_tex, String target_pdf) {
		return getCommand_pdfLaTeX(source_tex, Global.extractPathTo(source_tex));
	}
	public static Command getCommand_pdfLaTeX(String source_tex) {
		return getCommand_pdfLaTeX(source_tex, Global.extractPathTo(source_tex));
	}
	public static Command getCommand_pdfLaTeX(String source_tex, String target_pdf) {
		//using a portable pdfLaTeX postscript ghostscript program
    	return new Command(Global.root + "pdflatex/pdflatex ", source_tex, " ", target_pdf);
		
	}
	
	/**
	 * @return A reusable command.
	 */
	public static Command getCommand_tex2txt(String source_tex, String target_txt) {
		return getCommand_detex(source_tex, target_txt);
	}
	public static Command getCommand_tex2txt(String source_tex) {
		return getCommand_detex(source_tex, Global.replaceEnding(source_tex, "txt"));
	}
	public static Command getCommand_detex(String source_tex) {
		return getCommand_detex(source_tex, Global.replaceEnding(source_tex, "txt"));
	}
	public static Command getCommand_detex(String source_tex, String target_txt) {
		/* /path/to/detex    path/to/file.tex      >       path/to/file.txt    */
		return new Command(Global.root + "opendetex/detex -n ", source_tex, " > ", target_txt);
		
	}
	
	/**
	 * TEX -> (X)HTML.
	 * 
	 * This requires libxml2 and libxslt. Use aptitude to get propositions for what to install if
	 * these exact package names were not found.
	 * 
	 * It's all about perl here, so we also need some extra packages, see LatexML dependency webpage
	 * for how to install.
	 * 
	 * Also if some files are not found, check for Version.in and rename it to Version.pm .
	 * 
	 * @param source_tex
	 * @return
	 */
	public static Command getCommand_tex2html(String source_tex) {
		return getCommand_detex(source_tex, Global.replaceEnding(source_tex, "html"));
	}
	public static Command getCommand_tex2html(String source_tex, String target_specify_type_by_changing_ending) {
		/* /path/to/detex    path/to/file.tex      >       path/to/file.txt    */
		/* bash command:
		bin/latexml --destination=~/path/to/aufgabe9.tex.xml ~/path/to/auf9.tex-479873197-47
		 && bin/latexmlpost --destination=~/path/to/aufgabe9.tex.xhtml ~/path/to/aufgabe9.tex.xml
		*/
		String xml_filelink = Global.replaceEnding(source_tex, "xml");
		/* Two commands in one don't really fit into our Command class. So put it in as string 
		   until we need to override this command what is no really probable.
		   */
		return  Command.constructInstance(/*Note: This needs perl and extra libs.  TODO specify.*/
				/*intermediate step over xml:*/
				Global.root + "latexml/bin/latexml --destination=" /*+ Global.root*/ + xml_filelink 
				+ " " + source_tex
				/*final conversion by postprocessing:*/
				+ " " + Global.root + "latexml/bin/latexmlpost --destination=" + target_specify_type_by_changing_ending
				+ " " + xml_filelink
		);
		
	}
	

	
	
	
    
    //======= HELPER
    
    /**
     * generateCommandList
     * 
     * contains:
     * A "Fahrplan" how to get this complicated conversion problem aside.
     */
    private void generateDefaultCommandList(String inFilelink, String targetFilelinkOrDir) {

    	//this.commandList// = new ArrayList<String>();
    	/*IMPORTANT NOTE:
    	  ArrayList seems not to keep the order eventhough I think it should as it should be built upon arrays.
    	  As the execution order is critical now arrays that keep the ordering are used. 
    	 */
    	
    	
    	/*fill in the commands - alle absolut erfolgskritisch, all are critical*/
    	//TODO - handle and give feedback if something goes wrong while executing
    	int cmd_i = -1;
    	
    	
    	/*Use the command class' functionality for determining targetDir and targetFilelink.*/
    	//the strings are random and not important, they are for explaning the arguments only.
    	Command cmd = new Command("command begin", inFilelink, "inbetween", targetFilelinkOrDir, "end");
    	String targetDir = cmd.getTargetDir();
    	String targetFilelink = cmd.getTargetFilelink();
    	
    	//-----------------perhaps cycle over this bloc to get other previews also?--//
    	//PDF
    	cmd = getCommand_2pdf_usingLibreOffice(inFilelink, targetDir);
    	commandList[++cmd_i] = cmd;//.add(cmd);

        String hypo_pdf_file = Global.replaceEnding(targetFilelink, "pdf");
        String hypo_jpg_file = Global.getImageLinkFromFile(targetFilelink);//this.filePathWithoutEnding.concat(".jpg");
        System.out.println(hypo_pdf_file);

        //IMAGE - I figured out we use ImageMagick for conversion.
        cmd = getCommand_pdf2image_usingImageMagick(hypo_pdf_file, hypo_jpg_file);
        commandList[++cmd_i] = cmd;//.add(cmd);
    	//-----------------END-OF-BLOC-PDF-TO-IMAGE----------------------------------//
    	

    	
    	
    	
    	
    	//WHATEVER --> TEX
    	String tex_filelink = Global.replaceEnding(targetFilelink, "tex");
    	boolean weHaveTex = false; 
    	if (Global.extractEnding(inFilelink).equals("docx")) {
	    	//DOCX -> TEX
	    	//docx2tex using mono framework equivalent to .NET runtime environment on windows
	    	cmd = getCommand_docx2tex(inFilelink, tex_filelink);
//TODO	    	commandList[++cmd_i] = cmd;//.add(cmd);
	    	weHaveTex = true;
	    	
    	}
//    	else if (inFileEnding.equals("ods")) {
//    		//OPEN XML -> TEX
//	    	cmd = Command.getOfficeCommand() + "  -convert-to tex" + this.inFilelink + " -outdir "
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
//	    	cmd = Command.getOfficeCommand() + "  -convert-to xhtml" + this.inFilelink + " -outdir "
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

  