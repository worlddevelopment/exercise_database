<%@page import="java.util.List, java.util.ArrayList,java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="core.Global, core.Part, core.Sheetdraft"%>
<jsp:useBean id="draft" class="core.Sheetdraft" scope="session"></jsp:useBean>
<%
/**
 * Here happen several important things:
 * - If a new draft:
 *   1) create new sheetdraft
 *   2) add all given parts by reference
 *   3) add the relationships from point 2) to the database.
 * - If existing draft:
 *   1) add all given (per POST/GET, request) parts by ref.
 *   2) write relationships from 1) to database.
 *
 */

//Preconditions not met?
if (request.getParameter("filelink") == null
		&& request.getParameterValues("part_filelinks_to_add[]") == null
		&& request.getParameterValues("part_filelinks[]") == null
		&& request.getParameterValues("sheetdraft_filelinks[]") == null) {
	Global.addMessage("No part/sheetdraft filelinks or ids were given. Without that, obviously, nothing can be added to a draft."
		  + " Use the treeview of the start page for editing sheetdrafts and selecting parts."
		  + " Currently only parts are supported.", "warning");
	return ;
}
if (request.getParameter("filelink") == null
		&& request.getParameterValues("part_filelinks_to_add[]") == null
		&& request.getParameterValues("part_filelinks[]") == null
		) {
	Global.addMessage("No parts have been selected. Without that, nothing can be added to a draft."
			+ " Use the treeview of the start page for editing sheetdrafts and selecting parts."
			+ " Currently only parts are supported.", "warning");
	return ;
}


String[] part_filelinks = (String[]) request.getParameterValues("part_filelinks_to_add[]");
if (part_filelinks == null) {
	part_filelinks = (String[]) request.getParameterValues("part_filelinks[]");
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
	if (part_filelinks == null || part_filelinks.length == 0) {
		Global.addMessage("`Add to draft` was forced to cancel due to lack of a "
				+ "destination draft and part identifiers(filelinks) required to know what to add."
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
List<String> part_filelinks_to_add = new ArrayList<String>();
// Add all already existing parts of the draft bean. (should be 0)
part_filelinks_to_add.addAll(draft.getAllParts().keySet());

// Add all given part filelinks to the list.
for (String l : part_filelinks) {
	part_filelinks_to_add.add(l);
}

// Intermezzo, how many parts have been added so far?
// Note: Arrays are given by reference!
int part_filelinks_to_add_size = part_filelinks_to_add.size(); // for performance


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
	// So perhaps better only have the filelink as non-foreign-key in the draftpartassignment table?
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
	//Global.addMessage("Created new draft with no parts in it.", "success");
}
else {
	// ADD TO A DRAFT THAT ALREADY EXISTS.
	//SEMI-WORKING draft = new Sheetdraft();
	//SEMI-WORKING draft.synchronizeWithDatabaseLoadFromItBecomeIdentical(destination_draft_filelink);

	// Join the now possibly loaded part_filelinks with the ones
	// delivered by http request.
	//SEMI-WORKING part_filelinks_to_add.addAll(draft.getAllParts().keySet());
	new_or_latest_changed_or_other_draft = "<em>bereits vorhandenen</em>";

	//Global.addMessage("Synchronized draft with another sheet or draft.", "success");
	//SEMI-WORKING int parts_count_prior_to_addition = draft.getAllParts().keySet().size();

}



// Intermezzo, how many parts have been added so far?
part_filelinks_to_add_size = part_filelinks_to_add.size(); // for performance



// ADD EXERCISES DATABASE
// 1.) Add relations in database, hence referenced the parts to be added.
// HINT: A DRAFT ONLY CONSISTS OF REFERENCED EXERCISES ALWAYS UNTIL INDEPENDENCE!
// Assign the referenced parts.
int part_filelinks_added_actively_count = 0;
for (int part_position = 0; part_position < part_filelinks_to_add_size
		; part_position++) {

	if ( Global.sqlm.exist(" `draftpartassignment` ", " * "
			, "sheetdraft_filelink = '" + destination_draft_filelink + "'"
			+ " AND part_filelink = '" + part_filelinks_to_add.get(part_position) + "'")	) {
		System.out.println(Global.addMessage("Because this sheet/draft already contains this part, it was skipped: "
			+ part_filelinks_to_add.get(part_position), "warning"));
		continue;
	}

	String query = "INSERT INTO `draftpartassignment`"
			+ " (sheetdraft_filelink, `position`, part_filelink)"
			+ " VALUES('" + destination_draft_filelink/*draft.getFilelinkRelative()*/ + "'"
			+ ", " + part_position
			+ ", '" + part_filelinks_to_add.get(part_position) + "'"
			+ ");";
	Global.query(query);
	part_filelinks_added_actively_count++;
}


// NOTE:
// At this point all the draft's parts are referenced in the database
// in the table `draftpartassignment`.


// To keep integrity between DB and the object/instance of the active draft:
//draft.loadAssignedAndReferencedSingleSourcePartsFromDatabase();





// 2.) Add those given per request.
// Already checked per precondition, see beginning of this file.
//if (request.getParameterValues("part_filelinks_to_add[]") != null) {
/*
	String part_filelink_to_add;
	for (int i = 0; i < part_filelinks_to_add_size; i++) {
		//if (!aufg_ids.contains(request.getParameterValues("aufg_id[]")[i])) {
		//	aufg_ids.add(request.getParameterValues("aufg_id[]")[i]);
		//}
		// Synchronize with/from database
		part_filelink_to_add = part_filelinks_to_add.get(i);
		if (!draft.getAllParts().keySet().contains(part_filelink_to_add)) {
			draft.getAllParts().put(
				part_filelink_to_add
				, new Part(
					// Arguments to be loaded prior to this point of time
					//sheetdraft_id,
					part_filelink_to_add,
					//originsheetdraft_filelink,
					content,
					header
				)
			);

			// count up
			part_filelinks_added_count++;
		}

	}
*/

// Independence skipped for now.
// Don't make this draft's parts independent single users now!
// => Skip this point for now! -> Then, when independence requested, copy:
// - the filesystem files equivalent to the part,
// - the database entries from table `part`.
// - Update the relation draftpartassignment to refer to the newly copied
//   parts. This is done by deleting the to the draft correlated entries
//   in the relation draftpartassignment because the parts are single users
//   i.e. independent and separately in the table `part`.




// 3.)
// FEEDBACK
String message = "<p>Es wurden " + part_filelinks_added_actively_count + " von "
		+ part_filelinks_to_add_size + " angefragten "
		//+ " Aufgaben zum aktuell aktiven Entwurf hinzugefügt</p>");
		+ " Aufgaben zum " + new_or_latest_changed_or_other_draft
		+ " Entwurf (filelink: " + draft.getFilelink() + ") hinzugef&uuml;gt (per Referenz).</p>"
		+ (part_filelinks_added_actively_count < part_filelinks_to_add_size
			? " <div class='anno'>(A guess for the missing ones: Filelink combinations that already exist are not inserted! That means, one sheet/draft can't contain one part more than once!)</div>" : "")
		+ "Insgesamt umfasst der Entwurf nun " + draft.getAllParts().size()
		+ " Aufgaben.";
	System.out.print(
		Global.addMessage(message, (part_filelinks_added_actively_count > 0 ? "success" : "nosuccess"))
	);
	Global.addMessage("Filelink of draft been added to: " + draft.getFilelink(), "info");
//}


















//--------------------------------------------------------------------------//
// Add complete sheetdrafts - TODO This could be achieved via parts.
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

