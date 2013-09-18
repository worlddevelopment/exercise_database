<%response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
%>
  <script type="text/javascript">
 
  
$(document).ready(function() {
	$("#semester").autocomplete("get_autocomplete_data.jsp?change=semester", {
		width: 260,
		matchContains: true,
		selectFirst: false
	});
	$("#course").autocomplete("get_autocomplete_data.jsp?change=course", {
		width: 260,
		matchContains: true,
		selectFirst: false
	});
	$("#lecturer").autocomplete("get_autocomplete_data.jsp?change=lecturer", {
		width: 260,
		matchContains: true,
		selectFirst: false
	});
});

</script>
<script>

$(document).ready(function() {
	var hidden_ellements  = new Array();  
	var j = "";

if($("#search").val() =="") {
      //$("#hide1").hide();
      hidden_ellements.push(1);
      j = j+1;
}
if($("#semester").val() =="") {
    //$("#hide2").hide();
    j = j+2;
    hidden_ellements.push(2);
}
if($("#course").val() =="") {
    //$("#hide3").hide();
    j = j+3;
    hidden_ellements.push(3);
}
if($("#lecturer").val() =="") {
    //$("#hide4").hide();
    j = j+4;
    hidden_ellements.push(4);
}
if($("#type :selected").text() == "") {
	j = j+5;
	//$("#hide5").hide();
    hidden_ellements.push(5);
}
if($("#datei_typ :selected").text() == "") {
    //$("#hide6").hide();
    j = j+6;
    hidden_ellements.push(6);
}

if(hidden_ellements.length>0 && hidden_ellements.length<6 ) {

	$("#ausgabe").append("");
		for(var i= 0;i<=hidden_ellements.length;i++) {
			
		    $("#hide" + hidden_ellements[i]).hide();
		}


	$("#showr").click(function (event) {
		 event.preventDefault(); // sehr wichtig,somit wird die seite nicht neu geladen
		for(var i= 0;i<=hidden_ellements.length;i++) {
		
			    $("#hide" + hidden_ellements[i]).show();

		}
		 $("#showr").hide();
	});
}
else
{
	$("#ausgabe").append("Geben Sie ein Suchwort ein!");
	$("#showr").hide();
	
}

});
</script>


<div style="float: left;width: 30%">
<form id = "ext_search_form" method="get"  action="index.jsp?id=ext_search&view=ext_search_result">
    <table >
        <tr id="hide1">
            <td ><label for="search">Suchbegriff: </label></td>
            <td style="width: 500px"><input name="search" id="search" type="text"
                style="min-width: 200px;font-size : 16px;" value ='<%
                if(request.getParameter("search") != null) { // Eingegebener Suchbegriff bleibt im Feld
                	out.print(request.getParameter("search"));
                }
                %>'/></td>
                
        </tr>
        <tr></tr>
       
        <tr id="hide2">
            <td><label for="semester">Semester: </label></td>
            <td style="width: 500px"><input id="semester" name="semester" type="text"
                style="min-width: 200px;font-size : 16px;" value ='<%
                if(request.getParameter("semester") != null) {
                	out.print(request.getParameter("semester"));
                }
                %>'/></td>
        </tr>
        <tr></tr>
        <tr id="hide3">
            <td><label for="course">Veranstaltung: </label></td>
            <td style="width: 500px"><input id="course" name="course" type="text"
                style="min-width: 200px;font-size : 16px;" value ='<%
                if(request.getParameter("course") != null) {
                	out.print(request.getParameter("course"));
                }
                %>'/></td>
        </tr>
         <tr id="hide4">
            <td><label for="lecturer">Dozent: </label></td>
            <td style="width: 500px"><input id="lecturer" name="lecturer" type="text"
                style="min-width: 200px;font-size : 16px;" value ='<%
                if(request.getParameter("lecturer") != null) {
                	out.print(request.getParameter("lecturer"));
                }
                %>'/></td>
        </tr>
        <tr id="hide5">
            <td><label for="type">Typ: </label></td>
            <td style="width: 500px">
            <select style="min-width: 200px;font-size : 16px;" name="type" id="type">
            <option style="min-width: 200px;font-size : 16px;"></option>
            <option style="min-width: 200px;font-size : 16px;"<%if(request.getParameter("type")!= null &&
            		request.getParameter("type").equals("Übung")){
            		%>selected="selected"<%
            				}%>
            >Übung</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(request.getParameter("type")!= null &&
            		request.getParameter("type").equals("Lösung")){
            		%>selected="selected"<%
            				}%>
           	>Lösung</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(request.getParameter("type")!= null &&
            		request.getParameter("type").equals("Klausur")){
            		%>selected="selected"<%
            				}%>
            >Klausur</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(request.getParameter("type")!= null &&
            		request.getParameter("type").equals("Klausurlösung")){
            		%>selected="selected"<%
            				}%>
            >Klausurlösung</option>
            </select></td>
        </tr>
        
         <tr id="hide6">
            <td><label for="datei_typ">Dateityp: </label></td>
            <td style="width: 500px">
            <select style="min-width: 200px;font-size : 16px;" name="datei_typ" id="datei_typ">
            <option style="min-width: 200px;font-size : 16px;"></option>
            <option style="min-width: 200px;font-size : 16px;" <%if(request.getParameter("datei_typ")!= null &&
            		request.getParameter("datei_typ").equals("pdf")){
            		%>selected="selected"<%
            				}%>
            >pdf</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(request.getParameter("datei_typ")!= null &&
            		request.getParameter("datei_typ").equals("docx")){
            		%>selected="selected"<%
            				}%>
           	>docx</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(request.getParameter("datei_typ")!= null &&
            		request.getParameter("datei_typ").equals("doc")){
            		%>selected="selected"<%
            				}%>
            >doc</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(request.getParameter("datei_typ")!= null &&
            		request.getParameter("datei_typ").equals("rtf")){
            		%>selected="selected"<%
            				}%>
            >rtf</option>
            </select></td>
        </tr>
        <tr>
		<td></td>
		<td ><button id='showr' style="min-width: 220px;font-size : 16px;text-align: left">Mehr Suchkriterien</button></td>
		</tr>
		<tr>
		<td></td>
		<td> <button>Suche</button></td>
		</tr>
    </table>
   
 </form>
 </div>

 <div style="float: right;max-width: 375px;">
 <div style="background-color: #49AFCD;padding: 0.4em">
 <h2 style="font-size: 0.7em;">Suchtipps</h2>
 </div>
 <div style="font-size: 0.7em;border: 1px solid #49AFCD;padding: 0.4em">
 <p></p>
 <strong>Groß- und Kleinschreibung</strong> werden bei der Suche nicht unterschieden.
 <p></p>
	<strong>*</strong> ersetzt beliebig viele Zeichen</br>
	<strong>?</strong> ersetzt genau ein Zeichen
<p></p>
	<strong>genaue Suche</strong> : Begriffe in Anführungszeichen werden genau wie eingegeben gesucht
	<p></p>
 	<strong>Kombination Suchbegriff + Feld/er</strong> : In diesem Fall wird sowohl im Inhalt der Datei und beim jeweiligen Datenbankeintrag des Feldes bzw. der Felder gesucht.
	
 </div>
 </div>
 <div id="ausgabe" style="clear: both;width: 30%"></div>
<!--  <p id="add_result"><p> -->
 <%
if (request.getParameter("view") != null
&& request.getParameter("view").equals("ext_search_result")) {

	%>
	<jsp:include page='ext_search_result.jsp' />
	<%
}
%>