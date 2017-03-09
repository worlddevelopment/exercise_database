<%@page import="aufgaben_db.Global;" %>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");
%>

<style type="text/css">

em {
	font-weight: bold;
	padding-right: 1em;
	vertical-align: top;
}
input[name^="description"] {
	width: 100%;
}
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
.auto-style1 {
	font-size: large;
	font-weight: bold;
}
.submit {
	margin-left: 12em;
}


</style>






<!-- BOOTSTRAP -->
<script src="bootstrap/js/bootstrap-tooltip.js"></script>
<script src="bootstrap/js/bootstrap-popover.js"></script>

<!-- Form-Validierung -->
<script type="text/javascript" src="jquery/jquery.validate.min.js"></script>
<!-- ToolTip -->
<script type="text/javascript" src="jquery/toolTipPreview.js"></script>
<script type="text/javascript" charset="utf-8" src="js/validateForm.js"></script>

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

	$.validator.addMethod("noSpecialChars", function(value, element) {
		//return this.optional(element) || value.match(/[^\\*\.\+\?\\\/<>:!\\"\\'Â§\\$%&]+$/);
	   return this.optional(element) || value.match(/^[a-zA-Z0-9\_\u00FC\u00F6\u00E4\u00DC\u00D6\u00C4\/\s\(\)\.]+$/);
	}, "nicht zul&auml;ssige Zeichen");

	$.validator.addMethod(
			"falseFile"
			, function(value, element) {
				//return this.optional(element) || value.match(/[^\\*\.\+\?\\\/<>:!\\"\\'Â§\\$%&]+$/);
				//return this.optional(element) || value.match(/[.](^xls)$/);
				return this.optional(element)/*no semicolon allowed here!*/
				|| value.match(/[.](<%=Global.getSupportedFiletypesAsString().replaceAll(",[ ]?", ")|(")%>)$/i);
   			}
			, "<%=Global.display("format not permitted")%>(<%=Global.display("supported types")%>: <%=Global.getSupportedFiletypesAsString()%>)"
	);

	/*
	// Lecturer field must not be empty. Else choose N.N. automatically.
	$.validator.addMethod("falseFile", function(value, element) {
	   return this.optional(element) || value.equals(""));
	}, "New lecturer will be inserted? Clear input field to make select list choosable again.");
	*/

	$("#commentForm").validate();
  });
</script>


<!-- JQUERY -->
<script type="text/javascript">
function show_loader() {
	if($("#commentForm").valid()) {
		$(".loader").show();
		$("#form").hide();
		return true; // Continue and submit.
	}
	return false; // Prevents the form from being submitted to the server.
}

if($(document).ready()) {
	$(".loader").hide();
	$("#form").show();
}

$(function () {
	$("#info_tooltip").tooltip({ placement: 'right'});
});


deactivateIfFilledOrActivateIfEmpty = function(caller, differentTargetObjOrId) {
	caller = arguments[0] || alert('At least one object, the caller has to be given to act upon.');
	differentTargetObjOrId = arguments[1];
	// Usually caller is easier to give as a object using 'this'. Anyway:
	if (typeof caller != 'object') {
		//it's an id string (high possibility)
		caller = document.getElementById(caller);
	}
	// Now we have a caller at least.
	// Check if we shall activate us? Strange enough this option
	var targetObj = differentTargetObjOrId;
	if (!differentTargetObjOrId || differentTargetObjOrId == 'undefined') {
		targetObj = caller;
	}
	else if (typeof differentTargetObjOrId == 'string') {
		// It's an id string (high possibility)
		targetObj =
		differentTargetObjOrId = document.getElementById(differentTargetObjOrId);
	}

	// Now we have a target object to direct our actions to.
	if (caller.value == '') {
		// Activate the target object.
		targetObj.disabled = false;
		return ;
	}
	// Deactivate the target object.
	targetObj.disabled = true;
	// Also show a message.
	//targetObj.parentNode.
	return;
}




</script>
