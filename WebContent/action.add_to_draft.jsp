<%@page import="java.util.List, java.util.ArrayList,java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="aufgaben_db.Global, aufgaben_db.Exercise, aufgaben_db.Sheetdraft"%>
<jsp:useBean id="draft" class="aufgaben_db.Sheetdraft" scope="session"></jsp:useBean>
<%
/**
 * Here happen several important things:
 * - If a new draft:
 *   1) create new sheetdraft
 *   2) add all given exercises by reference
 *   3) add the relationships from point 2) to the database.
 * - If existing draft:
 *   1) add all given (per POST/GET, request) exercises by ref.
 *   2) write relationships from 1) to database.
 *
 */

//Preconditions not met?
if (request.getParameter("filelink") == null
		&& request.getParameterValues("exercise_filelinks_to_add[]") == null
		&& request.getParameterValues("exercise_filelinks[]") == null
		&& request.getParameterValues("sheetdraft_filelinks[]") == null) {
	Global.addMessage("No exercise/sheetdraft filelinks or ids were given. Without that, obviously, nothing can be added to a draft."
		  + " Use the treeview of the start page for editing sheetdrafts and selecting exercises."
		  + " Currently only exercises are supported.", "warning");
	return ;
}
if (request.getParameter("filelink") == null
		&& request.getParameterValues("exercise_filelinks_to_add[]") == null
		&& request.getParameterValues("exercise_filelinks[]") == null
		) {
	Global.addMessage("No exercises have been selected. Without that, nothing can be added to a draft."
			+ " Use the treeview of the start page for editing sheetdrafts and selecting exercises."
			+ " Currently only exercises are supported.", "warning");
	return ;
}


String[] exercise_filelinks = (String[]) request.getParameterValues("exercise_filelinks_to_add[]");
if (exercise_filelinks == null) {
	exercise_filelinks = (String[]) request.getParameterValues("exercise_filelinks[]");
}

// Redirect/correct target page
request.setAttribute("id", "drafts");
Global.redirectTo("drafts");

// Initialization using a number less than 0 happens if no destination is given
//int destination_draft_id;
String destination_draft_filelink;
String new_or_latest_changed_or_other_draft = "";
// Already existing destination draft is given.
//destination_draft_id = Integer.parseInt(request.getParameter("destination_draft_id"));
destination_draft_filelink = request.getParameter("destination_draft_filelink");
// Check if no destination is given and hence a new draft has to be generated. (a filelink is unique)
if (destination_draft_filelink == null) {
	// Without filelinks to add to the draft continuing is senseless.
	// =>Are there filelinks?
	if (exercise_filelinks == null || exercise_filelinks.length == 0) {
		Global.addMessage("`Add to draft` was forced to cancel due to lack of a "
				+ "destination draft and exercise identifiers(filelinks) required to know what to add."
				, "danger");
		return ;  //cancel
	}
	//else
	Global.addMessage("`Add to draft` was not given a (sheet)draft as destination."
			+ "\r\n<br />Using a new draft as default now instead."
			, "warning");
	//destination_draft_id = -1;		// => -1 is for indicating a new draft
	destination_draft_filelink = null;  // => null is for indicating a new draft
}


// GET THE FILELINKS LISTING AS ARRAYLIST.
List<String> exercise_filelinks_to_add = new ArrayList<String>();
// Add all already existing exercises of the draft bean. (should be 0)
exercise_filelinks_to_add.addAll(draft.getAllExercises().keySet());

// Add all given exercise filelinks to the list.
for (String l : exercise_filelinks) {
	exercise_filelinks_to_add.add(l);
}

// Intermezzo, how many exercises have been added so far?
// Note: Arrays are given by reference!
int exercise_filelinks_to_add_size = exercise_filelinks_to_add.size(); // for performance


// HANDLE DESTINATION DRAFT.
// first real appearance of the draft bean comes here
if (destination_draft_filelink == null || destination_draft_filelink.isEmpty()
			   || destination_draft_filelink.equals("-1")) {
	// CREATE NEW DRAFT.
	draft = new Sheetdraft();

	// Whenever a new Sheetdraft() without arguments is created
	// the user will be taken as the lecturer, hence changing
	// the filelink is very likely (not only for the filename!).
	// Without adapting the filelink this could never be an independent
	// draft or sheet because it would always point to the other/wrong file in the filesystem.

	// Modify some attributes?
	//draft.setType("Uebung");
	destination_draft_filelink = draft.getFilelinkRelative();

	// INSERT THE DRAFT INTO THE DATABASE.
	// It's not for certain that we should do this here as the sheetdraft name will change anyways:
	// So perhaps better only have the filelink as non-foreign-key in the draftexerciseassignment table?
	// - The problem will be then to determine to which author the draft belongs! So the following remains:

	//ResultSet result = Global.query(
	//		Global.QUERY_INSERT_INTO_sheetdraft(
	//			destination_draft_filelink, draft.getType(), draft.getCourse(),
	//			draft.getSemester(), 0/*N.N.*/, draft.getDescription(),
	//			draft.getAuthor(), 1/*is_draft*/, ""
	//		)
	//);

	// Save it to the database
	draft.synchronizeDatabaseToThisInstance();
	// At this point the new sheetdraft filelink is available in the sheetdraft database table.




	new_or_latest_changed_or_other_draft = "<em>neu erstellten</em>";
	//Global.addMessage("Created new draft with no exercises in it.", "success");
}
else {
	// ADD TO A DRAFT THAT ALREADY EXISTS.
	//SEMI-WORKING draft = new Sheetdraft();
	//SEMI-WORKING draft.synchronizeWithDatabaseLoadFromItBecomeIdentical(destination_draft_filelink);

	// Join the now possibly loaded exercise_filelinks with the ones
	// delivered by http request.
	//SEMI-WORKING exercise_filelinks_to_add.addAll(draft.getAllExercises().keySet());
	new_or_latest_changed_or_other_draft = "<em>bereits vorhandenen</em>";

	//Global.addMessage("Synchronized draft with another sheet or draft.", "success");
	//SEMI-WORKING int exercises_count_prior_to_addition = draft.getAllExercises().keySet().size();

}



// Intermezzo, how many exercises have been added so far?
exercise_filelinks_to_add_size = exercise_filelinks_to_add.size(); // for performance



// ADD EXERCISES DATABASE
// 1.) Add relations in database, hence referenced the exercises to be added.
// HINT: A DRAFT ONLY CONSISTS OF REFERENCED EXERCISES ALWAYS UNTIL INDEPENDENCE!
// Assign the referenced exercises.
int exercise_filelinks_added_actively_count = 0;
for (int exercise_position = 0; exercise_position < exercise_filelinks_to_add_size
		; exercise_position++) {

	if ( Global.sqlm.exist(" `draftexerciseassignment` ", " * "
			, "sheetdraft_filelink = '" + destination_draft_filelink + "'"
			+ " AND exercise_filelink = '" + exercise_filelinks_to_add.get(exercise_position) + "'")	) {
		System.out.println(Global.addMessage("Because this sheet/draft already contains this exercise, it was skipped: "
			+ exercise_filelinks_to_add.get(exercise_position), "warning"));
		continue;
	}

	String query = "INSERT INTO `draftexerciseassignment`"
			+ " (sheetdraft_filelink, `position`, exercise_filelink)"
			+ " VALUES('" + destination_draft_filelink/*draft.getFilelinkRelative()*/ + "'"
			+ ", " + exercise_position
			+ ", '" + exercise_filelinks_to_add.get(exercise_position) + "'"
			+ ");";
	Global.query(query);
	exercise_filelinks_added_actively_count++;
}


// NOTE:
// At this point all the draft's exercises are referenced in the database
// in the table `draftexerciseassignment`.


// To keep integrity between DB and the object/instance of the active draft:
//draft.loadAssignedAndReferencedSingleSourceExercisesFromDatabase();





// 2.) Add those given per request.
// Already checked per precondition, see beginning of this file.
//if (request.getParameterValues("exercise_filelinks_to_add[]") != null) {
/*
	String exercise_filelink_to_add;
	for (int i = 0; i < exercise_filelinks_to_add_size; i++) {
		//if (!aufg_ids.contains(request.getParameterValues("aufg_id[]")[i])) {
		//	aufg_ids.add(request.getParameterValues("aufg_id[]")[i]);
		//}
		// Synchronize with/from database
		exercise_filelink_to_add = exercise_filelinks_to_add.get(i);
		if (!draft.getAllExercises().keySet().contains(exercise_filelink_to_add)) {
			draft.getAllExercises().put(
				exercise_filelink_to_add
				, new Exercise(
					// Arguments to be loaded prior to this point of time
					//sheetdraft_id,
					exercise_filelink_to_add,
					//originsheetdraft_filelink,
					content,
					header
				)
			);

			// count up
			exercise_filelinks_added_count++;
		}

	}
*/

// Independence skipped for now.
// Don't make this draft's exercises independent single users now!
// => Skip this point for now! -> Then, when independence requested, copy:
// - the filesystem files equivalent to the exercise,
// - the database entries from table `exercise`.
// - Update the relation draftexerciseassignment to refer to the newly copied
//   exercises. This is done by deleting the to the draft correlated entries
//   in the relation draftexerciseassignment because the exercises are single users
//   i.e. independent and separately in the table `exercise`.




// 3.)
// FEEDBACK
String message = "<p>Es wurden " + exercise_filelinks_added_actively_count + " von "
		+ exercise_filelinks_to_add_size + " angefragten "
		//+ " Aufgaben zum aktuell aktiven Entwurf hinzugefügt</p>");
		+ " Aufgaben zum " + new_or_latest_changed_or_other_draft
		+ " Entwurf (filelink: " + draft.getFilelink() + ") hinzugef&uuml;gt (per Referenz).</p>"
		+ (exercise_filelinks_added_actively_count < exercise_filelinks_to_add_size
			? " <div class='anno'>(A guess for the missing ones: Filelink combinations that already exist are not inserted! That means, one sheet/draft can't contain one exercise more than once!)</div>" : "")
		+ "Insgesamt umfasst der Entwurf nun " + draft.getAllExercises().size()
		+ " Aufgaben.";
	System.out.print(
		Global.addMessage(message, (exercise_filelinks_added_actively_count > 0 ? "success" : "nosuccess"))
	);
	Global.addMessage("Filelink of draft been added to: " + draft.getFilelink(), "info");
//}


















//--------------------------------------------------------------------------//
// Add complete sheetdrafts - TODO This could be achieved via exercises.
//--------------------------------------------------------------------------//


// This variant here uses the old design where there were not even plans for a draft system.
// The syntax style is as was used by Artiom: bl for sheet, ...
/*
ArrayList<String> bl_ids = draft.getBl_ids();
int anzahl = 0;
if (bl_ids == null) {
	draft.setBl_ids(new ArrayList<String>());
	bl_ids = draft.getBl_ids();
}
*/

// ADD SHEETS
/*
if (request.getParameterValues("bl_id[]") != null) {
	for (int i = 0; i < request.getParameterValues("bl_id[]").length; i++) {
		if (!bl_ids.contains(request.getParameterValues("bl_id[]")[i])) {
			bl_ids.add(request.getParameterValues("bl_id[]")[i]);
			anzahl++;
		}
	}
	draft.setBl_ids(bl_ids);
	out.print("<p>Es wurden " + draft.getBl_ids().size()
			+ " Bl&auml;tter zum aktuell aktiven Entwurf hinzugef&uuml;gt</p>");
}
*/


%>

