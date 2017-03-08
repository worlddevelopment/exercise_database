<%@page import="java.util.*, java.text.*,aufgaben_db.*, java.sql.ResultSet;" %>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");
%>


<!-- Since we don't know if we need it at several places at once we have it in a separate file. -->
<jsp:include page="js/validateForm.js.jsp" />




<!-- FORM -->
<div id="form">
<!-- MULTIPART CONTENT FORM
request.getAttribute("attr") will be NULL if not in action part!!! if multiform/form-data
see also http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet

 -->

<form class="cmxform" id="commentForm" method="post"  action="?id=upload_sheet&q=upload#add_result"
enctype="multipart/form-data">
   <input type="hidden" name="uploader" value="<%= Global.session.getAttribute("user") %>" />
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
                <jsp:include page="input.semester.jsp" />
            </td>
            
        </tr>
        
        
        <!-- COURSE -->
        <tr>
            <td><label for="course">Veranstaltung: *</label></td>
            <td>
                <jsp:include page="input.course.jsp"/>
            </td>
            
        </tr>


        <!-- TYPE -->
        <tr>
            <td><label for="type">Typ: *</label></td>
            <td>
                <jsp:include page="input.type.jsp"/>
            </td>
        </tr>
        

        <!-- LECTURER ID -->
        <tr>
            <td><label for="lecturer">Dozent: *</label></td>
            <td>
                 <jsp:include page="input.lecturer.jsp" />
            </td>
        </tr>
        
        
        
        <!-- DESCRIPTION -->
        <tr>
            <td><label for="description">Beschreibung &amp; Tags: </label></td>
            <td colspan="2">
                <jsp:include page="input.generic.jsp">
                    <jsp:param name="name" value="description"/>
                </jsp:include>
            </td>
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

