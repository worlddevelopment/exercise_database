<%@page import="java.util.List, java.util.ArrayList,java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="core.Global, core.Part, core.Sheetdraft, core.DocType"%>
<jsp:useBean id="draft" class="core.Sheetdraft" scope="session"></jsp:useBean>
<%
/**
At this point all the decisions of target-fileformat, which parts to add, which order, ...
should have been made.
If all preconditions are fulfilled, then part files are created and its contents
become editable while still keeping track of the original sheetdraft it comes from.

Here happens:
* A draft will be turned into a full-fledged sheet. That means the assigned/referenced
  parts will merge into a filesystem file.
* The references are eventually turned into real parts by inserting them as semi-doubles
  where only the sheetdraft_it_belongs_to database table field differs.
  This allows for a possible conversion of filetypes prior to merging the parts into a sheet and
  making real of referenced/assigned parts. (Thus is_native_format would differ too, while
  the plainText content remains equal. Even more important: The part filelink will then be
  different as it's a different format than the original part fileformat! Hence it's then no
  longer possible to let the parts point to other sheetdrafts' part filesystem partfiles.)

REMARK: If a sheetdraft from database table sheetdraft has attribute is_draft=1 but there
are no part assignments in draftpartassignment database table then (if it's not a newly created
draft) it is a draft that turned into a sheet, still referencing the assigned parts
but the parts have their own representation in the database table part to allow
for changing the content of parts later on -- The originsheetdraft is stored
in the part table too. This can be used to know where the part came from, where
the original can be found if something went wrong and the content has to be restored or
for statistics, i.e. how often one sheet/part was used for other sheet/drafts.

*/

//TODO CHECK THAT EXERCISE POSITION IS WORKING PROPERLY. CURRENTLY IT IS SORTED
// in drafts.in.jsp according to position ascending, so that should work without
// forwarding the new part position.

// Preconditions not met?
if (request.getParameter("draft_filelink") == null//target_filelink
		|| request.getParameterValues("draft_part_filelink[]") == null
		) {
	Global.addMessage("Either no parts' filelinks have been transmitted"
			+ " or no target sheetdraft filelink was given. Without those"
			+ " nothing can be joined into the latter.", "nosuccess");
	return ;
}

String target_sheetdraft_filelink; target_sheetdraft_filelink
		= request.getParameter("draft_filelink");

String[] filelinks = (String[]) request.getParameterValues("draft_part_filelink[]");
String[] filelinks_solution = (String[]) request.getParameterValues("draft_solution_filelink[]");
if (filelinks.length < 1) {
	Global.addMessage("Parts' filelinks array that was given was empty!", "nosuccess");
	return ;
}


// FILELINKS CORRESPONDING EXERCISE/SOLUTION
if (filelinks_solution != null && filelinks_solution.length > 0) {
	// filter empties:
	List<String> l = new ArrayList<String>();
	for (String f : filelinks_solution) {
		if (f != null && !f.isEmpty()) {
			l.add(f);
		}
	}
	// Now that we have the size of the filtered items:
	filelinks_solution = new String[l.size()];
	int lL = l.size();
	for (int i = 0; i < lL; ++i) {
		filelinks_solution[i] = l.get(i);
	}
}

if (filelinks_solution != null && filelinks_solution.length > 0) {
	String[] filelinks_tmp = filelinks;
	// Create array with enough space for parts and solutions:
	filelinks = new String[filelinks_tmp.length + filelinks_solution.length];
	// Merge:
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
		else if (request.getParameter("draft_solution_position_after_part") != null) {
			int filelinks_tmp_index = -1;
			int filelinks_solution_index = -1;
			while (++filelinks_tmp_index < filelinks_tmp.length
					&& filelinks_index < filelinks.length
					&& ++filelinks_solution_index < filelinks_solution.length) {

				filelinks[++filelinks_index] = filelinks_tmp[filelinks_tmp_index];			// <- first part
				filelinks[++filelinks_index] = filelinks_solution[filelinks_solution_index];// <- then corresponding

			}

		}

	}

}// FILELINKS CORRESPONDING EXERCISE/SOLUTION -END


// Note: Not needed as long as the filelinks are given in the correct order (an array is ordered, JavaScript gives them ordered too.).
//String[] part_position_old = (String[]) request.getParameterValues("draft_part_position_old[]");
//String[] part_position_new = (String[]) request.getParameterValues("draft_part_position_new[]");
//if (part_position_old == null || part_position_old.length < 1) {
//	Global.addMessage("No part positions (old) given! For saving the extra time needed for adapting the old"
//			+ " value to the new value this should be given and forwarded to the merge functions.", "warning");
//	return ;
//}
//else if (part_position_old == null || part_position_old.length < 1) {
//	Global.addMessage("No part positions (new) given! For saving the extra time needed for adapting the old"
//			+ " value to the new value this should be given and forwarded to the merge functions.", "warning");
//	return ;
//}


// TODO How to ensure a file ending is always given? Perhaps:
// 1) Make draft a target option (as default, this then implies automatic mode).
// 2) Remove file endings from the filename change field. (then it has to be ensured, that the filename only is changed on submit and not the complete filelink as it is currently!)
// 3) If a supported fileending can be found in the filename field, then automatically assume it is the desired target fileformat. It can't be the original fileformat as this is fixed as '.draft.'.
// 4) Take this fileending and set the select list to the default value (draft).
// This implies Automatic mode and thus it is first checked for target sheetdraft filename ending.
// If not then the parts are examined and if an part fileending (this time it has to be double ending! TODO really?)
// has a supported fileformat ending we just start taking the first part's fileformat as target fileformat.

// A Generic Approach - Automatic mode:
String target_filetype;
target_filetype = request.getParameter("draft_target_fileformat");
if (target_filetype == null || target_filetype.equalsIgnoreCase("draft")) {
	//Sheetdraft filename has fileending - no matter if double or single one?
	target_filetype = Global.extractEnding(target_sheetdraft_filelink);
	if (target_filetype == null
			// Is unsupported file type?
			||  DocType.getByEnding(target_filetype) == null) {

		// Determine the fileformat of the target_sheetdraft and use that as the target format candidate.
		target_filetype = Global.extractEnding(target_sheetdraft_filelink);

		if (target_filetype == null //Is unsupported file type?
				||  DocType.getByEnding(target_filetype) == null) {

			//Determine the fileending from the first part and use that as the target filetype.
			target_filetype = Global.extractEnding(filelinks[0]); // <-- existence is checked in preconditions

			if (target_filetype == null
					//Is unsupported filetype?
					|| DocType.getByEnding(target_filetype) == null)
					//|| target_filetype.equalsIgnoreCase("draft"))
			{

				// UNLIKELY because existing parts can't have a unsupported filetype because they were extracted
				// from a sheet that was supported, otherwise they had not been extracted.
				System.out.println("Target filetype could not be derived of the first part: "
						+ filelinks[0] + " Target format result: " + target_filetype);
				return ;

			}
		}

	}
}

// Set the target type we settled on as the file ending:
String target_sheetdraft_filelink_initial = target_sheetdraft_filelink;
target_sheetdraft_filelink = Global.replaceEnding(target_sheetdraft_filelink, target_filetype.toLowerCase());

// Redirect/correct target page:
Global.redirectTo("drafts");


Global.joinToFile(/*part_*/filelinks, target_filetype.toLowerCase(), target_sheetdraft_filelink
//		, /*part_position_old <-- mostly equal to new.*/new String[] {"-1", "-1"}
//		, part_position_new <-- this is not necessary as the filelinks are given in the correct order
		);
//Global.joinToFile(/*part_*/filelinks, target_filetype, target_sheetdraft_filelink, part_position_new, part_position_old);



// If we are still alive here, then update the sheetdraft's filelink in the database:
// Is there any difference?
if (!target_sheetdraft_filelink_initial.equals(target_sheetdraft_filelink)) {
	Global.query("UPDATE sheetdraft SET filelink = '" + Global.removeRootPath(target_sheetdraft_filelink) + "'"
			+ " WHERE filelink = '" + Global.removeRootPath(target_sheetdraft_filelink_initial) + "'");
	// Update the in the draft referenced parts, otherwise the draft will be shown as empty on the drafts page.
	Global.query("UPDATE draftpartassignment SET sheetdraft_filelink = '" + Global.removeRootPath(target_sheetdraft_filelink) + "'"
			+ " WHERE sheetdraft_filelink = '" + Global.removeRootPath(target_sheetdraft_filelink_initial) + "'");
	/* TODO Shall the parts be checked for a sheetdraft_filelink foreign key? Usually no, because a draft can't
		have parts. So if this target_filelink ends on draft.draft there should be no need to rename in the
		part database table too.
	*/
}


%>

