<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="swp.MysqlHelper"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
%>


<style type="text/css">
.auto-style1 {
	font-size: large;
	font-weight: bold;
}
</style>
<style type="text/css">
label {
	width: 10em;
	float: left;
}

label.error {
	float: none;
	color: red;
	padding-left: .5em;
	vertical-align: top;
}

p {
	clear: both;
}

.submit {
	margin-left: 12em;
}

em {
	font-weight: bold;
	padding-right: 1em;
	vertical-align: top;
}
</style>



<!-- Form-Validierung -->
<script type="text/javascript" src="jquery/jquery.validate.min.js"></script>
<!-- ToolTip -->
<script type="text/javascript" src="jquery/toolTipPreview.js"></script>


<script type="text/javascript" charset="utf-8">
	$(document)
			.ready(
					function() {

						$.validator
								.addMethod(
										"noSpecialChars",
										function(value, element) {
											//return this.optional(element) || value.match(/[^\\*\.\+\?\\\/<>:!\\"\\'Â§\\$%&]+$/);
											return this.optional(element)
													|| value
															.match(/^[a-zA-Z0-9\_\u00FC\u00F6\u00E4\u00DC\u00D6\u00C4\/\s\(\)\.]+$/);
										}, "nicht zulässige Zeichen");
						$.validator.addMethod("falseFile", function(value,
								element) {
							//return this.optional(element) || value.match(/[^\\*\.\+\?\\\/<>:!\\"\\'Â§\\$%&]+$/);
							return this.optional(element)
									|| value.match(/[^\.odt]+$/);
						}, ".odt-Format nicht erlaubt");
						$("#commentForm").validate();
					});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#sem").autocomplete("get_autocomplete_data.jsp?change=semester", {
			width : 260,
			matchContains : true,
			selectFirst : false
		});
		$("#ver").autocomplete("get_autocomplete_data.jsp?change=course", {
			width : 260,
			matchContains : true,
			selectFirst : false
		});
		$("#doz").autocomplete("get_autocomplete_data.jsp?change=lecturer", {
			width : 260,
			matchContains : true,
			selectFirst : false
		});
	});
	
	
	
</script>



<div class="loader" id="loader">
	<img src="pictures/loader.gif" alt="Please wait..." /> Bitte warten...
</div>

<script type="text/javascript">
	function show_loader() {
		if ($("#commentForm").valid()) {
			$(".loader").show();
			$("#edit_form").hide();
		}
	}
	if ($(document).ready()) {
		$(".loader").hide();
		$("#edit_form").show();
	}
	$(function ()  
			{ $("#info_tooltip").tooltip({placement: 'right'});  
			});
</script>


<%

String datei_pfad = "";
String bild_pfad = "";
String bl_bez = "";
String sem_bez = "";
String ver_bez = "";
String typ_bez = "";
String doz_bez = "";
String ver_id = "";
String doz_id = "";
String sem_id = "";
String ext = "";
String beschreibung = "";
String [] values = request.getParameterValues("bl_id[]");
if(request.getParameterValues("bl_id[]") != null) {
	

		//for (int i = 0; i < values.length; i++) {
			MysqlHelper msqh = new MysqlHelper();
			msqh.establishConnection(response);
			Statement statement1 = null;

			
			Connection con = msqh.establishConnection(response);
			try {
				statement1 = con.createStatement();
				String str_query = "select * from blatt b,veranstaltung v,semester s,typ t,dozent d where b.id='" + values[0] + "' and b.doz_id = d.id and b.sem_id = s.id and b.ver_id = v.id and b.typ_id = t.id;";
				
				ResultSet res = statement1.executeQuery(str_query);
				res.next();
				
			    //Holle Daten von Blatt
			    datei_pfad = res.getString("link");
				bild_pfad = res.getString("bild_link");
				bl_bez = res.getString("bl_bez");
				ext = res.getString("datei_typ");
				ver_id = res.getString("ver_bez");
			    doz_id = res.getString("doz_id");
			    sem_id = res.getString("sem_id");
			    beschreibung = res.getString("beschr");
			    
			    sem_bez = res.getString("sem_bez");
			    ver_bez = res.getString("ver_bez");
			    typ_bez = res.getString("typ_bez");
			    doz_bez = res.getString("name");
			    
				statement1.close();
			}
			catch (SQLException e) {
		        e.printStackTrace();
		   	}
		
}


%>
<script src="bootstrap/js/bootstrap-tooltip.js"></script>  
<script src="bootstrap/js/bootstrap-popover.js"></script>  
<div id="edit_form">
	<form class="cmxform" id="commentForm" method="post"
		action="index.jsp?id=edit">
		<p class="auto-style1 screenshot">&Auml;ndern: 
			<a id="info_tooltip" class="btn btn-info"
				data-original-title	='Es sind nur alphanumerische und "_  . ) ( Leerzeichen / "  Symbole erlaubt.'><i class="icon-info-sign icon-white"></i></a>
		</p>
		<table>
			<tr>
				<td><label for="name">Dateiname: *</label></td>
				<td style="width: 500px"><input name="name" value='<%out.print(bl_bez);%>'
					class="required noSpecialChars" type="text"
					style="min-width: 200px; font-size: 16px;"
					value="<%request.getAttribute("bl_id[]");%>" /></td>
			</tr>
			<tr>
			<tr>
				<td><label for="beschr">Beschreibung: </label></td>
				<td style="width: 500px"><input type="text" value='<%out.print(beschreibung);%>'name="beschr"
					style="min-width: 200px; font-size: 16px;"></td>
			</tr>
			<tr>
				<td><label for="sem">Semester: *</label></td>
				<td style="width: 500px"><input type="text" value='<%out.print(sem_bez);%>' name="sem"
					style="min-width: 200px; font-size: 16px;"
					class="required noSpecialChars" id="sem" /></td>

			</tr>
			<tr>
				<td><label for="veranstaltung">Veranstaltung: *</label></td>
				<td style="width: 500px"><input type="text" value='<%out.print(ver_bez);%>'
					name="veranstaltung" class="required noSpecialChars" id="ver"
					style="min-width: 200px; font-size: 16px;" /></td>

			</tr>

			<tr>
				<td><label for="dozent">Dozent: *</label></td>
				<td style="width: 500px"><input type="text" value='<%out.print(doz_bez);%>' name="dozent"
					class="required noSpecialChars" id="doz"
					style="min-width: 200px; font-size: 16px;" /></td>
			</tr>

			<tr>
				<td><label for="typ">Typ: *</label></td>
				<td style="width: 500px"><select
					class="required noSpecialChars" name="typ"
					style="min-width: 200px; font-size: 16px;">
						<option style="min-width: 200px;font-size : 16px;"<%if(typ_bez.equals("Übung")){
            		%>selected="selected"<%
            				}%>
            >Übung</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(typ_bez.equals("Lösung")){
            		%>selected="selected"<%
            				}%>
           	>Lösung</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(typ_bez.equals("Klausur")){
            		%>selected="selected"<%
            				}%>
            >Klausur</option>
            <option style="min-width: 200px;font-size : 16px;"<%if(typ_bez.equals("Klausurlösung")){
            		%>selected="selected"<%
            				}%>
            >Klausurlösung</option>
				</select></td>
			</tr>
			<tr>

			</tr>
			<tr>
				<td></td>
				<td>
				<input type="hidden" name="bl_id" value='<%out.print(values[0]);%>'/>
					<button id="aendern" class="btn btn-warning" type="submit"
						>
						<i class="icon-edit icon-white"></i> &Auml;ndern
					</button>
				</td>
			</tr>

		</table>

	</form>
	<br /> <small><b>Mit * markierte Felder dürfen nicht leer
			sein.</b></small>
</div>