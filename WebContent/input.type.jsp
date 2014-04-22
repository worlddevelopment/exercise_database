<%@page import="aufgaben_db.Global" %>
            
            
            <%//for determining if the values has been changed/edited.
            if (request.getParameter("default") != null) {
                out.println("<input type='hidden' name='type_old' class='required noSpecialChars' " 
                        + " id='type_old' value='"+ request.getParameter("default") +"'/>");
            }
            %>
            <!-- (tbd) better load from database? -->
            <select class="required noSpecialChars" name="type" id="type" onchange="<%= request.getParameter("onchange") %>;">
            <%
            for (Global.SheetTypes sheetType : Global.SheetTypes.values()) {
                out.println(
                        "<option " + (sheetType.name().equalsIgnoreCase(request.getParameter("default")) ? " selected='selected'" : "")
                       + "value='" + sheetType.name() + "'>"
                        + Global.display(sheetType.name().toLowerCase()) + "</option>"
                );
            }
            %>
            </select>
