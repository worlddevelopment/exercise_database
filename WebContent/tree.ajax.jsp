<%response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
%>



<%@page import="swp.SQL_Methods" %>
<!--
<script type="text/javascript" src="jquery.treeview/demo/demo.js">
</script>
-->
<%
//ANSICHT | VIEW
String varname_chosen = request.getParameter("ansicht");
if (varname_chosen == null) {
	//This is for a version, when javascript is deactivated in the browser!
	//It is functional!! -- but we wanted to save bandwidth.
//	/*statically called by start.in.jsp -- listing loaded completely*/
//	request.setAttribute("varname_chosen", "semester");
//	request.setAttribute("treeviewnum", "0");
//	response.getWriter().write("<div id='start_list_semester' class='start_list' style=''>");
//	//now dynamically - one call, one method, no more multiple source here
//	SQL_Methods.createTreeView(request, response);
//	response.getWriter().write("</div>");
//	
//	request.setAttribute("varname_chosen", "lecturer");
//	request.setAttribute("treeviewnum", "1");
//	response.getWriter().write("<div id='start_list_lecturer' class='start_list' style=''>");
//	//now dynamically - one call, one method, no more multiple source here
//	SQL_Methods.createTreeView(request, response);
//	response.getWriter().write("</div>");
//	
//	request.setAttribute("varname_chosen", "course");
//	request.setAttribute("treeviewnum", "2");
//	response.getWriter().write("<div id='start_list_course' class='start_list' style=''>");
//	//now dynamically - one call, one method, no more multiple source here
//	SQL_Methods.createTreeView(request, response);
//	response.getWriter().write("</div>");
//	
//	request.setAttribute("varname_chosen", "type");
//	request.setAttribute("treeviewnum", "3");
//	response.getWriter().write("<div id='start_list_type' class='start_list' style=''>");
//	//now dynamically - one call, one method, no more multiple source here
//	SQL_Methods.createTreeView(request, response);
//	response.getWriter().write("</div>");
}
else {/*ajax request*/
	//now dynamically - one call, one method, no more multiple source here
    SQL_Methods.createTreeView(request, response);
}
%>
 
<!-- 
<div id="ajaxcontent">

</div> -->
