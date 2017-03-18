<!--
<script type="text/javascript" src="jquery/toolTipPreview.js"></script>
-->
<%@page import="core.DocType"%>
<%@page import="java.util.ArrayList,java.util.List, java.util.Map, java.util.HashMap"%>
<%@page import="java.sql.ResultSet,
				java.sql.SQLException,
				core.Global" %>
<%
/* <!-- theoretically this bean works, unfortuneately it does not fit into this site until now
<jsp: useBean id="draft" class="core.Sheetdraft" scope="session"></jsp:useBean>
--> */
// Preconditions
if (Global.session == null) {
	Global.addMessage("Session was null in page 'drafts'!", "danger");
	return ;
}

// Load all drafts.
ResultSet res = Global.query("SELECT DISTINCT filelink FROM sheetdraft, lecturer"
		+ " WHERE"
		+ " (author = '" + Global.session.getAttribute("user") + "'"
			+ " OR (lecturer.lecturer = '" + Global.session.getAttribute("user") + "' AND lecturer_id = lecturer.id)"
		+ " )"
		+ " AND (is_draft = 1 OR is_draft = '1');" //<- TODO detect from filelink. draft.<ending> => isDraft == true
);



List<String> drafts = new ArrayList<String>();
// LIST ALL DRAFTS, CREATE BUTTONS FOR OPERATIONS TOO.
String draft_filelink;
while (res.next()) {
	// Make the filelinks of the drafts available.
	draft_filelink = res.getString("filelink");
	drafts.add(draft_filelink);
	/* (Important note: Without the question mark, a attempt is made
	to repeat the last action if this action had been executed via GET!
	The reason is that the already existent action link parameters are not reset. */
	%>
	<!-- DRAFTFORM -->
	<form action="?#draftrow<%=res.getRow() %>" method="post" class="draftform">
	<div class="draftrow" id="draftrow<%=res.getRow() %>">

		<!-- DRAFTDATA -->
		<div class="draftdata">


			<!-- DRAFT TARGET FILEFORMAT -->
			<fieldset style="display:inline">
			<label for="draft_<%=res.getRow() %>_target_fileformat_<%=res.getRow() %>">Target <%=Global.display("fileformat") %>:</label>
			<select id="draft_<%=res.getRow() %>_target_fileformat_<%=res.getRow() %>" name="draft_target_fileformat">

				<%
				out.print("<option value='DRAFT' selected='selected'>Derive from first part.</option>");
				for (DocType fileformat : DocType.values()) {
					String format = fileformat.name();
					//skip entries that shall never be converted to:
					if (!fileformat.isConversionTarget()) {
						continue ;
					}
					out.print("<option value='" + format + "' "+ ( format.equals(Global.getNativeFormatEnding(res.getString("filelink"))) ? " selected='selected' " : "") + ">" + format + "</option>");
				}
				%>
			</select>
			</fieldset>


			<!-- ACTION: MAKE DRAFT A REAL SHEETDRAFT (makes position fixed, creates a filesystem file
			in the desired fileformat or at least the native fileformat!) -->
			<button type="button" name="q" value="draft_to_sheet" class="btn btn-info" title="Draft -> Sheet."
					onclick="this.type='submit'; return true;">
				<i class="icon-book icon-white"></i>This Draft &rarr; <i class="icon-leaf icon-white"></i>Sheet (cannot be undone).
			</button>


			<!-- FILELINK -->
			<input type="hidden" name="draft_filelink" value="<% out.print(draft_filelink); %>" />

			<!-- FILELINK RENAME -->
			<input type="hidden" style="display:;" name="draft_part_filelink_old" value="<% out.print(draft_filelink); %>" />
			<input id='draft_filelink_<%=res.getRow() %>' style="display:;" type="text" name="draft_part_filelink" value="<% out.print(draft_filelink); %>"
					onkeyup="if (this.value != this.previousElementSibling.value) { this.nextElementSibling.style.display='inline'; } else { this.nextElementSibling.style.display='none'; } /*if (this.value == '' || this.value == this.placeholder) { this.value = this.previousElementSibling.value; }*/" />
			<button type="button" name="q" value="change_draft_filelink" class="btn btn-warning" title="rename" style="display:none;"
					onclick="this.type='submit'; return true;">
				<i class="icon-edit icon-white"></i>
			</button>
			<!-- ACTION: DELETE -->
			<button type="button" name="q" value="delete_draft" class="btn btn-danger" title="l&ouml;schen"
					onclick="this.type='submit'; return true;">
				<i class="icon-trash icon-white"></i>
			</button>
			<!-- SOLUTION POSITION -->
			<span style="display:inline-block;">
				<span style="display:inline-block;">
				<input id="draft_<%= res.getRow() %>_solution_position_none" type="radio" name="solution_position" value=""
					checked="checked" onclick="var draft_solution_index=0; var draft_solution_row; while(typeof ( draft_solution_row = document.getElementById('draft_<%= res.getRow() %>_solution_fieldset_' + (++draft_solution_index)) ) == 'object') { draft_solution_row.setAttribute('style', 'display:none;'); draft_solution_row.disabled=true; }"
					/>
				</span>
				<span style="display:inline-block; vertical-align:bottom;">
				<label for="draft_<%= res.getRow() %>_solution_position_none"
					onclick="var draft_solution_index=0; var draft_solution_row; while(typeof ( draft_solution_row = document.getElementById('draft_<%= res.getRow() %>_solution_fieldset_' + (++draft_solution_index)) ) == 'object') { draft_solution_row.setAttribute('style', 'display:none;'); draft_solution_row.disabled=true; }"
				>No solutions.</label>
				</span>
			</span>
			<span style="display:inline-block;">
				<span style="display:inline-block;">
				<input id="draft_<%= res.getRow() %>_solution_position_at_end" type="radio" name="solution_position" value="at end"
					onclick="var draft_solution_index=0; var draft_solution_row; while(typeof ( draft_solution_row = document.getElementById('draft_<%= res.getRow() %>_solution_fieldset_' + (++draft_solution_index)) ) == 'object') { draft_solution_row.setAttribute('style', 'display:none;'); draft_solution_row.disabled=false; }"
					/>
				</span>
				<span style="display:inline-block; vertical-align:bottom;">
				<label for="draft_<%= res.getRow() %>_solution_position_at_end" title="TODO: insert page-break for each format."
					onclick="var draft_solution_index=-1; var draft_solution_row; while(typeof ( draft_solution_row = document.getElementById('draft_<%= res.getRow() %>_solution_fieldset_' + (++draft_solution_index)) ) == 'object') { draft_solution_row.setAttribute('style', 'display:none;'); draft_solution_row.disabled=false; }"
				>Jointly at end.</label>
				</span>
			</span>
			<span style="display:inline-block;">
				<span style="display:inline-block;">
				<input id="draft_<%= res.getRow() %>_solution_position_after_part" type="radio" name="solution_position" value="after part"
					onclick="var draft_solution_index=-1; var draft_solution_row; while(typeof ( draft_solution_row = document.getElementById('draft_<%= res.getRow() %>_solution_fieldset_' + (++draft_solution_index)) ) == 'object') { draft_solution_row.setAttribute('style', 'display:inline;'); draft_solution_row.disabled=false; }"
					/>
				</span>
				<span style="display:inline-block; vertical-align:bottom;">
				<label for="draft_<%= res.getRow() %>_solution_position_after_part"
					onclick="var draft_solution_index=-1; var draft_solution_row; while(typeof ( draft_solution_row = document.getElementById('draft_<%= res.getRow() %>_solution_fieldset_' + (++draft_solution_index)) ) == 'object') { draft_solution_row.setAttribute('style', 'display:inline;'); draft_solution_row.disabled=false; }"
				>Directly after part.</label>
				</span>
			</span>

			<!-- IMAGELINK -->
			<div class='draft_img'>
				<img src="<% out.print(Global.convertToImageLink(draft_filelink)); %>" />
			</div>

		</div>


		<!-- SHOW HIDE DRAFT EXERCISES -->
		<a class="btn btn-secondary" style="color:black !important;"
				onclick="if (this.nextElementSibling.style.display == 'none') { this.nextElementSibling.style.display='block' } else { this.nextElementSibling.style.display='none' }">
			<i class="icon-zoom-in icon-white"></i>Toggle contained Parts (show/hide).
		</a>


		<!-- DRAFT EXERCISES (ASSIGNED TO DRAFT) -->
		<div class="draftpartrows" style="display:none;">
		<%
		// Load parts of the current draft item.
		String str_query = "SELECT part.*, dea.position"
				+ " FROM part, draftpartassignment dea"
				+ " WHERE dea.sheetdraft_filelink = '" + draft_filelink +"'"//select first
				+ " AND part.filelink = dea.part_filelink"//join remaining
				+ " ORDER BY position";
		ResultSet res1 = Global.query(str_query);
//		 res1.last();
		List<Map<String, Object>> res1Rows = new ArrayList<Map<String, Object>>();
		while (res1.next()) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("filelink", res1.getString("filelink"));
			resMap.put("position", res1.getString("position"));
			res1Rows.add(resMap);
		}
		int count = res1.getRow();
		count = res1Rows.size();
		if (count == 0) {
			out.print("- no parts contained in this draft -");
			//continue ;Continuing makes trouble here as the HTML markup will not be closed properly.
		}
		else {
//			 res1.beforeFirst();
//			 while (res1.next()) {
		int res1Rows_index = -1;
		while (++res1Rows_index < res1Rows.size()) {
			Map<String, Object> res1Row =  res1Rows.get(res1Rows_index);
			String draft_part_filelink = (String)res1Row.get("filelink");//stored filelinks are relative filelinks!
			String part_position = (String)res1Row.get("position");
			%>
				<!-- DRAFT EXERCISE ROW -->
				<div class="draft_part_row">
					<!-- DRAFT EXERCISE FILELINK -->
					<input type="hidden" name="draft_part_filelink[]" value="<% out.print(draft_part_filelink); %>" />

					<!-- DRAFT EXERCISE POSITION -->
					<input name="draft_part_position_old[]" type="hidden" value="<%=part_position %>" class="" title="Change Position" />
					<input name="draft_part_position_new[]" value="<%=part_position/*#*/ %>" placeholder="<%=part_position %>" class="" title="Change Position"
					onfocus="makeNextButtonSubmit(this);" onkeyup="makeNextButtonSubmit(this);" />
					<!--
					<a style="display:none;" href="javascript:this.form.submit();" onclick="return false;">Aktualisieren </a>
					<a style="display:none;" href="javascript:this.form.submit();" onclick="return false;">Reset</a>
					-->
					<!-- ACTION: CHANGE EXERCISE POSITION -->
					<!--NOTE: Now position is modified IF and only if old and new value differ!
					submit makes this button the default action -->
					<button type="button" style="display:none;" disabled="disabled" name="q" value="change_draft_part_position" class="btn btn-warning" title="Update all!">
					  &larr;<i class="icon-refresh icon-white"></i>&nbsp;&amp;&nbsp;<i class="icon-ok icon-white"></i> all
					</button>
					<div style="display:inline-block;">
						<!-- ACTION: DELETE -->
						<label class="btn btn-danger" for="draft_<%=res.getRow() %>_part_to_be_removed_<%=res1Rows_index/*res1.getRow()*/%>">
						   <%=Global.display("Remove") + " " + Global.display("multiple") %>?
						</label>
						<input id="draft_<%=res.getRow() %>_part_to_be_removed_<%=res1Rows_index/*res1.getRow()*/ %>" type="checkbox"
							name="draft_part_to_be_removed[]" value="<%=draft_part_filelink %>" />
					</div>
					<button type="button" name="q" value="remove_draft_parts" class="btn btn-danger" title="remove this and update all"
						onclick="this.previousElementSibling.lastElementChild.checked=true; this.type='submit';">
					  &larr;<i class="icon-trash icon-white"></i>&nbsp;&amp;&nbsp;<i class="icon-ok icon-white"></i>all
					</button>
					<!-- actualize
					<button type="button" name="q" value="change_draft_part_position" class="btn btn-warning" title="Change order!">
					  <i class="icon-goal icon-white"></i>
					</button>
					-->

				</div>
				<!-- IMAGE -->
				<div class='draft_part_img'>
				<label class="btn btn-danger" for="draft_<%=res.getRow() %>_part_to_be_removed_<%=res1Rows_index/*res1.getRow()*/%>">
				  <img src="<% out.print(Global.convertToImageLink(draft_part_filelink)); %>"
					  alt="<% out.print(draft_part_filelink); %>" title="<% out.print(draft_part_filelink); %>" />
				</label>
				</div>


			<%

//			if (res_solution.next()) {
//				String draft_solution_filelink = res_solution.getString("filelink");//stored filelinks are relative filelinks!
//				String part_position = res_solution.getString("position");
				String draft_solution_filelink = Global.resolveCorrespondingFilelinkTo(draft_part_filelink);
				if (draft_solution_filelink != null) {

				}
				else {
					System.out.println("No corresponding part/solution found to "+ draft_part_filelink + ".");
					draft_solution_filelink = "--No corresponding part/solution found.--";
				}
				%>
					<!-- DRAFT EXERCISE ROW -->
					<fieldset id="draft_<%= res.getRow() %>_solution_fieldset_<%=res1Rows_index/*res1.getRow()*/ %>" class="draft_part_row" style="display:none;" disabled="disabled">
						<div>
						<!-- DRAFT EXERCISE FILELINK -->
						<input type="hidden" name="draft_solution_filelink[]" value="<% out.print(draft_solution_filelink); %>" />

						<!-- ACTION: TOGGLE EXERCISE'S CORRESPONDING SOLUTION -->
						<button type="button" name="q" value="" class="btn btn-danger" title="deactivate"
							onclick="if (this.title=='deactivate') {this.className='btn btn-success';title='activate'} else { this.className='btn btn-danger'; title='deactivate'} return false;">
						  &larr;<i class="icon-trash icon-white"></i>&nbsp;&amp;&nbsp;<i class="icon-ok icon-white"></i>all
						</button>
						<!-- ACTION: ADD TO DRAFTEXERCISEASSIGNMENT FOR ALLOWING TO POSITION IT FREELY. -->
						<div style="display:inline-block;">
							<label class="btn btn-info" for="draft_<%=res.getRow() %>_solution_to_be_assigned_<%=res1Rows_index/*res1.getRow()*/%>">
							   <%=Global.display("Assign") + " " + Global.display("directly") %>?
							</label>
							<input id="draft_<%=res.getRow() %>_solution_to_be_assigned_<%=res1Rows_index/*res1.getRow()*/ %>" type="checkbox"
								name="draft_solution_to_be_assigned[]" value="<%=draft_solution_filelink %>" />
						</div>
						<button type="button" name="q" value="assign_solution_directly" class="btn btn-danger" title="deactivate"
							onclick="document.getElementById('draft_<%=res.getRow() %>_solution_to_be_assigned_<%=res1Rows_index/*res1.getRow()*/ %>').checked=true; this.type='submit'; this.form.submit();">
						  &larr;<i class="icon-trash icon-white"></i>&nbsp;&amp;&nbsp;<i class="icon-ok icon-white"></i>all
						</button>
						</div>
						<!-- IMAGE -->
						<div class='draft_part_img'>
						<label class="btn btn-info" for="draft_<%=res.getRow() %>_solution_to_be_assigned_<%=res1Rows_index/*res1.getRow()*/%>">
						  <img src="<% out.print(Global.convertToImageLink(draft_solution_filelink)); %>"
							  alt="<% out.print(draft_solution_filelink); %>" title="<% out.print(draft_solution_filelink); %>" />
						</label>
						</div>
					</fieldset>
				<%
//			}
			}
		}/*else -END*/
		// Tackle memory leaks by closing result set and its statement properly:
		Global.queryTidyUp(res1);

		%>
			<div class="clearBoth" style="clear:both;">&nbsp;</div><!-- CLEAR BOTH -->

			</div><!-- DRAFT EXERCISES (ASSIGNED TO DRAFT) -END -->


		</div>
	</form><!-- DRAFTFORM -END -->
<%



}
// Tackle memory leaks by closing result set and its statement properly:
Global.queryTidyUp(res);



// No drafts found?
if(drafts == null || drafts.size() == 0) {
	out.print("Ihre Entwurfskammer beinhaltet zur Zeit keine Prototypen (&Uuml;bungsbl&auml;tter oder Klausur).");
	out.print("<br />");
	/* out.print("Erstellen Sie entweder hier einen Entwurf oder lassen Sie den Schritt aus und"
			+ " gehen sogleich zur Startseite, um Aufgaben f&uuml;r einen beliebigen Entwurf auszuw&auml;hlen."
			+ "<form method='get' action='?id=drafts'>"
			+ "<button class='btn btn-secondary' name='q' value='create_draft'>Entwurf erstellen!</button>"
			+ "</form>");
	*/
	out.print("<form method='get' action='?id=start'>"
			+ "<button class='btn btn-secondary' name='id' value='start'>Zur Startseite (Aufgaben ausw&auml;hlen)!</button>"
			+ "</form>");
	out.print("<br />");
	out.print("<h3>Wie erstelle ich einen Entwurf?</h3>");
	out.print("<p>Auf der <a href='?id=start'>Startseite</a>"
			+ " suchen Sie nach Aufgaben. Entweder verwenden Sie daf&uuml;r die Filter- oder Suchfunktion oder aber Sie"
			+ " w&auml;hlen eine geeignete Baumansicht und hangeln sich im Baum bis zu der Veranstaltung und"
			+ " dem Blatt durch, wessen Aufgaben Sie gern wiederverwenden m&ouml;chten.</p>");
	out.print("<p>Im HTML-Modus bleiben nur die wichtigsten Formatierungen erhalten.</p>");
	//out.print("<br />Einzig TeX- oder PDF-Dokumente k&ouml;nnen derzeit erstellt werden.");

	out.print("<h3>Wozu ist diese Seite da?</h3>");
	out.print("<p>Auf dieser Seite k&ouml;nnen Sie die Entw&uuml;rfe verwalten."
			+ " Dazu z&auml;hlt die Reihenfolge und das Zielformat festzulegen als auch Aufgaben im"
			+ " Nachhinein hinzuzuf&uuml;gen oder zu entfernen. Die Entw&uuml;rfe/Aufgabensammlungen"
			+ " k&ouml;nnen auch im Ganzen gel&ouml;scht werden.</p>");
	out.print("<p>Sie können auch wieder ganze, vollwertige eigentst&auml;ndige Bl&auml;tter aus Ihren"
	+ " Entw&uuml;rfen generieren. Sollte sich eine von Ihnen gew&auml;hlte Aufgabe &auml;ndern, so"
	+ " k&ouml;nnen Sie selbst entscheiden, ob Sie Ihre Entw&uuml;rfe von neuem generieren oder in"
	+ " in der alten Form belassen.</p>");
}


%>
