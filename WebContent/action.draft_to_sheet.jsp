
<%@page import="java.util.List, java.util.ArrayList,java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="aufgaben_db.Global, aufgaben_db.Exercise, aufgaben_db.Sheetdraft, aufgaben_db.DocType"%>
<jsp:useBean id="draft" class="aufgaben_db.Sheetdraft" scope="session"></jsp:useBean>
<%
/**
 * Here happens: (the main difference between prior and after the conversion being that
		 at this point all the decisions of target-fileformat, which exercises to add, which order ...
		 should have been made.
		 Exercise files then are created and its contents become editable while still
		 keeping track of the original sheetdraft it comes from.)
 * - A draft will be turned into a full-fledged sheet. That means the assigned/referenced
     exercises will merge into a filesystem file.
 * - The references are eventually turned into real exercises by inserting them as semi-doubles
	 where only the sheetdraft_it_belongs_to database table field differs.
	 This allows for a possible conversion of filetypes prior to merging the exercises into a sheet and
	 making real of referenced/assigned exercises (so is_native_format would differ too,
	 while the plainText content remains equal
	 -- more importantly the exercise filelink will then be different as it's a different format
	 than the original exercise fileformat! Hence it's then no longer possible to let the exercises
	 point to other sheetdrafts' exercise filesystem exercisefiles.).   
 (REMARK: If a sheetdraft from database table sheetdraft has attribute is_draft=1 but there
 are no exercise assignments in draftexerciseassignment database table then (if it's not a newly created
 draft) it is a draft that turned into a sheet, still referencing the assigned exercises
 but the exercises have their own representation in the database table exercise to allow
 for changing the content of exercises later on -- The originsheetdraft is being stored
 in the exercise table too. This can be used to know where the exercise came from, where
 the original can be found if something went wrong and the content has to be restored or
 for statistics, i.e. how often one sheet/exercise was used for other sheet/drafts.)
 *
 */

 //TODO CHECK THAT EXERCISE POSITION IS WORKING PROPERLY. CURRENTLY IT IS BEING SORTED
 //     in drafts.in.jsp according to position ascending, so that should work without
 //     forwarding the new exercise position ().
    //Preconditions not met?
    if (request.getParameter("draft_filelink") == null/*target_filelink*/
            || request.getParameterValues("draft_exercise_filelink[]") == null
             ) {
         Global.addMessage("Either no exercises' filelinks have been transmitted" 
        		 + " or no target sheetdraft filelink was given. Without those"
        		 + " nothing can be joined into the latter.", "nosuccess");
         return ;
    }
    
	String target_sheetdraft_filelink; target_sheetdraft_filelink
            = request.getParameter("draft_filelink");
	
    String[] filelinks = (String[]) request.getParameterValues("draft_exercise_filelink[]");
    String[] filelinks_solution = (String[]) request.getParameterValues("draft_solution_filelink[]");
    if (filelinks.length < 1) {
    	Global.addMessage("Exercises' filelinks array that was given was empty!", "nosuccess");
    	return ;
    }
    /* FILELINKS CORRESPONDING EXERCISE/SOLUTION */
    if (filelinks_solution != null && filelinks_solution.length > 0) {
    	// filter empties:
    	List<String> l = new ArrayList<String>();
        for (String f : filelinks_solution) {
        	if (f != null && !f.isEmpty()) {
       		    l.add(f);

        	}
        }
        filelinks_solution = (String[]) l.toArray()
        		;
    }

    if (filelinks_solution != null && filelinks_solution.length > 0) {
    	String[] filelinks_tmp = filelinks;
    	// create array with enough space for exercises and solutions:
    	filelinks = new String[filelinks_tmp.length + filelinks_solution.length];
    	// merge:
    	if (request.getParameter("draft_solution_position_none") == null) {
            int filelinks_index = -1;
    		if (request.getParameter("draft_solution_position_at_end") != null) {

    	        while (++filelinks_index < filelinks_tmp.length) {
    	        	filelinks[filelinks_index] = filelinks_tmp[filelinks_index];
    	        }
    	        int filelinks_solution_index = -1;
                while (++filelinks_index < filelinks.length && ++filelinks_solution_index < filelinks_solution.length) {
                	filelinks[filelinks_index] = filelinks_solution[filelinks_solution_index];
                }
                
    		}
    		else if (request.getParameter("draft_solution_position_after_exercise") != null) {
    			int filelinks_tmp_index = -1;
    			int filelinks_solution_index = -1;
    			while (++filelinks_tmp_index < filelinks_tmp.length
    					&& filelinks_index < filelinks.length
    					&& ++filelinks_solution_index < filelinks_solution.length) {
    				
                    filelinks[++filelinks_index] = filelinks_tmp[filelinks_tmp_index];            //<-first exercise
                    filelinks[++filelinks_index] = filelinks_solution[filelinks_solution_index];  //<-then corresponding
                    
                }
    			
    		}
    		
    	}
    	
    }/* FILELINKS CORRESPONDING EXERCISE/SOLUTION -END */
    
    //Note: Not needed as long as the filelinks are given in the correct order (an array is ordered, JavaScript gives them ordered too.).
//     String[] exercise_position_old = (String[]) request.getParameterValues("draft_exercise_position_old[]");
//     String[] exercise_position_new = (String[]) request.getParameterValues("draft_exercise_position_new[]");
//    if (exercise_position_old == null || exercise_position_old.length < 1) {
//         Global.addMessage("No exercise positions (old) given! For saving the extra time needed for adapting the old"
//         		+ " value to the new value this should be given and forwarded to the merge functions.", "warning");
//         return ;
//     }
//     else if (exercise_position_old == null || exercise_position_old.length < 1) {
//     	Global.addMessage("No exercise positions (new) given! For saving the extra time needed for adapting the old"
//                 + " value to the new value this should be given and forwarded to the merge functions.", "warning");
//         return ;
//     }
    
    /*TODO How to ensure a file ending is always given? Perhaps:
    	1) Make draft a target option (as default, this then implies automatic mode).
    	2) Remove file endings from the filename change field. (then it has to be ensured, that the filename only is changed on submit and not the complete filelink as it is currently!)
    	3) If a supported fileending can be found in the filename field, then automatically assume it is the desired target fileformat. It can't be the original fileformat as this is fixed as '.draft.'.
    	4) Take this fileending and set the select list to the default value (draft).
    	This implies Automatic mode and thus it is first checked for target sheetdraft filename ending.
    	If not then the exercises are examined and if an exercise fileending (this time it has to be double ending! TODO really?)
    	has a supported fileformat ending we just start taking the first exercise's fileformat as target fileformat.
   	*/
    /*A Generic Approach - Automatic mode:*/
    String target_filetype;
    target_filetype = request.getParameter("draft_target_fileformat");
    if (target_filetype == null || target_filetype.equalsIgnoreCase("draft")) {
    	//Sheetdraft filename has fileending - no matter if double or single one?
    	target_filetype = Global.extractEnding(target_sheetdraft_filelink);
        if (target_filetype == null
                //Is unsupported file type?
                ||  DocType.getByEnding(target_filetype) == null) {
            
        	//Determine the fileformat of the target_sheetdraft and use that as the target format candidate.
        	target_filetype = Global.extractEnding(target_sheetdraft_filelink);
            
        	if (target_filetype == null //Is unsupported file type?
                    ||  DocType.getByEnding(target_filetype) == null) {
        		
        		//Determine the fileending from the first exercise and use that as the target filetype.
        	    target_filetype = Global.extractEnding(filelinks[0]);/*<-- existence is checked in preconditions*/
        	    
        	    if (target_filetype == null ||//Is unsupported filetype?
        	    		   DocType.getByEnding(target_filetype) == null)
        	    	    //|| target_filetype.equalsIgnoreCase("draft"))
        	    	    {
        	    	    	
        	        /*UNLIKELY- because existing exercises can't have a unsupported filetype because they were extracted
        	        from a sheet that was supported, otherwise they had not been extracted.*/ 
        	    	System.out.println("Target filetype could not be derived of the first exercise: "
        	    			   + filelinks[0] + " Target format result: " + target_filetype);
        	    	return ;
        	    	
        	    }
        	}
            
        }
    }
    
    //set the target type we settled on as the file ending:
    String target_sheetdraft_filelink_initial = target_sheetdraft_filelink;
    target_sheetdraft_filelink = Global.replaceEnding(target_sheetdraft_filelink, target_filetype.toLowerCase());
    
    //redirect/correct target page:
    Global.redirectTo("drafts");
    
    
    Global.joinToFile(/*exercise_*/filelinks, target_filetype.toLowerCase(), target_sheetdraft_filelink
//     		, /*exercise_position_old <-- mostly equal to new.*/new String[] {"-1", "-1"}
//          , exercise_position_new <-- this is not necessary as the filelinks are given in the correct order
    );
    //Global.joinToFile(/*exercise_*/filelinks, target_filetype, target_sheetdraft_filelink, exercise_position_new, exercise_position_old);
    
    
    
    // if we are still alive here, then update the sheetdraft's filelink in the database:
    // is there any difference?
   	if (!target_sheetdraft_filelink_initial.equals(target_sheetdraft_filelink)) {
	    Global.query("UPDATE sheetdraft SET filelink = '" + Global.removeRootPath(target_sheetdraft_filelink) + "'"
	    	    + " WHERE filelink = '" + Global.removeRootPath(target_sheetdraft_filelink_initial) + "'");
	    // update the in the draft referenced exercises, otherwise the draft will be shown as empty on the drafts page.
	    Global.query("UPDATE draftexerciseassignment SET sheetdraft_filelink = '" + Global.removeRootPath(target_sheetdraft_filelink) + "'"
                + " WHERE sheetdraft_filelink = '" + Global.removeRootPath(target_sheetdraft_filelink_initial) + "'");
	    /* TODO Shall the exercises be checked for a sheetdraft_filelink foreign key? Usually no, because a draft can't 
	            have exercises. So if this target_filelink ends on draft.draft there should be no need to rename in the
	            exercise database table too.
	     */ 
   	}
    
    
   
%>
 