        
        <%
        String type = "text"; //<- for the default type: text it's tested and working. 
        if (request.getParameter("type") != null) {//Generic type is hardly (not at all) used and not tested!
        	type = request.getParameter("type");
        }
        String varname = request.getParameter("name");
        if (varname == null) {
        	System.err.println("Request parameter varname that is used for building a generic input field was null in input.generic.jsp.");
        }
        String init = "";
        if (request.getParameter("default") != null) {
        	init = request.getParameter("default");
        	String varname_old = varname + "_old";
        	// Do we have to generate an array input field?
        	if (varname != null && varname.endsWith("[]")) {
                varname_old = varname.replaceAll("\\[\\]", "") + "_old[]";
            }
            out.println("<input type='hidden' name='" + varname_old + "' " + " value='" + init + "' "
            		+ " id='" + ( request.getParameter("id") != null ? request.getParameter("id") : "" )  + "'"
                    + " onfocus='"+ request.getParameter("onfocus") +"' onkeyup='"+ request.getParameter("onkeyup") +"'  />");
        }
        %>
        <input type="<%= type %>" name="<%=varname %>" value="<%=init %>" placeholder="<%=init %>"
                id="<%= request.getParameter("node_id") %>" 
                onfocus="<%= request.getParameter("onfocus") %>" onkeyup="<%= request.getParameter("onkeyup") %>"
                 />