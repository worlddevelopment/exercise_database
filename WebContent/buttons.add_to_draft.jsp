<%@page import="core.DocType, java.util.Map
		, core.Global
		, core.PartDB, core.Sheetdraft"
%>


	<button id="add_to_draft_btn" name="q" value="add_to_draft" class="btn btn-primary" onclick="updateQueryString('id');updateQueryString('q')">
		<i class="icon-plus-sign"></i>
<%
out.print(Global.translate("add to draft"));
%>:
	</button>
	<select name="destination_draft_filelink">
		<option value="-1"><% out.print(Global.display("new draft")); %></option>


<%
// For not generating a conflict if this file is included somewhere else where
// session_user is already defined.
String buttons__add_to_draft__session_user = request.getParameter("session_user");
if (buttons__add_to_draft__session_user != null) {
	// LOAD USER DRAFTS.
	// false because drafts always belong to the author/uploader/deriving and not the lecturer
	PartDB.loadAllSheetdrafts(buttons__add_to_draft__session_user, false);
	Map<String, Sheetdraft> loadedDrafts = PartDB.getLoadedDraftsOnly();
	Sheetdraft latestChangedDraft = PartDB.getLatestChangedDraft();

	// USER DRAFTS TO BE LOADED PRIOR TO THIS OR ON THE FLY IN GET ALL...!
	// latest changed draft
	// No sheetdrafts? This would imply this one being an empty dummy Sheetdraft?
	//if (!latestChangedDraft.getId() == -1) {
	if (latestChangedDraft != null
			&& (latestChangedDraft.getFilelink() != null
			|| !latestChangedDraft.getFilelink().equals(""))
			) {
		out.println("<option value='" + latestChangedDraft.getFilelink()
				+ "' selected='selected'>"
				Global.translate("last draft that has been added to") + "("
				+ Global.decodeUmlauts(latestChangedDraft.getFileName()) + ")</option>");
	}

	// available drafts
	for (Sheetdraft draft : loadedDrafts.values()) {
	%>
		<option value="<%=draft.getFilelink() %>"><%=Global.extractFilename(draft.getFilelink())%></option>

	<%
	}

}
//else
//buttons__add_to_draft__session_user = "";//user = Global.session_user;


%>
	</select>
	<script type="text/javascript" src="js/validateSelectionDependingOnButton.js"></script>
