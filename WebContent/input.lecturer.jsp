<%@page import="aufgaben_db.Global, java.sql.ResultSet" %>

<%
String id = "lecturer";
if (request.getParameter("node_id") != null) {
	id = request.getParameter("node_id");
}

if (request.getParameter("default") != null) { %> 
                <input type="hidden" name="lecturer_id_old" class="required noSpecialChars"
                    id="<%= id %>_id_old" value="<% out.print( request.getParameter("default") ); %>"
                    onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'lecturer_id_select');"/>
<%
} 
if (request.getParameter("lecturer") != null) { %>
                <input type="text" name="lecturer" class="required noSpecialChars" style="display:none;"
                    id="<%= id %>" value="<% out.print( Global.decodeUmlauts(request.getParameter("lecturer")) ); %>"
                    onkeyup="deactivateIfFilledOrActivateIfEmpty(this, '<%= id %>_id_select');"/>
<%
}
else {
%>
                <input type="text" name="lecturer" class="required noSpecialChars"
                    id="<%= id %>" value=""
                    onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'lecturer_id_select');"/>
<%
}
%>
                <select name="lecturer_id_select" class="" id="<%= id %>_id_select"
                onchange="document.getElementById('<%= id %>').value=this.options[this.selectedIndex].text; <%= request.getParameter("onchange") %>;">
                    <%
                    //fetch all distinct lecturer from database
                    ResultSet res = Global.query("SELECT `id`, `lecturer` FROM `lecturer`" /*LIMIT 0, 30*/);
                    if (res == null || !res.next()) {
                        out.print("<option disabled='disabled'>---(leer)---</option>");
                    } else {
                        res.beforeFirst();//Because above we already increase by one!!
                        while (res.next()) {
                            //add option
                            
                            out.print("<option ");
                            if (request.getParameter("default") != null
                                    && (res.getString("id").equals(request.getParameter("default"))) ) {
                                out.print(" selected='selected'");
                            }
                            out.print("value='"+ res.getString("id") + "," + res.getString("lecturer")
                                      +"'>");
                            out.print( Global.decodeUmlauts(res.getString("lecturer")) );
                            out.print("</option>");
                        }
                        // tackle memory leaks by closing result set and its statement properly:
                        Global.queryTidyUp(res);
                    }
                    %>
                </select>
                
