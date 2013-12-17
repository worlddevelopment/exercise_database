<%@page import="java.util.*, java.text.*,
        aufgaben_db.*,
        HauptProgramm.DocType,
        java.net.URLDecoder,
        java.sql.ResultSet;" %>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");

//Preconditions not met?
if (request.getParameter("filelink") == null) {
	Global.addMessage("No filelink or sheetdraft id was given. Without that, nothing can be edited."
		    +" Use the treeview of the start page for editing sheetdrafts.", "warning");
	return ;
}


%>
<jsp:include page="js/validateForm.js.jsp" />
<%




String filelink = URLDecoder.decode(request.getParameter("filelink"), "utf-8");

//The values as so far assignment made available for later use.
String semester = "";
String course = "";
int lecturer_id = 0;
String lecturer = "N.N.";
String type = "";//request.getParameter("type");

String description = "";


//LOAD required data from database (exactly one sheet/draft).
ResultSet res0 = Global.query(
		"SELECT sheetdraft.*, lecturer.lecturer"
	    + " FROM sheetdraft, lecturer"
		+ " WHERE filelink = '" + filelink + "'"
		+ " AND lecturer.id = sheetdraft.lecturer_id"
);

while (res0.next()) {
	semester = res0.getString("semester");
	course = res0.getString("course");
	lecturer_id = res0.getInt("lecturer_id");
	lecturer = res0.getString("lecturer");
	type = res0.getString("type");
	
	description = res0.getString("description");
}


%>











<!-- FORM -->
<div id="form">
<!-- MULTIPART CONTENT FORM
request.getAttribute("attr") will be NULL if not in action part!!! if multiform/form-data
see also http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
-->
<form class="cmxform" id="commentForm" method="post"  action="index.jsp?id=edit&q=edit_sheet#add_result">
   <!-- To steer the ship. -->
   <!--
   <input type="hidden" name="id" value="edit" /><!-- load the edit page -- >
   <input type="hidden" name="q" value="edit_sheet" />
   -->
   <p class="auto-style1 screenshot">Blatt hinzuf&uuml;gen:
        <a id="info_tooltip" href='#' rel="tooltip" title="Es sind nur alphanumerische und _ . ) ( Leerzeichen /  Symbole erlaubt.">
            <img src="pictures/info3.png" />
        </a>
   </p>
    <table >
        
        <!-- SEMESTER -->
        <tr>
            <td><label for="semester">Semester: *</label></td>
            <td>
               <!--
               <input type="text" name="semester" class="required noSpecialChars" id="semester"/>
               -->
            
                <% DateFormat dF = new SimpleDateFormat("EEE,  MMM d, ''yy");
                //Calendar now = new GregorianCalendar();//.getInstance();
                //offer all semesters from -5 to +5 years
                int YEAR_AMPLITUDE_TO_SHOW = 5;
                int y = Global.now.get(Calendar.YEAR) - YEAR_AMPLITUDE_TO_SHOW - 1;
                int mMin;// = Global.now.get(Calendar.MONTH);
                int mMax;
                //out.print("y = " + y + "<br />now[YEAR] = " + Global.now.get(Calendar.YEAR));
                %>
                <select name="semester" class="required" id="semester_select"
                >
                <%
                //System.out.println("GregCal: " + dF.format(Global.now.getTime()));
                int nowMonth = Global.now.get(Calendar.MONTH);
                //out.println("nowMonth = " + nowMonth);
                //YEAR-LOOP
                while (++y < Global.now.get(Calendar.YEAR) + YEAR_AMPLITUDE_TO_SHOW) {
                    //TERM-LOOP
                    String[] semterms = {"SS", "WS"};//TODO: PUT IN CONFIGURATION-FILE!
                    for (String semterm : semterms) {
                        //add option
                        mMin = 10 - 1; /*<-- Oktober as first month of semester.*/
                        mMax =  3 - 1; /*<-- March as last month of semester.*/
                        boolean semterm_condition = nowMonth >= mMin || nowMonth <= mMax;
                        //if (semterm == "SS") {
                        //    mMin = 4 - 1; /*<-- April as first month of semester.*/
                        //    mMax = 9 - 1; /*<-- September as last month of semester.*/
                        //    semterm_condition = nowMonth >= mMin && nowMonth <= mMax;
                        //}
                        out.print("<option value='" + semterm + "_" + y + "'"
                        + ( (semterm + "_" + y).equals(semester) ? " selected='selected' ": "" )
                        +">" + semterm + " " + y + "</option>");                 
                    }
                }
                %>
                </select>
            </td>
            
        </tr>
        
        
        <!-- COURSE -->
        <tr>
            <td><label for="course">Veranstaltung: *</label></td>
            <td><input type="text" name="course" class="required noSpecialChars"
             id="course" value="<% out.print(course); %>" onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'course_select');"/>
                <select name="course_select" class="" id="course_select"
                 onchange="document.getElementById('course').value=this.value">
                    <%
                    //fetch all distinct courses from database
                    ResultSet res = Global.query("SELECT DISTINCT `course` FROM `sheetdraft`");
                    //get length
                    int resL = 0;
                    if (res.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
                        resL = res.getRow();
                        res.beforeFirst();/*because afterwards follows a next()*/
                    }
                    //generate the option fields
                    if (res == null || resL == 0) {
                        out.print("<option disabled='disabled'>-------</option>");
                    } else {
                        while (res.next()) {
                            //add option
                            out.print("<option ");
                            if (res.getString("course").equals(course)) out.print(" selected='selected'");
                            out.print(">" + Global.decodeUmlauts(res.getString("course")) + "</option>");
                        }
                    }
                    %>
                </select>
            </td>
            
        </tr>


        <!-- TYPE -->
        <tr>
            <td><label for="type">Typ: *</label></td>
            <td>
            <!-- (tbd) better load from database? -->
            <select class="required noSpecialChars" name="type" id="type">
            <option <% if (type.equals("Uebung")) out.print(" selected='selected'"); %>
                value="Uebung">Übung</option>
            <option <% if (type.equals("Loesung")) out.print(" selected='selected'"); %>
                value="Loesung">Lösung</option>
            <option <% if (type.equals("Klausur")) out.print(" selected='selected'"); %>
                value="Klausur">Klausur</option>
            <option <% if (type.equals("Klausurloesung")) out.print(" selected='selected'"); %>
                value="Klausurloesung">Klausurlösung</option>
            </select>
            </td>
        </tr>
        

        <!-- LECTURER ID -->
        <tr>
            <td><label for="lecturer">Dozent: *</label></td>
            <td><input type="text" name="lecturer" class="required noSpecialChars"
             id="lecturer" value="<% out.print( Global.decodeUmlauts(lecturer) ); %>"
                    onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'lecturer_id_select');"/>
                <select name="lecturer_id_select" class="" id="lecturer_id_select"
                onchange="document.getElementById('lecturer').value=this.options[this.selectedIndex].text;">
                    <%
                    //fetch all distinct lecturer from database
                    res = Global.query("SELECT `id`, `lecturer` FROM `lecturer`");
                    if (!res.next()) {
                        out.print("<option disabled='disabled'>---(leer)---</option>");
                    } else {
                    	res.beforeFirst();//Because above we already increase by one!!
                        while (res.next()) {
                            //add option
                            out.print("<option"
                            	    + " value='"+ res.getString("id") + "," + res.getString("lecturer") +"'");
                            if (res.getInt("id") == (lecturer_id)) {
                            	out.print(" selected='selected'");
                            }
                            out.print(">" + Global.decodeUmlauts(res.getString("lecturer")) + "</option>");             
                        }
                    }
                    %>
                </select>
            </td>
        </tr>
        
        
        
        <!-- DESCRIPTION -->
        <tr>
            <td><label for="description">Beschreibung &amp; Tags: </label></td>
            <td colspan="2"><input type="text" name="description" value="<% out.print(description); %>"/></td>
        </tr>


        <!-- EMPTY -->
        <tr>
            
        </tr>
        
        
        <!-- FILE -->
        <tr>
            <td><label for="File">Datei: * <span class="anno">(currently not editable)</span></label></td>
            <td><input class="falseFile" disabled="disabled" name="File" type="" type_that_will_change_action.edit_sheet="file" /></td>
        </tr>
        <tr>
        <!-- FILELINK -->
        <td>
            <input type="hidden" name="filelink" class="" value="<%=filelink %>"/></td>
        <!-- SUBMIT -->
        <td><button name="q" type="submit" value="edit_sheet" class="btn btn-primary"
            onclick="show_loader();" style="font-size : 16px;">OK</button></td>
        </tr>
        
    </table>
   
 </form>
 <br/>
<small><b>Mit * markierte Felder dürfen nicht leer sein.</b></small>
</div>
<%
//if (request.getParameter("form") != null
//&& request.getParameter("form").equals("multi")) {
    %>
    <!--jsp:include page='action.upload.jsp' /-->
    <%
//}
%>
<p id="add_result"></p>

