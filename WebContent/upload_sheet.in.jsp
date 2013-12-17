<%@page import="java.util.*, java.text.*,aufgaben_db.*, java.sql.ResultSet;" %>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");
%>



<jsp:include page="js/validateForm.js.jsp" />





<!-- FORM -->
<div id="form">
<!-- MULTIPART CONTENT FORM
request.getAttribute("attr") will be NULL if not in action part!!! if multiform/form-data
see also http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet

 -->

<form class="cmxform" id="commentForm" method="post"  action="?id=upload_sheet&q=upload#add_result"
enctype="multipart/form-data">
   <!-- To steer the ship. -->
   <!--
   <input type="hidden" name="q" value="upload" />
   <input type="hidden" name="id" value="upload_sheet" />
   -->
   <p class="auto-style1 screenshot">Blatt hinzuf&uuml;gen:<a id="info_tooltip" href='#' rel="tooltip" title="Es sind nur alphanumerische und _ . ) ( Leerzeichen /  Symbole erlaubt."><img src="pictures/info3.png" /></a></p>
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
                        if (semterm == "SS") {
                        	mMin = 4 - 1; /*<-- April as first month of semester.*/
                        	mMax = 9 - 1; /*<-- September as last month of semester.*/
                        	semterm_condition = nowMonth >= mMin && nowMonth <= mMax;
                        }
                        out.print("<option value='" + semterm + "_" + y + "'"
                        + ((y == Integer.valueOf(Global.now.get(Calendar.YEAR))
                        		&& semterm_condition) ? " selected='selected' ": "")
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
             id="course" value="" onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'course_select');"/>
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
                        out.print("<option selected='selected' disabled='disabled'>-------</option>");
                    } else {
	                   	while (res.next()) {
	                        //add option
	                        out.print("<option value='"+ res.getString("course") +"'>"
			                        + Global.decodeUmlauts(res.getString("course"))
			                        + "</option>");             
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
            <option value="Uebung" selected="selected">Übung</option>
            <option value="Loesung">Lösung</option>
            <option value="Klausur">Klausur</option>
            <option value="Klausurloesung">Klausurlösung</option>
            </select>
            </td>
        </tr>
        

        <!-- LECTURER ID -->
        <tr>
            <td><label for="lecturer">Dozent: *</label></td>
            <td><input type="text" name="lecturer" class="required noSpecialChars"
             id="lecturer" value=""  onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'lecturer_id_select');"/>
                <select name="lecturer_id_select" class="" id="lecturer_id_select"
                onchange="document.getElementById('lecturer').value=this.options[this.selectedIndex].text;">
	                <%
	                //fetch all distinct lecturer from database
	                res = Global.query("SELECT `id`, `lecturer` FROM `lecturer`" /*LIMIT 0, 30*/);
	                if (!res.next()) {
	                	out.print("<option disabled='disabled'>---(leer)---</option>");
	                } else {
	                	res.beforeFirst();//Because above we already increase by one!!
		                while (res.next()) {
		                    //add option
		                    out.print("<option value='"+ res.getString("id") + ","
		                              + res.getString("lecturer") +"'>"
		                    		  + Global.decodeUmlauts(res.getString("lecturer"))
		                    		  + "</option>");             
		                }
	                }
	                %>
                </select>
            </td>
        </tr>
        
        
        
        <!-- DESCRIPTION -->
        <tr>
            <td><label for="description">Beschreibung &amp; Tags: </label></td>
            <td colspan="2"><input type="text" name="description" value=""/></td>
        </tr>


        <!-- EMPTY -->
        <tr>
            
        </tr>
        
        
        <!-- FILE -->
        <tr>
            <td><label for="File">Datei: *</label></td>
            <td><input class="required falseFile" name="File" type="file" /></td>
        </tr>
		<tr>
		<td></td>
		<td> <button name="q" type="submit" value="upload" onclick="/*validate();*/show_loader();" style="font-size : 16px;"
			 class="btn btn-secondary"><i class="icon-gift"></i> Upload
			</button>
		</td>
		</tr>
		
    </table>
   
 </form>
 <br/>
<small><b>Mit * markierte Felder d&uuml;rfen nicht leer sein.</b></small>
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

