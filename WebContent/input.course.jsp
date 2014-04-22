<%@page import="java.util.*,
	     aufgaben_db.Global,
	     java.sql.ResultSet;"
%>
<%
  String id = "course";
  if (request.getParameter("node_id") != null) {
	  id = request.getParameter("node_id");
  }

  if (request.getParameter("default") != null) { %>      
                <input type="hidden" name="course_old" class="required noSpecialChars"
                        id="<%= id %>" value="<% out.print(request.getParameter("default")); %>"
                        onkeyup="deactivateIfFilledOrActivateIfEmpty(this, '<%= id %>_select');"/>
                <!--
                    The input is hidden as at first teachers/lecturers should be motivated to use existing courses.
                    May be shown via JavaScript if already existing courses are not enough.
                 -->
                <input type="text" name="course" class="required noSpecialChars" style="display:none;"
                        id="<%= id %>" value="<% out.print(request.getParameter("default")); %>"
                        onkeyup="deactivateIfFilledOrActivateIfEmpty(this, '<%= id %>_select');" />
<% } else { %>
                <input type="text" name="course" class="required noSpecialChars"
                        id="<%= id %>" value=""
                        onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'course_select');"/>
<% } %>
                <select name="course_select" class="" id="<%= id %>_select"
                        onchange="document.getElementById('course').value=this.value;<%= request.getParameter("onchange") %>;">
                    <%
                    //fetch all distinct courses from database
                    ResultSet res = Global.query("SELECT DISTINCT `course` FROM `sheetdraft`");
                    //get length
                    int resL = 0;
                    if (res != null) {
	                    if (res.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
	                        resL = res.getRow();
	                        res.beforeFirst();/*because afterwards follows a next()*/
	                    }
                    }
                    //generate the option fields
                    if (resL == 0) {
                        out.print("<option selected='selected' disabled='disabled'>-------</option>");
                    } else {
                        out.print("<option selected='selected' disabled='disabled'>---" + resL + " " + Global.display("entries found") + ":----</option>");
                        while (res.next()) {
                            //add option
                            out.print("<option ");
                            out.print(" title='" + Global.decodeUmlauts(res.getString("course")) + "'");
                            if (res.getString("course").equals(request.getParameter("default"))) {
                                out.print(" selected='selected'");
                            }
                            out.print(" value='"+ res.getString("course") +"' ");
                            out.print(">" + Global.decodeUmlauts(res.getString("course")) + "</option>");
                        }
                        // tackle memory leaks by closing result set and its statement properly:
                        Global.queryTidyUp(res);
                    }
                    %>
                </select>         
                        
