package command;

import java.io.File;

import core.Global;



public class Command { 
	//private List<String> commandParts;
	private String[] commandParts;
	int source_index;
	int target_index;
	
	private Command(String beginPart) {
		commandParts = beginPart.split(" ");
		source_index = -1;//0; Better return null instead of the mixed string!! Because it can't
		target_index = -1;//0; be relied upon that the calling functions test if it's a valid path string.
	}
	/**
	 * The last parameter really is not required very often, so that's why this constructor exists.
	 * @param beginPart
	 * @param source
	 * @param inbetweenPart
	 * @param target
	 */
	public Command(String beginPart, String source, String inbetweenPart, String target) {
		this(beginPart, source, inbetweenPart, target, "");
	}
	/**
	 * The detailed constructor. Attention: Source and target may be different for different commands.
	 * TODO: Deal with the special cases (e.g. tar with c option for compressing.).
	 *  
	 * @param beginPart
	 * @param source - a filelink in the most cases. Could be a path too, e.g. for a move command (mv source target).
	 * @param inbetweenPart - could also be called the part of the command that follows the source filelink.
	 * @param targetFilelinkOrDir - optional parameter - a target file path or path to directory.
	 * @param endPart - optional parameter for command options that follow the target.
	 */
	public Command(String beginPart, String source, String inbetweenPart, String targetFilelinkOrDir, String endPart) {
		/*commandParts = new ArrayList<String>();
		commandParts.addAll(beginPart.split(" "));
		*/
		String[] beginParts;
		beginParts = beginPart.trim().split(" ");
		
		source_index = beginParts.length;
		
		String[] inbetweenParts;
		inbetweenParts = inbetweenPart.trim().split(" ");
		target_index = source_index + inbetweenParts.length + 1; /* <-- The correction for starting with 0 
		instead of 1 is already done for source_index! */
		
		String[] endParts;
		endParts = endPart.trim().split(" ");
		
		/* Create a new array with the total length of all previously constructed arrays, because we 
		   want to have them all in one final array:  (it's easy to extract the subarrays afterwards. 
		   Skipping entries is still possible by adding index as assemble exceptions. Adding entries 
		   is doable too. The question is if this all is required. A command may be dynamically generated 
		   and stored in this Command container class. This container is rather a storage than a dynamic 
		   builder. This can't be done without program context anyway, so this has to be done before 
		   creating objects of this storage class.)
		   */ 
		this.commandParts = new String[target_index + 1 + endParts.length];
		
		// Add all parts.
		int i = -1;
		while (++i < beginParts.length) {
			commandParts[i] = beginParts[i];//<-- As commandParts is empty here, the indices are equal => i and i.
		}
		
		commandParts[source_index] = source;
		
		int commandParts_index = source_index;
		i = -1;
		while (++i < inbetweenParts.length) {
			commandParts[++commandParts_index] = inbetweenParts[i];
		}
		
		commandParts[target_index] = targetFilelinkOrDir;
		
		commandParts_index = target_index;
		i = -1;
		while (++i < endParts.length) {
			commandParts[++commandParts_index] = endParts[i];
		}
		
	}
//	public Command(String program, Map<String, String> options) {
//		
//	}
	
	public String getCommand() {//assemble
		String command = "";
		for (String part : commandParts) {
			if (part != null) {
				command += part + " ";
			}
		}
		return command;
	}
	public String getSourceFilelink() { /* sometimes the source file from a libreoffice command is selected from an 
		array. We have to replace this ending if we want to find the (correct) file! */
		return ((source_index == -1) ? null : commandParts[source_index].replaceAll("\\[[0-9]*\\][ ]*$", ""));//Java thinks it's possible to have another character class [ within a character class introduced by [ too. Escape of [ necessary. Crazy.
	}
	
	
	//-------HANDLING OF TARGET FILELINK OR DIRECTORY PARAMETER----------------------------//
	/*TODO How to make this triple redundant code reusable in a better way?
			Occurrences: 1x in UnixComandosThread, 1..2x here in Command.
			Currently it's done by extra cache variables.
			In C++ we could give the variables per reference but how to give a primitive type by reference 
			in JAVA?
	 */
	/**
	 * Better not use these private attribtes directly anywhere, not even in this class.
	 * Despite this being a cache it's only to make the code non-redundant.
	 */
	//For storing our results:
	private String targetDir;
	private String targetFilelink;
	/**
	 * Extracts the target directory from the stored targetFilelink or directy.
	 */
	private void determineTargetDirAndFilelink() {
		String targetFilelinkOrDir = getTargetFilelinkOrDir();
		
		String sourceFilelink = getSourceFilelink();
		//The following is required to keep both target filelink and target directory consistent!
		if (targetFilelinkOrDir == null) {
			targetFilelinkOrDir = sourceFilelink;
		}
		// still null?
		if (targetFilelinkOrDir == null) {
			System.out.println("Could not determine targetFilelink because both targetFilelinkOrDir and sourceFilelink are null .");
			return ;
		}
		
		
		//Does the path given represent a directory?
		if ( targetFilelinkOrDir.matches("/$")  /*<-- Has a / at the end? */
				|| new File(targetFilelinkOrDir).isDirectory() ) {
			targetDir = targetFilelinkOrDir;
			//now we replace the pathTo, e.g. /old/path/to/input.file -->/new/path/to/input.file that's then used as an output file.
			//=> If a different than the original fileformat is desired, then it's to be required
			targetFilelink = /*with the replace, this is double: this.targetDir + System.getProperty("file.separator") +*/ sourceFilelink.replace(Global.getParentDirOfFile(sourceFilelink), targetDir);
			targetFilelink.replace(System.getProperty("file.separator") + System.getProperty("file.separator"), System.getProperty("file.separator"));//TODO Is using // a problem? What's the proper way dealing with filelinks in JAVA?
			String conversion_target_file_type = getConversionTargetFileType(commandParts);
			if (conversion_target_file_type != null) {
				targetFilelink = Global.replaceEnding(targetFilelink, conversion_target_file_type);
			}
		}
		else {
			targetDir = Global.getParentDirOfFile(targetFilelinkOrDir);
			targetFilelink = targetFilelinkOrDir;
		}
	}
	public static String getConversionTargetFileType(String[] commandParts) {
		int i = 0;
		while (++i < commandParts.length) {
			if (commandParts[i].contains("convert-to")) {
				return commandParts[i + 1];//<--Then the following argument must be the ending, otherwise there is a bug!
			}
		}
		//nothing found.
		return null;
	}
	/**
	 * @return target filelink if it exists.
	 */
	public String getTargetFilelink() {
		determineTargetDirAndFilelink();//<-- always regenerate the cached values for targetDir and Filelink.
		return targetFilelink;
	}
	/**
	 * @return target directory (either extracted from target filelink or if this is not given from source filelink).
	 */
	public String getTargetDir() {
		determineTargetDirAndFilelink();
		return targetDir;
	}
	
	private String getTargetFilelinkOrDir() {
		/* It's not enough to test for the source only! 
		   There are commands that only have a target and not source file!
		 */ 
		return ((target_index == -1) ? null : commandParts[target_index]);
	}
	
	public String toString() {
		return getCommand();
	}
	
	public static Command constructInstance(String command) {
		/* TODO parse, i.e. try to extract source and target from the command string that could 
		 be almost anything! In any order. So I wonder if this is possible at all!?
		 => For now we simply do nothing and always return the whole command if the target source 
		 are demanded.
		 */
		return new Command(command);
	}
	
	
	
	//--- predefined or on the fly for a OS determined commands:
	public static String getOfficeCommand() {
		return "libreoffice --headless ";//--nocrashreport --nodefault ";// --nofirststartwizard --nolockcheck --nologo --norestore ";
	}
}
