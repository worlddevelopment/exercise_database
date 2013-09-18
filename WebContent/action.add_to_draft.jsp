/**
 * Here happen several important things:
 * - If a new draft:    1) create new sheetdraft 2) add all given exercises by reference 3) add the relationships from point 2) to the db.
 * - If existing draft: 1) add all given (per POST/GET, request) exercises by ref. 2) write relationships from 1) to  
 *
 */


<%@page import="swp.SQL_Methods"%>
<%@page import="java.util.List,java.util.ArrayList,java.util.LinkedList,java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="aufgaben_db.Global, aufgaben_db.Exercise, aufgaben_db.Sheetdraft"%>
<jsp:useBean id="draft" class="aufgaben_db.Sheetdraft" scope="session"></jsp:useBean>
<%
    //initialization using a number less than 0 happens if no destination is given 
    //int destination_draft_id;
    String destination_draft_filelink;
    String new_or_latest_changed_or_other_draft = "";

    //Check if no destination is given and hence a new draft has to be generated. (a filelink is unique)   
   	if (request.getParameter("destination_draft_filelink") == null) {
    	//Without filelinks to add to the draft continuing is senseless.
    	//=>Are there filelinks?
    	if (request.getParameter("exercise_filelinks_to_add[]") == null) {
	    	//cancel
	    	Global.addMessage("`Add to draft` was forced to cancel due to lack of a "
	    			   + "destination draft and exercise identifiers(filelinks) required to know what to add."
	    			   , "danger");
	    	
	    	return ;
    	}
    	//else 
    	//warning
        Global.addMessage("`Add to draft` was not given a (sheet)draft as destination."
        		+ "\r\n<br />Using a new draft as default now instead."
                   , "warning");
        //destination_draft_id = -1;        /*=> -1 is for indicating a new draft*/
        destination_draft_filelink = null;  /*=> null is for indicating a new draft*/
    }
    else {
    	//already existing destination draft is given.
    	//destination_draft_id = Integer.parseInt(request.getParameter("destination_draft_id"));
    	destination_draft_filelink = request.getParameter("destination_draft_filelink");
    }
    
    
    //GET THE FILELINKS LISTING AS ARRAYLIST.
    List<String> exercise_filelinks_to_add = new ArrayList<String>();
    //Add all already existing exercises of the draft bean. (should be 0)
	exercise_filelinks_to_add.addAll(draft.getAllExercises().keySet());
    
    //Add all given exercise filelinks to the list.
    //this is now happening here
    for (String l : (String[])request.getAttribute("exercise_filelinks_to_add[]")) {
        exercise_filelinks_to_add.add(l);
    }
    
    //Intermezzo, how many exercises have been added so far?
	//Note: Arrays are given by reference!
	int exercise_filelinks_to_add_size = exercise_filelinks_to_add.size();   /*for performance
                                                 it's being used in three loops */ 

	
	//HANDLE DESTINATION DRAFT.
	/*first real appearance of the draft bean comes here*/
	if (destination_draft_filelink == null || destination_draft_filelink == ""
			|| destination_draft_filelink == "-1") {
		//CREATE NEW DRAFT.
	    draft = new Sheetdraft(); // the garbage 
		
		//modify some attributes?
	    //TODO
	    //whenever a new Sheetdraft() without arguments is created
	    //the user will be taken as the lecturer, hence changing the filelink.
	    //Without changing the filelink this could never be an independent
	    //draft or sheet because it would always point to the other file.
	    
		//save it to the database
		draft.synchronizeDatabaseToThisInstance();
		new_or_latest_changed_or_other_draft = "<em>neu erstellten</em>";
		//Global.addMessage("Created new draft with no exercises in it.", "success");
	}
	else {
		//ADD TO A DRAFT THAT ALREADY EXISTS.
		draft = new Sheetdraft();
		//draft.synchronizeWithDatabaseLoadFromIt(destination_draft_id);
		draft.synchronizeWithDatabaseLoadFromItBecomeIdentical(destination_draft_filelink);
		
		//Join the now possibly loaded exercise_filelinks with the ones
        //delivered by http request.
		exercise_filelinks_to_add.addAll(draft.getAllExercises().keySet());
		new_or_latest_changed_or_other_draft = "<em>bereits vorhandenen</em>";
		
		//Global.addMessage("Synchronized draft with another sheet or draft.", "success");
		int exercises_count_prior_to_addition = draft.getAllExercises().keySet().size();
	}
	
	
	
	//Intermezzo, how many exercises have been added so far?
    exercise_filelinks_to_add_size = exercise_filelinks_to_add.size();   /*for performance
                                                 it's being used in three loops */
	
	
	
	/*ADD EXERCISES DATABASE*/
	//1.) Add relations in database, hence referenced the exercises to be added.
	//HINT: A DRAFT ONLY CONSISTS OF REFERENCED EXERCISES ALWAYS UNTIL INDEPENDENCE!
	//Assign the referenced exercises.
	int exercise_filelinks_added_actively_count = 0;
	for (int exercise_position = 0; exercise_position < exercise_filelinks_to_add_size
			; exercise_position++) {
		
		if ( Global.sqlm.exist(" `draftexerciseassignment` ", " sheetdraft_filelink "
				, "exercise_filelink = 'exercise_filelinks_to_add.get(exercise_position)'")	) {
			continue;
		}
		
	    String query = "INSERT INTO `draftexerciseassignment`"
		   + " (sheetdraft_filelink, position, exercise_filelink)"
		   + " VALUES("
		           + draft.getFilelink()
		           + ", " + exercise_position
		           + ", '" + exercise_filelinks_to_add.get(exercise_position) + "'"
		           + ");";
		Global.query(query);
		exercise_filelinks_added_actively_count++;
	}
	
	
	//NOTE:
	//At this point all the draft's exercises are referenced in the database
	//in the table `draftexerciseassignment`.
	
	
	//To keep integrity between DB and the object/instance of the active draft:
	draft.loadAssignedAndReferencedSingleSourceExercisesFromDatabase();
	
	
	

	
	/*
	//2.) Add those given per request.
    //Already checked per precondition, see beginning of this file.
	//if (request.getParameterValues("exercise_filelinks_to_add[]") != null) {
		String exercise_filelink_to_add;
		for (int i = 0; i < exercise_filelinks_to_add_size; i++) {
			// if (!aufg_ids.contains(request
			//		.getParameterValues("aufg_id[]")[i])) {
			//	aufg_ids.add(request.getParameterValues("aufg_id[]")[i]);
			//}
			//synchronize with/from database
			exercise_filelink_to_add = exercise_filelinks_to_add.get(i);
			if (!draft.getAllExercises().keySet().contains(exercise_filelink_to_add)) {
				draft.getAllExercises().put(
					    exercise_filelink_to_add
                        , new Exercise(
	                        //arguments to be loaded prior to this point of time
	                        //sheetdraft_id,
	                        exercise_filelink_to_add,
	                        //originsheetdraft_filelink,
	                        content,
	                        header
                        )
				);
				
				//count up
				exercise_filelinks_added_count++;
			}
			
		}
		
		*/

	    // Independence skipped for now.
	    // Don't make this draft's exercises independent single users now!!
	    //=> Skip this point for now! -> Then, when independence requested, copy:
	        // - the filesystem files equivalent to the exercise,
	        // - the database entries from table `exercise`.
	        // - Update the relation draftexerciseassignment to refer to the newly copied
	        //   exercises. This is done by deleting the to the draft correlated entries
	        //   in the relation draftexerciseassignment because the exercises are single users
	        //   i.e. independent and separately in the table `exercise`.
	    
		
		
		
		//3.)	
		//FEEDBACK 
		String message = "<p>Es wurden " + exercise_filelinks_added_actively_count + " von "
                + exercise_filelinks_to_add_size + " angefragten "
                //+ " Aufgaben zum aktuell aktiven Entwurf hinzugefügt</p>");
                + " Aufgaben zum " + new_or_latest_changed_or_other_draft
                + " Entwurf (filelink: " + draft.getFilelink() + ") hinzugefügt (per Referenz).</p>"
                + "Insgesamt umfasst der Entwurf nun " + draft.getAllExercises().size()
                + "Aufgaben.";
		out.print(message);
        Global.addMessage(message, "success");
        Global.addMessage("Filelink of draft been added to: " + draft.getFilelink(), "info");
	//}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//--------------------------------------------------------------------------//
	//add complete sheetdrafts -TODO (currently this could be done via exercises)
	//--------------------------------------------------------------------------//
	
	   
    /*This variant here is of Artiom and for now stays like it is because to
    add complete Sheets to Drafts could be split into adding its exercises individually.*/
    /* ArrayList<String> bl_ids = draft.getBl_ids();
    int anzahl = 0;
    if (bl_ids == null) {
        draft.setBl_ids(new ArrayList<String>());
        bl_ids = draft.getBl_ids();
    } */
    
    /*ADD SHEETS*/
    /* if (request.getParameterValues("bl_id[]") != null) {
        for (int i = 0; i < request.getParameterValues("bl_id[]").length; i++) {
            if (!bl_ids.contains(request.getParameterValues("bl_id[]")[i])) {
                bl_ids.add(request.getParameterValues("bl_id[]")[i]);
                anzahl++;
            }
            
        }
        draft.setBl_ids(bl_ids);
        out.print("<p>Es wurden " + draft.getBl_ids().size()
                + " Blätter zum aktuell aktiven Entwurf hinzugefügt</p>");
    } */
    
    
%>
 