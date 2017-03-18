<%@page import="core.Global" %>
<%@page import= "java.sql.ResultSet, java.util.List, java.util.ArrayList, java.io.File, core.PartDB" %>
<%



// SWITCH QUEST
String q = request.getParameter("q");
//Global.addMessage("q = " + q, "info");

if (q.equals("logout")) {//LOGOUT
	session.removeAttribute("user");//Global.isLoggedIn() checks this!
	Global.anzeige = session.getAttribute("defaultAnzeige").toString();
}
//if (request.getParameter("q").equals("login")) {  //LOGIN
// CREATE DRAFT
else if (q.equals("create_draft")) {
	//PartDB.addSheetdraft(new Sheetdraft("filelink",));
	if (true) {
		Global.message += "<div class='success'>Entwurf erfolgreich erstellt.</div>";
	}
}
// CLEAR DATABASE PARTIAL
else if (q.equals("clear_database_partial")) {
	String[] exceptions = new String[1];
	exceptions[0] = Global.msqh.sqlite_db_filelink;
	PartDB.clearDatabaseAllButLecturer();
	Global.deleteDirRecursively(new File(Global.root + File.separator + Global.uploadTarget), exceptions);
}
// CLEAR DATABASE COMPLETELY
else if (q.equals("clear_database")) {
	// Clear MySQL database | SQLite is within uploadTarget therefore gets deleted with the filesystem files:
	PartDB.clearDatabase();
	// TODO Filesystem? (files generally get overriden at file-upload.
	// the only problem is that files remain which are no longer generated at upload.)
	Global.deleteDirRecursively(new File(Global.root + File.separator + Global.uploadTarget));
}


//====== DRAFT ACTIONS: ===========================================================//
if (request.getParameter("draft_filelink") != null) {

	/* Each draft has its own and separate draft form containing many draft parts: */
	String draft_filelink = request.getParameter("draft_filelink");


	//CHANGE DRAFT EXERCISE POSITION
	if (q.equals("change_draft_part_position") || q.equals("change_draft_parts_positions")
			|| (request.getParameterValues("draft_part_position_old[]") != null
					&& request.getParameterValues("draft_part_position_new[]") != null
					&& request.getParameterValues("draft_part_filelink[]") != null
			)
			|| (request.getParameter("draft_part_position_old") != null
					&& request.getParameter("draft_part_position_new") != null
					&& request.getParameter("draft_part_filelink") != null
				   // Only if old and new value differ the database has to be adapated.
					&& request.getParameter("draft_part_position_old") != request.getParameter("draft_part_position_new")
			)
	) {


		String[] draft_parts_positions_old; draft_parts_positions_old = request.getParameterValues("draft_part_position_old[]");
		String[] draft_parts_positions_new; draft_parts_positions_new = request.getParameterValues("draft_part_position_new[]");
		String[] draft_parts_filelinks; draft_parts_filelinks = request.getParameterValues("draft_part_filelink[]");
		if (draft_parts_positions_old == null || draft_parts_positions_new == null
				|| draft_parts_filelinks == null) {
			draft_parts_positions_old = new String[1];
			draft_parts_positions_new = new String[1];
			draft_parts_filelinks = new String[1];
			draft_parts_positions_old[0] = request.getParameter("draft_part_position_old");
			draft_parts_positions_new[0] = request.getParameter("draft_part_position_new");
			draft_parts_filelinks[0] = request.getParameter("draft_part_filelink");
		}
		int draft_parts_index; draft_parts_index = draft_parts_filelinks.length;
		while (--draft_parts_index > -1) {
			if (draft_parts_positions_new[draft_parts_index]
					.equals(draft_parts_positions_old[draft_parts_index])) {
				continue;
			}
			String sql;
			sql = "UPDATE `draftpartassignment`"
					+ " SET position = " + draft_parts_positions_new[draft_parts_index]
					// Both draft filelink and part filelink is the joint primary key:
					+ " WHERE sheetdraft_filelink = '" + draft_filelink + "'"
					+ " AND part_filelink = '" + draft_parts_filelinks[draft_parts_index] + "'";
			Global.query(sql, "Tried to update draft part position. " + draft_parts_positions_old[draft_parts_index]
					+ " &rarr; " + draft_parts_positions_new[draft_parts_index]);
		}

	}





	//REMOVE DRAFT EXERCISES
	if (q.equals("remove_draft_parts") || q.equals("remove_draft_part")
			|| request.getParameterValues("draft_part_to_be_removed[]") != null
			|| request.getParameter("draft_part_to_be_removed") != null
			) {
		String[] draft_parts_filelinks = request.getParameterValues("draft_part_filelink[]");
		// Because not all parts must be removed.
		String[] draft_parts_to_be_removed = request.getParameterValues("draft_part_to_be_removed[]");
		if (draft_parts_to_be_removed == null) {//automatically detect if only one part filelink given
			if (request.getParameter("draft_part_to_be_removed") != null) {
				draft_parts_to_be_removed = new String[1];
				draft_parts_to_be_removed[0] = request.getParameter("draft_part_to_be_removed");
			}
			else {
				// Because draft_parts_to_be_removed == null now the next branch body commands
				// will not be reached.
			}
		}
		// Preconditions met, i.e. Parameter given?
		if (draft_filelink != null && draft_parts_to_be_removed != null) {

			//Delete all parts that are assigned to this draft: (because otherwise they are orphans)
			String sql_base = "DELETE FROM `draftpartassignment`"
					// Sheetdraft filelink and part filelink together form the primary key.
					+ " WHERE `sheetdraft_filelink` = '" + draft_filelink + "'";
					//+ " AND `part_filelink` = '" + part_filelink + "'";
			ResultSet res; res = null;
			int resL;

			boolean all_draft_parts_to_be_removed_deleted; all_draft_parts_to_be_removed_deleted = true;

			List<String> draft_part_filelinks_not_successful; draft_part_filelinks_not_successful
				= new ArrayList<String>();

			String draft_part_to_be_removed_filelink;

			int draft_parts_to_be_removed_index;
			draft_parts_to_be_removed_index = draft_parts_to_be_removed.length;
			while (--draft_parts_to_be_removed_index > -1) {
				draft_part_to_be_removed_filelink
				= draft_parts_to_be_removed[draft_parts_to_be_removed_index];
				res = Global.query(sql_base
						+ " AND `part_filelink` = '" + draft_part_to_be_removed_filelink + "'"
				);

				if (res != null) {
//					res.last(); // For achieving correct row/result count.
//					resL = res.getRow(); // <-- row/result count.
//					res.beforeFirst(); // Necessary for loops starting correctly.
					resL = 0;
					while (res.next()) {
						++resL;
					}
					if (resL < 1) {
						draft_part_filelinks_not_successful.add(draft_part_to_be_removed_filelink);
						all_draft_parts_to_be_removed_deleted = false;
					}
				}
				else {
					draft_part_filelinks_not_successful.add(draft_part_to_be_removed_filelink);
					all_draft_parts_to_be_removed_deleted = false;
				}

				// Tackle memory leaks by closing result set and its statement properly:
				Global.queryTidyUp(res);

			}

			// Generate message: (deactivated even in debug mode to make things easier.)
			if (false && !all_draft_parts_to_be_removed_deleted) {
				String message;
				message = "Not successfully removed draft parts: <ul>";
				for (String draft_part_filelink_that_failed : draft_part_filelinks_not_successful) {
					message += "<li>" + draft_part_filelink_that_failed + "</li>";
				}
				message += "</ul>";
				Global.addMessage(message, "nosuccess");
			}
			else {
				//Global.addMessage(draft_parts_to_be_removed.length + " Aufgaben des Entwurfs erfolgreich gel&ouml;scht.", "success");
				Global.addMessage("Tried to remove " + draft_parts_to_be_removed.length + " parts.", "info");
			}
			// Tackle memory leaks by closing result set and its statement properly:
			Global.queryTidyUp(res);

		}
		else {
			Global.addMessage("For deleting a draft part a draft filelink as well as the draft_parts have to be given.", "nosuccess");
		}
	}




	// DRAFT DELETE
	if (q.equals("delete_draft")) {
		// Parameter given?
		if (draft_filelink != null) {
			String sql;
			ResultSet res; res = null;
			int resL; resL = 0;

			// Delete all parts that are assigned to this draft: (because otherwise they are orphans)
			sql = "DELETE FROM `draftpartassignment` WHERE `sheetdraft_filelink` = '" + draft_filelink + "'";
			res = Global.query(sql);
			if (res == null || resL == 0) {
				Global.addMessage("Resource ResultSet = " + res + " was null or result length = " + resL + " was zero.", "nosuccess");
			}
			else {
//				res.last(); // For achieving correct row/result count.
//				resL = res.getRow(); // <-- row/result count.
//				res.beforeFirst(); // Necessary for loops starting correctly.
				Global.addMessage("Alle Aufgaben des Entwurfs erfolgreich gel&ouml;scht.", "success");
			}
			// Tackle memory leaks by closing result set and its statement properly:
			Global.queryTidyUp(res);

			// Delete all already copied and standalone parts too? => depending on not isDraft?
			if (!Global.extractEndingDouble(draft_filelink).equals("draft.draft")) {
				// => has been generated at least once! => has parts!
				// => don't delete sheetdraft if not deleting everything! This everything deletion can be handled via the delete_sheet, initiated from the tree or list view!

				// TODO complete deletion: draft related in the filesystem too. <-- USE delete_sheet (see above)

//				/* As it should be separated this here really only deletes the draft part. The sheetdraft
//				must not be deleted from the database if this draft has been generated at least once! */
//				sql = "DELETE FROM `part` WHERE `sheetdraft_filelink` = '" + draft_filelink + "'";
//				res = Global.query(sql);
//				if (res == null || resL == 0) {
//					Global.addMessage("Resource ResultSet = " + res + " was null or result length = " + resL + " was zero.", "nosuccess");
//				}
//				else {
//					res.last();		   // For achieving correct row/result count.
//					resL = res.getRow();  // <-- row/result count.
//					res.beforeFirst();	// Necessary for loops starting correctly.
//					Global.addMessage("Alle kopierten Aufgaben des bereits einmal generierten Entwurfs in der Datenbank erfolgreich gel&ouml;scht.", "success");
//				}
//				// tackle memory leaks by closing result set and its statement properly:
//				Global.queryTidyUp(res);
			}
			else { // it's okay, there are no copied parts for a never before merged draft. Only references.
				// deleting the sheetdraft from the sheetdraft database table is now okay as there are no part copies in part.
				sql = "DELETE FROM `sheetdraft` WHERE `filelink` = '" + draft_filelink + "'";
				res = Global.query(sql);
				if (res != null) {
//					res.last(); // For achieving correct row/result count.
//					resL = res.getRow(); // <-- row/result count.
//					res.beforeFirst(); // Necessary for loops starting correctly.
					Global.addMessage("Entwurf erfolgreich gel&ouml;scht.", "success");
				}
				else {// (res == null || resL == 0) {
					Global.addMessage("Resource ResultSet = " + res + " was null or result lenght = " + resL + " was zero.", "nosuccess");
				}
				// Tackle memory leaks by closing result set and its statement properly:
				Global.queryTidyUp(res);
			}

		}
		else {
			Global.addMessage("For deleting a draft a filelink has to be given.", "nosuccess");
		}
	}





}
else if (q != null) {
	System.out.println(Global.addMessage("A draft filelink is required for all draft actions!", "nosuccess"));
}



// GENERAL: Each command action in a separate file with indicator action.<action>.<ending> !
String filelink = "action." + q + ".jsp";
if (Global.fileExists(filelink, pageContext)) {
//else {
	%>
	<jsp:include page='<%= filelink %>' />
	<%
}
%>

