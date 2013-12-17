/*<script type="text/javascript">//<!--*/

var getCheckedCount = function(elements_array_name) {
    elements_array_name = elements_array_name || "sheetdraft_id[]";
    
    var elements = document.getElementsByName(elements_array_name);
    var check = 0;
    for (var zaehler = 0; zaehler < elements.length; zaehler++) {
        if (elements[zaehler].checked) {
            check++;
        }
    }
    
    return check;
    
};

$(document).ready(function() {
	
	$('#delete_btn').click(function() {
	
	    var check = getCheckedCount("sheetdraft_filelinks[]");
	    
	    if (check > 0) {
	        var answer = confirm("M\u00F6chten Sie die " + check +
	               (check == 1 ? " Datei" : " Dateien") + " wirklich l\u00F6schen?");
	        if (answer == true) {
	            return true;
	        } 
	        return false;
	    } 
	    alert("Kein Blatt wurde Ausgew\u00E4hlt");
	    return false;
	});

	$('#edit_btn').click(function() {
	
	    var check = getCheckedCount("sheetdraft_filelinks[]");
	
	    if (check == 0) {
	        alert("Kein Blatt wurde Ausgew\u00E4hlt");
	        return false;
	    } 
	    return true;
	    
	});
	
	
	
	$('#add_to_draft_btn').click(function() {

		var count = getCheckedCount("sheetdraft_filelinks[]");
		var exerciseCount = getCheckedCount("exercise_filelinks_to_add[]");
		if (exerciseCount == 0) {
			exerciseCount = getCheckedCount("exercise_filelinks[]");
		}
	    if (count == 0 && exerciseCount == 0) {
	        alert("Weder ein Blatt noch eine Aufgabe wurde ausgew\u00E4hlt.");
	        return false;
	    }
	    else if (count > 0) {
	    	alert("Momentan wird das Hinzuf\u00FCgen ganzer Bl\u00E4tter nicht unterst\u00FCtzt.\r\nKeine Aufgaben ausgew\u00E4hlt.");
	    }
	    return true;
	    
	});
	
	
	
	
	$('#search_btn').click(function() {

		//var el = $('#search_btn').previousSibling;
		var el = document.getElementById('search_string');
		if (el.value == "") {
			alert("Bitte geben Sie einen Suchbegriff ein.");
			return false;
		}
		
		//el.value = encodeURI(el.value, "iso-8859-1");
	    return true;
	    
	});
	
	//using our fairytale achievements 
	/* it's working but the result using search string field is crazy */
	//addEvent(document.getElementById('search_string'), 'keyup', (function() {
	/*addEvent(document.getElementById('search_btn'), 'click', (function() {
        //alert("reached fairytales");
		var el = document.getElementById('search_string');
		if (el.value == "") {
			alert("Bitte geben Sie einen Suchbegriff ein.");
			return false;
			
		}
		
		el.value = encodeURI(el.value);
	    return true;
	    
	}));
	*/
	
	
	/*
	$('#show_search_string').ready(function() {

		//var el = $('#search_btn').previousSibling;
		var el = document.getElementById('show_search_string');
		el.value = decodeURI(el.value, "iso-8859-1");
	    return true;
	    
	});
	
	*/
	
});
/*//--></script>*/