<%@page import="java.text.DateFormat, java.text.SimpleDateFormat, java.util.Calendar, core.Global;" %>

<%
if (request.getParameter("default") != null) {
	out.println("<input type='hidden' name='semester_old' class='required noSpecialChars' "
			+ " id='semester_old' value='"+ request.getParameter("default") +"'/>");
}


DateFormat dF = new SimpleDateFormat("EEE,  MMM d, ''yy");
//Calendar now = new GregorianCalendar();//.getInstance();
// Offer all semesters from -5 to +5 years:
int YEAR_AMPLITUDE_TO_SHOW = 5;
int y = Global.now.get(Calendar.YEAR) - YEAR_AMPLITUDE_TO_SHOW - 1;
int mMin;// = Global.now.get(Calendar.MONTH);
int mMax;
//out.print("y = " + y + "<br />now[YEAR] = " + Global.now.get(Calendar.YEAR));
%>
	<select name="semester" class="required" id="semester_select" onchange="<%= request.getParameter("onchange") %>"
>
<%
//System.out.println("GregCal: " + dF.format(Global.now.getTime()));
int nowMonth = Global.now.get(Calendar.MONTH);
//out.println("nowMonth = " + nowMonth);
// YEAR-LOOP
while (++y < Global.now.get(Calendar.YEAR) + YEAR_AMPLITUDE_TO_SHOW) {
	// TERM-LOOP
	String[] semterms = {"SS", "WS"}; // TODO: PUT INTO A CONFIGURATION-FILE!
	for (String semterm : semterms) {
		// add option
		if (request.getParameter("default") != null) {
			out.print("<option value='" + semterm + "_" + y + "'"
					+ ( (semterm + "_" + y).equals(request.getParameter("default")) ? " selected='selected' ": "" )
					+">" + semterm + " " + y + "</option>");
		}
		else {
			mMin = 10 - 1; // <-- Oktober as first month of semester.
			mMax =  3 - 1; // <-- March as last month of semester.
			boolean semterm_condition = nowMonth >= mMin || nowMonth <= mMax;
			if (semterm == "SS") {
				mMin = 4 - 1; // <-- April as first month of semester.
				mMax = 9 - 1; // <-- September as last month of semester.
				semterm_condition = nowMonth >= mMin && nowMonth <= mMax;
			}
			out.print("<option value='" + semterm + "_" + y + "'"
					+ ((y == Integer.valueOf(Global.now.get(Calendar.YEAR))
							&& semterm_condition) ? " selected='selected' ": "")
					+">" + semterm + " " + y + "</option>");
		}
	}
}
%>
</select>

