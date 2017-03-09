<%response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
%>
<%@page import="java.sql.Connection"%>
<%@page import="db.*" %>

<%
// Attention: For AJAX the Global database connection can not be used.

// Connect to database
MysqlHelper mh = new MysqlHelper();
Connection con = mh.establishConnection(response);

//out.print("con" + connnn);
SQL_Methods sqlm = new SQL_Methods();
//sqlm.setStr("hop");
//out.print(sqlm.getStr());
sqlm.setConn(con);

String q = request.getParameter("q");
if(q.equals(null)) {
	//Global.addMessage("No action."); <- makes no sense as this is ajax!
	return;
}

String query = "";
if(request.getParameter("change").equals("semester")) {

	query = "SELECT DISTINCT semester FROM sheetdraft WHERE semester LIKE '" + q +"%'";
	out.print(sqlm.printQuery(query, "semester"));
}

if(request.getParameter("change").equals("course")) {

	query = "SELECT DISTINCT course FROM sheetdraft WHERE course LIKE '" + q +"%'";
	out.print(sqlm.printQuery(query, "course"));
}

if(request.getParameter("change").equals("lecturer")) {

	query = "SELECT DISTINCT lecturer FROM lecturer WHERE lecturer LIKE '" + q +"%'";
	out.print(sqlm.printQuery(query, "lecturer"));
}
%>
